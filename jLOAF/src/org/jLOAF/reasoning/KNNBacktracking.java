package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.KNNRetrieval;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.retrieve.kNNRandom;

public class KNNBacktracking extends BacktrackingReasoning {

	private KNNRetrieval retrival;
	
	private boolean shuffleCandidates;
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k) {
		this(cb, currentRun, k, false, false);
	}
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k, boolean randomK, boolean shuffleCandidates){
		super((randomK) ? new kNNRandom(k, cb): new kNN(k, cb));
		this.currentRun = currentRun;
		
		this.retrival = new KNNRetrieval();
		this.shuffleCandidates = shuffleCandidates;
	}
	
	@Override
	public Action selectAction(Input i) {

		
		ArrayList<CaseRun> candidates = generateCandidateRuns(i);

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
