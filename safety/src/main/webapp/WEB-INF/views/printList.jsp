<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script>
$(document).ready(function() {
	//alert(1);
	//var result = getCodeDetail('W0404' , '2');
	//alert(result.toolVo.checkList)
	//alert(dat);
	/* alert("data" + data.toolVO.toolName);
	$('#print').text(data ); */
	
});

function openTBM(){
   var url    ="tbm";
   var title  = "tbmView";
   var status = "toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=700, height=500, top=0,left=20"; 
   window.open("tbm", title,status);       
   
}

</script>

<form id="printForm" method="POST"	autocomplete="off">
	<form:input type="hidden" path="work_idx" value="${work_idx}" />
	<!--<input type="button" name="button1" value="전 송" onclick="openTBM(this.form);">-->
</form>

<div id="print">
${work_idx }
${tbm_idx}
<div onclick="openTBM()">TBM출력</div>

현장:
인쇄날짜:
협력업체 :
소장(연락처):
작업명 :
공사감독자(연락처) :
작업기간/시간 :
작업책임자:
작업자수:
사용장비, 점검여부(여러개)

주요위험정보(여러개)
작업장소(여러개)
위험등급 :
위험경고 :
작업허가대상 :
장비점검 :
날씨 :


안전작업지침(measure):
보호구(equip):
작업방법(guide):
비상조치(measure??):


작업전 지시사항(특이사항:remark) :

공사담당자(연락처) :
긴급연락처 :



</div>


<%@ include file="IncludeBottom.jsp"%>