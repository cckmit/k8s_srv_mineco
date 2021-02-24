<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/MasivaDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/masiva.js"></script>
</head>
<body>
<s:form action="cargaMasiva.do" method="post" enctype="multipart/form-data" theme="simple">
 	<table>
 		<tr> <td class="tituloHeader2" colspan="2">Seleccione al acreedor respecto del cual realizará el Asiento : </td> </tr> <tr></tr>
 		<tr></tr>
 		<tr> <td style="padding-left: 28px;" colspan="2">
 			<s:select onchange="cargaMasiva()" list="listaAcreedores" listKey="idPersona" listValue="nombreCompleto" name="idAcreedorTO" id="idAcreedorTO">
			</s:select>	 
		</td> </tr>
		<tr>
 	 	 <td class="2" id="dwrId">
		</td>
 	 	 
 	 	</tr>
 	 	
 	</table>
 </s:form>
 <script>
//setActiveTab('seisMenu');
$("#seisMenu").attr("class","linkSelected");
function cargaMasiva(){	
	var idUsuario = <s:property value="idUsuario" />;
	var idAcreedor = document.getElementById("idAcreedorTO").value;
	displayLoader(true);
	MasivaDwrAction.getCargaMasiva(idAcreedor, idUsuario, showMasiva);
}
cargaMasiva();
</script>
</body>
</html>