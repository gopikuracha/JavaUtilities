package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ArrayListTDS {

	/**
	 * 	The name of the Input source
	 */
	private String source;
	
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
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
			System.out.println("Cut rowIndex is greater than the TDS rows size.");
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
	 * Table feature: Add rows from other TDS at the end
	 * 
	 */
	public boolean appendRecords(ArrayListTDS secondTDS) {
		if (secondTDS == null) return false;
		if (secondTDS.rows() == 0) return false;
		if (secondTDS.getColumnHeaders() != null && secondTDS.getColumnHeaders() == columnHeaders) {
			setSource(source + "\n" + "\t" + "Merged with..." + "\n" + "\t"+ secondTDS.getSource());
			Iterator<List<String>> itr = secondTDS.getRecords().iterator();
			while (itr.hasNext()) {
				List<String> record = (List<String>) itr.next();
				if (record == null) continue;
				records.add(record);
			}
		} else return false;
		return true;
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 * 		Single Filter Value
	 *  	(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecords(String columnName, String filterValue, boolean ignore) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getFilteredRecords(colIndex+1, filterValue, ignore);
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Single Filter Value
	 * 		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecords(int colIndex, String filterValue, boolean ignore) {
		colIndex = colIndex-1;
		if (filterValue == null || filterValue.equalsIgnoreCase("")) return null;
		
		ArrayListTDS getTDS = new ArrayListTDS();
		getTDS.setSource(this.source + "\n" + "\t" + "- Filtered Column: " + columnHeaders.get(colIndex) + " with values(s): " + filterValue);
		getTDS.setColumnHeaders(columnHeaders);
		
		ArrayListTDS ignoreTDS = new ArrayListTDS();
		ignoreTDS.setSource(this.source + "\n" + "\t" + "- Ignored Column: " + columnHeaders.get(colIndex) + " for values(s): " + filterValue);
		ignoreTDS.setColumnHeaders(columnHeaders);
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && record.get(colIndex).equals(filterValue)) {
				getTDS.getRecords().add(record);
			} else {
				ignoreTDS.getRecords().add(record);
			}
		}
		return (ignore) ? ignoreTDS : getTDS;
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 * 		Multiple Filter Value
	 * 		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecords(String columnName, List<String> filterValues, boolean ignore) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getFilteredRecords(colIndex+1, filterValues, ignore);
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Multiple Filter Value
	 *		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecords(int colIndex, List<String> filterValues, boolean ignore) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return null;
		ArrayListTDS getTDS = new ArrayListTDS();
		getTDS.setSource(this.source + "\n" + "\t" + "- Filtered Column: " + columnHeaders.get(colIndex) + " with values(s): " + filterValues);
		getTDS.setColumnHeaders(columnHeaders);
		
		ArrayListTDS ignoreTDS = new ArrayListTDS();
		ignoreTDS.setSource(this.source + "\n" + "\t" + "- Ignored Column: " + columnHeaders.get(colIndex) + " for values(s): " + filterValues);
		ignoreTDS.setColumnHeaders(columnHeaders);
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && filterValues.contains(record.get(colIndex))) {
				getTDS.getRecords().add(record);
			} else {
				ignoreTDS.getRecords().add(record);
			}
		}
		return (ignore) ? ignoreTDS : getTDS;
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 * 		Multiple Filter Value - Wild card search
	 * 		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecordsWithWildCardSearch(String columnName, List<String> filterValues, boolean ignore) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getFilteredRecordsWithWildCardSearch(colIndex+1, filterValues, ignore);
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Multiple Filter Value - Wild card search
	 *		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS getFilteredRecordsWithWildCardSearch(int colIndex, List<String> filterValues, boolean ignore) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return null;
		ArrayListTDS getTDS = new ArrayListTDS();
		getTDS.setSource(this.source + "\n" + "\t" + "- Filtered Column: " + columnHeaders.get(colIndex) + " with Wild Card Search values(s): " + filterValues);
		getTDS.setColumnHeaders(columnHeaders);
		
		ArrayListTDS ignoreTDS = new ArrayListTDS();
		ignoreTDS.setSource(this.source + "\n" + "\t" + "- Ignored Column: " + columnHeaders.get(colIndex) + " for Wild Card Search values(s): " + filterValues);
		ignoreTDS.setColumnHeaders(columnHeaders);
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			boolean present = false;
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			for (String filterValue : filterValues) {
				if (record.get(colIndex).contains(filterValue)) {
					getTDS.getRecords().add(record);
					present = true;
					break;
				}
			}
			if (!present) ignoreTDS.getRecords().add(record);
		}
		return (ignore) ? ignoreTDS : getTDS;
	}
	
	/**
	 * Table feature: Get or Ignore Null Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 * 		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS ignoreNullRecords(String columnName, boolean ignore) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return ignoreNullRecords(colIndex+1, ignore);
	}
	
	/**
	 * Table feature: Get or Ignore Null Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		(true for Ignore) | (false for Get)
	 */
	public ArrayListTDS ignoreNullRecords(int colIndex, boolean ignore) {
		colIndex = colIndex-1;
		
		ArrayListTDS getTDS = new ArrayListTDS();
		getTDS.setSource(this.source + "\n" + "\t" + "- Filtered Column: " + columnHeaders.get(colIndex) + " with Null values(s)");
		getTDS.setColumnHeaders(columnHeaders);
		
		ArrayListTDS ignoreTDS = new ArrayListTDS();
		ignoreTDS.setSource(this.source + "\n" + "\t" + "- Ignored Column: " + columnHeaders.get(colIndex) + " for Null values(s)");
		ignoreTDS.setColumnHeaders(columnHeaders);
		
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) == null) {
				getTDS.getRecords().add(record);
			} else {
				ignoreTDS.getRecords().add(record);
			}
		}
		return (ignore) ? ignoreTDS : getTDS;
	}
	
	/**
	 * Table feature: Get Specific Columns data
	 * Options:
	 * 		With Column Names
	 */
	public ArrayListTDS getRecordsForSpecificColumns(List<String> columnNames) {
		if (columnNames == null || columnNames.size() == 0) {
			System.out.println("Incorrect Column Data");
			return null;
		}
		ArrayListTDS newTDS = new ArrayListTDS();
		newTDS.setSource(this.source + "\n" + "\t" + "- Shortened Columns to: " + columnNames);
		for (String colName : columnNames) {
			int colIndex = columnHeaders.indexOf(colName);
			if (colIndex == -1) {
				System.out.println("Incorrect Column Name : " + colName);
				continue;
			}
			newTDS.getColumnHeaders().add(colName);
		}
		for (List<String> record : records) {
			if (record == null) continue;
			List<String> newRecord = new ArrayList<String>();
			for (String columnName : newTDS.getColumnHeaders()) {
				int colIndex = columnHeaders.indexOf(columnName);
				newRecord.add(record.get(colIndex));
			}
			newTDS.getRecords().add(newRecord);
		}
		return newTDS;
	}
	
	/**
	 * Table feature: Get Distinct data for a Column, sorted ASC
	 * Options:
	 * 		With Column Name
	 */
	public Set<String> getUniqueValues(String columnName) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getUniqueValues(colIndex+1);
	}
	
	/**
	 * Table feature: Get Distinct data for a Column
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public Set<String> getUniqueValues(int colIndex) {
		colIndex = colIndex-1;
		Set<String> results = new TreeSet<String>();
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			String value = record.get(colIndex);
			if (value != null) results.add(value);
		}
		return results;
	}
	
	/**
	 * Get the count of Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 */
	public int getFilteredRecordsCount(String columnName, String filterValue) {
		if (columnName == null) return -1;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return -1;
		}
		return getFilteredRecordsCount(colIndex+1, filterValue);
	}
	
	/**
	 * Get the count of Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public int getFilteredRecordsCount(int colIndex, String filterValue) {
		colIndex = colIndex-1;
		if (filterValue == null || filterValue.equalsIgnoreCase("")) return -1;
		
		int filterCount = 0;
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && record.get(colIndex).equals(filterValue)) {
				filterCount++;
			}
		}
		return filterCount;
	}
	
	/**
	 * Get the count of Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 */
	public int getFilteredRecordsCount(String columnName, List<String> filterValues) {
		if (columnName == null) return -1;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return -1;
		}
		return getFilteredRecordsCount(colIndex+1, filterValues);
	}
	
	/**
	 * Get the count of Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public int getFilteredRecordsCount(int colIndex, List<String> filterValues) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return -1;
		
		int filterCount = 0;
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null) continue;
			if (record.get(colIndex) != null && filterValues.contains(record.get(colIndex))) {
				filterCount++;
			}
		}
		return filterCount;
	}
	
	/**
	 * Count of Null Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 */
	public int getNullRecordsCount(String columnName) {
		if (columnName == null) return -1;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return -1;
		}
		return getNullRecordsCount(colIndex+1);
	}
	
	/**
	 * Count of Null Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public int getNullRecordsCount(int colIndex) {
		colIndex = colIndex-1;
		int nullCount = 0;
		Iterator<List<String>> itr = records.iterator();
		while (itr.hasNext()) {
			List<String> record = (List<String>) itr.next();
			if (record == null || record.get(colIndex) == null) { 
				nullCount++;
			}
		}
		return nullCount;
	}
	
	/**
	 * Returns a String of the Overview of the Tabular Data split by a each Column
	 * Options:
	 * 		Ignore the Columns that are not required in the Overview
	 */
	public String getDataOverview(List<String> skipColumns) {
		StringBuilder builder = new StringBuilder("\n" + "----- Data Overview Start -----");
		for (String column : this.columnHeaders) {
			if (skipColumns == null || !skipColumns.contains(column)) {
				builder.append("\n" + "\n" + getFilteredRecordsSplitByAColumn(column, null));
			}
		}
		return builder.append("\n" + "----- Data Overview End -----").toString();
	}
	
	/**
	 * Returns a String of the results filtered and split by a specific Column
	 * 
	 */
	public String getFilteredRecordsSplitByAColumn(String columnName, Map<String, List<String>> filterValues) {
		if (columnName == null) return null;
		StringBuilder builder = new StringBuilder("Split Column - " + columnName + " | ");
		ArrayListTDS filterTDS = this;
		if (filterValues != null) {
			builder.append("Filter Values: " + filterValues.toString() + " | Total count: ");
			for (String key : filterValues.keySet()) {
				filterTDS = filterTDS.getFilteredRecords(key, filterValues.get(key), false);
			}
			builder.append(filterTDS.rows());
		} else builder.append("Filter Values: NONE" + " | Total count: " + filterTDS.rows());
		
		if (filterTDS.rows() != 0) {
			int dataCount = 0; 
			for (String columnValue : filterTDS.getUniqueValues(columnName)) {
				int splitCount = filterTDS.getFilteredRecordsCount(columnName, columnValue);
				builder.append("\n" + "\t" + columnValue + ": " + splitCount);
				dataCount += splitCount;
			}
			if (filterTDS.rows() != dataCount) builder.append("\n" + "\t" + "NULL" + ": " + filterTDS.getNullRecordsCount(columnName));
		} else builder.append("\n" + "Found empty results for the filter Values");
		return builder.toString();
	}
	
	/**
	 * Returns a Map of the results filtered and split by a specific Column
	 * 
	 */
	public Map<String, String> getMapOfFilteredRecordsSplitByAColumn(String columnName, Map<String, List<String>> filterValues) {
		if (columnName == null) return null;
		StringBuilder builder = new StringBuilder("Split Column - " + columnName + " | ");
		ArrayListTDS filterTDS = this;
		if (filterValues != null) {
			builder.append("Filter Values: " + filterValues.toString() + " | Total count: ");
			for (String key : filterValues.keySet()) {
				filterTDS = filterTDS.getFilteredRecords(key, filterValues.get(key), false);
			}
			builder.append(filterTDS.rows());
		} else builder.append("Filter Values: NONE" + " | Total count: " + filterTDS.rows());
		
		Map<String, String> results = new LinkedHashMap<String, String>();
		if (filterTDS.rows() != 0) {
			int dataCount = 0; 
			for (String columnValue : filterTDS.getUniqueValues(columnName)) {
				int splitCount = filterTDS.getFilteredRecords(columnName, columnValue, false).rows();
				results.put(columnValue, String.valueOf(splitCount));
				builder.append("\n" + "\t" + columnValue + ": " + splitCount);
				dataCount += splitCount;
			}
			if (filterTDS.rows() != dataCount) {
				int nullCount = filterTDS.getNullRecordsCount(columnName);
				results.put("NULL", String.valueOf(nullCount));
				builder.append("\n" + "\t" + "NULL: " + nullCount);
			}
		} else builder.append("\n" + "Found empty results for the filter Values");
		
		results.put("Total", String.valueOf(filterTDS.rows()));
		results.put("Title", builder.toString());
		return results;
	}
	
	/**
	 * Table feature: Sort the records ascending or descending
	 * Options:
	 * 		With Column Name
	 */
	public void sortRecordsWithColumnName(String columnName, boolean asc) {
		if (columnName == null) return;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return;
		}
		sortRecordsWithColumnIndex(colIndex, asc);
	}
	
	/**
	 * Table feature: Sort the records ascending or descending
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public void sortRecordsWithColumnIndex(int colIndex, boolean asc) {
		SortTDSWithColIndex comparator = new SortTDSWithColIndex(colIndex);
		if (asc) Collections.sort(getRecords(), comparator);
		else Collections.sort(getRecords(), Collections.reverseOrder(comparator));
	}
	
	/**
	 * Table feature: Duplicate table
	 * 
	 */
	public ArrayListTDS cloneTDS() {
		ArrayListTDS newObject = new ArrayListTDS();
		newObject.setSource(this.source);
		
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
	
	/**
	 * Validates the data in TDS
	 * 
	 */
	public String validateTDS() {
		StringBuilder builder = new StringBuilder("Validate ArrayListTDS - " + getSource());
		builder.append("\n");
		builder.append("[rows=" + rows() + ", columns=" + columns() + "]");
		builder.append("\n"); builder.append("\t");
		
		if (columnHeaders.size() == 0) builder.append("1. Column Headers are Empty");
		else builder.append("1. Column Headers are not Empty");
		
		int nullHeaderCount = 0;
		builder.append("\n"); builder.append("\t");
		builder.append("2. Null Column Header Numbers: ");
		for (String header : columnHeaders) {
			nullHeaderCount++;
			if (header == null) builder.append(nullHeaderCount + ", ");
		}
		
		int recCount = 1;
		int nullRecordsCount = 0;
		StringBuilder nullRecords = new StringBuilder("4. Null Record Numbers: ");
		Iterator<List<String>> itr2 = records.iterator();
		while (itr2.hasNext()) {
			List<String> record = (List<String>) itr2.next();
			if (record == null) { nullRecords.append(recCount + ", "); nullRecordsCount++; }
			recCount++;
		}
		builder.append("\n"); builder.append("\t");
		builder.append("3. Null Records count: " + nullRecordsCount);
		builder.append("\n"); builder.append("\t");
		builder.append(nullRecords.toString());

		return builder.toString();
	}
	
	/**
	 * Removes all the Null records in the TDS
	 * 
	 */
	public boolean removeNullRecords() {
		return records.removeAll(Collections.singleton(null));
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ArrayListTDS - " + getSource());
		builder.append("\n");
		builder.append("[rows=" + rows() + ", columns=" + columns() + "]");
		builder.append("\n");
		builder.append("columnHeaders=" + getColumnHeaders());
		builder.append("\n");
		builder.append("Values= [");
		builder.append("\n");
		int k = 0;
		Iterator<List<String>> itr = getRecords().iterator();
		while (itr.hasNext()) {
			k++;
			builder.append(k + " : ");
			List<String> record = (List<String>) itr.next();
			builder.append(record);
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
}

class SortTDSWithColIndex implements Comparator<List<String>>
{
	int index;
	public SortTDSWithColIndex(int index) {
		this.index = index;
	}
	
	public int compare(List<String> a, List<String> b) { 
        return a.get(index).compareTo(b.get(index));
    }
}