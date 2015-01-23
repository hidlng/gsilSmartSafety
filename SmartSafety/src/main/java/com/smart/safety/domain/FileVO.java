package com.smart.safety.domain;

import java.io.*;

public class FileVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String fileName;
	private String fileIdx;
	
	private String name;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(String fileIdx) {
		this.fileIdx = fileIdx;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
