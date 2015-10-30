package org.jLOAF.casebase;

import org.jLOAF.action.Action;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;

public class AtomicCase extends Case{

	protected static SimilarityCaseMetricStrategy<AtomicCase> s_metric;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtomicCase(Input input, Action action) {
		super(input, action);
	}

	
	public static boolean isClassStrategySet(){
		if(AtomicCase.s_metric == null){
			return false;
		}else{
			return true;
		}
	}

	public static void setClassGlobalStrategy(SimilarityCaseMetricStrategy<AtomicCase> s){
		AtomicCase.s_metric = s;
	}


	@Override
	public double similarity(Case c) {
		//See if the user has defined similarity for each specific input, for all inputs
		//  of a specific type, of defered to superclass
		if(AtomicCase.isClassStrategySet()){
			return s_metric.similarity(this, c);
		}else{
			//normally we would defer to superclass, but super
			// is abstract
			System.err.println("Problem. In AtomicCase no similarity metric set!");
			return 0;
		}
	}
}
