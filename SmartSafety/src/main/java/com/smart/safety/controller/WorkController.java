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


@Controller(value="WorkController")
public class WorkController {
	private static final Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	@Resource(name="WorkService")
	WorkService workService;
		
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;
	
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
			
			
			
		}
		
	}
	
	
	@RequestMapping(value = "insertWork", method = RequestMethod.POST)
	public String insertWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO, BindingResult bindingResult, Model model) {
		model.addAttribute("updateMode", false);
		model.addAttribute("workVO", workVO);
		
		if(bindingResult.hasErrors())
			return "registerWork";
		
		else {
			try{
				workService.insertWork(workVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerWork";
			}
			
			return "redirect:workList";
		}
		
	}
	
	@RequestMapping(value = "updateWork", method = RequestMethod.POST)
	public String updateWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO, BindingResult bindingResult, Model model) {
		
		model.addAttribute("updateMode", true);
		model.addAttribute("workVO", workVO);
		
		if(bindingResult.hasErrors())
			return "registerWork";
		
		else {
			try{
				workService.updateWork(workVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerWork";
			}
			
			
			return "redirect:workList";
		}
		
	}
	
	
	@RequestMapping("workPopup")
	public void workPopup(HttpSession session) {
		
	}
	
}
