 package org.jLOAF.util;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jLOAF.util.logger.CaseLoggerParser;

public class CaseLoggerConsole {
	public static void main(String args[]){
		try {
			String filename = "LOG_SmartRandomAgent_1_k_4";
			CaseLoggerConsole console = new CaseLoggerConsole("C:/Users/Daywalker/git/jLOAF-Sandbox-Agent/" + filename + ".txt", filename + ".csv");
			JFrame frame = new JFrame("");
			frame.setSize(500, 500);
			final JTable table = new JTable(console.getTableMode());
	        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	        table.setFillsViewportHeight(true);
	        //table.setEnabled(false);
	        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			frame.add(sp);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//console.saveFile(filename + ".csv");
			//frame.setVisible(true);
			System.out.println("DONE");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to find File");
		}
	}
	
	private BufferedReader br;
	
	private DefaultTableModel model;
		
	private FileWriter writer;
	
	private CaseLoggerConsole(String filename, String saveFile) throws IOException{
		br = new BufferedReader(new FileReader(filename));
		BufferedReader bb = new BufferedReader(new FileReader(filename));
		writer = new FileWriter(saveFile);
		bb.close();
		readFile();
	}
	
	public DefaultTableModel getTableMode(){
		return model;
	}
	
	private void readFile() throws IOException{
		CaseLoggerParser parser = new CaseLoggerParser(writer);
		ArrayList<String> block = new ArrayList<String>();
		String s = br.readLine();
		while (s != null){
			if (s.contains("S G O 0")){
				if (block.size() != 0){
					parser.parseRunBlock(block);
					block.clear();
				}
			}
			block.add(s);
			s = br.readLine();
		}
		parser.parseRunBlock(block);
		
		writer.close();
	}
}
