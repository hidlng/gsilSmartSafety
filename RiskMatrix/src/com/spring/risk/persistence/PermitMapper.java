package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;

public interface PermitMapper {
	List<PermitVO> getPermitList();	
	
	PermitVO getPermitByCode(PermitVO permitVO);
	PermitVO getPermitByIdx(String permit_idx);
	void insertPermitVO(PermitVO permitVO);
	void deletePermitByIdx(String permit_idx);	
	void updatePermitVO(PermitVO permitVO);


}
