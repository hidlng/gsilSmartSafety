package com.spring.risk.web.actions;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import com.spring.risk.domain.UserVO;
import com.spring.risk.service.UserService;



public class LoginActionBean extends AbstractActionBean {
	
	private static final String LOGINPAGE = "/WEB-INF/views/admin/Login.jsp";
	
	@SpringBean
	private transient UserService userService;
	
	
	private String id;
	private String password;
	
	private boolean isAuthenticated = false;
	private boolean loginFail = false;
	


	
	@DefaultHandler 
	public Resolution login() {
		loginFail = false;
		return new ForwardResolution(LOGINPAGE);
	}

	
	public Resolution loginProcess() {
		UserVO vo = userService.getUserbyId(id);
		
		if(vo != null && vo.getPassword().equals(password)) {
			isAuthenticated = true;
	        HttpSession s = context.getRequest().getSession();		    
		    s.setAttribute("userBean", this);
		    s.setAttribute("init", true);
		    return new RedirectResolution(CategoryActionBean.class);
		}
		
		loginFail = true;
		//System.out.println(loginFail);
		return new ForwardResolution(LOGINPAGE);
	}

	public Resolution logout() {
	 context.getRequest().getSession().invalidate();
	
	 clear();
	 return new RedirectResolution(LoginActionBean.class);
	}
	
	public boolean isAuthenticated() {
	    return isAuthenticated ;
	}

	public void clear() {
	    id = "";
	    password = "";
	    isAuthenticated = false;
	}
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean getLoginFail() {
		return loginFail;
	}


	public void setLoginFail(boolean loginFail) {
		this.loginFail = loginFail;
	}




	
	
}
