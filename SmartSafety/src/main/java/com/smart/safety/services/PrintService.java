package com.smart.safety.services;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;

@Service(value="PrintService")
public class PrintService{	
	
	
	@Resource(name="TBMMapper")
	private TBMMapper tbmMapper;

//
//	public TBMVO getTBMByWorkIdx(String work_idx) {
//		return tbmMapper.getTBMByWorkIdx(work_idx);
//	}
//	
//	public TBMVO getTBMByTBMIdx(String tbm_idx) {
//		return tbmMapper.getTBMByTBMIdx(tbm_idx);
//	}
//	
//	
//	
//	@Transactional
//	public String insertTBM(TBMVO tbmVO) {
//		String tbm_idx = "TBM" + Calendar.getInstance().getTimeInMillis(); 
//		tbmVO.setTbm_idx(tbm_idx);
//		tbmMapper.insert(tbmVO);
//		
//		return tbm_idx;
//	}
//	
//	@Transactional
//	public void delete(String tbm_idx) {
//		tbmMapper.deleteByTBMIdx(tbm_idx);
//	}
//	
//	
	
	
	
	
	
}
