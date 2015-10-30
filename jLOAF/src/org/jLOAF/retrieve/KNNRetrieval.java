package org.jLOAF.retrieve;

import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

@Deprecated
public class KNNRetrieval extends SimilarityComplexCaseMetricStrategy{
	
	public static final double DEFAULT_THRESHOLD = -0.25;

	
	@SuppressWarnings("unused")
	private double threshold;
	
	public KNNRetrieval(double threshold){
		this.threshold = threshold;
	}

	@Override
	public double complexCaseSimilarity(ComplexCase c1, ComplexCase c2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
