package com.smart.safety.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.SiteVO;
import com.smart.safety.domain.UserVO;
import com.smart.safety.services.LoginService;
import com.smart.safety.services.ManagerService;
import com.smart.safety.services.SiteService;
import com.smart.safety.util.Paging;


@Controller(value="ManagerController")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Resource(name="LoginService")
	private LoginService loginService;
	
	@Resource(name="ManagerService")
	private ManagerService managerService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	
	public static final int MAX_ROW_NUM=10;
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
		managerVO.setPhone(keyword);
		managerVO.setGrade(keyword);
		
		//Level 지정 : 본사 or 현장  
		managerVO.setLevel(listLevel);
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
		managerVO.setCont_name(keyword);
		//managerVO.setCont_work(keyword);
		
		 //현장사용자 : level 3
		managerVO.setLevel(3);
		
		
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
		
		/** model setting **/
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
		model.addAttribute("managerList", list);
		session.setAttribute("contentView", "siteUserList");
	}
	
	
	@RequestMapping(value = {"registerManager","registerSiteUser"})
	public void registerManager(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model) {
		
		if(updateIdx != null && !updateIdx.equals("")) {
			ManagerVO managerVO = managerService.getManagerByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("managerVO", managerVO);
		}
		else {//insertMode
			model.addAttribute("updateMode", false);
			model.addAttribute("managerVO", new ManagerVO());
		}
		
		//현장 리스트 출력 (level 상관없이 일단 가지고옴)
		List<SiteVO> siteList = siteService.getSiteList();
		model.addAttribute("siteList" , siteList);
		
	}
	
	@RequestMapping(value = {"insertManager","insertSiteUser"}, method = RequestMethod.POST)
	public String insertManager(HttpSession session, @ModelAttribute @Valid ManagerVO managerVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		
		//true -> insertManager , false -> insertSiteUser
		boolean isManager = request.getServletPath().equals("/insertManager");
		boolean hasError = false;
		
		if(bindingResult.hasErrors())
			hasError = true;		
		else {
			try{
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
		
		
		if(bindingResult.hasErrors())
			hasError = true;
		
		else {
			try{
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
	
	
	
	
	

}
