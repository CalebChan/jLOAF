package org.jLOAF.action;

import java.io.Serializable;

import org.jLOAF.sim.SimilarityActionMetricStrategy;

public abstract class Action implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected SimilarityActionMetricStrategy simStrategy;
	
	public Action(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSimpleString(){
		return this.name;
	}
	
	public abstract double similarity(Action i);
	
	public void setSimilarityMetric(SimilarityActionMetricStrategy s) {
		this.simStrategy = s;
	}

}
