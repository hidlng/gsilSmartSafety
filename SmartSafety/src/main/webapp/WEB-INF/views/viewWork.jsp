<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
$(document).ready(function() {	
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
			addTool('cons_machine' , false);
		else if(type == 1)
			addTool('trans_machine' , false);
		else if(type == 2)
			addTool('etc_machine' , false);
		else if(type == 3)
			addTool('weld_tool' , false);
		else if(type == 4)
			addTool('elec_tool' , false);
		else if(type == 5)
			addTool('nelec_tool' , false);
		else if(type == 6)
			addTool('etc_tool' , false);
		
		
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
function getTool(tarId, isSelect) {
	var str;
	if(isSelect){
		str = "<select id='toolSelect_" + equipIdx + "' name='toollist["
		+ equipIdx + "].toolname' class='siteSelectBox' onchange='selectTool("
		+ equipIdx + ", \""	+ tarId + "\")'>"
	//	+ "<option>:::선택:::</option>"
		+ "</select>";
	}else {
		str =  "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
		+ equipIdx + "].toolname' />";
	}
	
	str += "<input type='button' id='toolDelete_" + equipIdx + "' style='width:30px' onclick='removeTool(" 
   	+ equipIdx + ")' value='X'></input>"
   	+ "<input  type='hidden' name='toollist[" + equipIdx + "].toolcode' id='toolcode_" + equipIdx + "' />" 
 	+ "<input  type='hidden' name='toollist[" + equipIdx + "].tooltype' id='tooltype_" + equipIdx + "' />" 
   	;
 	
 	return str;
}


/* selectbox 추가
 * tarId : select박스를 추가할 TD
 */
function addTool(tarId, isSelect) {	
	var str = getTool(tarId , isSelect);
	
	/**span 추가**/
	var addedSpan = document.createElement("span");
	addedSpan.id = "toolSpan_" + equipIdx;
	addedSpan.innerHTML = str;
	$("#" + tarId).append(addedSpan);
	
	if(isSelect){
		if(tarId == 'cons_machine') setCode(57, 'toolSelect_' + equipIdx);
		else if(tarId == 'trans_machine') setCode(58, 'toolSelect_' + equipIdx);
		else if(tarId == 'etc_machine') setCode(59, 'toolSelect_' + equipIdx);
		else if(tarId == 'weld_tool') setCode(61, 'toolSelect_' + equipIdx);
		else if(tarId == 'elec_tool') setCode(62, 'toolSelect_' + equipIdx);
		else if(tarId == 'nelec_tool') setCode(63, 'toolSelect_' + equipIdx);
		else if(tarId == 'etc_tool') setCode(64, 'toolSelect_' + equipIdx);

	}
	equipIdx++;
//	$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함	
}



 function submitWork() {	
	$('#workForm').submit();

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
	
	
	<table class="user_signup">
		<colgroup>
			<col>
			<col style="width: 33%">
			<col style="width: 33%">
		</colgroup>
		<tr>
			<th>관련업체</th>
			<td colspan="2">${workVO.cont_name}	</td>
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
			<td>						
				${workVO.worktype}
			</td>
			<td>				
				${workVO.category1}
			<td>				
				${workVO.category2}
			</td>
		</tr>
		<tr>
			<th>작업종류</th>
			<td colspan="2">			
				${workVO.workname}
			</td>
		</tr>			
		
		<tr>
			<th>작업타이틀</th>
			<td colspan="2">	${workVO.worktitle}</td>
		</tr>
		<tr>
			<th>작업 유형</th>
			<td colspan="2">
				${workVO.ischarge}
			</td>
		</tr>
		<tr>
			<th>작업기간</th>	
			<td>시작 : ${workVO.startdate}</td>
			<td>마감 : ${workVO.enddate}</td>
		</tr>

		<tr>
			<th>작업시간</th>
			<td>시작 :  ${workVO.starttime}</td>
			<td>마감 :  ${workVO.endtime}</td>
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
				
			</td>
			<td id="trans_machine">
				
			</td>
			<td id="etc_machine">
				
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
				
			</td>
			<td id="elec_tool">
				
			</td>
			<td id="nelec_tool">
								
			</td>
			<td id="etc_tool">
				
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
			${workVO.placename}</td>
		</tr>
		<!--  end -->
		
		<tr>
			<th>세부장소</th>
			<td colspan="3">
				${workVO.addr_detail}
			</td>
		</tr>
		<tr>
			<th>실내/외 여부</th>
			<td colspan="3">
				${workVO.indoor}
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
				<td>${workVO.pic_name}</td>
				<th>생년월일</th>
				<td>${workVO.pic_birth}</td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>${workVO.pic_phone}</td>
				<th>소속</th>
				<td>
				${workVO.pic_position}</td>	
			</tr>
			<tr>
				<th>작업자 수</th>
				<td colspan="3">${workVO.pic_num_worker}</td>
			</tr>
			<tr>
				<th rowspan="2">작업상황<br />난이도	</th>
				<td colspan="3">${workVO.worklevel}	</td>
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
			<td>>${workVO.remark}</td>
		</tr>
	</table>

	<div class="paging">
		<span class="signup"><span class="btn_typ02 toHomePage">목록</span>	</span>
	</div>
	<p class="goTop">
		<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a>
	</p>



</form:form>

<%@ include file="IncludeBottom.jsp"%>