package jloaf;

import org.jLOAF.action.AtomicAction;
import org.jLOAF.inputs.Feature;
import org.jLOAF.sim.atomic.ActionEquality;
import org.junit.Assert;
import org.junit.Test;

public class AtomicActionTest {
	
	@Test
	public void test(){
		AtomicAction.setClassStrategy(new ActionEquality());
		
		AtomicAction expected = new AtomicAction("Hello");
		expected.addFeature(new Feature(10.0));
		AtomicAction actual1 = new AtomicAction("World");
		actual1.addFeature(new Feature(10.0));
		
		Assert.assertNotEquals(expected, actual1);
		
		AtomicAction actual2 = new AtomicAction("Hello");
		actual2.addFeature(new Feature(20.0));
		Assert.assertNotEquals(expected, actual2);
		
		AtomicAction actual3 = new AtomicAction("Hello");
		actual3.addFeature(new Feature(10.0));
		Assert.assertEquals(expected, actual3);
	}
}
