package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;

public abstract class BacktrackingReasoning  implements Reasoning{
	
	protected Case currentRun;
	private CaseBase cb;
	private double threshold;
	
	protected SimilarityCaseMetricStrategy<ComplexCase> strategy;
	
	public BacktrackingReasoning(CaseBase cb, double threshold){
		this.cb = cb;
		this.threshold = threshold;
		this.strategy = null;
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
	
	public Action getBestRun(List<ComplexCase> candidateRuns, Case problemRun){
		if (this.strategy == null){
			throw new RuntimeException("Missing strategy for retrival");
		}
		ComplexCase.setClassGlobalStrategy(strategy);
		ComplexCase c = null;
		double best = -1;
		for (ComplexCase cc : candidateRuns){
			double sim = problemRun.similarity(cc);
			if (sim > best){
				best = sim;
				c = cc;
			}
		}
		return c.getAction();
	}
}
