package jloaf;
import jloaf.util.AbstractCaseParser;
import jloaf.util.BaselineCaseParser;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.reasoning.BacktrackingReasoning;
import org.jLOAF.reasoning.SequentialReasoning;
import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.jLOAF.sim.SimilarityInputMetricStrategy;
import org.jLOAF.sim.atomic.ActionEquality;
import org.jLOAF.sim.atomic.InputEquality;
import org.jLOAF.sim.complex.ActionMean;
import org.jLOAF.sim.complex.InputMean;



public class BaselineTest extends AbstractTestFramework {

	private static String problemRunString[] = {
		"0.1 1",
		"0.2 2",
	};
	
	private static String cbString[] = {
		"0.1 1",
		"0.2 2",
		"",
		"0.3 3",
		"0.4 4",
	};
	
	protected static final int DEFAULT_K = 2;
	
	protected BaselineTest(boolean b) {
		super(b);
	}
	
	public BaselineTest() {
		this(false);
	}

	@Override
	public BacktrackingReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
		return new SequentialReasoning(cb, problemRun, DEFAULT_K);
	}

	@Override
	public SimilarityInputMetricStrategy getAtomicInputSimMetric(){
		return new InputEquality();
	}

	@Override
	public SimilarityInputMetricStrategy getComplexInputSimMetric(){
		return new InputMean();
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
	public AbstractCaseParser getCaseParser() {
		return new BaselineCaseParser();
	}

	@Override
	public SimilarityActionMetricStrategy getAtomicActionSimMetric() {
		return new ActionEquality();
	}

	@Override
	public SimilarityActionMetricStrategy getComplexActionSimMetric() {
		return new ActionMean();
	}
}
