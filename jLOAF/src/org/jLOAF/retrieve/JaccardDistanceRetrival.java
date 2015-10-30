package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

public class JaccardDistanceRetrival extends SimilarityComplexCaseMetricStrategy{

	private double threshold;
	
	public JaccardDistanceRetrival(double threshold){
		this.threshold = threshold;
	}
	
	@Override
	public double complexCaseSimilarity(ComplexCase c1, ComplexCase c2) {
		
		List<Case> problemRun = new ArrayList<Case>();
		problemRun.add(c1.getCurrentCase());
		problemRun.addAll(c1.getPastCases());
		
		List<Case> candidateRun = new ArrayList<Case>();
		candidateRun.add(c2.getCurrentCase());
		candidateRun.addAll(c2.getPastCases());
		
		int union = 0;
		for (Case pCase : problemRun){
			for (Case cCase : candidateRun){
				if (pCase.similarity(cCase) > this.threshold){
					union++;
				}
			}
		}
		
		return union * 1.0 / ((problemRun.size() + candidateRun.size() - union ) * 1.0);
	}

}
