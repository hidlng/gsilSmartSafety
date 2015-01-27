package com.smart.safety.util;
import java.io.*;
import java.net.*;
import java.util.*;

import com.google.android.gcm.server.*;

public class GCMPusher {
	public static GCMPusher INSTANCE = new GCMPusher();
	
	public void sendMessage(String regId, String msg) throws IOException {
		Sender sender = new Sender("AIzaSyBQNLUyd80UKgvloLjeUg3FUYRHNCRKtjU");
		//String regId = "APA91bHKzAacDO86UqeCntFzUck6bf8RcVyiDDJo4uvcYSJzErpGkLWNBKAZLArm3G0lpLllxp1mHfK4__SKytzqLtXh9sRkH66tmI9Fs5h1JO_eIP8qaVryYsSeCY3TRdleBgbSn9G06_625NAiDdVrDKbkVU_HEaSkyca01lSUt3ts4dz_Dwg";
		Message message = new Message.Builder().addData("msg", URLEncoder.encode(msg,"UTF-8")).build();
		List<String> list = new ArrayList<String>();
		list.add(regId);
		
		MulticastResult multiResult = sender.send(message, list, 5);
		if (multiResult != null) {
			List<Result> resultList = multiResult.getResults();
			for (Result result : resultList) {
				System.out.println(result.getMessageId());
			}
		}
	}
	 
	 
	 
	
}