<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page session="true"%>

<!doctype html>


<html>
<head>
  <title>Smart Safety</title>
  <link rel="stylesheet" href="css/common.css" type="text/css">
  <link rel="stylesheet" href="css/jquery.datepick.css" type="text/css" >
  <link rel="stylesheet" href="css/jquery.timepicker.css" type="text/css" > 
  <link rel="stylesheet" href="css/custom.css" type="text/css">    
  <link href="../images/ss.ico" rel="icon" type="image/x-icon" />
  <link href="../images/ss.ico" rel="shortcut icon" type="image/x-icon" />
  
  <script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
  <script type="text/javascript" src="js/jquery.plugin.js"></script>
  <script type="text/javascript" src="js/jquery.datepick.js"></script> 
  <script type="text/javascript" src="js/jquery.datepick-ko.js"></script>
  <!-- http://timepicker.co/ -->
 <script type="text/javascript" src="js/jquery.timepicker.js"></script>
  <script type="text/javascript" src="js/simple_overlay.js"></script>
 <script type="text/javascript" src="js/smart.safety.js"></script>
 <script>
	$(document).ready(function() {
		var contentView = '#' +'${sessionScope.contentView}';
		$(contentView).addClass('on');
		//document.body.style.zoom =1.4;
		
		//CEO일경우 class="aside" 숨김 ,  .content widht 속성 제거
		<c:if test ="${sessionScope.userLoginInfo.level == 3 || fromCEO == true}">
		//alert('ceo');
		$('.aside').hide();
		$('.content').css('width', 'auto');
		</c:if>
		
	});
	
   $(document).on("click", "#siteUserList", function(e) { $('#siteUserList_form').submit(); });
   $(document).on("click", "#managerList", function(e) { $('#managerList_form').submit(); } );

   $(document).on("click", "#workList", function(e) { $('#workList_form').submit(); });
   $(document).on("click", "#homeLogo", function(e) { $('#workList_form').submit(); });
   $(document).on("click", ".toHomePage", function(e) { $('#workList_form').submit(); });
   $(document).on("click", "#report", function(e) { $('#report_form').submit(); });
   $(document).on("click", "#contractorList", function(e) { $('#contractorList_form').submit(); });
   $(document).on("click", "#noticeList", function(e) { $('#noticeList_form').submit(); });
   $(document).on("click", "#siteList", function(e) { $('#siteList_form').submit(); });
   $(document).on("click", "#logout", function(e) { $('#logout_form').submit(); });
	
  //뒤로가기 방지
   window.history.forward(0);
   history.navigationMode = 'compatible'; // 오페라, 사파리 뒤로가기 막기
   function _no_Back(){window.history.forward(0);}
   
   //f5 bs 방지
function disableF5(e) { if ((e.which || e.keyCode) == 116) e.preventDefault(); };
$(document).on("keydown", disableF5);
   
   function addZoom(){
	   var zoom = document.body.style.zoom; 
	   alert(zoom);
	   document.body.style.zoom = zoom + 0.5;
	   
   }
   
   function minusZoom(){
	   document.body.style.zoom -=0.1;
   }
   
  </script>
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="Pragma" content="no-cache" />

</head>

<body  onload="_no_Back();" onpageshow="if(event.persisted)_no_Back();"> 

<!-- 현장/안전 구분위해 -->
<div id="menuForm" style="display:none">
	<form id="managerList_form" action="managerList" method="GET">
		<input type="hidden" name="listLevel" value="1">
	</form>
	<form id="siteUserList_form" action="siteUserList" method="GET">
		<input type="hidden" name="listLevel" value="3">
	</form>
	<form id="report_form" action="ceo" method="POST"></form>
	<form id="workList_form" action="workList" method="POST"></form>
	<form id="contractorList_form" action="contractorList" method="POST"></form>
	<form id="noticeList_form" action="noticeList" method="POST"></form>
	<form id="siteList_form" action="siteList" method="POST">
	<input type="hidden" name="type" value="0">
	</form>
	<form id="logout_form" action="logout" method="POST"></form>
</div>

<div id="wrap" class="sub"><!-- //1202 수정 -->
	<!-- ceo화면에서 viewlist 넘어온후 logo 클릭하여 가는것 방지 -->
	<c:if test="${sessionScope.userLoginInfo.level != 3}">
		<h1><!--img id="homeLogo" src="images/logo_ds.png" alt="두산그룹로고"   style="cursor:pointer"/--></h1>
	</c:if>
	<!-- ceo화면에서 viewlist 넘어온후 logo 클릭하여 가는것 방지 -->
	<c:if test="${sessionScope.userLoginInfo.level == 3}">
		<h1><!--img src="images/logo_ds.png" alt="두산그룹로고"   style="cursor:pointer"/--></h1>
	</c:if>
	
	<div class="back_bg sub"><!-- //1202 수정 -->
		<div class="wrapBox">

			<div class="aside">
				<ul class="aside_list">
					<c:if test= "${sessionScope.userLoginInfo.level == 0}">
						<p class="aside_title">	Smart Safety <p>
						<li id="siteList" style="cursor:pointer" >현장 관리</li>
					</c:if>
					<c:if test= "${sessionScope.userLoginInfo.level <= 1}"><!-- 본사 관리자(EHS팀) & 현장 안전관리자 -->
						<li id="managerList" style="cursor:pointer" >관리자 관리</li>						
					</c:if>
					
					<c:if test= "${sessionScope.userLoginInfo.level == 2}">
						<li id="contractorList" style="cursor:pointer" >업체 관리</li> <!--  업체 관리 -->
						<li id="siteUserList" style="cursor:pointer" >사용자 관리</li> <!-- 현장 사용자 -->
					</c:if>
					<c:if test= "${sessionScope.userLoginInfo.level <= 4}">
						<li id="report" style="cursor:pointer" >CEO 리포트</li>
					</c:if>
					<c:if test= "${sessionScope.userLoginInfo.level == 2 ||
								 sessionScope.userLoginInfo.level == 4 || 
								 sessionScope.userLoginInfo.level == 5 ||
								 sessionScope.userLoginInfo.level == 6 ||
								 sessionScope.userLoginInfo.level == 7 }">
						<li id="noticeList" style="cursor:pointer" >공지사항</li> 
					</c:if>
					<li id="workList" style="cursor:pointer" >작업 관리</li><!--   -->
					
				</ul><!-- //aside_list -->		
								
				<br>
				
				<ul class="aside_ident">
					<li><span class="identTitle"><span class="head">아이디  : </span><br>
						<span class="identContent">${sessionScope.userLoginInfo.id}</span>
						</span>
					</li>
					<li><span class="identTitle"> <span class="head"> 이&nbsp;&nbsp;&nbsp;름 : <br></span>
						<c:if test ="${sessionScope.userLoginInfo.level == 0}"> 관리자</c:if> 
						<c:if test ="${sessionScope.userLoginInfo.level != 0}">${sessionScope.userName}</c:if>
						</span>
					</li>
					<li><span class="identTitle"> <span class="head">권&nbsp;&nbsp;&nbsp;한 : <br></span>
						<c:if test ="${sessionScope.userLoginInfo.level == 0}">SS 관리자</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 1}">본사 관리자</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 2}">현장 안전 관리자</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 3}">CEO</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 4}">현장 소장</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 5}">팀장</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 6}">감독자</c:if>
						<c:if test ="${sessionScope.userLoginInfo.level == 7}">현장 업체</c:if>
						</span>
					</li>
					<c:if test ="${sessionScope.userLoginInfo.level >= 2}"> 
						<li><span class="identTitle"><span class="head">소속 현장 : <br></span>
							${sessionScope.siteVO.sitename}</span>
						</li>
					</c:if>
					<li  class="last"><span id="logout" class="btn_typ01">로그아웃&nbsp;></span></li>
				</ul><!-- //aside_ident -->
			
			</div><!-- //aside -->
			
			<div class="content work">