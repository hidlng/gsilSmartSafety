package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.ContractorVO;
import com.smart.safety.domain.ManagerVO;

@Repository(value="ContractorMapper")
public interface ContractorMapper {
	public List<ContractorVO> getContractorListByVO(ContractorVO contractorVO);
	public List<ContractorVO> getContractorListBySiteIdx(String site_idx);
	
	public ContractorVO getContractorByIdx(String idx);
	public ContractorVO getContractorByID(String id);
	
	public void insert(ContractorVO contractorVO);
	public void update(ContractorVO contractorVO);
	public void delete(String idx);
	public int getRowCount(ContractorVO contractorVO);
}
