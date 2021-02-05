<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript" 	  src="${pageContext.servletContext.contextPath}/resources/js/campos.js"></script>
<script type="text/javascript" 	  src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/FuncionesDeFechas.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript">
function anterior(){
		document.location="<%=request.getContextPath() %>/home/anterior.do";
}

function siguiente(){
		document.location="<%=request.getContextPath() %>/home/siguiente.do";
}
function cancelacion(){
	// obtener el costo de una cancelacion: tipo_tramite=4
	$.ajax({
		url: '<%= request.getContextPath() %>/rs/tipos-tramite/4',
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
								window.location.href ='<%= request.getContextPath() %>/home/cancelacionTramite.do';
							}
						}
					}
				}
			);
		}
	});
}

function certificacion() {
	// obtener el costo de una certificacion: tipo_tramite=5
	$.ajax({
		url: '<%= request.getContextPath() %>/rs/tipos-tramite/5',
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
							return true;
						}
					}
				}
			}
		);
		}
	});
}

function buscaPunto(texto){
	for(i=0;i<texto.length;i++){
		if(texto.charAt(i)==".") return true;
	}
	return false;
}

function IsNumber(evt) {
var key = (document.all) ? evt.keyCode : evt.which;
var cadena = document.getElementById('modmonto').value;
var onlyPunto = buscaPunto(cadena);    
if (onlyPunto){    	
	return (key <= 13 || (key >= 48 && key <= 57));
}else{
	return ( key <= 13 || (key >= 48 && key <= 57) || key==46);    	 
}    
}
	function sendFormM(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('faformulario').submit();
	}
	
	function sendFormF(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('fafactoraje').submit();
	}
	
	function sendForm(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;
		getObject('famodificacion').submit();
	}

	function isNullOrEmpty(valor){
		if ( valor == null )
			true;
		for ( i = 0; i < valor.length; i++ )
				if ( valor.charAt(i) != " " )
					return false; 
		return true;
	}



	function validarSinAutoridad(){
		//var tipoGarantia = document.getElementById('modtipogarantia').value;
		var tipoGarantia = 0;
		var dia = new Date();
		var bandera = true;
		
		if(document.getElementById('tableDeudores')==null){
			alertMaterialize('No se puede continuar sin un Deudor');
			return false;
		}
		if(document.getElementById('tableAcreedores')==null){
			alertMaterialize('No se puede continuar sin un Acreedor');
			return false;
		}
		
		// obtener el costo de una modificacion: tipo_tramite=7
		$.ajax({
			url: '<%= request.getContextPath() %>/rs/tipos-tramite/7',
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
	
	function inscripcionFormulario() {
		var tipoGarantia = 0;
		var dia = new Date();
		var bandera = true;
		
		if(document.getElementById('tableDeudores')==null){
			MaterialDialog.alert(
					'No se puede continuar sin un Deudor.', // Alert Body (Acepts html tags)
					{
						title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
						buttons:{ // Receive buttons (Alert only use close buttons)
							close:{
								text:'cerrar', //Text of close button
								className:'red' // Class of the close button								
							}
						}
					}
				);		
			return false;
		}
		if(document.getElementById('tableAcreedores')==null){			
			MaterialDialog.alert(
					'No se puede continuar sin un Acreedor.', // Alert Body (Acepts html tags)
					{
						title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
						buttons:{ // Receive buttons (Alert only use close buttons)
							close:{
								text:'cerrar', //Text of close button
								className:'red' // Class of the close button								
							}
						}
					}
				);		
			return false;
		}
		if(document.getElementById('txtregistro').value==''){			
			MaterialDialog.alert(
					'Debe ingresar la informacion del registro de la prenda', // Alert Body (Acepts html tags)
					{
						title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
						buttons:{ // Receive buttons (Alert only use close buttons)
							close:{
								text:'cerrar', //Text of close button
								className:'red' // Class of the close button								
							}
						}
					}
				);		
			return false;
		}
		
		MaterialDialog.dialog(
			"Esta seguro que la informacion ingresada es correcta; el costo del traslado es de Q 300.00, desea continuar?",
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
							sendFormM();
						}
					}
				}
			}
		);
				
	}
	
	function inscripcionFactoraje() {
		var tipoGarantia = document.getElementById('reftipogarantia').value;
		var dia = new Date();
		var bandera = true;		
		var rows = 0; 
		var costo = 0;
		
		if(tipoGarantia==2) {						
			if(document.getElementById('bienes')==null){			
				MaterialDialog.alert(
						'No se puede continuar sin una factura ingresada.', // Alert Body (Acepts html tags)
						{
							title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
							buttons:{ // Receive buttons (Alert only use close buttons)
								close:{
									text:'cerrar', //Text of close button
									className:'red' // Class of the close button								
								}
							}
						}
					);		
				return false;
			}
			rows = document.getElementById('bienes').getElementsByTagName('tr').length;
			costo = 20 * (rows-1);
		} else {
			costo = 300;
		}
		
		if(document.getElementById('tableAcreedores')==null){			
			MaterialDialog.alert(
					'No se puede continuar sin un Factor o Cesionario.', // Alert Body (Acepts html tags)
					{
						title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
						buttons:{ // Receive buttons (Alert only use close buttons)
							close:{
								text:'cerrar', //Text of close button
								className:'red' // Class of the close button								
							}
						}
					}
				);		
			return false;
		}		
		if(document.getElementById('tableDeudores')==null){
			MaterialDialog.alert(
					'No se puede continuar sin un Cedente.', // Alert Body (Acepts html tags)
					{
						title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
						buttons:{ // Receive buttons (Alert only use close buttons)
							close:{
								text:'cerrar', //Text of close button
								className:'red' // Class of the close button								
							}
						}
					}
				);		
			return false;
		}
		
		if(tipoGarantia!=4) {
			if(document.getElementById('tableOtorgantes')==null){			
				MaterialDialog.alert(
						'No se puede continuar sin un Deudor Cedido.', // Alert Body (Acepts html tags)
						{
							title:'<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
							buttons:{ // Receive buttons (Alert only use close buttons)
								close:{
									text:'cerrar', //Text of close button
									className:'red' // Class of the close button								
								}
							}
						}
					);		
				return false;
			}	
		}
		
				
				
		MaterialDialog.dialog(
			"Esta seguro que la informacion ingresada es correcta; el costo de la inscripcion es de Q " + costo + ".00, desea continuar?",
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
							sendFormF();
						}
					}
				}
			}
		);
				
	}

	function validarAutoridad(){
		// validando campos obligatorios con autoridad
		var tipoGarantia = document.getElementById('modtipogarantia').value;
		var dia = new Date();
		var bandera = true;
		
		if(tipoGarantia == 10 || tipoGarantia == 12){
			if (isNullOrEmpty(document.getElementById("modtipoacto").value)
					|| isNullOrEmpty(document.getElementById("autoridad").value)
						|| isNullOrEmpty(document.getElementById("modtipobien").value)
							|| isNullOrEmpty(document.getElementById("tiposbienes").value)) {
				alert('Los campos marcados con * son obligatorios.');
				bandera = false;
			}else{
				// si fecha 1 es null
				if(isNullOrEmpty(document.getElementById("datepicker1").value)){
					alert('Los campos marcados con * son obligatorios.');
					bandera = false;
				}
			}
		}else{
			// si no es ni retencion ni privilegios debe exigir el campo del acto
			// contrato que obliga.
			if (isNullOrEmpty(document.getElementById("modtipoacto").value)
					|| isNullOrEmpty(document.getElementById("autoridad").value)
						|| isNullOrEmpty(document.getElementById("tipoContOb").value)
							|| isNullOrEmpty(document.getElementById("modtipobien").value)
								|| isNullOrEmpty(document.getElementById("tiposbienes").value)) {
				alert('Los campos marcados con * son obligatorios.');
				bandera = false;
			}else{
				// si fecha 1 es null
				if(isNullOrEmpty(document.getElementById("datepicker1").value)){
					alert('Los campos marcados con * son obligatorios.');
					bandera = false;
				}
			}
		}

		if(bandera){			     
			sendForm();
		}			
		
	}

	todos = new Array();
	function marcar(s) {	
		cual=s.selectedIndex;
		for(y=0;y<s.options.length;y++){
		if(y==cual){
			s.options[y].selected=(todos[y]==true)?false:true;
			todos[y]=(todos[y]==true)?false:true;
		}else{
		s.options[y].selected=todos[y];
			}
		}
	} 

	function escondePartes(){
		var valor = document.getElementById('modtipogarantia').value;
		var bfecha1 = true;
		var bfecha2 = false;
		var bfecha3 = false;

		switch(valor){
			case "1":
				/*
				fechadato= "* Fecha de celebración del acto o contrato"				
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir'; */			
				displayObject('fecha1',true);
				displayObject('fecha2',false);	
				displayObject('fecha3',false);				
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "2":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1', true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "3":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "4":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "5":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "6":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "7":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "8":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "9":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "10":				
				/*document.getElementById('obligacionDIV').style.visibility = 'hidden';
				document.getElementById('obligacionDIV').style.display = 'none';					
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Derecho de Retención :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Derecho de Retención';*/
				displayObject('fecha1',false);
				displayObject('fecha2',true);
				displayObject('fecha3',false);
				displayObject('terminos1',false);
				displayObject('terminos2',true);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="none";  
				document.getElementById("tittipoacto").style.display="none"; 
				document.getElementById("tipoContOb").style.display="none";
				document.getElementById("titdate2").style.display="none"; 
				document.getElementById("datepicker4").style.display="none";
				document.getElementById("titdate3").style.display="none"; 
				document.getElementById("datepicker5").style.display="none";
				document.getElementById("titterminos").style.display="none"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="none";
				displayObject('_modotrosgarantia',false);
				displayObject('_noEditablemodotrosgarantia',true);
				break;
			case "11":
				/*
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de celebración del acto o contrato :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> Términos y Condiciones del Acto o Contrato de la Garantía Mobiliaria que desee incluir';*/
				displayObject('fecha1',true);
				displayObject('fecha2',false);
				displayObject('fecha3',false);
				displayObject('terminos1',true);
				displayObject('terminos2',false);
				displayObject('terminos3',false);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',false);
				break;
			case "12":
				/*document.getElementById('obligacionDIV').style.visibility = 'hidden';
				document.getElementById('obligacionDIV').style.display = 'none';					
				document.getElementById('fechaCeleb').innerHTML = '<span class="textoGeneralRojo"> * Fecha de surgimiento del Privilegio Especial :</span>';
				document.getElementById('terIDcond').innerHTML = '<span class="textoGeneralRojo"> * Fundamento Legal del cual surge el Privilegio Especial';*/
				displayObject('fecha1',true);
				displayObject('fecha2',true);
				displayObject('fecha3',true);
				displayObject('terminos1',true);
				displayObject('terminos2',true);
				displayObject('terminos3',true);
				//document.getElementById("titulocopia").style.display="block";
				//document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("tipoContOb").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker4").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker5").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				displayObject('_modotrosgarantia',true);
				displayObject('_noEditablemodotrosgarantia',true);
				break;
				
		}
	}
	
 	function selecciona(frm) {
 		  for (i = 0; ele = frm.modtipobien.options[i]; i++){
 			  
 		   	  ele.selected = true;
 		  }
 		  getObject('idTipoBienAll').value="true";
 			} 
 	 	
 		function desSelecciona(frm) {
 		  for (i = 0; ele = frm.modtipobien.options[i]; i++){
 		   	  ele.selected = false;
 		  }
 		  getObject('idTipoBienAll').value="false";
 			} 	
 		
 		function fechasCorrectas(){
 			
 		}
</script>