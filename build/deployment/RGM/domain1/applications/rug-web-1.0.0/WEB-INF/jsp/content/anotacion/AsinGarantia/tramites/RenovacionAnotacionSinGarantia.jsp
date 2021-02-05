<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript">
var anioModificado;
var diaModificado ;
var mesModificado;
var menos;

function IsNumber(evt) {
	 var key = (document.all) ? evt.keyCode : evt.which;
	 if (key == 13)
	 {  
	  validaVigencia();
	 return false;
	 }
	 return (key == 45 || key <= 13 || (key >= 48 && key <= 57));
	}

function loadValues(){
	anioModificado= parseInt(getObject('anio').value,10);
	diaModificado = parseInt(getObject('dia').value),10;
	mesModificado = parseInt(getObject('mes').value,10);
//	document.getElementById('meses').focus();
	menos = false;
}
$(function () {
	$('.ayuda').ayuda({
		url: '<%= request.getContextPath() %>/comun/publico/help.do',
		ico: '<%= request.getContextPath() %>/resources/imgs/documentinfo.png',
		width: 500,
		height: 400
	})
})
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('renovacionsnID').submit();
}

function validaVigencia(){
	var val=0;
	var mesesSum =parseInt (getObject('meses').value,10);
	if(mesesSum<=9999){
	if(document.getElementById('hayAutoridad').value =="si"){
		if(noVacio(document.getElementById('autoridad').value)){
			val=val+1;
		}
		else{
			changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
			changeStyle("messageAlertDiv","HEIGHT:260px; WIDTH:450px")
			displayAlert(true,"Información Incompleta","El campo de Persona que solicita o Autoridad que instruye la Renovación o Reducción de la Vigencia y Resolución Judicial Administrativa en la cual se funda la anotación es obligatorio");
			return false;
		}
	}else{
		
			val=val+1;
	}
	if (parseInt(getObject('meses').value,10) == 0 || getObject('meses').value ==""){
		displayAlert(true, 'Vigencia', 'Su vigencia no ha sido modificada');
		return false;
	}
	else {
		val = val + 1;
		}
	if (val==2){
		sendForm();
	}
	}else{
		displayAlert(true, 'Vigencia', 'Su vigencia solo puede contener 4 digitos');
		}
}

function getMeses(){
	var dia = parseInt (getObject('dia').value,10);
	var mes = parseInt (getObject('mes').value,10);
	var anio = parseInt (getObject('anio').value,10);
	var fechaActual = new Date();
	var diaActual= parseInt(fechaActual.getDate(),10);
	var mesActual= parseInt(fechaActual.getMonth(),10) + 1;
	var anioActual= parseInt (fechaActual.getFullYear(),10);
	numMeses = (((anio*12) + mes) - ((anioActual*12) + mesActual));
    if (dia <= diaActual){
	    numMeses = numMeses - 1;
	  }
	 return numMeses;
}

function aumentar(){
	var mes = parseInt(getObject('meses').value,10);
	mes= mes + 1;
	getObject('meses').value = mes;
	getObject('vigenciaM').value = parseInt (getObject('vigencia').value,10) + mes;
	if (mes>=0){
		aumentaMes();
		}
	else{
		disminuirMes();
		}
	document.getElementById('meses').focus();
}

function disminuir(){
	var mes = parseInt (getObject('meses').value,10);
	if ( (mes*(-1)) <= getMeses()){
		mes= mes - 1;
		getObject('meses').value = mes;
		getObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mes );
		if (mes>=0){
			aumentaMes();
			}
		else{	
			disminuirMes();
		}
	}
	document.getElementById('meses').focus();
}


function pasarValor(){
	var dia = parseInt (getObject('dia').value,10);
	var mes = parseInt (getObject('mes').value,10);
	var anio = parseInt (getObject('anio').value,10);
	var mesesSum =parseInt (getObject('meses').value,10);
if(mesesSum<=9999){
	if (mesesSum >=0){
			getObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mesesSum);
			aumentaMes();
			}
		else{
			if ( (mesesSum*(-1)) <= getMeses()){
				getObject('vigenciaM').value = (parseInt (getObject('vigencia').value,10) + mesesSum);
				disminuirMes();
			}
			else{
				displayAlert(true,'Vigencia','Debe introducir una vigencia que sea mayor a la fecha actual');
				getObject('meses').value =0;
				getObject('vigenciaM').value = getObject('vigencia').value;
				getObject('fechaFinM').value = armaFecha(dia, mes, anio); 
				}
		}
}else{
	displayAlert(true, 'Vigencia', 'Su vigencia solo puede contener 4 digitos');
}
}

function cerosIzq(sVal, nPos){
    var sRes = sVal;
    for (var i = sVal.length; i < nPos; i++)
     sRes = "0" + sRes;
    return sRes;
   }
 
   function armaFecha(nDia, nMes, nAno){
    var sRes = cerosIzq(String(nDia), 2);
    sRes = sRes + "/" + cerosIzq(String(nMes), 2);
    sRes = sRes + "/" + cerosIzq(String(nAno), 4);
    return sRes;
   }
   
   function esBisiesto(year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? true : false;
	}

   
   function aumentaMes(){
		var mesesSum = parseInt (getObject('meses').value,10);
		var dia = parseInt (getObject('dia').value,10);
		var mes = parseInt (getObject('mes').value,10);
		var anio = parseInt (getObject('anio').value,10);
		var myDate=new Date();
		myDate.setFullYear(anio,mes-1,dia);
	//Agregar meses
		myDate.setMonth(myDate.getMonth()+mesesSum);
		getObject('fechaFinM').value = armaFecha(myDate.getDate(), parseInt(myDate.getMonth(),10)+1, myDate.getFullYear());
		anioModificado=anio;	
	}
   
   function disminuirMes(){
		var mesesSum = parseInt (getObject('meses').value,10);
		var dia = parseInt (getObject('dia').value,10);
		var mes = parseInt (getObject('mes').value,10);
		var anio = parseInt (getObject('anio').value,10);
		var myDate=new Date();
		myDate.setFullYear(anio,mes-1,dia);
	//Quitar meses
	
		myDate.setMonth(myDate.getMonth()-Math.abs(mesesSum));
	
		getObject('fechaFinM').value = armaFecha(myDate.getDate(), parseInt(myDate.getMonth(),10)+1, myDate.getFullYear());
		anioModificado=anio;	
	}   

</script>

<style type="text/css">
h4 {
	font-size: 10px;
	font-style: italic;
	color: #FF8000;
}

.Estilo3 {
	font-style: italic
}

.Estilo12 {
	font-size: 10px;
	font-style: italic;
	color: #FF8000;
}

.Estilo14 {
	font-size: 10px;
	font-style: italic;
	color: #EF7421;
}
</style>
<s:form name="renovacionsn" id="renovacionsnID" namespace="anotacion" action="saveRenovacion.do" theme="simple" >
	<div style="width: 760px; margin-top: 40px ">
	<table id="tablaPaso1Gral" cellspacing="5" cellpadding="0" width="770"> 
		<tr> 
			<td width="960px"height="25" style="padding-left: 5px;"><h1>Renovación o Reducción de la Vigencia</h1> <a class="thickbox" href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=renovacion_mov.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" target="_blank" tabindex="31" ><h2>(Video Tutorial)</h2></a></td>		
		</tr>
		<tr></tr>
		<tr>
			<td>
				<table style="padding-left: 6px; padding-right: 5px;" width="743px;">
					<tr>
						<td align="left" style="background-color: #D8D8D8; padding-left:10px; padding-top:4px; padding-bottom:4px" class="texto_bolder_gris"><b>Acreedor:  <s:property value="nomAcreedor" /> </b></td> 
					</tr> 
				</table>
			</td>
		</tr>
			
		<tr>
			<td align="left" style="padding-left: 28px;font-size: 10px; height: 28px;" class="texto_azul">* Campo Obligatorio </td> 
		</tr> 
	
		<tr> 
			<td align="center"> 
				<table class="nota">
					<tr>
						<td class="imgNota"><img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png"> </td>
						<td align="justify" class="contenidoNota textoJustificado"><s:text name="Mediante esta operación usted podrá aumentar o disminuir la vigencia de la Inscripción, por el número de meses que desee. Fundamento Legal.- Art. 32 bis 4 CCom" /></td>
					</tr>
				</table>
			</td> 
		</tr>
		
	</table>

	<table >
		
			<tr>
				<td style="padding-left: 11px;" class="tituloHeader1" height="25">1. Solicitante</td>
			</tr>
			
			<tr>
				<td style="visibility: hidden; display: none;"><input id="hayAutoridad" value="si"></td>
			</tr>
			
			<tr>
				<td style="padding-left: 4px;">
					<table>
						<tr> 
							<td width="650px;" colspan="3" class="textoGeneralRojo" align="left" style="padding-left: 7px"> Persona que solicita o Autoridad que instruye la Renovaci&oacute;n o Reducci&oacute;n de la Vigencia y Resoluci&oacute;n Judicial Administrativa en la cual se funda la anotaci&oacute;n : <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=autoridadRenovReducVig&keepThis=true&TB_iframe=true&height=300&width=500" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> " src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
						</tr>	
						<tr>
							<td> 
								<table> 
									<tr> 
										<td class="texto_azul" width="19px;" style="padding-left: 23px;">*</td> 
										<td colspan="2"><s:textarea cols="70" rows="5" name="anotacionSnGarantia.autoridadAutorizaTramite" id="autoridad" onkeyup="return ismaxlength(this)" maxlength="3000" /> </td>
									</tr> 
								</table>
							</td>
						</tr>		
						<tr> 
							<td> 
								<table>
									<tr> 
										<td style="padding-left:43px;" class="textoEjemplo">Ejemplo&nbsp;</td>
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
					</table>
				</td>
			</tr>
		
	
	</table>
	
	<table align="left" width="740">
		<tr>                                                                   
			<td class="textoGeneralRojo"  align="left" style="padding-left: 12px" height="30px">Su Garant&iacute;a Mobiliaria se encuentra inscrita por 
			<s:textfield name="anotacionSnGarantia.vigencia" id="vigencia" maxlength="4" size="4" readonly="true" cssClass="textoGeneralRojo" />
			 mes (es), por lo tanto vence en la fecha 
			 <s:textfield name="fechaFin" id="fechaFin" maxlength="10" size="10" readonly="true" cssClass="textoGeneralRojo" />
		</tr>
		
		<tr> 
			<td align="center" style="margin-bottom: 10px;"> 
				<table> 
					<tr> 
						<td align="center" width="10px"> <h4 style="cursor: pointer"><a onclick="disminuir();"> <u>Disminuir</u></a></h4> </td>
						<td>&nbsp; </td>
						<td class="textoGeneralRojo" align="center" width="10px">
						 <div id="campoMeses">
						 <input eonfocus="cambiar();" class="textoGeneralRojo" value="0" type="text" onblur="pasarValor();" name="meses" id="meses" size="2" maxLength="5" onkeypress="return IsNumber(event);" />
						 </div> </td> 
						<td>&nbsp; </td>
						<td align="center" width="10px"> <h4 style="cursor: pointer"><a onclick="aumentar();"> <u>Aumentar </u></a></h4> </td>
						
					</tr>
				</table>
			</td> 
		</tr>
		
		<tr>
			<td class="textoGeneralRojo"  align="left" style="padding-left: 12px" height="41px">
			Su Garant&iacute;a Mobiliaria se ver&aacute; afectada con un plazo de 
			<s:textfield name="anotacionSnGarantia.vigenciaNueva" id="vigenciaM" maxlength="4" size="4" readonly="true" cssClass="textoGeneralRojo" /> 
			mes (es), por lo tanto vencer&aacute; en la fecha 
			<s:textfield name="fechaFinM" id="fechaFinM" maxlength="10" size="10" readonly="true" cssClass="textoGeneralRojo" onfocus="true" />
		</tr>
		
		<tr> <td>&nbsp;</td> </tr>
		
		<tr>
			<td align="center"><input type="button" value="Firmar" class="boton_rug" onclick="validaVigencia();" id="bFirmar" /></td>
		</tr>
		
		<tr>
			<td style="visibility: hidden;"><s:textfield name="dia" id="dia" /> <s:textfield name="mes" id="mes" /> <s:textfield name="anio" id="anio" /></td>
		</tr>
	</table>
	<div style="visibility: hidden;"><s:property value="msgError"/></div>  
</s:form>
<script type="text/javascript">
loadValues();
</script>

