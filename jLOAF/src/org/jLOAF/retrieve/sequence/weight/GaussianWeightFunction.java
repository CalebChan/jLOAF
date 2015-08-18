package org.jLOAF.retrieve.sequence.weight;

public class GaussianWeightFunction implements WeightFunction{

	private double mean;
	private double std;
	
	/**
	 * 
	 * @param mean The mean
	 * @param std The variance
	 */
	public GaussianWeightFunction(double mean, double std) {
		this.mean = mean;
		this.std = std;
	}
	
	@Override
	public double getWeightValue(int time) {
		double total = Math.sqrt(this.std * 2 * Math.PI);
		double e = Math.exp(-Math.pow(time - this.mean, 2) / (2 * this.std));
		return e / (1.0 * total);
	}
	
	@Override
	public String toString(){
		return "GaussianWeightFunction-" + this.mean + "-" + this.std;
	}

}
