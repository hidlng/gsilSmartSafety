package com.smart.safety.domain;

import java.io.*;

public class BaseInfoVO implements Serializable{
	private String sitename;
	private String printtime;
	private String cont_name;
	private String cont_rep_name;
	private String cont_phone;	
	private String cont_rep_phone;
	private String cont_emg_phone;
	private String worktitle;
	private String weather;
	private String chief_name;
	private String chief_phone;
	
	private String inspector;
	private String inspector_phone;
	private String pic_name;
	private String pic_phone;
	private String pic_num_worker;
	private String startdate;
	private String starttime;
	private String enddate;
	private String endtime;

	
	
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
	public String getCont_rep_name() {
		return cont_rep_name;
	}
	public void setCont_rep_name(String cont_rep_name) {
		this.cont_rep_name = cont_rep_name;
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

	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getChief_name() {
		return chief_name;
	}
	public void setChief_name(String chief_name) {
		this.chief_name = chief_name;
	}
	public String getChief_phone() {
		return chief_phone;
	}
	public void setChief_phone(String chief_phone) {
		this.chief_phone = chief_phone;
	}

}
