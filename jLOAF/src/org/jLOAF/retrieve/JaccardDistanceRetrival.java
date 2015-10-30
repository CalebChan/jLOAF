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
		
		List<Case> smallRun = (problemRun.size() > candidateRun.size()) ? candidateRun : problemRun;
		List<Case> largeRun = (problemRun.size() <= candidateRun.size()) ? candidateRun : problemRun;
		
		ArrayList<Case> unionCase = new ArrayList<Case>();
		for (Case pCase : smallRun){
			for (Case cCase : largeRun){
				if (pCase.similarity(cCase) > this.threshold){
					if (!unionCase.contains(pCase)){
						unionCase.add(pCase);
					}
				}
			}
		}
		double value = unionCase.size() * 1.0 / ((problemRun.size() + candidateRun.size() - unionCase.size()) * 1.0);
		if (value > 1.0){
			throw new RuntimeException("Value over 1.0 : " + value);
		}
		return value;
	}

}
