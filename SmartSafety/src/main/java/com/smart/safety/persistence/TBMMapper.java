package com.smart.safety.persistence;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="TBMMapper")
public interface TBMMapper {	
	public TBMVO getTBMByWorkIdx(String work_idx) ;
	public void insert(TBMVO tbmVO);
	public void deleteByTBMIdx(String tbm_idx);
	public TBMVO getTBMByTBMIdx(String tbm_idx);
}