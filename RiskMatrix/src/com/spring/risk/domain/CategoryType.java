package com.spring.risk.domain;

public enum CategoryType {
//	1	작업
//	2	장비/공도구
//	3	작업장소
//	4	사고
	WORK(1) 
	,TOOL(2)
	,PLACE(3)
	,ACC(4);
	
	public int idx;	
	CategoryType(int val){
		this.idx = val;
	}
}
