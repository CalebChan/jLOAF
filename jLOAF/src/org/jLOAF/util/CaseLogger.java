package org.jLOAF.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CaseLogger {

	private static Logger LOGGER;

	protected CaseLogger(){}
	
	public static void createLogger(boolean writeToFile, String filename){
		LOGGER = Logger.getLogger("org.jLOAF.reasoning");
		LOGGER.setUseParentHandlers(false);
		try {
			if (writeToFile && filename != null && !filename.isEmpty()){
				FileHandler fh = new FileHandler(filename);
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter);
				LOGGER.addHandler(fh);
			}else if (writeToFile && (filename == null || filename.isEmpty())){
				FileHandler fh = new FileHandler("LOG.xml");
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter);
				LOGGER.addHandler(fh);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(Level level, String message){
		if (LOGGER != null){
			LOGGER.log(level, message + "\n");
		}
	}
}
