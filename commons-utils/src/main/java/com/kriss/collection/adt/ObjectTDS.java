package com.kriss.collection.adt;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectTDS<T> extends TabularDS {
	
	public List<T> values = new ArrayList<T>();
	
	public ObjectTDS(T obj) {}
	
	public ObjectTDS(T obj, boolean hasHeader) {
		super(obj.getClass().getDeclaredFields().length, hasHeader);
	}
	
	@Override
	public List<T> getValues() {
		return values;
	}
	
	public void setValues(List<T> values) {
		this.values = values;
	}
	
	@Override
	public Object getValue(int row, int column) {
		Object obj = values.get(row);
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
	    for(Field field : fields) {
	    	try {
	    		String name = field.getName();
		        if (columnHeaders[column].equals(name)) return field.get(obj).toString();
			} catch (IllegalAccessException iae) {
				iae.printStackTrace();
			}
	    }
	    return null;
	}

	@Override
	public void setValue(int row, int column, Object obj) {
		this.rows = row+1;
		int index = row*columns + column;
		//this.values.add(index, obj);
	}

	@Override
	public String toString() {
		return "ObjectTDS [values=" + values + "]";
	}
	
	
}
