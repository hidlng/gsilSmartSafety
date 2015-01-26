package com.smart.safety.domain;

import java.io.Serializable;

public class CeoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String work_idx;
	private String sitename;
	private String worktitle;
	private String startdate;
	private String enddate;
	private String type;
	private String site_idx;
	private String checkyn;
	private String chifcheckyn;
	private String leadcheckyn;
	private String chk_user_idx;
	private String risk_grade;
	private String user_idx;
	private String name;
	private String workdate;
	private String basedate;
	private String siteValue;
	private String riskSearchValue;
	private String chkSearchValue;
	private String searchWord;
		
	private int start=0;
	private int size=10;
	
	public String getChifcheckyn() {
		return chifcheckyn;
	}
	public void setChifcheckyn(String chifcheckyn) {
		this.chifcheckyn = chifcheckyn;
	}
	public String getLeadcheckyn() {
		return leadcheckyn;
	}
	public void setLeadcheckyn(String leadcheckyn) {
		this.leadcheckyn = leadcheckyn;
	}
	public String getRiskSearchValue() {
		return riskSearchValue;
	}
	public void setRiskSearchValue(String riskSearchValue) {
		if( riskSearchValue.equals("1") ) {
			this.riskSearchValue = "A";
		} else if( riskSearchValue.equals("2") ) {
			this.riskSearchValue = "B";
		} else if( riskSearchValue.equals("3") ) {
			this.riskSearchValue = "C";
		} else {
			this.riskSearchValue = "";
		}
		
	}
	public String getChkSearchValue() {
		return chkSearchValue;
	}
	public void setChkSearchValue(String chkSearchValue) {
		if( chkSearchValue.equals("1") ) {
			this.chkSearchValue = "Y";
		} else if( chkSearchValue.equals("2") ) {
			this.chkSearchValue = "N";
		} else {
			this.chkSearchValue = "";
		}
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getSiteValue() {
		return siteValue;
	}
	public void setSiteValue(String siteValue) {
		this.siteValue = siteValue;
	}
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getBasedate() {
		return basedate;
	}
	public void setBasedate(String basedate) {
		this.basedate = basedate;
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
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}
	public String getCheckyn() {
		return checkyn;
	}
	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}
	public String getChk_user_idx() {
		return chk_user_idx;
	}
	public void setChk_user_idx(String chk_user_idx) {
		this.chk_user_idx = chk_user_idx;
	}
	public String getRisk_grade() {
		return risk_grade;
	}
	public void setRisk_grade(String risk_grade) {
		this.risk_grade = risk_grade;
	}
	public String getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(String user_idx) {
		this.user_idx = user_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
