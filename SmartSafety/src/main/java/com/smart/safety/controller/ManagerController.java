package com.smart.safety.controller;

import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.*;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.smart.safety.domain.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;


@Controller(value="ManagerController")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Resource(name="LoginService")
	private LoginService loginService;
	
	@Resource(name="ManagerService")
	private ManagerService managerService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	@RequestMapping(value = "managerList")
	public void userList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			@RequestParam(value="listLevel", defaultValue="1")int listLevel,
			HttpSession session ) {
		
		/**searching**/
		String keyword = "%" + searchWord + "%";
		ManagerVO managerVO = new ManagerVO();
		
		managerVO.setId(keyword);
		managerVO.setName(keyword);
		managerVO.setPosition(keyword);
		managerVO.setPhone(keyword);
		managerVO.setGrade(keyword);
		
		//Level 지정 : 본사 or 현장  
		//managerVO.setLevel(listLevel+"");
		managerVO.setSearchlevel(new int[]{listLevel});
		
		//안전관리자/현장사용자 구분
		managerVO.setIsmanager(1);

		managerVO.setSite_idx("%");
		
		
		/**paging**/
		int rowCnt = managerService.getRowCount(managerVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
		managerVO.setStart((pageNum - 1) * MAX_ROW_NUM);
		managerVO.setSize(MAX_ROW_NUM);		

		List<ManagerVO> list = managerService.getManagerListByVO(managerVO);
		
		/** model setting **/

		model.addAttribute("listLevel", listLevel);
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
		model.addAttribute("managerList", list);
		
		session.setAttribute("contentView", "managerList");		
	}
	
	@RequestMapping(value = "siteUserList")
	public void siteUserList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			HttpSession session) {
		
		/**searching**/
 		String keyword = "%" + searchWord + "%";
		ManagerVO managerVO = new ManagerVO();
		
		//현장사용자 
		managerVO.setId(keyword);
		managerVO.setName(keyword);
		managerVO.setPhone(keyword);
		//managerVO.setCont_name(keyword);
		//managerVO.setCont_work(keyword);
			
		managerVO.setSearchlevel(new int[]{USERLEVEL.CONT_CHEIF.idx, USERLEVEL.CONT_LEADER.idx,USERLEVEL.CONT_INSPECTOR.idx,});
		managerVO.setIsmanager(0);
		
		
		//현장관리자 -> 자신의 현장의 사용자만 출력. 
		//SS 관리자 - > 전부 출력
		String site_idx = ((SiteVO)session.getAttribute("siteVO")).getSite_idx();
		managerVO.setSite_idx(site_idx);
		

		
		/**paging**/
		int rowCnt = managerService.getRowCount(managerVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
		managerVO.setStart((pageNum - 1) * MAX_ROW_NUM);
		managerVO.setSize(MAX_ROW_NUM);
		
		List<ManagerVO> list = managerService.getManagerListByVO(managerVO);
		
		/**업체 1건이라도 등록되어있는지 체크하여 등록버튼 show여부 결정**/
		SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");
		List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx());
		boolean canRegister = false;
		if(contList.size() > 0) canRegister = true;
		
		/** model setting **/
		model.addAttribute("canRegister" , canRegister);
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
		model.addAttribute("managerList", list);
		session.setAttribute("contentView", "siteUserList");
	}
	
	
	@RequestMapping(value = "registerManager")
	public void registerManager(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model, HttpSession session) {
		
		if(updateIdx != null && !updateIdx.equals("")) {
			ManagerVO managerVO = managerService.getManagerByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("managerVO", managerVO);
		}
		else {//insertMode
			model.addAttribute("updateMode", false);
			model.addAttribute("managerVO", new ManagerVO());
		}
		
		setBaseModel(session, model);
		
	}
	
	@RequestMapping(value = "registerSiteUser")
	public void registerSiteUser(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model, HttpSession session) {
		
		if(updateIdx != null && !updateIdx.equals("")) { //update
			ManagerVO managerVO = managerService.getManagerByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("managerVO", managerVO);
		}
		else {//insertMode
			model.addAttribute("updateMode", false);
			model.addAttribute("managerVO", new ManagerVO());
		}
		
		setBaseModel(session, model);
	}
		
	
	private void setBaseModel(HttpSession session , Model model) {
		//현장 리스트 출력 (registerManager에서 사용)
		List<SiteVO> siteList = siteService.getSiteList();
		model.addAttribute("siteList" , siteList);
		
		//업체리스트 출력 (siteUser에서 사용)	
		SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");	
		List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
		model.addAttribute("contList", contList);
		
		//소장 이미등록되었는지 여부 파악
		boolean isRegisteredChief = false;
		ManagerVO chiefVO = managerService.getChiefBySiteIdx(siteVO.getSite_idx());
		if(chiefVO != null) isRegisteredChief = true;
		model.addAttribute("isRegisteredChief" , isRegisteredChief);
		
	}

	@RequestMapping(value = {"insertManager","insertSiteUser"}, method = RequestMethod.POST)
	public String insertManager(HttpSession session, @ModelAttribute @Valid ManagerVO managerVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		
		//true -> insertManager , false -> insertSiteUser
		boolean isManager = request.getServletPath().equals("/insertManager");
		boolean hasError = false;
		
		setBaseModel(session, model);
		
		if(bindingResult.hasErrors())
			hasError = true;		
		else {
			try{
				if(managerVO.getSite_idx() == null)managerVO.setSite_idx("NONE");//임시. 본사관리자 등록시 site_idx가 null이면 like조건에 걸리지 않아 list에 나오지않음&삽입안됨
				managerService.insertManager(managerVO);
			}catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
	
		if(hasError) {//에러 발생시 왔던 주소로 되돌아감
			//error발생시 되돌아가더라도 insertMode상태 유지위함
			model.addAttribute("updateMode", false);		
			
			if(isManager)	
				return "registerManager";
			else
				return "registerSiteUser";
		}else{//성공시 list로 이동
			if(isManager)
				return "redirect:managerList";
			else
				return"redirect:siteUserList";
		}
		
	}
	
	@RequestMapping(value = {"updateManager", "updateSiteUser"}, method = RequestMethod.POST)
	public String updateManager(HttpSession session, @ModelAttribute @Valid ManagerVO managerVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		logger.debug(managerVO.getName() + session.getId()) ;
		
		//true -> updateManager , false -> updateSiteUser
		boolean isManager = request.getServletPath().equals("/updateManager"); 		
		boolean hasError = false;
		
		setBaseModel(session, model);
		
		if(bindingResult.hasErrors())
			hasError = true;
		
		else {
			try{
				if(managerVO.getSite_idx() == null)managerVO.setSite_idx("NONE");//임시. 본사관리자 등록시 site_idx가 null이면 like조건에 걸리지 않아 list에 나오지않음&삽입안됨
				managerService.updateManager(managerVO);
			}catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		
		if(hasError) {//에러 발생시 왔던 주소로 되돌아감
			//error발생시 되돌아가더라도 updateMode상태 유지위함
			model.addAttribute("updateMode", true);		
			
			if(isManager)	
				return "registerManager";
			else 
				return "registerSiteUser";
		}else{//성공시 list로 이동
			if(isManager)	
				return "redirect:managerList";
			else
				return"redirect:siteUserList";
		}
		

		
	}
	
	@RequestMapping(value = "deleteManager", method = RequestMethod.POST)
	public String deleteManager(@ModelAttribute @Valid ManagerVO managerVO) {
		managerService.deleteManager(managerVO.getManager_idx(), managerVO.getUser_idx());
		return "redirect:managerList";
	}
	
	@RequestMapping(value = "deleteSiteUser", method = RequestMethod.POST)
	public String deleteSiteUser(@ModelAttribute @Valid ManagerVO managerVO) {
		managerService.deleteManager(managerVO.getManager_idx(), managerVO.getUser_idx());
		return "redirect:siteUserList";
	}
	
	
	

}
