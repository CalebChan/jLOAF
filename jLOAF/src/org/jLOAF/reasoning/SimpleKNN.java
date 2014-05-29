package org.jLOAF.reasoning;

import java.util.HashMap;
import java.util.List;

import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;
import org.jLOAF.retrieve.Retrieval;
import org.jLOAF.retrieve.kNN;

public class SimpleKNN implements Reasoning {

	private Retrieval ret;
	
	public SimpleKNN(int k, CaseBase cb){
		ret = new kNN(k, cb);
	}
	
	@Override
	public Action selectAction(Input i) {
		List<Case> nn = ret.retrieve(i);
		HashMap<String, Integer> classes = new HashMap<String, Integer>();
		for (Case c : nn){
			if (classes.containsKey(c.getAction().getName())){
				classes.put(c.getAction().getName(), classes.get(c.getAction().getName()) + 1);
			}else{
				classes.put(c.getAction().getName(), 1);
			}			
		}
		int largestClass = 0;
		String className = "";
		for (String s : classes.keySet()){
			if (classes.get(s) > largestClass){
				className = s;
				largestClass = classes.get(s);
			}
		}
		for (Case c : nn){
			if (c.getAction().getName().equals(className)){
				return c.getAction();
			}
		}
		return null;
	}

}
