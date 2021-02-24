<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Persona que solicita o Autoridad que instruye la Anotación" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" />
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Es la Autoridad Judicial o Administrativa que ordenó asentar una anotación." /> 
		</td>
	</tr>
		
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
		</td>	
	</tr>	
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="C. JUEZ QUINTO DE LO CIVIL EN EL ESTADO DE JALISCO" />
		</td>
	</tr>	
	
</table>
