package jloaf.best;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	RunSimilarityTest.class,
	FixedSequenceFailTest.class,
	FixedSequencePassTest.class,
})
public class AllTest {

}
