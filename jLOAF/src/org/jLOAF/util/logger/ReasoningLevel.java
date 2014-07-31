package org.jLOAF.util.logger;

import java.util.ArrayList;

import org.jLOAF.util.CaseLoggerConsole;

public class ReasoningLevel extends LoggerLevel {

	private ArrayList<RunLevel> childLevels;
	
	public ReasoningLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console) {
		super(topLevel, level, time, console);
	}
	
	public String parseCommand(String command){
		String args[] = command.split(" ");
		if (args[0].equals("summary")){
			String s = "";
			s += ((isStateLevel)?"State":"Action") + " -> Time : " + time + "\n";
			s += "Sim : " + valueMap.get("BEST_SIM") + "\n";
			s += "NN Size : " + valueMap.get("NN") + "\n";
			s += "NN Action Size : " + valueMap.get("NNACTION") + "\n";
			s += "Runs : " + childLevels.size();
			s += "\n";
			return s;
		}else if (args[0].equals("type")){
			return this.getLevelName() + "\n";
		}else if (args[0].equals("overview")){
			return ((isStateLevel)?"State":"Action") + " -> Time : " + time + " Sim : " + valueMap.get("BEST_SIM") + "\n";
		}else if (args[0].equals("down")){
			int index = Integer.parseInt(args[1]);
			console.setLevel(childLevels.get(index - 1));
		}else if (args[0].equals("up")){
			console.setLevel(this.topLevel);
		}
		return getCommands();
	}
	
	protected void parseLevel(ArrayList<String> level){
		childLevels = new ArrayList<RunLevel>();
		ArrayList<String> childLvl = new ArrayList<String>();
		for (String s : level){
			String []l = s.split(" ");
			if (l[1].equals(getLevelName())){
				parseLine(s);
				if (l[2].equals("TIME")){
					this.time = Integer.parseInt(l[3]);
				}
			}else{
				if (l[1].equals("RUN") && !childLvl.isEmpty()){
					childLevels.add(new RunLevel(this, childLvl, time, console));
					childLvl.clear();
				}
				childLvl.add(s);
			}
		}
		childLevels.add(new RunLevel(this, childLvl, time, console));
	}
	
	public int getTime(){
		return time;
	}
	
	@Override
	public String getCommands() {
		return "";
	}

	@Override
	public String getLevelName() {
		return "GLOBAL";
	}

}
