package org.jLOAF.retrieve;

public class SequenceRetrieval {

//	protected double problemThreshold;
//	protected double solutionThreshold;
//	
//	public SequenceRetrieval(double probThresh, double solThresh){
//		this.problemThreshold = probThresh;
//		this.solutionThreshold = solThresh;
//	}
//	
//	protected double getStateSimilairty(CaseRun currentRun, CaseRun pastRun, int time){
//		Input pastIn = pastRun.getCasePastOffset(time).getInput();
//		Input runIn = currentRun.getCasePastOffset(time).getInput();
//		return pastIn.similarity(runIn);
//	}
//	protected double getActionSimilarity(CaseRun currentRun, CaseRun pastRun, int time){
//		Action pastAction = pastRun.getCasePastOffset(time).getAction();
//		Action runAction = currentRun.getCasePastOffset(time).getAction();
//		return pastAction.similarity(runAction);
//	}
//	
//	private CaseRun getBestRun(List<ComplexCase> candidateRun, CaseRun run, int time, boolean isState){
//		CaseRun best = null;
//		double bestSim = -1;
//		for (CaseRun r : candidateRun){
//			if (time >= r.getRunLength() || time >= run.getRunLength()){
//				continue;
//			}
//			double s = -1;
//			if (isState){
//				s = r.getCasePastOffset(time).getInput().similarity(run.getCasePastOffset(time).getInput());
//			}else{
//				// Current problem case does not have an action
//				if (time != 0){
//					s = r.getCasePastOffset(time).getAction().similarity(run.getCasePastOffset(time).getAction());
//				}
//			}
//			if (s > bestSim){
//				bestSim = s;
//				best = r;
//			}
//		}
//		
//		return best;
//	}
//	
//	public Action stateRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
//		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
//		ArrayList<Action> NNAction = new ArrayList<Action>();
//		double bestSim = -1;
//		CaseRun bestRun = getBestRun(pastRuns, run, time, true);
//
//		for (CaseRun past : pastRuns){
//			
//			double sim = -1;
//			if (time < run.getRunLength() && time < past.getRunLength()){
//				sim = getStateSimilairty(run, past, time);
//			}
//			if (sim > bestSim){
//				bestSim = sim;
//				bestRun = past;
//			}
////			System.out.println("State: Sim : " + sim);
//			if (sim > this.problemThreshold){
//				NN.add(past);
//				if (!NNAction.contains(past.getCurrentCase().getAction())){
//					NNAction.add(past.getCurrentCase().getAction());
//				}
//			}
//		}
//		
//		if (NN.isEmpty()){
//			if (bestRun == null){
//				return null;
//			}
//			return bestRun.getCurrentCase().getAction();
//		}else if (NNAction.size() == 1){
//			return NNAction.get(0);
//		}
//		Action a = actionRetrival(run, NN, time + 1);
//		if (a != null){
//			return a;
//		}
//		return bestRun.getCurrentCase().getAction();
//	}
//	
//	public Action actionRetrival(CaseRun run, List<CaseRun> pastRuns, int time){
//		
//		ArrayList<CaseRun> NN = new ArrayList<CaseRun>();
//		ArrayList<Action> NNAction = new ArrayList<Action>();
//		double bestSim = -1;
//		CaseRun bestRun = getBestRun(pastRuns, run, time, false);
//		
//		for (CaseRun past : pastRuns){
//			double sim = -1;
//			
//			if (time < run.getRunLength() && time < past.getRunLength()){
//				sim = getActionSimilarity(run, past, time);
//			}
//			
//			if (sim > bestSim){
//				bestSim = sim;
//				bestRun = past;
//			}
////			System.out.println("Action: Sim : " + sim);
//			if (sim > this.solutionThreshold){
//				NN.add(past);
//				if (!NNAction.contains(past.getCurrentCase().getAction())){
//					NNAction.add(past.getCurrentCase().getAction());
//				}
//			}
//		}
//		if (NN.isEmpty()){
//			if (bestRun == null){
//				return null;
//			}
//			return bestRun.getCurrentCase().getAction();
//		}else if (NNAction.size() == 1){
//			
//			return NNAction.get(0);
//		}
//		Action a = stateRetrival(run, NN, time);
//		if (a != null){
//			return a;
//		}
//		return bestRun.getCurrentCase().getAction();
//	}
}
