package com.spring.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.risk.domain.TestVO;
import com.spring.risk.persistence.TestMapper;

@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;

	public List<TestVO> getTest(){
		return testMapper.getTest();
	}


}

