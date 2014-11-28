package org.jLOAF.retrieve.sequence.weight;


public class TimeVaryingWeightFunction implements WeightFunction {

	private double weights[];
	
	public TimeVaryingWeightFunction(double weights[]){
		this.weights = weights;
	}
	
	@Override
	public double getWeightValue(int time) {
		if (time >= weights.length){
			return weights[weights.length - 1];
		}
		return weights[time];
	}

}
