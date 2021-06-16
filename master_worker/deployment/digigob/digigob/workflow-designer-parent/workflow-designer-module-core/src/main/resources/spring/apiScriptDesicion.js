var nextState = "";

var VARS=[];

//var tool = require("herramientas");

var irPaso = function(paso) {	
	nextState=paso; 
}; 

// ---- general basic functions ----
var paseUsuario=function(destino, usuario) {
	var estadoDestino = {
			state:destino,
			user:usuario,
			distribution:null,
			sector:null
	};
	irPaso(destino);
	if(windowPase != null){
		state.selectUsuario(windowPase, usuario);
	} else {
		remoteRestService.executeDesicion(state.getWorkingTask(),state.getEe(),usuario ,destino, 'realizar pase', null);
	}
//	state.populateDataFromClient("paseUsuario",tool.convertirAJSON(estadoDestino));
}

var paseReparticion=function(destino, reparticion) {
	var estadoDestino = {
			state:destino,
			user:null,
			distribution:reparticion,
			sector:null
	};
	irPaso(destino);
	state.selectReparticion(windowPase, reparticion);
//	state.populateDataFromClient("paseReparticion",tool.convertirAJSON(estadoDestino));
}

var paseReparticionSector=function(destino, reparticion, sector) {
	var estadoDestino = {
			state:destino,
			user:null,
			distribution:reparticion,
			sector:sector
	};
	irPaso(destino);
	state.selectSector(windowPase, reparticion, sector);
//	state.populateDataFromClient("paseReparticionSector",tool.convertirAJSON(destino));
}

var realizarPase=function(estadoSeleccionado,motivoExpediente,detalles){
	tramitacionHelper.getExpedienteElectronicoService().generarPaseExpedienteElectronico(state.getWorkingTask(),state.getEe(),usuario.getUsername(),estadoSeleccionado,motivoExpediente,detalles);
}

var documentoVinculado = function(acronimo){
	return state.existDocumentByAcronymous(acronimo);
}

var guid = function() {
	  return s4() + s4();
}

var s4 = function(){
	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
}

var forkPaseUsuario= function(estado, usuario){
	print("paseUsuario "+estado+" - "+usuario);
	
	var estadoDestino = {
			state:estado,
			user:usuario,
			distribution:null,
			sector:null
	};
//	state.populateParallelDataFromClient("forkPaseUsuario-"+guid(),tool.convertirAJSON(estadoDestino));
}

var forkPaseReparticionSector= function(estado, reparticion, sector) {
	print("paseReparticionSector "+estado+" - "+reparticion+" - "+sector);
	
	var estadoDestino = {
			state:estado,
			user:null,
			distribution:reparticion,
			sector:sector
	};
//	state.populateParallelDataFromClient("forkPaseReparticionSector-"+guid(),tool.convertirAJSON(estadoDestino));
}

var forkPaseReparticion= function(estado, reparticion) {
	print("paseReparticion "+estado+" - "+reparticion);
	
	var estadoDestino = {
		state:estado,
		user:null,
		distribution:reparticion,
		sector:null
	};
//	state.populateParallelDataFromClient("forkPaseReparticion-"+guid(),tool.convertirAJSON(estadoDestino));
}

var joinPaseUsuario= function(estado, usuario ) {
	print("paseUsuario "+estado+" - "+usuario);
	
	var estadoDestino = {
			state:estado,
			user:usuario,
			distribution:null,
			sector:null
	};
//	state.populateParallelDataFromClient("joinPaseUsuario-"+guid(),tool.convertirAJSON(estadoDestino));
}

var joinPaseReparticionSector= function(estado, reparticion, sector) {
	print("paseReparticionSector "+estado+" - "+reparticion+" - "+sector);
	
	var estadoDestino = {
			state:estado,
			user:null,
			distribution:reparticion,
			sector:sector
	};
//	state.populateParallelDataFromClient("joinPaseReparticionSector-"+guid(),tool.convertirAJSON(estadoDestino));
}

var joinPaseReparticion= function(estado, reparticion) {
	print("paseReparticion "+estado+" - "+reparticion);
	
	var estadoDestino = {
			state:estado,
			user:null,
			distribution:reparticion,
			sector:null
	};
//	state.populateParallelDataFromClient("joinPaseReparticion-"+guid(),tool.convertirAJSON(estadoDestino));
}

function paseDivision() {
	var args = [].join.call(arguments, ':');
	for (var c=0; c<arguments.length; c++) {
		VARS.push(arguments[c]);
	}
	for (var c=0; c<VARS.length; c++) {
		eval(VARS[c]);
	}
};

function paseUnion() {
	var args = [].join.call(arguments, ':');
	for (var c=0; c<arguments.length; c++) {
		print("VAR[] PASE UNION " + arguments[c]);
	}
	for (var c=0; c<VARS.length; c++) {
		eval(VARS[c]);
	}
};


%s

return nextState;