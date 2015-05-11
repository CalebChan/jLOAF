package org.jLOAF.action;

import java.io.Serializable;

import org.jLOAF.sim.SimilarityMetricStrategy;
import org.json.JSONObject;

public abstract class Action implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected SimilarityMetricStrategy simStrategy;
	
	public Action(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract double similarity(Action i);
	
	public void setSimilarityMetric(SimilarityMetricStrategy s) {
		this.simStrategy = s;
	}
	
	public JSONObject exportActionDetailToJSON(){
		JSONObject o = new JSONObject();
		
		o.put("Name", name);
		
		return o;
	}

}
