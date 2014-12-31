package com.spring.risk.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.risk.domain.FileVO;


public interface FileMapper {

  List<FileVO> getFilesByCode(String code);
  List<FileVO> getFileList();
  FileVO getFileVOByIdx(String file_idx);  

  void insertFileVO(FileVO fileVO);
  void deleteFileVOByCode(String code);
  void deleteFileVOByVO(FileVO fileVO);

}
