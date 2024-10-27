package com.kriss.util.print;

import java.util.Iterator;
import java.util.Set;

public class DesiredPrinting {
	
	public static void printASet(String desc, Set<String> set) {
		System.out.println("\n");
		System.out.println(desc + ":");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
	}

}
