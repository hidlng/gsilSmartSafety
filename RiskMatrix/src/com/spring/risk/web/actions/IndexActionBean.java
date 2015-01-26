package com.spring.risk.web.actions;

import net.sourceforge.stripes.action.*;

@UrlBinding("/")
public class IndexActionBean  extends AbstractActionBean {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@DefaultHandler
  public ForwardResolution view() {
    return new ForwardResolution(LoginActionBean.class);
  }
}
