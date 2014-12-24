package org.jLOAF.util;

import java.util.Observable;

public class JLOAFLogger extends Observable{
	
	private static JLOAFLogger instance;
	
	private JLOAFLogger(){
		
	}
	
	public static JLOAFLogger getInstance(){
		if (instance == null){
			instance = new JLOAFLogger();
		}
		
		return instance;
	}
	
	public enum Level{
		DEBUG,
		INFO,
		EXPORT,
	};
	
	public class JLOAFLoggerInfoBundle{
		private Level level;
		private Class<?> c;
		private String tag;
		private String message;
		public JLOAFLoggerInfoBundle(Level level, Class<?> c, String tag, String message) {
			this.level = level;
			this.c = c;
			this.tag = tag;
			this.message = message;
		}
		public Level getLevel() {
			return level;
		}
		public Class<?> getC() {
			return c;
		}
		public String getTag() {
			return tag;
		}
		public String getMessage() {
			return message;
		}
	}
	
	public void logMessage(Level level, Class<?> c, String tag, String message){
		JLOAFLoggerInfoBundle bundle = new JLOAFLoggerInfoBundle(level, c, tag, message);
		
		this.setChanged();
		this.notifyObservers(bundle);
	}
}
