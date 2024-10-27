package com.kriss.util.poi.ams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kriss.collection.adt.ArrayListTDS;
import com.kriss.util.PrintUtil;
import com.kriss.util.poi.ExcelFileReader;

public class TestIncidentReport {
	
	@Test
	public void testDownloadedReportFromSC3() {
		String fileName = "C:/Development/Data/Incidents/Incidents_02012024.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		// ArrayListTDS tds = reader.readSheetWithHeader(fileName, 0);
		// System.out.println(tds);
		
		// System.out.println(tds.validateTDS());
		// System.out.println(tds.getDataOverview(Arrays.asList("Incident ID", "Title")));
		// PrintUtil.printAMap("getMapOfFilteredResultsSplitByAColumn", tds.getMapOfFilteredRecordsSplitByAColumn("Assignment Group", null));
		// tds.removeNullRecords();
		// System.out.println(tds);
		
		/** Get Transferred tickets split by Assignment Group **/
		Map<String, List<String>> transFilter = new HashMap<String, List<String>>();
		transFilter.put("Status", Arrays.asList("Transferred"));
		// System.out.println("\n" + "Transferred tickets split by Assignment Group: " + "\n" + tds.getFilteredResultsSplitByAColumn("Assignment Group", transFilter));
		
	}

}
