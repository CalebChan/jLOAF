package org.jLOAF.util.logger;

import java.util.ArrayList;

import org.jLOAF.util.CaseLoggerConsole;

public class InputLevel extends LoggerLevel {

	public InputLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console) {
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
			s += "Input\n";
			s += "Sim : " + valueMap.get("SIM") + "\n";
			s += "Past Input : " + valueMap.get("PAST") + "\n";
			s += "New Input : " + valueMap.get("RUN") + "\n";
			s += "\n";
			return s;
		}
		return getCommands();
	}

	@Override
	public String getLevelName() {
		return "INPUT";
	}

}
