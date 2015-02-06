package jloaf;
import jloaf.util.AbstractCaseParser;
import jloaf.util.StringToCaseConverter;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.reasoning.SequentialReasoning;
import org.jLOAF.sim.SimilarityMetricStrategy;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractTestFramework{

	private CaseRun problemRun;
	private SequentialReasoning r;
	
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
		
		r = buildReasoning(cb, problemRun);
	}
	
	public abstract SequentialReasoning buildReasoning(CaseBase cb, CaseRun problemRun);

	public CaseBase buildCaseBase(){
		return StringToCaseConverter.convertStringToCaseBase(getCaseBaseString(), getCaseParser());
	}

	public CaseRun buildCaseRun(){
		return StringToCaseConverter.convertStringToCaseRun(getProblemString(), getCaseParser());
	}

	public abstract SimilarityMetricStrategy getAtomicInputSimMetric();

	public abstract SimilarityMetricStrategy getComplexInputSimMetric();
	
	public abstract String[] getProblemString();
	public abstract String[] getCaseBaseString();
	public abstract AbstractCaseParser getCaseParser();
	

	@Test
	public void test() {
		Case testCase = problemRun.removeCurrentCase(0);
		
		Action a = r.selectAction(testCase.getInput());
		
		if (!toFail){
			Assert.assertEquals(testCase.getAction(), a);
		}else{
			Assert.assertNotEquals(testCase.getAction(), a);
		}
		System.out.println("Expected : " + testCase.getAction().toString() + ", Actual  : " + a.toString() + ", To Fail : " + toFail);
	}

}