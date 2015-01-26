<!doctype html>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>작업전 위험 예지 조회(TBM)</title>
  <link rel="stylesheet" href="css/screen.css" type="text/css">
  <link rel="stylesheet" href="css/print.css" type="text/css" media="print">
   <script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
  <script type="text/javascript" src="js/jquery.plugin.js"></script>
 </head>	
 
 <body id="body">

 <img src="images/print2.gif" class="printIcon" width="100" alt="출력하기" onclick="window.print();" >
 <div id="wrap"> 
	 <div class="box_top">
	 <h1><img></h1>
		<!-- <h1><img src="images/logo_ds.png" width="100" alt="두산로고"></h1>-->
		<span class="date">${tbmVO.printtime}</span>
		<div class="top">
			<p class="title01">${tbmVO.sitename}</p>			
		</div><!-- //top -->
		<h2>작업전 위험 예지 조회(TBM)</h2>
	 </div><!-- //box_top -->
	 <div class="wrap_table">
		<table class="typ02">
		<caption>정보</caption>
		<colgroup>
				<col style="width: 12%">
				<col style="width: 2%">
				<col style="width: 38%">
				<col style="width: 12%">
				<col style="width: 2%">
				<col style="width: 34%">			
		</colgroup>
		 <tr>
			<th class="bold"><span class="bull_dot">&middot;&nbsp;</span>협&nbsp;력&nbsp;업&nbsp;체</th>
			<th> : </th>
			<td class="bold">${tbmVO.cont_name}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>협력사소장</th>
			<th> : </th>
			<td>${tbmVO.cont_rep_name}(${tbmVO.cont_rep_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;명 </th>
			<th> : </th>
			<td>${tbmVO.worktitle}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>공사감독자 </th>
			<th> : </th>
			<td>${tbmVO.inspector}(${tbmVO.inspector_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업책임자 </th>
			<th> : </th>
			<td>${tbmVO.pic_name} (${tbmVO.pic_phone})</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업자&nbsp; 수 </th>
			<th> : </th>
			<td><c:if test="${tbmVO.pic_num_worker >= 999 }">31+</c:if>
				<c:if test="${tbmVO.pic_num_worker < 999 }">${tbmVO.pic_num_worker}</c:if>
			</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>날&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;씨 </th>
			<th> : </th>
			<td colspan="3">${tbmVO.weather} </td>
			<th></th>
			<td colspan="3"></td>
		 </tr>
	 </table><!-- //typ02 -->
	 </div>

	 <table>
		<caption>사용안전점검여부</caption>
		<colgroup>
			<col style="/">
			<col style="width: 16%">
			<col style="width: 16%">
			<col style="width: 16%">
			<col style="width: 16%">
			<col style="width: 16%">			
		</colgroup>
		 <tr>
			<th>장비명<br>(사용전점검)</th>
			<td colspan="5"> ${tbmVO.toollist}</td>
		 </tr>
		 <tr>
			<th>주요 위험</th>
			<td class="lft" colspan="5" style="padding-top:-10px">
			${tbmVO.mainrisk}</td>
			
		 </tr>
		 <tr>
		 	<th>작업장소</th>
			<td colspan="5">${tbmVO.place_state}</td>		
		 </tr>
		 <tr>
			
		 </tr>
		 <tr>
			<th>위험등급</th>
			<td>${tbmVO.risk_level}</td>
			<th>위험경고</th>
			<td>${tbmVO.risk_warn}</td>
			<th>작업허가</th>
			<td>${tbmVO.workpermit}</td>
		 </tr>
	 </table><!-- //table -->

	 <table>
		<caption>안전 작업 지침</caption>
		<colgroup>
			<col style="/">
			<col style="width: 35%">				
		</colgroup>
		 <tr>
			<th colspan="2">안전 작업 지침</th>		
		 </tr>
		 <tr>	
			<td class="lft"><b>안전 조치 사항 안내</b>
							<br>${tbmVO.safety}</td>
			<td class="lft"><b>보호구 착용 지침</b>
							<br>${tbmVO.equip}</td>		
		 </tr>
		 <tr>
			<td class="lft"><b>안전 작업 가이드</b>
						<br>${tbmVO.guide}</td>
			<td class="lft"><b>비상시 조치 사항</b>
						<br>${tbmVO.measure}</td>		
		 </tr>
	 </table><!-- //table -->

	 <table>
		 <colgroup>		
				<col style="width: 33%">
				<col style="width: 33%">
				<col style="width: 33%">
			</colgroup>
		<caption>안&nbsp;전&nbsp;구&nbsp;호</caption>	
		 <tr>
			<th colspan="3">안&nbsp;전&nbsp;구&nbsp;호(작업 전 지시사항)</th>
		 </tr>
		 <tr>
			<td class="lft"><b>작성자</b><br>
			${tbmVO.remark}
			</td>
			<td class="lft"><b>팀장/안전관리자</b><br>
			${tbmVO.remark_leader}</td>
			<td class="lft"><b>현장소장</b><br>
			${tbmVO.remark_chief}</td>
		 </tr>
	 </table><!-- //table -->

	 <table>
	 <caption></caption>
		<colgroup>		
			<col style="width: 20%">
			<col style="width: 25%">
			<col style="width: 20%">
			<col style="/">		
		</colgroup>
		 <tr>
			<th>원청사<br>공사담당자</th>
			<td>${tbmVO.chief_name}<br>(${tbmVO.chief_phone})</td>
			<th>긴급연락처</th>
			<td>${tbmVO.cont_phone}<br>(${tbmVO.cont_emg_phone})</td>
		 </tr>
	 </table><!-- //table -->
 
 </div><!-- //wrap -->  
 </body>
</html>
