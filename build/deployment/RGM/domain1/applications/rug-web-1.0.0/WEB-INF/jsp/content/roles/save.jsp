<%@ taglib prefix="s" uri="/struts-tags"%>

<h2><s:text name="usuario.titulo.registro" /></h2>
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

<table class="formulario" width="800px">
<tr>
	<td class="texto_general" width="63%" valign="bottom">
		<br><br>
		<s:text name="usuario.estimado.titulo" />: 
		<b><s:property value="personaFisica.nombre" /> <s:property value="personaFisica.apellidoPaterno" /> <s:property value="personaFisica.apellidoMaterno" /></b> 
	</td>
	<td align="right">
<!--		<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg"/> -->
	</td>
</tr>
<tr>
	<td class="texto_general">
		<s:text name="usuario.registrado.titulo" />:<br>
		<b><s:property value="personaFisica.datosContacto.emailPersonal" /></b>
	</td>
	<td></td>
</tr>
<tr>
	<td class="etiqueta" >
		<br><br>
		<h3>
			<s:text name="usuario.revise.titulo" />
		</h3>
	</td>
	<td></td>
</tr>
</table>
	
</s:else>



<br><br>
<a href="<%= request.getContextPath() %>" class="enlace"><s:text name="common.go.home" /></a>


