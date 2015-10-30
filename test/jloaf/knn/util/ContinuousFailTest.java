//package jloaf.knn.util;
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
//import jloaf.BaselineTest;
//
//public class ContinuousFailTest extends BaselineTest{
//
//	public ContinuousFailTest(){
//		super(true);
//	}
//	
//	private static String problemRunString[] = {
//		"0.1 1",
//		"0.2 2",
//		"0.3 3",
//		"0.4 4",
//		"0.5 1",
//	};
//	
//	private static String cbString[] = {
//		"0.1 1",
//		"0.2 2",
//		"0.3 3",
//		"0.4 4",
//		"0.48 1",
//		"",
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"0.49 5",
//		"",
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"0.48 4",
//		"",
//		"0.0 1",
//		"0.0 2",
//		"0.0 3",
//		"0.0 4",
//		"0.48 3",
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
//		return new KNNBacktracking(cb, problemRun, 4, false, true);
//	}
//	
//}
