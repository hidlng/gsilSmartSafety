package com.spring.risk.persistence;

import com.spring.risk.domain.ToolVO;

public interface ToolMapper {
	ToolVO getToolByCode(String toolCode);
	void insertToolVO(ToolVO toolVO);
	void deleteToolVOByCode(String code);	
	void updateToolVO(ToolVO toolVO);
}
