package com.kriss.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	private boolean printStackTrace;
	
	public boolean isPrintStackTrace() {
		return printStackTrace;
	}
	
	public void enablePrintStackTrace() {
		printStackTrace = true;
	}
	
	public void disablePrintStackTrace() {
		printStackTrace = false;
	}
	
	public static final String DF_MMddyyyy_slash = "MM/dd/yyyy";
	public static final String DF_MMddyyyy_iphen = "MM-dd-yyyy";
	public static final String DF_yyyyMMdd = "yyyy-MM-dd";
	public static final String DF_yyyyMMddhhmmss = "yyyy-MM-dd hh:mm:ss";
	public static final String DF_yyyyMMddhhmmssSSS = "yyyy-MM-dd hh:mm:ss.SSS";
	
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		System.out.println(getCurrentDate("STRING", DF_MMddyyyy_slash));
		System.out.println(removeTimeFromDate(new Date(), DF_yyyyMMdd));
		System.out.println(formatDate(date, DF_yyyyMMdd));
		System.out.println(getformatDate(date, DF_yyyyMMdd));
		System.out.println("Date from String: " + getDateFromString("10-28-2024", DF_MMddyyyy_iphen));
		System.out.println("Date from String: " + getDateFromISO8601String("2024-06-10T08:05:07.768+00:00", DF_yyyyMMddhhmmssSSS));
		Date dateTime = getDateFromISO8601String("2024-06-10T08:05:07.768+00:00", DF_yyyyMMddhhmmssSSS);
		System.out.println("dateTime: " + dateTime);
		
	}
	
	
	/**
	 * Convert ISO 8601 Date String to Date of desired format
	 * 	Step #1: Convert ISO Date String to ISO Date
	 * 	Step #2: Covert ISO Date to a desired format Date String
	 * 	Step #3: Convert desired format Date String to a Date
	 * @param returnType: "STRING", "java.util.Date"
	 */
	public static Date determineAndGetDateFromString(String dateString, String dateFormat) {
		if (dateString == null) return null;
		if (dateString.contains("T") && !dateString.contains("UTC")) return getDateFromISO8601String(dateString, dateFormat);
		return getDateFromString(dateString, dateFormat);
	}
	
	/**
	 * Convert ISO 8601 Date String into Date Object of desired format
	 * 	Step #1: Convert ISO Date String to ISO Date
	 * 	Step #2: Covert ISO Date to a desired format Date String
	 * 	Step #3: Convert desired format Date String to a Date
	 * @param returnType: "java.util.Date"
	 */
	public static Date getDateFromISO8601String(String isoDate, String dateFormat) {
		if (isoDate == null) return null;
		Date date = null;
		try {
			LocalDateTime dateTime = LocalDateTime.parse(isoDate, DateTimeFormatter.ISO_DATE_TIME);
			// System.out.println(DateTimeFormatter.ISO_DATE_TIME);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
			String formattedDate = dateTime.format(formatter);
			// System.out.println("formattedDate: " + formattedDate);
			date = getDateFromString(formattedDate, dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Convert Date String into Date Object of desired format
	 * @param returnType: "java.util.Date"
	 */
	public static Date getDateFromString(String stringDate, String dateFormat) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			date = formatter.parse(stringDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	
	/**
	 * @param returnType: "STRING", "java.util.Date"
	 */
	public static Object getCurrentDate(String returnType, String dateFormat) {
		if (returnType == null || dateFormat == null) return null;
		Date now = new Date();
		String dateInString = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			dateInString = format.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (returnType) {
			case "STRING": return dateInString;
			case "java.util.Date": return now;
			default: return null;
		}
	}
	
	public static String removeTimeFromDate(Date date, String dateFormat) {
		if (date == null) return null;
		if (dateFormat == null) return date.toString();
		String dateStr = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			dateStr = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	public static Date formatDate(Date date, String dateFormat) {
		if (date == null) return null;
		if (dateFormat == null) return date;
		Date returnDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			returnDate = format.parse(format.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnDate;
	}
	
	public static String getformatDate(Date date, String dateFormat) {
		if (date == null) return null;
		if (dateFormat == null) return date.toString();
		String formatString = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			formatString = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatString;
	}
	
	
	
}
