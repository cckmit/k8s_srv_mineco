<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/template_css.css" type="text/css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/default.css" type="text/css" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/campos.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/thickbox.js"></script>   
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/ThickBox.css" type="text/css" media="screen" />
	<script type="text/javascript">
		function window.onbeforeprint(){
		noprint.style.visibility = 'hidden';
		noprint.style.position = 'absolute';
		}
		function window.onafterprint(){
		noprint.style.visibility = 'visible';
		noprint.style.position = 'relative';
		}
	</script>
</head>

<body>
 
<table>
	<tr>
		<td align="left"><input type="button" name="imprimir" value="Imprimir" onclick="window.print();"><input type="button" name="modificarPaso3" value="Modificar" onclick='top.location.href="<%= request.getContextPath() %>/inscripcion/cambioEstatus.do?idInscripcion=<%= request.getParameter("idInscripcion")%>"'></td>
	</tr>
</table>

<table width="746" border="0" cellspacing="5" cellpadding="0" style="overflow: auto;">
	<TR>
		<td height="32" class="textoGeneralRojo" align="right">
		<s:property value="acreedorTORep.nombreCompleto"/>
		</td>
	</TR>
	<tr>
		<td class="titulo_exterior_rojo" align="left">
			Validaci&oacute;n de los Datos de la Garant&iacute;a Mobiliaria
		</td>
	</tr>
	
	<% if (priv.get(new Integer(20))!=null ) { %>
				<tr></tr>
				<tr></tr>
				<tr>
			<td class="titulo_exterior_blanco" bgcolor="#DEDEDE"
				height="25">Solicitante	
			</td>
			</tr>
			<tr>
			</tr>
				<tr></tr>
			<tr><td><table>
				<tr>
						<td class="texto_general" width="10">*</td>
						<td colspan="2" class="texto_general" align="left"><span class="textoGeneralRojo">Persona que solicita o Autoridad que instruye la Inscripci&oacute;n y contenido de la Resoluci&oacute;n :</span></td>
						</tr>
				<tr>
				<td></td>
				<td class="texto_azul"><s:property value="autoridad"/> 	
				</td>
				</tr>
				<tr></tr>
		</table> </td> </tr>
			<% }%>	
	
	<tr>
			<TD colspan="11" class="titulo_exterior_blanco" bgcolor="#DEDEDE"
				height="25" nowrap="nowrap">Sujetos de la Garant&iacute;a Mobiliaria</TD>
		</tr>
		
	<TR><td>
				<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
	border="0" width="650">
	
	<thead>	
		<tr>
			<td class="tituloInteriorRojo">Acreedor</td>
		</tr>
			
		<tr>
			
			<td width="12%" class="encabezadoTablaResumen"
				style="text-align: center">Nombre, Denominaci&oacute;n o Raz&oacute;n Social</td>
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">RFC</td>		
			
			
		</tr>
	</thead>

	<tbody>
	
		<tr>
			<td class="cuerpo1TablaResumen"><s:property value="acreedorTORep.nombreCompleto"/></td>
			<td class="cuerpo1TablaResumen"><s:property value="acreedorTORep.rfc"/></td>
			
							
		</tr>
	
		
	</tbody>
</table>
	</td></TR>
	<tr>
		<td>
			<s:if test="hayOtorgante">
			
				<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
	border="0" width="650">
	
	<thead>	
		<tr>
			<td class="tituloInteriorRojo">Otorgante de la Garant&iacute;a Mobiliaria</td>
		</tr>
			
		<tr>
			
			<td width="12%" class="encabezadoTablaResumen"
				style="text-align: center">Nombre, Denominaci&oacute;n o Raz&oacute;n Social</td>
			
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">Folio Electr&oacute;nico</td>	
			
		</tr>
	</thead>

	<tbody>
	
		<s:iterator value="otorganteTO">
							<tr>
								<td class="cuerpo1TablaResumen"><s:property value="nombreCompleto"/></td>						
								<td class="cuerpo1TablaResumen"><s:property value="folioMercantil"/></td>				
							</tr>
							</s:iterator>
	
		
	</tbody>
</table>
			 
			</s:if>
		</td>
	</tr>
	<tr>
		<td>
			<s:if test="hayDeudores"> 

	<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
	border="0" width="650">
	
	<thead>	
		<tr>
			<td class="tituloInteriorRojo">Deudores</td>
		</tr>
			
		<tr>
			
			<td width="12%" class="encabezadoTablaResumen"
				style="text-align: center">Nombre, Denominaci&oacute;n o Raz&oacute;n Social</td>
			<td width="8%" class="encabezadoTablaResumen"
				style="text-align: center">Tipo de Persona</td>			
			
		</tr>
	</thead>

	<tbody>
	<s:iterator value="listaDeudores">
		<tr>
			<td class="cuerpo1TablaResumen"><s:property value="nombreCompleto"/></td>
			<td class="cuerpo1TablaResumen"><s:property value="tipoPersona"/></td>
						
		</tr>
	</s:iterator>
		
	</tbody>
</table>
</s:if>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<s:if test="hayAcreedores"> 
<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
	border="0" width="95%">
	<thead>
		<tr>
			<TD class="tituloInteriorRojo" >Acreedores Adicionales</TD>
		</tr>
		<tr>
			
			<td width="12%" class="encabezadoTablaResumen"
				style="text-align: center">Nombre, Denominaci&oacute;n o Raz&oacute;n Social</td>
				
			<td width="12%" class="encabezadoTablaResumen"
				style="text-align: center">Correo Electronico</td>
			
		</tr>
	</thead>

	<tbody>
			
			<s:iterator value= "listaAcreedores">
			<tr>  				
  				<td class="cuerpo1TablaResumen"> <s:property value="nombreCompleto" /></td>
  				
  				<td class="cuerpo1TablaResumen"> <s:property value="correoElectronico" /></td>
  				
  			</tr>
			</s:iterator> 
			
	</tbody>
</table>	
</s:if>
		
		</td>
	</tr>
	
	
	<tr>
			<TD colspan="11" class="titulo_exterior_rojo" style="font-size: 14px"
				height="20" nowrap="nowrap">Datos de la Garant&iacute;a Mobiliaria</TD>
		</tr>
		
				<tr>
					<td class="titulo_exterior_blanco" bgcolor="#DEDEDE"
						width="95%"	height="25">Acto o Contrato que crea la Garant&iacute;a Mobiliaria	
					</td>				
				</tr>
				
				<tr>
				<td>
					<table>
						<tr>
							<td width="726" align="left" class="texto_general"><span class="textoGeneralRojo"> * Tipo de Garantia Mobiliaria que se inscribe :</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="tipoGarantiaStr"/>
								
							</td>
						</tr>
						<tr>
							<td class="texto_general" align="left" id="fechaCeleb"><span class="textoGeneralRojo"> * Fecha de celebraci&oacute;n del Acto o Contrato :</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="actoContratoTO.fechaCelebracion"/>
								
							</td>
						</tr>
						
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> * Monto M&aacute;ximo Garantizado :</span></td>
						</tr>
						<tr>
							<td>
								<table>
									<tr class="texto_azul">
										<td><s:property value="actoContratoTO.montoMaximo"/> </td>
										<td> <s:property value="garantiaTO.descMoneda"/> </td>
									</tr>
								</table>
								 
							
							</td>
						</tr>
						
					</table>
				</td>
				</tr>
				
				 
						<s:if test="actoContratoTO.noGarantiaPreviaOt">
						<tr>
							<td class="textoGeneralRojo">
								Declaro que de conformidad con el contrato de garant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen otro gravamen, anotaci&oacute;n o limitaci&oacute;n previa. 
							</td>
						</tr>
						</s:if>
			
				
				<tr>
					<td>
					<table>
						<tr>
							<td width="727" align="left" class="texto_general"><span class="textoGeneralRojo"> * Tipo de Bienes Muebles objeto de la Garant&iacute;a Mobiliaria :</span></td>
						</tr>
						<tr>
							<td>
								<table class="texto_azul">
									<s:iterator value="listaBienesPendientes">
									<tr><td>
										<s:property value="descripcion"/>
									</td></tr>
									</s:iterator>
								</table>
								
								
							</td>
						</tr>
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> * Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria:</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="actoContratoTO.descripcion"/>
								
							</td>
						</tr>
						
						
						<s:if test="actoContratoTO.cambiosBienesMonto">
						<tr>
							<td class="textoGeneralRojo">
								El acto o contrato prev&eacute; incrementos, reducciones o sustituciones  de los bienes muebles o del monto garantizado
							</td>
						</tr>
						</s:if>
						<s:if test="insPublico"> 
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> Datos del Instrumento P&uacute;blico mediante el cu&aacute;l se formaliz&oacute; el Acto o Contrato :</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="actoContratoTO.instrumentoPub"/>
								
							</td>
						</tr>
						</s:if>
						<tr>
							<td class="texto_general" align="left" id="terIDcond"><span class="textoGeneralRojo"> T&eacute;rminos y Condiciones del Acto o Contrato :</span></td>
						</tr>
						<tr>
							<td class="texto_azul" style="width: 300px;">
								<s:property value="actoContratoTO.otrosTerminos"/>
								
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr><td id="noMostrar"><table>
				<tr>
					<td class="titulo_exterior_blanco" bgcolor="#DEDEDE"
						width="95%"	height="25">Acto o Contrato que crea la Obligaci&oacute;n Garantizada	</td>				
				</tr>
				<tr>
					<td>
					<table>
						<tr>
							<td width="728" align="left" class="texto_general"><span class="textoGeneralRojo"> * Acto o Contrato que crea la Obligaci&oacute;n Garantizada :</span></td>
						</tr>
						<tr>
							<td class="texto_azul" style="width: 300px;">
								<s:property value="obligacionTO.tipoActoContrato"/>
								
							</td>
						</tr>
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> * Fecha de Celebración del Acto o Contrato :</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="obligacionTO.fechaCelebracion"/>
								
							</td>
						</tr>
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> Fecha de Terminaci&oacute;n del Acto o Contrato :</span></td>
						</tr>
						<tr>
							<td class="texto_azul">
								<s:property value="obligacionTO.fechaTerminacion"/>
								
							</td>
						</tr>
						<tr>
							<td class="texto_general" align="left"><span class="textoGeneralRojo"> T&eacute;rminos y Condiciones del Acto o Contrato:</span></td>
						</tr>
						<tr>
							<td class="texto_azul" style="width: 300px;">
								<s:property value="obligacionTO.otrosTerminos"/>
								
							</td>
						</tr>
					</table>
					</td>
				</tr>
				</table></td></tr>
			<tr>
					<td class="titulo_exterior_blanco" bgcolor="#DEDEDE"
						width="95%"	height="25">Vigencia de la Inscripci&oacute;n
					</td>				
				</tr>
			<tr>
				<td class="texto_azul">
				<s:form action="actualizaVigencia.do" namespace="inscripcion" theme="simple" name="formAcVig" id="formAcVig">
				<table>
					<tr>
						<td width="469" align="left" class="texto_general"><span class="textoGeneralRojo"> * Indique por cu&aacute;ntos meses desea que la Garantía Mobiliaria quede inscrita :</span></td>
						<td class="texto_azul"> <s:property value="inscripcionTO.meses"/> </td>
					</tr>
				</table>
				</s:form>
				</td>			
			</tr>
			<tr align="left">
	<td class="ComunicaTexto"><br>
	* Campo obligatorio<br>
	</td>
</tr>
</table>
<script>
//setActiveTab('cuatroMenu');
$("#cuatroMenu").attr("class","linkSelected");

var valorSelect = <s:property value="idTipoGarantia"/>;
if (valorSelect == 10){
	document.getElementById('noMostrar').style.visibility = 'hidden';
	document.getElementById('noMostrar').style.display = 'none';					
	document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
	document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Derecho de Retención';
}else{
	if (valorSelect == 12){
		document.getElementById('noMostrar').style.visibility = 'hidden';
		document.getElementById('noMostrar').style.display = 'none';					
		document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Privilegio Especial :</span>';
		document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Privilegio Especial';
	}
}
</script>
</body>
</html>
