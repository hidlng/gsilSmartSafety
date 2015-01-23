package com.spring.risk.web.actions;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.http.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.*;

import org.springframework.dao.*;

import util.*;

import com.spring.risk.domain.*;
import com.spring.risk.service.*;

@SessionScope
public class CategoryActionBean extends AbstractActionBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

//
//	private static final int WORK = 1;
//	private static final int TOOL = 2;
//	private static final int PLACE = 3;
//	private static final int ACC = ;
	
	//1 : WORK, 2: TOOL, 3: PLACE, 4: ACC

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
	
	
	public static final String UPLOAD_PATH = "C:\\server\\upload";
	public static final String CHEKCLIST_PATH = "C:\\server\\upload\\checklist";
	public static final String TOOLIMG_PATH =  "C:\\server\\upload\\tool";
	
	
	/** for view**/
	private List<List<CategoryVO>> totalList;	
	private List<CategoryVO> childList; //chld 보여줄떄
	private List<CodeVO> codeList; //child Code 보여줄때
	private String categoryPath;
	
	
	/**category manage**/
	int deleteCategoryIdx;
	
	/**for 'selected' in selectbox **/
	private List<CategoryVO> parentList;
	
	
	/**parameter**/
	private List<String> idxList = new ArrayList<String>();
	private int lastIdx = 1;
	
	
	/**input**/
	String inputCode;
	String inputName;
	String catType = "code"; // code or category


	/**modfiy,delete Code**/
	//String modifyCode;
	//String deleteCode;
	
	/**detailContent**/
	String contentCode;
	CategoryType codeType;//place, acc, work, tool 구분
	
	private String test;	
	private String resultMsg;
	
	/**work Detail**/
	private List<AccDetailVO> inputAccList = new ArrayList<AccDetailVO>();
	private List<CodeVO> accList;
	private WorkVO workVO;
	
	/**Tool Detail**/
	private ToolVO toolVO;	
	private PlaceVO placeVO;
	private List<CheckVO> checkList = new ArrayList<CheckVO>();//141209
	private List<CheckVO> modifyCheckList = new ArrayList<CheckVO>();//141210
	private int deleteCheckOrder=0;
	private int checkCount=1;
	


	/**File Upload **/
	private FileBean fileBean;
	private String fileIdx;
	//private String fileCode;
	//private String fileName;
	private List<FileVO> fileList;//for get List
	private List<FileBean> fileBeanList;//for insert
	private List<Integer> delFileList;
	private int uploadFileMaxIdx = 2;
	//private int inputFileCurSize = 0;
	//private String deleteFileCode;
	//private String deleteFileName;
	
	
	
	/**search**/
	private String searchString;
	
	
	private boolean isModify=false;

	
	/**paging**/
	//private static final int MAX_SIZE = 100;
	private static final int PAGE_SIZE = 10;



	private int pageNum=1;
	private int totalPage=1;

	//private boolean isCodeModify = false;//코드 이름 수정 
	/**User**/
	//LoginActionBean userBean;

	public void initParameter() {
		lastIdx = 1;
		pageNum = 1;
		totalPage = 1;
		contentCode="";
	}
	
	public boolean isAuthenticated() {
		HttpSession session = context.getRequest().getSession();
		if( session != null) {
			LoginActionBean userBean = (LoginActionBean) session.getAttribute("userBean");
	    
			 if (userBean != null && userBean.isAuthenticated()) //인증			
				return true;
			 else 			
				return false;
		}else 
			return false;
		 
	}

	@DefaultHandler
	public Resolution viewMain() {	
		if(!isAuthenticated()) 
			return new RedirectResolution(RISKURL.REDIRECT_LOGINPAGE);
		
//		HttpSession session = context.getRequest().getSession();
//	    boolean isLogin = (boolean)session.getAttribute("init");
//		if(isLogin) {//로그인 한 경우 파라메터 초기화
//			initParameter();
//			session.setAttribute("init", false);
//		}
	    
		searchString=null;//링크선택, category변경시에는 검색어를 초기화함
		pageNum = 1;
		refresh();
		return new ForwardResolution(RISKURL.CATEGORYLIST);

	}

	
	/**refresh each lists**/
	/**synchronized 추가 : 네트워크지연상태에서 하위 카테고리 2개 동시선택시 문제발생하여 추가**/
	private synchronized void refresh() {
		
		//조상 찾기
		List<CategoryVO> ancList = categoryService.getAncestorCategory(lastIdx);
		
		
		/**ancestor&their sibling list**/ 
		makeTotalList(ancList);
		makeCategoryPath(ancList);
		makeParentList(ancList);
		/**childlist**/
		childList = categoryService.getChildCategory(lastIdx);
		
		/**codelist of last Idx**/
		makeCodeList();	
		
	}


	/**parameter로 넘어오므로별도 처리 하지 않음**/
	public Resolution paging(){
		makeCodeList();
		return new ForwardResolution(RISKURL.CATEGORYLIST);
	}


	/**Search**/
	public Resolution searchCode(){
		pageNum = 1;//search전 페이지 초기화
		int count = makeCodeList();
		resultMsg = "(" + count + ") 건 검색";
		
		return new ForwardResolution(RISKURL.CATEGORYLIST);
	}

	private int makeCodeList() {
		//codeList = categoryService.getCodeListByCategory(lastIdx);
		CodeVO vo = new CodeVO();
		if(searchString == null) 
			searchString="";
			
		vo.setCode("%"+searchString+"%");
		vo.setName("%"+searchString+"%");
		vo.setStart((pageNum - 1) * PAGE_SIZE);
		vo.setSize(PAGE_SIZE);
		vo.setCategory(lastIdx);		
		int count = categoryService.getCountByCode(vo); 
		totalPage = (int) Math.ceil((double) count/PAGE_SIZE );
		codeList = categoryService.getDescendantCodeVOList(vo);
		
		return count;
	}

	  
	public Resolution viewDetail() {
		CodeVO code = categoryService.getCodeVO(contentCode);
		codeType = categoryService.getCategoryType(code);
		
		fileList = fileListService.getFileListByCode(code.getCode());
		//TODO : 통합할부분 통합 할 것 (기획 확정전까지는 분리된상태로 보류)
		
		switch(codeType) {
		case WORK :
				workVO = workListService.getWorkByWorkCode(code.getCode());
				inputAccList = workListService.getAccListByWorkcode(code.getCode());	
				accList = categoryService.getCodeListByCategory(CategoryType.ACC.idx);
				break;
		case TOOL :
				toolVO = toolListService.getToolByCode(code.getCode());
				checkList = toolVO.getCheckList();//141209 
				break;
		case PLACE :
				placeVO = placeListService.getPlaceByCode(code.getCode());
				break;
		case ACC :
			
				break;
		}
		
		
		return new ForwardResolution(RISKURL.TOTALLIST);

	}
	


	

	public Resolution insertCategory() {
		
		try{
			if(catType != null && !catType.equals("")) {
				if(catType.toLowerCase().equals("code")) {//CODE - 대문자로 입력
					CodeVO vo = new CodeVO();
					vo.setCode(inputCode.toUpperCase());
					vo.setName(inputName);
					vo.setCategory(lastIdx);
					categoryService.insertCode(vo);
					resultMsg = "MSG : INSERT [CODE -" + inputCode +  ", NAME -" + inputName + " PARENT -" + lastIdx + "]";
				}else if(catType.toLowerCase().equals("category")) {//CATEGORY
					//if(lastIdx==0)
					if(lastIdx==4) throw new Exception("cannot add category in accident type");
						
					CategoryVO vo = new CategoryVO();
					vo.setIdx(categoryService.getMaxCategoryIdx() + 1);
					vo.setName(inputName);
					vo.setParent(lastIdx);
				
					categoryService.insertCategory(vo);
				}
					
				
			}
		
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			resultMsg="Error : 중복된 카테고리 혹은 코드 입력";
		}catch(NullPointerException e) {
			e.printStackTrace();
			resultMsg="Error : Null값 존재";
		}catch(Exception e){
				e.printStackTrace();
				return new ErrorResolution(e.hashCode(), e.getMessage());	
		}finally {
			
			refresh();
		}
		return new ForwardResolution(RISKURL.CATEGORYLIST);
	}

	public Resolution deleteCategory() {
		categoryService.deleteCategory(deleteCategoryIdx);
		refresh();
		
		return new RedirectResolution(CategoryActionBean.class);
	}


	
	/**단순히 hidden에 contentCode 설정할 경우 첫 delete 이후 contentCode가 계속 동일한 값을 가지고 박혀있게됨. 그래서 우회 방법 사용함
	 * categoryList deleteCheck() 참조
	 * **/	
	public Resolution deleteCode() {		
		categoryService.deleteCode(contentCode);		
		setResultMsg("MSG : DELETE CODE " + contentCode);
		
		refresh();
		return new ForwardResolution(RISKURL.CATEGORYLIST);
		
	}

	
	
	/**File Read**/
	/**file load는 virtname으로 반환은 원 filename으로
	 * @throws UnsupportedEncodingException **/
	public Resolution getFile() throws UnsupportedEncodingException { 

        InputStream is = null;		
		FileVO fileVO = fileListService.getFileVOByIdx(fileIdx);		
	
        try { 
            is = new FileInputStream(new File(UPLOAD_PATH + File.separator +  fileVO.getVirtName()));
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        } 

        StreamingResolution resol = new StreamingResolution("application/download; charset=UTF-8", is).setFilename(URLEncoder.encode(fileVO.getFileName(), "UTF-8"));

        return resol ;
    }  
	
	
	public Resolution viewInsertForm() {
		CodeVO code = categoryService.getCodeVO(contentCode);
		codeType = categoryService.getCategoryType(code);
		
		
		//type에 따라 다른 쿼리를 사용하여 list를 불러옴 (코드는 contentCode 이용)
		//jsp level에서는 반환되는 Type값에따라 다른 페이지를 전달 
		
		isModify=false;
		fileList = fileListService.getFileListByCode(code.getCode());
		modifyCheckList = new ArrayList<CheckVO>();
		delFileList = new ArrayList<Integer>();
		switch(codeType) {
		case WORK :	workVO = new WorkVO();
					workVO.setWorkCode(code.getCode());
					workVO.setWorkName(code.getName());
					inputAccList = new ArrayList<AccDetailVO>();				
					accList = categoryService.getCodeListByCategory(CategoryType.ACC.idx);
					break;
		case TOOL :		
					toolVO = new ToolVO();
					toolVO.setToolCode(code.getCode());	
					toolVO.setToolName(code.getName());
					checkList = new ArrayList<CheckVO>();//141209 
					checkList.add(new CheckVO());
					break;
		case PLACE :		
					placeVO = new PlaceVO();
					placeVO.setPlaceCode(code.getCode());
					placeVO.setPlaceName(code.getName());	
					break;
		case ACC:
			break;
		default:
			break;
		}
	
		return new ForwardResolution(RISKURL.TOTALINSERT);
		
	}
	

	
	
	public Resolution insertDetail() {
		/**insert시에 CodeName 수정**/
		CodeVO tmp_codeVO = new CodeVO();
		
		
		try {
			String fileCode = null;
			switch (codeType) {
			case WORK:
				workListService.insertWorkVO(workVO, inputAccList);
				tmp_codeVO.setCode(workVO.getWorkCode());
				tmp_codeVO.setName(workVO.getWorkName());
				fileCode = workVO.getWorkCode();
				break;
			case TOOL:
				//toolVO.setCheckList(checkList); // insert/update page에서 입력/변경된 checkList를 할당해줌
				toolVO.setCheckList(modifyCheckList);
				toolListService.insertToolVO(toolVO);
			
				
				tmp_codeVO.setCode(toolVO.getToolCode());
				tmp_codeVO.setName(toolVO.getToolName());
				fileCode = toolVO.getToolCode();
				break;
			case PLACE:
				placeListService.insertPlaceVO(placeVO);
				tmp_codeVO.setCode(placeVO.getPlaceCode());
				tmp_codeVO.setName(placeVO.getPlaceName());
				fileCode = placeVO.getPlaceCode();
				break;
			case ACC:
				break;
			default:
				break;
			}

			/**update Code Name -141117**/
			categoryService.updateCodeName(tmp_codeVO);
			
			/** input File **/
			fileListService.insertFileListVO(fileCode, fileBeanList);
			
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg="Error : 파일 업로드시 에러 발생";
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			resultMsg="Error : 중복된 사고 유형 입력";
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			insertFormClear();
			refresh();
		}
		return new ForwardResolution(RISKURL.CATEGORYLIST);
	}
	
	public Resolution updateDetailForm() {
		isModify=true;
		fileBeanList = new ArrayList<FileBean>();
		modifyCheckList = new ArrayList<CheckVO>();
		return new ForwardResolution(RISKURL.TOTALINSERT);
	}

	
	public Resolution updateDetail() {		
		
		CodeVO tmp_codeVO = new CodeVO();
		String fileCode="";
		try {
		switch(codeType) {
		case WORK : 
			workListService.updateWorkVO(workVO, inputAccList);
			tmp_codeVO.setCode(workVO.getWorkCode());
			tmp_codeVO.setName(workVO.getWorkName());
			fileCode=workVO.getWorkCode();
			break;
		case TOOL :
			//update시 넘겨준 checklist의 size가 web에서 넘겨받은 checklist size보다 클 경우 , 남은 항목들은 기존의 값을 그대로 가지고있으므로
			//이를 지워줘야함
			//toolVO.setCheckList(checkList.subList(0, checkCount));
			toolVO.setCheckList(modifyCheckList);
			toolListService.updateToolVO(toolVO);
				
			tmp_codeVO.setCode(toolVO.getToolCode());
			tmp_codeVO.setName(toolVO.getToolName());
			fileCode=toolVO.getToolCode();
			break;
		case PLACE :
			placeListService.updatePlaceVO(placeVO);
			tmp_codeVO.setCode(placeVO.getPlaceCode());
			tmp_codeVO.setName(placeVO.getPlaceName());
			fileCode=placeVO.getPlaceCode();
			break;
		case ACC:
			break;
		default:
			break;
		}
		
		/**update Code Name -141117**/
		categoryService.updateCodeName(tmp_codeVO);
		
		
		
		
		
		/** input File **/
		if(delFileList != null)deleteFile();//fileList에서 deleteFileList에 해당하는 내용을 제거(수정화면에서 파일 x 버튼누른것들)
		fileListService.updateFileListVO(fileCode, fileList, fileBeanList);
		
		} catch (IOException e) {
			e.printStackTrace();
	
		}  catch (DuplicateKeyException e) {
			e.printStackTrace();
			resultMsg="Error : 중복된 사고유형 입력";
		}catch (Exception e) {
			e.printStackTrace();
	
		} finally {
			insertFormClear();
			refresh();
		}	
		return new ForwardResolution(RISKURL.CATEGORYLIST); 
	}
	
	public void deleteFile() {
		if(delFileList.size() == 0) return;
		Iterator<FileVO>it =  fileList.iterator();
		while(it.hasNext()) {
			FileVO vo = it.next();
			
			for(int delIdx : delFileList){
				if( delIdx == Integer.valueOf(vo.getFile_idx()) ) {
					it.remove();
				}
			}
		}
	}
	
//	public Resolution addCheckList() {
//		checkList.add(new CheckVO());
//		return new ForwardResolution(TOTALINSERT);
//	}
//	
//	public Resolution removeCheckList() {
//		checkList.remove(deleteCheckOrder);
//		return new ForwardResolution(TOTALINSERT);
//	}
	
	public Resolution deleteDetail() {
		switch(codeType) {
		case WORK : 
			
			break;
		case TOOL : 
					
			break;
		case PLACE :
			
			break;
		case ACC:
			break;
		default:
			break;
		}
		insertFormClear();
		refresh();		
		return new ForwardResolution(RISKURL.CATEGORYLIST); 
	}
	

	private void insertFormClear() {
		workVO = new WorkVO();
		toolVO = new ToolVO();
		placeVO = new PlaceVO();
		fileBeanList = new ArrayList<FileBean>();
		checkList = new ArrayList<CheckVO>();
		inputAccList = null;
		inputCode="";
		inputName="";
	}

	
	public void makeTotalList(List<CategoryVO> anc_list){
		totalList = new ArrayList<List<CategoryVO>>();
		
		//최상위 root 별도 추가
		List<CategoryVO> root = categoryService.getRootCategory();
		totalList.add(root);
			
		/**check Ancestor List for select box**/ 
		/**get Path of lastIdx**/
		/**get Sibling of lastIdx **/
		for(CategoryVO cat : anc_list){
			List<CategoryVO> sibList = categoryService.getSiblingCategory(cat.getIdx());//각각의 형제리스트를 구해옴
			if( sibList.size() > 0 )//0:root의 경우 형제값 못찾음
				totalList.add(sibList);
		}
		
		
		
	}
	

	
	public void makeCategoryPath(List<CategoryVO> anc_list) {
		StringBuffer buf = new StringBuffer();
		for(CategoryVO cat : anc_list){
			buf.append(cat.getName()).append(" : "); //category경로를 directory path처럼 나타냄
		}
		
		categoryPath = buf.toString();//path 설정
	}
	
	public void makeParentList(List<CategoryVO> anc_list) {
		parentList = new ArrayList<CategoryVO>();
		
		for(CategoryVO cat : anc_list){
			parentList.add(cat); //parentList를 기록해 selectbox에서 select 표현
		}
	}
	

	
	public List<List<CategoryVO>> getTotalList() {
		if(totalList == null){
			totalList = new ArrayList<List<CategoryVO>>();
			totalList.add(categoryService.getRootCategory());
		}
		return totalList;
	}







	public List<CategoryVO> getCategoryList() {
		return getParentList();
	}




	public void setCategoryList(List<CategoryVO> categoryList) {
		this.setParentList(categoryList);
	}



	public List<String> getIdxList() {
		return idxList;
	}




	public void setIdxList(List<String> idxList) {
		this.idxList = idxList;
	}




	public int getLastIdx() {
		return lastIdx;
	}




	public void setLastIdx(int lastIdx) {
		this.lastIdx = lastIdx;
	}




	public void setTotalList(List<List<CategoryVO>> totalList) {
		this.totalList = totalList;
	}




	public String getInputCode() {
		return inputCode;
	}




	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}




	public String getInputName() {
		return inputName;
	}




	public void setInputName(String inputName) {
		this.inputName = inputName;
	}




	public String getCatType() {
		return catType;
	}




	public void setCatType(String catType) {
		this.catType = catType;
	}




	public List<CategoryVO> getChildList() {
		return childList;
	}




	public void setChildList(List<CategoryVO> childList) {
		this.childList = childList;
	}




	public String getCategoryPath() {
		return categoryPath;
	}




	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}




	public List<CategoryVO> getParentList() {
		return parentList;
	}




	public void setParentList(List<CategoryVO> parentList) {
		this.parentList = parentList;
	}




	public List<CodeVO> getCodeList() {
		return codeList;
	}




	public void setCodeList(List<CodeVO> codeList) {
		this.codeList = codeList;
	}






	public String getResultMsg() {
		return resultMsg;
	}




	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}




	public String getContentCode() {
		return contentCode;
	}




	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}


	public WorkListService getWorkListService() {
		return workListService;
	}


	public void setWorkListService(WorkListService workListService) {
		this.workListService = workListService;
	}




	public List<AccDetailVO> getInputAccList() {
		return inputAccList;
	}


	public void setInputAccList(List<AccDetailVO> inputAccList) {
		this.inputAccList = inputAccList;
	}




	public WorkVO getworkVO() {
		return workVO;
	}


	public void setworkVO(WorkVO workVO) {
		this.workVO = workVO;
	}


	public boolean getIsModify() {
		return isModify;
	}


	public void setIsModify(boolean isModify) {
		this.isModify = isModify;
	}




	public int getDeleteCategoryIdx() {
		return deleteCategoryIdx;
	}

	public void setDeleteCategoryIdx(int deleteCategoryIdx) {
		this.deleteCategoryIdx = deleteCategoryIdx;
	}

	public ToolVO getToolVO() {
		return toolVO;
	}

	public void setToolVO(ToolVO toolVO) {
		this.toolVO = toolVO;
	}

	public PlaceVO getPlaceVO() {
		return placeVO;
	}

	public void setPlaceVO(PlaceVO placeVO) {
		this.placeVO = placeVO;
	}

	public FileBean getFileBean() {
		return fileBean;
	}

	public void setFileBean(FileBean fileBean) {
		this.fileBean = fileBean;
	}

	

	public List<FileVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileVO> fileList) {
		this.fileList = fileList;
	}

	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}

	public void setFileBeanList(List<FileBean> fileBeanList) {
		this.fileBeanList = fileBeanList;
	}


	public CategoryType getCodeType() {
		return codeType;
	}


	public void setCodeType(CategoryType codeType) {
		this.codeType = codeType;
	}


	public List<CodeVO> getAccList() {
		return accList;
	}


	public void setAccList(List<CodeVO> accList) {
		this.accList = accList;
	}


	


	public int getUploadFileMaxIdx() {
		return uploadFileMaxIdx;
	}


	public void setUploadFileMaxIdx(int uploadFileMaxIdx) {
		this.uploadFileMaxIdx = uploadFileMaxIdx;
	}




	public String getSearchString() {
		return searchString;
	}


	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<CheckVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<CheckVO> checkList) {
		this.checkList = checkList;
	}

	public int getDeleteCheckOrder() {
		return deleteCheckOrder;
	}

	public void setDeleteCheckOrder(int deleteCheckOrder) {
		this.deleteCheckOrder = deleteCheckOrder;
	}

	public int getCheckCount() {
		return checkCount;
	}

	public void setCheckCount(int checkCount) {
		this.checkCount = checkCount;
	}

	public List<CheckVO> getModifyCheckList() {
		return modifyCheckList;
	}

	public void setModifyCheckList(List<CheckVO> modifyCheckList) {
		this.modifyCheckList = modifyCheckList;
	}



	public String getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(String fileIdx) {
		this.fileIdx = fileIdx;
	}

	public List<Integer> getDelFileList() {
		return delFileList;
	}

	public void setDelFileList(List<Integer> delFileList) {
		this.delFileList = delFileList;
	}







	
	
}
