package org.jLOAF.inputs;

import java.io.Serializable;

public class Feature implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private double val;
	
	public Feature(double value){
		this.val = value;
	}
	
	public double getValue(){
		return this.val;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Feature)){
			return false;
		}
		Feature f = (Feature)o;
		return this.val == f.val;
	}
	
	@Override
	public String toString(){
		return "" + this.val;
	}
}
