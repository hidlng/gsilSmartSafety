package com.spring.risk.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;

@Service
public class PermitService {

	@Autowired
	private PermitMapper permitMapper;

	public List<PermitVO> getPermitList(){
		return permitMapper.getPermitList();
	}
	
	public PermitVO getPermitByCode(PermitVO permitVO){
		return permitMapper.getPermitByCode(permitVO);
	}
	
	public PermitVO getPermitByIdx(String permit_idx){
		return permitMapper.getPermitByIdx(permit_idx);
	}
	
	@Transactional
	public void insertPermitVO(PermitVO permitVO){
		permitMapper.insertPermitVO(permitVO);
	}
	
	@Transactional
	public void deletePermitByIdx(String permit_idx){
		permitMapper.deletePermitByIdx(permit_idx);
	}
	
	@Transactional
	public void updatePermitVO(PermitVO permitVO){
		permitMapper.updatePermitVO(permitVO);
	}


}

