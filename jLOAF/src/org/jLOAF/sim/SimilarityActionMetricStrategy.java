package org.jLOAF.sim;

import org.jLOAF.action.Action;

public interface SimilarityActionMetricStrategy {
	public double similarity(Action a1, Action a2);
}
