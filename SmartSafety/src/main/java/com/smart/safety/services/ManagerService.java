package com.smart.safety.services;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.UserVO;
import com.smart.safety.persistence.ManagerMapper;
import com.smart.safety.persistence.UserMapper;
import com.smart.safety.util.*;

@Service(value="ManagerService")
public class ManagerService{

	@Resource(name="ManagerMapper")
	private ManagerMapper managerMapper;
	
	@Resource(name="UserMapper")
	private UserMapper userMapper;

	public ManagerVO getManagerByIdx(String idx) {
		return managerMapper.getManagerByIdx(idx);
	}
	

	public ManagerVO getManagerByID(String id) {
		return managerMapper.getManagerByID(id);
	}
	
	@Transactional
	public void insertManager(ManagerVO managerVO) {
		
		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		userVO.setId(managerVO.getId());
		userVO.setPassword(passwordEncoder.encode(managerVO.getPassword()));
		userVO.setLevel(Integer.valueOf(managerVO.getLevel()));
		//userVO.setUser_idx(managerVO.getUser_idx());
		
		userMapper.insertUser(userVO);
		managerMapper.insert(managerVO);
	}
	
	
	
	
	@Transactional
	public void updateManager(ManagerVO managerVO) {
	
		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		userVO.setId(managerVO.getId());
		userVO.setPassword(passwordEncoder.encode(managerVO.getPassword()));
		userVO.setLevel(Integer.valueOf(managerVO.getLevel()));
		userVO.setUser_idx(managerVO.getUser_idx());
		
		
		
		if(managerVO.getIsPWChanged().equals("false"))  //비밀번호 수정하지 않을 경우
			userMapper.updateUserWithoutPW(userVO);
		else
			userMapper.updateUser(userVO);
		
		//userMapper.insertAuthority(userVO);
		managerMapper.update(managerVO);
		
	}
	
	@Transactional
	public void deleteManager(String manager_idx, String user_idx) {
		userMapper.deleteUserByIdx(user_idx);
		managerMapper.delete(manager_idx);
	}

	public int getRowCount(ManagerVO managerVO) {
		return managerMapper.getRowCount(managerVO);
	}

	public List<ManagerVO> getManagerListByVO(ManagerVO managerVO) {
		return managerMapper.getManagerListByVO(managerVO);
	}

	/**cont_idx를 가진 managerlist중 level이 소장인 사람을 가져옴(참고 - 쿼리에 LIMIT 0,1사용하여 한건만가져옴 )**/
	public ManagerVO getChiefByContIdx(String cont_idx) {		
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("cont_idx", cont_idx); 
		params.put("level", USERLEVEL.SITE_CHEIF.idx); 
		return managerMapper.getChiefByContIdx(params);
	}

	
}
