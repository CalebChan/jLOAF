package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.AtomicCase;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityCaseMetricStrategy;
import org.jLOAF.sim.cases.SimilarityAtomicCaseMetricStrategy;

public abstract class BacktrackingReasoning  implements Reasoning{
	
	private static final double DEFAULT_THRESHOLD_FALLBACK = 0.75;
	
	protected Case currentRun;
	private CaseBase cb;
	private double threshold;
	
	protected SimilarityCaseMetricStrategy<ComplexCase> strategy;
	
	public BacktrackingReasoning(CaseBase cb, double threshold){
		this.cb = cb;
		this.threshold = threshold;
		this.strategy = null;
		AtomicCase.setClassGlobalStrategy(new SimilarityAtomicCaseMetricStrategy());
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
		if (candidates.isEmpty()){
			for (ComplexCase c : this.cb.getRuns()){
				candidates.addAll(c.getSubRuns(i, DEFAULT_THRESHOLD_FALLBACK));
			}
		}
//		System.out.println("Candidate cases : " + candidates.size());
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
		Action a = c.getAction();
		return a;
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
