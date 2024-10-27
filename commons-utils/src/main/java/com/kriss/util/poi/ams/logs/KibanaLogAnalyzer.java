package com.kriss.util.poi.ams.logs;

import java.util.ArrayList;
import java.util.List;

import com.kriss.util.poi.ExcelFileWriter;
import com.kriss.util.poi.ams.model.LogLine;

public class KibanaLogAnalyzer {

	public static void main(String[] args) {
		
		KibanaLogAnalyzer kla = new KibanaLogAnalyzer();
		
		String str = "2024-03-12 00:57:40.396 [http-nio-8245-exec-17] c37e15a6-2a97-4ac2-b133-300317999a0d [DEBUG] com.vw.carnet.ms.traffic.logging.RequestResponseLoggingInterceptor.intercept - \r\n"
				+ "     LocalRequestId: bdec1158-aace-40bd-9c60-5e2cc7ac340d \r\n"
				+ "     Response Status 200 OK \r\n"
				+ "      Response Time 137 \r\n"
				+ "      Response body: {\"data\":{\"vehicles\":[{\"vin\":\"1V2FR2CA0RC540233\",\"vehicleId\":\"3c181aea-0752-3540-b781-7e8b2ff8ef38\",\"brand\":\"VW\",\"modelName\":\"Atlas\",\"modelCode\":\"CA35PR\",\"modelDesc\":\"atlas_pa2_2024\",\"stolenFlag\":\"N\",\"tankCapacity\":66.50,\"tspProvider\":\"ATC\",\"modelYear\":\"2024\",\"fctyModelYear\":\"2024\",\"saleModelYear\":\"2024\",\"firstOtarDate\":1703030400000,\"lastOtarDate\":1703030400000,\"representativeImgURLPartial\":\"https://d29xg6cejtjmis.cloudfront.net/2024/CA35PR/2R2R/image.png\",\"representativeImgURLComplete\":\"https://d29xg6cejtjmis.cloudfront.net/2024/CA35PR/2R2R/image.png\",\"vehicleRegistered\":true,\"engineTypeCode\":\"ICE\",\"testVehicle\":false,\"createdTimestamp\":1702789226000}]}}";
		
		LogLine log = new LogLine();
		log.setRawMessage(str);
		
		LogParser parser = new LogParser(23, str);
		parser.parse(log);
		
		List<LogLine> logs = new ArrayList<LogLine>();
		logs.add(log);
		
		System.out.println(log);
		
		//kla.writeToFile(logs);

	}
	
	public void writeToFile(List<LogLine> logs) {
		String fileName = "C:/ams_ISSUES_Car-Net/c2_Remote Access/RS_android_captcha-API_failure/Results.xlsx";
		ExcelFileWriter writer = new ExcelFileWriter();
		writer.writeSheetToExcelFile(logs, fileName, "Results");
	}
}
