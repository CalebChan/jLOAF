package org.jLOAF.casebase;

import java.io.Serializable;

import org.jLOAF.action.Action;
import org.jLOAF.inputs.Input;

public abstract class Case implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Input in;
	protected Action act;
	
	public Case(Input input, Action action, Case previousCase){
		this.in = input;
		this.act = action;
	}
	
	public Case(Input input, Action action){
		this(input, action, null);
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
	
	public abstract double similarity(Case i);
	
	@Override
	public String toString(){
		String s = "";
		s += "Input : " + this.in.toString() + " ";
		if (this.act != null){
			s += "Action : " + this.act.toString();
		}else{
			s += "Action : ?";
		}
		
		return s;
	}
}
