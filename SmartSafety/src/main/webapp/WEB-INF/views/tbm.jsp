<!doctype html>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<html>
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>두산건설-작업전 위험 예지 조회(TBM)</title>
  <link rel="stylesheet" href="css/screen.css" type="text/css">
  <link rel="stylesheet" href="css/print.css" type="text/css">
 </head>
 <body>

 <div id="wrap"> 
	 <div class="box_top">
		<h1><img src="images/logo_ds.png" width="100" alt="두산로고"></h1>
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
			<th><span class="bull_dot">&middot;&nbsp;</span>소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장 </th>
			<th> : </th>
			<td>${tbmVO.inspector}(${tbmVO.inspector_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;명 </th>
			<th> : </th>
			<td>${tbmVO.worktitle}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>공사감독자 </th>
			<th> : </th>
			<td>${tbmVO.cont_rep_name}(${tbmVO.cont_rep_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업책임자 </th>
			<th> : </th>
			<td>${tbmVO.pic_name} (${tbmVO.pic_phone})</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업자&nbsp; 수 </th>
			<th> : </th>
			<td>${tbmVO.pic_num_worker}</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>날&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;씨 </th>
			<th> : </th>
			<td colspan="3">맑음 ${tbmVO.weather} <img src="images/weather_sunny-40.gif" height="20px" width="20"> </td>
			
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
			<th>주요 위험정보</th>
			<td colspan="5">${tbmVO.mainrisk}</td>
			
		 </tr>
		 <tr>
		 	<th>작업장소</th>
			<td colspan="5">${tbmVO.placename}</td>		
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
							${tbmVO.measure}</td>
			<td class="lft"><b>보호구 착용 지침</b>
							${tbmVO.equip}</td>		
		 </tr>
		 <tr>
			<td class="lft"><b>안전 작업 가이드</b>
						${tbmVO.guide}</td>
			<td class="lft"><b>비상시 조치 사항</b></td>		
		 </tr>
	 </table><!-- //table -->

	 <table>
		<caption>안&nbsp;전&nbsp;구&nbsp;호</caption>	
		 <tr>
			<th>안&nbsp;전&nbsp;구&nbsp;호</th>
		 </tr>
		 <tr>
			<td class="lft"><b>작업 전 지시사항</b><br><br>
			${tbmVO.remark}</td>
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
			<th>두산건설<br/>공사담당자</th>
			<td>${tbmVO.site_rep_name}<br>(${tbmVO.site_rep_phone})</td>
			<th>긴급연락처</th>
			<td>${tbmVO.cont_phone}(${tbmVO.cont_emg_phone})</td>
		 </tr>
	 </table><!-- //table -->
 
 </div><!-- //wrap -->  
 </body>
</html>
