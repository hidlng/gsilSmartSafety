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
  	<link rel="stylesheet" href="css/custom.css" type="text/css">
     <link rel="stylesheet" href="css/print.css" type="text/css" media="print"/>   
 </head>
 <body>
 
 <c:forEach var="puiVO" items="${puiList}" varStatus="index">
 <div id="wrap" class="a4"> 
	 <div class="box_top">
		<!--h1><img src="images/logo_ds.png" width="100"alt="두산로고"></h1-->
		<div class="top">
			<p class="title01">${puiVO.sitename}</p>
			<span class="date">${puiVO.printtime}</span>
		</div><!-- //top -->
		<h2>사용전 점검(Pre Use Inspection)</h2>
	 </div><!-- //box_top --> 
	<table class="typ02">
	<caption>정보</caption>
	<colgroup>
			<col style="width: 15%">
			<col style="width: 48%">
			<col style="width: 12%">
			<col>			
	</colgroup>
	 <tr>
		<th class="bold">협력업체 : </th>
		<td class="bold">${puiVO.cont_name}</td>
		<th>소장 : </th>
		<td>${puiVO.inspector}(${puiVO.inspector_phone})</td>
	 </tr>
	 <tr>
		<th>작업명 : </th>
		<td>${puiVO.worktitle}</td>
		<th>공사감독자 : </th>
		<td>${puiVO.cont_rep_name}(${puiVO.cont_rep_phone})</td>
	 </tr>
	 <tr>
		<th>작업책임자 : </th>
		<td>${puiVO.pic_name} (${puiVO.pic_phone})</td>
		<th>작업자 수 : </th>
		<td>${puiVO.pic_num_worker}</td>
	 </tr>
	 <tr>
		<th>날씨 : </th>
		<td colspan="3">${puiVO.weather}</td>
		
	 </tr>
 </table><!-- //typ02 -->

	 <p class="textBox">본 장비는 사용 전 반드시 아래사항을 확인 후 사용하시오.</p>
	 <table>
	 <caption>주요위험</caption>
		<colgroup>		
			<col style="width: 20%">
			<col style="width: 20%">
			<col style="/">		
		</colgroup>
		 <tr>
			<th>장비명</th>
			<td>${puiVO.toolname}</td>
			<th>주요 위험</th>		
		 </tr>
		 <tr>
		 <td colspan="2">
		 <c:if test="${puiVO.toolurl == null}"></c:if>
		 <c:if test="${puiVO.toolurl != null}"><img src="${puiVO.toolurl}" width="200" height="200" alt=""></c:if>
		 </td>
			<td>${puiVO.mainrisk}</td>
		 </tr>
	 </table><!-- //table -->

	 <table>
	 <caption>사용 전 점검 사항</caption>
		<colgroup>		
			<col style="width: 30%">
			<col style="/">
			<col style="width: 10%">
					
		</colgroup>
		<tr>
			<th colspan="3">사용 전 점검 사항</th>		
		</tr>
		<tr>
			<th>사진</th>
			<th>체크사항</th>
			<th>확인란</th>
		</tr>
		<c:forEach var="check" items="${puiVO.checklist}" varStatus="index">
			<tr>
				<td><img src="${check.url}" width="130" height="130" alt=""></td>
				<td> ${check.check}</td>
				<td></td>
			</tr>
		</c:forEach>
	 </table><!-- //table -->
	 <div class="signBox">
		<p>성명</p><span>(서명)</span>	
	 </div>
  
 </c:forEach>
 </body> 
</html>
