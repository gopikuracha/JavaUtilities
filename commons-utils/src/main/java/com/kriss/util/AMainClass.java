package com.kriss.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.compress.utils.ByteUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.util.StringUtil;

public class AMainClass {

	public static void main(String[] args) {
		
		String str = "2023-08-24 19:45:00.178 [http-nio-8205-exec-12] 10-1692906300149 310ed2a9-7cf3-48ee-8c8f-fce7cc5aa880 [INFO ] com.vw.carnet.push.controller.PushController.processPushNotification - Request in PushController PushNotificationRequest [appUIDs=null, message=null, templateName=null, placeholders=null, userIds=null, vehicleId=cc85b902-07ea-36e1-9c80-ebb486a31cb8, appendVehicleDetails=false, data={\"eventHeader\":{\"vehicleID\":\"cc85b902-07ea-36e1-9c80-ebb486a31cb8\",\"id\":\"cc85b902-07ea-36e1-9c80-ebb486a31cb8\",\"idType\":2,\"operation\":\"climateControl\",\"operationType\":2,\"correlationId\":\"310ed2a9-7cf3-48ee-8c8f-fce7cc5aa880\",\"timestamp\":1692906297024},\"eventStatus\":{\"responseOutcome\":3,\"responseStatus\":1,\"responseCode\":\"RO_UNDELIVERED_VEHICLE_UNREACHABLE\"},\"climateControlData\":{}}, appUID=C34C3EFA-3568-46A5-A450-E0575B9DDC48, tokenAgeInDays=0]";
		
		// PrintUtil.printSplitString(str, " ");
		// PrintUtil.printCurrentDate();
		// PrintUtil.printRandomNumber();
		
		String str1 = "Hello World";
		byte[] b = str1.getBytes();
		// Byte[]
		Byte[] byteObject = ArrayUtils.toObject(b);
		ByteArrayInputStream bi = new ByteArrayInputStream(b);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		//bo.write(b);
		System.out.println("Byte Object: " + bo.toString());
		// Byte[] b2 = (Byte[]) b;
 		System.out.println(Arrays.toString(b));
		
		String s = new String(b, StandardCharsets.UTF_8);
		
		System.out.println("s: " + s);
		
		System.out.println(b);
		for (byte byt : b) {
			Byte bt = Byte.valueOf(byt);
			System.out.println(String.valueOf(byt));
			//System.out.println(bt.toString(bt));
		}
		
	}
}
