<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Persona que solicita o Autoridad que instruye el Aviso Preventivo y Resolución Judicial o Administrativa en la cual se funda el mismo" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	
	<tr> 
		<td><b>Si la solicitud es de un particular: </b></td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Ingrese los datos de la persona (nombre, cargo, datos del instrumento en el que funda su representación o poder, etc.) que solicita realizar el Asiento." /> 
			<br>
		</td>
	</tr>
	<tr> 
		<td><b>Si la solicitud es de una Autoridad:</b></td>
	</tr>
	
	<tr>
		<td>		
			<br class="texto_general">
				<s:text	name="Ingrese los datos de la Autoridad Judicial o Administrativa (nombre, cargo, organización, etc.) que instruyó realizarel Asiento, así como el contenido de la resolución judicial o administrativa." /> 
			<br>
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
		<td  class="titulo_interior">			
			<br><a
				href="<%= request.getContextPath() %>/resources/pdf/legislacion/CODIGO DE COMERCIO.pdf");
				class="ver_todas3" target="_blank"> <s:text name="Codigo de Comercio" />
				</a>
			<br>
		</td>	
	</tr>
	<tr>
		<td class="texto_general">			
			<br>
				<s:text	name="<b>Art. 32 bis 4.-</b>" />
			 <br>
		</td>
	</tr>	
	<tr>
		<td class="texto_general">			 
			<br> 
				<s:text name="..." />
			<br>
		</td>	
	</tr>	
	<tr>
		<td class="texto_general">			 
			<br> 
				<s:text name="Se encuentran facultados para llevar a cabo inscripciones o anotaciones en el Registro, los fedatarios públicos, los jueces y las oficinas habilitadas de la Secretaria en las entidades federativas, así como las entidades financieras, los servidores públicos y otras personas que para tales fines autorice la Secretaria." />
			<br>
		</td>	
	</tr><tr>
		<td class="texto_general">			 
			<br> 
				<s:text name="..." />
			<br>
		</td>	
	</tr><tr>
		<td class="texto_general">			 
			<br> 
				<s:text name="Artículo 32 bis 5.  En los términos que establezca el Reglamento respectivo, de igual forma serán susceptibles de anotarse en el Registro, los avisos preventivos; las resoluciones judiciales o administrativas, así como cualquier acto que por su naturaleza constituye, modifique, transmita o cancele una garantía mobiliaria." />
			<br>
		</td>	
	</tr>
	
	<tr>
		<td class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="Ejemplo" /><br>
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" />
		</td>
	</tr>
	
	<tr>
		<td> <br> <b>Si la solicitud es de un particular: </b> <br> </td>
	</tr>	
	
	<tr>
		<td><br> Licenciado Fermín Espinoza Juárez, Director General Jurídico de Otorgante de Prueba, S.A. de C.V. y apoderado de dicha sociedad como lo acredita mediante poder otorgado ante la fe del Lic. ___, Notario Público Número  123, del Estado de ___ que consta en escritura pública número 123 de fecha 7 de octubre de 2010. <br></td>		
	</tr>
	
	<tr>
		<td> <br> <b>Si la solicitud es de una Autoridad: </b> <br> </td>
	</tr>
	
	<tr>	
		<td> <br> Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco. <br> </td>		
	</tr>	
	
	<tr>
		<td> <br> Oficio 222/10 Expediente 2288/2010. Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V., se ordenó girar a usted atento oficio como tengo el honor de hacerlo a efecto de que se sirva llevar a cabo la inscripción dentro del Registro Único de Garantías Mobiliarias, sección del Registro Público de Comercio, del embargo trabado en autos.</td>		
	</tr>	
</table>
