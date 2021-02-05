<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Folio Electrónico" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="El Folio Electrónico es el expediente electrónico de un Otorgante en el que se contienen los asientos relativos a Garantías Mobiliarias, así como los actos jurídicos por los que se constituya un privilegio especial o derecho de retención sobre bienes muebles a favor de terceros, incluyendo las anotaciones. Dicho folio electrónico reside en la base de datos nacional del RUG prevista expresamente en el Código de Comercio." /> 
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<br class="texto_general">
				<s:text name="Para Persona Moral ingrese el número de Folio que fue asignado al Otorgante por el Registro Público de Comercio." />
			<br>
		</td>
	</tr>		
	<tr>
		<td>
			<br class="texto_general">
				<s:text name="Para Persona Física ingrese el número de Folio que fue asignado al Otorgante por el RUG." />
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
			<br> <a href="<%= request.getContextPath() %>/resources/pdf/legislacion/CODIGO DE COMERCIO.pdf"); class="ver_todas3" target="_blank"> <s:text name="Codigo de Comercio" /> </a> <br>
		</td>	
	</tr>	
	
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="<b>Artículo 21.-</b> Existirá un folio electrónico por cada comerciante o sociedad, en el que se anotarán:" />
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
		<td>			
			<br class="texto_general"> 
				<s:text name="<b>XX.-</b> Las garantías mobiliarias que hubiere otorgado, así como los actos jurídicos por los queconstituya un privilegio especial o derecho de retención sobre bienes muebles a favor de terceros, en los términos de lo dispuesto por los artículos 32 bis 1 a 32 bis 9 del presente Capítulo." /><br />

		</td>	
	</tr>
	<tr>
		<td  class="titulo_interior">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/Decreto de Reforma del Reglamento del Registro Publico de Comercio.pdf");
				class="ver_todas3" target="_blank"> <s:text name="tipo.empresa.ayuda.reglamento.registro.publico.comercio" />
				</a>
			<br>
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="<b>Artículo 30 bis 1 .- </b> Los Asientos se llevarán a cabo en el folio electrónico del Otorgante. Para efectos del artículo 21, fracción XX del Código de Comercio, se entiende que el folio electrónico antes referido es el que reside en la base de datos nacional del RUG prevista en el artículo 32 Bis 3 del Código de Comercio. En caso de que el Otorgante sea una persona física que no se encuentre matriculada en el Registro, el Sistema la matriculará de oficio para efectos del RUG en la base de datos nacional del RUG." /><br />
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text name="Toda consulta y solicitud de certificación de una Garantía Mobiliaria inscrita en el RUG deberá llevarse a cabo exclusivamente conforme al artículo 34 de este Reglamento." /><br />
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
			<br class="texto_general"> <s:text name="Otorgante Persona Física: R20101026B040" /><br>
		</td>	
	</tr>	
	<tr>
		<td>		
			<br class="texto_general"> <s:text name="Otorgante Persona Moral:"/> "34011*7" I <br>
		</td>	
	</tr>
</table>
