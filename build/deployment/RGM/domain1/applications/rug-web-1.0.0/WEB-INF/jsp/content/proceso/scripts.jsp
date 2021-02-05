<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/UsuarioDwrAction.js"></script>
<!-- <script src="<%=request.getContextPath()%>/resources/js/validator.min.js"></script> -->
<script src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

<script type="text/javascript">
 
    
function onChangePregunta() {
	var pregunta = $('#pregunta').val();
	console.log(pregunta);
	if (pregunta === 'Agregar otra') {
		$('#otra-pregunta').show();
	} else {
		$('#otra-pregunta').hide();
	}
}
function aceptaalfa(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)
                      && (charCode < 65 || charCode > 90)
                      && (charCode < 97 || charCode > 122)
                      && (charCode <209  || charCode > 249)
        )
        return false;
    return true;
}
function inicializaFormUsuario(usuario) {
	if (usuario) {
		$('#usuarioModificar').val(usuario.idPersona);
		$('#nombre').val(usuario.nombre);
		$('#docId').val(usuario.docId);
		$('#email').val(usuario.cveUsuario);
		$('#password').val('');
		$('#confirmacion').val('');
		$("#pregunta").prop('selectedIndex', 0);
		$('#otraPregunta').val('');
		$('#respuesta').val('');
	} else {
		$('#usuarioModificar').val('');
		$('#nombre').val('');
		$('#docId').val('');
		$('#email').val('');
		$('#password').val('');
		$('#confirmacion').val('');
		$("#pregunta").prop('selectedIndex', 0);
		$('#otraPregunta').val('');
		$('#respuesta').val('');
	}
	$('#pregunta').material_select();
	Materialize.updateTextFields();
	$('#frmUsuario').scrollTop(0);
}
$('#btnAgregar').on('click', function () {
	inicializaFormUsuario();
});
function guardaUsuario(idPersona) {
	var nombre = $('#nombre').val();
	var docId = $('#docId').val();
	var email = $('#email').val();
	var password = $('#password').val();
	var confirmacion = $('#confirmacion').val();
	var pregunta = $('#pregunta').val();
	var otraPregunta = $('#otraPregunta').val();
	var respuesta = $('#respuesta').val();
	var usuarioModificar = $('#usuarioModificar').val();
	// validar los datos ingresados
	$('#error').empty();
	$('.error-label').remove();
	if (nombre && docId && email && password && confirmacion /*&& pregunta && respuesta*/) {
		if (password != confirmacion) {
			$('#password').parent().append('<span class="error-label" style="position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;">La contraseña y la confirmación deben ser iguales.</span>');
			return;
		}
		
		if (!validator.matches(password, '(?=.*[0-9])(?=.+[a-z])(?=.*[A-Z]).{8,16}')) {
			$('#password').parent().append('<span class="error-label" style="position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;">Su contraseña debe tener una longitud de entre 8 y 16 caracteres, debe incluir letras minúsculas, al menos una letra mayúscula y al menos un número.</span>');
			return;
		}
		
		if (!validator.isEmail(email)) {
			$('#email').parent().append('<span class="error-label" style="position: relative; top: -1rem; left: 0rem; font-size: 0.8rem; color: #FF4081; display: block; text-align: center;">El correo electrónico debe ser válido.</span>');
			return;
		}
		
		if (usuarioModificar) {
			// actualiza al usuario existente
			UsuarioDwrAction.actualizaSubusuario(idPersona, usuarioModificar, nombre, docId, email, password, pregunta, otraPregunta, respuesta, resultadoActualizar);
		} else {
			// crea un nuevo usuario
			UsuarioDwrAction.guardaSubusuario(idPersona, nombre, docId, email, password, pregunta, otraPregunta, respuesta, resultadoGuardar);
		}
	} else {
		$('#error').append('<p style="color: red;">Todos los datos son obligatorios.</p>');
	}
}
function resultadoGuardar(message) {
	console.log(message);
	if (message.codeError == 0) {
		// agregar el nuevo usuario al listado
		var data = JSON.parse(message.data);
		var row = $('<tr>', {id: data.idPersona}).append(
			$('<td>').html(data.nombre)
		).append(
			$('<td>').html(data.cveUsuario)
		).append(
			$('<td>').append(
				$('<a>', {class: 'btn waves-effect indigo', onclick: 'cargaUsuario(' + data.idPersona + ')'}).append(
					$('<i>', {class: 'material-icons'}).html('edit')
				)
			).append(" ").append(
				$('<a>', {class: 'btn waves-effect red darken-4', onclick: 'eliminarUsuario(' + data.idPersona + ')'}).append(
						$('<i>', {class: 'material-icons'}).html('delete')
				)
			)
		);
		
		$('#usuarios tbody').append(row);
		
		$('#frmUsuario').modal('close');
		//inicializaFormAcreedor();
		// TODO: mostrar mensaje de exito
	} else {
		// TODO: mostrar errores al grabar usuario
	}
}
function resultadoActualizar(message) {
	console.log(message);
	if (message.codeError == 0) {
		// actualizar datos de usuario
		var data = JSON.parse(message.data);
		var idPersona = data.idPersona;
		var previous = $('#' + idPersona).prev();
		$('#' + idPersona).remove();
		var row = $('<tr>', {id: data.idPersona}).append(
			$('<td>').html(data.nombre)
		).append(
			$('<td>').html(data.cveUsuario)
		).append(
			$('<td>').append(
				$('<a>', {class: 'btn waves-effect indigo', onclick: 'cargaUsuario(' + data.idPersona + ')'}).append(
					$('<i>', {class: 'material-icons'}).html('edit')
				)
			).append(" ").append(
				$('<a>', {class: 'btn waves-effect red darken-4', onclick: 'eliminarUsuario(' + data.idPersona + ')'}).append(
					$('<i>', {class: 'material-icons'}).html('delete')
				)
			)
		);
		
		console.log($(previous));
		if(previous) {
			$(previous).after(row);
		} else {
			$('#usuarios tbody').append(row);
		}
		
		$('#frmUsuario').modal('close');
		//inicializaFormAcreedor();
		// TODO: mostrar mensaje de exito
	} else {
		// TODO: mostrar errores al grabar usuario
	}
}
function cargaUsuario(usuarioId) {
	UsuarioDwrAction.getUsuario(usuarioId, recibeUsuario);
}
function recibeUsuario(data) {
	var usuario = JSON.parse(data);
	inicializaFormUsuario(usuario);
	$('#frmUsuario').modal('open');	
}
function eliminarUsuario(usuarioId) {
	if (confirm('¿Está seguro de eliminar al usuario?')) {
		UsuarioDwrAction.eliminaSubusuario(usuarioId, resultadoEliminar);
	}
}
function resultadoEliminar(message) {
	if (message.codeError == 0) {
		// quitaral usuario de la tabla
		var data = JSON.parse(message.data);
		var idPersona = data.idPersona;
		$('#' + idPersona).remove();
	}
}



</script>
