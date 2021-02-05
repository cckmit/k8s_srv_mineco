<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
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
	diaModificado = parseInt(getObject('dia').value,10);
	mesModificado = parseInt(getObject('mes').value,10);
//	document.getElementById('meses').focus();
	menos = false;	
}

function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('prorrogaGuarda').submit();
}

function validaVigencia(){	

	var vigAct = '<s:property value="vigencia"/>';
	var vigNew = document.getElementById("slider1").value;
	
	var meses = parseInt(vigNew) - parseInt(vigAct);
		
	document.getElementById("meses").value = meses;
		
	// obtener el costo de una renovacion o reduccion: tipo_tramite=9
	$.ajax({
		url: '<%= request.getContextPath() %>/rs/tipos-tramite/9',
		success: function(result) {
			MaterialDialog.dialog(
				"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100).toFixed(2) + ", ¿está seguro que desea continuar?",
				{				
					title:'<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
					buttons:{
						// Use by default close and confirm buttons
						close:{
							className:"red",
							text:"cancelar"						
						},
						confirm:{
							className:"indigo",
							text:"aceptar",
							modalClose:false,
							callback:function(){
								sendForm();
							}
						}
					}
				}
			);
		}
	});
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
   
   function actualizarFecha() {
	   var mesesSum = parseInt (getObject('meses').value,10);
	   
	   if(mesesSum >3) {
		   alertMaterialize('No puede exceder los 3 años');
		   return false;
	   }
	   if(mesesSum <=0) {
		   alertMaterialize('No puede ser menor o igual a 0 años');
		   return false;
	   }
	   var dia = parseInt (getObject('dia').value,10);
	   var mes = parseInt (getObject('mes').value,10);
	   var anio = parseInt (getObject('anio').value,10);
	   
	   var myDate = new Date();
	   myDate.setFullYear(anio+mesesSum,mes,dia);
	   document.getElementById('fechaFinal').innerHTML =  armaFecha(myDate.getDate(), myDate.getMonth(), myDate.getFullYear());
   }

</script>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>
<main>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>
<div class="container-fluid">
	<div class="row">
			<div id="menuh">
				<ul>
					<%
					UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
					MenuTO menuTO= new MenuTO(1,request.getContextPath());	
					MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
					
					Boolean noHayCancel= (Boolean) request.getAttribute("noHayCancel");
					Boolean noVigencia = (Boolean) request.getAttribute("vigenciaValida");
					if(noHayCancel==null ||(noHayCancel!=null && noHayCancel.booleanValue()==true)){
						Integer idAcreedorRepresentado=(Integer) session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO);
						MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
						menuSecundarioTO = menuService.cargaMenuSecundario(idAcreedorRepresentado,usuarioTO,menuSecundarioTO,noVigencia);
						Iterator<String> iterator2 = menuSecundarioTO.getHtml().iterator();
						while (iterator2.hasNext()) {
							String menuItem = iterator2.next();
					%><%=menuItem%>
					<%
						}
					}
						
					%>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col s12"><div class="card">
				<div class="col s2"></div>
				<div class="col s8">
					<form id="prorrogaGuarda" name="prorrogaGuarda" action="prorrogaGuarda.do">
						<span class="card-title">Vigencia de la Inscripci&oacute;n</span>
						<div class="row note">
							<p>
								<span>La inscripci&oacute;n en el Registro tendr&aacute; vigencia por un plazo de 5 a&ntilde;os, renovable por per&iacute;odos de tres a&ntilde;os. </span>
							</p>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<span class="blue-text text-darken-2">Garant&iacute;a
									Mobiliaria No. </span> <span> <s:property value="idGarantia" />
								</span>
							</div>
						</div>						
						<div class="row">
							<div class="input-field col s12">
								<span>Su Garant&iacute;a Mobiliaria se encuentra inscrita por <span class="blue-text text-darken-4"><s:property value="vigencia"/> </span>&nbsp;								
								a&ntilde;o (s) , por lo tanto vence en la fecha <span class="blue-text text-darken-4"> <s:property value="fechaFin"/></span></span>								
							</div>
						</div>												
						<div class="row">		
							<div class="input-field col s12">
								<input type="text" id="slider1" class="slider">											
							</div>
						</div>						
						 <hr />
				 	<center>
			            <div class='row'>			            	
			            	<input type="button" id="bFirmar" name="button" class="btn btn-large waves-effect indigo" value="Aceptar" onclick="validaVigencia();"/>			            				            							            	
			            </div>
		          	</center>	
		          	<div style="visibility: hidden;">
		          		<s:textfield name="dia" id="dia" /> <s:textfield name="mes" id="mes" /> <s:textfield name="anio" id="anio" />
						<input type="hidden" id="meses" name="meses" />
		          	</div>							
					</form>
				</div>
				<div class="col s2"></div>
			</div>
		</div>
	</div>
</main>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/rSlider.min.css">
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/rSlider.min.js"></script>
<script>
	var vig = '<s:property value="vigencia"/>';
	var fechaFin = '<s:property value="fechaFin"/>';
	var yearFin = fechaFin.substring(6);
	
	var today = new Date();
	var yyyy = today.getFullYear();
	var past = parseInt(yearFin) - parseInt(yyyy);
				
	var max = parseInt(vig) + parseInt("3");
	var min = parseInt(vig) - parseInt(past) + 1;
			
	document.getElementById("slider1").value = vig;
	document.getElementById("slider1").max = max;
	
	var numbers = [min];
	for(i=min; i<max; i++){
		numbers.push(i+1);
	}
		
	var sets = [parseInt(vig)];		
	
	//alert(sets);

	var mySlider = new rSlider({
    target: '#slider1',
	values: numbers,
	set: sets,
    range: false, // range slider,	
	step: 1,
	width:  600		
});
</script>
<script type="text/javascript">
loadValues();
</script>