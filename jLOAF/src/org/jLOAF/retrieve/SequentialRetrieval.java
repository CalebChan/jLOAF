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
		CaseLogger.log(Level.INFO, "S G O " + time);
		CaseLogger.log(Level.INFO, "S G T " + run.getRunLength());
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		CaseLogger.log(Level.INFO, "S G A " + bestRun.getCurrentCase().getAction().getName());
		CaseLogger.log(Level.INFO, "S G I " + bestRun.getCurrentCase().getInput().getName());
		for (CaseRun past : pastRuns){
			CaseLogger.log(Level.INFO, "S R I " + pastRuns.indexOf(past));
			double sim = -1;
			if (time < run.getRunLength() && time < past.getRunLength()){
				Input pastIn = past.getCase(past.getRunLength() - 1 - time).getInput();
				Input runIn = run.getCase(run.getRunLength() - 1 - time).getInput();
				sim = pastIn.similarity(runIn);
				CaseLogger.log(Level.INFO, "S I P " + pastIn.toString());
				CaseLogger.log(Level.INFO, "S I R " + runIn.toString());
				CaseLogger.log(Level.INFO, "S I S " + sim);
			}else{
				CaseLogger.log(Level.INFO, "S I P -");
				CaseLogger.log(Level.INFO, "S I R -");
				CaseLogger.log(Level.INFO, "S I S -");
			}
			if (sim > bestSim){
				CaseLogger.log(Level.INFO, "S C SO " + bestSim + " SN " + sim);
				CaseLogger.log(Level.INFO, "S C RO " + pastRuns.lastIndexOf(bestRun) + " RN " + pastRuns.lastIndexOf(past));
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.problemThreshold){
				CaseLogger.log(Level.INFO, "S T P T");
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
					CaseLogger.log(Level.INFO, "S T A N");
				}
			}else{
				CaseLogger.log(Level.INFO, "S T P F");
			}
		}
		CaseLogger.log(Level.INFO, "S G BS " + bestSim);
		CaseLogger.log(Level.INFO, "S G NN " + NN.size());
		CaseLogger.log(Level.INFO, "S G NNA " + NNAction.size());
		if (NN.isEmpty()){
			CaseLogger.log(Level.INFO, "S G C F");
			CaseLogger.log(Level.INFO, "S G FA " + bestRun.getCurrentCase().getAction().getName());
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			CaseLogger.log(Level.INFO, "S G C T");
			CaseLogger.log(Level.INFO, "S G FA " + NNAction.get(0).getName());
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
		CaseLogger.log(Level.INFO, "A G T " + time);
		CaseLogger.log(Level.INFO, "A G O " + run.getRunLength());
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim = -1;
			CaseLogger.log(Level.INFO, "A R I " + pastRuns.indexOf(past));
			if (time < run.getRunLength() && time < past.getRunLength()){
				Action pastAction = past.getCase(past.getRunLength() - 1 - time).getAction();
				Action runAction = run.getCase(run.getRunLength() - 1 - time).getAction();
				sim = similarityActions(pastAction, runAction);
				CaseLogger.log(Level.INFO, "A I P " + pastAction.getName());
				CaseLogger.log(Level.INFO, "A I R " + runAction.getName());
				CaseLogger.log(Level.INFO, "A I S " + sim);
			}else{
				CaseLogger.log(Level.INFO, "A I P -");
				CaseLogger.log(Level.INFO, "A I R -");
				CaseLogger.log(Level.INFO, "A I S -");
			}
			if (sim > bestSim){
				CaseLogger.log(Level.INFO, "A C SO " + bestSim + " SN " + sim);
				CaseLogger.log(Level.INFO, "A C RO " + pastRuns.lastIndexOf(bestRun) + " RN " + pastRuns.lastIndexOf(past));
				bestSim = sim;
				bestRun = past;
			}
			if (sim > this.solutionThreshold){
				CaseLogger.log(Level.INFO, "A T P T");
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
					CaseLogger.log(Level.INFO, "A T A N");
				}
			}else{
				CaseLogger.log(Level.INFO, "A T P F");
			}
		}
		CaseLogger.log(Level.INFO, "A G BS " + bestSim);
		CaseLogger.log(Level.INFO, "A G NN " + NN.size());
		CaseLogger.log(Level.INFO, "A G NNA " + NNAction.size());
		if (NN.isEmpty()){
			CaseLogger.log(Level.INFO, "A G C F");
			CaseLogger.log(Level.INFO, "A G FA " + bestRun.getCurrentCase().getAction().getName());
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			CaseLogger.log(Level.INFO, "A G C T");
			CaseLogger.log(Level.INFO, "A G FA " + NNAction.get(0).getName());
			return NNAction.get(0);
		}
		
		return stateRetrival(run, NN, time);
	}
}
