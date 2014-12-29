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
		<col style="width: 30%">
		<col style="width: 25%">
		<col style="width: 15%">
		<col style="width: 18%">
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">업체명</th>
			<th scope="col">해당작업</th>
			<th scope="col">대표관리자</th>
			<th scope="col">연락처</th>
			<th scope="col">정보수정</th>

		</tr>
	<thead>
	<tbody>
		<c:forEach var="contractor" items="${contractorList}" varStatus="idx">
			<tr>
				<td>${contractor.cont_name}</td>
				<td>${contractor.cont_work}</td>
				<td>${contractor.rep_name}</td>
				<td>${contractor.rep_phone}</td>
				<td><input id="updateIdx_${idx.index}" type="hidden"
					value="${contractor.cont_idx}" /> 
					<span class="signup"><span class="btn_typ01"  onclick="updateSubmit('${idx.index}')">수정</span></span>
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
	<form id="searchForm" action="contractorList" action="GET">
		<input id="searchWord" name="searchWord" type="hidden"> <input
			id="pageNum" name="pageNum" type="hidden"> <input
			type="hidden" name="listLevel" value="${listLevel}" />
	</form>
	<form id="updateForm" action="registerContractor" method="POST">
		<input id="updateIdx" type="hidden" name="updateIdx" />
	</form>

	<form id="registerForm" action="registerContractor" action="POST">
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>