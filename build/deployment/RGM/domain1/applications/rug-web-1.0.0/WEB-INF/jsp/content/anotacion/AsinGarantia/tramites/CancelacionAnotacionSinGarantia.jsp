<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/rug.css" media="screen" type="text/css" />
<script type="text/javascript">
function sendForm(){
	var pasa= false;
	valor= document.getElementById('personaAutoridadAutorizaId').value;
	for ( i = 0; i < valor.length; i++ ) {  
	    if ( valor.charAt(i) != " " ) {
		    pasa= true;
	    }
	}
	if (pasa){
		//self.parent.tb_remove();
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		document.getElementById('cancelacionsgID').submit();
	}
	else{
		alert("El campo de Persona que solicita o Autoridad que instruye la Cancelación y Resolución Judicial o Administrativa en la cual se funda la misma es obligatorio");
		return false;
	}
}
</script>

<s:form name="cancelacionsg" id="cancelacionsgID" namespace="anotacion" action="saveCancelacion.do" theme="simple" >
		
		<table id="tablaPaso1Gral" cellspacing="5" cellpadding="0"> 
			<tr> 
				<td width="500" class="textoGeneralRojo" height="21" style="padding-left: 5px;">Cancelaci&oacute;n de Anotaci&oacute;n &nbsp <a class="thickbox" href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=cancelacion_avi.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500"  target="_blank" tabindex="31" >(Video Tutorial)</a></td>		
			</tr>
		</table>
		
		<table>
			<tr>
				<td align="left" style="font-size: 10px; height: 28px;padding-left: 28px;" class="texto_azul">* Campo Obligatorio </td> 
			</tr> 
		</table>
		
		<div style='margin-top:1px;'>
		
		<table> 
			<tr> 
				<td colspan="2" class="textoGeneralRojo" align="left" style="padding-left: 29px;"> Persona que solicita o Autoridad que instruye la Cancelaci&oacute;n y Resoluci&oacute;n Judicial o Administrativa en la cual se funda la misma: <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=cancelacionAutoridad&keepThis=true&TB_iframe=true&height=175&width=500" target="_blank" title="" class="thickbox"><img alt=" <s:text name="" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
			</tr> 
			<tr> 	
				<td> 
					<table> 
						<tr> 
							<td class="texto_azul" width="14px;" style="padding-left: 25px;">*</td>
							<td> 
							<s:textarea  id="personaAutoridadAutorizaId" cols="60" rows="8"  name="anotacionSnGarantia.autoridadAutorizaTramite" onkeyup="return ismaxlength(this)" maxlength="3000"/> 
							</td>
						</tr>		
					 </table>
				</td>	
			</tr>
		
			<tr> 
				<td> 
					<table>
						<tr> 
							<td style="padding-left:42px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="352px;" class="textoGris textoJustificado"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris textoJustificado" align="justify">b) Oficio 222/10 Expediente 2288/2010.Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V.</td> 
									</tr> 
								</table>
							</td> 
						</tr> 
					</table>
				</td>
			</tr>
			
			<tr> 
				<td> 
					<table align="center">
						<tr>
							<td><input id="bFirmar" type='button' name='Aceptar' value='Aceptar' onclick="sendForm();" class="boton_rug"/> </td> 
							<td> <input type='button' name='cancelar' value='Cancelar' class="boton_rug" onclick="self.parent.tb_remove()"/> </td> 
						</tr> 
					</table>
				</td>
			</tr>
		</table>
		<div style="visibility: hidden;"><s:property value="msgError"/></div>  
</s:form>