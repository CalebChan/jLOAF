package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.kNN;

public abstract class BacktrackingReasoning  implements Reasoning{
	
	protected CaseRun currentRun;
	protected kNN knn;
	
	public BacktrackingReasoning(kNN knn){
		this.knn = knn;
	}
	
	public void setCurrentRun(CaseRun currentRun){
		this.currentRun = currentRun;
	}
	
	public ArrayList<CaseRun> generateCandidateRuns(Input i){
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
		return candidates;
	}
}
