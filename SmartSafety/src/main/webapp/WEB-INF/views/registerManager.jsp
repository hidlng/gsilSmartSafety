<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <script>
 var idNotDuplicate = false;
 $(document).ready(function() {
	 var level = '${managerVO.level}'; //
	
	 if( level == "" || level == null || level <= 0 )//default value는 본사로 지정
		 level = 1
	 chgMLevel(level);
	  
	
	 /**updateMode에선 ID중복체크하지 않음**/
	 if(${updateMode} == true) {
		 idNotDuplicate = true;	
		 $('#btnNewPasswd').show();
		 $('#input_password').hide();
		 $('#input_password').val('!@#$%^&&!@#$');
		 $('#managerForm').attr('action', 'updateManager');
	 }
	 /**insertmode**/
	 else{
		 idNotDuplicate = false;
		 $('#btnNewPasswd').hide();
		 $('#input_password').show();
		
		 $('#managerForm').attr('action', 'insertManager');
	 }
	 
		 
 });
 
 function chgMLevel(val) {
		if(val == 2) {//현장
			$('#siteTR').show();
			if(${updateMode} == false) {//insert mode 일때
				$("#site_selectBox option:eq(0)").attr("selected", "selected");
			}
		}else {//본사,CEO(1,3)
			 $('#siteTR').hide();			
		     $('#site_idx').val('NONE');//make empty value
		}
}

 function deleteManager() {	
		input = confirm('삭제하시겠습니까?');
		if(input){
				$('#managerForm').attr('action', 'deleteManager');
				$('#managerForm').submit();
		}
 }
  function submitManager() {
	var input;
	if(${updateMode} == true) {
		input = confirm('수정하시겠습니까?');
	}else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) { //yes
	  if(idNotDuplicate){
			$('#managerForm').submit();
	 }else {
		  alert('아이디 중복체크를 해주시기 바랍니다');
		  }  
	}else
		return;
  }
  
  
  


  //$(document).on("click", "#managerList", function(e) {
	 
 </script>


	<form:form id="managerForm"  method="POST" modelAttribute="managerVO" autocomplete="off">
		<input type="hidden" name="manager_idx" value="${managerVO.manager_idx}" />
		<input type="hidden" name="user_idx" value="${managerVO.user_idx}"/>
		<input type="hidden" name="ismanager" value="1" />
		<input type="hidden" id="isPWChanged" name="isPWChanged" value="false" />
		<input type="hidden" id="cont_idx" name="cont_idx" value="NONE" />
<!-- //srchbox -->
<table class="user_signup">
	<colgroup>
		<col style="width: 20%">
		<col style="width: 30%">
		<col style="width: 20%">
		<col>
	</colgroup>

	<tr>
		<th>권&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한</th>
		<td colspan="3">
			<c:if test="${!updateMode}">
				<form:select class="selectBox"  path="level" onchange="chgMLevel(this.value)" style="width:94%;">
						<form:option value="1" selected="selected">본사 관리자</form:option>
						<form:option value="2">현장 안전관리자</form:option>
						<form:option value="3">CEO</form:option>
				</form:select>
			</c:if>
			<c:if test="${updateMode}">
				<input type="hidden" name="level" value="${managerVO.level}"/>
				<c:if test="${managerVO.level == 1}">
					본사 관리자
				</c:if>
				<c:if test="${managerVO.level == 2}">
					현장 안전관리자
				</c:if>
				<c:if test="${managerVO.level == 3}">
					CEO
				</c:if>
			</c:if>
		</td>
		
	</tr>
	
	<tr id="siteTR">
		<th>현&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</th>
		<td colspan="3">
		<form:select id="site_selectBox" path="site_idx" class="siteSelectBox" style="width:94%" >
			<c:forEach var="site" items="${siteList}" varStatus="idx">
					<form:option value="${site.site_idx}">${site.sitename}</form:option>
			</c:forEach>
		</form:select>
		</td>
		
	</tr>
	
	<tr>
		<th>직&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</th>
		<td><form:input path="grade" maxlength="45"/><br><form:errors cssClass="formError" path="grade" /></td>
		<th>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</th>
		<td><form:input path="name" maxlength="45"/><br><form:errors cssClass="formError" path="name" />
		</td>
		
	</tr>
	<tr>
		<th>생년월일</th>
		<td><form:input id="birthInput" path="birth" maxlength="10" /><br><form:errors cssClass="formError" path="birth" /></td>
		<th>연&nbsp;락&nbsp;처<br><span style="font-size:20px">(010-1234-5678)</span></th>
		<td><form:input path="phone" maxlength="13" onblur="checkPhone(this, this.value)"/><br><form:errors cssClass="formError" path="phone" /></td>
	</tr>
	<tr>
		<th>소&nbsp;속&nbsp;사</th>
		<td colspan="3"><form:input path="position" class="colspanInput" maxlength="45"/><br><form:errors cssClass="formError" path="position" /></td>
	</tr>
	<tr>
		<th>아&nbsp;이&nbsp;디</th>
		<!-- insert -->
		<c:if test="${!updateMode}">
			<td colspan="2">
			<form:input id="input_id" path="id"  maxlength="45" style="width:88%" />
				<br><form:errors id="idError" cssClass="formError" path="id" />
			</td>
			<td><span class="btn_typ01 duplCheck"  onclick="duplicateIdCheck()">중복체크</span>
				<span class="duplCheckOk" style="display:none">사용 가능</span>
			</td>
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
		<td colspan="3">
			<form:password id="input_password" path="password"  maxlength="45" style="width:93%;"/><p><form:errors cssClass="formError" path="password" />
			<div id="btnNewPasswd" onclick="newPassword()" class="btn_typ01 btnNewPasswd" >신규 비밀번호 발급</div>
		</td>					
	</tr>
		
	
</table>

<div class="paging">
<!--  insert -->
<c:if test="${!updateMode}">
<span class="signup"><span class="btn_typ02"  onclick="submitManager()">등록</span></span>
</c:if>

<!-- update -->
<c:if test="${updateMode}">
	
		<span class="btn_typ02"  onclick="submitManager()">수정</span>
		<span class="btn_typ02"  onclick="deleteManager()">삭제 </span>
	
</c:if>

	
</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>