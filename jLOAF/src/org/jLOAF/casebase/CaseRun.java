package org.jLOAF.casebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONObject;

public class CaseRun implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList<Case> run;
	
	private String runName;
	
	public CaseRun(){
		this("");
	}
	
	public CaseRun(String runName){
		this.runName = runName;
		this.run = new LinkedList<Case>();
	}
	
	public String getRunName(){
		return this.runName;
	}
	
	public void addCaseToRun(Case c){
		this.run.push(c);
		c.setParentCaseRun(this);
	}
	
	public Case getCurrentCase(){
		return this.run.peek();
	}
	
	public void amendCurrentCase(Case newCase){
		this.run.pop().setParentCaseRun(null);
		this.addCaseToRun(newCase);
	}
	
	public void reverseRun(){
		Collections.reverse(run);
	}
	
	public int getTimeStep(Case c){
		return this.run.indexOf(c);
	}
	
	public Case getCase(int time){
		return this.run.get(Math.max(this.run.size() - 1 - time, 0));
	}
	
	public int getRunLength(){
		return this.run.size();
	}
	
	public String toString(){
		String s = "";
		for (Case c : this.run){
			s += c.toString() + "\n";
		}
		return s;
	}
	
	public JSONObject exportRunToJSON(){
		Case c = null;
		Iterator<Case> i = this.run.descendingIterator();
		while(i.hasNext()){
			c = i.next();
			
			
		}
		
		
		return (JSONObject) JSONObject.NULL;
	}
}
