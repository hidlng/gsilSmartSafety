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
  <title>두산건설-사용전 점검(Pre Use Inspection)</title>
    <link rel="stylesheet" href="css/screen.css" type="text/css">
     <link rel="stylesheet" href="css/print.css" type="text/css" media="print"/>   
 </head>
 <body>
 
 <c:forEach var="puiVO" items="${puiList}" varStatus="index">
 <div id="wrap" class="a4"> 
 	 <div class="box_top">
		<h1><img src="images/logo_ds.png" width="100" alt="두산로고"></h1>
		<span class="date">${puiVO.printtime}</span>
		<div class="top">
			<p class="title01">${puiVO.sitename}</p>			
		</div><!-- //top -->
		<h2>사용전 점검(Pre Use Inspection)</h2>
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
			<td class="bold">${puiVO.cont_name}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>협력사소장</th>
			<th> : </th>
			<td>${puiVO.cont_rep_name}(${puiVO.cont_rep_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;명 </th>
			<th> : </th>
			<td>${puiVO.worktitle}</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>공사감독자 </th>
			<th> : </th>
			<td>${puiVO.inspector}(${puiVO.inspector_phone})</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업책임자 </th>
			<th> : </th>
			<td>${puiVO.pic_name} (${puiVO.pic_phone})</td>
			<th><span class="bull_dot">&middot;&nbsp;</span>작업자&nbsp; 수 </th>
			<th> : </th>
			<td>
				<c:if test="${puiVO.pic_num_worker >= 999 }">30+</c:if>
				<c:if test="${puiVO.pic_num_worker < 999 }">${puiVO.pic_num_worker}</c:if>
			</td>
		 </tr>
		 <tr>
			<th><span class="bull_dot">&middot;&nbsp;</span>날&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;씨 </th>
			<th> : </th>
			<td colspan="3">${puiVO.weather}</td>
			
		 </tr>
	 </table><!-- //typ02 -->
	 </div>
	 <p class="textBox">본 장비는 사용 전 반드시 아래사항을 확인 후 사용하시오.</p>
	 <table>
	 <caption>주요위험</caption>
		<colgroup>		
			<col style="width: 10%">
			<col style="width: 15%">
			<col style="/">
			<col style="width:50%">				
		</colgroup>
		 <tr>
			<th>장비명</th>
			<td>${puiVO.toolname}</td>
			<th>주요 위험</th>		
			<th>장비사용가이드</th>		
		 </tr>
		 <tr>
			 <td colspan="2">
			 	<c:if test="${puiVO.toolurl == null}"></c:if>
			 	<c:if test="${puiVO.toolurl != null}"><img src="${puiVO.toolurl}" width="150" height="150" alt=""></c:if>
			 </td>
			<td>${puiVO.mainrisk}</td>
			<td>${puiVO.guide}</td>
		 </tr>
	 </table><!-- //table -->

	 <table>
	 <caption>사용 전 점검 사항</caption>
		<colgroup>		
			<col style="width: 10%">
			<col style="width: 33%">
			<col>
			<col style="width: 10%">
			<col style="width: 33%">
			<col>		
		</colgroup>
		<tr>
			<th colspan="6">사용 전 점검 사항</th>		
		</tr>
		<tr>
			<th>사  진</th>
			<th>체크사항</th>
			<th>확 인 란</th>
			<th>사  진</th>
			<th>체크사항</th>
			<th>확  인</th>
		</tr>
		<c:forEach var="check" items="${puiVO.checklist}" varStatus="index">
			<c:if test="${index.count % 2 == 1}"><tr></c:if>
				<td><img src="${check.url}" width="55" height="55" alt=""></td>
				<td style="text-align:left; padding-left:20px"> ${check.check}</td>
				<td></td>
			<c:if test="${index.count % 2 == 0}"></tr></c:if>
		</c:forEach>
	 </table><!-- //table -->
	 <div class="signBox" style="margin-top: ${ (4 - puiVO.checklist.size()/2) * 55 }px">
		<p>성명</p><span>(서명)</span>	
	 </div>
  </div>
 </c:forEach>
 </body> 
</html>
