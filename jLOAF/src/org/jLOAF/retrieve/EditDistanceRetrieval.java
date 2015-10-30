package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.sim.cases.SimilarityComplexCaseMetricStrategy;

public class EditDistanceRetrieval extends SimilarityComplexCaseMetricStrategy{

	public static final double DEFAUL_EDIT_THRESHOLD = 0.5;
	public static final double DEFAULT_WEIGHT = 1.0;
	
	private double threshold = DEFAUL_EDIT_THRESHOLD;
	
	private double subWeight;
	private double addWeight;
	private double delWeight;
	
	public EditDistanceRetrieval(double threshold){
		this(DEFAULT_WEIGHT, DEFAULT_WEIGHT, DEFAULT_WEIGHT, threshold);
	}
	
	public EditDistanceRetrieval(double subWeight, double addWeight, double delWeight, double threshold){
		this.subWeight = subWeight;
		this.addWeight = addWeight;
		this.delWeight = delWeight;
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
		
		int pLength = problemRun.size() + 1;
		int cLength = candidateRun.size() + 1;
		double d[][] = new double[pLength][cLength];
		for (int i = 0; i < pLength; i++){
			d[i][0] = i;
		}
		for (int i = 0; i < cLength; i++){
			d[0][i] = i;
		}
		
		for (int i = 0; i < problemRun.size(); i++){
			Case pCase = problemRun.get(i);
			for (int j = 0; j < candidateRun.size(); j++){
				Case cCase = candidateRun.get(j);
				if (i == 0 || j == 0){
					double sim = pCase.getInput().similarity(cCase.getInput());
					if (sim > threshold){
						d[i + 1][j + 1] = d[i][j];
					}else{
						double min = Math.min(d[i][j] + subWeight, d[i][j + 1] + delWeight);
						min = Math.min(d[i + 1][j] + addWeight, min);
						d[i + 1][j + 1] = min;
					}
				}else{
					double sim = pCase.similarity(cCase);
					if (sim > threshold){
						d[i + 1][j + 1] = d[i][j];
					}else{
						double min = Math.min(d[i][j] + subWeight, d[i][j + 1] + delWeight);
						min = Math.min(d[i + 1][j] + addWeight, min);
						d[i + 1][j + 1] = min;
					}
				}
			}
		}

		return 1.0 / d[pLength - 1][cLength - 1];
	}
	
}
