package org.jLOAF.sim.atomic;

import org.jLOAF.action.Action;
import org.jLOAF.action.AtomicAction;
import org.jLOAF.sim.SimilarityActionMetricStrategy;

public class ActionEquality implements SimilarityActionMetricStrategy{

	@Override
	public double similarity(Action a1, Action a2) {
		
		if (!(a1 instanceof AtomicAction) || !(a2 instanceof AtomicAction)){
			throw new IllegalArgumentException("Equality.similarity(...): One of the arguments was not an AtomicInput.");
		}
		
		double val1 = ((AtomicAction)a1).getFeature().getValue();
		double val2 = ((AtomicAction)a2).getFeature().getValue();
		
		if(val1 == val2){
			return 1.0;
		}else{
			return 0.0;
		}
	}

}
