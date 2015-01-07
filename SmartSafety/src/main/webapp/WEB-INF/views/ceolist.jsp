<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
<!doctype html>
<html >
	<head>
		<title>GSIL</title>
		<link rel="stylesheet" href="css/ceocommon.css" type="text/css">  
  	    <script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
		<script type="text/javascript" src="js/jquery.plugin.js"></script>
		<script type="text/javascript" src="js/jquery.datepick.js"></script> 
		<script type="text/javascript" src="js/jquery.datepick-ko.js"></script>
		<script type="text/javascript" src="js/jquery.timer.js"></script>

		<script>
		function goPage(val) {
			$('#pageNum').val(val);
			$('#searchForm').submit();
		}
		
		function starttimer() {
 			var timer = $.timer(function(){ 
				window.location.reload();
 			});
			timer.set({ time :300000 , autostart :true}); 
		}
		
		</script>
	</head>

	<body onload="starttimer();">
		<div id="wrap" class="sub ">
			<h1><img src="images/logo_ds.png" alt="두산그룹로고" /></h1>
			<div class="back_bg sub">
				<div class="wrapBox ceo">		

					<div class="content ">
					
					<div class="siteSearchBox">
						<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
								<option value="1" >본사 관리자(EHS팀)</option>
								<option value="2" >현장 안전관리자</option>
						</select>
					</div>
					<div class="searchboxdata" >
					
						<div class="listLevelBox">
							<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
									<option value="1" >본사 관리자(EHS팀)</option>
									<option value="2" >현장 안전관리자</option>
							</select>
							
							<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
									<option value="1" >본사 관리자(EHS팀)</option>
									<option value="2" >현장 안전관리자</option>
							</select>
							
							<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
									<option value="1" >본사 관리자(EHS팀)</option>
									<option value="2" >현장 안전관리자</option>
							</select>
							
							<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
									<option value="1" >본사 관리자(EHS팀)</option>
									<option value="2" >현장 안전관리자</option>
							</select>
							
						</div>					
						<div class="srchbox">
							<p>
								<input type="search" name="searchWord" title="검색창" class="search_input" autocomplete="off" >
								<span class="btn_search"><a href="#">검색</a></span>
							</p>
						</div><!-- //srchbox -->
					</div>

						<table>
						<colgroup>
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">								
								<col>					

						</colgroup>
							
							<tr>
								<th>현장</th>
								<th>작업</th>
								<th>위험도</th>
								<th>안전점검<br/>여부</th>								
								<th>점검자</th>
								<th>작업기간</th>						
							</tr>
							
							<c:forEach var="ceo" items="${ceoList}" varStatus="idx">
								<tr class="listTR">
									<td>${ceo.sitename}</td>
									<td>${ceo.worktitle}</td>
									<td>${ceo.risk_grade}</td>
									<td><c:if test="${ceo.checkyn == 'Y'}"><img src="images/check-blue.png" /></c:if><c:if test="${ceo.checkyn == 'N'}"><img src="images/check-red.png" /></c:if>
									</td>
									<td>${ceo.name}</td>
									<td>${ceo.startdate} ~ ${ceo.enddate}</td>
								</tr>		
							</c:forEach>
						</table>					

						<div class="paging">
						    <a href="javascript:goPage(${paging.firstPageNo})"  class="first"><span> &lt;&lt; </span></a>
						    <a href="javascript:goPage(${paging.prevPageNo})" class="prev"><span> &lt; </span></a>
						    <span>
						        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
						            <c:choose>
						                <c:when test="${i eq paging.pageNo}"><a href="javascript:goPage(${i})" class="now"><span>${i}</span></a></c:when>
						                <c:otherwise><a href="javascript:goPage(${i})"><span>${i}</span></a></c:otherwise>
						            </c:choose>
						        </c:forEach>
						    </span>
						    <a href="javascript:goPage(${paging.nextPageNo})" class="next"><span> &gt; </span></a>
						    <a href="javascript:goPage(${paging.finalPageNo})" class="last"><span> &gt;&gt; </span></a>
							
						</div>

					</div><!-- //content -->

				</div><!-- //wrapBox -->
			</div><!-- //back_bg -->
		</div><!-- //wrap -->

		<div class="pageClosing">
		 <span class="renew"><img src="images/renew.png" width="35px" alt="새로고침"></span>
		 <span class="closing"><img src="images/closing.png" width="35px" alt="닫기"></span>
		</div><!-- //pageClosing  -->
		
		<div id="form_group" style="display:none">
			<form id="searchForm" action="ceolist" action="GET" >
				<input id="searchWord" name="searchWord" type="hidden">
				<input id="pageNum" name="pageNum" type="hidden">
			</form>
		</div>
	</body>
</html>
