package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;

public class KNNRetrieval{
	
	private int k;
	
	private kNNUtil util;
	 
	public KNNRetrieval(int k){
		this.k = k;
		util = new kNNUtil();
	}

	private CaseRun getBestRun(List<CaseRun> candidateRun, CaseRun run, int time, boolean isState){
		CaseRun best = null;
		double bestSim = -1;
		for (CaseRun r : candidateRun){
			if (time >= r.getRunLength() || time >= run.getRunLength()){
				continue;
			}
			double s = -1;
			if (isState){
				s = r.getCasePastOffset(time).getInput().similarity(run.getCasePastOffset(time).getInput());
			}else{
				s = r.getCasePastOffset(time).getAction().similarity(run.getCasePastOffset(time).getAction());
			}
			if (s > bestSim){
				bestSim = s;
				best = r;
			}
		}
		
		return best;
	}
	
	public Action retrieve(CaseRun run, List<CaseRun> pastRuns, int time){
		
		CaseRun bestRun = getBestRun(pastRuns, run, time, true);
		List<CaseRun> candidateRuns = pastRuns;
		for (int i = 0; i < run.getRunLength(); i++){
			candidateRuns = util.kNNCaseRun(candidateRuns, run, true, time, k);
			if (hasConsensus(candidateRuns)){
				return candidateRuns.get(0).getCurrentCase().getAction();
			}
			time++;
			candidateRuns = util.kNNCaseRun(candidateRuns, run, false, time, k);
			if (hasConsensus(candidateRuns)){
				return candidateRuns.get(0).getCurrentCase().getAction();
			}
		}
		
		return bestRun.getCurrentCase().getAction();
	}
	
	private boolean hasConsensus(List<CaseRun> runs){
		List<Action> actions = new ArrayList<Action>();
		for (CaseRun r : runs){
			if (!actions.contains(r.getCurrentCase().getAction())){
				actions.add(r.getCurrentCase().getAction());
			}
		}
		return actions.size() == 1;
	}
}
