package jloaf.baseline;


import jloaf.BaselineTest;
import jloaf.util.BaselineCaseParser;
import jloaf.util.StringToCaseConverter;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;


public class BaselineLargerProblemTest extends BaselineTest{
	
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
	public CaseBase buildCaseBase(){
		return StringToCaseConverter.convertStringToCaseBase(cbString, new BaselineCaseParser());
	}

	@Override
	public CaseRun buildCaseRun(){
		return StringToCaseConverter.convertStringToCaseRun(problemRunString, new BaselineCaseParser());
	}
}
