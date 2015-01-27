package com.smart.safety.controller;

import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.smart.safety.domain.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;

@Controller(value="NoticeController")
public class NoticeController {
	@Resource(name="NoticeService")
	private NoticeService noticeService;
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	
	@RequestMapping(value = "noticeList")
	public void noticeList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum, 
				@RequestParam(value="searchWord", defaultValue="")String searchWord ,
				HttpSession session) {
		
		//keyword 생성
//		String keyword = "%" + searchWord + "%";
		NoticeVO noticeVO = new NoticeVO();
//		noticeVO.setCont_name(keyword);			
//		noticeVO.setCont_phone(keyword);
//		noticeVO.setRep_name(keyword);
//		noticeVO.setId(keyword);
		
	
		//자신의 site 내용만 가져옴
		String site_idx = ((SiteVO)session.getAttribute("siteVO")).getSite_idx();
		noticeVO.setSite_idx(site_idx);
		//Paging처리			
		int rowCnt = noticeService.getRowCount(noticeVO);
	    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
	    paging.makePaging();
	    noticeVO.setStart((pageNum - 1) * MAX_ROW_NUM);
	    noticeVO.setSize(MAX_ROW_NUM);		
		
		//model setting
		model.addAttribute("searchWord",searchWord);
	    model.addAttribute("paging",paging);
	     
		
		List<NoticeVO> noticeList = noticeService.getNoticeListByVO(noticeVO);
		

		model.addAttribute("noticeList", noticeList);
		session.setAttribute("contentView", "noticeList");
	}
	
	
	@RequestMapping(value = "registerNotice")
	public void registerNotice(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model, HttpSession session) {
		if(updateIdx != null && !updateIdx.equals("")) {
			NoticeVO noticeVO = noticeService.getNoticeVO(updateIdx);
			model.addAttribute("updateMode", true);
			model.addAttribute("noticeVO", noticeVO);
		}
		else {//insertMode			
			//UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");
			model.addAttribute("updateMode", false);
			model.addAttribute("noticeVO", new NoticeVO());
		}
		
	}
	
	
	@RequestMapping(value = "insertNotice", method = RequestMethod.POST)
	public String insertNotice(HttpSession session, @ModelAttribute @Valid NoticeVO noticeVO, BindingResult bindingResult, Model model) {
		model.addAttribute("updateMode", false);
		if(bindingResult.hasErrors())
			return "registerNotice";
		
		else {
			try{
				noticeService.insert(noticeVO);
				pushNoticeToSiteUser(noticeVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerNotice";
			}
			
			return "redirect:noticeList";
		}
		
	}
	
	@RequestMapping(value = "updateNotice", method = RequestMethod.POST)
	public String updateNotice(HttpSession session, @ModelAttribute @Valid NoticeVO noticeVO, BindingResult bindingResult, Model model) {
		
		model.addAttribute("updateMode", true);
		if(bindingResult.hasErrors())
			return "registerNotice";
		
		else {
			try{
				noticeService.update(noticeVO);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerNotice";
			}
			
			
			return "redirect:noticeList";
		}
		
	}
	
	@RequestMapping(value = "deleteNotice", method = RequestMethod.POST)
	public String deleteNotice( @RequestParam(value="deleteIdx",required=false)String deleteIdx) {
		noticeService.deleteByIdx(deleteIdx);
		return "redirect:noticeList";
	}
	
	@RequestMapping(value = "viewNotice")
	public String viewNotice(@RequestParam(value="viewIdx",required=false)String viewIdx, HttpServletRequest request, Model model, HttpSession session) {
		if(viewIdx != null && !viewIdx.equals("")) {
			
			NoticeVO noticeVO = noticeService.getNoticeVO(viewIdx);
			UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");

			//notice의 경우 줄바꿈이 안되어서 나타나므로 다음작업 수행
			noticeVO.setContent(noticeVO.getContent().replace("\r\n", "<br>"));//		 remark
			model.addAttribute("noticeVO", noticeVO);
			
			
			/**수정권한 체크 - 대상 : 작성자 or 작업 관련 업체 사람 or 현장관리자**/
			USERLEVEL userlevel = USERLEVEL.get(userVO.getLevel());
			boolean canModify = false;
			switch(userlevel) {
			case SS_MANAGER:case EHS_MANAGER:case CEO:break;
			case SITE_MANAGER :case CONT_CHEIF: case CONT_LEADER : //자신의 현장의 모든 작업 수정 가능
				canModify = true;
				break;
			case CONT_INSPECTOR : break;
			case CONTRACTOR:
				break;
			}
			 
			model.addAttribute("canModify", canModify);
			
			return "viewNotice";
		}else {
			return "redirect:noticeList";
		}
			
		
	}
	
	public void pushNoticeToSiteUser(NoticeVO noticeVO) {
		String message =  "공지 : " + noticeVO.getTitle();
		try {			//Manager 전달 (감독자제외 전부)
			List<ManagerVO> manList = managerSerivce.getManagerListBySiteIdx(noticeVO.getSite_idx());
			
			Iterator<ManagerVO> it_man = manList.iterator();			
			while(it_man.hasNext()) {
				ManagerVO manVO = it_man.next();
				int level = manVO.getLevel();
				if(level == USERLEVEL.SITE_MANAGER.idx || level == USERLEVEL.CONT_CHEIF.idx || level == USERLEVEL.CONT_LEADER.idx ||
						level == USERLEVEL.CONT_INSPECTOR.idx) 
					if(manVO.getPid() != null && !manVO.getPid().equals("")){
						GCMPusher.INSTANCE.sendMessage(manVO.getPid(), message);
				}
			}
			
			//Contractor 전달
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(noticeVO.getSite_idx());
			
			Iterator<ContractorVO> it_cont = contList.iterator();			
			while(it_cont.hasNext()) {
				ContractorVO contVO = it_cont.next();
				GCMPusher.INSTANCE.sendMessage(contVO.getPid(), message);	
				
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
