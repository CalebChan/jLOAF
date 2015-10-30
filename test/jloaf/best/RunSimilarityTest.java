package jloaf.best;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.reasoning.BestRunReasoning;
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
	public BacktrackingReasoning buildReasoning(CaseBase cb, ComplexCase problemRun){
		//kNNUtil.setWeightFunction(new LinearWeightFunction(0.1));
		BacktrackingReasoning r = new BestRunReasoning(cb, DEFAULT_THRESHOLD, new LinearWeightFunction(0.1));
		r.setCurrentRun(problemRun);
		return r;
	}

}
