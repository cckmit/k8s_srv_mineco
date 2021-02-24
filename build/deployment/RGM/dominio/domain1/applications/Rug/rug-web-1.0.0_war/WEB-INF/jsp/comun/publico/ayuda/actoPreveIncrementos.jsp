<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellspacing="0" cellpadding="0" width="90%" height="90%"
	border="0" valign="top">
	<tr>
		<td align="justify" class="tituloInteriorRojo">
			<br class="tituloInteriorRojo"><s:text name="El acto o contrato prevé incrementos, reducciones o sustituciones  de los bienes muebles o del monto garantizado" /><br />
				<img src="<%= request.getContextPath() %>/resources/imgs/plecagris_delgada.jpg"
				width="500" height="3" /><br>
		</td>
	</tr>
	<tr>
		<td align="justify">		
			<br class="texto_general">
				<s:text	name="Para evitar que usted tenga que realizar modificaciones en el RUG en caso de un convenio modificatorio o cualquier acto que cambie los términos y condiciones de la Garantía Mobiliaria, el sistema le permite señalar desde el momento de la inscripción, que el Acto o Contrato prevé incrementos, reducciones o sustituciones de los bienes muebles o del monto garantizado." /> 
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
				class="ver_todas3" target="_blank"> <s:text name="Reglamento del Registro Publico de Comercio" />
				</a>
			<br>
		</td>	
	</tr>	
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="Art.33 Bis.- Las operaciones que se pueden realizar en el RUG son las siguientes:" />
			 <br>
		</td>
	</tr>	
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="..." />
			 <br>
		</td>
	</tr>
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="I.	Modificación, transmisión, rectificación por error y cancelación de garantías mobiliarias, así como renovación de inscripción." />
			 <br>
		</td>
	</tr>
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="..." />
			 <br>
		</td>
	</tr>
	<tr>
		<td>			
			<br class="texto_general">
				<s:text	name="Cuando el acto por virtud del cual se crea una Garantía Mobiliaria prevea incrementos, reducciones o sustituciones de bienes muebles o del monto garantizado, no será necesario realizar modificaciones siempre y cuando dicha circunstancia haya quedado asentada en el momento de su inscripción en el RUG." />
			 <br>
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
				<s:text	name="e)	Ganado Preñado, donde las crías quedan afectadas" /><br>
				<s:text	name="f)	Materia Prima sujeta a los procesos de fabricación " />
			 <br>
		</td>
	</tr>
</table>
