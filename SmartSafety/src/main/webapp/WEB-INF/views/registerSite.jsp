<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=ture_or_false"></script>
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

 function deleteSite() {	
	input = confirm('삭제하시겠습니까?');
	if(input){		
		input_2 = confirm('현장 삭제시 관련된 사용자, 작업내용이 삭제됩니다. 진행하시겠습니까?');
		if(input_2){
			$('#siteForm').attr('action', 'deleteSite');
			$('#siteForm').submit();
		}
	}
 }
 
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
			
			//segun . 날씨 검색위한 위도 경도 저장
			 var geocoder = new google.maps.Geocoder();
			 var addr=roadFullAddr; //
			 addr = addr.substring( 0, addr.indexOf(",") );
			 var lat="";
			 var lng="";
			 geocoder.geocode({'address':addr},
			 function(results, status){
				 if(results!=""){
				    var location=results[0].geometry.location;
				    lat=location.lat();
				    lng=location.lng(); 
				    
				    $('#lat').val(lat);
				    $('#lng').val(lng);
				   // alert(lat);
				 } 
			 })

	} 
 </script>

		
<form:form id="siteForm" method="POST" modelAttribute="siteVO"
	autocomplete="off">
	<form:input type="hidden" path="site_idx" value="${siteVO.site_idx}" />
	<form:input type="hidden" id="lat" path="lat" value="${siteVO.lat}" />
	<form:input type="hidden" id="lng" path="lng" value="${siteVO.lng}" />

	<!-- //srchbox -->
	<table class="user_signup">
		<colgroup>
			<col style="width: 20%">
			<col style="width: 30%">
			<col style="width: 20%">
			<col>
		</colgroup>

		<tr>
			<th>구&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;분</th>
			<td colspan="3" style="text-align:center">
			<form:radiobutton id="type_1" path="type" class="radi"  value="0" style="cursor:pointer"/> <label for="type_1" style="cursor:pointer">건축</label>		
			<form:radiobutton id="type_2" path="type" class="radi" value="1" style="cursor:pointer"/><label for="type_2" style="cursor:pointer">토목</label>		
			</td>
		</tr>
		<tr>
			<th>현&nbsp;&nbsp;장&nbsp;&nbsp;명</th>
			<td colspan="3"><form:input path="sitename" class="colspanInput" maxlength="45" />
				<br>
				<form:errors cssClass="formError" path="sitename" /></td>
		</tr>
		<tr>
			<th>주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</th>
			<td class="listTR" colspan="3"><form:textarea id="addr_detail" path="addr_detail" class="colspanInput" maxlength="255"  readonly="true" rows="2" style="font-family:inherit;border:1px;border: 2px solid #1D4F99;width:90%;cursor:pointer;height:60px;overflow:hidden;" onclick="goPopup()" />
				<br>
				<form:errors cssClass="formError" path="addr_detail" /></td>
		</tr>
	
		<tr>
			<th>공사 기간<br>(시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;작)</th>
			<td><form:input id="startDateInput" path="starttime"
					maxlength="10" />
				<br>
				<form:errors cssClass="formError" path="starttime" /></td>
			<th>공사 기간<br>(종&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;료)</th>
			<td><form:input id="endDateInput" path="endtime" maxlength="10" />
				<br>
				<form:errors cssClass="formError" path="endtime" /></td>
		</tr>
	</table>

	<div class="paging">
		<!--  insert -->
		<c:if test="${!updateMode}">
			<span class="btn_typ02"  onclick="submitSite()">등록 </span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="btn_typ02"  onclick="submitSite()">수정 </span>&nbsp;&nbsp;&nbsp;
			<span class="btn_typ02"  onclick="deleteSite()">삭제 </span>
		</c:if>


	</div>
</form:form>

<%@ include file="IncludeBottom.jsp"%>