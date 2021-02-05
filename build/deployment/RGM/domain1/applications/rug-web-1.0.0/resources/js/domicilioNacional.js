function cargaListas(formName, metodo){
	
		var formulario = document.getElementsByName(formName.name)[0];

		formulario.action="/portal/Controller?operacionCatalogo=IN&servicio=DomiciliosNacionales&metodo="+metodo
		
		formulario.submit();
	
	}	

function focusinicial() {
	 document.datosForm.passline.focus();
 }

function cargaLista(formName, metodo, funcion){
	
	var formulario = document.getElementsByName(formName.name)[0];

	formulario.action="/portal/Controller?operacionCatalogo=CR&servicio="+funcion+"&metodo="+metodo
	
	
	formulario.submit();

}


function buscaCP(formName, metodo){
	
	var formulario = document.getElementsByName(formName.name)[0];
	
	var cp = formulario.cp.value;

	formulario.action="/portal/Controller?operacionCatalogo=IN&servicio=DomiciliosNacionales&metodo="+metodo
	
	if(cp.length == 5){
		
		formulario.submit();
		
	}

}


function buscaPorCP(formName, metodo, funcion){
	
	var formulario = document.getElementsByName(formName.name)[0];
	
	var cp = formulario.cp.value;

	formulario.action="/portal/Controller?operacionCatalogo=CR&servicio="+funcion+"&metodo="+metodo
	
	if(cp.length == 5){
		
		formulario.submit();
		
	}

}

function selectOpcion(oControl, sValor){
	var iOpcion= 0;
	var bEncontrado = false;
	var oControl = document.getElementsByName(oControl)[0];
	
	while (iOpcion < oControl.options.length){
		if (oControl.item(iOpcion).value == sValor){
			oControl.selectedIndex = iOpcion;
			bEncontrado = true;
		}
		iOpcion++;
	}
	return bEncontrado;
}



function enviar(formName, funcion, metodo){
	
	var passline = document.datosForm.passline.value;
	
	var formulario = document.getElementsByName(formName.name)[0];
	formulario.action="/portal/Controller?operacionCatalogo=CR&servicio="+funcion+"&metodo="+metodo	
	
	if (!validaDatosComplementarios()){
		
		 muestraMensaje ("Faltan Datos complementarios ", "tache.png")
		
		return false;
		
	}else{
		
		formulario.submit();	
		return true;
	}
	

}


function validaDatosComplementarios(){
	
	
	
	var calle  = document.getElementById("calle").value;
	var numeroExterior  = document.getElementById("numeroExterior").value;
	var numeroInterior  = document.getElementById("numeroInterior").value;
	var cp  = document.getElementById("cp").value;
	var estado  = document.getElementById("estado").value;
	var delegacion  = document.getElementById("delegacion").value;
	var colonia  = document.getElementById("colonia").value;
	var localidad  = document.getElementById("localidad").value;
	
	
	if(trim(calle) == ''){
		
		return false;
		
	} else if(trim(numeroExterior) == ''){
			
			return false;
			
	}else if(trim(cp) == ''){
		
		return false;
		
	}else if(trim(estado) == ''){
		
		return false;
		
	}else if(trim(delegacion) == ''){
		
		return false;
		
	}else if(trim(colonia) == '' && trim(localidad) == ''){
		
		return false;
		
	}else{
		return true;
	}
	
	
	
	
}


function enviarCapcha(formName, funcion, metodo){
	
	document.datosForm.action="/portal/Controller?operacionCatalogo=CR&servicio="+funcion+"&metodo="+metodo	
	document.datosForm.submit();	
	return;
}



function valida(formName) {
	
	var campos_validos = 0;
	var nombre_campo = "";
	var formulario = document.getElementsByName(formName.name)[0];
	
	var elementos = formulario.elements.length;

	for (i=0 ; i<elementos; i++){
		
		if( (trim(formulario.elements[i].value) != "") && (formulario.elements[i].value != "-1") ) {
			
			campos_validos++;
	
	    } else{
	    	nombre_campo = formulario.elements[i].title;
	    	break;
	    }
		
	} 
	
	if (campos_validos == elementos){
		
		return true;
		
	}else{
		
		alert("El Campo " + nombre_campo + " es obligatorio, favor de ingresar datos");
		return false;
	}
      
}


function respuesta (respuesta){
	
	if(respuesta == "0"){
		
		self.close();
		
	}
	
	
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
