package com.spring.risk.domain;

import java.io.Serializable;

public class FileVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String file_idx;
	private String code;
	private String fileName;
	private String virtName;
	private String fileType;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getVirtName() {
		return virtName;
	}
	public void setVirtName(String virtName) {
		this.virtName = virtName;
	}
	public String getFile_idx() {
		return file_idx;
	}
	public void setFile_idx(String file_idx) {
		this.file_idx = file_idx;
	}
	

}
