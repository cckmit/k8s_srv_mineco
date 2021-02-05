<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var idImage=0;
function newCapcha(){

getObject('captchaImage').src="<%=request.getContextPath() %>/captcha.jp?id="+idImage;
idImage=idImage+1;
}
</script>

<table width="100%">
	<tr>
		<td class="encabezadoTablaResumen" width="100%">
			<s:text name="roles.titulo.registro" />
		</td>
	</tr>
</table>

<s:actionmessage />
<s:actionerror />

<s:form namespace="roles" action="save.do" theme="simple">
	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.jefe" /></td>
						<td class="campo" valign="bottom">
							<s:property value="#session.User.profile.nombre"/> <s:property value="#session.User.profile.apellidoPaterno"/> <s:property value="#session.User.profile.apellidoMaterno"/>
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.nombre" /></td>
						<td class="campo" valign="bottom">
							<s:fielderror fieldName="personaFisica.nombre" />
							<s:textfield name="personaFisica.nombre" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.paterno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoPaterno" />
							<s:textfield name="personaFisica.apellidoPaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="roles.field.materno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoMaterno" />
							<s:textfield name="personaFisica.apellidoMaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.correo" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.datosContacto.emailPersonal" />
							<s:textfield name="personaFisica.datosContacto.emailPersonal" required="true"
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
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.password" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.password" />
							<s:password name="registroUsuario.password" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="roles.field.confirmacion" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.confirmacion" />
							<s:password name="registroUsuario.confirmacion" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
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
<!--				<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg" /> -->
			</td>			
		</tr>			
	</table>
</s:form>
