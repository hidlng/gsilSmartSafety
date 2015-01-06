package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.*;

@Repository(value="PrintMapper")
public interface PrintMapper {
	public int getPrintCnt(PrintVO printVO);
	public List<PrintVO> getPrintByWorkIdx(String work_idx);
	public void insert(PrintVO printVO);
	public void update(PrintVO printVO);
	public void deleteByWorkIdx(String work_idx);
	
}
