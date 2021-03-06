package com.smart.safety.persistence;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="UserMapper")
public interface UserMapper {
	public UserVO getLoginUserById(String id) ;
	
	public void insertUser(UserVO userVO);
	public void updateUser(UserVO userVO);
	public void updateUserWithoutPW(UserVO userVO);
	public void updateUserPID(UserVO userVO);
	public void deleteUserByIdx(String user_idx);
	

	

}