package com.kriss.collection.adt;

import java.util.List;
import java.util.Set;

public class TabularDS {
	
	/**
	 * 	The total # of table rows
	 */
	public int rows;

	/**
	 * 	The total # of table columns
	 */
	public int columns;
	
	/**
	 * 	boolean value if table has Headers
	 */
	public boolean hasHeaders;

	/**
	 * 	The table Column Headers
	 */
	public String[] columnHeaders;
	
	public TabularDS() {}
	
	public TabularDS(int columns, boolean hasHeader) {
		if(columns == 0) {
			System.out.println("Invalid Columns Data...");
			return;
		}
		this.columns = columns;
		if(hasHeader) {
			hasHeaders = Boolean.TRUE;
			columnHeaders = new String[columns];
		}
	}
	
	public TabularDS(int rows, int columns, boolean hasHeader) {
		if(rows == 0 || columns == 0) {
			System.out.println("Invalid input Data...");
			return;
		}
		this.rows = rows;
		this.columns = columns;
		if(hasHeader) {
			hasHeaders = Boolean.TRUE;
			columnHeaders = new String[columns];
		}
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public boolean isHasHeaders() {
		return hasHeaders;
	}

	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}
	
	public String[] getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}
	
	public Object getValue(int row, int column) {
		return 0;
	}
	
	public void setValue(int row, int column, Object obj) {
		
	}
	
	public Object getValues() { 
		return null;
	}
	
	public void append(TabularDS tds) {}
	
	public int getColumnIndex(String columnName) {
		return -1;
	}
	
	public TabularDS filterValuesWithColumnNames(String columnName, String filterValue) {
		return null;
	}
	
	public TabularDS filterValues(int column, String filterValue) {
		return null;
	}
	
	public TabularDS filterValuesWithColumnNames(String columnName, List<String> filterValue) {
		return null;
	}
	
	public TabularDS filterValues(int column, List<String> filterValue) {
		return null;
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
	
	public Set<String> getUniqueValues(int columnNumbers) {
		return null;
	}
}
