package com.kriss.util.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.kriss.collection.adt.ArrayListTDS;
import com.kriss.collection.adt.DynamicTDS;
import com.kriss.collection.adt.StaticTDS;
import com.kriss.collection.adt.TDS;
import com.kriss.collection.adt.TabularDS;

public class ExcelFileReader {
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param rows - Excluding the Headers
	 * @param columns
	 * @param hasHeader
	 * @return
	 */
	public TabularDS readFileWithIterator(String fileName, int sheetNumber, int rows, int columns, boolean hasHeader, boolean dynamicTDS) {
		TabularDS tds = null;
		if (dynamicTDS) tds = new DynamicTDS(columns, true);
		else tds = new StaticTDS(rows, columns, true);
		
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        Iterator<Row> iterator = sheet.iterator();
	        
	        int i = 0;
	        if(hasHeader) i = -1;
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            
	            int j = 0;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                 
	                switch (cell.getCellType()) {
	                    case STRING:
	                    	if(i == -1) tds.columnHeaders[j] = cell.getStringCellValue(); 
	                    	else tds.setValue(i, j, cell.getStringCellValue());
	                        break;
	                    case BOOLEAN:
	                    	if(i == -1) tds.columnHeaders[j] = Boolean.toString(cell.getBooleanCellValue());
	                    	else tds.setValue(i, j, cell.getBooleanCellValue());
	                        break;
	                    case NUMERIC:
	                    	Double cellVal = cell.getNumericCellValue();
	                    	String str = Double.toString(cellVal);
	                    	String value = str.substring(0, str.indexOf("."));
	                    	if(i == -1) tds.columnHeaders[j] = value;
	                    	else tds.setValue(i, j, value);
	                        break;
	                    default:
	                    	if(i == -1) tds.columnHeaders[j] = null;
	                    	else tds.setValue(i, j, null);
	                }
	                j++;
	            }
	            i++;
	        }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
		return tds;
	}
	
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param rows - Excluding the Headers
	 * @param columns
	 * @param hasHeader
	 * @return
	 */
	public TabularDS readFileWithIndex(String fileName, int sheetNumber, int rows, int columns, boolean hasHeader, boolean dynamicTDS) {
		TabularDS tds = null;
		if (dynamicTDS) tds = new DynamicTDS(columns, true);
		else tds = new StaticTDS(rows, columns, true);
		
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        
	        if (hasHeader) rows++;
	        for(int i=0; i < rows; i++) {
	        	Row row = sheet.getRow(i);
	        	
	        	if (row == null) continue;
	        	// if (hasHeader) i--;
	        	
	        	for(int j=0; j < columns; j++) {
	        		Cell cell = row.getCell(j);
	        		
	        		if (cell == null) continue;
	        		
	        		switch (cell.getCellType()) {
                    case STRING:
                    	if(hasHeader) { 
                    		if (i == 0) tds.columnHeaders[j] = cell.getStringCellValue();
                    		else tds.setValue(i-1, j, cell.getStringCellValue());
                    	}
                    	else tds.setValue(i, j, cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                    	if(hasHeader) { 
                    		if (i == 0) tds.columnHeaders[j] = Boolean.toString(cell.getBooleanCellValue());
                    		else tds.setValue(i-1, j, cell.getBooleanCellValue());
                    	}
                    	else tds.setValue(i, j, cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                    	Double cellVal = cell.getNumericCellValue();
                    	String str = Double.toString(cellVal);
                    	String value = str.substring(0, str.indexOf("."));
                    	if(hasHeader) { 
                    		if (i == 0) tds.columnHeaders[j] = cell.getStringCellValue();
                    		else tds.setValue(i-1, j, value);
                    	}
                    	else tds.setValue(i, j, value);
                        break;
                    default:
                    	if(hasHeader) { 
                    		if (i == 0) tds.columnHeaders[j] = null;
                    		else tds.setValue(i-1, j, null);
                    	}
                    	else tds.setValue(i, j, null);
	        		}
	        	}
	        
	        }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
		return tds;
	}
	
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param rows - Excluding the Headers
	 * @param columns
	 * @param hasHeader
	 * @return
	 */
	public ArrayListTDS readFileWithIndex(String fileName, int sheetNumber, int rows, int columns, boolean hasHeader) {
		ArrayListTDS tds = new ArrayListTDS();
		tds.setSource(fileName);
		List<List<String>> records = tds.getRecords();
		List<String> columnHeaders = tds.getColumnHeaders();
		
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        
	        if (hasHeader) rows++;
	        for(int i=0; i < rows; i++) {
	        	Row row = sheet.getRow(i);
	        	
	        	if (row == null) { records.add(null); continue; }
	        	List<String> record = new ArrayList<String>();
	        	for(int j=0; j < columns; j++) {
	        		Cell cell = row.getCell(j);
	        		
	        		if (hasHeader && i == 0) {
	        			if (cell == null) columnHeaders.add(null);
	        			else columnHeaders.add(cell.getStringCellValue());
	        			continue;
	        		} else if (cell == null) {
	        			record.add(null); 
	        			continue; 
	        		}
	        		
	        		switch (cell.getCellType()) {
                    case STRING:
                    	record.add(cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                    	record.add(Boolean.toString(cell.getBooleanCellValue()));
                        break;
                    case NUMERIC:
                    	Double cellVal = cell.getNumericCellValue();
                    	String str = Double.toString(cellVal);
                    	String value = str.substring(0, str.indexOf("."));
                    	record.add(value);
                        break;
                    default:
                    	record.add(null);
	        		}
	        	}
	        	if (!(i == 0)) records.add(record);
	        }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { if(workbook!=null) workbook.close(); } catch(IOException ioe) {ioe.printStackTrace();}
			try { if(inputStream!=null) inputStream.close(); } catch(IOException ioe) {ioe.printStackTrace();}
		}
		return tds;
	}
	
	
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param dateIndexes - Index starts with 0
	 * @return
	 */
	public TDS<Object> readSheetWithHeader(String fileName, int sheetNumber, List<Integer> dateIndexes) {
		return readSheetWithHeader(fileName, sheetNumber, 1, -1, dateIndexes);
	}
	
	// TODO - Add Date Types to the TDS
	/**
	 * @apiNote: 
	 * @param fileName - Full path of the file
	 * @param sheetNumber - Starts with 0
	 * @param startRowIndex - Actual Row number in the sheet, default value 1
	 * @param endRowIndex - Actual Row number in the sheet, default value -1
	 * @return
	 */
	public TDS<Object> readSheetWithHeader(String fileName, int sheetNumber, int startRowIndex, int endRowIndex, List<Integer> dateIndexes) {
		TDS<Object> tds = new TDS<Object>();
		
		List<List<Object>> records = tds.getRecords();
		List<String> columnHeaders = tds.getColumnHeaders();
		List<String> columnTypes = tds.getColumnTypes();
		
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(inputStream);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	        Sheet sheet = workbook.getSheetAt(sheetNumber);
	        
	        tds.setSource("Version:" + workbook.getSpreadsheetVersion() + ", File:" + fileName 
	        		+ ", Sheet:" + sheet.getSheetName() + ", # of Sheets:" + workbook.getNumberOfSheets());
	        
	        Row headerRow = sheet.getRow(0 + startRowIndex - 1);
	        int columns = headerRow.getLastCellNum();
	        for (int col=0; col<columns; col++) {
	        	Cell cell = headerRow.getCell(col);
	        	if (cell != null) columnHeaders.add(cell.getStringCellValue());
	        	else columnHeaders.add(null);
	        }
	        
	        int rows = sheet.getPhysicalNumberOfRows();
	        for(int i=startRowIndex; i < rows; i++) {
	        	Row row = sheet.getRow(i);
	        	
	        	if (row == null) { records.add(null); continue; }
	        	List<Object> record = new ArrayList<Object>();
	        	for(int j=0; j<columns; j++) {
	        		Cell cell = row.getCell(j);
	        		Object obj = null;
	        		if (dateIndexes != null && dateIndexes.contains(j+1)) {
	        			obj = extractValueFromCell(cell, evaluator, false, true);
	        		}
	        		else obj = extractValueFromCell(cell, evaluator, true, false);
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
	
	private Object extractDateFromCell(Cell cell) {
		if (cell == null) return null;
		return cell.getDateCellValue();
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
	
	private Object extractValueFromCell(Cell cell, FormulaEvaluator evaluator, boolean string, boolean date) {
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
        case FORMULA:
        	DataFormatter formatter = new DataFormatter();
        	String strValue = null;
        	try {
        		strValue = formatter.formatCellValue(cell, evaluator);
        	} catch (Exception e) {
        		//e.printStackTrace();
        	}
        	return strValue;
        default:
        	return null;
		}
	}
}
