package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;

public interface PlaceMapper {
	List<PlaceVO> getPlaceList();	
	List<PlaceVO> getNotRegisteredCodeList();
	PlaceVO getPlaceByCode(String placeCode);
	
	void insertPlaceVO(PlaceVO placeVo);
	void deletePlaceVOByCode(String code);	
	void updatePlaceVO(PlaceVO placeVo);
	
}
