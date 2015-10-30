package jloaf.util;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;

public class StringToCaseConverter {
	public static final CaseBase convertStringToCaseBase(String text[], AbstractCaseParser cp){
		CaseBase c = new CaseBase();
		ComplexCase r = new ComplexCase(null, null);
		for (String s : text){
			if (s.isEmpty() && r.getComplexCaseSize() != 0){
				c.add(r);
				r = new ComplexCase(null, null);
			}else{
				Case newCase = cp.parseLine(s);
				r.pushCurrentCase(newCase.getInput(), newCase.getAction());
			}			
		}
		return c;
	}
	
	public static final ComplexCase convertStringToCaseRun(String text[], AbstractCaseParser cp){
		ComplexCase r = new ComplexCase();
		for (String s : text){
			Case newCase = cp.parseLine(s);
			r.pushCurrentCase(newCase.getInput(), newCase.getAction());
		}
		return r;
	}
}
