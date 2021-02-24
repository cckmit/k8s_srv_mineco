<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script type="text/javascript">
	$(function () {
		$('.ayuda').ayuda({
			url: '<%= request.getContextPath() %>/comun/publico/help.do',
			ico: '<%= request.getContextPath() %>/resources/imgs/documentinfo.png',
			width: 400,
			height: 300
		})
	})
</script>
<script>
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('frmAnotacionGarantia').submit();
}
function validar(){
	var val=0;
	if (noVacio(document.getElementById('autoridad').value)){
		val=val+1;
	}
	else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","El campo de  Persona que solicita o Autoridad que instruye la Anotación es obligatorio");
		return false;
	}
	if (noVacio(document.getElementById('anotacion').value)){
		val=val+1;
	}
	else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","El campo de Contenido de la Resolución es obligatorio");
		return false;
	}
	if (noVacio(document.getElementById('meses').value)){
		val=val+1;
	}
	else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","El campo de vigencia de la Anotación es obligatorio");
		return false;
	}
	if(val==3){
		sendForm();
	}
}
</script>

<s:form action="guardaAnotacionGarantia.do" namespace="anotacion" name="frmAnotacionGarantia" id="frmAnotacionGarantia" theme="simple">
 
	<div style="padding-top: 32px;">
		<table id="tablaPaso1Gral" cellspacing="5" cellpadding="0"> 
			<tr> 
				<td width="740" height="25" style="padding-left: 5px;"><h1>Anotación</h1> <a class="thickbox" href="<%=request.getContextPath()%>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=anotacion_avi.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" target="_blank" tabindex="31" ><h2>(Video Tutorial)</h2></a></td>		
			</tr>
			<tr>
				<td>
					<table style="padding-left: 6px; padding-right: 5px;" width="743px;">
						<tr>
							<td align="left" style="color: #515151; font-size: 12px; border-color:#BDBDBD; background-color: #D8D8D8; padding-left:10px; padding-top:4px; padding-bottom:4px"><b> <s:property value="nomAcreedor"/> </b></td>
						</tr> 
					</table>
				</td>
			</tr>	
		</table>
	</div>
	
	<table>
		<tr>
			<td align="left" style="font-size: 10px; height: 28px;padding-left: 27px;" class="texto_azul">* Campo Obligatorio </td> 
		</tr> 
	</table>
	
 	<table class="nota">
		<tr>
			<td class="imgNota">
				<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >
				
			</td>
			<td align="justify" class="contenidoNota textoJustificado"><s:text name="Deberá especificar la resolución judicial o administrativa en la cual se funda la anotación, así como el texto a anotar. Fundamento Legal.- Art.33 Bis 3 del RRPC" /> </td>
		</tr>
		
	</table> 
		
	<table width="762" border="0" cellpadding="0">
		<tbody>
			<tr>
				<td colspan="2" class="textoGeneralRojo" align="left" style="padding-left: 17px;"> Persona que solicita o Autoridad que instruye la Anotaci&oacute;n : <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=anotacionContenidoResolucion&keepThis=true&TB_iframe=true&height=175&width=500" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
		 	 </tr>
			<tr>
				<td colspan="2">
					<table>
						 <tr> 
						 	<td class="texto_azul" width="14px;" style="padding-left: 25px;">*</td> 
						 	<td class="textoGeneralRojo" align="left" colspan="2"> <s:textarea name="autoridad" rows="4" cols="70" id="autoridad" maxlength="3000" onkeyup="return ismaxlength(this)"></s:textarea> </td> 
						 </tr> 
					</table>
				</td>
			</tr>
			<tr> 
				<td> 
					<table>
						<tr> 
							<td style="padding-left:40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris textoJustificado"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris textoJustificado" align="justify">b) Oficio 222/10 Expediente 2288/2010.Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V.</td> 
									</tr> 
								</table>
							</td> 
						</tr> 
					</table>
				</td>
			</tr>
      		<tr></tr><tr></tr>
           
          	<tr>	 
	            <td colspan="2" align="center"> 
		            <table class="nota">
						<tr>
							<td class="imgNota"> <img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >  </td>
							<td class="contenidoNota textoJustificado"><s:text name="En este campo deberá transcribir el contenido de la Resolución Administrativa o Judicial el cual se funda la anotación, así como el contenido de la misma. Fundamento Legal.-Artículo 33 Bis 4 RRPC" /> </td>
						</tr>
						
					</table>
				</td>
			</tr>
         	<tr></tr><tr></tr>
        	 
      		 <tr>
  		            
  		            <td colspan="2" class="textoGeneralRojo" width="97%" align="left" style="padding-left: 17px;">Contenido de la Resoluci&oacute;n :</td>
        		 </tr>
    		              
    		 <tr>
  		            <td colspan="2"> 
  		            	<table>
  		            		<tr>	
  		            			<td class="texto_azul" width="14px;" style="padding-left: 25px;">*</td>
  		           				<td class="texto_general" align="left" colspan="2">  <s:textarea name="anotacion" cols="70" rows="10" class="ComunicaCampo" id="anotacion"> </s:textarea> </td>
						</tr>
					</table>    			 	
   			 	</td>
   			 </tr>
   			 <tr> 
   			 	<td> 
   			 		<table>
						<tr> 
							<td style="padding-left:40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris textoJustificado"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris textoJustificado" height="10 px" align="justify">b) Oficio 222/10 Expediente 2288/2010.</td> 
									</tr> 
								</table>
							</td> 
						</tr> 
					</table>
   			 	</td>
   			 </tr>
		</tbody>
	</table>
		
	<table width="762" border="0" cellpadding="0">
		<tr>
			<td class="tituloHeader1" height="31" colspan="2" style="padding-left: 11px;">Vigencia <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=vigenciaInscripcion&keepThis=true&TB_iframe=true&height=290&width=500" title="" class="thickbox"><img alt=" <s:text name="" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a></td>				
		</tr>
		
		<tr></tr><tr></tr>
		
		<tr> 
			<td align="center">
				<table class="nota">
					<tr>
						<td class="imgNota"> <img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >  </td>
						<td class="contenidoNota textoJustificado"><s:text name="El sistema automáticamente le dará una vigencia a la anotación de un año, a menos de que usted establezca  otra. Art. 32 bis 4 CCom. " /> </td>
					</tr>
						
				</table> 
			</td>
		</tr>
	</table>
	
	<tr><td>&nbsp;</td></tr>
	
	<table width="762" border="0" cellpadding="0"> 	
		<tr>
			<td colspan="2" class="textoGeneralRojo" align="left" style="padding-left: 19px;"> Indique los meses de la vigencia de la Anotaci&oacute;n:</td>
        </tr>
        
		<tr>
			<td colspan="2">
				<table> 
					<tr> 
						<td class="texto_azul" width="14px;" style="padding-left: 25px;">*</td>
						<td> <s:textfield  name="meses" value="12" id="meses" onkeypress="return IsNumber(event);" maxlength="4" size="4"/></td>			
					</tr>
				</table>
			</td>			
		</tr>
		<tr> 
			<td> 
				<table>
						<tr> 
							<td style="padding-left:40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
							<td>
								<table>
									<tr>
										<td width="414px;" class="textoGris"  align="justify">a) 1</td> </tr><tr><td class="textoGris" align="justify">b) 48</td> 
									</tr> 
								</table>
							</td> 
						</tr> 
					</table>
			</td>
		</tr>
	</table>
	
	<table>
		<tr>
			<td width="740" align="center"> <input type="button" value="Firmar" class="boton_rug" id="bFirmar" onclick="validar();"/> </td>
		</tr>
	</table>
	
	<table >
		<tr></tr>
		<tr> <td align="center" width="765px"> <hr> </td></tr> <br>
		<tr>
			<td width="730px" align="left" height="21px" class="tituloHeader1" style="padding-left: 12px"> Datos de la Inscripción </td>
		</tr>
		<tr> 
			<td> 
				<table>
					<tr>
						<td class="textoGeneralRojo" style="padding-left:17px" height="21px"> Vigencia:</td> 
						<td style="color: black;"><s:property value="DetalleTO.vigencia"/> Meses </td>
					</tr>
				</table>
			</td>	
		</tr>	
		<tr> 	
			<td>
				<table>
					<tr>
						<td class="textoGeneralRojo" style="padding-left: 17px">Partes de la Garantía Mobiliaria:</td>
						<td style="color: black;"> Otorgante de la Garantía Mobiliaria </td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="tituloHeader1" height="45" colspan="2" style="padding-left: 12px">Detalle de la Garantía Mobiliaria sobre la cúal se realiza la Anotación</td>				
		</tr>
		<tr>
			<td align="center"> 
				<table class="nota">
					<tr>
						<td class="imgNota"><img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" > </td>
						<td class="contenidoNota textoJustificado"><s:text name="Verifique que la información que ha ingresado es correcta. " /> </td>
					</tr>
					<tr>
						<td class="imgNota">
						</td>
					</tr>
				</table> 
			</td>				
		</tr>
	</table>
	
	<table>
		<tr>
			<td width="743">
				<div align="left" height="38px" class="tituloHeader1" style="padding-left: 12px;width: 700px;" ><b>Datos de la Garant&iacute;a Mobiliaria</b></div><div id="detalle" style="margin-top: 10px">
					<table id="mytabledaO" class="mytabledaO" border="0" width="79%" cellspacing="1" cellpadding="1" align="center" style="padding-left: 42px;">
						 <thead>
							<tr>
								<td class="encabezadoTablaResumen" style="text-align: center">Nombre, Raz&oacute;n o Denominaci&oacute;n Social</td>
								<td class="encabezadoTablaResumen" style="text-align: center">Tipo de Persona</td>
								<td class="encabezadoTablaResumen" style="text-align: center">RFC</td>					
							</tr>
						</thead>
						<tbody>
						<s:iterator value="otorganteTOs">	        							
							<tr>				
								<td class="cuerpo1TablaResumen"><div align="center"><s:property value="nombre"/></div></td>
								<td class="cuerpo1TablaResumen"><div align="center"><s:property value="perjuridica"/></div></td>
								<td class="cuerpo1TablaResumen"><div align="center"><s:property value="rfc"/></div></td>								
							</tr>
						</s:iterator>																											
						</tbody>
					</table>		       
					
					<div style="heigth:100px; border:0; background:#FFFFFF;" >&nbsp;</div>
					<div  class="tituloHeader1" height="45" colspan="2" style="padding-left: 12px; width:700px;" >Deudor(es)</div>						
						<table id="mytabledaD" class="mytabledaD" border="0" width="79%" cellspacing="1" cellpadding="1" align="center" style="padding-left: 42px;">
							<thead>
								<tr>
									<td class="encabezadoTablaResumen"
										style="text-align: center">Nombre, Raz&oacute;n o Denominaci&oacute;n Social</td>
									<td class="encabezadoTablaResumen"	style="text-align: center">Tipo de persona</td>					
								</tr>
							</thead>
							<tbody>
							<s:iterator value="deudorTOs">
							<tr>
								<td class="cuerpo1TablaResumen"><div align="center"><s:property value="nombre"/></div></td>
								<td class="cuerpo1TablaResumen"><div align="center"><s:property value="perjuridica"/></div></td>		
							</tr>		
							</s:iterator>
							</tbody>
						</table>
					<s:if test="hayAcreedores">		
						<div style="heigth:100px; border:0; background:#FFFFFF;">&nbsp;</div>											
						<div class="tituloInteriorRojo" style="width: 700px;" >&nbsp;<br align="left">Acreedores adicionales</div>															
							<table id="mytabledaA" class="mytabledaA" border="0" width="79%" cellspacing="1" cellpadding="1" align="center">
								<thead>
									<tr>
										<td width="51%" class="encabezadoTablaResumen"	style="text-align: center">Nombre, Raz&oacute;n o Denominaci&oacute;n Social</td>
										<td width="17%" class="encabezadoTablaResumen"	style="text-align: center">Tipo de Persona</td>
										<td width="11%" class="encabezadoTablaResumen"  style="text-align: center">RFC</td>					
									</tr>
								</thead>
								<tbody>
								<s:iterator value="acreedorTOs">
								<tr>
							  		<td class="cuerpo1TablaResumen"><div align="center"><s:property value="nombre"/></div></td>
									<td class="cuerpo1TablaResumen"><div align="center"><s:property value="perjuridica"/></div></td>
									<td class="cuerpo1TablaResumen"><div align="center"><s:property value="rfc"/></div></td>		
								</tr>
								</s:iterator>			
								</tbody>
							</table>
					</s:if>
			
					 
					 <div class="tituloHeader1" style="padding-left: 12px; height: 31px;"><br><b>Acto o Contrato que crea la Garant&iacute;a Mobiliaria</b><br></div>   
					 <br></br>
					 <div>
				 		<div>       
				              <div class="textoGeneralRojo" style="padding-left: 19px;" ><span class="textoGeneralRojo">Tipo de Garant&iacute;a Mobiliaria:</span></div>
				              <div class="contenidoNota" style="padding-left: 19px;"><s:property value="detalleTO.tipogarantia"/></div>
				        </div>
				        <br>
				        <div>     
				              <div class="textoGeneralRojo" style="padding-left: 19px;" ><span class="textoGeneralRojo">Fecha de celebraci&oacute;n del Acto o Contrato</span></div>
				              <div class="contenidoNota" style="padding-left: 19px;"><s:property value="detalleTO.fecacelebcontrato"/></div>
				        </div>
				    	<br>
				        <div>
				              <div style="padding-left: 19px;"><span class="textoGeneralRojo">Monto Máximo Garantizado:</span> <span class="contenidoNota"> $ <s:property value="detalleTO.monto"/> <s:property value="detalleTO.desmoneda"/></span></div>
				        </div>
				        
				        <div style="padding-left: 19px;" class="textoGeneralRojo"> de Bienes Muebles objeto de la Garant&iacute;a Mobiliaria</div>
						<br>
						<div>	         
				         	<table id="mytabledaO" class="mytabledaO" border="0" width="100%" cellspacing="1" cellpadding="1" align="left">
				 				<tbody>
									<s:iterator value="bienesTOs">
										<tr>
											<td><span class="contenidoNota" ><s:property value="descripcion"/></span></td>
										</tr>	
						             </s:iterator>
						        </tbody>
						   </table>
						</div>   
						 <br>   
			            <div>
			              	<div style="padding-left: 19px;" class="textoGeneralRojo">Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria:</div>
			              	<div class="texto_general" style="padding-left: 44px;"> <label for="textarea"></label>
			                <textarea name="tiposbienes" cols="70" rows="10" readonly="readonly" class="campo_general" id="tiposbienes"><s:property value="detalleTO.descbienes"/></textarea>
			               </div>
			            </div>	               
			  		</div>
			  		
				   <div>
					     <s:if test="actoContratoTO.noGarantiaPreviaOt">
					     	<div class="textoGeneralRojo" style="padding-left: 19px;">Declaro bajo protesta de decir verdad que se solicit&oacute; al otorgante de la garant&iacute;a manifestaci&oacute;n respecto de la NO existencia de garant&iacute;as otorgadas previamente referente a los bienes objeto de esta garant&iacute;a</div>
					     </s:if> 
					     	<div></div>
					</div>
			  		
					<div>
					     <s:if test="actoContratoTO.cambiosBienesMonto">
					     	<div class="textoGeneralRojo" style="padding-left: 19px;">El Acto o Contrato prev&eacute; incrementos, reducciones o sustituciones  de los bienes muebles o del monto garantizado</div>
					     </s:if>
					     	<div></div>
					</div>
					<br>    
				     <div>
				     	<div class="textoGeneralRojo" style="padding-left: 19px">Datos del Instrumento P&uacute;blico mediante el cu&aacute;l se formaliz&oacute; el Acto o Contrato :</div>
				     	<div style="padding-left: 19px; text-align: justify;"><s:property value="detalleTO.instrumento"/></div>	     	
				     </div>   
				     <br>      
		            <div>
		              <div class="textoGeneralRojo" style="padding-left: 19px;">T&eacute;rminos y Condiciones del Contrato de Garant&iacute;a Mobiliaria</div>
		              <div class="texto_general" style="padding-left: 44px;"><textarea name="otrosgarantia" cols="70" rows="10" readonly="readonly" class="campo_general" id="otrosgarantia"> <s:property value="detalleTO.otrosgarantia"/></textarea> </div>
		            </div>            
		 	 	</div>
		  		<br> 
		  		<div>      
		 		 <!--<div  class="encabezado_tabla"><b>Descripci&oacute;n de los bienes</b></div>--><!-- <tr> <td class="texto_azul">Ubicaci&oacute;n de los bienes muebles: </td> <td class="texto_general"><form id="form2" name="form2" method="post" action=""> <label for="textarea2"></label> <textarea name="textarea" cols="70" rows="5" readonly="readonly" class="campo_general" id="textarea2">CABALLO CALCO 14, 1VILLA COYOACAN, COYOACAN, DISTRITO FEDERAL, 04000</textarea> </form></td> </tr> -->
		 		 <s:if test="habilitaobligacion">         
		    		 
		           		<br>
		           		<div class="tituloHeader1" style="padding-left: 12px; height:21px"><b>Acto o Contrato que crea la Obligaci&oacute;n Garantizada</b></div>
	              		 <br>
	              		 <div class="textoGeneralRojo" style="padding-left: 19px">Acto o Contrato que crea la Obligacion Garantizada:</div>
	              		 <br></br>
	               		<div style="padding-left: 19px;text-align: justify;"><s:property value="detalleTO.tipocontrato"/></div>
	          	   		<br>
	          	 		<div>
	            	  		<div class="textoGeneralRojo" style="padding-left: 19px">Fecha de celebraci&oacute;n del Acto o Contrato:</div>
	             	  		<div class="contenidoNota" style="padding-left: 19px"><s:property value="detalleTO.fecacelebcontrato"/></div>
	            		</div>
	               		<br>
		           		<div>
		              		<div class="textoGeneralRojo" style="padding-left: 19px">Fecha de terminaci&oacute;n del Acto o Contrato:</div>
		              		<div class="contenidoNota" style="padding-left: 19px"></div>
		           		</div>
		             <!-- 
		             <tr>
		              <td class="texto_azul">L&iacute;mite del Cr&eacute;dito de las Obligaciones Garantizadas:</td>
		              <td class="contenidoNota" ></td>
		              </tr>
		               -->
		           		 <br>
		            	<div>
		             		<div class="textoGeneralRojo" style="padding-left: 19px">T&eacute;rminos y Condiciones de la Obligaci&oacute;n Garant&iacute;zada</div>
		             		<div class="texto_general" style="padding-left: 44px;"><textarea name="otrosterminos" cols="70" rows="10" readonly="readonly" class="campo_general" id="otrosterminos"><s:property value="detalleTO.otrosterminos"/></textarea> 			</div>
	           			</div>
	  			     	
	 			 </s:if>  
				</div>
			</td>
		</tr>
	</table>
</s:form>
<script>
function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57));
}
</script>