package com.smart.safety.domain;

import java.io.*;

import org.hibernate.validator.constraints.*;

public class SiteVO implements Serializable{
	private static final long serialVersionUID = 3913612834491910434L;
	
	String site_idx;
	@NotEmpty(message="입력 필요")
	String sitename;
	@NotEmpty(message="입력 필요")
	String addr_detail;
	@NotEmpty(message="입력 필요")
	String rep_name;
	@NotEmpty(message="입력 필요")
	String rep_phone;
	@NotEmpty(message="입력 필요")
	String starttime;
	@NotEmpty(message="입력 필요")
	String endtime;
	String writetime;
	String delyn;
	
	
	/**paging**/
	private int start=0;
	private int size=10;
	
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getRep_name() {
		return rep_name;
	}
	public void setRep_name(String rep_name) {
		this.rep_name = rep_name;
	}
	public String getRep_phone() {
		return rep_phone;
	}
	public void setRep_phone(String rep_phone) {
		this.rep_phone = rep_phone;
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
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
