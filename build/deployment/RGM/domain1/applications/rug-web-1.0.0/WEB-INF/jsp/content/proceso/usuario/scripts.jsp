<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/UsuarioDwrAction.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>

<script type="text/javascript">
$('#frmRecover').on('submit', function (e) {
	if ($('#btnRecuperar').is(':hidden')) {
		e.preventDefault();
		obtenerPreguntaSeguridad();
	}
});
function obtenerPreguntaSeguridad() {
	var email = $('#emailPersonal').val();
	if (!email) {
		MaterialDialog.alert(
			"Debe ingresar su email para recuperar su contraseña",
			{
				title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
				buttons:{
					close:{
						className:"red",
						text:"cerrar"						
					}
				}
			}
		);
		return false;
	}
	UsuarioDwrAction.getSecurityQuestion(email, updatePregunta);
}
function updatePregunta(response) {
	if (response.codeError === 0) {
		var data = JSON.parse(response.data);
		$('#pregunta').empty();
		var divRow = $('<div>', {class: 'row'}).append(
			$('<div>', {class: 'input-field col s10'}).append(
				$('<input>', {type: 'text', name: 'registroUsuario.respuesta', id: 'registroUsuario.respuesta'})
			).append(
				$('<label>').text(data)
			)
		);
		$('#pregunta').append(divRow);
		$('#btnValidar').hide();
		$('#btnRecuperar').show();
	} else {
		MaterialDialog.alert(
			response.message,
			{
				title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
				buttons:{
					close:{
						className:"red",
						text:"cerrar"						
					}
				}
			}
		);
	}
}
function onChangeTipoPersona() {
	var tipo = $('#tipoPersona').val();
	var inscrito = $('#nacionalidadInscrito').val();
	$('#documentoIdentificacion').parent().show();
	$('.user-data').addClass('hidden');
	$('.user-data').removeClass('visible');
	setTimeout(function () {
		if (tipo == 'PF') {
			$('#nombre').parent().find('label').html('Nombre completo');
			if (inscrito == 'N') {
				$('#documentoIdentificacion').parent().find('label').html('DPI');
				$('#nit').show();
				$('#nit').find('label').html('NIT');
			} else {
				$('#documentoIdentificacion').parent().find('label').html('Pasaporte');
				$('#nit').hide();
			}
		} else {
			$('#nombre').parent().find('label').html('Razon o denominacion social');
			$('#nit').show();
			$('#nit').find('label').html('NIT de la empresa');
			if (inscrito == 'N') {
				$('#documentoIdentificacion').parent().find('label').html('Numero de registro');
			} else {
				$('#documentoIdentificacion').parent().hide();
			}
		}
		$('.user-data').addClass('visible');
		$('.user-data').removeClass('hidden');
	}, 1000);
}
function onChangePregunta() {
	var pregunta = $('#pregunta').val();
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
$(document).ajaxComplete(function() {
	if ($('input[type=checkbox][name="registroUsuario.judicial"]').is(':checked')) {
		$('.usuario-judicial').show();
	} else {
		$('.usuario-judicial').hide();
	}
});
</script>
