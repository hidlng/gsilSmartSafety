package com.spring.risk.persistence;

import com.spring.risk.domain.WorkVO;

public interface WorkMapper {
	WorkVO getWorkByWorkCode(String workCode);
	void insertWorkVO(WorkVO workVO);
	void deleteWorkVOByCode(String code);	
	void updateWorkVO(WorkVO workVO);
	
}


