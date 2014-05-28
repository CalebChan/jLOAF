package org.jLOAF.reasoning;

import java.util.ArrayList;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.SequentialRetrival;

public class SequentialReasoning implements Reasoning  {

	private SequentialRetrival retrival;
	
	private static final double DEFAULT_THREHSOLD = 0.5;
	private static final double DEFAULT_CANDIDATE_THREHSOLD = 0.75;
	private CaseBase cb;
	private CaseRun currentRun;
	
	public SequentialReasoning(CaseBase cb, CaseRun currentRun){
		retrival = new SequentialRetrival(DEFAULT_THREHSOLD, DEFAULT_THREHSOLD);
		this.cb = cb;
		this.currentRun = currentRun;
	}
	
	@Override
	public Action selectAction(Input i) {
		CaseRun curRun = currentRun;
		
		ArrayList<CaseRun> candidates = new ArrayList<CaseRun>();
		ArrayList<Case> pastCases = (ArrayList<Case>) cb.getCases();
		
		int index = 0;
		for (Case c : pastCases){
			//System.out.println("Case : " + c.toString());
			double sim = c.getInput().similarity(curRun.getCurrentCase().getInput());
			//System.out.println("Sim : " + sim);
			if (sim >= DEFAULT_CANDIDATE_THREHSOLD){
				CaseRun run = new CaseRun();
				for (int j = 0; j <= index; j++){
					run.addCaseToRun(pastCases.get(j));
				}
				candidates.add(run);
			}
			
			index++;
		}
		//System.out.println("Candidate Length : " + candidates.size());
		return retrival.stateRetrival(curRun, candidates, 0);
	}

}
