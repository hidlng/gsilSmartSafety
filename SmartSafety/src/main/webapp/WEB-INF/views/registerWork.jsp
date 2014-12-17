<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>

 
 var idNotDuplicate = false;
 $(document).ready(function() {	
	
	 if(${updateMode} == true) {	
		 $('#workForm').attr('action', 'updateWork');
	 }
	 /**insertmode**/
	 else{		
		 $('#workForm').attr('action', 'insertWork');
	 }
	

 });
 
 
  function submitWork() {	
		$('#workForm').submit();
  }

 
 </script>


<form:form id="workForm" method="POST" modelAttribute="workVO"
	autocomplete="off">
	<!--다른사용자의update동작을 고려하여 자동으로 입력되는 부분 추가시 주의-->
	<input type="hidden" name="site_idx" value="${workVO.site_idx}" />
	<input type="hidden" name="work_idx" value="${workVO.work_idx}" />
	<input type="hidden" name="write_user_idx" value="${workVO.write_user_idx}" />
	
	
	<table class="user_signup">
		<colgroup>
			<col>
			<col style="width: 33%">
			<col style="width: 33%">
		</colgroup>
		<tr>
			<th>관련업체</th>
			<td colspan="2"><form:input path="cont_name"  readonly="true" />
			</td>
		</tr>
		<tr>
			<th>			
			<select id="author_typ" name="worktype">
					<option value="" selected="selected">공종</option>
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
			</select></th>
			<th><select id="author_typ" name="author_typ">
					<option value="" selected="selected">하부작업</option>
					<option value="">1</option>
					<option value="">2</option>
					<option value="">3</option>
			</select></th>
			<th>
				<form:select path="workcode" class="siteSelectBox">
					<form:option value="TESTCODE01">TESTCODE01</form:option>
				</form:select> 
			</th>
		</tr>

		<tr>
			<th>작업타이틀</th>
			<td colspan="2"><form:input path="worktitle" 	maxlength="30" />
							<p /> <form:errors path="worktitle" cssClass="formError"  /></td>
		</tr>
		<tr>
			<th>작업 유형</th>
			<td colspan="2">
				<span>
				<form:radiobutton path="ischarge" class="radi"  value="N" />신규작업			
				<form:radiobutton path="ischarge" class="radi" value="Y"/><label style="color:red">돌관작업</label>				
				</span>
				<p><form:errors path="ischarge" cssClass="formError"/>
			</td>
		</tr>
		<tr>
			<th>작업기간</th>	
			<td>시작 : <form:input id="startDateInput" path="startdate" 	maxlength="10" readonly="true"/>
				<p /> <form:errors cssClass="formError" path="startdate" /></td>
			<td>마감 : <form:input id="endDateInput" path="enddate" maxlength="10" readonly="true"/>
				<p /><form:errors cssClass="formError" path="enddate" /></td>
		</tr>

		<tr>
			<th>작업시간</th>
			<td>시작 : <form:input path="starttime" id="starttimeInput" class="selectBox" autocomplete="off" readonly="true" ></form:input> </td>
			<td>마감 : <form:input path="endtime" id="endtimeInput" class="time ui-timepicker-input" autocomplete="off" readonly="true" ></form:input> </td>
		</tr>

	</table>
	<!--  //user_signup -->

	<p class="red">장비선택</p>
	<ul class="equipment_typ">
		<li><span class="iconImg"><img
				src="images/icon_equipment01.png" alt="건설장비"><br />&nbsp;&nbsp;건설장비</span></li>
		<li class="on"><span class="iconImg"><img
				src="images/icon_equipment02.png" alt="운반장비"><br />&nbsp;운반장비</span></li>
		<li><span class="iconImg"><img
				src="images/icon_equipment03.png" alt="기타장비"><br />&nbsp;기타장비</span></li>
	</ul>
	<table class="user_signup equipment">
		<colgroup>
			<col style="width: 33.3%">
			<col style="width: 33.3%">
			<col>
		</colgroup>
		<tr>
			<th>장비</th>
			<td><input type="text" name="" value="">톤
			</th>
			<td><input type="text" name="" value="">모델
			</th>
		</tr>
		<tr>
			<th>굴삭기</th>
			<td colspan="2"><input type="text" name="" value=""></td>
		</tr>
		<tr>
			<th>지게차</th>
			<td colspan="2"><input type="text" name="" value=""></td>
		</tr>
	</table>
	<!--  //user_signup -->

	<p class="red">공도구선택</p>
	<table class="user_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>
		<tr>
			<th><span class="iconImg"><img
					src="images/icon_tool01.png" alt="용접기"></span><br />용접기</th>
			<th><span class="iconImg"><img
					src="images/icon_tool02.png" alt="전동도구"></span><br />전동도구</th>
			<th><span class="iconImg"><img
					src="images/icon_tool03.png" alt="비전동도구"></span><br />비전동도구</th>
			<th>기타</th>
		</tr>
		<tr>
			<td><input type="text" name="" value=""></td>
			<td><input type="text" name="" value=""></td>
			<td><input type="text" name="" value=""></td>
			<td><input type="text" name="" value=""></td>
		</tr>
	</table>
	<!--  //user_signup -->

	<p class="red">작업장소등록</p>
	<table class="user_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>
		<tr>
			<th>장소유형</th>
			<td colspan="3">
			<form:select path="placecode" class="siteSelectBox">
				<form:option value="PLACECODE01">PLACECODE01</form:option>
				<form:option value="PLACECODE02">PLACECODE01</form:option>
			</form:select></td>
		</tr>
		<tr>
			<th>세부장소</th>
			<td colspan="3">
				<form:input path="addr_detail" 	maxlength="255" />
				<p /> <form:errors path="addr_detail" cssClass="formError"  />
			</td>
		</tr>
		<tr>
			<th>실내/외 여부</th>
			<td colspan="3"><span class="side">
				<form:radiobutton path="indoor" class="radi"  value="Y" />실내				
				<form:radiobutton path="indoor" class="radi" value="N"/>실외</span>
			</td>
		</tr>
	</table>
	<!--  //user_signup -->

	<div class="work_cap">
		<p class="red">작업책임자</p>
		<table class="work_cap">
			<colgroup>
				<col style="width: 25%">
				<col style="width: 25%">
				<col style="width: 25%">
				<col>
			</colgroup>
			<tr>
				<th>성명</th>
				<td><form:input path="pic_name" 	maxlength="45" />
					<p /> <form:errors path="pic_name" cssClass="formError"  /></td>
				<th>생년월일</th>
				<td><form:input id="birthInput" path="pic_birth" 	maxlength="10" readonly="true" />
					<p /> <form:errors path="pic_birth" cssClass="formError"  />
				</td>
			</tr>
			<tr>
				<th>연락처</th>
				<td><form:input path="pic_phone" 	maxlength="13" />
				<p /> <form:errors path="pic_phone" cssClass="formError"  /></td>
				<th>소속</th>
				<td><form:input path="pic_position" 	maxlength="45" />
				<p /> <form:errors path="pic_position" cssClass="formError"  /></td>
			</tr>
			<tr>
				<th>작업자 수</th>
				<td colspan="3">
					<form:select path="pic_num_worker" class="selectBox" >
					<c:forEach begin="1" end="50" varStatus="idx">
							<form:option value="${idx.count}">${idx.count}</form:option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<th rowspan="2">작업상황<br />난이도
				</th>
				<td colspan="3">
				<form:select path="worklevel" class="selectBox" >
					<c:forEach begin="1" end="4" varStatus="idx">
							<form:option value="${idx.count}">${idx.count}</form:option>
					</c:forEach>
				</form:select>
				</td>
			</tr>

			<tr>
			<td colspan="3">
			난이도 1 : ㅇㄹㅇㄹㅇㄹ<p>
			난이도 2: ㄴㅇㄹㅇㄹㅇㄹ<p>
			난이도 3: ㅇㄹㅇㄹ
			</td>
			</tr>

		</table>
	</div>

	<p class="red">특이사항</p>
	<table>
		<tr>
			<td><form:textarea path="remark" 	maxlength="600" />
			<p /> <form:errors path="remark" cssClass="formError"  /></td>
		</tr>
	</table>

	<div class="paging">
			<!--  insert -->
		<c:if test="${!updateMode}">
			<span class="signup"><img src="images/btn_signup.png"
				class="signupImg" alt="등록하기" onclick="submitWork()"
				onmouseover="this.src='images/btn_signup_over.png'"
				onmouseout="this.src='images/btn_signup.png'"
				style="cursor: pointer"></span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="signup"><img src="images/btn_info.png"
				alt="수정하기" onclick="submitWork()" style="cursor: pointer"
				onmouseover="this.src='images/btn_info_over.png'"
				onmouseout="this.src='images/btn_info.png'"></span>
		</c:if>
	</div>
	<p class="goTop">
		<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a>
	</p>



</form:form>

<%@ include file="IncludeBottom.jsp"%>