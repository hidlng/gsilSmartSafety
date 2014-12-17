package com.spring.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.risk.domain.PlaceVO;
import com.spring.risk.persistence.PlaceMapper;

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

