<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<p class="tituloInteriorRojo"><s:text name="Persona que solicita o Autoridad que instruye la Cancelación y Resolución Judicial o Administrativa en la cual se funda la Cancelación" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /></p>
		</td>
	</tr>
	<tr>
		<td>		
			<p class="texto_general">
				<s:text	name="Ingrese los datos de la Autoridad Judicial o Administrativa que ordenó la Inscripción, los datos de la Resolución Judicial  o Administrativa, así como el texto que debe ser anotado en virtud de la misma" /> 
			</p>
		</td>
	</tr>
	
	<tr>
		<td>	
			<p class="tituloInteriorRojo"><s:text name="Ejemplo" /><br />
			</p>
		</td>	
	</tr>	
	
	<tr>
		<td>	
			<p class="texto_general"> 	
				<s:text name="Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco." /><br />
			</p>
		</td>
	</tr>	
	<tr>
		<td>	
			<p class="texto_general"> 	
				<s:text name="Oficio 222/10 Expediente 2288/2010. Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V., se ordenó girar a usted atento oficio como tengo el honor de hacerlo a efecto de que se sirva llevar a cabo la inscripción dentro del Registro Único de Garantías Mobiliarias, sección del Registro Público de Comercio, del embargo trabado en autos." /><br />
			</p>
		</td>
	</tr>
	
</table>
