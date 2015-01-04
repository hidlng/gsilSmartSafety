package com.smart.safety.controller;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
	
	@RequestMapping(value="loginProcess", method = RequestMethod.POST)
	public void loginProcess(HttpSession session, HttpServletRequest request) {
		String pid = request.getParameter("pid");
		
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
	public String main(HttpSession session  , Principal principal, HttpServletRequest request, @RequestParam(value="pid")String pid) {
		
		String id = principal.getName();
		UserVO userVO = loginService.getLoginUserById(id);	
		
		//pid가 존재할 경우 모바일 접속이므로 해당정보를 업데이트해줌
		//pid 존재하지 않을경우 pass
		if(pid != null & !pid.equals("")) {
			userVO.setPid(pid);
			loginService.updateUserPID(userVO);
		}
		
		
		SiteVO siteVO = new SiteVO();
//		<c:if test ="${sessionScope.userLoginInfo.level == 0}">SS 관리자</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 1}">본사 관리자(EHS팀)</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 2}">현장 안전 관리자</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 3}">CEO</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 4}">현장 사용자(소장)</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 5}">작업팀장(2)</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 6}">일반 작업자(3)</c:if>
//		<c:if test ="${sessionScope.userLoginInfo.level == 7}">현장 업체</c:if>
		String userName="";
		switch(userVO.getLevel()) {
		case 1://본사관리자(EHS팀)
			userName = managerSerivce.getManagerByID(id).getName();
			//안전관리자는 site 정보 없음
			break;
		case 2://현장관리자
		case 3://CEO
		case 4:case 5:case 6://현장 사용자(소장 ,팀장,작업자)
			ManagerVO managerVO = managerSerivce.getManagerByID(id);
			userName = managerVO.getName(); //관리자/사용자 명 할당			
			siteVO = siteService.getSiteByIdx(managerVO.getSite_idx()) ; //소속 Site정보 가져옴
			session.setAttribute("managerVO", managerVO); //작업등록시 필요
			break;
		case 7://업체
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
