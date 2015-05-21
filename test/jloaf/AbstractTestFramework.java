package jloaf;
import jloaf.util.AbstractCaseParser;
import jloaf.util.StringToCaseConverter;

import org.jLOAF.action.Action;
import org.jLOAF.action.AtomicAction;
import org.jLOAF.action.ComplexAction;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.jLOAF.sim.SimilarityInputMetricStrategy;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractTestFramework{

	private CaseRun problemRun;
	private BacktrackingReasoning r;
	
	private boolean toFail;
	
	public AbstractTestFramework(){
		this(false);
	}
	
	public AbstractTestFramework(boolean toFail){
		this.toFail = toFail;
		CaseBase cb = buildCaseBase();;
		problemRun = buildCaseRun();
		
		AtomicInput.setClassStrategy(getAtomicInputSimMetric());
		ComplexInput.setClassStrategy(getComplexInputSimMetric());
		
		AtomicAction.setClassStrategy(getAtomicActionSimMetric());
		ComplexAction.setClassStrategy(getComplexActionSimMetric());
		
		r = buildReasoning(cb, problemRun);
	}
	
	public abstract BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun);

	public CaseBase buildCaseBase(){
		return StringToCaseConverter.convertStringToCaseBase(getCaseBaseString(), getCaseParser());
	}

	public CaseRun buildCaseRun(){
		return StringToCaseConverter.convertStringToCaseRun(getProblemString(), getCaseParser());
	}

	public abstract SimilarityInputMetricStrategy getAtomicInputSimMetric();
	public abstract SimilarityInputMetricStrategy getComplexInputSimMetric();
	
	public abstract SimilarityActionMetricStrategy getAtomicActionSimMetric();
	public abstract SimilarityActionMetricStrategy getComplexActionSimMetric();
	
	public abstract String[] getProblemString();
	public abstract String[] getCaseBaseString();
	public abstract AbstractCaseParser getCaseParser();
	

	@Test
	public void test() {
		System.out.println("Test : " + this.getClass().getSimpleName());
		Case testCase = problemRun.getCurrentCase();
		Action actual = testCase.getAction();
		
		testCase.setAction(null);
		
		Action a = r.selectAction(testCase.getInput());
		System.out.println("Expected : " + actual.toString() + ", Actual  : " + a.toString() + ", To Fail : " + toFail + "\n");
		if (!toFail){
			Assert.assertEquals(actual, a);
		}else{
			Assert.assertNotEquals(actual, a);
		}
	}

}