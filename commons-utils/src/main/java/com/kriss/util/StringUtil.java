package com.kriss.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static void main(String[] args) {
		
		String str = "2024-03-12 00:57:40.396 [http-nio-8245-exec-17] c37e15a6-2a97-4ac2-b133-300317999a0d [DEBUG] com.vw.carnet.ms.traffic.logging.RequestResponseLoggingInterceptor.intercept - \r\n"
				+ "     LocalRequestId: bdec1158-aace-40bd-9c60-5e2cc7ac340d \r\n"
				+ "     Response Status 200 OK \r\n"
				+ "      Response Time 137 \r\n"
				+ "      Response body: {\"data\":{\"vehicles\":[{\"vin\":\"1V2FR2CA0RC540233\",\"vehicleId\":\"3c181aea-0752-3540-b781-7e8b2ff8ef38\",\"brand\":\"VW\",\"modelName\":\"Atlas\",\"modelCode\":\"CA35PR\",\"modelDesc\":\"atlas_pa2_2024\",\"stolenFlag\":\"N\",\"tankCapacity\":66.50,\"tspProvider\":\"ATC\",\"modelYear\":\"2024\",\"fctyModelYear\":\"2024\",\"saleModelYear\":\"2024\",\"firstOtarDate\":1703030400000,\"lastOtarDate\":1703030400000,\"representativeImgURLPartial\":\"https://d29xg6cejtjmis.cloudfront.net/2024/CA35PR/2R2R/image.png\",\"representativeImgURLComplete\":\"https://d29xg6cejtjmis.cloudfront.net/2024/CA35PR/2R2R/image.png\",\"vehicleRegistered\":true,\"engineTypeCode\":\"ICE\",\"testVehicle\":false,\"createdTimestamp\":1702789226000}]}}";
	
		
		Map<Integer, String> tokenIndex = new HashMap<>();
		str.lines().forEach(null);
		
		StringTokenizer tokenizer = new StringTokenizer(str);
		
		while (tokenizer.hasMoreTokens()) {
	        String token = tokenizer.nextToken();
	        //System.out.println(token);
			/*
			 * String[] keyValue = token.split(":");
			 * 
			 * if (keyValue.length == 2) { String key = keyValue[0]; String value =
			 * keyValue[1];
			 * 
			 * // Store key-value pairs in the map keyValueMap.put(key, value); }
			 */
	    }
		
	}

	public static void parseString(String str) {
		String[] strs = str.split(" ");
		System.out.println(Arrays.asList(strs));
	}
	
	public static Long convertStringDateToUTC(String timestampString) {
		Long epochMs = null;
		if(!StringUtils.isBlank(timestampString)) {
			try {
				if (timestampString.trim().toUpperCase().endsWith("Z")) {
					SimpleDateFormat df1 = null;
					if (timestampString.trim().length() == 20) {
						df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
						
					} else {
						df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					}
					df1.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date date1 = df1.parse(timestampString);
					epochMs = date1.getTime();
					System.out.println("getEpochTsFromIso8601Ts::{} ");
					return epochMs;
				}
			}
			catch(Exception e) {
				System.out.println("error in parsing ISO timestamp");
			}
		}
		return 0L;
	}
}