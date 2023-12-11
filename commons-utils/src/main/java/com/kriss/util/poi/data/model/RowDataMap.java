package com.kriss.util.poi.data.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class RowDataMap {
	
	public String identifier;
	public String value;
	
	public Map<String, Object> attributes;
	
	
	public RowDataMap() {
		this.attributes = new TreeMap<String, Object>();
	}
	
	public RowDataMap(boolean sorted) {
		if (sorted) this.attributes = new TreeMap<String, Object>();
		else this.attributes = new HashMap<String, Object>();
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("\n");
		builder.append("RowData [ ");
		builder.append("identifier=" + identifier + ", value=" + value + ", attributes= [ ");
		builder.append("\n");
		
		int count = 0;
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			if (entry.getValue() != null) {
				if (entry.getValue() instanceof String) {
					String str = (String) entry.getValue();
					builder.append(entry.getKey() + " | " + str);
				} else if (entry.getValue() instanceof String[]) {
					String[] str = (String[]) entry.getValue();
					builder.append(entry.getKey() + " | " + Arrays.toString(str));
				} else builder.append(entry.getKey() + " | " + entry.getValue());
				builder.append("\n");
				count ++;
			}
		}
		builder.append(" ] - Total: " + count);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributes, identifier, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowDataMap other = (RowDataMap) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(value, other.value);
	}


}
