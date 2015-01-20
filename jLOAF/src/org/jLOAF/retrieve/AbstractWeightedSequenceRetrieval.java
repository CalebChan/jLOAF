package org.jLOAF.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseRun;
import org.jLOAF.retrieve.sequence.weight.WeightFunction;
import org.jLOAF.util.JLOAFLogger;
import org.jLOAF.util.JLOAFLogger.Level;

public abstract class AbstractWeightedSequenceRetrieval {

	protected double problemThreshold;
	protected double solutionThreshold;
	protected WeightFunction weightFunction;
	
	private JLOAFLogger logger;
	
	public AbstractWeightedSequenceRetrieval(double probThresh, double solThresh){
		this.problemThreshold = probThresh;
		this.solutionThreshold = solThresh;
		
		this.logger = JLOAFLogger.getInstance();
	}
	
	protected abstract double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time);
	protected abstract double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time);
	
	public WeightFunction getWeightFunction(){
		return this.weightFunction;
	}
	
	public Action stateRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		logger.logMessage(Level.DEBUG, this.getClass(), "S G O", "" + time);
		logger.logMessage(Level.DEBUG, this.getClass(), "S G T", "" + run.getRunLength());
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		logger.logMessage(Level.DEBUG, this.getClass(), "S G A", bestRun.getCurrentCase().getAction().getName());
		logger.logMessage(Level.DEBUG, this.getClass(), "S G I", bestRun.getCurrentCase().getInput().getName());
		
		for (CaseRun past : pastRuns){
			
			logger.logMessage(Level.DEBUG, this.getClass(), "S R I", "" + pastRuns.indexOf(past));
			
			double sim = -1;
			if (time < run.getRunLength() && time < past.getRunLength()){
				sim = getStateSimilairty(run, past, time);
				
				logger.logMessage(Level.DEBUG, this.getClass(), "S I S", "" + sim);
			}else{
				logger.logMessage(Level.DEBUG, this.getClass(), "S I S", "-");
			}
			
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Time", time);
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "RType", "State");
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Candidate", past.getCase(time).exportCaseToJSON());
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Sim", sim);
			
			if (sim > bestSim){
				logger.logMessage(Level.DEBUG, this.getClass(), "S C SO", bestSim + " SN " + sim);
				logger.logMessage(Level.DEBUG, this.getClass(), "S C RO","" +  pastRuns.lastIndexOf(bestRun) + " RN " + pastRuns.lastIndexOf(past));
				bestSim = sim;
				bestRun = past;
			}
			
			if (sim > this.problemThreshold){
				
				logger.logMessage(Level.DEBUG, this.getClass(), "S T P", "T");
				
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
				}
			}else {
				logger.logMessage(Level.DEBUG, this.getClass(), "S T P", "F");
			}
		}
		
		logger.logMessage(Level.DEBUG, this.getClass(), "S G BS", "" + bestSim);
		logger.logMessage(Level.DEBUG, this.getClass(), "S G NN", "" + NN.size());
		logger.logMessage(Level.DEBUG, this.getClass(), "S G NNA", "" + NNAction.size());
		
		if (NN.isEmpty()){
			
			logger.logMessage(Level.DEBUG, this.getClass(), "S G C", "F");
			logger.logMessage(Level.DEBUG, this.getClass(), "S G FA", bestRun.getCurrentCase().getAction().getName());
			
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			
			logger.logMessage(Level.DEBUG, this.getClass(), "S G C", "T");
			logger.logMessage(Level.DEBUG, this.getClass(), "S G FA", NNAction.get(0).getName());
			
			return NNAction.get(0);
		}
		
		return actionRetrival(run, NN, time + 1);
	}

	protected double similarityActions(Action a1, Action a2){
		if (a1.equals(a2)){
			return 1;
		}
		return -1;
	}
	
	public Action actionRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
		
		logger.logMessage(Level.DEBUG, this.getClass(), "A G O", "" + time);
		logger.logMessage(Level.DEBUG, this.getClass(), "A G T", "" + run.getRunLength());
		
		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
		ArrayList<Action> NNAction = new ArrayList<Action>();
		double bestSim = -1;
		CaseRun bestRun = pastRuns.get(0);
		
		for (CaseRun past : pastRuns){
			double sim = -1;
			
			logger.logMessage(Level.DEBUG, this.getClass(), "A R I", "" + pastRuns.indexOf(past));
			
			if (time < run.getRunLength() && time < past.getRunLength()){
				sim = getActionSimilarity(run, past, time);
				
				logger.logMessage(Level.DEBUG, this.getClass(), "A I S", "" + sim);
			}else{
				logger.logMessage(Level.DEBUG, this.getClass(), "A I S", "-");
			}
			
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Time", time);
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "RType", "Action");
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Candidate", past.getCase(time).exportCaseToJSON());
			logger.logMessage(Level.EXPORT, getClass(), JLOAFLogger.JSON_TAG, "Sim", sim);
			
			if (sim > bestSim){
				
				logger.logMessage(Level.DEBUG, this.getClass(), "A C SO", bestSim + " SN " + sim);
				logger.logMessage(Level.DEBUG, this.getClass(), "A C RO", pastRuns.lastIndexOf(bestRun) + " RN " + pastRuns.lastIndexOf(past));
				
				bestSim = sim;
				bestRun = past;
			}
			if (sim > this.solutionThreshold){
				logger.logMessage(Level.DEBUG, this.getClass(), "A T P", "T");
				NN.add(past);
				if (!NNAction.contains(past.getCurrentCase().getAction())){
					NNAction.add(past.getCurrentCase().getAction());
					logger.logMessage(Level.DEBUG, this.getClass(), "A T A", "N");
				}
			}else{
				logger.logMessage(Level.DEBUG, this.getClass(), "A T P", "F");
			}
		}
		
		logger.logMessage(Level.DEBUG, this.getClass(), "A G BS", "" + bestSim);
		logger.logMessage(Level.DEBUG, this.getClass(), "A G NN", "" + NN.size());
		logger.logMessage(Level.DEBUG, this.getClass(), "A G NNA", "" + NNAction.size());
		
		if (NN.isEmpty()){
			
			logger.logMessage(Level.DEBUG, this.getClass(), "A G C", "F");
			logger.logMessage(Level.DEBUG, this.getClass(), "A G FA", "" + bestRun.getCurrentCase().getAction().getName());
			
			return bestRun.getCurrentCase().getAction();
		}else if (NNAction.size() == 1){
			
			logger.logMessage(Level.DEBUG, this.getClass(), "A G C", "T");
			logger.logMessage(Level.DEBUG, this.getClass(), "A G FA", "" + NNAction.get(0).getName());
			
			return NNAction.get(0);
		}
		
		return stateRetrival(run, NN, time);
	}
}
