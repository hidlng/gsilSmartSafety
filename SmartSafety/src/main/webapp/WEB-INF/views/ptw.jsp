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
  <link href="/sss.ico" rel="icon" type="image/x-icon" />
  <link href="/sss.ico" rel="shortcut icon" type="image/x-icon" />  
 </head>
 <body>
  <img src="images/print2.gif" class="printIcon" width="100" alt="출력하기" onclick="window.print();" >
 <div id="wrap"> 
	 <div class="box_top">
		 <h1><img></h1>
		<!--h1><img src="images/logo_ds.png" width="100" alt="두산로고"></h1-->
		<div class="top">
			<p class="title01">${ptwVO.sitename}</p>
			<span class="date">${ptwVO.printtime}</span>
		</div><!-- //top -->
		<h2>작업 허가증(Permit to work)</h2>
	 </div><!-- //box_top -->

	 <div class="wrap_table">
		<table class="typ02">
		<caption>정&nbsp;보</caption>
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
			<td><c:if test="${ptwVO.pic_num_worker >= 999 }">31+</c:if>
				<c:if test="${ptwVO.pic_num_worker < 999 }">${ptwVO.pic_num_worker}</c:if>
			</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>날&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;씨 </th>
			<th> : </th>
			<td colspan="3">${ptwVO.weather}</td>
			<th></th>
			<td colspan="3"></td>
		 </tr>
	 </table><!-- //typ02 -->
	 </div>



	 <table class="ptw_table">
	 <caption>작업허가서</caption>
	 <caption>작업장소내용</caption>
		<colgroup>		
			<col style="width: 7%">
			<col style="width: 15%">
			<col style="/">
			<col style="width: 8%">
			<col style="width: 8%">
			<col style="width: 8%">				
		</colgroup>
		<tr>
			<th>종&nbsp;&nbsp;류</th>
			<th>명&nbsp;&nbsp;&nbsp;&nbsp;칭</th>
			<th>내&nbsp;&nbsp;용</th>
			<th>안&nbsp;전<br>조&nbsp;치</th>
			<th>안&nbsp;전<br>확&nbsp;인</th>
			<th>승&nbsp;인</th>
		</tr>
		
		<c:forEach var="permit" items="${ptwVO.permitList}" varStatus="idx">
		<tr>
			<td>
				<c:if test="${permit.type == 1}">작&nbsp;&nbsp;업</c:if>
				<c:if test="${permit.type == 3}">장&nbsp;&nbsp;소</c:if>
				<c:if test="${permit.type == 99}">작업 + 장소</c:if>
			</td>
			<td>${permit.name}</td>
			<td class="lft">${permit.content}</td>
			<td>Y / N</td>
			<td>Y / N</td>
			<td>Y / N</td>
		</tr>
		</c:forEach>
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
			<td>소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</td>
			<td class="lft">성명 : </td>
			<td class="lft">(서명)</td>
		</tr>
	 </table><!-- //table lastsign -->
 
 </div><!-- //wrap -->  
 </body> 
</html>
