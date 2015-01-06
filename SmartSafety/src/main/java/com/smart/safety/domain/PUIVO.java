package com.smart.safety.domain;

import java.io.*;
import java.util.*;

public class PUIVO extends BaseInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String toolname;	
	String toolurl;	
	String mainrisk="";
	List<CheckVO> checklist = new ArrayList<CheckVO>();
	
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
	}
	public String getToolurl() {
		return toolurl;
	}
	public void setToolurl(String toolurl) {
		this.toolurl = toolurl;
	}
	public String getMainrisk() {
		return mainrisk;
	}
	public void setMainrisk(String mainrisk) {
		this.mainrisk = mainrisk;
	}
	public List<CheckVO> getChecklist() {
		return checklist;
	}
	public void setChecklist(List<CheckVO> checklist) {
		this.checklist = checklist;
	}
}
