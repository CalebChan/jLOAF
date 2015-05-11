package org.jLOAF.reasoning;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.kNN;

public class KNNBacktracking implements Reasoning {

	private kNN knn;
	
	private CaseRun currentRun;
	
	public KNNBacktracking(CaseBase cb, CaseRun currentRun, int k) {
		this.knn = new kNN(k, cb);
		this.currentRun = currentRun;
	}
	
	@Override
	public Action selectAction(Input i) {
		return null;
	}

}
