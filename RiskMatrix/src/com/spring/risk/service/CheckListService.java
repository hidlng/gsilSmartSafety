package com.spring.risk.service;

import java.io.*;
import java.util.*;

import net.sourceforge.stripes.action.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;


@Service
public class CheckListService {

	
	
	@Autowired
	private CheckMapper checkMapper;

	public List<CheckVO> getFileListByCode(String code) {
		return checkMapper.getCheckListByCode(code);
	}
	
	@Transactional
	public void insertFileListVO(List<CheckVO> checkList) throws IOException {
//		for(FileBean fileBean : fileBeanList){
//			if(fileBean != null){
//				CheckVO fileVo = new CheckVO();
//				fileVo.setToolCode(toolCode);
//				fileVo.setImage(fileBean.getFileName());
//				fileVo.setChecklist();
//				insertCheckVO(fileVo);
//				fileBean.save(new File(UPLOAD_PATH + File.separator + fileBean.getFileName()));
//			
//			}
//		}
	}
	
	@Transactional
	public void updateCheckVO(String code, FileBean fileBean) throws IOException {
//		if(fileBean != null) {
//			CheckVO fileVo = new CheckVO();
//			fileVo.setCode(code);
//			fileVo.setFileName(fileBean.getFileName());
//			fileVo.setFileType(fileBean.getContentType());
//			
//			updateCheckVO(fileVo);
//			fileBean.save(new File(UPLOAD_PATH + File.separator + fileBean.getFileName()));
//			
//		} else
//			throw new NullPointerException("filebean is NULL");
//		
	}
	
	
	@Transactional
	private void insertCheckVO(CheckVO checkVO) {		
		checkMapper.insertCheckVO(checkVO);
	}
	
	@Transactional
	private void updateCheckVO(CheckVO checkVO) {
		checkMapper.updateCheckVOByIdx(checkVO);
	}
	
	@Transactional
	public void deleteCheckVOByIdx(String check_idx) {
		checkMapper.deleteCheckVOByIdx(check_idx);
		
	}


}

