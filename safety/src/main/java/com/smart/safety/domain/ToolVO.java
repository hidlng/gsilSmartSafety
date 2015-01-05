package com.smart.safety.domain;

import java.io.*;

public class ToolVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tool_idx;
	String work_idx;
	int	 tooltype;
	String	toolcode;
	String toolname;
	String writetime;
	String delyn;
	
	public String getTool_idx() {
		return tool_idx;
	}
	public void setTool_idx(String tool_idx) {
		this.tool_idx = tool_idx;
	}
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public int getTooltype() {
		return tooltype;
	}
	public void setTooltype(int tooltype) {
		this.tooltype = tooltype;
	}
	public String getToolcode() {
		return toolcode;
	}
	public void setToolcode(String toolcode) {
		this.toolcode = toolcode;
	}
	public String getToolname() {
		return toolname;
	}
	public void setToolname(String toolname) {
		this.toolname = toolname;
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
	
}
