package com.spring.risk.service;

import java.io.*;
import java.util.*;

import net.sourceforge.stripes.action.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;
import com.spring.risk.web.actions.*;


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
				insertFileByBean(code, fileBean);
			}
		}
	}
	
	/**
	 *  filebean 이용하여 db 및 서버에 파일 정보 업로드
	 * @param code
	 * @param fileBean
	 * @throws IOException
	 */
	@Transactional
	public void insertFileByBean(String code, FileBean fileBean) throws IOException {
		FileVO fileVo = new FileVO();
		fileVo.setCode(code);
		fileVo.setFileName(fileBean.getFileName());
		fileVo.setFileType(fileBean.getContentType());
		String virtName = UUID.randomUUID().toString() +"_" + fileVo.getCode() +"_";
		fileVo.setVirtName(virtName);
		insertFileVO(fileVo);
	
		fileBean.save(new File(CategoryActionBean.UPLOAD_PATH + File.separator + fileVo.getVirtName()) );
	}
	
	/**
	 * FileVo property 전부 세팅시 해당 함수 호출 
	 * @param fileVo
	 */
	@Transactional
	private void insertFileVO (FileVO fileVo) {
		fileListMapper.insertFileVO(fileVo);
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
		if(fileBeanList != null && fileBeanList.size() > 0)	
			insertFileListVO(code, fileBeanList);
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

