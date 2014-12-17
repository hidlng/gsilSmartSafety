package com.spring.risk.web.actions;

import java.io.Serializable;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.SimpleMessage;

abstract class AbstractActionBean implements ActionBean, Serializable {

  private static final long serialVersionUID = -1767714708233127983L;

  protected static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

  protected transient ActionBeanContext context;

  protected void setMessage(String value) {
    context.getMessages().add(new SimpleMessage(value));
  }
  
  @Override
public ActionBeanContext getContext() {
    return context;
  }

  @Override
public void setContext(ActionBeanContext context) {
    this.context = context;
  }

}
