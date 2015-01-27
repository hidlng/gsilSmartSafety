package com.smart.safety.services;

import java.util.*;

import javax.annotation.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;
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
	

	public int getRowCount(ManagerVO managerVO) {
		return managerMapper.getRowCount(managerVO);
	}

	public List<ManagerVO> getManagerListByVO(ManagerVO managerVO) {
		return managerMapper.getManagerListByVO(managerVO);
	}
	
	
	/**siteIdx 및 Userlevel을 통해 리스트 검색 **/
	public List<ManagerVO> getManagerListByLevel(String site_idx, USERLEVEL level) {
		ManagerVO managerVO = new ManagerVO();
		managerVO.setSite_idx(site_idx);
		managerVO.setSearchlevel(new int[]{level.idx});
		managerVO.setId("%");
		managerVO.setName("%");
		managerVO.setPhone("%");
		managerVO.setIsmanager(0);
		return getManagerListByVO(managerVO);
		
	}

	/**cont_idx를 가진 managerlist중 level이 소장인 사람을 가져옴(참고 - 쿼리에 LIMIT 0,1사용하여 한건만가져옴 )**/
	public ManagerVO getChiefBySiteIdx(String site_idx) {		
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("site_idx", site_idx); 
		params.put("level", USERLEVEL.CONT_CHEIF.idx); 
		return managerMapper.getChiefBySiteIdx(params);
	}

	
	public List<ManagerVO> getManagerListBySiteIdx(String site_idx) {
		return managerMapper.getManagerListBySiteIdx(site_idx);
	}

	@Transactional
	public void insertManager(ManagerVO managerVO) {
		
		//PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		
		String user_idx = UIDMaker.makeNewUID("U");
		userVO.setUser_idx(user_idx);
		userVO.setId(managerVO.getId());
		userVO.setPassword(managerVO.getPassword());
		userVO.setLevel(managerVO.getLevel());
		
		//userVO.setUser_idx(managerVO.getUser_idx());
		
		managerVO.setManager_idx(UIDMaker.makeNewUID("M"));
		managerVO.setUser_idx(user_idx);
		
		userMapper.insertUser(userVO);
		managerMapper.insert(managerVO);
	}
	
	
	
	
	@Transactional
	public void updateManager(ManagerVO managerVO) {
	
		//PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		userVO.setId(managerVO.getId());
		userVO.setPassword(managerVO.getPassword());
		userVO.setLevel(managerVO.getLevel());
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


	
}
