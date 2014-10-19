package org.jLOAF.tools;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;

public class TestingTrainingPair {

	private CaseBase training;
	private CaseRun testing;
	
	private int testNo;
	
	public TestingTrainingPair(CaseBase training, CaseRun testing, int testNo){
		this.testing = testing;
		this.training = training;
		this.testNo = testNo;
	}
	
	public int getTestNo(){
		return this.testNo;
	}

	public CaseBase getTraining() {
		return training;
	}

	public CaseRun getTesting() {
		return testing;
	}

}
