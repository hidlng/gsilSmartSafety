package com.smart.safety.services;

import javax.annotation.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;

@Service(value="LoginService")
public class LoginService {
	@Resource(name="UserMapper")
	private UserMapper userMapper;

	
//	public UserVO getLoginUserByIdAndPassword(String id, String password) {
//		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
//		String hashedPassword = passwordEncoder.encode(password);
//		return userMapper.getLoginUserByIdAndPassword(id, hashedPassword);
//	}

	
	public UserVO getLoginUserById(String id) {
		return userMapper.getLoginUserById(id);
	}

	
	public void setLoginMapper(UserMapper loginMapper) {
		this.userMapper = loginMapper;
	}
	
	public void updateUserPID(UserVO userVO){
		userMapper.updateUserPID(userVO);
	}

}
