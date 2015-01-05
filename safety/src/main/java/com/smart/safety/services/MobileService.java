package com.smart.safety.services;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.safety.domain.UserVO;
import com.smart.safety.persistence.MobileMapper;




@Service(value="MobileService")
public class MobileService {
	@Resource( name="MobileMapper" )
	private MobileMapper mobileMapper;
	
	public UserVO getMobileLogin(String id, String password) {
		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return mobileMapper.getMobileLogin( id, hashedPassword);
	}
}
