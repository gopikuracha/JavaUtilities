package com.kriss.util.poi.ams.incidentreport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.kriss.collection.adt.TabularDS;
import com.kriss.util.poi.io.ExcelFileReader;
import com.kriss.util.print.DesiredPrinting;

public class IncidentTagging {
	
	public static List<String> tag3 = null;
	public static List<String> tag4 = null;
	public static List<String> tag5 = null;

	public static void main(String[] args) {
		testSC3Report();
	}
	
	public static void testSC3Report() {
		/** Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		
		String fileName = "C:/Development/Data/SC3/Incidents-12112023.xlsx";
		TabularDS tds = reader.readFileWithIndex(fileName, 0, 221, 23, true, false);
		System.out.println(tds);
		
		/** Step2 - Process Data **/
		Set<String> tag3FromReport = tds.getUniqueValues(19);
		DesiredPrinting.printASet("Tag3 from Report", tag3FromReport);
		tag3FromReport.removeAll(tag3);
		DesiredPrinting.printASet("Incorrect Tag3", tag3FromReport);
		
		TabularDS shortColTDS = tds.filterColumns(Arrays.asList(1, 2));
		System.out.println(shortColTDS);
	}
	
	static {
		tag3 = new ArrayList<String>();
		tag3.add("US | MOD3 | MQB");
		tag3.add("US | MOD3 | MQB-Low");
		tag3.add("US | MOD3 | MQB-High");
		tag3.add("US | MOD4 | PQ");
		tag3.add("US | MOD4 | MEB2");
		tag3.add("US | MOD4 | MEB3");
		
		tag3.add("CA | MOD3 | MQB");
		tag3.add("CA | MOD3 | MQB-Low");
		tag3.add("CA | MOD3 | MQB-High");
		tag3.add("CA | MOD4 | PQ");
		tag3.add("CA | MOD4 | MEB2");
		tag3.add("CA | MOD4 | MEB3");
		
		tag4 = new ArrayList<String>();
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		tag4.add("");
		
		tag5 = new ArrayList<String>();
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		tag5.add("");
		
	}
}
