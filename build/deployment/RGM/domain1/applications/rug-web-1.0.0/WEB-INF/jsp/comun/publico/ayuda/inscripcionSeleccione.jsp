<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Selecciona el acreedor respecto del cual realizará inscripciones" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				 width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Acreedor es la persona en cuyo favor se otorga una Garantía Mobiliaria." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Cada usuario puede tener varios Acreedores, según haya sido autorizado por ellos para realizar operaciones en el RUG." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td  class="titulo_interior">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf");
				class="ver_todas3" target="_blank"> <s:text name="Reglamento del Registro Publico de Comercio" />
				</a>
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name=" <b>Artículo 1.-</b> El presente ordenamiento establece las normas reglamentarias a que se sujetará la prestación del servicio del Registro Público de Comercio." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Para efecto de este Reglamento se entiende por:" /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name=" ..." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="1. Acreedor: La persona en cuyo favor se otorga una Garantía Mobiliaria;" /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="..." /> 
		</td>
	</tr>
</table>
