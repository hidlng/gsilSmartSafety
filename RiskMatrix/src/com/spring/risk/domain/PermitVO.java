package com.spring.risk.domain;

import java.io.*;
import java.util.*;

public class PermitVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3228250592788780377L;
	private String permit_idx;
	private String workcode;
	private String placecodes;
	private ArrayList<String> parse_codeList = new ArrayList<String>();//parse저장용
	private ArrayList<String> parse_nameList = new ArrayList<String>();//parse저장용
	private String content;
	
	public String getPermit_idx() {
		return permit_idx;
	}
	public void setPermit_idx(String permit_idx) {
		this.permit_idx = permit_idx;
	}
	public String getWorkcode() {
		return workcode;
	}
	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}
	public String getPlacecodes() {
		return placecodes;
	}
	public void setPlacecodes(String placecodes) {
		this.placecodes = placecodes;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getParse_codeList() {
		return parse_codeList;
	}
	public void setParse_codeList(ArrayList<String> parse_codeList) {
		this.parse_codeList = parse_codeList;
	}
	public ArrayList<String> getParse_nameList() {
		return parse_nameList;
	}
	public void setParse_nameList(ArrayList<String> parse_nameList) {
		this.parse_nameList = parse_nameList;
	}


	
}
