package com.spring.risk.domain;

import java.io.*;
import java.util.*;

public class CategoryVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int idx;
	String name;
	private int parent;
	List<String> codearray;
	
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
	public List<String> getCodearray() {
		return codearray;
	}
	public void setCodearray(List<String> codearray) {
		this.codearray = codearray;
	}
	
}
