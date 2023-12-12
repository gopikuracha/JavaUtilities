package com.kriss.collection.adt;

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
	public void setValue(int row, int column, Object obj) {
		this.values[row][column] = obj;
	}
	
	@Override
	public Object getValue(int row, int column) {
		return this.values[row][column];
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
	public TabularDS filterValues(int column, List<String> filterValues) {
		if (filterValues == null) return null;
		if (this.getValues() == null) return null;
		
		// Get the count of records that match the filter
		int count = 0;
		for(int i=0; i<this.rows; i++) {
			Object obj = this.getValue(i, column);
			String str = (String) obj;
			if (filterValues.contains(str)) count++;
		}
		
		TabularDS results = new StaticTDS(count, this.columns, this.hasHeaders);
		
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
	public TabularDS filterColumns(List<Integer> columnNumbers) {
		if (columnNumbers == null) return null;
		if (this.getValues() == null) return null;
		
		TabularDS results = new StaticTDS(this.rows, columnNumbers.size(), this.hasHeaders);
		
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
	
	@Override
	public String printSpecificColumns() {
		StringBuilder builder = new StringBuilder("Limited Column TabularDS:");
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
