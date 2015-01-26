<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>

<head>
<link rel="StyleSheet" href="../css/riskadmin.css" type="text/css"	media="screen" />
<link rel="StyleSheet" href="../css/common.css" type="text/css"	media="screen" />
<script src="../js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/select.js"></script>

<title>Risk Matrix Admin</title>

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
		<stripes:form id="loginForm"  method="post" beanclass="com.spring.risk.web.actions.LoginActionBean">
			<div class="back_bg">
				<div class="logBox">
					<div class="inputLog" >
						<stripes:text name="id" />
						<stripes:password name="password"/>
					</div>					
					<p><stripes:image name="loginProcess" id="loginButton"  src="../images/btn_log.png" alt="login" /></p>
				</div><!-- //logBox -->
			</div><!-- //back_bg -->			
		</stripes:form>
		</div>
		
</body>