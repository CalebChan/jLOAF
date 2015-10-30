//package jloaf.knn.util;
//
//import org.jLOAF.casebase.CaseBase;
//import org.jLOAF.casebase.CaseRun;
//import org.jLOAF.reasoning.BacktrackingReasoning;
//import org.jLOAF.reasoning.KNNBacktracking;
//import org.jLOAF.retrieve.kNNUtil;
//import org.jLOAF.retrieve.sequence.weight.LinearWeightFunction;
//
//import jloaf.BaselineTest;
//
//public class kNNUtilTest extends BaselineTest {
//	
//	@Override
//	public BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
//		kNNUtil.setWeightFunction(new LinearWeightFunction(0.1));
//		return new KNNBacktracking(cb, problemRun, DEFAULT_K);
//	}
//}
