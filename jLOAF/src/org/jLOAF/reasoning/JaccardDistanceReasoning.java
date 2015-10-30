package org.jLOAF.reasoning;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.retrieve.JaccardDistanceRetrival;

public class JaccardDistanceReasoning extends BacktrackingReasoning{
	
	public JaccardDistanceReasoning(CaseBase cb, double threshold, double equalThreshold) {
		super(cb, threshold);
		this.strategy = new JaccardDistanceRetrival(equalThreshold);
	}

}
