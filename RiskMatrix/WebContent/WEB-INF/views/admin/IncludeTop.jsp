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
<script src="../js/select.js" type="text/javascript"></script>
<script src="../js/simple_overlay.js" type="text/javascript" ></script>

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

 <script>
 function zoom() {
     document.body.style.zoom = "300%" 
 }
 
//뒤로가기 방지
 window.history.forward(0);
 history.navigationMode = 'compatible'; // 오페라, 사파리 뒤로가기 막기
 function _no_Back(){window.history.forward(0);}
 

 function removeHangul(obj) {
     obj.value = obj.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
 }
 
function checkSpeChar(level) {
	var objEv = event.srcElement;
	var regex_alphanum = /[0-9]|[a-z]|[A-Z]|[_-]/;
	var regex_special = /[~!@\#$%<>^*\\=+{}\']/; //특수문자 정규식 변수 선언 허용
	//var num = "{}|~`!@#$%^&*+\"'\\/"; //ㅔ[],(),<>,?, _ ,-허용 -- 일반 text
	
		//num = "{}[]()<>?_|~`!@#$%^&*-+\"'\\/ ";
	event.returnValue = true;

	isValid = true;
	
	for (var i = 0; i < objEv.value.length; i++) {
		var cur_key  = objEv.value.charAt(i);
		if( level <= 1 ){
			if (!cur_key.match(regex_alphanum)) {
				isValid = false;
			}
		}else if (level == 2){
			//if (-1 != num.indexOf(cur_key)){ //특수문자 비교
			if (cur_key.match(regex_special)){ //특수문자 비교
				isValid = false;
			}
		}
	}
		
	if(level <= 1 && !isValid) {
		alert("_,-를 제외한 특수문자 및 한글은 입력하실 수 없습니다.");
		objEv.value = "";
	}
	
	if(level == 2 && !isValid) {
		event.preventDefault();
		alert("다음의 특수문자는 입력하실 수 없습니다.(~!@\#$%<>^*\\=+{}\)");
		objEv.value = objEv.value.replace(/[~!@\#$%<>^*\\=+{}\']/gi, '');
	}

}
	
	
	</script>
</head>

<body   onload="_no_Back();" onpageshow="if(event.persisted)_no_Back();">

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
		<stripes:link id="logout" class="button" beanclass="com.spring.risk.web.actions.LoginActionBean" event="logout">
						Logout
		</stripes:link>
	</div>



	<div id="Content">
	