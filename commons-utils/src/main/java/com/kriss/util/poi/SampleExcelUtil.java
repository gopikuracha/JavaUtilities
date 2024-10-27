package com.kriss.util.poi.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SampleExcelUtil {

	public void readExcelFileWithIterator(String fileName) {
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	             
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                 
	                switch (cell.getCellType()) {
	                    case STRING:
	                        System.out.print("STRING: " + cell.getStringCellValue()); break;
	                    case BOOLEAN:
	                        System.out.print("BOOLEAN: " + cell.getBooleanCellValue()); break;
	                    case NUMERIC:
	                        System.out.print("NUMERIC: " + cell.getNumericCellValue()); break;
	                    case BLANK:
	                    	System.out.println("BLANK: "); break;
	                    case ERROR:
	                    	System.out.println("ERROR: "); break;
	                    case _NONE:
	                    	System.out.println("NONE : " + cell.getCellType()); break;
	                    case FORMULA:
	                    	System.out.println("FORMULA : " + cell.getCellFormula()); break;
	                    default:
	                    	System.out.println("Nothing... ");
	                }
	                System.out.print(" - ");
	            }
	            System.out.println();
	        }
	        
	        workbook.close();
	        inputStream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readExcelFile(String fileName) {
		XSSFWorkbook wb = null;
		try{
			File file = new File(fileName);
			wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			System.out.println("Sheet Name : " + sheet.getSheetName());

			Map<Integer, List<String>> data = new HashMap<>();
			int i = 0;
			for (Row row : sheet) {
			    data.put(i, new ArrayList<String>());
			    for (Cell cell : row) {
			    	switch (cell.getCellType()) {
                    case STRING:
                        System.out.print("STRING: " + cell.getRichStringCellValue().getString());
                        data.get(i).add(cell.getRichStringCellValue().getString()); break;
                    case NUMERIC:
                        System.out.print("NUMERIC: " + cell.getNumericCellValue());
                        data.get(i).add(cell.getNumericCellValue() + ""); break;
                    case BOOLEAN:
                        System.out.print("BOOLEAN: " + cell.getBooleanCellValue());
                        data.get(i).add(cell.getBooleanCellValue() + ""); break;
                    case BLANK:
                    	System.out.println("BLANK: "); break;
                    case ERROR:
                    	System.out.println("ERROR: "); break;
                    case _NONE:
                    	System.out.println("NONE: " + cell.getCellType()); break;
                    case FORMULA:
                    	System.out.println("FORMULA: " + cell.getCellFormula());
                    	data.get(i).add(cell.getCellFormula() + ""); break;
                    default:
                    	System.out.println("Nothing... ");
			    	}
			    	System.out.print(" - ");
			    }
			    i++;
			    System.out.println();
			    
			}
			
			System.out.println(data);
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				wb.close();
			}catch(Exception e){
				System.out.println("Couldnt close...");
				e.printStackTrace();
			}
		}
	}
	
	
	public void getCellValue(String fileName, int sheetIndex, int rowIndex, int colIndex) {
		XSSFWorkbook wb = null;
		try {
			File file = new File(fileName);
			wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			System.out.println("Sheet Name : " + sheet.getSheetName());
			XSSFRow row = sheet.getRow(rowIndex);
			XSSFCell cell = row.getCell(colIndex);
			// System.out.println("Cell val : "+cell.getRawValue());
			System.out.println("Text Val : "+cell.getStringCellValue());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				wb.close();
			}catch(Exception e){
				System.out.println("Couldnt close...");
				e.printStackTrace();
			}
		}
	}
}
