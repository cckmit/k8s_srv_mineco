<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript">
	var idImage=0;
	function newCapcha(){
		getObject('captchaImage').src="<%=request.getContextPath() %>/captcha.jp?id="+idImage;
		idImage=idImage+1;
	}
</script>
	<a href="<%=request.getContextPath()%>/usuario/addExtranjero.do"><input type='button' name='AddExtranjero' value='Registro de Usuario' class="boton_rug_largo"></a>
	<a href="<%=request.getContextPath()%>/usuario/jobAvisoP.do"><input type="button" name="JobAnotacion" value='Trabajos' class="boton_rug_largo"></a>
<table width="100%">
	<tr>
		<td width="740" height="25" style="padding-left: 5px;">
			<h1><s:text name="usuario.titulo.registro" /></h1>
		</td>
	</tr>
</table>
<br></br>
<s:actionmessage />
<s:actionerror />
<s:form namespace="usuario" action="saveExtranjero.do" theme="simple">
	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>						
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="Seleccione tipo de Usuario" /></td>
						<td class="campo">		
							<s:select id="tipoUsuario" name="registroUsuario.tipoUsuario" list="listaUsuarios" 
							cssClass="campo_general" onchange="mostrarOcultarRfc()"/>				
						</td>
					</tr>
				
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.nombre" /></td>
						<td class="campo" valign="bottom">
							<s:fielderror fieldName="personaFisica.nombre" />
							<s:textfield name="personaFisica.nombre" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.paterno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoPaterno" />
							<s:textfield name="personaFisica.apellidoPaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="usuario.field.materno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoMaterno" />
							<s:textfield name="personaFisica.apellidoMaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.nacionalidad" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.lugarNacimiento.pais" />		
							<s:select id="nacionalidad" name="personaFisica.idPais" list="nacionalidades"
								listKey="idNacionalidad" listValue="descNacionalidad" 
								cssClass="campo_general" onchange="mostrarOcultarRfc()" value="1"/>				
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="usuario.field.sexo" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.sexo" />
							<s:select name="personaFisica.sexo" list="genero" cssClass="campo_general"/>	
						</td>
					</tr>
					<tr id="rfc">
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="usuario.field.rfc" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.rfc" />
							<s:textfield id="textRfc" name="personaFisica.rfc" required="false" cssClass="campo_general" maxlength="13" size="40" onkeyup="this.value = this.value.toUpperCase()"/>
						</td>
					</tr>
					
					<tr id="nSerieCert">
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="usuario.field.nSerieCert" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.nSerieCert" />
							<s:textfield id="nSerieCertText" name="personaFisica.nSerieCert" required="false" cssClass="campo_general" 
							maxlength="70" size="100" />
						</td>
					</tr>
					
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.correo" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.datosContacto.emailPersonal" />
							<s:textfield name="personaFisica.datosContacto.emailPersonal" required="true" onkeyup="this.value = this.value.toLowerCase()"
									cssClass="campo_general" maxlength="256" size="40" cssStyle="text-transform:lowercase" />
						</td>
					</tr>
					<tr valign="top">
						<td></td>
						<td class="ayuda">
							<table class="ayuda">
							<tr>
								<td class="texto_general">
									<s:text name="usuario.field.correo.ayuda" /><br /><br />
								</td>
							</tr>
							</table>								
						</td>
					</tr>
	<%-- 			<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.password" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.password" />
							<s:password name="registroUsuario.password" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
						</td>
					</tr>
					<tr valign="top">
						<td></td>
						<td class="ayuda">
							<table class="ayuda">
							<tr>
								<td class="texto_general">
									<s:text name="usuario.field.password.ayuda" /><br /><br />
								</td>
							</tr>
							</table>								
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.confirmacion" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.confirmacion" />
							<s:password name="registroUsuario.confirmacion" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
						</td>
					</tr>
		--%>			
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.pregunta" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.pregunta" />
							<s:select name="registroUsuario.pregunta" list="preguntas"
								listKey="pregunta" listValue="pregunta" 
								cssClass="campo_general"/>
						</td>
					</tr>
					
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="usuario.field.respuesta" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.respuesta" />
							<s:textfield name="registroUsuario.respuesta" required="true"
									cssClass="campo_general" maxlength="50" size="40" />
						</td>
					</tr>
										
					<tr>
						<td class="terminos" colspan="2">
							<br><br>
							<h3>
							<s:text name="usuario.add.aviso" />
							</h3>
						</td>
					</tr>
					<tr>
						<td class="btnSubmit" colspan="2">
							<s:url id="uri"  value="%{getContextPath()}/imgs/%{getText('common.img.i18n.path')}/aceptar.gif" />
							<s:submit type="button" src="%{uri}" name="Aceptar" value="Aceptar" align="center" />
						</td>
					</tr>
				</table>			
			</td>
			<td width="15%" valign="top">
<!-- 			<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg" /> -->
			</td>			
		</tr>			
	</table>
</s:form>

<script type="text/javascript">
function showTerminos() {
    var URL="<%=request.getContextPath()%>/comun/publico/help.do?llave=terminosUsoModal";
    window.open(URL,"ventana1","width=500,height=750,scrollbars=1");
}
function showPoliticas() {
    var URL="<%=request.getContextPath()%>/comun/publico/help.do?llave=politicasPrivacidadModal";
    window.open(URL,"ventana1","width=500,height=750,scrollbars=1");
}
</script>