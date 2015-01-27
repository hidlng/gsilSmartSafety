package com.smart.safety.domain;

import java.io.*;

public class UserVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4177606408787283224L;
	String user_idx;	
	private String id;
	private String password;
	private String authority;
	private int level;
	String pid;
	private String site_idx;
	private String sitename;
	
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	String writetime;
	String delyn;
	
	
	public String getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(String user_idx) {
		this.user_idx = user_idx;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
		
}
