package org.jLOAF.reasoning;

import org.jLOAF.Reasoning;
import org.jLOAF.casebase.CaseRun;

public abstract class BacktrackingReasoning  implements Reasoning{
	
	protected CaseRun currentRun;
	
	public void setCurrentRun(CaseRun currentRun){
		this.currentRun = currentRun;
	}
}
