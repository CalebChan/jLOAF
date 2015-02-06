package jloaf;
import jloaf.baseline.BaselineLargerCBTest;
import jloaf.baseline.BaselineLargerProblemTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	BaselineTest.class,
	BaselineLargerProblemTest.class,
	BaselineLargerCBTest.class
})
public class AllTest {

}
