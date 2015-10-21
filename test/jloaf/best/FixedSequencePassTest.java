package jloaf.best;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.reasoning.BestRunReasoning;
import org.jLOAF.retrieve.kNNUtil;
import org.jLOAF.retrieve.sequence.weight.LinearWeightFunction;
import org.jLOAF.sim.SimilarityInputMetricStrategy;

public class FixedSequencePassTest extends RunSimilarityTest{

	private static String problemRunString[] = {
//		"0.1 5",
//		"0.2 6",
//		"0.3 7",
		"0.4 8",
		"0.1 1",
		"0.2 2",
		"0.3 3",
		"0.4 4",
	};
	
	private static String cbString[] = {
		"0.5 1",
		"0.5 2",
		"0.5 3",
		"0.5 4",
		"",
		"0.5 5",
		"0.5 6",
		"0.5 7",
		"0.5 8",
		"",
		"0.5 2",
		"0.5 3",
		"0.5 4",
		"0.5 5",
		"",
		"0.5 6",
		"0.5 7",
		"0.5 8",
		"0.5 1",
		"",
		"0.5 3",
		"0.5 4",
		"0.5 5",
		"0.5 6",
		"",
		"0.5 7",
		"0.5 8",
		"0.5 1",
		"0.5 2",
	};
	
	@Override
	public BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
		kNNUtil.setWeightFunction(new LinearWeightFunction(0.1));
		BacktrackingReasoning r = new BestRunReasoning(cb, 24, true);
		r.setCurrentRun(problemRun);
		return r;
	}
	
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
		return new SimilarityInputMetricStrategy(){
			@Override
			public double similarity(Input i1, Input i2) {
				if (!(i1 instanceof AtomicInput) || !(i2 instanceof AtomicInput)){
					throw new RuntimeException("Not correct inputs");
				}
				
				return 1.0;
			}
		};
	}
}
