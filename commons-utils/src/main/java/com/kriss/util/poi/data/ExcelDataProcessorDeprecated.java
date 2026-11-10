package com.kriss.util.poi.data;

import java.util.Arrays;

import com.kriss.collection.adt.deprecated.TabularDS;
import com.kriss.util.poi.data.model.ExcelDataMapDeprecated;
import com.kriss.util.poi.data.model.RowDataMapDeprecated;

public class ExcelDataProcessorDeprecated {
	
	/*
	 * public TabularDS filterExcelData(TabularDS tds, int column, List<String>
	 * filterValues) { if (filterValues == null) return null; TabularDS results =
	 * null; for(String str : filterValues) { results = tds.filterValues(column,
	 * str); System.out.println(results); tds = results; } return results; }
	 */

	public ExcelDataMapDeprecated processExcelDataToMap(TabularDS tds, boolean sorted) {
		
		ExcelDataMapDeprecated data = null;
		if (tds == null) return null;
		
		data = new ExcelDataMapDeprecated();
		data.headers = Arrays.asList(tds.getColumnHeaders());
		
		int identIndex = 0;
		if (tds.getValues() != null) {
			for(int i=0; i<tds.rows; i++) {
				RowDataMapDeprecated row = new RowDataMapDeprecated(sorted);
				row.setIdentifier(tds.getColumnHeaders()[identIndex]);
				row.setValue((String) tds.getValue(i, identIndex));
				for(int j=0; j<tds.columns; j++) {
					/** Enable this if you do not want the Identifier as Attribute **/
					// if (j == identIndex) continue;
					row.attributes.put(tds.getColumnHeaders()[j], getObjectValue(tds.getValue(i, j)));
				}
				data.rows.add(row);
			}
		}
		return data;
	}
	
	private Object getObjectValue(Object data) {
		if (data == null) return null;
		if (data instanceof String) {
			String str = (String) data;
			boolean multiple = str.contains(", ");
			if (multiple) return str.split(", ");
			else return str;
		}
		return data;
	}
}
