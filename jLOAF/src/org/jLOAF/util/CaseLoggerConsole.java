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
			CaseLoggerConsole console = new CaseLoggerConsole("C:/Users/Caleb/git/jLOAF-Sandbox-Agent/LOG_Random_6.txt");
			JFrame frame = new JFrame("");
			frame.setSize(500, 500);
			final JTable table = new JTable(console.getTableMode());
	        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	        table.setFillsViewportHeight(true);
	        table.setEnabled(false);
			frame.add(new JScrollPane(table));
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			console.saveFile("SAVE_CSV.csv");
			frame.setVisible(true);
			System.out.println("DONE");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to find File");
		}
	}
	
	private BufferedReader br;
	
	private DefaultTableModel model;
	
	private CaseLoggerConsole(String filename) throws IOException{
		br = new BufferedReader(new FileReader(filename));
		readFile();
	}
	
	public DefaultTableModel getTableMode(){
		return model;
	}
	
	public void saveFile(String filename) throws IOException{
		FileWriter writer = new FileWriter(filename);
		for (int j = 0; j < model.getColumnCount(); j++){
			writer.write(model.getColumnName(j) + ",");
		}
		for (int i = 0; i < model.getRowCount(); i++){
			for (int j = 0; j < model.getColumnCount(); j++){
				if (model.getValueAt(i, j) == null){
					writer.write(",");
				}else{
					writer.write(model.getValueAt(i, j).toString() + ",");
				}
				
			}
			writer.write("\n");
		}
		writer.close();
	}
	
	private void readFile() throws IOException{
		String line = br.readLine();
		ArrayList<String> lines = new ArrayList<String>();
		while(line != null){
			lines.add(line);
			line = br.readLine();
		}
		
		CaseLoggerParser parser = new CaseLoggerParser();
		model = parser.parseLogger(lines);
	}
}
