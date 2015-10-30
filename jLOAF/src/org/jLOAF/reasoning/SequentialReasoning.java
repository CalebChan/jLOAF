package org.jLOAF.reasoning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.retrieve.EndRunRetrieval;
import org.jLOAF.retrieve.SequenceRetrieval;

public class SequentialReasoning extends BacktrackingReasoning  {

	public static final double DEFAULT_THREHSOLD = 0.5;
	public static final double DEFAULT_SOLUTION_THRESHOLD = 0.0;
	
	public static final String SEQUENCE_RESONING_INFO_TAG = "SR_TAG";
	
	public SequentialReasoning(CaseBase cb, double threshold, Case currentRun, SequenceRetrieval retrival){
		super(cb, threshold);
		this.setCurrentRun(currentRun);
		
		this.strategy = new SequenceRetrieval(DEFAULT_THREHSOLD, DEFAULT_SOLUTION_THRESHOLD);
		if (retrival != null){
			this.strategy = retrival;
		}
	}
	
	public SequentialReasoning(CaseBase cb, double threshold, Case currentRun){
		this(cb, threshold, currentRun, null);
	}
	
	public Action getBestRun(List<ComplexCase> candidateRuns, Case problemRun){
		if (this.strategy == null){
			throw new RuntimeException("Missing strategy for retrival");
		}
		ComplexCase.setClassGlobalStrategy(strategy);
		HashMap<Integer, ArrayList<ComplexCase>> simMap = new HashMap<Integer, ArrayList<ComplexCase>>();
		int bestSim = -1;
		for (ComplexCase cc : candidateRuns){
			int sim = (int) problemRun.similarity(cc);
			if (simMap.containsKey(sim)){
				simMap.put(sim, new ArrayList<ComplexCase>());
			}
			simMap.get(sim).add(cc);
			if (sim > bestSim){
				bestSim = sim;
			}
		}
		if (simMap.get(bestSim).size() == 1){
			return simMap.get(bestSim).get(0).getAction();
		}
		ComplexCase.setClassGlobalStrategy(new EndRunRetrieval());
		double best = -1;
		Case bestCase = null;
		for (ComplexCase cc : simMap.get(bestSim)){
			double s = problemRun.similarity(cc); 
			if (s > best){
				best = s;
				bestCase = cc;
			}
		}
		Action a = bestCase.getAction();
		return a;
	}
}
