<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Criterios de Búsqueda" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Toda persona podrá realizar consultas y solicitar certificaciones." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<br>
				<s:text name="Se pueden realizar búsquedas por uno o varios criterios: " />
			<br>
		</td>
	</tr>		
	
	<tr>
		<td>	
			<br class="texto_general"><s:text name="i) Por la Descripción de los Bienes Muebles;"/><br>
		</td>	
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"><s:text name="ii) Por el nombre del Otorgante de la Garantía Mobiliaria;" /><br>
		</td>
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"><s:text name="iii) Por folio electrónico del Otorgante de la Garantía Mobiliaria;" /><br>
		</td>
	</tr>
	<tr>
		<td>	
			<br class="texto_general"><s:text name="iv) Por el Número de Garantía Mobiliaria o de Asiento." /><br>
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
				class="ver_todas3" target="_blank"> <b><s:text name="Reglamento del Registro público de Comercio	" /></b>
				</a>
			<br>
		</td>	
	</tr>		
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="<b>Artículo 34.-</b> Toda persona podrá realizar consultas y solicitar la emisión de certificaciones de Asientos que consten en el RUG." />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="La certificación será emitida con firma electrónica y sello digital de tiempo y contendrá, al igual que la boletas que emita el Sistema, una cadena única de datos que podrá ser ingresada en el sistema para verificar su autenticidad." />
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
				<s:text name="I. Nombre, denominación o razón social del Acreedor, según sea el caso;" />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="II. Nombre, denominación o razón social del Otorgante, según sea el caso;" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>
		
			<br class="texto_general"> 	
				<s:text name="III. Nombre, denominación o razón social del deudor, en aquellos casos en que éste último sea distinto del Otorgante;" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="IV. Folio del Otorgante;" />
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general"> 	
				<s:text name="V.	Tipo de Garantía Mobiliaria;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="VI. Descripción de los bienes muebles objeto de la Garantía Mobiliaria;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	 
			<br class="texto_general"> 	
				<s:text name="VII. Fecha y hora de la inscripción de la Garantía Mobiliaria y de la certificación;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="VIII. Número de la Garantía Mobiliaria, que le haya asignado el Sistema; y" />
			
		</td>	
	</tr>		
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="IX Los Asientos de Garantías Mobiliarias vigentes." />
		</td>	
	</tr>
	
</table>
