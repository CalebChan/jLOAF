package org.jLOAF.util.logger;

import java.util.ArrayList;

import org.jLOAF.util.CaseLoggerConsole;

public class ChangeLevel extends LoggerLevel{

	public ChangeLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console) {
		super(topLevel, level, time, console);
	}

	@Override
	protected void parseLevel(ArrayList<String> level) {
		for (String s : level){
			String l[] = s.split(" ");
			if (l[1].equals(getLevelName())){
				parseLine(s);
			}
		}
	}

	@Override
	public String getCommands() {
		return "";
	}

	@Override
	public String parseCommand(String command) {
		String args[] = command.split(" ");
		if (args[0].equals("up")){
			console.setLevel(topLevel);
		}else if (args[0].equals("summary")){
			String s = "";
			s += "Change\n";
			s += "Sim Old : " + valueMap.get("SIM_OLD") + "\n";
			s += "Sim New : " + valueMap.get("SIM_NEW") + "\n";
			s += "Run Old : " + valueMap.get("RUN_OLD") + "\n";
			s += "Run New : " + valueMap.get("RUN_NEW") + "\n";
			s += "\n";
			return s;
		}
		return getCommands();
	}

	@Override
	public String getLevelName() {
		return "CHANGE";
	}

}
