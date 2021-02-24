<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
	border="0" width="100%" align="left">
	<thead>
		<tr>
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">N&uacute;mero de Garant&iacute;a Mobiliaria /
			Operaci&oacute;n</td>
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">Tipo de Operaci&oacute;n</td>
			<td width="19%" class="encabezadoTablaResumen"
				style="text-align: center">Nombre del Otorgante de la Garant&iacute;a Mobiliaria</td>
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">Folio Electr&oacute;nico del Otorgante de la Garantía Mobiliaria</td>
			<td width="11%" class="encabezadoTablaResumen"
				style="text-align: center">Fecha de Inscripci&oacute;n</td>
			<td width="28%" class="encabezadoTablaResumen"
				style="text-align: center">Tipo de bienes muebles objeto de la
			Garant&iacute;a Mobiliaria</td>
			<td width="32%" class="encabezadoTablaResumen"
				style="text-align: center">Operaci&oacute;n</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="busquedaTOs">
			<tr>
				<td class="cuerpo1TablaResumen"><a
					href="<%=request.getContextPath()%>/home/detalle.do?idGarantia=<s:property value="idGarantia"/>">
				<s:property value="idTramite" /></a></td>
				<td class="cuerpo1TablaResumen"><s:property value="descripcion" />
				</td>
				<td class="cuerpo1TablaResumen"><s:property value="nombre" /></td>
				<td class="cuerpo1TablaResumen"><s:property
					value="folioMercantil" /></td>
				<td align="center" class="cuerpo1TablaResumen">
				<p><s:property value="fechaCreacion" /></p>
				</td>
				<td class="cuerpo1TablaResumen"><s:property
					value="descGarantia" /></td>
				<td class="cuerpo1TablaResumen"><a style="cursor: pointer;">Consulta
				</a> | <a style="cursor: pointer;"> Certificaci&oacute;n</a></td>
			</tr>
		</s:iterator>

	</tbody>
</table>

