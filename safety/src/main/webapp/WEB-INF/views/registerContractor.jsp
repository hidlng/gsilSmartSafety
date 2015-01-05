<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <script>
 var idNotDuplicate = false;
 $(document).ready(function() {
	
	 /**updateMode에선 ID중복체크하지 않음**/
	 if(${updateMode} == true) {
		 idNotDuplicate = true;	
		 $('#btnNewPasswd').show();
		 $('#input_password').hide();
		 $('#input_password').val('!@#$%^&&!@#$');
		 $('#contractorForm').attr('action', 'updateContractor');
	 }
	 /**insertmode**/
	 else{
		 idNotDuplicate = false;
		 $('#btnNewPasswd').hide();
		 $('#input_password').show();
		
		 $('#contractorForm').attr('action', 'insertContractor');
	 }
		 
 });
 

  function submitContractor() {
	var input;
	if(${updateMode} == true) {
		input = confirm('수정하시겠습니까?');
	}else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) { //yes
		  if(idNotDuplicate){
				$('#contractorForm').submit();
		  }else {
			  //$('#idError').text('중복 체크');
			  alert('ID중복체크를 해주시기 바랍니다(ID Check)');
		  }
	}else
		return;

	  
  }
  

	 
 </script>


<form:form id="contractorForm"  method="POST" modelAttribute="contractorVO" autocomplete="off">
	<input type="hidden" name="cont_idx" value="${contractorVO.cont_idx}" />
	<input type="hidden" name="user_idx" value="${contractorVO.user_idx}"/>
	<input type="hidden" id="isPWChanged" name="isPWChanged" value="false" />
	<input type="hidden" name="level" value="4" />
	<!-- 다른 site_idx 가진 유저가 접근하여 수정시 해당 유저의 site_idx로 수정되는 경우 방지 -->
	<c:if test="${!updateMode}">
		<input type="hidden" name="site_idx" value="${sessionScope.siteVO.site_idx}">
	</c:if>
	<c:if test="${updateMode}">
		<form:input type="hidden" path="site_idx"/>
	</c:if>
		
<!-- //srchbox -->
<table class="user_signup">
	<colgroup>
		<col style="width: 25%">
		<col style="width: 25%">
		<col style="width: 25%">
		<col>
	</colgroup>

	<tr>
		<th>현장</th>
		<td colspan="3">
			${sessionScope.siteVO.sitename}
		</td>
	</tr>
	<tr>
		<th>업체명</th>
		<td><form:input path="cont_name" maxlength="45"/><p/><form:errors cssClass="formError" path="cont_name" /></td>
		<th>업체 연락처<p><span style="font-size:17px">(010-1234-5678)</span></th>
		<td><form:input path="cont_phone" maxlength="13" onblur="checkPhone(this, this.value)"/><p/><form:errors cssClass="formError" path="cont_phone" /></td>
	</tr>
	<tr>
		<th>대표관리자</th>
		<td><form:input path="rep_name" maxlength="45"/><p/><form:errors cssClass="formError" path="rep_name" />
		</td>
		<th>연락처<p><span style="font-size:17px">(010-1234-5678)</span></th>
		<td><form:input path="rep_phone" maxlength="13" onblur="checkPhone(this, this.value)"/><p/><form:errors cssClass="formError" path="rep_phone" />
		</tr>
	<tr>
		<th>해당작업</th>
		<td colspan="3"><form:input path="cont_work" maxlength="45"/><p/><form:errors cssClass="formError" path="cont_work" /></td>
	</tr>
	<tr>
		<th>ID</th>
		<!-- insert -->
		<c:if test="${!updateMode}">
			<td colspan="2">
			<form:input id="input_id" path="id" maxlength="45" />
				<p/><form:errors id="idError" cssClass="formError" path="id" />
			</td>
			<td><span class="btn_typ01 duplCheck"  onclick="duplicateIdCheck()">중복체크</span>
				<span class="duplCheckOk" style="display:none">사용 가능</span>
			</td>
		</c:if>
		
		<!--  update -->
		<c:if test="${updateMode}">
			<td colspan="3">
			<form:input id="input_id" path="id" maxlength="45" readonly="true" />
			</td>
		</c:if>
			
		
	</tr>
	<tr>
		<th>PW</th>
		<td colspan="3">					
			<form:password id="input_password" path="password" maxlength="45"/><p/><form:errors cssClass="formError" path="password" />
			<div id="btnNewPasswd" onclick="newPassword()" class="btn_typ01" style="width:170px">신규 비밀번호 발급</div> 						
		</td>
	</tr>
		
		
</table>

<div class="paging">
<!--  insert -->
<c:if test="${!updateMode}">
<span class="signup"><span class="btn_typ02"  onclick="submitContractor()">등록</span></span>
</c:if>

<!-- update -->
<c:if test="${updateMode}">
	<span class="signup"><span class="btn_typ02"  onclick="submitContractor()">수정</span></span>
</c:if>

	
</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>