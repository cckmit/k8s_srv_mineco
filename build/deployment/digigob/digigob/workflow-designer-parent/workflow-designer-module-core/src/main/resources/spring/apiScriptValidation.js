var resultado = false;

var valido = function() {
	resultado = true;
}

var noValido = function() {
	resultado = false;
}

var documentoParaValidar = function(acronimo) {
	if (!state.existDocumentByAcronymous(acronimo)){
		return false;
	} else {
		return true;
	}
}

%s


return resultado;