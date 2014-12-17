package com.smart.safety.controller;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.safety.domain.ContractorVO;
import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.SiteVO;
import com.smart.safety.domain.UserVO;
import com.smart.safety.services.ContractorService;
import com.smart.safety.services.LoginService;
import com.smart.safety.services.ManagerService;
import com.smart.safety.services.SiteService;

/**
 * Handles requests for the application home page.
 */

@Controller(value="loginController")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name="LoginService")
	private LoginService loginService;
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;

	@Resource(name="SiteService")
	private SiteService siteService;
	
	@RequestMapping(value = {"/","/login"})
	public void login(HttpSession session) {
		
		logger.info("login! {}", session.getId() );
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		if(session != null)
			session.invalidate();		
		return "login";
	}
	
	@RequestMapping("login_fail")
	public String loginFail(HttpSession session) {
//		if(session != null)
//			session.invalidate();		
		return "login_fail";
	}
	
	@RequestMapping("logout_success")
	public String logout_success(HttpSession session) {

		return "logout_success";
	}
	
	
	@RequestMapping("login_duplicate")
	public void login_duplicate(HttpSession session) {
		logger.info("Login Duplicated", session.getId());
		session.invalidate();
	}
	
	@RequestMapping(value = "/duplicateIdCheck")
	public void duplicateIdCheck(String id,  HttpServletResponse response) {
		UserVO userVO = loginService.getLoginUserById(id);
		try {
			if(userVO == null)			
				response.getWriter().print(true);			
			else
				response.getWriter().print(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="main", method = RequestMethod.GET)
	public String main(HttpSession session  , Principal principal) {
		String id = principal.getName();
		UserVO userVO = loginService.getLoginUserById(id);	
		
		SiteVO siteVO = new SiteVO();
		
		String userName="";
		switch(userVO.getLevel()) {
		case 1://안전관리자
			userName = managerSerivce.getManagerByID(id).getName();
			//안전관리자는 site 정보 없음
			break;
		case 2://현장관리자
		case 3://현장 사용자
			ManagerVO managerVO = managerSerivce.getManagerByID(id);
			userName = managerVO.getName(); //관리자/사용자 명 할당			
			siteVO = siteService.getSiteByIdx(managerVO.getSite_idx()) ; //소속 Site정보 가져옴
			session.setAttribute("managerVO", managerVO); //작업등록시 필요
			break;
		case 4://업체
			ContractorVO contractorVO = contractorService.getContractorByID(id);
			userName = contractorVO.getCont_name(); //업체명 할당
			siteVO = siteService.getSiteByIdx(contractorVO.getSite_idx());
			session.setAttribute("contractorVO", contractorVO); //작업등록시 필요
			break;
		}
		
		session.setAttribute("userLoginInfo", userVO);
		session.setAttribute("userName", userName);
		session.setAttribute("siteVO", siteVO);
		
		//현장 사용자/업체가 소속된 현장이 삭제된 (혹은 잘못 링크된경우) 경우 로그아웃 시킴
		if (siteVO == null) { 
			return "redirect:logout";	
		}
		logger.info("Welcome login_success! {}, {}", session.getId(), userVO.getId() + "/" + userVO.getPassword());
		
		return "redirect:workList";

	}
	
	@RequestMapping(value ="test")
	public void test(HttpSession session  , Principal principal) {
		
	}
	
	@RequestMapping(value ="jusoPopup")
	public void jusoPopup(HttpSession session  , Principal principal) {
		
	}
}
