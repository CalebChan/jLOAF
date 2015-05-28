package org.jLOAF.retrieve.sequence.weight;

public class GaussianWeightFunction implements WeightFunction{

	private double mean;
	private double std;
	
	public GaussianWeightFunction(double mean, double std) {
		this.mean = mean;
		this.std = std;
	}
	
	@Override
	public double getWeightValue(int time) {
		double total = 1 / (this.std * Math.sqrt(2 * Math.PI));
		double e = Math.exp(- Math.pow(time - this.mean, 2) / (2 * Math.pow(this.std, 2)));
		return total * e;
	}
	
	@Override
	public String toString(){
		return "GaussianWeightFunction-" + this.mean + "-" + this.std;
	}

}
