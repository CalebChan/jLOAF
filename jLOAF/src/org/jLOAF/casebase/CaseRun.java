package org.jLOAF.casebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class will hold a run.
 * @author calebchan
 *
 */
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
	
	/**
	 * This method will add a case to the run and change the case's parent run to this run. Cases should be added from start of the run to end of run.
	 * @param c The case to be added
	 */
	public void addCaseToRun(Case c){
		// Highest index should be current case
		this.run.push(c);
		c.setParentCaseRun(this);
	}
	
	/**
	 * This method will add a case to the run, but will not modify the parent run of the case
	 * @param c The case to be added
	 */
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
	
	/**
	 * If cases are added in from current to start, use this method to reverse the run order
	 */
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
		return this.run.get(this.getRunLength() - 1 - time);
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
