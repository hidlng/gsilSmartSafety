package com.spring.risk.domain;

import java.io.*;

public class WorkVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String workCode;		
	private String workName;
	private String guide;
	private String equip;
	private String measure;
	private String safety;
	private int permit;
	
	private String accName;
	private String accCode;
	private String accPoss;
	private String accSerious;	
	
	private String fileName;
	private int accCnt;
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getAccCnt() {
		return accCnt;
	}
	public void setAccCnt(int accCnt) {
		this.accCnt = accCnt;
	}
	public String getSafety() {
		return safety;
	}
	public void setSafety(String safety) {
		this.safety = safety;
	}
	public int getPermit() {
		return permit;
	}
	public void setPermit(int permit) {
		this.permit = permit;
	}

	

}
