package org.jLOAF.sim.cases;

import org.jLOAF.casebase.AtomicCase;
import org.jLOAF.casebase.Case;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;

public class SimilarityAtomicCaseMetricStrategy implements  SimilarityCaseMetricStrategy<AtomicCase> {

	@Override
	public double similarity(AtomicCase c1, Case c2) {
		double simInput = c1.getInput().similarity(c2.getInput());
		if (c1.getAction() != null && c2.getAction() != null){
			double simAction = c1.getAction().similarity(c2.getAction());
			return (simInput + simAction) / 2.0;
		}
		return simInput;
	}
	
}
