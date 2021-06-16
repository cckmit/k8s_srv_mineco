var button = function (name, label, tooltip) {
	this.name = name;
	this.label = label;
	this.tooltip = tooltip;
} 	

var buttons = [ new button('ZUL', 'ZUL', 'Campos pantalla fija'),
                new button('CV', 'CV', 'Campos Caratula Variable'),
                new button('VARS', 'VARS', 'Variables a usar'),
                new button('FFCC', 'FFCC', 'Campos Formulario Controlado'),
                new button('BPM', 'BPM', 'Variables transición workflow'),
                new button('DOCS', 'DOCS', 'Documentos Necesarios'),
				new button('FORMS', 'FORMS', 'Formularios')];

var ZUL_onClick = function(name, tooltip) {
	ingresarVariable(tooltip, name);
}
var CV_onClick = function(name, tooltip) {
	ingresarVariable(tooltip, name);
}
var VARS_onClick = function(name, tooltip) {
	ingresarVariable(tooltip, name);
}
var BPM_onClick = function(name, tooltip) {
	ingresarVariable(tooltip, name);
}
var FFCC_onClick = function(name, tooltip) {
	ingresarCampoFFCC(tooltip, name);
}
var DOCS_onClick = function(name,tooltip){
	ingresarFunction('Documentos Necesarios','Acrónimo del Documento','documentoParaValidar');
}
var FORMS_onClick = function(name, tooltip) {
	getAllComponentsForms();
}
