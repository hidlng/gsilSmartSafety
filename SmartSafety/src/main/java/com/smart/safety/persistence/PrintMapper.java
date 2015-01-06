package com.smart.safety.persistence;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="PrintMapper")
public interface PrintMapper {
	public PrintVO getPrintVO(PrintVO printVO);
	public void insert(PrintVO printVO);
	public void update(PrintVO printVO);
	public void deleteByWorkIdx(String work_idx);
	
}
