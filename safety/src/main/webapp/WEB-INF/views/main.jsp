<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>


<div >
<h2>로그인 성공 </h2>

이름 : ${sessionScope.userLoginInfo.id}
					
<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>

<a href="page1">세션테스트</a>

<form action="test" >
<input type="submit" name="test" value ="test">
</form>


<a href="managerList">관리자리스트</a>
<a href="contractorList">업체리스트</a>

</div>
<%@ include file="IncludeBottom.jsp"%>