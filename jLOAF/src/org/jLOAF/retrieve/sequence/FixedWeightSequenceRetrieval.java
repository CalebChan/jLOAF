package org.jLOAF.retrieve.sequence;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.AbstractWeightedSequenceRetrieval;

public class FixedWeightSequenceRetrieval extends AbstractWeightedSequenceRetrieval {

	protected double weight;
	
	public FixedWeightSequenceRetrieval(double probThresh, double solThresh, double weight) {
		super(probThresh, solThresh);
		this.weight = weight;
	}

	@Override
	protected double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time) {
		Input pastIn = pastRun.getCase(pastRun.getRunLength() - 1 - time).getInput();
		Input runIn = currentRun.getCase(currentRun.getRunLength() - 1 - time).getInput();
		return pastIn.similarity(runIn);
	}

	@Override
	protected double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time) {
		Action pastAction = pastRun.getCase(pastRun.getRunLength() - 1 - time).getAction();
		Action runAction = currentRun.getCase(currentRun.getRunLength() - 1 - time).getAction();
		return similarityActions(pastAction, runAction);
	}

}
