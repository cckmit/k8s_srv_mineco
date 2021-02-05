<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
    Constants constants= new Constants();
	boolean caheOn = Boolean.valueOf(constants.getParamValue(Constants.CACHE_STATUS)).booleanValue(); // consulta a la base de datos
	if (caheOn){
		%>
			<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
			<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
		<%
		
	}
%>
<title>Secretaría de Econom&iacute;a - Regristro &Uacute;nico de Garant&iacute;as Mobiliarias</title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/template_css.css" type="text/css" />
<body>
<div id="layoutContainer">
<div id="headerContainer"><tiles:insertAttribute name=".header" />
</div>
<div id="workingContainer"><tiles:insertAttribute
	name="working.region" /></div>
<div id="spacer" ><br>
</br>
<div id="footerContainer"><tiles:insertAttribute name=".footer" />
</div>
</div>
</body>
</html>