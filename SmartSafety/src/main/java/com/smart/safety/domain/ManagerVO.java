package com.smart.safety.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class ManagerVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String manager_idx;
	String user_idx;
	
	
	
	@NotEmpty(message="입력 필요")
	String name;		
	@NotEmpty(message="입력 필요")
	String birth;	
	@NotEmpty(message="입력 필요")
	String phone;
	
	String writetime;
	String delyn;
	
	@NotNull(message="입력 필요")
	String site_idx;
	
	/**안전관리자**/	
	@NotEmpty(message="입력 필요")
	String grade;
	@NotEmpty(message="입력 필요")
	String position; 
		
	/**현장사용자**/
	@NotNull(message="선택 필요")
	private	String cont_idx;
	
	/**관리자/사용자 구분**/
	private int ismanager;
	
	/**User Data**/
	@NotEmpty(message="입력 필요")
	private String id;	
	@NotEmpty(message="비밀번호 등록시 빈 값이 아니어야 합니다.")
	private String password;
	private String isPWChanged;	
	private String level;//int값이지만like구문 위해 String으론	
	
	/**Site Data**/
	private String sitename;
	
	/**paging**/
	private int start=0;
	private int size=10;
	
	
	
	public String getManager_idx() {
		return manager_idx;
	}
	public void setManager_idx(String manager_idx) {
		this.manager_idx = manager_idx;
	}
	public String getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(String user_idx) {
		this.user_idx = user_idx;
	}

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public String getIsPWChanged() {
		return isPWChanged;
	}
	public void setIsPWChanged(String isPWChanged) {
		this.isPWChanged = isPWChanged;
	}
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

	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getIsmanager() {
		return ismanager;
	}
	public void setIsmanager(int ismanager) {
		this.ismanager = ismanager;
	}
	public String getCont_idx() {
		return cont_idx;
	}
	public void setCont_idx(String cont_idx) {
		this.cont_idx = cont_idx;
	}
}
