package com.smart.safety.persistence;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.UserVO;


@Repository(value="MobileMapper")
public interface MobileMapper {
	public UserVO getMobileLogin(String id, String password);
}
