package com.spring.risk.domain;

import java.io.Serializable;

public class AccDetailVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2165377917733718412L;
	String workCode;
	String accCode;
	private String accName;
	String accPoss;
	String accSerious;
	
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workcode) {
		this.workCode = workcode;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccPoss() {
		return accPoss;
	}
	public void setAccPoss(String accPoss) {
		this.accPoss = accPoss;
	}
	public String getAccSerious() {
		return accSerious;
	}
	public void setAccSerious(String accSerious) {
		this.accSerious = accSerious;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	



}
