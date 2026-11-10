package com.kriss.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 */
public class PrintUtil extends StringUtil {
	
	public static void printCurrentDate() {
		System.out.println(DateUtil.getCurrentDate("STRING", "yyyy-MM-dd"));
	}
	
	public static void printSplitString(String str, String regex) {
		String[] strs = str.split(regex);
		System.out.println(Arrays.asList(strs));
	}
	
	public static void printRandomNumber() {
		int count = 0;
		for(int i=0;i<10;i++) {
			String pin = String.format("%04d", new Random().nextInt(9999));
			System.out.println(pin.length() + " " + pin);
			if (pin.length() == 4) {
				count++;
			}
		}
		System.out.println("Count : " + count);
	}
	
	public static void printAString(String str) {
		System.out.println(str);
	}
	
	public static void printASet(String desc, Set<String> set) {
		System.out.println("\n");
		System.out.println(desc + ": " + set.size());
		for (String str : set) {
			System.out.println(str);
		}
	}
	
	/**
	 * 
	 * @param desc The Set definition
	 * @apiNote Prints the size of the Set along with the desc
	 * 
	 */
	public static void printAnObjectSet(String desc, Set<Object> set) {
		System.out.println("\n");
		System.out.println(desc + ": " + set.size());
		for (Object obj : set) {
			System.out.println(obj);
		}
	}
	
	public static void printAMap(String desc, Map<String, Integer> map) {
		int i= 0;
		System.out.println("Data split by " + desc + ":");
		for (String str : map.keySet()) {
			System.out.println(str + ": " + map.get(str));
			i+=map.get(str);
		}
		System.out.println("Total: " + i);
		System.out.println("Total " + desc + ": " + map.keySet().size());
	}
	
	public static void printAStringMap(String desc, Map<String, String> map) {
		System.out.println(desc + ":");
		for (String str : map.keySet()) {
			System.out.println(str + ": " + map.get(str));
		}
	}
	
	public static Set<String> printSubStringInList(List<String> lines, String searchKey, String searchEndKey, boolean sort) {
		int i = 0;
		String val = null;
		Set<String> results = new TreeSet<String>();
		for (String line : lines) {
            if (line !=null && line.contains(searchKey)) {
            	i++;
            	val = StringUtil.getSubStringWithSearchKeys(line, searchKey, searchEndKey);
            	if (!sort) System.out.println(val);
            	results.add(val);
            }
        }
		System.out.println("Total Results: " + i);
		if (sort) {
			for (String result: results) {
				System.out.println(result);
			}
		}
		return results;
	}

}
