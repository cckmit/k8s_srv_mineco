<%@page import="java.util.Iterator"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/campos.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/dwr/interface/cambioOtorganteService.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/FuncionesDeFechas.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/tooltip.css" />
<script type="text/javascript">
	
	
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




	function fechasCorrectas(){
		var strFI = getObject('datepicker2').value;
		var strFF = getObject('datepicker3').value;
		if ( getObject('datepicker2').value != ''){
			if (!esMenorHoy(strFI)){
				getObject('datepicker2').value = '';
				alert("La Fecha de Celebración del Acto o Contrato debe ser menor o igual a la actual");
				return false;
			}else{
				if ( getObject('datepicker3').value != ''){
					if (!validaFechas(strFI, strFF)){
						getObject('datepicker3').value = '';
						alert("La Fecha de Terminación del Acto o Contrato debe ser mayor o igual a la Fecha de Celebración del Acto o Contrato");
						return false;
					}					
				}
			}
		}
	}


	
	
	function cambiaAbajo(){
		var datepicker1 = getObject('datepicker1').value;
		var cadena = '';
		var valor = document.getElementById('modtipogarantia').value;	
		switch(valor){
			
			case "10":	
				cadena = 'Fecha de surgimiento del Derecho de Retención';
				break;
			case "12":	
				cadena = 'Fecha de surgimiento del Privilegio Especial';
				break;
			default: cadena = 'Fecha de celebración del Acto o Contrato';
		}
		
		if (!esMenorHoy(datepicker1)){
			 getObject('datepicker1').value='';
			alert('La '+cadena+' debe ser menor o igual a la actual');
		}

		
		if(getObject('copia').checked){
			
			getObject('modtipoacto').value=getObject('modtipogarantia').options[getObject('modtipogarantia').selectedIndex].text;
			getObject('datepicker2').value=getObject('datepicker1').value;
			getObject('farectificacion_modotrosterminos').value=getObject('modotrosgarantia').value;
			
			getObject('modtipoacto').readOnly = true;
			getObject('datepicker2').readOnly = true;
			
			getObject('farectificacion_modotrosterminos').readOnly = true;
		}
	}
	
	
	
	
	
	
	function sendForm(){
		document.getElementById("bFirmar").value = "Enviando";
		document.getElementById("bFirmar").disabled = true;		
		document.getElementById('farectificacion').submit();
	}

	
	
	
	
	
	
	
	function isNullOrEmpty(valor){
		if ( valor == null )
			return true;
		for ( i = 0; i < valor.length; i++ )
				if ( valor.charAt(i) != " " )
					return false; 
		return true; 
	}

	
	
	
	
	
	
	function validarSelectMultiple(){
		if (document.getElementById('modtipobien').selectedIndex!=-1){
			return true;
		}else{
			
			return false;
		}
	}

	
	
	
	
	
	
	
	
	
	
	function validar(){
		var dia = new Date();
		var bandera = true;
		var tipoGarantia = document.getElementById('modtipogarantia').value;
		var datepicker1 = getObject('datepicker1').value;
		var montoMaximo = getObject('modmonto').value;
		var descripcionActoContrato = getObject('modtipobien').value;
		var descripciontiposbienes = getObject('tiposbienes').value;
		var tipoActoContrato = getObject('modtipoacto').value;
		var autoridad = getObject('autoridad');
		var cadena = '';

		if (document.getElementById('sepuedecontinuar')!=null){
		
			switch(tipoGarantia){
			
			case "10":	
				cadena = 'Fecha de surgimiento del Derecho de Retención';
				break;
			
			case "12":	
				cadena = 'Fecha de surgimiento del Privilegio Especial';
				break;
		
				
			default: cadena = 'Fecha de celebración del Acto o Contrato';
		}
			
			bandera = true;
			
			
			
		if (tipoGarantia<0){
				alert('El campo Tipo de Garantía Mobiliaria es obligatorio');
				bandera= false;
			}
			
			
		if (!noVacio(datepicker1)){
				alert('El campo '+cadena+' es obligatorio');
				bandera= false;
			}
		
			if (!noVacio(descripcionActoContrato)){
				alert('El campo Tipo de Bienes Muebles objeto de la Garantía Mobiliaria es obligatorio');
				bandera=false;
			}
			if (!noVacio(descripciontiposbienes)){
				alert('El campo Descripción de los Bienes Muebles objeto de la Garantía Mobiliaria es obligatorio');
				bandera=false;
			}
			if (autoridad!=null){
				if (!noVacio(autoridad.value)){
					alert('El campo Persona que solicita o Autoridad que instruye la Inscripción y contenido de la Resolución es obligatorio');
					bandera=false;
				}
			}
			if (!bandera){
				return false;
			}			
			if (!validarSelectMultiple()){				
				alert('Seleccione un Tipo de Bien(es) Mueble(s) Objeto de la Garantía Mobiliaria');
				bandera=false;
			}
			
			
			var validaTx1 = document.getElementById('modotrosgarantia').value;
			if (!noVacio(validaTx1)){
					if(tipoGarantia == '10' || tipoGarantia == '12'){
						alert('El campo Fundamento Legal del cual surge el Derecho de Retención es obligatorio');
						bandera=false;
					}
			}			
			
			switch(tipoGarantia){
			
				case "10":
					bandera = true;
					break;
				case "12":
					bandera = true;
					break;
				default:
					if (!noVacio(tipoActoContrato)){
						alert('El campo Acto o Contrato que crea la Obligación Garantizada es obligatorio');
						bandera= false;
					}
					
					if(bandera){
						  if (!noVacio(getObject('datepicker3').value)){
							    displayMessageConfirmacionGeneral(true,'Confirmaci&oacute;n','¿Desea continuar sin llenar el campo de Fecha de Terminación del Acto o Contrato?');
							    return false;
						    }
					}
			}
			
			
			
		  					
				
			if(bandera){			     
				sendForm();
			}
			
		}else{
			alert('No se puede continuar sin un Otorgante');
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


	
	
	
	
	
	function copiaContrato(){
		if(getObject('copia').checked){
		
			getObject('modtipoacto').value=getObject('modtipogarantia').options[getObject('modtipogarantia').selectedIndex].text;
			getObject('datepicker2').value=getObject('datepicker1').value;
			getObject('farectificacion_modotrosterminos').value=getObject('modotrosgarantia').value;
			
			getObject('modtipoacto').readOnly = true;
			getObject('datepicker2').readOnly = true;
			getObject('farectificacion_modotrosterminos').readOnly = true;
		}else{
			getObject('modtipoacto').value='';
			getObject('datepicker2').value='';
		
			getObject('farectificacion_modotrosterminos').value='';
			
			getObject('modtipoacto').readOnly = false;
			getObject('datepicker2').readOnly = false;
			getObject('farectificacion_modotrosterminos').readOnly = false;
		}
		
	}
	
	
	
	
	
	
	function escondePartes(){
		var valor = document.getElementById('modtipogarantia').value;		
		var bfecha1 = true;
		var bfecha2 = false;
		var bfecha3 = false;

		
		switch(valor){
		
		case "8":
			  //alert('Validar Folios '+ <s:property value="idTramite"/>);
			  
			  ParteDwrAction.verificarFolios(<s:property value="idTramite"/>,resultadoVerificacion);
				//var validador=dwr.util.getValue(mensaje.codeError);
			    
			    break;
						
			case "10":		
				displayObject('fecha1',false);
				displayObject('fecha2',true);
				displayObject('fecha3',false);
				displayObject('terminos1',false);
				displayObject('terminos2',true);
				displayObject('terminos3',false);
				displayObject('actoContratoID',false);
				document.getElementById("titulocopia").style.display="none";
				document.getElementById("copia").style.display="none";  
				document.getElementById("titulo").style.display="none";  
				document.getElementById("tittipoacto").style.display="none"; 
				document.getElementById("modtipoacto").style.display="none";
				document.getElementById("titdate2").style.display="none"; 
				document.getElementById("datepicker2").style.display="none";
				document.getElementById("titdate3").style.display="none"; 
				document.getElementById("datepicker3").style.display="none";
				document.getElementById("titterminos").style.display="none"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="none";
				break;
			
			case "12":
				displayObject('fecha1',true);
				displayObject('fecha2',true);
				displayObject('fecha3',true);
				displayObject('terminos1',true);
				displayObject('terminos2',true);
				displayObject('terminos3',true);
				displayObject('actoContratoID',true);
				document.getElementById("titulocopia").style.display="block";
				document.getElementById("copia").style.display="block";  
				document.getElementById("titulo").style.display="block";  
				document.getElementById("tittipoacto").style.display="block"; 
				document.getElementById("modtipoacto").style.display="block";
				document.getElementById("titdate2").style.display="block"; 
				document.getElementById("datepicker2").style.display="block";
				document.getElementById("titdate3").style.display="block"; 
				document.getElementById("datepicker3").style.display="block";
				document.getElementById("titterminos").style.display="block"; 
				document.getElementById("farectificacion_modotrosterminos").style.display="block";
				break;
			default:
				displayObject('fecha1',true);
			displayObject('fecha2',false);
			displayObject('fecha3',false);
			displayObject('terminos1',true);
			displayObject('terminos2',false);
			displayObject('terminos3',false);
				displayObject('actoContratoID',true);	
			document.getElementById("titulocopia").style.display="block";
			document.getElementById("copia").style.display="block";  
			document.getElementById("titulo").style.display="block";  
			document.getElementById("tittipoacto").style.display="block"; 
			document.getElementById("modtipoacto").style.display="block";
			document.getElementById("titdate2").style.display="block"; 
			document.getElementById("datepicker2").style.display="block";
			document.getElementById("titdate3").style.display="block"; 
			document.getElementById("datepicker3").style.display="block";
			document.getElementById("titterminos").style.display="block"; 
			document.getElementById("farectificacion_modotrosterminos").style.display="block";
			break;
				
				
				
		}
	}
	
	
	 function continuar(id){
	 switch(id){
	  	case 1:
	  		//alert("redireccion a AR");
	  		window.location.href = '<%= request.getContextPath() %>/acreedor/inicia.do?idTramite=<s:property value="idTramite"/>';
		  break;
		case 2:
	  		//alert("redireccion a misma pagina");
	  		
			document.location.href = "#anclaAA";
			document.getElementById('modtipogarantia').selectedIndex="0";
			displayMessage(false);
			
	  		
		  break;
		case 3:
	  		//alert("redireccion a ARyAD");
	  		window.location.href = '<%= request.getContextPath() %>/acreedor/inicia.do?idTramite=<s:property value="idTramite"/>';
		  break;
	 }
 }
	 

 function cancelar(){
	 //alert("Setear");
	 displayMessageFolioElectronicoAcreedores(false);
	 //me falta setear el combo tipo garantia para que regrese a seleccione
	 document.getElementById('modtipogarantia').selectedIndex="0";
	 
 }
	
	 function resultadoVerificacion(mensaje){
	  //alert('resultado '+mensaje.codeError);
	  
	  
	 switch(mensaje.codeError){
	  	case 0:
	  		//alert("acredores tienen folio Electrónico Continuar "+<s:property value="idTramite"/>);
	  		displayLoader(false);
		  break;
	  	
	  	case 1:
	  		//alert('Validar Folio Acreedor  Ir a alta de acreedores '+ <s:property value="idTramite"/>);
	  		displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor represnetado para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		
		  break;
		  
	  	case 2:
	  		//alert('Validar Folios Acredor Adicional Quedarse en esta pagina'+ <s:property value="idTramite"/>);
	  		displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','El Acreedor Adicional para esta garantía no cuenta con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		
	  	break;
	  	case 3:
	  		//alert('Ninguno tiene folio Ir a alta de acreedores');
	  		displayMessageFolioElectronicoAcreedores(true,'Folio Electrónico','Los Acreedores para esta garantía no cuentan con Folio Electrónico','continuar('+mensaje.codeError+')','cancelar()');
	  		
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
	
	
	
	
</script>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>
<script type="text/javascript">

</script>

<input type="hidden" id="titulo" />
<input type="hidden" name="tipoBienAll" value="false" id="idTipoBienAll" />
<div id="rectificacion" style="margin-top: 50px">

	<s:hidden id="modtipoant" value="%{modtipoacto}" />
	<s:hidden id="date2ant" value="%{modfechacelebcontrato}" />
	<s:hidden id="date3ant" value="%{modfechatermino}" />
	<s:hidden id="terminosant" value="%{modotrosterminos}" />

	<table id="tablaPaso1Gral" cellspacing="5" cellpadding="0">
		<tr>
			<td width="960px"
				height="25" style="padding-left: 5px;"><h1>Rectificaci&oacute;n por
				Error </h1><a class="thickbox"
				href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=rectificacion_por_errorII.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500"
				target="_blank" tabindex="31"
				><h2>(Video
					Tutorial)</h2></a></td>
		</tr>
		<tr>
			<td>
				<table style="padding-left: 6px; padding-right: 5px;" width="743px;">
					<tr>
						<td align="left"
							style="background-color: #D8D8D8; padding-left: 10px; padding-top: 4px; padding-bottom: 4px" class="texto_bolder_gris"><b>
								<s:property value="nomAcreedor" /> </b></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<td align="left"
				style="font-size: 10px; height: 28px; padding-left: 27px;"
				class="texto_azul">* Campo Obligatorio</td>
		</tr>
	</table>

	<table width="743px;">
		<tr>
			<td align="center" style="padding-left: 9px;">
				<table class="nota">
					<tr>
						<td class="imgNota"><img
							src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png">

						</td>
						<td class="contenidoNota textoJustificado"><s:text
								name="En esta sección usted podrá corregir cualquier error en la información de su Garantía Mobiliaria registrada. Fundamento Legal.- Art. 33 bis 3 RRPC" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<s:form id="farectificacion" name="farectificacion"
		action="saverectificacion.do" namespace="home" theme="simple">
		<s:hidden id="priv" value="%{perfil}" />

		<% if (priv.get(new Integer(20))!=null ) { %>
		<table>
			<tr>
				<td class="tituloHeader1" height="25" style="padding-left: 9px;">1.
					Solicitante</td>
			</tr>

			<tr>
				<td>
					<table width="742">
						<tr>
							<td colspan="2" class="textoGeneralRojo" align="left"
								style="padding-left: 21px;">Persona que solicita o
								Autoridad que instruye la Rectificaci&oacute;n y contenido de la
								Resoluci&oacute;n</td>
						</tr>
						<tr>
							<td class="texto_azul" width="14px;" style="padding-left: 25px;">*</td>
							<td><s:textarea cols="70" rows="4" name="autoridad"
									value="%{autoridad}" id="autoridad" maxlength="3900"
									onkeyup="return ismaxlength(this)" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table>
									<tr>
										<td style="padding-left: 38px;" class="textoEjemplo">Ejemplo&nbsp;</td>
										<td>
											<table>
												<tr>
													<td width="414px;" class="textoGris textoJustificado"
														align="justify">a) Licenciado Francisco Rodríguez
														Hernández, Juez Décimo Cuarto de lo Mercantil del Primer
														Partido Judicial en el Estado de Jalisco.</td>
												</tr>
												<tr>
													<td class="textoGris textoJustificado" align="justify">b)
														Oficio 222/10 Expediente 2288/2010.Dentro de los autos del
														Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde
														García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE
														C.V.</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		</table>
		<% }%>

		<table>
			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 10px">2. Datos de la Inscripción</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td class="textoGeneralRojo" style="padding-left: 21px"
								height="31px">Vigencia:</td>
							<td style="color: black;"><s:property
									value="%{detalleTO.vigencia}" /> Meses</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 10px">3. Partes de la Garant&iacute;a
					Mobiliaria</td>
			</tr>

			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 10px">Acreedor</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 5px;">
					<table border="0" width="79%" cellspacing="1" cellpadding="1"
						align="center">
						<thead>
							<tr>
								<td class="encabezadoTablaResumen" style="text-align: center">Nombre,
									Denominación o Razón Social</td>
								<td class="encabezadoTablaResumen" style="text-align: center">Tipo
									de Persona</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="cuerpo1TablaResumen" style="text-align: center">
										<s:property value="acreedorRepresentado.nombreCompleto" />
									</td>
								<td class="cuerpo1TablaResumen" style="text-align: center">
										<s:property value="acreedorRepresentado.tipoPersona" />
									</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
			</tr>
			<tr>
			</tr>
			<tr>
			</tr>
			<tr>
			</tr>
			<tr>
				<td align="center">
					<table>
						<tr>
							<td id="notaMoral">
								<table class="nota">
									<tr>
										<td class="imgNota"><img
											src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png">

										</td>
										<td align="justify" class="contenidoNota textoJustificado"><s:text
												name="Es necesario que usted esté autorizado por el  Acreedor al que se transmitirá la Garantía Mobiliaria, para poder realizar esta operación." />
										</td>
									</tr>

								</table>
							</td>
						</tr>

						<tr>
							<td id="notaFisica" style="visibility: hidden; display: none;">
								<table class="nota">
									<tr>
										<td class="imgNota"><img
											src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png">
											<s:text name="common.nota" />
										</td>
										<td class="contenidoNota"><s:text
												name="Si  el Otorgante no cuenta con folio electrónico, el Sistema lo matriculará  de oficio para efectos del RUG" />
										</td>
									</tr>

								</table>
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
						</tr>

					</table>
				</td>
			</tr>
			<tr>
				<td style="padding-left: 7px;">
					<table border="0" width="79%" cellspacing="1" cellpadding="1"
						align="left">
						<s:select name="acreedorTransmite" id="idAcreedorTrasmite"
							list="acreedoresDisponibles" listKey="idAcreedor"
							listValue="nombreCompleto" cssClass="texto_general"
							headerValue=" Seleccione el acreedor " headerKey="-1"
							label="Seleccione el acreedor de la garantía mobiliaria."
							labelposition="top" />
					</table>
				</td>
			</tr>
			<tr>
			</tr>
			<tr>
			</tr>
			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 9px">Otorgante de la Garant&iacute;a
					Mobiliaria <a tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=rectOtorgante&keepThis=true&TB_iframe=true&height=315&width=500"
					title="" class="thickbox"><img
						alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a>
				</td>
			</tr>

			<tr>
				<td align="center"></td>
			</tr>

			<tr>
				<td style="padding-left: 4px" id="divParteDWRxx"></td>
			</tr>

			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 9px">Deudor(es) <a tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=modifDeudor&keepThis=true&TB_iframe=true&height=350&width=500"
					title="" class="thickbox"><img
						alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a>
				</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td id="divParteDWRxx2"></td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td 
				width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 10px">Acreedores adicionales <a
					tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=ayudaAgregarAcreedor&keepThis=true&TB_iframe=true&height=105&width=500"
					title="" class="thickbox"><img
						alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a>
						<a name="anclaAA"></a>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td id="divParteDWRxx3"></td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td width="730px" align="left" height="38px" class="tituloHeader1"
					style="padding-left: 10px">4. Acto o Contrato que crea la
					Garantía Mobiliaria</td>
			</tr>

			<tr>
				<td class="textoGeneralRojo" align="left"
					style="padding-left: 28px;">Tipo de Garantía Mobiliaria: <a
					tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=ayudaTipoGarantiaMobiliaria&keepThis=true&TB_iframe=true&height=450&width=500"
					title="" class="thickbox"><img alt=" <s:text name="" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a></td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td class="texto_azul" style="padding-left: 25px" width="14px">*</td>
							<td width="14px;"><s:select name="modtipogarantia"
									list="tipoGarantiaTOs" listValue="descripcion"
									listKey="idTipoGarantia" value="%{modtipogarantia}"
									headerValue="Seleccione un tipo de garantía" headerKey="-1"
									onchange="javascript:escondePartes();" labelposition="top"
									id="modtipogarantia" onblur="cambiaAbajo()" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="tituloInteriorRojo" align="left"
					style="padding-left: 28px;"><s:textfield id="fecha1"
						name="fecha1" labelposition="top"
						cssStyle="visibility:visible; display:block; border:0; "
						cssClass="textoGeneralRojo" readonly="true"
						value="Fecha de celebración del Acto o Contrato" size="90" /> <s:textfield
						id="fecha2" name="fecha2" labelposition="top"
						cssStyle="display:none; border:0; visibility:hidden;"
						cssClass="textoGeneralRojo" readonly="true"
						value="Fecha de surgimiento del Derecho de Retención" size="90" />
					<s:textfield id="fecha3" name="fecha3" labelposition="top"
						cssStyle="display:none; border:0; visibility:hidden;"
						cssClass="textoGeneralRojo" readonly="true"
						value="Fecha de surgimiento del Privilegio Especial" size="90" />
				</td>
			</tr>


			<tr>
				<td>
					<table>
						<tr>
							<td class="texto_azul" style="padding-left: 25px" width="12px">*</td>
							<td align="left">
								<table>
									<tr>
										<td>
											<dl style="width: 60px">
												<dd style="width: 60px">
													<s:textfield id="datepicker1" name="fechaCelebracionC"
														size="10" labelposition="top" onchange="cambiaAbajo()" />
													<span class="hint">
														<div class="cerrar">
															<a
																onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																style="cursor: pointer;">x</a>
														</div>
														<div class="contenido">Fecha indicada en el acto o
															contrato de Garantía Mobiliaria correspondiente.</div> <span
														class="hint-pointer">&nbsp;</span> </span>
												</dd>
											</dl>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>


			<tr>
				<td align="left" style="padding-left: 28px;"><s:textfield
						id="titmonto" name="titmonto" labelposition="top"
						cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true"
						value="Monto Máximo Garantizado" size="70" />
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 43px;"><s:textfield
						name="modmonto" id="modmonto" size="20" labelposition="top"
						value="%{modmonto}" maxlength="20"
						onkeypress="return IsNumber(event);" />
				</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris" align="justify">a)
											50000</td>
									</tr>
									<tr>
										<td class="textoGris" align="justify">b) 1000000</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 28px;"><s:textfield
						id="tittipomoneda" name="tittipomoneda" labelposition="top"
						cssStyle="border:0; " cssClass="textoGeneralRojo" readonly="true"
						value="Tipo de moneda:" size="70" />
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 43px;"><s:select
						list="listaMonedas" listKey="idMoneda" listValue="descMoneda"
						name="idTipoMoneda" labelposition="top" headerKey="%{cveMoneda}"
						value="%{moneda}" />
				</td>
			</tr>


			<tr>
				<td align="left" style="padding-left: 28px;"><s:textfield
						id="tittipo" name="tittipo" labelposition="top"
						cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true"
						value="Tipo de Bienes Muebles objeto de la Garantía Mobiliaria:"
						size="57" /> <a tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=tipoBienesMueblesrec&keepThis=true&TB_iframe=true&height=300&width=500"
					title="" class="thickbox"><img
						alt=" <s:text name="Acreedores Adicionales" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td class="texto_azul" style="padding-left: 25px" width="14px">*</td>
							<td align="left"><s:select name="modtipobien"
									key="tipoBienTOs.idbien" list="tipoBienTOs" id="modtipobien"
									listKey="idbien" listValue="descrbien"
									value="%{bienes.{idTipoBien}}" multiple="true"
									headerValue="Seleccione un Tipo de Bien"
									cssStyle="width:462px; text-indent:2em;" size="10"
									labelposition="top" onclick="marcar(this)" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			   <tr>
      <td>
    	 <table> 
         	 <tr>
           		<td> &nbsp; &nbsp;<input class="boton_rug_largos" type="button" value="Seleccionar todos" onclick= "selecciona(this.form)" /> &nbsp; &nbsp; </td>
           		<td> &nbsp; &nbsp; <input class="boton_rug_largos" type="button" value="Anular Selección" onclick= "desSelecciona(this.form)"/> </td>
    		</tr>
         </table>
        </td>
    </tr>
			
			


			<tr>
				<td align="left" style="padding-left: 28px;"><s:textfield
						id="titdescrip" name="titdescrip" labelposition="top"
						cssStyle="border:0;" cssClass="textoGeneralRojo" readonly="true"
						value="Descripción de los Bienes Muebles objeto de la Garantía:"
						size="58" /> <a tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=descripcionBienesMuebles&keepThis=true&TB_iframe=true&height=300&width=500"
					title="" class="thickbox"><img alt=" <s:text name="" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a></td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td class="texto_azul" style="padding-left: 25px" width="12px">*</td>
							<td align="left">
								<table>
									<tr>
										<td>
											<dl style="width: 425px">
												<dd style="width: 425px">
													<span class="hint">
														<div class="cerrar">
															<a
																onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																style="cursor: pointer;">x</a>
														</div>
														<div class="contenido">Características de los bienes
															muebles objeto de la Garantía Mobiliaria que permiten su
															identificación. Art. 32 Apartado B RRPC.</div> <span
														class="hint-pointer">&nbsp;</span> </span>
													<s:textarea name="moddescripcion" cols="70" rows="10"
														id="tiposbienes" value="%{moddescripcion}"
														labelposition="top" />
												</dd>
											</dl>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris textoJustificado"
											align="justify">a) "Vehículo Marca Ford , Modelo Pick Up
											Lobo LX, doble cabina, automática, placas WES 532, Número de
											Identificación vehicular (VIN) FDH276549964"</td>
									</tr>
									<tr>
										<td class="textoGris textoJustificado" align="justify">b)
											"Todos los bienes muebles que utilice para la realización de
											su actividad preponderante"</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 27px;">
					<table>
						<tr>
							<td style="width: 10px;" align="left"><div
									class="titulo_exterior_rojo" style="align:left;">
									<s:checkbox name="cambio" id="cambio" theme="simple" />
								</div></td>
							<td style="width: 670px;" align="left" height="21px"><div
									class="textoGeneralRojo" style="align:left;"> &nbsp; El acto o contrato prevé incrementos, reducciones o sustituciones  de los bienes muebles o del monto garantizado. <a
										tabindex="31"
										href="<%= request.getContextPath() %>/comun/publico/help.do?llave=actoPreveIncrementos&keepThis=true&TB_iframe=true&height=347&width=500"
										title="" class="thickbox"><img alt=" <s:text name="" /> "
										src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
										border="0"> </a>
								</div></td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
			</tr>
			<tr>
			</tr>

			<tr>
				<td class="textoGeneralRojo" align="left"
					style="padding-left: 28px;">Datos del Instrumento Público
					mediante el cuál se formalizó el Acto o Contrato: <a tabindex="31"
					href="<%= request.getContextPath() %>/comun/publico/help.do?llave=datosInstrumentoPublico&keepThis=true&TB_iframe=true&height=347&width=500"
					title="" class="thickbox"><img alt=" <s:text name="" /> "
						src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
						border="0"> </a></td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 43px;"><s:textarea
						labelposition="top" value="%{modinstrumento}" cols="70" rows="10"
						name="modinstrumento" maxlength="3900"
						onkeyup="return ismaxlength(this)" />
				</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris textoJustificado"
											align="justify">Mediante instrumento público número
											[número del instrumento público] de fecha [día] de [mes] del
											[año], otorgada ante la fe del Lic. [nombre del fedatario
											público], [Corredor/Notario] Público número [número de
											fedatario público] de [Nombre de la Entidad Federativa]</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 28px;"><s:textfield
						id="terminos1" name="terminos1" labelposition="top"
						cssStyle="visibility:visible; display:block; border:0; "
						cssClass="textoGeneralRojo" readonly="true"
						value="Términos y Condiciones de la Obligación Garantizada:"
						size="90" />
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 21px;"><s:textfield
						id="terminos2" name="terminos2" labelposition="top"
						cssStyle="display:none; border:0; visibility:hidden;"
						cssClass="textoGeneralRojo" readonly="true"
						value="Fundamento Legal del cual surge el Derecho de Retención:"
						size="90" />
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 21px;"><s:textfield
						id="terminos3" name="terminos3" labelposition="top"
						cssStyle="display:none; border:0; visibility:hidden;"
						cssClass="textoGeneralRojo" readonly="true"
						value="Fundamento Legal del cual surge el Privilegio Especial:"
						size="90" />
				</td>
			</tr>

			<tr>
				<td align="left" style="padding-left: 41px;">
					<table>
						<tr>
							<td>
								<dl style="width: 425px">
									<dd style="width: 425px">
										<span class="hint">
											<div class="cerrar">
												<a
													onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
													style="cursor: pointer;">x</a>
											</div>
											<div class="contenido">Se recomienda utilizar este
												campo para incluir cualesquiera circunstancias de hecho o de
												derecho que considere relevantes para efectos de la
												publicidad de la Garantía Mobiliaria.</div> <span
											class="hint-pointer">&nbsp;</span> </span>
										<s:textarea name="modotrosgarantia" cols="70" rows="10"
											id="modotrosgarantia" labelposition="top"
											onblur="cambiaAbajo()" />
									</dd>
								</dl>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris textoJustificado"
											align="justify">a) CLAUSULA VIGÉSIMA CUARTA. Cesión. El
											BANCO podrá en cualquier momento durante la vigencia de este
											Contrato, ceder o transmitir sus derechos y obligaciones bajo
											el presente Contrato y sus anexos, sin necesidad de previa
											autorización del ACREDITADO.</td>
									</tr>
									<tr>
										<td class="textoGris textoJustificado" align="justify">b)
											Conforme a la cláusula SEXTA del contrato, el otorgante
											deberá mantener los bienes muebles objeto de la Garantía
											Mobiliaria en la siguiente ubicación: Av. Hidalgo No. 321,
											Interior 123, Colonia Morelos, C.P. 0149870, Delegación
											Álvaro Obregón; México, D.F.</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="100%" id="actoContratoID">
					<table>
						<tr>
							<td width="730px" align="left" height="31px"
								class="tituloHeader1" style="padding-left: 10px">5. Acto o
								Contrato que crea la Obligación Garantizada</td>
						</tr>
						<tr>
							<td align="center">
								<table>
									<tr>
										<td id="notaMoral">
											<table class="nota">
												<tr>
													<td class="imgNota"><img
														src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png">

													</td>
													<td align="justify" class="contenidoNota textoJustificado"><s:text
															name="Si conforme a las leyes aplicables el acto o contrato que crea la Garantía Mobiliaria debe estar otorgado o ser ratificado ante un fedatario público, será necesario ingresar en este campo los datos que permitan identificar el Instrumento Público correspondiente." />
													</td>
												</tr>

											</table>
										</td>
									</tr>
									<tr>
										<td align="left" style="padding-left: 25px;">
											<table>
												<tr>
													<td style="width: 15px;" align="left"><div
															class="titulo_exterior_rojo"
															style="width:15px; align:left;">
															<s:checkbox name="copia" id="copia" theme="simple"
																onclick="javascript:copiaContrato();" />
														</div></td>
													<td style="width: 690px;" align="left" height="31px"><div
															class="titulo_exterior_rojo"
															style="width:690px; align:left;">
															<s:textfield id="titulocopia" theme="simple"
																name="titcopia" size="110"
																value="Acto o Contrato que crea la Obligación Garantizada es el mismo que el que crea la Garantía Mobiliaria"
																cssStyle="visibility:visible; display:block; border:0;"
																cssClass="textoGeneralRojo" readonly="true" />
														</div></td>
												</tr>
											</table>
										</td>
									</tr>


									<tr>
										<td align="left" style="padding-left: 27px;"><s:textfield
												id="tittipoacto" name="tittipoacto" size="55"
												value="Acto o Contrato que crea la Obligación Garantizada:"
												labelposition="top"
												cssStyle="visibility:visible; display:block; border:0;"
												cssClass="textoGeneralRojo" readonly="true" />
										</td>
									</tr>
									<tr>
										<td>
											<table>
												<tr>
													<td class="texto_azul" style="padding-left: 21px"
														width="14px">*</td>
													<td align="left">
														<table>
															<tr>
																<td>
																	<dl style="width: 425px">
																		<dd style="width: 425px">
																			<span class="hint">
																				<div class="cerrar">
																					<a
																						onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																						style="cursor: pointer;">x</a>
																				</div>
																				<div class="contenido">Información de la
																					obligación principal que se garantiza con la
																					Garantía Mobiliaria.</div> <span class="hint-pointer">&nbsp;</span>
																			</span>
																			<s:textarea id="modtipoacto" name="modtipoacto"
																				cols="70" rows="10" value="%{modtipoacto}"
																				labelposition="top"
																				cssStyle="visibility:visible; display:block; "
																				maxlength="3900" onkeyup="return ismaxlength(this)" />
																		</dd>
																	</dl>
																</td>
															</tr>
														</table>
													</td>
												</tr>

											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table>
												<tr>
													<td style="padding-left: 38px;" class="textoEjemplo">Ejemplo&nbsp;</td>
													<td>
														<table>
															<tr>
																<td width="414px;" class="textoGris textoJustificado"
																	align="justify">Contrato de crédito simple número
																	00012352221545, de fecha 5 de septiembre de 2010 con
																	garantía prendaria.</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td align="left" style="padding-left: 26px;"><s:textfield
												id="titdate2" name="titdate2" size="50"
												value="Fecha de celebración del Acto o Contrato: "
												labelposition="top"
												cssStyle="visibility:visible; display:block; border:0;"
												cssClass="textoGeneralRojo" readonly="true" />
										</td>
									</tr>
									<tr>
										<td>
											<table>
												<tr>
													<td class="texto_azul" style="padding-left: 21px"
														width="14px">*</td>
													<td align="left">
														<table>
															<tr>
																<td>
																	<dl style="width: 60px">
																		<dd style="width: 60px">
																			<s:textfield id="datepicker2"
																				name="modfechacelebcontrato" size="10"
																				value="%{modfechacelebcontrato}" labelposition="top"
																				cssStyle="visibility:visible; display:block; "
																				onchange="fechasCorrectas();" />
																			<span class="hint">
																				<div class="cerrar">
																					<a
																						onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																						style="cursor: pointer;">x</a>
																				</div>
																				<div class="contenido">Fecha indicada en el
																					acto o contrato principal.</div> <span
																				class="hint-pointer">&nbsp;</span> </span>
																		</dd>
																	</dl>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>


									<tr>
										<td align="left" style="padding-left: 26px;"><s:textfield
												id="titdate3" name="titdate3" size="50"
												value="Fecha de terminación del Acto o Contrato:"
												labelposition="top"
												cssStyle="visibility:visible; display:block; border:0;"
												cssClass="textoGeneralRojo" readonly="true" />
										</td>
									</tr>

									<tr>
										<td align="left" style="padding-left: 39px;">´
											<table>
												<tr>
													<td>
														<dl style="width: 60px">
															<dd style="width: 60px">
																<s:textfield id="datepicker3" name="modfechatermino"
																	size="10" value="%{modfechatermino}"
																	labelposition="top"
																	cssStyle="visibility:visible; display:block; "
																	onchange="fechasCorrectas();" />
																<span class="hint">
																	<div class="cerrar">
																		<a
																			onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																			style="cursor: pointer;">x</a>
																	</div>
																	<div class="contenido">Fecha indicada en el acto
																		o contrato principal.</div> <span class="hint-pointer">&nbsp;</span>
																</span>
															</dd>
														</dl>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td>
											<table>
												<tr>
													<td align="left" style="padding-left: 24px;"><s:textfield
															id="titterminos" name="titterminos" size="45"
															value="Términos y Condiciones del Acto o Contrato:"
															labelposition="top"
															cssStyle="visibility:visible; display:block; border:0;"
															cssClass="textoGeneralRojo" readonly="true" />
													</td>
													<td><a tabindex="31" style="float: left"
														href="<%= request.getContextPath() %>/comun/publico/help.do?llave=terminosyCondicionesActoContrato&keepThis=true&TB_iframe=true&height=347&width=500"
														title="" class="thickbox"><img
															alt=" <s:text name="" /> "
															src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
															border="0"> </a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="left" style="padding-left: 39px;">
											<table>
												<tr>
													<td>
														<dl style="width: 425px">
															<dd style="width: 425px">
																<span class="hint">
																	<div class="cerrar">
																		<a
																			onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																			style="cursor: pointer;">x</a>
																	</div>
																	<div class="contenido">Se recomienda utilizar
																		este campo para incluir cualesquiera circunstancias de
																		hecho o de derecho que considere relevantes para
																		efectos de la publicidad de la Garantía Mobiliaria.</div> <span
																	class="hint-pointer">&nbsp;</span> </span>
																<s:textarea id="farectificacion_modotrosterminos"
																	name="modotrosterminos" cols="70" rows="10"
																	labelposition="top"
																	cssStyle="visibility:visible; display:block; " />
															</dd>
														</dl>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td>
											<table>
												<tr>
													<td style="padding-left: 38px;" class="textoEjemplo">Ejemplo&nbsp;</td>
													<td>
														<table>
															<tr>
																<td width="414px;" class="textoGris textoJustificado"
																	align="justify">a) CLAUSULA VIGÉSIMA CUARTA.
																	Cesión. El BANCO podrá en cualquier momento durante la
																	vigencia de este Contrato, ceder o transmitir sus
																	derechos y obligaciones bajo el presente Contrato y sus
																	anexos, sin necesidad de previa autorización del
																	ACREDITADO.</td>
															</tr>
															<tr>
																<td class="textoGris textoJustificado" align="justify">b)
																	Conforme a la cláusula SEXTA del contrato, el otorgante
																	deberá mantener los bienes muebles objeto de la
																	Garantía Mobiliaria en la siguiente ubicación: Av.
																	Hidalgo No. 321, Interior 123, Colonia Morelos, C.P.
																	0149870, Delegación Álvaro Obregón; México, D.F.</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
							<%-- 		
									<!-- Autoridad Rectificacion -->
									<s:if test="autoridadInstruyeTramite!=null">
									<tr>
										<td>
											<table>
												<tr>
													<td align="left" style="padding-left: 24px;">
													<s:textfield
															id="idtitautoridadInstruye" name="titautoridadInstruye" size="110"
															name="autoridadInstruyeTramiteTitulo"
															labelposition="top"
															cssStyle="visibility:visible; display:block; border:0;"
															cssClass="textoGeneralRojo" readonly="true" />
													</td>
													<td><a tabindex="31" style="float: left"
														href="<%= request.getContextPath() %>/comun/publico/help.do?llave=terminosyCondicionesActoContrato&keepThis=true&TB_iframe=true&height=347&width=500"
														title="" class="thickbox"><img
															alt=" <s:text name="" /> "
															src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png"
															border="0"> </a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="left" style="padding-left: 39px;">
											<table>
												<tr>
													<td>
														<dl style="width: 425px">
															<dd style="width: 425px">
																<span class="hint">
																	<div class="cerrar">
																		<a
																			onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';"
																			style="cursor: pointer;">x</a>
																	</div>
																	<div class="contenido">Se recomienda utilizar
																		este campo para incluir cualesquiera circunstancias de
																		hecho o de derecho que considere relevantes para
																		efectos de la publicidad de la Garantía Mobiliaria.</div> <span
																	class="hint-pointer">&nbsp;</span> </span>
																<s:textarea id="idAutoridadInstruyeTramite"
																	name="autoridadInstruyeTramite" cols="70" rows="4"
																	labelposition="top" maxlength="3900"
																	cssStyle="visibility:visible; display:block; " />
															</dd>
														</dl>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									
									
									
									
									</s:if>
									<!-- Autoridad Rectificacion -->
									
									 --%>
								</table>
							</td>

						</tr>
						</table></td></tr>

						<tr>
							<td align="center">
								<table width="745">
									<tr>
										<td align="center"><input type="button" id="bFirmar"
											name="button" class="boton_rug" value="Firmar"
											onclick="validar();" /></td>
									</tr>
								</table>
							</td>
						</tr>

					</table>
	</s:form>

</div>
<input type="hidden" name="nombreAcreedor" id="nombreAcreedor" value="<s:property value="nomAcreedor"/>" />
<script type="text/javascript"> 
displayLoader(true);
	setTimeout("$(function() {$('#datepicker1').datepicker({changeMonth: true,changeYear: true});});",10);
	setTimeout("$(function() {$('#datepicker2').datepicker({changeMonth: true,changeYear: true});});",10);
	setTimeout("$(function() {$('#datepicker3').datepicker({changeMonth: true,changeYear: true});});",10);

	setTimeout("$(function() {$('#datepicker12').datepicker({changeMonth: true,changeYear: true});});",10);
	setTimeout("$(function() {$('#datepicker13').datepicker({changeMonth: true,changeYear: true});});",10);
	
	var idPersona = <s:property value="idpersona"/>;
	var idTramite= <s:property value="idTramite"/>;
		
	cargaParteOtorgante('divParteDWRxx',idTramite, idPersona,'0','1');
	cargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','1');
	cargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0');
	setTimeout('escondePartes()',100);
	displayLoader(false);
</script>