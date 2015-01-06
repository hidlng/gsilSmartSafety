package com.smart.safety.domain;

import java.io.*;
import java.sql.*;

public class PrintVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String work_idx;
	private String workdate;
	String chk_user_idx;
	String checkyn;
	String ptw;
	String pui;
	String tbm;
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getChk_user_idx() {
		return chk_user_idx;
	}
	public void setChk_user_idx(String chk_user_idx) {
		this.chk_user_idx = chk_user_idx;
	}
	public String getCheckyn() {
		return checkyn;
	}
	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}
	public String getPtw() {
		return ptw;
	}
	public void setPtw(String ptw) {
		this.ptw = ptw;
	}
	public String getPui() {
		return pui;
	}
	public void setPui(String pui) {
		this.pui = pui;
	}
	public String getTbm() {
		return tbm;
	}
	public void setTbm(String tbm) {
		this.tbm = tbm;
	}
}
