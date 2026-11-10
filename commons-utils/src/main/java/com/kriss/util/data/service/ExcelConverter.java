package com.kriss.util.data.service;

import java.util.HashMap;
import java.util.Map;

import com.kriss.collection.adt.TDS;
import com.kriss.util.poi.ExcelFileReader;

public class ExcelConverter {

public static void main(String[] args) {
		
		String fileName = "C:/Development/Data/my2024_offline_vins_102824.xlsx";
		// String fileName2 = "C:/Development/Data/Incidents/Incidents_02192024_V2.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		Map<Integer, String> dateIndexes = new HashMap<Integer, String>();
		dateIndexes.put(16, "yyyy-MM-dd hh:mm:ss");
		TDS<Object> tds = reader.readSheetWithHeader(fileName, 1, dateIndexes);
		tds.enablePrint();
		tds.validateTDS(); 
		tds.printTDS();
		tds.printTDSMetadata();
		
	}
	
}
