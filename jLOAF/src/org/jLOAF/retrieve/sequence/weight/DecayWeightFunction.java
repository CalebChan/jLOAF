package org.jLOAF.retrieve.sequence.weight;

public class DecayWeightFunction implements WeightFunction{
	
	private double decayRate;
	
	/**
	 * 
	 * @param decayRate Positive decay rate do not need to add negative
	 */
	public DecayWeightFunction(double decayRate){
		this.decayRate = decayRate;
	}
	
	@Override
	public double getWeightValue(int time) {
		return Math.exp(time * -this.decayRate);
	}
	@Override
	public String toString(){
		return "DecayWeightFunction-" + this.decayRate;
	}
}
