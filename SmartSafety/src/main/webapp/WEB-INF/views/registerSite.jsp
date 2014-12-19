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
			// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
			//document.form.addr_detail.value = roadFullAddr;
			$('#addr_detail').val(roadFullAddr);
			
			/* document.form.roadFullAddr.value = roadFullAddr;
			document.form.roadAddrPart1.value = roadAddrPart1;
			document.form.roadAddrPart2.value = roadAddrPart2;
			document.form.addrDetail.value = addrDetail;
			document.form.engAddr.value = engAddr;
			document.form.jibunAddr.value = jibunAddr;
			document.form.zipNo.value = zipNo;
			document.form.admCd.value = admCd;
			document.form.rnMgtSn.value = rnMgtSn;
			document.form.bdMgtSn.value = bdMgtSn; */
	}

 
 </script>


<form:form id="siteForm" method="POST" modelAttribute="siteVO"
	autocomplete="off">
	<form:input type="hidden" path="site_idx" value="${siteVO.site_idx}" />

	<!-- //srchbox -->
	<table class="user_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>

		<tr>
			<th>현장명</th>
			<td colspan="3"><form:input path="sitename" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="sitename" /></td>
		</tr>
		<tr>
			<th>주소</th>
			<td colspan="3"><form:input id="addr_detail" path="addr_detail" maxlength="255"  readonly="true" style="width:90%" onfocus="goPopup()"/>
				<p />
				<form:errors cssClass="formError" path="addr_detail" /></td>
		</tr>
		<tr>
			<th>대표관리자</th>
			<td><form:input path="rep_name" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="rep_name" /></td>
			<th>연락처
				<p />
				<span style="font-size: 17px">(010-1234-5678)</span>
			</th>
			<td><form:input path="rep_phone" maxlength="13" />
				<p />
				<form:errors class="formError" path="rep_phone" /></td>
		</tr>
		<tr>
			<th>작업 기간(시작)</th>
			<td><form:input id="startDateInput" path="starttime"
					maxlength="10" />
				<p />
				<form:errors cssClass="formError" path="starttime" /></td>
			<th>작업 기간(종료)</th>
			<td><form:input id="endDateInput" path="endtime" maxlength="10" />
				<p />
				<form:errors cssClass="formError" path="endtime" /></td>
		</tr>
		<tr>
			<th>관련업체</th>
			<td><form:input path="contractor" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="contractor" />
			<th>공종</th>
			<td><form:input path="worktype" maxlength="45" />
				<p />
				<form:errors cssClass="formError" path="worktype" />
		</tr>
	</table>

	<div class="paging">
		<!--  insert -->
		<c:if test="${!updateMode}">
			<span class="signup"><img src="images/btn_signup.png"
				class="signupImg" alt="등록하기" onclick="submitSite()"
				onmouseover="this.src='images/btn_signup_over.png'"
				onmouseout="this.src='images/btn_signup.png'"
				style="cursor: pointer"></span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="signup"><img src="images/btn_info.png"
				alt="수정하기" onclick="submitSite()" style="cursor: pointer"
				onmouseover="this.src='images/btn_info_over.png'"
				onmouseout="this.src='images/btn_info.png'"></span>
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>