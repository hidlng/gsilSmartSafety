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
			+ "].checklist' style='width:50%' type='text' class='tf_checkList' onkeyup='checkSpeChar(2)''/><input name='modifyCheckList[" 
            + idx + "].fileBean' type='file' />"
            + "<div  onclick='delCheckList(" 
        	+ idx + ")' class='checkListBtn'>X</div>";
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
	
	var listIdx =0;
	function deleteFileList(fileIdx){
		
	 	$('#file_' + fileIdx).remove();
		$('#file_del_' + fileIdx).remove();
		
		var input = "<input type='text' name='delFileList[" + listIdx +"]' value='" + fileIdx +"'/>";
    	
		var addedSpan = document.createElement("span");
		addedSpan.innerHTML = input;
		$("#delListSpan").append(addedSpan);
		
		listIdx++;
		 
		//deleteFileIdx
	}
	</script>


<div id="viewDetailTitle"><img src="../images/blue_title.gif"/>세부사항 입력</div>
<div id="totalInsert">
<stripes:form id="insertDetail" name="insertDetail"
	beanclass="com.spring.risk.web.actions.CategoryActionBean"
	enctype="multipart-form/data"  method="POST">
	
<span id="delListSpan" style="display:none"></span>
<stripes:hidden name="codeType" value="${actionBean.codeType}"/>
<stripes:hidden id="checkCount" name="checkCount" value="${actionBean.checkList.size()}"/>
<!--Work-->
<c:if test = "${actionBean.codeType == 'WORK'}">
<table >
		<tr><th class="detailHeader">작업코드</th><td colspan="5"><stripes:text name="workVO.workCode" readonly="true" onkeypress="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">작업명</th><td  colspan="5"><stripes:text name="workVO.workName"  onkeyup="checkSpeChar(2)"/></td></tr>
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
				<stripes:radio name="inputAccList[${accIdx.index}].accPoss" value="-1"/>-1
				<stripes:radio name="inputAccList[${accIdx.index}].accPoss" value="0"/>0
				<stripes:radio name="inputAccList[${accIdx.index}].accPoss" value="1"/>1				
     		</td>
     		</c:forEach>
     	</tr>
		<tr><th class="detailHeader">잠재심각성</th>		
			<c:forEach begin="0" end="4" step="1" varStatus="accIdx">
			<td>
				<stripes:select name="inputAccList[${accIdx.index}].accSerious">
					<c:forEach begin="1" end="5" step="1" varStatus="seriousVal">				
						<c:if test="${actionBean.inputAccList[accIdx.index].accSerious == seriousVal.count}">
							<option value ="${seriousVal.count}" label="${seriousVal.count}" selected="selected"/>
						</c:if>
						<c:if test="${actionBean.inputAccList[accIdx.index].accSerious != seriousVal.count}">
							<option value ="${seriousVal.count}" label="${seriousVal.count}"/>
						</c:if>
				  	</c:forEach>
			  	</stripes:select>
			</td>
			</c:forEach>
		</tr>
		<tr><th class="detailHeader">안전작업가이드</th><td  colspan="5"><stripes:textarea   name="workVO.guide" onkeyup="checkSpeChar(2)"/></td>	</tr>
		<tr><th class="detailHeader">보호구</th><td  colspan="5"><stripes:textarea  name="workVO.equip" onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">이상징후/<br>비상시조치사항</th><td colspan="5"><stripes:textarea  name="workVO.measure" onkeyup="checkSpeChar(2)" /></td></tr>
		<tr><th class="detailHeader">안전조치사항</th><td colspan="5"><stripes:textarea  name="workVO.safety" onkeyup="checkSpeChar(2)" /></td></tr>
		<tr><th class="detailHeader">작업허가</th><td colspan="5">
			<stripes:radio  name="workVO.permit" id="permit_1" value="1" /><stripes:label for="permit_1" >해당</stripes:label>
			<stripes:radio  name="workVO.permit" id="permit_0" value="0" /><stripes:label for="permit_0" >해당없음</stripes:label>
		 </td></tr>
		<tr><th class="detailHeader">사고사례</th><td  colspan="5"><!--File Upload -->
				<!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						<span id="file_${file.file_idx}">${file.fileName} </span>
						<span id="file_del_${file.file_idx}" style="cursor:pointer" onclick="deleteFileList(${file.file_idx})">X</span><br>
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
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
		<tr><th class="detailHeader">장비/공도구명</th><td><stripes:text  name="toolVO.toolName"  onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">주요위험</th><td><stripes:textarea  name="toolVO.mainRisk" onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">장비사용가이드</th><td><stripes:textarea  name="toolVO.guide" onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">보호구</th><td><stripes:textarea  name="toolVO.equip" onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">사용전 점검<br> 체크리스트</th>
			<td><div id="checklistTD">
				<c:if test ="${actionBean.checkList.size() > 0}"> 
				<c:forEach begin="0" end="${actionBean.checkList.size()-1}" varStatus="loop">
					<span id="added_${loop.index}">
					<!-- 값은 checlist에서 가져오되 보내주는건 modfiyCheckList로 보냄 -->
					<stripes:text class="tf_checkList" name="modifyCheckList[${loop.index}].checklist" style="width:60%" onkeyup="checkSpeChar(2)">${actionBean.checkList[loop.index].checklist}</stripes:text> 
					
					<!-- image값 여부에 따라 image or file upload  -->
					<c:if test="${actionBean.checkList[loop.index].virtName == null}" >
						<stripes:file name="modifyCheckList[${loop.index}].fileBean"/>
					</c:if>
					<c:if test="${actionBean.checkList[loop.index].virtName != null}" >
						<img class="checkListImage" src="Data.action?getChekcListImage=&filename=${actionBean.checkList[loop.index].virtName}"/>
						<stripes:hidden name="modifyCheckList[${loop.index}].image" value="${actionBean.checkList[loop.index].image}"/>
						<stripes:hidden name="modifyCheckList[${loop.index}].virtName" value="${actionBean.checkList[loop.index].virtName}"/>
					</c:if>
					<span  onclick="delCheckList(${loop.index})" class="checkListBtn" style="cursor:pointer">  X</span>
					</span>
				</c:forEach>					
				</c:if>
				<br>
				</div>
				<div  onclick="addCheckList()" class="button checkListBtn" >추가</div>
			</td>
		</tr>
		<tr><th class="detailHeader">장비이미지</th><td><stripes:file  name="toolVO.imgFileBean" /></td></tr>
		<tr><th class="detailHeader">사고사례</th><td><!--File Upload -->
				<!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						<span id="file_${file.file_idx}">${file.fileName} </span>
						<span id="file_del_${file.file_idx}"  style="cursor:pointer" onclick="deleteFileList(${file.file_idx})">X</span><br>
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
					</c:forEach>
				</c:if>
			</td></tr>		
	</table>	
</c:if>


<!--Place-->
<c:if test = "${actionBean.codeType == 'PLACE'}">
	<table>
		<tr><th class="detailHeader">장소코드</th><td><stripes:text name="placeVO.placeCode" readonly="true" /></td></tr>		
		<tr><th class="detailHeader">장소명</th><td><stripes:text name="placeVO.placeName" onkeyup="checkSpeChar(2)" /></td></tr>
		<tr><th class="detailHeader">주요위험</th><td><stripes:textarea  name="placeVO.mainRisk" onkeyup="checkSpeChar(2)" /></td>	</tr>
		<tr><th class="detailHeader">안전작업가이드</th><td><stripes:textarea  name="placeVO.guide" onkeyup="checkSpeChar(2)"/></td></tr>
		<tr><th class="detailHeader">보호구</th><td><stripes:textarea  name="placeVO.equip" onkeyup="checkSpeChar(2)"/></td>			</tr>
		<tr><th class="detailHeader">작업허가</th><td>
			<stripes:radio  name="placeVO.permit" id="permit_1" value="1" /><stripes:label for="permit_1" >해당</stripes:label>
			<stripes:radio  name="placeVO.permit" id="permit_0" value="0" /><stripes:label for="permit_0" >해당없음</stripes:label>
			
	 </td></tr>
		<tr><th class="detailHeader">사고사례</th><td><!-- insert -->
				<c:if test="${!actionBean.isModify}">
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
					</c:forEach>
				</c:if>
				<!-- modify -->
				<c:if test="${actionBean.isModify}">
					<c:forEach var="file" items="${actionBean.fileList}" varStatus="fileIdx">
						<span id="file_${file.file_idx}">${file.fileName} </span>
						<span id="file_del_${file.file_idx}" style="cursor:pointer" onclick="deleteFileList(${file.file_idx})">X</span><br>	
					</c:forEach>
					<c:forEach begin="0" end="${actionBean.uploadFileMaxIdx}" varStatus="loop">
						<stripes:file name="fileBeanList[${loop.index}]" /><br>
					</c:forEach>
				</c:if>
				</td></tr>				
		</table>
		
</c:if>
<!--Acc-->
<c:if test = "${actionBean.codeType == 'ACC'}">
</c:if>
<br>
<div class="updateBtnDiv">
<!-- insert 모드 --> <c:if test="${!actionBean.isModify}"><stripes:button name="insertDetail" class="button" value="등록" onclick="checkSubmit('insertDetailSubmit')"/>
<stripes:submit id="insertDetailSubmit" name="insertDetail" class="button" style="display:none"/></c:if>
<!-- update 모드 --> <c:if test="${actionBean.isModify}"><stripes:button name="updateDetail" class="button" value="수정" onclick="checkSubmit('updateDetailSubmit')"/>
<stripes:submit id="updateDetailSubmit" name="updateDetail" class="button" style="display:none"/>	</c:if>
</div>
<br>
<br>
</stripes:form>
</div>
