package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;


public interface CheckMapper {

  List<CheckVO> getCheckListByCode(String toolCode);

  void insertCheckVO(CheckVO checkVO);  
  void updateCheckVOByIdx(CheckVO checkVO);  
  void deleteCheckVOByIdx(String check_idx);
  void deleteCheckVOByCode(String toolCode);

}
