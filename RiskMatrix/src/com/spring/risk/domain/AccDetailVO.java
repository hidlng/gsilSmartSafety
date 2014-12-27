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
	private int accPoss;
	private int accSerious;
	
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
	
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public int getAccPoss() {
		return accPoss;
	}
	public void setAccPoss(int accPoss) {
		this.accPoss = accPoss;
	}
	public int getAccSerious() {
		return accSerious;
	}
	public void setAccSerious(int accSerious) {
		this.accSerious = accSerious;
	}
	



}
