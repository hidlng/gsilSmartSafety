package com.smart.safety.domain;

import java.io.*;
import java.sql.*;

public class PrintVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String work_idx;
	int print_type;
	private String workdate;
	String chk_user_idx;
	String printtime;
	String checktime;
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public int getPrint_type() {
		return print_type;
	}
	public void setPrint_type(int print_type) {
		this.print_type = print_type;
	}
	
	public String getChk_user_idx() {
		return chk_user_idx;
	}
	public void setChk_user_idx(String chk_user_idx) {
		this.chk_user_idx = chk_user_idx;
	}
	public String getPrinttime() {
		return printtime;
	}
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}

	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
}
