package jloaf.best;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.reasoning.BestRunReasoning;
import org.jLOAF.retrieve.kNNUtil;
import org.jLOAF.retrieve.sequence.weight.LinearWeightFunction;

import jloaf.BaselineTest;

public class RunSimilarityTest extends BaselineTest{
	
	public RunSimilarityTest(){
		super();
	}
	
	public RunSimilarityTest(boolean toFail){
		super(toFail);
	}
	
	@Override
	public BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
		kNNUtil.setWeightFunction(new LinearWeightFunction(0.1));
		BacktrackingReasoning r = new BestRunReasoning(cb, DEFAULT_K);
		r.setCurrentRun(problemRun);
		return r;
	}

}
