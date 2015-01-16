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
		 
	 
	 //현장소장이 이미 등록되어있는지에 따른 처리
	 var isRegisteredChief = ${isRegisteredChief};
	 if(isRegisteredChief) {
		 $('#chief_opt').hide();
		 $("#level_select_box option[value='4']").remove(); //4 - level 의미 달라질 경우 수정
	 }
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
				  alert('아이디 중복체크를 해주시기 바랍니다');
			  }  
		}else
			return;
 }
  
 function deleteSiteUser() {	
		input = confirm('삭제하시겠습니까?');
		if(input){
				$('#siteUserForm').attr('action', 'deleteSiteUser');
				$('#siteUserForm').submit();
		}
}
 </script>

* 현장 소장은 현장 별 1명만 설정 가능합니다.

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
			<col style="width: 20%">
			<col style="width: 30%">
			<col style="width: 20%">
			<col>
		</colgroup>
		<tr>
			<th>소속현장</th>
			<td colspan="3">${sessionScope.siteVO.sitename}</td>
		</tr>
		<tr>
			<th>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</th>
			<td><form:input path="name" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="name" /></td>
			<th>생년월일</th>
			<td><form:input id="birthInput" path="birth" maxlength="10" />
				<p />
				<span id="birthError" class="formError"></span></td>
		</tr>
		<tr>
			<th>연&nbsp;락&nbsp;처
				<p />
				<span style="font-size: 20px">(010-1234-5678)</span>
			</th>
			<td><form:input path="phone" maxlength="13" onblur="checkPhone(this, this.value)"/>
				<p />
				<form:errors cssClass="formError" path="phone" />
			</td>
			<th>권&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한	</th>
			<c:if test="${!updateMode}"> 
			<td>
				<form:select id="level_select_box" class="selectBox"  path="level" style="width:84%">
						<form:option value="4" >현장소장</form:option>
						<form:option value="5" >팀장</form:option>
						<form:option value="6" selected="selected">감독자</form:option>
				</form:select>
			</td>
			</c:if>
			<c:if test="${updateMode}">
				<td>
					<form:hidden path="level" value="${managerVO.level}"/>
					<c:if test="${managerVO.level == 4}">현장소장</c:if>
					<c:if test="${managerVO.level == 5}">팀장</c:if>
					<c:if test="${managerVO.level == 6}">감독자</c:if>
				</td>
			</c:if>
		</tr>
		<tr>
			<th>소&nbsp;속&nbsp;사</th>
			<td colspan="3">
			<form:select id="cont_select_box" path="cont_idx" class="siteSelectBox colspanInput"  style="width:94%">
					<c:forEach var="cont" items="${contList}" >
						<form:option value="${cont.cont_idx}">${cont.cont_name}</form:option>
					</c:forEach>
			</form:select>
			</td>
		</tr>
		
		<tr>
			<th>아&nbsp;이&nbsp;디</th>
			<!-- insert -->
			<c:if test="${!updateMode}">
				<td colspan="2"><form:input id="input_id" path="id" maxlength="45" style="width:88%"/>
					<p />
					<form:errors id="idError" cssClass="formError" path="id" /></td>
				<td><span class="btn_typ01 duplCheck"  onclick="duplicateIdCheck()">중복체크</span>
					 <span class="duplCheckOk"	style="display: none">사용 가능</span></td>
			</c:if>

			<!--  update -->
			<c:if test="${updateMode}">
				<td colspan="3">
					<form:hidden path="id" value="${managerVO.id}"/>
					${managerVO.id}	
				</td>
			</c:if>


		</tr>
		<tr>
			<th>비밀번호</th>
			<td colspan="3"><form:password id="input_password" path="password" maxlength="45" style="width:93%;"/>
				<p />
				<form:errors cssClass="formError" path="password" /> 
				<div id="btnNewPasswd" onclick="newPassword()" class="btn_typ01 btnNewPasswd" >신규 비밀번호 발급</div> 
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
			<span class="btn_typ02"  onclick="submitSiteUser()">수정</span>
			<span class="btn_typ02"  onclick="deleteSiteUser()">삭제</span>
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>