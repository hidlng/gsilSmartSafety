package com.smartsafety.smartsafetyalarm.util;

import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.R.array;

import android.content.Context;

public class HttpUrl {
	
	//api final value
	public static final int SMARTSAFETY_LOGIN 							= 0;
	public static final int SMARTSAFETY_WORK_LIST 							= 1;
	public static final int SMARTSAFETY_UPDATE 							= 2;
	
	public static String getUrl(Context context, int idx) {
		String apiUrl = String.format(context.getResources().getStringArray(R.array.kaodee_url)[idx]);
		if(apiUrl==null){
			return "apiUrl is null";
		}
		return apiUrl;
	}

}
