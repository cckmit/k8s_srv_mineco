<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Descripción de los Bienes Muebles" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Ingrese algún elemento descriptivo de los bienes muebles objeto de la Garantía Mobiliaria. El sistema buscará con base en la descripción de los bienes muebles que haya realizado la persona que llevó a cabo la inscripción." /> 
			<br>
		</td>
	</tr>
	
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Fundamento Legal" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
			<br>
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
				<s:text	name="<b>Artículo 32.-</b> Para efectos del RUG, los criterios de clasificación de las Garantías Mobiliarias y de los bienes muebles afectos a las mismas serán los siguientes:" />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			 
			<br class="texto_general"> 
				<s:text name="..." />
			<br>
		</td>	
	</tr>		
	<tr>
		<td align="justify">			
			<br class="texto_general"> 
				<s:text name="B. Los bienes muebles que pueden ser objeto de una Garantía Mobiliaria se clasifican de la siguiente manera:" />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="I.	Maquinaria y equipo;" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>
		
			<br class="texto_general"> 	
				<s:text name="II.	Vehículos de motor;" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="III.	Ganado; " />
			<br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general"> 	
				<s:text name="IV.	Productos agrícolas;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="V.	Bienes de consumo;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	 
			<br class="texto_general"> 	
				<s:text name="VI.	Inventario;" />
			<br>
		</td>
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="VII.	Acciones y obligaciones, bonos, contratos de opción y futuros;" />
			<br>
		</td>	
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="VIII.	Derechos, incluyendo derechos de cobro; y" />
			<br>
		</td>	
	</tr>		
	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="IX.	Otros." />
			<br>
		</td>	
	</tr>	
	<tr>
		<td class="tituloInteriorRojo">	
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"	width="500" height="3" />
		</td>	
	</tr>	
	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="Tractor Número de Serie K2337654754899JP" />
			<br>
		</td>	
	</tr>	
</table>
