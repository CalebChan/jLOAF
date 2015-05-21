package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.KNNRetrieval;
import org.jLOAF.retrieve.kNN;

public class KNNBacktracking extends BacktrackingReasoning {

	private kNN knn;
		
	private KNNRetrieval retrival;
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k) {
		this.knn = new kNN(k, cb);
		this.currentRun = currentRun;
		
		this.retrival = new KNNRetrieval();
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
			//System.out.println("Consensus at : " + this.currentRun.getRunLength());
			return actions.get(0);
		}
		Case tmpCase = new Case(i, null, currentRun.getCurrentCase());
		currentRun.addCaseToRun(tmpCase);
		Action a = retrival.retrieve(currentRun, candidates, 0);
		currentRun.removeCurrentCase(0);
		return a;
	}

}
