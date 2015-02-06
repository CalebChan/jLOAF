package org.jLOAF.retrieve.sequence;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.AbstractWeightedSequenceRetrieval;

public class DefaultWeightSequenceRetrieval extends AbstractWeightedSequenceRetrieval {

	public DefaultWeightSequenceRetrieval(double probThresh, double solThresh) {
		super(probThresh, solThresh);
	}

	@Override
	protected double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time) {
		Input pastIn = pastRun.getCasePastOffset(time).getInput();
		Input runIn = currentRun.getCasePastOffset(time).getInput();
		return pastIn.similarity(runIn);
	}

	@Override
	protected double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time) {
		Action pastAction = pastRun.getCasePastOffset(time).getAction();
		Action runAction = currentRun.getCasePastOffset(time).getAction();
		return similarityActions(pastAction, runAction);
	}

}
