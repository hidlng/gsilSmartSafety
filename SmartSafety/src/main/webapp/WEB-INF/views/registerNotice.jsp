<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <script>
 var idNotDuplicate = false;
 $(document).ready(function() {
	 if(${updateMode} == true) { $('#noticeForm').attr('action', 'updateNotice'); }
	 else{ $('#noticeForm').attr('action', 'insertNotice'); }
		 
 });
 
 
  function submitNotice() {
	var input;
	if(${updateMode} == true) {
		input = confirm('수정하시겠습니까?');
	}else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) { //yes
		$('#noticeForm').submit();
	}else
		return;
  }
  
 </script>


<form:form id="noticeForm"  method="POST" modelAttribute="noticeVO" autocomplete="off">
	<input type="hidden" name="notice_idx" value="${noticeVO.notice_idx}" />
	<c:if test="${!updateMode}"><!-- insert mode -->
		<input type="hidden" name="site_idx" value="${sessionScope.siteVO.site_idx}">
		<input type="hidden" name="writer_idx" value="${sessionScope.userLoginInfo.user_idx}" />
	</c:if>	
<!-- //srchbox -->
<table class="user_signup">
	<colgroup>
		<col style="width: 24%">
		<col>
	</colgroup>

	<tr>
		<th>제목</th><td><form:input path="title" maxlength="50"/><br><form:errors cssClass="formError" path="title" /></td>		
	</tr>
	<tr>
		<th>작성자</th><td>${userName}</td>		
	</tr>
	<c:if test="${updateMode}">
	<tr>
		<th>작성일</th><td> ${noticeVO.writetime} </td>		
	</tr>
	</c:if>
	<tr>
		<th>내용</th><td><form:textarea path="content" maxlength="300"/><br><form:errors cssClass="formError" path="content" /></td>		
	</tr>
	
			
	
</table>

<div class="paging">
<!--  insert -->
<c:if test="${!updateMode}">
<span class="signup"><span class="btn_typ02"  onclick="submitNotice()">등록</span></span>
</c:if>

<!-- update -->
<c:if test="${updateMode}">
		<span class="btn_typ02"  onclick="submitNotice()">수정</span>
		<span class="btn_typ02"  onclick="deleteNotice()">삭제 </span>
	
</c:if>

	
</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>