package com.smart.safety.persistence;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.ManagerVO;

@Repository(value="ManagerMapper")
public interface ManagerMapper {
	//public List<ManagerVO> getManagerList();
	public List<ManagerVO> getManagerListByVO(ManagerVO managerVO);
	public ManagerVO getManagerByIdx(String idx);
	public ManagerVO getManagerByID(String id);
	public ManagerVO getChiefByContIdx(Map<String, Object> params);
	public void insert(ManagerVO managerVO);
	public void update(ManagerVO managerVO);
	public void delete(String idx);
	public int getRowCount(ManagerVO managerVO);
	
	
	
}
