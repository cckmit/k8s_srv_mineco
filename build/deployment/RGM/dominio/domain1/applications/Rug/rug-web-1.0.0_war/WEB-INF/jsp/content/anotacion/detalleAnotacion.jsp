<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@include file="/WEB-INF/jsp/Layout/menu/privilegios.jsp"%>

<script language='javascript' src='/portal/comun/lib/bBibliotecas.js'></script>
<script type="text/javascript">
function anterior(){
	
		document.location="<%=request.getContextPath() %>/anotacion/anterior.do";
}

function siguiente(){
		document.location="<%=request.getContextPath() %>/anotacion/siguiente.do";
}


</script>

<?xml version="1.0" encoding="ISO-8859-1"?>



<table style="width:100%">


<s:form action="inicia.do" id="frmDetalle" method="post"
	name="frmDetalle">
	<div id="nota" style="margin-top: 50px"></div>
	<TABLE>
		<TR>
			<TD width="743">
				<div id="detalle">
					<table>
						<tr>
							<td width="743" class="titulo_exterior_blanco" 
								height="25" style="padding-left: 5px; height: 21px"><a
								style="color: #BA2025; font-size: 16px; font-family: verdana; font-weight: bold;
								text-decoration: none; width: 743px;"
								href="<%=request.getContextPath() %>/home/detalle.do?idGarantia=<s:property value="idGarantia"/>&=<s:property value=""/> 
								">Detalle de la Anotaci&oacute;n</a>
							</td>
						</tr>
					</table>
						<div align="right" style="width: 745px">
							<s:if test="hayAnterior">
								<img
									src="<%=request.getContextPath()%>/resources/imgs/flecha_anterior.gif"
									height="30" width="30" onclick="anterior();"
									style="cursor: pointer" />
							</s:if>
							<s:else>
								<img
									src="<%=request.getContextPath()%>/resources/imgs/flecha_anteriorD.gif"
									height="30" width="30" />
							</s:else>
							<s:if test="haySiguiente">
								<img
									src="<%=request.getContextPath()%>/resources/imgs/flecha_siguiente.gif"
									height="30" width="30" onclick="siguiente();"
									style="cursor: pointer" />
							</s:if>
							<s:else>
								<img
									src="<%=request.getContextPath()%>/resources/imgs/flecha_siguienteD.gif"
									height="30" width="30" />
							</s:else>
						</div>
						<!--<div style="width: 745px"><hr></div> -->
						<div class="tituloHeader1" height="25" style="padding-left: 9px">
							N&uacute;mero de Asiento:
							<s:property value="anotacionSnGarantia.idTramiteFinal" />
						</div>
						<br>
						
						<table id="mytabledaO" class="mytabledaO" border="0" width="40%"
						cellspacing="1" cellpadding="1" align="center"
						style="padding-left: 16px;">
						<thead>
							<tr>
								<td class="encabezadoTablaResumen" style="text-align: center">
									Fecha de la Anotaci&oacute;n</td>
								<td class="encabezadoTablaResumen" style="text-align: center">
									Fecha del &uacute;ltimo Asiento</td>
							</tr>
						</thead>
						<tbody>
								<tr>
									<td class="cuerpo1TablaResumen"><div align="center">
											<s:property value="anotacionSnGarantia.fechaAnotacion" />
										</div></td>
									<td class="cuerpo1TablaResumen"><div align="center">
											<s:property value="anotacionSnGarantia.fechaFirma" />
										</div></td>
								</tr>
						</tbody>
					</table>
					<br></br>
					<br>
					<table id="mytabledaO" class="mytabledaO" border="0" width="40%"
						cellspacing="1" cellpadding="1" align="center"
						style="padding-left: 16px;">
						<tr>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Tipo de Asiento</td>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Fecha del Asiento</td>
						</tr>
						<tr>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="anotacionSnGarantia.tipoTramiteStr" />
								</div></td>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="anotacionSnGarantia.fechaFirma" />
								</div></td>
							</tr>
					</table>
					<br></br>
					<table style="Padding-left: 15px">
						<tr>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Inscrito en el folio Mercantil No.</td>
						</tr>
						<tr>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="anotacionSnGarantia.otorganteFolioElectronico" />
								</div></td>
						</tr>
					</table>
					<br></br>
				
					<!-- inicia if de anotacion -->
				
					<div id="tituloBusqueda" class="tituloHeader1" height="25"
						style="padding-left: 9px">
						<p>
							<b class="tituloHeader1">Datos de la Anotaci&oacute;n</b>
						</p>
					</div>					
							<div>
							<br>
							
							<table width="738">
								<tr>
									<td style="padding-left: 14px;" width="714" colspan="2"	align="left" class="texto_general">
											<span class="textoGeneralRojo">Vigencia:</span>
									</td>
									</tr>
									<tr>
										<td class="textoJustificado" style="padding-left: 14px;">
										<s:property	value="anotacionSnGarantia.vigenciaStr" />
										</td>
								</tr>
								<tr></tr>
								<tr></tr>
									<tr>
										<td style="padding-left: 14px;" width="714" colspan="2"	align="left" class="texto_general">
											<span class="textoGeneralRojo">Persona o Autoridad que instruye anotaci&oacute;n:</span>
										</td>
									</tr>
									<tr>
										<td class="textoJustificado" style="padding-left: 14px;">
										<s:property	value="anotacionSnGarantia.autoridadAutoriza" />
										</td>
									</tr>
									<tr></tr>
									<tr></tr>
									<tr>
										<td style="padding-left: 14px;" width="714" colspan="2"	align="left" class="texto_general">
											<span class="textoGeneralRojo">Contenido de resoluci&oacute;n:</span>
										</td>
									</tr>
									<tr>
										<td class="textoJustificado" style="padding-left: 14px;">
										<s:property	value="anotacionSnGarantia.anotacion" />
										</td>
									</tr>
									<tr></tr>
									<tr></tr>
							</table>
					<div class="tituloHeader1" height="25" style="padding-left: 9px">
					Persona en cuyo folio electr&oacute;nico se realizó la Anotaci&oacute;n:
					</div>
					<br>
					<div>
					<table>
							<tr>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Nombre, Razon o Denominacion Social</td>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Folio Electronico</td>	
						</tr>
						<tr>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="anotacionSnGarantia.otorganteNombre" />&nbsp;
									<s:property value="anotacionSnGarantia.otorganteAPaterno" />&nbsp;
									<s:property value="anotacionSnGarantia.otorganteAMaterno" />&nbsp;
									<s:property value="anotacionSnGarantia.otorganteDenominacion" />
								</div></td>
					
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="anotacionSnGarantia.otorganteFolioElectronico" />
								</div></td>
							</tr>
							
					</table>
						</div>
					<div class="tituloHeader1" height="25" style="padding-left: 9px">
						Historico de la Anotaci&oacute;n
					</div>
					<br>
					<table>
						<tr>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Numero de Anotaci&oacute;n</td>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Numero de Operaci&oacute;n</td>
							<td class="encabezadoTablaResumen" style="text-align: center">
								Tipo de Operaci&oacute;n</td>	
							<td class="encabezadoTablaResumen" style="text-align: center">
								Fecha de Creaci&oacute;n</td>	
						</tr>
						
						<s:iterator value="anotacionSnGarantiaTOs" >
						<tr>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="idTramitePadre" />
								</div></td>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="idTramiteFinal" />
								</div></td>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="tipoTramiteStr" />
								</div></td>
							<td class="cuerpo1TablaResumen"><div align="center">
									<s:property value="fechaFirma" />
							</div></td>
								
						</tr>
						</s:iterator>
					</table>
					<!-- Termina if de anotacion -->
		</div>
			</table>	
</s:form>

</table>