package jloaf;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	BaselineTest.class,
	
	jloaf.baseline.AllTest.class,
	jloaf.knn.util.AllTest.class,
	jloaf.tb.AllTest.class,
})
public class AllTest {

}
