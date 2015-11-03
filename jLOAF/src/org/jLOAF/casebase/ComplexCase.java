package org.jLOAF.casebase;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;

public class ComplexCase extends Case {
	
	protected static SimilarityCaseMetricStrategy<ComplexCase> s_metric;
	
	public static boolean isClassStrategySet(){
		if(ComplexCase.s_metric == null){
			return false;
		}else{
			return true;
		}
	}

	public static void setClassGlobalStrategy(SimilarityCaseMetricStrategy<ComplexCase> s){
		ComplexCase.s_metric = s;
	}
	
	@Override
	public double similarity(Case c) {
		//See if the user has defined similarity for each specific input, for all inputs
		//  of a specific type, of defered to superclass
		if(ComplexCase.isClassStrategySet()){
			return s_metric.similarity(this, c);
		}else{
			//normally we would defer to superclass, but super
			// is abstract
			System.err.println("Problem. In AtomicCase no similarity metric set!");
			return 0;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Case> pastCases;
	
	/**
	 * Encapsulates a run
	 * @param input The input of the current case
	 * @param action The action of the current case
	 * @param pastCase A list of past cases in a run where the index of the list is the offset in the past for the current
	 */
	public ComplexCase(Input input, Action action, List<Case> pastCase) {
		super(input, action);
		
		this.pastCases = new ArrayList<Case>();
		if (pastCase != null){
			this.pastCases = pastCase;
		}
	}
	
	/**
	 * Create a default constructor. To use the object must call pushCurrentCase to fill the case
	 */
	public ComplexCase(){
		this(null, null);
	}
	
	public ComplexCase(AtomicCase c){
		this(c.in, c.act);
	}
	
	public ComplexCase(Input input, Action action){
		this(input, action, null);
	}
	
	public ComplexCase(List<Case> caseList){
		this(caseList.get(0).in, caseList.get(0).act);
		if (caseList.size() > 1){
			this.pastCases = new ArrayList<Case>(caseList.subList(1, caseList.size()));
		}
	}
	
	public ComplexCase(ComplexCase c){
		super(c.in, c.act);
		this.pastCases = new ArrayList<Case>(c.pastCases);
	}
	
	public List<Case> toArrayList(){
		ArrayList<Case> array = new ArrayList<Case>();
		array.add(this.getCurrentCase());
		array.addAll(this.getPastCases());
		return array;
	}
	
	public List<ComplexCase> getSubRunsEqual(Input input){
		List<ComplexCase> runs = new ArrayList<ComplexCase>();
		
		if (this.in.similarity(input) >= 1.0){
			runs.add(this);
		}
		List<Case> past = getPastCases();
		for (int i = 0; i < past.size(); i++){
			if (past.get(i).getInput().similarity(input) >= 1.0){
				runs.add(new ComplexCase(past.subList(i, past.size())));
			}
		}
		
		return runs;
	}
	
	public List<ComplexCase> getSubRuns(Input input, double threshold){
		List<ComplexCase> runs = new ArrayList<ComplexCase>();
		double sim = this.in.similarity(input);
		if (sim > threshold){
			runs.add(this);
		}
		List<Case> past = getPastCases();
		for (int i = 0; i < past.size(); i++){
			if (past.get(i).getInput().similarity(input) > threshold){
				runs.add(new ComplexCase(past.subList(i, past.size())));
			}
		}
		
		return runs;
	}

	public int getComplexCaseSize(){
		int total = 1;
		for (Case c : this.pastCases){
			if (c instanceof ComplexCase){
				ComplexCase cc = (ComplexCase)c;
				total += cc.getComplexCaseSize();
			}else{
				total++;
			}
		}
		return total;
	}
	
	public AtomicCase getCurrentCase(){
		return new AtomicCase(this.in, this.act);
	}
	
	/**
	 * This method will get all past cases. If a past case is a complex case it will unroll it
	 * @return
	 */
	public List<Case> getPastCases(){
		List<Case> past = new ArrayList<Case>();
		for (Case c : this.pastCases){
			if (c instanceof ComplexCase){
				ComplexCase cc = (ComplexCase)c;
				past.add(cc.getCurrentCase());
				past.addAll(cc.getPastCases());
			}else{
				past.add((AtomicCase) c);
			}
		}
		return past;
	}
	
	
	public void pushCurrentCase(Input input, Action action){
		if (this.in != null && this.act != null){
			AtomicCase c = new AtomicCase(this.in, this.act);
			this.pastCases.add(0, c);
		}
		this.act = action;
		this.in = input;
	}
	

	@Override
	public String toString(){
		String s = "";
		s += "Input : " + this.in.toString() + " ";
		if (this.act != null){
			s += "Action : " + this.act.toString();
		}else{
			s += "Action : ?";
		}
		s += "\n";
		for (Case c : this.pastCases){
			s += c.toString() + "\n";
		}
		return s;
	}
}
