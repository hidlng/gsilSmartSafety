package com.smart.safety.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.ContractorVO;
import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.UserVO;
import com.smart.safety.persistence.ContractorMapper;
import com.smart.safety.persistence.UserMapper;

@Service(value="ContractorService")
public class ContractorService{	
	
	@Resource(name="ContractorMapper")
	private ContractorMapper contractorMapper;
	
	@Resource(name="UserMapper")
	private UserMapper userMapper;


	public ContractorVO getContractorByIdx(String idx) {
		return contractorMapper.getContractorByIdx(idx);
	}
	

	public ContractorVO getContractorByID(String id) {
		return contractorMapper.getContractorByID(id);
	}
	
	@Transactional
	public void insertContractor(ContractorVO contractorVO) {
		
		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		userVO.setId(contractorVO.getId());
		userVO.setPassword(passwordEncoder.encode(contractorVO.getPassword()));
		userVO.setLevel(contractorVO.getLevel());
		//userVO.setUser_idx(contractorVO.getUser_idx());
		
		userMapper.insertUser(userVO);
		contractorMapper.insert(contractorVO);
	}
	
	
	
	
	@Transactional
	public void updateContractor(ContractorVO contractorVO) {
	
		PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		
		UserVO userVO = new UserVO();
		userVO.setId(contractorVO.getId());
		userVO.setPassword(passwordEncoder.encode(contractorVO.getPassword()));
		userVO.setLevel(contractorVO.getLevel());
		userVO.setUser_idx(contractorVO.getUser_idx());
		
		
		
		if(contractorVO.getIsPWChanged().equals("false"))  //비밀번호 수정하지 않을 경우
			userMapper.updateUserWithoutPW(userVO);
		else
			userMapper.updateUser(userVO);
		
		//userMapper.insertAuthority(userVO);
		contractorMapper.update(contractorVO);
		
	}
	
	@Transactional
	public void deleteContractor(String cont_idx, String user_idx) {
		userMapper.deleteUserByIdx(user_idx);
		contractorMapper.delete(cont_idx);
	}

	
	public int getRowCount(ContractorVO contractorVO) {
		return contractorMapper.getRowCount(contractorVO);
	}

	public List<ContractorVO> getContractorListByVO(ContractorVO contractorVO) {
		return contractorMapper.getContractorListByVO(contractorVO);
	}

	
	
}
