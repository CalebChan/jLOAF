package jloaf.tb;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.reasoning.SequentialReasoning;
import org.jLOAF.retrieve.SequenceRetrieval;
import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.jLOAF.sim.SimilarityInputMetricStrategy;
import org.jLOAF.sim.atomic.ActionDistance;
import org.jLOAF.sim.atomic.InputDistance;

import jloaf.BaselineTest;

public class ReactiveContinuousVariableKFail extends BaselineTest{

	public ReactiveContinuousVariableKFail(){
		// kNN orders the runs by most similiar to least, thus this code does not work right now.
		//super(true);
	}
	
	private static String problemRunString[] = {
		"0.1 1.01",
		"0.2 2.01",
	};
	
	private static String cbString[] = {
		"0.1 1.01",
		"0.201 2",
		"",
		"0.1 1.01",
		"0.2 2.01",
		"",
		"0.1 1.01",
		"0.2 2.01",		
	};
	
	@Override
	public String[] getProblemString() {
		return problemRunString;
	}

	@Override
	public String[] getCaseBaseString() {
		return cbString;
	}
	@Override
	public BacktrackingReasoning buildReasoning(CaseBase cb, ComplexCase problemRun){
		return new SequentialReasoning(cb, DEFAULT_THRESHOLD, problemRun, new SequenceRetrieval());
	}
	
	@Override
	public SimilarityInputMetricStrategy getAtomicInputSimMetric(){
		return new InputDistance();
	}
	
	@Override
	public SimilarityActionMetricStrategy getAtomicActionSimMetric() {
		return new ActionDistance();
	}
}
