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
	<input type="hidden" name="ismanager" value="0" />
	<input type="hidden" id="isPWChanged" name="isPWChanged" value="false" />
	
	
	
	<!-- null값 방지 위함 -->
	<input type="hidden" name="grade" value="_">
	<input type="hidden" name="position" value="_">

	<!-- siteIdx 관리  -->
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
		<tr>
			<th>소속현장</th>
			<td colspan="3">${sessionScope.siteVO.sitename}</td>
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
			<td><form:input path="phone" maxlength="13" onblur="checkPhone(this, this.value)"/>
				<p />
				<form:errors cssClass="formError" path="phone" />
			</td>
			<th>권한	</th>
			<td>
				<form:select class="selectBox"  path="level">
						<form:option value="4" selected="selected">소장</form:option>
						<form:option value="5">작업 팀장</form:option>
						<form:option value="6">일반 작업자</form:option>
				</form:select>
			</td>
		</tr>
		
		<tr>
			<th>ID</th>
			<!-- insert -->
			<c:if test="${!updateMode}">
				<td colspan="2"><form:input id="input_id" path="id"
						maxlength="45" />
					<p />
					<form:errors id="idError" cssClass="formError" path="id" /></td>
				<td><span class="btn_typ01 duplCheck"  onclick="duplicateIdCheck()">중복체크</span>
					 <span class="duplCheckOk"	style="display: none">사용 가능</span></td>
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
				<form:errors cssClass="formError" path="password" /> 
				<div id="btnNewPasswd" onclick="newPassword()" class="btn_typ01" style="width:170px">신규 비밀번호 발급</div> 
			</td>

		</tr>


	</table>

	<div class="paging">
		<!--  insert -->
		<c:if test="${!updateMode}">
			<span class="signup"><span class="btn_typ02"  onclick="submitSiteUser()">등록</span></span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="signup"><span class="btn_typ02"  onclick="submitSiteUser()">수정</span></span>
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>