<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>
function checkSubmit(id){
	var input;
	if(id == 'insertDetailSubmit')
		input = confirm('등록 하시겠습니까?' );
	else 
		input = confirm('수정 하시겠습니까?' );
	if(input) {
		$('#' + id).click();
	}else {
		
	}
	
}	
</script>


<div id="viewDetailTitle"><img src="../images/blue_title.gif"/>세부사항 입력</div>
<div id="totalInsert">
<stripes:form id="updatePermit" name="updatePermit" beanclass="com.spring.risk.web.actions.PermitActionBean" method="POST">
	
	<table>
	<tr>
		<th class="detailHeader">작업코드</th>
		<td colspan="5">
			<!-- insert -->
			<c:if test="${!actionBean.isModify}">
				<stripes:select name="permitVO.workcode" >
				<c:forEach var="work" items="${actionBean.workList}" varStatus="i">						
					<stripes:option value="${work.code}" label="${work.code} : ${work.name}"/>
				</c:forEach>
				</stripes:select>
			</c:if>
			<!-- update -->
			<c:if test="${actionBean.isModify}">
				${actionBean.permitVO.workcode}
			</c:if>
		</td>
	</tr>
	<tr>
		<th class="detailHeader">장소유형</th>
		<td  colspan="5">
			<!-- insert -->
			<c:if test="${!actionBean.isModify}">			
				<c:forEach var="place" items="${actionBean.placeList}" varStatus="idx">
					${place.name}<stripes:checkbox name="inputPlaceList[${idx.index}]" value="${place.code}" title=""> </stripes:checkbox>
				</c:forEach>
			</c:if>
		
			<!-- update -->
			<c:if test="${actionBean.isModify}">
				${actionBean.permitVO.parse_codeList}
			</c:if>
		</td>
	</tr>
	<tr>
		<th class="detailHeader">내용</th>
		<td  colspan="5">
			<stripes:textarea name="permitVO.content"  onkeyup="checkSpeChar(2)"/>
		</td>
	</tr>
	<tr>
		<th>등록</th>
		<td>
		<div class="updateBtnDiv">
			<!-- insert 모드 --> <c:if test="${!actionBean.isModify}"><stripes:button name="insertDetail" class="button" value="등록" onclick="checkSubmit('insertDetailSubmit')"/>
			<stripes:submit id="insertDetailSubmit" name="insertDetail" class="button" style="display:none"/></c:if>
			<!-- update 모드 --> <c:if test="${actionBean.isModify}"><stripes:button name="updateDetail" class="button" value="수정" onclick="checkSubmit('updateDetailSubmit')"/>
			<stripes:submit id="updateDetailSubmit" name="updateDetail" class="button" style="display:none"/>	</c:if>
		</div>
		</td>
	</tr>	
	</table>

</stripes:form>
</div>
