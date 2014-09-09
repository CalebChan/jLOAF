package org.jLOAF.util.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CaseLoggerParser {
	
	private enum NAMES{
		TIME,
		OFFSET,
		RUN,
		TYPE,
		BEST_INPUT,
		BEST_ACTION,
		INPUT_PAST,
		
		INPUT_PAST_NORTH_DIST,
		INPUT_PAST_NORTH_TYPE,
		INPUT_PAST_EAST_DIST,
		INPUT_PAST_EAST_TYPE,
		INPUT_PAST_SOUTH_DIST,
		INPUT_PAST_SOUTH_TYPE,
		INPUT_PAST_WEST_DIST,
		INPUT_PAST_WEST_TYPE,
		
		INPUT_CUR,
		
		INPUT_CUR_NORTH_DIST,
		INPUT_CUR_NORTH_TYPE,
		INPUT_CUR_EAST_DIST,
		INPUT_CUR_EAST_TYPE,
		INPUT_CUR_SOUTH_DIST,
		INPUT_CUR_SOUTH_TYPE,
		INPUT_CUR_WEST_DIST,
		INPUT_CUR_WEST_TYPE,
		
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
	
	public static String []COLUMN;
	static{
		COLUMN = new String[NAMES.LAST_NAME.ordinal()];
		for (int i = 0; i < COLUMN.length; i++){
			COLUMN[i] = NAMES.values()[i].name();
		}
	}
	
	public static int getTableSize(){
		return COLUMN.length;
	}
	
	private FileWriter writer;
	
	public CaseLoggerParser(FileWriter writer) throws IOException{
		
		this.writer = writer;
		for (int j = 0; j < COLUMN.length - 1; j++){
			this.writer.write(COLUMN[j] + ",");
		}
		this.writer.write(COLUMN[COLUMN.length - 1] + "\n");
	}
	
	private void mergeArray(String array[][]) throws IOException{

		for (int i = 0; i < array.length; i++){
			for (int j = 0; j < array[0].length - 1; j++){
				if (array[i][j] == null){
					writer.write(",");
				}else{
					writer.write(array[i][j] + ",");
				}
			}
			if (array[i][array[0].length - 1] == null){
				writer.write("\n");
			}else{
				writer.write(array[i][array[0].length - 1] + "\n");
			}
		}
	}
	
	
	
	public void parseRunBlock(ArrayList<String> lines) throws IOException{
		ArrayList<String> blocks = new ArrayList<String>();
		boolean isState = true;
		for (String s : lines){
			String l[] = s.split(" ");
			if (isState && l[0].equals("A")){
				mergeArray(parseBlock(blocks));
				blocks.clear();
				isState = !isState;
			}else if (!isState && l[0].equals("S")){
				mergeArray(parseBlock(blocks));
				blocks.clear();
				isState = !isState;
			}
			blocks.add(s);
		}
		mergeArray(parseBlock(blocks));
	}
	
	private String[][] parseBlock(ArrayList<String> blocks){
		int iter = 0;
		for (String b : blocks){
			String l[] = b.split(" ");
			if (l[1].equals("R")){
				iter++;
			}
		}
		String grid[][] = new String[iter][NAMES.LAST_NAME.ordinal()];
		int run = -1;
		for (String b : blocks){
			String l[] = b.split(" ");
			if (l[1].equals("G")){
				int index = -1;
				if (l[2].equals("T")){
					index = NAMES.TIME.ordinal();
				}else if (l[2].equals("O")){
					index = NAMES.OFFSET.ordinal();
				}else if (l[2].equals("BS")){
					index = NAMES.BEST_SIM.ordinal();
				}else if (l[2].equals("NN")){
					index = NAMES.NN.ordinal();
				}else if (l[2].equals("NNA")){
					index = NAMES.NNACTION.ordinal();
				}else if (l[2].equals("I")){
					index = NAMES.BEST_INPUT.ordinal();
				}else if (l[2].equals("A")){
					index = NAMES.BEST_ACTION.ordinal();
				}else if (l[2].equals("C")){
					index = NAMES.CONSENSUS.ordinal();
				}else if (l[2].equals("FA")){
					index = NAMES.FINAL_ACTION.ordinal();
				}		
				for (int i = 0; i < iter; i++){
					grid[i][index] = l[3];
					grid[i][NAMES.TYPE.ordinal()] = l[0];
				}
			}else if (l[1].equals("R")){
				run = Integer.parseInt(l[3]);
				grid[run][NAMES.RUN.ordinal()] = run + "";
			}else if (l[1].equals("I")){
				if (l[2].equals("P")){
					grid[run][NAMES.INPUT_PAST.ordinal()] = l[3];
					if (!l[3].equals("-") && l[0].equals("S")){
						buildInput(l, 5, b, true, grid[run]);
					}
				}else if (l[2].equals("R")){
					grid[run][NAMES.INPUT_CUR.ordinal()] = l[3];
					if (!l[3].equals("-") && l[0].equals("S")){
						buildInput(l, 5, b, false, grid[run]);
					}
				}else if (l[2].equals("S")){
					grid[run][NAMES.INPUT_SIM.ordinal()] = l[3];
				}
			}else if (l[1].equals("C")){
				if (l[2].equals("SO")){
					grid[run][NAMES.CHANGE_SIM_OLD.ordinal()] = l[3];
					grid[run][NAMES.CHANGE_SIM_NEW.ordinal()] = l[5];
				}else if (l[2].equals("SN")){
					grid[run][NAMES.CHANGE_RUN_OLD.ordinal()] = l[3];
					grid[run][NAMES.CHANGE_RUN_NEW.ordinal()] = l[5];
				}
			}else if (l[1].equals("T")){
				if (l[2].equals("P")){
					grid[run][NAMES.THRESHOLD_PASSED.ordinal()] = l[3];
				}else if (l[2].equals("A")){
					grid[run][NAMES.THRESHOLD_ACTION.ordinal()] = l[3];
				}
			}
			
		}
		return grid;
	}
	
	private void buildInput(String lineArray[], int index, String line, boolean isPast, String grid[]){
		int offset = index;
		for (int i = 0; i < index; i++){
			offset += lineArray[i].length();
		}
		String inputLine[] = line.substring(offset).split(",");
		for (String s : inputLine){
			String map[] = s.split(":");
			if (map[0].trim().equals("NORTH-DIST")){
				grid[(isPast)? NAMES.INPUT_PAST_NORTH_DIST.ordinal(): NAMES.INPUT_CUR_NORTH_DIST.ordinal()] = map[1];
			}else if (map[0].trim().equals("NORTH-TYPE")){
				grid[(isPast)? NAMES.INPUT_PAST_NORTH_TYPE.ordinal(): NAMES.INPUT_CUR_NORTH_TYPE.ordinal()] = map[1];
			}else if (map[0].trim().equals("EAST-DIST")){
				grid[(isPast)? NAMES.INPUT_PAST_EAST_DIST.ordinal(): NAMES.INPUT_CUR_EAST_DIST.ordinal()] = map[1];
			}else if (map[0].trim().equals("EAST-TYPE")){
				grid[(isPast)? NAMES.INPUT_PAST_EAST_TYPE.ordinal(): NAMES.INPUT_CUR_EAST_TYPE.ordinal()] = map[1];
			}else if (map[0].trim().equals("SOUTH-DIST")){
				grid[(isPast)? NAMES.INPUT_PAST_SOUTH_DIST.ordinal(): NAMES.INPUT_CUR_SOUTH_DIST.ordinal()] = map[1];
			}else if (map[0].trim().equals("SOUTH-TYPE")){
				grid[(isPast)? NAMES.INPUT_PAST_SOUTH_TYPE.ordinal(): NAMES.INPUT_CUR_SOUTH_TYPE.ordinal()] = map[1];
			}else if (map[0].trim().equals("WEST-DIST")){
				grid[(isPast)? NAMES.INPUT_PAST_WEST_DIST.ordinal(): NAMES.INPUT_CUR_WEST_DIST.ordinal()] = map[1];
			}else if (map[0].trim().equals("WEST-TYPE")){
				grid[(isPast)? NAMES.INPUT_PAST_WEST_TYPE.ordinal(): NAMES.INPUT_CUR_WEST_TYPE.ordinal()] = map[1];
			}
		}
	}

}
