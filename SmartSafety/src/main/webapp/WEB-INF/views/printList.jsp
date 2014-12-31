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
   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=700, height=700, top=0,left=20"; 
   window.open("tbm?work_idx=" + $('#work_idx').val() , title,status);  //프로그램처럽보일떈 파업 X?   
}

function openPUI(){
	   var url    ="pui";
	   var title  = "puiView";
	   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=700, height=700, top=0,left=20"; 
	   window.open("pui?work_idx=" + $('#work_idx').val() , title,status);  //프로그램처럽보일떈 파업 X?   
	}
function openPTW(){
	   var url    ="ptw";
	   var title  = "ptwView";
	   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=700, height=700, top=0,left=20"; 
	   window.open("ptw?work_idx=" + $('#work_idx').val() , title,status);  //프로그램처럽보일떈 파업 X?   
	}
</script>

<form id="printForm" method="POST"	autocomplete="off">
	<form:input type="hidden" id="work_idx" path="work_idx" value="${work_idx}" />
	<!--<input type="button" name="button1" value="전 송" onclick="openTBM(this.form);">-->
</form>

<div id="print">
${work_idx }
${tbm_idx}
<div onclick="openTBM()">TBM출력</div>

<div onclick="openPTW()">PTW출력</div>

<div onclick="openPUI()">PUI출력</div>



</div>


<%@ include file="IncludeBottom.jsp"%>