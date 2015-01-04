<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
 var idNotDuplicate = false;
 $(document).ready(function() {	
	
	 if(${updateMode} == true) {	
		 $('#siteForm').attr('action', 'updateSite');
	 }
	 /**insertmode**/
	 else{		
		 $('#siteForm').attr('action', 'insertSite');
	 }

	
 });
/*  function deleteSite() {	
	input = confirm('삭제하시겠습니까?');
	if(input){
		$('#siteForm').attr('action', 'deleteSite');
		$('#siteForm').submit();
	}
 } */
 
  function submitSite() {	
	  var input;
		if(${updateMode} == true) {
			input = confirm('수정하시겠습니까?');
		}else {
			input = confirm('등록하시겠습니까?');
		}
		
		if(input) { //yes
			$('#siteForm').submit();
		}else
			return;
  }

  
  function goPopup(){
		// 주소검색을 수행할 팝업 페이지를 호출합니다.
		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
		
		var pop = window.open("jusoPopup","pop","width=570,height=420, scrollbars=yes, resizable=yes, modal=yes"); 
	}


	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
			$('#addr_detail').val(roadFullAddr);		
	}

 
 </script>


<form:form id="siteForm" method="POST" modelAttribute="siteVO"
	autocomplete="off">
	<form:input type="hidden" path="site_idx" value="${siteVO.site_idx}" />

	<!-- //srchbox -->
	<table class="user_signup">
		<colgroup>
			<col style="width: 20%">
			<col style="width: 30%">
			<col style="width: 20%">
			<col>
		</colgroup>

		<tr>
			<th>현장명</th>
			<td colspan="3"><form:input path="sitename" maxlength="45" />
				<br>
				<form:errors cssClass="formError" path="sitename" /></td>
		</tr>
		<tr>
			<th>주소</th>
			<td colspan="3"><form:input id="addr_detail" path="addr_detail" maxlength="255"  readonly="true" style="width:90%" onclick="goPopup()"/>
				<br>
				<form:errors cssClass="formError" path="addr_detail" /></td>
		</tr>
		<tr>
			<th>대표관리자</th>
			<td><form:input path="rep_name" maxlength="45" />
				<br>
				<form:errors cssClass="formError" path="rep_name" /></td>
			<th>연락처
				<br>
				<span style="font-size: 17px">(010-1234-5678)</span>
			</th>
			<td><form:input path="rep_phone" maxlength="13" onblur="checkPhone(this, this.value)"/>
				<br>
				<form:errors class="formError" path="rep_phone" /></td>
		</tr>
		<tr>
			<th>작업 기간<br>(시작)</th>
			<td><form:input id="startDateInput" path="starttime"
					maxlength="10" />
				<br>
				<form:errors cssClass="formError" path="starttime" /></td>
			<th>작업 기간<br>(종료)</th>
			<td><form:input id="endDateInput" path="endtime" maxlength="10" />
				<br>
				<form:errors cssClass="formError" path="endtime" /></td>
		</tr>
	</table>

	<div class="paging">
		<!--  insert -->
		<c:if test="${!updateMode}">
			<span id="popupOKBtn" class="btn_typ02"  onclick="submitSite()">등록 </span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span id="popupOKBtn" class="btn_typ02"  onclick="submitSite()">수정 </span>
			<!--span id="popupOKBtn" class="btn_typ02"  onclick="deleteSite()">삭제 </span-->
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>