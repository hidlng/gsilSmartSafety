package com.smart.safety.services;

import java.util.*;

import javax.annotation.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;

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
