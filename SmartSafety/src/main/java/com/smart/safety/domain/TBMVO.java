package com.smart.safety.domain;

import java.io.*;

public class TBMVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -642899471456883370L;
	
	String tbm_idx;
	String work_idx;
	
	String sitename;
	String printtime;
	String cont_name;
	String cont_rep_name;
	String cont_phone;	
	String cont_rep_phone;
	String cont_emg_phone;
	String worktitle;

	String inspector;
	String inspector_phone;
	String pic_name;
	String pic_phone;
	String pic_num_worker;
	String startdate;
	String starttime;
	String enddate;
	String endtime;
	String toollist;
	String mainrisk;
	String weather;
	
	String workname;//작업코드관련 이름임..
	String placename;
	String risk_grade;
	String risk_warn;
	String workpermit;
	String measure;
	String equip;
	String guide;
	String remark;
	
	String site_rep_name;
	String site_rep_phone;
	
	String writetime;
	String delyn;
	
	
	
	public String getCont_rep_name() {
		return cont_rep_name;
	}
	public void setCont_rep_name(String cont_rep_name) {
		this.cont_rep_name = cont_rep_name;
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
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getPrinttime() {
		return printtime;
	}
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getCont_phone() {
		return cont_phone;
	}
	public void setCont_phone(String cont_phone) {
		this.cont_phone = cont_phone;
	}
	public String getCont_rep_phone() {
		return cont_rep_phone;
	}
	public void setCont_rep_phone(String cont_rep_phone) {
		this.cont_rep_phone = cont_rep_phone;
	}
	public String getCont_emg_phone() {
		return cont_emg_phone;
	}
	public void setCont_emg_phone(String cont_emg_phone) {
		this.cont_emg_phone = cont_emg_phone;
	}
	public String getWorktitle() {
		return worktitle;
	}
	public void setWorktitle(String worktitle) {
		this.worktitle = worktitle;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getInspector_phone() {
		return inspector_phone;
	}
	public void setInspector_phone(String inspector_phone) {
		this.inspector_phone = inspector_phone;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getPic_phone() {
		return pic_phone;
	}
	public void setPic_phone(String pic_phone) {
		this.pic_phone = pic_phone;
	}
	public String getPic_num_worker() {
		return pic_num_worker;
	}
	public void setPic_num_worker(String pic_num_worker) {
		this.pic_num_worker = pic_num_worker;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
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
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
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
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}

	
	
}
