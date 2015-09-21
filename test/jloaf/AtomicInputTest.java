package jloaf;

import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.Feature;
import org.jLOAF.sim.atomic.InputEquality;
import org.junit.Assert;
import org.junit.Test;

public class AtomicInputTest {

	
	@Test
	public void test(){
		AtomicInput.setClassStrategy(new InputEquality());
		
		AtomicInput expected = new AtomicInput("Hello", new Feature(10.0));
		AtomicInput actual1 = new AtomicInput("World", new Feature(10.0));
		
		Assert.assertNotEquals(expected, actual1);
		
		AtomicInput actual2 = new AtomicInput("Hello", new Feature(20.0));
		Assert.assertNotEquals(expected, actual2);
		
		AtomicInput actual3 = new AtomicInput("Hello", new Feature(10.0));
		Assert.assertEquals(expected, actual3);
	}
}
