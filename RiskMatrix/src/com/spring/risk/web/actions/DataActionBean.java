package com.spring.risk.web.actions;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import com.spring.risk.domain.CategoryVO;
import com.spring.risk.domain.CodeVO;
import com.spring.risk.domain.UserVO;
import com.spring.risk.service.CategoryService;
import com.spring.risk.service.FileListService;
import com.spring.risk.service.PlaceListService;
import com.spring.risk.service.ToolListService;
import com.spring.risk.service.UserService;
import com.spring.risk.service.WorkListService;

public class DataActionBean extends AbstractActionBean {
	
	private static final String DATAPAGE = "/WEB-INF/views/admin/data.jsp";
	@SpringBean
	private transient CategoryService categoryService;
	
	@SpringBean
	private transient WorkListService workListService;
	
	@SpringBean
	private transient ToolListService toolListService;
	
	@SpringBean
	private transient PlaceListService placeListService;
	

	@SpringBean
	private transient FileListService fileListService;
	
	
	private JSONObject jsonObj;
	private int lastIdx;
	private int ancIdx; //ancestor idx of treepath
	
	@SpringBean
	private transient UserService userService;

	@DefaultHandler
	public Resolution main() {
		return new ForwardResolution(DATAPAGE);
	}

	
	public Resolution getCategoryByJSON() {
		List<CategoryVO> catList = categoryService.getChildCategory(ancIdx);
		
		jsonObj = new JSONObject();
		jsonObj.put("catList", catList);
		System.out.println(jsonObj.toString());
		
		return new ForwardResolution(DATAPAGE);
	}
	
	
	/** lastIdx에 해당하는카테고리 안 코드리스트 반환**/
	public Resolution getCodeByJSON() {
		List<CodeVO> codeList = categoryService.getCodeListByCategory(lastIdx);
		
		jsonObj = new JSONObject();
		jsonObj.put("codeList", codeList);
		System.out.println(jsonObj.toString());
		
		return new ForwardResolution(DATAPAGE);
	}


	public JSONObject getJsonObj() {
		return jsonObj;
	}


	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}


	public int getLastIdx() {
		return lastIdx;
	}


	public void setLastIdx(int lastIdx) {
		this.lastIdx = lastIdx;
	}


	public int getAncIdx() {
		return ancIdx;
	}


	public void setAncIdx(int ancIdx) {
		this.ancIdx = ancIdx;
	}


	

	


	
	
}
