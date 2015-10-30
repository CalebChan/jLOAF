//package jloaf.knn.util;
//
//import jloaf.BaselineTest;
//
//import org.jLOAF.casebase.CaseBase;
//import org.jLOAF.casebase.CaseRun;
//import org.jLOAF.reasoning.BacktrackingReasoning;
//import org.jLOAF.reasoning.KNNBacktracking;
//import org.jLOAF.retrieve.kNNUtil;
//import org.jLOAF.retrieve.sequence.weight.LinearWeightFunction;
//import org.jLOAF.sim.SimilarityActionMetricStrategy;
//import org.jLOAF.sim.SimilarityInputMetricStrategy;
//import org.jLOAF.sim.atomic.ActionDistance;
//import org.jLOAF.sim.atomic.InputDistance;
//
//public class ContinuousPassTest extends BaselineTest{
//	private static String problemRunString[] = {
//		"1 1",
//		"2 2",
//		"3 3",
//		"4 4",
//		"5 1",
//	};
//	
//	
//	// Note order of candidate runs matters
//	private static String cbString[] = {
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"4.9 5",
//		"",
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"4.1 4",
//		"",
//		"1 1",
//		"2 2",
//		"3 3",
//		"4 4",
//		"4.1 1",
//		"",
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"4.1 3",
//	};
//	
//	@Override
//	public String[] getProblemString() {
//		return problemRunString;
//	}
//
//	@Override
//	public String[] getCaseBaseString() {
//		return cbString;
//	}
//	
//	@Override
//	public SimilarityInputMetricStrategy getAtomicInputSimMetric(){
//		return new InputDistance();
//	}
//	
//	@Override
//	public SimilarityActionMetricStrategy getAtomicActionSimMetric() {
//		return new ActionDistance();
//	}
//	
//	
//	@Override
//	public BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
//		kNNUtil.setWeightFunction(new LinearWeightFunction(0.2));
//		return new KNNBacktracking(cb, problemRun, 4);
//	}
//}
