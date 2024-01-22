package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ArrayListTDS {

	/**
	 * 	The table Column Headers
	 */
	private List<String> columnHeaders;
	
	/**
	 * 	The table Values
	 */
	private List<List<String>> records;
	
	public ArrayListTDS() { 
		records = new ArrayList<List<String>>();
		columnHeaders = new ArrayList<String>();
	}
	
	public int rows() {
		return records.size();
	}

	public int columns() {
		return columnHeaders.size();
	}
	
	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	} 
	
	public List<List<String>> getRecords() {
		return records;
	}

	/**
	 * Input values are actual Table indexes and not Java indexes
	 */
	public String getValue(int row, int column) {
		if (row > records.size()) { 
			System.out.println("The Input row index is greater than the Table Rows");
			return null;
		}
		List<String> record = records.get(row-1);
		if (record == null) {
			System.out.println("The Record for the Input row index is Null");
			return null;
		}
		if (column > record.size()) {
			System.out.println("The Input Column index is greater than the Record Columns");
			return null;
		}
		return record.get(column-1);
	}
	
	/**
	 * Table feature: Update table data
	 * Input values are actual Table indexes and not Java indexes
	 */
	public boolean setValue(int row, int column, String obj) {
		if (row > records.size()) {
			System.out.println("The Input row index is greater than the Table Rows");
			return false;
		}
		List<String> record = records.get(row-1);
		if (record == null) {
			System.out.println("The Record for the Input row index is Null");
			return false;
		}
		if (column > record.size()) {
			System.out.println("The Input Column index is greater than the Record Columns");
			return false;
		}
		record.set(column-1, obj);
		return true;
	}
	
	/**
	 * Table feature: Add a new row at the end
	 * 
	 */
	public boolean appendRecord(List<String> record) {
		return records.add(record);
	}
	
	/**
	 * Table feature: Insert a new row in the middle
	 * Input values are actual Table indexes and not Java indexes
	 * 
	 */
	public boolean insertRecord(List<String> record, int rowIndex) {
		if (rowIndex > records.size()) {
			System.out.println("Insert rowIndex is greater than the TDS rows size.");
			return false;
		}
		records.add(rowIndex-1, record);
		return true;
	}
	
	/**
	 * Table feature: Cut/Delete a row
	 * Input values are actual Table indexes and not Java indexes
	 */
	public List<String> cutRecord(int rowIndex) {
		if (rowIndex > records.size()) {
			System.out.println("Insert rowIndex is greater than the TDS rows size.");
			return null;
		}
		return records.remove(rowIndex-1);
	}
	
	/**
	 * Table feature: Cut/Delete/Copy Column Data
	 * Options:
	 * 		With Column Name (Case sensitive ?)
	 * 		Include or exclude Column Header
	 * 		(cut = true) for Cut/Delete; (cut = false) for Copy;
	 */
	public List<String> getOrCutColumnData(String columnName, boolean includeHeader, boolean cut) {
		int colIndex = 0;
		if (columnName == null) return null;
		/*
		 * if (columnHeaders == null) { System.out.println("Column Headers are null.");
		 * return null; }
		 */
		colIndex = columnHeaders.indexOf(columnName);
		return getOrCutColumnData(colIndex, columnName, includeHeader, cut);
	}
	
	/**
	 * Table feature: Cut/Delete/Copy Column Data
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Include or exclude Column Header
	 * 		(cut = true) for Cut/Delete; (cut = false) for Copy;
	 */
	public List<String> getOrCutColumnData(int colIndex, boolean includeHeader, boolean cut) {
		/*
		 * if (columnHeaders == null) { System.out.println("Column Headers are null.");
		 * return null; }
		 */
		if (colIndex > columnHeaders.size()) {
			System.out.println("colIndex is greater than the TDS Columns size.");
			return null;
		}
		String columnName = null;
		columnName = columnHeaders.get(colIndex-1);
		return getOrCutColumnData(colIndex-1, columnName, includeHeader, cut);
	}
	
	private List<String> getOrCutColumnData(int colIndex, String columnName, boolean includeHeader, boolean cut) {
		List<String> columnData = null;
		if (colIndex == -1) {
			System.out.println("No matching Column Headers found.");
			return null;
		}
		if (columnName == null) {
			System.out.println("No matching Column Headers found.");
			return null;
		}
		columnData = new ArrayList<String>();
		if (includeHeader) columnData.add(columnName);
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) {
				columnData.add(null);
			} else {
				columnData.add(record.get(colIndex));
				if (cut) record.remove(colIndex);
			}
		}
		if (cut) columnHeaders.remove(colIndex);
		return columnData;
	}
	
	/**
	 * Table feature: Add/Insert a new Column
	 * Options:
	 * 		Include or exclude Column Header
	 * 		(colIndex = -1) for Add; (colIndex = insert Index, starts with 1) for Insert;
	 */
	public boolean addNewColumn(List<String> columnData, int colIndex, boolean includeHeader) {
		if (columnData == null || columnData.size() == 0) return false;
		if (records.size() == 0) return false;
		if (includeHeader) {
			if (columnData.size() > records.size()+1) {
				System.out.println("The Input data is greater than the Table Rows");
				return false;
			}
		} else {
			if (columnData.size() > records.size()) {
				System.out.println("The Input data is greater than the Table Rows");
				return false;
			}
		}
		
		int rowIndex = 0;
		if (includeHeader) {
			if (colIndex == -1) columnHeaders.add(columnData.get(rowIndex));
			else columnHeaders.add(colIndex, columnData.get(rowIndex));
			rowIndex++;
		}

		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) {
				rowIndex++;
				continue;
			}
			if (rowIndex < columnData.size()) {
				if (colIndex == -1) record.add(columnData.get(rowIndex));
				else record.add(colIndex, columnData.get(rowIndex));
			} else {
				if (colIndex == -1) record.add(null);
				else record.add(colIndex, null);
			}
			rowIndex++;
		}
		return true;
	}
	
	/**
	 * Table feature: Duplicate table
	 * 
	 */
	public ArrayListTDS cloneTDS() {
		ArrayListTDS newObject = new ArrayListTDS();
		List<String> newColumnHeaders = new ArrayList<String>();
		
		Iterator<String> itr = this.columnHeaders.iterator();
		while (itr.hasNext()) {
			String columnName = (String) itr.next();
			newColumnHeaders.add(columnName);
		}
		newObject.setColumnHeaders(newColumnHeaders);
		
		List<List<String>> newRecs = newObject.getRecords();
		Iterator<List<String>> itr2 = records.iterator();
		while (itr2.hasNext()) {
			List<String> record = (List<String>) itr2.next();
			if (record == null) {
				newRecs.add(null);
				continue;
			}
			List<String> newRec = new ArrayList<String>();
			Iterator<String> itr3 = record.iterator();
			while (itr3.hasNext()) {
				String rec = (String) itr3.next();
				newRec.add(rec);
			}
			newRecs.add(newRec);
		}
		return newObject;
	}
	
	public boolean removeNullRecords() {
		return records.removeAll(Collections.singleton(null));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ArrayListTDS ");
		builder.append("[rows=" + rows() + ", columns=" + columns() + ", columnHeaders=" + getColumnHeaders() + "]");
		builder.append("\n");
		builder.append("Values= [");
		builder.append("\n");
		int k = 0;
		Iterator<List<String>> itr = getRecords().iterator();
		while (itr.hasNext()) {
			builder.append(k + " : ");
			List<String> record = (List<String>) itr.next();
			builder.append(record);
			builder.append("\n");
			k++;
		}
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * Table feature: Filter Rows for specific Column value
	 * Options:
	 * 		With Column Name
	 * 		And single Filter Value 
	 */
	public ArrayListTDS filterValuesWithColumnNames(String columnName, String filterValue) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return filterValuesWithIndex(colIndex+1, filterValue);
	}
	
	/**
	 * Table feature: Filter Rows for specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		And single Filter Value 
	 */
	public ArrayListTDS filterValuesWithIndex(int colIndex, String filterValue) {
		colIndex = colIndex-1;
		if (filterValue == null || filterValue.equalsIgnoreCase("")) return null;
		ArrayListTDS subTDS = new ArrayListTDS();
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && record.get(colIndex).equals(filterValue)) {
				subTDS.getRecords().add(record);
			}
		}
		return subTDS;
	}
	
	/**
	 * Table feature: Filter Rows for specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 
	 */
	public ArrayListTDS filterValuesWithColumnNames(String columnName, List<String> filterValues) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return filterValuesWithIndex(colIndex, filterValues);
	}
	
	/**
	 * Table feature: Filter Rows for specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 
	 */
	public ArrayListTDS filterValuesWithIndex(int colIndex, List<String> filterValues) {
		if (filterValues == null || filterValues.size() == 0) return null;
		ArrayListTDS subTDS = new ArrayListTDS();
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && filterValues.contains(record.get(colIndex))) {
				subTDS.getRecords().add(record);
			}
		}
		return subTDS;
	}
	
	public TabularDS filterColumnsWithColumnNames(List<String> columnNames) {
		return null;
	}
	
	public TabularDS filterColumns(List<Integer> columnNumbers) {
		return null;
	}
	
	public TabularDS filterColumns(List<String> columnNames, List<Integer> columnNumbers) {
		return null;
	}
	
	public Set<String> getUniqueValues(int colIndex) {
		return null;
	}
}
