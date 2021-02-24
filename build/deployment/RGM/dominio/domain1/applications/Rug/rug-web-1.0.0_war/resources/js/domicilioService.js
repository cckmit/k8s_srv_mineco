	var idCp="cp";
	var idEstado="estado";
	var idDelegacion="delegacion";
	var idColonia="colonia";
	var idLocalidad="localidad";
	var idCalle="idCalle";
	var idNumExt="idNumExt";
	var idNumInt="idNumInt";
	var idEmail="idEmail";
	var idTelefono="idTelefono";
	var idLada="idLada";
	
	var oCp="";
	var oEstado="";
	var oDelegacion="";

	var idFormulario="idFrmDomicilioDefault";
	
	function setidFormulario(idForm){
		idFormulario=idForm;
	}
	
	
	function init(cp, estado, delegacion, colonia, localidad){
		oCp=cp;
		oEstado=estado;
		oDelegacion=delegacion;
		oColonia=colonia;
		oLocalidad=localidad;
	}
	
	function getMunicipios(campo){
			var estado= {cvePais:"", cveEstado:"", descEstado:"" };
			estado.cveEstado= campo.options[campo.selectedIndex].value;
			if(estado.cveEstado!=oEstado){
				oEstado=estado.cveEstado;
				oDelegacion="";
				DomiciliosDwrAction.getMunicipios(estado, addMunicipios);
			}
	}
	
	function addMunicipios(data){
		resetGrupo([idDelegacion,idColonia,idLocalidad],[delegacion,colonia,localidad]);
		dwr.util.addOptions(idDelegacion, data, 'cveMunicipDeleg', 'descMunicipDeleg');
	}

	var municipio={cveEstado:"", cveMunicipDeleg:"", descMunicipDeleg:"" };
	function getColoniasLocalildades(campo, estado){
			municipio.cveMunicipDeleg=campo.options[campo.selectedIndex].value;
			municipio.cveEstado=estado.options[estado.selectedIndex].value;
			if(municipio.cveMunicipDeleg!=oDelegacion){
				oDelegacion=municipio.cveMunicipDeleg;
				DomiciliosDwrAction.getColonias(municipio, addColonias);
			}
	}
	
	function addColonias(data){
		DomiciliosDwrAction.getLocalidades(municipio, addLocalidades);
		resetGrupo([idColonia],[colonia]);
		dwr.util.addOptions(idColonia, data, 'idColonia', 'descColonia');
	}
	
	function addLocalidades(data){
		resetGrupo([idLocalidad],[localidad]);
		dwr.util.addOptions(idLocalidad, data, 'idLocalidad', 'descLocalidad');
	}
	
	function _buscarDomicilio(campo){
		if(campo.value.length==5){
			var domicilio={cp:""};
			domicilio.cp=campo.value;
			if(domicilio.cp!=oCp){
				oCp=domicilio.cp;
				DomiciliosDwrAction.getCodigoPostalDomicilio(domicilio, addDomicilio);
			}
		}else{
			if(campo.value!=oCp){
				resetGrupo([idDelegacion,idColonia,idLocalidad],[delegacion,colonia,localidad]);
				BuscaSelectOpcion(document.getElementById(idFormulario).estado,"");
				oCp="";
			}
		}
	}
	
	function _addColonias(data){
		resetGrupo([idColonia],[colonia]);
		dwr.util.addOptions(idColonia, data, 'idColonia', 'descColonia');
	}
	
	function addDomicilio(data){
		if(data.domicilioTO.claveEstado!=null){
			addMunicipios(data.listMunicipios);
			if(data.listaColonias!=null){
				_addColonias(data.listaColonias);
			}else{
				resetGrupo([idColonia],[colonia]);
			}
			if(data.listLocalidades!=null){
				addLocalidades(data.listLocalidades);
			}else{
				resetGrupo([idLocalidad],[localidad]);
			}
			BuscaSelectOpcion(document.getElementById(idFormulario).estado,data.domicilioTO.claveEstado);
			oEstado=data.domicilioTO.claveEstado;
			BuscaSelectOpcion(document.getElementById(idFormulario).delegacion,data.domicilioTO.claveDelegacion);
			oDelegacion=data.domicilioTO.claveDelegacion;
			addImageCp();
		}else{	
			resetCP();
			document.images['cpImage'].src="/portal/imgs/tachee.gif";
		}
	}
	
	function getCodigoPostalColonia(campo){
		if(campo.selectedIndex!=0){
			var colonia = {idColonia:""};
			colonia.idColonia=campo.options[campo.selectedIndex].value;
			DomiciliosDwrAction.getCodigoC(colonia, addCodigoPostal);
		}else{
			resetCP();
		}
		
	}
	
	function addCodigoPostal(data){
		if(data.cp!=""){
			oCp=data.cp;
			document.getElementById(idCp).value=data.cp;
		}
	}
	
	function getCodigoPostalLocalidad(campo){
		if(campo.selectedIndex!=0){
			var localidad = {idColonia:""};
			localidad.idLocalidad=campo.options[campo.selectedIndex].value;
			DomiciliosDwrAction.getCodigoL(localidad, addCodigoPostalLocalidad);
		}else{
			resetCP();
		}
		
	}
	function addCodigoPostalLocalidad(data){
		if(data.cp!=""){
			oCp=data.cp;
			document.getElementById(idCp).value=data.cp;
		}
	}
	
	function resetCP(){	
		if((document.getElementById(idColonia).selectedIndex==0)&&(document.getElementById(idLocalidad).selectedIndex==0)){
			oCp="";
			document.getElementById(idCp).value="";
			document.images['cpImage'].src="/portal/imgs/pixel_gris.gif";
		}
	}
	
	function resetColonia(){
		document.getElementById(idColonia).selectedIndex=0;
	}
	function resetLocalidad(){
		document.getElementById(idLocalidad).selectedIndex=0;
	}
	
	function reset(id, msg){
		dwr.util.removeAllOptions(id);
		dwr.util.addOptions(id,{"":msg});
	}
	function resetGrupo(ids,msg){
		for(_i=0; _i<parseInt(ids.length); _i++){
			reset(ids[_i],msg[_i]);
		}
	}
	
	function addImageCp(){
		document.images['cpImage'].src="/portal/imgs/gif_palomitaverde.gif";	
	}
	var _cpx;
	var _coloniax;
	var _localidadx;
	var _callex;
	var _numerox;
	var _numeroint;
	function getDomicilioNacional(_colonia, _localidad,_cp, _calle, _numero, _numerointerior){
		_numeroint=_numerointerior;
		_coloniax=_colonia;
		_localidadx=_localidad;
		_cpx= _cp;
		_callex=_calle;
		_numerox=_numero;
		var domicilio={cp:""};
		domicilio.cp=_cp;
		DomiciliosDwrAction.getCodigoPostalDomicilio(domicilio, _addDomicilio);
	}
	
	function _addDomicilio(data){
		if(data.domicilioTO.claveEstado!=null){
			addMunicipios(data.listMunicipios);
			_addColonias(data.listaColonias);
			addLocalidades(data.listLocalidades);
			BuscaSelectOpcion(document.getElementById(idFormulario).estado,data.domicilioTO.claveEstado);
			oEstado=data.domicilioTO.claveEstado;
			BuscaSelectOpcion(document.getElementById(idFormulario).delegacion,data.domicilioTO.claveDelegacion);
			oDelegacion=data.domicilioTO.claveDelegacion;
			addImageCp();
			BuscaSelectOpcion(document.getElementById(idFormulario).colonia,_coloniax);
			BuscaSelectOpcion(document.getElementById(idFormulario).localidad,_localidadx);
			document.getElementById(idCalle).value=_callex;
			document.getElementById(idNumExt).value=_numerox;
			document.getElementById(idCp).value=_cpx;
			document.getElementById(idNumInt).value=_numeroint;
		}else{
			resetCP();
		}
	}
	
	function otroDomicilioNacional(_colonia, _localidad,_cp, _calle, _numero, _email, _telefono, _lada){
		
		if((_cp!="")&&(parseInt(_cp.length)==5)){
			getDomicilioNacional(_colonia, _localidad,_cp, _calle, _numero, _email, _telefono, _lada);
		}else{
			resetGrupo([idDelegacion,idColonia,idLocalidad],[delegacion,colonia,localidad]);
			document.getElementById(idEmail).value="";
			document.getElementById(idTelefono).value="";
			document.getElementById(idCalle).value="";
			document.getElementById(idCp).value="";
			BuscaSelectOpcion(document.getElementById(idFormulario).estado,"");
		}
		
	}
 