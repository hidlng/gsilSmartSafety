package com.smart.safety.controller;


import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.safety.domain.UserVO;
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
		
		String resultJson = mobileServie.getMobileLogin(userid, password);
		
		
		model.addAttribute("jsonResult", resultJson);
	}
	
}
