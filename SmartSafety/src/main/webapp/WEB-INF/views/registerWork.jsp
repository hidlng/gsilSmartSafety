<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
 $(document).ready(function() {	
	 /**updatemode**/
	 if(${updateMode} == true) {	
		$('#workForm').attr('action', 'updateWork');

		
	}else {/**insertmode**/		
		$('#workForm').attr('action', 'insertWork');

	 }
	 
	 //size가 0이아니면	 
	 //for
	 //정보가 온전한것들에대해서
	 //type별로 지정
	 //addTool
	 //option:select val 지정
	 //hidden값 지정해줄필욘없을듯?
	
				 
	var toolSize = ${workVO.toollist.size()};
	
	
	if(toolSize > 0) {
		<c:set var="idx" value="0" />
		for (var i = 0; i <toolSize ; i++) {		
		//정보가 온전한것들에대해서
				
		var type = '${workVO.toollist[idx].tooltype}';
		var code = '${workVO.toollist[idx].toolcode}';
		var name = '${workVO.toollist[idx].toolname}';
		
		if(type == 0)
			addToolText('cons_tool');
		else if(type == 1)
			addToolText('trans_tool');
		else if(type == 2)
			addToolText('etc_tool');
		
		//$('#toolSelect_' + i + ' > option[value=[' + code + ']').attr('selected', 'true'); //i = equipIdx */
		
		//alert($('#toolSelect_' + i).val());
		
		//$('#toolSelect_0').val(code);
		
		$('#toolSelect_' + i).val(name);
		$('#toolcode_' + i).val(code);
		$('#tooltype_' + i).val(type);
		
		//$('#toolSelect_' + i + 'option:selected').val(code);
		//alert($("#toolSelect_0 option:selected").val());
		
		} 
	}else {
		addTool('cons_tool'); //건설장비
		addTool('trans_tool');//운반장비
		addTool('etc_tool');//기타 장비
	}
		

	setCode(3, 'placecode', 'placecode');//작업 형태 지정
	

	
	
 });
 
function goPopup(){
	$.ajax({
		type : "POST",
		url : "workPopup",
		cache : false,
		success : function(json){
			$('#viewContent').html(json);
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
	var workcode = $('#workcode_pop').val();
	
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
		$('#workForm').submit();
	}else
		reutrn;
 } 

/**장비관련**/
var equipIdx = 0;

/**option 선택시 새로 추가될 selectbox형태 설정
 * tarId : select박스를 추가할 TD
 */
function getTool(tarId) {
	return "<select id='toolSelect_" + equipIdx + "' name='toollist["
	+ equipIdx + "].toolname' class='siteSelectBox' onchange='alert(this.id);selectTool("
	+ equipIdx + ", \""	+ tarId + "\")'>"
//	+ "<option>:::선택:::</option>"
	+ "</select>"
	+ "<div  onclick='delTool(" 
   	+ equipIdx + ")' class='button checkListBtn'>제거</div>"
   	+ "<input  type='hidden' name='toollist[" + equipIdx + "].toolcode' id='toolcode_" + equipIdx + "' />" 
 	+ "<input  type='hidden' name='toollist[" + equipIdx + "].tooltype' id='tooltype_" + equipIdx + "' />" 
   	;
}

function getToolText(tarId) {
	return "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
	+ equipIdx + "].toolname' />"
	+ "<div  onclick='delTool(" 
   	+ equipIdx + ")' class='button checkListBtn'>제거</div>"
   	+ "<input  type='hidden' name='toollist[" + equipIdx + "].toolcode' id='toolcode_" + equipIdx + "' />" 
 	+ "<input  type='hidden' name='toollist[" + equipIdx + "].tooltype' id='tooltype_" + equipIdx + "' />" 
   	;
}

function addToolText(tarId) {	
	var str = getToolText(tarId);
	
	/**span 추가**/
	var addedSpan = document.createElement("span");
	addedSpan.id = "toolSpan_" + equipIdx;
	addedSpan.innerHTML = str;
	$("#" + tarId).append(addedSpan);
	
	equipIdx++;
//	$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함	
}

/* selectbox 추가
 * tarId : select박스를 추가할 TD
 */
function addTool(tarId) {	
	var str = getTool(tarId);
	
	/**span 추가**/
	var addedSpan = document.createElement("span");
	addedSpan.id = "toolSpan_" + equipIdx;
	addedSpan.innerHTML = str;
	$("#" + tarId).append(addedSpan);
	
	
	/**추가된 span 내 select에 항목 추가**/	
	setCode(57, 'toolSelect_' + equipIdx);
	
	equipIdx++;
//	$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함	
}

//넘겨받은 selectbox의 id값은 code값과 같음
//선택 시 해당하는 값을 지정해주고 새로운 박스를 생성
function selectTool(equipIdx, tarId) {
	var code = $('#toolSelect_' + equipIdx + ' option:selected').attr('id'); //선택된 option의 id값이 code값임.(val은 name)
	
	$('#toolcode_' + equipIdx).val(code);
	$('#tooltype_' + equipIdx).val(0); //TODO : type에 따라 구분할것
	
	addTool(tarId);
}

function delTool(equipIdx) {
	
}

 </script>
 
<input type="button" onclick ="alert('${workVO.toollist[0].toolname}');">
<div class="bgCover">&nbsp;</div>
<!-- overlay box -->
<div class="overlayBox">
<a href=# class="closeLink" ><img src="images/x-button.png" onclick="doOverlayClose()" style="cursor:hand"/></a>
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
	<!-- <input type="hidden" name="toollist[0].tooltype" value="1" />
	<input type="hidden" name="toollist[0].toolcode" value="1" />
	<input type="hidden" name="toollist[0].toolname" value="1" /> -->
	
	
	
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
			<th>작업명</th>
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
			<td id="cons_tool">
			</td>
			<td id="trans_tool">				
			</td>
			<td id="etc_tool">				
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
			<th>기타</th>
		</tr>
		<tr>
			<td>
				<select id="cons_tool" class="siteSelectBox">
					<option value="" selected="selected" >소분류</option>					
				</select>
			</td>
			<td>
				<select id="trans_tool" class="siteSelectBox">
					<option value="" selected="selected" >소분류</option>					
				</select>
			</td>
			<td>
				<select id="etc_tool" class="siteSelectBox">
					<option value="" selected="selected" >소분류</option>					
				</select>
			</td>
			<td>
				<select id="etc_tool" class="siteSelectBox">
					<option value="" selected="selected" >소분류</option>					
				</select>
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
			<form:select path="placecode" class="siteSelectBox" id="placecode">
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