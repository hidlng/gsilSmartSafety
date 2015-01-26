package com.spring.risk.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;



@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public UserVO getUserbyId(String id) {
		return userMapper.getUserbyId(id);
	}
}
