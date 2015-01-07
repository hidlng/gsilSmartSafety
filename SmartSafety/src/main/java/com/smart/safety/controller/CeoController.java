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
import com.smart.safety.services.CeoService;
import com.smart.safety.util.Paging;

@Controller(value="CeoController")
public class CeoController {
	
	@Resource(name="CeoService")
	private CeoService ceoService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	@RequestMapping(value = "ceolist")
	public void ceoList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum,
			@RequestParam(value="searchWord", defaultValue="")String searchWord ,
			HttpSession session ) {
		
		/**searching**/
		//String keyword = "%" + searchWord + "%";
		CeoVO ceoVO = new CeoVO();

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
		
		/** model setting **/
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
		model.addAttribute("ceoList", list);
	}
}
