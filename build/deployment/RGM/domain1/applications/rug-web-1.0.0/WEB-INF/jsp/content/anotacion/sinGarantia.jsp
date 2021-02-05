<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 
<script>
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('frmAnotacionSinGarantia').submit();
}
function validar(){
	var val=0;
	if (noVacio(document.getElementById('autoridad').value)){
		val=val+1;
	}
	else{
		displayAlert(true,"Información Incompleta","El campo de Persona que solicita o Autoridad que instruye la Anotación es obligatorio");
		return false;
		}
	if (document.getElementById('sepuedecontinuar')!=null){
		val=val+1;
	}else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","Debe Ingresar los datos de la persona en cuyo Folio Electrónico se realizará la Anotación");
		return false;
		}
	if (noVacio(document.getElementById('anotacion').value)){
		val=val+1;
	}
	else{
		displayAlert(true,"Información Incompleta","El campo de Contenido de la Resolución  es obligatorio");
		return false;
		}
	if (noVacio(document.getElementById('meses').value)){
		val=val+1;
	}
	else{
		displayAlert(true,"Información Incompleta","El campo de vigencia de la Anotación es obligatorio");
		return false;
		}
	if(val==4){
		sendForm();
	}
}
</script>


<s:form  action="guardaAnotacion.do" id="frmAnotacionSinGarantia" name="frmAnotacionSinGarantia" theme="simple">


	<table id="tablaPaso1Gral" cellspacing="5" cellpadding="0"> 
		<tr> 
			<td width="740" height="25" style="padding-left: 5px;"><h1>Anotaci&oacute;n</h1> <a class="thickbox" href="<%=request.getContextPath()%>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=anotacion_avi.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" target="_blank" tabindex="31"><h2>(Video Tutorial)</h2></a></td>		
		</tr>
	</table>
	<table>
		<tr>
			<td align="left" style="padding-left: 28px;font-size: 10px; height: 28px;" class="texto_azul">* Campo Obligatorio </td> 
		</tr> 
	</table>
	<table class="nota">
	
		<tr>
			<td class="imgNota"><img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" > </td>
			<td align="justify" class="contenidoNota"><s:text name="Las anotaciones que podrán asentarse deberán estar fundadas en Resoluciones Judiciales o Administrativas. Art. 33 Bis RRPC" /> </td>
		</tr>
	</table>
	
	<table width="745">
		<tbody>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="5" cellpadding="0">
						<tbody>
							<tr>
								<td class="tituloInteriorRojo">	 </td>
							</tr>
						
							<tr>
								<td class="tituloHeader1" height="16"> 1. Solicitante </td>
							</tr>
							<tr>
								<td>
									<table width="732">
										<tr>						
											<td colspan="2" class="textoGeneralRojo" style="padding-left: 22px;" align="left"> Persona que solicita o Autoridad que instruye la Anotaci&oacute;n : <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=anotacionContenidoResolucion&keepThis=true&TB_iframe=true&height=170&width=520" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a> </td>
										</tr>
										<tr>
											<td>
												<table width="730">
													<tr>
														<td class="texto_azul" width="18px" style="padding-left: 19px;">* </td>
														<td> 
															<dl style="width: 300px">
																<dd style="width: 300px">
																	<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Es la Autoridad Judicial o Administrativa que ordenó asentar una anotación.</div><span class="hint-pointer">&nbsp;</span></span>
																	<s:textarea cols="80" rows="4" name="autoridad" id="autoridad" maxlength="3000" onkeyup="return ismaxlength(this)"/>
															 	</dd>
															</dl>
														</td>
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
																	<td width="434px;" class="textoGris"  align="justify">a) C. Juez quinto de lo civil en el estado de nuevo León.</td> </tr><tr><td class="textoGris" align="justify">b) C. Juez Décimo Cuarto de lo mercantil del primer partido judicial en el estado de jalisco.</td> 
																</tr> 
															</table>
														</td> 
													</tr> 
												</table>
											</td>
										</tr>	
									</table> 
								</td> 
							</tr>
		
							<tr>
								<td class="tituloHeader1" height="25">2. Persona en cuyo Folio Electr&oacute;nico se realizar&aacute; la Anotaci&oacute;n </td>
							</tr>
		
							<tr>
								<td id="notaMoral">
									<table class="nota">
										<tr>
											<td class="imgNota">
												<img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" > 
											</td>
											<td class="contenidoNota"><s:text name="El Otorgante persona moral deberá estar previamente inscrito en el Registro Público de Comercio, incluyendo las sociedades extranjeras." /> </td>
										</tr>
									</table>	
								</td>
							</tr>
							
							<tr>
								<td id="notaFisica" style="visibility: hidden; display: none;"></td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td id="divParteDWR"> </td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
								<td>
									<table>
				         		         <tr>
				         		            <td class="tituloHeader1" height="25"> 3. Anotaci&oacute;n</td>
				       		            </tr> 
				       		           
				       		            <tr>
					       		            <td width="734">
					       		            	<table class="nota">
													<tr>
														<td class="imgNota"> <img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" >  </td>
														<td class="contenidoNota textoJustificado"><s:text name="En este campo deberá transcribir el contenido de la resolución administrativa o judicial el cual se funda la anotación, así como el contenido de la misma." /><b> <s:text name="Fundamento Legal.-" /></b> <s:text name="Art. 33 bis 4 RRPC" /> </td>
													</tr>
												</table>
											</td>
										</tr>
					
     		  							 <tr>
				         		          	
				         		         	 <td colspan="2" class="textoGeneralRojo" style="padding-left: 21px;" align="left">Contenido de la Resoluci&oacute;n:</td>
				       		          	 </tr>
				         		          
				         		         <tr>
				         		         	<td> 
				         		         		<table> 
				         		         			<tr> 
				         		         				<td class="texto_azul" width="18px" style="padding-left: 19px;">*</td> 
				         		         				<td colspan="2" class="texto_general"> <s:textarea name="anotacion" cols="80" rows="10" class="ComunicaCampo" id="anotacion"/>  </td> 
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
										<td width="474px;" class="textoGris"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris" height="16 px" align="justify">b) Oficio 222/10 Expediente 2288/2010.</td> 
									</tr> 
								</table>
							</td> 
						</tr> 
					</table>
   			 	</td>
   			 </tr>
    		          				</table>
    		          			</td>
    		          		</tr>
						</tbody>
					</table>
				</td>
			</tr>

			<tr>
				<td class="tituloHeader1" height="25" style="padding-left: 6px;">4. Vigencia</td>				
			</tr>
			
			<tr>
				<td height="90">	
						<table class="nota">
								<tr>
									<td class="imgNota"> <img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" > <s:text name="common.nota" /> </td>
									<td class="contenidoNota textoJustificado"><s:text name="El sistema automáticamente le dará una vigencia a la anotación de un año, a menos de que usted establezca  otra." /></td>
								</tr>
				  		</table>
						<table>
							<tr>
								
								<td class="textoGeneralRojo" style="padding-left: 27px;" align="left"><span class="textoGeneralRojo">Indique los meses de la vigencia de la Anotaci&oacute;n:</span></td>
			                </tr>
			               
							<tr>
								<td> 
									<table> 
										<tr> 
											<td class="texto_azul" width="18px" style="padding-left: 24px;">*</td><td colspan="2">
												<dl style="width: 25px">
													<dd style="width: 25px">
														<s:textfield  name="meses" value="12" id="meses" onkeypress="return IsNumber(event);" maxlength="4" size="4"/>
														<span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido">Lapso de tiempo que permanecerá vigente la anotación de la garantía mobiliaria. Art. 32 bis 4 CCom.</div><span class="hint-pointer">&nbsp;</span></span>
													</dd>
												</dl>
											</td>
										</tr> 
										
									</table>
								</td>
							</tr>
						</table>
			  	</td>			
			</tr>
			<tr> 
											<td> 
												<table>
														<tr> 
															<td style="padding-left:45px;" class="textoEjemplo">Ejemplo&nbsp;</td>
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
			<tr>
				<td align="center"> <input type="button" value="Firmar" class="boton_rug"  onclick="validar();" id="bFirmar"/> </td>
			</tr>
		</tbody>
  </table>
</s:form>

	<script>
	
		function IsNumber(evt) {
			var key = (document.all) ? evt.keyCode : evt.which;
			return (key <= 13 || (key >= 48 && key <= 57));
		}
		//setActiveTab('cincoMenu');
		$("#cincoMenu").attr("class","linkSelected");
		var idTramite= <s:property value="idTramite"/>;
		var idPersona = <s:property value="idUsuario"/>;
		
		cargaParteOtorgante('divParteDWR',idTramite, idPersona, '0','0','1');
	</script>