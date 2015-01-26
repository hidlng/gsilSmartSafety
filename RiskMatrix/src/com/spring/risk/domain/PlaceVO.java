package com.spring.risk.domain;

import java.io.*;

public class PlaceVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3847460488648711829L;
	/*
	 * SELECT PLACECODE, PLACENAME, MAINRISK, GUIDE, EQUIP
FROM PLACELIST_VIEW;
	 */	
	private String placeCode;
	private String placeName;
	private String mainRisk;
	private String guide;
	private String equip;
	private int	permit;
	private String fileName;
	
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getPermit() {
		return permit;
	}
	public void setPermit(int permit) {
		this.permit = permit;
	}
	

}
