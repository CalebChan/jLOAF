package org.jLOAF.casebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
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
	
	public void appendCaseToRun(Case c){
		this.run.push(c);
	}
	
	public Case getCurrentCase(){
		return this.run.peek();
	}
	
	/**
	 * This method will remove the case offset from the current case.
	 * @param time
	 * @return
	 */
	public Case removeCurrentCase(int time){
		Case c = this.run.remove(time);
		c.setParentCaseRun(null);
		return c;
	}
	
	public void amendCurrentCase(Case newCase){
		Case c = this.run.pop();
		c.setParentCaseRun(null);
		this.addCaseToRun(newCase);
	}
	
	public void reverseRun(){
		Collections.reverse(run);
	}
	
	public int getTimeStep(Case c){
		return this.getRunLength() - 1 - this.run.indexOf(c);
	}
	
	/**
	 * This method will get the case offset from the current case. 0 will be the current case, 1 will be the case previous to the current case
	 * @param time
	 * @return
	 */
	public Case getCasePastOffset(int time){
		return this.run.get(time);
	}
	
	/**
	 * This method will get the case at the current time in the list. This means that the case at time 0 is the start of the run
	 * and length() - 1 is the end of the run.
	 * @param time
	 * @return
	 * @deprecated
	 */
	public Case getCase(int time){
		return this.run.get(this.getRunLength() - 1 - time);
//		return this.run.get(Math.max(this.run.size() - 1 - time, 0));
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
		JSONObject o = new JSONObject();
		o.put("Name", this.getRunName());
		o.put("Length", this.run.size());
		
		JSONArray a = new JSONArray();
		
		while(i.hasNext()){
			c = i.next();
			a.put(c.exportCaseToJSON());
		}
		
		
		return o;
	}
}
