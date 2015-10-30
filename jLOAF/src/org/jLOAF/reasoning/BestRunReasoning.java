package org.jLOAF.reasoning;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.retrieve.BestRunRetrieval;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;

public class BestRunReasoning extends BacktrackingReasoning{

	public BestRunReasoning(CaseBase cb, double threshold){
		super(cb, threshold);
		this.strategy = new BestRunRetrieval();
	}
	
	public BestRunReasoning(CaseBase cb, double threshold, WeightFunction function){
		super(cb, threshold);
		this.strategy = new BestRunRetrieval(function);
	}

}
