<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
<link rel="StyleSheet" href="../css/riskadmin.css" type="text/css"	media="screen" />
<link rel="StyleSheet" href="../css/common.css" type="text/css"	media="screen" />
<script src="../js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/select.js"></script>

<title>Risk Matrix Admin</title>


</head>

<body>

DATA:
${actionBean.lastIdx}
${actionBean.jsonObj.toString()}
</body>