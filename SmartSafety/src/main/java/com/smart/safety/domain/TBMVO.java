package com.smart.safety.domain;

import java.io.*;

public class TBMVO extends BaseInfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -642899471456883370L;
	String tbm_idx;
	String work_idx;
	
	String workname;//작업코드관련 이름임..
	String placename;
	int risk_level;
	String risk_grade;
	String risk_warn;
	String workpermit;
	String toollist ="";
	String mainrisk ="";
	String measure ="";
	String equip ="";
	String guide ="";
	String remark ="";
	
	String site_rep_name;
	String site_rep_phone;
	
	public String getToollist() {
		return toollist;
	}
	public void setToollist(String toollist) {
		this.toollist = toollist;
	}
	public String getMainrisk() {
		return mainrisk;
	}
	public void setMainrisk(String mainrisk) {
		this.mainrisk = mainrisk;
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
	public String getSite_rep_name() {
		return site_rep_name;
	}
	public void setSite_rep_name(String site_rep_name) {
		this.site_rep_name = site_rep_name;
	}
	public String getSite_rep_phone() {
		return site_rep_phone;
	}
	public void setSite_rep_phone(String site_rep_phone) {
		this.site_rep_phone = site_rep_phone;
	}
	public int getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(int risk_level) {
		this.risk_level = risk_level;
	}

	

	
	

	
	
}
