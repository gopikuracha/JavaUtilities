package com.kriss.util.poi.ams.events;

import com.kriss.collection.adt.TabularDS;
import com.kriss.util.poi.ExcelFileReader;


/**
 * 
 */
public class AMSEventParser {

	public static void main(String[] args) {
		
		String fileName = "C:/2 Issues_Car-Net/vehicleStatus.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		// TabularDS tds = reader.readFileWithIterator(fileName, 0, 61, 6, true, false);
		TabularDS tds = reader.readFileWithIndex(fileName, 0, 40, 5, true, false);
		System.out.println(tds);
		
		
		for(int i=0; i<tds.getRows(); i++) {
			for(int j=0; j<tds.getColumns(); j++) {
				Object obj = tds.getValue(i, j);
				String str = (String) obj;
				switch (j) {
				case 3:
					System.out.println(str.indexOf("doorStatus"));
					str = str.substring(str.indexOf("doorStatus"));
					System.out.println(str);
				case 1:
				default:
				}
			}
		}
	}

}
