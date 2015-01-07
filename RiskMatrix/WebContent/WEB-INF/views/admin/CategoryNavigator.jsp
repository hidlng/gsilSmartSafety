<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="stripes"	uri="http://stripes.sourceforge.net/stripes.tld"%>
<script>
      function chgValue(val) {
   	   $('#lastIdx').val(val);    	
   	   $('#getCategory').submit();   
   	}
 	 
 </script>
 
	   	
<div id ="categoryNavigator">
<stripes:form  id="getCategory" name="getCategory"
		beanclass="com.spring.risk.web.actions.CategoryActionBean" method="POST">

	<stripes:hidden id="lastIdx" name="lastIdx" value=""  />	
	
	<!-- Category Navgation -->	
	<div id="categorySelectWrap">
			<img src="../images/blue_title.gif"/> 카테고리 선택 >> 
			<c:forEach var="list" items="${actionBean.totalList}" varStatus="count">		
				<!--div class="select open" style="width:200px;">
				<span class="ctrl"><span class="arrow"></span></span>
				<button type="button" class="myValue">${actionBean.parentList[count.index].name}</button-->
				<!--ul class="aList"-->
				<stripes:select class="naviSelect" name="parentList[${count.index}].idx"  onchange="chgValue(this.value)">			
				<c:if test="${list.size() > 0}">
					<c:forEach begin="0" end="${list.size() - 1}" varStatus="i">						
						<stripes:option value="${list[i.index].idx}" label="${list[i.index].idx} : ${list[i.index].name}"/>
						<!--li onclick="chgValue(this.value)" value="${list[i.index].idx}">${list[i.index].idx} : ${list[i.index].name}</li-->
					</c:forEach>
				</c:if>
				</stripes:select>
				<!--/ul>
				</div-->
				>
			</c:forEach>
	</div>
	<!--  마지막으로 선택한 카테고리의 하위 자식들 출력 . 나중에는 코드도 출력 할 것-->
	<div id="categoryChildWrap">	
		<c:if test="${actionBean.childList.size() > 0}">
			
		</c:if>			
		<ul class="childWrapUL">
				<c:forEach var="child" items="${actionBean.childList}" >
					<li class="childWrap" onclick="chgValue('${child.idx}')"> ${child.name}
					<!--
					<stripes:link  class="deleteCategory" beanclass="com.spring.risk.web.actions.CategoryActionBean" 	event="deleteCategory"> <stripes:param name="deleteCategoryIdx">${child.idx}</stripes:param>X</stripes:link>
					-->
					</li>
					<li style="float:left">  | &nbsp;&nbsp;</li>				
				</c:forEach>
			</ul>
		
	</div>	
	</stripes:form>
</div>