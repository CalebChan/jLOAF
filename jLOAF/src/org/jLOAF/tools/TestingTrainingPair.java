package org.jLOAF.tools;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;

public class TestingTrainingPair {

	private CaseBase training;
	private CaseRun testing;
	
	public TestingTrainingPair(CaseBase training, CaseRun testing){
		this.testing = testing;
		this.training = training;
	}

	public CaseBase getTraining() {
		return training;
	}

	public CaseRun getTesting() {
		return testing;
	}

}
