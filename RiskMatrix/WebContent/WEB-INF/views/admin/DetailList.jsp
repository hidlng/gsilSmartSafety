<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div id="viewDetailTitle"><img src="../images/blue_title.gif"/>세부사항</div>
<div id ="totalList">
<stripes:form name="insertDetail"
	beanclass="com.spring.risk.web.actions.CategoryActionBean"
	enctype="multipart-form/data" method="POST">



	<!--Work-->
		<c:if test="${actionBean.codeType == 'WORK'}">
			<table>
				<tr>
					<th class="detailHeader">작업코드</th>
					<td colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.workCode}</td>
				</tr>
				<tr>
					<th class="detailHeader">작업명</th>
					<td colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.workName}</td>
				</tr>
				<tr>
					<th class="detailHeader">사고유형</th>
					<c:forEach var="acc" items="${actionBean.inputAccList}">
						<td>${acc.accName}</td>
					</c:forEach>

				</tr>
				<tr>
					<th class="detailHeader">사고가능성</th>
					<c:forEach var="acc" items="${actionBean.inputAccList}">
						<td>${acc.accPoss}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="detailHeader">잠재심각성</th>
					<c:forEach var="acc" items="${actionBean.inputAccList}">
						<td>${acc.accSerious}</td>
					</c:forEach>
				</tr>
				<tr>
					<th class="detailHeader">안전작업가이드</th>
					<td class="wordBreak" colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.guide}</td>
				</tr>
				<tr>
					<th class="detailHeader">보호구</th>
					<td class="wordBreak" colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.equip}</td>
				</tr>
				<tr>
					<th class="detailHeader">이상징후/<br>비상시조치사항
					</th>
					<td class="wordBreak" colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.measure}</td>
				</tr>
				<tr>
					<th class="detailHeader">안전 조치 사항</th>
					<td class="wordBreak" colspan="${actionBean.inputAccList.size()}">${actionBean.workVO.safety}</td>
				</tr>
				<tr><th class="detailHeader">사고사례</th>					
					<td colspan="${actionBean.inputAccList.size()}"><c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
							<stripes:link	beanclass="com.spring.risk.web.actions.CategoryActionBean"	event="getFile">
								<stripes:param name="fileIdx" value="${file.file_idx}" />
								${file.fileName}<br>
							</stripes:link>
						</c:forEach>
					</td>
				</tr>
				
			</table>
			
		</c:if>

	<!--Tool-->
	<c:if test="${actionBean.codeType == 'TOOL'}">
	<table>
			<tr><th class="detailHeader">장비/공도구코드</th><td>${actionBean.toolVO.toolCode}</td></tr>
			<tr><th class="detailHeader">장비/공도구명</th><td>${actionBean.toolVO.toolName}</td></tr>
			<tr><th class="detailHeader">주요위험</th><td class="wordBreak">${actionBean.toolVO.mainRisk}</td></tr>
			<tr><th class="detailHeader">안전작업가이드</th><td class="wordBreak">${actionBean.toolVO.guide}</td></tr>
			<tr><th class="detailHeader">보호구</th><td class="wordBreak">${actionBean.toolVO.equip}</td></tr>
			<tr><th class="detailHeader">사용전 점검<br> 체크리스트</th><td >
				<div class="checkListDiv">
					<c:forEach var="check" items="${actionBean.toolVO.checkList}">
							<c:if test="${check.image != null}">
							<img class="checkListImage" src="Data.action?getChekcListImage=&filename=${check.virtName}"/>
							</c:if>
						 	${check.checklist}
						 	<p/>
						 	
					</c:forEach>
				</div>
			</td></tr>
			<tr><th class="detailHeader">장비이미지</th><td class="wordBreak">
				<img class="toolImage" src="Data.action?getToolImage=&filename=${actionBean.toolVO.imgVirtName}"/>
				</td>
			</tr>
			<tr><th class="detailHeader">사고사례</th>
				<!-- file list -->
				<td><c:forEach var="file" items="${actionBean.fileList}"
						varStatus="fileIdx">
						<stripes:link
							beanclass="com.spring.risk.web.actions.CategoryActionBean"
							event="getFile">
							<stripes:param name="fileIdx" value="${file.file_idx}" />
							${file.fileName}<br>						
						</stripes:link>		
					</c:forEach>
				</td>
			</tr>
	</table>
	
	</c:if>

	<!--Place-->
	<c:if test="${actionBean.codeType == 'PLACE'}">
	<table>
		<tr><th class="detailHeader">장소코드</th><td>${actionBean.placeVO.placeCode}</td></tr>
		<tr><th class="detailHeader">장소명</th><td>${actionBean.placeVO.placeName}</td></tr>
		<tr><th class="detailHeader">주요위험</th><td class="wordBreak">${actionBean.placeVO.mainRisk}</td></tr>
		<tr><th class="detailHeader">안전작업가이드</th><td class="wordBreak">${actionBean.placeVO.guide}</td></tr>
		<tr><th class="detailHeader">보호구</th><td class="wordBreak">${actionBean.placeVO.equip}</td></tr>
		<tr><th class="detailHeader">사고사례</th><td><c:forEach var="file" items="${actionBean.fileList}"
					varStatus="fileIdx">
					<stripes:link
						beanclass="com.spring.risk.web.actions.CategoryActionBean"
						event="getFile">
						<stripes:param name="fileIdx" value="${file.file_idx}" />
					${file.fileName}
				</stripes:link>
				</c:forEach></td></tr>
	</table>

	</c:if>

	<!--Acc-->
	<c:if test="${actionBean.codeType == 'ACC'}">
	</c:if>
	
	
	<div class="updateBtnDiv"><stripes:link
							class="detailLink button"
							beanclass="com.spring.risk.web.actions.CategoryActionBean"
							event="updateDetailForm">
							<stripes:param name="contentCode">${contentCode}</stripes:param>수정</stripes:link>							
	</div>
	<!--button id="closeViewBtn" onclick="closeViewContent()">닫기</button-->
	
							
	<br>
	<br>
</stripes:form>
</div>
