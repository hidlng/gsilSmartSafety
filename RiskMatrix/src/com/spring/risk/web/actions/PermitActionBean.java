package com.spring.risk.web.actions;

import java.util.*;

import javax.servlet.http.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.*;
import util.*;

import com.spring.risk.domain.*;
import com.spring.risk.service.*;

@SessionScope
public class PermitActionBean extends AbstractActionBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private transient PermitService permitService;
	@SpringBean
	private transient CategoryService categoryService;
	
	@SpringBean
	private transient PlaceListService placeListService;
	
	
	private List<PermitVO> permitList;
	private List<CodeVO> workList;
	private List<CodeVO> placeList;//전체 placeList - insertForm에 사용
	private List<String> inputPlaceList;//입력받은 placeList , insertdetail에 사용
	
	
	private String permit_idx;
	
	private PermitVO permitVO;
	private boolean isModify; 

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
		
		
		
		refresh();
		return new ForwardResolution(RISKURL.PERMITLIST);

	}

	
//	public Resolution viewDetail() {		
//		return new ForwardResolution(RISKURL.PERMITDETAIL);
//	}
	
	/**insert form 출력전**/
	public Resolution insertDetailForm() {
		isModify = false;
		permitVO = new PermitVO();
		inputPlaceList = new ArrayList<String>();
		
		workList = categoryService.getCodeListByType(CategoryType.WORK.idx);
		placeList = categoryService.getCodeListByType(CategoryType.PLACE.idx);		
		return new ForwardResolution(RISKURL.PERMITDETAIL);
	}
	
	/**insert수행**/
	public Resolution insertDetail() {
		
		StringBuffer str = new StringBuffer();
		for(String placeCode : inputPlaceList){
			//placeCode는 code 형태로 넘어옴
			if(placeCode != null && !placeCode.equals("") && !placeCode.equals("null"))
				str.append(placeCode).append("|");
		}
		permitVO.setPlacecodes(str.toString());
		permitService.insertPermitVO(permitVO);
		
		refresh();
		return new ForwardResolution(RISKURL.PERMITLIST);

	}
	
	/**
	 * code1 | code2 | ....의 형태 를 파싱 
	 * @param placecodes
	 */
	public void parsePlaceCodes(PermitVO permitVO) {
		try{
		StringTokenizer stk = new StringTokenizer(permitVO.getPlacecodes(), "|");
		System.out.println(permitVO.getPlacecodes());
		System.out.println(stk.countTokens());
		while(stk.hasMoreElements()) {
			String parse = (String)stk.nextElement();
			System.out.println(parse);
			permitVO.getParse_codeList().add(parse);
		}
		
		System.out.println(permitVO.getParse_codeList());
		}catch(Exception e ){
			e.printStackTrace();	
		}
		
	}



	public Resolution updateDetailForm() {				
		isModify = true;
		permitVO = permitService.getPermitByIdx(permit_idx);	
		parsePlaceCodes(permitVO);	
		return new ForwardResolution(RISKURL.PERMITDETAIL);
	}
	
	public Resolution updateDetail() {		
		isModify = true;
		
		permitService.updatePermitVO(permitVO);
		refresh();
		return new ForwardResolution(RISKURL.PERMITLIST); 
	}

	private void refresh() {
		placeList = new ArrayList<CodeVO>();
		inputPlaceList = new ArrayList<String>();
		
		//list호출
		permitList = permitService.getPermitList();
		for(PermitVO vo : permitList) {
			parsePlaceCodes(vo);//view에서 사용위해 parse
		}
			
	}

	public Resolution deletePermit() {
		permitService.deletePermitByIdx(permit_idx);
		refresh();
		return new ForwardResolution(RISKURL.PERMITLIST); 
		
	}

	public List<PermitVO> getPermitList() {
		return permitList;
	}


	public void setPermitList(List<PermitVO> permitList) {
		this.permitList = permitList;
	}


	public String getPermit_idx() {
		return permit_idx;
	}


	public void setPermit_idx(String permit_idx) {
		this.permit_idx = permit_idx;
	}


	public PermitVO getPermitVO() {
		return permitVO;
	}


	public void setPermitVO(PermitVO permitVO) {
		this.permitVO = permitVO;
	}


	public List<CodeVO> getPlaceList() {
		return placeList;
	}


	public void setPlaceList(List<CodeVO> placeList) {
		this.placeList = placeList;
	}


	public List<String> getInputPlaceList() {
		return inputPlaceList;
	}


	public void setInputPlaceList(List<String> inputPlaceList) {
		this.inputPlaceList = inputPlaceList;
	}


	public boolean getIsModify() {
		return isModify;
	}


	public void setIsModify(boolean isModify) {
		this.isModify = isModify;
	}


	public List<CodeVO> getWorkList() {
		return workList;
	}


	public void setWorkList(List<CodeVO> workList) {
		this.workList = workList;
	}









	
	
}
