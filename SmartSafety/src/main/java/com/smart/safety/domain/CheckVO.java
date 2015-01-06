package com.smart.safety.domain;

import java.io.*;

public class CheckVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	String check;
}
