package com.spring.risk.domain;

import java.io.*;

public class TestVO implements Serializable{

	String code;
	String type;
	String content;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getcontent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
