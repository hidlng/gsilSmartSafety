package com.spring.risk.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	
	public FileVO getFileType(String code, String fileName) {
		return fileListMapper.getFileType(code, fileName);
	}
	
	public List<FileVO> getFileList() {
		return fileListMapper.getFileList();
	}

	@Transactional
	public void insertFileListVO(String code, List<FileBean> fileBeanList) throws IOException {
		for(FileBean fileBean : fileBeanList){
			if(fileBean != null){
				FileVO fileVo = new FileVO();
				fileVo.setCode(code);
				fileVo.setFileName(fileBean.getFileName());
				fileVo.setFileType(fileBean.getContentType());
				
				insertFileVO(fileVo);
				fileBean.save(new File(CategoryActionBean.UPLOAD_PATH + File.separator + fileBean.getFileName()));
			
			}
		}
	}
	
	@Transactional
	public void updateFileVO(String code, FileBean fileBean) throws IOException {
		if(fileBean != null) {
			FileVO fileVo = new FileVO();
			fileVo.setCode(code);
			fileVo.setFileName(fileBean.getFileName());
			fileVo.setFileType(fileBean.getContentType());
			
			updateFileVO(fileVo);
			fileBean.save(new File(CategoryActionBean.UPLOAD_PATH + File.separator + fileBean.getFileName()));
			
		} else
			throw new NullPointerException("filebean is NULL");
		
	}
	
	
	@Transactional
	private void insertFileVO(FileVO fileVO) {		
		fileListMapper.insertFileVO(fileVO);
	}
	
	@Transactional
	private void updateFileVO(FileVO fileVO) {
		fileListMapper.updateFileVO(fileVO);
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

