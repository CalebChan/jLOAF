package org.jLOAF.sim.complex;

import java.util.Set;

import org.jLOAF.action.Action;
import org.jLOAF.action.ComplexAction;
import org.jLOAF.sim.SimilarityActionMetricStrategy;

public class ActionMean implements SimilarityActionMetricStrategy {

	@Override
	public double similarity(Action a1, Action a2) {
		if(!(a1 instanceof ComplexAction) || !(a2 instanceof ComplexAction) ){
			throw new IllegalArgumentException("Mean.similarity(..): Not ComplexInputs");
		}
		
		ComplexAction cplx1 = (ComplexAction)a1;
		ComplexAction cplx2 = (ComplexAction)a2;
		
		Set<String> keys = cplx1.getChildNames();
		Set<String> keys2 = cplx2.getChildNames();
		
		if(!keys.equals(keys2)){
			System.out.println("Mean.similarity(...):Likely a problem since not same features.");
		}
		
		double total = 0;
		
		for(String s: keys){
			total += cplx1.get(s).similarity(cplx2.get(s));
		}
		
		return total/keys.size();
	}

}
