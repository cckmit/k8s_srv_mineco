
function validaformulario(Form)	{
	if(Form.nombreempresa.value == "") {
		alert("Por favor ingrese el nombre");			
		Form.nombreempresa.focus();
		return false;
	}	
}	

