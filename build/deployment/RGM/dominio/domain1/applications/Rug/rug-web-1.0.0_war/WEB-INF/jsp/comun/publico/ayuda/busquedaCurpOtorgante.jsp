<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Busqueda por CURP" />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Ingrese la Clave Única de Registro de Población (CURP) del Otorgante de la Garantía Mobiliaria. El sistema buscará con base en la CURP del otorgante con la cual se haya matriculado a éste al momento de realizar la inscripción de la garantía mobiliaria." /> 
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
				class="ver_todas3" target="_blank"> <b><!--s:text name="Reglamento del Registro Publico de Comercio" /--></b>
				</a>
			<br>
		</td>	
	</tr>		
	<tr>
		<td align="justify">			
			<br class="texto_general">
				<s:text	name="<b>Artículo 30 Bis 1</b>
Los Asientos se llevarán a cabo en el folio electrónico del Otorgante.
Para efectos del artículo 21, fracción XX del Código de Comercio, se entiende que el folio electrónico antes referido es el que reside en la base de datos nacional del RUG prevista en el artículo 32 Bis 3 del Código de Comercio. En caso de que el Otorgante sea una persona física que no se encuentre matriculada en el Registro, el Sistema la matriculará de oficio para efectos del RUG en la base de datos nacional del RUG.<br/>
Los Asientos quedarán realizados en el momento en el que sean firmados electrónicamente por quien los realiza, cuya fecha y hora quedará establecida en el sello digital de tiempo contenido en la boleta que emita el Sistema.<br/>
Toda consulta y solicitud de certificación de una Garantía Mobiliaria inscrita en el RUG deberá llevarse a cabo exclusivamente conforme al artículo 34 de este Reglamento.<br/>" />
			 <br>
		</td>
	</tr>	

	<tr>
		<td>	
			<br class="texto_general"> 	
				<s:text name="Ejemplo CSCK800807DDFSRC93" />
			<br>
		</td>	
	</tr>	
</table>
