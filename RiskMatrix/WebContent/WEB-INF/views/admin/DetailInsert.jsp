<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>


	function refreshCheckList(json, status) {
		$('.content').html($(data).find('.content').html());
		$('#viewContent').html($(json).find('.content').html());
		$('tr:even').addClass('even');
		$('tr:odd').addClass('odd');

		doOverlayOpen();
	}

	var checkCount = ${actionBean.checkList.size()};
	/**add checklist**/
	
	function getCheckList(idx) {
		return "<input name='modifyCheckList["
			+ idx
			+ "].checklist' style='width:60%' type='text' class='tf_checkList' /><input name='modifyCheckList[" 
            + idx + "].fileBean' type='file' />"
            + "<div  onclick='delCheckList(" 
        	+ idx + ")' class='button checkListBtn'>제거</div>";
	}
	
	function addCheckList() {
		//alert(checkCount);
		var str = getCheckList(checkCount);
		var addedSpan = document.createElement("span");
		addedSpan.id = "added_" + checkCount;
		addedSpan.innerHTML = str;
		$("#checklistTD").append(addedSpan);
		//$("#"+addedSpan.id).appendTo("#checklistTD");   

		checkCount++;
		
		$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함
		
		//reidxCheckList();
	}
	
	function delCheckList(val) {
		//alert(val);
		var addedFormDiv = document.getElementById("addedFormDiv");
		if (checkCount > 1) { // 현재 폼이 두개 이상이면
			//$("#" + "added_" + (--checkCount)).remove();
			$("#" + "added_" + val).remove();
			checkCount--;
		} else { //0번 진입시 리셋
			$("#added_"+val).remove();
			checkCount--;
			addCheckList();
		}
		
		
		$("#checkCount").val(checkCount);
		reidxCheckList();
	}
	
	//중간에 지워질경우 index 정리
	function reidxCheckList() {
		/* $('#checklistTD').children('input').each(function () {
			alert(1);
		}
		 */
		$("input:text[name^='checkList']").each(function(index,item){
			$(item).attr(name).val("checkList["+ index+ "].checklist");
		});
		 
		$("input:text[name^='checkList']").each(function(index,item){
			$(item).attr(name).val("checkList["+ index+ "].checklist");
		});
	}
	/* function delCheckList() {
		//alert(checkCount);
		var addedFormDiv = document.getElementById("addedFormDiv");
		if (checkCount > 1) { // 현재 폼이 두개 이상이면
			$("#" + "added_" + (--checkCount)).remove();
		} else { //0번 진입시 리셋
			$("#added_0").remove();
			checkCount--;
			addCheckList();
		}
		
		$("#checkCount").val(checkCount);
	}  */
	
	</script>


<div id="viewDetailTitle"><img src="../images/blue_title.gif"/>세부사항 입력</div>
<div id="totalInsert">
<stripes:form id="insertTool" name="insertTool"
	beanclass="com.spring.risk.web.actions.CategoryActionBean"
	enctype="multipart-form/data"  method="POST">
	
	
<stripes:hidden name="codeType" value="${actionBean.codeType}"/>
<stripes:hidden id="checkCount" name="checkCount" value="${actionBean.checkList.size()}"/>
<!--Work-->
<c:if test = "${actionBean.codeType == 'WORK'}">
<table >
		<tr><th class="detailHeader">작업코드</th><td colspan="5"><stripes:text name="workVO.workCode" readonly="true" /></td></tr>
		<tr><th class="detailHeader">작업명</th><td  colspan="5"><stripes:text name="workVO.workName"  /></td></tr>
		<tr><th class="detailHeader">사고유형</th>
			<c:forEach begin="0" end="4" step="1" varStatus="accIdx">	
			<td>	
				<stripes:select name="inputAccList[${accIdx.index}].accCode">	
					<option value ="NONE" label="NONE"/>
					<c:forEach var="accVar" items="${actionBean.accList}" varStatus="idx">
						<!-- update시 selected지정 -->
				    	<c:if test="${actionBean.inputAccList[accIdx.index].accCode == actionBean.accList[idx.index].code }">
							<option value ="${accVar.code}" label="${accVar.name}" selected="selected"/>
						</c:if>
						<c:if test="${actionBean.inputAccList[accIdx.index].accCode != actionBean.accList[idx.index].code }">
							<option value ="${accVar.code}" label="${accVar.name}"/>
						</c:if>
			  	  	</c:forEach>	
			  	  </stripes:select>		
		  	  </td>
		  	  </c:forEach>
		</tr>
		<tr>
			<th class="detailHeader">사고가능성</th>			
			<c:forEach begin="0" end="4" step="1" varStatus="accIdx">	
			<td>	
				<stripes:radio name="inputAccList[${accIdx.index}].accPoss" value="-1"/>-1<stripes:radio
				 name="inputAccList[${accIdx.index}].accPoss" value="0"/>0<stripes:radio
				  name="inputAccList[${accIdx.index}].accPoss" value="1"/>1				
     		</td>
     		</c:forEach>
     	</tr>
		<tr><th class="detailHeader">잠재심각성</th>		
			<c:forEach begin="0" end="4" step="1" varStatus="accIdx">
			<td>	
				<stripes:select name="inputAccList[${accIdx.index}].accSerious">
					<c:forEach begin="1" end="5" step="1" varStatus="seriousVal">
						<option value ="${seriousVal.count}" label="${seriousVal.count}"/>
				  	</c:forEach>
			  	</stripes:select>
			</td>
			</c:forEach>
		</tr>
		<tr><th class="detailHeader">안전작업가이드</th><td  colspan="5"><stripes:textarea   name="workVO.guide" /></td>		</tr>
		<tr><th class="detailHeader">보호구</th><td  colspan="5"><stripes:textarea  name="workVO.equip" /></td>		</tr>
		<tr><th class="detailHeader">이상징후/비상시조치사항</th><td colspan="5"><stripes:textarea  name="workVO.measure"  /></td>		</tr>
		<tr><th class="detailHeader">사고사례</th><td  colspan="5"><!--File Upload -->
				<!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						${file.fileName} 		
						<stripes:link class="detailLink" beanclass="com.spring.risk.web.actions.CategoryActionBean" event="deleteFile">
						<stripes:param name="deleteFileIdx">${file.file_idx}</stripes:param>x</stripes:link>
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
			</td>
		</tr>
	</table>	
</c:if>

<!--Tool-->
<c:if test = "${actionBean.codeType == 'TOOL'}">
	<table id ="toolInsertTable">
		<tr><th class="detailHeader">장비/공도구 코드</th><td><stripes:text  name="toolVO.toolCode" readonly="true" /></td></tr>
		<tr><th class="detailHeader">장비/공도구명</th><td><stripes:text  name="toolVO.toolName"  /></td></tr>
		<tr><th class="detailHeader">주요위험</th><td><stripes:textarea  name="toolVO.mainRisk" /></td></tr>
		<tr><th class="detailHeader">안전작업가이드</th><td><stripes:textarea  name="toolVO.guide" /></td></tr>
		<tr><th class="detailHeader">보호구</th><td><stripes:textarea  name="toolVO.equip" /></td></tr>
		<tr><th class="detailHeader">사용전 점검 체크리스트</th>
			<td><div id="checklistTD">
				<c:if test ="${actionBean.checkList.size() > 0}"> 
				<c:forEach begin="0" end="${actionBean.checkList.size()-1}" varStatus="loop">
					<span id="added_${loop.index}">
					<!-- 값은 checlist에서 가져오되 보내주는건 modfiyCheckList로 보냄 -->
					<stripes:text class="tf_checkList" name="modifyCheckList[${loop.index}].checklist" style="width:60%">${actionBean.checkList[loop.index].checklist}</stripes:text> 
					
					<!-- image값 여부에 따라 image or file upload  -->
					<c:if test="${actionBean.checkList[loop.index].image == null}" >
						<stripes:file name="modifyCheckList[${loop.index}].fileBean"/>
					</c:if>
					<c:if test="${actionBean.checkList[loop.index].image != null}" >
						<img class="checkListImage" src="Category.action?getChekcListImage=&fileName=${actionBean.checkList[loop.index].image}"/>
						<stripes:hidden name="modifyCheckList[${loop.index}].image" value="${actionBean.checkList[loop.index].image}"/>
					</c:if>
					<div  onclick="delCheckList(${loop.index})" class="button checkListBtn" style="cursor:pointer">제거</div>
					</span>
				</c:forEach>					
				</c:if>
				<br>
				</div>
				<div  onclick="addCheckList()" class="button checkListBtn" >추가</div>
			</td>
		</tr>
		<tr><th class="detailHeader">사고사례</th><td><!--File Upload -->
				<!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						${file.fileName} 		
						<stripes:link class="detailLink" beanclass="com.spring.risk.web.actions.CategoryActionBean" event="deleteFile">
						<stripes:param name="deleteFileIdx">${file.file_idx}</stripes:param>x</stripes:link>
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
			</td></tr>		
	</table>	
</c:if>


<!--Place-->
<c:if test = "${actionBean.codeType == 'PLACE'}">
	<table>
		<tr><th class="detailHeader">장소코드</th><td><stripes:text name="placeVO.placeCode" readonly="true" /></td></tr>		
		<tr><th class="detailHeader">장소명</th><td><stripes:text name="placeVO.placeName"  /></td></tr>
		<tr><th class="detailHeader">주요위험</th><td><stripes:textarea  name="placeVO.mainRisk" /></td>	</tr>
		<tr><th class="detailHeader">안전작업가이드</th><td><stripes:textarea  name="placeVO.guide" value=""/></td></tr>
		<tr><th class="detailHeader">보호구</th><td><stripes:textarea  name="placeVO.equip" /></td>			</tr>
		<tr><th class="detailHeader">사고사례</th><td><!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						${file.fileName} 		
						<stripes:link class="detailLink" beanclass="com.spring.risk.web.actions.CategoryActionBean" event="deleteFile">
						<stripes:param name="deleteFileIdx">${file.file_idx}</stripes:param>x</stripes:link>				
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" />
					</c:forEach>
				</c:if>
				</td></tr>				
		</table>
		
</c:if>
<!--Acc-->
<c:if test = "${actionBean.codeType == 'ACC'}">
</c:if>
<div class="updateBtnDiv">
<!-- insert 모드 --> <c:if test="${!actionBean.isModify}"><stripes:submit name="insertDetail" class="button" value="등록" /></c:if>
<!-- update 모드 --> <c:if test="${actionBean.isModify}"><stripes:submit name="updateDetail" class="button" value="수정" />	</c:if>
</div>
</stripes:form>
</div>
