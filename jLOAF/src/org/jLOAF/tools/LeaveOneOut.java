package org.jLOAF.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jLOAF.casebase.CaseRun;

public class LeaveOneOut {

	private List<CaseRun> training;
	private CaseRun testing;
	
	public LeaveOneOut(List<CaseRun> runs){
		this.training = new ArrayList<CaseRun>(runs);
		Random r = new Random();
		this.testing = this.training.remove(r.nextInt(this.training.size()));
	}
	
	public CaseRun getTestRun(){
		return this.testing;
	}
	
	public List<CaseRun> getTrainingRun(){
		return this.training;
	}

}
