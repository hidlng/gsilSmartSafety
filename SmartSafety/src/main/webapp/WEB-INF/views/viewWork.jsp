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
	
		var name = '${tool.toolname}';
		
		if(type == 0)
			addTool('cons_machine' , name);
		else if(type == 1)
			addTool('trans_machine' , name);
		else if(type == 2 || type == 98) //장비 수기
			addTool('etc_machine' , name);
		else if(type == 3)
			addTool('weld_tool' , name);
		else if(type == 4)
			addTool('elec_tool' , name);
		else if(type == 5)
			addTool('nelec_tool' , name);
		else if(type == 6 ||type == 99)//도구 수기
			addTool('etc_tool' , name);
		
		
		i++;
		</c:forEach>
		
	}else {
		/* addTool('cons_machine' , true); //건설장비 selectbox
		addTool('trans_machine' , true);//운반장비
		addTool('etc_machine' , true);//기타 장비 */
	}

	//setCode(3, 'placename', 'placename');//작업 형태 지정
	

//	alert($("#cons_machine").length);
	
 });

/**viewWork에서 사용된s addTool 함수 **/
function addTool(tarId, name) {	
	var str = "<div>" + name + "</div>"; 	
	/**span 추가**/
	var addedSpan = document.createElement("span");	
	addedSpan.innerHTML = str;
	$("#" + tarId).append(addedSpan);
}

function updateSubmit(){
	var input = confirm('수정하시겠습니까?');
	if(input)
		$('#updateForm').submit();
}



 </script>
 

<div class="bgCover">&nbsp;</div>
<!-- overlay box -->
<div class="overlayBox">
<div class="closeLink" style="cursor:hand"><img src="images/x-button.png" onclick="doOverlayClose()" /></div>
<div class="overlayContent"><div id="viewContent"></div></div>
</div>
<!-- 임시. ceo화면에서 상세보기 했을때 back -->
<c:if test = "${fromCEO == true}">
<div onclick="history.back(-1)" style="cursor:pointer"><img src="images/back_btn.png" width="30px" height="30px"> back</div>
<br>
</c:if>

<!--  ceo화면에서 상세보기한경우  출력 숨김 -->
<c:if test = "${fromCEO == false }">
<div class="printSelect">	
	<span class="printSetting"><img src="images/icon_print.png"  alt="인쇄하기" />Print Select :</span>
	<span class="btn_txtPrint" onclick="openTBM('${workVO.work_idx}')" >TBM</span>
	<c:if test="${workVO.toollist.size() > 0}"><span class="btn_txtPrint" onclick="openPUI('${workVO.work_idx}')">PUI</span></c:if>
	<span class="btn_txtPrint" onclick="openPTW('${workVO.work_idx}')">PTW</span>		
</div><!-- //printSelect -->
</c:if>


<form:form id="workForm" method="POST" modelAttribute="workVO"
		autocomplete="off">
	<!--다른사용자의update동작을 고려하여 자동으로 입력되는 부분 추가시 주의-->


	<table class="work_signup">
		<colgroup>
			<col>
			<col style="width: 33%">
			<col style="width: 33%">
		</colgroup>
		<tr>
			<th>관련업체</th>
			<td colspan="2">${workVO.cont_name}</td>
		</tr>
		<tr>
			<th>감독자</th>
			<td colspan="2">${workVO.inspec_mgr_name}</td>
		</tr>
		<!-- work start -->
		<tr>
			<th>공종</th>
			<th>대분류
			<th>소분류</th>
		</tr>
		<tr>
			<td>${workVO.worktype}</td>
			<td>${workVO.category1}
			<td>${workVO.category2}</td>
		</tr>
		<tr>
			<th>작업종류</th>
			<td colspan="2">${workVO.workname}</td>
		</tr>

		<tr>
			<th>작업타이틀</th>
			<td colspan="2">${workVO.worktitle}</td>
		</tr>
		<tr>
			<th>작업 유형</th>
			<td colspan="2">
			<c:if test='${workVO.ischarge.equals("Y")}'><div style="color:red">돌관작업</div></c:if>
			<c:if test='${workVO.ischarge.equals("N")}'>신규작업</c:if>
				
			</td>
		</tr>
		<tr>
			<th>작업기간</th>
			<td>시작 : ${workVO.startdate}</td>
			<td>마감 : ${workVO.enddate}</td>
		</tr>

		<tr>
			<th>작업시간</th>
			<td>시작 : ${workVO.starttime}</td>
			<td>마감 : ${workVO.endtime}</td>
		</tr>

	</table>


	<!--  start -->
	<p class="red">장비<br>
	<table class="work_signup">
		<colgroup>
			<col style="width: 33%">
			<col>
		</colgroup>
		<tr id ="test">
			<th><span class="iconImg"><img src="images/icon_equipment02.png" alt="건설장비"><br />건설장비</span></th>
			<td id="cons_machine"></td>
		</tr>
		<tr>
			<th><span class="iconImg"><img src="images/icon_equipment01.png" alt="운반장비"><br />운반장비</span></th>
			<td id="trans_machine"></td>
		</tr>
		<tr>
			<th><span class="iconImg"><img src="images/icon_equipment03.png" alt="기타장비"><br />기타장비</span></th>
			<td id="etc_machine"></td>
		</tr>
	</table>
	<!--  //work_signup -->

	<p class="red">공도구<br>
	<table class="work_signup">
		<colgroup>
			<col style="width:33%">
			<col>	
		</colgroup>
		<tr>
			<th><span class="iconImg"><img src="images/icon_tool01.png" alt="용접기"></span><br />용접기</th>
			<td id="weld_tool"></td>
		</tr>
		<tr>
			<th><span class="iconImg"><img src="images/icon_tool02.png" alt="전동도구"></span><br />전동도구</th>
			<td id="elec_tool"></td>
		</tr>
		<tr>
			<th><span class="iconImg"><img src="images/icon_tool03.png" alt="비전동도구"></span><br />비전동도구</th>
			<td id="nelec_tool"></td>
		</tr>
		<tr>
			<th><span class="iconImg"><img src="images/icon_tool04.png" alt="용접기"></span><br />기타도구</th>
			<td id="etc_tool" ></td>
		</tr>
	</table>
	<!-- end  -->

	<p class="red">작업장소<br>
	<table class="work_signup">
		<colgroup>
			<col style="width: 25%">
			<col style="width: 25%">
			<col style="width: 25%">
			<col>
		</colgroup>

		<!-- start -->
		<tr>
			<th>장소유형</th>
			<td colspan="3">${workVO.placename}</td>
		</tr>
		<!--  end -->

		<tr>
			<th>세부장소</th>
			<td colspan="3">${workVO.addr_detail}</td>
		</tr>
		<tr>
			<th>실내/외 여부</th>
			<td colspan="3">
				<c:if test='${workVO.indoor.equals("Y")}'>실내</c:if>
				<c:if test='${workVO.indoor.equals("N")}'>실외</c:if>
			</td>
		</tr>
	</table>

	<div class="work_cap">
		<p class="red">작업책임자<br>
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
				<th>작업자 수</th>
				<td>
					<c:if test="${workVO.pic_num_worker >= 999 }">31+ 명</c:if>
					<c:if test="${workVO.pic_num_worker < 999 }">${workVO.pic_num_worker} 명</c:if>
					
				</td>
			</tr>
			<tr>
				<th>소속</th>
				<td colspan="3">${workVO.pic_position}
				<c:if test='${workVO.pic_position.equals("기타업체") && !workVO.pic_pos_detail.equals("")}'>
					(${workVO.pic_pos_detail}) 
				</c:if>
				</td>
			</tr>			
			
			<tr>
				<th rowspan="2">작업상황<br />난이도
				</th>
				<td colspan="3">
					<c:if test="${workVO.worklevel == 0}"><b style="color: limegreen">평범한 상황</b></c:if>
					<c:if test="${workVO.worklevel == 1}"><b style="color: yellow">어려운 상황</b></c:if>
					<c:if test="${workVO.worklevel == 2}"><b style="color: red">매우 어려운 상황</b></c:if>
				</td>
			</tr>

			<tr>
				<td colspan="3"><span class="explain">
						<b style="color: limegreen">평범한 상황</b> : 평상 시 작업과 유사한 조건으로 작업 상 애로사항이 없음<br>
						<b style="color: yellow">어려운 상황</b> : 혹서기, 혹한기, 일기(비, 눈, 바람 등)등에 의해 작업상 어려움이 있어 관리가 필요한 상황<br> 
						<b style="color: red">매우 어려운 상황</b> : 작업이 매우 어려운 상황이지만 불가피하게 작업을 진행해야 하는 경우,특별관리가 필요한 상황<br>
				</span></td>
			</tr>

		</table>
	</div>

	<p class="red">특이사항<br>
	<table>
		<colgroup>
			<col style="width: 20%">
			<col>
		</colgroup>
		<tr>
			<th>작성자<br>	특이사항</th>
			<td id="remark">${workVO.remark}</td>
		</tr>
		<tr>
			<th>팀장/<br>안전관리자<br>특이사항</th>
			<td id="remark">${workVO.remark_leader}</td>
		</tr>
		<tr>
			<th>현장소장<br> 특이사항</th>
			<td id="remark">${workVO.remark_chief}</td>
		</tr>
	</table>
	
	
	<c:if test = "${fromCEO == false}">
		<div class="paging">
			<span class="btn_typ02 toHomePage">목록</span>&nbsp;
			
			<c:if test="${canModify == true}">
				<span class="btn_typ02" onclick="updateSubmit();">수정</span>
			</c:if>
		</div>
	</c:if>
	<!-- class="goTop">
		<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a-->
	<br>
	</form:form>
	
<!-- 임시. ceo화면에서 상세보기 했을때 back -->
<c:if test = "${fromCEO == true}">
	<div onclick="history.back(-1)" style="cursor:pointer"><img src="images/back_btn.png" width="30px" height="30px"> back</div>
	<br>
</c:if>
<div id="form_group">
	<form id="updateForm" action="registerWork" method="POST" >
		<input id="updateIdx" type="hidden" name="updateIdx" value="${workVO.work_idx}"/>
	</form>
</div>

	<%@ include file="IncludeBottom.jsp"%>