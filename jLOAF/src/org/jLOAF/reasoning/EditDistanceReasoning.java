package org.jLOAF.reasoning;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.retrieve.EditDistanceRetrieval;

public class EditDistanceReasoning extends BacktrackingReasoning{

	public EditDistanceReasoning(CaseBase cb, double threshold) {
		super(cb, threshold);
		this.strategy = new EditDistanceRetrieval(threshold);
	}

}
