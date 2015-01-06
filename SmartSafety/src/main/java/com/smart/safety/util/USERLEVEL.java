package com.smart.safety.util;

public enum USERLEVEL {
	SS_MANAGER(0),//0
	EHS_MANAGER(1),//1
	SITE_MANAGER(2),
	CEO(3),
	SITE_CHEIF(4),
	SITE_LEADER(5),
	SITE_WORKER(6),
	SITE_CONTRACTOR(7);
	public int idx;	 USERLEVEL(int val){this.idx = val;}
}

