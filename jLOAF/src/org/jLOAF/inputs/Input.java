/** Class that represents an agent's inputs.
 * 
 * Author: Michael W. Floyd
 * 
 */

package org.jLOAF.inputs;

import java.io.Serializable;

import org.jLOAF.sim.SimilarityMetricStrategy;
import org.json.JSONObject;

public abstract class Input implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected SimilarityMetricStrategy simStrategy;
	
	public Input(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract double similarity(Input i);
	
	
	public void setSimilarityMetric(SimilarityMetricStrategy s) {
		this.simStrategy = s;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public JSONObject exportInputDetailToJSON(){
		JSONObject o = new JSONObject();
		
		o.put("Name", name);
		
		return o;
	}
}
