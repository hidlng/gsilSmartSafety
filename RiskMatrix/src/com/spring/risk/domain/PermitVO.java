package com.spring.risk.domain;

import java.io.Serializable;

public class PermitVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3228250592788780377L;
	String workCode;
	String permit;
	String checkList;
	
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	public String getCheckList() {
		return checkList;
	}
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
}
