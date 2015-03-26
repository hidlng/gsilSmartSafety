<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
$(document).ready(function() {
	var mType = ${type} ;
	$("#mSelectBox").val(mType).attr("selected", "selected");
	
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
	$('#type').val(val);
	$('#changetypeForm').submit();
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
<div id="listLevelBox">
<select id="mSelectBox" class="selectBox" onchange="chgLevel(this.value)">
		<option value="0" >건축</option>
		<option value="1" >인프라</option>
</select>
</div>
<div>

<div class="srchbox">

	<p><span class="searchReq">현장명 : </span> 
		<input id="searchInput" type="search" title="검색창" class="search_input"
			autocomplete="off" value="${searchWord}"
			onkeypress="if(event.keyCode=='13') goPage(1)"> <a href="#"><span
			class="btn_search" onclick="goPage(1)">&nbsp;</span></a>

	</p>
</div>
</div>


<!-- //srchbox -->
<table>
	<colgroup>
		<col style="width: 8%">
		<col style="width: 12%">
		<col style="width: 55%">
		<col>
		
	</colgroup>
	<thead>
		<tr>
			<th scope="col">NO.
			</th>
			<th scope="col">종&nbsp;류</th>
			<th scope="col">현&nbsp;장&nbsp;명</th>
			<th scope="col">공사기간</th>
		</tr>
	<thead>
	<tbody>
		<c:forEach var="site" items="${siteList}" varStatus="idx">
			<tr onclick="updateSubmit('${idx.index}')" class="listTR">
				<!-- <td>${site.sitename}</td>-->
				<td>${((paging.pageNo - 1) * paging.pageSize) + (idx.index + 1) } </td>
				<td>
					<c:if test="${site.type == 0}">건축</c:if>
					<c:if test="${site.type == 1}">인프라</c:if>
				</td>
				<td>${site.sitename}</td>
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
				<c:when test="${i == paging.pageNo}"><a href="javascript:goPage(${i})" ><span class="now">${i}</span></a></c:when>
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
		<input id="searchWord" name="searchWord" type="hidden">
		<input id="pageNum" name="pageNum" type="hidden">
		<input name="type" value="${type}" type="hidden">
	</form>
	<form id="updateForm" action="registerSite" method="POST">
		<input id="updateIdx" type="hidden" name="updateIdx" />
	</form>

	<form id="registerForm" action="registerSite" action="POST"></form>
	<form id="changetypeForm" action="siteList" action="POST">
		<input id="type" name="type" type="hidden">
	</form>
	
</div>

<%@ include file="IncludeBottom.jsp"%>