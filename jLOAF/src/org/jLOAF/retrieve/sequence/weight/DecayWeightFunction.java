package org.jLOAF.retrieve.sequence.weight;

public class DecayWeightFunction implements WeightFunction{
	
	private double decayRate;
	
	public DecayWeightFunction(double decayRate){
		this.decayRate = decayRate;
	}
	
	@Override
	public double getWeightValue(int time) {
		return Math.exp(time * this.decayRate);
	}
	
	public String toString(){
		return "DecayWeightFunction-" + this.decayRate;
	}
}
