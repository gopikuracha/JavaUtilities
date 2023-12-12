package com.kriss.util.poi.io;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.kriss.collection.adt.TabularDS;
import com.kriss.util.poi.data.ExcelDataProcessor;
import com.kriss.util.poi.data.model.ExcelDataMap;
import com.kriss.util.poi.data.model.RowDataMap;
import com.kriss.util.print.DesiredPrinting;	

public class AMainClass {

	public static void main(String[] args) {
		//testTransactionsFile();
		testPersonalLaptopLocalFile();
		
	}
	
	public static void testTransactionsFile () {
		/** Basics - To Understand the concept of read/write of an Excel file **/
		// SampleExcelUtil util = new SampleExcelUtil();
		
		// util.readExcelFileWithIterator(fileName);
		// util.readExcelFile(fileName);
		// util.getCellValue(fileName, 0, 1, 5);
		
		// String fileName = "C:/AMS_Issues_Car-Net/CARNET-FLEET-20230306_1.xlsx";
		String fileName = "C:/Development/Data/PMS/Canada/Transactions.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		// TabularDS tds = reader.readFileWithIterator(fileName, 0, 61, 6, true, false);
		TabularDS tds = reader.readFileWithIndex(fileName, 0, 205, 18, true, false);
		// System.out.println(tds);

		
		/** Step2 - To process the extracted data **/
		ExcelDataProcessor process = new ExcelDataProcessor();
		ExcelDataMap data = process.processExcelDataToMap(tds, true);
		// System.out.println(data);
		
		
		/** Step3 - To filter the results on processed data **/
		List<RowDataMap> filterResults = null;
		// filterResults = data.getDataWithAttributeName("Linked Operations", "OP_41199");
		// filterResults = data.getDataWithAttributeName("Type", "ESSENTIAL");
		// filterResults = data.getDataWithIdentifierValue("Transaction Type ID", "TTIAA-VehicleUserHandling");
		// filterResults = data.getDataWithIdentifierValue("Transaction Type ID", "TTENT-sxm_connected_radio_v1");
		// System.out.println("Size: " + filterResults.size());
		// System.out.println(filterResults);
		
		
		/** Step4 - To filter the results on processed data and limit the data **/
		List<String> filterResult = null;
		// filterResult = data.getKeyValueWithAttributeName("Type", "ESSENTIAL", "Business ID");
		filterResult = data.getKeyValueWithAttributeName("Linked Operations", "OP_41199", "Transaction Type ID");
		System.out.println("Size: " + filterResult.size());
		System.out.println(filterResult);
		
	
		
		// TODO - /** Step3 - To filter the results on extracted data (TDS) **/

		
	}
	
	public static void testPersonalLaptopLocalFile() {
		/** Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		String fileName = "D:/Gopi/Documents/Agriculture.xlsx";
		TabularDS tds = reader.readFileWithIndex(fileName, 0, 95, 9, true, false);
		
		System.out.println(tds);
		
		/** Step2 - Process Data **/
		Set<String> unique = tds.getUniqueValues(1);
		DesiredPrinting.printASet("Category from Report", unique);
		
		//TabularDS filterdRowsTDS = tds.filterValues(0, Arrays.asList("Potato", "Garlic"));
		//System.out.println(filterdRowsTDS);
		
		TabularDS filterdRowsTDS = tds.filterValuesWithColumnNames("name", Arrays.asList("Potato", "Garlic"));
		System.out.println(filterdRowsTDS);
		
		//TabularDS filteredColTDS = filterdRowsTDS.filterColumns(Arrays.asList(0, 1));
		//System.out.println(filteredColTDS);
		
		TabularDS filteredColTDS = filterdRowsTDS.filterColumnsWithColumnNames(Arrays.asList("name", "category"));
		System.out.println(filteredColTDS);
		
	}
	
}
