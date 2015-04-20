<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
$(document).ready(function() {
	if(${hasNoContractor} == false){
		alert("소속 업체가 없습니다. 안전관리자에게 문의하시기 바랍니다.");
		 location.href="viewWork";
	}
	
	if (${isNotValid} == true) {
		alert("입력하지 않은 항목이 있습니다. 확인해주시기 바랍니다");
	}
	 
	 /**updatemode**/
	if(${updateMode} == true) {	
		$('#workForm').attr('action', 'updateWork');			
	}else {/**insertmode**/		
		$('#workForm').attr('action', 'insertWork');
	
	 }

			//장비공도구 설정
	var toolSize;
	
	<c:if test="${workVO.toollist== null}">
		toolSize = 0 ;
	</c:if>
	<c:if test="${workVO.toollist != null}">
		toolSize = ${workVO.toollist.size()};
	</c:if>
	
	if(toolSize > 0) {
	var i=0;
		<c:forEach var="tool" items="${workVO.toollist}" varStatus="idx">
	
		var type = '${tool.tooltype}';
		var code = '${tool.toolcode}';
		var name = '${tool.toolname}';
		
		if(type == 0)
			addTool('cons_machine' , 1);
		else if(type == 1)
			addTool('trans_machine' , 1);
		else if(type == 2)
			addTool('etc_machine' , 1);
		else if(type == 3)
			addTool('weld_tool' , 1);
		else if(type == 4)
			addTool('elec_tool' , 1);
		else if(type == 5)
			addTool('nelec_tool' , 1);
		else if(type == 6)
			addTool('etc_tool' , 1);
		else if(type == 98) //장비 수기
			addTool('etc_machine' , 1);
		else if(type == 99)//공도구 수기
			addTool('etc_tool' , 1);
		
		$('#toolSelect_' + i).val(name);
		$('#toolcode_' + i).val(code);
		$('#tooltype_' + i).val(type);		
		
		i++;
		</c:forEach>
		
	}else {}
	//장비공도구 설정 END
 
	//placeList 출력
	setPlaceList('placeList');
	
	/**updateMode일 경우 pic_pos_detail 설정**/
	if($('#pic_position').val() == '기타업체') {
		 showPicDetail();
	}else
		 hidePicDetail();
});

function setPlaceList(targetId) {
	 $.ajax({
	  		type : "POST",
	  		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getPlaceList=",
	  		data : {},
	  		dataType : "jsonp",
	  	    jsonp : "callback",
	  		cache : false,
	  		success : function(json) {
	  			$('#' + targetId).empty();
	  			var placeList = json.placeList;
	  			
	  			var length = 0;
	  			for(var prop in placeList){//size 파악
	 			    if(placeList.hasOwnProperty(prop))
	 			        length++;
	 			}
	  			for(var i = 0 ; i < length; i ++) {
	  				var str ='<input id="place_' + placeList[i].code + '" name="input_placecodes[' + i + ']" type="checkbox" value="' + placeList[i].code +
	  				'"></input><label for="place_' + placeList[i].code + '">' + placeList[i].name + '</label>' +
	  				'<input type="hidden" name="input_placenames[' + i + ']" value="' + placeList[i].name +	'"/> ';
	  				
	  			 	$("#" + targetId).append(str);
	  				
	  			} 
	  			//placeList check (update시 & insert validation 실패시 (back되었을떄))
  				<c:forEach var="placecode" items="${workVO.parse_placecodes}">
  					$('#place_' + '${placecode}').prop("checked", true);
  				</c:forEach>
	  			
	  			
	  		}
	 });
	 			
	 
	 
}

function showPicDetail() {
	 $('#td_pic_pos_detail_1').show();
	 $('#td_pic_pos_detail_2').show();
}

function hidePicDetail() {
	 $('#td_pic_pos_detail_1').hide();
	 $('#td_pic_pos_detail_2').hide();
	 $('#pic_pos_detail').val('');
}


function submitWork() {	
var input;
if(${updateMode} == true) {
	input = confirm('수정하시겠습니까?');
}else {
	input = confirm('등록하시겠습니까?');
}

if(input) { //yes
	checkBeforeSubmit();
	$('#workForm').submit();
}else
	reutrn;
} 

</script>
 

<div class="bgCover">&nbsp;</div>
<!-- overlay box -->
<div class="overlayBox">
<div class="closeLink" style="cursor:pointer"><img src="images/closing.png" onclick="doOverlayClose()" style="cursor:pointer" /></div>
<div class="overlayContent"><div id="viewContent"></div></div>
</div>

<form:form id="workForm" method="POST" modelAttribute="workVO"
	autocomplete="off">
<!--다른사용자의update동작을 고려하여 자동으로 입력되는 부분 추가시 주의-->
<input type="hidden" name="site_idx" value="${workVO.site_idx}" />
<input type="hidden" name="work_idx" value="${workVO.work_idx}" />
<input type="hidden" name="cont_idx" value="${workVO.cont_idx}" />
<input type="hidden" name="write_user_idx" value="${workVO.write_user_idx}" />
<input type="hidden" name="workcode" value="${workVO.workcode}" id="workcode" />
<input type="hidden" name="placecodes" value="${workVO.placecodes}" id="placecodes" />
<input type="hidden" name="workstatus" value="${workVO.workstatus}" id="workcode" />

<input type="hidden" name="risk_grade" value="${workVO.risk_grade}" id="risk_grade" />
<input type="hidden" name="risk_warn" value="${workVO.risk_warn}" id="risk_warn" />
<input type="hidden" name="ptw_exist" value="${workVO.ptw_exist}" />
<input type="hidden" name="pui_exist" value="${workVO.pui_exist}" />

<table class="work_signup">
	<colgroup>
		<col style="width: 33%">
		<col>		
		<col style="width: 33%">
	</colgroup>
	<tr>
		<th>관&nbsp;련&nbsp;업&nbsp;체</th>
		<td colspan="2">
			<form:input id="cont_name"  path="cont_name"  readonly="true" style="border: 0px solid;"/>		
		</td>		
	</tr>
	<tr>
		<th>감&nbsp;&nbsp;&nbsp;독&nbsp;&nbsp;&nbsp;자</th>
		<td colspan="2"><form:select path="inspec_mgr_idx" class="siteSelectBox colspanInput"  style="width:95%">
				<c:forEach var="manager" items="${managerList}" >
					<form:option value="${manager.manager_idx}">${manager.name}</form:option>
				</c:forEach>
		</form:select>
		</td>
	</tr>
</table>


<!-- work start -->
<p class="red">작업선택<br>
<table class="work_signup">
	<colgroup>
		<col>
		<col style="width: 33%">
		<col style="width: 33%">
	</colgroup>
	
	<tr>
		<th>						
			공&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종
		</th>
		<th>				
			대&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;류
		<th>				
			소&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;류
		</th>
	</tr>
	<tr class="listTR">
		<td  onclick="goPopup()">						
			<form:input id="worktype" class="categoryInput" path="worktype"  readonly="true" style="cursor:pointer;border:2px solid #1D4F99" />
			<br><form:errors path="worktype" cssClass="formError"/>
		</td>
		<td  onclick="goPopup()">				
			<form:input id="category1"  class="categoryInput" path="category1"  readonly="true" style="cursor:pointer;border:2px solid #1D4F99"/>
			<br><form:errors path="category1" cssClass="formError"/>
		<td  onclick="goPopup()">				
			<form:input id="category2"  class="categoryInput" path="category2"  readonly="true" style="cursor:pointer;border:2px solid #1D4F99"/>
			<br><form:errors path="category2" cssClass="formError"/>
		</td>
	</tr>
	<tr>
		<th>작&nbsp;업&nbsp;종&nbsp;류</th>
		<td colspan="2"  onclick="goPopup()"  class="listTR categoryInput ">			
			<form:input id="workname"  path="workname"  readonly="true" class="colspanInput" style="cursor:pointer;border:2px solid #1D4F99"/>
			<br><form:errors path="workname" cssClass="formError"/>
		</td>
	</tr>			
	
	
	
	<tr>
		<th>작업타이틀</th>
		<td colspan="2"><form:input path="worktitle" class="colspanInput"	maxlength="30" />
						<br> <form:errors path="worktitle" cssClass="formError"  /></td>
	</tr>
	<tr>
		<th>작&nbsp;업&nbsp;유&nbsp;형</th>
		<td colspan="2">
			<span>
			<form:radiobutton path="ischarge" class="radi"  value="N" id="ischarge1"/><label for="ischarge1" style="cursor:pointer">신규작업</label>			
			<form:radiobutton path="ischarge" class="radi" value="Y"  id="ischarge2"/><label for="ischarge2"  style="color:red;cursor:pointer">돌관작업</label>				
			</span>
			<p><form:errors path="ischarge" cssClass="formError"/>
		</td>
	</tr>
	<tr>
		<th>작&nbsp;업&nbsp;시&nbsp;작</th>	
		<td><form:input id="startDateInput" path="startdate" 	maxlength="10" readonly="true"/>
			<br> <form:errors cssClass="formError" path="startdate" /></td>
		<td>	
			<form:select path="starttime" class="siteSelectBox" >
			<c:forEach begin="1" end="9" varStatus="idx">
					<form:option value="0${idx.index}:00:00">0${idx.index}:00:00</form:option>
					<form:option value="0${idx.index}:30:00">0${idx.index}:30:00</form:option>
			</c:forEach>
			<c:forEach begin="10" end="23" varStatus="idx">
					<form:option value="${idx.index}:00:00">${idx.index}:00:00</form:option>
					<form:option value="${idx.index}:30:00">${idx.index}:30:00</form:option>
			</c:forEach>
					
			</form:select>
			<br> <form:errors cssClass="formError" path="starttime" />
		 </td>
	</tr>

	<tr>
		<th>작&nbsp;업&nbsp;마&nbsp;감</th>
		<td><form:input id="endDateInput" path="enddate" maxlength="10" readonly="true"/>
			<br><form:errors cssClass="formError" path="enddate" /></td>
		<td>
			
			<form:select path="endtime" class="siteSelectBox" >
			<c:forEach begin="1" end="9" varStatus="idx">
					<form:option value="0${idx.index}:00:00">0${idx.index}:00:00</form:option>
					<form:option value="0${idx.index}:30:00">0${idx.index}:30:00</form:option>
			</c:forEach>
			<c:forEach begin="10" end="23" varStatus="idx">
					<form:option value="${idx.index}:00:00">${idx.index}:00:00</form:option>
					<form:option value="${idx.index}:30:00">${idx.index}:30:00</form:option>
			</c:forEach>
			</form:select>
		<br> <form:errors cssClass="formError" path="endtime" />
		 </td>
	</tr>

</table>


<!--  start -->
	<p class="red">장&nbsp;비&nbsp;선&nbsp;택<br>				
	<table class="work_signup">		
	<colgroup>	
		<col style="width:33%">
		<col>					
	</colgroup>
	<tr>
		<th><span class="iconImg"><img src="images/icon_equipment02.png" alt="건설장비"><br/>건&nbsp;설&nbsp;장&nbsp;비</span></th>
		<td id="cons_machine"></td>
		<td>
			<span class="btn_typ01"  onclick="addTool('cons_machine', 0);">추가</span>
		</td>
	</tr>
	<tr>
		<th><span class="iconImg"><img src="images/icon_equipment01.png" alt="운반장비"><br/>운&nbsp;반&nbsp;장&nbsp;비</span></th>
		<td id="trans_machine"></td>
		<td><span class="btn_typ01"  onclick="addTool('trans_machine', 0);">추가</span></td>
	</tr>
	<tr>
		<th><span class="iconImg"><img src="images/icon_equipment03.png" alt="기타장비"><br/>기&nbsp;타&nbsp;장&nbsp;비</span></th>
		<td id="etc_machine"></td>
		<td><span class="btn_typ01"  onclick="addTool('etc_machine', 0);">추가</span>
		<br>
		<span class="btn_typ01 handWrite"  onclick="addToolText('etc_machine',false);">수기입력</span>
		</td>
	</tr>	
</table>

<!--  //work_signup -->
<p class="red">공도구선택<br>
<table class="work_signup">
	<colgroup>	
		<col style="width:33%">
		<col>					
	</colgroup>
	<tr>
		<th><span class="iconImg"><img src="images/icon_tool01.png" alt="용접기"></span><br />용&nbsp;&nbsp;&nbsp;접&nbsp;&nbsp;&nbsp;기</th>
		<td id="weld_tool"></td>
		<td><span class="btn_typ01"  onclick="addTool('weld_tool', 0);">추가</span></td>
	</tr>
	<tr>
		<th><span class="iconImg"><img src="images/icon_tool02.png" alt="전동도구"></span><br />전&nbsp;동&nbsp;도&nbsp;구</th>
		<td id="elec_tool"></td>
		<td><span class="btn_typ01"  onclick="addTool('elec_tool', 0);">추가</span></td>
	</tr>
	<tr>
		<th><span class="iconImg"><img src="images/icon_tool03.png" alt="비전동도구"></span><br />비전동도구</th>
		<td id="nelec_tool"></td>
		<td><span class="btn_typ01"  onclick="addTool('nelec_tool', 0);">추가</span></td>
	</tr>
	<tr>
		<th><span class="iconImg"><img src="images/icon_tool04.png" alt="기타도구"></span><br />기&nbsp;타&nbsp;도&nbsp;구</th>
		<td id="etc_tool"></td>
		<td>
			<span class="btn_typ01"  onclick="addTool('etc_tool', 0);">추가</span>
			<br>
			<span class="btn_typ01 handWrite"  onclick="addToolText('etc_tool', true);">수기입력</span>
		</td>
	</tr>
</table>
<!-- end  -->

<p class="red">작업장소등록<br>
<table class="work_signup">
	<colgroup>
		<col style="width: 33%">
		<col>
	</colgroup>
	
	<!-- start -->
	<tr>
		<th>장&nbsp;소&nbsp;유&nbsp;형</th>
		<td id="placeList"></td>
	</tr>
	
	<tr>
		<th>세&nbsp;부&nbsp;장&nbsp;소</th>
		<td>
			<form:input path="addr_detail" class="colspanInput" 	maxlength="255" />
			<br> <form:errors path="addr_detail" cssClass="formError"  />
		</td>
	</tr>
	
</table>

<div class="work_cap">
	<p class="red">작업책임자<br>
	<table class="work_cap">
		<colgroup>
			<col style="width: 20%">
			<col style="width: 30%">
			<col style="width: 20%">
			<col>
		</colgroup>
		<tr>
			<th>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</th>
			<td><form:input path="pic_name" 	maxlength="45" />
				<br> <form:errors path="pic_name" cssClass="formError"  /></td>
			<th>생년&nbsp;월일</th>
			<td><form:input id="birthInput" path="pic_birth" 	maxlength="10" readonly="true" />
				<br> <form:errors path="pic_birth" cssClass="formError"  />
			</td>
		</tr>
		<tr>
			<th>연&nbsp;락&nbsp;처</th>
			<td><form:input class="phone" path="pic_phone" 	maxlength="13" onblur="checkPhone(this, this.value)"/>
			<br> <form:errors path="pic_phone" cssClass="formError"  /></td>
			
			<th>작업자 수</th>
			<td >
				<form:select id="test" path="pic_num_worker" class="selectBox" style="width:83%">
				<c:forEach begin="1" end="30" varStatus="idx">
						<form:option value="${idx.index}">${idx.index}</form:option>
				</c:forEach>
					<form:option value="999">31+</form:option>
				</form:select>
			</td>
		</tr>
		<tr>
			<th  rowspan="2">소&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;속</th>
			<td colspan="3">
				<form:select id="pic_position" path="pic_position" class="siteSelectBox"  style="width:94%;">
					
					<c:forEach var="cont" items="${contList}" >
						<form:option value="${cont.cont_name}" onclick="hidePicDetail()">${cont.cont_name}</form:option>
					</c:forEach>
					<form:option value="기타업체" onclick="showPicDetail()">기타</form:option>
				</form:select>
				 <form:errors path="pic_position" cssClass="formError"  />
			</td>	
		</tr>
		<tr>
		<td id="td_pic_pos_detail_1">기타업체명</td>
		<td id="td_pic_pos_detail_2" colspan="2">
			<form:input path="pic_pos_detail" style="width:88%"/>
		</td>
		</tr>
	
		<tr>
			<th rowspan="2">작업상황<br><br>난&nbsp;이&nbsp;도
			</th>
			<td colspan="3">
			<form:select path="worklevel" class="selectBox" >
				<form:option value="0">평범한 상황</form:option>
				<form:option value="1">어려운 상황</form:option>
				<form:option value="2">매우 어려운 상황</form:option>
			</form:select>
			</td>
		</tr>

		<tr>
		<td colspan="3"><span class="explain">
		<b style="color:limegreen">평범한 상황</b> : 평상 시 작업과 유사한 조건으로 작업 상 애로사항이 없음<br>
		<b style="color:yellow">어려운 상황</b> : 혹서기, 혹한기, 일기(비, 눈, 바람 등)등에 의해 작업상 어려움이 있어 관리가 필요한 상황<br>
		<b style="color:red">매우 어려운 상황</b> : 작업이 매우 어려운 상황이지만 불가피하게 작업을 진행해야 하는 경우, 특별관리가 필요한 상황<br>
	</span>
		</td>
		</tr>

	</table>
</div>

<p class="red">특이사항(최대 100자 입력)<br>
<table>
	<colgroup>
		<col style="width: 20%">
		<col>
	</colgroup>
	<tr>
		<th>작&nbsp;성&nbsp;자</th>
		<td><form:textarea  class="remark" path="remark" 	maxlength="100" />
			<br> <form:errors path="remark" cssClass="formError"  />
		</td>
	</tr>
	<tr>
		<th>팀장/안전<br>관&nbsp;리&nbsp;자</th>
			<td><form:textarea  class="remark"  path="remark_leader" 	maxlength="100" />
			<br> <form:errors path="remark_leader" cssClass="formError"  />
		</td>
	</tr>
	<tr>
		<th>현장소장</th>
				<td><form:textarea  class="remark"  path="remark_chief" 	maxlength="100" />
		<br> <form:errors path="remark_chief" cssClass="formError"  />
		</td>
	</tr>
</table>

<div class="paging">
		<!--  insert -->
	<c:if test="${!updateMode}">
		<span class="signup"><span class="btn_typ02"  onclick="submitWork()">등록 ></span>	</span>
	</c:if>

	<!-- update -->
	<c:if test="${updateMode}">
		<span class="signup"><span class="btn_typ02"  onclick="submitWork()">수정 ></span></span>
	</c:if>
</div>
<!--p class="goTop">
	<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a>
</p-->



</form:form>

<%@ include file="IncludeBottom.jsp"%>