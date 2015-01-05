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
			, @RequestParam(value="password", defaultValue="")String password) {
		
		//UserVO userVO = mobileServie.getMobileLogin(userid, password);
		UserVO userVO = mobileServie.getMobileLogin("test", "test");
		
		JSONObject jo = new JSONObject();
		if( userVO != null && !userVO.getId().equals("") ) {
			jo.put("result", "true");
			jo.put("id", userVO.getId());
			jo.put("password", userVO.getPassword());
			jo.put("useridx", userVO.getUser_idx());
			jo.put("authority", userVO.getAuthority());
			jo.put("level", userVO.getLevel());
		} else {
			jo.put("result", "true");
		}
		
		model.addAttribute("jsonResult", jo.toString());
		
	}
	
}
