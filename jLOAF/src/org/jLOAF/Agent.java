package org.jLOAF;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;

public abstract class Agent {

	protected Reasoning r;
	protected MotorControl mc;
	protected Perception p;
	protected CaseBase cb;
	
	public Agent(Reasoning reasoning, MotorControl motorcontrol, Perception perception, CaseBase casebase){
		this.r = reasoning;
		this.mc = motorcontrol;
		this.p = perception;
		this.cb = casebase;
	}
	
	public abstract Action senseEnvironment(Input input);
}
