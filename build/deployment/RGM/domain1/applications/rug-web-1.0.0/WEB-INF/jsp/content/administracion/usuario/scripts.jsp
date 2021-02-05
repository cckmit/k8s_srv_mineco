<script type="text/javascript">
$(document).on('submit', 'idFormPerfil', function () {
	var password = $('#contrasenaPass').val();
	var confPassword = $('#confirmacionPass').val();
	
	if(password) {
		if(espacios(password)) {			
			alert("La contraseña no puede contener espacios en blanco.");
			return false;
		}
		if(espacios(confPassword)) {			
			alert("La confirmación de contraseña no puede contener espacios en blanco.");
			return false;
		}
		
	}
	$('#aceptarSendID').attr("disabled", "disabled");
	//getObject('idFormPerfil').submit();
});

function espacios(valor) {
	c=0;
	c=valor.match(/\s/g)+1;
	if(c.length>1) {
		c=0;
		return true;
	}
	return false;
}
</script>
