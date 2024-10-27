package com.kriss.util.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kriss.collection.adt.TDS;
import com.kriss.util.poi.ams.model.LogLine;

public class ExcelFileWriter {

	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @return
	 */
	public void writeSheetToExcelFile(TDS<Object> tds, String fileName, String sheetName) {
		if (tds == null) { System.out.println("TDS is null"); return; }
		Workbook workbook = null;
		FileOutputStream outputStream = null;
		try {
			workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet(sheetName);
	        outputStream = new FileOutputStream(fileName);
	        
	        List<String> columnHeaders = tds.getColumnHeaders();
	        Row headerRow = sheet.createRow(0);
	        for (int i=0; i<columnHeaders.size(); i++) {
	        	Cell headerCell = headerRow.createCell(i);
	        	headerCell.setCellValue(columnHeaders.get(i));
	        }
	        
	        List<List<Object>> records = tds.getRecords();
	        for (int i=1; i<=records.size(); i++) {
	        	List<Object> record = records.get(i-1);
	        	if (record == null) continue;
	        	Row recordRow = sheet.createRow(i);
	        	for (int j=0; j<record.size(); j++) {
	        		Object obj = record.get(j);
	        		Cell cell = recordRow.createCell(j);
	        		if (obj != null) { 
	        			if (obj instanceof String) cell.setCellValue((String) obj);
	        			if (obj instanceof Date) cell.setCellValue((Date) obj);
	        			if (obj instanceof Boolean) cell.setCellValue((Boolean) obj);
	        			if (obj instanceof Double) cell.setCellValue((Double) obj);
	        		}
	        	}
	        }
	        workbook.write(outputStream);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace(); }
			try { if(outputStream!=null) outputStream.close(); } catch(IOException ioe) {ioe.printStackTrace(); }
		}
	}
	
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @return
	 */
	public void writeSheetToExcelFile(List<LogLine> logs, String fileName, String sheetName) {
		if (logs == null || logs.size() == 0) { System.out.println("Logs are empty..."); return; }
		Workbook workbook = null;
		FileOutputStream outputStream = null;
		try {
			workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet(sheetName);
	        outputStream = new FileOutputStream(fileName);
	        
	        Row headerRow = sheet.createRow(0);
	        Cell headerCell = null; 
	        
	        headerCell = headerRow.createCell(0); headerCell.setCellValue("@timestamp");
        	headerCell = headerRow.createCell(1); headerCell.setCellValue("message");
        	headerCell = headerRow.createCell(2); headerCell.setCellValue("UTC Timestamp");
        	headerCell = headerRow.createCell(3); headerCell.setCellValue("Thread");
        	headerCell = headerRow.createCell(4); headerCell.setCellValue("Co Id");
        	headerCell = headerRow.createCell(5); headerCell.setCellValue("Log Level");
        	headerCell = headerRow.createCell(6); headerCell.setCellValue("Class Name");
        	headerCell = headerRow.createCell(7); headerCell.setCellValue("Message After");
        	headerCell = headerRow.createCell(8); headerCell.setCellValue("VIN");
        	headerCell = headerRow.createCell(9); headerCell.setCellValue("Vehicle Id");
        	headerCell = headerRow.createCell(10); headerCell.setCellValue("Local Request Id");
        	headerCell = headerRow.createCell(11); headerCell.setCellValue("HTTP Method");
        	headerCell = headerRow.createCell(12); headerCell.setCellValue("URI");
        	headerCell = headerRow.createCell(13); headerCell.setCellValue("Request headers");
        	headerCell = headerRow.createCell(14); headerCell.setCellValue("Request Body");
        	headerCell = headerRow.createCell(15); headerCell.setCellValue("Data");
        	headerCell = headerRow.createCell(16); headerCell.setCellValue("Pod Name");
        	headerCell = headerRow.createCell(17); headerCell.setCellValue("Service");
        	headerCell = headerRow.createCell(18); headerCell.setCellValue("Log Message");
	        
	        for (int i=1; i<=logs.size(); i++) {
	        	LogLine log = logs.get(i-1);
	        	if (log == null) continue;
	        	Row recordRow = sheet.createRow(i);
	        	
	        	Cell cell = null;
	        	
	        	cell = recordRow.createCell(0);
	        	if (log.getEstTimeStamp() != null) setCellValue(cell, log.getEstTimeStamp().toString());
	        	
	        	cell = recordRow.createCell(1);
	        	setCellValue(cell, log.getRawMessage());
	        	
	        	cell = recordRow.createCell(2);
	        	if (log.getUtcTimeInMessage() != null) setCellValue(cell, log.getUtcTimeInMessage().toString());
	        	
	        	cell = recordRow.createCell(3);
	        	setCellValue(cell, log.getThreadNum());
	        	
	        	cell = recordRow.createCell(4);
	        	setCellValue(cell, log.getCoid());
	        	
	        	cell = recordRow.createCell(5);
	        	setCellValue(cell, log.getLogLevel());
	        	
	        	cell = recordRow.createCell(6);
	        	setCellValue(cell, log.getClassName());
	        	
	        	cell = recordRow.createCell(7);
	        	setCellValue(cell, log.getMessageAfter());
	        	
	        	cell = recordRow.createCell(8);
	        	setCellValue(cell, log.getVin());
	        	
	        	cell = recordRow.createCell(9);
	        	setCellValue(cell, log.getVehicleId());
	        	
	        	cell = recordRow.createCell(10);
	        	setCellValue(cell, log.getLocalRequestId());
	        	
	        	cell = recordRow.createCell(11);
	        	setCellValue(cell, log.getHttpMethod());
	        	
	        	cell = recordRow.createCell(12);
	        	setCellValue(cell, log.getUri());
	        	
	        	cell = recordRow.createCell(13);
	        	setCellValue(cell, log.getRequestHeaders());
	        	
	        	cell = recordRow.createCell(14);
	        	setCellValue(cell, log.getRequestBody());
	        	
	        	cell = recordRow.createCell(15);
	        	if (log.getDataMap() != null) setCellValue(cell, log.getDataMap().toString());
	        	
	        	cell = recordRow.createCell(16);
	        	setCellValue(cell, log.getPodName());
	        	
	        	cell = recordRow.createCell(17);
	        	setCellValue(cell, log.getServiceName());
	        	
	        	cell = recordRow.createCell(18);
	        	setCellValue(cell, log.getLogMessage());
	        	
	        }
	        workbook.write(outputStream);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace(); }
			try { if(outputStream!=null) outputStream.close(); } catch(IOException ioe) {ioe.printStackTrace(); }
		}
	}
	
	public void setCellValue(Cell cell, Object obj) {
		if (obj != null) { 
			if (obj instanceof String) cell.setCellValue((String) obj);
			if (obj instanceof Date) cell.setCellValue((Date) obj);
			if (obj instanceof Boolean) cell.setCellValue((Boolean) obj);
			if (obj instanceof Double) cell.setCellValue((Double) obj);
		}
	}
}
