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
		candidateRun.add(c1.getCurrentCase());
		candidateRun.addAll(c1.getPastCases());
		
		int pLength = problemRun.size() * 2;
		int cLength = candidateRun.size() * 2;
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
				if (i != 0 && j != 0){
					double sim = pCase.getAction().similarity(cCase.getAction());
					if (sim > threshold){
						d[i * 2][j * 2] = d[i * 2 - 1][j * 2 - 1];
					}else{
						double min = Math.min(d[i * 2 - 1][j * 2 - 1] + subWeight, d[i * 2 - 1][j * 2] + delWeight);
						min = Math.min(d[i * 2][j * 2 - 1] + addWeight, min);
						d[i * 2][j * 2] = min;
					}
				}else{
					double sim = pCase.getInput().similarity(cCase.getInput());
					if (sim > threshold){
						if (sim > threshold){
							d[i * 2 + 1][j * 2 + 1] = d[i * 2][j * 2];
						}else{
							double min = Math.min(d[i * 2][j * 2] + subWeight, d[i * 2][j * 2 + 1] + delWeight);
							min = Math.min(d[i * 2 + 1][j * 2] + addWeight, min);
							d[i * 2 + 1][j * 2 + 1] = min;
						}
					}
				}
			}
		}
		
		return d[pLength][cLength];
	}
	
}
