package com.smart.safety.util;

public enum USERLEVEL {
	SS_MANAGER(0),//0
	EHS_MANAGER(1),//1
	SITE_MANAGER(2),
	CEO(3),
	CONT_CHEIF(4),
	CONT_LEADER(5),
	CONT_INSPECTOR(6),
	CONTRACTOR(7);
	public int idx;	
	USERLEVEL(int val){this.idx = val;}
	public static USERLEVEL get(int val){
		for(USERLEVEL lv : USERLEVEL.values()) {
			if(lv.idx == val)
				return lv;
		}
		return null;
	}
	
	
}

