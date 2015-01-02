package com.spring.risk.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import net.sourceforge.stripes.action.FileBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.risk.domain.CheckVO;
import com.spring.risk.domain.FileVO;
import com.spring.risk.domain.ToolVO;
import com.spring.risk.persistence.CheckMapper;
import com.spring.risk.persistence.ToolMapper;
import com.spring.risk.web.actions.CategoryActionBean;

@Service
public class ToolListService {

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private CheckMapper checkMapper;
	
	public ToolVO getToolByCode(String toolCode){
		ToolVO toolVO = toolMapper.getToolByCode(toolCode);
		if(toolVO != null)
			toolVO.setCheckList(checkMapper.getCheckListByCode(toolCode));
		return toolVO;
	}

	@Transactional
	public void insertToolVO(ToolVO toolVO) throws IOException {
		
		for(CheckVO checkVO : toolVO.getCheckList()) {
			if(checkVO == null) continue;//입력창에서 중간에 제거되어 빠진경우
			checkVO.setToolCode(toolVO.getToolCode());
			FileBean bean = checkVO.getFileBean();
			if(bean != null){
				checkVO.setImage(bean.getFileName());
				String virtName = UUID.randomUUID().toString() +"_" + toolVO.getToolCode() +"_";
				checkVO.setVirtName(virtName);
				bean.save(new File(CategoryActionBean.CHEKCLIST_PATH + File.separator + virtName));
			}
			//file이 없더라도 checklist 내용은 삽입해야함 
			checkMapper.insertCheckVO(checkVO);
		}
		
		/**장비이미지 insert **/
		FileBean filebean = toolVO.getImgFileBean();
		if(filebean != null) { 
			toolVO.setImgName(filebean.getFileName());
			toolVO.setImgType(filebean.getContentType());
			String virtName = UUID.randomUUID().toString() +"_" + toolVO.getToolCode() +"_";
			toolVO.setImgVirtName(virtName);
			filebean.save(new File(CategoryActionBean.TOOLIMG_PATH + File.separator + toolVO.getImgVirtName()) );
		}
		
		toolMapper.insertToolVO(toolVO);
		
	}
	
	@Transactional
	public void updateToolVO(ToolVO toolVO) throws IOException {
		////toolcode 관련 컬럼 제거 후 삽입
		checkMapper.deleteCheckVOByCode(toolVO.getToolCode());
		
		//checklist정보로 다시 insert
		for(CheckVO checkVO : toolVO.getCheckList()) {	
			FileBean bean = checkVO.getFileBean();
			if(checkVO == null) continue;//입력창에서 중간에 제거되어 빠진경우
			checkVO.setToolCode(toolVO.getToolCode());
			if(bean != null){
				checkVO.setToolCode(toolVO.getToolCode());
				checkVO.setImage(bean.getFileName());
				String virtName = UUID.randomUUID().toString() +"_" + toolVO.getToolCode() +"_";
				checkVO.setVirtName(virtName);
				bean.save(new File(CategoryActionBean.CHEKCLIST_PATH + File.separator + virtName));
			}
			
			checkMapper.insertCheckVO(checkVO);			
		}
		
		
		/**장비이미지 업데이트 **/
		FileBean filebean = toolVO.getImgFileBean();
		if(filebean != null) { 
			toolVO.setImgName(filebean.getFileName());
			toolVO.setImgType(filebean.getContentType());
			String virtName = UUID.randomUUID().toString() +"_" + toolVO.getToolCode() +"_";
			toolVO.setImgVirtName(virtName);
			filebean.save(new File(CategoryActionBean.TOOLIMG_PATH + File.separator + toolVO.getImgVirtName()) );
		}
		toolMapper.updateToolVO(toolVO);
	}

	@Transactional
	public void deleteToolVOByCode(String toolCode) {
		toolMapper.deleteToolVOByCode(toolCode);
	}


}

