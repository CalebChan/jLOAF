package org.jLOAF.retrieve.dbn;

import weka.core.matrix.Matrix;

public class ExpectationMaximization {
	
	private Matrix[][] x;
	private Matrix[][] v;
	
	private int tMax;
	
	private Matrix j[];
	private Matrix k[];
	
	private Matrix skipV[];
	
	private Matrix oldC;
	private Matrix oldM;
	private Matrix oldB;
	private Matrix oldA;
	
	public ExpectationMaximization(int timeLength){
		x = new Matrix[timeLength][timeLength];
		v = new Matrix[timeLength][timeLength];
		
		skipV = new Matrix[timeLength];
		k = new Matrix[timeLength];
		this.tMax = timeLength;
	}
	
	public void calculateEM(int iter, int stopPoint, Matrix mean, Matrix covar, Matrix initC, Matrix initA, Matrix initR, Matrix initQ, Matrix initM, Matrix initB, Matrix inputs[], Matrix observations[]){
		x[0][1] = mean;
		v[0][1] = covar;
		
		for (int i = 0; i < iter; i++){
			fowardProp(initC, initA, initR, initQ, initB, initM, inputs, observations);
			backwardProp(initA, initB, inputs, stopPoint);
			timeSliceProb(initC, initA);
			
			initC = getCnew(observations, inputs);
			initA = getAnew(observations, inputs);
			initR = getRnew(observations, inputs);
			initQ = getQnew(observations, inputs);
			initB = getBnew(observations, inputs);
			initM = getMnew(observations, inputs);
			
			x[0][1] = getNewMean();
			v[0][1] = getNewCovariance();
		}
	}
	
	public Matrix getNewMean(){
		return getXt(1);
	}
	
	public Matrix getNewCovariance(){
		return getVar1();
	}
	
	public void fowardProp(Matrix c, Matrix a, Matrix r, Matrix q, Matrix b, Matrix m, Matrix[] u, Matrix y[]){
		oldA = a;
		oldB = b;
		oldC = c;
		oldM = m;
		
		for (int i = 1; i < this.tMax; i++){
			if (i != 1){ 
				this.x[i - 1][i] = a.times(this.x[i - 1][i - 1]).plus(b.times(u[i]));
				this.v[i - 1][i] = a.times(this.v[i - 1][i - 1]).times(a.transpose()).plus(q);
			}
			
			Matrix tmp = c.times(this.v[i - 1][i]).times(c.transpose()).plus(r);
			tmp.inverse();
			k[i] = this.v[i - 1][i].times(c.transpose()).times(tmp);

			tmp = y[i].minus(c.times(this.x[i - 1][i])).minus(m.times(u[i]));
			this.x[i][i] = this.x[i - 1][i].plus(k[i].times(tmp));
			
			tmp = k[i].times(c).times(this.v[i - 1][i]);
			this.v[i][i] = this.v[i - 1][i].minus(tmp);
		}
	}
	
	private void backwardProp(Matrix a, Matrix b, Matrix u[], int stopPoint){
		j = new Matrix[this.tMax];
		for (int i = this.tMax - 1; i > stopPoint; i--){
			j[i - 1] = this.v[i - 1][i - 1].times(a.transpose()).times(this.v[i - 1][i].inverse());
			
			Matrix tmp = j[i - 1].times(this.x[this.tMax - 1][i].minus(a.times(this.x[i - 1][i - 1])).minus(b.times(u[i])));
			this.x[this.tMax - 1][i - 1] = this.x[i - 1][i - 1].plus(tmp);
			
			tmp = j[i - 1].times(this.v[this.tMax - 1][i].minus(this.v[i - 1][i])).times(j[i - 1].transpose());
			this.v[this.tMax - 1][i - 1] = this.v[i - 1][i - 1].plus(tmp);
		}
	}
	
	private void timeSliceProb(Matrix c, Matrix a){
		skipV[this.tMax - 2] = Matrix.identity(c.getRowDimension(), c.getColumnDimension());
		skipV[this.tMax - 2] = skipV[this.tMax - 2].minus(k[this.tMax - 1].times(c)).times(a).times(v[this.tMax - 2][this.tMax - 2]);
		for (int i = this.tMax - 1; i > 1; i--){
			Matrix tmp = j[i - 1].times(v[this.tMax - 1][i - 1].minus(a.times(v[i - 1][i - 1]))).times(j[i - 2]);
			skipV[i - 2] = v[i - 1][i - 1].times(j[i - 2].transpose()).plus(tmp);
		}
	}
	
	public Matrix getCnew(Matrix ob[], Matrix in[]){
		Matrix probTotal = getXtXt(1);
		Matrix total = getXt(1).times(ob[1].transpose()).minus(oldM.times(in[1]).times(getXt(1).transpose()));
		for (int i  = 2; i < this.tMax; i++){
			total = total.plus(getXt(i).times(ob[i].transpose()).minus(oldM.times(in[i]).times(getXt(i).transpose())));
			probTotal = probTotal.plus(getXtXt(i));
		}
		return total.times(probTotal.times(1.0 / probTotal.det()).inverse());
	}
	
	public Matrix getAnew(Matrix ob[], Matrix in[]){
		Matrix probTotal = getXtXt(1);
		Matrix total = getXtXt_1(2).minus(oldB.times(in[2]).times(getXt(1).transpose()));
		for (int i  = 3; i < this.tMax; i++){
			total = total.plus(getXtXt_1(i).minus(oldB.times(in[i]).times(getXt(i - 1).transpose())));
			probTotal = probTotal.plus(getXtXt(i - 1));
		}
		return total.times(probTotal.times(1.0 / probTotal.det()).inverse());
	}
	
	public Matrix getMnew(Matrix ob[], Matrix in[]){
		Matrix probTotal = in[1].times(in[1].transpose());
		Matrix total = ob[1].times(in[1].transpose()).minus(oldC.times(getXt(1).times(in[1].transpose())));
		for (int i  = 2; i < this.tMax; i++){
			total = total.plus(ob[1].times(in[i].transpose()).minus(oldC.times(getXt(i).times(in[i].transpose()))));
			probTotal = probTotal.plus(in[i].times(in[i].transpose()));
		}
		return total.times(probTotal.times(1.0 / probTotal.det()).inverse());
	}
	
	public Matrix getBnew(Matrix ob[], Matrix in[]){
		Matrix probTotal = in[1].times(in[1].transpose());
		Matrix total = getXt(1).times(in[1].transpose()).minus(oldA.times(getXt(1).times(in[1].transpose())));
		for (int i  = 2; i < this.tMax; i++){
			total = total.plus(getXt(i).times(in[i].transpose()).minus(oldA.times(getXt(i).times(in[i].transpose()))));
			probTotal = probTotal.plus(in[i].times(in[i].transpose()));
		}
		return total.times(probTotal.times(1.0 / probTotal.det()).inverse());
	}
	
	public Matrix getRnew(Matrix ob[], Matrix in[]){
		Matrix total = ob[1].times(ob[1].transpose());
		total = total.minus(getCnew(ob, in).times(getXt(1)).times(ob[1].transpose()));
		total = total.minus(getMnew(ob, in).times(in[1]).times(ob[1].transpose()));
		Matrix tmp;
		for (int i  = 2; i < this.tMax; i++){
			tmp = ob[i].times(ob[i].transpose());
			tmp = tmp.minus(getCnew(ob, in).times(getXt(i)).times(ob[i].transpose()));
			tmp = tmp.minus(getMnew(ob, in).times(in[i]).times(ob[i].transpose()));
			total = total.plus(tmp);
		}
		return total.times(1.0 / this.tMax);
	}
	
	public Matrix getQnew(Matrix ob[], Matrix in[]){
		Matrix total = getXtXt(2);
		total.minusEquals(getAnew(ob, in).times(getXtXt_1(2)));
		total.minusEquals(getBnew(ob, in).times(in[2]).times(getXt(2).transpose()));
		Matrix tmp;
		for (int i  = 3; i < this.tMax; i++){
			tmp = getXtXt(2);
			tmp.minusEquals(getAnew(ob, in).times(getXtXt_1(2)));
			tmp.minusEquals(getBnew(ob, in).times(in[2]).times(getXt(2).transpose()));
			total.plusEquals(tmp);
		}
		return total.times(1.0 / (this.tMax - 1));
	}
	
	public Matrix getVar1(){
		return getXtXt(1).minus(getXt(1).times(getXt(1).transpose()));
	}
	
	public Matrix getXt(int t){
		return this.x[this.tMax - 1][t];
	}
	
	public Matrix getXtXt(int t){
		return this.x[this.tMax - 1][t].times(this.x[this.tMax - 1][t].transpose()).plus(this.v[this.tMax - 1][t]);
	}
	
	public Matrix getXtXt_1(int t){
		return skipV[t - 1].plus(x[this.tMax][t].times(x[this.tMax][t - 1].transpose()));
	}
	
}
