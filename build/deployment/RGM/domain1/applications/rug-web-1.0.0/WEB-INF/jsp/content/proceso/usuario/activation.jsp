<%@ taglib prefix="s" uri="/struts-tags"%>

<h2><s:text name="usuario.titulo.activacion" /></h2>
<br><br><br>

<s:if test="hasFieldErrors()">
	<p class="errorMessage">
		<s:text name="usuario.msg.activacion.error" />
		<br><br>
	</p>
	<s:fielderror />    
</s:if>
<s:elseif test="hasActionErrors()">
	<s:actionerror />
</s:elseif>
<s:else>
	<s:actionmessage />
</s:else>
    
<br><br><br>
<table class="formularioSide">
<tr>
	<td class="etiquetaRight"><s:text name="usuario.field.correo" />:</td>
	<td class="campo">
		<s:property value="personaFisica.datosContacto.emailPersonal" />
	</td>
</tr>
</table>

<br><br>
<a href="<%= request.getContextPath() %>/home/logout.do" class="enlace"><s:text name="common.go.home" /></a>