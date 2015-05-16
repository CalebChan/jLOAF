package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequenceRetrieval;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.retrieve.kNNRandom;
import org.jLOAF.util.JLOAFLogger;
import org.jLOAF.util.JLOAFLogger.Level;

public class SequentialReasoning implements Reasoning  {

	private SequenceRetrieval retrival;
	
	public static final double DEFAULT_THREHSOLD = 0.5;
	public static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	private CaseRun currentRun;
	
	private kNN knn;
	
	private JLOAFLogger logger;
	public static final String SEQUENCE_RESONING_INFO_TAG = "SR_TAG";
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k){
		this(cb, currentRun, k, false);
	}
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k, boolean useRandomKNN){
		this(cb, currentRun, k, useRandomKNN, DEFAULT_THREHSOLD, DEFAULT_SOLUTION_THRESHOLD);
	}
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k, boolean useRandomKNN, double problemThreshold, double solutionThreshold){		
		this(cb, currentRun, k, useRandomKNN, new SequenceRetrieval(problemThreshold, solutionThreshold));
	}
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun, int k, boolean useRandomKNN, SequenceRetrieval retrival){
		this.retrival = retrival;
		this.currentRun = currentRun;
		if (!useRandomKNN){
			this.knn = new kNN(k, cb);
		}else{
			this.knn = new kNNRandom(k, cb);
		}
		logger = JLOAFLogger.getInstance();
	}
	
	public void setCurrentRun(CaseRun currentRun){
		this.currentRun = currentRun;
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
		logger.logMessage(Level.DEBUG, getClass(), SEQUENCE_RESONING_INFO_TAG, "Candidates:" + candidates.size());
		if (actions.size() == 1){
			//System.out.println("Consensus at : " + this.currentRun.getRunLength());
			return actions.get(0);
		}
		//Case tmpCase = new Case(i, null, currentRun.getCurrentCase());
		//currentRun.addCaseToRun(tmpCase);
		logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Start Retrieval");
		Action a = retrival.stateRetrival(currentRun, candidates, 0);
		
		//currentRun.removeCurrentCase(0);
		logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "End Retrieval", a.toString());
		logger.logMessage(Level.DEBUG, getClass(), SEQUENCE_RESONING_INFO_TAG, "Action:" + a.toString());
		return a;
	}

}
