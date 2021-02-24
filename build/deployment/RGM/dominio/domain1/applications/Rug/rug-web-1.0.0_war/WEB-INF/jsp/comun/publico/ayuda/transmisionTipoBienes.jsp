<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Tipos de Bienes Muebles objeto de la Garantía." /><br>
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /></p>
		</td>
	</tr>
	<tr>
		<td align="justify">			 
			<br class="texto_general"> 
				<s:text name="Usted deberá seleccionar uno o varios tipos de Bienes Muebles." />
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
				class="ver_todas3" target="_blank"> <s:text name="Reglamento del Registro Público de Comercio" />
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
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="..." />
			 <br>
		</td>
	</tr>	
	<tr>
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="B. Los bienes muebles que pueden ser objeto de una Garantía Mobiliaria se clasifican de la siguiente manera:" />
			 <br>
		</td>
	</tr>
	<tr>
		<td align="justify">			 
				<s:text name="*	Maquinaria y equipo; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Vehículos de motor; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Ganado; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Productos agrícolas; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Bienes de consumo; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Inventario; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Derechos, incluyendo derechos de cobro; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Derechos previstos en la Ley de la Propiedad Industrial y en la Ley Federal del Derecho de Autor; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	Acciones y obligaciones bonos, contratos de opción Futuros; " />
		</td>	
	</tr>	
	<tr>
		<td align="justify">			 
				<s:text name="*	y Otros." />
		</td>	
	</tr>	
	<tr>
	   <p>
		<td align="justify">			 
				<s:text name="Usted podrá seleccionar uno o varios tipos de Bienes Muebles" />
		</td>
	  </p>	
	</tr>	

</table>
