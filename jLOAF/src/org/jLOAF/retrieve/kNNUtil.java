package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jLOAF.casebase.CaseRun;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;

public class kNNUtil {
	
	private static WeightFunction function;
	
	public static void setWeightFunction(WeightFunction function){
		kNNUtil.function = function;
	}

	public List<CaseRun> kNNCaseRun(List<CaseRun> pool, CaseRun target, boolean isState, int time, int k){
		if (k < 1){
			k = 1;
		}else if (k >= pool.size()){
			k  = pool.size() - 1;
		}
		ArrayList<SimilarityPair> pair = new ArrayList<SimilarityPair>();
		for (CaseRun r : pool){
			if (time < r.getRunLength()){
				double sim = calculateSimilarity(r, target, isState, time);
				pair.add(new SimilarityPair(sim, r));
			}
		}
		
		Collections.sort(pair);
		
		ArrayList<CaseRun> newRuns = new ArrayList<CaseRun>();
		for (int i = 0; i < k && i < pair.size(); i++){
			newRuns.add(pair.get(pair.size() - 1 - i).getCaseRun());
		}
		return newRuns;
	}
	
	private double calculateSimilarity(CaseRun run, CaseRun target, boolean isState, int time){
		double sim = 0;
		time = Math.min(time, target.getRunLength() - 1);
		time = Math.min(time, run.getRunLength() - 1);
		for (int i = time; i >= 0; i--){
			if (i != 0){
				if (!isState && i == time){
					sim += run.getCasePastOffset(i).getAction().similarity(target.getCasePastOffset(i).getAction()) * function.getWeightValue(i);
				}else{
					sim += run.getCasePastOffset(i).getInput().similarity(target.getCasePastOffset(i).getInput()) * function.getWeightValue(i);
					sim += run.getCasePastOffset(i).getAction().similarity(target.getCasePastOffset(i).getAction()) * function.getWeightValue(i);
				}
			}else{
				sim += run.getCasePastOffset(i).getInput().similarity(target.getCasePastOffset(i).getInput()) * function.getWeightValue(i);
			}

		}
		return sim / (1.0 * (time + 1));
	}

	
	class SimilarityPair implements Comparable<SimilarityPair>{
		private double similarity;
		private CaseRun run;
		public SimilarityPair(double similarity, CaseRun run) {
			this.similarity = similarity;
			this.run = run;
		}
		public double getSimilarity() {
			return similarity;
		}
		public CaseRun getCaseRun() {
			return run;
		}
		@Override
		public int compareTo(SimilarityPair o) {
			if (this.getSimilarity() < o.getSimilarity()){
				return -1;
			}else if (this.getSimilarity() > o.getSimilarity()){
				return 1;
			}else {
				return 0;
			}
		}
		@Override
		public String toString(){
			String s = "Sim : " + this.similarity + "\n";
			s += this.run.toString();
			return s;
		}
	}
}
