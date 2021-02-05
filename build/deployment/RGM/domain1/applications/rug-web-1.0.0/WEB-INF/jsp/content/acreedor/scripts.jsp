<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<%-- <script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script> --%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>

<script type="text/javascript">
function firmaUsuario(idTramite){
	window.location.href="/Rug/firma/firmaUsuario.do?idTramite="+idTramite;
}

//var idPersona = <s:property value="idPersona"/>;
//cargaParteAcreedorRepresentado('acredID',idPersona,'0');
//$("#dosMenu").attr("class","linkSelected");
//cargaFormDrirecciones("cpTab");
// inicializacion
$('#residenciaA').val(1);
$('#residenciaA').material_select();
$('#nacionalidadA').val(1);
$('#nacionalidadA').material_select();
$('#tipoPersonaA').val('PF');
$('#tipoPersonaA').material_select();
cambiaTipoPersonaARep(0);
cambiaResidencia();
cambiaNacionalidadARep(0);


function cambiaInscribe() {
	var obligatorio = "*";
	if ($('#acreedorInscribe').is(':checked')) {
		obligatorio = "*";
	} else {
		obligatorio = "";
	}
	$('#curpDocA').html('<span class="textoGeneralRojo">' + obligatorio + 'CURP :  </span>');
	$('#tipoObliga').html('<span class="textoGeneralRojo">' + obligatorio + '</span>');
	$('#folioxxx').html('<span class="textoGeneralRojo">' + obligatorio + '</span>');
}
function cambiaTipoPersonaARep(esAutoridadStr) {
	var valor = $('#tipoPersonaA').val();
	var valor2 = $('#nacionalidadA').val();
	if (valor == 'PM') {
		$('#folioIDNoExtAC').show();
		$('#folioIDExtAC').hide();
		$('#buttonValidar').hide();
		$('#NIFpaisAC').hide();
		$('#trtdoptogantefisicoAcreedores').hide();
		$('#anotacionRfc').html('<span class="textoGeneralRojo">* RFC  :  </span>');
		$('#personaFisicaTRA').find('label').html('Razón o Denominación Social')
		$('#personaFisicaTRA').show();
		$('#personaMoralTRA').show();
	} else {
		$('#razonSocialA').val(''); // razonSocialA
		$('#razonSocialA').prop('disabled', false);
		$('#folioExistenteAC').val('');
		$('#folioExistenteAC').prop('disabled', false);
		$('#rfcA').val('');
		$('#rfcA').prop('disabled', false);
		$('#optotorganteAC').prop('checked', false);
		$('#folioIDNoExtAC').show();
		$('#folioIDExtAC').hide();
		$('#buttonValidar').hide();
		$('#NIFpaisAC').hide();
		$('#nombreA').prop('disabled', false);
		$('#trtdoptogantefisicoAcreedores').show();
		$('#anotacionRfc').html('<span class="textoGeneralRojo">* RFC  :  </span>');
		$('#personaFisicaTRA').find('label').html('Nombre Completo')
		$('#personaFisicaTRA').show();
		$('#personaMoralTRA').hide();
	}
}
function cambiaNacionalidadARep(esAutoridadStr) {
}
function cambiaNacionalidadARepOld(esAutoridadStr) {
	var valor = $('#nacionalidadA').val();
	var valor2 = $('#tipoPersonaA').val();

	if (valor == '1') {

		/* Validacion de despliegue de campos tipo y folio electronico */
		$('#TipoDA').val('TI');
		$('#personaTipoSociedadAC').show();
		$('#folioElectronicoTRAC').hide();
		$('#NIFpaisAC').hide();
		$('#trtdoptogantefisicoAcreedores').hide();
		$('#NIFpaisAC').hide();
		/* Validacion de despliegue de campos tipo y folio electronico */

		$('#anotacionRfc').html('<span class="textoGeneralRojo"> * RFC  :  </span>');

		if (valor2 == 'PM') {
			$('#razonSocialA').val('');// razonSocialA
			$('#razonSocialA').prop('disabled', false);
			$('#folioExistenteAC').val('');
			$('#folioExistenteAC').prop('disabled', false);
			$('#rfcA').val('');
			$('#rfcA').prop('disabled', false);

			$('#curpAyudaIDAcre').hide();
			$('#docExtranjeroTRAE').hide();
			$('#optotorganteAC').prop('checked', false);
			$('#folioIDNoExtAC').show();
			$('#folioIDExtAC').hide();
			$('#buttonValidar').hide();
		} else {
			$('#nombreA').prop('disabled', false);
			$('#apellidoPaternoA').prop('disabled', false);
			$('#apellidoMaternoA').prop('disabled', false);
			$('#docExtranjeroA').val('');
			$('#docExtranjeroA').prop('disabled', false);

			$('#curpAyudaIDAcre').show();
			$('#optotorganteAC').prop('checked', false);
			$('#folioIDNoExtAC').show();
			$('#folioIDExtAC').hide();
			$('#buttonValidar').hide();
			$('#curpDocA').html('<span class="textoGeneralRojo">&nbsp;CURP :  </span>');
			$('#docExtranjeroTRAE').show();
			$('#personaTipoSociedadAC').hide();
			$('#folioElectronicoTRAC').hide();
			$('#trtdoptogantefisicoAcreedores').show();

			$('#apellidoMaternoTxtA').html('<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>');
		}
	} else {
		$('#razonSocialA').val('');// razonSocialA
		$('#razonSocialA').prop('disabled', false);
		$('#folioExistenteAC').val('');
		$('#folioExistenteAC').prop('disabled', false);
		$('#rfcA').val('');
		$('#rfcA').prop('disabled', false);
		$('#TipoDA').val('TI');

		$('#nombreA').prop('disabled', false);
		$('#apellidoPaternoA').prop('disabled', false);
		$('#apellidoMaternoA').prop('disabled', false);
		/* Validacion de despliegue de campos tipo y folio electronico */
		$('#personaTipoSociedadAC').hide();
		$('#folioElectronicoTRAC').hide();
		$('#trtdoptogantefisicoAcreedores').show();
		$('#optotorganteAC').prop('checked', false);
		$('#folioIDNoExtAC').show();
		$('#folioIDExtAC').hide();
		$('#buttonValidar').hide();
		/* Validacion de despliegue de campos tipo y folio electronico */
		if (valor2 == 'PM') {
			$('#NIFpaisAC').show();
		} else {
			$('#NIFpaisAC').hide();
		}

		$('#docExtranjeroTRAE').hide();
		$('#curpAyudaIDAcre').hide();

		$('#apellidoMaternoTxtA').html('<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>');
	}
}
function cambiaTipoDAcreedor() {
	var valor = $('#tipoPersonaA').val();
	var valor2 = $('#nacionalidadA').val();
	var valor3 = $('#TipoDA').val();
	if (valor == 'PM') {// Si es moral
		if (valor2 == '1') {// Si es mexicano
			if (valor3 == 'SM') {// Tipo de organizacion (Social Mercantil)
				$('#trtdoptogantefisicoAcreedores').prop('checked', false);
				$('#folioElectronicoTRAC').show; // Este lo modificamos 11/08/2014
				$('#trtdoptogantefisicoAcreedores').hide;
				$('#razonSocialA').val('');// razonSocialA
				$('#razonSocialA').prop('disabled', false);
				$('#folioExistenteAC').val('');
				$('#folioExistenteAC').prop('disabled', false);
				$('#rfcA').val('');
				$('#rfcA').prop('disabled', false);
			} else if (valor3 == 'OT') {// Tipo de organizacion (Otro)
				$('#folioElectronicoTRAC').hide;
				$('#trtdoptogantefisicoAcreedores').show;
			} else {
				$('#razonSocialA').val('');// razonSocialA
				$('#razonSocialA').prop('disabled', false);
				$('#folioExistenteAC').val('');
				$('#folioExistenteAC').prop('disabled', false);
				$('#rfcA').val('');
				$('#rfcA').prop('disabled', false);
				$('#TipoDA').val('TI');
				$('#trtdoptogantefisicoAcreedores').prop('checked', false);
				$('#folioElectronicoTRAC').hide;
				$('#trtdoptogantefisicoAcreedores').hide;
			}
		}
	}
}
function cambiaFolioElectronicoAcreedor() {
	if ($('#optotorganteAC').is(':checked')) {
		$('#folioIDExtAC').show();
		$('#folioIDNoExtAC').hide();
		$('#buttonValidar').show();
	} else {
		$('#folioIDNoExtAC').show();
		$('#folioIDExtAC').hide();
		$('#buttonValidar').hide();
	}
}
function desactivaBoton() {
	$("#butonIDAceptar").prop('disabled', false);
}
function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57));
}
var arrayMails = new Array();
function agregaMail() {
	var i = 1;
	while ($('#email' + i).length > 0) {
		arrayMails[i] = $('#email' + i).val();
		i++;
	}
	arrayMails[i] = "";
	mailTextShow();
}
function eliminaMail(elemento) {
	var sig = 1;
	var actual = 1;
	while ($('#email' + sig).length > 0) {
		if (sig != elemento) {
			arrayMails[actual] = $('#email' + sig).val();
			actual++;
		}
		sig++;
	}
	arrayMails.splice(arrayMails.length - 1, 1);
	mailTextShow();
}
function mailTextShow() {
	$('#correosExtra').empty();
	for (var i = 1; i < arrayMails.length; i++) {
		var divRow = $('<div>', {class: 'row'}).append(
			$('<div>', {class: 'input-field col s10'}).append(
				$('<input>', {type: 'text', name: 'email' + i, id: 'email' + i, value: arrayMails[i]})
			)
		).append(
			$('<div>', {class: 'col s2'}).append(
				$('<a>', {text: 'Eliminar', class: 'btn waves-effect red darken-4 waves-light', onclick: 'eliminaMail(' + i + ')', name: 'email' + i, id: 'email' + i})
			)
		);
		$('#correosExtra').append(divRow);
	}
	if (arrayMails.length < 5) {
		$('#Agregar').show();
	} else {
		$('#Agregar').hide();
	}
}
function cambiaNacionalidadA(esAutoridadStr) {
	var valor = $('#nacionalidadA').val();
	var valor2 = $('#tipoPersonaA').val();
	$('#docExtranjeroA').removeAtt("onkeyup");
	$('#docExtranjeroA').removeAtt("maxLength");
	$('#docExtranjeroA').attr("maxLength", "50");

	if (valor == '1') {
		if (valor2 == 'PM') {
			$('#docExtranjeroTRA').hide();
		} else {
			$('#docExtranjeroTRA').hide();
			$('#apellidoMaternoTxtA').html('<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>');

		}
	} else {
		$('#docExtranjeroTRA').show();
		$('#curpAyudaIDAcre').hide();
		$('#curpDocA').html('<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el pa&iacute;s:  </span>');
	}
}
function cambiaResidencia() {
}
function maximaLongitud(texto, maxlong) {
	var tecla, int_value, out_value;

	if (texto.value.length > maxlong) {
		/*
		 * con estas 3 sentencias se consigue que el texto se reduzca al tamaño
		 * maximo permitido, sustituyendo lo que se haya introducido, por los
		 * primeros caracteres hasta dicho limite
		 */
		in_value = texto.value;
		out_value = in_value.substring(0, maxlong);
		texto.value = out_value;
		return false;
	}
	return true;
}
function noVacio(valor) {
	for (i = 0; i < valor.length; i++) {
		if (valor.charAt(i) != " ") {
			return true;
		}
	}
	return false;
}
function valEmail(valor) {
	re = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/
	if (!re.exec(valor)) {
		return false;
	} else {
		return true;
	}
}
function validaRfc(rfcStr) {

	var strCorrecta;
	strCorrecta = rfcStr;
	if (!noVacio(rfcStr)) {
		alert('El campo RFC es obligatorio');
		return false;
	}
	if (rfcStr.length == 13) {

		var valid = '^(([A-Z_Ñ_&]|[a-z_ñ_&]|\s){1})(([A-Z_Ñ_&]|[a-z_ñ_&]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';

	} else {

		alert('El campo RFC es incorrecto');
		return false;
	}
	var validRfc = new RegExp(valid);
	var matchArray = strCorrecta.match(validRfc);
	if (matchArray == null) {

		alert('El campo RFC es incorrecto');
		return false;
	} else {

		return true;
	}

}
function guardaParteAcreedorRepresentado(elementID, idPersona, idPersonaModificar, esAutoridadStr) {
	var nombre = $('#nombreA').val();
	var apellidoP = '';
	var apellidoM = '';
	var razonSocial = $('#razonSocialA').val();
	var nacionalidad = $('#nacionalidadA').val();
	var tipoPersona = $('#tipoPersonaA').val();
	var folioElectronico = '';
	var rfc = $('#rfcA').val();
	var idColonia = '';
	var idLocalidad = '';
	var calle = '';
	var numExterior = '';
	var curpdoc = '';
	var numInterior = '';
	var correoElectronico = $('#correoElectronico').val();
	var telefono = $('#telefono').val();
	var extencion = $('#extencionA').val();
	var codigoPostal = '';
	var domicilioUno = $('#domicilioUno').val();
	var domicilioDos = '';
	var poblacion = '';
	var zonaPostal = '';
	var residencia = $('#residenciaA').val();
	var tipoSociedad = '';
	var nif = '';
	var acreedorInscribe;

	acreedorInscribe = false;
	
	// Campos nuevos
	var inscrita = $('#inscritaA').val();
	var folioInscrito = $('#folioA').val();
	var libroInscrito = $('#libroA').val();
	var ubicacionInscrito = $('#ubicacionA').val();
	var edad = $('#edadA').val();
	var estadoCivil = $('#estadoCivilA').val();
	var profesion = $('#profesionA').val();

	var correosAdicionales = new Array();

	for (var i = 0; i < 4; i++) {
		var correoAdicional = '#email' + (i + 1);
		if ($(correoAdicional).length > 0) {
			correosAdicionales[i] = $(correoAdicional).val();
			if (correosAdicionales[i] == ' ') {
				correosAdicionales[i] = '';
			}
		} else {
			correosAdicionales[i] = '';
		}
	}

	var cadenaStr = idPersona + idPersonaModificar + nombre + razonSocial 
		+ nacionalidad + tipoPersona + rfc + correoElectronico + telefono
		+ extencion + domicilioUno + residencia;

	if (cadenaStr.indexOf('<script') >= 0) {
		alert('no puedes escribir codigo malicioso');
		return false;
	}

	elementIDACreedor = elementID;

	var esAutoridad = false;

	if (esAutoridadStr == '1') {
		esAutoridad = true;
	}

	var valor = tipoPersona;
	var valor2 = nacionalidad;

	if (valor == 'PM') {
		if (valor2 == '1') {
			// Persona Moral Mexicana
			if (!noVacio(razonSocial)) {
				alert('El campo Denominación o Razón Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Teléfono es obligatorio');
				return false;
			}
			if (!noVacio(correoElectronico)) {
				alert('El campo Correo Electrónico es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electrónico es incorrecto');
				return false;
			}
			for (var i = 0; i < correosAdicionales.length; i++) {
				if (correosAdicionales[i] != '') {
					if (!valEmail(correosAdicionales[i])) {
						alert('El campo Correo Electronico Adicional '
								+ (i + 1) + ' es incorrecto');
						return false;
					}
				}
			}
			if (esAutoridad) {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				}else {
				if (valor2 == '1') {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
				}
			} else {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				} else {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
			}
		} else {
			// Persona Moral Extrangera
			if (!noVacio(razonSocial)) {
				alert('El campo Denominación o Razón Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Teléfono es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electrónico es incorrecto');
				return false;
			}
			for (var i = 0; i < correosAdicionales.length; i++) {
				if (correosAdicionales[i] != '') {
					if (!valEmail(correosAdicionales[i])) {
						alert('El campo Correo Electronico Adicional '
								+ (i + 1) + ' es incorrecto');
						return false;
					}
				}
			}
			if (!noVacio(correoElectronico)) {
				alert('El campo Correo Electrónico es obligatorio');
				return false;
			}
			if (esAutoridad) {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				}
			} else {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
				} else {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
			}

		}

	} else {
		// Persona Fisica
		if (valor2 == '1') {
			// Mexicano
			folioElectronico = $('#folioExistenteAC').val();
			
			if (!esAutoridad) {
				if (rfc.length > 0) {
					rfcStr = rfc;
				} else {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
			}
		} else {
			folioElectronico = $('#folioExistenteAC').val();
			// Extranjero
			if (esAutoridad) {
				if (rfc.length > 0) {
					rfcStr = rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				}
			} else {
				if (rfc.length > 0) {
					rfcStr = rfc;
				} else {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
			}
		}
		if (!noVacio(nombre)) {
			alert('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(correoElectronico)) {
			alert('El campo Correo Electrónico es obligatorio');
			return false;
		}
		if (!valEmail(correoElectronico)) {
			alert('El campo Correo Electrónico es incorrecto');
			return false;
		}
		for (var i = 0; i < correosAdicionales.length; i++) {
			if (correosAdicionales[i] != '') {
				if (!valEmail(correosAdicionales[i])) {
					alert('El campo Correo Electronico Adicional ' + (i + 1)
							+ ' es incorrecto');
					return false;
				}
			}
		}
		if (!noVacio(telefono)) {
			alert('El campo Teléfono es obligatorio');
			return false;
		}
	}
	// Domicilios Verificacion
	if (!noVacio(domicilioUno)) {
		alert('El domicilio Uno es obligatorio');
		return false;
	}
	
	for (var i = 0; i < correosAdicionales.length; i++) {
		if (correosAdicionales[i] != '') {
			correoElectronico = correoElectronico + ',' + correosAdicionales[i];
		}
	}
	ParteDwrAction.guardaParteAcreedorRepresentadoSinMensaje(elementID,
			idPersona, idPersonaModificar, nombre, apellidoP, apellidoM,
			razonSocial, nacionalidad, tipoPersona, folioElectronico, rfc,
			idColonia, idLocalidad, calle, numExterior, numInterior,
			correoElectronico, telefono, extencion, curpdoc, domicilioUno,
			domicilioDos, poblacion, zonaPostal, residencia, tipoSociedad,
			acreedorInscribe, nif, inscrita, folioInscrito, libroInscrito,
			ubicacionInscrito, edad, estadoCivil, profesion, showNuevoAcreedorRep);
}
function showNuevoAcreedorRep(message) {
	if (message.codeError == 0) {
		var data = JSON.parse(message.data);
		var row = $('<tr>').append(
			$('<td>').html(data.nombre)
		).append(
			$('<td>').html(data.rfc)
		).append(
			$('<td>').append(
				$('<a>', {text: 'Confirmar', class: 'btn waves-effect indigo', onclick: 'firmaAcreedorRepresentado(' + data.idTramitePendiente + ')'})
			).append(" ").append(
				$('<a>', {text: 'Eliminar', class: 'btn waves-effect red darken-4', onclick: 'eliminaParteAcreedorRepresentado(' + data.idTramitePendiente + ')'})
			)
		);
		
		$('#acreedoresPendientes tbody').append(row);

		$('#frmAcreedor').modal('close');
		inicializaFormAcreedor();
		// TODO: mostrar mensaje de exito
	} else {
		// TODO: mostrar errores al grabar acreedor
	}
}
function showAcreedorRepEliminado(message) {
	if (message.codeError == 0) {
		var idPersona = message.data;
		
		$('#acreedoresPendientes tbody #' + idPersona).remove();
	} else {
		// TODO: mostrar errores al eliminar acreedor pendiente
	}
}
function firmaAcreedorRepresentado(idTramite) {
	window.location.href = "/Rug/firma/firmaAcreedores.do?idTramite=" + idTramite;
}
function eliminaParteAcreedorRepresentado(idPersona, idPersonaModificar) {
	if (confirm("Esta seguro que desea desvincularse con este acreedor")) {
		ParteDwrAction.eliminaAcreedorRepresentado(idPersona, idPersonaModificar, showAcreedorRepEliminado);
	}
}
function controlUsuarioJS(idAcreedor) {
	window.location.href = "/Rug/controlusuario/setAcreedor.do?idAcreedor=" + idAcreedor;
}
function cargaAcreedor(acreedorId) {
	ParteDwrAction.getAcreedor(acreedorId, recibeAcreedor);
}
function recibeAcreedor(data) {
	var acreedor = JSON.parse(data);
	inicializaFormAcreedor(acreedor);
	$('#frmAcreedor').modal('open');	
}
function inicializaFormAcreedor(acreedor) {
	if (acreedor) {
		$('#tipoPersonaA').val(acreedor.tipoPersona);
		$('#nacionalidadA').val(acreedor.idNacionalidad);
		$('#nif').val(acreedor.nif);
		$('#nombreA').val(acreedor.nombre);
		$('#razonSocialA').val(acreedor.razonSocial);
		$('#inscritaA').val(acreedor.inscrita);
		$('#folioA').val(acreedor.folioInscrito);
		$('#libroA').val(acreedor.libroInscrito);
		$('#ubicacionA').val(acreedor.ubicacionInscrito);
		$('#rfcA').val(acreedor.rfc);
		$('#extencionA').val(acreedor.extencion);
		$('#edadA').val(acreedor.edad);
		$('#estadoCivilA').val(acreedor.estadoCivil);
		$('#profesionA').val(acreedor.profesion);
		$('#telefono').val(acreedor.telefono);
		$('#correoElectronico').val(acreedor.correoElectronico);
		$('#residenciaA').val(acreedor.idPaisResidencia);
		$('#domicilioUno').val(acreedor.domicilioUno);
	} else {
		$('#tipoPersonaA').val('PF');
		$('#nacionalidadA').val(1);
		$('#nif').val('');
		$('#nombreA').val('');
		$('#razonSocialA').val('');
		$('#inscritaA').val('');
		$('#folioA').val('');
		$('#libroA').val('');
		$('#ubicacionA').val('');
		$('#rfcA').val('');
		$('#extencionA').val('');
		$('#edadA').val('');
		$('#estadoCivilA').val('');
		$('#profesionA').val('');
		$('#telefono').val('');
		$('#correoElectronico').val('');
		$('#residenciaA').val(1);
		$('#domicilioUno').val('');
	}
	$('#residenciaA').material_select();
	$('#nacionalidadA').material_select();
	$('#tipoPersonaA').material_select();
	Materialize.updateTextFields();
	$('#frmAcreedor').scrollTop(0);
}
$('#btnAgregar').on('click', function () {
	inicializaFormAcreedor();
});
</script>
