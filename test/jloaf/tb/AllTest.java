package jloaf.tb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ReactiveBooleanTest.class,
	ReactiveContinuousTest.class,
	ReactiveBooleanVariableK.class,
	ReactiveContinuousVariableKPass.class,
	ReactiveContinuousVariableKFail.class,
	
	NonReactiveBooleanTest.class,
	NonReativeBooleanVaryingRunSizeTest.class,
	NonReactiveContinousVaryingRunSizeTest.class
})
public class AllTest {

}
