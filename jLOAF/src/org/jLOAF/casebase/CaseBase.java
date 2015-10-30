package org.jLOAF.casebase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CaseBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Collection<Case> cb;
	
	private List<ComplexCase> runs;
	
	public CaseBase(){
		this.cb = new ArrayList<Case>();
		this.runs = new ArrayList<ComplexCase>();
	}
	
	public Collection<Case> getCases(){
		return this.cb;
	}
	
	public List<ComplexCase> getRuns(){
		return this.runs;
	}
	
	/**
	 * When complex cases are added they are added from current to past
	 * @param c
	 */
	public void add(Case c){
		if (c instanceof AtomicCase){
			this.cb.add(c);
			this.runs.add(new ComplexCase((AtomicCase) c));
		}else{
			ComplexCase cc = (ComplexCase)c;
			this.cb.add(cc.getCurrentCase());
			this.cb.addAll(cc.getPastCases());
			this.runs.add(cc);
		}
	}

	public int getSize(){
		return this.cb.size();
	}

	public static CaseBase load(String filename) {
		//test the parameters
		if(filename == null){
			throw new IllegalArgumentException("A null value was given for the file name");
		}
				
		try{
			//open the file streams
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fileIn);
					
			Object o = ois.readObject();
					
			//make sure we read a CaseBase
			if( !(o instanceof CaseBase)){
				ois.close();
				return null;
			}
			ois.close();
			return (CaseBase)o;
		}catch(Exception e){
			//if there was a file problem we return null
			System.out.println("Error loading CaseBase:" + e.getMessage());
			return null;
		}
			
	}

	public static void save(CaseBase casebase, String filename) {
		//test the parameters
		if(filename == null || casebase == null){
			throw new IllegalArgumentException("A null value was given for the file name");
		}
		
		try{
			//create the output streams
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			    
			//write the case base
			oos.writeObject(casebase);
				
			//close the output streams
			oos.close();
			fileOut.close();
		}catch(IOException e){
			System.out.println("Error saving CaseBase:" + e.toString());
		}
	
	}
}
