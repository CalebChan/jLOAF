package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jLOAF.casebase.CaseRun;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;
import org.jLOAF.util.JLOAFLogger;
import org.jLOAF.util.JLOAFLogger.Level;

public class kNNUtil {
	
	private static WeightFunction function;
	
	private JLOAFLogger log = JLOAFLogger.getInstance();
	
	public static void setWeightFunction(WeightFunction function){
		kNNUtil.function = function;
	}

	public List<CaseRun> kNNCaseRun(List<CaseRun> pool, CaseRun target, boolean isState, int time, int k){
		return kNNCaseRun(pool, target, isState, time, k, -1);
	}
	
	public List<CaseRun> kNNCaseRun(List<CaseRun> pool, CaseRun target, boolean isState, int time, int k, double threshold){
		log.logMessage(Level.DEBUG, getClass(), "C", "ALL");
		if (k >= pool.size()){
			k = pool.size() - 1;
		}
		if (k < 1){
			k = 1;
		}
		ArrayList<SimilarityPair> pair = new ArrayList<SimilarityPair>();
		for (CaseRun r : pool){
			log.logMessage(Level.DEBUG, getClass(), "C", "RUN", r.getCurrentCase().getAction());
			double sim = -1;
			if (time < r.getRunLength()){
				sim = calculateSimilarity(r, target, isState, time);
			}else{
				sim = 0;
				for (int i = 0, count = 1; i <= time; i++, count+=2){
					log.logMessage(Level.DEBUG, getClass(), "S" + (i), "" + 0);
					log.logMessage(Level.DEBUG, getClass(), "T" + (i), "" + (-count));
				}
			}
			pair.add(new SimilarityPair(sim, r));
		}
		log.logMessage(Level.DEBUG, getClass(), "C", "DONE");
		Collections.sort(pair);
		
		ArrayList<CaseRun> newRuns = new ArrayList<CaseRun>();
		newRuns.add(pair.get(pair.size() - 1).getCaseRun());
		for (int i = 1; newRuns.size() < k && i < pair.size(); i++){
			if (pair.get(pair.size() - 1 - i).getSimilarity() > threshold){
				newRuns.add(pair.get(pair.size() - 1 - i).getCaseRun());
			}
		}
		return newRuns;
	}
	
	private double calculateSimilarity(CaseRun run, CaseRun target, boolean isState, int time){
		
		double sim = 0;
		time = Math.min(time, target.getRunLength() - 1);
		time = Math.min(time, run.getRunLength() - 1);
		double tmpVal;
		for (int i = 0, count = 1; i <= time; i++, count++){
			if (i != 0){
				if (!isState && i == time){
					tmpVal = run.getCasePastOffset(i).getAction().similarity(target.getCasePastOffset(i).getAction()) * function.getWeightValue(i);
					log.logMessage(Level.DEBUG, getClass(), "S" + (i - 0.5), "" + tmpVal);
					sim += tmpVal;
					log.logMessage(Level.DEBUG, getClass(), "T" + (i - 0.5), "" + (sim - count));
				}else{
					tmpVal = run.getCasePastOffset(i).getAction().similarity(target.getCasePastOffset(i).getAction()) * function.getWeightValue(i);
					log.logMessage(Level.DEBUG, getClass(), "S" + (i - 0.5), "" + tmpVal);
					sim += tmpVal;
					log.logMessage(Level.DEBUG, getClass(), "T" + (i - 0.5), "" + (sim - count));
					count++;
					tmpVal = run.getCasePastOffset(i).getInput().similarity(target.getCasePastOffset(i).getInput()) * function.getWeightValue(i);
					log.logMessage(Level.DEBUG, getClass(), "S" + (i), "" + tmpVal);
					sim += tmpVal;
					log.logMessage(Level.DEBUG, getClass(), "T" + (i), "" + (sim - count));
				}
			}else{
				tmpVal = run.getCasePastOffset(i).getInput().similarity(target.getCasePastOffset(i).getInput()) * function.getWeightValue(i);;
				log.logMessage(Level.DEBUG, getClass(), "S" + (i), "" + tmpVal);
				sim += tmpVal;
				log.logMessage(Level.DEBUG, getClass(), "T" + (i), "" + (sim - count));
			}

		}
		return sim / (2.0 * (target.getRunLength()));
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
