<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<title>GSIL Homepage</title>
<script type="text/javascript">
// iframe resize
function init(){
var doc = document.getElementById('ifrm');
if(doc.offsetHeight == 0){
} else {
pageheight = doc.offsetHeight;
parent.document.getElementById("contentFrame").height = pageheight+"px";
}
}

</script>
</head>

<body onload="init()"  scroll="no">

	<div id="ifrm" >
		<iframe src="http://www.gsilove.com" frameborder="0" style="width:100%;height:1000px;" id="contentFrame"></iframe> 
	</div>
</body>
</html>
