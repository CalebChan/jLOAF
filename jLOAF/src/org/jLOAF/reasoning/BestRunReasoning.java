package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.retrieve.kNNRandom;
import org.jLOAF.retrieve.kNNUtil;

public class BestRunReasoning extends BacktrackingReasoning{

	private kNN knn;
	
	private kNNUtil util;
	
	public BestRunReasoning(CaseBase cb, int k){
		this.knn = new kNN(k, cb);
		
		util = new kNNUtil();
	}
	
	public BestRunReasoning(CaseBase cb, int k, boolean randomK){
		if(randomK){
			this.knn = new kNNRandom(k, cb);
		}else{
			this.knn = new kNN(k, cb);
		}
		
		util = new kNNUtil();
	}
	
	@Override
	public Action selectAction(Input i) {
		if (this.currentRun == null){
			throw new RuntimeException("Current Run is not set");
		}
		
		ArrayList<CaseRun> candidates = new ArrayList<CaseRun>();
		List<Case> closestCase = knn.retrieve(i);
		for (Case c : closestCase){
			Case tmp = c;
			CaseRun run  = new CaseRun("" + (candidates.size() + 1));
			while(tmp != null){
				run.appendCaseToRun(tmp);
				tmp = tmp.getPreviousCase();
			}
			run.reverseRun();
			candidates.add(run);
		}
		List<Action> actions = new ArrayList<Action>();
		for (CaseRun r : candidates){
			Action curAction = r.getCurrentCase().getAction();
			if (!actions.contains(curAction)){
				actions.add(curAction);
			}
		}
		
		/*if (actions.size() == 1){
			System.out.println("Consensus at : " + this.currentRun.getRunLength());
			return actions.get(0);
		}*/
		Action a = getBestRun(currentRun, candidates);
		return a;
	}
	
	private Action getBestRun(CaseRun currentRun, List<CaseRun> candidates){
		
		int time = currentRun.getRunLength() - 1;
		
		List<CaseRun> finalRun = util.kNNCaseRun(candidates, currentRun, true, time, 1);
		if (finalRun.size() != 1){
			throw new ArithmeticException("Final Size is not 1 : " + finalRun.size());
		}
		return finalRun.get(0).getCurrentCase().getAction();
	}


}
