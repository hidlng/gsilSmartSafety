package com.smart.safety.util;

import java.util.*;

public class UIDMaker {
	public static String makeNewUID(String head){
		return (head + UUID.randomUUID() + (Calendar.getInstance().getTimeInMillis()+"")).substring(0,42); 
	}
}
