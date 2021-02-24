var elementOperacion;

function busquedaOp(idPersona, tipoOperacion){
	var dateStart = "";
	var dateEnd  = "";
	displayLoader(true);
	if(tipoOperacion == 1){
		dateStart = document.getElementById("datepicker1").value;
		dateEnd = document.getElementById("datepicker2").value;
		elementOperacion="OpPendientes";
//		obtener fecha inicio y fecha fin de los tags de struts en cada caso
//		si es necesario en este punto se realizaria una validación 
	}
	if(tipoOperacion == 2){
		var dateStart = "";
		var dateEnd  = "";
		dateStart = document.getElementById("datepicker3").value;
		dateEnd = document.getElementById("datepicker4").value;
		elementOperacion="OpPenFirma";
//		obtener fecha inicio y fecha fin de los tags de struts en cada caso
//		si es necesario en este punto se realizaria una validación 
	}
	if(tipoOperacion == 3){
		var dateStart = "";
		var dateEnd  = "";
		dateStart = document.getElementById("datepicker7").value;
		dateEnd = document.getElementById("datepicker8").value;
		elementOperacion="OpTerminadas";
//		obtener fecha inicio y fecha fin de los tags de struts en cada caso
//		si es necesario en este punto se realizaria una validación 
	}
	BusquedaOpDwrAction.iniciaBusPendientesPagin(idPersona, tipoOperacion, dateStart, dateEnd, showResultado);
	
}

function busOpPenFirmaMasiva(idPersona){
	var dateStart = "";
	var dateEnd  = "";
	dateStart = document.getElementById("datepicker5").value;
	dateEnd = document.getElementById("datepicker6").value;
	elementOperacion="OpPenFirmaMasiva";
	displayLoader(true);
	BusquedaOpDwrAction.iniciaBusPenFirMasiva(idPersona, dateStart, dateEnd, showResultado);
	
}

function busOpNombre(idPersona, tipoOperacion){
	var nomOtorgante;
	if(tipoOperacion == 1){
		nomOtorgante = document.getElementById("nombreOtorgante").value;
		elementOperacion="OpPendientes";
	}
	if(tipoOperacion == 2){
		nomOtorgante = document.getElementById("nombreOtorgante2").value;
		elementOperacion="OpPenFirma";
	}
	if(tipoOperacion == 3){
		nomOtorgante = document.getElementById("nombreOtorgante3").value;
		elementOperacion="OpTerminadas";
	}
	displayLoader(true);
	BusquedaOpDwrAction.iniBusPendByOtorgante(idPersona, tipoOperacion, nomOtorgante, showResultado);
	
}

function busOpPenFirmaMasivaByClvRastreo(idPersona){
	var clvRastreo;
	clvRastreo = document.getElementById("clvRastreo").value;
	elementOperacion="OpPenFirmaMasiva";
	displayLoader(true);
	BusquedaOpDwrAction.iniBusPendFirmaMasivaByClvRastreo(idPersona, clvRastreo, showResultado);
}

function pagBusPenAsiMulByClvRastreo(registroTotales,pagActiva,regPagina){
	var clvRastreo;
	clvRastreo = document.getElementById("clvRastreo").value;
	elementOperacion="OpPenFirmaMasiva";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusPenAsiMulByClvRastreo(idPersona, registroTotales,pagActiva,'20',clvRastreo,showResultado);
}

function pagBusPendientes(registroTotales,pagActiva,regPagina){
	var dateStart = "";
	var dateEnd  = "";
	dateStart = document.getElementById("datepicker1").value;
	dateEnd = document.getElementById("datepicker2").value;
	elementOperacion="OpPendientes";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpPendientes(idPersona, registroTotales,pagActiva,'20',dateStart,dateEnd,showResultado);
}

function pagBusPenByOtorgante(registroTotales,pagActiva,regPagina){
	var nomOtorgante;
	nomOtorgante = document.getElementById("nombreOtorgante").value;
	elementOperacion="OpPendientes";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpPenByOtorgante(idPersona, registroTotales,pagActiva,'20',nomOtorgante,showResultado);
}
function pagBusPendFirma(registroTotales,pagActiva,regPagina){
	var dateStart = "";
	var dateEnd  = "";
	dateStart = document.getElementById("datepicker3").value;
	dateEnd = document.getElementById("datepicker4").value;
	elementOperacion="OpPenFirma";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpPendFirma(idPersona, registroTotales,pagActiva,'20',dateStart,dateEnd,showResultado);
}

function pagBusPendFirmaByOtorgante(registroTotales,pagActiva,regPagina){
	var nomOtorgante;
	nomOtorgante = document.getElementById("nombreOtorgante2").value;
	elementOperacion="OpPenFirma";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpPenFirmaByOtorgante(idPersona, registroTotales,pagActiva,'20',nomOtorgante,showResultado);
}

function pagBusPenAsiMultiples(registroTotales,pagActiva,regPagina){
	var dateStart = "";
	var dateEnd  = "";
	dateStart = document.getElementById("datepicker5").value;
	dateEnd = document.getElementById("datepicker6").value;
	elementOperacion="OpPenFirmaMasiva";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpPendAsiMultiples(idPersona, registroTotales,pagActiva,'20',dateStart,dateEnd,showResultado);
}

function pagBusOpTerminadas(registroTotales,pagActiva,regPagina){
	var dateStart = "";
	var dateEnd  = "";
	dateStart = document.getElementById("datepicker7").value;
	dateEnd = document.getElementById("datepicker8").value;
	elementOperacion="OpTerminadas";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpTerminadas(idPersona, registroTotales,pagActiva,'20',dateStart,dateEnd,showResultado);
}



function pagBusOpTermByOtorgante(registroTotales,pagActiva,regPagina){
	var nomOtorgante;
	nomOtorgante = document.getElementById("nombreOtorgante3").value;
	elementOperacion="OpTerminadas";
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BusquedaOpDwrAction.pagBusOpTermByOtorgante(idPersona, registroTotales,pagActiva,'20',nomOtorgante,showResultado);
}

function showResultado(message){
	if (message.codeError==0){
		fillObject(elementOperacion,message.message);
		ParceJS(message.message);
	}
	displayLoader(false);
}

function habilita(num){
	obj = document.getElementById('bus1');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus2');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus3');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus4');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus5');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus6');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}
	
	obj = document.getElementById('bus7');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}	
	
	obj = document.getElementById('bus8');
	for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
	    ele.disabled = true;
	}	
	
	if(num==1){
		obj = document.getElementById('bus1');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}	
	}else if(num==2){
		obj = document.getElementById('bus2');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}	
	}else if(num==3){
		obj = document.getElementById('bus3');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
	}else if(num == 4){
		obj = document.getElementById('bus4');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
	}else if(num == 5){
		obj = document.getElementById('bus5');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
	}else if(num == 6){
		obj = document.getElementById('bus6');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
		
	}else if(num == 7){
		obj = document.getElementById('bus7');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
		
	}else{
		obj = document.getElementById('bus8');
		for (i=0; ele = obj.getElementsByTagName('input')[i]; i++){
		    ele.disabled = false;
		}
	}
		
}
