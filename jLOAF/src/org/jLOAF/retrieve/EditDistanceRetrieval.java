package org.jLOAF.retrieve;

import java.util.ArrayList;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseRun;

public class EditDistanceRetrieval {

	public static final double DEFAUL_EDIT_THRESHOLD = 0.5;
	public static final double DEFAULT_WEIGHT = 1.0;
	
	private double threshold = DEFAUL_EDIT_THRESHOLD;
	
	private double subWeight;
	private double addWeight;
	private double delWeight;
	
	public EditDistanceRetrieval(){
		this(DEFAULT_WEIGHT, DEFAULT_WEIGHT, DEFAULT_WEIGHT);
	}
	
	public EditDistanceRetrieval(double subWeight, double addWeight, double delWeight){
		this.subWeight = subWeight;
		this.addWeight = addWeight;
		this.delWeight = delWeight;
	}
	
	public Action retrieve(CaseRun problemRun, ArrayList<CaseRun> candidateRun, int time){
		CaseRun bestRun = null;
		double bestDistance = Double.MAX_VALUE;
		for (CaseRun c : candidateRun){
			double tmpDist = calculateEditDistance(problemRun, c);
			if (tmpDist < bestDistance){
				bestDistance = tmpDist;
				bestRun = c;
			}
		}
		return bestRun.getCurrentCase().getAction();
	}
	
	private double calculateEditDistance(CaseRun problemRun, CaseRun candidateRun){
		int pLength = problemRun.getRunLength() * 2;
		int cLength = candidateRun.getRunLength() * 2;
		double d[][] = new double[pLength][cLength];
		for (int i = 0; i < pLength; i++){
			d[i][0] = i;
		}
		for (int i = 0; i < cLength; i++){
			d[0][i] = i;
		}
		
		for (int i = 0; i < problemRun.getRunLength(); i++){
			Case pCase = problemRun.getCasePastOffset(i);
			for (int j = 0; j < candidateRun.getRunLength(); j++){
				Case cCase = candidateRun.getCasePastOffset(j);
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
