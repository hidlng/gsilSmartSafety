package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;

public interface AccDetailMapper {
	List<AccDetailVO> getAccListByWorkcode(String workCode);	
	void insertAccVO(AccDetailVO accVo);
	void updateAccVO(AccDetailVO accVo);
	void deleteAccVOByWorkCode(String code);

}
