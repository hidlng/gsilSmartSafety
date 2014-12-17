package com.spring.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.risk.domain.FileVO;
import com.spring.risk.domain.UserVO;
import com.spring.risk.persistence.UserMapper;



@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public UserVO getUserbyId(String id) {
		return userMapper.getUserbyId(id);
	}
}
