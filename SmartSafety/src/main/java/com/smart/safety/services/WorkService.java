package com.smart.safety.services;

import java.sql.*;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;

@Service(value="WorkService")
public class WorkService{	
	
	@Resource(name="WorkMapper")
	private WorkMapper workMapper;
	
	@Resource(name="ToolMapper")
	private ToolMapper toolMapper;
	
	public int getRowCount(WorkVO workVO) {
		return workMapper.getRowCount(workVO);
	}

	public int getRowCntForInsert() {
		return workMapper.getRowCntForInsert();
	}
	public List<WorkVO> getWorkListByVO(WorkVO workVO) {
		return workMapper.getWorkListByVO(workVO);
	}


	public WorkVO getWorkByIdx(String work_idx) {
		WorkVO workVO = workMapper.getWorkByIdx(work_idx);
		workVO.setToollist(toolMapper.getToolByWorkIdx(work_idx));
		
		return workVO;
	
	}

	
	@Transactional
	public synchronized String insertWork(WorkVO workVO) {
		int count = getRowCntForInsert();
		
		//work 및 tool 동시삽입위해 여기서 key생성
		String work_idx = "W" + count + Calendar.getInstance().getTimeInMillis();
		
		workVO.setWork_idx(work_idx);
		workMapper.insert(workVO);
		
		List<ToolVO> toollist = workVO.getToollist();		
		for(ToolVO toolVO : toollist){
			toolVO.setWork_idx(work_idx);
			toolMapper.insert(toolVO);
		}
		
		return work_idx;
		
	}
	
	
	@Transactional
	public void updateWork(WorkVO workVO) {
		workMapper.update(workVO);
		
		/** update의 경우 항목의 추가/제거가 있을수 있으므로 delete 후 insert를 수행함**/ 
		List<ToolVO> toollist = workVO.getToollist();	
		toolMapper.deleteByWorkIdx(workVO.getWork_idx());
		for(ToolVO toolVO : toollist){
			toolVO.setWork_idx(workVO.getWork_idx());
			toolMapper.insert(toolVO);
		}
	}
	
	@Transactional
	public void deleteWork(String work_idx) {
		workMapper.delete(work_idx);
		toolMapper.deleteByWorkIdx(work_idx);
	}

	

	
	
}
