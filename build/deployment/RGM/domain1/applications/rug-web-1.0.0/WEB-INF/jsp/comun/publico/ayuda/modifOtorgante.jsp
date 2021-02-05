<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Otorgante de la Garantía Mobiliaria" /><br>
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /></p>
		</td>
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="El Otorgante es la persona que otorga una Garantía Mobiliaria." />
			<br>
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="Ingrese los datos de la persona que otorga la Garantía Mobiliaria." />
			<br>
		</td>	
	</tr>
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td  class="texto_general">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf"
				class="ver_todas3" target="_blank"> <b><s:text name="Reglamento del Registro Publico de Comercio" /></b>
				</a>
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="<b>Articulo 1.- </b> El presente ordenamiento establece las normas reglamentarias a que se sujetará la prestación del servicio del Registro Público de Comercio." />
			<br>
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="Para efecto de este Reglamento se entiende por:" />
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="..." />
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="<b>IV. Otorgante:</b> La persona que otorga una Garantía Mobiliaria;" />
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="..." />
		</td>	
	</tr>
</table>
