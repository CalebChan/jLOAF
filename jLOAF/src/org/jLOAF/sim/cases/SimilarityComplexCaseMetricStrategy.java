package org.jLOAF.sim.cases;

import org.jLOAF.casebase.AtomicCase;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;

public abstract class SimilarityComplexCaseMetricStrategy implements  SimilarityCaseMetricStrategy<ComplexCase>{

	@Override
	public double similarity(ComplexCase c1, Case c2) {
		if (c2 instanceof AtomicCase){
			SimilarityAtomicCaseMetricStrategy sim = new SimilarityAtomicCaseMetricStrategy();
			return sim.similarity((AtomicCase) c2, c1);
		}
		return complexCaseSimilarity(c1, (ComplexCase) c2);
	}
	
	public abstract double complexCaseSimilarity(ComplexCase c1, ComplexCase c2);

}
