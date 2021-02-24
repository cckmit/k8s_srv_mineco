<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Otorgante de la Garantía Mobiliaria" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>ADVERTENCIA:</b>Si con motivo de la transmisión cambia al Otorgante, la Garantía Mobiliaria se transmitirá al folio electrónico del nuevo Otorgante y en el folio del Otorgante original se incluirá un asiento indicando <b>Cancelada por Transmisión</b>." /> 
			<br>
		</td>
	</tr>
		
	<tr>
		<td  >			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/legislacion/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf");
				class="ver_todas3" target="_blank"> <s:text name="Reglamento del Registro Publico de Comercio" />
				</a>
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>Artículo 33 Bis.-</b>Las operaciones que se pueden realizar en el RUGson las siguientes:" /> 
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
				<s:text	name="<b>VI.</b> Modificación, transmisión, rectificación por error y cancelación de Garantías Mobiliarias, así como  renovación de inscripción." /> 
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
				<s:text	name=" Se entiende por transmisión, el Asiento de un acto mercantil que agregue, elimine o modifique al Otorgante y/o al o los Acreedores. Si la transmisión es respecto del Otorgante, se asentará una cancelación por transmisión en el folio del Otorgante original y una inscripción por transmisión en el folio del nuevo Otorgante." /> 
			<br>
		</td>
	</tr>
	
</table>
