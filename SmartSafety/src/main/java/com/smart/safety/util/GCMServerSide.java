package com.smart.safety.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMServerSide {
	public void sendMessage(String regId) throws IOException {
		Sender sender = new Sender("AIzaSyBQNLUyd80UKgvloLjeUg3FUYRHNCRKtjU");
		//String regId = "APA91bHKzAacDO86UqeCntFzUck6bf8RcVyiDDJo4uvcYSJzErpGkLWNBKAZLArm3G0lpLllxp1mHfK4__SKytzqLtXh9sRkH66tmI9Fs5h1JO_eIP8qaVryYsSeCY3TRdleBgbSn9G06_625NAiDdVrDKbkVU_HEaSkyca01lSUt3ts4dz_Dwg";
		Message message = new Message.Builder().addData("msg", "push notify").build();
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