<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {
		$('tr:even').addClass('even');
		$('tr:odd').addClass('odd');
	});

	$(document).on("click", ".detailLink", function(e) {
		e.preventDefault();
		$.ajax({
			type : "POST",
			url : this.href,
			cache : false,
			success : onSuccess,
			error : onError
		});
	});
	
	
	function onSuccess(json, status) {
		$('#viewContent').html(json);
		$('tr:even').addClass('even');
		$('tr:odd').addClass('odd');
		
		doOverlayOpen();	
	}

	function onError(data, status) {
		alert("error : " + status);
	}
	
	function closeViewContent() {
		$('#viewContent').html('');
		doOverlayClose();
		
	}
	
	
</script>

<stripes:form id="listForm"	beanclass="com.spring.risk.web.actions.PermitActionBean">


<!-- List -->
<div id="categoryList">
<div id="categoyListTitle"><img src="../images/blue_title.gif"/> 리스트</div>
	<table id="categoryListTable">		
		<colgroup>
		<col style="width: 5%">
		<col style="width: 10%">
		<col style="width: 30%">
		<col>
		<col style="width: 5%">
		<col style="width: 5%">
		</colgroup>
		<tr>
			<th>번호</th>
			<th>작업코드</th>
			<th>장소유형</th>
			<th>내용</th>
			<th>등록</th>
			<th>삭제</th>
		</tr>
		
			<c:forEach var="row" items="${actionBean.permitList}" varStatus="idx">
				<tr>
				<td>${idx.index + 1}</td>
				<td>${row.workcode}</td>
				<td>
					${row.parse_codeList}
				</td>
				<td class="wordBreak" style="text-align: left; padding-left:5px;">${row.content}</td>
				<td>
					<stripes:link class="detailLink"
						beanclass="com.spring.risk.web.actions.PermitActionBean" event="updateDetailForm">
					<stripes:param name="permit_idx">${row.permit_idx}</stripes:param>수정</stripes:link>
				</td>	
				<td>
					<stripes:link 
						beanclass="com.spring.risk.web.actions.PermitActionBean" event="deletePermit">
					<stripes:param name="permit_idx">${row.permit_idx}</stripes:param>삭제</stripes:link>
				</td>	
				</tr>
			</c:forEach>
		
	</table>
</div>
</stripes:form>




<stripes:link class="detailLink button" beanclass="com.spring.risk.web.actions.PermitActionBean" event="insertDetailForm">
<stripes:param name="permit_idx">${row.permit_idx}</stripes:param>등록</stripes:link>






<!-- Detail -->
<div class="bgCover">&nbsp;</div>
<!-- overlay box -->
<div class="overlayBox">
<a href=# class="closeLink" ><img src="../images/x-button.png" onclick="closeViewContent()" style="cursor:hand"/></a>
<div class="overlayContent"><div id="viewContent"></div></div>
</div>



<%@ include file="IncludeBottom.jsp"%>