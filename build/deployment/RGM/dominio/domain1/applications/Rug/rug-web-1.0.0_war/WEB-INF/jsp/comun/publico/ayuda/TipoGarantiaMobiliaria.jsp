<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Tipo Garantia Mobiliaria" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Conforme al Reglamento del Registro Público de Comercio las Garantías Mobiliarias se clasifican en 7 tipos." /> 
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
		<td  class="texto_general">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf"
				class="ver_todas3" target="_blank"> <b><s:text name="Reglamento del Registro Público de Comercio" /></b>
				</a>
			<br>
		</td>	
	</tr>	
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text name="<b>Artículo 32.-</b> Para efectos del RUG, los criterios de clasificación de las Garantías Mobiliarias y de los bienes muebles afectos a las mismas serán los siguientes:" /><br />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b> A.</b> Las Garantías Mobiliarias se clasifican en:" /><br />
			<br>
		</td>	
	</tr>	
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>VII.</b> Prenda sin transmisión de posesión;" /><br />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>IX.</b> La derivada de un crédito refaccionario o de habilitación o avío;" /><br />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>X.</b> La derivada de una hipoteca industrial;" /><br />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>XI.</b> La constituida sobre una aeronave o embarcación;" /><br />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>XII.</b> La derivada de un arrendamiento financiero;" /><br />
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text name="<b>XIII.</b> Cláusula de reserva de dominio en una compraventa mercantil de bienes muebles que sean susceptibles de identificarse de manera indubitable;" /><br />
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text name="<b>XIV.</b> La derivada de un fideicomiso de garantía, derechos de retención,y otros privilegios especiales conforme al Código de Comercio o las demás leyes mercantiles." /><br />
		</td>	
	</tr>
</table>
