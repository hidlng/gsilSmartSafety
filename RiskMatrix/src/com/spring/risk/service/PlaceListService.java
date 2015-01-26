package com.spring.risk.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;

@Service
public class PlaceListService {

	@Autowired
	private PlaceMapper placeMapper;

	public List<PlaceVO> getPlaceList() {
		return placeMapper.getPlaceList();
	}
	
	@Transactional
	public List<PlaceVO> getNotRegisteredCodeList(){
		return placeMapper.getNotRegisteredCodeList();
	}

	public PlaceVO getPlaceByCode(String placeCode){
		return placeMapper.getPlaceByCode(placeCode);
	}
	
	@Transactional
	public void insertPlaceVO(PlaceVO placeVO) {
		placeMapper.insertPlaceVO(placeVO);
	}
	
	@Transactional
	public void updatePlaceVO(PlaceVO placeVO) {
		placeMapper.updatePlaceVO(placeVO);
	}

	@Transactional
	public void deletePlaceVOByCode(String code) {
		placeMapper.deletePlaceVOByCode(code);
	}


}

