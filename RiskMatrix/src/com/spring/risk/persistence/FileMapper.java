package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;


public interface FileMapper {

  List<FileVO> getFilesByCode(String code);
  List<FileVO> getFileList();
  FileVO getFileVOByIdx(String file_idx);  

  void insertFileVO(FileVO fileVO);
  void deleteFileVOByCode(String code);
  void deleteFileVOByVO(FileVO fileVO);

}
