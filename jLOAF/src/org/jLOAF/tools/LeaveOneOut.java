package org.jLOAF.tools;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.ComplexCase;

public class LeaveOneOut {
	
	private List<TestingTrainingPair> testTrainPair;
	
	private LeaveOneOut(List<ComplexCase> runs, int numOfTest){
		this.testTrainPair = new ArrayList<TestingTrainingPair>();
		for (int i = 0; i < Math.min(runs.size(), numOfTest); i++){
			ArrayList<ComplexCase> train = new ArrayList<ComplexCase>(runs);
			ComplexCase test = null;
			test = train.remove(i);
			
			CaseBase base = new CaseBase();
			for (ComplexCase cc : train){
				base.add(cc);
			}
			
			this.testTrainPair.add(new TestingTrainingPair(base, test, i));			
		}	
	}
	
	public List<TestingTrainingPair> getTestingAndTrainingSets(){
		return this.testTrainPair;
	}
	
	public static LeaveOneOut loadTrainAndTest(String filename, int runSize, int numOfTest){
		CaseBase cb = CaseBaseIO.loadCaseBase(filename);
		ArrayList<ComplexCase> runs = new ArrayList<ComplexCase>();
		
		ComplexCase r = new ComplexCase();
		for (Case c : cb.getCases()){
			r.pushCurrentCase(c.getInput(), c.getAction());
			if (r.getComplexCaseSize() >= runSize){
				runs.add(r);
				r = new ComplexCase();
			}
		}
		return new LeaveOneOut(runs, numOfTest);
	}
}
