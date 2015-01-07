package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.CeoVO;

@Repository(value="CeoMapper")
public interface CeoMapper {
	public List<CeoVO> getCeoList(CeoVO ceoVO);
	public int getRowCount(CeoVO ceoVO);
}
