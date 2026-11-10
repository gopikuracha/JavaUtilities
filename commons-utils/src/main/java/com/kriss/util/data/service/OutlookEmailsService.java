package com.kriss.util.data.service;

import com.kriss.collection.adt.TDS;
import com.kriss.util.poi.ExcelFileReader;

public class OutlookEmailsService {
	
	public static void main(String[] args) {
		
		OutlookEmailsService oes = new OutlookEmailsService();
		
		String fileName = "C:/Development/Data/email-configurations.xlsx";
		// String fileName2 = "C:/Development/Data/Incidents/Incidents_02192024_V2.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		TDS<Object> tds = reader.readSheetWithHeader(fileName, 0, null);
		tds.enablePrint();
		tds.validateTDS();
		tds.printTDS();
		
		System.out.println(tds.getUniqueValues("From"));
		
	}

}
