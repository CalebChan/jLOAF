package org.jLOAF.reasoning;

import java.util.ArrayList;
import org.jLOAF.Reasoning;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;

public abstract class BacktrackingReasoning  implements Reasoning{
	
	protected Case currentRun;
	private CaseBase cb;
	private double threshold;
	
	public BacktrackingReasoning(CaseBase cb, double threshold){
		this.cb = cb;
		this.threshold = threshold;
	}
	
	public void setCurrentRun(Case currentRun){
		this.currentRun = currentRun;
	}
	
	public ArrayList<ComplexCase> generateCandidateRuns(Input i){
		if (this.currentRun == null){
			throw new RuntimeException("Current Run is not set");
		}
		
		ArrayList<ComplexCase> candidates = new ArrayList<ComplexCase>();
		for (ComplexCase c : this.cb.getRuns()){
			candidates.addAll(c.getSubRuns(i, threshold));
		}
		return candidates;
	}
}
