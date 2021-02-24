<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Acreedores Adicionales" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Acreedor es la persona en cuyo favor se otorga una Garantía Mobiliaria." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">
			<br class="texto_general">
				<s:text name="Agregar la información de las demás personas en cuyo favor se otorga la Garantía Mobiliaria, si los hubiere." />
			<br>
		</td>
	</tr>		
	<tr>
		<td align="justify">
			<br class="texto_general">
				<s:text name="Deberán agregarse como "/> "Acreedores Adicionales" 
				<s:text name=" los Acreedores de un crédito sindicado o fideicomisarios de un fideicomiso de garantía, distintos del Acreedor o fideicomisario que inscribe la Garantía Mobiliaria." />
			<br>
		</td>
	</tr>	
		
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td  class="titulo_interior">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf");
				class="ver_todas3" target="_blank"> <s:text name="Reglamento del Registro Público de Comercio" />
				</a>
			<br>
		</td>	
	</tr>	
	

		
	<tr>
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="<b>Artículo 1.-</b> El presente ordenamiento establece las normas reglamentarias a que se sujetará la prestación del servicio del Registro Público de Comercio." />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			 
			<br class="texto_general"> 
				<s:text name="Para efecto de este Reglamento se entiende por:" />
			<br>
		</td>	
	</tr>		
	<tr>
		<td>			
			<br class="texto_general"> 
				<s:text name="I. Acreedor. La persona en cuyo favor se otorga una garantía mobiliaria" /><br />

		</td>	
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name=" ..." />
		</td>	
	</tr>
</table>
