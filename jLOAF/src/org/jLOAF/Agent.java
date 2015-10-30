package org.jLOAF;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;

public abstract class Agent {

	protected Reasoning r;
	protected Perception p;
	protected CaseBase cb;
	
	public Agent(Reasoning reasoning, Perception perception, CaseBase casebase){
		this.r = reasoning;
		this.p = perception;
		this.cb = casebase;
	}
	
	public abstract Action senseEnvironment(Input input);
	
	/**
	 * This method will add the new case to the case based as a amendment to the case just performed
	 * @param newCase The new case to be added
	 */
	public void learn(Case newCase){
		this.cb.add(newCase);
	}
}
