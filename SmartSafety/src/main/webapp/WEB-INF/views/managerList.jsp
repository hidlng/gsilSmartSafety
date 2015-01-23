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
		<option value="1" >본사 관리자</option>
		<option value="2" >현장 안전관리자</option>
		<option value="3" >CEO</option>
</select>
</div>
	<div class="srchbox">
		<p>		
			<input id="searchInput" type="search"  title="검색창" class="search_input" autocomplete="off" value="${searchWord}" onkeypress="if(event.keyCode=='13') goPage(1)">
			<a href="#"><span class="btn_search" onclick="goPage(1)">&nbsp;</span></a>
			
		<br>
	</div>
	

	<!-- //srchbox -->
	<table>
		<colgroup>
			<col style="width: 30%">
			<col style="width: 10%">
			<col style="width: 15%">
			<col style="width: 27%">
			<col>
		</colgroup>
		<thead>
			<tr>				
				<th scope="col">소&nbsp;속&nbsp;사</th>
				<th scope="col">직&nbsp;급</th>
				<th scope="col">이&nbsp;름</th>
				<th scope="col">연&nbsp;락&nbsp;처</th>
				<th scope="col">아&nbsp;이&nbsp;디</th>
			</tr>
		<thead>
		<tbody>
			<c:forEach var="manager" items="${managerList}" varStatus="idx">
				<tr class="listTR"  onclick="updateSubmit('${idx.index}')">					
					<td>${manager.position}</td>
					<td>${manager.grade}</td>
					<td>${manager.name}</td>
					<td>${manager.phone}</td>
					<td>${manager.id}</td>
					<td style="display:none">
						<input id="updateIdx_${idx.index}" type="hidden" value="${manager.manager_idx}"/>
						<!--<span class="signup"><span class="btn_typ01"  onclick="updateSubmit('${idx.index}')">수정</span></span>-->
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
    
    <span class="signup"><span class="btn_typ02"  onclick="registerSubmit()">등록 ></span></span>
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