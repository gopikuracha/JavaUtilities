package com.kriss.util.poi.ams.incidents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kriss.collection.adt.TDS;
import com.kriss.util.PrintUtil;
import com.kriss.util.poi.ExcelFileReader;

public class IncidentsStatus extends IncidentData {	

	public static void main(String[] args) {
		
		IncidentsStatus is = new IncidentsStatus();
		
		String fileName = "C:/Development/Data/Incidents/Incidents_08292024.xlsx";
		// String fileName2 = "C:/Development/Data/Incidents/Incidents_02192024_V2.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		TDS<Object> tds = reader.readSheetWithHeader(fileName, 0, Arrays.asList(16,17));
		
		/**  Step2 - Handle Date Columns **/
		tds.getDateFormats().put("Start Time", "yyyy-MM-dd");
		tds.getDateFormats().put("Last Update Time", "yyyy-MM-dd hh:mm:ss");
		System.out.println("Generate new Date Column - Start Date: " + tds.generateNewDateColumn("Start Time", "Start Date", "MM-dd-yyyy"));
		System.out.println("Generate new Date Column - Last Update Date: " + tds.generateNewDateColumn("Last Update Time", "Last Update Date", "MM-dd-yyyy"));
		is.printTDS(tds, true, true, false, false);
		// is.printRecordsSummarySplitByAColumn(tds, "Assignment Group");
		// is.printTransferredTicketsSplitByAssignmentGroup(tds);
		
		/**  Step3 - Filter data (rows, columns) to align with the Report data **/
		TDS<Object> reportTDS = tds.getRecordsForSpecificColumns(IS_Columns).getMultipleFilteredRecords("Status", getFilterStatuses());
		is.printTDS(reportTDS, true, true, true, true);
		if (reportTDS != null ) {
			is.printIncorrectTagsData(reportTDS);
			is.printIncorrectQueueAssignmentData(reportTDS);
			is.printAssignmentDetails(reportTDS);
		} else System.out.println("ReportTDS is null...");
		
		List<String> IS_Columns_2 = new ArrayList<String>(IS_Columns);
		IS_Columns_2.add("Title");
		TDS<Object> otherTDS = tds.getRecordsForSpecificColumns(IS_Columns_2).getMultipleFilteredRecords("Status", getFilterStatuses());
		System.out.println("\n" + "Working Tickets Assigned to Yaswanth: ");
		otherTDS.getFilteredRecords("Assigned to", "DVUSONV").getFilteredRecords("Status", "Working").getRecords().stream().forEach(System.out::println);
		
		/**  Using Java 8 features **/
		// reportTDS.getRecords().stream().forEach(System.out::println);
		
		
		//System.out.println(reportTDS.getFilteredRecords("Status", Arrays.asList("Working", "Updated", "Wait on External")));
		
		// reader.writeSheetToExcelFile(reportTDS, fileName2, "Test");
	}
	
	public void printTDS(TDS<Object> tds, boolean print, boolean validate, boolean overview, boolean specific) {
		if (print) System.out.println(tds);
		if (validate) System.out.println("\n" + tds.validateTDS());
		if (overview) System.out.println("\n" + tds.getDataOverview(Arrays.asList("Incident ID", "Title", "Start Time", "Last Update Time")));
		if (specific) System.out.println("\n" + tds.getSpecificColumnDataOverview((Arrays.asList("Assignment Group", "Assigned to", "Start Date", "Last Update Date"))));
	}
	
	public void printRecordsSummarySplitByAColumn(TDS<Object> tds, String column) {
		PrintUtil.printAMap("\n" + "MapOfResultsSplitByAColumn - " + column, tds.getMapOfRecordsSplitByAColumn(column));
	}
	
	public void printTransferredTicketsSplitByAssignmentGroup(TDS<Object> tds) {
		System.out.println("\n" + "Transferred tickets split by - Assignment Group:" + tds.getFilteredRecords("Status", "Transferred").getRecordsSplitByAColumn("Assignment Group"));
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
		
		// tag5USMNO.appendRecords(tag5CAMNO);
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
	
	private void printAssignmentDetails(TDS<Object> tds) {
		for (String dvid : resources.keySet()) {
			System.out.println();
			System.out.println(resources.get(dvid));
			System.out.println(tds.getFilteredRecords("Assigned to", dvid).getRecordsSplitByAColumn("Status"));
		}
	}
	
	private static List<Object> getFilterStatuses() {
		Object[] status = {"Working", "Wait on User", "Wait on External", "Updated"};
		List<Object> statuses = new ArrayList<Object>();
		for (Object obj : status) statuses.add(obj);
		return statuses;
	}
	
}