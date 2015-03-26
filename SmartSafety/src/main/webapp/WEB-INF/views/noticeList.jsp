<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
$(document).ready(function() {
	
});

function viewSubmit(val) {
	var idx = $("#viewIdx_"+val).val();	
	$("#viewIdx").val(idx);
	$('#viewForm').submit();
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

</div>
	<!--div class="srchbox">
		<p>		
			<input id="searchInput" type="search"  title="검색창" class="search_input" autocomplete="off" value="${searchWord}" onkeypress="if(event.keyCode=='13') goPage(1)">
			<a href="#"><span class="btn_search" onclick="goPage(1)">&nbsp;</span></a>
			
		<br>
	</div-->
	

	<!-- //srchbox -->
	<table>
		<colgroup>
			<col style="width: 8%">
			<col style="width: 50%">
			<col style="width: 14%">
			<col>
		</colgroup>
		<thead>
			<tr>				
				<th scope="col">NO.</th>
				<th scope="col">제&nbsp;&nbsp;목</th>
				<th scope="col">작성자</th>
				<th scope="col">작성일자</th>
			</tr>
		<thead>
		<tbody>
			<c:forEach var="notice" items="${noticeList}" varStatus="idx">
				<tr class="listTR"  onclick="viewSubmit('${idx.index}')">			
					<td>${((paging.pageNo - 1) * paging.pageSize) + (idx.index + 1) }</td>		
					<td>${notice.title}</td>
					<td>${notice.writer_name}</td>
					<td>${notice.writetime.substring(0,10)}</td>
					<td style="display:none">
						<input id="viewIdx_${idx.index}" type="hidden" value="${notice.notice_idx}"/>
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
                <c:when test="${i == paging.pageNo}"><a href="javascript:goPage(${i})" ><span class="now">${i}</span></a></c:when>
                <c:otherwise><a href="javascript:goPage(${i})"><span>${i}</span></a></c:otherwise>
            </c:choose>
        </c:forEach>
    </span>
    <a href="javascript:goPage(${paging.nextPageNo})" class="next"><span> &gt; </span></a>
    <a href="javascript:goPage(${paging.finalPageNo})" class="last"><span> &gt;&gt; </span></a>
    
    <span class="signup"><span class="btn_typ02"  onclick="registerSubmit()">등록 ></span></span>
</div>


<div id="form_group" style="display:none">
	<form id="searchForm" action="noticeList" action="GET" >
		<input id="searchWord" name="searchWord" type="hidden">
		<input id="pageNum" name="pageNum" type="hidden">
	</form>
	
	<form id="viewForm" action="viewNotice" method="POST" >
		<input id="viewIdx" type="hidden" name="viewIdx"/>
	</form>
	
	<form id="registerForm" action="registerNotice" action="POST" >
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>