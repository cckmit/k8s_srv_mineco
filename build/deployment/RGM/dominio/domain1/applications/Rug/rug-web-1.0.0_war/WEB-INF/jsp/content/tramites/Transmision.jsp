<%@page import="java.util.Iterator"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"	  src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/cambioOtorganteService.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript" 	  src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"	  src="<%=request.getContextPath()%>/resources/js/FuncionesDeFechas.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 
<script type="text/javascript">
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>


	function sendForm(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('fatransmision').submit();
	}

	function isNullOrEmpty(valor){
		if ( valor == null )
			true;
		for ( i = 0; i < valor.length; i++ )
				if ( valor.charAt(i) != " " )
					return false; 
		return true; 
	}

	var modificoOtorgante = -1;
	var modificoAcreedores = -1;
	
	function transmisionValida(){
		cambioOtorganteService.cambioOtorgante('<s:property value="#session.idTramiteNuevo" />', '<s:property value="#session.idGarantia" />', '<s:property value="#session.usuario.persona.idPersona"/>', imprimeDato);
	}
	
	function validar(){
		// validando campos obligatorios
		var tipoGarantia = document.getElementById('modtipogarantia').value;
		var dia = new Date();
		var bandera = true;
		
		if (isNullOrEmpty(document.getElementById("modtipoacto").value)) {
			alert('Los campos marcados con * son obligatorios.');
			bandera = false;
		}else{
			// si fecha 1 es null
			if(isNullOrEmpty(document.getElementById("datepicker1").value)){
				alert('Los campos marcados con * son obligatorios.');
				bandera = false;
			}
		}
		if(bandera){			     
			sendForm();
		}
	}

	function validarAutoridad(){
		// validando campos obligatorios con autoridad
		var tipoGarantia = document.getElementById('modtipogarantia').value;
		var dia = new Date();
		var bandera = true;
		
		// validando campos obligatorios sin autoridad
		if (isNullOrEmpty(document.getElementById("modtipoacto").value)
				|| isNullOrEmpty(document.getElementById("autoridad").value )) {
			alert('Los campos marcados con * son obligatorios.');
			bandera = false;
		}else{
			// si fecha 1 es null
			if(isNullOrEmpty(document.getElementById("datepicker1").value)){
				alert('Los campos marcados con * son obligatorios.');
				bandera = false;
			}
		}
		if(bandera){			     
			sendForm();
		}			
		
	}

	function imprimeDato(data){
		//alert("Usando la vieja usansa del dwr:" + data);
	
		modificoOtorgante = data;
		modificoAcreedoresAdicionales();
	}

	function modificoAcreedoresAdicionales(){
		cambioOtorganteService.cambioAcreedores('<s:property value="#session.idTramiteNuevo" />', '<s:property value="#session.idGarantia"/>', asignaDato);
	}

	function asignaDato(data){

		modificoAcreedores = data;
		_temporal();
	}
	
	function _temporal(){
		// si no se mueve el otorgante, ni el acreedor principal ni los acredores adicionales, transmision no valida 
		if(modificoOtorgante == 0 && modificoAcreedores == 0 && document.getElementById('idAcreedorTrasmite').value == -1){
				alert("Para realizar la transmisión debe modificar al acreedor y/o acreedores adicionales y/o al otorgante.");
		}else{
			<% if (priv.get(new Integer(20))!=null ) { %>
				validarAutoridad();
			<% }else{ %>
				validar();
			<% } %>
		}
	}


	document.onkeydown = function(){  
    if(window.event && window.event.keyCode == 116){ 
     window.event.keyCode = 505;  
    } 
    if(window.event && window.event.keyCode == 505){  
     return false;     
    }  
   }   

	function pon_hora(){ 
	    var dHora = new Date(); 
	    var res = String(dHora.getHours()) + ":" + String(dHora.getMinutes()) + ":" + String(dHora.getSeconds()); 
	    document.fatransmision.txt.value = res; 
	    return res; 
   }  

	function pagar(){		
			getObject('fatransmision').action="savetransmision.do";
			getObject('fatransmision').submit();					
			//location.href = "< %=request.getContextPath() %>/home/agregadeudor.do";	
	}	

	function marcar(s) {		
		cual=s.selectedIndex;
		for(y=0;y<s.options.length;y++){
		if(y==cual){
			s.options[y].selected=(todos[y]==true)?false:true;
			todos[y]=(todos[y]==true)?false:true;
		}else{
		s.options[y].selected=todos[y];
			}
		}
	}

	function escondePartes(){
		var _valor = document.getElementById('modtipogarantia').value;
		var bfecha1 = true;
		var bfecha2 = false;
		var bfecha3 = false;

		switch(_valor){
			case "1":
				/*
				fechadato= "* Fecha de celebración del acto o contrato"				
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir'; */			
				displayObject('fecha1',true);
				displayObject('fecha2',false);	
				displayObject('fecha3',false);				
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "2":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1', true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "3":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "4":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "5":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "6":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "7":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "8":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "9":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "10":				
				/*document.getElementById('obligacionDIV').style.visibility = 'hidden';
				document.getElementById('obligacionDIV').style.display = 'none';					
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Derecho de Retención';*/
				displayObject('fecha1',false);
				displayObject('fecha2',true);
				displayObject('fecha3',false);
				displayObject('terminos1',false);
				displayObject('terminos2',true);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="none";  
				document.getElementById("tittipoacto").style.display="none"; 
				document.getElementById("tipoContOb").style.display="none";
				document.getElementById("titdate2").style.display="none"; 
				document.getElementById("datepicker4").style.display="none";
				document.getElementById("titdate3").style.display="none"; 
				document.getElementById("datepicker5").style.display="none";
				document.getElementById("titterminos").style.display="none"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="none";
				break;
			case "11":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			case "12":
				/*document.getElementById('obligacionDIV').style.visibility = 'hidden';
				document.getElementById('obligacionDIV').style.display = 'none';					
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Privilegio Especial :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Privilegio Especial';*/
				displayObject('fecha1',true);
				displayObject('fecha2',true);
				displayObject('fecha3',true);
				displayObject('terminos1',true);
				displayObject('terminos2',true);
				displayObject('terminos3',true);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
		}			
	}
</script>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript">
	$(function () {
		$('.ayuda').ayuda({
			url: '<%= request.getContextPath() %>/comun/publico/help.do',
			ico: '<%= request.getContextPath() %>/resources/imgs/documentinfo.png',
			width: 500,
			height: 400
		})
	})
</script>
 	
 <div id="transmision" style="margin-top: 50px">
 	<table> 
 		<tr> 
 			<td width="960px"height="25" style="padding-left: 5px;"><h1>Transmisi&oacute;n</h1> <a class="thickbox"  target="_blank" href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=8_transmision_mov.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" tabindex="31"><h2>(Video Tutorial)</h2></a></td>
 		</tr>
 		
 		<tr>	
			<td>
				<table style="padding-left: 6px; padding-right: 5px;" width="743px;">
					<tr>
						<td align="left" style="background-color: #D8D8D8; padding-left:10px; padding-top:4px; padding-bottom:4px" class="texto_bolder_gris"><b> <s:property value="nomAcreedor" /> </b></td> 
					</tr> 
				</table>
			</td>
		</tr>
		
		
		<tr>
			<td align="left" style="padding-left: 9px;font-size: 10px; height: 28px;" class="texto_azul">* Campo Obligatorio </td> 
		</tr> 
		<tr></tr>
		<tr> 
			<td align="center" style="padding-left: 34px;"> 
				<table class="nota">
					<tr>
						<td class="imgNota">
							<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
							
						</td>
						<td align="justify" class="contenidoNota textoJustificado"><s:text name="Mediante esta operación usted podrá agregar, eliminar o modificar al otorgante o al acreedor, incluyendo a los acreedores adicionales. Únicamente podrá modificar los campos habilitados." /><b> <s:text name="Fundamento Legal.-" /></b> <s:text name="Art. 33 bis 5 RRPC" /> </td>
					</tr>
				</table>
			</td>
		</tr>
 	</table>
	
	<s:form id="fatransmision" name="fatransmision" action="savetransmision.do" namespace="home" theme="simple">
	
	<table width="743px;"> 
		<tr> 
			<td align="left" class="tituloHeader1" height="25">Acreedor</td>
		</tr>
		<tr> 
			<td align="left" style="padding-left: 28px;">
				<table border="0" width="79%" cellspacing="1" cellpadding="1" align="left" style="width: 500px;">
					<thead>
						<tr>
							<td class="encabezadoTablaResumen"	style="text-align: center">Nombre, Denominación o Razón Social</td>
							<td class="encabezadoTablaResumen" style="text-align: center">Tipo de Persona</td>					
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="cuerpo1TablaResumen" style="text-align: center"><s:property value="acreedorRepresentado.nombreCompleto" /></td>
							<td class="cuerpo1TablaResumen" style="text-align: center"><s:property value="acreedorRepresentado.tipoPersona" /></td>		
						</tr>		
					</tbody>
				</table>
			</td> 
		</tr>
		<tr> </tr> 
		<tr>
			<td id="notaMoral" style="padding-left: 37px;">
				<table class="nota">
					<tr>
						<td class="imgNota">
							<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
							
						</td>
						<td align="justify" class="contenidoNota textoJustificado"><s:text name="Es necesario que usted esté autorizado por el  Acreedor al que se transmitirá la Garantía Mobiliaria, para poder realizar esta operación." /> </td>
					</tr>
				</table>	
			</td>
		</tr>
		<tr></tr><tr></tr><tr></tr>
		<tr> 
			<td align="left" style="padding-left: 30px;"> 
				<table border="0" width="79%" cellspacing="1" cellpadding="1" align="left">
					<s:select name="acreedorTransmite" id="idAcreedorTrasmite" list="acreedoresDisponibles" 
					listKey="idAcreedor" listValue="nombreCompleto" cssClass="texto_general"
					headerValue="-- Seleccione acreedor --" headerKey="-1"
					label="Seleccione el acreedor al que transmitirá la garantía" labelposition="top" />
				</table>
			</td> 
		</tr>
		<tr> </tr> 
	</table>
	
	<!--<div>--> <!--<s:form id="fatransmision" name="fatransmision" action="savetransmision.do" namespace="home" theme="simple">-->
<% if (priv.get(new Integer(20))!=null ) { %>
	<table>	
		<tr>
			<td class="tituloHeader1" height="25">1. Solicitante</td>
		</tr>
		<tr>
			<td>
				<table width="742">
					<tr>
						<td style="padding-left: 7px;" width="715" colspan="2" align="left" class="textoGeneralRojo">Persona que solicita o Autoridad que instruye la Transmisi&oacute;n y contenido de la Resoluci&oacute;n <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=autoridadTransmision&keepThis=true&TB_iframe=true&height=300&width=500" title="" class="thickbox"><img alt=" <s:text name="Persona Autoridad" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
	  				</tr>
					<tr>
						<td colspan="2" style="padding-left: 5px;">
							<table> 
								<tr> 
									<td class="texto_azul" width="19">*</td>
									<td class="textoJustificado"><s:textarea cols="70" rows="7" name="autoridad" value="%{autoridad}" id="autoridad" maxlength="3000" onkeyup="return ismaxlength(this)" />	</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr> 
						<td> 
							<table>
								<tr> 
									<td style="padding-left:26px;" class="textoEjemplo">Ejemplo&nbsp;</td>
									<td>
										<table>
											<tr>
												<td width="415px;" class="textoGris textoJustificado"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris textoJustificado" align="justify">b) Oficio 222/10 Expediente 2288/2010.Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V.</td> 
											</tr> 
										</table>
									</td> 
								</tr> 
							</table>
						</td>
					</tr>
					<tr></tr>
				</table> 
			</td> 
		</tr>
	</table>
<% }%>
	<!--</s:form >-->
	<!--</div>-->

	<table width="780px;"> 
		<tr> 
			<td class="tituloHeader1" height="25">2. Datos de la Inscipci&oacute;n</td>
		</tr>
		<tr> 
			<td class=textoGeneralRojo style="font-size: 11px; padding-left: 7px;" >Vigencia : <span class="texto_general"><s:property value="%{detalleTO.vigencia}"/> Meses</span> </td> 
		</tr>
		<tr> 
			<td class="tituloHeader1" height="25">3. Partes de la Garant&iacute;a Mobiliaria </td> 
		</tr>
		<tr> 
			<td class="tituloHeader1" height="25" style="padding-left: 7px;">Otorgante de la Garant&iacute;a Mobiliaria <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=modifOtorgante&keepThis=true&TB_iframe=true&height=300&width=500" title="" class="thickbox"><img alt=" <s:text name="Persona Autoridad" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td> 
		</tr>
		<tr align="left">
			<td id="notaMoral" align="left">
				<table class="nota">
					<tr>
						<td class="imgNota">
							<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
							<s:text name="common.nota" />
						</td>
						<td class="contenidoNota textoJustificado"><s:text name="El Otorgante persona moral deberá estar previamente inscrito en el Registro Público de Comercio, incluyendo las sociedades extranjeras." /> </td>
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<td id="notaFisica" style="visibility: hidden; display: none;">
				<table class="nota">
					<tr>
						<td class="imgNota">
							<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
							<s:text name="common.nota" />
						</td>
						<td class="contenidoNota"><s:text name="Si  el Otorgante no cuenta con folio electrónico, el Sistema lo matriculará  de oficio para efectos del RUG" /> </td>
					</tr>
					
				</table>	
			</td>
		</tr>
		<tr>	
			<td id ="divParteDWRxx" style="padding-left: 7px;"></td>
		</tr>
		<tr> 
			<td class="tituloHeader1" height="25">4. Deudor (es) <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=modifDeudor&keepThis=true&TB_iframe=true&height=135&width=500" title="" class="thickbox"><img alt=" <s:text name="Persona Autoridad" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td> 
		</tr>	
		<tr> 
			<td style="padding-left: 7px;"> 
				<table id="mytabledaD" class="mytabledaD" border="0" width="79%" cellspacing="1" cellpadding="1" align="left">
					<thead>
						<tr>
							<td class="encabezadoTablaResumen" 	style="text-align: center">Nombre, Raz&oacute;n o Denominaci&oacute;n Social</td>
							<td class="encabezadoTablaResumen"	style="text-align: center">Tipo de Persona</td>					
						</tr>
					</thead>
					<tbody>
					<s:iterator value="deudorTOs">
					<tr>
						<td class="cuerpo1TablaResumen" style="text-align: center"><s:property value="nombre"/></td>
						<td class="cuerpo1TablaResumen" style="text-align: center"><s:property value="perjuridica"/></td>		
					</tr>		
					</s:iterator>
					</tbody>
				</table>
			</td> 
		</tr>
		<tr> 
			<td class="tituloHeader1" height="25">5. Acreedores Adicionales <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=transmisionAcreedoresAdicionales&keepThis=true&TB_iframe=true&height=190&width=500" title="" class="thickbox"><img alt=" <s:text name="Persona Autoridad" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
		</tr>
		<tr> 
			<td> 
				<table>
					<tr>	
						<td id ="divParteDWRxx3"></td>
					</tr>
				</table>
			</td> 
		</tr>
		<tr> 
			<td class="tituloHeader1" height="25">6. Acto o Contrato que crea la Garantía Mobiliaria</td>
		</tr>
		
		<tr> 
			<td class="textoGeneralRojo" style="padding-left: 7px;"><s:textfield name="DetalleTO.tipogarantia" id="DetalleTO.tipogarantia" size="50" value="Tipo de Garantía Mobiliaria que se inscribe:" cssStyle="border:0; " cssClass="textoGeneralRojo" readonly="true"/></td>
		</tr>
		<tr> 
			<td style="padding-left: 30px;">	
	 		<s:select name="modtipogarantia" list="tipoGarantiaTOs" headerKey="%{modtipogarantia}" id="modtipogarantia"
	        listValue="descripcion" listKey="idTipoGarantia" value="%{modtipogarantia}" 
            headerValue="Seleccione un tipo de contrato" disabled="true"
            label="Tipo de Garantía Mobiliaria" labelposition="top" onchange="javascript:escondePartes();" />
			</td>
		</tr>
		
		<tr> 
			<td style="padding-left: 7px;">
				<span class="tituloInteriorRojo">
					<s:textfield id="fecha1" name="fecha1"  labelposition="top" cssStyle="visibility:visible; display:block; border:0; " cssClass="textoGeneralRojo" readonly="true" value="Fecha de celebración del Acto o Contrato"  size="90"/>
					<s:textfield id="fecha2" name="fecha2"  labelposition="top" cssStyle="display:none; border:0; visibility:hidden;" cssClass="textoGeneralRojo" readonly="true" value="Fecha de surgimiento del Derecho de Retención" size="90"/>	
					<s:textfield id="fecha3" name="fecha3"  labelposition="top" cssStyle="display:none; border:0; visibility:hidden;" cssClass="textoGeneralRojo" readonly="true" value="Fecha de surgimiento del Privilegio Especial" size="90"/>
				</span>
        	</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"> 
				<table>
					<tr>
						<td class="texto_azul" width="19px">*</td>
						<td>
							<dl style="width: 195px">
								<dd style="width: 195px">                      
									<s:textfield name="fechaCelebracionC" id="datepicker3" size="10" disabled="true" labelposition="top" /> <span class="hint"> <p align="right" style="height: 3px;"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </p>  <br/>Fecha indicada en el acto o contrato de Garantía Mobiliaria correspondiente.<span class="hint-pointer">&nbsp;</span></span>
	        					</dd>
							</dl>						
						</td>
					</tr>				
				</table>					
	        </td>
		</tr>
		
		<tr>
			<td style="padding-left: 7px;"><s:textfield id="titmonto" name="titmonto"  labelposition="top" cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true" value="Monto Máximo Garantizado" size="70"/>	</td>
		</tr>
		<tr>
			<td style="padding-left: 28px;">
				<table>
					<tr>
						<td>
							<dl style="width: 195px">
								<dd style="width: 195px">
				 					<s:textfield name="modlimite" id="modlimite" size="10" labelposition="top" value="%{modlimite}" disabled="true" />
		 							<span class="hint"> <p align="right" style="height: 3px;"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </p>  <br/> 	Importe o valor tope con que una Garantía Mobiliaria garantiza la obligación principal.<span class="hint-pointer">&nbsp;</span></span>
	        					</dd>
							</dl>						
						</td>
					</tr>				
				</table>		 		
		 	</td>
		</tr>
		
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield id="tittipomoneda" name="tittipomoneda"  labelposition="top" cssStyle="border:0; " cssClass="textoGeneralRojo" readonly="true" value="Tipo de moneda:" size="70"/>	
			</td>
		</tr>
		<tr>
			<td style="padding-left: 30px;">
				 <s:select list="listaMonedas" listKey="idMoneda" listValue="descMoneda" name="idTipoMoneda" labelposition="top" headerKey="%{moneda}" value="%{moneda}" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;">	
				<s:textfield labelposition="top" size="70" cssClass="titulo_exterior_blanco" value=" " cssStyle="border:0;background:#DEDEDE;"/>	
	      	</td>
		</tr>
		<tr>
			<td class="textoGeneralRojo" style="padding-left: 7px;">Bienes Muebles objeto de la Garant&iacute;a Mobiliaria: </td>
		</tr>
		<tr>
			<td style="padding-left: 28px;">
				<table id="mytabledaO" class="mytabledaO" border="0" width="100%"	cellspacing="1" cellpadding="1" align="left">
					<tbody>
						<s:iterator value="bienes">
							<tr>
								<td><span class="contenidoNota"><s:property	value="descripcion" /></span></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</td>
		</tr>	
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield id="titdescrip" name="titdescrip"  labelposition="top" cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true" value="Descripción de los Bienes Muebles objeto de la Garantía Mobiliaria:" size="90"/>	
			</td>
		</tr>
		<tr>
			<td style="padding-left: 30px;">
				<s:textarea name="moddescripcion" cols="70" rows="10" id="tiposbienes" value="%{moddescripcion}" labelposition="top" disabled="true"/>
	     	</td>
		</tr>
		
		<tr>
			<td style="padding-left: 7px;">	
				 <s:if test="aBoolean">
				 	<s:textfield name="DetalleTO.cambio" id="DetalleTO.cambio" value="El acto o contrato prevé incrementos, reducciones o sustituciones  de los bienes muebles o del monto garantizado" cssStyle="border:0;" labelposition="top" size="125" cssClass="textoGeneralRojo" readonly="true" />
				 </s:if>
		  	</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield id="titins" name="titins"  labelposition="top" cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true" value="Datos del Instrumento Público mediante el cuál se formalizó el Acto o Contrato:" size="90"/>	
			</td>
		</tr>
		<tr>
			<td style="padding-left: 30px;">
		 		<s:textarea labelposition="top"  value="%{DetalleTO.instrumento}" cols="70" rows="10" disabled="true"/>
			</td>
		</tr>
		
		
		<tr>
			<td style="padding-left: 7px;"><s:textfield id="terminos1" name="terminos1"  labelposition="top" cssStyle="visibility:visible; display:block; border:0; " cssClass="textoGeneralRojo" readonly="true" value="Términos y Condiciones del Acto o Contrato:"  size="90"/></td>
		</tr>
		<tr>
			<td>
				<table> 
					<tr> 
						<td class="texto_azul" width="19px" style="display:none; border:0; visibility:hidden;" readonly="true">*</td> 
						<td> <s:textfield id="terminos2" name="terminos2"  labelposition="top" cssStyle="display:none; border:0; visibility:hidden;" cssClass="textoGeneralRojo" readonly="true" value="Fundamento Legal del cual surge el Derecho de Retención:" size="90"/> </td>
					</tr>				
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table> 
					<tr> 
						<td class="texto_azul" width="19px" style="display:none; border:0; visibility:hidden;" readonly="true">*</td> 
						<td> <s:textfield id="terminos3" name="terminos3"  labelposition="top" cssStyle="display:none; border:0; visibility:hidden;" cssClass="textoGeneralRojo" readonly="true" value="Fundamento Legal del cual surge el Privilegio Especial:" size="90"/> </td> 
					</tr> 
				</table>
			</td>
		</tr>
		<tr > <!-- Esta es la que viene apareciendo por default -->
			<td style="padding-left: 30px;"><s:textarea name="modotrosgarantia" cols="70" rows="10" id="modotrosgarantia" value="%{modotrosgarantia}" labelposition="top" disabled="true"/></td>
		</tr>
		<tr>
			<td><s:textfield labelposition="top" size="70" cssClass="titulo_exterior_blanco" value=" " cssStyle="border:0;background:#DEDEDE;"/></td>
		</tr>
			
		<!-- <tr><td style="padding-left: 7px;" height="35px;" class="tituloHeader1"> <s:textfield  cssClass="tituloHeader1" id="titulo"  size="70" value="7. Acto o Contrato que crea la Obligación Garantizada:" labelposition="top" cssStyle="border:0;background:#AD4110;" readonly="true" /></td>  </tr>-->
		<tr> 
			<td style="padding-left: 7px;" height="35px;" class="tituloHeader1"> 7. Acto o Contrato que crea la Obligación Garantizada</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"> <s:textfield id="tittipoacto" name="tittipoacto" size="55" value="Acto o Contrato que crea la Obligación Garantizada:" labelposition="top" cssStyle="visibility:visible; display:block; border:0;" cssClass="textoGeneralRojo" readonly="true"/> </td>
		</tr>
		<tr>
			<td style="padding-left: 7px;" >
				<table> 
					<tr> 
						<td class="texto_azul" width="19px" >*</td> 
						<td><s:textarea id="tipoContOb" name="tipoContOb" cols="70" rows="10" value="%{DetalleTO.tipocontrato}" labelposition="top" cssStyle="visibility:visible; display:block; " maxlength="3900" onkeyup="return ismaxlength(this)" disabled="true"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"><s:textfield id="titdate2" name="titdate2" size="50" value="Fecha de celebración del Acto o Contrato: " labelposition="top" cssStyle="visibility:visible; display:block; border:0;" cssClass="textoGeneralRojo" readonly="true"/></td>
		</tr>
		<tr>
			<td style="padding-left: 30px;"><s:textfield id="datepicker4" name="modfechacelebcontrato" size="10" labelposition="top" cssStyle="visibility:visible; display:block; " disabled="true"/></td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"><s:textfield id="titdate3" name="titdate3" size="50" value="Fecha de terminación del Acto o Contrato:" labelposition="top" cssStyle="visibility:visible; display:block; border:0;" cssClass="textoGeneralRojo" readonly="true"/></td>
		</tr>
		<tr>
			<td style="padding-left: 30px;"><s:textfield id="datepicker5" name="fechaFinOb" size="10" labelposition="top" cssStyle="visibility:visible; display:block; " disabled="true" /></td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"><s:textfield id="titterminos" name="titterminos" size="50" value="Términos y Condiciones de la Obligación Garantizada" labelposition="top" cssStyle="visibility:visible; display:block; border:0;" cssClass="textoGeneralRojo" readonly="true"/></td>
		</tr>
		<tr>
			<td style="padding-left: 30px;"><s:textarea id="farectificacion_modotrosterminos" name="otrosTermOb" cols="70" rows="10" labelposition="top" cssStyle="visibility:visible; display:block; " maxlength="3900" onkeyup="return ismaxlength(this)" disabled="true" value="%{DetalleTO.otrosterminos}"/></td>
		</tr>
		<tr> 
			<td style="padding-left: 7px;" height="35px;" class="tituloHeader1"> 8. Acto o Convenio que da origen a la Transmisión</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;"><s:textfield labelposition="top" size="70" cssClass="textoGeneralRojo" value="Acto o Convenio que da origen a la Transmisión" cssStyle="border:0;" readonly="true" /> </td> 
		</tr>
		<tr> 
			<td style="padding-left: 7px;"> 
				<table> 
					<tr> 
						<td class="texto_azul" width="19px">*</td>
						<td>
							<table>
								<tr>
									<td>
										<dl style="width: 490px">
											<dd style="width: 490px">							
												<s:textfield id="modtipoacto" name="modtipoacto" size="72"  value="%{modtipoacto}" maxlength="3900" />
												<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Ingrese los datos del Acto o Convenio por virtud del cual realizará la Transmisión.</div><span class="hint-pointer">&nbsp;</span></span>	
											</dd>
										</dl>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td> 
		</tr>
		
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield labelposition="top" size="70" cssClass="textoGeneralRojo" value="Fecha de celebración del Acto o Convenio" cssStyle="border:0;" readonly="true" />
			</td>
		</tr>
		<tr>
			<td style="padding-left: 7px;">
				<table> 
					<tr> 
						<td class="texto_azul" width="19px">*</td>
						<td>	
							<table>
								<tr>
									<td>
										<dl style="width: 50px">
											<dd style="width: 50px">			
												<s:textfield id="datepicker1" name="modfechaceleb"  size="10" labelposition="top" maxlength="10" onchange="fechasCorrectas();" />
												<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Fecha indicada en el acto o convenio que da origen a la Transmisión.</div><span class="hint-pointer">&nbsp;</span></span>	
											</dd>
										</dl>
									</td>
								</tr>
							</table>
						</td> 
					</tr>
				</table> 	
			</td>
		</tr>
		
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield labelposition="top" size="70" cssClass="textoGeneralRojo" value="Fecha de terminación del Acto o Convenio" cssStyle="border:0;" readonly="true" />
			</td>
		</tr>
		<tr>
			<td style="padding-left: 32px;">
				<s:textfield id="datepicker2" name="modfechatermino"  size="10" labelposition="top" maxlength="10" onchange="fechasCorrectas();" />
			</td>
		</tr>
		
		<tr>
			<td style="padding-left: 7px;">
				<s:textfield labelposition="top" size="70" cssClass="textoGeneralRojo" value="Términos y Condiciones del Acto o Convenio" cssStyle="border:0;" readonly="true" />
			</td>
		</tr>
		<tr>
			<td style="padding-left: 30px;">   
				<table>
					<tr>
						<td>
							<dl style="width: 420px">
								<dd style="width: 420px">&nbsp;
									<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Se recomienda utilizar este campo para incluir cualesquiera circunstancias de hecho o de derecho que considere relevantes del Convenio que da origen a la Transmisión.</div><span class="hint-pointer">&nbsp;</span></span>
									<s:textarea id="modterminos" name="modterminos" cols="70" rows="10" value="%{modterminos}" labelposition="top" maxlength="3900" onkeyup="return ismaxlength(this)" />
								</dd>
							</dl>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr> 
		<td> 
			<table>
				<tr> 
					<td style="padding-left:30px;" class="textoEjemplo">Ejemplo&nbsp;</td>
					<td>
						<table>
							<tr>
								<td width="414px;" class="textoGris textoJustificado"  align="justify">a) CLAUSULA VIGÉSIMA CUARTA. Cesión. El BANCO podrá en cualquier momento durante la vigencia de este Contrato, ceder o transmitir sus derechos y obligaciones bajo el presente Contrato y sus anexos, sin necesidad de previa autorización del ACREDITADO.</td> </tr><tr><td class="textoGris textoJustificado" align="justify">b) Conforme a la cláusula SEXTA del contrato, el otorgante deberá mantener los bienes muebles objeto de la Garantía Mobiliaria en la siguiente ubicación: Av. Hidalgo No. 321, Interior 123, Colonia Morelos, C.P. 0149870, Delegación Álvaro Obregón; México, D.F.</td> 
							</tr> 
						</table>
					</td> 
				</tr> 
			</table>
		</td>
	</tr>
	</table>
	
	<table width="745">
		<tr>
			<td align="center"><input type="button" id="bFirmar" name="button" class="boton_rug" value="Firmar" onClick="transmisionValida();"/></td>
		</tr>
	</table>
	<!-- Comentado pk no se que hace o mas bien creo q no hace nada	<div class="tituloInteriorRojo" style="heigth:100px;width:740px;" labelposition="top">&nbsp;<a onclick="habilita('AcreedorDIV');" style="cursor: pointer;">Acreedores adicionales</a>	</div><table><tr>	<td id ="divParteDWRxx4"></td></tr></table> -->
	<!--  <tr><td><s:textfield labelposition="top" size="90" cssClass="titulo_exterior_blanco" value="Acto o Convenio que da origen a la Transmisión" cssStyle="border:0;background:#AD4110;" readonly="true" /></td></tr> -->
</s:form>
	<script type="text/javascript"> 
		setTimeout("$(function() {$('#datepicker1').datepicker({changeMonth: true,changeYear: true});});",10);
		setTimeout("$(function() {$('#datepicker2').datepicker({changeMonth: true,changeYear: true});});",10);
		setTimeout("$(function() {$('#datepicker3').datepicker({changeMonth: true,changeYear: true});});",10);
	
		var idTramite= <s:property value="idTramiteTemporal"/>;
		var idPersona = <s:property value="idpersona"/>;
		
		cargaParteOtorgante('divParteDWRxx',idTramite, idPersona,'0','1');
	
		cargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','0');
		cargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0');
	
		escondePartes();
	
		function fechasCorrectas(){
			var strFI = getObject('datepicker1').value;
			var strFF = getObject('datepicker2').value;
			if ( getObject('datepicker1').value != ''){
				if (!esMenorHoy(strFI)){
					getObject('datepicker1').value = '';
					alert("La Fecha de Celebración del Acto o Convenio debe ser menor o igual a la actual");
					return false;
				}else{
					if ( getObject('datepicker2').value != ''){
						if (!validaFechas(strFI, strFF)){
							getObject('datepicker2').value = '';
							alert("La Fecha de Terminación del Acto o Convenio debe ser mayor o igual a la Fecha de Celebración del Acto o Convenio");
							return false;
						}					
					}
				}
			}
		}
			
	</script>
</div>
<input type="hidden" name="nombreAcreedor" id="nombreAcreedor" value="<s:property value="nomAcreedor"/>" />
