package com.kriss.util.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kriss.collection.adt.TDS;
import com.kriss.util.DateUtil;

/**
 * 
 */
public class ExcelFileReader {
	
	private boolean print;
	
	private boolean printStackTrace;

	public void enablePrint() {
		print = true;
	}
	
	public void disablePrint() {
		print = false;
	}
	
	public boolean isPrintStackTrace() {
		return printStackTrace;
	}
	
	public void enablePrintStackTrace() {
		printStackTrace = true;
	}
	
	public void disablePrintStackTrace() {
		printStackTrace = false;
	}
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param dateIndexes - Index starts with 0
	 * @return
	 */
	public TDS<Object> readSheetWithHeader(String fileName, int sheetNumber, Map<Integer, String> dateIndexes) {
		return readSheetWithHeader(fileName, sheetNumber, 1, -1, -1, dateIndexes);
	}
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param dateIndexes - Index starts with 0
	 * @param processColumns - Number of Columns to process | Index starts with 0
	 * @return
	 */
	public TDS<Object> readSheetWithHeader(String fileName, int sheetNumber, int processColumns, Map<Integer, String> dateIndexes) {
		return readSheetWithHeader(fileName, sheetNumber, 1, -1, processColumns, dateIndexes);
	}
	
	// TODO - Add Date Types to the TDS
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param startRowIndex - Actual Row number in the sheet, default value 1
	 * @param endRowIndex - Actual Row number in the sheet, default value -1
	 * @param processColumns - Number of Columns to process, default value -1
	 * @param dateIndexes - Index starts with 0
	 * @return
	 */
	public TDS<Object> readSheetWithHeader(String fileName, int sheetNumber, int startRowIndex, int endRowIndex, int processColumns, Map<Integer, String> dateIndexes) {
		TDS<Object> tds = new TDS<Object>();
		
		List<List<Object>> records = tds.getRecords();
		List<String> columnHeaders = tds.getColumnHeaders();
		Map<String, String> columnTypes = tds.getColumnTypes();
		Map<String, String> dateFormats = tds.getDateFormats();
		
		if (fileName !=null && !fileName.contains(".xlsx")) fileName = fileName+".xlsx";
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        
	        tds.setSource("Version:" + workbook.getSpreadsheetVersion() + ", File:" + fileName 
	        		+ ", Sheet:" + sheet.getSheetName() + ", # of Sheets:" + workbook.getNumberOfSheets());
	        
	        if (print) System.out.println("readSheetWithHeader: TDS Source set...");
	        
	        Row headerRow = sheet.getRow(0 + startRowIndex - 1);
	        int columns = headerRow.getLastCellNum();
	        if (processColumns != -1) columns = processColumns;
	        for (int col=0; col<columns; col++) {
	        	Cell cell = headerRow.getCell(col);
	        	if (cell != null) columnHeaders.add(cell.getStringCellValue());
	        	else columnHeaders.add(null);
	        }
	        
	        if (dateIndexes != null) {
	        	for (Integer dateX : dateIndexes.keySet()) {
		        	dateFormats.put(columnHeaders.get(dateX), dateIndexes.get(dateX));
					columnTypes.put(columnHeaders.get(dateX), "DATE");
					if (print) System.out.println("readSheetWithHeader: Date Indexes set for: " + dateX);
				}
	        }
	        
	        int rows = sheet.getPhysicalNumberOfRows();
	        for(int i=startRowIndex; i < rows; i++) {
	        	Row row = sheet.getRow(i);
	        	
	        	if (row == null) { records.add(null); continue; }
	        	List<Object> record = new ArrayList<Object>();
	        	for(int j=0; j<columns; j++) {
	        		Cell cell = row.getCell(j);
	        		Object obj = null;
	        		if (dateIndexes != null && dateIndexes.keySet() != null && dateIndexes.keySet().contains(j)) {
	        			obj = extractValueFromCell(tds, cell, evaluator, false, true, dateIndexes.get(j), i, j);
	        		}
	        		else obj = extractValueFromCell(tds, cell, evaluator, true, false, null, i, j);
	        		record.add(obj);
	        	}
	        	records.add(record);
	        	if (i == endRowIndex-1) break;
	        }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
		if (print) System.out.println("readSheetWithHeader: Finished processing file...");
		return tds;
	}
	
	public void readSheet(String fileName, int sheetNumber) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        System.out.println("Sheet: " + "\n" + sheet);
	        System.out.println("Physical Rows: " + sheet.getPhysicalNumberOfRows());
	        
	        int rows = sheet.getPhysicalNumberOfRows();
	        for(int i=0; i < rows; i++) {
	        	System.out.println();
	        	Row row = sheet.getRow(i);
	        	if (row == null) { System.out.print("NULL Row"); continue; }
	        	Iterator<Cell> itr = row.cellIterator();
	        	System.out.print("ROW-" + i + ": ");
	        	while (itr.hasNext()) {
	        		Cell cell = itr.next();
	        		String str = (String) extractValueFromCell(cell, true, false);
	        		System.out.print(str + " | ");
	        	}
	        }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
	}
	
	public Object extarctCellValue(String fileName, int sheetNumber, int rowIndex, int colIndex) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(sheetNumber);	        
	        
	        Row row = sheet.getRow(rowIndex);
	        System.out.println("Column: " + sheet.getRow(0).getCell(colIndex));
	        Cell cell = row.getCell(colIndex);
	        System.out.println(cell.getCellType());
	        System.out.println(cell.getLocalDateTimeCellValue());
	        System.out.println(cell.getDateCellValue());
	        System.out.println(cell.getNumericCellValue());
	        return cell.getCellType();
	        // return extractObjectFromCell(row.getCell(colIndex));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
		return null;
	}
	
	private Object extractObjectFromCell(Cell cell) {
		if (cell == null) return null;
		switch (cell.getCellType()) {
        case STRING:
        	return cell.getStringCellValue();
        case BOOLEAN:
        	return Boolean.toString(cell.getBooleanCellValue());
        case NUMERIC:
        	// LocalDateTime date = cell.getLocalDateTimeCellValue();
        	// System.out.println("Date: " + date);
        	return cell.getNumericCellValue();
        case BLANK:
        	return "blank";
        case FORMULA:
        	return "formula";
        case ERROR:
        	return "error";
        default:
        	return "default";
		}
	}
	

	
	private Object extractValueFromCell(Cell cell, boolean string, boolean date) {
		if (cell == null) return null;
		switch (cell.getCellType()) {
        case STRING:
        	return cell.getStringCellValue();
        case BOOLEAN:
        	if (string) return Boolean.toString(cell.getBooleanCellValue());
        	else return cell.getBooleanCellValue();
        case NUMERIC:
        	String value = null;
        	if (date) {
        		if (string) {
        			LocalDateTime dateTime = cell.getLocalDateTimeCellValue();
            		if (dateTime != null) return dateTime.toString();
        		} else return cell.getDateCellValue();
        		
        	} else {
        		Double cellVal = cell.getNumericCellValue();
        		if (string) {
        			String str = Double.toString(cellVal);
                	return str.substring(0, str.indexOf("."));
        		}
        		else return cellVal;
        	}
        	return value;
        default:
        	return null;
		}
	}
	
	
	private Object extractValueFromCell(TDS<Object> tds, Cell cell, FormulaEvaluator evaluator, boolean string, boolean date, String dateFormat, int i, int j) {
		if (cell == null) return null;
		switch (cell.getCellType()) {
        case STRING:
        	if (date) {
        		String cellValue = cell.getStringCellValue();
        		if (print) System.out.println("[" + i + "," + j + "] Inside STRING case: " + cellValue);
        		Date newDate = DateUtil.determineAndGetDateFromString(cellValue, dateFormat);
        		if (newDate == null) {
        			tds.updateWarnings("Unable to parse date - " + "[" + i + "," + j + "]" + " - " + cell.getStringCellValue());
        			return cellValue;
        		}
        		return newDate;
        	}
        	return cell.getStringCellValue();
        case BOOLEAN:
        	if (string) return Boolean.toString(cell.getBooleanCellValue());
        	else return cell.getBooleanCellValue();
        case NUMERIC:
        	String value = null;
        	if (date) {
        		if (string) {
        			LocalDateTime dateTime = cell.getLocalDateTimeCellValue();
            		if (dateTime != null) return dateTime.toString();
        		} else {
        			if (print) System.out.println("[" + i + "," + j + "] Inside NUMERIC case: " + cell.getNumericCellValue() + " Long: " + (long)cell.getNumericCellValue());
        			Date newdate = new Date((long)cell.getNumericCellValue());
        			return newdate;
        		}
        		
        	} else {
        		Long cellVal = (long) cell.getNumericCellValue();
        		if (string) {
        			String str = Long.toString(cellVal);
                	return str;
        		}
        		else return cellVal;
        	}
        	return value;
        case FORMULA:
        	DataFormatter formatter = new DataFormatter();
        	String strValue = null;
        	try {
        		strValue = formatter.formatCellValue(cell, evaluator);
        		if (print) System.out.println("[" + i + "," + j + "] Inside FORMULA case: " + strValue);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	return strValue;
        default:
        	return null;
		}
	}
}
