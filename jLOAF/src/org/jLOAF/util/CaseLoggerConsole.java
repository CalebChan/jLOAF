package org.jLOAF.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jLOAF.util.logger.GlobalLevel;
import org.jLOAF.util.logger.LoggerLevel;

public class CaseLoggerConsole {
	public static void main(String args[]){
		try {
			CaseLoggerConsole console = new CaseLoggerConsole("LOG_1.xml");
			Scanner s = new Scanner(System.in);
			boolean isRunning = true;
			while (isRunning){
				String line = s.nextLine();
				if (line.equals("exit")){
					isRunning = false;
				}else{
					console.sendCommand(line);
				}
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to find File");
		}
	}
	
	private BufferedReader br;
	
	private LoggerLevel level;
	
	private CaseLoggerConsole(String filename) throws IOException{
		br = new BufferedReader(new FileReader(filename));
		readFile();
	}
	
	public void setLevel(LoggerLevel level){
		this.level = level;
	}
	
	public void sendCommand(String command){
		System.out.println(level.parseCommand(command));
	}
	
	private void readFile() throws IOException{
		String line = br.readLine();
		ArrayList<String> lines = new ArrayList<String>();
		while(line != null){
			
			if (line.contains("INFO:")){
				lines.add(line.substring(("INFO: ").length()));
			}
			line = br.readLine();
		}
		level = new GlobalLevel(null, lines, -1, this);
	}
}
