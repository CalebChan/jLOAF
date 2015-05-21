package org.jLOAF.sim.atomic;

import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityInputMetricStrategy;

public class InputDistance implements SimilarityInputMetricStrategy{
	@Override
	public double similarity(Input i1, Input i2) {
		if(!(i1 instanceof AtomicInput) || !(i2 instanceof AtomicInput)){
			throw new IllegalArgumentException("Equality.similarity(...): One of the arguments was not an AtomicInput.");
		}

		double val1 = ((AtomicInput)i1).getFeature().getValue();
		double val2 = ((AtomicInput)i2).getFeature().getValue();
		
		double norm = Math.sqrt(Math.pow(val1, 2) + Math.pow(val2, 2));
		
		return 1 - Math.abs(val1 - val2) / norm;
	}
}
