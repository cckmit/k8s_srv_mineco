<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/BusquedaDwrAction.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/dwr/busquedaDWR.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones/validaciones.js"></script>

<script type="text/javascript">

	let datos = false;

  function tramitesJSP(persona){
		checkUser(persona);
		
	  var ruta = '${pageContext.servletContext.contextPath}';	
	  certificacionDwr(ruta);
  }
  
  function certificacion(garantia, tramite) {	
		
		var ruta = '/Rug/home/certificaTramite.do?idGarantia=' + garantia + '&idTramite='+tramite;   
		
		if( datos){
			
			window.open(ruta, "_blank");
		}else{
			var ruta = '/Rug/home/certificaTramite.do?idGarantia=' + garantia + '&idTramite='+tramite;    
			// obtener el costo de una certificacion: tipo_tramite=5
			$.ajax({
				url: '<%= request.getContextPath() %>/rs/tipos-tramite/5',
				success: function(result) {
					MaterialDialog.dialog(
						"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100).toFixed(2) + ", �est� seguro que desea continuar?",
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
									modalClose:true,
									callback:function(){																
										window.open(ruta, "_blank");
									}
								}
							}
						}
					);
													
				}
			});
		}
	}


	function checkUser(user) {
		if (user == 17381) {
			datos = true;
		}
	}



  
</script>
