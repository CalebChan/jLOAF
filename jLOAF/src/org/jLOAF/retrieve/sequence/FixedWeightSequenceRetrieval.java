package org.jLOAF.retrieve.sequence;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.AbstractWeightedSequenceRetrieval;
import org.jLOAF.retrieve.sequence.weight.FixedWeightFunction;

public class FixedWeightSequenceRetrieval extends AbstractWeightedSequenceRetrieval {

	public FixedWeightSequenceRetrieval(double probThresh, double solThresh, double weight) {
		super(probThresh, solThresh);
		this.weightFunction = new FixedWeightFunction(weight);
	}

	@Override
	protected double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time) {
		Input pastIn = pastRun.getCase(pastRun.getRunLength() - 1 - time).getInput();
		Input runIn = currentRun.getCase(currentRun.getRunLength() - 1 - time).getInput();
		return pastIn.similarity(runIn) * this.weightFunction.getWeightValue(time);
	}

	@Override
	protected double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time) {
		Action pastAction = pastRun.getCase(pastRun.getRunLength() - 1 - time).getAction();
		Action runAction = currentRun.getCase(currentRun.getRunLength() - 1 - time).getAction();
		return similarityActions(pastAction, runAction) * this.weightFunction.getWeightValue(time);
	}

}