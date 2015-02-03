import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.reasoning.SequentialReasoning;
import org.jLOAF.sim.SimilarityMetricStrategy;
import org.jLOAF.sim.atomic.Equality;
import org.jLOAF.sim.complex.Mean;
import util.BaselineCaseParser;
import util.StringToCaseConverter;



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
	
	private static final int DEFAULT_K = 2;
	
	/* (non-Javadoc)
	 * @see TestInterface#buildReasoning()
	 */
	@Override
	public SequentialReasoning buildReasoning(CaseBase cb, CaseRun problemRun){
		return new SequentialReasoning(cb, problemRun, DEFAULT_K);
	}
	
	/* (non-Javadoc)
	 * @see TestInterface#buildCaseBase()
	 */
	@Override
	public CaseBase buildCaseBase(){
		return StringToCaseConverter.convertStringToCaseBase(cbString, new BaselineCaseParser());
	}
	
	/* (non-Javadoc)
	 * @see TestInterface#buildCaseRun()
	 */
	@Override
	public CaseRun buildCaseRun(){
		return StringToCaseConverter.convertStringToCaseRun(problemRunString, new BaselineCaseParser());
	}
	
	/* (non-Javadoc)
	 * @see TestInterface#getAtomicInputSimMetric()
	 */
	@Override
	public SimilarityMetricStrategy getAtomicInputSimMetric(){
		return new Equality();
	}
	
	/* (non-Javadoc)
	 * @see TestInterface#getComplexInputSimMetric()
	 */
	@Override
	public SimilarityMetricStrategy getComplexInputSimMetric(){
		return new Mean();
	}
}
