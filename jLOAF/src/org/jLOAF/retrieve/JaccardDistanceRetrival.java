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
		
		for (Case cc1 : c1.getPastCases()){
			boolean isIn = false;
			for (Case p : problemRun){
				if (p.similarity(cc1) > threshold){
					isIn = true;
					break;
				}
			}
			if (!isIn){
				problemRun.add(cc1);
			}
		}
		
		//problemRun.addAll(c1.getPastCases());
		
		List<Case> candidateRun = new ArrayList<Case>();
		candidateRun.add(c2.getCurrentCase());
		
		for (Case cc1 : c2.getPastCases()){
			boolean isIn = false;
			for (Case c : candidateRun){
				if (c.similarity(cc1) > threshold){
					isIn = true;
					break;
				}
			}
			if (!isIn){
				candidateRun.add(cc1);
			}
		}
		//candidateRun.addAll(c2.getPastCases());
		
		int min = Math.min(problemRun.size(), candidateRun.size());
		ArrayList<Case> intersectCase = new ArrayList<Case>();
		for (Case cCase : candidateRun){
			for (Case pCase : problemRun){
				double sim = pCase.similarity(cCase);
				if (sim > this.threshold){
					boolean isIn = false;
					for (Case iCase : intersectCase){
						double sim2 = iCase.similarity(pCase);
						if (sim2 > threshold){
							isIn = true;
							break;
						}
					}
					if (!isIn){
						intersectCase.add(pCase);
					}
					if (intersectCase.size() > min){
						System.out.println("MOO");
					}
					break;
				}
			}
		}
			
		double value = intersectCase.size() * 1.0 / ((problemRun.size() + candidateRun.size() - intersectCase.size()) * 1.0);
		if (value > 1.0){
//			throw new RuntimeException("Value over 1.0 : " + value);
			System.out.println("ERROR");
		}
		return value;
	}

}
