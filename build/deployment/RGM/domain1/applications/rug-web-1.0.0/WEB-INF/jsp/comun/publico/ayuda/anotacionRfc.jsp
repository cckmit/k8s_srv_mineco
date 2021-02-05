<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="RFC" /><br>
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Registro Federal de Contribuyentes (RFC) es la clave alfanumérica que el Servicio de Administración Tributaria (SAT) otorga al contribuyente al momento de quedar inscrito en el Registro antes referido." /> 
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Incluye información sobre el domicilio fiscal, obligaciones y declaraciones registradas de toda persona física o moral, que conforme a las leyes vigentes sea contribuyente y/o responsable del pago de contribuciones." /> 
		</td>
	</tr>
</table>
