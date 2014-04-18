package tp1.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ARGELogger {

	private static ARGELogger loggerInstance = null;
	private static SimpleDateFormat dateFormater = new SimpleDateFormat("hh:mm:ss:SSSS");
	
	public enum LOG_LEVEL {DEBG, INFO, WARN, CRIT};
	
	private ARGELogger(){
	}
	
	private static ARGELogger getInstance(){
		if(loggerInstance==null){
			loggerInstance = new ARGELogger();
		}
		return loggerInstance;
	}
	
	/**
	 * @param origin : ex = worker2 , balancer ...
	 * @param msg 
	 * */
	public static void log(String origin, String msg, LOG_LEVEL logLevel){
		getInstance().log_internal("[" + logLevel.toString() +"]{" + getDate() + "}-" + msg , logLevel);
	}
	
	private void log_internal(String msg, LOG_LEVEL logLevel){
		System.out.println(msg);
	}
	
	
	private static String getDate(){
		return dateFormater.format(Calendar.getInstance().getTime());
	}
	
}
