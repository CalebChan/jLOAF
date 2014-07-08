package org.jLOAF.tools;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.CaseRun;

public class LeaveOneOut {
	
	private List<TestingTrainingPair> testTrainPair;
	
	public LeaveOneOut(List<CaseRun> runs, int numOfTest){
		this.testTrainPair = new ArrayList<TestingTrainingPair>();
		for (int i = 0; i < Math.min(runs.size(), numOfTest); i++){
			ArrayList<CaseRun> train = new ArrayList<CaseRun>(runs);
			CaseRun test = train.remove(i);
			this.testTrainPair.add(new TestingTrainingPair(train, test));
		}	
	}
	
	public List<TestingTrainingPair> getTestingAndTrainingSets(){
		return this.testTrainPair;
	}
}
