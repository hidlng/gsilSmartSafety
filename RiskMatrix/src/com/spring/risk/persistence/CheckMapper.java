package com.spring.risk.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.risk.domain.CheckVO;


public interface CheckMapper {

  List<CheckVO> getCheckListByCode(String toolCode);

  void insertCheckVO(CheckVO checkVO);  
  void updateCheckVOByIdx(CheckVO checkVO);  
  void deleteCheckVOByIdx(String check_idx);
  void deleteCheckVOByCode(String toolCode);

}
