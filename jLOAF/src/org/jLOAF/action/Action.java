package org.jLOAF.action;

import java.io.Serializable;

import org.json.JSONObject;

public class Action implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	public Action(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public JSONObject exportActionDetailToJSON(){
		JSONObject o = new JSONObject();
		
		o.put("Name", name);
		
		return o;
	}

}
