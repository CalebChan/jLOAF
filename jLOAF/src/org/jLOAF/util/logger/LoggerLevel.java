package org.jLOAF.util.logger;

import java.util.ArrayList;
import java.util.HashMap;

import org.jLOAF.util.CaseLoggerConsole;

public abstract class LoggerLevel {
	
	protected boolean isStateLevel;
	protected HashMap<String, String> valueMap;
	
	protected LoggerLevel topLevel;
	protected int time;
	
	protected CaseLoggerConsole console;
	
	public LoggerLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console){
		this.topLevel = topLevel;
		this.time = time;
		this.console = console;
		
		valueMap = new HashMap<String, String>();
		parseLevel(level);
		
	}
	
	protected void parseLine(String level){
		String []l = level.split(" ");
		if (l[0].equals("STATE")){
			isStateLevel = true;
		}else{
			isStateLevel = false;
		}
		for (int i = 2; i + 1 < l.length; i += 2){
			valueMap.put(l[i], l[i + 1]);
		}
	}
	
	protected abstract void parseLevel(ArrayList<String> level);
	public abstract String getCommands();
	public abstract String parseCommand(String command);
	public abstract String getLevelName();
}
