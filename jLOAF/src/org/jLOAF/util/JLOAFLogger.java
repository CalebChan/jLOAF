package org.jLOAF.util;

import java.util.Observable;

public class JLOAFLogger extends Observable{
	
	private static JLOAFLogger instance;
	
	public static final String DEFAULT_DELIMITER = "$";
	public static final String JSON_TAG = "JSON";
	
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
		
		private Object oMessage;
		
		public JLOAFLoggerInfoBundle(Level level, Class<?> c, String tag, String message) {
			this.level = level;
			this.c = c;
			this.tag = tag;
			this.message = message;
		}
		
		public JLOAFLoggerInfoBundle(Level level, Class<?> c, String tag, String message, Object extra){
			this(level, c, tag, message);
			this.oMessage = extra;
		}
		
		public Object getMessageExtra(){
			return this.oMessage;
		}
		
		public Level getLevel() {
			return level;
		}
		public Class<?> getMessageClass() {
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
	
	public void logMessage(Level level, Class<?> c, String tag, String message, Object extra){
		JLOAFLoggerInfoBundle bundle = new JLOAFLoggerInfoBundle(level, c, tag, message, extra);
		
		this.setChanged();
		this.notifyObservers(bundle);
	}
}
