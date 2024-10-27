package com.kriss.util.poi.ams.events;

import java.util.Arrays;

import org.json.JSONObject;

import com.kriss.collection.adt.TDS;
import com.kriss.util.poi.ExcelFileReader;

/**
 * 
 */
public class AMSEventLogParser {

	public static void main(String[] args) {
		
		AMSEventLogParser parser = new AMSEventLogParser();
		
		String fileName = "C:/Development/Data/Logs/vehicleStatus-log.xlsx";
		// String fileName2 = "C:/Development/Data/Incidents/Incidents_02192024_V2.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		TDS<Object> tds = reader.readSheetWithHeader(fileName, 0, Arrays.asList(2,4));
		// System.out.println(tds);
		
		/**  Step2 - Extract json objects and parse json **/
		String json = (String) tds.getRecords().get(0).get(3);
		//System.out.println(json);
		json = json.replace("\\", "");
		System.out.println(json);
		parser.parseJSON(json);
		
	}
	
	public void parseJSON(String json) {
		JSONObject obj = new JSONObject(json);
		System.out.println(obj);
		// JSONObject odoData = obj.getJSONObject("vehicleStatusData").getJSONObject("telemetricVehicleData").getJSONObject("odometer");
		System.out.println(obj.getJSONObject("vehicleStatusData").getJSONObject("telemetricVehicleData").get("odometer"));
	}
}
