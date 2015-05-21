package jloaf.tb;

import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.jLOAF.sim.SimilarityInputMetricStrategy;
import org.jLOAF.sim.atomic.ActionDistance;
import org.jLOAF.sim.atomic.InputDistance;
import jloaf.BaselineTest;

public class ReactiveContinuousTest extends BaselineTest {
	private static String problemRunString[] = {
		"0.1 1.01",
		"0.2 2.01",
	};
	
	private static String cbString[] = {
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
	public SimilarityInputMetricStrategy getAtomicInputSimMetric(){
		return new InputDistance();
	}
	
	@Override
	public SimilarityActionMetricStrategy getAtomicActionSimMetric() {
		return new ActionDistance();
	}

}
