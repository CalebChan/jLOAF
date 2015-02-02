package org.jLOAF.retrieve.sequence;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.AbstractWeightedSequenceRetrieval;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;

public class WeightSequenceRetrieval extends AbstractWeightedSequenceRetrieval {

	public WeightSequenceRetrieval(double probThresh, double solThresh, WeightFunction function) {
		super(probThresh, solThresh);
		this.weightFunction = function;
	}

	@Override
	protected double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time) {
		Input pastIn = pastRun.getCase(time).getInput();
		Input runIn = currentRun.getCase(time).getInput();
		return Math.max(0, Math.min(1.0, pastIn.similarity(runIn) * this.weightFunction.getWeightValue(time)));
	}

	@Override
	protected double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time) {
		Action pastAction = pastRun.getCase(time).getAction();
		Action runAction = currentRun.getCase(time).getAction();
		return Math.max(0, Math.min(1.0, similarityActions(pastAction, runAction) * this.weightFunction.getWeightValue(time)));
	}

}
