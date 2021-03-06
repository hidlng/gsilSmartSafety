package com.smart.safety.persistence;

import java.util.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="ManagerMapper")
public interface ManagerMapper {
	//public List<ManagerVO> getManagerList();
	public List<ManagerVO> getManagerListByVO(ManagerVO managerVO);
	public ManagerVO getManagerByIdx(String idx);
	public ManagerVO getManagerByID(String id);
	public ManagerVO getChiefBySiteIdx(Map<String, Object> params);
	public List<ManagerVO> getManagerListBySiteIdx(String site_idx);
	public void insert(ManagerVO managerVO);
	public void update(ManagerVO managerVO);
	public void delete(String idx);
	public int getRowCount(ManagerVO managerVO);
	
	
	
}
