package com.kriss.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		System.out.println(getCurrentDate("STRING", "MM/dd/yyyy"));
		System.out.println(removeTimeFromDate(new Date(), "yyyy-MM-dd"));
		System.out.println(formatDate(date, "yyyy-MM-dd"));
		System.out.println(getformatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * @param returnType: "STRING", "java.util.Date"
	 */
	public static Object getCurrentDate(String returnType, String dateFormat) {
		if (returnType == null || dateFormat == null) return null;
		Date now = new Date();
		String date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			date = format.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (returnType) {
			case "STRING": return date;
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
