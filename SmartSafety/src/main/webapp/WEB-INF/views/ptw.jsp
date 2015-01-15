<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="true"%>

<!doctype html>
<html>
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>작업 허가증(Permit to work)</title>
    <link rel="stylesheet" href="css/screen.css" type="text/css">
  	<link rel="stylesheet" href="css/custom.css" type="text/css">
     <link rel="stylesheet" href="css/print.css" type="text/css" media="print"/>   
 </head>
 <body>
 <div id="wrap"> 
	 <div class="box_top">
		<h1><img src="images/logo_ds.png" width="100" alt="두산로고"></h1>
		<div class="top">
			<p class="title01">두산건설 신사동 멋쟁이 증설 현장</p>
			<span class="date">2014-12-09 09:00</span>
		</div><!-- //top -->
		<h2>작업 허가증(Permit to work)</h2>
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
			<td class="bold">${ptwVO.cont_name}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>협력사소장 </th>
			<th> : </th>
			<td>${ptwVO.cont_rep_name}(${ptwVO.cont_rep_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;명 </th>
			<th> : </th>
			<td>${ptwVO.worktitle}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>공사감독자 </th>
			<th> : </th>
			<td>${ptwVO.inspector}(${ptwVO.inspector_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업책임자 </th>
			<th> : </th>
			<td>${ptwVO.pic_name} (${ptwVO.pic_phone})</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업자&nbsp; 수 </th>
			<th> : </th>
			<td><c:if test="${ptwVO.pic_num_worker >= 999 }">30+</c:if>
				<c:if test="${ptwVO.pic_num_worker < 999 }">${ptwVO.pic_num_worker}</c:if>
			</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>날&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;씨 </th>
			<th> : </th>
			<td colspan="3">${ptwVO.weather}</td>
			
		 </tr>
	 </table><!-- //typ02 -->
	 </div>



	 <table>
	 <caption>작업허가서</caption>
		<colgroup>		
			<col style="width: 15%">
			<col style="/">
			<col style="width: 10%">
			<col style="width: 10%">
			<col style="width: 10%">					
		</colgroup>
		<tr>
			<th colspan="5">작업허가서</th>		
		</tr>
		<tr>
			<td colspan="5" class="lft">본 작업은 고위험 작업이므로 반드시 안전전문가의 사전 확인을 취득한 후 작업을 시작하여야 합니다. </td>		
		</tr>
		<tr>
			<th colspan="5">안전조치 및 점검 Checklist</th>
		</tr>
		<tr>
			<th>작업명</th>
			<th>내용</th>
			<th>안전조치</th>
			<th>안전확인</th>
			<th>승인</th>
		</tr>
		<tr>
			<td rowspan="4">화기작업</td>
			<td class="lft">작업 장 주변 불꽃 비산 방지조치가 이루어졌는가?</td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>
		</tr>
		<tr>
			<td class="lft"></td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>
			
		</tr>
		<tr>
			<td class="lft"></td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>			
		</tr>
	 </table><!-- //table -->

	 <table>
	 <caption>작업장소내용</caption>
		<colgroup>		
			<col style="width: 15%">
			<col style="/">
			<col style="width: 10%">
			<col style="width: 10%">
			<col style="width: 10%">				
		</colgroup>
		<tr>
			<th>장소명</th>
			<th>내용</th>
			<th>안전조치</th>
			<th>안전확인</th>
			<th>승인</th>
		</tr>
		<tr>
			<td rowspan="2">고소(5m 이하)</td>
			<td class="lft">작업 장 주변 불꽃 비산 방지조치가 이루어졌는가?</td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>
		</tr>
		<tr>
			<td class="lft">화기작업 현장 주변 인화물질은 없는가?</td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>
		</tr>
	 </table><!-- //table -->
	 <table class="lastsign">
	 <caption>작업허가 최종 승인</caption>
		<colgroup>		
			<col style="width: 25%">
			<col style="width: 15%">
			<col style="/">		
			<col style="width: 30%">				
		</colgroup>
		<tr>
			<th rowspan="2">작업 허가 최종 승인</th>
			<td>안전팀장</td>
			<td class="lft">성명 : </td>
			<td class="lft">(서명)</td>
		</tr>
		<tr>		
			<td>소장</td>
			<td class="lft">성명 : </td>
			<td class="lft">(서명)</td>
		</tr>
	 </table><!-- //table lastsign -->
 
 </div><!-- //wrap -->  
 </body> 
</html>
