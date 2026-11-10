package com.kriss.util.data.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Entity {
	
	public String type;
	public String name;
	
	public Map<String, Object> attributes;
	
	
	public Entity() {
		this.attributes = new TreeMap<String, Object>();
	}
	
	public Entity(boolean sorted) {
		if (sorted) this.attributes = new TreeMap<String, Object>();
		else this.attributes = new HashMap<String, Object>();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		builder.append("Entity [ ");
		builder.append("type=" + type + ", name=" + name + ", attributes= [ ");
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
		return Objects.hash(attributes, type, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(type, other.type)
				&& Objects.equals(name, other.name);
	}

}
