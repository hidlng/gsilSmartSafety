package com.spring.risk.domain;

import java.io.*;

import net.sourceforge.stripes.action.*;

public class CheckVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int check_idx;
	String toolCode;
	String image;
	private String virtName;
	String checklist;
	private FileBean fileBean;
	
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	
	public String getChecklist() {
		return checklist;
	}
	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCheck_idx() {
		return check_idx;
	}
	public void setCheck_idx(int check_idx) {
		this.check_idx = check_idx;
	}
	public FileBean getFileBean() {
		return fileBean;
	}
	public void setFileBean(FileBean fileBean) {
		this.fileBean = fileBean;
	}
	public String getVirtName() {
		return virtName;
	}
	public void setVirtName(String virtName) {
		this.virtName = virtName;
	}
	
}
