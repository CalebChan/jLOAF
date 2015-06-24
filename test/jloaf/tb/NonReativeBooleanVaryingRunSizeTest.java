package jloaf.tb;

import jloaf.BaselineTest;

public class NonReativeBooleanVaryingRunSizeTest extends BaselineTest{

	private static String problemRunString[] = {
		"0.1 1",
		"0.2 5",
	};
	
	private static String cbString[] = {
		"0.0 0",
		"0.1 1",
		"0.2 5",
		"",
		"0.0 0",
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
