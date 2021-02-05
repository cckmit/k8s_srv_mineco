var elementID;
var elementIDDeudor;
var elementIDACreedor;
var elementIDACreedorRepresentado;
var elementIDBienes;
var elementResBusqueda;
var elementIDBusquedaEM;
var isInscripcionGlobal = '0';
var elementIDMostrarDesUsuario;
var numLinea = 0;

function maximaLongitud(texto, maxlong) {
	var tecla, int_value, out_value;

	if (texto.value.length > maxlong) {
		/*
		 * con estas 3 sentencias se consigue que el texto se reduzca al tama;o
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

function agregarOtorganteAdicional() {

	var valor = document.getElementById('tipoPersona').value;
	if (valor == 'PM') {
		showObject('trtdoptogantefisico', false);
	}
	showObject('agregaOtorganteAddID', true);
}

function ocultarAgregarOtorganteAdicional(elementId, idTramite, idPersona,
		idPersonaModificar, isInscripcion) {
	cargaParteOtorgante(elementId, idTramite, idPersona, idPersonaModificar,
			isInscripcion);
}

function desabilitarNvoUsuario() {
	showObject('tableIDT', true);
	document.getElementById('usuarioTO.nombre').disabled = true;
	document.getElementById('usuarioTO.apaterno').disabled = true;
	document.getElementById('usuarioTO.amaterno').disabled = true;
	document.getElementById('usuarioTO.rfc').disabled = true;
}

function habilitarNvoUsuario() {
	showObject('tableIDT', true);
	document.getElementById('usuarioTO.nombre').disabled = false;
	document.getElementById('usuarioTO.apaterno').disabled = false;
	document.getElementById('usuarioTO.amaterno').disabled = false;
	document.getElementById('usuarioTO.rfc').disabled = false;
}

function ocultarDeudorLim(elementId, idTramite, idPersona, idPersonaModificar,
		isInscripcion) {
	cargaParteDeudor(elementId, idTramite, idPersona, idPersonaModificar,
			isInscripcion);
}
function ocultarAcreRep(elementIDAR, idPersona, idPersonaModificar) {
	cargaParteAcreedorRepresentado(elementIDAR, idPersona, idPersonaModificar);
}
// Aqui enpieza el control de los usuarios DWR
function muestraDesUsuario(idUsuario) {
	displayLoader(true);
	ocultarAltaUsuario();
	showObject('mostrardesusuario', true);
	elementIDMostrarDesUsuario = 'mostrardesusuario';
	ParteDwrAction.muestraDesUsuario(idUsuario, showParteDesUsuario);
}
function muestraModUsuario(idUsuario, idSubUsuario, idAcreedor) {
	displayLoader(true);
	ocultarAltaUsuario();
	showObject('mostrardesusuario', true);
	elementIDMostrarDesUsuario = 'mostrardesusuario';
	ParteDwrAction.muestraModificarUsuarioGrupo(idUsuario, idSubUsuario,
			idAcreedor, showParteDesUsuario);
}
function ocultarDesUsuario() {
	showObject('mostrardesusuario', false);
}
function cambiarUsuarioGrupo(idUsuario, idSubUsuario, idAcreedor) {
	displayLoader(true);
	ocultarAltaUsuario();
	showObject('mostrardesusuario', true);
	idGrupo = getObject('grupoNuevo').value;
	elementIDMostrarDesUsuario = 'mostrardesusuario';
	ParteDwrAction.cambiaUsuarioGrupo(idUsuario, idSubUsuario, idAcreedor,
			idGrupo, showParteDesUsuario);
}

// Aqui termina el control de los usuarios DWR

function ocultarAltaUsuario() {
	ocultarDesUsuario();
	showObject('usuarioTD', false);
}
function mostrarAltaUsuario() {
	ocultarDesUsuario()
	showObject('usuarioTD', true);
}

function showParteDesUsuario(message) {

	if (message.codeError == 0) {
		fillObject(elementIDMostrarDesUsuario, message.message);
		ParceJS(message.message);
	}
	displayLoader(false);

}
function buscaBuscaCorreoUsuario(elementIDBusca) {

	displayLoader(true);
	var correoElectronico = getObject('correoElectronico').value;

	if (noVacio(correoElectronico)) {

		elementIDBusquedaEM = elementIDBusca;
		ParteDwrAction.getBuscaCorreoUsuario(correoElectronico,
				showParteBuscaCorreoUsuario);
	} else {
		displayLoader(false);
	}
}
function validaCurp(cadena) {

	if (curp.match(/^([a-z]{4})([0-9]{6})([a-z]{6})([0-9]{2})$/i)) {
		return true;
	} else {
		return false;
	}

}

function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57));
}

function valEmail(valor) {
	re = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/
	if (!re.exec(valor)) {
		return false;
	} else {
		return true;
	}
}

function soloNumeros(id) {
	getElementById(id).Attributes
			.Add(
					"onkeypress",
					"var key; if(window.event){ key = event.keyCode;}else if(event.which){ key = event.which;} return (key == 45 || key == 13 || key == 8 || key == 9 || key == 189 || (key >= 48 && key <= 58) )");
}

function cargaParteOtorgante(elementId, idTramite, idPersona,
		idPersonaModificar, isInscripcion, isMultiples) {
	displayLoader(true);
	if (isMultiples == null) {
		isMultiples = '0';
	}

	ParteDwrAction.getParteOtorgante(elementId, idTramite, idPersona,
			idPersonaModificar, isInscripcion, isMultiples, showParteOtorgante);
	
	elementID = elementId;

}

function cargaParteComerciante(elementId, idTramite, idPersona,
		idPersonaModificar) {
	elementID = elementId;
	displayLoader(true);
	ParteDwrAction.getParteComerciante(elementId, idTramite, idPersona,
			idPersonaModificar, showParteComerciante);

}
function cargaParteBienes(elementId, idTramite){
	displayLoader(true);
	ParteDwrAction.getParteBienes(elementId, idTramite, showParteBienes);
	elementIDBienes = elementId;
}


//divParteDWR2,214,1,0,1
function cargaParteDeudor(elementId, idTramite, idPersona, idPersonaModificar,
		isInscripcion) {
	displayLoader(true);	
	if (isInscripcion != null) {
		if (isInscripcion == '1' || isInscripcion == '2') {
			ParteDwrAction.getParteDeudor(elementId, idTramite, idPersona,
					idPersonaModificar, isInscripcion, showParteDeudor);
		} else {
			ParteDwrAction.getParteDeudor(elementId, idTramite, idPersona,
					idPersonaModificar, '0', showParteDeudor);
		}

	} else {
		ParteDwrAction.getParteDeudor(elementId, idTramite, idPersona,
				idPersonaModificar, '0', showParteDeudor);
	}
	elementIDDeudor = elementId;
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
function validaRfcMoral(rfcStr) {
	var strCorrecta;
	strCorrecta = rfcStr;
	if (rfcStr.length == 12) {
		var valid = '^(([A-Z_�_&]|[a-z_�_&]|\s){1})(([A-Z_�_&]|[a-z_�_&]){2})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	} else {
		alert('El RFC es incorrecto');
		return false;
	}
	var validRfc = new RegExp(valid);
	var matchArray = strCorrecta.match(validRfc);
	if (matchArray == null) {
		alert('El RFC es incorrecto');
		return false;
	} else {

		return true;
	}

}

function validaCurp(strCurp) {
	var strCorrecta;
	strCorrecta = strCurp;
	if (strCurp.length == 18) {
		var valid = '^([a-zA-Z]{4,4}[0-9]{6}[a-zA-Z]{6,6}[0-9]{2})';

	} else {

		return false;
	}
	var validcurp = new RegExp(valid);
	var matchArray = strCorrecta.match(validcurp);
	if (matchArray == null) {

		return false;
	} else {

		return true;
	}

}

function elmismo(elementId, idTramite, idPersona, idPersonaModificar,
		isInscripcion) {
	displayLoader(true);
	ParteDwrAction.insertMismoDeudor(elementId, idTramite, idPersona,
			idPersonaModificar, isInscripcion, showParteDeudor);

}

function cargaParteAcreedorRepresentado(elementId, idPersona, idPersonaModificar) {
	numLinea = 0;
	elementIDACreedorRepresentado = elementId;
	//displayLoader(true);
	ParteDwrAction.getParteAcreedorRepresentado(elementId, idPersona, idPersonaModificar, ' ', showParteAcreedorRepresentado);
}

function cargaParteAcreedor(elementId, idTramite, idPersona, idPersonaModificar, isInscripcion) {
	elementIDACreedor = elementId;
	displayLoader(true);
	ParteDwrAction.getParteAcreedor(elementId, idTramite, idPersona,
			idPersonaModificar, isInscripcion, showParteAcreedor);
}

function eliminaParteOtorgante(elementId, idTramite, idPersona,
		idPersonaModificar, isInscripcion, isMultiple) {

	elementID = elementId;
	displayLoader(true);
	ParteDwrAction.eliminaParte(elementId, idTramite, idPersona,
			idPersonaModificar, "1", isInscripcion, isMultiple,
			showParteOtorgante);
}
function eliminaParteComerciante(elementId, idTramite, idPersona,
		idPersonaModificar) {

	elementID = elementId;
	displayLoader(true);
	ParteDwrAction.eliminaParte(elementId, idTramite, idPersona,
			idPersonaModificar, "1", "2", showParteOtorgante);
}

function eliminaParteBien(elementId, idTramite, idTramiteGar){
	elementIDBien = elementId;
	displayLoader(true);
	ParteDwrAction.eliminaParteBien(elementId, idTramite, idTramiteGar, showParteBienes);
}

function modificaParteBien(elementId, idTramite, tipoBien, tipoId, ident, desc, idTramiteGar){
	elementIDBien = elementId;
	displayLoader(true);	
	
	var x = tipoBien;
	var y = tipoId;	
	  
	if(y=='Placa') {
		  var veh = ident.split('-');
		  document.getElementById("mdIdentificador").value = veh[0];
		  document.getElementById("mdIdentificador1").value = veh[1];
		  document.getElementById("mdIdentificador2").value = '';
	  } else {
		  document.getElementById("mdIdentificador2").value = ident;
	  }
	document.getElementById("mdDescripcion").value = desc;
	  
	if(x=='Vehiculo'){		  	
		  document.getElementById("secId1").style.display = 'block'; 
		  document.getElementById("secId2").style.display = 'block';
		  document.getElementById("secId3").style.display = 'block';
		  document.getElementById("secId4").style.display = 'none';
		  
		  document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';
		  		    
		  document.getElementById("lblMdIdentificador2").innerHTML = 'VIN';
		  document.getElementById("mdBienEspecial").value = 1;
		  		  
	  } else if (x=='Factura'){
		  document.getElementById("secId1").style.display = 'none'; 
		  document.getElementById("secId2").style.display = 'none';
		  document.getElementById("secId3").style.display = 'block';	
		  document.getElementById("secId4").style.display = 'none';		  
		  
		  document.getElementById("lblMdIdentificador2").innerHTML = 'No. Factura';
		  document.getElementById("lblMdIdentificador3").innerHTML = 'Serie';
		  document.getElementById("lblMdDescripcion").innerHTML = 'Emitido Por';
		  
		  document.getElementById("mdBienEspecial").value = 2;
	  } else {
		  document.getElementById("secId1").style.display = 'none'; 
		  document.getElementById("secId2").style.display = 'none';
		  document.getElementById("secId3").style.display = 'block';
		  document.getElementById("secId4").style.display = 'none';		  
		  
		  document.getElementById("lblMdIdentificador2").innerHTML = 'No. Serie';
		  document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del bien';
		  document.getElementById("mdBienEspecial").value = 3;
	  }
	
	document.getElementById("secId5").style.display = 'none';
	document.getElementById("secId6").style.display = 'block';
	document.getElementById("formBienButton").innerHTML = 'Modificar';
	document.getElementById("formBienButton").onclick = function(){
		
	     var m_mdDescripcion = document.getElementById("mdDescripcion").value;
	     var m_idTipo = document.getElementById("mdBienEspecial").value;
	     var m_mdIdentificador = document.getElementById("mdIdentificador").value;
	     var m_mdIdentificador1 = document.getElementById("mdIdentificador1").value;
	     var m_mdIdentificador2 = document.getElementById("mdIdentificador2").value;
	     var m_mdIdentificador3 = document.getElementById("mdIdentificador3").value;

		ParteDwrAction.modificaParteBien(elementId, idTramite, m_mdDescripcion, m_idTipo, m_mdIdentificador, m_mdIdentificador1, m_mdIdentificador2, m_mdIdentificador3, idTramiteGar, showParteBienes);
		$('#frmBien').modal('close');
	}
	
	$('#frmBien').modal('open');
	Materialize.updateTextFields();
	//$('#frmBien').modal('close');
}

function eliminaParteDeudor(elementId, idTramite, idPersona,
		idPersonaModificar, isInscripcion) {

	elementIDDeudor = elementId;
	displayLoader(true);
	ParteDwrAction.eliminaParte(elementId, idTramite, idPersona,
			idPersonaModificar, "2", isInscripcion, showParteDeudor);

}
function eliminaParteAcreedor(elementId, idTramite, idPersona,
		idPersonaModificar) {

	elementID = elementId;
	displayLoader(true);

	ParteDwrAction.eliminaParte(elementId, idTramite, idPersona,
			idPersonaModificar, "3", '0', showParteAcreedor);

}

function ParceJS(ObjResponse) {
	/***************************************************************************
	 * funcion creada por Abraham Stalin y Felix Diaz funcion encargada de
	 * recorrer el texto devuelto por el responseText de AJAX, e identificar
	 * codigo JavaScript y habilitarlos para modo ejecucion desde la pagina
	 * llamada
	 * 
	 * Nota: cualquier mejora sobre el codigo hacermela saber
	 **************************************************************************/

	if ("" == ObjResponse) {
		alert("No se han enviado parametros a parcear");
		return false;
	}
	// variable que almacena el texto del codigo javascript
	var TextJs = "";
	// almacena la cadena de texto a recorrer para encontrar el archivo
	// incluido en lso js's
	var TextSrc = "";
	// arreglo que almacena cada uno de los archivos incluidos llamados por
	// src
	var FileJsSrc = new Array();
	var counter = 0;
	// guarda las porciones siguientes de codigo de HTML que se van
	// generando por cada recorrido del parceador
	var TextNextHtml;
	var PosJSTagStart;
	var PosJSTagEnd;
	// guarda la posicion de la primera ocurrencia del parametro src
	var SrcPosIni;
	// guarda la posicion de ocurrencia de las comillas
	var SrcPosComilla;
	while (ObjResponse.indexOf("<script") > 0) {
		/* encuentra la primera ocurrencia del tag <script */
		PosJSTagStart = ObjResponse.indexOf("<script");
		/*
		 * corta el texto resultante desde la primera ocurrencia hasta el final
		 * del texto
		 */
		TextNextHtml = ObjResponse.substring(PosJSTagStart, ObjResponse.length);
		/*
		 * encuentra la primera ocurrencia de finalizacion del tag >, donde
		 * cierra la palabra javascript
		 */
		PosJSTagEnd = TextNextHtml.indexOf(">");
		// captura el texto entre el tag <script>
		TextSrc = TextNextHtml.substring(0, PosJSTagEnd);
		// verficica si tiene le texto src de llamado a un archivo js
		if (TextSrc.indexOf("src") > 0) {
			// posicion del src
			SrcPosIni = TextSrc.indexOf("src");
			// almacena el texto desde la primera aparicion del src
			// hasta el final
			TextSrc = TextSrc.substring(SrcPosIni, PosJSTagEnd);
			// lee la posicion de la primer comilla
			SrcPosComilla = TextSrc.indexOf('"');
			// arma el texto, desde la primer comilla hasta el final,se
			// le suma 1, para pasar la comilla inicial
			TextSrc = TextSrc.substring(SrcPosComilla + 1, PosJSTagEnd);
			// posicion de la comilla final
			SrcPosComilla = TextSrc.indexOf('"');
			// lee el archivo
			SrcFileJs = TextSrc.substring(0, SrcPosComilla);
			FileJsSrc[counter] = SrcFileJs;
			counter++;

		}

		// TextNextHtml, nuevo porcion de texto HTML empezando desde el tag
		// script
		TextNextHtml = TextNextHtml.substring(PosJSTagEnd + 1,
				ObjResponse.length);
		// encuentra el final del script
		objJSTagEndSc = TextNextHtml.indexOf("script>");

		/*
		 * recorre desde la primera ocurrencia del tag > hasta el final del
		 * script < /script>
		 */
		// se le resta 2 al objJSTagEndSc, para restarle el < /
		objJSText = TextNextHtml.substring(0, objJSTagEndSc - 2);

		ObjResponse = TextNextHtml;
		TextJs = TextJs + "\n" + objJSText;

	}
	// alert("FinalJS\n\n"+objJs);

	// Agrego los scripts dentro del encabezado
	EvalScript = document.createElement("script");
	EvalScript.text = TextJs;
	document.getElementsByTagName('head')[0].appendChild(EvalScript);
	// Agrego los scripts incluidos dentro del encabezado
	for (i = 0; i < FileJsSrc.length; i++) {
		EvalScript = document.createElement("script");
		EvalScript.src = FileJsSrc[i];
		document.getElementsByTagName('head')[0].appendChild(EvalScript);
	}
	return true;
}

function eliminaParteAcreedorRepresentado(elementId, idPersona,
		idPersonaModificar) {
	elementID = elementId;
	if (confirm("Esta seguro que desea desvincularse con este acreedor")) {
		displayLoader(true);
		ParteDwrAction.eliminaAcreedorRepresentado(elementId, idPersona,
				idPersonaModificar, showParteAcreedorRepresentado);
	}
}

function eliminaTramiteMasivoSinFirmar(elementId, idPersona, idFirmaMasiva) {
	elementID = elementId;
	ParteDwrAction.eliminaTramiteMasivo(elementId, idPersona, idFirmaMasiva,
			showParteAcreedorRepresentado);
}

function bloqueaFolio() {
	getObject('folioExistente').readOnly = true;
}
function desbloqueaFolio() {
	getObject('folioExistente').readOnly = false;
}

function checkboxValRfcOtFolMoral() {
	if (getObject('rfcOtFolValidar1Moral').checked) {
		showObject('rfcOtFolMoral', false);
		showObject('tbejrfcOtFolMoral', false);
		showObject('tbejrfcOtFoltitMoral', false);
		document.getElementById("rfcOtFolMoral").value = "";
	}else{
		showObject('rfcOtFolMoral', true);
		showObject('tbejrfcOtFolMoral', true);
		showObject('tbejrfcOtFoltitMoral', true);
	}
}

function copiaParteOtorgante(idTramite, idPersona, idPersonaModificar, isInscripcion) {
	var folioElectronico = getObject('folioExistente').value;
	var tipoPersona = getObject('tipoPersona').value;
	var rfc = getObject('rfcOtFolMoral').value;
	isInscripcionGlobal = isInscripcion;
	if (tipoPersona == 'PM') {
		if (getObject('rfcOtFolValidar1Moral').checked==false) {
			if (!noVacio(rfc)) {
				displayLoader(false);
				alert('El campo RFC es obligatorio');
				return false;
			}
			if (!validaRfcMoral(rfc)) {
				displayLoader(false);
				return false;
			}
		}
		displayLoader(true);
		if (isInscripcion != null && isInscripcion == '1') {
			ParteDwrAction.copiaParteOtorganteMoral(elementID, idTramite, idPersona, idPersonaModificar, isInscripcion, folioElectronico,rfc, '0', showParteOtorgante);
		} else {
			ParteDwrAction.copiaParteOtorganteMoral(elementID, idTramite, idPersona, idPersonaModificar, isInscripcion,	folioElectronico,rfc, '1', showParteOtorgante);
		}
	} else {
		if (getObject('rfcOtFolValidar1Moral').checked==false) {
			if (!noVacio(rfc)) {
				displayLoader(false);
				alert('El campo RFC es obligatorio');
				return false;
			}
			if (!validaRfc(rfc)) {
				displayLoader(false);
				return false;
			}
		}
		displayLoader(true);
		if (isInscripcion != null && isInscripcion == '1') {
			ParteDwrAction.copiaParteOtorgante(elementID, idTramite, idPersona, idPersonaModificar, isInscripcion, folioElectronico,rfc, '0', showParteOtorgante);
		} else {
			ParteDwrAction.copiaParteOtorgante(elementID, idTramite, idPersona,	idPersonaModificar, isInscripcion, folioElectronico,rfc, '1',showParteOtorgante);

		}

	}

}

function guardaParteOtorgante(elementID, idTramite, idPersona, idPersonaModificar, isInscripcion, isMultiple) {
	
	//Generales
	var nacionalidad = getObject('nacionalidad').value;
	var tipoPersona = getObject('tipoPersona').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialO').value;
	var inscrita = getObject('inscritaO').value;
	var folio = '';
	var libro = '';
	var direccion = '';
	if(isInscripcion != '2') direccion = getObject('ubicacionInO').value;
	// seccion persona fisica
	var nombre = getObject('nombreO').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcO').value;
	var nit = getObject('nitO').value;
	var nitF = getObject('nitOF').value;
	var extendida = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correo = getObject('correoElectronicoO').value;
	// seccion direccion
	var residencia = 1;
	var domicilio = getObject('domicilioUnoO').value;

	var valor = document.getElementById('tipoPersona').value;
	var valor2 = document.getElementById('nacionalidad').value;

	//Valida codigo malicioso
	var cadenaStr = idTramite + idPersona + idPersonaModificar + isInscripcion + nitF 
				nombre + apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona +
				inscrita + folio + libro + direccion + rfc + extendida + edad + nit +
				estadoCivil + profesion + telefono + correo + residencia + domicilio;

	if (cadenaStr.indexOf('<script') >= 0) {
		alertMaterialize('no puedes escribir codigo malicioso');
		return false;
	}
	
	elementID = elementID;
	
	if (valor == 'PM') {
		//validaciones
		if (!noVacio(nit)) {
			alertMaterialize('El campo NIT es obligatorio');
			return false;
		}
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Razon Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if (isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del Representante es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.guardaParteOtorgante(elementID, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre, apellidoP,
				apellidoM, razonSocial, rfc, nacionalidad, inscrita, folio, libro,
				direccion, nit, edad, estadoCivil, profesion, telefono, correo, 
				residencia, domicilio, tipoPersona, isMultiple, showParteOtorgante);

	} else {
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}		
				
		displayLoader(true);
		ParteDwrAction.guardaParteOtorgante(elementID, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre, apellidoP,
				apellidoM, razonSocial, rfc, nacionalidad, inscrita, folio, libro,
				direccion, nitF, edad, estadoCivil, profesion, telefono, correo, 
				residencia, domicilio, tipoPersona, isMultiple, showParteOtorgante);

	}
	
}

function noVacio(valor) {
	for (i = 0; i < valor.length; i++) {
		if (valor.charAt(i) != " ") {
			return true;
		}
	}
	return false;
}

function modificaParteOtorgante(elementID, idTramite, idPersona,
		idPersonaModificar, isInscripcion, isMultiple) {

	//Generales
	var nacionalidad = getObject('nacionalidad').value;
	var tipoPersona = getObject('tipoPersona').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialO').value;
	var inscrita = getObject('inscritaO').value;
	var folio = '';
	var libro = '';
	var direccion = ''; 
	if(isInscripcion != '2') direccion = getObject('ubicacionInO').value;
	// seccion persona fisica
	var nombre = getObject('nombreO').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcO').value;
	var nit = getObject('nitO').value;
	var nitF = getObject('nitOF').value;
	var extendida = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correo = getObject('correoElectronicoO').value;
	// seccion direccion
	var residencia = 1;
	var domicilio = getObject('domicilioUnoO').value;
	
	//Valida codigo malicioso
	var cadenaStr = idTramite + idPersona + idPersonaModificar + isInscripcion + nombre + apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona 
				+ nit + nitF
				+ inscrita + folio + libro + direccion + rfc + extendida + edad + estadoCivil + profesion + telefono + correo + residencia + domicilio;

	if (cadenaStr.indexOf('<script') >= 0) {
		alertMaterialize('no puedes escribir codigo malicioso');
		return false;
	}
	
	elementID = elementID;
	
	if (tipoPersona == 'PM') {
		//validaciones
		if (!noVacio(nit)) {
			alertMaterialize('El NIT es obligatorio');
			return false;
		}
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Razon Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if (isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del Representante es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.modificaParteOtorgante(elementID, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre,
				apellidoP, apellidoM, rfc, razonSocial, nacionalidad,
				inscrita, folio, libro, direccion, nitF, edad, 
				estadoCivil, profesion, telefono, correo, residencia,
				domicilio, tipoPersona, isMultiple, showParteOtorgante);

	} else {
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.modificaParteOtorgante(elementID, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre,
				apellidoP, apellidoM, rfc, razonSocial, nacionalidad,
				inscrita, folio, libro, direccion, nitF, edad, 
				estadoCivil, profesion, telefono, correo, residencia,
				domicilio, tipoPersona, isMultiple, showParteOtorgante);

	}
}

function recargaControlGrupo() {
	window.location.href = "Rug/controlusuario/iniciaAltaUsuario.do";
}

function firmaAcreedorRepresentado(elementId, idPersona, idAcreedor, idTramite) {
	window.location.href = "/Rug/firma/firmaAcreedores.do?idTramite="
			+ idTramite;
}
function controlUsuarioJS(idAcreedor) {
	window.location.href = "/Rug/controlusuario/setAcreedor.do?idAcreedor=" + idAcreedor;
}

function cambiaNacionalidadD() {	
	
	var nacionalidad = getObject('nacionalidadD').value;
	var tipoPersona = getObject('tipoPersonaD').value;
	
	if(tipoPersona == 'PF'){	
		if(nacionalidad == '1') {
			document.getElementById('labelRfcD').innerHTML = 'Documento de Identificaci&oacute;n';
			Materialize.updateTextFields();
		} else {
			document.getElementById('labelRfcD').innerHTML = 'Pasaporte';
			Materialize.updateTextFields();
		}
	} else {
		if(nacionalidad == '1') {
			document.getElementById('labelNitD').innerHTML = 'NIT';		
			Materialize.updateTextFields();
		} else {
			document.getElementById('labelNitD').innerHTML = 'N&uacute;mero de Registro Tributario';
			Materialize.updateTextFields();
		}
	}
}

function editarFolioElectronico() {
	ocultaButtonValidar();
	desbloqueaFolio();
	document.getElementById('resBusqueda').innerHTML = '<span class="textoGeneralRojo">  </span>';
}
function guardaParteAcreedor(elementID, idTramite, idPersona,
		idPersonaModificar, esAutoridadStr, isInscripcion) {

	//Generales
	var nacionalidad = getObject('nacionalidadA').value;
	var tipoPersona = getObject('tipoPersonaA').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialA').value;
	var inscrita = getObject('inscritaA').value;
	var folio = '';
	var libro = '';
	var direccion = '';
	if(isInscripcion != '2') direccion = getObject('ubicacionA').value;
	// seccion persona fisica
	var nombre = getObject('nombreA').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcA').value;
	var nit = getObject('nitA').value;
	var nitf = getObject('nitAF').value;
	var extencion = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correoElectronico = getObject('correoElectronicoA').value;
	// seccion direccion
	var idResidencia = 1;
	var domicilioUno = getObject('domicilioUnoA').value;

	var folioElectronico = '';
	var curpdoc = '';
	var idColonia = '1';
	var idLocalidad = '1';
	var calle = '';
	var numExterior = '';
	var numInterior = '';
	var codigoPostal = '';
	var domicilioDos = '';
	var poblacion = '';
	var zonaPostal = '';
	
	var nitfinal = '';

	elementIDACreedor = elementID;


	// <validacion Campos Obligatorios>

	if (!noVacio(correoElectronico)) {
		alertMaterialize('El campo Correo Electr\u00f3nico es obligatorio');
		return false;
	}
	if (!valEmail(correoElectronico)) {
		alertMaterialize('El campo Correo Electr\u00f3nico es incorrecto');
		return false;
	}

	if (tipoPersona == 'PM') {// Persona MORAL
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if(isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del Representante es obligatorio');
			return false;
		}
		if (!noVacio(nit)) {
			alertMaterialize('El campo NIT es obligatorio');
			return false;
		}
		nitfinal = nit;
		
	} else { // Persona FISICA
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}
		nitfinal = nitf;
	}

	// </validacion Campos Obligatorios>
	displayLoader(true);

	ParteDwrAction.guardaParteAcreedor(elementID, idTramite, idPersona,
			idPersonaModificar, isInscripcion, nombre, apellidoP, apellidoM, razonSocial,
			nacionalidad, tipoPersona, folioElectronico, idColonia,
			idLocalidad, calle, numExterior, numInterior, correoElectronico,
			telefono, nitfinal, curpdoc, domicilioUno, domicilioDos,
			poblacion, zonaPostal, idResidencia, inscrita, 
			folio, libro, direccion, edad, estadoCivil, profesion,
			rfc, showParteAcreedor);

}

function guardaParteAcreedorRepresentado(elementID, idPersona,
		idPersonaModificar, esAutoridadStr) {
	var nombre = getObject('nombreA').value;
	//var apellidoP = getObject('apellidoPaternoA').value;
	var apellidoP = ''; 
	//var apellidoM = getObject('apellidoMaternoA').value;
	var apellidoM = '';
	var razonSocial = getObject('razonSocialA').value;
	var nacionalidad = getObject('nacionalidadA').value;
	var tipoPersona = getObject('tipoPersonaA').value;
	//var folioElectronico = getObject('folioElectronicoA').value;
	var folioElectronico = '';
	var rfc = getObject('rfcA').value;
	//var idColonia = getObject('idColonia').value;
	var idColonia = '';
	//var idLocalidad = getObject('idLocalidad').value;
	var idLocalidad = '';
	//var calle = getObject('calle').value;
	var calle = '';
	//var numExterior = getObject('numExterior').value;
	var numExterior = '';
	//var curpdoc = getObject('docExtranjeroA').value;
	var curpdoc = '';
	//var numInterior = getObject('numInterior').value;
	var numInterior = '';
	var correoElectronico = getObject('correoElectronico').value;
	var telefono = getObject('telefono').value;
	var extencion = getObject('extencionA').value;
	//var codigoPostal = getObject('codigoPostal').value;
	var codigoPostal = '';
	var domicilioUno = getObject('domicilioUno').value;
	//var domicilioDos = getObject('domicilioDos').value;
	var domicilioDos = '';
	//var poblacion = getObject('poblacion').value;
	var poblacion = '';
	//var zonaPostal = getObject('zonaPostal').value;
	var zonaPostal = '';
	var residencia = getObject('residenciaA').value;
	//var tipoSociedad = getObject('TipoDA').value;
	var tipoSociedad = '';
	//var nif = getObject('nif').value;
	var nif = '';
	var acreedorInscribe;

	/**if (getObject('acreedorInscribe').checked) {
		acreedorInscribe = true;
	} else {
		acreedorInscribe = false;
	}*/
	acreedorInscribe = false;
	
	// Campos nuevos
	var inscrita = getObject('inscritaA').value;
	var folioInscrito = getObject('folioA').value;
	var libroInscrito = getObject('libroA').value;
	var ubicacionInscrito = getObject('ubicacionA').value;
	var edad = getObject('edadA').value;
	var estadoCivil = getObject('estadoCivilA').value;
	var profesion = getObject('profesionA').value;

	var correosAdicionales = new Array();

	for (var i = 0; i < 4; i++) {
		var correoAdicional = 'email' + (i + 1);
		if (getObject(correoAdicional) != null) {
			correosAdicionales[i] = getObject(correoAdicional).value;
			if (correosAdicionales[i] == ' ') {
				correosAdicionales[i] = '';
			}
		} else {
			correosAdicionales[i] = '';
		}
	}

	/*var cadenaStr = idPersona + idPersonaModificar + nombre + apellidoP
			+ apellidoM + razonSocial + nacionalidad + tipoPersona
			+ folioElectronico + rfc + idColonia + idLocalidad + calle
			+ numExterior + numInterior + correoElectronico + telefono
			+ extencion + curpdoc + domicilioUno + domicilioDos + poblacion
			+ zonaPostal + residencia;*/
	
	var cadenaStr = idPersona + idPersonaModificar + nombre + razonSocial 
			+ nacionalidad + tipoPersona + rfc + correoElectronico + telefono
			+ extencion + domicilioUno + residencia;

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
				alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Tel\u00E9fono es obligatorio');
				return false;
			}
			if (!noVacio(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es incorrecto');
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
			// ====================Validacion sociedad
			// mercantil=====================/
			/** no aplica 
			if (tipoSociedad == 'SM') {
				folioElectronico = getObject('folioElectronico').value;

				if (getObject('acreedorInscribe').checked) {
					if (!noVacio(folioElectronico)) {
						alert('El campo Folio Electr\u00f3nico es obligatorio, porque el acreedor Inscribe Garantias Tipo: Arrendamiento Financiero');
						displayLoader(false);
						return false;
					}
				}
				if (!noVacio(razonSocial)) {
					alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
					return false;
				}
				if (getObject('acreedorInscribe').checked) {
					if (!noVacio(folioElectronico)) {
						alert('Debe ingresar el n\u00famero de folio electr\u00f3nico del Acreedor. Sin este dato no es posible realizar una operaci\u00f3n respecto de dicho Acreedor.');
						return false;
					}
				}
			} else if (tipoSociedad == 'OT') {
				folioElectronico = getObject('folioExistenteAC').value;
				if (!noVacio(razonSocial)) {
					alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
					return false;
				}
			} else if (tipoSociedad == 'TI') {
				alert('Debe seleccionar en el campo "Tipo" el cual hace referencia a Sociedades Mercantiles, Sociedade Civiles Sindicatos, fideicomisos entre otros');
			}
			// ====================Validacion sociedad
			// mercantil=====================
			*/
		} else {
			// Persona Moral Extrangera
			/**
			if (getObject('acreedorInscribe').checked) {
				if (!noVacio(nif)) {
					alert('El campo N\u00famero de Identificaci\u00f3n Fiscal en el pa\u00eds de origen es obligatorio si al acreedor inscribe garantias de tipo arrendamineto financiero');
					return false;
				}
			}**/
			if (!noVacio(razonSocial)) {
				alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Tel\u00E9fono es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es incorrecto');
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
				alert('El campo Correo Electr\u00f3nico es obligatorio');
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
					/** No aplica
					if (!validaRfc(rfcStr)) {
						return false;
					} */
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
			folioElectronico = getObject('folioExistenteAC').value;

			/** no aplica
			if (getObject('acreedorInscribe').checked) {
				if (!noVacio(curpdoc)) {
					alert('El campo CURP es obligatorio, porque el acreedor Inscribe Garantias Tipo: Arrendamiento Financiero');
					displayLoader(false);
					return false;
				}
			}**/
			if (!esAutoridad) {
				if (rfc.length > 0) {
					rfcStr = rfc;
					/** no aplica
					if (!validaRfc(rfcStr)) {
						return false;
					} */
				} else {
					alert('El campo Documento de Identificación es obligatorio');
					return false;
				}
			}
		} else {
			folioElectronico = getObject('folioExistenteAC').value;
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
					/* no aplica
					 if (!validaRfc(rfcStr)) {
						return false;
					} */
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
		/*if (!noVacio(apellidoP)) {
			alert('El campo Apellido paterno es obligatorio');
			return false;
		}*/
		if (!noVacio(correoElectronico)) {
			alert('El campo Correo Electr\u00f3nico es obligatorio');
			return false;
		}
		if (!valEmail(correoElectronico)) {
			alert('El campo Correo Electr\u00f3nico es incorrecto');
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
			alert('El campo Tel\u00E9fono es obligatorio');
			return false;
		}
	}
	// Domicilios Verificacion
	/** no aplica
	if (residencia == '1') {
		if (!noVacio(codigoPostal)) {
			alert('El campo C\u00f3digo Postal es obligatorio');
			return false;
		}
		if (getObject('idLocalidad').value == -1
				&& getObject('idColonia').value == -1) {
			alert('Seleccione una Colonia o Localidad');
			return false;
		}
		if (!noVacio(calle)) {
			alert('El campo Calle es obligatorio');
			return false;

		}
		if (!noVacio(numExterior)) {
			alert('El campo N\u00famero Exterior es obligatorio');
			return false;
		}
	} else {
		// Extrangero Domicilio
		if (!noVacio(domicilioUno)) {
			alert('El domicilio Uno es obligatorio');
			return false;
		}
	}*/
	if (!noVacio(domicilioUno)) {
		alert('El domicilio Uno es obligatorio');
		return false;
	}
	
	for (var i = 0; i < correosAdicionales.length; i++) {
		if (correosAdicionales[i] != '') {
			correoElectronico = correoElectronico + ',' + correosAdicionales[i];
		}
	}
	displayLoader(true);

	// if (getObject('acreedorInscribe').checked) {
	// ParteDwrAction.guardaParteAcreedorRepresentado(elementID, idPersona,
	// idPersonaModificar, nombre, apellidoP, apellidoM,
	// razonSocial, nacionalidad, tipoPersona, folioElectronico,
	// rfc, idColonia, idLocalidad, calle, numExterior,
	// numInterior, correoElectronico, telefono, extencion,
	// curpdoc, domicilioUno,domicilioDos, poblacion, zonaPostal,
	// residencia, tipoSociedad, acreedorInscribe, nif, showNuevoAcreedorRep);
	// } else {
	ParteDwrAction.guardaParteAcreedorRepresentadoSinMensaje(elementID,
			idPersona, idPersonaModificar, nombre, apellidoP, apellidoM,
			razonSocial, nacionalidad, tipoPersona, folioElectronico, rfc,
			idColonia, idLocalidad, calle, numExterior, numInterior,
			correoElectronico, telefono, extencion, curpdoc, domicilioUno,
			domicilioDos, poblacion, zonaPostal, residencia, tipoSociedad,
			acreedorInscribe, nif, inscrita, folioInscrito, libroInscrito,
			ubicacionInscrito, edad, estadoCivil, profesion, showNuevoAcreedorRep);
	// }
}

function ocultaButtonValidar() {
	showObject('buttonValidar', false);
}
function muestraButtonValidar() {
	showObject('buttonValidar', true);
}
function desactivaBoton() {
	document.getElementById("butonIDAceptar").disabled = true;
}
function activarBoton() {
	document.getElementById("butonIDAceptar").disabled = false;

}
function cambiaFolioElectronico() {
	if (getObject('optotorgante').checked) {
		showObject('folioIDExt', true);
		showObject('folioIDNoExt', false);
		showObject('buttonValidar', true);
	} else {
		showObject('folioIDNoExt', true);
		showObject('folioIDExt', false);
		showObject('buttonValidar', false);
	}

}

//
function cambiaFolioElectronicoAcreedor() {
	if (getObject('optotorganteAC').checked) {
		showObject('folioIDExtAC', true);
		showObject('folioIDNoExtAC', false);
		showObject('buttonValidar', true);
	} else {
		showObject('folioIDNoExtAC', true);
		showObject('folioIDExtAC', false);
		showObject('buttonValidar', false);
	}
}

function cambiaInscribe() {
	if (getObject('acreedorInscribe').checked) {
		document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;* CURP :  </span>';
		document.getElementById('tipoObliga').innerHTML = '<span class="textoGeneralRojo">&nbsp;*</span>';
		document.getElementById('folioxxx').innerHTML = '<span class="textoGeneralRojo">&nbsp;*</span>';
	} else {
		document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
		document.getElementById('tipoObliga').innerHTML = '<span class="textoGeneralRojo">&nbsp;</span>';
		document.getElementById('folioxxx').innerHTML = '<span class="textoGeneralRojo">&nbsp;</span>';
	}
}

function cambiaFolioElectronicoAcreedorIns() {
	if (getObject('optotorganteAC').checked) {
		showObject('folioIDExtAC', true);
		showObject('folioIDNoExtAC', false);
		showObject('buttonValidar', true);
	} else {
		showObject('folioIDNoExtAC', true);
		showObject('folioIDExtAC', false);
		showObject('buttonValidar', false);
	}
}
//

function seleccionaFocus(id) {
	getObject(id).focus();
	getObject(id).select();

}

function buscaFolioElectronico(elementID, idTramite, idPersona, isInscripcion,
		isMultiple) {

	var tipoPersona = getObject('tipoPersona').value;
	var folio = getObject('folioExistente').value;
	if (!noVacio(folio)) {
		alert('Debe ingresar el n\u00famero de Folio Electr\u00f3nico del Otorgante. Sin este dato no es posible realizar una operaci\u00f3n respecto de dicho Otorgante.');
		seleccionaFocus('folioExistente');
		return false;
	}
	elementResBusqueda = elementID;
	if (tipoPersona == 'PM') {
		ParteDwrAction.buscaFolioElectronicoMoral(folio, idTramite, idPersona,
				isInscripcion, isMultiple, showResultadoBusqueda);
		return false;
	} else {
		displayLoader(true);
		ParteDwrAction.buscaFolioElectronicoFisica(folio, idTramite, idPersona,
				isInscripcion, isMultiple, showResultadoBusqueda);
	}
}

// Nuevo metodo validacion de folio electronico

function buscaFolioElectronicoAltaAcreedor() {

	var tipo = getObject('tipoPersonaA').value;
	var folio = getObject('folioExistenteAC').value;

	if (!noVacio(folio)) {
		alert('Debe ingresar el n\u00famero de Folio Electr\u00f3nico del Otorgante. Sin este dato no es posible realizar una operaci\u00f3n respecto de dicho Otorgante.');
		seleccionaFocus('folioExistenteAC');
		return false;
	}

	if (tipo == "PM") {

		ParteDwrAction.buscaPersonaByFolioElectronico(folio, tipo,
				resultadoBuscaFolioAcreedor);

	} else {
		ParteDwrAction.buscaPersonaByFolioElectronico(folio, tipo,
				resultadoBuscaFolioAcreedor);
	}
}

function resultadoBuscaFolioAcreedor(mensaje) {
	if (mensaje.codeError == 0) {// todo OK
		var persona = JSON.parse(mensaje.message);
		if (persona.perJuridica == 'PF') {
			getObject('nombreA').value = persona.nombre;
			getObject('apellidoPaternoA').value = persona.apaterno;
			getObject('apellidoMaternoA').value = persona.amaterno;
			getObject('docExtranjeroA').value = persona.curp;
		} else if (persona.perJuridica == 'PM') {
			getObject('razonSocialA').value = persona.razonSocial;
			getObject('folioExistenteAC').value = persona.folioMercantil;
		}

	} else {// ERROR no encontro
		alert('Error: Folio Electronico no encontrado.');
	}
}

function buscaDocumentoDeudor() {
	var tipo = getObject('tipoPersonaD').value;
	var id = '';
	
	if(tipo=='PM') {
		id = getObject('nitD').value;
	} else {
		id = getObject('rfcD').value;
	}
		
	if(!noVacio(id)){	
		alertMaterialize('Debe ingresar el Documento de Identificaci&oacute;n. Sin este dato no es posible validarlo.');				
		return false;
	}

	
	MaterialDialog.alert(
		'<p style="text-align: justify; text-justify: inter-word;">Recuerde: debe verificar ' +
		'que los datos encontrados sean los correctos. </p>',
		{
			title:'Busqueda', // Modal title
			buttons:{ // Receive buttons (Alert only use close buttons)
				close:{
					text:'aceptar', //Text of close button
					className:'green', // Class of the close button
					callback:function(){ // Function for modal click
						ParteDwrAction.buscaPersonaByFolioElectronico(id, tipo,
								resultadoBuscaDocumentoDeudor);
					}
				}
			}
		}
	);	
	
	
}

function buscaDocumentoAcreedor() {
	var tipo = getObject('tipoPersonaA').value;
	var id = '';
	
	if(tipo=='PM') {
		id = getObject('nitA').value;
	} else {
		id = getObject('rfcA').value;
	}
		
	if(!noVacio(id)){	
		alertMaterialize('Debe ingresar el Documento de Identificaci&oacute;n. Sin este dato no es posible validarlo.');		
		return false;
	}
	
	
	MaterialDialog.alert(
		'<p style="text-align: justify; text-justify: inter-word;">Recuerde: debe verificar ' +
		'que los datos encontrados sean los correctos. </p>',
		{
			title:'Busqueda', // Modal title
			buttons:{ // Receive buttons (Alert only use close buttons)
				close:{
					text:'aceptar', //Text of close button
					className:'green', // Class of the close button
					callback:function(){ // Function for modal click
						ParteDwrAction.buscaPersonaByFolioElectronico(id, tipo,
							resultadoBuscaDocumentoAcreedor);
					}
				}
			}
		}
	);	

}

function buscaDocumentoOtorgante() {
	var tipo = getObject('tipoPersona').value;
	var id = '';
	
	if(tipo=='PM') {
		id = getObject('nitO').value;
	} else {
		id = getObject('rfcO').value;
	}
		
	if(!noVacio(id)){	
		alertMaterialize('Debe ingresar el Documento de Identificaci&oacute;n. Sin este dato no es posible validarlo.');		
		return false;
	}
	MaterialDialog.alert(
		'<p style="text-align: justify; text-justify: inter-word;">Recuerde: debe verificar ' +
		'que los datos encontrados sean los correctos. </p>',
		{
			title:'Busqueda', // Modal title
			buttons:{ // Receive buttons (Alert only use close buttons)
				close:{
					text:'aceptar', //Text of close button
					className:'green', // Class of the close button
					callback:function(){ // Function for modal click
						ParteDwrAction.buscaPersonaByFolioElectronico(id, tipo,
							resultadoBuscaDocumentoOtorgante);
					}
				}
			}
		}
	);
	
}

function buscaFolioElectronicoAltaAcreedorRepresentado() {

	var tipo = getObject('tipoPersonaA').value;
	var folio = getObject('folioExistenteAC').value;
	if (!noVacio(folio)) {
		alert('Debe ingresar el n\u00famero de Folio Electr\u00f3nico del Otorgante. Sin este dato no es posible realizar una operaci\u00f3n respecto de dicho Otorgante.');
		seleccionaFocus('folioExistenteAC');
		return false;
	}

	if (tipo == "PM") {
		ParteDwrAction.buscaPersonaByFolioElectronico(folio, tipo,
				resultadoBuscaFolioAcreedorRepre);
	} else {
		ParteDwrAction.buscaPersonaByFolioElectronico(folio, tipo,
				resultadoBuscaFolioAcreedorRepre);
	}
}

function resultadoBuscaDocumentoDeudor(mensaje){
	if (mensaje.codeError == 0) {// todo OK
		var persona = JSON.parse(mensaje.message);
		//alert(persona);
		
		getObject('tipoPersonaD').value = persona.perJuridica;
		
		if(persona.perJuridica == 'PF') {
			getObject('nombreD').value = persona.personaFisica.nombre;
			getObject('labelNombreD').setAttribute("class","active");
			getObject('nombreD').disabled = true;
			
			getObject('nacionalidadD').value = persona.personaFisica.idNacionalidad;
			
			getObject('nitDF').value = persona.personaFisica.rfc;
			getObject('labelNitDF').setAttribute("class","active");
			getObject('nitDF').disabled = true;
			
			getObject('correoElectronico').value = persona.personaFisica.correo;
			getObject('labelCorreo').setAttribute("class","active");
			getObject('correoElectronico').disabled = true;
			
			getObject('domicilioUno').value = persona.personaFisica.domicilio;
			getObject('labelDomicilio').setAttribute("class","active");
			getObject('domicilioUno').disabled = true;
		} else {
			getObject('razonSocialD').value = persona.personaMoral.razonSocial;
			getObject('labelRazonSocialD').setAttribute("class","active");
			getObject('razonSocialD').disabled = true;
			
			getObject('nacionalidadD').value = persona.personaMoral.idNacionalidad;
			
			getObject('inscritaD').value = persona.personaMoral.tipoSociedad;
			getObject('labelInscritaD').setAttribute("class","active");
			getObject('inscritaD').disabled = true;
			
			getObject('ubicacionInD').value = persona.personaMoral.representante;
			getObject('labelUbicacionInD').setAttribute("class","active");
			getObject('ubicacionInD').disabled = true;
			
			getObject('correoElectronico').value = persona.personaMoral.correo;
			getObject('labelCorreo').setAttribute("class","active");
			getObject('correoElectronico').disabled = true;
			
			getObject('domicilioUno').value = persona.personaMoral.domicilio;
			getObject('labelDomicilio').setAttribute("class","active");
			getObject('domicilioUno').disabled = true;
		}
				
	} else {// ERROR no encontro
		alertMaterialize('Documento de indentificaci&oacute;n no fue encontrado.');
	}
}

function alertMaterialize(mensaje){
	MaterialDialog.alert(
			mensaje, // Alert Body (Acepts html tags)
			{
				title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
				buttons:{ // Receive buttons (Alert only use close buttons)
					close:{
						text:'cerrar', //Text of close button
						className:'red' // Class of the close button								
					}
				}
			}
		);		
}

function resultadoBuscaDocumentoAcreedor(mensaje){
	if (mensaje.codeError == 0) {// todo OK
		var persona = JSON.parse(mensaje.message);
		
		getObject('tipoPersonaA').value = persona.perJuridica;
		
		if(persona.perJuridica == 'PF') {
			getObject('nombreA').value = persona.personaFisica.nombre;
			getObject('labelNombreA').setAttribute("class","active");
			getObject('nombreA').disabled = true;
			
			getObject('nacionalidadA').value = persona.personaFisica.idNacionalidad;
			
			getObject('nitAF').value = persona.personaFisica.rfc;
			getObject('labelNitAF').setAttribute("class","active");
			getObject('nitAF').disabled = true;
			
			getObject('correoElectronicoA').value = persona.personaFisica.correo;
			getObject('labelCorreoA').setAttribute("class","active");
			getObject('correoElectronicoA').disabled = true;
			
			getObject('domicilioUnoA').value = persona.personaFisica.domicilio;
			getObject('labelDomicilioA').setAttribute("class","active");
			getObject('domicilioUnoA').disabled = true;
		}  else {
			
			getObject('razonSocialA').value = persona.personaMoral.razonSocial;
			getObject('labelRazonSocialA').setAttribute("class","active");
			getObject('razonSocialA').disabled = true;
			
			getObject('nacionalidadD').value = persona.personaMoral.idNacionalidad;
			
			getObject('inscritaA').value = persona.personaMoral.tipoSociedad;
			getObject('labelInscritaA').setAttribute("class","active");
			getObject('inscritaA').disabled = true;
			
			getObject('ubicacionA').value = persona.personaMoral.representante;
			getObject('labelUbicacionA').setAttribute("class","active");
			getObject('ubicacionA').disabled = true;
			
			getObject('correoElectronicoA').value = persona.personaMoral.correo;
			getObject('labelCorreoA').setAttribute("class","active");
			getObject('correoElectronicoA').disabled = true;
			
			getObject('domicilioUnoA').value = persona.personaMoral.domicilio;
			getObject('labelDomicilioA').setAttribute("class","active");
			getObject('domicilioUnoA').disabled = true;
		}
		
	} else {// ERROR no encontro
		alertMaterialize('Documento de Identificaci&oacute;n no encontrado.');
	}
}

function resultadoBuscaDocumentoOtorgante(mensaje){
	if (mensaje.codeError == 0) {// todo OK
		var persona = JSON.parse(mensaje.message);
		
		getObject('tipoPersona').value = persona.perJuridica;
		
		if(persona.perJuridica == 'PF') {
			getObject('nombreO').value = persona.personaFisica.nombre;
			getObject('labelNombreO').setAttribute("class","active");
			getObject('nombreO').disabled = true;
			
			getObject('nacionalidad').value = persona.personaFisica.idNacionalidad;
			
			getObject('nitOF').value = persona.personaFisica.rfc;
			getObject('labelNitOF').setAttribute("class","active");
			getObject('nitOF').disabled = true;
			
			getObject('correoElectronicoO').value = persona.personaFisica.correo;
			getObject('labelCorreoO').setAttribute("class","active");
			getObject('correoElectronicoO').disabled = true;
			
			getObject('domicilioUnoO').value = persona.personaFisica.domicilio;
			getObject('labelDomicilioO').setAttribute("class","active");
			getObject('domicilioUnoO').disabled = true;
		}  else {
			
			getObject('razonSocialO').value = persona.personaMoral.razonSocial;
			getObject('labelRazonSocialO').setAttribute("class","active");
			getObject('razonSocialO').disabled = true;
			
			getObject('nacionalidad').value = persona.personaMoral.idNacionalidad;
			
			getObject('inscritaO').value = persona.personaMoral.tipoSociedad;
			getObject('labelInscritaO').setAttribute("class","active");
			getObject('inscritaO').disabled = true;
			
			getObject('ubicacionO').value = persona.personaMoral.representante;
			getObject('labelUbicacionO').setAttribute("class","active");
			getObject('ubicacionO').disabled = true;
			
			getObject('correoElectronicoO').value = persona.personaMoral.correo;
			getObject('labelCorreoO').setAttribute("class","active");
			getObject('correoElectronicoO').disabled = true;
			
			getObject('domicilioUnoO').value = persona.personaMoral.domicilio;
			getObject('labelDomicilioO').setAttribute("class","active");
			getObject('domicilioUnoO').disabled = true;
		}
		
	} else {// ERROR no encontro
		alertMaterialize('Documento de Identificaci&oacute;n no encontrado.');
	}
}

function resultadoBuscaFolioAcreedorRepre(mensaje) {
	if (mensaje.codeError == 0) {// todo OK
		var persona = JSON.parse(mensaje.message);
		alert(persona);

		if (persona.perJuridica == 'Persona Fisica') {
			
			/** no aplica
			if (persona.personaFisica.idNacionalidad == '1') {
				showObject('docExtranjeroTRAE', true);
				showObject('curpAyudaIDAcre', true);
				document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
				getObject('docExtranjeroA').value = persona.personaFisica.curp;
				getObject('docExtranjeroA').disabled = true;
			}*/

			getObject('nombreA').value = persona.personaFisica.nombre;
			getObject('nombreA').disabled = true;
			
			/*getObject('apellidoPaternoA').value = persona.personaFisica.apaterno;
			getObject('apellidoPaternoA').disabled = true;
			getObject('apellidoMaternoA').value = persona.personaFisica.amaterno;
			getObject('apellidoMaternoA').disabled = true;*/

			getObject('tipoPersonaA').value = 'PF';
			getObject('nacionalidadA').value = persona.personaFisica.idNacionalidad;

			//showObject('personaTipoSociedadAC', false);
			//showObject('folioElectronicoTRAC', false);
			showObject('trtdoptogantefisicoAcreedores', true);
			showObject('personaFisicaTRA', true);
			document.getElementById('optotorganteAC').checked = false;
			showObject('folioIDNoExtAC', true);
			showObject('folioIDExtAC', false);
			showObject('buttonValidar', false);
			//showObject('docExtranjeroTRAE', true);

			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>';
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* Documento de Identificación  :  </span>';
			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>';

		} else if (persona.perJuridica == 'PM') {

			//showObject('folioElectronicoTRAC', false);

			//showObject('curpAyudaIDAcre', false);
			//showObject('docExtranjeroTRAE', false);

			showObject('folioIDNoExtAC', true);
			showObject('folioIDExtAC', false);
			showObject('buttonValidar', false);
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> * Documento de Identificación  :  </span>';

			/** no aplica
			if (persona.personaMoral.idNacionalidad == 1) {
				showObject('personaTipoSociedadAC', true);
			} else {
				document.getElementById('optotorganteAC').checked = false;
				showObject('trtdoptogantefisicoAcreedores', true);
			}

			if (persona.personaMoral.tipoSociedad == 'SM') {// Tipo de
															// organizacion
															// (Social
															// Mercantil)
				document.getElementById('trtdoptogantefisicoAcreedores').checked = false;
				showObject('folioElectronicoTRAC', true); // Este lo
															// modificamos
															// 11/08/2014
				showObject('trtdoptogantefisicoAcreedores', false);
				document.getElementById('TipoDA').value = 'SM';
			} else if (persona.personaMoral.tipoSociedad == 'OT') {// Tipo de
																	// organizacion
																	// (Otro)
				showObject('folioElectronicoTRAC', false);
				showObject('trtdoptogantefisicoAcreedores', true);
				document.getElementById('TipoDA').value = 'OT';
			}*/

			getObject('tipoPersonaA').value = 'PM';
			getObject('nacionalidadA').value = persona.personaMoral.idNacionalidad;
			//getObject('TipoDA').value = persona.personaMoral.tipoSociedad;
			getObject('razonSocialA').value = persona.personaMoral.razonSocial;// razonSocialA
			getObject('razonSocialA').disabled = true;
			getObject('folioExistenteAC').value = persona.personaMoral.folioMercantil;
			getObject('folioExistenteAC').disabled = true;
			getObject('rfcA').value = persona.personaMoral.rfc;
			getObject('rfcA').disabled = true;
		}

	} else {// ERROR no encontro
		alert('Error: Folio Electronico no encontrado.');
	}
}

// Nuevo metodo validacion de folio electronico

function modificaParteAcreedor(elementID, idTramite, idPersona,
		idPersonaModificar, isInscripcion) {

	//Generales
	var nacionalidad = getObject('nacionalidadA').value;
	var tipoPersona = getObject('tipoPersonaA').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialA').value;
	var inscrita = getObject('inscritaA').value;
	var folio = '';
	var libro = '';
	var direccion = '';
	if(isInscripcion != '2') direccion = getObject('ubicacionA').value;
	// seccion persona fisica
	var nombre = getObject('nombreA').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcA').value;
	var nit = getObject('nitA').value;
	var nitf = getObject('nitAF').value;
	var extencion = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correoElectronico = getObject('correoElectronicoA').value;
	// seccion direccion
	var idResidencia = 1;
	var domicilioUno = getObject('domicilioUnoA').value;

	var folioElectronico = '';
	var curpdoc = '';
	var idColonia = '1';
	var idLocalidad = '1';
	var calle = '';
	var numExterior = '';
	var numInterior = '';
	var codigoPostal = '';
	var domicilioDos = '';
	var poblacion = '';
	var zonaPostal = '';
	
	var nitfinal = '';
	
	elementIDACreedor = elementID;
	var valor = tipoPersona;
	var valor2 = nacionalidad;
	var cadenaStr = idTramite + idPersona + idPersonaModificar + nombre
			+ apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona
			+ folioElectronico + rfc + idColonia + idLocalidad + calle
			+ numExterior + numInterior + correoElectronico + telefono
			+ extencion + curpdoc + edad + estadoCivil + profesion + inscrita
			+ folio + libro + direccion + nit + nitf;

	if (cadenaStr.indexOf('<script') >= 0) {
		alertMaterialize('no puedes escribir codigo malicioso');
		return false;
	}
	
	if (!noVacio(correoElectronico)) {
		alertMaterialize('El campo Correo Electr\u00f3nico es obligatorio');
		return false;
	}
	if (!valEmail(correoElectronico)) {
		alertMaterialize('El campo Correo Electr\u00f3nico es incorrecto');
		return false;
	}

	if (tipoPersona == 'PM') {// Persona MORAL
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if (isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del representante es obligatorio');
			return false;
		}
		if (!noVacio(nit)) {
			alertMaterialize('El campo NIT es obligatorio');
			return false;
		}
		nitfinal = nit;
				
	} else { // Persona FISICA
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}
		
		nitfinal = nitf;
	}

	displayLoader(true);
	ParteDwrAction.modificaParteAcreedor(elementID, idTramite, idPersona,
			idPersonaModificar, isInscripcion, nombre, apellidoP, apellidoM, razonSocial,
			nacionalidad, tipoPersona, folioElectronico, rfc, idColonia,
			idLocalidad, domicilioUno, numExterior, numInterior, correoElectronico,
			telefono, nitfinal, curpdoc, inscrita, folio, libro, direccion, 
			edad, estadoCivil, profesion, showParteAcreedor);

}

function espacioEnBlanco(q) {
	for (i = 0; i < q.length; i++) {
		if (q.charAt(i) != " ") {
			return true;
		}
	}
	return false;
}
function modificaParteAcreedorRepresentado(elementID, idPersona,
		idPersonaModificar, esAutoridadStr) {

	var nombre = getObject('nombreA').value;
	var apellidoP = getObject('apellidoPaternoA').value;
	var apellidoM = getObject('apellidoMaternoA').value;
	var razonSocial = getObject('razonSocialA').value;
	var nacionalidad = getObject('nacionalidadA').value;
	var tipoPersona = getObject('tipoPersonaA').value;
	var folioElectronico = getObject('folioExistenteAC').value;
	var rfc = getObject('rfcA').value;
	var idColonia = getObject('idColonia').value;
	var idLocalidad = getObject('idLocalidad').value;
	var calle = getObject('calle').value;
	var numExterior = getObject('numExterior').value;
	var numInterior = getObject('numInterior').value;
	var correoElectronico = getObject('correoElectronico').value;
	var telefono = getObject('telefono').value;
	var extencion = getObject('extencion').value;
	var codigoPostal = getObject('codigoPostal').value;
	var curpdoc = getObject('docExtranjeroA').value;
	var domicilioUno = getObject('domicilioUno').value;
	var domicilioDos = getObject('domicilioDos').value;
	var poblacion = getObject('poblacion').value;
	var zonaPostal = getObject('zonaPostal').value;
	var residencia = getObject('residenciaA').value;
	var tipo = getObject('TipoDA').value;
	var nif = getObject('nif').value;
	elementIDACreedor = elementID;
	var valor = tipoPersona;
	var valor2 = nacionalidad;

	var correosAdicionales = new Array();
	for (var i = 0; i < 4; i++) {
		var correoAdicional = 'email' + (i + 1);
		if (getObject(correoAdicional) != null) {
			correosAdicionales[i] = getObject(correoAdicional).value;
			if (correosAdicionales[i] == ' ') {
				correosAdicionales[i] = '';
			}
		} else {
			correosAdicionales[i] = '';
		}
	}

	var acreedorInscribe;

	if (getObject('acreedorInscribe').checked) {
		acreedorInscribe = true;
	} else {
		acreedorInscribe = false;
	}

	var cadenaStr = idPersona + idPersonaModificar + nombre + apellidoP
			+ apellidoM + razonSocial + nacionalidad + tipoPersona
			+ folioElectronico + rfc + idColonia + idLocalidad + calle
			+ numExterior + numInterior + correoElectronico + telefono
			+ extencion + curpdoc + domicilioUno + domicilioDos + poblacion
			+ zonaPostal + residencia;

	if (cadenaStr.indexOf('<script') >= 0) {
		alert('no puedes escribir codigo malicioso');
		return false;
	}
	var esAutoridad = false;
	if (esAutoridadStr == '1') {
		esAutoridad = true;
	}
	if (valor == 'PM') {
		if (valor2 == '1') {
			if (!noVacio(razonSocial)) {
				alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Tel\u00E9fono es obligatorio');
				return false;
			}
			if (!noVacio(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es incorrecto');
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
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}
			} else {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}
			}

			if (residencia == '1') {
				if (!noVacio(codigoPostal)) {
					alert('El campo Codigo Postal es obligatorio');
					return false;
				}
				if (!noVacio(calle)) {
					alert('El campo Calle es obligatorio');
					return false;
				}
				if (!noVacio(numExterior)) {
					alert('El campo N\u00famero Exterior es obligatorio');
					return false;
				}
			}

			if (tipo == 'SM') {
				folioElectronico = getObject('folioElectronico').value;

				if (!noVacio(folioElectronico)) {
					alert('El campo Folio Electronico es obligatorio');
					return false;
				}
			}

		} else {

			if (getObject('acreedorInscribe').checked) {
				if (!noVacio(nif)) {
					alert('El campo N\u00famero de Identificaci\u00f3n Fiscal en el pa\u00eds de origen es obligatorio');
					return false;
				}
			}

			if (!noVacio(razonSocial)) {
				alert('El campo Denominaci\u00f3n o Raz\u00f3n Social es obligatorio');
				return false;
			}
			if (!noVacio(telefono)) {
				alert('El campo Tel\u00E9fono es obligatorio');
				return false;
			}
			if (!noVacio(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es obligatorio');
				return false;
			}
			if (!valEmail(correoElectronico)) {
				alert('El campo Correo Electr\u00f3nico es incorrecto');
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
				}
			} else {
				if (rfc.length > 0) {
					rfcStr = 'a' + rfc;
					if (!validaRfc(rfcStr)) {
						return false;
					}
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}
			}
		}
	} else {

		if (!noVacio(nombre)) {
			alert('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(apellidoP)) {
			alert('El campo Apellido Paterno es obligatorio');
			return false;
		}

		if (valor2 == '1') {
			// if (!noVacio(apellidoM)){
			// alert('El campo Apellido Materno es obligatorio');
			// return false;
			// }

			if (getObject('acreedorInscribe').checked) {
				if (!noVacio(curpdoc)) {
					alert('El campo CURP es obligatorio, porque el acreedor Inscribe Garantias Tipo: Arrendamiento Financiero');
					displayLoader(false);
					return false;
				}
			}

			if (esAutoridad) {
				if (rfc.length > 0) {
					if (!validaRfc(rfc)) {
						return false;
					}
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}

			} else {
				if (rfc.length > 0) {
					if (!validaRfc(rfc)) {
						return false;
					}
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}
			}
			if (residencia == '1') {
				if (!noVacio(codigoPostal)) {
					alert('El campo Codigo Postal es obligatorio');
					return false;
				}
				if (!noVacio(calle)) {
					alert('El campo Calle es obligatorio');
					return false;
				}
				if (!noVacio(numExterior)) {
					alert('El campo N\u00famero Exterior es obligatorio');
					return false;
				}
			}

		} else {
			if (esAutoridad) {
				if (rfc.length > 0) {
					if (!validaRfc(rfc)) {
						return false;
					}
				}
			} else {
				if (rfc.length > 0) {
					if (!validaRfc(rfc)) {
						return false;
					}
				} else {
					alert('El campo RFC es obligatorio');
					return false;
				}
			}

		}
		if (!noVacio(correoElectronico)) {
			alert('El campo Correo Electr\u00f3nico es obligatorio');
			return false;
		}
		if (!valEmail(correoElectronico)) {
			alert('El campo Correo Electr\u00f3nico es incorrecto');
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
			alert('El campo Tel\u00E9fono es obligatorio');
			return false;
		}

	}

	for (var i = 0; i < correosAdicionales.length; i++) {
		if (correosAdicionales[i] != '') {
			correoElectronico = correoElectronico + ',' + correosAdicionales[i];
		}
	}

	displayLoader(true);
	ParteDwrAction.modificaParteAcreedorRepresentado(elementID, idPersona,
			idPersonaModificar, nombre, apellidoP, apellidoM, razonSocial,
			nacionalidad, tipoPersona, folioElectronico, rfc, idColonia,
			idLocalidad, calle, numExterior, numInterior, correoElectronico,
			telefono, extencion, curpdoc, domicilioUno, domicilioDos,
			poblacion, zonaPostal, residencia, tipo, acreedorInscribe, nif,
			showParteAcreedorRepresentado);

}
function guardaParteDeudor(elementID, idTramite, idPersona, idPersonaModificar,	isInscripcion) {

	//Generales
	var nacionalidad = getObject('nacionalidadD').value;
	var tipoPersona = getObject('tipoPersonaD').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialD').value;
	var inscrita = getObject('inscritaD').value;
	var folio = '';
	var libro = '';
	var direccion = '';
	if(isInscripcion != '2') direccion = getObject('ubicacionInD').value;
	// seccion persona fisica
	var nombre = getObject('nombreD').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcD').value;
	var nit = getObject('nitD').value;
	var nitF = getObject('nitDF').value;
	var extendida = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correo = getObject('correoElectronico').value;
	// seccion direccion
	var residencia = 1;
	var domicilio = getObject('domicilioUno').value;

	var valor = document.getElementById('tipoPersonaD').value;
	var valor2 = document.getElementById('nacionalidadD').value;

	//Valida codigo malicioso
	var cadenaStr = idTramite + idPersona + idPersonaModificar + isInscripcion + nitF 
				nombre + apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona +
				inscrita + folio + libro + direccion + rfc + extendida + edad + nit +
				estadoCivil + profesion + telefono + correo + residencia + domicilio;

	if (cadenaStr.indexOf('<script') >= 0) {
		alertMaterialize('no puedes escribir codigo malicioso');
		return false;
	}

	elementIDDeudor = elementID;

	if (valor == 'PM') {
		//validaciones
		if (!noVacio(nit)) {
			alertMaterialize('El campo NIT es obligatorio');
			return false;
		}
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Razon Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if (isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del Representante es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.guardaParteDeudor(elementIDDeudor, idTramite, idPersona,
				idPersonaModificar, isInscripcion, nombre, apellidoP,
				apellidoM, razonSocial, rfc, nacionalidad, inscrita, 
				folio, libro, direccion, nit, edad, estadoCivil, profesion, 
				telefono, correo, residencia, domicilio, tipoPersona,
				showParteDeudor);

	} else {
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}		
				
		displayLoader(true);
		ParteDwrAction.guardaParteDeudor(elementIDDeudor, idTramite, idPersona,
				idPersonaModificar, isInscripcion, nombre, apellidoP,
				apellidoM, razonSocial, rfc, nacionalidad, inscrita, 
				folio, libro, direccion, nitF, edad, estadoCivil, profesion, 
				telefono, correo, residencia, domicilio, tipoPersona,
				showParteDeudor);

	}

}

function modificaParteDeudor(elementID, idTramite, idPersona,
		idPersonaModificar, isInscripcion) {

	//Generales
	var nacionalidad = getObject('nacionalidadD').value;
	var tipoPersona = getObject('tipoPersonaD').value;
	//seccion persona juridica
	var razonSocial = getObject('razonSocialD').value;
	var inscrita = getObject('inscritaD').value;
	var folio = '';
	var libro = '';
	var direccion = ''; 
	if(isInscripcion != '2') direccion = getObject('ubicacionInD').value;
	// seccion persona fisica
	var nombre = getObject('nombreD').value;
	var apellidoP = ''; //no aplica
	var apellidoM = ''; //no aplica
	var rfc = getObject('rfcD').value;
	var nit = getObject('nitD').value;
	var nitF = getObject('nitDF').value;
	var extendida = '';
	var edad = '';
	var estadoCivil = '';
	var profesion = '';
	// seccion contacto
	var telefono = '';
	var correo = getObject('correoElectronico').value;
	// seccion direccion
	var residencia = 1;
	var domicilio = getObject('domicilioUno').value;
	
	//Valida codigo malicioso
	var cadenaStr = idTramite + idPersona + idPersonaModificar + isInscripcion + nombre + apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona 
				+ nit + nitF
				+ inscrita + folio + libro + direccion + rfc + extendida + edad + estadoCivil + profesion + telefono + correo + residencia + domicilio;

	if (cadenaStr.indexOf('<script') >= 0) {
		alertMaterialize('no puedes escribir codigo malicioso');
		return false;
	}
	elementIDDeudor = elementID;
	console.log('typeOf Person', tipoPersona);
	if (tipoPersona == 'PM') {
		//validaciones
		if (!noVacio(nit)) {
			alertMaterialize('El NIT es obligatorio');
			return false;
		}
		if (!noVacio(razonSocial)) {
			alertMaterialize('El campo Razon Social es obligatorio');
			return false;
		}
		if (!noVacio(inscrita)) {
			alertMaterialize('El campo Inscrito bajo el número es obligatorio');
			return false;
		}
		if (isInscripcion != '2' && !noVacio(direccion)) {
			alertMaterialize('El campo Datos del Representante es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.modificaParteDeudor(elementIDDeudor, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre,
				apellidoP, apellidoM,rfc, razonSocial, nacionalidad,
				inscrita, folio, libro, direccion, nit, edad, 
				estadoCivil, profesion, telefono, correo, residencia, 
				domicilio, tipoPersona, showParteDeudorModificar);

	} else {
		if (!noVacio(nombre)) {
			alertMaterialize('El campo Nombre es obligatorio');
			return false;
		}
		if (!noVacio(rfc)) {
			alertMaterialize('El campo Documento de identificación es obligatorio');
			return false;
		}
		
		displayLoader(true);
		ParteDwrAction.modificaParteDeudor(elementIDDeudor, idTramite,
				idPersona, idPersonaModificar, isInscripcion, nombre,
				apellidoP, apellidoM, rfc, razonSocial, nacionalidad,
				inscrita, folio, libro, direccion, nitF, edad, 
				estadoCivil, profesion, telefono, correo, residencia,
				domicilio, tipoPersona, showParteDeudorModificar);

	}

}

function showParteOtorgante(message) {

	if (message.codeError == 0) {

		fillObject(elementID, message.message);
		$('select').material_select();
		Materialize.updateTextFields();
		ParceJS(message.message);
	}
	displayLoader(false);
	
}

function showParteOtorganteCurp(message) {

	if (message.codeError == 0) {
		ParceJS(message.message);
	}
	displayLoader(false);

}

function showResultadoBusqueda(message) {
	if (message.codeError == 0) {
		fillObject(elementResBusqueda, message.message);
		ParceJS(message.message);
	}
	displayLoader(false);

}

function showParteComerciante(message) {
	if (message.codeError == 0) {
		fillObject(elementID, message.message);
		ParceJS(message.message);
	}
	displayLoader(false);
}

function showParteDeudor(message) {
	if (message.codeError == 0) {
		fillObject(elementIDDeudor, message.message);
		ParceJS(message.message);

	}
	displayLoader(false);

}

function showParteBienes(message) {
	if (message.codeError == 0) {
		fillObject(elementIDBienes, message.message);		
		ParceJS(message.message);

	}
	displayLoader(false);
}

function showParteDeudorModificar(message) {
	if (message.codeError == 0) {
		fillObject(elementIDDeudor, message.message);
		ParceJS(message.message);

	}
	displayLoader(false);

}

function showNuevoAcreedorRep(message) {
	if (message.codeError == 0) {
		fillObject(elementIDACreedorRepresentado, message.message);
		cargaFormDrirecciones("cpTab");
		ParceJS(message.message);
	} else {
		fillObject(elementIDACreedorRepresentado, message.message);
		cargaFormDrirecciones("cpTab");
		ParceJS(message.message);
	}
	displayLoader(false);
}

function showParteAcreedorRepresentado(message) {

	if (message.codeError == 0) {
		fillObject(elementIDACreedorRepresentado, message.message);
		cargaFormDrirecciones("cpTab");
		ParceJS(message.message);
	} else {

		fillObject(elementIDACreedorRepresentado, message.message);
		cargaFormDrirecciones("cpTab");
		ParceJS(message.message);

		// alert('sucedio un error');
	}
	displayLoader(false);

}

function showParteBuscaCorreoUsuario(message) {

	if (message.codeError == 0) {
		fillObject(elementIDBusquedaEM, message.message);
		ParceJS(message.message);
	}
	displayLoader(false);

}

function showParteAcreedor(message) {

	if (message.codeError == 0) {
		fillObject(elementIDACreedor, message.message);		
		$('select').material_select();
		Materialize.updateTextFields();
		ParceJS(message.message);
	}

	displayLoader(false);

}
function cambiaTipoPersona() {
	var valor = document.getElementById('tipoPersona').value;
	var nacionalidad = document.getElementById('nacionalidad').value;
	
	if (valor == 'PM') {
		if(nacionalidad == '1') {
			document.getElementById('labelNitO').innerHTML = 'NIT';		
		} else {
			document.getElementById('labelNitO').innerHTML = 'N&uacute;mero de Registro Tributario';
		}
		document.getElementById('nombreO').value = '';		
		//document.getElementById('labelNombreA').innerHTML  = 'Representante Legal';
		//document.getElementById('apellidoPaternoD').value = '';
		//document.getElementById('apellidoMaternoD').value = '';
		showObject('personaMoralTR', true);
		showObject('personaFisicaTR', false);
	} else {
		if(nacionalidad == '1') {
			document.getElementById('labelRfcO').innerHTML = 'Documento de Identificaci&oacute;n';		
		} else {
			document.getElementById('labelRfcO').innerHTML = 'Pasaporte';
		}
//		document.getElementById('razonSocialO').value = '';
		document.getElementById('inscritaO').value = '';
		document.getElementById('ubicacionInO').value = '';
		//document.getElementById('folioA').value = '';
		//document.getElementById('libroA').value = '';
		//document.getElementById('ubicacionA').value = '';
		//document.getElementById('labelNombreA').innerHTML = 'Nombre Completo';
		showObject('personaFisicaTR', true);
		showObject('personaMoralTR', false);
	}
}

function cambiaTipoPersonaOld() {
	var valor = document.getElementById('tipoPersona').value;
	var valor2 = document.getElementById('nacionalidad').value;
	showObject('curpAyudaID', false);
	document.getElementById('docExtranjero').removeAttribute("onkeyup");
	document.getElementById('docExtranjero').removeAttribute("maxLength");
	document.getElementById('docExtranjero').setAttribute("maxLength", "50");
	if (valor == 'PM') {
		document.getElementById('nombre').value = '';
		document.getElementById('apellidoPaterno').value = '';
		document.getElementById('apellidoMaterno').value = '';
		document.getElementById('optotorgante').checked = false;
		showObject('folioIDNoExt', true);
		showObject('notaMoral', true);
		showObject('folioIDExt', false);
		showObject('buttonValidar', false);
		showObject('notaMoral', true);
		showObject('notaFisica', false);
		showObject('folioElectronicoTR', true);
		showObject('trtdoptogantefisico', false);
		showObject('personaMoralTR', true);
		showObject('personaFisicaTR', false);
		if (valor2 == '1') {
			document.getElementById('apellidoMaternoTxt').innerHTML = '<td style="padding-left: 11px;"><span class="textoGeneralRojo"> Apellido Materno : </span></td>';
			document.getElementById('curpDocO').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP : </span>';
			showObject('rfcID', false);
			showObject('docExtranjeroTR', false);
			// Mostrar el select de tipo organizacion
			// agregando los campos miguel
			showObject('personaTipoSociedad', true);
			// termina agregando los campos miguel
			showObject('folioElectronicoTR', false);
			showObject('personaMoralTR', false);
			// Mostrar el select de tipo organizacion
			// pinta RFC
			showObject('rfcID', false);
			// termina RFC
			// pinta NIF
			showObject('NIFpais', false);
			// termina NIF
			cambiaTipo();
		} else {
			showObject('personaFisicaTR', false);
			showObject('botonGuardar', true);
			showObject('buttonBuscarCurp', false);
			document.getElementById('apellidoMaternoTxt').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno : </span></td>';
			showObject('docExtranjeroTR', false);
			showObject('rfcID', true);
			showObject('personaTipoSociedad', false);
			showObject('folioElectronicoTR', false);
			showObject('personaMoralTR', true);
			showObject('NIFpais', true);
			showObject('trtdoptogantefisico', true);
			showObject('personaMoralTR', true);
		}
	} else {
		document.getElementById('razonSocial').value = '';
		showObject('notaFisica', true);
		showObject('trtdoptogantefisico', true);
		showObject('notaMoral', false);
		showObject('folioElectronicoTR', false);
		if (valor2 == '1') {
			document.getElementById('optotorgante').checked = false;
			showObject('folioIDNoExt', true);
			showObject('folioIDExt', false);
			showObject('buttonValidar', false);
			// Ocultar Los campos no necesarios
			reset_options();
			// agregando los campos miguel
			showObject('personaTipoSociedad', false);
			// termina agregando los campos miguel
			// pinta NIF
			showObject('NIFpais', false);
			// termina NIF
			// Es mexicana
			document.getElementById('docExtranjero').setAttribute("onkeyup",
					"this.value = this.value.toUpperCase()");
			document.getElementById('docExtranjero').removeAttribute(
					"maxLength");
			document.getElementById('docExtranjero').setAttribute("maxLength",
					"18");
			showObject('curpAyudaID', true);
			showObject('personaFisicaTR', true);
			showObject('botonGuardar', true);
			// document.getElementById('apellidoMaternoTxt').innerHTML = '<td
			// style="padding-left: 11px;"><span class="textoGeneralRojo">
			// Apellido Materno : </span></td>';
			showObject('docExtranjeroTR', true);
			showObject('rfcID', true);
			document.getElementById('curpDocO').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP : </span>';
		} else {
			showObject('personaFisicaTR', true);
			showObject('personaTipoSociedad', false);
			showObject('NIFpais', false);
			showObject('buttonValidar', false);
			showObject('botonGuardar', true);
			showObject('buttonBuscarCurp', false);
			showObject('rfcID', false);
			showObject('docExtranjeroTR', false);
			showObject('curpAyudaID', false);
			document.getElementById('apellidoMaternoTxt').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno : </span></td>';
		}
		showObject('personaMoralTR', false);
	}
}

function cambiaTipoPersonaD() {

	var valor = document.getElementById('tipoPersonaD').value;
	var nacionalidad = document.getElementById('nacionalidadD').value;
	
	if (valor == 'PM') {
		if(nacionalidad == '1') {
			document.getElementById('labelNitD').innerHTML = 'NIT';		
		} else {
			document.getElementById('labelNitD').innerHTML = 'N&uacute;mero de Registro Tributario';
		}
		document.getElementById('nombreD').value = '';
		//document.getElementById('labelNombreD').innerHTML  = 'Representante Legal';
		//document.getElementById('apellidoPaternoD').value = '';
		//document.getElementById('apellidoMaternoD').value = '';
		showObject('personMoralTRD', true);
		showObject('personaFisicaTRD', false);
	} else {
		if(nacionalidad == '1') {
			document.getElementById('labelRfcD').innerHTML = 'Documento de Identificaci&oacute;n';		
		} else {
			document.getElementById('labelRfcD').innerHTML = 'Pasaporte';
		}
		document.getElementById('razonSocialD').value = '';
		document.getElementById('inscritaD').value = '';		
		//document.getElementById('folioInD').value = '';
		//document.getElementById('libroInD').value = '';
		document.getElementById('ubicacionInD').value = '';
		//document.getElementById('labelNombreD').innerHTML = 'Nombre Completo';
		showObject('personaFisicaTRD', true);
		showObject('personMoralTRD', false);
	}
}


function checkboxValRfc() {
	
	if (getObject('rfcValidar1').checked) {
		showObject('rfc', false);
		showObject('tbejrfc', false);
		showObject('tbejrfctit', false);
		document.getElementById("rfc").value = "";
	}else{
		showObject('rfc', true);
		showObject('tbejrfc', true);
		showObject('tbejrfctit', true);
	}
}
function checkboxValRfcDeud() {
	
	if (getObject('rfcValidaRfcDeud').checked) {
		showObject('rfcD', false);
		showObject('tbejrfcDeud', false);
		showObject('tbejrfcDeudtit', false);
		document.getElementById("rfcD").value = "";
	}else{
		showObject('rfcD', true);
		showObject('tbejrfcDeud', true);
		showObject('tbejrfcDeudtit', true);
	}
}

function cambiaNacionalidad() {
	var nacionalidad = getObject('nacionalidad').value;
	var tipoPersona = getObject('tipoPersona').value;

	if(tipoPersona == 'PF'){
		if(nacionalidad == '1') {
			document.getElementById('labelRfcO').innerHTML = 'Documento de Identificaci&oacute;n';		
			--labelNitD
			Materialize.updateTextFields();
		} else {
			document.getElementById('labelRfcO').innerHTML = 'Pasaporte';
			Materialize.updateTextFields();
		}
	} else {
		if(nacionalidad == '1') {
			document.getElementById('labelNitO').innerHTML = 'NIT';		
			Materialize.updateTextFields();
		} else {
			document.getElementById('labelNitO').innerHTML = 'N&uacute;mero de Registro Tributario';
			Materialize.updateTextFields();
		}
	}
}

function cambiaNacionalidadOLD() {
	var valor = document.getElementById('nacionalidad').value;
	var valor2 = document.getElementById('tipoPersona').value;
	//showObject('curpAyudaID', false);

	//document.getElementById('docExtranjero').removeAttribute("onkeyup");
	//document.getElementById('docExtranjero').removeAttribute("maxLength");
	//document.getElementById('docExtranjero').setAttribute("maxLength", "50");
	if (valor == '1') {
		//document.getElementById('apellidoMaternoTxt').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> Apellido Materno :  </span></td>';
		if (valor2 == 'PM') {
			// Desplegar tipo organizacion
			// agregando los campos miguel
			//showObject('personaTipoSociedad', true);
			// Desplegar tipo organizacion
			//document.getElementById('curpDocO').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
			//showObject('rfcID', false);
			//showObject('docExtranjeroTR', false);

			// pinta RFC
			//showObject('rfcID', false);
			// termina RFC
			// pinta NIF
			//showObject('NIFpais', false);
			// termina NIF
			//showObject('folioIDNoExt', true);
			//showObject('folioIDExt', false);
			//showObject('buttonValidar', false);

			//cambiaTipo();
		} else {
			document.getElementById('optotorgante').checked = false;

			showObject('folioIDNoExt', true);
			showObject('folioIDExt', false);
			showObject('buttonValidar', false);
			// agregando los campos miguel
			showObject('personaTipoSociedad', false);
			// termina agregando los campos miguel
			// pinta NIF
			showObject('NIFpais', false);
			// termina NIF

			// Desplegar menu de mexicano fisico

			document.getElementById('docExtranjero').setAttribute("onkeyup",
					"this.value = this.value.toUpperCase()");
			document.getElementById('docExtranjero').removeAttribute(
					"maxLength");
			document.getElementById('docExtranjero').setAttribute("maxLength",
					"18");
			showObject('curpAyudaID', true);
			document.getElementById('curpDocO').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
			showObject('docExtranjeroTR', true);
			
			if (valor == '1') {
				showObject('rfcID', true);
			}else{
				showObject('rfcID', false);
			}
		}

	} else {
		// agregando los campos miguel
		showObject('personaTipoSociedad', false);
		showObject('folioElectronicoTR', false);
		showObject('personaMoralTR', false);
		
		if (valor == '1') {
			showObject('rfcID', true);
		}else{
			showObject('rfcID', false);
		}

		if (valor2 == 'PM') {
			document.getElementById('optotorgante').checked = false;

			showObject('folioIDNoExt', true);
			showObject('folioIDExt', false);
			showObject('buttonValidar', false);

			showObject('trtdoptogantefisico', true);
			showObject('personaMoralTR', true);
			
			if (valor == '1') {
				showObject('rfcID', true);
			}else{
				showObject('rfcID', false);
			}
			// pinta NIF
			showObject('NIFpais', true);
			// termina NIF
			reset_options();
		} else {
			document.getElementById('optotorgante').checked = false;
			showObject('folioIDExt', false);
			showObject('buttonValidar', false);
			if (valor == '1') {
				showObject('rfcID', true);
			}else{
				showObject('rfcID', false);
			}
			showObject('docExtranjeroTR', false);
			showObject('personaFisicaTR', true);
			showObject('botonGuardar', true);
			showObject('buttonBuscarCurp', false);
		}
	}
}

function cambiaTipoPersonaARep(esAutoridadStr) {
	var valor = document.getElementById('tipoPersonaA').value;
	var valor2 = document.getElementById('nacionalidadA').value;
	//showObject('curpAyudaIDAcre', false);
	/**
	document.getElementById('docExtranjeroA').removeAttribute("onkeyup");
	document.getElementById('docExtranjeroA').removeAttribute("maxLength");
	document.getElementById('docExtranjeroA').setAttribute("maxLength", "50");
	**/
	if (valor == 'PM') {

		// document.getElementById('nombreA').value = '';
		// document.getElementById('apellidoPaternoA').value = '';
		// document.getElementById('apellidoMaternoA').value = '';
		// document.getElementById('folioExistenteAC').value = '';
		// document.getElementById('optotorganteAC').checked=false;
		showObject('folioIDNoExtAC', true);
		showObject('folioIDExtAC', false);
		showObject('buttonValidar', false);

		//if (valor2 == '1') {
			showObject('NIFpaisAC', false);
			//showObject('personaTipoSociedadAC', true);
			showObject('trtdoptogantefisicoAcreedores', false);
			//showObject('docExtranjeroTRAE', false);
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* Documento de Identificaci&oacute;n  :  </span>';
			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno : </span></td>';
		/**} else {
			showObject('NIFpaisAC', true);
			if (esAutoridadStr == '1') {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> Documento de Identificaci&oacute;n  :  </span>';
			} else {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* Documento de Identificaci&oacute;n  :  </span>';
			}
			//showObject('docExtranjeroTRAE', false);
			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>';
			//showObject('curpAyudaIDAcre', false);
			//document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el pa&iacute;s : </span>';
		}*/
		//showObject('personaFisicaTRA', false);
		showObject('personaMoralTRA', true);
	} else {

		getObject('razonSocialA').value = '';// razonSocialA
		getObject('razonSocialA').disabled = false;
		getObject('folioExistenteAC').value = '';
		getObject('folioExistenteAC').disabled = false;
		getObject('rfcA').value = '';
		getObject('rfcA').disabled = false;
		//document.getElementById('TipoDA').value = 'TI';

		// document.getElementById('razonSocialA').value = '';
		document.getElementById('optotorganteAC').checked = false;
		showObject('folioIDNoExtAC', true);
		showObject('folioIDExtAC', false);
		showObject('buttonValidar', false);
		//if (valor2 == '1') {
			// document.getElementById('nombreA').value = '';
			// document.getElementById('apellidoPaternoA').value = '';
			// document.getElementById('apellidoMaternoA').value = '';
			// document.getElementById('folioExistenteAC').value = '';
			// document.getElementById('optotorganteAC').checked=false;
			showObject('NIFpaisAC', false);
			getObject('nombreA').disabled = false;
			//getObject('apellidoPaternoA').disabled = false;
			//getObject('apellidoMaternoA').disabled = false;
			//getObject('docExtranjeroA').value = '';
			//getObject('docExtranjeroA').disabled = false;
			// Modificacion 2014
			//showObject('personaTipoSociedadAC', false);
			//showObject('folioElectronicoTRAC', false);
			showObject('trtdoptogantefisicoAcreedores', true);
			//showObject('docExtranjeroTRAE', true);

			/**if (getObject('acreedorInscribe').checked) {
				document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;* CURP :  </span>';
				document.getElementById('tipoObliga').innerHTML = '<span class="textoGeneralRojo">&nbsp;*</span>';
				document.getElementById('folioxxx').innerHTML = '<span class="textoGeneralRojo">&nbsp;*</span>';
			} else {
				document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
				document.getElementById('tipoObliga').innerHTML = '<span class="textoGeneralRojo">&nbsp;</span>';
				document.getElementById('folioxxx').innerHTML = '<span class="textoGeneralRojo">&nbsp;</span>';
			}**/

			// Modificacion 2014
			//showObject('curpAyudaIDAcre', true);
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* Documento de Identificaci&oacute;n  :  </span>';
			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>';
		/**} else {
			showObject('NIFpaisAC', false);
			//showObject('curpAyudaIDAcre', false);
			if (esAutoridadStr) {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> Documento de Identificaci&oacute;n  :  </span>';
			} else {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* Documento de Identificaci&oacute;n  :  </span>';
			}
			//document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>';
			//document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el pa�s  pa&iacute;s:  </span>';
			//showObject('docExtranjeroTRAE', false);
		}*/
		showObject('personaFisicaTRA', true);
		showObject('personaMoralTRA', false);
	}

}
function cambiaResidencia() {
	/*var residencia = document.getElementById('residenciaA').value;
	if (residencia == '1') {
		showObject('calleNac', true);
		showObject('calleExt', false);
	} else {
		showObject('calleExt', true);
		showObject('calleNac', false);
	}*/
}

function cambiaNacionalidadARep(esAutoridadStr) {
	
}

function cambiaNacionalidadARepOld(esAutoridadStr) {
	var valor = document.getElementById('nacionalidadA').value;
	var valor2 = document.getElementById('tipoPersonaA').value;
	//document.getElementById('docExtranjeroA').removeAttribute("onkeyup");
	//document.getElementById('docExtranjeroA').removeAttribute("maxLength");
	//document.getElementById('docExtranjeroA').setAttribute("maxLength", "50");

	if (valor == '1') {

		/* Validacion de despliegue de campos tipo y folio electronico */
		document.getElementById('TipoDA').value = 'TI';
		showObject('personaTipoSociedadAC', true);
		showObject('folioElectronicoTRAC', false);
		showObject('NIFpaisAC', false);
		showObject('trtdoptogantefisicoAcreedores', false);
		showObject('NIFpaisAC', false);
		/* Validacion de despliegue de campos tipo y folio electronico */

		document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> * Documento de Identificación  :  </span>';

		if (valor2 == 'PM') {
			getObject('razonSocialA').value = '';// razonSocialA
			getObject('razonSocialA').disabled = false;
			getObject('folioExistenteAC').value = '';
			getObject('folioExistenteAC').disabled = false;
			getObject('rfcA').value = '';
			getObject('rfcA').disabled = false;
			// document.getElementById('TipoDA').value='TI';

			showObject('curpAyudaIDAcre', false);
			showObject('docExtranjeroTRAE', false);
			document.getElementById('optotorganteAC').checked = false;
			showObject('folioIDNoExtAC', true);
			showObject('folioIDExtAC', false);
			showObject('buttonValidar', false);
		} else {
			getObject('nombreA').disabled = false;
			getObject('apellidoPaternoA').disabled = false;
			getObject('apellidoMaternoA').disabled = false;
			getObject('docExtranjeroA').value = '';
			getObject('docExtranjeroA').disabled = false;

			showObject('curpAyudaIDAcre', true);
			document.getElementById('optotorganteAC').checked = false;
			showObject('folioIDNoExtAC', true);
			showObject('folioIDExtAC', false);
			showObject('buttonValidar', false);
			document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo">&nbsp;CURP :  </span>';
			showObject('docExtranjeroTRAE', true);
			showObject('personaTipoSociedadAC', false);
			showObject('folioElectronicoTRAC', false);
			showObject('trtdoptogantefisicoAcreedores', true);

			document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>';

		}
	} else {
		getObject('razonSocialA').value = '';// razonSocialA
		getObject('razonSocialA').disabled = false;
		getObject('folioExistenteAC').value = '';
		getObject('folioExistenteAC').disabled = false;
		getObject('rfcA').value = '';
		getObject('rfcA').disabled = false;
		document.getElementById('TipoDA').value = 'TI';

		getObject('nombreA').disabled = false;
		getObject('apellidoPaternoA').disabled = false;
		getObject('apellidoMaternoA').disabled = false;
		/* Validacion de despliegue de campos tipo y folio electronico */
		showObject('personaTipoSociedadAC', false);
		showObject('folioElectronicoTRAC', false);
		showObject('trtdoptogantefisicoAcreedores', true);
		document.getElementById('optotorganteAC').checked = false;
		showObject('folioIDNoExtAC', true);
		showObject('folioIDExtAC', false);
		showObject('buttonValidar', false);
		/* Validacion de despliegue de campos tipo y folio electronico */
		if (valor2 == 'PM') {
			showObject('NIFpaisAC', true);
		} else {
			showObject('NIFpaisAC', false);
		}

		showObject('docExtranjeroTRAE', false);
		showObject('curpAyudaIDAcre', false);

		// document.getElementById('curpDocA').innerHTML = '<span
		// class="textoGeneralRojo"> Documento que acredita su legal estancia en
		// el pa�s : </span>';
		document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>';

		/* 2017-09-12. Se comenta campo que no debe existir y provoca interrupcion de carga de catalogo de paises. */
		/*
		if (esAutoridadStr == '1') {
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> RFC  :  </span>';
		} else {
			document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* RFC  :  </span>';
		}
		*/
	}

}

// Mantenimiento acreedor 2014
function cambiaTipoDAcreedor() {
	var valor = document.getElementById('tipoPersonaA').value;
	var valor2 = document.getElementById('nacionalidadA').value;
	var valor3 = document.getElementById('TipoDA').value;
	if (valor == 'PM') {// Si es moral
		if (valor2 == '1') {// Si es mexicano

			if (valor3 == 'SM') {// Tipo de organizacion (Social Mercantil)
				document.getElementById('trtdoptogantefisicoAcreedores').checked = false;
				showObject('folioElectronicoTRAC', true); // Este lo
															// modificamos
															// 11/08/2014
				showObject('trtdoptogantefisicoAcreedores', false);
				getObject('razonSocialA').value = '';// razonSocialA
				getObject('razonSocialA').disabled = false;
				getObject('folioExistenteAC').value = '';
				getObject('folioExistenteAC').disabled = false;
				getObject('rfcA').value = '';
				getObject('rfcA').disabled = false;
			} else if (valor3 == 'OT') {// Tipo de organizacion (Otro)
				showObject('folioElectronicoTRAC', false);
				showObject('trtdoptogantefisicoAcreedores', true);
			} else {
				getObject('razonSocialA').value = '';// razonSocialA
				getObject('razonSocialA').disabled = false;
				getObject('folioExistenteAC').value = '';
				getObject('folioExistenteAC').disabled = false;
				getObject('rfcA').value = '';
				getObject('rfcA').disabled = false;
				document.getElementById('TipoDA').value = 'TI';
				document.getElementById('trtdoptogantefisicoAcreedores').checked = false;
				showObject('folioElectronicoTRAC', false);
				showObject('trtdoptogantefisicoAcreedores', false);
			}
		}
	}

}
// Mantenimiento acreedor 2014

function ocultar() {
	showObject('agreNuevo', false);
}

function agregarNuevo() {
	$('select').material_select();
	Materialize.updateTextFields();
	showObject('agreNuevo', true);
}

function agregarNuevoO() {
	$('select').material_select();
	Materialize.updateTextFields();
	showObject('agreNuevoO', true);
}

function ocultarOT() {
	showObject('agreNuevoO', false);
}

function ocultarDeudor() {

	showObject('parteDeudorDTD', false);
}

function bloqueaOtorganteTP() {
	document.getElementById('tipoPersona').disabled = true;
}
function desbloqueaOtorganteTP() {
	document.getElementById('tipoPersona').disabled = false;
}

function bloqueaDeudorTP() {
	document.getElementById('tipoPersonaD').disabled = true;
}
function desbloqueaDeudorTP() {
	document.getElementById('tipoPersonaD').disabled = false;
}

function mostrarDeudor() {
	$('select').material_select();
	Materialize.updateTextFields();
	showObject('parteDeudorDTD', true);
}

function cambiaTipoD() {
	var valor = document.getElementById('tipoPersona').value;
	var valor2 = document.getElementById('nacionalidad').value;
	var valor3 = document.getElementById('TipoD').value;
	if (valor == 'PM') {// Si es moral
		if (valor2 == '1') {// Si es mexicano

			if (valor3 == 'SM') {// Tipo de organizacion (Social Mercantil)
				document.getElementById('optotorgante').checked = false;

				showObject('folioIDNoExt', true);
				showObject('folioIDExt', false);
				showObject('buttonValidar', false);

				showObject('folioElectronicoTR', true); // Este lo modificamos
														// 11/08/2014
				showObject('personaMoralTR', true);
				showObject('trtdoptogantefisico', false);
				showObject('rfcID', true);
				showObject('rfcValidar1', true);
				showObject('txrfc', true);

			} else if (valor3 == 'OT') {// Tipo de organizacion (Otro)
				showObject('folioElectronicoTR', false);
				showObject('personaMoralTR', true);
				showObject('trtdoptogantefisico', true);
				showObject('rfcID', true);
				showObject('rfcValidar1', false);
				showObject('txrfc', false);
				

			} else {
				document.getElementById('optotorgante').checked = false;
				showObject('folioElectronicoTR', false);
				showObject('personaMoralTR', false);
				showObject('rfcID', true);
				showObject('trtdoptogantefisico', false);
				
				showObject('rfcValidar1', true);
				showObject('txrfc', true);
				showObject('folioIDNoExt', true);
				showObject('folioIDExt', false);
				showObject('buttonValidar', false);

			}
		}
	}

}

function cambiaTipo() {
	var valor3 = document.getElementById('TipoD').value;

	if (valor3 == 'SM') {// Tipo de organizacion (Social Mercantil)
		showObject('folioElectronicoTR', true);
		showObject('personaMoralTR', true);
		document.getElementById('optotorgante').checked = false;

		// pinta RFC
		showObject('rfcID', true);
		showObject('trtdoptogantefisico', false);

	} else if (valor3 == 'OT') {// Tipo de organizacion (Otro)
		showObject('folioElectronicoTR', false);
		showObject('personaMoralTR', true);

		// pinta RFC
		showObject('rfcID', true);
		showObject('trtdoptogantefisico', true);

	} else {
		document.getElementById('optotorgante').checked = false;
		showObject('folioElectronicoTR', false);
		showObject('personaMoralTR', false);
		showObject('trtdoptogantefisico', false);
	}
}

function reset_options() {
	document.getElementById('TipoD').selectedIndex = "Seleccione";
	return true;
}

function mostrarOcultarRfc() {
	var valor = document.getElementById('tipoUsuario').value;
	var valor2 = document.getElementById('nacionalidad').value;

	if (valor == 'Representante de Acreedores') {
		// Representante extranjero
		if (valor2 != '1') {
			document.getElementById('rfc').style.visibility = 'hidden';
			clearText('textRfc');
			// Representante mexicano
		} else {
			document.getElementById('rfc').style.visibility = 'visible';
		}
		// Autoridad
	} else {
		document.getElementById('rfc').style.visibility = 'visible';
	}
}

function mostrarAgregarMas() {
	//document.getElementById('Agregar').style.visibility = 'visible';
	// document.getElementById('Agregar').style.display = 'block';
}

function ocultarAgregarMas() {
	document.getElementById('Agregar').style.visibility = 'hidden';
	// document.getElementById('Agregar').style.display = 'none';
}

function muestraCorreosAdd(mails) {
	mails.substring(mails.indexOf(','));
	arrayMails = mails.split(',');
	mailTextShow(arrayMails);
}

function muestraCorreosOAdd(mails) {
	mails.substring(mails.indexOf(','));
	arrayMails = mails.split(',');
	mailTextShow(arrayMails);
}

function muestraCorreosAddA(mails) {
	mails.substring(mails.indexOf(','));
	arrayMails = mails.split(',');
	mailTextShowA(arrayMails);
}

function mailTextShowA(arrayMails) {
	// arrayMails=mails.split(",");
	x = "<table>";
	for (var i = 1; i < arrayMails.length; i++) {
		x += "<tr>" + "<td style='padding-left: 35px;' class='texto_general'>"
				+ "<input type='text' value='" + arrayMails[i] + "' id='emailA"
				+ i + "' name='emailA" + i + "' maxlength='50'/>" + "</td>"
				+ "<td class='textoGeneralRojo'>" + "<a onclick='eliminaMailA("
				+ i + ")' style='cursor: pointer;'>- Eliminar</a>" + "</td>"
				+ "</tr>";
	}
	x += "</table>";
	document.getElementById('correosExtraA').innerHTML = x;
	if (arrayMails.length < 5) {
		mostrarAgregarMas();
	} else {
		ocultarAgregarMas();
	}
}

function mailTextShow(arrayMails) {
	// arrayMails=mails.split(",");
	x = "<table>";
	for (var i = 1; i < arrayMails.length; i++) {
		x += "<tr>" + "<td style='padding-left: 35px;' class='texto_general'>"
				+ "<input type='text' value='" + arrayMails[i] + "' id='email"
				+ i + "' name='email" + i + "' maxlength='50'/>" + "</td>"
				+ "<td class='textoGeneralRojo'>" + "<a onclick='eliminaMail("
				+ i + ")' style='cursor: pointer;'>- Eliminar</a>" + "</td>"
				+ "</tr>";
	}
	x += "</table>";
	document.getElementById('correosExtra').innerHTML = x;
	if (arrayMails.length < 5) {
		mostrarAgregarMas();
	} else {
		ocultarAgregarMas();
	}
}

function eliminaMail(elemento) {
	var arrayMails = new Array();
	var sig = 1;
	var actual = 1;
	while (document.getElementById('email' + sig) != null) {
		if (sig != elemento) {
			arrayMails[actual] = document.getElementById('email' + sig).value;
			actual++;
		}
		sig++;
	}
	mailTextShow(arrayMails);
}

function eliminaMailA(elemento) {
	var arrayMails = new Array();
	var sig = 1;
	var actual = 1;
	while (document.getElementById('emailA' + sig) != null) {
		if (sig != elemento) {
			arrayMails[actual] = document.getElementById('emailA' + sig).value;
			actual++;
		}
		sig++;
	}
	mailTextShowA(arrayMails);
}

function agregaMailA() {
	var arrayMails = new Array();
	var i = 1;
	while (document.getElementById('emailA' + i) != null) {
		arrayMails[i] = document.getElementById('emailA' + i).value;
		i++;
	}
	arrayMails[i] = "";
	mailTextShowA(arrayMails);
}

function agregaMail() {
	var arrayMails = new Array();
	var i = 1;
	while (document.getElementById('email' + i) != null) {
		arrayMails[i] = document.getElementById('email' + i).value;
		i++;
	}
	arrayMails[i] = "";
	mailTextShow(arrayMails);
}

function buscaCurp(elementID, idTramite, idPersona, idPersonaModificar,
		isInscripcion, isMultiple) {
	// displayLoader(true);
	var curp = getObject('docExtranjero').value;

	var nombre = getObject('nombre').value;
	var apellidoP = getObject('apellidoPaterno').value;
	var apellidoM = getObject('apellidoMaterno').value;
	var razonSocial = getObject('razonSocial').value;
	var nacionalidad = getObject('nacionalidad').value;
	var tipoPersona = getObject('tipoPersona').value;
	var folioElectronico = getObject('folioElectronico').value;
	var docExtranjero = getObject('docExtranjero').value;
	var rfc = getObject('rfc').value;
	var nacionalidad = getObject('nacionalidad').value;
	var valor = document.getElementById('tipoPersona').value;
	var valor2 = document.getElementById('nacionalidad').value;
	elementID = elementID;
	isInscripcionGlobal = isInscripcion;

	var cadenaStr = idTramite + idPersona + idPersonaModificar + nombre
			+ apellidoP + apellidoM + razonSocial + nacionalidad + tipoPersona
			+ folioElectronico + docExtranjero + rfc + isInscripcion
			+ isMultiple;

	if (cadenaStr.indexOf('<script') >= 0) {
		alert('no puedes escribir codigo malicioso');
		return false;
	}

	if (!noVacio(docExtranjero)) {
		alert('El campo CURP es obligatorio');
		return false;
	}

	if (!validaCurp(docExtranjero)) {
		alert('El campo CURP es incorrecto');
		return false;
	}

	if (valor == 'PF' && valor2 == '1' && validaCurp(docExtranjero)
			&& noVacio(docExtranjero)) {
		displayLoader(true);
		ParteDwrAction.curpRenapo(elementID, idTramite, idPersona,
				idPersonaModificar, nombre, apellidoP, apellidoM, razonSocial,
				nacionalidad, tipoPersona, folioElectronico, docExtranjero,
				rfc, isInscripcion, isMultiple, curp, showCurpResponse);
	}

}

function showCurpResponse(message) {

	ParceJS(message.message);
	displayLoader(false);

}

// -------------creando funcion para alerta
function muestraFolioOtorganteE(elementID, idTramite, idPersona,
		idPersonaModificar, nombre, apellidoPaterno, apellidoMaterno,
		isInscripcion, isMultiple, curp, idNacionalidad, tipoPersona1,
		folioMercantil) {
	alert('La CURP que ingres\u00f3 corresponde al siguiente Folio Electr\u00f3nico :'
			+ folioMercantil);
}
// -----------

function guardaParteRenapoAcreedorRepresentado(elementID, idTramite, idPersona,
		idPersonaModificar, nombre, apellidoPaterno, apellidoMaterno,
		isInscripcion, isMultiple, curp, idNacionalidad, tipoPersona1,
		folioMercantil, domicilioUno, domicilioDos, poblacion, zonaPostal,
		tipoPersona, razonSocial, rfc, calle, calleNumero, calleNumeroInterior,
		idColonia, idLocalidad, telefono, correoElectronico, extencion,
		idPaisResidencia, acreedorInscribe) {
	// var razonSocial = getObject('razonSocialA').value;
	var folioElectronico = folioMercantil;
	var docExtranjero = curp;
	// var rfc = getObject('rfcA').value;
	var nacionalidad = idNacionalidad;
	var valor = tipoPersona1;
	var valor2 = idNacionalidad;
	elementID = elementID;
	isInscripcionGlobal = isInscripcion;

	var cadenaStr = idTramite + idPersona + idPersonaModificar + nombre
			+ apellidoPaterno + apellidoMaterno + razonSocial + nacionalidad
			+ tipoPersona + folioElectronico + docExtranjero + rfc
			+ isInscripcion + isMultiple + curp;

	if (cadenaStr.indexOf('<script') >= 0) {
		alert('no puedes escribir codigo malicioso');
		return false;
	}
	if (valor == 'PF' && valor2 == '1') {

		displayLoader(true);
		// dirigirse a metodo de guardaParteOtorganteNuevo para
		// matricularlo en el sistema
		ParteDwrAction.guardarAcreedor(domicilioUno, domicilioDos, poblacion,
				zonaPostal, tipoPersona, razonSocial, rfc, folioMercantil,
				calle, calleNumero, calleNumeroInterior, nombre, curp,
				apellidoPaterno, apellidoMaterno, idColonia, idLocalidad,
				idNacionalidad, telefono, correoElectronico, extencion,
				idPaisResidencia, idPersona, idTramite, elementID,
				acreedorInscribe, showNuevoAcreedorRep);

	}
}

function guardaParteOtorganteRenapo(elementID, idTramite, idPersona,
		idPersonaModificar, nombre, apellidoPaterno, apellidoMaterno,
		isInscripcion, isMultiple, curp, idNacionalidad, tipoPersona1, rfc) {
	
	//var razonSocial = getObject('razonSocial').value;
	var razonSocial = '';
	var tipoPersona = tipoPersona1;
	//var folioElectronico = getObject('folioElectronico').value;
	var folioElectronico = '';
	var docExtranjero = curp;
	var nacionalidad = idNacionalidad;
	var valor = tipoPersona1;
	var valor2 = idNacionalidad;
	elementID = elementID;
	isInscripcionGlobal = isInscripcion;

	var cadenaStr = idTramite + idPersona + idPersonaModificar + nombre
			+ apellidoPaterno + apellidoMaterno + razonSocial + nacionalidad
			+ tipoPersona + folioElectronico + docExtranjero + rfc
			+ isInscripcion + isMultiple + curp;

	if (cadenaStr.indexOf('<script') >= 0) {
		alert('no puedes escribir codigo malicioso');
		return false;
	}

	if (valor == 'PF' && valor2 == '1') {
		/*if (!noVacio(docExtranjero)) {

			alert('El campo CURP es obligatorio');
			return false;
		}

		if (!validaCurp(docExtranjero)) {
			alert('El campo CURP es incorrecto');
			return false;
		}*/
		displayLoader(true);
		// dirigirse a metodo de guardaParteOtorganteNuevo para
		// matricularlo en el sistema
		ParteDwrAction.guardaParteOtorganteNuevo(elementID, idTramite,
				idPersona, idPersonaModificar, nombre, apellidoPaterno,
				apellidoMaterno, razonSocial, nacionalidad, tipoPersona,
				folioElectronico, docExtranjero, rfc, isInscripcion,
				isMultiple, showParteOtorgante);

	}
}

function showObject(idObject, display) {
	if (display == true) {
		getObject(idObject).style.visibility = 'visible';
		getObject(idObject).style.display = 'block';
	} else if (display == false) {
		getObject(idObject).style.visibility = 'hidden';
		getObject(idObject).style.display = 'none';
	} else {
		alert("Opcion no valida");
	}

}

function cambiaNacionalidadA(esAutoridadStr) {
	
var nacionalidad = getObject('nacionalidadA').value;
var tipoPersona = getObject('tipoPersonaA').value;

if(tipoPersona == 'PF'){
	if(nacionalidad == '1') {
		document.getElementById('labelRfcA').innerHTML = 'Documento de Identificaci&oacute;n';		
		--labelNitD
		Materialize.updateTextFields();
	} else {
		document.getElementById('labelRfcA').innerHTML = 'Pasaporte';
		Materialize.updateTextFields();
	}
} else {
	if(nacionalidad == '1') {
		document.getElementById('labelNitA').innerHTML = 'NIT';		
		Materialize.updateTextFields();
	} else {
		document.getElementById('labelNitA').innerHTML = 'N&uacute;mero de Registro Tributario';
		Materialize.updateTextFields();
	}
}

}

function cambiaTipoPersonaA(esAutoridadStr) {
	var valor = document.getElementById('tipoPersonaA').value;
	var nacionalidad = document.getElementById('nacionalidadA').value;
	
	if (valor == 'PM') {
		if(nacionalidad == '1') {
			document.getElementById('labelNitA').innerHTML = 'NIT';		
		} else {
			document.getElementById('labelNitA').innerHTML = 'N&uacute;mero de Registro Tributario';
		}
		document.getElementById('nombreA').value = '';		
		//document.getElementById('labelNombreA').innerHTML  = 'Representante Legal';
		//document.getElementById('apellidoPaternoD').value = '';
		//document.getElementById('apellidoMaternoD').value = '';
		showObject('personaMoralTRA', true);
		showObject('personaFisicaTRA', false);
	} else {
		if(nacionalidad == '1') {
			document.getElementById('labelRfcA').innerHTML = 'Documento de Identificaci&oacute;n';		
		} else {
			document.getElementById('labelRfcA').innerHTML = 'Pasaporte';
		}
		document.getElementById('razonSocialA').value = '';
		document.getElementById('inscritaA').value = '';
		document.getElementById('ubicacionA').value = '';
		//document.getElementById('folioA').value = '';
		//document.getElementById('libroA').value = '';
		//document.getElementById('ubicacionA').value = '';
		//document.getElementById('labelNombreA').innerHTML = 'Nombre Completo';
		showObject('personaFisicaTRA', true);
		showObject('personaMoralTRA', false);
	}
}

function cambiaTipoPersonaAOld(esAutoridadStr) {
	var valor = document.getElementById('tipoPersonaA').value;
	var valor2 = document.getElementById('nacionalidadA').value;
	document.getElementById('docExtranjeroA').removeAttribute("onkeyup");
	document.getElementById('docExtranjeroA').removeAttribute("maxLength");
	document.getElementById('docExtranjeroA').setAttribute("maxLength", "50");
	if (valor == 'PM') {
		document.getElementById('nombreA').value = '';
		document.getElementById('apellidoPaternoA').value = '';
		document.getElementById('apellidoMaternoA').value = '';

		if (valor2 == '1') {
			showObject('docExtranjeroTRA', false);
			/* 2017-09-12. Se comenta campo que no debe existir y provoca interrupcion de carga de catalogo de paises. */
			//document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* RFC  :  </span>';
			document.getElementById('nombreA').value = '';
			document.getElementById('apellidoPaternoA').value = '';
			document.getElementById('apellidoMaternoA').value = '';
			document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno : </span></td>';
		} else {
			document.getElementById('nombreA').value = '';
			document.getElementById('apellidoPaternoA').value = '';
			document.getElementById('apellidoMaternoA').value = '';
			/* 2017-09-12. Se comenta campo que no debe existir y provoca interrupcion de carga de catalogo de paises. */
			/*
			if (esAutoridadStr == '1') {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> RFC  :  </span>';
			} else {
				document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* RFC  :  </span>';
			}*/
			showObject('docExtranjeroTRA', true);
			document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>';
			showObject('curpAyudaIDAcre', false);
			document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el pa&iacute;s :  </span>';
		}
		showObject('personaMoralTRA', true);
		showObject('personaFisicaTRA', false);
	} else {
		document.getElementById('razonSocialA').value = '';
		document.getElementById('nombreA').value = '';
		document.getElementById('apellidoPaternoA').value = '';
		document.getElementById('apellidoMaternoA').value = '';
		if (valor2 == '1') {
			document.getElementById('nombreA').value = '';
			document.getElementById('apellidoPaternoA').value = '';
			document.getElementById('apellidoMaternoA').value = '';
	//		document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* RFC  :  </span>';
			// document.getElementById('docExtranjeroA').setAttribute("onkeyup","this.value
			// = this.value.toUpperCase()");
			// document.getElementById('docExtranjeroA').removeAttribute("maxLength");
			// document.getElementById('docExtranjeroA').setAttribute("maxLength","18");
			// document.getElementById('curpAyudaIDAcre').style.visibility =
			// 'visible';
			// document.getElementById('curpAyudaIDAcre').style.display =
			// 'block';
			showObject('docExtranjeroTRA', false);
			document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 11px;"><span  class="textoGeneralRojo"> <span class="texto_azul"> </span> Apellido Materno :  </span></td>';
			// document.getElementById('curpDocA').innerHTML = '<span
			// class="textoGeneralRojo"> * CURP : </span>';
		} else {
			document.getElementById('nombreA').value = '';
			document.getElementById('apellidoPaternoA').value = '';
			document.getElementById('apellidoMaternoA').value = '';
			showObject('curpAyudaIDAcre', false);
			if (esAutoridadStr) {
			//	document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo"> RFC  :  </span>';
			} else {
			//	document.getElementById('anotacionRfc').innerHTML = '<span class="textoGeneralRojo">* RFC  :  </span>';
			}
			document.getElementById('apellidoMaternoTxtA').innerHTML = '<td style="padding-left: 12px;"><span class="textoGeneralRojo"> Apellido Materno :  </span></td>';
			document.getElementById('curpDocA').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el pa&iacute;s :  </span>';
			showObject('docExtranjeroTRA', true);
		}
		showObject('personaFisicaTRA', true);
		showObject('personaMoralTRA', false);
	}
}