package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

public class BestRunRetrieval extends SimilarityComplexCaseMetricStrategy{

	public BestRunRetrieval(){
		super();
	}
	
	public BestRunRetrieval(WeightFunction function){
		super(function);
	}
	
	@Override
	public double complexCaseSimilarity(ComplexCase c1, ComplexCase c2) {
		
		int length = Math.min(c1.getComplexCaseSize(), c2.getComplexCaseSize());
		
		List<Case> a1 = new ArrayList<Case>();
		a1.add(c1.getCurrentCase());
		a1.addAll(c1.getPastCases());
		
		List<Case> a2 = new ArrayList<Case>();
		a2.add(c2.getCurrentCase());
		a2.addAll(c2.getPastCases());
		
		double totalSim = a1.get(0).getInput().similarity(a2.get(0).getInput()) * function.getWeightValue(0);
		
		for(int i = 1; i < length; i++){
			totalSim += a1.get(0).getAction().similarity(a2.get(0).getAction()) * function.getWeightValue(i);
			totalSim += a1.get(0).getInput().similarity(a2.get(0).getInput()) * function.getWeightValue(i);
		}
		
		return totalSim / (length * 2.0 - 1);
	}

}
