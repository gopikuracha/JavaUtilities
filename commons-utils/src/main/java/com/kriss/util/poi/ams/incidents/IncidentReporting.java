package com.kriss.util.poi.ams.incidents;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kriss.collection.adt.TDS;
import com.kriss.util.DesiredPrinting;
import com.kriss.util.PrintUtil;
import com.kriss.util.poi.ExcelFileReader;

public class IncidentReporting extends IncidentData {
	
	public static Map<String, Integer> reports = new HashMap<String, Integer>();

	/*
	 * static { reports.put("Incidents Open", null); }
	 */
	
	public static void main(String[] args) {
		
		IncidentReporting ir = new IncidentReporting();
		
		String fileName = "C:/ams_Documents_Car-Net/Reporting/June-2024/1_100_IM Raw Data Report_AMS_Connected_Car (Last Month)_115_9002315459506786781.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		TDS<Object> tds = reader.readSheetWithHeader(fileName, 1, 4, 688, Arrays.asList(8,9,11,13,80,82,83,84,85));
		tds.removeNullRecords();
		
		/**  Step2 - Print Raw TDS **/
		// ir.printTDS(tds, true, true, true, false);
		
		/**  Step3 - Generate Shortened TDS **/
		TDS<Object> reportTDS = tds.getRecordsForSpecificColumns(IR_Columns);
		
		/**  Step4 - Print Shortened TDS **/
		// ir.printTDS(reportTDS, true, true, true, false);
		
		/**  Step5 - Print Shortened TDS **/
		ir.printIncorrectTagsData(reportTDS);
		
		
		// ir.writeReportData(null);
		// ir.printReportData(tds);
		
		/*
		 * int inc_open_Count = 0; int inc_resolved_Count = 0;
		 * 
		 * int p1_open_count = 0; int p2_open_count = 0; int p3_open_count = 0; int
		 * p4_open_count = 0; int p5_open_count = 0; int p6_open_count = 0;
		 * 
		 * int p1_resolved_count = 0; int p2_resolved_count = 0; int p3_resolved_count =
		 * 0; int p4_resolved_count = 0; int p5_resolved_count = 0; int
		 * p6_resolved_count = 0;
		 */
		
	}
	
	public void printTDS(TDS<Object> tds, boolean print, boolean validate, boolean overview, boolean specific) {
		if (print) System.out.println(tds);
		if (validate) System.out.println("\n" + tds.validateTDS());
		if (overview) System.out.println("\n" + tds.getDataOverview(Arrays.asList("No.", "Incident ID", "Title", "Update Time" + "\n" + "(Timezone based)",
				"Open Time" + "\n" + "(Timezone based)", "Resolve Time" + "\n" + "(Timezone based)", "Close Time" + "\n" + "(Timezone based)",
				"EXACT Time in DAYS to Resolve [DAYS(Resolve date, Open Date)]")));
		if (specific) System.out.println("\n" + tds.getSpecificColumnDataOverview((Arrays.asList("Assignment Group"))));
	}
	
	public void printReportData(TDS<Object> tds) {
		TDS<Object> openTDS = tds.getFilteredRecords("Open Year [Year of Open Time]", "2024").getFilteredRecords("Open Month [Month of Open Time]", "2");
		// printTDS(openTDS, true, true, true, false);
		
		TDS<Object> resolvedTDS = tds.getFilteredRecords("Resolved Year [Year of Closed or Resolved date column in left here]", "2024").getFilteredRecords("Resolved Month [Month of Closed or Resolved date column in left here]", "2");
		// printTDS(resolvedTDS, true, true, true, false);
		
		System.out.println("Incidents Open: " + openTDS.getRecords().size());
		System.out.println("Incidents Resolved: " + resolvedTDS.getRecords().size());
		
		System.out.println("\n" + "Waiting on Partner: " + tds.getMapOfRecordsSplitByAColumn("Status").get("Wait on External"));
		System.out.println("Waiting on Incident Reporter: " + (tds.getMapOfRecordsSplitByAColumn("Status").get("Wait on User") + tds.getMapOfRecordsSplitByAColumn("Status").get("Closed")));
		
		PrintUtil.printAMap("\n" + "Priority | Resolved (Count)", resolvedTDS.getMapOfRecordsSplitByAColumn("Priority"));
		PrintUtil.printAMap("\n" + "Incidents by Priority", openTDS.getMapOfRecordsSplitByAColumn("Priority"));
		PrintUtil.printAMap("\n" + "Incidents by Market", openTDS.getMapOfRecordsSplitByAColumn("Market [Split Tag 3]"));
		PrintUtil.printAMap("\n" + "Incidents by Vehicle Platform: ", openTDS.getMapOfRecordsSplitByAColumn("Vehicle Plaform [Split Tag 3]"));
		PrintUtil.printAMap("\n" + "Incidents by Vehicle Platform", openTDS.getMapOfRecordsSplitByAColumn("Vehicle Program [Split Tag 3]"));
		
		System.out.println("\n" + "Carrying forward to Next Month: ");
		System.out.println("Waiting on Incident Reporter: " + (openTDS.getMapOfRecordsSplitByAColumn("Status").get("Wait on User") + openTDS.getMapOfRecordsSplitByAColumn("Status").get("Closed")));
		System.out.println("Waiting on Partner Resolution: " + openTDS.getMapOfRecordsSplitByAColumn("Status").get("Wait on External"));
		System.out.println("Work in-progress: " + openTDS.getMapOfRecordsSplitByAColumn("Status").get("Working"));
		
		// PrintUtil.printAMap("\n" + "Service Group / Partner", openTDS.getMapOfRecordsSplitByAColumn("Partner"));
		System.out.println("\n" + "Service Group / Partner: <Opened>");
		System.out.println("Aeris: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Aeris"));
		System.out.println("Wireless Car: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Wireless Car"));
		System.out.println("Car-Net SP: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Car-Net SP"));
		System.out.println("CWP: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("CWP"));
		System.out.println("Dealer Apps: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Dealer Apps"));
		System.out.println("Mobile Apps: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Mobile Apps"));
		System.out.println("Developer Platform: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Developer Platform"));
		System.out.println("Infrastructure: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Infrastructure"));
		System.out.println("Cubic: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Cubic"));
		System.out.println("T-Mobile: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("T-Mobile"));
		System.out.println("Verizon: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Verizon"));
		System.out.println("AG: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("AG"));
		System.out.println("Infotainment: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Infotainment"));
		System.out.println("Vehicle Engineering: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Vehicle Engineering"));
		System.out.println("Others: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("Others"));
		System.out.println("NITS: " + openTDS.getMapOfRecordsSplitByAColumn("Partner").get("NITS"));
		
		
		
	}
	
	public void writeReportData(TDS<Object> tds) {
		String fileName = "C:/Documents_Car-Net/Reporting/Jan-2024/report_Jan-2024.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		List<Integer> list = Arrays.asList(1, 2);
		map.put("Total Opened", list);
		// reader.updateSheet(fileName, 0, map);
	}
	
	public void printIncorrectTagsData(TDS<Object> tds) {
		System.out.println("\n" + "Incorrect Tag 3 data: " + "\n" + tds.ignoreMultipleFilteredRecords("Tag 3", tag3Data).ignoreNullRecords("Tag 3"));
		System.out.println("\n" + "Incorrect Tag 4 data: " + "\n" + tds.ignoreMultipleFilteredRecords("Tag 4", tag4Data).ignoreNullRecords("Tag 4"));
		System.out.println("\n" + "Incorrect Tag 5 data: " + "\n" + tds.ignoreMultipleFilteredRecords("Tag 5", tag5Data).ignoreNullRecords("Tag 5"));
		
		TDS<Object> tag5USMNO = tds.getMultipleFilteredRecords("Tag 5", tag5Data)
								.getFilteredRecordsWithWildCardSearch("Tag 5", Arrays.asList("Verizon", "T-Mobile"))
								.getFilteredRecordsWithWildCardSearch("Tag 3", Arrays.asList("CA |"));
		TDS<Object> tag5CAMNO = tds.getMultipleFilteredRecords("Tag 5", tag5Data)
				.getFilteredRecordsWithWildCardSearch("Tag 5", Arrays.asList("Rogers"))
				.getFilteredRecordsWithWildCardSearch("Tag 3", Arrays.asList("US |"));
		
		System.out.println("\n" + "Incorrect market/MNO Tagging for USA market: "+ tag5USMNO.rows() + "\n" + tag5USMNO);
		System.out.println("\n" + "Incorrect market/MNO Tagging for CAN market: "+ tag5CAMNO.rows() + "\n" + tag5CAMNO);
		
		TDS<Object> untaggedTDS = tds.ignoreMultipleFilteredRecords("Tag 3", tag3Data).getNullRecords("Tag 3");
		untaggedTDS.sortRecordsWithColumnName("Assigned to", true);
		
		System.out.println("\n" + "Untagged data: "+ untaggedTDS.rows() + "\n" + untaggedTDS);
	}
	
	private void printIncorrectQueueAssignmentData(TDS<Object> tds) {
		TDS<Object> tag5USMNO = tds.getMultipleFilteredRecords("Tag 5", tag5Data)
				.getFilteredRecordsWithWildCardSearch("Tag 3", Arrays.asList("US |"))
				.getFilteredRecords("Assignment Group", "VW Car-Net Canada Connected Services Support VW Group of America");
		TDS<Object> tag5CAMNO = tds.getMultipleFilteredRecords("Tag 5", tag5Data)
				.getFilteredRecordsWithWildCardSearch("Tag 3", Arrays.asList("CA |"))
				.getMultipleFilteredRecords("Assignment Group", usQueues);
		
		tag5USMNO.appendRecords(tag5CAMNO);
		// tag5USMNO = tag5USMNO.getFilteredRecords("Status", "Working", false);
		System.out.println("\n" + "Incorrect Queue Assignment: "+ tag5USMNO.rows() + "\n" + tag5USMNO);
	}
	
}
