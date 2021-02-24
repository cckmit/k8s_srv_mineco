<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/OperacionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/operacionesJS.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/BusquedaOpDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/busquedaMisOpPag.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/thickbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script> 
iniciaPaginacionPendientes('<s:property value="idPersona"/>','1');

	function desactiva(){
    	document.finiciaM.submit();
    	if (document.getElementById("suini")!=null){
			document.getElementById("suini").value = "Enviando";
	    	document.getElementById("suini").disabled = true;
    	}
     	
	}
	
	function runScript(event) {
	    if (event.which == 13 || event.keyCode == 13) {
	        //code to execute here
	        return false;
	    }
	    return true;
	};
	
	function muestraInfo(idTramite){
		OperacionesDwrAction.detalleGarantia(idTramite, showGarantia);
	}
	
	function showGarantia(message){
		 if (message.codeError==0){			 
			 MaterialDialog.alert(
						message.message,
						{
							title:'Detalle Garantia', // Modal title
							buttons:{ // Receive buttons (Alert only use close buttons)
								close:{
									text:'close', //Text of close button
									className:'red', // Class of the close button
									callback:function(){ // Function for modal click
										//alert("hello")
									}
								}
							}
						}
					);
			}
		 displayLoader(false);
		 
	}
	
	function buscarporfiltro(){
		var filtro = getObject('terfiltro').value;
				
		if(filtro!=null && filtro!=''){
			iniciaPaginacionFiltro('<s:property value="idPersona"/>','3',filtro);
		} else {			
			iniciaPaginacionPendientes('<s:property value="idPersona"/>','3');
		}
	}
	
	function activa(){
		if (document.getElementById("suini")!=null){
			document.getElementById("suini").value = "Operaciones de Usuarios Delegados";
			document.getElementById("suini").disabled = false;
		}
	}

	activa();
</script>
