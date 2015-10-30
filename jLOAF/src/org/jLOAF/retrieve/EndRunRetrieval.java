package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

public class EndRunRetrieval extends SimilarityComplexCaseMetricStrategy{

	@Override
	public double complexCaseSimilarity(ComplexCase c1, ComplexCase c2) {
		
		List<Case> a1 = new ArrayList<Case>();
		a1.add(c1.getCurrentCase());
		a1.addAll(c1.getPastCases());
		
		List<Case> a2 = new ArrayList<Case>();
		a2.add(c2.getCurrentCase());
		a2.addAll(c2.getPastCases());
		
		int min = Math.min(a1.size(), a2.size());
		
		return a1.get(min).getInput().similarity(a2.get(min).getInput());
	}

}
