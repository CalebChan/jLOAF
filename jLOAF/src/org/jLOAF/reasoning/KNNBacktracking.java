package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.KNNRetrieval;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.retrieve.kNNRandom;

public class KNNBacktracking extends BacktrackingReasoning {

	private kNN knn;
		
	private KNNRetrieval retrival;
	
	private boolean shuffleCandidates;
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k) {
		this(cb, currentRun, k, false, false);
	}
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k, boolean randomK, boolean shuffleCandidates){
		if (randomK){
			this.knn = new kNNRandom(k, cb);
		}else {
			this.knn = new kNN(k, cb);
		}
		this.currentRun = currentRun;
		
		this.retrival = new KNNRetrieval();
		this.shuffleCandidates = shuffleCandidates;
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
		
		if (actions.size() == 1){
			return actions.get(0);
		}
		
		if (shuffleCandidates){
			Collections.shuffle(candidates);
		}
		Action a = retrival.retrieve(currentRun, candidates, 0);
		return a;
	}

}
