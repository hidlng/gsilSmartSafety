package com.spring.risk.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class LogoutActionBean extends AbstractActionBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String LOGOUTPAGE = "/WEB-INF/views/admin/Logout.jsp";
	@DefaultHandler
	public Resolution login() {
		return new ForwardResolution(LOGOUTPAGE);
	}

}
