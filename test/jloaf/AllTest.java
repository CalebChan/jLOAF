package jloaf;
import jloaf.baseline.BaselineLargerCBTest;
import jloaf.baseline.BaselineLargerProblemTest;
import jloaf.tb.NonReactiveBooleanTest;
import jloaf.tb.ReactiveBooleanTest;
import jloaf.tb.ReactiveBooleanVariableK;
import jloaf.tb.ReactiveContinuousTest;
import jloaf.tb.ReactiveContinuousVariableKFail;
import jloaf.tb.ReactiveContinuousVariableKPass;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	BaselineTest.class,
	BaselineLargerProblemTest.class,
	BaselineLargerCBTest.class,
	
	ReactiveBooleanTest.class,
	ReactiveContinuousTest.class,
	ReactiveBooleanVariableK.class,
	ReactiveContinuousVariableKPass.class,
	ReactiveContinuousVariableKFail.class,
	
	NonReactiveBooleanTest.class,
})
public class AllTest {

}
