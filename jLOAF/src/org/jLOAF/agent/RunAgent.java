package org.jLOAF.agent;

import org.jLOAF.Agent;
import org.jLOAF.Perception;
import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;
import org.jLOAF.inputs.Input;

public class RunAgent extends Agent{
	
	protected ComplexCase currentRun;

	public RunAgent(Reasoning reasoning, CaseBase casebase){
		this(reasoning, null, casebase);
	}
	
	public RunAgent(Reasoning reasoning, Perception perception, CaseBase casebase){
		super(reasoning, perception, casebase);
		
		this.currentRun = new ComplexCase(null, null);
	}
	
	public ComplexCase getCurrentRun(){
		return this.currentRun;
	}
	
	/**
	 * This method will take an input and return an action based on the reasoning method. Using the given
	 * input it will build a case and add it to the current run. The new case will be composed from the given
	 * input and the action that was returned from the reasoning method. This method will add the new case to the
	 * case base
	 * 
	 * @param input The input received from the environment
	 * @return The Action returned from the reasoning method
	 */
	@Override
	public Action senseEnvironment(Input input){
		this.currentRun.pushCurrentCase(input, null);
		Action a = this.r.selectAction(input);
		this.currentRun.setAction(a);
		return a;
	}
	
	@Override
	public void learn(Case newCase){
//		if (newCase == null){
//			throw new IllegalArgumentException("Case to learn is NULL");
//		}
//		Case c = new Case(newCase.getInput(), newCase.getAction());
//		this.currentRun.amendCurrentCase(c, false);
//		super.learn(c);
	}
}
