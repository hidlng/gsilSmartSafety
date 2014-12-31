<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="true"%>
 
  
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>두산건설-작업전 위험 예지 조회(TBM)</title>
  <link rel="stylesheet" href="css/screen.css" type="text/css">
  <link rel="stylesheet" href="css/print.css" type="text/css">
  
  <link rel="stylesheet" href="css/custom.css" type="text/css">      
  
  <script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
  <script type="text/javascript" src="js/jquery.plugin.js"></script>
  <script type="text/javascript" src="js/smart.safety.js"></script>
 
 </head>
 <body>

 <div id="wrap"> 
	 <div class="box_top">
		<h1><img src="images/logo_ds.png" width="100"alt="두산로고"></h1>
		<div class="top">
			<p class="title01">${tbmVO.sitename}</p>
			<span class="date">출력시간</span>
		</div><!-- //top -->
		<h2>작업전 위험 예지 조회(TBM)</h2>
	 </div><!-- //box_top -->
	 <table class="typ02">
		<caption>정보</caption>
		<colgroup>
				<col style="width: 12%">
				<col style="width: 38%">
				<col style="width: 12%">
				<col style="width: 38%">			
		</colgroup>
		 <tr>
			<th class="bold">협력업체 : </th>
			<td class="bold">${tbmVO.cont_name}</td>
			<th>소장 : </th>
			<td>${tbmVO.cont_rep_name}(${tbmVO.cont_rep_phone})</td>
		 </tr>
		 <tr>
			<th>작업명 : </th>
			<td>${tbmVO.worktitle}</td>
			<th>공사감독자 : </th>
			<td>${tbmVO.inspector}(${tbmVO.inspector_phone})</td>
		 </tr>
		 <tr>
			<th>작업책임자 : </th>
			<td>${tbmVO.pic_name} (${tbmVO.pic_phone})</td>
			<th>작업자 수 : </th>
			<td>${tbmVO.pic_num_worker}</td>
		 </tr>
		 <tr>
			<th>날씨 : </th>
			<td colspan="3">${tbmVO.weather}</td>
			
		 </tr>
	 </table><!-- //typ02 -->

	 <table>
		<caption>사용안전점검여부</caption>
		<colgroup>
			<col style="/">
			<col style="width: 12%">
			<col style="width: 12%">
			<col style="width: 12%">
			<col style="width: 18%">
			<col style="width: 20%">			
		</colgroup>
		 <tr>
			<th colspan="6">사용장비 /사용안전점검여부</th>			
		 </tr>
		 <tr>	
			<td colspan="6"> ${tbmVO.toollist}</td>
		 </tr>
		 <tr>
			<th>주요 위험정보</th>
			<td colspan="3">${tbmVO.mainrisk}</td>	
			<th>작업장소</th>
			<td colspan="2">${tbmVO.workname}</td>	
		 </tr>
		 <tr>
			<th>위험등급</th>
			<td>${tbmVO.risk_grade}</td>
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
			<td class="lft">안전 조치 사항 안내
						 ${tbmVO.measure}</td>
			<td class="lft">보호구 착용 지침
					${tbmVO.equip}</td>		
		 </tr>
		 <tr>
			<td class="lft">안전 작업 가이드
			${tbmVO.guide}</td>
			<td class="lft">비상시 조치 사항(보류)</td>		
		 </tr>
	 </table><!-- //table -->

	 <table>
		<caption>안전구호</caption>	
		 <tr>
			<th>안전구호</th>
		 </tr>
		 <tr>
			<td class="lft">작업 전 지시사항
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