<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<p class="tituloInteriorRojo"><s:text name="Acreedores Adicionales" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /></p>
		</td>
	</tr>
	<tr>
		<td align="justify">			 
			<p class="texto_general"> 
				<s:text name="Acreedor es la persona en cuyo favor se otorga una Garantía Mobiliaria." />
			</p>
		</td>	
	</tr>	
</table>
