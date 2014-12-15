package org.jLOAF.retrieve.sequence.weight;

public class LinearWeightFunction implements WeightFunction {

	private double weight;
	
	public LinearWeightFunction(double weight){
		this.weight = weight;
	}
	
	@Override
	public double getWeightValue(int time) {
		return Math.max(0, 1 - this.weight * time);
	}

	public String toString(){
		return "LinearWeightFunction-" + this.weight;
	}
}
