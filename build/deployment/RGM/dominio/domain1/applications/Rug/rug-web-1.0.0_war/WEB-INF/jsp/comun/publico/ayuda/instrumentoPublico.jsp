<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Datos del instrumento público mediante el cual se formalizó el Acto o Contrato" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Indique el número de instrumento público cuando la operación haya sido formalizada ante fedatario público." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">	
			<br class="texto_general">
				<s:text name="En el caso dela prenda sin transmisión de posesión, el articulo 365 Ley General de Títulos y Operaciones de Crédito dispone que cuando el monto de se refiera a bienes cuyo monto sea igual o superior al equivalente en moneda nacional a doscientos cincuenta mil Unidades de Inversión, las partes deberán ratificar sus firmas ante fedatario." />
			<br>
		</td>
	</tr>		
			
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
			<br>
		</td>	
	</tr>	
	
		<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text name="Mediante instrumento público número [número del instrumento público] de fecha [día] de [mes] del [año], otorgada ante la fe del Lic. [nombre del fedatario público], [Corredor/Notario] Público número [número de fedatario público] de [Nombre de la Entidad Federativa], se hizo constar la ratificación de firmas del contrato de [nombre del contrato de Garantía mobiliaria] celebrado con esa misma fecha entre [nombre del acreedor], en su carácter de acreedor, y [nombre del otorgante de la Garantía mobiliaria], en su carácter de otorgante de [carácter del otorgante de la Garantía Mobiliaria]." /><br />
			<br>
		</td>	
	</tr>
</table>
