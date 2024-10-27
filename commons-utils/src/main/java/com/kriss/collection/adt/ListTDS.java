package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.kriss.util.DateUtil;

public class ListTDS<T> {
	
	private String defaultDateFormat = "MM-dd-yyyy HH:mm:ss";
	
	/**
	 * 	The name of the Input source
	 */
	protected String source;
	
	/**
	 * 	The name of the Input source
	 */
	protected String transHistory;
	
	/**
	 * 	boolean value if table has Headers
	 */
	protected boolean hasHeaders;

	/**
	 * 	The table Column Headers
	 */
	protected List<String> columnHeaders;
	
	/**
	 * 	The table Column Headers
	 */
	protected List<String> columnTypes;
	
	/**
	 * 	The table Values
	 */
	protected List<List<T>> records;
	
	/**
	 * 	Custom date formats for toString() method
	 */
	protected Map<String, String> dateFormats;

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

	public String getTransHistory() {
		return transHistory;
	}

	public void setTransHistory(String transHistory) {
		this.transHistory = transHistory;
	}
	
	public void updateTransHistory(String transHistory, String update) {
		if (transHistory == null ) this.transHistory = "\t" + update;
		else this.transHistory = transHistory + "\n" + "\t" + update;
	}
	
	public void updateTransHistory(String transHistory, String update, String secondTransHistory) {
		if (transHistory == null ) this.transHistory = "\t" + update;
		else this.transHistory = transHistory + "\n" + "\t" + update;
		if (secondTransHistory != null) this.transHistory = this.transHistory + "\n" + secondTransHistory;
	}

	public boolean isHasHeaders() {
		return hasHeaders;
	}

	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}

	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> columns) {
		if (columns == null || columns.size() == 0) return;
		hasHeaders = true;
		if (columnHeaders.size() == 0) columnHeaders.addAll(columns);
	}

	public List<String> getColumnTypes() {
		return columnTypes;
	}

	public void setColumnTypes(List<String> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public List<List<T>> getRecords() {
		return records;
	}

	public boolean setRecords(List<List<T>> records) {
		if (records == null || records.size() == 0) return false;
		return this.records.addAll(records);
	}
	
	public Map<String, String> getDateFormats() {
		return dateFormats;
	}

	public void setDateFormats(Map<String, String> dateFormats) {
		this.dateFormats = dateFormats;
	}

	public String getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public void setDefaultDateFormat(String defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

	public ListTDS() {
		records = new ArrayList<List<T>>();
		columnHeaders = new ArrayList<String>();
		dateFormats = new HashMap<String, String>();
	}
	
	/**
	 * Input values are actual Table indexes and not Java indexes
	 */
	public T getValue(int row, int column) {
		if (row > records.size()) { 
			System.out.println("The Input row index is greater than the Table Rows");
			return null;
		}
		List<T> record = records.get(row-1);
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
	 * Input values are actual Table indexes and not Java indexes
	 */
	public String getStringValue(int row, int column) {
		Object obj = getValue(row, column);
		return (obj == null) ? null : (String) obj;
	}
	
	/**
	 * Table feature: Update table data
	 * Input values are actual Table indexes and not Java indexes
	 */
	public boolean setValue(int row, int column, T obj) {
		if (row > records.size()) {
			System.out.println("The Input row index is greater than the Table Rows");
			return false;
		}
		List<T> record = records.get(row-1);
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
	public boolean addRecord(List<T> record) {
		return records.add(record);
	}
	
	/**
	 * Table feature: Insert a new row in the middle
	 * Input values are actual Table indexes and not Java indexes
	 * 
	 */
	public boolean insertRecord(List<T> record, int rowIndex) {
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
	public List<T> cutRecord(int rowIndex) {
		if (rowIndex > records.size()) {
			System.out.println("Cut rowIndex is greater than the TDS rows size.");
			return null;
		}
		return records.remove(rowIndex-1);
	}
	
	/**
	 * Table feature: Copy Column Data
	 * Options:
	 * 		With Column Name (Case sensitive ?)
	 */
	public List<Object> getColumnData(String columnName) {
		int colIndex = 0;
		if (columnName == null) return null;
		colIndex = columnHeaders.indexOf(columnName);
		return getColumnData(colIndex, columnName);
	}
	
	/**
	 * Table feature: Copy Column Data
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public List<Object> getColumnData(int colIndex) {
		if (colIndex > columnHeaders.size()) {
			System.out.println("colIndex is greater than the TDS Columns size.");
			return null;
		}
		String columnName = null;
		columnName = columnHeaders.get(colIndex-1);
		return getColumnData(colIndex-1, columnName);
	}
	
	private List<Object> getColumnData(int colIndex, String columnName) {
		List<Object> columnData = null;
		if (colIndex == -1) {
			System.out.println("No matching Column Headers found.");
			return null;
		}
		if (columnName == null) {
			System.out.println("No matching Column Headers found.");
			return null;
		}
		columnData = new ArrayList<Object>();
		columnData.add(columnName);
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) columnData.add(null);
			else columnData.add(record.get(colIndex));
		}
		return columnData;
	}
	
	/**
	 * Table feature: Add/Insert a new Column at the end
	 */
	public boolean addNewColumn(List<T> columnData) {
		return addNewColumnWithIndex(columnData, 0);
	}
	
	/**
	 * Table feature: Add/Insert a new Column
	 * 		(colIndex = 0) for Add; (colIndex = insert Index, starts with 1) for Insert;
	 */
	public boolean addNewColumnWithIndex(List<T> columnData, int colIndex) {
		colIndex = colIndex-1;
		if (columnData == null || columnData.size() == 0) return false;
		if (records.size() == 0) return false;
		if (columnData.size() > records.size()+1) {
			System.out.println("The Input data is greater than the Table Rows");
			return false;
		}
		if (colIndex > columns()) return false;
		
		String columnName = (String) columnData.get(0);
		updateTransHistory(transHistory, "- Added new Column: " + columnName);
		if (colIndex == -1) columnHeaders.add(columnName);
		else columnHeaders.add(colIndex, columnName);

		for (int i=0; i<rows(); i++) {
			List<T> record = records.get(i);
			if (record == null) continue;
			T obj = null;
			if (columnData.size() > i+1) obj = columnData.get(i+1);
			if (colIndex == -1) record.add(obj);
			else record.add(colIndex, obj);
		}
		return true;
	}
	
	/**
	 * Table feature: Generate a new Column
	 */
	public boolean generateNewDateColumnAtTheEnd(String columnName, String newColumnName, String dateFormat) {
		return generateNewDateColumn(columnName, newColumnName, 0, dateFormat);
	}
	
	/**
	 * Table feature: Generate a new Column
	 */
	public boolean generateNewDateColumn(String columnName, String newColumnName, String dateFormat) {
		int colIndex = columnHeaders.indexOf(columnName);
		return generateNewDateColumn(columnName, newColumnName, colIndex+2, dateFormat);
	}
	
	/**
	 * Table feature: Generate a new Column
	 * 		(colIndex = 0) for Add; (colIndex = insert Index, starts with 1) for Insert;
	 */
	public boolean generateNewDateColumn(String columnName, String newColumnName, int newColIndex, String dateFormat) {
		newColIndex = newColIndex-1;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return false;
		}
		if (newColIndex > columns()) return false;
		if (records.size() == 0) return false;
		
		for (int i=0; i<rows(); i++) {
			List<T> record = records.get(i);
			if (record == null) continue;
			T obj = record.get(colIndex);
			if (obj == null || !(obj instanceof Date)) { record.add(null); continue; }
			Date date = (Date) obj;
			T newDate = (T) DateUtil.formatDate(date, dateFormat);
			if (newColIndex == -1) record.add(newDate);
			else record.add(newColIndex, newDate);
		}
		updateTransHistory(transHistory, "- Generated new Date Column: " + newColumnName);
		if (newColIndex == -1) columnHeaders.add(newColumnName);
		else columnHeaders.add(newColIndex, newColumnName);
		dateFormats.put(newColumnName, dateFormat);
		return true;
	}
	
	/**
	 * Table feature: Add rows from other TDS at the end
	 * 
	 */
	public boolean appendRecords(ListTDS<T> secondTDS) {
		if (secondTDS == null) return false;
		if (secondTDS.rows() == 0) return false;
		if (secondTDS.getColumnHeaders() != null && secondTDS.getColumnHeaders().equals(columnHeaders)) {
			setSource(source + "\n" + "\t" + "Merged with..." + "\n" + "\t" + secondTDS.getSource());
			updateTransHistory(transHistory, "- Merged with...", secondTDS.getTransHistory());
			Iterator<List<T>> itr = secondTDS.getRecords().iterator();
			while (itr.hasNext()) {
				List<T> record = itr.next();
				if (record == null) continue;
				records.add(record);
			}
		} else return false;
		return true;
	}
	
	/**
	 * Table feature: Get Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 * 		Single Filter Value
	 */
	public ListTDS<T> getFilteredRecords(String columnName, Object filterValue) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getFilteredRecords(colIndex+1, filterValue);
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Single Filter Value
	 */
	public ListTDS<T> getFilteredRecords(int colIndex, Object filterValue) {
		colIndex = colIndex-1;
		if (filterValue == null) return null;
		ListTDS<T> tds = new ListTDS<T>();
		tds.setSource(source);
		tds.updateTransHistory(transHistory, "- Filtered Column: " + columnHeaders.get(colIndex) + " with values(s): " + filterValue);
		tds.setColumnHeaders(columnHeaders);

		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Object obj = record.get(colIndex);	
			if (obj != null && obj.equals(filterValue)) tds.getRecords().add(record);
		}
		return tds;
	}
	
	/**
	 * Table feature: Get Filtered Rows for a specific Date Column value
	 * 		With Column Name
	 * 		Single Filter Value
	 */
	public ListTDS<T> getFilteredRecordsForDateColumn(String columnName, Object filterValue, String dateFormat) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) { System.out.println("Did not find the Column with Name: " + columnName); return null; }
		return getFilteredRecordsForDateColumn(colIndex+1, filterValue, dateFormat);
	}
	
	/**
	 * Table feature: Get or Ignore Filtered Rows for a specific Date Column value
	 * 		With Column Index (starts with 1)
	 * 		Single Filter Value
	 */
	public ListTDS<T> getFilteredRecordsForDateColumn(int colIndex, Object filterValue, String dateFormat) {
		colIndex = colIndex-1;
		if (filterValue == null) return null;
		ListTDS<T> tds = null;
		tds = new ListTDS<T>();
		tds.setSource(source);
		tds.updateTransHistory(transHistory, "- Filtered Column: " + columnHeaders.get(colIndex) + " with values(s): " + filterValue);
		tds.setColumnHeaders(columnHeaders);

		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Date date = (Date) record.get(colIndex);
			date = DateUtil.formatDate(date, dateFormat);
			if (date != null && date.equals(filterValue)) tds.getRecords().add(record);
		}
		return tds;
	}
	
	/**
	 * Table feature: Ignore Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 * 		Single Filter Value
	 */
	public ListTDS<T> ignoreFilteredRecords(String columnName, Object filterValue) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return ignoreFilteredRecords(colIndex+1, filterValue);
	}
	
	/**
	 * Table feature: Ignore Filtered Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Single Filter Value
	 */
	public ListTDS<T> ignoreFilteredRecords(int colIndex, Object filterValue) {
		colIndex = colIndex-1;
		if (filterValue == null) return null;
		ListTDS<T> tds = null;
		tds = this.cloneTDS();
		tds.updateTransHistory(transHistory, "- Ignored Column: " + columnHeaders.get(colIndex) + " for values(s): " + filterValue);

		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Object obj = record.get(colIndex);	
			if (obj != null && obj.equals(filterValue)) tds.getRecords().remove(record);
		}
		return tds;
	}
	
	/**
	 * Table feature: Get Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 * 		Multiple Filter Value
	 */
	public ListTDS<T> getMultipleFilteredRecords(String columnName, List<Object> filterValues) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getMultipleFilteredRecords(colIndex+1, filterValues);
	}
	
	/**
	 * Table feature: Get Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Multiple Filter Value
	 */
	public ListTDS<T> getMultipleFilteredRecords(int colIndex, List<Object> filterValues) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return null;
		ListTDS<T> tds = new ListTDS<T>();
		tds.setSource(source);
		tds.updateTransHistory(transHistory, "- Filtered Column: " + columnHeaders.get(colIndex) + " with values(s): " + filterValues);
		tds.setColumnHeaders(columnHeaders);
		
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Object obj = record.get(colIndex);
			if (obj != null && filterValues.contains(obj)) tds.getRecords().add(record);
		}
		return tds;
	}
	
	/**
	 * Table feature: Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 * 		Multiple Filter Value
	 */
	public ListTDS<T> ignoreMultipleFilteredRecords(String columnName, List<Object> filterValues) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return ignoreMultipleFilteredRecords(colIndex+1, filterValues);
	}
	
	/**
	 * Table feature: Ignore Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Multiple Filter Value
	 */
	public ListTDS<T> ignoreMultipleFilteredRecords(int colIndex, List<Object> filterValues) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return null;
		ListTDS<T> tds = cloneTDS();
		tds.updateTransHistory(transHistory, "- Ignored Column: " + columnHeaders.get(colIndex) + " for values(s): " + filterValues);
		
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Object obj = record.get(colIndex);
			if (obj != null && filterValues.contains(obj)) tds.getRecords().remove(record);
		}
		return tds;
	}
	
	/**
	 * Table feature: Get wild card search Filtered Rows for specific Column value
	 * Options:
	 * 		With Column Name
	 * 		Multiple Filter Value - Wild card search
	 */
	public ListTDS<T> getFilteredRecordsWithWildCardSearch(String columnName, List<String> filterValues) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getFilteredRecordsWithWildCardSearch(colIndex+1, filterValues);
	}
	
	/**
	 * Table feature: Get wild card search Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Index (starts with 1)
	 * 		Multiple Filter Value - Wild card search
	 */
	public ListTDS<T> getFilteredRecordsWithWildCardSearch(int colIndex, List<String> filterValues) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return null;
		ListTDS<T> getTDS = new ListTDS<T>();
		getTDS.setSource(source);
		getTDS.updateTransHistory(transHistory, "- Filtered Column: " + columnHeaders.get(colIndex) + " with Wild Card Search values(s): " + filterValues);
		getTDS.setColumnHeaders(columnHeaders);
		
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			for (String filterValue : filterValues) {
				String recordValue = (String) record.get(colIndex);
				if (recordValue.contains(filterValue)) {
					getTDS.getRecords().add(record);
					break;
				}
			}
		}
		return getTDS;
	}
	
	/**
	 * Table feature: Get Null Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 */
	public ListTDS<T> getNullRecords(String columnName) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getNullRecords(colIndex+1);
	}
	
	/**
	 * Table feature: Get Null Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public ListTDS<T> getNullRecords(int colIndex) {
		colIndex = colIndex-1;
		
		ListTDS<T> tds = new ListTDS<T>();
		tds.setSource(source);
		tds.updateTransHistory(transHistory, "- Filtered Column: " + columnHeaders.get(colIndex) + " with Null values(s)");
		tds.setColumnHeaders(columnHeaders);
		
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			T obj = record.get(colIndex);
			if (obj == null ) {
				tds.getRecords().add(record);
			}
		}
		return tds;
	}
	
	/**
	 * Table feature: Ignore Null Rows for a specific Column value
	 * Options:
	 * 		With Column Name
	 */
	public ListTDS<T> ignoreNullRecords(String columnName) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return ignoreNullRecords(colIndex+1);
	}
	
	/**
	 * Table feature: Ignore Null Rows for a specific Column value
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public ListTDS<T> ignoreNullRecords(int colIndex) {
		colIndex = colIndex-1;
		
		ListTDS<T> tds = this.cloneTDS();
		tds.updateTransHistory(transHistory, "- Ignored Column: " + columnHeaders.get(colIndex) + " for Null values(s)");
		tds.setColumnHeaders(columnHeaders);
		
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			T obj = record.get(colIndex);
			if (obj == null ) {
				tds.getRecords().remove(record);
			}
		}
		return tds;
	}
	
	/**
	 * Table feature: Get Specific Columns data
	 * Options:
	 * 		With Column Names
	 */
	public ListTDS<T> getSpecificColumnsData(List<String> columnNames) {
		if (columnNames == null || columnNames.size() == 0) {
			System.out.println("Incorrect Column Data");
			return null;
		}
		ListTDS<T> newTDS = new ListTDS<T>();
		newTDS.setSource(source);
		newTDS.updateTransHistory(transHistory, "- Shortened Columns to: " + columnNames);
		for (String colName : columnNames) {
			int colIndex = columnHeaders.indexOf(colName);
			if (colIndex == -1) {
				System.out.println("Incorrect Column Name : " + colName);
				continue;
			}
			newTDS.getColumnHeaders().add(colName);
		}
		for (List<T> record : records) {
			if (record == null) continue;
			List<T> newRecord = new ArrayList<T>();
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
	public Set<Object> getUniqueValues(String columnName) {
		if (columnName == null) return null;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return null;
		}
		return getUniqueValues(columnName, colIndex+1);
	}
	
	/**
	 * Table feature: Get Distinct data for a Column
	 * Options:
	 * 		With Column Index (starts with 1)
	 */
	public Set<Object> getUniqueValues(String columnName, int colIndex) {
		colIndex = colIndex-1;
		Set<Object> results = new TreeSet<Object>();
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			T value = record.get(colIndex);
			if (value != null) {
				if (value instanceof Date) {
					Date date = (Date) value;
					String dateFormat = dateFormats.get(columnName);
					if (dateFormat == null) dateFormat = defaultDateFormat;
					date = DateUtil.formatDate(date, dateFormat);
					results.add(date);
				} else results.add(value);
			}
		}
		return results;
	}
	
	
	/**
	 * Get the count of Filtered Rows for specific Column values
	 * Options:
	 * 		With Column Name
	 */
	public int getFilteredRecordsCount(String columnName, Object filterValue) {
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
	public int getFilteredRecordsCount(int colIndex, Object filterValue) {
		colIndex = colIndex-1;
		if (filterValue == null) return -1;
		
		int filterCount = 0;
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
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
	public int getFilteredRecordsCount(String columnName, List<Object> filterValues) {
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
	public int getFilteredRecordsCount(int colIndex, List<Object> filterValues) {
		colIndex = colIndex-1;
		if (filterValues == null || filterValues.size() == 0) return -1;
		
		int filterCount = 0;
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			Object obj = record.get(colIndex);
			if (obj != null && filterValues.contains(obj)) {
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
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null || record.get(colIndex) == null) { 
				nullCount++;
			}
		}
		return nullCount;
	}
	
	/**
	 * Table feature: Get Specific Columns data
	 * Options:
	 * 		With Column Names
	 */
	public ListTDS<T> getRecordsForSpecificColumns(List<String> columnNames) {
		if (columnNames == null || columnNames.size() == 0) {
			System.out.println("Incorrect Column Data");
			return null;
		}
		ListTDS<T> newTDS = new ListTDS<T>();
		newTDS.setSource(source);
		newTDS.updateTransHistory(transHistory, "- Shortened Columns to: " + columnNames);
		for (String colName : columnNames) {
			int colIndex = columnHeaders.indexOf(colName);
			if (colIndex == -1) {
				System.out.println("Incorrect Column Name : " + colName);
				continue;
			}
			newTDS.getColumnHeaders().add(colName);
		}
		for (List<T> record : records) {
			if (record == null) continue;
			List<T> newRecord = new ArrayList<T>();
			for (String columnName : newTDS.getColumnHeaders()) {
				int colIndex = columnHeaders.indexOf(columnName);
				newRecord.add(record.get(colIndex));
			}
			newTDS.getRecords().add(newRecord);
		}
		return newTDS;
	}
	
	/**
	 * Returns a String of the Overview of the Tabular Data split by a each Column
	 * Options:
	 * 		Ignore the Columns that are not required in the Overview
	 */
	public String getDataOverview(List<String> skipColumns) {
		StringBuilder builder = new StringBuilder("--------- Data Overview Start ---------");
		for (String column : this.columnHeaders) {
			if (skipColumns != null && skipColumns.contains(column)) continue;
			builder.append("\n" + getRecordsSplitByAColumn(column) + "\n");
		}
		return builder.append("----- Data Overview End -----").toString();
	}
	
	/**
	 * Returns a String of the Overview of the Tabular Data split by a each Column
	 * Options:
	 * 		Ignore the Columns that are not required in the Overview
	 */
	public String getSpecificColumnDataOverview(List<String> columns) {
		StringBuilder builder = new StringBuilder("--------- Data Overview Start ---------");
		for (String column : this.columnHeaders) {
			if (columns != null && columns.contains(column)) builder.append("\n" + getRecordsSplitByAColumn(column) + "\n");
		}
		return builder.append("----- Data Overview End -----").toString();
	}
	
	/**
	 * Returns a String of the records split by a specific Column
	 * 
	 */
	public String getRecordsSplitByAColumn(String columnName) {
		if (columnName == null) return null;
		StringBuilder builder = new StringBuilder("Split Column: " + columnName);
		if (rows() != 0) {
			int dataCount = 0; 
			for (Object columnValue : getUniqueValues(columnName)) {
				int splitCount = getFilteredRecordsCount(columnName, columnValue);
				if (columnValue instanceof Date) {
					String columnValueString = DateUtil.getformatDate((Date)columnValue, dateFormats.get(columnName));
					builder.append("\n" + "\t" + columnValueString + ": " + splitCount);
				}
				else builder.append("\n" + "\t" + columnValue + ": " + splitCount);
				dataCount += splitCount;
			}
			if (rows() != dataCount) builder.append("\n" + "\t" + "NULL" + ": " + getNullRecordsCount(columnName));
		} else builder.append("\n" + "Found empty results.");
		return builder.toString();
	}
	
	/**
	 * Returns a Map of the records split by a specific Column
	 * 
	 */
	public Map<String, Integer> getMapOfRecordsSplitByAColumn(String columnName) {
		if (columnName == null) return null;
		StringBuilder builder = new StringBuilder(transHistory + "\n" + "\t" + "- Split Column: " + columnName);
		Map<String, Integer> results = new LinkedHashMap<String, Integer>();
		if (rows() != 0) {
			int dataCount = 0; 
			for (Object columnValue : getUniqueValues(columnName)) {
				int splitCount = getFilteredRecordsCount(columnName, columnValue);
				results.put((String) columnValue, Integer.valueOf(splitCount));
				builder.append("\n" + "\t" + columnValue + ": " + splitCount);
				dataCount += splitCount;
			}
			if (rows() != dataCount) {
				int nullCount = getNullRecordsCount(columnName);
				results.put("NULL", Integer.valueOf(nullCount));
				builder.append("\n" + "\t" + "NULL: " + nullCount);
			}
		} else builder.append("\n" + "Found empty results.");
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
		SortTDSWithColIndex4<T> comparator = new SortTDSWithColIndex4<T>(colIndex);
		if (asc) Collections.sort(getRecords(), comparator);
		else Collections.sort(getRecords(), Collections.reverseOrder(comparator));
	}
	
	// TODO - Finish the Deep Clone 
	/**
	 * Table feature: Duplicate table
	 * 
	 */
	public ListTDS<T> cloneTDS() {
		ListTDS<T> newObject = new ListTDS<T>();
		newObject.setSource(source);
		newObject.setTransHistory(transHistory);
		newObject.setColumnHeaders(columnHeaders);
		newObject.setRecords(records);
		
		/*
		 * List<List<T>> newRecs = newObject.getRecords(); Iterator<List<T>> itr2 =
		 * records.iterator(); while (itr2.hasNext()) { List<T> record = itr2.next(); if
		 * (record == null) { newRecs.add(null); continue; } List<T> newRec = new
		 * ArrayList<T>(); Iterator<T> itr3 = record.iterator(); while (itr3.hasNext())
		 * { Object rec = itr3.next(); newRec.add(rec); } newRecs.add(newRec); }
		 */
		return newObject;
	}
	
	/**
	 * Validates the data in TDS
	 * 
	 */
	public String validateTDS() {
		StringBuilder builder = new StringBuilder("Validate TDS: " + this.getClass() + " | Source: " + getSource());
		if (getTransHistory() != null) builder.append("\n" + getTransHistory());
		builder.append("\n" + "[rows=" + rows() + ", columns=" + columns() + "]");
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
		Iterator<List<T>> itr2 = records.iterator();
		while (itr2.hasNext()) {
			List<T> record = itr2.next();
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
	 */
	public boolean removeNullRecords() {
		return records.removeAll(Collections.singleton(null));
	}
	
	/**
	 * Trim all the extra spaces in each cell of each record in the TDS
	 */
	public void trimAllRecords() {
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			for (int i=0; i<record.size(); i++) {
				T obj = (T) record.get(i);
				if (obj == null) continue;
				if (obj instanceof String) {
					String str = (String) obj;
					str = str.trim();
					record.set(i, (T) str);
				}
			}
		}
	}
	
	/**
	 * Trim all the extra spaces for a specific Column data in the TDS
	 * 
	 */
	public void trimRecordsForASpecificColumn(String columnName) {
		if (columnName == null) return;
		int colIndex = columnHeaders.indexOf(columnName);
		if (colIndex == -1) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return;
		}
		Iterator<List<T>> itr = records.iterator();
		while (itr.hasNext()) {
			List<T> record = itr.next();
			if (record == null) continue;
			T obj = (T) record.get(colIndex);
			if (obj == null) continue;
			if (obj instanceof String) {
				String str = (String) obj;
				str = str.trim();
				record.set(colIndex, (T) str);
			}
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("TDS: " + this.getClass() + "\n" + "Source: " + getSource());
		if (getTransHistory() != null) builder.append("\n" + getTransHistory());
		builder.append("\n" + "[rows=" + rows() + ", columns=" + columns() + "]");
		builder.append("\n");
		builder.append("columnHeaders=" + getColumnHeaders());
		builder.append("\n");
		builder.append("dateFormats=" + getDateFormats());
		builder.append("\n");
		builder.append("Values= [");
		builder.append("\n");
		int k = 0;
		Iterator<List<T>> itr = getRecords().iterator();
		while (itr.hasNext()) {
			k++;
			builder.append(k + " : ");
			List<T> record = itr.next();
			if (record == null) builder.append(record);
			else builder.append(getRecordString(record));
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public String getRecordString(List<T> record) {
		StringBuilder builder = new StringBuilder("[");
		int index = 0;
		for (T obj : record) {
			if (obj == null) builder.append("null");
			else {
				if (obj instanceof Date) {
					Date date = (Date) obj;
					String columnName = getColumnHeaders().get(index);
					String dateFormat = dateFormats.get(columnName);
					// if (dateFormat == null) dateFormat = defaultDateFormat;
					builder.append(DateUtil.getformatDate(date, dateFormat));
				}
				else builder.append(obj.toString());
			}
			
			if (index+1 != columnHeaders.size()) builder.append(", ");
			index++;
		}
		return builder.append("]").toString();
	}

}

class SortTDSWithColIndex4<T> implements Comparator<List<T>>
{
	int index;
	public SortTDSWithColIndex4(int index) {
		this.index = index;
	}
	
	public int compare(List<T> a, List<T> b) {
		T obj1 = a.get(index);
		T obj2 = b.get(index);
		if (obj1 instanceof String) {
			return ((String) obj1).compareTo((String) obj2);
		} else if (obj1 instanceof Date) {
			return ((Date) obj1).compareTo((Date) obj2);
		}
        return 0;
    }
}
