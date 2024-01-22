package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author krishgo
 * @apiNote: 
 * 		- Stores the values of table items in a Tabular fashion.
 *		- Row and Column indexes start from 0
 *
 */
public class StaticTDS extends TabularDS {

	private Object[][] values;
	
	public StaticTDS() {}
	
	public StaticTDS(int rows, int columns, boolean hasHeader) {
		super(rows, columns, hasHeader);
		values = new Object[rows][columns];
	}
	
	/*
	 * public Object iterate() { int k = 0; if (hasHeaders) k++; if (values != null)
	 * { for(int i=0; i<rows; i++) { builder.append(k+1 + " : ["); for(int j=0;
	 * j<columns; j++) { if(j != 0) builder.append(" ,");
	 * builder.append(values[i][j]); } builder.append("]"); builder.append("\n");
	 * k++; } } }
	 */
	
	@Override
	public Object[][] getValues() {
		return values;
	}

	public void setValues(Object[][] values) {
		this.values = values;
	}
	
	@Override
	public Object getValue(int row, int column) {
		return this.values[row][column];
	}
	
	@Override
	public void setValue(int row, int column, Object obj) {
		this.values[row][column] = obj;
	}
	
	@Override
	public void append(TabularDS tds) {
		if(tds == null || tds.getColumnHeaders() == null) return;
		if (this.columnHeaders == null) return;
		if (this.columnHeaders.length != tds.getColumnHeaders().length) {
			System.out.println("TDS does not match");
			return;
		}

		//TODO
	}
	
	@Override
	public int getColumnIndex(String columnName) {
		if (columnName == null) return -1;
		int count = 0;
		for (String str : this.columnHeaders) {
			if (columnName.equalsIgnoreCase(str)) break;
			count++;
		}
		if (count == columnHeaders.length) {
			System.out.println("Did not find the Column with Name: " + columnName);
			return -1;
		}
		return count;
	}
	
	@Override
	public TabularDS filterValuesWithColumnNames(String columnName, String filterValue) {
		int index = getColumnIndex(columnName);
		if(index == -1) return null;
		return filterValues(index, filterValue);
	}
	
	@Override
	public TabularDS filterValues(int column, String filterValue) {
		if (filterValue == null || filterValue.equalsIgnoreCase("")) return null;
		if (this.getValues() == null) return null;
		
		// Get the count of records that match the filter
		int count = 0;
		for(int i=0; i<this.rows; i++) {
			Object obj = this.getValue(i, column);
			String str = (String) obj;
			if (filterValue.equalsIgnoreCase(str)) count++;
		}
		
		TabularDS results = new StaticTDS(count, this.columns, this.hasHeaders);
		results.setColumnHeaders(this.getColumnHeaders());
		
		int k = 0;
		for(int i=0; i<this.rows; i++) {
			Object obj = this.getValue(i, column);
			String str = (String) obj;
			if (filterValue.equalsIgnoreCase(str)) {
				for(int j=0; j<this.columns; j++) {
					results.setValue(k, j, this.getValue(i, j));
				}
				k++;
			}
		}
		return results;
	}
	
	@Override
	public TabularDS filterValuesWithColumnNames(String columnName, List<String> filterValue) {
		int index = getColumnIndex(columnName);
		if(index == -1) return null;
		return filterValues(index, filterValue);
	}
	
	@Override
	public TabularDS filterValues(int column, List<String> filterValues) {
		if (filterValues == null || filterValues.size() == 0) return null;
		if (this.getValues() == null) return null;
		
		// Get the count of records that match the filter
		int count = 0;
		for(int i=0; i<this.rows; i++) {
			Object obj = this.getValue(i, column);
			String str = (String) obj;
			if (filterValues.contains(str)) count++;
		}
		
		TabularDS results = new StaticTDS(count, this.columns, this.hasHeaders);
		results.setColumnHeaders(this.getColumnHeaders());
		
		int k = 0;
		for(int i=0; i<this.rows; i++) {
			Object obj = this.getValue(i, column);
			String str = (String) obj;
			if (filterValues.contains(str)) {
				for(int j=0; j<this.columns; j++) {
					results.setValue(k, j, this.getValue(i, j));
				}
				k++;
			}
		}
		return results;
	}
	
	@Override
	public TabularDS filterColumnsWithColumnNames(List<String> columnNames) {
		if (columnNames == null) return null;
		List<Integer> columnNumbers = new ArrayList<Integer>();
		for (String str : columnNames) {
			int index = getColumnIndex(str);
			if (index == -1) continue;
			columnNumbers.add(index);
		}
		return filterColumns(columnNames, columnNumbers);
	}
	
	@Override
	public TabularDS filterColumns(List<Integer> columnNumbers) {
		return filterColumns(null, columnNumbers);
	}
	
	@Override
	public TabularDS filterColumns(List<String> columnNames, List<Integer> columnNumbers) {
		if (columnNumbers == null) return null;
		if (this.getValues() == null) return null;
		
		TabularDS results = new StaticTDS(this.rows, columnNumbers.size(), this.hasHeaders);
		String[] str = new String[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            str[i] = columnNames.get(i);
        }
		results.setColumnHeaders(str);
		
		for(int i=0; i<this.rows; i++) {
			int k = 0;
			for(int j=0; j<this.columns; j++) {
				if(columnNumbers.contains(j)) {
					results.setValue(i, k, this.getValue(i, j));
					k++;
				}
			}
		}
		return results;
	}
	
	@Override
	public Set<String> getUniqueValues(int columnNumber) {
		if (this.getValues() == null) return null;
		
		Set<String> results = new HashSet<String>();
		for(int i=0; i<this.rows; i++) {
			for(int j=0; j<this.columns; j++) {
				if(j == columnNumber) {
					results.add((String) this.getValue(i, j));
				}
			}
		}
		return results;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("TabularDS ");
		builder.append("[rows=" + rows + ", columns=" + columns + ", columnHeaders=" + Arrays.toString(columnHeaders) + "]");
		builder.append("\n");
		builder.append("Values= [");
		builder.append("\n");
		int k = 0;
		if (hasHeaders) k++;
		if (values != null) {
			for(int i=0; i<rows; i++) {
				builder.append(k+1 + " : [");
				for(int j=0; j<columns; j++) {
					if(j != 0) builder.append(" ,");
					builder.append(values[i][j]);
				}
				builder.append("]");
				builder.append("\n");
				k++;
			}
		}
		builder.append("]");
		return builder.toString();
	}
	
}
