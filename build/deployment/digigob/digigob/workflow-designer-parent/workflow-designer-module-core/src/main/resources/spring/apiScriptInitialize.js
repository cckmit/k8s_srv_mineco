var habilitarVinculacionDocumentos = function(mensaje) {
	try {
		state.enableDocumentLinker(mensaje);
	} catch (e) {}
};

var deshabilitarSolapa=function(nombre) {
	try {
		state.disableTab(nombre);
	} catch (e) {}
};

var deshabilitarSolapas=function() {
	try {
		state.invisibleAll();
	} catch (e) {}
};

var activarSolapa=function(nombre) {
	try {
		state.focusTab(nombre);
	} catch(e) {}
};

%s

