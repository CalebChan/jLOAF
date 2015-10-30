package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.kNN;

public class EditDistanceReasoning extends BacktrackingReasoning{

	public EditDistanceReasoning(CaseBase cb, double threshold) {
		super(cb, threshold);
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
		return null;
	}

}
