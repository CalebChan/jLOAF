package org.jLOAF.action;

import java.util.ArrayList;
import java.util.List;

import org.jLOAF.inputs.Feature;

public class AtomicAction extends Action {

	private static final long serialVersionUID = 1L;

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
		//this.features.add(f);
		this.feat = f;
	}
	
	public Feature getFeature(){
		return this.feat;
	}
	
	public Feature getFeature(int idx){
//		if(idx > features.size() -1){
//			return null;
//		}else{
//			return features.get(idx);
//		}
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
	
	@Deprecated
	public List<Feature> getFeatures(){
		ArrayList<Feature> f = new ArrayList<Feature>();
		f.add(feat);
		return f;
	}
	
	@Override
	public String toString(){
		String s = "Atomic Action : " + this.name + "\n";
		s += "Feature :\n";
		for (Feature f : this.features){
			s += f.toString() + "\n";
		}
		return s;
	}
}
