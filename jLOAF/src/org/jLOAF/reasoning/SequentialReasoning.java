package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequentialRetrival;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.util.CaseLogger;

public class SequentialReasoning implements Reasoning  {

	private SequentialRetrival retrival;
	
	private static final double DEFAULT_THREHSOLD = 0.5;
	private static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	private CaseRun currentRun;
	
	private kNN knn;
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k){
		retrival = new SequentialRetrival(DEFAULT_THREHSOLD, DEFAULT_SOLUTION_THRESHOLD);
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
			CaseLogger.log(Level.INFO, "Run Length : " + run.getRunLength());
			candidates.add(run);
		}
		CaseLogger.log(Level.INFO, "Candidate Length : " + candidates.size() + "\n");
		return retrival.stateRetrival(currentRun, candidates, 0);
	}

}
