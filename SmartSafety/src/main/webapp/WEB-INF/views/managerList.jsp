<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
$(document).ready(function() {
	var mLevel = ${listLevel} ;
	//listLevel값 지정
	$("#mSelectBox").val(mLevel).attr("selected", "selected");
	
});

function updateSubmit(val) {
	var idx = $("#updateIdx_"+val).val();	
	$("#updateIdx").val(idx);
	$('#updateForm').submit();
}

function registerSubmit() {
	$('#registerForm').submit();
}

function goPage(val) {
	$('#searchWord').val($('#searchInput').val());
	$('#pageNum').val(val);
	$('#searchForm').submit();
}

function chgLevel(val) {
	$('#listLevel').val(val);
	$('#changeLevelForm').submit();
}


</script>
<div id="listLevelBox">
<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
		<option value="1" >본사 관리자(EHS팀)</option>
		<option value="2" >현장 안전관리자</option>
</select>
</div>
	<div class="srchbox">
		<p>		
			<input id="searchInput" type="search"  title="검색창" class="search_input" autocomplete="off" value="${searchWord}" onkeypress="if(event.keyCode=='13') goPage(1)">
			<a href="#"><span class="btn_search" onclick="goPage(1)">&nbsp;</span></a>
			
		</p>
	</div>
	

	<!-- //srchbox -->
	<table>
		<colgroup>
			<col style="width: 15%">
			<col style="width: 15%">
			<col style="width: 30%">
			<col style="width: 15%">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">이름</th>
				<th scope="col">소속직급</th>
				<th scope="col">연락처</th>
				<th scope="col">ID</th>
				<th scope="col">정보수정</th>

			</tr>
		<thead>
		<tbody>
			<c:forEach var="manager" items="${managerList}" varStatus="idx">
				<tr>
					<td>${manager.name}</td>
					<td>${manager.grade}</td>
					<td>${manager.phone}</td>
					<td>${manager.id}</td>
					<td>
						<input id="updateIdx_${idx.index}" type="hidden" value="${manager.manager_idx}"/>
						<img src="images/btn_info.png" onmouseover="this.src='images/btn_info_over.png'"   onmouseout="this.src='images/btn_info.png'" 
						onclick="updateSubmit('${idx.index}')" alt="정보수정하기" style="cursor:pointer" >
					
						
					</td>
				</tr>		
			</c:forEach>	
		</tbody>
	</table>

<div class="paging">
    <a href="javascript:goPage(${paging.firstPageNo})"  class="first"><span> &lt;&lt; </span></a>
    <a href="javascript:goPage(${paging.prevPageNo})" class="prev"><span> &lt; </span></a>
    <span>
        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
            <c:choose>
                <c:when test="${i eq paging.pageNo}"><a href="javascript:goPage(${i})" class="now"><span>${i}</span></a></c:when>
                <c:otherwise><a href="javascript:goPage(${i})"><span>${i}</span></a></c:otherwise>
            </c:choose>
        </c:forEach>
    </span>
    <a href="javascript:goPage(${paging.nextPageNo})" class="next"><span> &gt; </span></a>
    <a href="javascript:goPage(${paging.finalPageNo})" class="last"><span> &gt;&gt; </span></a>
    
    <span class="signup"><a	href="#"><img src="images/btn_signup.png" onmouseover="this.src='images/btn_signup_over.png'"	
    onmouseout="this.src='images/btn_signup.png'" alt="등록하기" onclick="registerSubmit()"></a></span>
</div>


<div id="form_group" style="display:none">
	<form id="searchForm" action="managerList" action="GET" >
		<input id="searchWord" name="searchWord" type="hidden">
		<input id="pageNum" name="pageNum" type="hidden">
		<input  type="hidden" name="listLevel" value="${listLevel}"/>
	</form>
	
	<form id="changeLevelForm" action="managerList" method="GET" >
		<input id="listLevel" type="hidden" name="listLevel"/>
	</form>
	
	<form id="updateForm" action="registerManager" method="POST" >
		<input id="updateIdx" type="hidden" name="updateIdx"/>
	</form>
	
	<form id="registerForm" action="registerManager" action="POST" >
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>