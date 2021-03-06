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


@Controller(value="ContractorController")
public class ContractorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractorController.class);
	
	@Resource(name="LoginService")
	private LoginService loginService;
	
	@Resource(name="ContractorService")	
	private ContractorService contractorService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	
	@RequestMapping(value = "contractorList")
	public void contractorList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum, 
				@RequestParam(value="searchWord", defaultValue="")String searchWord ,
				HttpSession session) {
			//keyword 생성
			String keyword = "%" + searchWord + "%";
			ContractorVO contractorVO = new ContractorVO();
			contractorVO.setCont_name(keyword);			
			contractorVO.setCont_phone(keyword);
			contractorVO.setRep_name(keyword);
			contractorVO.setId(keyword);
			
			
			//현장관리자 -> 자신의 현장의 업체만 출력. 
			//SS 관리자 - > 전부 출력
			String site_idx = ((SiteVO)session.getAttribute("siteVO")).getSite_idx();
			contractorVO.setSite_idx(site_idx);
			
			//Paging처리			
			int rowCnt = contractorService.getRowCount(contractorVO);
		    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
		    paging.makePaging();
		    contractorVO.setStart((pageNum - 1) * MAX_ROW_NUM);
		    contractorVO.setSize(MAX_ROW_NUM);		
			
			//model setting
			model.addAttribute("searchWord",searchWord);
		    model.addAttribute("paging",paging);
		     
			List<ContractorVO> list = contractorService.getContractorListByVO(contractorVO);
			

			model.addAttribute("contractorList", list);
			session.setAttribute("contentView", "contractorList");
		}
	
	@RequestMapping(value = "registerContractor")
	public void registerContractor(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model) {
		if(updateIdx != null && !updateIdx.equals("")) {
			ContractorVO contractorVO = contractorService.getContractorByIdx(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("contractorVO", contractorVO);
		}
		else {//insertMode
			model.addAttribute("updateMode", false);
			model.addAttribute("contractorVO", new ContractorVO());
		}
		
	}
	
	@RequestMapping(value = "insertContractor", method = RequestMethod.POST)
	public String insertContractor(HttpSession session, @ModelAttribute @Valid ContractorVO contractorVO, BindingResult bindingResult, Model model) {
		model.addAttribute("updateMode", false);
		if(bindingResult.hasErrors())
			return "registerContractor";
		
		else {
			try{
				contractorService.insertContractor(contractorVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerContractor";
			}
			
			return "redirect:contractorList";
		}
		
	}
	
	@RequestMapping(value = "updateContractor", method = RequestMethod.POST)
	public String updateContractor(HttpSession session, @ModelAttribute @Valid ContractorVO contractorVO, BindingResult bindingResult, Model model) {
		
		model.addAttribute("updateMode", true);
		if(bindingResult.hasErrors())
			return "registerContractor";
		
		else {
			try{
				contractorService.updateContractor(contractorVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerContractor";
			}
			
			
			return "redirect:contractorList";
		}
		
	}
	
	@RequestMapping(value = "deleteContractor", method = RequestMethod.POST)
	public String deleteContractor( @ModelAttribute @Valid ContractorVO contractorVO) {
		contractorService.deleteContractor(contractorVO.getCont_idx(), contractorVO.getUser_idx());
		return "redirect:contractorList";
	}
	
	
	
}
