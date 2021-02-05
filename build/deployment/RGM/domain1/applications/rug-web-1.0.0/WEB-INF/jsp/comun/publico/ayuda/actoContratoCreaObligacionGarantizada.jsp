<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Acto o contrato que crea la obligación garantizada es el mismo que crea la Garantía Mobiliaria." /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Si la Garantía Mobiliaria se encuentra contenida en el mismo documento que la obligación principal seleccione este campo." /> 
			<br>
		</td>
	</tr>
		
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>*</b>	El Crédito Refaccionario  (Artículo 324 LGTOC)" /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>*</b>	El Crédito de Habilitación o Avío (Artículo 321 LGTOC)" /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>*</b>	Arrendamiento Financiero (Artículo 408 LGTOC)" /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>*</b>	Cláusula de Reserva de Dominio en una Compraventa Mercantil  (Artículo 2312 CCF)" /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>*</b>   Derechos de retención y Privilegios Especiales (en la mayoría de los casos)" /> 
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
				class="ver_todas3" target="_blank"> <s:text name="tipo.empresa.ayuda.reglamento.registro.publico.comercio" />
				</a>
			<br>
		</td>	
	</tr>	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name=" <b>Art. 33.-</b>  Para que proceda un Asiento en el RUG deberá cumplimentarse toda la información que sea identificada como obligatoria en las pantallas del Sistema, misma que será para todos los efectos a que haya lugar la contenida en las formas precodificadas. Para efectos de este artículo, la Secretaría deberá publicar en el Diario Oficial de la Federación los formatos correspondientes a la información que se solicite en las pantallas del Sistema." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Siempre que deban ser  inscritos instrumentos jurídicos, se entenderá que los mismos quedan inscritos en el RUG mediante el llenado de la información sobre los mismos que requiera las pantallas del Sistema y la generación del Asiento." /> 
			<br>
		</td>
	</tr>
</table>
