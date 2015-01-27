<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!doctype html>
<html>
<head>
  <title>GSIL</title>
  <link rel="stylesheet" href="css/common.css" type="text/css">  
  <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
  <link href="../images/sss.ico" rel="icon" type="image/x-icon" />
  <link href="../images/sss.ico" rel="shortcut icon" type="image/x-icon" />
  <script>
  function login() {
	$('#loginForm').submit();  
  }
  
  function setPData(pid) {
	  $('#pid').val(pid);
  }
  </script>
</head>

<body>

<div id="wrap">
<form id="loginForm" name="form" method="post" action="loginProcess">		 
<input type="hidden" id="pid" name="pid" value="">
	<div class="back_bg">
		<div class="logBox">
			<div class="inputLog" >
				<input type="text" name="id"  placeholder="Enter your ID">
				<input type="password" name="password"  placeholder="Enter your PASSWORD" onkeypress="if(event.keyCode=='13') login()">
			</div>
			<p><img id="loginButton" src="images/btn_log.png" alt="login" style="cursor:pointer" onclick="login()" ></p>
		</div><!-- //logBox -->
	</div><!-- //back_bg -->
</form>
 </div><!-- //wrap -->
</body>
</html>