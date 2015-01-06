package com.smart.safety.services;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.safety.domain.UserVO;
import com.smart.safety.persistence.MobileMapper;




@Service(value="MobileService")
public class MobileService {
	@Resource( name="MobileMapper" )
	private MobileMapper mobileMapper;
	
	public String getMobileLogin(String id, String password) {		
		UserVO userVO = new UserVO();
		userVO.setId(id);
		userVO.setPassword(password);
		
		UserVO returnVO = mobileMapper.getMobileLogin( userVO );
		
		JSONObject jo = new JSONObject();
		if( userVO != null && !userVO.getId().equals("") ) {
			jo.put("result", "true");
			jo.put("id", returnVO.getId());
			jo.put("password", returnVO.getPassword());
			jo.put("useridx", returnVO.getUser_idx());
			jo.put("authority", returnVO.getAuthority());
			jo.put("level", returnVO.getLevel());
		} else {
			jo.put("result", "false");
		}
		
		return jo.toString();
	}
}
