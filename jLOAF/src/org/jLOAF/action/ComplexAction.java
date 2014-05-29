package org.jLOAF.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
}
