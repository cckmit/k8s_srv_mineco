<%@ taglib prefix="s" uri="/struts-tags"%>

<h2><s:text name="usuario.titulo.recuperacion" /></h2>
<br><br><br>

<s:actionmessage />
<s:actionerror />
<br>
<br>

<s:form namespace="usuario" action="change.do" theme="simple">
<table class="formularioSide">
<tr>
	<td class="terminos" colspan="2">
		<h3>
		<s:text name="usuario.valid.aviso" />
		</h3>
		<br><br>
	</td>
</tr>
<tr>
	<td class="etiquetaRight"><b>*</b><s:text name="usuario.field.correo" />:</td>
	<td class="campo">
		<s:fielderror fieldName="personaFisica.datosContacto.emailPersonal" />
		<s:hidden name="personaFisica.datosContacto.emailPersonal" value="%{personaFisica.datosContacto.emailPersonal}" />
		<b>
		<s:property value="personaFisica.datosContacto.emailPersonal" />
		</b>
	</td>
</tr>
<tr>
	<td class="ayuda" colspan="2" align="center">
		<br>
		<table class="ayuda">
		<tr>
			<td class="titEjemplo">
				<s:text name="common.ejemplo" />
			</td>
			<td class="ejemplo">
				<s:text name="usuario.recover.ejemplo1" /><br>
				<s:text name="usuario.recover.ejemplo2" />
			</td>
		</tr>
		</table>
		<br><br>
	</td>
</tr>
<tr>
	<td class="etiquetaRight">
		<b>*</b><s:property value="registroUsuario.pregunta" />
		<s:hidden name="registroUsuario.pregunta" value="%{registroUsuario.pregunta}" />
	</td>
	<td class="campo">
		<s:fielderror fieldName="registroUsuario.respuesta" />
		<s:textfield name="registroUsuario.respuesta" required="true"
				cssClass="campo_general" maxlength="50" />
	</td>
</tr>
<tr>
	<td class="ayuda" colspan="2" align="center">
		<br>
		<table class="ayuda">
		<tr>
			<td class="titEjemplo">
				<s:text name="common.ejemplo" />
			</td>
			<td class="ejemplo">
				<s:text name="usuario.valid.ejemplo1" /><br>
				<s:text name="usuario.valid.ejemplo2" />
			</td>
		</tr>
		</table>
		<br><br>
	</td>
</tr>



<tr>
	<td class="btnSubmit" colspan="2">
		<s:url id="uri" value="%{getContextPath()}/resources/imgs/%{getText('common.img.i18n.path')}/recuperar.gif" />
		<s:submit type="image" src="%{uri}" align="center" />
	</td>
</tr>
</table>
</s:form>

