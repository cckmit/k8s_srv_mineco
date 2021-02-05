var ie4 = (document.all) ? true : false;
var ns4 = (document.layers) ? true : false;
var ns6 = (document.getElementById && !document.all) ? true : false;

function escribemensaje(lay, txt) {
	if (ie4) {
		document.all[lay].innerHTML = txt;
	}
	if (ns4) {
		document[lay].document.write(txt);
		document[lay].document.close();
	}
	if (ns6) {
		over = document.getElementById( [ lay ]);
		range = document.createRange();
		range.setStartBefore(over);
		domfrag = range.createContextualFragment(txt);
		while (over.hasChildNodes()) {
			over.removeChild(over.lastChild);
		}
		over.appendChild(domfrag);
	}
}
function cambiacode(campo) {
	vtexto = campo.value;
	vtexto = vtexto.replace(/�/g, '&aacute;').replace(/�/g, '&iacute;')
			.replace(/�/g, '&eacute;').replace(/�/g, '&oacute;').replace(/�/g,
					'&uacute;').replace(/�/g, '&ntilde;').replace(/�/g,
					'&Ntilde;').replace(/�/g, '&Aacute;').replace(/�/g,
					'&iacute;').replace(/�/g, '&Eacute;').replace(/�/g,
					'&Oacute;').replace(/�/g, '&Uacute;');
	campo.value = vtexto;
}

function writetolayer(lay, txt) {
	if (ie4) {
		document.all[lay].innerHTML = txt;
	}
	if (ns4) {
		document[lay].document.write(txt);
		document[lay].document.close();
	}
	if (ns6) {
		over = document.getElementById( [ lay ]);
		range = document.createRange();
		range.setStartBefore(over);
		domfrag = range.createContextualFragment(txt);
		while (over.hasChildNodes()) {
			over.removeChild(over.lastChild);
		}
		over.appendChild(domfrag);
	}
}

function estilo_foco(enviacampo, id, iderror, desabilita, ayudainf) {
	enviacampo.style.backgroundColor = 'FFFFDC';
	enviacampo.style.border = 'solid';
	enviacampo.style.borderWidth = '1px';
	enviacampo.style.borderColor = 'FC9900';
	var desabilita_o = document.getElementById(desabilita);
	var ayudainf_o = document.getElementById(ayudainf);
	if (desabilita_o != null)
		desabilita_o.style.display = 'none';
	if (ayudainf_o != null)
		ayudainf_o.style.display = 'block';
	// if ( document.getElementById(iderror).style.display == 'block')
	// document.getElementById(id).style.display = 'none' ;
	document.getElementById(iderror).style.display = 'none';
	// else
	document.getElementById(id).style.display = 'block';
}

function estilo_sinfoco(enviacampo, id, iderror, valordefault, ayudainf) {
	enviacampo.style.backgroundColor = 'FFFFFF';
	enviacampo.style.border = 'solid';
	enviacampo.style.borderWidth = '1px';
	enviacampo.style.borderColor = '818181';
	var ayudainf_o = document.getElementById(ayudainf);
	var id_o = document.getElementById(id);
	if (id_o != null)
		id_o.style.display = 'none';
	if (ayudainf_o != null)
		ayudainf_o.style.display = 'none';
	if (enviacampo.value == "" || enviacampo.value == valordefault)
		document.getElementById(iderror).style.display = 'block';
	else
		document.getElementById(iderror).style.display = 'none';
}

function selecciona_value(objInput) {
	var valor_input = objInput.value;
	var longitud = valor_input.length;
	if (objInput.setSelectionRange) {
		objInput.focus();
		objInput.setSelectionRange(0, longitud);
	} else if (objInput.createTextRange) {
		var range = objInput.createTextRange();
		range.collapse(true);
		range.moveEnd('character', longitud);
		range.moveStart('character', 0);
		range.select();
	}
}

function validarfc(valor) {
	var RFCRegEx = '^(([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&]){4})([0-9]{6})((([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]|[0-9]){3}))';
	var RFCRegE = '^(([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&]){3})([0-9]{6})((([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]|[0-9]){3}))';

	if (valor == 'fisica') {
		if (document.frmDatosGrales.rfc.value == "") {
			document.frmDatosGrales.rfc.focus();
			// alert("El campo 'RFC' es obligatorio!");
			return false;
		} else {
			if (!document.frmDatosGrales.rfc.value.match(RFCRegEx)) {
				document.frmDatosGrales.rfc.focus();
				document.frmDatosGrales.rfc.value = "";
				// alert("RFC Incorrecto.");
				return false;
			}
		}
	} else if (valor == 'moral') {
		if (document.frmDatosGrales.rfc.value == "") {
			document.frmDatosGrales.rfc.focus();
			alert("El campo 'RFC' es obligatorio!");
			return false;
		} else {
			if (!document.frmDatosGrales.rfc.value.match(RFCRegE)) {
				document.frmDatosGrales.rfc.focus();
				document.frmDatosGrales.rfc.value = "";
				// alert("RFC Incorrecto.");
				return false;
			}
		}
		return true;
	}
}

function enterBoton(evt, obj) {
	var key = nav4 ? evt.which : evt.keyCode;
	if (key == 13) {
		obj.click();
	}
}

function lTrim(sStr) {
	while (sStr.charAt(0) == " ")
		sStr = sStr.substr(1, sStr.length - 1);
	return sStr;
}

function rTrim(sStr) {
	while (sStr.charAt(sStr.length - 1) == " ")
		sStr = sStr.substr(0, sStr.length - 1);
	return sStr;
}

function allTrim(sStr) {
	return rTrim(lTrim(sStr));
}

function validacurp() {
	var CURPRegEx = '^(([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&]){4})([0-9]{6})([hmHM]{1})(([abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]|\s){5})([0-9]{2})';

	if (document.frmDatosGrales.curp.value == "") {
		document.frmDatosGrales.curp.focus();
		// alert("CURP Vacio.");
		return false;
	} else {
		if (!document.frmDatosGrales.curp.value.match(CURPRegEx)) {
			document.frmDatosGrales.curp.focus();
			// document.frmDatosGrales.curp.value = "";
			// alert("CURP Incorrecto.");
			return false;
		}
	}
	return true;
}

// var nav4 = window.Event ? true : false;
function aceptanum(evt) {
	// var key = nav4 ? evt.which : evt.keyCode;
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57) || key == 46);
}
/*
 * var nav4 = window.Event ? true : false; function aceptanum(evt){ var key =
 * nav4 ? evt.which : evt.keyCode; return (key <= 13 || (key >= 48 && key <= 57) ||
 * key == 46); }
 */

function aceptaalfa(evt) {
	// var key = nav4 ? evt.which : evt.keyCode;
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 90) || key == 46);
}

function oNumero(numero) {
	// Propiedades
	this.valor = numero || 0
	this.dec = -1;
	// M�todos
	this.formato = numFormat;
	this.ponValor = ponValor;
	// Definici�n de los m�todos

	function ponValor(cad) {
		if (cad == '-' || cad == '+')
			return
		if (cad.length == 0)
			return
		if (cad.indexOf('.') >= 0)
			this.valor = parseFloat(cad);
		else
			this.valor = parseInt(cad);
	}
	function numFormat(dec, miles) {
		var num = this.valor, signo = 3, expr;
		var cad = "" + this.valor;
		var ceros = "", pos, pdec, i;
		for (i = 0; i < dec; i++)
			ceros += '0';
		pos = cad.indexOf('.')
		if (pos < 0)
			cad = cad + "." + ceros;
		else {
			pdec = cad.length - pos - 1;
			if (pdec <= dec) {
				for (i = 0; i < (dec - pdec); i++)
					cad += '0';
			} else {
				num = num * Math.pow(10, dec);
				num = Math.round(num);
				num = num / Math.pow(10, dec);
				cad = new String(num);
			}
		}
		pos = cad.indexOf('.')
		if (pos < 0)
			pos = cad.lentgh
		if (cad.substr(0, 1) == '-' || cad.substr(0, 1) == '+')
			signo = 4;
		if (miles && pos > signo)
			do {
				expr = /([+-]?\d)(\d{3}[\.\,]\d*)/
				cad.match(expr)
				cad = cad.replace(expr, RegExp.$1 + ',' + RegExp.$2)
			} while (cad.indexOf(',') > signo)
		if (dec < 0)
			cad = cad.replace(/\./, '')
		return cad;
	}
}// Fin del objeto oNumero:

function cargaCabeceras() {
	switch ('${ClavePantallaTO.datGen}') {
	case 'CO':
		document.images["Opcion1"].src = "/portal/imgs/cuadroGRIS_OK.gif";
		break;
	case 'PC':
		document.images["Opcion1"].src = "/portal/imgs/cuadroGRIS_MISS.gif";
		break;
	case 'ER':
		document.images["Opcion1"].src = "/portal/imgs/cuadroGRIS_ERROR.gif";
		break;
	}
	switch ('${ClavePantallaTO.domicilio}') {
	case 'CO':
		document.images["Opcion2"].src = "/portal/imgs/cuadroGRIS_OK.gif";
		break;
	case 'PC':
		document.images["Opcion2"].src = "/portal/imgs/cuadroGRIS_MISS.gif";
		break;
	case 'ER':
		document.images["Opcion2"].src = "/portal/imgs/cuadroGRIS_ERROR.gif";
		break;
	}
	switch ('${ClavePantallaTO.socAdm}') {
	case 'CO':
		document.images["Opcion3"].src = "/portal/imgs/cuadroGRIS_OK.gif";
		break;
	case 'PC':
		document.images["Opcion3"].src = "/portal/imgs/cuadroGRIS_MISS.gif";
		break;
	case 'ER':
		document.images["Opcion3"].src = "/portal/imgs/cuadroGRIS_ERROR.gif";
		break;
	}
	switch ('${ClavePantallaTO.estatutos}') {
	case 'CO':
		document.images["Opcion5"].src = "/portal/imgs/cuadroGRIS_OK.gif";
		break;
	case 'PC':
		document.images["Opcion5"].src = "/portal/imgs/cuadroGRIS_MISS.gif";
		break;
	case 'ER':
		document.images["Opcion5"].src = "/portal/imgs/cuadroGRIS_ERROR.gif";
		break;
	}
}

// FUNCION QUE VALIDA NUMEROS SIN PUNTO
// var nav4 = window.Event ? true : false;

function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57));
}

function _validarPuntoDecimal(e, txt) {
	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla <= 13)
		return true;

	if (txt.indexOf('.') != -1) {
		if (tecla == 46) {
			return false;
		}
		patron = /[\d\.]/;
		te = String.fromCharCode(tecla);
		return patron.test(te);
	} else {
		patron = /[\d\.]/;
		te = String.fromCharCode(tecla);
		return patron.test(te);
	}
}

function encabezadoHover(campo) {
	document.getElementById(campo).style.fontFamily = "Verdana";
	document.getElementById(campo).style.fontSize = "11px";
	// document.getElementById(campo).style.fontStyle = "noral";
	// document.getElementById(campo).style.fontWeight = "normal";
	document.getElementById(campo).style.textTransform = "none";
	document.getElementById(campo).style.color = "#D0DAE6";
	document.getElementById(campo).style.textDecoration = "underline";
}
function encabezadoNormal(campo) {
	document.getElementById(campo).style.fontFamily = "Verdana";
	document.getElementById(campo).style.fontSize = "11px";
	// document.getElementById(campo).style.fontStyle = "noral";
	// document.getElementById(campo).style.fontWeight = "normal";
	document.getElementById(campo).style.textTransform = "none";
	document.getElementById(campo).style.color = "#FFFFFF";
	document.getElementById(campo).style.textDecoration = "none";
}
