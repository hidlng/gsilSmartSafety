<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>코드검색</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<link rel="stylesheet" href="css/custom.css" type="text/css">
<script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="js/jquery.plugin.js"></script> 

<script>

function init() {			
	 setChildCategoryOf(1, 'worktype');
	
}


/** set Child Category Of Idx (add option to target Selectbox) **/
function setChildCategoryOf(idx, targetId) {
	 
	$.ajax({
 		type : "POST",
 		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getCategoryByJSON=",
 		data : {ancIdx : idx},
 		dataType : "jsonp",
 	    jsonp : "callback",
 		cache : false,
 		success : function(json) {
 			$('#' + targetId).empty();
 			var catList = json.catList;
 		
 			$('#' + targetId).append('<option id="" value="">-----------선택----------</option>');
 			for(var i = 0 ; i < Object.keys(catList).length; i ++) {
 				$('#' + targetId).append('<option id="' + catList[i].idx + '" value="' + catList[i].name + '">' + catList[i].name  + '</option>');
 			} 
			
 		},
 		error : onError
	});
}

function setCateogry(objId, targetId) {
	var idx = $("#" + objId + " option:selected" ).attr('id');
	setChildCategoryOf(idx , targetId);
}


function setCode(objId, targetId) {
	 var idx = $("#" + objId + " option:selected" ).attr('id');	 
	 
	 $.ajax({
	  		type : "POST",
	  		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getCodeByJSON=",
	  		data : {lastIdx : idx},
	  		dataType : "jsonp",
	  	    jsonp : "callback",
	  		cache : false,
	  		success : function(json) {
	  			$('#' + targetId).empty();
	  			var codeList = json.codeList;
	  			
	  			$('#' + targetId).append('<option id="" value="">-----------선택----------</option>');
	  			for(var i = 0 ; i < Object.keys(codeList).length; i ++) {
	  				//$('#' + targetId).append('<option id="CODE_' + codeList[i].code + '" value="' + codeList[i].name + '">' + codeList[i].name  + '</option>');
	  				$('#' + targetId).append('<option value="' + codeList[i].name + '">' + codeList[i].name  + '</option>');
	  				$('#workcode').val(codeList[i].code);
	  			} 
				
	  		},
	  		error : onError
		});
}


function onError(data, status) {
	alert(this.url);
	alert("error : " + status +"data:"+ data);
	
}
	
	
	
function confirmCode(){
	var worktype = $('#worktype option:selected').val();
	var category1 = $('#category1 option:selected').val();
	var category2 = $('#category2 option:selected').val();
	var workname = $('#workname option:selected').val();
	var workcode = $('#workcode').val();
	
	
	var str  = "'" + worktype + "','" + category1 + "','" + category2 + "','" + workcode + "','" + workname + "'";
	$(opener.location).attr("href", "javascript:workCallBack(" + str +")" );

	self.close();
	
}


</script>
</head>

<body onload="init();">
<!-- 	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form> -->
<div class="content">
	<input type="hidden" id="workcode" name="workcode" value=""/>
	<table>
			<tr>
			<th>
			<div>공종</div>			
			<select id="worktype"  class="siteSelectBox" onchange="setCateogry(this.id, 'category1')">
					<option value="" selected="selected" >공종</option>					
			</select>
			</th>
			<th>
			<div>중분류</div>
			<select id="category1"  class="siteSelectBox" onchange="setCateogry(this.id, 'category2')">
					<option value="" selected="selected" >중분류</option>					
			</select></th>
			<th>
			<div>소분류</div>
			<select id="category2" class="siteSelectBox" onchange="setCode(this.id, 'workname')">
					<option value="" selected="selected" >소분류</option>					
			</select>
			</th>
		</tr>
		<tr>
			<th>작업명</th>
			<td colspan="2"><select id="workname" class="siteSelectBox" onchange="setWorkDetail(this.id)">
				<option value="" selected="selected" >작업명 선택</option>					
			</select>
			
			</td>
		</tr>
	</table>
	
	<div class="signup"><img src="images/btn_signup.png"
				class="signupImg" alt="등록하기" onclick="confirmCode()"
				onmouseover="this.src='images/btn_signup_over.png'"
				onmouseout="this.src='images/btn_signup.png'"
				style="cursor: pointer">
	</div>
</div>
</body>
</html>