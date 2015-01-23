package com.spring.risk.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;

@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;

	public List<TestVO> getTest(){
		return testMapper.getTest();
	}


}

