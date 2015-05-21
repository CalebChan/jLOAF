package jloaf.tb;

import jloaf.BaselineTest;

public class NonReactiveBooleanTest extends BaselineTest{
	
	public NonReactiveBooleanTest(){
		super(true);
	}
	
	private static String problemRunString[] = {
		"0.1 1",
		"0.2 2",
	};
	
	private static String cbString[] = {
		"0.1 1",
		"0.2 5",
		"",
		"0.1 1",
		"0.2 3",
	};
	
	@Override
	public String[] getProblemString() {
		return problemRunString;
	}

	@Override
	public String[] getCaseBaseString() {
		return cbString;
	}
}
