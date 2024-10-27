package com.kriss.util.poi.ams.incidents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentData {

	public static List<Object> tag3Data;
	public static List<Object> tag4Data;
	public static List<Object> tag5Data;
	public static List<Object> carnetQueues;
	public static List<Object> usQueues;
	public static List<Object> statuses;
	public static Map<String, String> resources;
	
	// SC3 Incidents Status Data
	public static List<String> IS_Columns;
	
	// Incident Reports Data
	public static List<String> IR_Columns;
	
	static {
		tag3Data = new ArrayList<Object>();
		tag3Data.add("US | MOD3 | PQ");
		tag3Data.add("US | MOD3 | MQB");
		tag3Data.add("US | MOD3 | MQB-Low");
		tag3Data.add("US | MOD3 | MQB-High");
		tag3Data.add("US | MOD4 | MEB2");
		tag3Data.add("US | MOD4 | MEB3");
		tag3Data.add("US | MOD4 | MEB4");
		tag3Data.add("US | MOD4 | MEB5");
		
		tag3Data.add("CA | MOD3 | PQ");
		tag3Data.add("CA | MOD3 | MQB");
		tag3Data.add("CA | MOD3 | MQB-Low");
		tag3Data.add("CA | MOD3 | MQB-High");
		tag3Data.add("CA | MOD4 | MEB2");
		tag3Data.add("CA | MOD4 | MEB3");
		tag3Data.add("CA | MOD4 | MEB4");
		tag3Data.add("CA | MOD4 | MEB5");
		
		tag4Data = new ArrayList<Object>();
		Object[] tag4s = {"R&P", "Enrollment - Unenrollment", "Activation (Product Data Mgmt)", "A&A", "B2C", "RO", "RLU", "RS","VHR", "iCall", 
				"eCall", "rCall", "Hybrid Streaming Services", "Online Navigation Services", "WebApps", "Mobile App", "CWP", "Dealer App",
				"Admin Tool", "OTA", "CCPA", "Communications", "Device Swap", "Fleet-TP Streaming Services", "Fleet Batch", "CoC", "Other"};
		for (Object tagObj : tag4s) tag4Data.add(tagObj);
		
		tag5Data = new ArrayList<Object>();
		Object[] components = {"Vehicle Components", "Vehicle OCU", "Vehicle WebApps", "Service Platform", "Device Platform", "Front Ends", 
				"Group Systems", "Cubic","Payment Systems", "Mobile Apps", "CWP", "Dealer App", "Infrastructure", "Call Center",
				"SalesForce", "Developer Platform", "MNO", "NITS", "Other"};
		for (Object str : components) { 
			tag5Data.add(str + " | Verizon");
			tag5Data.add(str + " | T-Mobile");
			tag5Data.add(str + " | Rogers");
		}
		
		carnetQueues = new ArrayList<Object>();
		carnetQueues.add("VW Car-Net Connected Services Support VW Group of America");
		carnetQueues.add("VW EV Connected Services Support VW Group of America");
		carnetQueues.add("VW Car-Net Connectivity Support VW Group of America");
		carnetQueues.add("VW Car-Net Canada Connected Services Support VW Group of America");
		
		usQueues = new ArrayList<Object>();
		usQueues.add("VW Car-Net Connected Services Support VW Group of America");
		usQueues.add("VW EV Connected Services Support VW Group of America");
		usQueues.add("VW Car-Net Connectivity Support VW Group of America");
		
		statuses = new ArrayList<Object>();
		Object[] statusObjects = {"Working", "Wait on User", "Wait on External", "Updated", "Transferred", "Closed", "Resolved"};
		for (Object obj : statusObjects) {
			statuses.add(obj);
		}
		
		resources = new HashMap<String, String>();
		resources.put("DVU845Y", "Kiran Kumar");
		resources.put("DVUQY3P", "Naveen");
		resources.put("DVUNWB4", "Merhawi");
		resources.put("DVUE214", "Radhika");
		resources.put("DVUDS9Z", "Gopi");
		resources.put("DVUKMQK", "Suby");
		resources.put("DVUSONV", "Yaswanth");
		resources.put("DVUYFO0", "Chris");
		resources.put("DVUSWVH", "Corey");
		resources.put("DVUSK8K", "Kranthi");
		resources.put("DVUB9TO", "Imtiaz");
		resources.put("DVUS3LD", "Anil");
		resources.put("DVUSNQS", "Vidya");
		
		IS_Columns = new ArrayList<String>();
		String[] isColumns = {"Incident ID", "Status", "Assignment Group", "Assigned to", "Tag 3", "Tag 4", "Tag 5"};
		for (String tagObj : isColumns) IS_Columns.add(tagObj);
		
		IR_Columns = new ArrayList<String>();
		String[] irColumns = {"No.", "Incident ID", "Title", "Status", "Assignment Group", "Priority", "Impact", "Update Time" + "\n" + "(Timezone based)",
				"Open Time" + "\n" + "(Timezone based)", "Resolve Time" + "\n" + "(Timezone based)", "Resolve Group", "Close Time" + "\n" + "(Timezone based)", 
				"Autoclose by", "Tag 3", "Tag 4", "Tag 5", "Closed or Resolved \"CR\" Date [Resolved date if not Blank else Closed date]",
				"Part of Month Data [Either Open, CR date is  BLANK or CR date is in this month]", "Open Year [Year of Open Time]",
				"Open Month [Month of Open Time]", "Resolved Year [Year of Closed or Resolved date column in left here]",
				"Resolved Month [Month of Closed or Resolved date column in left here]", "Time to Resolve [DAYS(Resolve date, Open Date)]",
				"EXACT Time in DAYS to Resolve [DAYS(Resolve date, Open Date)]",
				"Carry Forward FROM previous month [Those which were opened on or before last date of previous month but  CR date during this month or never CRd so far yet, use Filters to identify such, e.g. for Dec take those open date till Nov 30, and date in CR (i.e. C",
				"Pushed to Next month [ Those which were open until this month last date and CRdate yet blank or CRd in following month, e.g. for Jan Report filter on Open date till Jan 31 and CR date either blank or after Jan 31 (i.e. of Jan ...)]",
				"Market [Split Tag 3]", "Vehicle Program [Split Tag 3]", "Vehicle Plaform [Split Tag 3]",
				"Feature aligned to Reporting Head [VLOOKUP for CORRECTED Tag 4 in Tag Mapping]", "Component [Split from Tag 5]", "MNO [Split from Tag 5]",
				"Service Group (or Partner Group) [Start with Filter on Components and follow Partner Group Mapping to Manually fill for each Component. For \"Device Platform\",  use Tag 3 Program to split again between Aeris and WC]",
				"Partner", "Aging At Month End in Days [Last Date of the reporting month - open date]"};
		for (String tagObj : irColumns) IR_Columns.add(tagObj);
	}
}
