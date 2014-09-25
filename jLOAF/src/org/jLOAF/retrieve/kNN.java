package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;

public class kNN implements Retrieval {

	protected int k;
	protected CaseBase cb;
	
	public kNN(int k, CaseBase cb){
		this.cb = cb;
		this.k = k;
	}
	
	@Override
	public List<Case> retrieve(Input i) {
		double[] sim = new double[cb.getSize()];
		Case[] bestCases = new Case[k];
		bestCases[0] = null;
		
		int index = 0;
		for(Case c: cb.getCases()){
			sim[index] = i.similarity(c.getInput());
			index++;
		}
		
		double bestSim;
		int bestIndex;
		Case cases[] = cb.getCases().toArray(new Case[cb.getSize()]);
		for (int j = 0; j < k; j++){
			bestSim = -1;
			bestIndex = -1;
			for (int m = 0; m < cb.getSize(); m++){
				if (sim[m] > bestSim){
					bestSim = sim[m];
					bestIndex = m;
				}
			}
			if (bestIndex > -1){
				bestCases[j] = cases[bestIndex];
				cases[bestIndex] = null;
				sim[bestIndex] = -1;
			}
		}
		
		List<Case> best = new ArrayList<Case>();
		for (Case c : bestCases){
			best.add(c);
		}
		return best;
	}

}
