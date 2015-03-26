<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
	function updateSubmit(val) {
		var idx = $("#updateIdx_" + val).val();
		$("#updateIdx").val(idx);
		$('#updateForm').submit();
	}

	function registerSubmit() {
		var canRegister = ${canRegister};
		if(canRegister){
			$('#registerForm').submit();
		}else
			alert('업체를 먼저 등록하시기 바랍니다.');
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
		<col style="width: 15%">		
		<col style="width: 30%">
		<col>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">권&nbsp;&nbsp;한</th>
			<th scope="col">이&nbsp;&nbsp;름</th>			
			<th scope="col">연&nbsp;락&nbsp;처</th>
			<th scope="col">아&nbsp;이&nbsp;디</th>
			
		</tr>
	<thead>
	<tbody>
		<c:forEach var="manager" items="${managerList}" varStatus="idx">
			<tr class="listTR"  onclick="updateSubmit('${idx.index}')">
				<td>
					<c:if test ="${manager.level == 4}">현장소장</c:if>
					<c:if test ="${manager.level == 5}">팀장</c:if>
					<c:if test ="${manager.level == 6}">감독자</c:if>
				</td>
				<td>${manager.name}</td>				
				<td>${manager.phone}</td>
				<td>${manager.id}</td>
				<td><input id="updateIdx_${idx.index}" type="hidden"	value="${manager.manager_idx}" />
					<!-- <span class="signup"><span class="btn_typ01"  onclick="updateSubmit('${idx.index}')">수정</span></span>-->	
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


<div id="form_group" style="display: none">
	<form id="searchForm" action="siteUserList" action="GET">
		<input id="searchWord" name="searchWord" type="hidden"> <input
			id="pageNum" name="pageNum" type="hidden"> <input
			type="hidden" name="listLevel" value="${listLevel}" />
	</form>

	<form id="updateForm" action="registerSiteUser" method="POST">
		<input id="updateIdx" type="hidden" name="updateIdx" />
	</form>

	<form id="registerForm" action="registerSiteUser" action="POST">
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>