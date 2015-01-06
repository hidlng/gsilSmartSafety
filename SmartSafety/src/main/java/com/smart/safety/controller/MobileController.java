package com.smart.safety.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.safety.services.MobileService;

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
			, @RequestParam(value="siteidx", defaultValue="")String siteidx) {
		
		String resultJson = mobileServie.getMobileWorkList(siteidx,searchdate);
		model.addAttribute("jsonResult", resultJson);
	}

	@RequestMapping( value = "mobileupdateresult" )
	public void mobileUpdateResult(Model model
			, @RequestParam(value="workdate", defaultValue="")String workdate
			, @RequestParam(value="useridx", defaultValue="")String useridx
			, @RequestParam(value="checkyn", defaultValue="")String checkyn
			, @RequestParam(value="workidx", defaultValue="")String workidx ) {
		
		String resultJson = mobileServie.updatCheckYn(workdate, useridx, checkyn, workidx);
		model.addAttribute("jsonResult", resultJson);
	}
	
	
}
