package com.smart.safety.domain;

import java.io.*;

public class TBMVO extends BaseInfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -642899471456883370L;
	private String tbm_idx;
	private String work_idx;
	
	private String workname;//작업코드관련 이름임..
	private String placename;
	private int risk_level;
	private String risk_grade;
	private String risk_warn;
	private String workpermit;
	private String toollist ="";
	private String mainrisk ="";
	private String measure ="";
	private String equip ="";
	private String guide ="";
	private String remark ="";
	private String remark_leader="";
	private String remark_chief="";
	private String safety ="";
	
	
	
	public String getToollist() {
		return toollist;
	}
	public void setToollist(String toollist) {
		this.toollist = toollist;
	}

	
	public String getTbm_idx() {
		return tbm_idx;
	}
	public void setTbm_idx(String tbm_idx) {
		this.tbm_idx = tbm_idx;
	}
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public String getRisk_grade() {
		return risk_grade;
	}
	public void setRisk_grade(String risk_grade) {
		this.risk_grade = risk_grade;
	}
	public String getRisk_warn() {
		return risk_warn;
	}
	public void setRisk_warn(String risk_warn) {
		this.risk_warn = risk_warn;
	}
	public String getWorkpermit() {
		return workpermit;
	}
	public void setWorkpermit(String workpermit) {
		this.workpermit = workpermit;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(int risk_level) {
		this.risk_level = risk_level;
	}
	public String getSafety() {
		return safety;
	}
	public void setSafety(String safety) {
		this.safety = safety;
	}
	public String getMainrisk() {
		return mainrisk;
	}
	public void setMainrisk(String mainrisk) {
		this.mainrisk = mainrisk;
	}
	public String getRemark_leader() {
		return remark_leader;
	}
	public void setRemark_leader(String remark_leader) {
		this.remark_leader = remark_leader;
	}
	public String getRemark_chief() {
		return remark_chief;
	}
	public void setRemark_chief(String remark_chief) {
		this.remark_chief = remark_chief;
	}

	

	
	

	
	
}
