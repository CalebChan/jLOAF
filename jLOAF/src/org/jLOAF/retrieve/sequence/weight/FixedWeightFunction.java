package org.jLOAF.retrieve.sequence.weight;

public class FixedWeightFunction implements WeightFunction {

	private double weight;
	
	public FixedWeightFunction(double weight){
		this.weight = weight;
	}
	
	@Override
	public double getWeightValue(int time) {
		return this.weight;
	}

}
