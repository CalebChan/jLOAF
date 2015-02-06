package jloaf;
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
	
	public AbstractTestFramework(){
		CaseBase cb = buildCaseBase();;
		problemRun = buildCaseRun();
		
		AtomicInput.setClassStrategy(getAtomicInputSimMetric());
		ComplexInput.setClassStrategy(getComplexInputSimMetric());
		
		r = buildReasoning(cb, problemRun);
	}
	
	public abstract SequentialReasoning buildReasoning(CaseBase cb, CaseRun problemRun);

	public abstract CaseBase buildCaseBase();

	public abstract CaseRun buildCaseRun();

	public abstract SimilarityMetricStrategy getAtomicInputSimMetric();

	public abstract SimilarityMetricStrategy getComplexInputSimMetric();

	@Test
	public void test() {
		Case testCase = problemRun.removeCurrentCase(0);
		
		Action a = r.selectAction(testCase.getInput());
		
		Assert.assertEquals(testCase.getAction(), a);
		System.out.println("Expected : " + testCase.getAction().toString() + " Expected  : " + a.toString());
	}

}