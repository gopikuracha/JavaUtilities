package com.kriss.util.poi.ams.incidentreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kriss.collection.adt.TabularDS;
import com.kriss.util.poi.ExcelFileReader;

public class AMSReporting {
	
	public static Map<String, Integer> reports = new HashMap<String, Integer>();

	static {
		reports.put("Incidents Open", null);
	}
	
	public static void main(String[] args) {
		
		String fileName = "C:/Documents_Car-Net/Reporting/Sep-2023/1_100_IM Raw Data Report_AMS_Connected_Car (Last Month)_48_2162350979924260918 - Sep.xlsx";
		
		/**  Step1 - To read and extract an Excel file **/
		ExcelFileReader reader = new ExcelFileReader();
		// TabularDS tds = reader.readFileWithIterator(fileName, 0, 61, 6, true, false);
		TabularDS tds = reader.readFileWithIndex(fileName, 2, 183, 76, true, false);
		System.out.println(tds);
		
		// 79
		
		
		TabularDS openTDS = reader.readFileWithIndex(fileName, 2, 183, 76, true, false);
		TabularDS resolvedTDS = reader.readFileWithIndex(fileName, 2, 183, 76, true, false);

		int inc_open_Count = 0;
		int inc_resolved_Count = 0;
		
		int p1_open_count = 0;
		int p2_open_count = 0;
		int p3_open_count = 0;
		int p4_open_count = 0;
		int p5_open_count = 0;
		int p6_open_count = 0;
		
		int p1_resolved_count = 0;
		int p2_resolved_count = 0;
		int p3_resolved_count = 0;
		int p4_resolved_count = 0;
		int p5_resolved_count = 0;
		int p6_resolved_count = 0;
		
		
		
		for(int i=0; i<tds.getRows(); i++) {
			for(int j=0; j<tds.getColumns(); j++) {
				Object obj = tds.getValue(i, j);
				String str = (String) obj;
				switch (j) {
				case 3:
					// if ()
				case 1:
				default:
				}
			}
		}
		
		
		/** Step2 - To process the extracted data **/
		
		// List<String> filterValues = new ArrayList<String>();
		// filterValues.add("VW Car-Net Connected Services Support VW Group of America"); filterValues.add("VW Car-Net Canada Connected Services Support VW Group of America");
		// filterValues.add("VW EV Connected Services Support VW Group of America");
  
		// tds = tds.filterValues(4, filterValues);
		// System.out.println(tds);
		
		/*
		 * List<Integer> filterColumns = new ArrayList<Integer>(); filterColumns.add(0);
		 * filterColumns.add(1);
		 */
		
		// tds = tds.filterColumns(filterColumns);
		// System.out.println(tds);
		
		/**
		Set<String> results = tds.getUniqueValues(43);
		List<String> results1 = new ArrayList<String>();
		Comparator<String> cmp = new Comparator<String>() {
	        public int compare(String o1, String o2) {
	            return o1.compareTo(o2);
	        }
	    };
		results1.addAll(results);
		results1.remove(null);
		results1.sort(cmp);
		// Collections.sort(results1);
		Iterator<String> itr = results1.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		**/
		
	}
}
