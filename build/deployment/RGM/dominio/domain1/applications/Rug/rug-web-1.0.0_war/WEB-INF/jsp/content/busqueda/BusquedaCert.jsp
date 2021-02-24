<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 
<style type="text/css" >
h4 {font-size: 10px; font-style: italic; color: #FF8000;}
</style>
<script type="text/javascript">
	function showBoleta() {
		var URL="<%=request.getContextPath()%>/home/boleta.do";
		window.location.href=URL;
	}

	function validaCadena(){
		if((getObject('numOperacion').value == "0")||(getObject('numOperacion').value == "")){
			displayAlert(true, 'Información Incompleta', 'Debe Introducir la Cadena Única de Datos');
		}
			else{
				sendForm();
				}
		
	}
	function sendForm(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('busquedaCert').submit();
	}

</script>
  
<s:form id="busquedaCert" action="busquedaCert.do" theme="simple">
<!--
<div style="width: 760px" align="right"> <a style="font-size: 12px;"	tabindex="31" target="_blank" href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=validacion_datos.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" title="" class="thickbox"><b>Video-Tutorial "Validación de Boletas y Certificaciones"</b> <img  style="width: 31px; height: 31px "  src="<%= request.getContextPath() %>/resources/imgs/caratula1_rug.jpg" border="0"></a></div>
<table width="747" >
	<tr>
		<td width="739" class="titulo_exterior_rojo">Validaci&oacute;n de Boletas y Certificaciones <br></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td><table class="nota">
					<tr>
						<td class="imgNota"><img
							src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png">
						<s:text name="common.nota" /></td>
						<td class="contenidoNota"><s:text
							name="Todas  las boletas y certificaciones expedidas por el RUG contienen <b>una cadena única de datos</b>, que podrá ser ingresada al Sistema para verificar su autenticidad. Art 34 RRPC." />
						</td>
					</tr>
			</table> 
		</td>
	</tr>
	
	<tr>
		<td ><br><hr><br></td>
	</tr>
	
	<tr>
		<td>
			<table width="97%" align="center">
				<tr>
					<td class="texto_azul" width="3%" align="right">*</td>
					<td width="97%" class="textoGeneralRojo">Cadena Única de Datos:</td>
				</tr>	
			</table>
				
			<table>
				<tr>
					<td width="31"></td>
					<td width="696">
						<dl style="width: 60px">
							<dd style="width: 60px">
								<s:textfield name="numOperacion" id="numOperacion" size="10" maxlength="10" onkeypress="return IsNumber(event);"/>
								<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Ingrese el Número de Asiento o Cadena Única de Datos  que se encuentra en la Boleta o Certificación que desea validar.</div><span class="hint-pointer">&nbsp;</span></span>
						 	</dd>
						</dl>
					</td>
				</tr>
			</table>
		</td>
	</tr>	
</table>
	<tr></tr>
	<tr>
		<td align="center"><input type="button" value="Validar" class="tituloInteriorRojo" onclick="validaCadena();" id="bFirmar"/>
		</td>
	</tr>
	<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr> -->
	<s:if test="hayDatos">
	<tr id="datosCadena"><td><table width="739">
	  <tr>
		<td width="828" align="left"><span class="textoGeneralRojo"> La Cadena &Uacute;nica de Datos se encuentra relacionado al Tr&aacute;mite:</span> <span class="textoGeneral"><s:property value="tipoOperacion"/> </span></td>  
	</tr>
	<tr>
		<td align="left"> <span class="textoGeneralRojo"> El n&uacute;mero de garant&iacute;a es:</span> <span class="textoGeneral"><s:property value="numGarantia"/> </span></td>
	</tr>
	<tr>
		<td align="left"> <span class="textoGeneralRojo"> Fue realizada el d&iacute;a: </span>
		 <span class="textoGeneral"> <s:property value="fechaInsc"/> <s:property value="fechaStr"/> </span></td> 
	</tr>
	<tr>	
		<td align="left"> <span class="textoGeneralRojo"> Por los Otorgantes de la Garant&iacute;a Mobiliaria: </span> </td>
	</tr>
	<tr>	
		<td align="left">		
		<table>
			<tr><td class="textoGeneralRojo">Nombre :</td>
			<td class="textoGeneralRojo">Folio Electr&oacute;nico:</td></tr>
			<s:iterator value="otorgantes">
			<tr>
				<td class="textoGeneral"><s:property value="nombreCompleto"/> </td>
				<td class="textoGeneral"><s:property value="folioMercantil"/></td>
			</tr>
			</s:iterator>
		</table>
		
		</td>
	</tr>
	<tr>
	</tr>
	<s:if test="idTipoTramite!=null&&idTipoTramite==21">
	<tr>
	</tr>
	<tr>
	<td><table><tr><td class="textoGeneralRojo">Debido al t&eacute;rmino de vigencia, esta garant&iacute;a se cancel&oacute; autom&aacute;ticamente</td></tr></table></td>
	</tr>
	</s:if>
	<s:else>
	<tr>
		<td align="center"> 
		<h4 style="cursor: pointer">
		<a onclick="showBoleta();"> <u>Clic aqu&iacute; para ver la boleta</u></a>
		</h4>
		</td>
	</tr>
	</s:else>
	</table></td></tr>
	</s:if>
	<s:else>
	<s:if test="pasoInicio">
	<tr>
	</tr>
	<tr id="noDatosCadena"><td><table><tr>
		<td class="textoGeneralRojo" align="center"> No existe un Tr&aacute;mite relacionado a esta Cadena &Uacute;nica de Datos</td>  
	</tr>
	</table></td></tr>
	</s:if>
	</s:else>
	
	<tr> <td> <br> <a  class="ComunicaTexto" href="/Rug/home/busqueda.do"> <b>Regresar a Pantalla Anterior </b> </a>    </td></tr>
	<!-- <tr align="left"> <td class="ComunicaTexto"><br> * Campo obligatorio<br></td></tr>  -->
</table>

</s:form>
<script>
function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	if (key == 13) {  
		validaCadena();
		return false;
	}
	return (key <= 13 || (key >= 48 && key <= 57));
}
//setActiveTab('unoMenu');
//setActiveTabBusqueda('dosMenuBusqueda');
$("#unoMenu").attr("class","linkSelected");
</script>