package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.*;

@Repository(value="ToolMapper")
public interface ToolMapper {
	public List<ToolVO> getToolByWorkIdx(String work_idx);
	public void insert(ToolVO toolVO);
	public void update(ToolVO toolVO);
	public void deleteByWorkIdx(String work_idx);
	
}
