package com.smart.safety.domain;

import java.io.*;
import java.util.*;

public class PTWVO extends BaseInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private List<PermitVO> permitList = new ArrayList<PermitVO>();


	public List<PermitVO> getPermitList() {
		return permitList;
	}


	public void setPermitList(List<PermitVO> permitList) {
		this.permitList = permitList;
	}
	

	
	
}

