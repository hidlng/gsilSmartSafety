package com.smart.safety.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class ContractorVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String cont_idx;
	String user_idx;
	String site_idx;
	
	
	@NotEmpty(message="입력 필요")
	String cont_name;
	@NotEmpty(message="입력 필요")
	String cont_work;	
	@NotEmpty(message="입력 필요")
	String cont_phone;	
	
	@NotEmpty(message="입력 필요")
	String rep_name;
	@NotEmpty(message="입력 필요")
	String rep_phone;
	
	String writetime;
	String delyn;
	
	/**User Data**/
	@NotEmpty(message="입력 필요")
	private String id;	
	@NotEmpty(message="비밀번호 등록시 빈 값이 아니어야 합니다.")
	private String password;
	private String isPWChanged;	
	private int level;	
	
	/**paging**/
	private int start=0;
	private int size=10;
	
	
	
	public String getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(String user_idx) {
		this.user_idx = user_idx;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getCont_work() {
		return cont_work;
	}
	public void setCont_work(String cont_work) {
		this.cont_work = cont_work;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsPWChanged() {
		return isPWChanged;
	}
	public void setIsPWChanged(String isPWChanged) {
		this.isPWChanged = isPWChanged;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
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
	public String getCont_idx() {
		return cont_idx;
	}
	public void setCont_idx(String cont_idx) {
		this.cont_idx = cont_idx;
	}
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}

	public String getCont_phone() {
		return cont_phone;
	}
	public void setCont_phone(String cont_phone) {
		this.cont_phone = cont_phone;
	}

	
}
