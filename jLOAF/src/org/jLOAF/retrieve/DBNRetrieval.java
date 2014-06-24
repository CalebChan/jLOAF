package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jLOAF.action.Action;
import org.jLOAF.action.AtomicAction;
import org.jLOAF.action.ComplexAction;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Feature;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.dbn.ExpectationMaximization;

import weka.core.matrix.Matrix;

public class DBNRetrieval implements Retrieval{
	
	private CaseBase cb;
	
	private ExpectationMaximization em;
	
	private Matrix a;
	private Matrix b;
	private Matrix c;
	private Matrix m;
	private Matrix r;
	private Matrix q;
	
	private Matrix mean;
	private Matrix covar;
	
	private CaseRun run;
	private int internalState;
	
	private static final int DEFAULT_MATRIX_VALUE = 1;
	
	public DBNRetrieval(CaseBase cb, int iterations, CaseRun run, int internalState){
		this.cb = cb;
		this.run = run;
		this.internalState = internalState;
		em = new ExpectationMaximization(this.cb.getSize() + 1);
		buildModel(iterations);
	}
	
	private void buildModel(int iterations){
		Matrix input[] = new Matrix[this.cb.getSize() + 1];
		Matrix output[] = new Matrix[this.cb.getSize() + 1];
		int index = 1;
		for (Case c : this.cb.getCases()){
			List<AtomicInput> inList = unravelInput(c.getInput());
			input[index] = new Matrix(inList.size(), 1, 0);
			for (int j = 0; j < inList.size(); j++){
				input[index].set(j, 1, inList.get(j).getFeature().getValue());
			}
			
			List<Feature> actionList = unravelAction(c.getAction());
			output[index] = new Matrix(actionList.size(), 1, 0);
			for (int j = 0; j < actionList.size(); j++){
				output[index].set(j , 1, actionList.get(j).getValue());
			}
			index++;
		}
		initializeMatrixies(input[0].getRowDimension(), output[0].getRowDimension(), output[0].getColumnDimension());
		
		em.calculateEM(iterations, 0, mean, covar, c, a, r, q, m, b, input, output);
		mean = em.getNewMean();
		covar = em.getNewCovariance();
		c = em.getCnew(output, input);
		a = em.getAnew(output, input);
		r = em.getRnew(output, input);
		q = em.getQnew(output, input);
		m = em.getMnew(output, input);
		b = em.getBnew(output, input);
	}
	
	private void initializeMatrixies(int inputRow, int outputRow, int outputCol){
		b = new Matrix(this.internalState, inputRow, DEFAULT_MATRIX_VALUE);
		a = new Matrix(this.internalState, this.internalState, DEFAULT_MATRIX_VALUE);
		c = new Matrix(outputRow, this.internalState, DEFAULT_MATRIX_VALUE);
		m = new Matrix(outputRow, inputRow, DEFAULT_MATRIX_VALUE);
		
		r = new Matrix(outputRow, outputRow, DEFAULT_MATRIX_VALUE);
		q = new Matrix(this.internalState, this.internalState, DEFAULT_MATRIX_VALUE);
		
		mean = new Matrix(outputCol, this.internalState, DEFAULT_MATRIX_VALUE);
		covar = new Matrix(this.internalState,this.internalState, DEFAULT_MATRIX_VALUE);
	}
	
	private List<Feature> unravelAction(Action action){
		ArrayList<Feature> actionList = new ArrayList<Feature>();
		Stack<ComplexAction> actionStack = new Stack<ComplexAction>();
		
		if (action instanceof AtomicAction){
			AtomicAction a = (AtomicAction)action;
			return a.getFeatures();
		}
		actionStack.push((ComplexAction)action);
		while(!actionStack.isEmpty()){
			ComplexAction c = actionStack.pop();
			for (String s : c.getChildNames()){
				if (c.get(s) instanceof AtomicAction){
					AtomicAction a = (AtomicAction) c.get(s);
					actionList.addAll(a.getFeatures());
				}else{
					actionStack.push((ComplexAction) c.get(s));
				}
			}
		}
		
		return actionList;
	}
	
	private List<AtomicInput> unravelInput(Input input){
		ArrayList<AtomicInput> inputList = new ArrayList<AtomicInput>();
		Stack<ComplexInput> inputStack = new Stack<ComplexInput>();
		
		if (input instanceof AtomicInput){
			AtomicInput a = (AtomicInput) input;
			inputList.add(a);
			return inputList;
		}
		
		inputStack.push((ComplexInput) input);
		
		while(!inputStack.isEmpty()){
			ComplexInput c = inputStack.pop();
			for (String s : c.getChildNames()){
				if (c.get(s) instanceof AtomicInput){
					inputList.add((AtomicInput) c.get(s));
				}else{
					inputStack.push((ComplexInput) c.get(s));
				}
			}
		}
		return inputList;
	}
	
	@Override
	public List<Case> retrieve(Input i) {
		ExpectationMaximization inference = new ExpectationMaximization(run.getRunLength() + 1);
		Matrix input[] = new Matrix[this.run.getRunLength() + 1];
		Matrix output[] = new Matrix[this.run.getRunLength() + 1];
		for (int j = 0; j < this.run.getRunLength(); j++){
			Case c = this.run.getCase(j);
			List<AtomicInput> inList = unravelInput(c.getInput());
			input[j + 1] = new Matrix(inList.size(), 1, 0);
			for (int k = 0; k < inList.size(); k++){
				input[k + 1].set(k, 1, inList.get(k).getFeature().getValue());
			}
			
			List<Feature> actionList = unravelAction(c.getAction());
			output[j + 1] = new Matrix(actionList.size(), 1, 0);
			for (int k = 0; k < inList.size(); k++){
				output[k + 1].set(k , 1, actionList.get(k).getValue());
			}
		}
		inference.fowardProp(c, a, r, q, b, m, input, output);
		Matrix result = inference.getXtXt(this.run.getRunLength());
		Matrix predict = inference.getCnew(output, input).times(result).plus(inference.getBnew(output, input).times(input[input.length - 1]));
		double greatestValue = -1;
		for (int k = 0; k < predict.getRowDimension(); k++){
			if (predict.get(k, 0) > greatestValue){
				greatestValue = predict.get(k, 0);
			}
		}
		AtomicAction a = new AtomicAction("");
		a.addFeature(new Feature(greatestValue));
		List<Case> cases = new ArrayList<Case>();
		Case newCase = new Case(i, a, this.run.getCurrentCase());
		cases.add(newCase);
		return cases;
	}
}
