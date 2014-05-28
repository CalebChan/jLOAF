package org.jLOAF.casebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseRun implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Case> run;
	
	public CaseRun(){
		this.run = new ArrayList<Case>();
	}
	
	public void addCaseToRun(Case c){
		this.run.add(c);
	}
	
	public Case getCurrentCase(){
		return this.run.get(run.size() - 1);
	}
	
	public Case getCase(int time){
		return this.run.get(time);
	}
	
	public int getRunLength(){
		return this.run.size();
	}
}
