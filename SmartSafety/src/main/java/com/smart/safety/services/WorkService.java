package com.smart.safety.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.WorkVO;
import com.smart.safety.persistence.WorkMapper;

@Service(value="WorkService")
public class WorkService{	
	
	@Resource(name="WorkMapper")
	private WorkMapper workMapper;
	
	

	public WorkVO getWorkByIdx(String work_idx) {
		return workMapper.getWorkByIdx(work_idx);
	}

	
	@Transactional
	public void insertWork(WorkVO workVO) {
		workMapper.insert(workVO);
	}
	
	
	@Transactional
	public void updateWork(WorkVO workVO) {
			workMapper.update(workVO);
	}
	
	@Transactional
	public void deleteWork(String work_idx) {
		workMapper.delete(work_idx);
	}

	
	public int getRowCount(WorkVO workVO) {
		return workMapper.getRowCount(workVO);
	}

	public List<WorkVO> getWorkListByVO(WorkVO workVO) {
		return workMapper.getWorkListByVO(workVO);
	}

	
	
}
