package com.smart.safety.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smart.safety.domain.CeoVO;
import com.smart.safety.persistence.CeoMapper;

@Service(value="CeoService")
public class CeoService {
	@Resource(name="CeoMapper")
	private CeoMapper ceoMapper;
	
	
	public int getRowCount(CeoVO ceoVO) {
		return ceoMapper.getRowCount(ceoVO);
	}

	public List<CeoVO> getCeoList(CeoVO ceoVO) {
		return ceoMapper.getCeoList(ceoVO);
	}
}
