package org.jLOAF.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jLOAF.inputs.Feature;

public class ComplexAction extends Action {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Action> collect;
	
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
	public String toString(){
		String s = "Complex Action : " + this.name + "\n";
		s += "Actions :\n";
		for (String ss : this.collect.keySet()){
			s += this.collect.get(ss).toString() + "\n";
		}
		return s;
	}
}
