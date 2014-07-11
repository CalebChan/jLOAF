package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public DBNRetrieval(CaseBase cb, int iterations, CaseRun run, int internalState){
		this.cb = cb;
		this.run = run;
		this.internalState = internalState;
		em = new ExpectationMaximization(this.cb.getSize() + 1);
		buildModel(iterations);
	}
	
	public void setCaseRun(CaseRun run){
		this.run = run;
	}
	
	private void buildModel(int iterations){
		Matrix input[] = new Matrix[this.cb.getSize() + 1];
		Matrix output[] = new Matrix[this.cb.getSize() + 1];
		int index = 1;
		for (Case c : this.cb.getCases()){
			List<AtomicInput> inList = unravelInput(c.getInput());
			input[index] = new Matrix(inList.size(), 1, 0);
			for (int j = 0; j < inList.size(); j++){
				input[index].set(j, 0, inList.get(j).getFeature().getValue());
			}
			
			output[index] = new Matrix(9, 1, 0);
			List<AtomicAction> actionList = unravelAction(c.getAction());
			if (actionList.size() == 0){
				System.out.println("No Action");
				index++;
				continue;
			}
			if (actionList.get(0).getFeature() == null){
				System.out.println("No Feature : " + actionList.get(0).getName() + ", Index : " + index);
				index++;
				continue;
			}
			output[index].set((int) actionList.get(0).getFeature().getValue() , 0, 1);
			index++;
		}
		initializeMatrixies(input[1].getRowDimension(), output[1].getRowDimension(), output[1].getColumnDimension());
		
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
		b = Matrix.random(this.internalState, inputRow);
		a = Matrix.random(this.internalState, this.internalState);
		c = Matrix.random(outputRow, this.internalState);
		m = Matrix.random(outputRow, inputRow);
		
		r = Matrix.random(outputRow, outputRow);
		q = Matrix.random(this.internalState, this.internalState);
		
		mean = Matrix.random(this.internalState, outputCol);
		covar = Matrix.random(this.internalState,this.internalState);
	}
	
	private List<AtomicAction> unravelAction(Action action){
		ArrayList<AtomicAction> actionList = new ArrayList<AtomicAction>();
		Stack<ComplexAction> actionStack = new Stack<ComplexAction>();
		
		if (action instanceof AtomicAction){
			AtomicAction a = (AtomicAction)action;
			actionList.add(a);
			return actionList;
		}
		actionStack.push((ComplexAction)action);
		while(!actionStack.isEmpty()){
			ComplexAction c = actionStack.pop();
			for (String s : c.getChildNames()){
				if (c.get(s) instanceof AtomicAction){
					AtomicAction a = (AtomicAction) c.get(s);
					actionList.add(a);
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
		HashMap<Integer, String> actionMap = new HashMap<Integer, String>();
		for (int j = 0; j < this.run.getRunLength(); j++){
			Case c = this.run.getCase(j);
			List<AtomicInput> inList = unravelInput(c.getInput());
			input[j + 1] = new Matrix(inList.size(), 0, 0);
			for (int k = 0; k < inList.size(); k++){
				input[k + 1].set(k, 1, inList.get(k).getFeature().getValue());
			}
			
			List<AtomicAction> actionList = unravelAction(c.getAction());
			output[j + 1] = new Matrix(9, 1, 0);
			int val = (int) actionList.get(0).getFeature().getValue();
			output[j + 1].set(val , 0, 1);
			actionMap.put(val, actionList.get(0).getName());
		}
		inference.fowardProp(c, a, r, q, b, m, input, output);
		Matrix result = inference.getXtXt(this.run.getRunLength());
		Matrix predict = inference.getCnew(output, input).times(result).plus(inference.getBnew(output, input).times(input[input.length - 1]));
		double greatestValue = -1;
		int index = -1;
		for (int k = 0; k < predict.getRowDimension(); k++){
			if (predict.get(k, 0) > greatestValue){
				greatestValue = predict.get(k, 0);
				index = k;
			}
		}
		AtomicAction a = new AtomicAction(actionMap.get(index));
		a.addFeature(new Feature(index));
		List<Case> cases = new ArrayList<Case>();
		Case newCase = new Case(i, a, this.run.getCurrentCase());
		cases.add(newCase);
		return cases;
	}
}
