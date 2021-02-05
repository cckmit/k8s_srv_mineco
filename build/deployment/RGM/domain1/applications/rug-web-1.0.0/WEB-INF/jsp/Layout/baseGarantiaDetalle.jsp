<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Secretaría de Economía - RUG</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/template_css.css" type="text/css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/default.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tuempresa.css" type="text/css" />
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
</head>

<body>

<table>
	<tr>
		<td id="workingAyudaContainer"><tiles:insertAttribute name="working.region" /></td>
	</tr>
</table>

</body>
</html>