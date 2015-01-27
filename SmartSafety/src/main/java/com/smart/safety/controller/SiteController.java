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

@Controller(value="SiteController")
public class SiteController {
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	@RequestMapping(value ="siteList")
	public void siteList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			 @RequestParam(value="type", defaultValue="0")int type,
			HttpSession session, HttpServletRequest request) {
				
		//keyword 생성
		String keyword = "%" + searchWord + "%";
		SiteVO siteVO = new SiteVO();
		
		siteVO.setSitename(keyword);
		//siteVO.setRep_name(keyword);
		siteVO.setType(type);
			
		//Paging처리
		int rowCnt = siteService.getRowCount(siteVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
	    siteVO.setStart((pageNum - 1) * MAX_ROW_NUM);
	    siteVO.setSize(MAX_ROW_NUM);
	    
	    List<SiteVO> list = siteService.getSiteListByVO(siteVO);

		//model setting
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);	    
	    model.addAttribute("type", type);
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
	
	@RequestMapping(value = "deleteSite", method = RequestMethod.POST)
	public String deleteSite(@ModelAttribute @Valid SiteVO siteVO) {
		siteService.deleteSite(siteVO.getSite_idx());
		return "redirect:siteList";
	}
	
	
}
