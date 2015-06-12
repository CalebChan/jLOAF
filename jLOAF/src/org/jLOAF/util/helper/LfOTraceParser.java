package org.jLOAF.util.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jLOAF.action.AtomicAction;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Feature;
import org.jLOAF.tools.CaseBaseIO;

public class LfOTraceParser {

	enum MovementAction{
		REMOVE_OBSTACLE,
		
		STAND,
		MOVE_UP,
		MOVE_RIGHT,
		MOVE_LEFT,
		MOVE_DOWN,
		
		TURN_RIGHT,
		TURN_LEFT,
		
		REVERSE
	};
	
	enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST,
	};
		
	
	public Case parseLine(String[] tokens, Case c) {
		ComplexInput ci = new ComplexInput("LfoInput");
		int index = 0;
		for (Direction d : Direction.values()){
			int type = (int) Double.parseDouble(tokens[index]);
			index++;
			int dist = (int) Double.parseDouble(tokens[index]);
			index++;
			AtomicInput ait = new AtomicInput(d + "-TYPE", new Feature(type));
			AtomicInput aid = new AtomicInput(d + "-DIST", new Feature(dist));
			ci.add(ait);
			ci.add(aid);
		}
		int actionIndex = (int) Double.parseDouble(tokens[index]);
		MovementAction action = MovementAction.values()[actionIndex];
		AtomicAction a = new AtomicAction(action.name());
		a.addFeature(new Feature(actionIndex));
		
		return new Case(ci, a, c); 
	}
	
	protected String filename;
	protected CaseBase casebase;
	
	public LfOTraceParser(String filename){
		this.filename = filename;
		this.casebase = new CaseBase();
	}
	
	public boolean parseFile(){
		BufferedReader ip = null;
		try {
			ip  = new BufferedReader (new FileReader(filename));
			String line = ip.readLine();
			Case c = null;
			while(line != null){
				if (line.isEmpty()){
					continue;
				}

				String tokens[] = line.split(" ");
				
				c = parseLine(tokens, c);
				casebase.add(c);
				line = ip.readLine();
			}
			if (ip != null){
				ip.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void saveCaseBase(String filename){
		try {
			CaseBaseIO.saveCaseBase(casebase, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
	

