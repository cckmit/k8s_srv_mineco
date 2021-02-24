function getObject(elementId) {
	return document.getElementById(elementId);
}

function trim(str){
	return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}
function getObjectValue(elementId) {
	return trim(getObject(elementId).value);
}

function displayObject(elementId, onoff) {
	getObject(elementId).style.visibility = (onoff) ? 'visible' : 'hidden';
	getObject(elementId).style.display = (onoff) ? 'block' : 'none';
}

function fillObject(elementId, content) {
	var aux = getObject(elementId);
	if(!aux) {
		return false;
	}

	aux.innerHTML = content;

	return true;
}

function displayAlert(onoff, title, content) {
	var msg = "";

	msg = msg
			.concat("<div style='height:30px;margin-top:10px' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg
			.concat(" <div style=\"float:center;\"><input type='button' name='aceptar' id='btnAceptar' value='Aceptar' onclick='displayAlert(false);' class='tituloInteriorRojo'/>  </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
		getObject('btnAceptar').focus();
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageConfirmacionGeneral(onoff, title, content) {
	var msg = "";

	msg = msg
			.concat("<div style='height:30px;margin-top:10px; width:250px;' class='tituloInteriorRojo' align=\"center\">@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg
			.concat(" <div align='center'> <input type='button' name='Aceptar' value='SI' onclick='sendForm()' /> <input type='button' name='cancelar' value='NO' onclick='displayMessageAlert(false);'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageConfirmacionPaso3(onoff, title, content) {
	var msg = "";

	msg = msg.concat("<div style='height:30px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg
			.concat(" <div align='center'> <input type='button' name='Aceptar' value='Si' onclick='sendForm()' /> <input type='button' name='cancelar' value='No' onclick='displayMessageAlert(false);'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}
function displayMessageAyuda(onoff, title, content) {
	var msg = "";
	msg = msg.concat("<div style=\" background-color =blue;\">");
	msg = msg
			.concat("<div style='height:30px;margin-top:10px' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg
			.concat(" <div style=\"float:center;\">  <input type='button' name='cancelar' value='Aceptar' onclick='displayMessageAyuda(false);' class='tituloInteriorRojo'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	msg = msg.concat("</div>");
	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}
function displayMessageAlert(onoff, title, content) {
	var msg = "";

	msg = msg
			.concat("<div style='height:30px;margin-top:10px' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg
			.concat("<input type='button' name='Aceptar' value='Aceptar' onclick='sendForm()' class='tituloInteriorRojo'/>");
	msg = msg
			.concat("<input type='button' name='cancelar' value='Cancelar' onclick='displayMessageAlert(false);' class='tituloInteriorRojo'/>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageAlertCancel(onoff) {
	var msg = "";
	msg = msg + "<form id='fcancelacion' name='fcancelacion' action='cancelacionTramite.do'>";
	msg = msg + "<div style='margin-top:20px;'> <table class='nota'>";
	msg = msg + "<tr><td class='imgNota'><div>";
	msg = msg + "<img src='../resources/imgs/ico_nota.png' >";
	msg = msg + "<b>NOTA</b></div></td>";
	msg = msg + "<td class='contenidoNota'>La cancelación es el asiento de extinción de una Garantía Mobiliaria inscrita en el RUG.<br> </td>";
	msg = msg + "</tr></table></div>";
	
	msg = msg + "<div style='margin-top:10px;' class='tituloInteriorRojo'>* Persona que solicita o Autoridad que instruye la Cancelaci&oacute;n y Resoluci&oacute;n Judicial o Administrativa en la cual se funda la misma:</div>";
	msg = msg + "<div>";
	msg = msg + "<div><textarea cols='45' rows='5' name='autoridad' id='autoridad' maxlength='3000' onkeyup='return ismaxlength(this)'> </textarea></div>";
	msg = msg + "<div>";
	msg = msg + "<input type='button' name='Aceptar' value='Aceptar' onclick='sendForm()' class='tituloInteriorRojo'/>";
	msg = msg + "<input type='button' name='cancelar' value='Cancelar' onclick='displayMessageAlertCancel(false);' class='tituloInteriorRojo'/>";
	msg = msg + "</div></div>";
	msg = msg + "<form>";

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if(onoff){
			setTimeout("function ismaxlength(obj){var mlength=obj.getAttribute? parseInt(obj.getAttribute('maxlength')) : ''; if (obj.getAttribute && obj.value.length>mlength) obj.value=obj.value.substring(0,mlength);}",0);
		}
		if (msg != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayLoader(onoff) {
	
}

function changeClass(elementId, className) {
	getObject(elementId).setAttribute("class", className);
	getObject(elementId).setAttribute("className", className);
}
function changeStyle(elementId, style) {
	getObject(elementId).setAttribute("style", style);
}

function setActiveTab(elementId) {
if(elementId=='tresMenu'){
	changeClass(elementId + "Link","activeTabTres");
	changeStyle(elementId + "Link","padding:8px 11px 0px 8px");
}else if(elementId=='sieteMenu'){
	changeClass("unoMenuLink","unoMenuLinkChico");
	changeClass("sieteMenuLink","activeTabFinal");
}else if(elementId=='unoMenu'){
	changeClass("unoMenuLink","activeTabNoMargin");
}else if(elementId=='dosMenu'){
	changeClass("dosMenuLink","activeTabDos");
}else if(elementId=='cuatroMenu'){
	changeClass("cuatroMenuLink","activeTabCuatro");
}else if(elementId=='cincoMenu'){
	changeClass("cincoMenuLink","activeTabCinco");
}
else if(elementId=='seisMenu'){
	changeClass("seisMenuLink","activeTabSeis");
 }
}

function setActiveTabBusqueda(elementId) {
	changeStyle(
			elementId,
			"color: #f16a21; background: transparent url(../imgs/menu-bpi.png) no-repeat scroll 100% -28px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;");
	changeStyle(elementId + "Link",
			"background:transparent url(../imgs/menu-rb.png) no-repeat scroll 100% 0");
}

function displayMessageLegislacion(onoff) {
	changeStyle("messageAlertDiv",
			"height:550px; margin-left:23%; width:647px; margin-top:-215px;");
	var msg = "";
	msg = msg
			.concat("<table cellspacing=\"2\" cellpadding=\"2\" width=\"90%\" height=\"90%\" border=\"0\" valign=\"top\">");

	msg = msg.concat("	<tr valign=\"top\">");
	msg = msg
			.concat("		<td class=\"tituloInteriorRojo\">Legislación <br/> <img src=\"/portal/imgs/plecagris_delgada.jpg\" 			   			width=\"300\" height=\"3\"></td>");
	msg = msg.concat("	</tr>");
	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"texto_general\">");
	msg = msg
			.concat("		<p class=\"texto_general\">Estimado usuario, a continuación le presentamos un listado de normas vigentes de relevancia en relación con el Registro Único de Garantías Mobiliarias. Para consultar el texto completo dé click sobre las normas que sean de su interés.</p>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/CONSTITUCION POLITICA DE LOS ESTADOS UNIDOS MEXICANOS.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Constitución Política de los Estados Unidos Mexicanos </a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/CODIGO CIVIL FEDERAL.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\">Código Civil Federal </a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/CODIGO DE COMERCIO.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Código de Comercio </a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/CODIGO FISCAL DE LA FEDERACION_1.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Código Fiscal de la Federación</a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/CODIGO PENAL FEDERAL.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Código Penal Federal</a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Ley de Aviación Civil.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley de Aviación Civil");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY DE INSTITUCIONES DE CREDITO.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley de Instituciones de Crédito");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY DE NAVEGACION Y COMERCIO MARITIMOS.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley de Navegación y Comercio Marítimos");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY DE LA PROPIEDAD INDUSTRIAL.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley de Propiedad Industrial");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY FEDERAL DE PROCEDIMIENTO ADMINISTRATIVO.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley Federal de Procedimiento Administrativo");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Ley Federal del Derecho de Autor.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> 	Ley Federal del Derecho de Autor");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY GENERAL DE SOCIEDADES MERCANTILES.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley General de Sociedades Mercantiles");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/LEY GENERAL DE TITULOS Y OPERACIONES DE CREDITO.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley General de Títulos y Operaciones de Crédito");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	   <a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Ley Minera.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Ley Minera </a>");
	msg = msg.concat("	</td>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Reglamento de la Ley de Navegación.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> 	Reglamento de la Ley de Navegación");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Reglamento de la Ley de la Propiedad Industrial.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> 	Reglamento de la Ley de la Propiedad Industrial");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>	");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Reglamento de la Ley Federal del Derecho de Autor.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> 	Reglamento de la Ley Federal del Derecho de Autor");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>	");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/Reglamento del Registro Aeronáutico Mexicano.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> 	Reglamento del Registro Aeronáutico Mexicano");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");

	msg = msg.concat("<tr valign=\"top\">");
	msg = msg.concat("	<td class=\"titulo_interior\">");
	msg = msg.concat("	<a");
	msg = msg
			.concat("		href=\"/Rug/resources/pdf/legislacion/AcuerdoCURP.pdf\"");
	msg = msg
			.concat("		class=\"ver_todas3\" target=\"_blank\"> Acuerdo para la adopción y uso por la Administración Pública Federal de la Clave Única de Registro de Población.");
	msg = msg.concat("		</a>");
	msg = msg.concat("	</td>");
	msg = msg.concat("</tr>");
	msg = msg
			.concat("<tr><td align = 'center'><input type='button' name='cancelar' value='Ocultar' onclick='displayMessageLegislacion(false);' /></td></tr>");

	msg = msg.concat("</table>");

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);

		
		
		if (typeof content != 'undefinded') {
			getObject('messageAlertDiv').innerHTML = msg;
		}

	} else {
		alert('el div del mensaje no existe no existe');
	}
}


function setActiveTabAltaAcreedor(elementId) {
	changeStyle(
			elementId,
			"color: #f16a21; background: transparent url(../imgs/menu-bpi.png) no-repeat scroll 100% -28px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;");
	changeStyle(elementId + "Link",
			"background:transparent url(../imgs/menu-rb.png) no-repeat scroll 100% 0");
}
	
function ayudaUno(onoff,titulo, cadena) {
	changeStyle("messageAlertDiv",
			"height:180px; margin-left:42%; width:200px; margin-top:-100px;");
	var msg = "";
	msg = msg.concat("<div style='background: white; height:180px; overflow: auto';><table bgcolor='white'><tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo);
	msg = msg.concat("</td></tr><tr><td class ='texto_general'>");
	msg = msg.concat(cadena);
	msg = msg.concat("</td></tr><tr><td align = 'center'>");
	msg = msg.concat("<input type='button'value='Aceptar' onclick='ayudaUno(false)'");
	msg = msg.concat("</td></tr></table></div>");

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function ayudaDos(onoff,titulo, cadena,titulo2,cadena2) {
	changeStyle("messageAlertDiv",
			"height:250px; margin-left:35%; width:400px; margin-top:-150px;");
	var msg = "";
	msg = msg.concat("<div style='background: white; height:250px; overflow: auto';><table bgcolor='white'><tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo);
	msg = msg.concat("</td></tr><tr><td class ='texto_general'>");
	msg = msg.concat(cadena);
	msg = msg.concat("</td></tr> ");
	msg = msg.concat("<tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo2);
	msg = msg.concat("</td></tr> <tr><td>");
	msg = msg.concat(cadena2);
	msg = msg.concat(" </td></tr><tr><td align = 'center'>");
	msg = msg.concat("<input type='button'value='Aceptar' onclick='ayudaDos(false)'");
	msg = msg.concat("</td></tr></table></div>");

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function ayudaTres(onoff,titulo, cadena,titulo2,cadena2,titulo3,cadena3) {
	changeStyle("messageAlertDiv",
			"height:250px; margin-left:35%; width:400px; margin-top:-150px;");
	var msg = "";
	msg = msg.concat("<div style='background: white; height:250px; overflow: auto';><table bgcolor='white'><tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo);
	msg = msg.concat("</td></tr><tr><td class ='texto_general'>");
	msg = msg.concat(cadena);
	msg = msg.concat("</td></tr> ");
	msg = msg.concat("<tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo2);
	msg = msg.concat("</td></tr> <tr><td>");
	msg = msg.concat(cadena2);
	msg = msg.concat(" </td></tr> ");
	msg = msg.concat("<tr><td class='tituloInteriorRojo'>");
	msg = msg.concat(titulo3);
	msg = msg.concat("</td></tr> <tr><td>");
	msg = msg.concat(cadena3);
	msg = msg.concat(" </td></tr> ");
	msg = msg.concat("<tr><td align = 'center'>");
	msg = msg.concat("<input type='button'value='Aceptar' onclick='ayudaTres(false)'");
	msg = msg.concat("</td></tr></table></div>");

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageConfirmacionCurpAcreedor(
		onoff, 
		title, 
		content, 
		curp, 
		nombre, 
		apellidoPaterno,
		apellidoMaterno, 
		elementId,
		idTramite,
		idPersona,
		isInscripcion,
		idNacionalidad,
		tipoPersona1,
		isMultiple,
		domicilioUno,
		domicilioDos,
		poblacion,
		zonaPostal,
		tipoPersona2,
		razonSocial,
		rfc,
		folioMercantil,
		calle,
		calleNumero,
		calleNumeroInterior,
		idColonia,
		idLocalidad,
		telefono,
		correoElectronico,
		extencion,
		idPaisResidencia,
		acreedorInscribe) {
	var msg = "";
    
	changeStyle("messageAlertDiv",
	"height:200px; margin-left:38%; width:320px; margin-top:-150px;");
	msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	
	msg = msg
	.concat(" <div align='center'>" +
			"<table>" +
			"<tr><td style='text-align:right;'>" +
			"CURP : </td>" +
			"<td><input disabled='false' type='text' name='' value='@curp' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Nombre : </td>" +
			"<td><input disabled='false' type='text' name='' value='@nombre' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apedillo paterno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido1' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apedillo Materno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido2' /></td>" +
			"</tr>" +
			"</table></div>");
	msg = msg
	.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"guardaParteRenapoAcreedorRepresentado('"
				+ elementId+ "',"+
				idTramite+ ","+
				idPersona+ ",'0','"+
				nombre+ "','"+
				apellidoPaterno+"','"+
				apellidoMaterno+"','"+
				isInscripcion+"','"+
				isMultiple+"','"+
				curp+"','"+
				idNacionalidad+"','"+
				tipoPersona1+"','"+
				folioMercantil+"','"+
				domicilioUno+"','"+
				domicilioDos+"','"+
				poblacion+"','"+
				zonaPostal+"','"+
				tipoPersona2+"','"+
				razonSocial+"','"+
				rfc+"','"+
				calle+"','"+
				calleNumero+"','"+
				calleNumeroInterior+"','"+
				idColonia+"','"+
				idLocalidad+"','"+
				telefono+"','"+
				correoElectronico+"','"+
				extencion+"','"+
				idPaisResidencia+"','"+
				acreedorInscribe+"'),displayMessageAlert(false); \"/> </td>");

	
	msg = msg
	.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	msg = msg.replace("@curp", curp);
	msg = msg.replace("@nombre", nombre);
	msg = msg.replace("@apellido1", apellidoPaterno);
	msg = msg.replace("@apellido2", apellidoMaterno);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageConfirmacionCurp(onoff, title, content, curp, nombre, apellidoPaterno, apellidoMaterno, elementId,idTramite,idPersona,isInscripcion,idNacionalidad,tipoPersona,isMultiple, rfc) {
	
	var msg = "";
    
	changeStyle("messageAlertDiv",
	"height:200px; margin-left:38%; width:320px; margin-top:-150px;");
	msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	
	msg = msg
	.concat(" <div align='center'>" +
			"<table>" +
			"<tr><td style='text-align:right;'>" +
			"CURP : </td>" +
			"<td><input disabled='false' type='text' name='' value='@curp' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Nombre : </td>" +
			"<td><input disabled='false' type='text' name='' value='@nombre' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apedillo paterno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido1' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apedillo Materno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido2' /></td>" +
			"</tr>" +
			"<tr>" +
			"<td><input type='hidden' disabled='false' type='text' name='' value='@rfc' /></td>" +
			"</tr>" +
			"</table></div>");
	msg = msg
			.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"guardaParteOtorganteRenapo('"
					+ elementId
					+ "',"
					+ idTramite
					+ ","
					+ idPersona
					+ ",'0','"
					+ nombre
					+ "','"+apellidoPaterno+"','"+apellidoMaterno+"','"+isInscripcion+"','"+isMultiple+"','"+curp+"','"+idNacionalidad+"','"+tipoPersona+"','"+rfc+"'),displayMessageAlert(false)\"/> <script> desbloqueaOtorganteTP(); </script> </td>");
	
	msg = msg
	.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	msg = msg.replace("@curp", curp);
	msg = msg.replace("@nombre", nombre);
	msg = msg.replace("@apellido1", apellidoPaterno);
	msg = msg.replace("@apellido2", apellidoMaterno);
	msg = msg.replace("@rfc", rfc);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}

function displayMessageConfirmacionCurpMatriculado(onoff, title, content, curp, nombre, apellidoPaterno, apellidoMaterno, elementId,idTramite,idPersona,isInscripcion,idNacionalidad,tipoPersona,isMultiple,folioMercantil,rfc) {
	
	var msg = "";
    
	changeStyle("messageAlertDiv",
	"height:200px; margin-left:38%; width:320px; margin-top:-150px;");
	msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	
	msg = msg
	.concat(" <div align='center'>" +
			"<table>" +
			"<tr><td style='text-align:right;'>" +
			"CURP : </td>" +
			"<td><input disabled='false' type='text' name='' value='@curp' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Nombre : </td>" +
			"<td><input disabled='false' type='text' name='' value='@nombre' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apellido paterno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido1' /></td>" +
			"</tr>" +
			"<tr><td style='text-align:right;'>" +
			"Apellido Materno : </td>" +
			"<td><input disabled='false' type='text' name='' value='@apellido2' /></td>" +
			"</tr>" +
			"<tr>"+
			"<td><input type='hidden' disabled='false' type='text' name='' value='@rfc' /></td>" +
			"</tr>" +
			"</table></div>");

	msg = msg
	.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"muestraFolioOtorganteE('"
			+ elementId
			+ "',"
			+ idTramite
			+ ","
			+ idPersona
			+ ",'0','"
			+ nombre
			+ "','"+apellidoPaterno+"','"+apellidoMaterno+"','"+isInscripcion+"','"+isMultiple+"','"+curp+"','"+idNacionalidad+"','"+tipoPersona+"','"+folioMercantil+"','"+rfc+"'),displayMessageAlert(false);\"/> </td>");

	
	msg = msg
	.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	
	msg = msg.replace("@curp", curp);
	msg = msg.replace("@nombre", nombre);
	msg = msg.replace("@apellido1", apellidoPaterno);
	msg = msg.replace("@apellido2", apellidoMaterno);
	msg = msg.replace("@rfc", rfc);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}


function displayMessageNuevoOtorganteAcreedor(
		onoff, 
		title, 
		content, 
		curp, 
		nombre, 
		apellidoPaterno,
		apellidoMaterno, 
		elementId,
		idTramite,
		idPersona,
		isInscripcion,
		idNacionalidad,
		tipoPersona1,
		isMultiple,
		domicilioUno,
		domicilioDos,
		poblacion,
		zonaPostal,
		tipoPersona2,
		razonSocial,
		rfc,
		folioMercantil,
		calle,
		calleNumero,
		calleNumeroInterior,
		idColonia,
		idLocalidad,
		telefono,
		correoElectronico,
		extencion,
		idPaisResidencia,
		acreedorInscribe) {
	var msg = "";
	    
		changeStyle("messageAlertDiv",
		"height:100px; margin-left:28%; width:640px; margin-top:50px;");
		msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
		msg = msg.concat("<div>");
		msg = msg.concat("<div class='texto_general'> @contenido </div>");
		msg = msg.concat("<div>");
		
		msg = msg
		.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"guardaParteRenapoAcreedorRepresentado('"
				+ elementId+ "',"+
				idTramite+ ","+
				idPersona+ ",'0','"+
				nombre+ "','"+
				apellidoPaterno+"','"+
				apellidoMaterno+"','"+
				isInscripcion+"','"+
				isMultiple+"','"+
				curp+"','"+
				idNacionalidad+"','"+
				tipoPersona1+"','"+
				folioMercantil+"','"+
				domicilioUno+"','"+
				domicilioDos+"','"+
				poblacion+"','"+
				zonaPostal+"','"+
				tipoPersona2+"','"+
				razonSocial+"','"+
				rfc+"','"+
				calle+"','"+
				calleNumero+"','"+
				calleNumeroInterior+"','"+
				idColonia+"','"+
				idLocalidad+"','"+
				telefono+"','"+
				correoElectronico+"','"+
				extencion+"','"+
				idPaisResidencia+"','"+
				acreedorInscribe+"'),displayMessageAlert(false); \"/> </td>");

		
		msg = msg
		.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
		msg = msg.concat("</div></div>");

		msg = msg.replace("@titulo", title);
		msg = msg.replace("@contenido", content);
		
		msg = msg.replace("@curp", curp);
		msg = msg.replace("@nombre", nombre);
		msg = msg.replace("@apellido1", apellidoPaterno);
		msg = msg.replace("@apellido2", apellidoMaterno);

		if (getObject('smothboxDiv') != null
				&& getObject('messageAlertDiv') != null) {
			displayObject('smothboxDiv', onoff);
			displayObject('messageAlertDiv', onoff);
			
			if (content != null) {
				getObject('messageAlertDiv').innerHTML = msg;
			}
		} else {
			alert('el div del mensaje no existe no existe');
		}	

	}
// funcion para mostrar mensaJE OTORGANTE nuevo
function displayMessageNuevoOtorgante(onoff, title, content, curp, nombre, apellidoPaterno, apellidoMaterno, elementId,idTramite,idPersona,isInscripcion,idNacionalidad,tipoPersona,isMultiple,folioMercantil,rfc) {
var msg = "";
    
	changeStyle("messageAlertDiv",
	"height:100px; margin-left:28%; width:640px; margin-top:50px;");
	msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	
	msg = msg
	.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"guardaParteOtorganteRenapo('"
			+ elementId
			+ "',"
			+ idTramite
			+ ","
			+ idPersona
			+ ",'0','"
			+ nombre
			+ "','"+apellidoPaterno+"','"+apellidoMaterno+"','"+isInscripcion+"','"+isMultiple+"','"+curp+"','"+idNacionalidad+"','"+tipoPersona+"','"+folioMercantil+"','"+rfc+"'),displayMessageAlert(false);\"/> </td>");

	
	msg = msg
	.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	
	msg = msg.replace("@curp", curp);
	msg = msg.replace("@nombre", nombre);
	msg = msg.replace("@apellido1", apellidoPaterno);
	msg = msg.replace("@apellido2", apellidoMaterno);
	msg = msg.replace("@rfc", rfc);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}	

}
//fin mensaje otorgante nuevo

//inicia nuevo popup de validacion de nuevo acreedor representado

function displayMessageNuevoAcreedorRepresentado(
		onoff, 
		title, 
		content, 
		curp, 
		nombre, 
		apellidoPaterno,
		apellidoMaterno, 
		elementId,
		idTramite,
		idPersona,
		isInscripcion,
		idNacionalidad,
		tipoPersona1,
		isMultiple,
		domicilioUno,
		domicilioDos,
		poblacion,
		zonaPostal,
		tipoPersona2,
		razonSocial,
		rfc,
		folioMercantil,
		calle,
		calleNumero,
		calleNumeroInterior,
		idColonia,
		idLocalidad,
		telefono,
		correoElectronico,
		extencion,
		idPaisResidencia,
		acreedorInscribe) {
	var msg = "";
		changeStyle("messageAlertDiv",
		"height:100px; margin-left:28%; width:640px; margin-top:50px;");
		msg = msg.concat("<div style='height:5px;margin-top:10px; width:400px;' class='tituloInteriorRojo'>@titulo</div>");
		msg = msg.concat("<div>");
		msg = msg.concat("<div class='texto_general'> @contenido </div>");
		msg = msg.concat("<div>");
		msg = msg
		.concat("			<td style=\"padding-left: 55px; \" id=\"botonGuardar\"><input value=\"Aceptar\" class=\"boton_rug\" type=\"button\" onclick=\"guardaParteRenapoAcreedorRepresentado('"
				+ elementId+ "',"+
				idTramite+ ","+
				idPersona+ ",'0','"+
				nombre+ "','"+
				apellidoPaterno+"','"+
				apellidoMaterno+"','"+
				isInscripcion+"','"+
				isMultiple+"','"+
				curp+"','"+
				idNacionalidad+"','"+
				tipoPersona1+"','"+
				folioMercantil+"','"+
				domicilioUno+"','"+
				domicilioDos+"','"+
				poblacion+"','"+
				zonaPostal+"','"+
				tipoPersona2+"','"+
				razonSocial+"','"+
				rfc+"','"+
				calle+"','"+
				calleNumero+"','"+
				calleNumeroInterior+"','"+
				idColonia+"','"+
				idLocalidad+"','"+
				telefono+"','"+
				correoElectronico+"','"+
				extencion+"','"+
				idPaisResidencia+"','"+
				acreedorInscribe+"'),displayMessageAlert(false); \"/> </td>");

		
		msg = msg
		.concat(" <input type='button' name='cancelar' value='Cancelar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
		msg = msg.concat("</div></div>");

		msg = msg.replace("@titulo", title);
		msg = msg.replace("@contenido", content);
		
		msg = msg.replace("@curp", curp);
		msg = msg.replace("@nombre", nombre);
		msg = msg.replace("@apellido1", apellidoPaterno);
		msg = msg.replace("@apellido2", apellidoMaterno);

		if (getObject('smothboxDiv') != null
				&& getObject('messageAlertDiv') != null) {
			displayObject('smothboxDiv', onoff);
			displayObject('messageAlertDiv', onoff);
			
			if (content != null) {
				getObject('messageAlertDiv').innerHTML = msg;
			}
		} else {
			alert('el div del mensaje no existe no existe');
		}	

	}

//inicia nuevo popup de validacion de nuevo acreedor representado

function displayMessageConfirmacionCurpFail(onoff, title, content) {
	var msg = "";
    
	changeStyle("messageAlertDiv",
	"height:110px; margin-left:40%; width:250px; margin-top:-150px;");
	msg = msg.concat("<div style='height:5px;margin-top:10px; width:80px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
		
	msg = msg
	.concat(" <input type='button' name='cancelar' value='Cerrar' class=\"boton_rug\" onclick='displayMessageAlert(false);'/>  </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
	
}

//Función para decicidir si se inscriben los folios de los
//acreedores tipo de garantia AF

function displayMessageFolioElectronicoAcreedores(onoff, title, content, okFunction, cancelarFunction) {
	var msg = "";

	msg = msg.concat("<div style='height:30px;margin-top:10px; width:250px;' class='tituloInteriorRojo'>@titulo</div>");
	msg = msg.concat("<div>");
	msg = msg.concat("<div class='texto_general'> @contenido </div>");
	msg = msg.concat("<div>");
	msg = msg.concat(" <div align='center'> <input type='button' name='Aceptar' value='REGISTRAR FOLIO' onclick='@okFunction' /> " +
											"<input type='button' name='Cancelar' value='CANCELAR' onclick='@cancelarFunction' /> </div>");
	// msg = msg.concat(" <div> </div>");
	msg = msg.concat("</div></div>");

	msg = msg.replace("@titulo", title);
	msg = msg.replace("@contenido", content);
	msg = msg.replace("@okFunction", okFunction );
	msg = msg.replace("@cancelarFunction", cancelarFunction);

	if (getObject('smothboxDiv') != null
			&& getObject('messageAlertDiv') != null) {
		displayObject('smothboxDiv', onoff);
		displayObject('messageAlertDiv', onoff);
		if (content != null) {
			getObject('messageAlertDiv').innerHTML = msg;
		}
	} else {
		alert('el div del mensaje no existe no existe');
	}
}



	
	
