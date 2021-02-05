<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/GruposDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/gruposJS.js"></script>

</head>
<body>

<table><tr><td id="tdParteGrupo"></td></tr></table>

</body>
<script type="text/javascript">
	var nombreCompleto = '<s:property value="acreedorTO.nombreCompleto"/>';
	document.getElementById('cargaNombre').innerHTML = '<s:property value="acreedorTO.nombreCompleto"/>';
	var idAcreedorJSP = '<s:property value="_idAcreedor"/>';
	var idPersonaJSP = '<s:property value="idUsuario"/>';
	getParteGrupo('tdParteGrupo', idPersonaJSP, idAcreedorJSP);
	if ('<s:property value="grupo"/>' == 'N') {
		setTimeout("mostrarAltaGrupo();" ,1500);
		setTimeout("document.getElementById('nombreGrupo').focus();" ,1500);
	}
</script>
</html>

