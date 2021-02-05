<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Términos y condiciones del acto o contrato de garantía que desee incluir" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Se recomienda utilizar este campo para incluir cualesquiera circunstancias de hecho o de derecho que considere relevantes para efectos de la publicidad de la Garantía Mobiliaria." /> 
			<br>
		</td>
	</tr>
		
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="El caso de una prenda sin transmisión de posesión constituida en los términos del artículo 358 de la LGSM , el deudor dará en prenda sin transmisión de posesión a sus acreedores los bienes muebles que utilice para la realización de sus actividades preponderantes, sin embargo el deudor podrá dar en garantía a otros acreedores, los bienes que adquiera con los recursos del crédito que le otorguen los nuevos acreedores, caso en el cual, los Bienes Muebles deberán identificarse (y describirse en el RUG) con toda precisión y distinguirse del resto de los bienes muebles que el deudor haya dado enprenda al primer acreedor." /> 
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
				class="ver_todas3" target="_blank"> <s:text name="tipo.empresa.ayuda.reglamento.registro.publico.comercio" />
				</a>
			<br>
		</td>	
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="<b>Art. 33.- </b> Para que proceda un Asiento en el RUG deberá cumplimentarse toda la información que sea identificada como obligatoria en las pantallas del Sistema, misma que será para todos los efectos a que haya lugar la contenida en las formas precodificadas. Para efectos de este artículo, la Secretaría deberá publicar en el Diario Oficial de la Federación los formatos correspondientes a la información que se solicite en las pantallas del Sistema. Siempre que deban ser  inscritos instrumentos jurídicos, se entenderá que los mismos quedan inscritos en el RUG mediante el llenado de la información sobre los mismos que requiera las pantallas del Sistema y la generación del Asiento correspondiente." /> 
			<br>
		</td>
	</tr>	
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name=" Conforme a la cláusula [número de cláusula] del contrato de [nombre del contrato de Garantía Mobiliaria], el [nombre del otorgante de la Garantía Mobiliaria] deberá mantener los bienes muebles objeto de la Garantía Mobiliaria en la siguiente ubicación: Av. Hidalgo No. 321, Interior 123, Colonia Morelos, C.P. 0149870, Delegación Álvaro Obregón; México, D.F." /> 
		</td>
	</tr>
	
</table>
