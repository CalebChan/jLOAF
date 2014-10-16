package org.jLOAF.casebase;

import java.io.Serializable;

import org.jLOAF.action.Action;
import org.jLOAF.inputs.Input;

public class Case implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Input in;
	private Action act;
	
	private Case previousCase;
	
	public Case(Input input, Action action, Case previousCase){
		this.in = input;
		this.act = action;
		this.previousCase = previousCase;
	}
	
	public void setPreviousCase(Case previousCase){
		this.previousCase = previousCase;
	}
	
	public Case getPreviousCase(){
		return this.previousCase;
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
		int index = 1;
		
		Case c = this.previousCase;
		while (c != null){
			index++;
			c = c.getPreviousCase();
		}
		
		return index;
	}
	
	@Override
	public String toString(){
		String s = "";
		
		s += "Input : " + this.in.toString();
		s += "Action : " + this.act.toString();
		
		return s;
	}
}
