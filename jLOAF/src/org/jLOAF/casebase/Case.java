package org.jLOAF.casebase;

import java.io.Serializable;

import org.jLOAF.action.Action;
import org.jLOAF.inputs.Input;
import org.json.JSONObject;

public class Case implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Input in;
	private Action act;
	
	private Case previousCase;
	
	private Case nextCase;
	private CaseRun parentRun;
	
	public Case(Input input, Action action, Case previousCase){
		this.in = input;
		this.act = action;
		this.previousCase = previousCase;
	}
	
	public void setParentCaseRun(CaseRun run){
		this.parentRun = run;
	}
	
	public CaseRun getParentCaseRun(){
		return this.parentRun;
	}
	
	public void setPreviousCase(Case previousCase){
		this.previousCase = previousCase;
	}
	
	public Case getPreviousCase(){
		return this.previousCase;
	}
	
	public void setNextCase(Case nextCase){
		this.nextCase = nextCase;
	}
	
	public Case getNextCase(){
		return this.nextCase;
	}
	
	public Input getInput(){
		return this.in;
	}
	
	public Action getAction(){
		return this.act;
	}
	
	public void setAction(Action a){
		this.act = a;
	}
	
	public int caseIndex(){
//		int index = 1;
//		
//		Case c = this.previousCase;
//		while (c != null){
//			index++;
//			c = c.getPreviousCase();
//		}
//		
//		return index;
		if (this.getParentCaseRun() == null){
			return -1;
		}
		
		return this.getParentCaseRun().getTimeStep(this);
	}
	
	@Override
	public String toString(){
		String runName = "NONE";
		if (this.getParentCaseRun() != null){
			runName = this.getParentCaseRun().getRunName();
		}
		String s = "Case Run name : " + runName + "\n";
		
		s+= "Case Index : " + this.caseIndex() + "\n";
		
		s += "Input : " + this.in.toString() + "\n";
		s += "Action : " + this.act.toString();
		
		return s;
	}
	
	public JSONObject exportCaseToJSON(){
		JSONObject o = new JSONObject();
		o.put("Input", in.exportInputDetailToJSON());
		o.put("Output", act.exportActionDetailToJSON());
		o.put("Index", this.caseIndex());
		o.put("Parent Run", this.getParentCaseRun().getRunName());
		return o;
	}
}
