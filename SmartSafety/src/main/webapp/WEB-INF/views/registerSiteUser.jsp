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
		 $('#siteUserForm').attr('action', 'updateSiteUser');
	 }
	 /**insertmode**/
	 else{
		 idNotDuplicate = false;
		 $('#btnNewPasswd').hide();
		 $('#input_password').show();		
		 $('#siteUserForm').attr('action', 'insertSiteUser');
	 }
	 
	 /**calendar**/
	 $(function() {
		    $( "#birthInput" ).datepick({
		    	yearRange: '1960:2000',
		    	 defaultDate: new Date(1985, 00, 01)
		    }).attr('readonly','readonly');     
	});
		 
 });
 

 
 
 function submitSiteUser(val) {
	 var input;
		if(${updateMode} == true) {
			input = confirm('수정하시겠습니까?');
		}else {
			input = confirm('등록하시겠습니까?');
		}
		
		if(input) { //yes
			  if(idNotDuplicate){
				$('#siteUserForm').submit();
			  }else {
				  alert('ID중복체크를 해주시기 바랍니다(ID Check)');
			  }  
		}else
			return;
 }
  
 </script>


<form:form id="siteUserForm" method="POST" modelAttribute="managerVO"
	autocomplete="off">
	<input type="hidden" name="manager_idx"
		value="${managerVO.manager_idx}" />
	<input type="hidden" name="user_idx" value="${managerVO.user_idx}" />
	<input type="hidden" id="isPWChanged" name="isPWChanged" value="false" />
	<input type="hidden" name="level" value="3">
	
	<!-- null값 방지 위함 -->
	<input type="hidden" name="grade" value="_">
	<input type="hidden" name="position" value="_">

	<!-- siteIDx 관리  -->
	<c:if test="${!updateMode}">
		<input type="hidden" name="site_idx"
			value="${sessionScope.siteVO.site_idx}">
	</c:if>
	<c:if test="${updateMode}">
		<form:input type="hidden" path="site_idx" />
	</c:if>

	<!-- //srchbox -->
	<table class="user_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>
		<c:if test="${!updateMode}">
			<tr>
				<th>현장</th>
				<td colspan="3">${sessionScope.siteVO.sitename}</td>
			</tr>
		</c:if>
		<tr>
			<th>업체명</th>
			<td colspan="3"><form:input path="cont_name" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="cont_name" /></td>
		</tr>
		<tr>
			<th>성명</th>
			<td><form:input path="name" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="name" /></td>
			<th>생년월일
				<p />
				<span style="font-size: 17px">(YYYMMDD)</span>
			</th>
			<td><form:input id="birthInput" path="birth" maxlength="10" />
				<p />
				<span id="birthError" class="formError"></span></td>
		</tr>
		<tr>
			<th>연락처
				<p />
				<span style="font-size: 17px">(010-1234-5678)</span>
			</th>
			<td colspan="3"><form:input path="phone" maxlength="13" />
				<p />
				<form:errors cssClass="formError" path="phone" /></td>
		</tr>
		<tr>
			<th>해당작업</th>
			<td colspan="3"><form:input path="cont_work" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="cont_work" /></td>
		</tr>
		<tr>
			<th>ID</th>
			<!-- insert -->
			<c:if test="${!updateMode}">
				<td colspan="2"><form:input id="input_id" path="id"
						maxlength="45" />
					<p />
					<form:errors id="idError" cssClass="formError" path="id" /></td>
				<td><input type="button" class="duplCheck" value="중복체크"
					onclick="duplicateIdCheck()"> <span class="duplCheckOk"
					style="display: none">사용 가능</span></td>
			</c:if>

			<!--  update -->
			<c:if test="${updateMode}">
				<td colspan="3"><form:input id="input_id" path="id"
						maxlength="45" readonly="true" /></td>
			</c:if>


		</tr>
		<tr>
			<th>PW</th>
			<td colspan="3"><form:password id="input_password"
					path="password" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="password" /> <input
				id="btnNewPasswd" type="button" value="신규 비밀번호 발급"
				onclick="newPassword()" style="cursor: pointer"></td>

		</tr>


	</table>

	<div class="paging">
		<!--  insert -->
		<c:if test="${!updateMode}">
			<span class="signup"><img src="images/btn_signup.png"
				class="signupImg" alt="등록하기" onclick="submitSiteUser()"
				onmouseover="this.src='images/btn_signup_over.png'"
				onmouseout="this.src='images/btn_signup.png'"></span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="signup"><img src="images/btn_info.png"
				alt="수정하기" onclick="submitSiteUser()"
				style="cursor: pointer"
				onmouseover="this.src='images/btn_info_over.png'"
				onmouseout="this.src='images/btn_info.png'"></span>
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>