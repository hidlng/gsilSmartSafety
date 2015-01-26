package com.spring.risk.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.spring.risk.domain.*;
import com.spring.risk.persistence.*;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	// public List<Category> getCategoryList() {
	// return categoryMapper.getCategoryList();
	// }
	//

	public CodeVO getCodeVO(String code) {
		return categoryMapper.getCodeVO(code);
	}

	public List<CategoryVO> getRootCategory() {
		return categoryMapper.getRootCategory();
	}

	public List<CategoryVO> getChildCategory(int idx) {
		return categoryMapper.getChildCategory(idx);
	}

	public List<CategoryVO> getAncestorCategory(int idx) {
		return categoryMapper.getAncestorCategory(idx);
	}

	public List<CategoryVO> getSiblingCategory(int idx) {
		return categoryMapper.getSiblingCategory(idx);
	}

	public List<CodeVO> getCodeListByCategory(int idx) {
		return categoryMapper.getCodeListByCategory(idx);
	}
	
	public List<CodeVO> getCodeListByType(int type) {
		return categoryMapper.getCodeListByType(type);
	}
	
	public int getMaxCategoryIdx() {
		return categoryMapper.getMaxCategoryIdx();
	}

	public int getCountByCode(CodeVO vo) {
		return categoryMapper.getCountByCode(vo);
	}
	
	public String getPermitNameByCode(List<String>  codearray) {
		return categoryMapper.getPermitNameByCode(codearray);
	}

	
	/**CodeList는 이함수를 통해서 **/
	public List<CodeVO> getDescendantCodeVOList(CodeVO codeVO) {
		return categoryMapper.getDescendantCodeVOList(codeVO);
	}
	
	public CategoryType getCategoryType(CodeVO codeVO) {
		List<CategoryVO> list = getAncestorCategory(codeVO.getCategory());
		
		CategoryType type = null;
		
		switch(list.get(0).getIdx()) {
		case 1 : type = CategoryType.WORK; break;
		case 2 : type = CategoryType.TOOL;break;
		case 3 : type = CategoryType.PLACE;break;
		case 4 : type = CategoryType.ACC;break;
		}
		return type;
	}
	
//	public List<CodeVO> getDescendantCodes(int idx) {
//		return categoryMapper.getDescendantCodes(idx);
//	}
//	
	
	
	// public List<CodeVO> getCategoryListByVO(CodeVO category) {
	// return categoryMapper.getCategoryListByVO(category);
	// }
	//

	// public int getCountByVO(CodeVO category) {
	// return categoryMapper.getCountByVO(category);
	// }
	//

	@Transactional
	public void insertCode(CodeVO codeVO) {
		categoryMapper.insertCode(codeVO);
	}

	@Transactional
	public void updateCodeName(CodeVO codeVO) {
		categoryMapper.updateCodeName(codeVO);
	}

	@Transactional
	public void deleteCode(String code) {
		categoryMapper.deleteCode(code);
	}

	@Transactional
	public void insertCategory(CategoryVO categoryVO) {
		categoryMapper.insertCategory(categoryVO);
		categoryMapper.insertTreePath(categoryVO);
	}

//	@Transactional
//	public void updateCategory(CategoryVO categoryVO) {
//		categoryMapper.updateCategory(categoryVO);
//	}

	/**category 및 하위 category와 code를 제거**/
	@Transactional
	public void deleteCategory(int idx) {
		//순서중요
		categoryMapper.deleteCategoryInTree(idx);
		categoryMapper.deleteCategoryInCode(idx);
		categoryMapper.deleteCategoryInCategory(idx);
	}


	
}


