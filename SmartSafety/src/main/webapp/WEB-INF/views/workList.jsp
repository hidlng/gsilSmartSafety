<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
function updateSubmit(val) {
	var idx = $("#updateIdx_"+val).val();	
	$("#updateIdx").val(idx);
	$('#updateForm').submit();
}

function viewSubmit(val) {
	var idx = $("#updateIdx_"+val).val();
	$("#viewIdx").val(idx);
	$('#viewForm').submit();
	
}
function workSubmit(val) {
/* 	var user_idx = '${sessionScope.userLoginInfo.user_idx}';
	
	if(user_idx == work_idx)
		updateSubmit(val);
	else */
	viewSubmit(val);
}

function registerSubmit() {
	$('#registerForm').submit();
}

function goPage(val) {
	$('#searchWord').val($('#searchInput').val());
	$('#pageNum').val(val);
	$('#searchForm').submit();
}


</script>
	<div class="srchbox">
		<p>		
			<input id="searchInput" type="search"  title="검색창" class="search_input" autocomplete="off" value="${searchWord}" onkeypress="if(event.keyCode=='13') goPage(1)">
			<a href="#"><span class="btn_search" onclick="goPage(1)">&nbsp;</span></a>
			
		</p>
	</div>
	

	<!-- //srchbox -->
<table>
	<colgroup>
		<col style="width: 6%">
		<col style="width: 15%">
		<col style="width: 30%">
		<col style="width: 20%">		
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">공종</th>
			<th scope="col">작업타이틀</th>
			<th scope="col">작업기간</th>
			<th scope="col">작성자</th>
		</tr>
	<thead>
	<tbody>
		<c:forEach var="work" items="${workList}" varStatus="idx">
			<c:if test='${work.ischarge.equals("Y")}'><tr class="chargeWorkTR listTR"  onclick="workSubmit('${idx.index}')"></c:if> <!--  돌관작업 -->
			<c:if test='${!work.ischarge.equals("Y")}'><tr class="listTR" onclick="workSubmit('${idx.index}')"></c:if>
			
				<td>${((paging.pageNo - 1) * paging.pageSize) + (idx.index + 1) }</td>
				<td>${work.worktype}</td>
				<td>${work.worktitle}</td>
				<td>${work.startdate} ~<p> ${work.enddate}</td>
				<td>${work.username}</td>
				<td style="display:none">
				<input id="updateIdx_${idx.index}" type="hidden" value="${work.work_idx}" />
				
				
			
				</td>
			</tr>
		</c:forEach>
	</tbody>

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
    
    	<c:if test ="${sessionScope.userLoginInfo.level >= 3}"> <!-- 현장사용자/업체만 등록가능 -->
	    	<span class="signup"><span class="btn_typ02"  onclick="registerSubmit()">등록 ></span></span>
	    </c:if>
</div>


<div id="form_group">
	<form id="searchForm" action="workList" action="GET" >
		<input id="searchWord" name="searchWord" type="hidden">
		<input id="pageNum" name="pageNum" type="hidden">
	</form>
	<!-- <form id="updateForm" action="registerWork" method="POST" >
		<input id="updateIdx" type="hidden" name="updateIdx"/>
	</form>	 -->
	<form id="registerForm" action="registerWork" action="POST" >
	</form>
	<form id="viewForm" action="viewWork" action="POST" >
		<input id="viewIdx" type="hidden" name="viewIdx"/>
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>