package org.jLOAF.sim;

import org.jLOAF.casebase.Case;

public interface SimilarityCaseMetricStrategy<C extends Case> {
	public double similarity(C c1, Case c2);
}
