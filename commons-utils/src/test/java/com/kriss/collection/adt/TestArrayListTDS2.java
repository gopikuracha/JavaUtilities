package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class TestArrayListTDS2 {
	
	@Test
	// Completed
	public void testGettersAndSetters() {
		System.out. println("Inside testGettersAndSetters()");
		ArrayListTDS2 tds = getTDS();
		System.out.println(tds);
		
		// Test getValue
		int rowIndex = 2; int colIndex = 2;
		System.out.println("Value of " + rowIndex + "," + colIndex + " : " + tds.getValue(rowIndex, colIndex));
		
		// Test setValue
		String str = "Bangalore";
		System.out.println("Set value of " + rowIndex + "," + colIndex + " to " + str + " : " + tds.setValue(rowIndex, colIndex, str));
		System.out.println("Value of " + rowIndex + "," + colIndex + " : " + tds.getValue(rowIndex, colIndex));
		System.out.println(tds);
		
		System.out. println("Exiting testGettersAndSetters()" + "\n");
	}
	
	@Test
	// Completed
	public void testRowFeatures() {
		System.out. println("Inside testRowFeatures()");
		ArrayListTDS2 tds = getTDS();
		System.out.println(tds);
		
		// Test insertRecord
		Object[] rec5 = {"Bobby", "Bidhar", "Bison", "Bell"};
		int rowIndex = 2;
		System.out.println("Insert record at : " + rowIndex + " : " + tds.insertRecord(Arrays.asList(rec5), rowIndex));
		System.out.println(tds);
		
		// Test cutRecord
		int rowIndex2 = 3;
		System.out.println("Record removed at : " + rowIndex2 + " : " + tds.cutRecord(rowIndex2));
		System.out.println(tds);
		
		System.out. println("Exiting testRowFeatures()" + "\n");
	}
	
	@Test
	// Completed
	public void testColumnFeatures() {
		System.out. println("Inside testColumnFeatures()");
		ArrayListTDS2 tds = getTDS();
		System.out.println(tds);
		
		// Test getColumnData with Name
		String str = "Animal";
		System.out.println("getColumnData with Name: " + str + " : " + tds.getOrCutColumnData(str, true, false));
		System.out.println("getColumnData with Name and without Headers: " + tds.getOrCutColumnData(str, false, false));
		
		// Test getColumnData with Index
		int index = 2;
		System.out.println("getColumnData with Index: " + index + " : " + tds.getOrCutColumnData(index, true, false));
		System.out.println("getColumnData with Index and without Headers: " + tds.getOrCutColumnData(index, false, false));
		
		// Test cutColumnData with Index
		System.out.println("cutColumnData with Index: " + index + " : " + tds.getOrCutColumnData(index, true, true));
		System.out.println(tds);
		System.out.println("cutColumnData with Index and without Headers: " + index + " : " + tds.getOrCutColumnData(index, false, true));
		System.out.println(tds);
		
		// Test cutColumnData with Name
		String str2 = "Name";
		System.out.println("cutColumnData with Name: " + str2 + " : " + tds.getOrCutColumnData(str2, true, true));
		System.out.println(tds);
		String str3 = "Thing";
		System.out.println("cutColumnData with Name and without Headers: " + str3 + " : " + tds.getOrCutColumnData(str3, false, true));
		System.out.println(tds);
		
		// Test Add New Column
		Object[] newCol = {"Country", "India", "China", "Japan", "France", "Italy", "Mexico"};
		System.out.println("Adding New Column: " + newCol[0] + " : " + tds.addNewColumn(Arrays.asList(newCol), true));
		System.out.println(tds);
		
		System.out. println("Exiting testColumnFeatures()" + "\n");
	}
	
	@Test
	// Completed
	public void testCloneTDS() {
		System.out. println("Inside testCloneTDS()");
		ArrayListTDS2 tds = getTDS();
		ArrayListTDS2 tds2 = tds.cloneTDS();
		
		System.out.println(tds);
		System.out.println(tds2);
		
		tds.setValue(2, 2, "Update");
		tds2.setValue(2, 2, "New Update");
		
		System.out.println(tds);
		System.out.println(tds2);
		
		System.out. println("Exiting testCloneTDS()" + "\n");
	}
	
	
	/**
	 * 1. ArrayList accepts multiple null values
	 * 2. ArrayList accepts duplicate values
	 */
	// @Test
	public void testArrayList() {
		
	}
	
	public ArrayListTDS2 getTDS() {
		return buildTDS2();
	}
	
	public ArrayListTDS2 buildTDS1() {
		String[] headers = {"Name", "City", "Animal", "Thing", "Body Part"};
		
		String[] rec1 = {"Anil", "Anakapalle", "Ant", "Arrow", "Leg"};
		String[] rec2 = {"Baskar", "Bombay", "Bull", "Bucket", "Hand"};
		String[] rec3 = {"Charlie", "Chennai", "Cheeta", "Chair", "Head"};
		String[] rec4 = {"David", "Delhi", "Dog", "Desk", "Ear"};
		
		ArrayListTDS2 tds = new ArrayListTDS2();
		tds.setColumnHeaders(new ArrayList<String>(Arrays.asList(headers)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec1)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec2)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec3)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec4)));
		
		return tds;
	}
	
	public ArrayListTDS2 buildTDS2() {
		String[] headers = {"Name", "City", "Animal", "Thing", "Body Part"};
		
		String[] rec1 = {"Anil", "Anakapalle", "Ant", "Arrow", "Leg"};
		String[] rec2 = {"Baskar", "Bombay", "Bull", "Bucket", "Hand"};
		String[] rec3 = {"Charlie", "Chennai", "Cheeta", "Chair", "Head"};
		String[] rec4 = {"David", "Delhi", "Dog", "Desk", "Ear"};
		String[] rec5 = {"David", "Delhi", "Dog", "Desk", "Ear"};
		
		ArrayListTDS2 tds = new ArrayListTDS2();
		tds.setColumnHeaders(new ArrayList<String>(Arrays.asList(headers)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec1)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec2)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec3)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec4)));
		tds.getRecords().add(new ArrayList<Object>(Arrays.asList(rec5)));
		tds.getRecords().add(null);
		
		return tds;
	}
}
