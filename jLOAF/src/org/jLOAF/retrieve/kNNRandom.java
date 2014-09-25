package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;

public class kNNRandom extends kNN {

	public kNNRandom(int k, CaseBase cb) {
		super(k, cb);
	}

	@Override
	public List<Case> retrieve(Input i){
		
		HashMap<Double, ArrayList<Case>> simList = new HashMap<Double, ArrayList<Case>>();
		for(Case c: cb.getCases()){
			double sim = i.similarity(c.getInput());
			if (!simList.containsKey(sim)){
				simList.put(sim, new ArrayList<Case>());
			}
			simList.get(sim).add(c);
		}
		TreeSet<Double> similarity = new TreeSet<Double>(simList.keySet());
		List<Case> bestCases = new ArrayList<Case>();
		for (Double d : similarity.descendingSet()){
			ArrayList<Case> c = simList.get(d);
			if (c == null){
				continue;
			}else if (c.size() + bestCases.size() <= k){
				bestCases.addAll(c);
			}else{
				Collections.shuffle(c);
				for (int j = 0; bestCases.size() < k; j++){
					bestCases.add(c.get(j));
				}
				return bestCases;
			}
		}
		
		
		return bestCases;
	}
}
