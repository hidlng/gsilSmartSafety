package com.smart.safety.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.safety.domain.CeoVO;
import com.smart.safety.domain.SiteVO;
import com.smart.safety.services.CeoService;
import com.smart.safety.services.SiteService;
import com.smart.safety.util.Paging;

@Controller(value="CeoController")
public class CeoController {
	
	@Resource(name="CeoService")
	private CeoService ceoService;
	
	@Resource(name="SiteService")
	private SiteService	siteService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	@RequestMapping(value = "ceolist")
	public void ceoList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			@RequestParam(value="siteValue", defaultValue="")String siteValue ,
			@RequestParam(value="riskSearchValue", defaultValue="")String riskSearchValue ,
			@RequestParam(value="chkSearchValue", defaultValue="")String chkSearchValue ,
			@RequestParam(value="siteindex", defaultValue="")String siteindex ,
			HttpSession session ) {
		
		/**searching**/
		CeoVO ceoVO = new CeoVO();
		ceoVO.setSiteValue(siteValue);
		ceoVO.setRiskSearchValue(riskSearchValue);
		ceoVO.setChkSearchValue(chkSearchValue);
		
		SimpleDateFormat mFormat = new SimpleDateFormat( "yyyyMMdd", Locale.KOREA );
		Date cTime = new Date();
		String bdate = mFormat.format(cTime);
		ceoVO.setBasedate(bdate);
		
		/**paging**/
		int rowCnt = ceoService.getRowCount(ceoVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
	    ceoVO.setStart((pageNum - 1) * MAX_ROW_NUM);
	    ceoVO.setSize(MAX_ROW_NUM);		

		List<CeoVO> list = ceoService.getCeoList(ceoVO);
		
		List<SiteVO> sitelist = siteService.getSiteList();
		
		/** model setting **/
		model.addAttribute("searchWord",searchWord);
		model.addAttribute("siteValue",siteValue);
		model.addAttribute("siteindex",siteindex);
		model.addAttribute("riskSearchValue",riskSearchValue);
		model.addAttribute("chkSearchValue",chkSearchValue);
		model.addAttribute("paging",paging);
		model.addAttribute("ceoList", list);
		model.addAttribute("siteList", sitelist);	
	}
}
