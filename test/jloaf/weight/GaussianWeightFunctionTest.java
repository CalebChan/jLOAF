package jloaf.weight;

import org.jLOAF.retrieve.sequence.weight.GaussianWeightFunction;
import org.junit.Test;

public class GaussianWeightFunctionTest {

	@Test
	public void testWeightFunction(){
		GaussianWeightFunction function = new GaussianWeightFunction(0.0, 0.15);
		System.out.println("Value : " + function.getWeightValue(0));
	}
}
