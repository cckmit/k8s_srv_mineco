<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/interface/OperacionesDwrAction.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/dwr/operacionesJS.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/interface/BusquedaDwrAction.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dwr/busquedaDWR.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/validaciones/validaciones.js"></script>
<script language="javascript"  type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>

<script type="text/javascript">

	function submitenter(myfield, e, idPersona) {
			var keycode;
			if (window.event) keycode = window.event.keyCode;
			else if (e) keycode = e.which;
			else return true;

			if (keycode == 13) {
				busquedaJSP(idPersona);
				return false;
			} else
				return true;
		}

		function submitenterNum(myfield, e, idPersona) {
			var keycode;
			if (window.event) keycode = window.event.keyCode;
			else if (e) keycode = e.which;
			else return true;

			if (keycode == 13) {
				busquedaJSP(idPersona);
				return false;
			} else {
				var key = (document.all) ? e.keyCode : e.which;
				return (key <= 13 || (key >= 48 && key <= 57));
			}
		}

	function aceptaalfa(evt) {
	    console.log(evt);
			var charCode = (evt.which) ? evt.which : event.keyCode
			if (charCode > 31 && (charCode < 48 || charCode > 57) &&
				(charCode < 65 || charCode > 90) &&
				(charCode < 97 || charCode > 122) &&
				(charCode < 209 || charCode > 249)
			)
				return false;
			return true;
		}

		function muestraInfo(idGarantia, idTramite) {
			OperacionesDwrAction.detalleGarantiaFinal(idGarantia, idTramite, showGarantia);
		}

		function showGarantia(message) {
			if (message.codeError == 0) {
				MaterialDialog.alert(
					message.message, {
					title: 'Detalle Garantia', // Modal title
					buttons: { // Receive buttons (Alert only use close buttons)
						close: {
							text: 'close', //Text of close button
							className: 'red', // Class of the close button
							callback: function () { // Function for modal click
								//alert("hello")
							}
						}
					}
				}
				);
			}
			displayLoader(false);

		}

		function checkText(idPersona,tipoBusqueda){

	        if($('#invoice').val().length == 0 && $('#set').val().length == 0){
	            MaterialDialog.alert(
	                'Complete los criterios de b&uacute;squeda',{
	                    title: '<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
                        buttons: {
	                        close: {
	                            text: 'cerrar',
                                className: 'red'
                            }
                        }
                    }
                );
	            return false;
            }

            var ruta = '${pageContext.servletContext.contextPath}';
            if (checkUser(idPersona)) {
                searchInvoiceform(ruta, idPersona, tipoBusqueda, 11);
            } else {
                $.ajax({
                    url: '<%= request.getContextPath() %>/rs/tipos-tramite/11',
                    success: function (result) {
                        MaterialDialog.dialog(
                            "El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100)
                                .toFixed(2) + ", Está seguro que desea continuar?", {
                                title: '<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
                                buttons: {
                                    // Use by default close and confirm buttons
                                    close: {
                                        className: "red",
                                        text: "cancelar"
                                    },
                                    confirm: {
                                        className: "indigo",
                                        text: "aceptar",
                                        modalClose: true,
                                        callback: function () {
                                            searchInvoiceform(ruta, idPersona, tipoBusqueda, 11);
                                        }
                                    }
                                }
                            }
                        );
                    }
                });
            }

        }



		function busquedaJSP(idPersona, tipoBusqueda) {
			if (!$('#idGarantia').val() && !$('#nombreOtorgante').val() && !$('#rfcOtorgante').val() && !$('#curpOtorgante').val() && !$('#serial').val()) {
				MaterialDialog.alert(
					'Debe ingresar al menos un criterio para realizar la consulta.', {
					title: '<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
					buttons: {
						close: {
							text: 'cerrar',
							className: 'red'
						}
					}
				}
				);
				return false;
			}

			var ruta = '${pageContext.servletContext.contextPath}';

			// obtener el costo de una consulta: tipo_tramite=11

			console.log(idPersona);
			if (checkUser(idPersona)) {
				busquedaDwr(ruta, idPersona, tipoBusqueda, 11);
			} else {
				$.ajax({
					url: '<%= request.getContextPath() %>/rs/tipos-tramite/11',
					success: function (result) {
						MaterialDialog.dialog(
							"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100)
								.toFixed(2) + ", Está seguro que desea continuar?", {
							title: '<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
							buttons: {
								// Use by default close and confirm buttons
								close: {
									className: "red",
									text: "cancelar"
								},
								confirm: {
									className: "indigo",
									text: "aceptar",
									modalClose: true,
									callback: function () {
										busquedaDwr(ruta, idPersona, tipoBusqueda, 11);
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
				return  true;
			}
		}


		function busquedaJudJSP(idPersona, tipoBusqueda) {
			if (!$('#idGarantia').val() && !$('#nombreOtorgante').val() && !$('#rfcOtorgante').val() && !$('#curpOtorgante')
				.val() && !$('#serial').val()) {
				MaterialDialog.alert(
					'Debe ingresar al menos un criterio para realizar la consulta.', {
					title: '<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>',
					buttons: {
						close: {
							text: 'cerrar',
							className: 'red'
						}
					}
				}
				);
				return false;
			}

			var ruta = '${pageContext.servletContext.contextPath}';

			MaterialDialog.alert(
				'<p style="text-align: justify; text-justify: inter-word;">Recuerde que las consultas realizadas quedaran en la bitacora del sistema.</p>', {
				title: '<table><tr><td width="10%"><i class="medium icon-yellow material-icons">warning</i></td><td style="vertical-align: middle; text-align:left;">Alerta</td></tr></table>', // Modal title
				buttons: { // Receive buttons (Alert only use close buttons)
					close: {
						text: 'aceptar', //Text of close button
						className: 'green', // Class of the close button
						callback: function () { // Function for modal click
							busquedaDwr(ruta, idPersona, tipoBusqueda, 32);
						}
					}
				}
			});

		}

		function validaCadena() {
			if ((getObject('numOperacion').value == "0") || (getObject('numOperacion').value == "")) {
				displayAlert(true, 'Informacion Incompleta', 'Debe Introducir la Cadena unica de Datos');
			} else {
				sendForm();
			}
		}

		function sendForm() {
			document.getElementById("bFirmar").value = "Enviando";
			document.getElementById("bFirmar").disabled = true;
			getObject('busquedaCert').submit();
		}

		//setActiveTab('unoMenu');
		//setActiveTabBusqueda('unoMenuBusqueda');
		//$("#unoMenu").attr("class","linkSelected");

		function IsNumber(evt) {
			var key = (document.all) ? evt.keyCode : evt.which;
			if (key == 13) {
				validaCadena();
				return false;
			}
			return (key <= 13 || (key >= 48 && key <= 57));
		}

		function showBoleta() {
			var URL = "<%=request.getContextPath()%>/pdf.pdo";
			window.open(URL, "_blank");
			//abrirCentrado(URL,"Boleta","500","500"); 
		}

	//setActiveTab('unoMenu');
	//setActiveTabBusqueda('dosMenuBusqueda');
</script>