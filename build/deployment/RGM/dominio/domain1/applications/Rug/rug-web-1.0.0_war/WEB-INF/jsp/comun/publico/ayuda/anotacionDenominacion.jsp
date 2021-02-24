<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Denominación o Razón Social" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name=" <b>Denominación</b> es el "/> "nombre" 
				<s:text name=" de una persona moral que la identifica para todos los efectos legales. Se integra por una o varias palabras, normalmente vinculadas a la actividad desarrollada, y no lleva el nombre de ningún socio"/> 
			<br>
		</td>
	</tr>
	<tr>
		<td align="justify">
			<br class="texto_general">
				<s:text name="<b>Razón social</b> es el "/> "nombre" 
				<s:text name=" que se le da a una persona moral integrado por el nombre de uno o más de sus socios." />
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
		<td  class="texto_general">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/legislacion/LEY GENERAL DE SOCIEDADES MERCANTILES.pdf"
				class="ver_todas3" target="_blank"> <b><s:text name="Ley General de Sociedades Mercantiles" /></b>
				</a>
			<br>
		</td>	
	</tr>	
	<tr>
		<td>		
			<br>
				<s:text name="<b>Artículo 6.-</b> La escritura constitutiva de una sociedad deberá contener:" /><br />
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name=" ..." /><br />
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>III.-</b> Su razón social o denominación;" /><br />
		</td>	
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b> ..." /><br />
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
		<td>		
			<br class="texto_general">
				<s:text name="<b> Denominación:</b> Electrón, S.A. de C.V." />
			
		</td>	
	</tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b> Razón Social:</b> López Méndez y Cía, S. A. de C.V. " /><br />
		</td>	
	</tr>
</table>
