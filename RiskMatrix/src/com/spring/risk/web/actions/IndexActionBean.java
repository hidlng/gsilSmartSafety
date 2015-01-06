package com.spring.risk.web.actions;

import net.sourceforge.stripes.action.*;

@UrlBinding("/")
public class IndexActionBean  extends AbstractActionBean {
  @DefaultHandler
  public ForwardResolution view() {
    return new ForwardResolution(LoginActionBean.class);
  }
}
