	function init(idEmpresa){
		
		DomiciliosService.getCombosEstaticos(setSelectEstaticos);
		
		if(idEmpresa != ''){
	    DomiciliosService.getListaTiposDomicilio(idEmpresa, setListaTiposDomicilio); 
		}
	       
	    
    }
	
	
	var listaEstadoTO;

	function setListaTiposDomicilio(domiciliosNacionales){
		
		dwr.util.removeAllOptions("tipoDomicilio");
		var listaTiposDomicilio = domiciliosNacionales.listaTiposDomicilio;	
	    dwr.util.addOptions("tipoDomicilio", listaTiposDomicilio, "idTipoDomicilio", "descTipoDomicilio");
		
	    var tipoDomicilio = dwr.util.getValue("tipoDomicilio");	
	    
	    validaChkFiscal(tipoDomicilio);
	}
	
	
	
	function borrar(idDomicilio, tipoDomicilio){
	
		var formulario = document.getElementsByName("domiciliosForm")[0];
		document.domiciliosForm.idDomicilio.value = idDomicilio;
		document.domiciliosForm.forward.value = "self";
		if(tipoDomicilio == '26'){
			
			muestraMensaje("No se puede borrar el domicilio, sólo se puede editar.", "tache.png");
			
		}else{
			
				document.domiciliosForm.action = "/portal/PortalController";
				document.domiciliosForm.Funcion.value = "CiudadanoDomiciliosNacionales"
				document.domiciliosForm.OperacionCatalogo.value = "AL"
				document.domiciliosForm.idDomicilio.value = idDomicilio;
				document.domiciliosForm.idTipoDomicilio.value = tipoDomicilio;
				document.domiciliosForm.idOperacion.value = "D";			
				document.domiciliosForm.submit();
			
		}
		
		
		
	}
	
	
	
	// Delegaciones
	function getListaMunicipDeleg(estado){

		DomiciliosService.getListaMunicipDeleg({estado:estado}, setListaMunicipDeleg);
		
	}
	
	
	function setListaMunicipDeleg(domiciliosNacionales){ 
		
		var idDelegacion = dwr.util.getValue("idDelegacion");
		dwr.util.setValue('cp', '');
				
		var listaMunicipDeleg = domiciliosNacionales.listaMunicipDeleg;		
		
		populateCombo('delegacion', listaMunicipDeleg, 'cveMunicipDeleg', 'descMunicipDeleg',  getDefaultDelegacionArray(), idDelegacion );
		resetCombo('colonia', getDefaultColoniaArray(), 'idColonia', 'descColonia')

		dwr.util.setValue('delegacion', '');
		
	}
	
	
	
	
	function setListaMunicipDelegConsulta(domiciliosNacionales){ 
		
		var idDelegacion = dwr.util.getValue("idDelegacion");
		var idEstado = dwr.util.getValue("idEstado");
		var listaMunicipDeleg = domiciliosNacionales.listaMunicipDeleg;		
		
		populateCombo('delegacion', listaMunicipDeleg, 'cveMunicipDeleg', 'descMunicipDeleg',  getDefaultDelegacionArray(), idDelegacion );
		resetCombo('colonia', getDefaultColoniaArray(), 'idColonia', 'descColonia')
		
		
		if(domiciliosNacionales.domicilioTO.colonia != null){
			DomiciliosService.getListaColonias({estado:idEstado, delegacion:idDelegacion}, setListaColoniasConsulta);
			
		}
		if(domiciliosNacionales.domicilioTO.localidad  != null){
			DomiciliosService.getListaLocalidades({estado:idEstado, delegacion:idDelegacion}, setListaLocalidadesConsulta);
		}

		
		
	}
	
	
	// Colonias
	
	function getListaColonias(delegacion){
		
		var estado = dwr.util.getValue("estado");
		dwr.util.setValue('cp', '');
		DomiciliosService.getListaColonias({estado:estado, delegacion:delegacion}, setListaColonias);
		
	}
	
	function getListaColoniasPorIdDomicilio(delegacion, estado){
		
		DomiciliosService.getListaColonias({estado:estado, delegacion:delegacion}, setListaColonias);
		
	}
	
	function getListaLocalidadesPorIdDomicilio(delegacion, estado){
		
		DomiciliosService.getListaLocalidades({estado:estado, delegacion:delegacion}, setListaLocalidades);
		
	}
	
	
	function setListaColonias(domiciliosNacionales){
		
		var idColonia = dwr.util.getValue("idColonia");
		
	    populateCombo('colonia', domiciliosNacionales.listaColonias, 'idColonia', 'descColonia',  getDefaultColoniaArray(), idColonia );
		
	}
	
	function setListaColoniasConsulta(domiciliosNacionales){
		
		var idColonia = dwr.util.getValue("idColonia");
		
		var estado = dwr.util.getValue("idEstado");

		dwr.util.setValue('estado', estado );
		
	    populateCombo('colonia', domiciliosNacionales.listaColonias, 'idColonia', 'descColonia',  getDefaultColoniaArray(), idColonia ); 
		
	}
	
// Localidades
	
	function getListaLocalidades(delegacion){
		
		var estado = dwr.util.getValue("estado");
		dwr.util.setValue('cp', '');
		
		DomiciliosService.getListaLocalidades({estado:estado, delegacion:delegacion}, setListaLocalidades);
		
	}
	
	
	function setListaLocalidades(domiciliosNacionales){
		
		var idLocalidad = dwr.util.getValue("idLocalidad");
		
		
		
		 populateCombo('localidad', domiciliosNacionales.listaLocalidades, 'idLocalidad', 'descLocalidad',  getDefaultLocalidadArray() , idLocalidad);	
		
	}
	
	
		function setListaLocalidadesConsulta(domiciliosNacionales){
		
			var idLocalidad = dwr.util.getValue("idLocalidad");
			
			var estado = dwr.util.getValue("idEstado");
	
			dwr.util.setValue('estado', estado );
			
			 populateCombo('localidad', domiciliosNacionales.listaLocalidades, 'idLocalidad', 'descLocalidad',  getDefaultLocalidadArray() , idLocalidad);	
		
	}
	
		// TODO DEV0163
	
		var operacion;
		var forward;
		
		
		
		
		function insertDomicilio(op, forwd){
			
			operacion = op;
			forward = forwd;
			
			var tipoDomicilio = dwr.util.getValue("tipoDomicilio");
			var estado = dwr.util.getValue("estado");
			
			
			if(tipoDomicilio == '26'){
				
				
				if(estado != ''){
					DomiciliosService.validaEstado(estado, listaEstadoTO, sendDomicilio)
					}else{
						
						muestraMensaje("Debe capturar el estado  ", "tache.png");
					}
				
			}else{
				
				enviar(operacion, forward);
				
			}
			
			
			
			
		}
		
		

	function cargaMensajeRestringidos(){
				
		var mensaje = "Por el momento, el Estado seleccionado se encuentra realizando las gestiones necesarias para realizar " 
			
			mensaje+= "la inscripción en el Registro Público de Comercio en tiempo real a través de <strong>tuempresa.gob.mx. </strong>"
				
			mensaje+= "Usted podrá constituir su empresa en el Estado seleccionado una vez que concluyan dichas gestiones. "
	
			mensaje+= "Actualmente las entidades federativas en las que usted puede constituir una empresa son: " 
				 
			mensaje+= "<strong>Campeche, Coahuila, Distrito Federal, Estado de México, Guanajuato,  Morelos, Nuevo León, San Luis Potosí, y Sinaloa.</strong>"
					
			writetolayer('area_comunicacion','<table cellpadding="2" class="CajaDenominacionAzul" cellspacing="1" width="100%"><tr><td class="texto_general" colspan="2"></td></tr><tr><td><img src="/portal/imgs/alerta.gif" border="0" /></td><td align="center" class="textoGeneralAzul">'+mensaje+'</td></tr><tr><td class="textoGeneralAzul" colspan="2">&nbsp;</td></table>');
			
			}
	
	function sendDomicilio(flag){
		
		if(flag){
		
			enviar(operacion, forward);
			
		}else{
	
			tb_show(null, "/portal/jsp/domicilio/modalDomicilios.jsp?keepThis=true&TB_iframe=true&modal=true&height=200&width=500" , false);	
			
		}
		
	}
		
		
	
	function validaChkFiscal(tipoDomicilio)
    {
		
	 var spanChkFiscal = document.getElementById("spanChkFiscal"); 
	 
        if (tipoDomicilio == 26){
        	spanChkFiscal.style.display='block';
        	document.getElementById("chkFiscal").checked = true;
        }else{
        	
        	spanChkFiscal.style.display='none';
        }
    } 
	
	
	
	function getCodigoPostal(input){
		

		if(input.id == "colonia"){
			
			resetCombo('localidad', getDefaultLocalidadArray(), 'idLocalidad' , 'descLocalidad');
			DomiciliosService.getCodigoPostal({localidad:null, colonia:input.value}, setCodigoPostal);
			clearDefault('colonia', listaColonias, 'idColonia', 'descColonia',  getDefaultColoniaArray() );
			
		}
		
		if(input.id  == "localidad"){
			
			if(input.value != ''){
				
				resetCombo('colonia', getDefaultColoniaArray(), 'idColonia' , 'descColonia');
				DomiciliosService.getCodigoPostal({localidad:input.value, colonia:null}, setCodigoPostal);	
				
				clearDefault('localidad', listaLocalidades, 'idLocalidad', 'descLocalidad',  getDefaultLocalidadArray() );	
				
			}
									
		}
		
		
	}
	
	function setCodigoPostal(domicilioTO){
		
		dwr.util.setValue('cp', domicilioTO.cp);
		
	}
	
	
	
	
	
	function consultarDomicilioEmpresa( idDomicilio , idTipoDomicilio){
	
		DomiciliosService.consultaDomicilio({idDomicilio:idDomicilio, idTipoDomicilio:idTipoDomicilio}, setDomiclioConsulta );
		
	}
	
	
	function populateForm( domicilioTO ){

		DomiciliosService.consultaDomicilio(domicilioTO, setForm );		
		
	}
	
	
	function setEstado(estado){
		
		var estado = estado;
	}
	
	
	
	
	function setForm(domicilioTO){
		
	dwr.util.setValue('cp', domicilioTO.cp );
	dwr.util.setValue('idDomicilio', domicilioTO.idDomicilio );
	dwr.util.setValue('estado', domicilioTO.estado );
	dwr.util.setValue('calle', domicilioTO.calle );
	dwr.util.setValue('numeroExterior', domicilioTO.numeroExterior );
	dwr.util.setValue('numeroInterior', domicilioTO.numeroInterior );
	dwr.util.setValue('tipoVialidad', domicilioTO.tipoVialidad );
	dwr.util.setValue('tipoInmueble', domicilioTO.tipoInmueble );
	dwr.util.setValue('entreCalle', domicilioTO.entreCalle );
	dwr.util.setValue('entreCalle2', domicilioTO.entreCalle2 );
	dwr.util.setValue('puntoReferencia', domicilioTO.puntoReferencia );
	dwr.util.setValue('callePosterior', domicilioTO.callePosterior );
	dwr.util.setValue('caracteristicasDomicilio', domicilioTO.caracteristicasDomicilio );
	dwr.util.setValue('tipoDomicilio', domicilioTO.idTipoDomicilio );
	
	
	
	dwr.util.setValue('idOperacion', 'U' );
	}

	
	function setDomiclio(domicilioNacionalTO){
		

		var estado = domicilioNacionalTO.domicilioTO.estado;
		var delegacion = domicilioNacionalTO.domicilioTO.delegacion;
		
		DomiciliosService.getListaMunicipDeleg({estado:estado}, setListaMunicipDeleg);
		
		dwr.util.setValue('idDelegacion', domicilioNacionalTO.domicilioTO.delegacion );
		dwr.util.setValue('idColonia', domicilioNacionalTO.domicilioTO.colonia );
		dwr.util.setValue('idLocalidad', domicilioNacionalTO.domicilioTO.localidad );
		
		if(domicilioNacionalTO.domicilioTO.colonia != null){
			DomiciliosService.getListaColonias({estado:estado, delegacion:delegacion}, setListaColonias);
			//resetCombo('localidad', getDefaultLocalidadArray(), 'idLocalidad' , 'descLocalidad');
			
		}
		if(domicilioNacionalTO.domicilioTO.localidad  != null){
			getListaLocalidadesPorIdDomicilio(domicilioNacionalTO.domicilioTO.delegacion, domicilioNacionalTO.domicilioTO.estado); 
			//resetCombo('colonia', getDefaultColoniaArray(), 'idColonia' , 'descColonia');
		}
		
		dwr.util.removeAllOptions("tipoDomicilio");
	    dwr.util.addOptions("tipoDomicilio", domicilioNacionalTO.listaTiposDomicilio, "idTipoDomicilio", "descTipoDomicilio");
		
		setForm(domicilioNacionalTO.domicilioTO)
		
		validaChkFiscal(domicilioNacionalTO.domicilioTO.tipoDomicilio);
		
	}
	
	
	
	
	
	function setDomiclioConsulta(domicilioNacionalTO){
		
		//dwr.util.setValue('idDelegacion', domicilioNacionalTO.domicilioTO.delegacion );
		//dwr.util.setValue('idColonia', domicilioNacionalTO.domicilioTO.colonia );
		//dwr.util.setValue('idLocalidad', domicilioNacionalTO.domicilioTO.localidad );
		//dwr.util.setValue('idEstado', domicilioNacionalTO.domicilioTO.estado );
		
		
		dwr.util.removeAllOptions("tipoDomicilio");
	    dwr.util.addOptions("tipoDomicilio", domicilioNacionalTO.listaTiposDomicilio, "idTipoDomicilio", "descTipoDomicilio");
		
		setForm(domicilioNacionalTO.domicilioTO)
		
		validaChkFiscal(domicilioNacionalTO.domicilioTO.tipoDomicilio);
		
		//DomiciliosService.getListaMunicipDeleg({estado:domicilioNacionalTO.domicilioTO.estado}, setListaMunicipDelegConsulta);
		//
		// Modificacion para traer el domicilio.
		//
		getDomicilioNacional(domicilioNacionalTO.domicilioTO.colonia, domicilioNacionalTO.domicilioTO.localidad,
				domicilioNacionalTO.domicilioTO.cp
				);
		
	}
	
	/**
	 * Busca las listas correspondientes al codigo postal dado para popular los combos 
	 * @param codigoPostal
	 * @return
	 */
	
	function buscarDomicilioNacionalporCP(codigoPostal){
		
		
		if(codigoPostal.length == 5){
			
			document.images['cpImage'].src="/portal/imgs/gif_flechitasnegras.gif";
			DomiciliosService.buscarPorCodigoPostal({cp:codigoPostal, colonia:null, localidad:null, delegacion:null}, setDomicilioNacional);		
	
			
		}
		
		if(codigoPostal.length == 0){
			dwr.util.setValue('estado', '');
			resetDinamicCombos()	
					
		}
		
		
	}
	
	
	function setDomicilioNacional(domiciliosNacionales) {
		
		
	if(domiciliosNacionales.domicilioTO != null){
	
		
	var delegacion = domiciliosNacionales.domicilioTO.delegacion;
	var estado = domiciliosNacionales.domicilioTO.estado;
	
	var listaMunicipDeleg = domiciliosNacionales.listaMunicipDeleg;
	var listaColonias = domiciliosNacionales.listaColonias;	
	var listaLocalidades = domiciliosNacionales.listaLocalidades;	
		
		 //Popula Combos 
		 populateCombo('delegacion', listaMunicipDeleg, 'cveMunicipDeleg', 'descMunicipDeleg',  getDefaultDelegacionArray() );
		 populateCombo('colonia', listaColonias, 'idColonia', 'descColonia',  getDefaultColoniaArray() );
		 populateCombo('localidad', listaLocalidades, 'idLocalidad', 'descLocalidad',  getDefaultLocalidadArray() );	
		
	    // Asigna valor a estado y municipo
	    dwr.util.setValue('estado', estado);
	    dwr.util.setValue('delegacion', delegacion);
	    
	    muestraMensaje("Código postal válido, seleccione una colonia.", "Palomita.gif");
	    document.images['cpImage'].src="/portal/imgs/gif_palomitaverde.gif";
	    return;
	    
	    
	}else{
		
		muestraMensaje(domiciliosNacionales.mensaje, "tache.png");
		document.images['cpImage'].src="/portal/imgs/tache.png";
		var estado = dwr.util.setValue("estado", '');
		 dwr.util.setValue('cp', null);
		 resetDinamicCombos();
		return;
		
	}
	    
	}
	
	
	
	function populateCombo(id, list, key, value, defaultArray, valueOf){
		
		dwr.util.removeAllOptions(id);
		dwr.util.addOptions(id,  defaultArray ,key, value);
	    dwr.util.addOptions(id, list, key, value);
		dwr.util.setValue(id, valueOf );
		
	}
	
	

	function clearDefault(id, list, key, value, defaultArray){
		
		dwr.util.removeAllOptions(id);
	    dwr.util.addOptions(id, list, key, value);
		
	}
	
	
	function resetCombo(id, defaultArray, key, value){
		dwr.util.removeAllOptions(id);
		dwr.util.addOptions(id,defaultArray,key,value);
		
	}
	
	function clearCombo(id, defaultArray, key, value){
		dwr.util.removeAllOptions(id);	
	}
	
	
	function resetDinamicCombos(){
		
		resetCombo('delegacion', getDefaultDelegacionArray(), 'cveMunicipDeleg' , 'descMunicipDeleg');
		resetCombo('colonia', getDefaultColoniaArray(), 'idColonia' , 'descColonia');
		resetCombo('localidad', getDefaultLocalidadArray(), 'idLocalidad' , 'descLocalidad');
		
	}
	
	
	function getDefaultDelegacionArray(){		
		var defaultArray =  [{cveMunicipDeleg:'', descMunicipDeleg: 'Seleccionar Municipio/Delegacion'}] ;                     
		return defaultArray;		
	}
	
	function getDefaultColoniaArray(){		
		var defaultArray =   [{idColonia:'', descColonia: 'Seleccionar Colonia'}] ;                     
		return defaultArray;		
	}
	
	function getDefaultLocalidadArray(){		
		var defaultArray =   [{idLocalidad:'', descLocalidad: 'Seleccionar Localidad'}] ;                     
		return defaultArray;		
	}
	      
	
	function getDefaultTipoInmuebleArray(){		
		var defaultArray =   [{idTipoInmueble: '', descTipoInmueble: 'Seleccionar Tipo de Inmueble'}] ;                     
		return defaultArray;		
	}
	
	function getDefaultTipoVialidadArray(){		
		var defaultArray =  [{idTipoVialidad: '', descTipoVialidad: 'Seleccionar Tipo de Vialidad'}];                     
		return defaultArray;		
	}

	function getDefaultEstadoArray(){		
		var defaultArray =  [{cveEstado:'', descEstado: 'Seleccionar Estado'}];                     
		return defaultArray;		
	}
	
	
	function getDefaultTiposDomicilioArray(){		
		

		var defaultArray =  [{idTipoDomicilio:'', descTipoDomicilio: 'Seleccionar Tipo de Domicilio'}];                     
		return defaultArray;		
	}
	
	
	
	function validaCapcha(id){
		
//		document.images['capchaImage'].src="/portal/imgs/gif_flechitasnegras.gif";
		var passline = dwr.util.getValue("passline");
		var passlineEnc = dwr.util.getValue("passlineEnc");
		
		if(id == "1"){
		DomiciliosService.validaCapcha(passline, passlineEnc, setCapchaImage);
		}else{
			DomiciliosService.validaCapcha(passline, passlineEnc, setCapchaImageView);
		}
		
//		dwr.util.setValue('estado', estado );
		
		
	}
	
	
	function setCapchaImage(capcha){
		
		
		dwr.util.setValue("passlineEnc", capcha.passlineValueEncoded );
		
		var formulario = document.getElementsByName("datosForm")[0];
		
		formulario.action="/portal/Controller?operacionCatalogo=CR&servicio=CiudadanoDenominacion&metodo=insert"	
		
		if(capcha.respuesta != 3){
			
			document.images['capchaImage'].src='/portal/PassImageServlet/'+ capcha.passlineValueEncoded;
			dwr.util.setValue("passline", "");
			document.getElementById("passline").focus();
			
		}else{
			
			if (!validaDatosComplementarios()){
				
				 muestraMensaje ("Faltan Datos complementarios ", "tache.png")
			
				
			}else{
				
				
				formulario.submit();	
				
			}	
		}
	
		
	}
	
	
	function setCapchaImageView(capcha){
		
		
		dwr.util.setValue("passlineEnc", capcha.passlineValueEncoded );
		
		var formulario = document.getElementsByName("datosForm")[0];
		
		formulario.action="/portal/Controller?operacionCatalogo=CR&servicio=CiudadanoDenominacion&metodo=insert"	
		
		if(capcha.respuesta != 3){
			
			document.images['capchaImage'].src='/portal/PassImageServlet/'+ capcha.passlineValueEncoded;
			dwr.util.setValue("passline", "");
			document.getElementById("passline").focus();
		}else{
			
			formulario.submit();	
		}
	
		
	}
	
	
	/**
	 * 
	 * @param domiciliosNacionales
	 * @return
	 * Carga de Combos Estaticos
	 * 
	 */

	function setSelectEstaticos(domiciliosNacionales){
		
		listaEstadoTO =domiciliosNacionales.listaEstados;
		 
	    populateCombo('estado', domiciliosNacionales.listaEstados, 'cveEstado', 'descEstado', getDefaultEstadoArray() ); 
	    populateCombo('tipoInmueble', domiciliosNacionales.listaTiposInmueble, 'idTipoInmueble', 'descTipoInmueble', getDefaultTipoInmuebleArray() ); 
	    populateCombo('tipoVialidad', domiciliosNacionales.listaTiposVialidad, 'idTipoVialidad', 'descTipoVialidad', getDefaultTipoVialidadArray() );
	    resetDinamicCombos()
		
	}
	 
	 function muestraMensaje (mensaje, icon){
		 
		
		 writetolayer('area_comunicacion','<table cellpadding="2" class="CajaDenominacionAzul" cellspacing="1" width="450"><tr><td class="texto_general" colspan="2"></td></tr><tr><td width="16px"><img src="/portal/imgs/'+icon+'" border="0" /></td><td align="center" class="texto_general">'+mensaje+'</td></tr><tr><td class="texto_general" colspan="2">&nbsp;</td></table>');
		 
		 
	 }