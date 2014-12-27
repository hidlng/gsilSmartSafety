package com.spring.risk.web.actions;

import java.util.*;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import com.spring.risk.domain.AccDetailVO;
import com.spring.risk.domain.CategoryType;
import com.spring.risk.domain.CategoryVO;
import com.spring.risk.domain.CheckVO;
import com.spring.risk.domain.CodeVO;
import com.spring.risk.domain.FileVO;
import com.spring.risk.domain.PlaceVO;
import com.spring.risk.domain.ToolVO;
import com.spring.risk.domain.UserVO;
import com.spring.risk.domain.WorkVO;
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
	private String code;//code
	private int type;//work,tool,place
	private String codelist;
	
	
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
	
	/** code & type에 맞는 상세정보 반환**/
	public Resolution getDetailByJSON() {
		//List<CodeVO> codeList = categoryService.getCodeListByCategory(code);
		
		jsonObj = new JSONObject();
		/*jsonObj.put("codeList", codeList);
		System.out.println(jsonObj.toString());
		*/
		
		List<FileVO> fileList = fileListService.getFileListByCode(code);
		//TODO : 통합할부분 통합 할 것 (기획 확정전까지는 분리된상태로 보류)
		
		switch(type) {
		case 1 : //WORK
				WorkVO workVO = workListService.getWorkByWorkCode(code);
				List<AccDetailVO> inputAccList = workListService.getAccListByWorkcode(code);
				jsonObj.put("workVO", workVO);
				jsonObj.put("inputAccList", inputAccList);
				break;
		case 2 : //TOOL
				ToolVO toolVO = toolListService.getToolByCode(code);					
				List<CheckVO> checkList = toolVO.getCheckList();//141209 
				jsonObj.put("toolVO", toolVO);
				jsonObj.put("checkList", checkList);
				break;
		case 3 : //PLACE
				PlaceVO placeVO = placeListService.getPlaceByCode(code);
				jsonObj.put("placeVO", placeVO);
				break;
		//case 4 ://ACC			
				//break;
		}
		
		jsonObj.put("fileList", fileList);
		
		System.out.println(jsonObj.toString());
		
		return new ForwardResolution(DATAPAGE);
	}


	/**workcode!num_worker!worklevel 로 전달받음 */
	public Resolution getRiskData() {
		//c를 JSON으로 넘겨받음
		//조합
		//grade 반환
		
		int riskPoint = 0;
		
		StringTokenizer token = new StringTokenizer(codelist, "!");
		if(token.hasMoreElements())  {//WORKCODE
			String code = (String) token.nextElement();
			
			//TODO: 1~2쿼리로동작하게 수정
			List<AccDetailVO> list = workListService.getAccDetailListByCode(code);
			
			Iterator<AccDetailVO> it = list.iterator();
			
			
			while(it.hasNext()) {
				AccDetailVO vo = (AccDetailVO)it.next();
				riskPoint += vo.getAccPoss();
				riskPoint += vo.getAccSerious();
				
			}
			
		}
		
		try{
			if(token.hasMoreElements())  {// num _worker
				int num_worker = Integer.valueOf((String)token.nextElement());
				System.out.println(num_worker);
				
				if(num_worker <= 3 ) riskPoint += 1;
				else if(num_worker <= 10) riskPoint += 2;
				else riskPoint += 3;	
			}
			
			if(token.hasMoreElements())  {//level
				int worklevel = Integer.valueOf((String)token.nextElement());
				System.out.println(worklevel);
				riskPoint += worklevel;
			}
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		String riskLevel = "";
		//등급결정
		if(riskPoint < 3) riskLevel = "C";
		else if(riskPoint < 6) riskLevel = "B";
		else riskLevel = "A";
		
		jsonObj = new JSONObject();
		jsonObj.put("riskLevel", riskLevel);
		
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getCodelist() {
		return codelist;
	}


	public void setCodelist(String codelist) {
		this.codelist = codelist;
	}


	

	


	
	
}
