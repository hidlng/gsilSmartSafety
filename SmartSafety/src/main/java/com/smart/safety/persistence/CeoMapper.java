package com.smart.safety.persistence;

import java.util.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="CeoMapper")
public interface CeoMapper {
	public List<CeoVO> getCeoList(CeoVO ceoVO);
	public int getRowCount(CeoVO ceoVO);
}
