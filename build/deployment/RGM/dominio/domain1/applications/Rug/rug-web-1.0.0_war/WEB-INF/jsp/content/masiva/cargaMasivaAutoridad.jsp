<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
	<%@page import="mx.gob.se.rug.inscripcion.dao.AltaParteDAO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/MasivaDwrAction.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/dwr/masiva.js"></script>

<%
	UsuarioTO usuario = (UsuarioTO) session.getAttribute("usuario");
	if (new AltaParteDAO().esAutoridad(usuario.getPersona()
			.getIdPersona())) {
%>
<table>
	<tr>
		<td style="cursor: pointer;"><a
			href="<%=request.getContextPath()%>/masiva/inicia.do">Carga
				Masiva </a>|</td>
		<td style="cursor: pointer;"><a
			href="<%=request.getContextPath()%>/masiva/autoridad/inicia.do">Autoridad</a>
		</td>
	</tr>
</table>
<%
	}
%>
<script>
function showDetalleTecnico(){
	document.getElementById('idMsgTecnico').style.display='block';
	document.getElementById('idMsgTecnico').style.visibility = 'visible';

}
</script>
	<s:if test="mensaje.mensaje!=null">
		<tr>
			<td>Sucedio el siguiente error:<s:property value="mensaje.mensaje" /></td>
		</tr>
		<s:if test="detalleTecnico!=null">
			<tr>
				<td ><a onclick="showDetalleTecnico();">Mostrar detalle tecnico</a></td>
			</tr>
			<tr>
				<td id="idMsgTecnico" style="visibility: hidden;display: none;"><s:property value="detalleTecnico" /></td>
			</tr>
			<tr><td></td></tr>
			<tr>
			<td><a href="<%= request.getContextPath() %>/masiva/inicia.do" > Intentar de nuevo</a> </td>
		    </tr>
		</s:if>
	</s:if>	
	<s:else>

<s:form action="carga" namespace="/masiva/autoridad"
	enctype="multipart/form-data" theme="simple">
	<table>
		<tr>
			<td class="tituloHeader2">Seleccione el tipo de trámite :</td>
			<td><s:select list="tramites" listKey="idTramite"
					listValue="descripcion" name="idTipoTramite"
					onchange="muestraAcreedor();"></s:select></td>
		</tr>
		<tr>
			<td class="tituloHeader2">Seleccione el Archivo :</td>
			<td><s:file type="file" name="autoridadfile" />
			</td>
		</tr>
		<tr>
			<td class="tituloHeader2">Seleccione el tipo de proceso :</td>
			<td><select name="idTipoProceso" id="idTipoProceso">
					<option value="1">Atendido</option>
					<option value="2">Desatendido</option>
			</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" id="idAcreedorTD"></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="Aceptar"></td>
		</tr>

	</table>
</s:form>

<script type="text/javascript">
	//setActiveTab('seisMenu');
	$("#seisMenu").attr("class","linkSelected");

	function muestraAcreedor() {
		var val = document.getElementById('carga_idTipoTramite').value;
		if (val == 3) {
			displayLoader(true);
			var idUsuario = <s:property value="idUsuario" />;
			MasivaDwrAction
					.writeInputIdAcreedor(idUsuario, showInputIdAcreedor);
		} else {
			document.getElementById('idAcreedorTD').innerHTML = "";
		}
		displayLoader(false);
		return false;
	}

	function showInputIdAcreedor(message) {
		if (message.codeError == 0) {
			fillObject("idAcreedorTD", message.message);
			ParceJS(message.message);
		}
		displayLoader(false);
	}
	displayLoader(true);
	setTimeout("muestraAcreedor()",2000);
	
</script>
	<s:if test="mensaje!=null">
<script type="text/javascript">



	document.getElementById('idTableError').style.visibility='visible';
	document.getElementById('idTableError').style.display='block';


</script>
	</s:if>
</s:else>
