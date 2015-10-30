package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.BestRunRetrieval;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;

public class BestRunReasoning extends BacktrackingReasoning{

	public BestRunReasoning(CaseBase cb, double threshold){
		super(cb, threshold);
		this.strategy = new BestRunRetrieval();
	}
	
	public BestRunReasoning(CaseBase cb, double threshold, WeightFunction function){
		super(cb, threshold);
		this.strategy = new BestRunRetrieval(function);
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
		
		Action a = getBestRun(candidates, currentRun);
		return a;
	}

}
