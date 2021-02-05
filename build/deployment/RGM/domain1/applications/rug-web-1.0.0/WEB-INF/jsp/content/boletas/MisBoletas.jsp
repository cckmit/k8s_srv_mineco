<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="idPersonaUsuario" value="idPersona" />
<s:set var="maestra" value="cuentaMaestra"/>

<div class="section"></div>
<main>
<div class="container-fluid">
	<div class="row">
		<div class="col s1"></div>
		<div class="col s10 note">En Esta secci&oacute;n podr&aacute;
			registrar las boletas de pago dentro del sistema.</div>
		<div class="col s1"></div>
	</div>
	<div class="row">
		<div class="col s1"></div>
		<div class="col s10">
			<div class="card">
				<div class="card-content">
					<div class="row">
						<div class="col s12 action-error">
							<s:actionerror />
						</div>
					</div>
					<div class="row">
						<span class="card-title">Saldo: <s:property value="getText('Q. {0,number,#,##0.00}',{mdSaldo})"/></span>						
					</div>
					<div class="row">
						<div class="col s8">
							<span class="card-title">Mis Boletas</span>
						</div>
						<div class="col s4 right-align">
							<a class="btn btn-large waves-effect indigo modal-trigger"
								href="#frmBoleta" id="btnAgregar"><i
								class="material-icons left">add</i>Agregar</a>
						</div>
					</div>
					<div class="section row">
					 	<div class="col s12">						
							<ul class="tabs">
							    <li class="tab col s4"><a class="blue-text text-darken-2" id ="tabs1" href ="#tabs-1" onclick = "iniciaPaginacionBoletas('<s:property value="idPersona"/>','1', '')">Boletas Aprobadas</a></li>
							    <li class="tab col s4"><a class="blue-text text-darken-2" id = "tabs2" href ="#tabs-2" onclick = "iniciaPaginacionBoletas('<s:property value="idPersona"/>','2', '');">Boletas pendientes por aprobar</a></li>
							    <li class="tab col s4"><a class="blue-text text-darken-2" id = "tabs3" href ="#tabs-3" onclick = "iniciaPaginacionBoletas('<s:property value="idPersona"/>','3', getFiltro());">Tr&aacute;mites Realizados</a></li>		
								<div class="indicator blue" style="z-index:1"></div>
							</ul>
						</div>
					</div>
					<div class="section" id="tabs-1">
						<div class="row note">					    	
					        	<span>Boletas Aprobadas</span>					   		
					 	</div>																		    
						<form>
							<div class="input-field col s4">									
								<input type="text" class="form-control" id="aprofiltro" name="aprofiltro" placeholder="Buscar" onkeypress="return runScript(event)">										
							</div>
							<div class="col s4">
								<button type="button" class="btn waves-effect indigo" onclick = "aprobadasByFiltro()" >
									<span class="fooicon fooicon-search"></span>
								</button>
							</div>								
						</form>
						<div id="BoAprobadas">
					   	</div>	
					</div>
					<div id="tabs-2" class="section">
						<div class="row note">					    	
					        	<span>Boletas pendientes por aprobar</span>					   		
					 	</div>
						<form>
							<div class="input-field col s4">									
								<input type="text" class="form-control" id="penfiltro" name="penfiltro" placeholder="Buscar" onkeypress="return runScript(event)">										
							</div>
							<div class="col s4">
								<button type="button" class="btn waves-effect indigo" onclick = "pendientesByFiltro()" >
									<span class="fooicon fooicon-search"></span>
								</button>
							</div>								
						</form>
						<div id="BoPendientes">
					   	</div>						
					</div>
					<div id="tabs-3" class="section">
						<div class="row note">
							<span>Tr&aacute;mites Realizados</span>
						</div>
						<div class="row">
							<div class="input-field col s6">
								<input type="text" name="fechaInicial" class="datepicker" id="fechaInicial" />									
								<label for="fechaFinal">Fecha Inicial</label>
							</div>
							<div class="input-field col s6">
								<input type="text" name="fechaFinal" class="datepicker" id="fechaFinal" />									
								<label for="fechaFinal">Fecha Final</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s4">									
								<input type="text" class="form-control" id="realfiltro" name="realfiltro" placeholder="Buscar por operación o usuario" onkeypress="return runScript(event)">										
							</div>
							<div class="col s8 right-align">
								<button type="button" class="btn waves-effect indigo" onclick = "realizadosByFiltro()" >
									<span class="fooicon fooicon-search"></span>
								</button>
								<button class="waves-effect waves-light btn" onclick="exportData();">Exportar</button>
							</div>
						</div>
						<div id="TraRealizados">
					   	</div>			
					</div>
					<div id="tabs-4" class="section"></div>
				</div>
			</div>
		</div>
		<div class="col s1"></div>
	</div>	
</div>
</main>
<div class="section"></div>
<div id="frmBoleta" class="modal">
	<div class="modal-content">
		<div class="card">
			<div class="card-content">
				<s:form action="registrarBoleta.do" namespace="inscripcion"
					method="post" enctype="multipart/form-data"
					theme="simple" name="formBoleta" id="formBoleta">
					<span class="card-title">Boleta de Pago</span>
					<div class="row">
						<div class="col m1"></div>
						<div class="col s12 m10">
							<div class="row">
								<div class="input-field col s6">
									<s:textfield name="mdNumeroBoleta" id="mdNumeroBoleta"
										cssClass="validate" maxlength="150" />
									<label for="mdNumeroBoleta">N&uacute;mero de Boleta</label>
								</div>
								<div class="input-field col s6">
									<s:textfield name="mdCantidad" id="mdCantidad"
										cssClass="validate" maxlength="150"
										onkeypress="return IsNumber(event);" />
									<label for="mdCantidad">Cantidad</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<select name="mdBanco" id="mdBanco">
								      <option value="" disabled selected>Seleccione una opci&oacute;n</option>
								      <option value="1">Banrural</option>
								      <option value="2">CHN</option>								      
								      <option value="BI3">BI</option>								      
								    </select>
							    	<label>Banco</label>
							    </div>
								<div class="input-field col s6">
									<select name="mdPago" id="mdPago">
								      <option value="" disabled selected>Seleccione una opci&oacute;n</option>
								      <option value="1">Efectivo</option>
								      <option value="2">Cheque mismo Banco</option>								      
								      <option value="3">Cheque otro Banco</option>
								    </select>
							    	<label>Forma de Pago</label>
								</div>
							</div>
							<div class="row">
								<div class="file-field input-field">
									<div class="btn">
										<span>Boleta</span>
										 <input type="file" name="upload" id="uploadfile" />										
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text"
											placeholder="Selecciona un archivo...">
									</div>
								</div>
							</div>
							<br />
							<hr />
							<center>
								<div class="row">
									<a href="#!"
										class="waves-effect waves-green btn-large indigo"
										onclick="ver_pago();">Aceptar</a>
								</div>
							</center>
						</div>
						<div class="col m1">
							<s:hidden value="%{idPersona}" id="idPersona" name="idPersona"/>
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/OperacionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/BoletaDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/operacionesJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript">
iniciaPaginacionBoletas('<s:property value="idPersona"/>','1', '');

function runScript(event) {
    if (event.which == 13 || event.keyCode == 13) {
        //code to execute here
        return false;
    }
    return true;
};

function realizadosByFiltro(){
	/*var filtro = getObject('realfiltro').value;
	if(filtro!=null && filtro!=''){		
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','3',filtro);
	} else {			
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','3', '');
	}*/
	iniciaPaginacionBoletas('<s:property value="idPersona"/>','3', getFiltro());
}

function aprobadasByFiltro(){
	var filtro = getObject('aprofiltro').value;
				
	if(filtro!=null && filtro!=''){		
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','1',filtro);
	} else {			
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','1', '');
	}
}

function pendientesByFiltro(){
	var filtro = getObject('penfiltro').value;
				
	if(filtro!=null && filtro!=''){		
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','2',filtro);
	} else {			
		iniciaPaginacionBoletas('<s:property value="idPersona"/>','2', '');
	}
}

function validarBoleta() {
	var numero = $('#mdNumeroBoleta').val();
	var monto = $('#mdCantidad').val();
	var banco = $('#mdBanco').val();
	var formaPago = $('#mdPago').val();
	
	if (!numero || !monto || !banco || !formaPago) {
		return false;
	}
	return true;
}
function ver_pago() {
	if (validarBoleta()) {
	  var e = document.getElementById("mdPago");
	  var valPago = e.options[e.selectedIndex].value;
	  
	  var imgVal = $('#uploadfile').val(); 
      if(imgVal=='') 
      { 
    	  MaterialDialog.alert(
  				"Debe cargar la boleta de pago.",
  				{
  					title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
  					buttons:{
  						close:{
  							className:"red",
  							text:"cerrar"						
  						}
  					}
  				}
  			);
  			return false;          
      } 
	  
	  if(valPago == '3'){		  
		  MaterialDialog.dialog(
			"Este tipo de deposito será aprobado hasta realizada la compensaci&oacute;n.",
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
							document.getElementById("formBoleta").submit();
						}
					}
				}
			}
		);
					
	  }  else {
          document.getElementById("formBoleta").submit();
      }
	} else {
		MaterialDialog.alert(
				"Debe ingresar la información de la boleta de pago.",
				{
					title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
					buttons:{
						close:{
							className:"red",
							text:"cerrar"						
						}
					}
				}
			);
			return false;
	}
}
  
 function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;	
	return (key <= 13 || (key >= 48 && key <= 57) || key == 46);
}
function exportData() {
	var filtro = JSON.parse(getFiltro());
	var fechaInicial = moment(filtro.fechaInicial);
	var fechaFinal = moment(filtro.fechaFinal);
	var dias = (fechaFinal.diff(fechaInicial, 'days'));
	if (dias > 90) {
		MaterialDialog.alert(
			"No puede seleccionar más de tres meses para exportar los datos.",
			{
				title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
				buttons:{
					close:{
						className:"red",
						text:"cerrar"						
					}
				}
			}
		);
		return;
	}
	var URL="<%=request.getContextPath()%>/home/exportExcel.do?filtro=" + filtro.nombre + "&fechaInicial=" + filtro.fechaInicial + "&fechaFinal=" + filtro.fechaFinal;
	console.log(URL);
	window.open(URL, "_blank");
}
function docReady(fn) {
    // see if DOM is already available
    if (document.readyState === "complete" || document.readyState === "interactive") {
        // call on next available tick
        setTimeout(fn, 1);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}
docReady(function() {
	var today = new Date();
	var $fechaInicial = $('#fechaInicial').pickadate({
        selectMonths: true,
        selectYears: 15, 
        today: 'Hoy',
        clear: 'Inicializar',
        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
        close: 'Ok',
        closeOnSelect: false,
        format: 'dd/mm/yyyy'
    });
	var fechaInicialPicker = $fechaInicial.pickadate('picker');
	fechaInicialPicker.set('select', addDays(today, -30));
	var $fechaFinal = $('#fechaFinal').pickadate({
        selectMonths: true,
        selectYears: 15, 
        today: 'Hoy',
        clear: 'Inicializar',
        monthsFull: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        weekdaysShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
        close: 'Ok',
        closeOnSelect: false,
        format: 'dd/mm/yyyy'
    });
	var fechaFinalPicker = $fechaFinal.pickadate('picker');
	fechaFinalPicker.set('select', today);
});
function addDays(date, days) {
	const copy = new Date(Number(date))
	copy.setDate(date.getDate() + days)
	return copy
}
function getFiltro() {
	var fechaInicial = $('#fechaInicial').val();
	fechaInicial = fechaInicial.split('/');
	var fechaFinal = $('#fechaFinal').val();
	fechaFinal = fechaFinal.split('/');
	var filtro = {
		nombre:	$('#realfiltro').val(),
		fechaInicial: fechaInicial[2] + '-' + fechaInicial[1] + '-' + fechaInicial[0],
		fechaFinal: fechaFinal[2] + '-' + fechaFinal[1] + '-' + fechaFinal[0]
	};
	console.log(filtro);
	return JSON.stringify(filtro);
}
</script>
