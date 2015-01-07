<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
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
/* 
$(document).on("click", ".detailLink", function(e) {
	e.preventDefault();
	$.ajax({
		type : "POST",
		url : this.href,
		cache : false,
		success : onSuccess,
		error : onError
	});
}); */


</script>
<div class="srchbox">
	<p>
		<input id="searchInput" type="search" title="검색창" class="search_input"
			autocomplete="off" value="${searchWord}"
			onkeypress="if(event.keyCode=='13') goPage(1)"> <a href="#"><span
			class="btn_search" onclick="goPage(1)">&nbsp;</span></a>

	</p>
</div>


<!-- //srchbox -->
<table>
	<colgroup>
		<col style="width: 8%">
		<col style="width: 25%">
		<col style="width: 17%">
		<col style="width: 20%">
		<col>
		
	</colgroup>
	<thead>
		<tr>
			<th scope="col">NO.
			</th>
			<th scope="col">현장명</th>
			<th scope="col">대표<br>관리자</th>
			<th scope="col">연락처</th>
			<th scope="col">작업기간</th>
		</tr>
	<thead>
	<tbody>
		<c:forEach var="site" items="${siteList}" varStatus="idx">
			<tr onclick="updateSubmit('${idx.index}')" class="listTR">
				<!-- <td>${site.sitename}</td>-->
				<td>${idx.index + 1} </td>
				<td>${site.sitename}</td>
				<td>${site.rep_name}</td>
				<td>${site.rep_phone}</td>
				<td>${site.starttime}~
					<p>${site.endtime}
				</td>
				<td style="display:none"><input  type="hidden" id="updateIdx_${idx.index}"	value="${site.site_idx}" />
					<!--<span class="signup"><span class="btn_typ01"  >수정</span></span>-->	
				</td>
				
				
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="paging">
	<a href="javascript:goPage(${paging.firstPageNo})" class="first"><span>
			&lt;&lt; </span></a> <a href="javascript:goPage(${paging.prevPageNo})"
		class="prev"><span> &lt; </span></a> <span> <c:forEach var="i"
			begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
			<c:choose>
				<c:when test="${i eq paging.pageNo}">
					<a href="javascript:goPage(${i})" class="now"><span>${i}</span></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:goPage(${i})"><span>${i}</span></a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</span> <a href="javascript:goPage(${paging.nextPageNo})" class="next"><span>
			&gt; </span></a> <a href="javascript:goPage(${paging.finalPageNo})" class="last"><span>
			&gt;&gt; </span></a>
			<span class="signup"><span class="btn_typ02"  onclick="registerSubmit()">등록 ></span></span>
</div>


<div id="form_group">
	<form id="searchForm" action="siteList" action="GET">
		<input id="searchWord" name="searchWord" type="hidden"> <input
			id="pageNum" name="pageNum" type="hidden">
	</form>
	<form id="updateForm" action="registerSite" method="POST">
		<input id="updateIdx" type="hidden" name="updateIdx" />
	</form>

	<form id="registerForm" action="registerSite" action="POST"></form>
</div>

<%@ include file="IncludeBottom.jsp"%>