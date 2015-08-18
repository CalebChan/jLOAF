package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.kNN;

public class EditDistanceReasoning extends BacktrackingReasoning{

	public EditDistanceReasoning(kNN knn) {
		super(knn);
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
		return null;
	}

}
