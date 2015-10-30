package org.jLOAF.tools;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;

public class TestingTrainingPair {

	private CaseBase training;
	private ComplexCase testing;
	
	private int testNo;
	
	public TestingTrainingPair(CaseBase training, ComplexCase testing, int testNo){
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

	public ComplexCase getTesting() {
		return testing;
	}

}
