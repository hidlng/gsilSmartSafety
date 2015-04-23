<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>



<%@ include file="CategoryNavigator.jsp"%>
<script>
	$(document).ready(function() {
		//$('#catType1').attr("checked", true);
		$('#inputCode').val('');
		$('#inputName').val('');
		$('#contentCode').val('');
		
		$('.inputCodeDiv').show();
		$('#catType1').attr("checked", true);
		
		$('tr:even').addClass('even');
		$('tr:odd').addClass('odd');
		
		
		
	});

	
	function checkDelete(contentCode) {
		var input = confirm('정말 삭제 하시겠습니까? [CODE : ' + contentCode + ']');
		if(input) {				
			$('#contentCodeHid_' + contentCode).val(contentCode);
			$('#deleteCodeBtn_' + contentCode).click();
		}else 
			return;
	}
	
	$(document).on("click", ".detailLink", function(e) {
		e.preventDefault();
		$.ajax({
			type : "POST",
			url : this.href,
			cache : false,
			success : onSuccess,
			error : onError
		});
	});
	
	function isAuthenticated() {
		$.ajax({
			type : "POST",
			url : this.href,
			cache : false,
			success : function(json) {
				alert(json);
				return true;
			},
			error : onError
		});
		
	}
	
	function onSuccess(json, status) {
		$('#viewContent').html(json);
		$('tr:even').addClass('even');
		$('tr:odd').addClass('odd');
		
		doOverlayOpen();	
	}

	function onError(data, status) {
		alert("error : " + status);
	}
	
	function closeViewContent() {
		$('#viewContent').html('');
		doOverlayClose();
		
	}
	

	
	function showInputCode(bool) {
		if(bool){
			$('.inputCodeDiv').show();
		} else {
			$('.inputCodeDiv').hide();
		} 
	}
	

	function submitCode(){
		
		if($('.inputCodeDiv').css("display") != "none") {//code입력모드
			if($('#inputCode').val() == '' || $('#inputName').val() == '')
				alert('내용을 입력해야합니다');
			else
				$('#categorySubmit').click();
		
		}else{ 		//category입력모드
			if($('#inputName').val() == '')
				alert('내용을 입력해야합니다');
			else
				$('#categorySubmit').click();
		}
			
	}
	


</script>
<!-- The dark background -->



<!--div onclick="insertOverlap('1')">등록하기</div-->
<!-- insert -->
<div id="insertCategoryDiv">
	<stripes:form id="insertCategoryForm" name="insertCategory"
		beanclass="com.spring.risk.web.actions.CategoryActionBean" >
		
		<img src="../images/blue_title.gif"/> 코드/카테고리 등록
		<!--<stripes:submit id="catsub" name="catsub" value="추가" class="button" />-->
		<table>
			<tr>
				
				<th class="codeCol">종류</th>
				<th class="inputCodeDiv">코드</th>
				<th>이름</th>
				<th>추가</th>
			</tr>
			<!-- 작업만 카테고리 등록 가능하도록.. 장비/공도구는 SmartSafety와 코드를 맞춰야하고, 장소 및 사고유형은 1단게만 필요함-->
			<c:if test="${actionBean.lastIdx == 2 || actionBean.lastIdx == 3  || actionBean.lastIdx == 4 }">
			<td>코드</td>
				<td><stripes:text id="inputCode" name="inputCode" size="10" onkeyup="checkSpeChar(1)"/></td>
				<td><stripes:text id="inputName" name="inputName" size="16" onkeyup="checkSpeChar(2)"/></td>
				<td><stripes:submit class="button" name="insertCategory" value="추가" /></td>
			</c:if>
			<!-- 작업등록 -->
			<c:if test="${actionBean.lastIdx != 2 && actionBean.lastIdx != 3 && actionBean.lastIdx != 4}">
			<tr>
			<td>
				<stripes:radio id="catType1" name="catType" value="code" onclick="showInputCode(true)"	  checked="checked"/>코드
				<stripes:radio id="catType2" name="catType" value="category"  onclick="showInputCode(false)" />카테고리
			</td>	
			<td class="inputCodeDiv"><stripes:text id="inputCode" name="inputCode" size="10" onkeyup="checkSpeChar(1)"/></td>
			<td><stripes:text id="inputName" name="inputName" size="10" onkeyup="checkSpeChar(2)"/></td>
			<td>
			<stripes:button class="button" name="insertCategory" value="추가" onclick="submitCode()"/>
			<stripes:submit class="button" id="categorySubmit" name="insertCategory" value="추가" style="display:none"/></td>
			</tr>
			</c:if>
		</table>
		<div>${resultMsg}</div>

	</stripes:form>
</div>

<!-- search  -->
<!-- 코드  및 코드명 통합검색-->
<div id="searchCode">
<stripes:form id="search" name="search"
	beanclass="com.spring.risk.web.actions.CategoryActionBean">
	<stripes:text name="searchString" onkeyup="checkSpeChar(2)"/><stripes:submit class="button" name="searchCode" value="검색" />
</stripes:form>
</div>

<!-- List -->
<div id="categoryList">
<div id="categoyListTitle"><img src="../images/blue_title.gif"/> 리스트</div>
	<table id="categoryListTable">		
		<colgroup>
		<col style="width: 40%">
		<col style="width: 15%">
		<col style="width: 30%">
		<col>
		<col style="width: 5%">
		</colgroup>
		<tr>
			<th  class="pathCol">카테고리</th>
			<th class="codeCol">코드</th>
			<th>명칭</th>
			<th class="buttonCol">상세</th>
			<th class="buttonCol">삭제</th>
		</tr>
		<stripes:form id="listForm"
			beanclass="com.spring.risk.web.actions.CategoryActionBean">
			<c:forEach var="row" items="${actionBean.codeList}" varStatus="idx">
				<tr>
				<td><stripes:link beanclass="com.spring.risk.web.actions.CategoryActionBean">
					<stripes:param name="lastIdx">${row.category}</stripes:param>
					 ${row.path}
					</stripes:link>
				</td>
				<td>${row.code}</td>
				<td>${row.name}</td>
				<td>
					<c:if test="${actionBean.lastIdx != 4 }">
						<c:if test="${row.registered == true}">
							<stripes:link class="detailLink"
									beanclass="com.spring.risk.web.actions.CategoryActionBean"
									event="viewDetail">
									<stripes:param name="contentCode">${row.code}</stripes:param>보기</stripes:link>
						</c:if>
						<c:if test="${row.registered == false}">
							<stripes:link class="detailLink"
									beanclass="com.spring.risk.web.actions.CategoryActionBean"
									event="viewInsertForm">
									<stripes:param name="contentCode">${row.code}</stripes:param>등록</stripes:link>
						</c:if>
					</c:if>
				</td>
				<td><stripes:form id="deleteForm" method="POST"
							beanclass="com.spring.risk.web.actions.CategoryActionBean">
							<stripes:hidden id="contentCodeHid_${row.code}" name="contentCode"/>
							<stripes:submit  class="button" id="deleteCodeBtn_${row.code}" name="deleteCode" value="삭제" style="display:none"/>
							<stripes:button  class="button" name="deleteBtn" value="삭제" onclick="checkDelete('${row.code}')"></stripes:button>
							</stripes:form>
				</td>
				
				</tr>
			</c:forEach>
		</stripes:form>
	</table>
</div>

<div id = "paging">
		<c:forEach begin="1" end="${actionBean.totalPage}" varStatus="num">
			 <stripes:link class="pageNum" beanclass="com.spring.risk.web.actions.CategoryActionBean"	event="paging">
				 <c:if test="${actionBean.pageNum == num.index}">
				 	<strong>${num.index}</strong>
				 </c:if>
				 <c:if test="${actionBean.pageNum !=num.index}">
				  	${num.index}
				 </c:if>
				 <stripes:param name="pageNum">${num.index}</stripes:param>
						 
			</stripes:link>
		</c:forEach>
						
</div>

<div class="bgCover">&nbsp;</div>
<!-- overlay box -->
<div class="overlayBox">
<a href=# class="closeLink" ><img src="../images/x-button.png" onclick="closeViewContent()" style="cursor:hand"/></a>
<div class="overlayContent"><div id="viewContent"></div></div>
</div>



<!--div  onclick="doOverlayOpen()" class="launchLink" >test OverLay</div-->




<div><img src="../images/blue_title.gif"/> 결과 메시지(임시)</div>
<div>${actionBean.resultMsg}</div>

<%@ include file="IncludeBottom.jsp"%>