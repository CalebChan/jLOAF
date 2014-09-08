package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;
import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequentialRetrieval;
import org.jLOAF.retrieve.kNN;

public class SequentialReasoning implements Reasoning  {

	private SequentialRetrieval retrival;
	
	private static final double DEFAULT_THREHSOLD = 0.5;
	private static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	private CaseRun currentRun;
	
	private kNN knn;
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k){
		retrival = new SequentialRetrieval(DEFAULT_THREHSOLD, DEFAULT_SOLUTION_THRESHOLD);
		this.currentRun = currentRun;
		
		this.knn = new kNN(k, cb);
	}
	
	@Override
	public Action selectAction(Input i) {
		ArrayList<CaseRun> candidates = new ArrayList<CaseRun>();
		List<Case> closestCase = knn.retrieve(i);
		for (Case c : closestCase){
			Case tmp = c;
			CaseRun run  = new CaseRun();
			while(tmp != null){
				run.addCaseToRun(tmp);
				tmp = tmp.getPreviousCase();
			}
			run.reverseRun();
			candidates.add(run);
		}
		List<Action> actions = new ArrayList<Action>();
		for (CaseRun r : candidates){
			Action curAction = r.getCase(r.getRunLength() - 1).getAction();
			if (!actions.contains(curAction)){
				actions.add(curAction);
			}
		}
		if (actions.size() == 1){
			//System.out.println("Consensus at : " + this.currentRun.getRunLength());
			return actions.get(0);
		}
		return retrival.stateRetrival(currentRun, candidates, 0);
	}

}
