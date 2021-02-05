<%@page import="mx.gob.se.rug.garantia.dao.GarantiasDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12 l6">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Consulta o B&uacute;squeda Principal</span>
						<div class="row note">
							Para cualquier consulta puede ingresar uno o m&aacute;s criterios.
						</div>
						<div class="row">
							<s:form namespace="usuario" action="resBusquedaJud.do" theme="simple" cssClass="col s12">
							 	<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="curpOtorgante" id="curpOtorgante" size="15" maxlength="15" onkeypress="return aceptaalfa(event);" 
							        				cssClass="validate tooltipped" data-position="right" data-delay="50" data-tooltip="Ingrese su número sin espacios en blanco"/>
							        	<label for="curpOtorgante">N&uacute;mero de Identificaci&oacute;n del Deudor Garante (DPI o Pasaporte)</label>
							   		</div>
							 	</div>
							 	<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="rfcOtorgante" id="rfcOtorgante" size="15" maxlength="15" onkeypress="return aceptaalfa(event);"  
							        		cssClass="validate tooltipped" data-position="right" data-delay="50" data-tooltip="Ingrese su número sin espacios en blanco ni guión"/>
							        	<label for="rfcOtorgante">NIT del Deudor Garante</label>
							   		</div>
							 	</div>
							 	<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="serial" id="serial" size="25"  maxlength="25" />
							        	<label for="serial">N&uacute;mero de serie</label>
							   		</div>
							 	</div>
							 	<center>
						            <div class='row'>
						            	<a class="btn btn-large waves-effect indigo" onclick="busquedaJudJSP(<s:property value="idPersona"/>,1);">Consultar</a>
						            </div>
					          	</center>
							</s:form>
						</div>
					</div>
				</div>
			</div>
			<div class="col s12 l6">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Consulta o B&uacute;squeda Secundaria</span>
						<div class="row">
							<s:form namespace="usuario" action="resBusqueda.do" theme="simple" cssClass="col s12">
							 	<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="nombreOtorgante" id="nombreOtorgante" size="32" maxlength="100" />
							        	<label for="nombreOtorgante">Nombre o Raz&oacute;n Social del Deudor Garante</label>
							   		</div>
							 	</div>
							 	<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="idGarantia" id="idGarantia" size="17"   maxlength="20" />
							        	<label for="idGarantia">N&uacute;mero de inscripci&oacute;n de la Garant&iacute;a</label>
							   		</div>
							 	</div>
							 	<center>
						            <div class='row'>
						            	<a class="btn btn-large waves-effect indigo" onclick="busquedaJudJSP(<s:property value="idPersona"/>,2);">Consultar</a>
						            </div>
					          	</center>
							</s:form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Resultado de la Consulta</span>
						<div id="resultadoDIV"></div> 
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
<div class="section"></div>

<div id="cuerpo"><!-- Empieza div cuerpo -->
	<div align="center" >
    <table width="960px"> 
      <tr>
			<td valign="top">
        
				<table width="100%" height="100%" >
					
					
					<tr><td> 
            
            
              <table width="100%">
                <tr>
                  <td>
                    <table>  					
                      <s:if test="hayDatos">
                        <tr id="datosCadena">
                          <td>
                            <table >
                              <tr>
                                <td align="left"><span class="textoGeneralRojo"> La Cadena &Uacute;nica de Datos se encuentra relacionado al Tr&aacute;mite:</span> <span class="textoGeneral"><s:property value="tipoOperacion"/> </span></td>  
                              </tr>
                              <tr>
                                <td align="left"> <span class="textoGeneralRojo"> El n&uacute;mero de garant&iacute;a es:</span> <span class="textoGeneral"><s:property value="numGarantia"/> </span></td>
                              </tr>
                              <tr>
                                <td align="left"> <span class="textoGeneralRojo"> Fue realizada el d&iacute;a: </span> <span class="textoGeneral"> <s:property value="fechaInsc"/> </span></td> 
                              </tr>
                              <tr>	
                                <td align="left"> <span class="textoGeneralRojo"> Por el Otorgante de la Garant&iacute;a Mobiliaria: </span> <span class="textoGeneral"> <s:property value="nomOtorgante"/> </span></td>
                              </tr>
                              <tr>	
                                <td align="left"><span class="textoGeneralRojo"> Con el folio electr&oacute;nico: </span> <span class="textoGeneral"> <s:property value="folioOtorgante"/> </span></td>
                              </tr>
                              <tr>
                                <td align="center"> 
																	<h4 style="cursor: pointer">
                                    <a onclick="showBoleta();"> <u>Clic aqu&iacute; para ver la boleta</u></a>
																	</h4>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </s:if>
                      <s:else>
												<s:if test="pasoInicio">
													<tr id="noDatosCadena">
														<td>
															<table>
																<tr>
																	<td class="textoGeneralRojo" align="center"> No existe un Tr&aacute;mite relacionado a esta Cadena &Uacute;nica de Datos</td>  
																</tr>
															</table>
														</td>
													</tr>
												</s:if>
                      </s:else>
                    </table>
                  </td>
                </tr>
                
								<!-- 	<tr> <td height="18px" style="padding-bottom: 1px; padding-top: 18px"> &nbsp;</td> </tr> -->
                
              </table>
					</table>
					
		</td>
      </tr>
      
      <tr>
        <td colspan="2">
          <div id="resultadoOtorgantesPrevios"></div>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <div id="resultadoDIV"></div>
        </td>
      </tr>
    </table>
	</div>
</div>