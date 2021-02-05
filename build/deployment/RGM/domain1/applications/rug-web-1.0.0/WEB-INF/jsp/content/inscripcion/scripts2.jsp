<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>

<script type="text/javascript">

function noVacio(valor){
	   for ( i = 0; i < valor.length; i++ ) {  
	     if ( valor.charAt(i) != " " ) {
	      return true; 
	    }  
	   }  
	 return false; 
}

function IsNumber(evt) {
    var key = (document.all) ? evt.keyCode : evt.which;
    
    return (key <= 13 || (key >= 48 && key <= 57));
}

var statSend = false;

function paso2() {
	if (!statSend) {
    	document.getElementById("buttonID").value = "Enviando";
    	document.getElementById("buttonID").disabled = true;
    	var idIns = document.getElementById("refInscripcion").value;
       	statSend = true;
       	window.location.href = '<%= request.getContextPath() %>/inscripcion/paso2.do?idInscripcion=' + idIns;       	
       	return true;
    } else {		
        return false;
    }		
 }
	function sendForm(){
		document.getElementById("baceptar").value = "Enviando";
    	document.getElementById("baceptar").disabled = true;
		document.formAcVig.submit();
	}

	function validaMesesPaso3(){
		var valor = document.getElementById('formAcVig_inscripcionTO_meses').value;
		if (valor == '' || valor == '0'){
			alertMaterialize('La Vigencia debe ser de por lo menos un año');
			return false;
		}
		
		if(valor > 5) {
			alertMaterialize('La Vigencia no puede ser mayor a cinco años');
			return false;
		}
		
		var saldo = document.getElementById('mdSaldo').value;		
		if(saldo=="0"){
			alertMaterialize('No tiene saldo suficiente para realizar la inscripción');
			return false;
		}
		
		// obtener el costo de una inscripcion: tipo_tramite=1
		$.ajax({
			url: '<%= request.getContextPath() %>/rs/tipos-tramite/1',
			success: function(result) {
				MaterialDialog.dialog(
					"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100).toFixed(2) + ", ¿está seguro que desea continuar?",
					{				
						title:'<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
						buttons:{
							// Use by default close and confirm buttons
							close:{
								className:"red",
								text:"cancelar"						
							},
							confirm:{
								className:"indigo",
								text:"aceptar",
								modalClose:false,
								callback:function(){
									sendForm();	
								}
							}
						}
					}
				);
			}
		});
	}

	function msg_guardar() {
		alertMaterialize('El sistema le guardara la garantía temporalmente por 72 horas, esto no constituye una inscripción y por lo tanto no otorga prelacion');
		return false;
	}
	
</script>