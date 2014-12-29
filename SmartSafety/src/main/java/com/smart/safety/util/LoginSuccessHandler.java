package com.smart.safety.util;

import java.io.*;
import java.security.*;

import javax.annotation.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

import com.smart.safety.domain.*;
import com.smart.safety.services.*;

public class LoginSuccessHandler implements AuthenticationSuccessHandler
{

	
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException
    {
     
    	String pid = request.getParameter("pid");
    	
    	
    	//PID update
    /*	UserVO userVO = loginService.getLoginUserById(auth.getName());
    	userVO.setPid(pid);
    	loginService.updateUserPID(userVO);*/
    	
    	response.sendRedirect(request.getContextPath() + "/main?pid="+pid);
    }

}

