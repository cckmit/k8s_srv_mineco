function mensaje (mensaje,gif  ){
	 
	 writetolayer('area_comunicacion','<table cellpadding="2" class="ComunicaHeader" cellspacing="1" width="450"><tr><td class="texto_general" colspan="2"></td></tr><tr><td width="16px"><img src="/portal/imgs/'+gif+'" border="0" /></td><td align="center" class="texto_general">'+mensaje+'</td></tr><tr><td class="texto_general" colspan="2">&nbsp;</td></table>');
	
}


function populateCombo(id, list, key, value, defaultArray, valueOf){
	
	dwr.util.removeAllOptions(id);
	dwr.util.addOptions(id,  defaultArray ,key, value);
    dwr.util.addOptions(id, list, key, value);
	dwr.util.setValue(id, valueOf );
	
}



function clearDefault(id, list, key, value, defaultArray){
	
	dwr.util.removeAllOptions(id);
    dwr.util.addOptions(id, list, key, value);
	
}


function resetCombo(id, defaultArray, key, value){
	dwr.util.removeAllOptions(id);
	dwr.util.addOptions(id,defaultArray,key,value);
	
}

function clearCombo(id, defaultArray, key, value){
	dwr.util.removeAllOptions(id);	
}


function ltrim(s) {
	return s.replace(/^\s+/, "");
}

function rtrim(s) {
	return s.replace(/\s+$/, "");
}

function trim(s) {
	return rtrim(ltrim(s));
}



function esMontoValido(monto){
	var bandera = false;
	// si esta vacio no es valido
	if(monto == null || monto == ""){
		return bandera;
	}
	
	var punto = ".";
	var posicionPunto = monto.indexOf(punto, 0);
	// valida si no tiene punto decimal
	if(posicionPunto == -1){
		return validarSoloNumeros(monto);
	// si tiene punto decimal
	}else{
		var montoDecimales = monto.substr(posicionPunto+1, monto.length);
		// valida que solo tenga un punto decimal
		if(montoDecimales.indexOf(punto, 0) == -1){
			
			var enteros = monto.substr(0,posicionPunto);
			var decimales = montoDecimales;
			// valida la parte entera y la parte decimal
			if(validarSoloNumeros(enteros) && validarSoloNumeros(decimales)){
				// fue valido
				bandera = true;
			}
		}else{
			return bandera;
		}
	}
	return bandera;
}