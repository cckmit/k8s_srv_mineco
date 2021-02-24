<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Deudor(es)" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="El Deudor es la persona que recibió el crédito garantizado por la Garantía Mobiliaria." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">
			<br class="texto_general">
				<s:text name="Ingrese los datos de la persona que recibió el crédito garantizado por la Garantía Mobiliaria, o haga clic en"/> "el otorgante es deudor",
				<s:text name=" si se trata de la misma persona." />
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
				href="<%= request.getContextPath() %>/resources/pdf/legislacion/Decreto de Reforma del Reglamento del Registro Público de Comercio.pdf");
				class="ver_todas3" target="_blank"> <b><s:text name="Reglamento  del Registro Público de Comercio" /></b>
				</a>
			<br>
		</td>	
	</tr>	
	

		
	<tr>
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="<b>Artículo 33 Bis 2.- </b>En la inscripción deberá identificarse en el Sistema la siguiente información:" />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			 
			<br class="texto_general"> 
				<s:text name="<b>I. </b>   El Otorgante y, en su caso, el o los deudores;" />
			<br>
		</td>	
	</tr>		
	<tr>
		<td>			
			<br class="texto_general"> 
				<s:text name=" ... " />
			<br>	
		</td>	
	</tr>
	
</table>
