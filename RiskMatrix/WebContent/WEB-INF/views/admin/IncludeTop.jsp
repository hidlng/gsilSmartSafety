<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
<link rel="StyleSheet" href="../css/riskadmin.css" type="text/css"
	media="screen" />
<script src="../js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="../js/jquery.modal.js" type="text/javascript"></script>
<script src="../js/site.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/select.js"></script>
<script type="text/javascript" src="../js/simple_overlay.js"></script>

<meta name="generator"
	content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org" />
<title>Risk Matrix Admin</title>
<meta content="text/html; charset=windows-1252"
	http-equiv="Content-Type" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="Pragma" content="no-cache" />

 
</head>

<body>
${actionBean.isAuthenticated()}
	<div id="Header">
		<div id="Title">RiskMatrix Admin</div>
		<div id="Menu">
			<stripes:link	beanclass="com.spring.risk.web.actions.CategoryActionBean">			
			<stripes:param name="lastIdx">1</stripes:param>	 
						카테고리
			</stripes:link>
			<stripes:link	beanclass="com.spring.risk.web.actions.CategoryActionBean">
						작업허가
			</stripes:link>
			
		</div>
		<stripes:link id="logout" beanclass="com.spring.risk.web.actions.LoginActionBean" event="logout">
						로그아웃
		</stripes:link>
	</div>



	<div id="Content">
	