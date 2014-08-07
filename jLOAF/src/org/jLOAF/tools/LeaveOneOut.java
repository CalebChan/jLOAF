package org.jLOAF.tools;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.casebase.CaseRun;

public class LeaveOneOut {
	
	private List<TestingTrainingPair> testTrainPair;
	
	private LeaveOneOut(List<CaseRun> runs, int numOfTest){
		this.testTrainPair = new ArrayList<TestingTrainingPair>();
		for (int i = 0; i < Math.min(runs.size(), numOfTest); i++){
			ArrayList<CaseRun> train = new ArrayList<CaseRun>(runs);
			CaseRun test = null;
			test = train.remove(i);
			
			CaseBase base = new CaseBase();
			for (CaseRun cc : train){
				for (int j = 0; j < cc.getRunLength(); j++){
					base.add(cc.getCase(j));
				}
			}
			
			this.testTrainPair.add(new TestingTrainingPair(base, test));			
		}	
	}
	
	public List<TestingTrainingPair> getTestingAndTrainingSets(){
		return this.testTrainPair;
	}
	
	public static LeaveOneOut loadTrainAndTest(String filename, int runSize, int numOfTest){
		CaseBase cb = CaseBaseIO.loadCaseBase(filename);
		ArrayList<CaseRun> runs = new ArrayList<CaseRun>();
		CaseRun r = new CaseRun();
		for (Case c : cb.getCases()){
			r.addCaseToRun(c);
			if (r.getRunLength() == runSize){
				runs.add(r);
				r = new CaseRun();
			}
		}
		return new LeaveOneOut(runs, numOfTest);
	}
}
