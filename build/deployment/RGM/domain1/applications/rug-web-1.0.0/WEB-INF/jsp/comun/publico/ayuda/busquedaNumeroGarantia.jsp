<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Número de Garantía Mobiliaria" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="El número de garantía mobiliaria es el número que el sistema asigna a cada garantía al quedar inscrita en el RUG." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<br>
				<s:text name="Ingrese el número que fue asignado por el sistema del RUG a una Garantía Mobiliaria al momento de su inscripción." />
			<br>
		</td>
	</tr>		
		
	<tr>
		<td>	
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td  class="texto_general">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf"
				class="ver_todas3" target="_blank"> <b><s:text name="tipo.empresa.ayuda.ley.genreal.mercantiles" /></b>
				</a>
			<br>
		</td>	
	</tr>		
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="Artículo 34.- Toda persona podrá realizar consultas y solicitar la emisión de certificaciones de Asientos que consten en el RUG. La certificación será emitida con firma electrónica y sello digital de tiempo y contendrá, al igual que la boletas que emita el Sistema, una cadena única de datos que podrá ser ingresada al mismo para verificar su autenticidad. " />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			 
			<br class="texto_general"> 
				<s:text name="En toda consulta y certificación el RUG proporcionará la siguiente información:" />
			<br>
		</td>	
	</tr>		
	<tr>
		<td>			
			<br class="texto_general"> 
				<s:text name="......." />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="VIII. Número de la Garantía Mobiliaria, que le haya asignado el Sistema" />
			<br>
		</td>	
	</tr>	
	
</table>
