package jloaf.tb;

import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.jLOAF.sim.SimilarityInputMetricStrategy;
import org.jLOAF.sim.atomic.ActionDistance;
import org.jLOAF.sim.atomic.InputDistance;

import jloaf.BaselineTest;

public class NonReactiveContinousVaryingRunSizeTest extends BaselineTest{
	
	private static String problemRunString[] = {
		"0.1 1",
		"0.2 3",
	};
	
	private static String cbString[] = {
		"0.0 0",
		"0.1 1",
		"0.25 7",
		"",
		"0.0 0",
		"0.1 1",
		"0.21 3",
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
	public SimilarityInputMetricStrategy getAtomicInputSimMetric(){
		return new InputDistance();
	}
	
	@Override
	public SimilarityActionMetricStrategy getAtomicActionSimMetric() {
		return new ActionDistance();
	}
}
