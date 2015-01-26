package com.spring.risk.web.actions;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import net.sf.json.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.*;

import com.spring.risk.domain.*;
import com.spring.risk.service.*;

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
	private transient PermitService permitService;
	

	@SpringBean
	private transient FileListService fileListService;
	
	
	private JSONObject jsonObj;
	private int lastIdx;
	private int ancIdx; //ancestor idx of treepath
	private String code;//code
	private int type;//work,tool,place
	private String codelist;
	private String workcode;
	private String placecodes;
	private String filename;
	
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
		List<FileVO> fileList = fileListService.getFileListByCode(code);
		//TODO : 통합할부분 통합 할 것 (기획 확정전까지는 분리된상태로 보류)
		
		switch(type) {//상세정보가 없는경우 아예 넣지 않음
		case 1 : //WORK
				WorkVO workVO = workListService.getWorkByWorkCode(code);
				if(workVO != null){
					List<AccDetailVO> inputAccList = workListService.getAccListByWorkcode(code);
					jsonObj.put("workVO", workVO);
					jsonObj.put("inputAccList", inputAccList);
				}
				break;
		case 2 : //TOOL
				ToolVO toolVO = toolListService.getToolByCode(code);	
				List<CheckVO> checkList = null;
				if(toolVO != null) {
					checkList = toolVO.getCheckList();//141209 
					jsonObj.put("toolVO", toolVO);
					jsonObj.put("checkList", checkList);
				}
				break;
		case 3 : //PLACE
				PlaceVO placeVO = placeListService.getPlaceByCode(code);
				if(placeVO != null)
					jsonObj.put("placeVO", placeVO);
				break;
		//case 4 ://ACC			
				//break;
		}
		
		jsonObj.put("fileList", fileList);
		
		System.out.println(jsonObj.toString());
		
		return new ForwardResolution(DATAPAGE);
	}

	
//	public Resolution getFile() {
//        InputStream is = null;
//
//		
//        try { 
//            is = new FileInputStream(new File(CategoryActionBean.CHEKCLIST_PATH + File.separator + filename)); 
//        } catch (FileNotFoundException ex) {
//        	ex.printStackTrace();
//        	return null;
//        } 
//        return new StreamingResolution("image/png", is); 
//        
//       		
//            try { 
//                is = new FileInputStream(new File(UPLOAD_PATH + File.separator +  fileVO.getVirtName()));
//            } catch (FileNotFoundException ex) {
//            	ex.printStackTrace();
//            } 
//
//            StreamingResolution resol = new StreamingResolution("application/download; charset=UTF-8", is).setFilename(URLEncoder.encode(fileVO.getFileName(), "UTF-8"));
//
//	}  
	public Resolution getPlaceList() {
		List<CodeVO> placeList = categoryService.getCodeListByType(CategoryType.PLACE.idx);	
		jsonObj = new JSONObject();		
		jsonObj.put("placeList", placeList);
		
		return new ForwardResolution(DATAPAGE);
	}
	
	public Resolution getPermitList() {
		
		ArrayList<PermitVO> permitList = new ArrayList<PermitVO>();
		//1.
		WorkVO workVO = workListService.getWorkByWorkCode(workcode);
		if(workVO != null && workVO.getPermit() == 1 ){ //1
			PermitVO vo = new PermitVO();
			vo.setName(workVO.getWorkName());
			vo.setContent(workVO.getMeasure());
			vo.setType(CategoryType.WORK.idx);
			permitList.add(vo);
		}
		
		if(placecodes != null) {
		//2.
		StringTokenizer stk = new StringTokenizer(placecodes, "|");
		while(stk.hasMoreElements()) {
			String placecode = (String) stk.nextElement();
			PlaceVO placeVO = placeListService.getPlaceByCode(placecode);
		
			if(placeVO != null &&  placeVO.getPermit() == 1) { //1
				PermitVO vo = new PermitVO();
				vo.setName(placeVO.getPlaceName());
				vo.setContent(placeVO.getGuide());
				vo.setType(CategoryType.PLACE.idx);
				permitList.add(vo);
			}
			
		}
		}
		
		//3.
		PermitVO permitVO = new PermitVO();
		permitVO.setWorkcode(workcode);
		permitVO.setPlacecodes(placecodes);
		PermitVO resultVO = permitService.getPermitByCode(permitVO);
		if(resultVO != null){
			resultVO.setType(99);
			
			ArrayList<String> codelist = new ArrayList<String>();
			codelist.add(workcode);
			StringTokenizer stk = new StringTokenizer(placecodes , "|");
			while(stk.hasMoreElements()) {
				codelist.add((String) stk.nextElement());
			}
			
			String[] codearray = codelist.toArray(new String[codelist.size()]);
			System.out.println(codearray.length);
			String comb_name = categoryService.getPermitNameByCode(codearray);
			System.out.println(comb_name);
			resultVO.setName(comb_name);
			permitList.add(resultVO);
		}	
		
		
		
		jsonObj = new JSONObject();		
		jsonObj.put("permitList", permitList);
		
		return new ForwardResolution(DATAPAGE);
	
	}
	
//	public Resolution getPermitVO() {
//		PermitVO permitVO = new PermitVO();
//		permitVO.setWorkcode(workcode);
//		permitVO.setPlacecodes(placecodes);
//		PermitVO resultVO = permitService.getPermitByCode(permitVO);
//		
//		jsonObj = new JSONObject();		
//		jsonObj.put("permitVO", resultVO);
//		
//		System.out.println(jsonObj.toString());
//		
//		return new ForwardResolution(DATAPAGE);
//	
//	}
//	
	public Resolution getChekcListImage() {
	        InputStream is = null;
	        
	        try { 
	            is = new FileInputStream(new File(CategoryActionBean.CHEKCLIST_PATH + File.separator + filename)); 
	        } catch (FileNotFoundException ex) {
	        	ex.printStackTrace();
	        	return null;
	        } 
	        return new StreamingResolution("image/png", is); 
	}  
	public Resolution getToolImage() {
        InputStream is = null;
        
        try { 
            is = new FileInputStream(new File(CategoryActionBean.TOOLIMG_PATH + File.separator + filename)); 
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        	return null;
        } 
        return new StreamingResolution("image/png", is); 
}  
	

	/**workcode!num_worker!worklevel 형태로 전달받음 */
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
				AccDetailVO vo = it.next();
				riskPoint += vo.getAccPoss();
				riskPoint += vo.getAccSerious();
				
			}
			
		}
		
		try{
			/**risklevel 계산 **/
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
		
		String riskgrade = "";
		//등급결정
		if(riskPoint < 3) riskgrade = "C";
		else if(riskPoint < 6) riskgrade = "B";
		else riskgrade = "A";
		
		jsonObj = new JSONObject();
		jsonObj.put("risklevel", riskPoint);
		jsonObj.put("riskgrade", riskgrade);
		
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


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getWorkcode() {
		return workcode;
	}


	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}


	public String getPlacecodes() {
		return placecodes;
	}


	public void setPlacecodes(String placecodes) {
		this.placecodes = placecodes;
	}


	

	


	
	
}
