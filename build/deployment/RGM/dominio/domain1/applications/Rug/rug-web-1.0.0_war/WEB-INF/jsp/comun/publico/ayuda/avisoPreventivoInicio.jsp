<%@ taglib prefix="s" uri="/struts-tags"%>


<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Otorgante" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="El Otorgante es la persona que otorga una garantía mobiliaria. Usualmente coincide con el Deudor." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de una prenda sin transmisión de posesión, el Otorgante sería el Deudor Prendario." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de una hipoteca industrial, el Otorgante sería el Deudor Hipotecario." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de un crédito refaccionario de habilitación o avío, el Otorgante sería el Acreditado (Deudor)." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de una compraventa con reserva de dominio, el Otorgante sería el Comprador." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de un Arrendamiento Financiero, el Otorgante sería el Arrendatario." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de un Fideicomiso de Garantía, el Otorgante sería el Fideicomitente que constituyó el fideicomiso para garantizar una obligación principal." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Tratándose de privilegios especiales o derechos de retención, el Otorgante sería la persona contra la cual se pueden ejercer dichos derechos." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Ingrese los datos de la persona que otorga una Garantía Mobiliaria." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Las Personas se clasifican en:" /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	
	<tr valign=top>
		<td  class="titulo_interior">			
			<p><a href="/Rug/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf" class= ver_todas3 target=_blank> REGLAMENTO DEL REGISTRO PUBLICO DE COMERCIO	</a><br>
		</td>	
	</tr>		
	<tr>
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="<b>Artículo 1o.-</b>"/> 
				<s:text	name="El presente ordenamiento establece las normas reglamentarias a que se sujetará la prestación del servicio del Registro Público de Comercio." />
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
				<s:text name="..." /><br />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<b><s:text name="IV. Otorgante:"/></b> 
				<s:text name="La persona que otorga una Garantía Mobiliaria;" /><br />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>
		
			<br class="texto_general"> 	
				<s:text name="..." /><br />
			<br>
		</td>	
	</tr>	
	

</table>
