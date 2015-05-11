package org.jLOAF.sim;

import org.jLOAF.inputs.Input;

public interface SimilarityInputMetricStrategy {

	public double similarity(Input i1, Input i2);
}
