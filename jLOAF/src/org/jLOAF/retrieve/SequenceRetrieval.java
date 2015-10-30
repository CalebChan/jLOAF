package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

public class SequenceRetrieval extends SimilarityComplexCaseMetricStrategy{

	private double problemThreshold;
	private double solutionThreshold;
	
	public SequenceRetrieval(double threshold) {
		this(threshold, threshold);
	}
	
	public SequenceRetrieval(double pThreshold, double sThreshold) {
		this.problemThreshold = pThreshold;
		this.solutionThreshold = sThreshold;
	}
	
	@Override
	public double complexCaseSimilarity(ComplexCase c1, ComplexCase c2) {
		
		List<Case> a1 = new ArrayList<Case>();
		a1.add(c1.getCurrentCase());
		a1.addAll(c1.getPastCases());
		
		List<Case> a2 = new ArrayList<Case>();
		a2.add(c2.getCurrentCase());
		a2.addAll(c2.getPastCases());
		
		int backtracking = 0;
		if (a1.get(0).getInput().similarity(a2.get(0).getInput()) > problemThreshold){
			backtracking++;
		}else{
			return 0;
		}
		
		for (int i = 1; i < a1.size(); i++){
			if (a1.get(i).getAction().similarity(a2.get(i).getAction()) > solutionThreshold){
				backtracking++;
			}else{
				return backtracking;
			}
			if (a1.get(i).getInput().similarity(a2.get(i).getInput()) > problemThreshold){
				backtracking++;
			}else{
				return backtracking;
			}
		}
		
		return backtracking;
	}
}
