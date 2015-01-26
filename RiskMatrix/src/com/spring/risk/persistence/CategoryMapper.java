package com.spring.risk.persistence;

import java.util.*;

import com.spring.risk.domain.*;


public interface CategoryMapper {

 // List<Category> getCategoryList();  

  
  List<CategoryVO> getRootCategory();
  List<CategoryVO> getChildCategory(int idx);
  List<CategoryVO> getAncestorCategory(int idx);
  List<CategoryVO> getSiblingCategory(int idx);
  List<CodeVO> 	   getCodeListByCategory(int idx);
  List<CodeVO> 	   getCodeListByType(int idx);
  List<CodeVO>	   getDescendantCodes(int idx);
  
  String		   getPermitNameByCode(List<String> codearray);
  
  CodeVO getCodeVO(String code);
  List<CodeVO> getDescendantCodeVOList(CodeVO codeVO);
  List<CodeVO> getCodeByCategory(int idx);
  int getCountByCode(CodeVO vo);
  
  /**Category의 다음 IDX를 구해옴**/
  int getMaxCategoryIdx();
  //int getCountByVO(CodeVO codeVO);
 
  void insertCode(CodeVO codeVO);  
  //void updateCode(CodeVO codeVO);  
  void updateCodeName(CodeVO codeVO);  
  void deleteCode(String code);
  
  void insertCategory(CategoryVO categoryVO);  
    
  void deleteCategoryInTree(int idx);
  void deleteCategoryInCode(int idx);
  void deleteCategoryInCategory(int idx);
  
  
  void insertTreePath(CategoryVO categoryVO);  
  //void updateTreePath(CategoryVO categoryVO);  
  void deleteTreePath(String idx);


  

}
