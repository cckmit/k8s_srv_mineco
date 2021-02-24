<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Vigencia de la inscripción " /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Es el lapso de tiempo que permanecerá vigente la inscripción de una garantía mobiliaria, misma que puede ser renovado o reducido." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal " /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>	
	<tr>
		<td  >			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/legislacion/CODIGO DE COMERCIO.pdf");
				class="ver_todas3" target="_blank"> <s:text name="Código de Comercio" />
				</a>
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="<b>Artículo 32 bis 4.-</b>" /> 
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
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Salvo que la vigencia de la inscripción o anotación se establezca en la forma precodificada, ésta tendrá una vigencia de un año, misma que será susceptible de ser renovada."/> 
			<br>
		</td>
	</tr>
	
</table>