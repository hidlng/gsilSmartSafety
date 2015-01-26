<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
<!doctype html>
<html >
	<head>
		<title>GSIL</title>
		<link rel="stylesheet" href="css/ceocommon.css" type="text/css"> 
  		<link rel="stylesheet" href="css/jquery.datepick.css" type="text/css" >
  		<link rel="stylesheet" href="css/jquery.timepicker.css" type="text/css" >  
  	    <link href="/sss.ico" rel="icon" type="image/x-icon" />
 		<link href="/sss.ico" rel="shortcut icon" type="image/x-icon" />
  	    <script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
		<script type="text/javascript" src="js/jquery.plugin.js"></script>
		<script type="text/javascript" src="js/jquery.datepick.js"></script> 
		<script type="text/javascript" src="js/jquery.datepick-ko.js"></script>
		<script type="text/javascript" src="js/jquery.timer.js"></script>
		<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=ture_or_false"></script>
		<script>

		
		$(document).ready(function() {
			if(document.getElementById( 'riskSearchValue' ).value != '' ) {
				document.getElementById( 'riskSearch' ).selectedIndex = document.getElementById( 'riskSearchValue' ).value;
			}
			
			if(document.getElementById( 'chkSearchValue' ).value != '' ) {
				document.getElementById( 'checkSearch' ).selectedIndex = document.getElementById( 'chkSearchValue' ).value;
			}

			if(document.getElementById( 'siteindex' ).value != '' ) {
				document.getElementById( 'siteSearch' ).selectedIndex = document.getElementById( 'siteindex' ).value;
			}
			
			$(document).on("click", "#homeLogo", function(e) { $('#workList_form').submit(); });
			
					   
		}); 
		
	  	$(function() {
		    
		    $( "#searchDate" ).datepick( { 
	    		onClose: function (selectedDate) {
	    		if(selectedDate != "") {	    			
	    			$( "#searchDate" ).datepick( "option", "dateFormat", 'yyyymmdd' ); 
	    			goPage(1);
	    		}
	    	}
		    } ); 
		});
	  	
	  	function goPage(val) {
	  		$('#searchWord').val($('#searchDate').val());
	  		$('#pageNum').val(val);
	  		$('#searchForm').submit();
	  	}
		
		function chgLevel(gubun,val) {
			
			if( gubun == '1' ) {
				$('#riskSearchValue').val(val);
			} else if( gubun == '2' ) {
				$('#chkSearchValue').val(val);	
			} else if( gubun == '3' ) {
				if( val.substring((val.indexOf("/")+1)) == '0' ) {
					$('#siteValue').val("");
					$('#siteindex').val("0");
				} else {
					$('#siteValue').val(val.substring(0,val.indexOf("/")));
					$('#siteindex').val(val.substring((val.indexOf("/")+1)));
				}
				
			}
			
			$('#searchForm').submit();
		}
		
		function starttimer() {
 			var timer = $.timer(function(){ 
				window.location.reload();
 			});
			timer.set({ time :60000 , autostart :true});
		}

		function viewSubmit(val) {
			var idx = $("#updateIdx_"+val).val();
			$("#viewIdx").val(idx);
			$('#viewForm').submit();
		 	
		}
		</script>
	</head>

	<body onload="starttimer();">
		<div id="wrap" class="sub ">
			<!--h1><img id="homeLogo" src="images/logo_ds.png" style="cursor:pointer"/></h1>--><!-- //1204 추가 -->
			<div class="back_bg sub">
				<div class="wrapBox ceo">		

					<div class="content ">
					
					<div class="searchboxdata" >
					
						<div class="listLevelBox">
						
							<select id="siteSearch" class="selectBox" onchange="chgLevel('3',this.value)">
								<option value="/0" >--현장선택--</option>
							<c:forEach var="site" items="${siteList}" varStatus="idx">
								<option value="${site.site_idx}/${idx.count}" >${site.sitename}</option>
							</c:forEach>
							</select>						
						
							<select id="riskSearch" class="selectBox" onchange="chgLevel('1',this.value)">
									<option value="0" >위험도</option>
									<option value="1" >A</option>
									<option value="2" >B</option>
									<option value="3" >C</option>
							</select>
							
							<select id="checkSearch" class="selectBox" onchange="chgLevel('2',this.value)">
									<option value="0" >점검여부</option>
									<option value="1" >점검확인</option>
									<option value="2" >점검대기</option>
							</select>
						</div>					
						<div class="srchbox">
							<p>
								<input id="searchDate" type="search" name="searchWord" title="검색창" class="search_input" value="${searchWord}" autocomplete="off" >
								<span class="btn_search" onclick="goPage(1)" ><a href="#">검색</a></span>
							</p>
						</div><!-- //srchbox -->
					</div>

						<table>
						<colgroup>
								<col style="width:8%">
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:8%">
								<col style="width:7%">
								<col style="width:7%">
								<col style="width:7%">
								<col>								
						</colgroup>

							<tr>
								<th colspan="4">현&nbsp;장&nbsp;작&nbsp;업&nbsp;정&nbsp;보</th>
								<th colspan="3" >점검여부</th>									
								<th rowspan="2">작업기간</th>						
							</tr>
							
							<tr>
								<th>구&nbsp;&nbsp;분</th>
								<th>현&nbsp;&nbsp;장</th>
								<th>작&nbsp;&nbsp;업</th>
								<th>위험도</th>
								<th >안전<BR>관리자</th>	
								<th >소&nbsp;장</th>	
								<th >팀&nbsp;장</th>						
							</tr>
							
							<c:forEach var="ceo" items="${ceoList}" varStatus="idx">
								<tr class="listTR" onclick="viewSubmit('${idx.index}')">
									<td><c:if test="${ceo.type == '0'}">건설</c:if><c:if test="${ceo.type == '1'}">인프라</c:if>
									</td>
									<td>${ceo.sitename}</td>
									<td>${ceo.worktitle}</td>
									<td>${ceo.risk_grade}</td>
									<td><c:if test="${ceo.checkyn == 'Y'}"><img src="images/check-blue.png" width="30px" /></c:if><c:if test="${ceo.checkyn == 'N'}"><img src="images/check-red.png"  width="30px"/></c:if>
									</td>
									<td><c:if test="${ceo.checkyn == 'Y'}"><img src="images/check-blue.png" width="30px" /></c:if><c:if test="${ceo.checkyn == 'N'}"><img src="images/check-red.png"  width="30px"/></c:if>
									</td>
									<td><c:if test="${ceo.checkyn == 'Y'}"><img src="images/check-blue.png" width="30px" /></c:if><c:if test="${ceo.checkyn == 'N'}"><img src="images/check-red.png"  width="30px"/></c:if>
									</td>
									<td>${ceo.startdate} ~ ${ceo.enddate}<input id="updateIdx_${idx.index}" type="hidden" value="${ceo.work_idx}" /></td>
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
<!-- 
		<div class="pageClosing">
		 <span class="renew"><img src="images/renew.png" width="35px" alt="새로고침"></span>
		 <span class="closing"><img src="images/closing.png" width="35px" alt="닫기"></span>
		</div> --><!-- //pageClosing  -->
		
		<div id="form_group" style="display:none">
			<form id="searchForm" action="ceolist" action="GET" >
				<input id="searchWord" name="searchWord" type="hidden" value="${searchWord}">
				<input id="siteValue" name="siteValue" type="hidden" value="${siteValue}" >
				<input id="siteindex" name="siteindex" type="hidden" value="${siteindex}" >
				<input id="riskSearchValue" name="riskSearchValue" value="${riskSearchValue}" type="hidden">
				<input id="chkSearchValue" name="chkSearchValue" value="${chkSearchValue}" type="hidden">
				<input id="pageNum" name="pageNum" type="hidden">
			</form>
			
			<form id="viewForm" action="viewWork" action="POST" >
				<input id="viewIdx" type="hidden" name="viewIdx"/>
				<input id="fromCEO" type="hidden" name="fromCEO" value="true"/>
			</form>
			<c:if test="${sessionScope.userLoginInfo.level != 3}">
				<form id="workList_form" action="workList" method="POST"></form>
			</c:if>
		</div>
	</body>
</html>
