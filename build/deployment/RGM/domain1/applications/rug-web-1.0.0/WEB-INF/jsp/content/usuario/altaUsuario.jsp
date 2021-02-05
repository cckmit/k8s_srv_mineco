<%@page import="java.util.List"%>
<%@page import="mx.gob.se.rug.acreedores.to.GrupoPerfilTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/interface/GruposDwrAction.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<SCRIPT type="text/javascript">
if ('<s:property value="existeUsuario"/>' == 'Y') {
	setTimeout("alert('El usuario ya se encuentra asociado a este acreedor.');" ,2000);
}
	function sendForm(){
		document.getElementById("btnAceptar").value = "Enviando..";
		document.getElementById("btnAceptar").disabled = true;
		getObject('formUsuario').submit();
	}
	function eliminaUsuario(idPersona){
		if(confirm('Esta seguro de eliminar a este usuario ?')){
		window.location.href="/Rug/controlusuario/eliminaUsuario.do?idPersonaModificar="+idPersona;
		}
	}
	function mandaFirma(idTramite){
		window.location.href="/Rug/firma/firmaUsuario.do?idTramite="+idTramite;
	}
	function buscaInformacionCorreo(){
		buscaBuscaCorreoUsuario('espacioIDmsj');
	}
	function mostrarAltaUsuario(){
		document.getElementById('usuarioTD').style.visibility = 'visible';
		document.getElementById('usuarioTD').style.display = 'block';
	}
	function ocultarAltaUsuario(){
		document.getElementById('usuarioTD').style.visibility = 'hidden';
		document.getElementById('usuarioTD').style.display = 'none';
	}
	function isNullOrEmpty(valor){
		if ( valor == null )
			true;
		for ( i = 0; i < valor.length; i++ )
				if ( valor.charAt(i) != " " )
					return false; 
		return true;
	}
	function showSelectGrupo(message){
		if (message.codeError==0){
			fillObject('grup',message.message);		
		}
	}
	function validaAltaUsuario() {
		// validando campos obligatorios
		if (document.getElementById("usuarioAutoridadId")!=null){
			var nombreCompleto =document.getElementById("usuarioAutoridadId").value;
			alert("El usuario "+nombreCompleto+"  está habilitado como autoridad en el sistema por lo cual cuenta con todos los privilegios y no puede ser asignado a ningún grupo");
			return false;
		}
		var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
		var comPasswd = document.getElementById('comPasswd') == null ? '' : document.getElementById('comPasswd').value;
		if (comPasswd == 'Y') {
			if (isNullOrEmpty(document.getElementById("correoElectronico").value) || isNullOrEmpty(document.getElementById("usuarioTO.nombre").value) || isNullOrEmpty(document.getElementById("usuarioTO.apaterno").value) || isNullOrEmpty(document.getElementById("usuarioTO.rfc").value)) {
				alert('Los campos marcados con * son obligatorios.');
			} else if (!document.getElementById("correoElectronico").value.match(emailExp)) {
				alert('El correo electrónico es invalido.');
			} else if (validaRfc(document.getElementById("usuarioTO.rfc").value)) {
				sendForm();
			}
		} else {
			if (isNullOrEmpty(document.getElementById("correoElectronico").value) || isNullOrEmpty(document.getElementById("usuarioTO.nombre").value) || isNullOrEmpty(document.getElementById("usuarioTO.apaterno").value) || isNullOrEmpty(document.getElementById("usuarioTO.rfc").value) || isNullOrEmpty(document.getElementById("passwd1").value) || isNullOrEmpty(document.getElementById("passwd2").value)) {
				alert('Los campos marcados con * son obligatorios.');
			} else if (document.getElementById("passwd1").value != document.getElementById("passwd2").value) {
				alert('Las contraseñas deben ser iguales.');
			} else if (!document.getElementById("correoElectronico").value.match(emailExp)) {
				alert('El correo electrónico es invalido.');
			} else if (validaRfc(document.getElementById("usuarioTO.rfc").value)) {
				sendForm();
			}
		}
	}
	function revisaGrupo(value) {
		if (value.value == -1) {
			document.getElementById('agregaGrupoNodal').click;
			
		}
	}
		

	function refrescarGrupo(idAcreedor){
		 GruposDwrAction.refrescarGrupo(idAcreedor,showSelectGrupo);
	}
</SCRIPT>

	<table>

		<tr>
			<td colspan="2"><s:if test="listaFirmados.size>0">
					<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
						border="0" width="650">
						<tr>
							<td class="tituloInteriorRojo">Usuarios</td>
						</tr>
						<tr>
							<td class="encabezadoTablaResumen">Correo Electronico</td>
							<td class="encabezadoTablaResumen">Nombre</td>
							<td class="encabezadoTablaResumen">Grupo</td>
							<td class="encabezadoTablaResumen">Opciones</td>
						</tr>
						<s:iterator value="listaFirmados">
							<tr>
								<td class="cuerpo1TablaResumen"><a style="cursor: pointer;"
									onclick="muestraDesUsuario(<s:property value="idSubusuario"/>);"><s:property
											value="cveUsuario" /> </a></td>
								<td class="cuerpo1TablaResumen"><a style="cursor: pointer;"
									onclick="muestraDesUsuario(<s:property value="idSubusuario"/>);"><s:property
											value="nombreCompleto" /> </a></td>
								<td class="cuerpo1TablaResumen"><s:property
										value="desGrupo" /></td>
								<td class="cuerpo1TablaResumen"><a style="cursor: pointer;"
									onclick="muestraModUsuario('<s:property value="idUsuario"/>','<s:property value="idSubusuario"/>','<s:property value="idAcreedor"/>');">
										Modificar Grupo</a> |<a style="cursor: pointer;"
									onclick="eliminaUsuario('<s:property value="idSubusuario"/>');">
										Eliminar</a></td>
							</tr>
						</s:iterator>
					</table>
				</s:if> <s:else>
					<!--No existen usuarios Firmados-->

				</s:else>
			</td>
		</tr>
		<s:if test="listaNoFirmados.size>0">
			<tr>
				<td>
					<table id="mytable" class="mytable" cellpadding="3" cellspacing="2"
						border="0" width="650">
						<tr>
							<td colspan="3" class="tituloInteriorRojo">Usuarios
								Pendientes por Firmar</td>
						</tr>
						<tr>
							<td class="encabezadoTablaResumen">Correo Electronico</td>
							<td class="encabezadoTablaResumen">Nombre</td>
							<td class="encabezadoTablaResumen">Grupo</td>
							<td class="encabezadoTablaResumen" width="25%">Opciones</td>
						</tr>
						<s:iterator value="listaNoFirmados">
							<tr>
								<td class="cuerpo1TablaResumen"><a style="cursor: pointer;"
									onclick="muestraDesUsuario(<s:property value="idSubusuario"/>);"><s:property
											value="cveUsuario" /> </a></td>
								<td class="cuerpo1TablaResumen"><a style="cursor: pointer;"
									onclick="muestraDesUsuario(<s:property value="idSubusuario"/>);"><s:property
											value="nombreCompleto" /> </a></td>
								<td class="cuerpo1TablaResumen"><s:property
										value="desGrupo" /></td>
								<td class="cuerpo1TablaResumen" width="25%"><a
									style="cursor: pointer;"
									onclick="mandaFirma('<s:property value="idTramiteTemporal" />')">
										Firmar |</a>&nbsp;<a
									onclick="eliminaUsuario('<s:property value="idSubusuario"/>');"
									style="cursor: pointer;"> Eliminar </a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
		</s:if>
		<s:else>


			<!--No existen usuarios NO Firmados  -->
		</s:else>
	</table>

	<table>
		<tr>
			<td id="mostrardesusuario" style="visibility: hidden; display: none;"></td>
		</tr>
	</table>


	<table class="nota">
		<tr>
			<td align="right"><img
				src="<%=request.getContextPath()%>/resources/imgs/ico_nota.png">
				&nbsp;</td>
			<td align="justify" class="contenidoNota"><s:text
					name="<b>ADVERTENCIA:</b> La(s) persona(s) que está autorizando podrá(n) realizar operaciones en el RUG en nombre y por cuenta del Acreedor con el que usted las ha vinculado; El Acreedor será responsable para todos los efectos a que haya lugar de las operaciones realizadas en su nombre, independientemente de la persona(s) autorizada(s) que las hayan efectuado." />
				<br>
			</td>
		</tr>
	</table>



	<!-- Abraham <<< No modifiquen de aki -->
	<table>
		<tr>
			<td class="imgNota" width="44"></td>
			<td><a onclick="mostrarAltaUsuario();ocultarDesUsuario();"
				style="cursor: pointer;" class="tituloInteriorRojo"> + Agregar
					Usuario</a> <input type="hidden" id="idAcreedorHidden"
				value='<s:property value="idAcreedor"/>'>
			</td>
		</tr>
		<tr>
			<td id="usuarioTD" style="visibility: hidden; display: none;"><s:form
					id="formUsuario" name="formUsuario" namespace="controlusuario"
					action="guardaUsuario.do" theme="simple">
					<table>
						<tr>
							<td id="espacioIDmsj"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="2" cellspacing="2">
						<tr>
							<td colspan="2"><table>
									<tr>
										<td class="textoGeneralAzul" valign="bottom" align="right"><b
											class="textoGeneralRojo">*</b> <s:text
												name="Correo Electronico" /></td>
										<td class="campo" valign="bottom"><s:textfield
												name="usuarioTO.cveUsuario"
												onblur="buscaInformacionCorreo();" id="correoElectronico"
												required="true" cssClass="campo_general" maxlength="40"
												size="40" />
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td colspan="2" id="tableIDT"
								style="visibility: hidden; display: none;"><table>
									<tr>
										<td class="textoGeneralAzul" valign="bottom" align="right"><b
											class="textoGeneralRojo">*</b> <s:text name="Nombre" /></td>
										<td class="campo" valign="bottom"><s:textfield
												name="usuarioTO.nombre" id="usuarioTO.nombre"
												required="true" cssClass="campo_general" maxlength="40"
												size="40" />
										</td>
									</tr>
									<tr>
										<td class="textoGeneralAzul" align="right"><b
											class="textoGeneralRojo">*</b> <s:text
												name="Apellido Paterno" /></td>
										<td class="campo"><s:textfield name="usuarioTO.apaterno"
												id="usuarioTO.apaterno" required="true"
												cssClass="campo_general" maxlength="40" size="40" />
										</td>
									</tr>
									<tr>
										<td class="textoGeneralAzul" align="right"><b
											class="textoGeneralRojo"></b> <s:text name="Apellido Materno" />
										</td>
										<td class="campo"><s:textfield name="usuarioTO.amaterno"
												id="usuarioTO.amaterno" required="true"
												cssClass="campo_general" maxlength="40" size="40" />
										</td>
									</tr>
									<tr>
										<td class="textoGeneralAzul" align="right"><b
											class="textoGeneralRojo">*</b> <s:text name="RFC" /></td>
										<td class="campo"><s:textfield name="usuarioTO.rfc"
												id="usuarioTO.rfc" required="true" cssClass="campo_general"
												maxlength="13" size="40"
												onkeyup="this.value = this.value.toUpperCase();" />
										</td>
									</tr>
									<tr>
										<td class="textoGeneralAzul" align="right" id="passDos"><b
											class="textoGeneralRojo">*</b> <s:text name="Password" /></td>
										<td class="campo" id="passDos2"><s:password
												name="usuarioTO.password" id="passwd1" required="true"
												cssClass="campo_general" maxlength="16" size="40" />
										</td>
									</tr>
									<tr>
										<td class="textoGeneralAzul" align="right" id="passDos3"><b
											class="textoGeneralRojo">*</b> <s:text
												name="Confirmar Password" /></td>
										<td class="campo" id="passDos4"><s:password
												name="usuarioTO.confirmacion" id="passwd2" required="true"
												cssClass="campo_general" maxlength="16" size="40" />
										</td>
									</tr>

									<tr>
										<td class="textoGeneralAzul" align="right"></td>
									</tr>
									<tr>
										<td></td>
										<td>
											<div id='_divLabelComboAdmin'>
												<table width="40" align="left" bgcolor="#FFFFFF"
													cellspacing="2" cellpadding="2">
													<tr class="texto_General">
														<td class="textoGeneralAzul" align="right"><b
															class="textoGeneralRojo">*</b> <s:text name="Grupo" />
														</td>

														<td id="grup">
														<s:select name="idGrupoSelect" id="idGrupoSelect"
																key="grupo" list="grupos"
																listKey="id" listValue="descripcion" required="false"
																class='thickbox' />
														</td class="textoGeneralAzul" align="right">

													</tr>
													<tr>
														<td>
														<p><a id="agregaGrupoNodal" tabindex="40"
															href='<%=request.getContextPath()%>/controlusuario/iniciaAltaGrupo.do?grupo=N&keepThis=true&height=500&width=800&TB_iframe=true'
															title='Agregar Grupo' class='thickbox'> 
															<s:text
																	name="Agregar Grupo"></s:text> </a>
																	</p>
														</td>
													</tr>
												</table>

											</div>
											<div id='_divLabelComboOper'>
												<table width="40" align="right" bgcolor="#FFFFFF"
													cellspacing="2" cellpadding="2">

												</table>
											</div>
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td class="btnSubmit"><input type="button" value="Aceptar"
								id="btnAceptar" alt="Aceptar" onclick="validaAltaUsuario();" />
							</td>
							<td><input type="button" value="Ocultar"
								onclick="ocultarAltaUsuario();" />
							</td>
						</tr>
					</table>
				</s:form>
			</td>
		</tr>
	</table>


	<s:if test="existeUsuario">
		<script type="text/javascript">
		alert('El usuario ya esta agregado a un grupo de este acreedor.');
	</script>
	</s:if>
	<s:if test="rfcError!=null">
		<script type="text/javascript">
		alert('El RFC es invalido');
	</script>
	</s:if>

<script type="text/javascript">
	var nombreCompleto = '<s:property value="acreedorTO.nombreCompleto"/>';
	document.getElementById('cargaNombre').innerHTML = '<s:property value="acreedorTO.nombreCompleto"/>' ;
</script>


