package com.kriss.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class AMainClass {

	public static void main(String[] args) {
		
		String str = "2023-08-24 19:45:00.178 [http-nio-8205-exec-12] 10-1692906300149 310ed2a9-7cf3-48ee-8c8f-fce7cc5aa880 [INFO ] com.vw.carnet.push.controller.PushController.processPushNotification - Request in PushController PushNotificationRequest [appUIDs=null, message=null, templateName=null, placeholders=null, userIds=null, vehicleId=cc85b902-07ea-36e1-9c80-ebb486a31cb8, appendVehicleDetails=false, data={\"eventHeader\":{\"vehicleID\":\"cc85b902-07ea-36e1-9c80-ebb486a31cb8\",\"id\":\"cc85b902-07ea-36e1-9c80-ebb486a31cb8\",\"idType\":2,\"operation\":\"climateControl\",\"operationType\":2,\"correlationId\":\"310ed2a9-7cf3-48ee-8c8f-fce7cc5aa880\",\"timestamp\":1692906297024},\"eventStatus\":{\"responseOutcome\":3,\"responseStatus\":1,\"responseCode\":\"RO_UNDELIVERED_VEHICLE_UNREACHABLE\"},\"climateControlData\":{}}, appUID=C34C3EFA-3568-46A5-A450-E0575B9DDC48, tokenAgeInDays=0]";
		
		parseString(str);
		
		// printCurrentDate();
		// printRandomNumber();
	}
	
	public static void parseString(String str) {
		String[] strs = str.split(" ");
		System.out.println(Arrays.asList(strs));
	}
	
	public static void printCurrentDate() {
		Date now = new Date();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(format.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
