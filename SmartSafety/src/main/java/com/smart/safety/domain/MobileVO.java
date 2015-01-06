package com.smart.safety.domain;

import java.io.Serializable;

public class MobileVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String risk_grade;
	private String work_idx;
	private String worklevel;
	private String pic_name;
	private String worktitle;
	private String startdate;
	private String enddate;
	private String checkyn;
		
	public String getRisk_grade() {
		return risk_grade;
	}
	public void setRisk_grade(String risk_grade) {
		this.risk_grade = risk_grade;
	}
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public String getWorklevel() {
		return worklevel;
	}
	public void setWorklevel(String worklevel) {
		this.worklevel = worklevel;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getWorktitle() {
		return worktitle;
	}
	public void setWorktitle(String worktitle) {
		this.worktitle = worktitle;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCheckyn() {
		return checkyn;
	}
	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
