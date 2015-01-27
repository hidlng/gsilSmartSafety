<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
$(document).ready(function() {
	
 });

function updateNotice(){
	var input = confirm('수정하시겠습니까?');
	if(input)
		$('#updateForm').submit();
}

function deleteNotice() {	
	input = confirm('삭제하시겠습니까?');
	if(input){
		$('#deleteForm').submit();
	}
}


 </script>
 

<div class="bgCover">&nbsp;</div>
	<table class="work_signup">
		<colgroup>
			<col style="width: 33%">
			<col>
		</colgroup>
		<tr>
			<th>제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</th>
			<td>${noticeVO.title}</td>
		</tr>
		<tr>
			<th>작&nbsp;성&nbsp;자</th>
			<td>${noticeVO.writer_name}</td>
		</tr>
		<tr>
			<th>작&nbsp;성&nbsp;일</th>
			<td>${noticeVO.writetime.substring(0,16)}</td>
		</tr>
		<tr>
			<th>내&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용</th>
			<td style="text-align:left;">${noticeVO.content}</td>
		</tr>
	</table>
	

	<div class="paging">
		<c:if test="${canModify == true}">
			<span class="btn_typ02" onclick="updateNotice();">수정</span>
			<span class="btn_typ02" onclick="deleteNotice();">삭제</span>
		</c:if>
	</div>
	
	
<div id="form_group">
	<form id="updateForm" action="registerNotice" method="POST" >
		<input id="updateIdx" type="hidden" name="updateIdx" value="${noticeVO.notice_idx}"/>
	</form>
	<form id="deleteForm" action="deleteNotice" method="POST" >
		<input id="deleteIdx" type="hidden" name="deleteIdx" value="${noticeVO.notice_idx}"/>
	</form>
</div>

	<%@ include file="IncludeBottom.jsp"%>