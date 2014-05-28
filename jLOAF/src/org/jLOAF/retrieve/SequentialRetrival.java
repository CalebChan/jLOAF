package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;

public class SequentialRetrival {
	
	private double problemThreshold;
	private double solutionThreshold;
	
	public SequentialRetrival(double probThresh, double solThresh){
		this.problemThreshold = probThresh;
		this.solutionThreshold = solThresh;
	}
	
	
	public Action stateRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim;
			if (past.getRunLength() < time || run.getRunLength() < time){
				sim = -1;
			}else{
				sim = calculateSimInput(run, past, time);
			}
			//System.out.println("SIM : " + sim);
			if (sim > bestSim){
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.problemThreshold){
				NN.add(past);
				if (!NNAction.contains(past.getCase(time).getAction())){
					NNAction.add(past.getCase(time).getAction());
				}
			}
		}
		
		if (NN.isEmpty()){
			return bestRun.getCase(bestRun.getRunLength() - 1).getAction();
		}else if (NNAction.size() == 1){
			return NNAction.get(0);
		}
		
		return actionRetrival(run, NN, time - 1);
	}
	
	private double calculateSimInput(CaseRun run, CaseRun past, int index){
		double total = 0;
		
		int lowIndex = Math.min(run.getRunLength(), past.getRunLength());
		for(int i = Math.max(lowIndex - index, 0); i < lowIndex; i++){
			total += past.getCase(i).getInput().similarity(run.getCase(i).getInput());
			System.out.println("Total : " + total);
		}
		
		return total;
	}
	
	private double calculateSimAction(CaseRun run, CaseRun past, int index){
		double total = 0;
		
		int lowIndex = Math.min(run.getRunLength(), past.getRunLength());
		for(int i = Math.max(lowIndex - index, 0); i < lowIndex; i++){
			total += similarityActions(past.getCase(i).getAction(), run.getCase(i).getAction());
		}
		return total;
	}
	
	private double similarityActions(Action a1, Action a2){
		if (a1.equals(a2)){
			return 1;
		}
		return -1;
	}
	
	public Action actionRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim;
			if (past.getRunLength() < time || run.getRunLength() < time){
				sim = -1;
			}else{
				sim = calculateSimAction(run, past, time);
			}
			
			if (sim > bestSim){
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.solutionThreshold){
				NN.add(past);
				if (!NNAction.contains(past.getCase(time).getAction())){
					NNAction.add(past.getCase(time).getAction());
				}
			}
		}
		
		if (NN.isEmpty()){
			return bestRun.getCase(bestRun.getRunLength() - 1).getAction();
		}else if (NNAction.size() == 1){
			return NNAction.get(0);
		}
		
		return stateRetrival(run, NN, time);
	}
}
