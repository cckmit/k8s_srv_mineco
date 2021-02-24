<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/FuncionesDeFechas.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/RugUtil.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>

<script type="text/javascript">
	
var statSend = false;
//$("#cuatroMenu").attr("class","linkSelected");

function aceptaalfa(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)
                      && (charCode < 65 || charCode > 90)
                      && (charCode < 97 || charCode > 122)
                      && (charCode <209  || charCode > 249)
        )
        return false;
    return true;
}

function checkSubmit() {

    if (!statSend) {
    	document.getElementById("baceptar").value = "Enviando";
    	document.getElementById("baceptar").disabled = true;
        statSend = true;
        return true;

    } else {
		
        return false;

    }

}

function activaBtn1_d_paso1(){
	document.getElementById("buttonID").value = "Continuar";
	document.getElementById("buttonID").disabled = false;
}

function paso2_d_paso1() {
	if(document.getElementById('tableDeudores')==null){
		alertMaterialize('No se puede continuar sin un Deudor');
		return false;
	}
	if(document.getElementById('tableAcreedores')==null){
		alertMaterialize('No se puede continuar sin un Acreedor');
		return false;
	}
	
	//if (document.getElementById('sepuedecontinuar')!=null){
		//if (!statSend) {
	    	document.getElementById("buttonID").value = "Enviando";
	    	document.getElementById("buttonID").disabled = true;
	        statSend = true;
	        window.location.href = "<%=request.getContextPath()%>/inscripcion/paso2.do";
				return true;

			//} else {

				//return false;

			//}
		/*} else {
			alert('No se puede continuar sin un Otorgante');
		}*/

}

function fechasCorrectas(){
	 //var strFI = getObject('datepicker4').value;
	 //var strFF = getObject('datepicker5').value;
	}

function fechaCelebCorrecta(){ 

}

function actualizaCopia(){
	 //if(getObject('cpContrato').checked){
	  
	  //getObject('formS2ag_obligacionTO_tipoActoContrato').value=getObject('idTipoGarantia').options[getObject('idTipoGarantia').selectedIndex].text;
	  //getObject('datepicker4').value=getObject('datepicker1').value;
	  //getObject('formS2ag_obligacionTO_otrosTerminos').value=getObject('formS2ag_actoContratoTO_otrosTerminos').value;
	  
	  //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = true;
	  //getObject('datepicker4').readOnly = true;
	  //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = true;
	 //}
}

function textCounter(field, countfield, maxlimit) {
	 if (field.value.length > maxlimit) // if too long...trim it!
	 field.value = field.value.substring(0, maxlimit);
	 // otherwise, update 'characters left' counter
	 else
	 countfield.value = maxlimit - field.value.length;
}

var todos = new Array();

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


function buscaPunto(texto){
	 for(i=0;i<texto.length;i++){
	  if(texto.charAt(i)==".") return true;
	 }
	 return false;
}
	
function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	var cadena = document.getElementById('idMontoMaximo').value;
	var onlyPunto = buscaPunto(cadena);    
	if (onlyPunto){     
	 return (key <= 13 || (key >= 48 && key <= 57) || key==118 );
	}else{
	 return ( key <= 13 || (key >= 48 && key <= 57) || key==46 || key==118);      

	IsNumberValidate('idMontoMaximo');
	}
}

function noVacio(valor){
	   for ( i = 0; i < valor.length; i++ ) {  
	     if ( valor.charAt(i) != " " ) {
	      return true; 
	    }  
	   }  
	 return false; 
}
	 
function activaBtn2(){
	  document.getElementById("baceptar").value = "Continuar";
      document.getElementById("baceptar").disabled = false;
}

  function paso1() {
	 
      document.getElementById("buttonID").value = "Enviando";
      document.getElementById("buttonID").disabled = true;
      var idIns = document.getElementById("refInscripcion").value;
      statSend = true;
      window.location.href = '<%= request.getContextPath() %>/inscripcion/paso1.do?idInscripcion=' + idIns;
      return true;
	   
}
	    
function validarSelectMultiple(){
	  if (document.getElementById('formS2ag_actoContratoTO_tipoBienes').selectedIndex!=-1){
	   return true;
	  }else{
	   
	   return false;
	  }
	  
}

function sendForm(){
	  document.getElementById("baceptar").value = "Enviando";
	     document.getElementById("baceptar").disabled = true;
	  document.formS2ag.submit();
 }
	 
 function seguroContinuar(){
	  	  
	  var bienes = getObject('descripcionId').value;
	  var instrumento = getObject('actoContratoTO.instrumentoPub').value;
	  var observaciones = getObject('actoContratoTO.otrosTerminos').value;
	  	  
	  if (!noVacio(bienes)){
		alertMaterialize('El campo Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria es obligatorio');
	   	return false;
	  }
	   
	  if (!noVacio(instrumento)){
		alertMaterialize('El campo Datos Generales del contrato de la garantia es obligatorio');
	    return false;
	  }
	  
	  if (!noVacio(observaciones)){
		alertMaterialize('El campo Datos Adicionales de la garantía es obligatorio');
	    return false;
	  }
	   
      document.getElementById("baceptar").value = "Enviando";
      document.getElementById("baceptar").disabled = true;
      document.formS2ag.submit();    

}
 
 function activaBtn1(){
	  document.getElementById("buttonID").value = "Regresar";
	  document.getElementById("buttonID").disabled = false;
}
	 
 function copiaContrato(){
	  if (!noVacio(getObject('datepicker1').value)){
	   getObject('cpContrato').checked=0;
	   alert('El campo Fecha de celebración del Acto o Contrato es obligatorio');
	   return false;
	  }
	  if(getObject('cpContrato').checked){
	  
	   //getObject('formS2ag_obligacionTO_tipoActoContrato').value=getObject('idTipoGarantia').options[getObject('idTipoGarantia').selectedIndex].text;
	   getObject('datepicker4').value=getObject('datepicker1').value;
	   //getObject('formS2ag_obligacionTO_otrosTerminos').value=getObject('formS2ag_actoContratoTO_otrosTerminos').value;
	   
	   //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = true;
	   getObject('datepicker4').readOnly = true;
	   //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = true;
	  }else{
	   //getObject('formS2ag_obligacionTO_tipoActoContrato').value="";
	   getObject('datepicker4').value="";
	   //getObject('formS2ag_obligacionTO_otrosTerminos').value="";
	   
	   //getObject('formS2ag_obligacionTO_tipoActoContrato').readOnly = false;
	   getObject('datepicker4').readOnly = false;
	   //getObject('formS2ag_obligacionTO_otrosTerminos').readOnly = false;
	  }
}
	 
	 
	 
	 
function escondePartes(){
		 
	  //var valor = document.getElementById('idTipoGarantia').value;
	  var valor = 1;
	  //var validador=dwr.util.getValue(mensaje.codeError);
	  //var validador=dwr.util.getValue(mensaje);
		
	  switch(valor){
	  case "8":
		  //alert('Validar Folios '+ <s:property value="idInscripcion"/>);
		  
		  //ParteDwrAction.verificarFolios(<s:property value="idInscripcion"/>,resultadoVerificacion);
		    
		    break;
	  
	  
	   case "10":
	    
	    document.getElementById('obligacionDIV').style.visibility = 'hidden';
	    document.getElementById('obligacionDIV').style.display = 'none';     
	    document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
	    document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Derecho de Retención';
	    break;
	    
	   case "12":
	    //document.getElementById('obligacionDIV').style.visibility = 'hidden';
	    //document.getElementById('obligacionDIV').style.display = 'none';     
	    document.getElementById('obligacionDIV').style.visibility = 'visible';
	    document.getElementById('obligacionDIV').style.display = 'block';
	    document.getElementById('fechaCeleb').innerHTML = '<spaon class="textoGeneralRojo"> * Fecha de surgimiento del Privilegio Especial :</span>';
	    document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Privilegio Especial';
	    break;
	   default:
	    //document.getElementById('obligacionDIV').style.visibility = 'visible';
	    //document.getElementById('obligacionDIV').style.display = 'block';
	    //document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> Fecha de celebración del acto o contrato :</span>';
	    //document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';
	    break;
	  }
}
	 
function continuar(id){
		 switch(id){
		  	case 1:
		  		//alert("redireccion a AR");
		  		window.location.href = '<%= request.getContextPath() %>/acreedor/inicia.do?idInscripcion=<s:property value="idInscripcion"/>';
			  break;
			case 2:
		  		//alert("redireccion a AD");
		  		window.location.href = '<%= request.getContextPath() %>/inscripcion/paso1.do?idInscripcion=<s:property value="idInscripcion"/>';
			  break;
			case 3:
		  		//alert("redireccion a ARyAD");
		  		window.location.href = '<%= request.getContextPath() %>/acreedor/inicia.do?idInscripcion=<s:property value="idInscripcion"/>';
			  break;
		 }
}
	  
 function cancelar(){
	 //alert("Setear");
	 displayMessageFolioElectronicoAcreedores(false);
	 //me falta setear el combo tipo garantia para que regrese a seleccione
	 document.getElementById('idTipoGarantia').selectedIndex="0";
	 
 }
	 
 function resultadoVerificacion(mensaje){
	  //alert('resultado '+mensaje.codeError);
	  //alert('Validar Folio'+ <s:property value="idInscripcion"/>);
	  
	 switch(mensaje.codeError){
	  	case 0:
	  		
	  		//alert("Acreedores tienen folio Electrónico "+<s:property value="idInscripcion"/>);
	  		displayLoader(false);
		  break;
	  	
	  	case 1:
	  		displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor Representado para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		//alert('Validar Folio Acreedor  Ir a alta de acreedores'+ <s:property value="idInscripcion"/>);
		  break;
		  
		  displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor Adicional para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		//alert('Validar Folios Acreedor Adicional Ir a Paso 1'+ <s:property value="idInscripcion"/>);
	  	break;
	  	case 3:
	  		displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','Los Acreedores para esta garantía no cuentan con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		//alert('Ninguno tiene folio Ir a alta de acreedores'+ <s:property value="idInscripcion"/>);
		  break;

	  	}
 }

	 	 
function IsNumberValidate(id) {
	var cadena = document.getElementById(id).value;
	
	var flagCharBad=false;
	   
	for(i=0; i<cadena.length;i++)  {
		var charValue= cadena.charAt(i)
		var key = ascii_value(charValue);
		if (!(key <= 13 || (key >= 48 && key <= 57) || key==46 ) ){
			flagCharBad=true;
		}    	 
	}	
	
	if(flagCharBad){
		alert('Por favor ingrese un valor valido para el campo');
		document.getElementById(id).value='';
	}
	
}

function ascii_value (c)
{
	// restrict input to a single character
	c = c . charAt (0);

	// loop through all possible ASCII values
	var i;
	for (i = 0; i < 256; ++ i)
	{
		// convert i into a 2-digit hex string
		var h = i . toString (16);
		if (h . length == 1)
			h = "0" + h;

		// insert a % character into the string
		h = "%" + h;

		// determine the character represented by the escape code
		h = unescape (h);

		// if the characters match, we've found the ASCII value
		if (h == c)
			break;
	}
	return i;
}
	 
	 
function selecciona(frm) {
 	for (i = 0; ele = frm.formS2ag_actoContratoTO_tipoBienes.options[i]; i++){
  
  	  ele.selected = true;
 	}
 	getObject('idTipoBienAll').value="true";
} 
	 	
function desSelecciona(frm) {
  for (i = 0; ele = frm.formS2ag_actoContratoTO_tipoBienes.options[i]; i++){
   	  ele.selected = false;
  }
  getObject('idTipoBienAll').value="false";
} 

function loadMaterialize(){
	
}

function msg_guardar() {
	alertMaterialize('El sistema le guardara la garantía temporalmente por 72 horas, esto no constituye una inscripci&oacute;n y por lo tanto no otorga prelacion.');
	return false;
}

</script>

