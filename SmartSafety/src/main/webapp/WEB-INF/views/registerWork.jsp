<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
 $(document).ready(function() {	
	if (${isNotValid} == true) {
		alert("입력하지 않은 항목이 있습니다. 확인해주시기 바랍니다");
	}
	 
	 /**updatemode**/
	if(${updateMode} == true) {	
		$('#workForm').attr('action', 'updateWork');			
	}else {/**insertmode**/		
		$('#workForm').attr('action', 'insertWork');

	 }
	 
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
		
	}else {
		/* addTool('cons_machine' , true); //건설장비 selectbox
		addTool('trans_machine' , true);//운반장비
		addTool('etc_machine' , true);//기타 장비 */
	}

	//setCode(3, 'placename', 'placename');//작업 형태 지정
	

	
	
 });
 
 
 
 /**장비관련**/
 var equipIdx = 0;

 /**option 선택시 새로 추가될 selectbox형태 설정
  * tarId : select박스를 추가할 TD
  * isSelect : Seletbox/Text 결정
  */
 function getTool(tarId, inputType) {
 	var str;
 	if(inputType == 0){//Select
 		str = "<select id='toolSelect_" + equipIdx + "' name='toollist["
 		+ equipIdx + "].toolname' class='siteSelectBox' onchange='selectTool("
 		+ equipIdx + ", \""	+ tarId + "\")'>"
 	//	+ "<option>:::선택:::</option>"
 		+ "</select>";
 	}else if(inputType == 1) { //readonly text
 		str =  "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
 		+ equipIdx + "].toolname' readonly='true'/>";
 	}else if(inputType == 2) {//수기입력 text
 		str =  "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
 		+ equipIdx + "].toolname' value=''/>";
 	}
 	
 	str += "<input type='button' id='toolDelete_" + equipIdx + "' style='width:30px;cursor:pointer' onclick='removeTool(" 
    	+ equipIdx + ")' value='X' ></input>"
    	+ "<input  type='hidden' name='toollist[" + equipIdx + "].toolcode' id='toolcode_" + equipIdx + "' />" 
  	+ "<input  type='hidden' name='toollist[" + equipIdx + "].tooltype' id='tooltype_" + equipIdx + "' />" 
    	;
  	
  	return str;
 }

 
 /* selectbox 추가
  * tarId : select박스를 추가할 TD
  */
 function addTool(tarId, inputType) {	
	var type;
	
 	var str = getTool(tarId , inputType);
 	
 	/**span 추가**/
 	var addedSpan = document.createElement("span");
 	addedSpan.id = "toolSpan_" + equipIdx;
 	addedSpan.innerHTML = str;
 	$("#" + tarId).append(addedSpan);
 	
 	if(inputType == 0){//selectbox 항목추가
 		if(tarId == 'cons_machine') setCode(57, 'toolSelect_' + equipIdx);
 		else if(tarId == 'trans_machine') setCode(58, 'toolSelect_' + equipIdx);
 		else if(tarId == 'etc_machine') setCode(59, 'toolSelect_' + equipIdx);
 		else if(tarId == 'weld_tool') setCode(61, 'toolSelect_' + equipIdx);
 		else if(tarId == 'elec_tool') setCode(62, 'toolSelect_' + equipIdx);
 		else if(tarId == 'nelec_tool') setCode(63, 'toolSelect_' + equipIdx);
 		else if(tarId == 'etc_tool') setCode(64, 'toolSelect_' + equipIdx);

 	}
 	equipIdx++;
// 	$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함	
 }
 
 /**수기입력용 Textbox생성**/
 function addToolText(tarId, isTool) {
	 var str = getTool(tarId , 2);//text박스
	 
	 var addedSpan = document.createElement("span");
	 	addedSpan.id = "toolSpan_" + equipIdx;
	 	addedSpan.innerHTML = str;
	 	$("#" + tarId).append(addedSpan);
	 	
		/**hidden값 code, type 값 부여**/
	 	$('#toolcode_' + equipIdx).val('_');
	 	if(isTool) //공도구면 99
	 		$('#tooltype_' + equipIdx).val(99); //99: 수기입력
	 	else//장비면 98
	 		$('#tooltype_' + equipIdx).val(98); //98: 수기입력
	 	
	 equipIdx++;
 }

 //넘겨받은 selectbox의 id값은 code값과 같음
 //code 및 type hiddne값 지정용도
 function selectTool(eIdx, tarId) {
 	var code = $('#toolSelect_' + eIdx + ' option:selected').attr('id'); //선택된 option의 id값이 code값임.(val은 name)
 	var type;
 	if(tarId == 'cons_machine') type=0;
 	else if(tarId == 'trans_machine') type=1;
 	else if(tarId == 'etc_machine') type=2;
 	else if(tarId == 'weld_tool') type=3;
	else if(tarId == 'elec_tool')  type=4;
	else if(tarId == 'nelec_tool') type=5;
	else if(tarId == 'etc_tool') type=6;
 	
 	
 	/**hidden값 code, type 값 부여**/
 	$('#toolcode_' + eIdx).val(code);
 	$('#tooltype_' + eIdx).val(type); //TODO : type에 따라 구분할것
 	
 	/**추가된 span 내 select에 항목 추가**/	
 //	addTool(tarId, true);
 }

 /**submit시 toollist에 추가되지않게 관련속성 모두 제거**/
function removeTool(idx) {
	$('#toolSelect_' + idx).remove();
	$('#toolcode_' + idx).remove();
	$('#tooltype_' + idx).remove();
	$('#toolDelete_' + idx).remove();
}

//submit전 선택하지 않은 list에 대해 정리를 실시 
//error발생시 ---선택--이 나타나는 문제떄문
function checkBeforeSubmit() {
	
	for(var i = 0 ; i < equipIdx ; i++ ) {
		if($('#toolcode_' + i).val() == ''){
			removeTool(i);
			//alert($('#toolcode_' + i).parent().attr('id'));
		}
	}
		
}


function goPopup(){
	$.ajax({
		type : "POST",
		url : "workPopup",
		cache : false,
		success : function(json){
			$('#viewContent').html(json);
			$('#popupOKBtn').hide();
			setChildCategoryOf(1, 'worktype_pop');//init
			doOverlayOpen();	
		},
		error : onError
	});
}



function confirmCode() {
	//alert(worktype + " 1" + category1 + " " + category2 + " " + workcode + " " + workname);	 
	var worktype = $('#worktype_pop option:selected').val();
	var category1 = $('#category1_pop option:selected').val();
	var category2 = $('#category2_pop option:selected').val();
	var workname = $('#workname_pop option:selected').val();
	var workcode = $('#workname_pop  option:selected').attr('id');
	
	$('#worktype').val(worktype);
	$('#category1').val(category1);
	$('#category2').val(category2);
	$('#workcode').val(workcode);
	$('#workname').val(workname);
	
	$('#viewContent').html('');
	doOverlayClose();	
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
<div class="closeLink" style="cursor:hand"><img src="images/x-button.png" onclick="doOverlayClose()" /></div>
<div class="overlayContent"><div id="viewContent"></div></div>
</div>

<form:form id="workForm" method="POST" modelAttribute="workVO"
	autocomplete="off">
	<!--다른사용자의update동작을 고려하여 자동으로 입력되는 부분 추가시 주의-->
	<input type="hidden" name="site_idx" value="${workVO.site_idx}" />
	<input type="hidden" name="work_idx" value="${workVO.work_idx}" />
	<input type="hidden" name="write_user_idx" value="${workVO.write_user_idx}" />
	<input type="hidden" name="workcode" value="${workVO.workcode}" id="workcode" />
	<input type="hidden" name="placecode" value="${workVO.placecode}" id="placecode" />
	<input type="hidden" name="workstatus" value="${workVO.workstatus}" id="workcode" />
	
	<input type="hidden" name="risk_grade" value="${workVO.risk_grade}" id="risk_grade" />
	<input type="hidden" name="risk_warn" value="${workVO.risk_warn}" id="risk_warn" />
	<input type="hidden" name="workpermit" value="${workVO.workpermit}" id="workpermit" />
	
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
		<!-- work start -->
		<tr>
			<th>						
				공종
			</th>
			<th>				
				대분류
			<th>				
				소분류
			</th>
		</tr>
		<tr>
			<td  onclick="goPopup()">						
				<form:input id="worktype" path="worktype"  readonly="true" />
				<form:errors path="worktype" cssClass="formError"/>
			</td>
			<td  onclick="goPopup()">				
				<form:input id="category1"  path="category1"  readonly="true" />
				<form:errors path="category1" cssClass="formError"/>
			<td  onclick="goPopup()">				
				<form:input id="category2" path="category2"  readonly="true" />
				<form:errors path="category2" cssClass="formError"/>
			</td>
		</tr>
		<tr>
			<th>작업종류</th>
			<td colspan="2"  onclick="goPopup()">			
				<form:input id="workname"  path="workname"  readonly="true" />
				<form:errors path="workname" cssClass="formError"/>
			</td>
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
	

	<!--  start -->
		<p class="red">장비선택</p>				
		<table class="user_signup">		
		<colgroup>	
			<col style="width:33%">
			<col style="width:33%">
			<col style="width:33%">						
		</colgroup>
		<tr>
			<th><span class="iconImg"><img src="images/icon_equipment02.png" alt="건설장비"><br/>건설장비</span></th>
			<th><span class="iconImg"><img src="images/icon_equipment01.png" alt="운반장비"><br/>운반장비</span></th>
			<th><span class="iconImg"><img src="images/icon_equipment03.png" alt="기타장비"><br/>기타장비</span></th>
		</tr>
		<tr>
			<td id="cons_machine">
				<span class="btn_typ01"  onclick="addTool('cons_machine', 0);">추가</span>
			</td>
			<td id="trans_machine">
				<span class="btn_typ01"  onclick="addTool('trans_machine', 0);">추가</span>
			</td>
			<td id="etc_machine">
				<span class="btn_typ01"  onclick="addTool('etc_machine', 0);">추가</span>
				<span class="btn_typ01"  onclick="addToolText('etc_machine');">수기입력</span>
			</td>
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
			<th>기타도구</th>
		</tr>
		<tr>
			<td id="weld_tool">
				<span class="btn_typ01"  onclick="addTool('weld_tool', 0);">추가</span>
			</td>
			<td id="elec_tool">
				<span class="btn_typ01"  onclick="addTool('elec_tool', 0);">추가</span>
			</td>
			<td id="nelec_tool">
				<span class="btn_typ01"  onclick="addTool('nelec_tool', 0);">추가</span>				
			</td>
			<td id="etc_tool">
				<span class="btn_typ01"  onclick="addTool('etc_tool', 0);">추가</span>
			</td>
		</tr>		
	</table>
	<!-- end  -->

	<p class="red">작업장소등록</p>
	<table class="user_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>
		
		<!-- start -->
		<tr>
			<th>장소유형</th>
			<td colspan="3">
			<form:select path="placename" class="siteSelectBox" id="placename">
				<form:option value="">:::선택:::</form:option>
				<form:option value="" selected="true">2</form:option>
			</form:select></td>
		</tr>
		<!--  end -->
		
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
				<td><form:input class="phone" path="pic_phone" 	maxlength="13" onblur="checkPhone(this, this.value)"/>
				<p /> <form:errors path="pic_phone" cssClass="formError"  /></td>
				<th>소속</th>
				<td>
				<form:select path="pic_position" class="siteSelectBox" >
					<c:forEach var="cont" items="${contList}" >
						<form:option value="${cont.cont_idx}">${cont.cont_name}</form:option>
					</c:forEach>
				</form:select></td>	
			</tr>
			<tr>
				<th>작업자 수</th>
				<td colspan="3">
					<form:select id="test" path="pic_num_worker" class="selectBox" >
					<c:forEach begin="1" end="50" varStatus="idx">
							<form:option value="${idx.index}">${idx.index}</form:option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<th rowspan="2">작업상황<br />난이도
				</th>
				<td colspan="3">
				<form:select path="worklevel" class="selectBox" >
					<c:forEach begin="0" end="2" varStatus="count">
							<form:option value="${count.index}">난이도 ${count.index}</form:option>
					</c:forEach>
				</form:select>
				</td>
			</tr>

			<tr>
			<td colspan="3"><span class="explain">
			<b style="color:red">0점</b> : 평범 평상 시 작업과 유사한 조건으로 작업 상 애로사항이 없음.<br>
			<b style="color:red">1점</b> : 어려운 상황 혹서기, 혹한기, 일기(비, 눈, 바람 등)등에 의해 작업상 어려움이 있어 관리가 필요한 상황<br>
			<b style="color:red">2점</b> : 매우어려운상황 작업이 매우 어려운 상황이지만 불가하게 작업을 진행해야 하는 경우, 특별관리가 필요한 상황<br>
		</span>
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
			<span class="signup"><span class="btn_typ02"  onclick="submitWork()">등록 ></span>	</span>
		</c:if>

		<!-- update -->
		<c:if test="${updateMode}">
			<span class="signup"><span class="btn_typ02"  onclick="submitWork()">수정 ></span></span>
		</c:if>
	</div>
	<p class="goTop">
		<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a>
	</p>



</form:form>

<%@ include file="IncludeBottom.jsp"%>