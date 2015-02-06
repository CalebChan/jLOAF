package jloaf.baseline;


import jloaf.BaselineTest;

public class BaselineLargerCBTest extends BaselineTest{
	private static String problemRunString[] = {
		"0.1 1",
		"0.2 2",
	};
	
	private static String cbString[] = {
		"0.1 1",
		"",
		"0.2 2",
		"",
		"0.3 3",
		"",
		"0.4 4",
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
