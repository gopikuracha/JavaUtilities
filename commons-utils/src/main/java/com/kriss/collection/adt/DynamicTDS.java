package com.kriss.collection.adt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author krishgo
 *	
 *	Row index starts from 0
 *	# of Rows are determined based on the columns and values size
 * 
 */
public class DynamicTDS extends TabularDS {
	
	/**
	 * 	The List of table items in a linear fashion, determined based on the total # of columns
	 */
	public List<Object> values = new ArrayList<Object>();
	
	public DynamicTDS() {}
	
	public DynamicTDS(int columns, boolean hasHeader) {
		super(columns, hasHeader);
	}

	@Override
	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	@Override
	public Object getValue(int row, int column) {
		int index = row*columns + column;
		return values.get(index);
	}
	
	@Override
	public void setValue(int row, int column, Object obj) {
		this.rows = row+1;
		int index = row*columns + column;
		this.values.add(index, obj);
	}

	/**
	 * 	Generates the total # of rows based on the total # of columns
	 * 	@return the total # of rows
	 */
	public int generateRows() {
		if (columns == 0) return 0;
		if (values == null) return 0;
		return (values.size()) / columns;
	}
	
	
	/**
	 * 	@param index - The current item index in the values list
	 * 	@return the row index to which the current item belongs to
	 * 
	 * 	Note: Row index starts from 0
	 */
	public int getCurrentRow(int index) {
		if (index == 0) return 0;
		return index / columns;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("DynamicTDS ");
		builder.append("[rows=" + rows + ", columns=" + columns + ", columnHeaders=" + Arrays.toString(columnHeaders) + "]");
		builder.append("\n");
		builder.append("Values= [");
		builder.append("\n");
		int columnCounter = 0;
		int rowIndex = -1;
		if (values != null) {
			for(Object obj : values) {
				columnCounter++;
				if(columnCounter == 1) { 
					rowIndex++;
					builder.append(rowIndex + " : [");
				}
				if (obj == null ) builder.append(obj);
				else builder.append(obj.toString());
				if(columnCounter == columns) {
					columnCounter = 0;
					builder.append("]");
					builder.append("\n");
				} else builder.append(" ,");
			}
		}
		builder.append("]");
		return builder.toString();
	}
}

