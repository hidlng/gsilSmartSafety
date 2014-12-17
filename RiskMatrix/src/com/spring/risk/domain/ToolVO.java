package com.spring.risk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToolVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2902716684331509322L;
	/*SELECT TOOLCODE, TOOLNAME, MAINRISK, GUIDE, EQUIP, CHECKLIST
	FROM TOOLLIST_VIEW;*/
	String toolCode;
	String toolName;
	String mainRisk;
	String guide;
	String equip;
	
	List<CheckVO> checkList = new ArrayList<CheckVO>();
	
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getMainRisk() {
		return mainRisk;
	}
	public void setMainRisk(String mainRisk) {
		this.mainRisk = mainRisk;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public List<CheckVO> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<CheckVO> checkList) {
		this.checkList = checkList;
	}




	
}
