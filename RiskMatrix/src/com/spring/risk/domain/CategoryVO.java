package com.spring.risk.domain;

import java.io.*;

public class CategoryVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int idx;
	String name;
	private int parent;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	
}
