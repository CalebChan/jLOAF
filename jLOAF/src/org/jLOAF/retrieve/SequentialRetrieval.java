package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.inputs.Input;
import org.jLOAF.util.CaseLogger;

public class SequentialRetrieval {
	
	private double problemThreshold;
	private double solutionThreshold;
	
	public SequentialRetrieval(double probThresh, double solThresh){
		this.problemThreshold = probThresh;
		this.solutionThreshold = solThresh;
	}
	
	
	public Action stateRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim = -1;
			if (time < run.getRunLength() && time < past.getRunLength()){
				Input pastIn = past.getCase(past.getRunLength() - 1 - time).getInput();
				Input runIn = run.getCase(run.getRunLength() - 1 - time).getInput();
				sim = pastIn.similarity(runIn);
			}
			CaseLogger.log(Level.INFO, "SIM State : " + sim + "\n");
			if (sim > bestSim){
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.problemThreshold){
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
				}
			}
		}
		
		if (NN.isEmpty()){
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			return NNAction.get(0);
		}
		
		return actionRetrival(run, NN, time + 1);
	}

	private double similarityActions(Action a1, Action a2){
		if (a1.equals(a2)){
			return 1;
		}
		CaseLogger.log(Level.INFO, "Action 1 : " + a1.toString() + "\n");
		CaseLogger.log(Level.INFO, "Action 2 : " + a2.toString() + "\n");
		return -1;
	}
	
	public Action actionRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim = -1;
			if (time < run.getRunLength() && time < past.getRunLength()){
				Action pastAction = past.getCase(past.getRunLength() - 1 - time).getAction();
				Action runAction = run.getCase(run.getRunLength() - 1 - time).getAction();
				sim = similarityActions(pastAction, runAction);
			}
			CaseLogger.log(Level.INFO, "SIM Action : " + sim + "\n");
			if (sim > bestSim){
				bestSim = sim;
				bestRun = past;
			}
			if (sim > this.solutionThreshold){
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
				}
			}
		}
		
		if (NN.isEmpty()){
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			return NNAction.get(0);
		}
		
		return stateRetrival(run, NN, time);
	}
}
