<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
<head>
<title>Risk Matrix Admin</title>

<script src="../js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/select.js"></script>
<link href="../images/sss.ico" rel="icon" type="image/x-icon" />
<link href="../images/sss.ico" rel="shortcut icon" type="image/x-icon" />
<style>
	 *{margin:0; padding:0;} 
	 body{width:100%;margin:0; padding:0;background-color:#4d6c8b;font-family:arial,sans-serif;color:#333;}
	 #wrap{width:100%;margin:0; padding:0;text-align:center;}
	 #hh{margin:100px auto; } 
	 .box_log{overflow:hidden;margin:180px auto 0px; width:420px;}
	 .box_log .box_input{width:100%; height:90px;border-top:1px solid #3498db;padding-left:88px;background:url("../images/bg_id.png") no-repeat 12px 18px;} 
	 .box_log .box_input2{width:100%; height:90px;border-top:1px solid #3498db;border-bottom:1px solid #3498db;margin-bottom:77px; 
	 padding-left:88px; background:url("../images/bg_pw.png") no-repeat 12px 18px;}
	  .box_log input{float:left;border:1px solid red; width:300px; height:90px; color:#333;font-size:30px; line-height:90px; border:none; background:none;text-align:center;font-weight:600;}
	 .btn_login{display:block;width:420px;margin:0 auto;}
	 .btn_login_a{display:block;width:420px;height:90px; border:4px solid #3498db;background-color:#3498db;text-align:center; color:#fff; font-size:30px;line-height:90px;font-weight:normal;text-decoration:none;cursor:pointer;}
	 .btn_login_a:hover{border:4px solid #fff;background:none;}
 </style> 
<script>
$(document).ready(function() {
	if(${actionBean.loginFail} == true){
		alert('ID 혹은 PASSWORD 가 잘못되었습니다.');
	} 
});
</script>
</head>

<body>
<div id="wrap">
	<div id="hh"><img src="../images/logo.png" width="" height="" border="0" alt="riskmatrix" /></div>
	
	<stripes:form id="loginForm"  method="post" beanclass="com.spring.risk.web.actions.LoginActionBean">
		<div class="box_log">
			<div class="box_input">	<stripes:text name="id"  /></div>
			<div class="box_input2"><stripes:password name="password"/>	</div>
		</div>
		<p class="btn_login"><stripes:submit name="loginProcess" id="loginButton" class="btn_login_a" value="LOGIN"/></p>
	</stripes:form>
</div>
		
</body>
</html>