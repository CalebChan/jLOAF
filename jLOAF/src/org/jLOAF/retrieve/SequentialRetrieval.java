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
		CaseLogger.log(Level.INFO, "STATE GLOBAL TIME " + time);
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		CaseLogger.log(Level.INFO, "STATE GLOBAL ACTION " + bestRun.getCurrentCase().getAction().getName());
		CaseLogger.log(Level.INFO, "STATE GLOBAL INPUT " + bestRun.getCurrentCase().getInput().getName());
		for (CaseRun past : pastRuns){
			CaseLogger.log(Level.INFO, "STATE RUN INDEX " + pastRuns.indexOf(past));
			double sim = -1;
			if (time < run.getRunLength() && time < past.getRunLength()){
				Input pastIn = past.getCase(past.getRunLength() - 1 - time).getInput();
				Input runIn = run.getCase(run.getRunLength() - 1 - time).getInput();
				sim = pastIn.similarity(runIn);
				CaseLogger.log(Level.INFO, "STATE INPUT PAST " + pastIn.getName());
				CaseLogger.log(Level.INFO, "STATE INPUT RUN " + runIn.getName());
				CaseLogger.log(Level.INFO, "STATE INPUT SIM " + sim);
			}else{
				CaseLogger.log(Level.INFO, "STATE INPUT PAST -");
				CaseLogger.log(Level.INFO, "STATE INPUT RUN -");
				CaseLogger.log(Level.INFO, "STATE INPUT SIM -");
			}
			if (sim > bestSim){
				CaseLogger.log(Level.INFO, "STATE CHANGE SIM_OLD " + bestSim + " SIM_NEW " + sim);
				CaseLogger.log(Level.INFO, "STATE CHANGE RUN_OLD " + pastRuns.lastIndexOf(bestRun) + " RUN_NEW " + pastRuns.lastIndexOf(past));
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.problemThreshold){
				CaseLogger.log(Level.INFO, "STATE THRESHOLD PASSED TRUE");
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
					CaseLogger.log(Level.INFO, "STATE THRESHOLD ACTION NEW");
				}
			}else{
				CaseLogger.log(Level.INFO, "STATE THRESHOLD PASSED FALSE");
			}
		}
		CaseLogger.log(Level.INFO, "STATE GLOBAL BEST_SIM " + bestSim);
		CaseLogger.log(Level.INFO, "STATE GLOBAL NN " + NN.size());
		CaseLogger.log(Level.INFO, "STATE GLOBAL NNACTION " + NNAction.size());
		if (NN.isEmpty()){
			CaseLogger.log(Level.INFO, "STATE GLOBAL CONSENSUS FALSE");
			CaseLogger.log(Level.INFO, "STATE GLOBAL FINAL_ACTION " + bestRun.getCurrentCase().getAction().getName());
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			CaseLogger.log(Level.INFO, "STATE GLOBAL CONSENSUS TRUE");
			CaseLogger.log(Level.INFO, "STATE GLOBAL FINAL_ACTION " + NNAction.get(0).getName());
			return NNAction.get(0);
		}
		
		return actionRetrival(run, NN, time + 1);
	}

	private double similarityActions(Action a1, Action a2){
		if (a1.equals(a2)){
			return 1;
		}
		return -1;
	}
	
	public Action actionRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		CaseLogger.log(Level.INFO, "ACTION GLOBAL TIME " + time);
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim = -1;
			CaseLogger.log(Level.INFO, "ACTION RUN INDEX " + pastRuns.indexOf(past));
			if (time < run.getRunLength() && time < past.getRunLength()){
				Action pastAction = past.getCase(past.getRunLength() - 1 - time).getAction();
				Action runAction = run.getCase(run.getRunLength() - 1 - time).getAction();
				sim = similarityActions(pastAction, runAction);
				CaseLogger.log(Level.INFO, "ACTION INPUT PAST " + pastAction.getName());
				CaseLogger.log(Level.INFO, "ACTION INPUT RUN " + runAction.getName());
				CaseLogger.log(Level.INFO, "ACTION INPUT SIM " + sim);
			}else{
				CaseLogger.log(Level.INFO, "ACTION INPUT PAST -");
				CaseLogger.log(Level.INFO, "ACTION INPUT RUN -");
				CaseLogger.log(Level.INFO, "ACTION INPUT SIM -");
			}
			if (sim > bestSim){
				CaseLogger.log(Level.INFO, "ACTION CHANGE SIM_OLD " + bestSim + " SIM_NEW " + sim);
				CaseLogger.log(Level.INFO, "ACTION CHANGE RUN_OLD " + pastRuns.lastIndexOf(bestRun) + " RUN_NEW " + pastRuns.lastIndexOf(past));
				bestSim = sim;
				bestRun = past;
			}
			if (sim > this.solutionThreshold){
				CaseLogger.log(Level.INFO, "ACTION THRESHOLD PASSED TRUE");
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
					CaseLogger.log(Level.INFO, "ACTION THRESHOLD ACTION NEW");
				}
			}else{
				CaseLogger.log(Level.INFO, "ACTION THRESHOLD PASSED FALSE");
			}
		}
		CaseLogger.log(Level.INFO, "ACTION GLOBAL BEST_SIM " + bestSim);
		CaseLogger.log(Level.INFO, "ACTION GLOBAL NN " + NN.size());
		CaseLogger.log(Level.INFO, "ACTION GLOBAL NNACTION " + NNAction.size());
		if (NN.isEmpty()){
			CaseLogger.log(Level.INFO, "ACTION GLOBAL CONSENSUS FALSE");
			CaseLogger.log(Level.INFO, "ACTION GLOBAL FINAL_ACTION " + bestRun.getCurrentCase().getAction().getName());
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			CaseLogger.log(Level.INFO, "ACTION GLOBAL CONSENSUS TRUE");
			CaseLogger.log(Level.INFO, "ACTION GLOBAL FINAL_ACTION " + NNAction.get(0).getName());
			return NNAction.get(0);
		}
		
		return stateRetrival(run, NN, time);
	}
}
