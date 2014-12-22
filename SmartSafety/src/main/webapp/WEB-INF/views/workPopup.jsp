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
 <script type="text/javascript" src="js/smart.safety.js"></script>
<script>

function init() {			
	 setChildCategoryOf(1, 'worktype');
	
}
	
function confirmCode(){
	var worktype = $('#worktype option:selected').val();
	var category1 = $('#category1 option:selected').val();
	var category2 = $('#category2 option:selected').val();
	var workname = $('#workname option:selected').val();
	var workcode = $('#workcode').val();
	
	alert(1);
	var str  = "'" + worktype + "','" + category1 + "','" + category2 + "','" + workcode + "','" + workname + "'";
	$(opener.location).attr("href", "javascript:workCallBack(" + str +")" );

	window.close();
	alert(2);
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
			<select id="category2" class="siteSelectBox" onchange="setCodeBySelect (this.id, 'workname')">
					<option value="" selected="selected" >소분류</option>					
			</select>
			</th>
		</tr>
		<tr>
			<th>작업명</th>
			<td colspan="2"><select id="workname" class="siteSelectBox">
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