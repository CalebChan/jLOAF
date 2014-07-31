package org.jLOAF.util.logger;

import java.util.ArrayList;

import org.jLOAF.util.CaseLoggerConsole;

public class GlobalLevel extends LoggerLevel{

	private ArrayList<ArrayList<ReasoningLevel>> levels;
	private int index;
	
	public GlobalLevel(LoggerLevel topLevel, ArrayList<String> level, int time, CaseLoggerConsole console) {
		super(topLevel, level, time, console);
		index = -1;
	}

	@Override
	protected void parseLevel(ArrayList<String> level) {
		ArrayList<ReasoningLevel> lvls = new ArrayList<ReasoningLevel>();
		
		boolean isState = true;
		ArrayList<String> lines = new ArrayList<String>();
		int time = 1;
		for(String s : level){
			String l[] = s.split(" ");
			if (isState && l[0].equals("ACTION")){
				lvls.add(new ReasoningLevel(this, lines, time, console));
				lines.clear();
				lines.add(s);
				isState = !isState;
				time++;
			}else if (!isState && l[0].equals("STATE")){
				lvls.add(new ReasoningLevel(this, lines, time, console));
				lines.clear();
				lines.add(s);
				isState = !isState;
				time++;
			}else{
				lines.add(s);
			}
		}
		lvls.add(new ReasoningLevel(this, lines, time, console));
		
		levels = new ArrayList<ArrayList<ReasoningLevel>>();
		ArrayList<ReasoningLevel> rl = new ArrayList<ReasoningLevel>();
		for (int i = 0; i < lvls.size(); i++){
			if (lvls.get(i).getTime() == 0 && !rl.isEmpty()){
				levels.add(rl);
				rl = new ArrayList<ReasoningLevel>();
			}
			rl.add(lvls.get(i));
		}
	}

	@Override
	public String getCommands() {
		return "";
	}

	@Override
	public String parseCommand(String command) {
		String args[] = command.split(" ");
		if (args[0].equals("down")){
			if (index == -1){
				int ii = Integer.parseInt(args[1]);
				index = ii - 1;
			}else{
				int ii = Integer.parseInt(args[1]);
				console.setLevel(levels.get(index).get(ii - 1));
			}
		}else if (args[0].equals("summary")){
			if (index == -1){
				String s = "";
				for (int i = 0; i < levels.size(); i++){
					s += "Test # : " + (i + 1) + " Total Time : " + levels.get(i).size() + "\n";
				}
				return s;
			}else{
				String s = "";
				for (int i = 0; i < levels.get(index).size(); i++){
					s += (i + 1) + " : " + levels.get(index).get(i).parseCommand("overview");
				}
				s += "\n";
				return s;
			}
		}else if (args[0].equals("up")){
			if (index != -1){
				index = -1;
			}
		}
		return getCommands();
	}

	@Override
	public String getLevelName() {
		return null;
	}

}
