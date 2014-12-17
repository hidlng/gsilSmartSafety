package com.spring.risk.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.risk.domain.FileVO;


public interface FileMapper {

  List<FileVO> getFilesByCode(String code);
  List<FileVO> getFileList();
  FileVO getFileType(@Param("code") String code, @Param("fileName") String fileName);  

  void insertFileVO(FileVO fileVO);  
  void updateFileVO(FileVO fileVO);  
  void deleteFileVOByCode(String code);
  void deleteFileVOByVO(FileVO fileVO);

}
