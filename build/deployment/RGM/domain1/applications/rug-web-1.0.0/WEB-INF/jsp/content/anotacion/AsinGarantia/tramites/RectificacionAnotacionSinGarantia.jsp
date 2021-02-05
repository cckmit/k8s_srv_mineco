<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 


<!-- Esta funcion valida los campos indicando al usuario que no pueden permanecer vacios -->

<script language = "javascript">
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	document.getElementById('rectificacionsgID').submit();
}
function validar(){
 if(getObject('personaAutoridadAutorizaTramiteId').value=='')
     {
     alert('El campo Autoridad que instruye la Rectificación por Error NO puede permanecer vacio');
     return false;
    }
     if (getObject('personaAutoridadAutorizaId').value=='')
     {
     alert('El campo Autoridad que instruye la Anotación NO puede permanecer vacio');
     return false;
    }
    if (getObject('anotacionId').value=='')
    {
     alert('El campo Contenido de la Resolución NO puede permanecer vacio');
     return false;
    }  
    sendForm();
}
</script>

<!-- Esta funcion indica cuan largo sera el texto introducido en los campos avisando al usuario que dichos campos no rebasaran los 5000 caracteres -->

<script type="text/javascript">
function ismaxlength(obj){
	//Variables que definen el largo de texto que podra ser insertado al cuadro del texto(limitado a 5000 caracteres)
	var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length>mlength){
		alert('"El texto ingresado será truncado a '+mlength+' caracteres, debido a que la información ingresada es mayor a la permitida. Por favor Verifique"  ');
		obj.value=obj.value.substring(0,mlength);
	}

}
</script>




<s:form name="rectificacionsg" id="rectificacionsgID" namespace="anotacion" action="saveRectificacion.do" theme="simple" >

<table border="0">

<tr>
<td width="960px" height="25" style="padding-left: 5px;"><h1>Rectificación por
				Error </h1><a tabindex="31" target="_blank" href="/Rug/resources/videos/video.jsp?llave=videoRenovacion&amp;heightV=450&amp;widthV=500&amp;videoUrl=rectificacion_por_errorII.flv&amp;alone=1&amp;keepThis=true&amp;TB_iframe=true&amp;height=490&amp;width=500" class="thickbox"><h2>(Video
					Tutorial)</h2></a></td>
</tr>

<tr>
<td align="left" style="padding-left: 28px;font-size: 10px; height: 28px;" class="texto_azul" colspan="2"> * Campo Obligatorio. </td>
</tr>

<tr>
<td>
<table style="margin-left:280px" cellpadding="0" width="0%" bordercolor="#D9D3D3">

<table style="margin-left:240px" border="0" cellpadding="0" cellspacing="0" width="0%"bordercolor="#D9D3D3">
    	<tr >
    	<td BGCOLOR="#D9D3D3" align="justify"><img src="/Rug/resources/imgs/ico_nota.png" >
    	<font>En esta sección usted podrá corregir cualquier error en la información de su</font>
 		<font><br>Cuenta Mobiliaria registrada. Fundamento Legal - Art.33 Bis3 del RFFC</br></font>
    	</td>
    	
</table>
</table>
</td>
</tr>


<tr>
         <td colspan="2" height="100" style="padding-left: 9px;" class="tituloHeader1">1.Solicitante
         </td>  	
         </tr>
         <tr>
    	 <td align="left" style="padding-left: 21px;" class="textoGeneralRojo" colspan="2">Persona que solicita o Autoridad que instruye la Rectificación por Error<a tabindex="31" href="/Rug/comun/publico/help.do?llave=anotacionContenidoResolucion&keepThis=true&TB_iframe=true&height=170&width=520" title="¿Desea obtener ayuda?" class="thickbox"><img alt=" cofepris.domicilio.tramite.ayuda "  src="/Rug/resources/imgs/documentinfo.png" border="0"></a></td>   	
         </tr>


<tr>
<td>
<table border="0">
<td><h5>*</h5></td>
   	    
   	     
   	     <td>
   	     <s:textarea  id="personaAutoridadAutorizaTramiteId" cols="75" rows="8"  name="anotacionSnGarantia.autoridadAutorizaTramite" onkeyup="return ismaxlength(this)" maxlength="3000"/>
         </td>
</table>
</td>
</tr>


<tr>
<td align="left" style="padding-left: 21px;" class="textoGeneralRojo" colspan="2">Persona que solicita o Autoridad que instruye la Anotación: <a tabindex="31" href="/Rug/comun/publico/help.do?llave=anotacionContenidoResolucion&keepThis=true&TB_iframe=true&height=170&width=520" title="¿Desea obtener ayuda?" class="thickbox"><img alt=" cofepris.domicilio.tramite.ayuda "  src="/Rug/resources/imgs/documentinfo.png" border="0"></a>   	
         </td>
</tr>


<tr>
<td>
<table border="0">
<td><h5>*</h5></td>
   	    
   	     
   	     <td>
   	     <s:textarea  id="personaAutoridadAutorizaId" cols="75" rows="8"  name="anotacionSnGarantia.autoridadAutoriza" onkeyup="return ismaxlength(this)" maxlength="3000"/>
<br>
         </td>
</table>
</td>
</tr>


<tr>
<td>
<table style="margin-left:150px" border="0" colspan="2">
<tr>
 <th rowspan="2">Ejemplo:</th>
 <td><FONT COLOR="GRAY" width="434px;" align="justify"/></h5>a) C. Juez quinto de lo civil en el estado de nuevo León.</td>
</tr>
<tr>
 <td><FONT COLOR="GRAY" width="434px;" align="justify"/></h5>b) C. Juez Décimo Cuarto de lo mercantil del primer partido judicial en el estado de jalisco.</td>
</tr>
</table>
</td> 
</tr>

<tr>
<td><font color= "#B30C0C"><h2>2. Persona en cuyo Folio Electronico se realizará la anotación</h2></font>
</tr>

<tr>
<td id="pOtorgante"></td>
</tr>

<tr>
<td><font color= "#B30C0C"><h2>3. Anotaci&oacute;n</h2></font>   	</td>
</tr>

<tr>
<td>
<table style="margin-left:280px" cellpadding="0" width="0%" bordercolor="#D9D3D3">

<tr >
    	<td BGCOLOR="#D9D3D3" align="justify"><img src="/Rug/resources/imgs/ico_nota.png" >
    	<font>Deberá especificar la Resolución Judicial o Administrativa en la cual de funda la</font>
 		<font><br>Anotación, asi como el Texto a anotar. Fundamento Legal Art. 33 Bis 3 del RRPC</font>
    	</td>
    	</tr>
</table>
</td>
</tr>


<tr>
<td align="left" style="padding-left: 21px;" class="textoGeneralRojo" colspan="2">Contenido de la Resoluci&oacute;n:</td> 
</tr>


<tr>
<td>
<table border="0">
<td><h5>*</h5></td>
   	    
   	     
   	     <td>
<s:textarea  id="anotacionId" cols="75" rows="8"  name="anotacionSnGarantia.anotacion" onkeyup="return ismaxlength(this)" maxlength="3000"/><br>
         </td>
</table>
</td>
</tr>

<tr>
<td>
<table style="margin-left:50px" border="0" colspan="2">
<tr>
 <th rowspan="2">Ejemplo:</th>
 <td><FONT COLOR="GRAY" width="434px;" align="justify"/></h5>a) Licenciado Francisco Rodriguez Hernandez, Juez Decimo Cuarto de lo Mercantil,del Primer Partido Judicial, <br>en el Estado de Jalisco.</td>
</tr>
<tr>
 <td><FONT COLOR="GRAY" width="434px;" align="justify"/></h5>b) Oficio 222/10, Expediente 2288/2010.Dentro de los Autos del Juicio Mercantil Ejecutivo promovido por <br>Rodrigo Guadarrama Flores en contra de REFACCIONES UTILES Y GRANOS S.A de C.V<A HREF="http://systemdefault.webs.com/ ">.</A></td>
</tr>
</table>
</td> 
</tr>

<tr>
<td align="center"colspan="2">
          <input type="button" value="Firmar" id="bFirmar" class="boton_rug"  onclick="validar();" id="bFirmar"/> 
          
</td>
</tr>




</table>
<div style="visibility: hidden;"><s:property value="msgError"/></div>  
</s:form>


<!-- Esta funcion muestra en pantalla la tabla de seleccion /tipo de persona -->

<script type="text/javascript">
		
		var idTramite= <s:property value="idTramiteNuevo"/>;
		var idPersona = <s:property value="idUsuario"/>;
		cargaParteOtorgante('pOtorgante',idTramite, idPersona, '0','0','1');
		
</script>










