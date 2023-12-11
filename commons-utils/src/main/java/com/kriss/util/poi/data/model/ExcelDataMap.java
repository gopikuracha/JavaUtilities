package com.kriss.util.poi.data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExcelDataMap {

	public List<String> headers;
	
	public List<RowDataMap> rows;
	
	public ExcelDataMap() {
		this.headers = new ArrayList<String>();
		this.rows = new ArrayList<RowDataMap>();
	}
	
	
	public List<RowDataMap> getDataWithAttributeName(String attName, String attValue) {
		if (attName == null || "".equalsIgnoreCase(attName)) return null;
		if (attValue == null || "".equalsIgnoreCase(attValue)) return null;
		
		List<RowDataMap> results = new ArrayList<RowDataMap>();
		Iterator<RowDataMap> iter = rows.iterator();
		while(iter.hasNext()) {
			RowDataMap row = iter.next();
			Object obj = row.getAttributes().get(attName);
			if (obj instanceof String[]) {
				String[] strs = (String[]) obj;
				for (String str : strs) {
					if (attValue.equalsIgnoreCase(str)) {
						results.add(row);
						break;
					}
				}
			} else if (obj instanceof String) {
				String str = (String) obj;
				if (attValue.equalsIgnoreCase(str)) results.add(row);
			}
		}
		if (results.size() == 0) return null;
		return results;
	}
	
	// TODO - Finish this method to filter the results based on multiple Strings
	/**
	public List<RowData> filterDataWithAttributeValueArray(String attName, String[] attValue) {
		if (attName == null || "".equalsIgnoreCase(attName)) return null;
		if (attValue == null || "".equalsIgnoreCase(attValue)) return null;
		
		List<RowData> results = new ArrayList<RowData>();
		Iterator<RowData> iter = rows.iterator();
		while(iter.hasNext()) {
			RowData row = iter.next();
			Object obj = row.getAttributes().get(attName);
			if (obj instanceof String[]) {
				String[] strs = (String[]) obj;
				for (String str : strs) {
					if (attValue.equalsIgnoreCase(str)) {
						results.add(row);
						break;
					}
				}
			} else if (obj instanceof String) {
				String str = (String) obj;
				if (attValue.equalsIgnoreCase(str)) results.add(row);
			}
		}
		if (results.size() == 0) return null;
		return results;
	} **/
	
	
	public List<String> getKeyValueWithAttributeName(String attName, String attValue, String key) {
		if (attName == null || "".equalsIgnoreCase(attName)) return null;
		if (attValue == null || "".equalsIgnoreCase(attValue)) return null;
		if (key == null || "".equalsIgnoreCase(key)) return null;
		
		List<String> results = new ArrayList<String>();
		Iterator<RowDataMap> iter = rows.iterator();
		while(iter.hasNext()) {
			RowDataMap row = iter.next();
			Object obj = row.getAttributes().get(attName);
			if (obj instanceof String[]) {
				String[] strs = (String[]) obj;
				
				for (String str : strs) {
					if (attValue.equalsIgnoreCase(str)) {
						Object obj2 = row.getAttributes().get(key);
						if (obj2 instanceof String[]) {
							String[] str2 = (String[]) obj2;
							results.add(Arrays.toString(str2));
						} else if (obj2 instanceof String) results.add((String) obj2);
						break;
					}
				}
			} else if (obj instanceof String) {
				String str = (String) obj;
				if (attValue.equalsIgnoreCase(str)) {
					Object obj2 = row.getAttributes().get(key);
					if (obj2 instanceof String[]) {
						String[] str2 = (String[]) obj2;
						results.add(Arrays.toString(str2));
					} else if (obj2 instanceof String) results.add((String) obj2);
				}
			}
		}
		if (results.size() == 0) return null;
		return results;
	}
	
	
	public List<RowDataMap> getDataWithIdentifierValue(String identifier, String value) {
		if (identifier == null || "".equalsIgnoreCase(identifier)) return null;
		if (value == null || "".equalsIgnoreCase(value)) return null;
		
		List<RowDataMap> results = new ArrayList<RowDataMap>();
		Iterator<RowDataMap> iter = rows.iterator();
		while(iter.hasNext()) {
			RowDataMap row = iter.next();
			if (value.equalsIgnoreCase(row.getValue())) results.add(row);
		}
		if (results.size() == 0) return null;
		return results;
	}
	
	
	public List<String> getKeyDataWithIdentifierValue(String identifier, String value, String key) {
		if (identifier == null || "".equalsIgnoreCase(identifier)) return null;
		if (value == null || "".equalsIgnoreCase(value)) return null;
		if (key == null || "".equalsIgnoreCase(key)) return null;
		
		List<String> results = new ArrayList<String>();
		Iterator<RowDataMap> iter = rows.iterator();
		while(iter.hasNext()) {
			RowDataMap row = iter.next();
			if (value.equalsIgnoreCase(row.getValue())) {
				Object obj = row.getAttributes().get(key);
				if (obj instanceof String[]) {
					String[] str = (String[]) obj;
					results.add(Arrays.toString(str));
				} else if (obj instanceof String) results.add((String) obj);
			}
		}
		if (results.size() == 0) return null;
		return results;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ExcelData ");
		builder.append("\n");
		builder.append("[headers=" + headers + "]");
		builder.append("\n");
		builder.append("rows= [" + rows + "]");
		return builder.toString();
	}
}
