package com.kriss.util.poi.io;

import java.util.Arrays;

import org.junit.Test;

import com.kriss.collection.adt.ArrayListTDS;

public class TestExcelFileReader {

	@Test
	// Completed
	public void testGettersAndSetters() {
		String fileName = "D:/Gopi/Documents/Agriculture.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		ArrayListTDS tds = reader.readFileWithIndex(fileName, 0, 95, 9, true);
		
		// System.out.println(tds);
		
		tds.removeNullRecords();
		// System.out.println(tds);
		
		ArrayListTDS tds2 = tds.filterValuesWithIndex(1, "Processed");
		System.out.println(tds2);
		
		String[] filters = {"Vegetable", "Fruits"};
		ArrayListTDS tds3 = tds.filterValuesWithIndex(1, Arrays.asList(filters));
		System.out.println(tds3);
	}
}
