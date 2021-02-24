
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/MasivaDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/masiva.js"></script>

<s:if test="mensaje!=null">
	<table>
		<tr>
			<td>Sucedio el siguiente error:</td>
			<td> <s:property value="mensaje.mensaje"/> </td>
		</tr>
	</table>
</s:if>

<table>
	<thead>
		<tr>
			<td colspan="2" style="font-size: 25px;">Resultados de la carga
			masiva</td>
		</tr>
	</thead>	
	<tr>
		<td>Total de tramites en el archivo : <s:property value="resCargaMasiva.listaTramites.size + resCargaMasiva.listaTramitesErroneos.size"/> </td>
		<td></td>
	</tr>
	<tr>
		<td>Tramites completos : <s:property value="resCargaMasiva.listaTramites.size"/> </td>
		<td>Tramites erroneos : <s:property value="resCargaMasiva.listaTramitesErroneos.size"/> </td>		
	</tr>
	<tr>
		<td colspan="2"> <a href="<%= request.getContextPath() %>/carga/masiva/descargaArchivo.servlet?idArchivo=<s:property value="resCargaMasiva.idArchivoXML"/>" >Descargar el informe de la carga </a> </td>
	</tr>
	<tr>
		<td colspan="2">
		<s:if test="idTipoTramite!=null&&idTipoTramite==12&&resCargaMasiva.listaTramites.size>0">
		
			<a href="<%= request.getContextPath() %>/firma/firmaAcreedores.do"> Firmar</a>
		</s:if><s:else>
			<s:if test="resCargaMasiva.listaTramites.size>0">
				<a href="<%= request.getContextPath() %>/masiva/autoridad/firma.do" > Firmar</a> 			
			</s:if>
			</s:else>
		</td>
	</tr>
	<tr>
		<td></td>
		<s:if test="resCargaMasiva.listaTramitesErroneos.size>0">
		<td><a href="<%= request.getContextPath() %>/masiva/inicia.do" > Intentar de nuevo</a> </td>
		</s:if>
	</tr>
</table>
 <script>
//setActiveTab('seisMenu');
$("#seisMenu").attr("class","linkSelected");
</script>