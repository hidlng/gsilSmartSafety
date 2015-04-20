package com.smart.safety.controller;

import java.io.InputStream;
import java.text.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.Payload;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;
import com.smart.safety.domain.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;

@Controller(value="CeoController")
public class CeoController {
	
	@Resource(name="CeoService")
	private CeoService ceoService;
	
	@Resource(name="SiteService")
	private SiteService	siteService;
	
	public static final int MAX_ROW_NUM=10;
	public static final int MAX_PAGE_NUM=10;
	
	@RequestMapping(value = "ceo")
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
		
		if( searchWord != null && !searchWord.equals("") ) {
			bdate = searchWord.replaceAll("-","");
		}
		
		ceoVO.setBasedate(bdate);
		
		/**paging**/
		int rowCnt = ceoService.getRowCount(ceoVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
	    ceoVO.setStart((pageNum - 1) * MAX_ROW_NUM);
	    ceoVO.setSize(MAX_ROW_NUM);		

		List<CeoVO> list = ceoService.getCeoList(ceoVO);
		
		List<SiteVO> sitelist = siteService.getSiteList();
		
		//pushMessage();
		
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
	
	
	public void pushMessage() {
		try {
			ApnsService service =
				    APNS.newService()
				    .withCert("c:\\serverpush.p12", "tpqmfld11!")
				    .withSandboxDestination()
				    .build();

//			ApnsService service =
//				    APNS.newService()
//				    .withCert("/Users/gangsegun/Desktop/serverpushr.p12", "tpqmfld11!")
//				    .withSandboxDestination()
//				    .build();
//			
//			String payload = APNS.newPayload().alertBody("금일해당하는 작업이 있습니다.").build();
//			String token = "4de4cac2ce3ff0a0800fbd39cb31a7bdaed0452b5171df81d41689876fc41b3e";
//		
//			service.push(token, payload);
			
		} catch( Exception e ) {
			System.out.println("Error :" + e);
			return;
			
		}
    }
	
//    @Scheduled(cron = "0 30 17 * * *")
//    public void cronTest1(){
//            System.out.println("value:1");
//    }
}
