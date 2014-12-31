package com.spring.risk.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import net.sourceforge.stripes.action.FileBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.risk.domain.FileVO;
import com.spring.risk.persistence.FileMapper;
import com.spring.risk.web.actions.CategoryActionBean;


@Service
public class FileListService {

	
	@Autowired
	private FileMapper fileListMapper;

	public List<FileVO> getFileListByCode(String code) {
		return fileListMapper.getFilesByCode(code);
	}
	
//	public FileVO getFileType(String code, String fileName) {
//		return fileListMapper.getFileType(code, fileName);
//	}
	
	public FileVO getFileVOByIdx(String file_idx) {
		return fileListMapper.getFileVOByIdx(file_idx);
	}
	
	public List<FileVO> getFileList() {
		return fileListMapper.getFileList();
	}

	
	/**변환된 virtname을 생성하여, virtname으로 저장**/
	@Transactional
	public void insertFileListVO(String code, List<FileBean> fileBeanList) throws IOException {
		for(FileBean fileBean : fileBeanList){
			if(fileBean != null){
				FileVO fileVo = new FileVO();
				fileVo.setCode(code);
				fileVo.setFileName(fileBean.getFileName());
				fileVo.setFileType(fileBean.getContentType());
				String virtName = UUID.randomUUID().toString() +"_" + fileVo.getCode() + fileBean.getContentType();
				fileVo.setVirtName(virtName);
				
				insertFileVO(fileVo);
				
				fileBean.save(new File(CategoryActionBean.UPLOAD_PATH + File.separator + fileVo.getVirtName()) );
			
			}
		}
	}
	
	/**
	 * 
	 * @param code
	 * @param fileList 기존 List(삭제 처리반영)
	 * @param fileBeanList(새로 추가된 List . insert작업프로세스 동일)
	 * @throws IOException
	 */
	@Transactional
	public void updateFileListVO(String code, List<FileVO> fileList, List<FileBean> fileBeanList) throws IOException {
		
		// 1. 기존 파일리스트 변경(전부 삭제 -> 수정된 기본 파일리스트 insert)
		deleteFileVOByCode(code);
		for(FileVO vo : fileList){
			insertFileVO(vo);
		}
		
		//2. 새로추가된 list는 기존 insert와 동일
		insertFileListVO(code, fileBeanList);
	}
	
	
	@Transactional
	private void insertFileVO(FileVO fileVO) {		
		fileListMapper.insertFileVO(fileVO);
	}
	
	
	/** code에 대한 row만 제거하고, 파일을 지우진 않음**/
	@Transactional
	public void deleteFileVOByCode(String code) {
		fileListMapper.deleteFileVOByCode(code);
		
	}
	
	/**code+name(primary key)을 통해 한 row만 제거**/
	@Transactional
	public void deleteFileVOByVO(FileVO fileVO) {
		fileListMapper.deleteFileVOByVO(fileVO);
		
	}


}

