package com.kriss.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
		System.out.println(desc + ":");
		for (String str : set) {
			System.out.println(str);
		}
	}
	
	public static void printAMap(String desc, Map<String, Integer> map) {
		System.out.println(desc + ":");
		for (String str : map.keySet()) {
			System.out.println(str + ": " + map.get(str));
		}
	}

}
