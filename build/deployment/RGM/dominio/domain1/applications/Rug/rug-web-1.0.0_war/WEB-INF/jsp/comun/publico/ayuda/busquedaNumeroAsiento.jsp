<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Número de Garantía Mobiliaria o Asiento" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="El Número de Garantía Mobiliaria es el número que el sistema asigna a cada garantía cuando queda inscrita en el RUG." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">
			<br>
				<s:text name="El número de Asiento es el número que el RUG asigna a todas las operaciones distintas de la Inscripción (Modificación, Transmisión, Renovación, Rectificación, Cancelación, Aviso Preventivo y Anotación." />
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
				<s:text	name="<b>Artículo 30.-</b> El registro de los actos en los que se haga constar la constitución, modificación, transmisión o cancelación de Garantías Mobiliarias, se efectuará en el RUG conforme a lo previsto en el Código de Comercio y estará sujeto a lo previsto en los capítulos I, salvo lo relativo a las formas precodificadas, el presente capítulo y VII de este Reglamento." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="..." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Para efectos del presente capítulo, el <b>Asiento</b> es la inscripción de una Garantía Mobiliaria, su modificación, transmisión, renovación, rectificación, cancelación, así como los avisos preventivos y las anotaciones." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name=" <b>Artículo 34.-</b>En toda consulta y certificación el RUG proporcionará la siguiente información:" /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="..." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name=" VIII. Número de la Garantía Mobiliaria, que le haya asignado el Sistema" /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name=" IX. Los Asientos de Garantías Mobiliarias vigentes" /> 
			<br>
		</td>
	</tr>
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="140" /> 
			<br>
		</td>
	</tr>
</table>
