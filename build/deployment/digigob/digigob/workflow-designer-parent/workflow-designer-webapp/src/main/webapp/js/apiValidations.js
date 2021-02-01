/**
 * 
 */
var ZUL  = [];
var CV   = [];
var BPM  = [];
var FFCC = [];

var mensaje = function(texto) {
	//alert(texto);
	out.println('Hello from JavaScript');
}
 
var irPaso = function(paso) {	
	getCodeChecker().log("Enviando al estado ({0})".format(stateName));
	if(getProject().findByName(stateName)){
		getCodeChecker().log("Encontre a {0}".format(stateName));
	}else{
		getCodeChecker().log("No encontre a {0}".format(stateName));
	}
};

var habilitarVinculacionDocumentos = function(mensaje) {
	getCodeChecker().log("Vincular documentos: {0}".format(mensaje));
};

var deshabilitarSolapa=function(nombre) {
	getCodeChecker().log("Se deshabilita solapa: {0}".format(nombre));
}

var paseUsuario=function(destino, usuario) {
	irPaso(destino);
	getCodeChecker().log(" +-> usuario: {0}".format(usuario));
}

var paseReparticion=function(destino, reparticion) {
	irPaso(destino);
	getCodeChecker().log(" +-> reparticion: {0}".format(reparticion));
}

var paseReparticionSector=function(destino, reparticion, sector) {
	irPaso(destino);
	getCodeChecker().log(" +-> reparticion: {0} - sector: {1}".format(reparticion,sector));
} 

// --------

var valido=function(){
	getCodeChecker().log(" se indico que el estado es válido y puede realizar el pase");
}

var noValido=function(){
	getCodeChecker().log(" se indico que el estado no es válido y no podrá realizar el pase");
}
