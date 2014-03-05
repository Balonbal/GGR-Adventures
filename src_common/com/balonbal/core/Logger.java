package com.balonbal.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	public static void log(String message) {
		log(null, message);
	}
	
	/**
	 * Print to console using a prefix
	 * @param prefix Prefix to be added before the main message
	 * @param message Message to be displayed
	 */
	public static void log(String prefix, String message) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		
		if (prefix != null) {
			System.out.printf("%s [%s] %s\n", dateFormat.format(date), prefix, message);
		} else {
			System.out.printf("%s [STD] %s\n", dateFormat.format(date), message);
		}
	}

}
