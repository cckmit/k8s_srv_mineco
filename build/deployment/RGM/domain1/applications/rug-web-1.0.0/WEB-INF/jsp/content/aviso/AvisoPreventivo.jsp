<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tooltips/tooltip.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/tooltip.css" /> 
<script>
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('frmAviso').submit();
}

function validar(){
	var val=0;
	if(document.getElementById('hayAutoridad').value =="si"){
		if(noVacio(document.getElementById('autoridad').value)){
			val=val+1;
		}
		else{
			changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
			displayAlert(true,"Error en llenado de Datos","El campo de Persona que solicita o Autoridad que instruye el Aviso Preventivo y contenido de la Resolución es obligatorio");
			return false;
		}
	}else{
			val=val+1;
		}
	if (document.getElementById('sepuedecontinuar')!=null){ 
		val=val+1;
	}else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","Debe registrar un Otorgante de la Garantía Mobiliaria");
		return false;
	}
	if (noVacio(document.getElementById('taDescBienes').value)){
		val=val+1;
	}else{
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Información Incompleta","El campo de Descripción de los Bienes Muebles objeto de la Garantía Mobiliaria es obligatorio");
		return false;
	}
	if(val==3){
		sendForm();
	}
}
</script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>

<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>


<div id="cuerpo"><!-- Empieza div cuerpo -->
<s:form action="avisoGuarda.do" namespace="home" name="frmAviso" id="frmAviso" theme="simple">

	<table > 
		<tr> 
			<td width="740" height="25" style="padding-left: 5px;"><h1>Aviso Preventivo</h1> <a class="thickbox" href="<%=request.getContextPath() %>/resources/videos/video.jsp?llave=videoRenovacion&heightV=450&widthV=500&videoUrl=6_aviso_mov.flv&alone=1&keepThis=true&TB_iframe=true&height=490&width=500" target="_blank" tabindex="31" ><h2>(Video Tutorial)</h2></a></td>
		</tr>
		<tr></tr>
		<tr>
			<td>
				<table style="padding-left: 6px; padding-right: 5px;" width="743px;">
					<tr>
						<td align="left" style="color: #515151; font-size: 12px; font:bold; border-color:#BDBDBD; background-color: #D8D8D8; padding-top:4px; padding-bottom:4px; padding-left: 5px; "><b>Acreedor:  <s:property value="nomAcreedor" /> </b></td> 
					</tr> 
				</table>
			</td>
		</tr>
	</table>
	
	<table>
		<tr>
			<td align="left" style="padding-left: 28px;font-size: 10px; height: 28px;" class="texto_azul">* Campo Obligatorio </td> 
		</tr> 
	</table>
	
	<table>
		<tbody>
			<tr>
				<td width="740">
					<table width="98%" border="0" cellspacing="5" cellpadding="0">
						
								<tbody>
									<tr>
										<td class="tituloInteriorRojo">				
											<table width="734">
										 		 <tr>
													<td width="163" class="titulo_exterior_rojo"></td>
											
												</tr>
											</table>
										</td>
									</tr>
										<% if (priv.get(new Integer(20))!=null ) { %>
											
									<tr>
										<td class="tituloHeader1" height="25">1. Solicitante</td>
										<td style="visibility: hidden;display: none;"><input id="hayAutoridad" value="si"></td>
									</tr>
											
									<tr>
										<td>
											<table>
												<tr>
													<td  colspan="3" class="textoGeneralRojo" align="left" style="padding-left: 22px"> Persona que solicita o Autoridad que instruye el Aviso Preventivo y Resoluci&oacute;n Judicial o Administrativa en la cual se funda el mismo : <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=autoridadAvisoPreventivo&keepThis=true&TB_iframe=true&height=300&width=500" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a>
												</tr>
												<tr>
													<td>
														<table> 
															<tr>
																<!-- <td class="texto_azul" style="padding-left: 14px; padding-right: -24px;  width: 10px">*</td> -->
																<td style="padding-left: 18px;" class="texto_azul" width="19px">*</td>
																<td style="padding-right: 35px;" colspan="1"><s:textarea cols="70" rows="5" name="autoridad" id="autoridad" onkeyup="return ismaxlength(this)" maxlength="3000"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table> 
											<table>
												<tr> 
													<td style="padding-left:40px;" class="textoEjemplo">Ejemplo&nbsp;</td>
													<td>
														<table>
															<tr>
																<td width="414px;" class="textoGris textoJustificado"  align="justify">a) Licenciado Francisco Rodríguez Hernández, Juez Décimo Cuarto de lo Mercantil del Primer Partido Judicial en el Estado de Jalisco.</td> </tr><tr><td class="textoGris textoJustificado" height="31 px" align="justify">b) Oficio 222/10 Expediente 2288/2010. Dentro de los autos del Juicio Mercantil Ejecutivo promovido por Ricardo Ugalde García en contra de REFACCIONES ÚTILES Y GRANOS, S.A. DE C.V.</td> 
															</tr> 
														</table>
													</td> 
												</tr> 
											</table>
										</td>
									</tr>
										<% }else{%>
									<tr>
										 <td style="visibility: hidden;display: none;"><input id="hayAutoridad" value="no"></td>	
									</tr>
										<% }%>
									<tr>
										<td class="tituloHeader1" height="25">2. Otorgante de la Garant&iacute;a Mobiliaria <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=avisoPreventivoInicio&keepThis=true&TB_iframe=true&height=500&width=500" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a></td>
									</tr>
										
									<tr align="center">
										<td width="665" id="notaMoral">
											<table>
												<tr>
													<td>
														<table class="nota" align="center">
															<tr>
																<td width="447" class="imgNota">
																	 <div align="center"><img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" /> </div></td>
																<td class="contenidoNota"><s:text name="El Otorgante persona moral deberá estar previamente inscrito en el Registro Público de Comercio, incluyendo las sociedades extranjeras." /> </td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
										
									<tr align="center">
										<td id="notaFisica" style="visibility: hidden; display: none;">
											<table width="469" height="42" align="center" class="nota">
													<tr>
														<td width="447" class="imgNota"> <div align="center"> <img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" /> <b> <s:text name="common.nota" /> </b></div> </td>
														<td class="contenidoNota"><s:text name="Si  el Otorgante no cuenta con folio electrónico, el Sistema lo matriculará  de oficio para efectos del RUG" /> </td>
													</tr>
											  </table>
										</td>
									</tr>	
									
									<tr>
										<td>
											<table>
												<tr>
													<td id="divParteDWR"></td>
												</tr>
											</table>
										</td>
									</tr>
										
									<tr>
										<td>
											<table>
							         			<tr>
							         		    	<td colspan="2" class="tituloHeader1" height="25">3. Bienes Muebles objeto de la Garant&iacute;a Mobiliaria</td>
							       		        </tr> 
													
												<tr> 
													<td>
														<table>
												<tr align="center">
													<td width="665" id="notaMoral">
														<table>
															<tr>
																<td>
																	<table class="nota" align="center">
																		<tr>
																			<td width="447" class="imgNota">
																				 <div align="center"><img src="<%=request.getContextPath() %>/resources/imgs/ico_nota.png" /> </div></td>
																			<td class="contenidoNota textoJustificado"><s:text name="La descripción de los Bienes Muebles objeto de la Garantía Mobiliaria que realice en el Aviso Preventivo deberá ser realizada de la misma manera en la Inscripción de la Garantía Mobiliaria correspondiente.<b>Articulo 33 Bis 3 </b>" /> </td>
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
							         		    	<td width="721" class="textoGeneralRojo" style="padding-left: 22px;"> Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria: <a tabindex="31" href="<%= request.getContextPath() %>/comun/publico/help.do?llave=avisoPreventivoDescripcionBienes&keepThis=true&TB_iframe=true&height=500&width=500" title="" class="thickbox"><img alt=" <s:text name="cofepris.domicilio.tramite.ayuda" /> "  src="<%= request.getContextPath() %>/resources/imgs/documentinfo.png" border="0"></a>
							       		        </tr>
							         		      
							         		    <tr>
							         		         <td colspan="2">
							         		         	 <table width="729" >
							         		          		<tr>
							         		          			 <td style="padding-left: 18px;" class="texto_azul" width="19px">*</td>
							         		           			 <td>
																	<dl style="width: 480px"><dd style="width: 480px"><span class="hint"> <div class="cerrar"> <a onclick="this.parentNode.getElementsByTagName('span')[0].style.display = 'none';" style="cursor: pointer;">x</a> </div> <div class="contenido"> Características de los bienes muebles objeto de la Garantía Mobiliaria que permiten su identificación. Art. 32 Apartado B RRPC.</div><span class="hint-pointer">&nbsp;</span></span>
																		<s:textarea name="descripcionBienes" cols="70" rows="10" id="taDescBienes" />
																	</dd></dl>
																
																</td>
							         		           	    </tr>
							       		               </table>
							       		               <table>
							<tr>
								<td style="padding-left: 38px;" class="textoEjemplo">Ejemplo&nbsp;</td><td>
									<table>
										<tr>
											<td width="415px;" class="textoGris textoJustificado"  align="justify">a) Vehículo Marca Ford , Modelo Pick Up Lobo LX, doble cabina, automática, placas WES 532,  Número de Identificación vehicular  (VIN) FDH276549964.</td>
						 				</tr>
										<tr>
											<td class="textoGris textoJustificado" align="justify">b) Todos los bienes muebles que utilice para la realización de su actividad preponderante.</td> 
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
				<td align="center">
			    <!--<a href="< //request.getContextPath() %>/home/busqueda.do"> <input name="button" type="button" class="tituloInteriorRojo" value="Cancelar"/> </a> -->
		        <input type="button" value="Firmar" class="boton_rug" onclick="validar();" id="bFirmar"/>
				</td>
			</tr>

		</tbody>
  </table>
</s:form>
</div>
<script>

cargaFormDrirecciones("cpTab");
</script>

<script>
//setActiveTab('tresMenu');
$("#tresMenu").attr("class","linkSelected");


var idTramite= <s:property value="idTramite"/>;
var idPersona = <s:property value="idUsuario"/>;

cargaParteOtorgante('divParteDWR',idTramite, idPersona,'0','0','1'	);

</script>