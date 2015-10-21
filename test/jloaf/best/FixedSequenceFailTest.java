package jloaf.best;

public class FixedSequenceFailTest extends RunSimilarityTest{

	private static String problemRunString[] = {
		"0.1 3",
		"0.2 4",
		"0.3 1",
		"0.4 2",
	};
	
	private static String cbString[] = {
		"0.1 1",
		"0.2 2",
		"0.3 3",
		"0.4 4",
		"",
		"0.3 3",
		"0.4 4",
		"0.2 1",
		"0.4 2",
	};
	
	public FixedSequenceFailTest() {
		super(true);
	}
	
	@Override
	public String[] getProblemString() {
		return problemRunString;
	}

	@Override
	public String[] getCaseBaseString() {
		return cbString;
	}
}
