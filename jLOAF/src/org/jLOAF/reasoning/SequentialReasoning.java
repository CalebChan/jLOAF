package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequenceRetrieval;
import org.jLOAF.retrieve.kNN;
import org.jLOAF.retrieve.kNNRandom;
import org.jLOAF.util.JLOAFLogger;
import org.jLOAF.util.JLOAFLogger.Level;

public class SequentialReasoning extends BacktrackingReasoning  {

	private SequenceRetrieval retrival;
	
	public static final double DEFAULT_THREHSOLD = 0.5;
	public static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	
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
		super((useRandomKNN) ? new kNNRandom(k, cb): new kNN(k, cb));

		this.retrival = retrival;
		this.currentRun = currentRun;
		
		logger = JLOAFLogger.getInstance();
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
