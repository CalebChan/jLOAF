package jloaf.util;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;

public class StringToCaseConverter {
	public static final CaseBase convertStringToCaseBase(String text[], AbstractCaseParser cp){
		CaseBase c = new CaseBase();
		CaseRun r = new CaseRun();
		for (String s : text){
			if (s.isEmpty() && r.getRunLength() != 0){
				r = new CaseRun();
			}else{
				Case newCase = cp.parseLine(s);
				if (r.getRunLength() > 0){
					newCase.setPreviousCase(r.getCurrentCase());
				}
				r.addCaseToRun(newCase);
				c.add(newCase);
			}			
		}
		return c;
	}
	
	public static final CaseRun convertStringToCaseRun(String text[], AbstractCaseParser cp){
		CaseRun r = new CaseRun();
		for (String s : text){
			Case newCase = cp.parseLine(s);
			if (r.getRunLength() > 0){
				newCase.setPreviousCase(r.getCurrentCase());
			}
			r.addCaseToRun(newCase);
		}
		return r;
	}
}
