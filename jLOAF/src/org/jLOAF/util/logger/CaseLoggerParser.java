package org.jLOAF.util.logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CaseLoggerParser {
	
	private enum NAMES{
		TIME,
		RUN,
		TYPE,
		BEST_INPUT,
		BEST_ACTION,
		INPUT_PAST,
		INPUT_CUR,
		INPUT_SIM,
		CHANGE_SIM_OLD,
		CHANGE_SIM_NEW,
		CHANGE_RUN_OLD,
		CHANGE_RUN_NEW,
		THRESHOLD_PASSED,
		THRESHOLD_ACTION,
		BEST_SIM,
		NN,
		NNACTION,
		CONSENSUS,
		FINAL_ACTION,
		
		LAST_NAME
	};
	
	private static String []COLUMN;
	static{
		COLUMN = new String[NAMES.LAST_NAME.ordinal()];
		for (int i = 0; i < COLUMN.length; i++){
			COLUMN[i] = NAMES.values()[i].name();
		}
	}
	
	private DefaultTableModel parseRunBlock(ArrayList<String> lines){
		ArrayList<String> blocks = new ArrayList<String>();
		DefaultTableModel model = new DefaultTableModel(COLUMN, 1);
		boolean isState = true;
		for (String s : lines){
			String l[] = s.split(" ");
			if (isState && l[0].equals("ACTION")){
				DefaultTableModel m = new DefaultTableModel(parseBlock(blocks), COLUMN);
				@SuppressWarnings("unchecked")
				Iterator<Vector<String>> i = m.getDataVector().iterator();
				while(i.hasNext()){
					model.addRow(i.next());
				}
				blocks.clear();
				blocks.add(s);
				isState = !isState;
			}else if (!isState && l[0].equals("STATE")){
				DefaultTableModel m = new DefaultTableModel(parseBlock(blocks), COLUMN);
				@SuppressWarnings("unchecked")
				Iterator<Vector<String>> i = m.getDataVector().iterator();
				while(i.hasNext()){
					model.addRow(i.next());
				}
				blocks.clear();
				blocks.add(s);
				isState = !isState;
			}else{
				blocks.add(s);
			}
		}
		DefaultTableModel m = new DefaultTableModel(parseBlock(blocks), COLUMN);
		@SuppressWarnings("unchecked")
		Iterator<Vector<String>> i = m.getDataVector().iterator();
		while(i.hasNext()){
			model.addRow(i.next());
		}
		return model;
	}

	public DefaultTableModel parseLogger(ArrayList<String> lines){
		ArrayList<String> block = new ArrayList<String>();
		DefaultTableModel model = new DefaultTableModel(COLUMN, 1);
		for (String s : lines){
			if (s.contains("STATE GLOBAL TIME 0")){
				if (block.size() != 0){
					DefaultTableModel m = parseRunBlock(block);
					@SuppressWarnings("unchecked")
					Iterator<Vector<String>> i = m.getDataVector().iterator();
					while(i.hasNext()){
						model.addRow(i.next());
					}
					block.clear();
				}
			}
			block.add(s);
		}
		return model;
	}
	
	private String[][] parseBlock(ArrayList<String> blocks){
		int iter = 0;
		for (String b : blocks){
			String l[] = b.split(" ");
			if (l[1].equals("RUN")){
				iter++;
			}
		}
		String grid[][] = new String[iter][NAMES.LAST_NAME.ordinal()];
		int run = -1;
		for (String b : blocks){
			String l[] = b.split(" ");
			if (l[1].equals("GLOBAL")){
				int index = -1;
				if (l[2].equals("TIME")){
					index = NAMES.TIME.ordinal();
				}else if (l[2].equals("BEST_SIM")){
					index = NAMES.BEST_SIM.ordinal();
				}else if (l[2].equals("NN")){
					index = NAMES.NN.ordinal();
				}else if (l[2].equals("NNACTION")){
					index = NAMES.NNACTION.ordinal();
				}else if (l[2].equals("INPUT")){
					index = NAMES.BEST_INPUT.ordinal();
				}else if (l[2].equals("ACTION")){
					index = NAMES.BEST_ACTION.ordinal();
				}else if (l[2].equals("CONSENSUS")){
					index = NAMES.CONSENSUS.ordinal();
				}else if (l[2].equals("FINAL_ACTION")){
					index = NAMES.FINAL_ACTION.ordinal();
				}				
				for (int i = 0; i < iter; i++){
					grid[i][index] = l[3];
					grid[i][NAMES.TYPE.ordinal()] = l[0];
				}
			}else if (l[1].equals("RUN")){
				run = Integer.parseInt(l[3]);
				grid[run][NAMES.RUN.ordinal()] = run + "";
			}else if (l[1].equals("INPUT")){
				if (l[2].equals("PAST")){
					grid[run][NAMES.INPUT_PAST.ordinal()] = l[3];
				}else if (l[2].equals("RUN")){
					grid[run][NAMES.INPUT_CUR.ordinal()] = l[3];
				}else if (l[2].equals("SIM")){
					grid[run][NAMES.INPUT_SIM.ordinal()] = l[3];
				}
			}else if (l[1].equals("CHANGE")){
				if (l[2].equals("SIM_OLD")){
					grid[run][NAMES.CHANGE_SIM_OLD.ordinal()] = l[3];
					grid[run][NAMES.CHANGE_SIM_NEW.ordinal()] = l[5];
				}else if (l[2].equals("RUN_OLD")){
					grid[run][NAMES.CHANGE_RUN_OLD.ordinal()] = l[3];
					grid[run][NAMES.CHANGE_RUN_NEW.ordinal()] = l[5];
				}
			}else if (l[1].equals("THRESHOLD")){
				if (l[2].equals("PASSED")){
					grid[run][NAMES.THRESHOLD_PASSED.ordinal()] = l[3];
				}else if (l[2].equals("ACTION")){
					grid[run][NAMES.THRESHOLD_ACTION.ordinal()] = l[3];
				}
			}
			
		}
		return grid;
	}

}
