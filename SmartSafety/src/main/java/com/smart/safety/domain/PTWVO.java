package com.smart.safety.domain;

import java.io.*;
import java.util.*;

public class PTWVO extends BaseInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private PTWCheckVO work;
	private List<PTWCheckVO> placelist = new ArrayList<PTWCheckVO>();
	public PTWCheckVO getWork() {
		return work;
	}
	public void setWork(PTWCheckVO work) {
		this.work = work;
	}
	public List<PTWCheckVO> getPlacelist() {
		return placelist;
	}
	public void setPlacelist(List<PTWCheckVO> placelist) {
		this.placelist = placelist;
	}
	
	
}

