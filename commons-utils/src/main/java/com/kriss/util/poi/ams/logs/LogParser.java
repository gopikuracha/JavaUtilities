package com.kriss.util.poi.ams.logs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kriss.util.poi.ams.model.LogLine;

public class LogParser {

	public int parseStartIndex;
	public int parseEndIndex;
	
	public String toParseString;
	
	public LogParser() {}
	
	public LogParser(int endIndex, String str) {
		parseStartIndex = 0;
		parseEndIndex = endIndex;
		toParseString = str;
	}
	
	public void setStartIndex(int index) {
		parseStartIndex = index;
	}
	
	public void setEndIndex(int index) {
		parseEndIndex = index;
	}
	
	public void setToParseString (String str) {
		toParseString = str;
	}
	
	public void parse(LogLine log) {
		log.setUtcTimeInMessage(parseDate());
		log.setThreadNum(getBracedParseString());
		log.setCoid(getParseString());
		log.setLogLevel(getBracedParseString());
		log.setClassName(getParseString());
		log.setMessageAfter(toParseString);
		//log.setLogMessage(getParseString());
	}
	
	public Date parseDate() {
		Date date = null;
		try {
			String dateStr = toParseString.substring(parseStartIndex, parseEndIndex);
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			date = df1.parse(dateStr);
		} catch (Exception e) { 
			return null;
		}
		incrementParser();
		return date;
	}
	
	public String getParseString() {
		try {
			parseEndIndex = toParseString.indexOf(" ");
			String str = toParseString.substring(parseStartIndex, parseEndIndex);
			incrementParser();
			return str;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public String getBracedParseString() {
		try {
			parseEndIndex = toParseString.indexOf("]")+1;
			String str = toParseString.substring(parseStartIndex+1, parseEndIndex-1);
			incrementParser();
			return str;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public void incrementParser() {
		toParseString = toParseString.substring(parseEndIndex+1);
	}
}
