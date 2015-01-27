<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="true"%>

<!doctype html>
<html>
 <head>
 	<title>사고사례</title>
	<link rel="stylesheet" href="css/common.css" type="text/css">
</head>
<body>


	<div id="wrap_pop">
		
			
		<table class="pop">
			<colgroup>
				<col style="width: 25%">
				<col>
			</colgroup>
			<tr>
				<th>작업</th>
				<td><c:forEach var="fileVO" items="${workFileList}" varStatus="idx">
				(${fileVO.name}) <a href="${fileVO.url}">${fileVO.fileName}</a>	<br>
					</c:forEach></td>
			<tr>
				<th>장비/공도구</th>
				<td><c:forEach var="fileVO" items="${toolFileList}" varStatus="idx">
				(${fileVO.name}) <a href="${fileVO.url}">${fileVO.fileName}</a>	<br>
				</c:forEach></td>
			<tr>
				<th>장소</th>
				<td><c:forEach var="fileVO" items="${placeFileList}"
						varStatus="idx">
			 	(${fileVO.name}) <a href="${fileVO.url}">${fileVO.fileName}</a>
						<br>
					</c:forEach></td>
			</tr>
		</table>
	</div>
</body>
</html>