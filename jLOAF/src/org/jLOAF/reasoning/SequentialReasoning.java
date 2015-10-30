package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequenceRetrieval;

@Deprecated
public class SequentialReasoning extends BacktrackingReasoning  {

	
	public static final double DEFAULT_THREHSOLD = 0.5;
	public static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	
	public static final String SEQUENCE_RESONING_INFO_TAG = "SR_TAG";
	
	public SequentialReasoning(CaseBase cb, double threshold, Case currentRun, SequenceRetrieval retrival){
		super(cb, threshold);
		this.setCurrentRun(currentRun);
	}
	
	@Override
	public Action selectAction(Input i) {
		ArrayList<ComplexCase> candidates = generateCandidateRuns(i);
		
		List<Action> actions = new ArrayList<Action>();
		for (ComplexCase r : candidates){
			Action curAction = r.getCurrentCase().getAction();
			if (!actions.contains(curAction)){
				actions.add(curAction);
			}
		}
		if (actions.size() == 1){
			return actions.get(0);
		}
//		Action a = retrival.stateRetrival(currentRun, candidates, 0);
		
		return null;
	}

}
