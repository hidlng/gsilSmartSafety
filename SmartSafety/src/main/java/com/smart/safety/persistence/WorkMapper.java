package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.WorkVO;
import com.smart.safety.domain.ManagerVO;

@Repository(value="WorkMapper")
public interface WorkMapper {
	public List<WorkVO> getWorkListByVO(WorkVO workVO);
	public int getRowCount(WorkVO workVO);
	public WorkVO getWorkByIdx(String work_idx);
	
	public void insert(WorkVO workVO);
	public void update(WorkVO workVO);
	public void delete(String work_idx);
	
}
