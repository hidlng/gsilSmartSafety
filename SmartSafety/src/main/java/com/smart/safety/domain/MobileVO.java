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
	private String workdate;
	private String user_idx;
	
	private String worktype;
	private String placenames;
	private String ptw_exist;
	private String pui_exist;
	private String pic_num_worker;
	private String toolname;
	
	private String name;
	private String notice_idx;
	private String site_idx;
	private String title;
	private String content;
	private String writetime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotice_idx() {
		return notice_idx;
	}
	public void setNotice_idx(String notice_idx) {
		this.notice_idx = notice_idx;
	}
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getPlacenames() {
		return placenames;
	}
	public void setPlacenames(String placenames) {
		this.placenames = placenames;
	}
	public String getPtw_exist() {
		return ptw_exist;
	}
	public void setPtw_exist(String ptw_exist) {
		this.ptw_exist = ptw_exist;
	}
	public String getPui_exist() {
		return pui_exist;
	}
	public void setPui_exist(String pui_exist) {
		this.pui_exist = pui_exist;
	}
	public String getPic_num_worker() {
		return pic_num_worker;
	}
	public void setPic_num_worker(String pic_num_worker) {
		this.pic_num_worker = pic_num_worker;
	}
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(String user_idx) {
		this.user_idx = user_idx;
	}
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
