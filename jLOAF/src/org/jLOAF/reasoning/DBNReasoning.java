package org.jLOAF.reasoning;

import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.DBNRetrieval;

public class DBNReasoning implements Reasoning{

	private DBNRetrieval dbn;
	
	public DBNReasoning(CaseBase cb, int iterations, CaseRun run, int internalState){
		//dbn = new DBNRetrieval(cb, iterations, run, internalState);
	}
	
	public void setCaseRun(CaseRun run){
		//dbn.setCaseRun(run);
	}
	
	@Override
	public Action selectAction(Input i) {
		List<Case> c = dbn.retrieve(i);
		if (c.size() == 0){
			return null;
		}else if (c.size() == 1){
			return c.get(0).getAction();
		}
		return c.get(0).getAction(); //TODO: Fix for more the one case
	}

}
