<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<%
 System.out.println("jsonp test");
 String callback = request.getParameter("callback");
 System.out.println("calback::" + callback);
%>
<%=callback%>(${actionBean.jsonObj.toString()})