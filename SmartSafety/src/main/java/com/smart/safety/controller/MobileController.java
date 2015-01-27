package com.smart.safety.controller;

import java.net.*;

import javax.annotation.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.smart.safety.services.*;

@Controller(value="MobileController")
public class MobileController {
	
	@Resource(name="MobileService")
	private MobileService mobileServie;
	
	@RequestMapping( value = "mobilelogin" )
	public void mobileLogin(Model model
			, @RequestParam(value="userid", defaultValue="")String userid
			, @RequestParam(value="password", defaultValue="")String password
			, @RequestParam(value="regId", defaultValue="")String regId) {
		String resultJson = mobileServie.getMobileLogin(userid, password, regId);
		model.addAttribute("jsonResult", resultJson);
	}
	
	
	@RequestMapping( value = "mobileworklist" )
	public void mobileWorkList(Model model
			, @RequestParam(value="searchdate", defaultValue="")String searchdate
			, @RequestParam(value="siteidx", defaultValue="")String siteidx
			, @RequestParam(value="userlevel", defaultValue="")String userlevel) {
		
		siteidx = URLDecoder.decode(siteidx);
		
		String resultJson = mobileServie.getMobileWorkList(siteidx,searchdate,userlevel);
		model.addAttribute("jsonResult", resultJson);
	}

	@RequestMapping( value = "mobileupdateresult" )
	public void mobileUpdateResult(Model model
			, @RequestParam(value="workdate", defaultValue="")String workdate
			, @RequestParam(value="useridx", defaultValue="")String useridx
			, @RequestParam(value="checkyn", defaultValue="")String checkyn
			, @RequestParam(value="workidx", defaultValue="")String workidx
			, @RequestParam(value="userlevel", defaultValue="")String userlevel
			, @RequestParam(value="siteIdx", defaultValue="")String siteIdx) {
		
		useridx = URLDecoder.decode(useridx);
		siteIdx = URLDecoder.decode(siteIdx);
		
		String resultJson = mobileServie.updatCheckYn(workdate, useridx, checkyn, workidx,userlevel,siteIdx);
		model.addAttribute("jsonResult", resultJson);
	}
	
	@RequestMapping( value = "mobileworkinfo" )
	public void mobileWorkInfo(Model model
			, @RequestParam(value="workIdx", defaultValue="")String workIdx) {
		
		workIdx = URLDecoder.decode(workIdx);
		
		String resultJson = mobileServie.getMobileWorkInfomation(workIdx);
		model.addAttribute("jsonResult", resultJson);
	}
	
	@RequestMapping( value = "mobilenoticelist" )
	public void mobileNoticeList(Model model
			, @RequestParam(value="siteidx", defaultValue="")String siteidx) {
		
		siteidx = URLDecoder.decode(siteidx);
		
		String resultJson = mobileServie.getMobileNoticeList(siteidx);
		model.addAttribute("jsonResult", resultJson);
	}
	
	@RequestMapping( value = "mobilenoticeinfo" )
	public void mobileNoticeInfo(Model model
			, @RequestParam(value="noticeIdx", defaultValue="")String noticeIdx) {
		
		noticeIdx = URLDecoder.decode(noticeIdx);
		
		String resultJson = mobileServie.getMobileNoticeInfo(noticeIdx);
		model.addAttribute("jsonResult", resultJson);
	}	
	
}
