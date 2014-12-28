<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
 
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
				<span class="btn_typ01"  onclick="addTool('cons_machine', true);">추가</span>
			</td>
			<td id="trans_machine">
				<span class="btn_typ01"  onclick="addTool('trans_machine', true);">추가</span>
			</td>
			<td id="etc_machine">
				<span class="btn_typ01"  onclick="addTool('etc_machine', true);">추가</span>
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
				<span class="btn_typ01"  onclick="addTool('weld_tool', true);">추가</span>
			</td>
			<td id="elec_tool">
				<span class="btn_typ01"  onclick="addTool('elec_tool', true);">추가</span>
			</td>
			<td id="nelec_tool">
				<span class="btn_typ01"  onclick="addTool('nelec_tool', true);">추가</span>				
			</td>
			<td id="etc_tool">
				<span class="btn_typ01"  onclick="addTool('etc_tool', true);">추가</span>
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
		<span class="signup"><span class="btn_typ02"  onclick="submitWork()">목록으로 ></span>	</span>
	</div>
	<p class="goTop">
		<a href="#"><img src="images/icon_top.png" alt="top으로 가기">&nbsp;</a>
	</p>



</form:form>

<%@ include file="IncludeBottom.jsp"%>