package com.smart.safety.controller;

import java.io.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.*;

import org.json.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.support.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;


@Controller(value="WorkController")
public class WorkController {
	private static final Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	@Resource(name="WorkService")
	WorkService workService;
		
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;

	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;

	
	
	
	@RequestMapping(value = "workList")
	public void workList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum, 
				@RequestParam(value="searchWord", defaultValue="")String searchWord ,
				HttpSession session) {
			//keyword 생성
			String keyword = "%" + searchWord + "%";
			WorkVO workVO = new WorkVO();
			workVO.setWorktype(keyword);
			workVO.setWorktitle(keyword);
			workVO.setUsername(keyword);
			
			//Paging처리			
			int rowCnt = workService.getRowCount(workVO);
		    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
		    paging.makePaging();
		    workVO.setStart((pageNum - 1) * MAX_ROW_NUM);
		    workVO.setSize(MAX_ROW_NUM);		
			
			//model setting
			model.addAttribute("searchWord",searchWord);
		    model.addAttribute("paging",paging);
		     
			List<WorkVO> list = workService.getWorkListByVO(workVO);
			
//			model.addAttribute("startPage", startPage);
//			model.addAttribute("endPage", endPage);
			model.addAttribute("workList", list);
			session.setAttribute("contentView", "workList");
		}
	
	@RequestMapping(value = "registerWork")
	public void registerWork(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model, HttpSession session) {
		model.addAttribute("isNotValid", false);
		
		if(updateIdx != null && !updateIdx.equals("")) {
			WorkVO workVO = workService.getWorkByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("workVO", workVO);
		}
		else {//insertMode
			//입력시점에 최신데이터를 가져옴
			//작업등록자의 현장정보, 업체정보, 유저정보 필요
			UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");
			SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");		
		
			WorkVO workVO = new WorkVO();
			workVO.setToollist(new ArrayList<ToolVO>());
			
			if(userVO == null || siteVO == null) {
				//error 처리
			}else {
				workVO.setWrite_user_idx(userVO.getUser_idx());
				workVO.setSite_idx(siteVO.getSite_idx());
				
				
				//업체에 경우 현장사용자는 직접 타이핑 입력이기때문에
				//통일 성을 위해 둘다 이름값만 입력함(cont_idx 사용 X)
				if(userVO.getLevel() == 3) {//현장사용자
					ManagerVO managerVO = (ManagerVO)session.getAttribute("managerVO");
					workVO.setCont_name(managerVO.getCont_name());
				}else if(userVO.getLevel() == 4) {//업체
					ContractorVO contractorVO = (ContractorVO)session.getAttribute("contractorVO");
					//workVO.setCont_idx(contractorVO.getCont_idx());
					workVO.setCont_name(contractorVO.getCont_name());
					
				}else {
					//error 처리
					//workVO.setCont_name("소속없체 없음");
				}
				
			}
			model.addAttribute("updateMode", false);
			model.addAttribute("workVO", workVO);
		}//else
		
	}
	
	
	@RequestMapping(value = "insertWork", method = RequestMethod.POST)
	public String insertWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO
			, BindingResult bindingResult, Model model, RedirectAttributes redirectAttr) {
		String work_idx;
		
		model.addAttribute("updateMode", false);
		if(workVO.getToollist() != null) arrayFilter(workVO.getToollist()); //빈공간 제거		
		model.addAttribute("workVO", workVO);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("isNotValid", true);
			return "registerWork";
		}
		
		else {
			try{
				//setRiskData(workVO);
				work_idx = workService.insertWork(workVO);
				
			}catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("isNotValid", true);
				return "registerWork";
			}
			
			//return "redirect:workList";
			
			redirectAttr.addFlashAttribute("work_idx", work_idx);
			return "redirect:printList";
		}
		
	}
	
	public String getRiskData(List<String> list) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getRiskData=&codelist=";
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		return json_result;
				
	}
	
	private void setRiskData(WorkVO workVO) {
		
		//ArrayList<String> list = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		
		buf.append('#');
		buf.append(workVO.getWorkcode());
		
		Iterator<ToolVO> it = workVO.getToollist().iterator();
		buf.append('#');
		
		
		
		while(it.hasNext()) {
			ToolVO vo = it.next();
			//list.add(vo.getToolcode());
			buf.append(vo.getToolcode());
		}
		
		list.add(workVO.getPlacecode());
		
		getRiskData(list);
		
		workVO.setRisk_grade("");//RiskGrade 
		workVO.setRisk_warn("");//RiskWarn
		workVO.setWorkpermit("");//WorkPermit
		
	}

	private void arrayFilter(List<ToolVO> toollist) {
		Iterator<ToolVO> it = toollist.iterator();
		while (it.hasNext()) {
			ToolVO vo = it.next();
			if(vo.getToolcode() == null || vo.getToolname() == null)
				it.remove();
		}
	}

	@RequestMapping(value = "updateWork", method = RequestMethod.POST)
	public String updateWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO, BindingResult bindingResult,
		Model model, RedirectAttributes redirectAttr) {
		
		model.addAttribute("updateMode", true);
		arrayFilter(workVO.getToollist()); //빈공간 제거
		model.addAttribute("workVO", workVO);
		
		if(bindingResult.hasErrors()) { 
			model.addAttribute("isNotValid", true);
			return "registerWork";
		}
		
		else {
			try{
				workService.updateWork(workVO);
			}catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("isNotValid", true);
				return "registerWork";
			}
			
			redirectAttr.addFlashAttribute("work_idx",workVO.getWork_idx());
			//return "redirect:workList";
			return "redirect:printList";
		}
		
	}
	
	
	@RequestMapping("workPopup")
	public void workPopup(HttpSession session) {
		
	}

	
	
}
