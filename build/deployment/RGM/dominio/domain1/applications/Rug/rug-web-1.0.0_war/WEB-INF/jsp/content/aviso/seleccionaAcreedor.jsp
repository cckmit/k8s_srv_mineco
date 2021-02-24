<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript">
	$(function () {
		$('.ayuda').ayuda({
			url: '<%= request.getContextPath() %>/comun/publico/help.do',
			ico: '<%= request.getContextPath() %>/resources/imgs/documentinfo.png',
			width: 500,
			height: 300
		})
	})
</script>
<table width="745" align="center">
<tr><td>
<table align="center" class="nota">
	<tr>
		<td class="imgNota">
			<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
		</td>
		<td class="contenidoNota textoJustificado" align="justify"><s:text name="Tipo de asiento que sirve para dar prelación a la Garantía Mobiliaria que sea otorgada e inscrita en el RUG dentro de un plazo no mayor a 15 días naturales sobre determinados Bienes Muebles." /><br></br><b> <s:text name="Fundamento Legal.-" /></b><s:text name="Art. 33 bis 2 RRPC" /></td>
	</tr>
</table>
</td></tr></table>

<s:form action="creaTramAviso.do" namespace="home" name="frmSeleccionaAcreedor" id="frmSeleccionaAcreedor">

	<table align="left" width="600px">	
			<tr>
				<td colspan="2" ><span id="inscripcionSeleccione" class="ayuda"><span class="textoGeneralRojo">Seleccione al Acreedor respecto del cual realizar&aacute; el Aviso Preventivo :</span></span></td><br>
			</tr>
			<tr>	
				<td align="left" colspan="2"><s:select list="listaAcreedores" listKey="idPersona" listValue="nombreCompleto" name="idAcreedorTO"></s:select></td>	
			</tr>
			<tr>
				<td colspan="2"><s:submit cssClass="boton" align="left" value="Aceptar"></s:submit></td>
			</tr> 
	</table>
</s:form>
<script>
//setActiveTab('tresMenu');
$("#tresMenu").attr("class","linkSelected");
</script>
</body>