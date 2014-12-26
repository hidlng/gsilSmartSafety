<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <script>
 var idNotDuplicate = false;
 $(document).ready(function() {
	 var level = ${managerVO.level};
	 if( level <= 0 )//default val
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
		if(val == 1) {//본사
			 $('#siteTR').hide();
			 /* $('<input>').attr({
				    type: 'hidden',
				    name: 'site_idx',
				    value: 'NONE'
				}).appendTo('#managerForm'); */
		     $('#site_idx').val('');//make empty value
		}else {//현장
			$('#siteTR').show();
			if(${updateMode} == false) {//insert mode 일때
				$("#site_selectBox option:eq(0)").attr("selected", "selected");
			}
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
		  alert('ID중복체크를 해주시기 바랍니다(ID Check)');
		  }  
	}else
		return;
  }
  
  
  


  //$(document).on("click", "#managerList", function(e) {
	 
 </script>


	<form:form id="managerForm"  method="POST" modelAttribute="managerVO" autocomplete="off">
		<input type="hidden" name="manager_idx" value="${managerVO.manager_idx}" />
		<input type="hidden" name="user_idx" value="${managerVO.user_idx}"/>
		<input type="hidden" id="isPWChanged" name="isPWChanged" value="false" />
		<input type="hidden" name="cont_name" value="_">
		<input type="hidden" name="cont_work" value="_">
<!-- //srchbox -->
<table class="user_signup">
	<colgroup>
		<col style="width: 25%">
		<col style="width: 25%">
		<col style="width: 25%">
		<col>
	</colgroup>

	<tr>
		<th>권한</th>
		<td>
			<c:if test="${!updateMode}">
				<form:select class="selectBox"  path="level" onchange="chgMLevel(this.value)">
						<form:option value="1" selected="selected">본사 관리자(EHS팀)</form:option>
						<form:option value="2">현장 안전관리자</form:option>
				</form:select>
			</c:if>
			<c:if test="${updateMode}">
				<form:input path="level" readonly="true"/>
			</c:if>
		</td>
		<th>직급</th>
		<td colspan="3"><form:input path="grade" maxlength="45"/><p/><form:errors cssClass="formError" path="grade" /></td>
	</tr>
	
	<tr id="siteTR">
		<th>현장</th>
		<td colspan="3">
		<form:select id="site_selectBox" path="site_idx" class="siteSelectBox" >
			<c:forEach var="site" items="${siteList}" varStatus="idx">
					<form:option value="${site.site_idx}">${site.sitename}</form:option>
			</c:forEach>
		</form:select>
		</td>
		
	</tr>
	
	<tr>
		<th>성명</th>
		<td><form:input path="name" maxlength="45"/><p/><form:errors cssClass="formError" path="name" />
		</td>
		<th>생년월일</th>
		<td><form:input id="birthInput" path="birth" maxlength="10" /><p/><form:errors cssClass="formError" path="birth" /></td>
	</tr>
	<tr>
		<th>연락처<p/><span style="font-size:17px">(010-1234-5678)</span></th>
		<td><form:input path="phone" maxlength="13" onblur="checkPhone(this, this.value)"/><p/><form:errors cssClass="formError" path="phone" /></td>
		<th>소속</th>
		<td><form:input path="position" maxlength="45"/><p/><form:errors cssClass="formError" path="position" /></td>
	</tr>
	<tr>
		<th>ID</th>
		<!-- insert -->
		<c:if test="${!updateMode}">
			<td colspan="2">
			<form:input id="input_id" path="id" maxlength="45" />
				<p/><form:errors id="idError" cssClass="formError" path="id" />
			</td>
			<td><input type="button" class="duplCheck" value="중복체크" onclick="duplicateIdCheck()">
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
			<input id="btnNewPasswd" type="button" value="신규 비밀번호 발급" onclick="newPassword()" style="cursor:pointer">							
		</td>
											
	</tr>
		
	
</table>

<div class="paging">
<!--  insert -->
<c:if test="${!updateMode}">
<span class="signup" ><img src="images/btn_signup.png" class="signupImg"  alt="등록하기" onclick="submitManager()" 
onmouseover="this.src='images/btn_signup_over.png'"   onmouseout="this.src='images/btn_signup.png'" style="cursor:pointer" ></span>
</c:if>

<!-- update -->
<c:if test="${updateMode}">
	<span class="signup" ><img src="images/btn_info.png" alt="수정하기" onclick="submitManager()" style="cursor:pointer"
	onmouseover="this.src='images/btn_info_over.png'"   onmouseout="this.src='images/btn_info.png'" ></span>
</c:if>

	
</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>