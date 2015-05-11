package org.jLOAF.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jLOAF.sim.SimilarityActionMetricStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComplexAction extends Action {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Action> collect;
	
	private static SimilarityActionMetricStrategy s_simstrategy;
	
	public ComplexAction(String name) {
		super(name);
		collect = new HashMap<String,Action>();
	}

	public void add(Action a){
		collect.put(a.name, a);
	}
	
	public Action get(String name){
		return collect.get(name);
	}
	
	public Set<String> getChildNames(){
		return collect.keySet();
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof ComplexAction)){
			return false;
		}
		ComplexAction c = (ComplexAction)o;
		if (!this.collect.keySet().equals(c.collect.keySet())){
			return false;
		}
		for (String s : this.collect.keySet()){
			if (!this.collect.get(s).equals(c.collect.get(s))){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString(){
		String s = "Complex Action : " + this.name + "\n";
		s += "Actions :\n";
		for (String ss : this.collect.keySet()){
			s += this.collect.get(ss).toString() + "\n";
		}
		return s;
	}
	
	@Override
	public JSONObject exportActionDetailToJSON(){
		JSONObject o = new JSONObject();
		o.put("Name", name);
		o.put("Type", "Complex");
		
		JSONArray a = new JSONArray();
		for (String ss : this.collect.keySet()){
			a.put(this.collect.get(ss).exportActionDetailToJSON());
		}
		o.put("Action", a);
		return o;
	}

	@Override
	public double similarity(Action i) {
		//See if the user has defined similarity for each specific input, for all inputs
		//  of a specific type, of defered to superclass
		if(this.simStrategy != null){
			return simStrategy.similarity(this, i);
		}else if(ComplexAction.isClassStrategySet()){
			return ComplexAction.similarity(this, i);
		}else{
			//normally we would defer to superclass, but super
			// is abstract
			System.err.println("Problem. In ComplexInput no similarity metric set!");
			return 0;
		}
	}
	
	private static double similarity(Action complexInput, Action i) {
		return ComplexAction.s_simstrategy.similarity(complexInput, i);
	}

	public static boolean isClassStrategySet(){
		if(ComplexAction.s_simstrategy == null){
			return false;
		}else{
			return true;
		}
	}

	public static void setClassStrategy(SimilarityActionMetricStrategy s){
		ComplexAction.s_simstrategy = s;
	}
}
