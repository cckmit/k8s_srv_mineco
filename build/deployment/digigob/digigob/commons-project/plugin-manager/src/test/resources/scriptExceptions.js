function evaluate(){                       
		try {                                 
			var mensaje = function(texto) {
				print("lanzaremos mensajes");
				throw new Error(texto);
			}
	
			var cerrarVentana = function(comp) {
				if (!esWS()) {
					state.closeWindow(comp);
				}
			}
	
			var mensajeOK = function(texto){
				if (!esWS()) {
					state.newMessageOK(texto);
				} else {
					console.log(texto);
				}
			}
	
			var mensajeERROR = function(texto){
				if (!esWS()) {
					state.newErrorMessage(texto);
				} else {
					console.log(texto);
				}
			}
	
			var sistemaCreador = function() { 
				return ee.getSistemaCreador();
			}
	
			var sistemaApoderado = function() { 
				return ee.getSistemaApoderado();
			}
	
			var esWS=function() {
				if (typeof __isWebService__ !=='undefined') {
					return true;
				}
				return false;
			}
	
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
				state.selectUsuario(windowPase, usuario);
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
				console.log("paseUsuario "+estado+" - "+usuario);
				
				var estadoDestino = {
						state:estado,
						user:usuario,
						distribution:null,
						sector:null
				};
			//	state.populateParallelDataFromClient("forkPaseUsuario-"+guid(),tool.convertirAJSON(estadoDestino));
			}
	
			var forkPaseReparticionSector= function(estado, reparticion, sector) {
				console.log("paseReparticionSector "+estado+" - "+reparticion+" - "+sector);
				
				var estadoDestino = {
						state:estado,
						user:null,
						distribution:reparticion,
						sector:sector
				};
			//	state.populateParallelDataFromClient("forkPaseReparticionSector-"+guid(),tool.convertirAJSON(estadoDestino));
			}
	
			var forkPaseReparticion= function(estado, reparticion) {
				console.log("paseReparticion "+estado+" - "+reparticion);
				
				var estadoDestino = {
					state:estado,
					user:null,
					distribution:reparticion,
					sector:null
				};
			//	state.populateParallelDataFromClient("forkPaseReparticion-"+guid(),tool.convertirAJSON(estadoDestino));
			}
	
			var joinPaseUsuario= function(estado, usuario ) {
				console.log("paseUsuario "+estado+" - "+usuario);
				
				var estadoDestino = {
						state:estado,
						user:usuario,
						distribution:null,
						sector:null
				};
			//	state.populateParallelDataFromClient("joinPaseUsuario-"+guid(),tool.convertirAJSON(estadoDestino));
			}
	
			var joinPaseReparticionSector= function(estado, reparticion, sector) {
				console.log("paseReparticionSector "+estado+" - "+reparticion+" - "+sector);
				
				var estadoDestino = {
						state:estado,
						user:null,
						distribution:reparticion,
						sector:sector
				};
			//	state.populateParallelDataFromClient("joinPaseReparticionSector-"+guid(),tool.convertirAJSON(estadoDestino));
			}
	
			var joinPaseReparticion= function(estado, reparticion) {
				console.log("paseReparticion "+estado+" - "+reparticion);
				
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
					VARS.push(arguments[c]);
				}
				for (var c=0; c<VARS.length; c++) {
					eval(VARS[c]);
				}
			};
	
			mensaje("Hola");
			return nextState;                                
  	} catch (e){    
  			print("ERROR: " + e)
			//throw e;                  
  	}                                     
};   

evaluate();
