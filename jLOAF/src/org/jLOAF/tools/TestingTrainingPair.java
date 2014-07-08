package org.jLOAF.tools;

import java.util.List;

import org.jLOAF.casebase.CaseRun;

public class TestingTrainingPair {

	private List<CaseRun> training;
	private CaseRun testing;
	
	public TestingTrainingPair(List<CaseRun> training, CaseRun testing){
		this.testing = testing;
		this.training = training;
	}

	public List<CaseRun> getTraining() {
		return training;
	}

	public CaseRun getTesting() {
		return testing;
	}

}
