package jloaf.util;

import java.util.StringTokenizer;

import org.jLOAF.action.AtomicAction;
import org.jLOAF.casebase.Case;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.Feature;

public class BaselineCaseParser extends AbstractCaseParser{

	@Override
	public Case parseLine(String line) {
		StringTokenizer t = new StringTokenizer(line);
		AtomicInput aI = new AtomicInput("Input", new Feature(Double.parseDouble(t.nextToken())));
		AtomicAction aA = new AtomicAction("Ouput");
		aA.addFeature(new Feature(Double.parseDouble(t.nextToken())));
		return new Case(aI, aA, null);
	}

}
