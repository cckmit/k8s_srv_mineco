<%@page import="java.util.Iterator"%>
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
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 

<!--  <! DOCTYPE html>
<html>
<head>-->

  <script language="javascript">
  function sendForm(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('modificacionsgID').submit();
	}
function validacion(){
	id1='personaAutoridadAutorizaId';
	id2='origenTramiteID';
	id3='anotacionId';
 if(getObject(id1).value=='')
     {
	   changeStyle("messageAlertDiv","HEIGHT:50px; WIDTH:200px");
       changeStyle("messageAlertDiv","HEIGHT:160px; WIDTH:350px");
	   displayAlert(true,"Autoridad","El campo Persona que solicita o Autoridad que instruye la Modificación NO puede permanecer vacio");
	   return false;
     }
 else if(getObject(id2).value=='')
 {
   
  displayAlert(true,"Convenio","El campo Acto o Convenio que da origen a la Modificación NO puede permanecer vacio");
  return false;
 }
 else if(getObject(id3).value=='')
 {
   
   displayAlert(true,"Resolucion","El campo Contenido de la resolución NO puede permanecer vacio");
   return false;
 }
 
 sendForm();
     
}
</script>

  <script type="text/javascript">
     function ismaxlength(obj)
      {
	//Variables que definen el largo de texto que podra ser insertado al cuadro del texto(limitado a 5000 caracteres)
	var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length>mlength)
         {
	   alert('"El texto ingresado será truncado a '+mlength+' caracteres, debido a que la información ingresada es mayor a la permitida. Por favor Verifique"  ');
	   obj.value=obj.value.substring(0,mlength);
	 }

       }
   </script>

   <script type="text/javascript">
      function permite(elEvento, permitidos)
        {
	   // Variables que definen los caracteres permitidos
	   var numeros = "0123456789";
	   var caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	   var numeros_caracteres = numeros + caracteres;
	   var teclas_especiales = [8, 37, 39, 46];
	   // 8 = BackSpace, 46 = Supr, 37 = flecha izquierda, 39 = flecha derecha
	 
	 
	   // Seleccionar los caracteres a partir del parámetro de la función
	   switch(permitidos) {
	     case 'num':
	       permitidos = numeros;
	       break;
	     case 'car':
	       permitidos = caracteres;
	       break;
	     case 'num_car':
	       permitidos = numeros_caracteres;
	       break;
	  }
	 
	   // Obtener la tecla pulsada 
	    var evento = elEvento || window.event;
	    var codigoCaracter = evento.charCode || evento.keyCode;
	    var caracter = String.fromCharCode(codigoCaracter);
	 
	   // Comprobar si la tecla pulsada es alguna de las teclas especiales
	   // (teclas de borrado y flechas horizontales)
	    var tecla_especial = false;
	     for(var i in teclas_especiales)
              {
	       if(codigoCaracter == teclas_especiales[i]) 
                {
	         tecla_especial = true;
	         break;
	        }
	      }
	 
	   // Comprobar si la tecla pulsada se encuentra en los caracteres permitidos
	   // o si es una tecla especial
	     return permitidos.indexOf(caracter) != -1 || tecla_especial;
	}
   </script>
   
   <script>
      function FIRMA()
	//Funcion que indicará la accion que se realizara con la info una vez que se presione el boton FIRMA
       {

       }
   </script>

<!--    <meta charset=utf-8 />
   <title>..::Secretaria de Econom&iacutea::..::Registro &Uacutenico de Garant&iacuteas Moviliarias::..</title>
 </head>-->





<!-- <body>

<head> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" />
  </head> -->
<s:form name="modificacionsg" id="modificacionsgID" namespace="anotacion" action="saveModificacion.do" theme="simple" >
<table>
 
 <tr>
   <td>
      <table cellspacing="0">
       <tr>
         <td>  <h1>Modificaci&oacute;n</h1> </td>
       </tr>
      </table>
   </td>
  </tr> 
  <tr>
    <td> 
      <table>
        <tr>
         <td align="left" style="padding-left: 28px;font-size: 10px; height: 28px;" class="texto_azul"> * Campo Obligatorio. </td>
        </tr>
      </table>
    </td>
   </tr>
   <tr>
     <td >   
      <table class="nota"> 
        <tr>
          <td class="imgNota" >   <img  src="/Rug/resources/imgs/ico_nota.png">   </td>
          <td align="justify" class="contenidoNota">
              En esta secci&oacute;n usted podr&aacute; corregir cualquier error en la informaci&oacute;n de su<br>
              Garant&iacute;a Mobiliaria registrada. Fundamento Legal - Art. 33 bis 3RRPC
          </td>
        </tr> 
      </table>
     </td>
    </tr>
    <tr>
      <td> 
        <table border="0">
          <tr>
            <td class="tituloHeader1" height="16"> 1. Solicitante  </td>  
          </tr>
          <tr>
            <td colspan="3" class="textoGeneralRojo" style="padding-left: 22px;" align="left">
             Persona que solicita o Autoridad que instruye la Modificaci&oacute;n        &nbsp; 
              <a tabindex="31" href="/Rug/comun/publico/help.do?llave=ayudaModificacionSnGarantia&keepThis=true&TB_iframe=true&height=170&width=500" title="" class="thickbox"><img  src="/Rug/resources/imgs/documentinfo.png" alt="HEEELPPP!!!"> </a>
            </td>
          </tr>
        </table>
     </td>
    </tr>
    <tr>
      <td> 
       <table>
         <tr>
           <td>
            *
           </td>
           <td>
           <s:textarea  id="personaAutoridadAutorizaId" cols="75" rows="8"  name="anotacionSnGarantia.autoridadAutorizaTramite" onkeyup="return ismaxlength(this)" maxlength="3000"/>
             
           </td>
        </tr>
      </table>
     </td>
    </tr> 
      
 <tr>
   <td>
     <table border="0">
       <tr>
         <th rowspan="2" style="padding-right:40px;" class="textoEjemplo">  Ejemplo </th>
           <td width="434px;" class="textoGris"  align="justify"> a. C. Juez quinto de lo civil en el estado de Nuevo Leon </td>
       </tr>
          <td width="434px;" class="textoGris"  align="justify"> b. C. Juez D&eacute;cimo Cuarto de lo mercantil del primer partido judicial en el estado de Jalisco  
          </td>
      </table>
   </td>
 </tr>

 
 <tr>
   <td>
      <table>
        <tr>
          <td class="tituloHeader1" height="25">   2. Persona en cuyo Folio Electr&oacute;nico se realizar&aacute; la Anotaci&oacute;n </td>
        </tr>
      </table>
   </td>
 </tr>
 <tr>
   <td>
    <table> 
      <tr>
       <td id="pOtorgante"></td>
      </tr>
    </table>
   </td>
 </tr>
 
<tr>
 <td>
   <table cellspacing="0">
    <tr>
      <td class="tituloHeader1" height="16">
        3. Anotaci&oacute;n
      </td>
    </tr>
    <tr>
       <td colspan="3" class="textoGeneralRojo" style="padding-left: 21px;" align="left">
           Contenido de la resoluci&oacute;n :
       </td>
      
    </tr>
   </table> 
 </td>
</tr>

<tr>
   <td>
     <table>
       <tr>
         
         <td colspan="3">
         <s:textarea  id="anotacionId" cols="75" rows="8"  name="anotacionSnGarantia.anotacion" onkeyup="return ismaxlength(this)" maxlength="3000"/>
       </td>
       </tr>
     </table>
   </td>   
 </tr>

<tr>
  <td align="left">
    <table class="nota">
      <tr>
        <td class="imgNota"> 
          <img src="/Rug/resources/imgs/ico_nota.png"> 
        </td>  
        <td align="justify" class="contenidoNota"> 
           Deber&aacute especificar la resoluci&oacute;n judicial o administrativa en la cual se funda la <br>
           anotaci&oacute;n, as&iacute; como el texto a anotar. Fundamento      Legal - Art 33 Bis 3 del RRPC
        </td>
      </tr>
    </table>
  </td>
</tr>

<tr>
 <td>
   <table>
     <tr>
     <td colspan="3" class="textoGeneralRojo" style="padding-left: 22px;" align="left">
        Acto o Resoluci&oacute;n que da origen a la Modificaci&oacute;n:
          
      </td>
      
     </tr>
     <tr>
     
       <td colspan="3">
        <s:textarea  id="origenTramiteID" cols="75" rows="8" name="anotacionSnGarantia.origenTramite"  onkeyup="return ismaxlength(this)" maxlength="3000"/>
           
         </td>
       
     </tr>
  
   </table>
 </td>
</tr>

<tr>
  <td>
    <table>
      <tr>
        <th  rowspan="2" class="textoEjemplo"> Ejemplo </th>
        <td width="474px;" class="textoGris"  align="justify">
          a) Licenciado Francisco Rodr&iacute;guez Hern&aacute;ndez, Juez D&eacute;cimo Cuarto de lo Mercantil del Primer Partido <br> 
             Judicial en el estado de Jalisco. 
        </td>
      </tr>
        <td width="474px;" class="textoGris"  align="justify">
           b) Oficio 222:10 Expediente 2288/2010 Dentro de los .... del Juicio Mercantil Ejecutivo promovido <br> 
           por Ricardo Ugalde Garc&iacute;a en contra de FFF ACCIONES Uacute;TILES Y GRANOS, S.A. DE C.V.
        </td>
    </table>
  </td>
</tr>

<tr>
  <td>
    <table border="0" width="800">
      <tr height="80">
        <td align="center">
          <input type="button" value="Firmar" class="boton_rug"  onclick="validacion();" id="bFirmar"/> 
        </td>
      </tr>
    </table>
  </td>
</tr>

 
</table>
<div style="visibility: hidden;"><s:property value="msgError"/></div>  
</s:form>
<!-- </body>
</html>  -->
<script type="text/javascript">
		//$("#cuatroMenu").attr("class","linkSelected");
		var idTramite= <s:property value="idTramiteNuevo"/>;
		var idPersona = <s:property value="idUsuario"/>;
		cargaParteOtorgante('pOtorgante',idTramite, idPersona, '0','0','1');
		//activaBtn1();
	</script>
