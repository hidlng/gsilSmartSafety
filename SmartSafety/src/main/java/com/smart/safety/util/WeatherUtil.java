package com.smart.safety.util;

import java.io.*;
import java.net.*;
import java.nio.charset.*;

import org.json.*;


public class WeatherUtil {
  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  public static String getWeatherText( String lat, String lng ) throws JSONException, IOException {
		  String returnString = "";
		  
		  JSONObject obj = readJsonFromUrl("http://map.naver.com/common2/getRegionByPosition.nhn?xPos="+lng+"&yPos="+lat);
		  JSONObject weather = ( JSONObject ) obj.get("result");
		  JSONObject obj2 = ( JSONObject ) weather.get("weather");
		  returnString = ( String ) obj2.get("weatherText");		  
		  
		  return returnString;
	  }
}
