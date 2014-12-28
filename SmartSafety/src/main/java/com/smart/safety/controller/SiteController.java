package com.smart.safety.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.smart.safety.domain.ContractorVO;
import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.SiteVO;
import com.smart.safety.services.LoginService;
import com.smart.safety.services.SiteService;
import com.smart.safety.util.Paging;

@Controller(value="SiteController")
public class SiteController {
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	public static final int MAX_ROW_NUM=10;
	public static final int MAX_PAGE_NUM=5;
	
	@RequestMapping(value ="siteList")
	public void siteList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			HttpSession session, HttpServletRequest request) {
				
		//keyword 생성
		String keyword = "%" + searchWord + "%";
		SiteVO siteVO = new SiteVO();
		
		siteVO.setSitename(keyword);
		siteVO.setRep_name(keyword);
		//siteVO.setAddr_detail(keyword);
		siteVO.setRep_phone(keyword);
		//siteVO.setStart/Endtime
			
		//Paging처리
		int rowCnt = siteService.getRowCount(siteVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
	    siteVO.setStart((pageNum - 1) * MAX_ROW_NUM);
	    siteVO.setSize(MAX_ROW_NUM);		

		//model setting
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
	     
		List<SiteVO> list = siteService.getSiteListByVO(siteVO);

		model.addAttribute("siteList", list);
		
		session.setAttribute("contentView", "siteList");
	}
	
	
	@RequestMapping(value = "registerSite")
	public void registerSite(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model) {
		if(updateIdx != null && !updateIdx.equals("")) {
			SiteVO siteVO = siteService.getSiteByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("siteVO", siteVO);
		}
		else {//insertMode
			model.addAttribute("updateMode", false);
			model.addAttribute("siteVO", new SiteVO());
		}
		
	}

	@RequestMapping(value = "insertSite", method = RequestMethod.POST)
	public String insertSite(HttpSession session, @ModelAttribute @Valid SiteVO siteVO, BindingResult bindingResult, Model model) {
		model.addAttribute("updateMode", false);
		if(bindingResult.hasErrors())
			return "registerSite";
		
		else {
			try{
				siteService.insertSite(siteVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerSite";
			}
			
			return "redirect:siteList";
		}
		
	}
	
	@RequestMapping(value = "updateSite", method = RequestMethod.POST)
	public String updateSite(HttpSession session, @ModelAttribute @Valid SiteVO siteVO, BindingResult bindingResult, Model model) {
		
		model.addAttribute("updateMode", true);
		if(bindingResult.hasErrors())
			return "registerSite";
		
		else {
			try{
				siteService.updateSite(siteVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerSite";
			}
			
			
			return "redirect:siteList";
		}
		
	}
	
}
