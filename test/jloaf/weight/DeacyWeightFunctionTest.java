package jloaf.weight;

import org.jLOAF.retrieve.sequence.weight.DecayWeightFunction;
import org.junit.Test;

public class DeacyWeightFunctionTest {

	@Test
	public void test() {
		DecayWeightFunction function = new DecayWeightFunction(0.1);
		System.out.println("Value : " + function.getWeightValue(0));
		System.out.println("Value : " + function.getWeightValue(1));
		System.out.println("Value : " + function.getWeightValue(2));
	}

}
