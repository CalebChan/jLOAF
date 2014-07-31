package org.jLOAF.util.logger;

import java.util.ArrayList;

import org.jLOAF.util.CaseLoggerConsole;

public class RunLevel extends LoggerLevel{
	
	private ArrayList<LoggerLevel> lvls;

	public RunLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console) {
		super(topLevel, level, time, console);
	}

	@Override
	protected void parseLevel(ArrayList<String> level) {
		lvls = new ArrayList<LoggerLevel>();
		
		ArrayList<String> inputLvl = new ArrayList<String>();
		ArrayList<String> cmpLvl = new ArrayList<String>();
		ArrayList<String> threshLvl = new ArrayList<String>();
		for (String s : level){
			String []l = s.split(" ");
			if (l[1].equals("INPUT")){
				inputLvl.add(s);
			}else if (l[1].equals("CHANGE")){
				cmpLvl.add(s);
			}else if (l[1].equals("THRESHOLD")){
				threshLvl.add(s);
			}else if (l[1].equals(getLevelName())){
				parseLine(s);
			}
		}
		lvls.add(new InputLevel(this, inputLvl, time, console));
		lvls.add(new ChangeLevel(this, cmpLvl, time, console));
		lvls.add(new ThresholdLevel(this, threshLvl, time, console));
	}

	@Override
	public String getCommands() {
		return "RUN";
	}

	@Override
	public String parseCommand(String command) {
		String args[] = command.split(" ");
		if (args[0].equals("down")){
			int index = Integer.parseInt(args[1]);
			console.setLevel(lvls.get(index - 1));
		}else if (args[0].equals("up")){
			console.setLevel(topLevel);
		}else if (args[0].equals("summary")){
			String s = "";
			s += "Run : " + valueMap.get("INDEX") + "\n";
			s += "Children : ";
			for (LoggerLevel ll : lvls){
				s += ll.getLevelName() + " ";
			}
			s += "\n";
			return s;
		}
		return "";
	}

	@Override
	public String getLevelName() {
		return "RUN";
	}

}
