package com.smart.safety.persistence;

import java.util.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="WorkMapper")
public interface WorkMapper {
	public List<WorkVO> getWorkListByVO(WorkVO workVO);
	public int getRowCount(WorkVO workVO);
	public WorkVO getWorkByIdx(String work_idx);
	public int getRowCntForInsert();
	public List<String> getSiteIdxListByDate(String date);
	public void insert(WorkVO workVO);
	public void update(WorkVO workVO);
	public void delete(String work_idx);
	
}
