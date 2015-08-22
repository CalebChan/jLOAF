package org.jLOAF.action;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.inputs.Feature;
import org.jLOAF.sim.SimilarityActionMetricStrategy;

public class AtomicAction extends Action {

	private static final long serialVersionUID = 1L;

	private static SimilarityActionMetricStrategy s_simstrategy;
	
	protected Feature feat;
	
	List<Feature> features;

	public AtomicAction(String name) {
		super(name);
		features = new ArrayList<Feature>();
	}

	@Deprecated
	public int getNumFeatures(){
		return this.features.size();
	}
	
	public void addFeature(Feature f){
		this.feat = f;
	}
	
	public Feature getFeature(){
		return this.feat;
	}
	
	@Deprecated
	public Feature getFeature(int idx){
		return getFeature();
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof AtomicAction)){
			return false;
		}
		AtomicAction a = (AtomicAction)o;
		//return this.name.equals(a.name) && this.features.equals(features);
		return this.name.equals(a.name) && this.feat.equals(a.feat);
	}
	
	@Override
	public String toString(){
		String s = "Atomic Action : " + this.name + " ";
		s += "Feature : " + feat.toString();
		return s;
	}
	
	@Override
	public String getSimpleString(){
		return feat.toString();
	}
	
	private static double similarity(Action atomicInput, Action i) {
		return AtomicAction.s_simstrategy.similarity(atomicInput, i);
	}

	public static boolean isClassStrategySet(){
		if(AtomicAction.s_simstrategy == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static void setClassStrategy(SimilarityActionMetricStrategy s){
		AtomicAction.s_simstrategy = s;
	}
	

	@Override
	public double similarity(Action i) {
		//See if the user has defined similarity for each specific input, for all inputs
		//  of a specific type, of defered to superclass
		if(this.simStrategy != null){
			return simStrategy.similarity(this, i);
		}else if(AtomicAction.isClassStrategySet()){
			return AtomicAction.similarity(this, i);
		}else{
			//normally we would defer to superclass, but super
			// is abstract
			System.err.println("Problem. In AtomicAction no similarity metric set!");
			return 0;
		}
	}
}
