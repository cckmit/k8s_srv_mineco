<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<TABLE border="0" width="600" bgcolor="#FFFFFF" align="center">
	<TBODY><TR>
		<TD width="45%">
		<TABLE width="90%" height="25" bgcolor="#AD4110">
			<TBODY><TR>
				<TD class="titulo_exterior_blanco" nowrap="nowrap">Inscripci&oacute;n de la Garant&iacute;a Mobiliaria</TD>
			</TR>
		</TBODY></TABLE>
		</TD>
		<TD width="55%" align="right">
		<TABLE width="600px" border="0">
			<TBODY><TR>
				<TD width="100" align="right" class="texto_azul">&nbsp;</TD>
				<TD width="100" align="left">&nbsp;</TD>
				<TD align="left" width="100" class="texto_azul"></TD>
				<TD align="right" width="80" class="texto_azul"><A onclick="retroalimenta();" class="textoAzulPointer" tabindex="27">Contacto</A></TD>
				<TD align="left" valign="bottom"><IMG src="<%= request.getContextPath() %>/imgs/iconoMail.png" onclick="retroalimenta();" border="0"></TD>
				<TD align="right" width="80" class="texto_azul"><A class="textoAzulPointer" href="http://www.tuempresa.gob.mx/portal" tabindex="28"> Inicio</A></TD>
				<TD align="left" valign="bottom">
					<A href="http://www.tuempresa.gob.mx/portal">
					<IMG class="texto_azul" src="<%= request.getContextPath() %>/imgs/iconoHome.png" border="0">
					</A>
				  </TD>
					
			</TR>
		</TBODY></TABLE>
		</TD>
	</TR>
	<TR height="3" valign="middle">
		<TD colspan="2" width="100%"><IMG src="<%= request.getContextPath() %>/imgs/plecagris_delgada.jpg" width="770" height="3"></TD>
	</TR>
</TBODY></TABLE>
 
</DIV>
<DIV id="workingContainer">
<TABLE class="workArea" cellspacing="0" cellpadding="0">
<TBODY><TR>
	<TD class="sideMenu">
		<DIV id="sideMenuContainer">
		  <p><BR>
		    </p>
		</DIV>
	</TD>
	<TD>
		<TABLE width="645" cellpadding="0" cellspacing="0">
		<TBODY><TR>
			<TD>
				<DIV id="topNavContainer">
 
<DIV id="contenedorBontonGuardar" style="width: 1610px; display: block;">
  <DIV id="divbotonGuardar" style="position: relative; left: 420px; top: -15px; display: block;"></DIV>
</DIV></DIV>
			</TD>
		</TR>
		<TR>
		  <TD>
		    
		    
		    
		    
  <SCRIPT type="text/javascript" src="/js/engine.js"></SCRIPT>
  <SCRIPT type="text/javascript" src="/js/util.js"></SCRIPT>
  <SCRIPT type="text/javascript" src="/js/DomiciliosDwrAction.js"></SCRIPT>
  <SCRIPT type="text/javascript" src="/js/domicilioService.js"></SCRIPT>
  <SCRIPT type="text/javascript" src="/js/domicilio.js"></SCRIPT>
		    
		    
  <FORM id="idFrmDomicilioDefault" name="frmDomicilio" action="http://www.tuempresa.gob.mx/portal/empresa/domicilios/save.do" method="post">
  <INPUT type="hidden" name="domicilioEdit.idDomicilio" value="" id="idDomicilioEdit">
  <INPUT type="hidden" name="domicilioEdit.idTipoDomicilio" value="" id="idTipoDomicilioEdit">
  <INPUT type="hidden" name="domicilio.idDomicilio" value="" id="idDomicilio">
  <TABLE class="formulario" width="600" cellspacing="0" cellpadding="0">
    <TBODY><TR>
      <TD>
        <TABLE width="100%" border="0" cellspacing="2">
          <TBODY><TR>
            <TD class="titulo_interior_naranja">
              <TABLE width="392">
                <TBODY>
                  <TR>
                    <TD width="305" height="32" class="titulo_exterior_rojo">
                      Alta de Acreedor&nbsp;	                    		</TD>
                    <TD width="75" align="left">
                      <A tabindex="31" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domicilio&keepThis=true&TB_iframe=true&height=500&width=500" title="Ayuda en el domicilio" class="thickbox">
                        <IMG alt="Ayuda en el domicilio" src="<%= request.getContextPath() %>/imgs/documentinfo.png" border="0">				                   	</A>	                    		</TD>
                    </TR>
                  </TBODY></TABLE>
              </TD>
            </TR>  
            
            
            <TR>
              <TD></TD>
              </TR>
            <TR>
              <TD><table>
                <tbody>
                  <tr>
                    <td width="184" class="tituloInteriorRojo">&nbsp;Acreedores adicionales </td>
                    <td width="16" align="left" valign="bottom"><a tabindex="32" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domiciliosRegistrados&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en los domicilios registrados" class="thickbox"> <img alt="Ayuda en los domicilios registrados" src="<%= request.getContextPath() %>/imgs/documentinfo.png" border="0" /></a></td>
                    </tr>
                  </tbody>
                </table></TD>
              </TR>
            <TR>
              <TD><table width="95%" border="0" cellspacing="5" cellpadding="0">
                <tbody>
                  <tr>
                    <td class="texto_azul" width="10">*</td>
                    <td class="textoGeneralRojo" align="left" colspan="2"> Tipo de Persona: </td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td class="texto_general"><select tabindex="42" name="domicilio.claveEstado4" id="domicilio.claveEstado3" class="campo_general" onfocus="estilo_foco(this,'estadoa','erroredo', 'errordel', 'HelpComodin');" onblur="getMunicipios(this); estilo_sinfoco(this,'estadoa','erroredo', '' , 'errorCP');" onchange="resetCP();">
                      <option value="m">Moral</option>
                      <option value="f">F&iacute;sica</option>
                      </select></td>
                    <td class="texto_general" valign="top">&nbsp;</td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Nombre : </span></td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><input tabindex="38" class="campo_general" size="60" maxlength="60" name="domicilio.calle3" id="domicilio.calle3" value="" onfocus="estilo_foco(this, 'tipocalle', 'errornumero', 'error_cp', 'HelpComodin'); onFocusSwitch(this,1);" onblur=" estilo_sinfoco(this, 'tipocalle', 'errornumero', '', 'HelpComodin'); onBlurSwitch(this,1);" /></td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><span class="textoGeneralRojo">RFC : </span></td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><input tabindex="38" class="campo_general" size="15" maxlength="60" name="domicilio.calle3" id="domicilio.calle4" value="" onfocus="estilo_foco(this, 'tipocalle', 'errornumero', 'error_cp', 'HelpComodin'); onFocusSwitch(this,1);" onblur=" estilo_sinfoco(this, 'tipocalle', 'errornumero', '', 'HelpComodin'); onBlurSwitch(this,1);" /></td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Domicilio: </span></td>
                    </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td colspan="2" class="texto_general"><table width="90%" border="0" align="left" cellspacing="0" cellpadding="0">
                      <tbody>
                        <tr>
                          <td width="540" align="left"><script>var delegacion="Seleccione la Delegaci&oacute;n o Municipio";var colonia="Seleccione Colonia";var localidad="Seleccione la Localidad";init('', 'null', 'null', 'null', '');</script>
                            <table>
                              <tbody>
                                <tr>
                                  <td><table width="100%">
                                    <tbody>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">Calle o vialidad</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td class="texto_general" nowrap="nowrap"><input tabindex="38" class="campo_general" size="19" maxlength="60" name="idCalle3" id="idCalle3" value="" onfocus="estilo_foco(this, 'tipocalle', 'errornumero', 'error_cp', 'HelpComodin'); onFocusSwitch(this,1);" onblur=" estilo_sinfoco(this, 'tipocalle', 'errornumero', '', 'HelpComodin'); onBlurSwitch(this,1);" />
                                          No.
                                          <input tabindex="39" class="campo_general" size="8" maxlength="20" name="idNumExt2" id="idNumExt3" value="" onfocus="estilo_foco(this, 'tiponumero', 'errornumero', 'error_cp', 'HelpComodin'); onFocusSwitch(this,1);" onblur=" estilo_sinfoco(this, 'tiponumero', 'errornumero', '', 'HelpComodin'); onBlurSwitch(this,1);" />
                                          Int.
                                          <input tabindex="40" class="campo_general" size="5" maxlength="20" name="idNumInt2" id="idNumInt3" value="" onfocus="onFocusSwitch(this,1);" onblur="onBlurSwitch(this,1);" /></td>
                                        <td class="texto_general" valign="top"><span id="tipocalle3" style="position:absolute;color:5c5c5c;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow.png" alt="" /></td>
                                                <td class="ComunicaAsesoria">El domicilio se integra en primera instancia por la calle con el n&uacute;mero del mismo. En caso de que la direcci&oacute;n tenga un n&uacute;mero interior, por favor ind&iacute;quelo en el tercer campo. <b>Art. 6 fr. VII LGSM, Art. 10 CFF, Art. 15 fr. II LFPA.</b></td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span><span id="errorcalle3" style="position:absolute;color:ff3300;display:none;width:200;">
                                            <table cellspacing="0" cellpadding="2" width="200">
                                              <tbody>
                                                <tr valign="top">
                                                  <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                  <td class="ComunicaError">La calle es un Campo obligatorio.</td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </span><span id="tiponumero3" style="position:absolute;color:5c5c5c;display:none;width:200;">
                                              <table cellspacing="0" cellpadding="2" width="200">
                                                <tbody>
                                                  <tr valign="top">
                                                    <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow.png" alt="" /></td>
                                                    <td class="ComunicaAsesoria">El domicilio se integra en primera instancia por la calle con el n&uacute;mero del mismo. En caso de que la direcci&oacute;n tenga un n&uacute;mero interior, por favor ind&iacute;quelo en el tercer campo. <b>Art. 6 fr. VII LGSM, Art. 10 CFF, Art. 15 fr. II LFPA.</b></td>
                                                    </tr>
                                                  </tbody>
                                                </table>
                                              </span><span id="errornumero3" style="position:absolute;color:ff3300;display:none;width:200;">
                                                <table cellspacing="0" cellpadding="2" width="200">
                                                  <tbody>
                                                    <tr valign="top">
                                                      <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                      <td class="ComunicaError">Calle y n&uacute;mero son obligatorios.</td>
                                                      </tr>
                                                    </tbody>
                                                  </table>
                                                </span><span id="tipointerior3" style="position:absolute;color:5c5c5c;display:none;width:200;">
                                                  <table cellspacing="0" cellpadding="2" width="200">
                                                    <tbody>
                                                      <tr valign="top">
                                                        <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow.png" alt="" /></td>
                                                        <td class="ComunicaAsesoria">El domicilio se integra en primera instancia por la calle con el n&uacute;mero del mismo. En caso de que la direcci&oacute;n tenga un n&uacute;mero interior, por favor ind&iacute;quelo en el tercer campo. <b>Art. 6 fr. VII LGSM, Art. 10 CFF, Art. 15 fr. II LFPA.</b></td>
                                                        </tr>
                                                      </tbody>
                                                    </table>
                                                  </span><span id="errorinterior3" style="position:absolute;color:ff3300;display:none;width:200;">
                                                    <table cellspacing="0" cellpadding="2" width="200">
                                                      <tbody>
                                                        <tr valign="top">
                                                          <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                          <td class="ComunicaError">&nbsp;</td>
                                                          </tr>
                                                        </tbody>
                                                      </table>
                                                    </span></td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><table>
                                          <tbody>
                                            <tr>
                                              <td class="ComunicaTexto">Ejemplo:</td>
                                              <td class="ComunicaCampo">a) Puente de la Morena # 211 int. 4</td>
                                              </tr>
                                            <tr>
                                              <td>&nbsp;</td>
                                              <td class="ComunicaCampo">b) Carretera libre M&eacute;xico - Cuernavaca km. 34</td>
                                              </tr>
                                            </tbody>
                                          </table></td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">C&oacute;digo postal:</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><input tabindex="41" type="text" autocomplete="off" id="cp3" name="cp2" size="6" class="campo_general" maxlength="5" onchange="javascript:_buscarDomicilio(this);" onkeyup="javascript:_buscarDomicilio(this);" onkeypress="return IsNumber(event);" onfocus="onFocusSwitch(this,2); estilo_foco(this,'codpos','error_cp', 'erroredo', 'HelpComodin'); " onblur="onBlurSwitch(this,2); estilo_sinfoco(this,'codpos','error_cp', '-1' , 'errorcod');  " value="" />
                                          <img src="<%= request.getContextPath() %>/imgs/pixel_gris.gif" alt="" width="1" height="1" border="0" align="bottom" id="cpImage3" /></td>
                                        <td class="texto_general" valign="top"><span id="codpos3" style="position:absolute;color:ff3300;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left">&nbsp;</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span><span id="error_cp3" style="position:absolute;color:ff3300;display:none;width:200;">
                                            <table cellspacing="0" cellpadding="2" width="200">
                                              <tbody>
                                                <tr valign="top">
                                                  <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                  <td class="ComunicaError">Campo obligatorio.</td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                            </span></td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><span id="HelpCod3" class="ComunicaCampo" style="width:250;height:80;display:block;">
                                          <table>
                                            <tbody>
                                              <tr>
                                                <td class="ComunicaTexto">Ejemplo:</td>
                                                <td>a) 03800</td>
                                                </tr>
                                              <tr>
                                                <td>&nbsp;</td>
                                                <td>b) 54050</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span></td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">Estado:</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><select tabindex="42" name="domicilio.claveEstado4" id="domicilio.claveEstado4" class="campo_general" onfocus="estilo_foco(this,'estadoa','erroredo', 'errordel', 'HelpComodin');" onblur="getMunicipios(this); estilo_sinfoco(this,'estadoa','erroredo', '' , 'errorCP');" onchange="resetCP();">
                                          <option value="">Seleccione Entidad Federativa</option>
                                          <option value="AGS">AGUASCALIENTES</option>
                                          <option value="BCN">BAJA CALIFORNIA</option>
                                          <option value="BCS">BAJA CALIFORNIA SUR</option>
                                          <option value="CAMP">CAMPECHE</option>
                                          <option value="CHIS">CHIAPAS</option>
                                          <option value="CHIH">CHIHUAHUA</option>
                                          <option value="COAH">COAHUILA</option>
                                          <option value="COL">COLIMA</option>
                                          <option value="DF">DISTRITO FEDERAL</option>
                                          <option value="DGO">DURANGO</option>
                                          <option value="MEX">ESTADO DE M&Eacute;XICO</option>
                                          <option value="GTO">GUANAJUATO</option>
                                          <option value="GRO">GUERRERO</option>
                                          <option value="HGO">HIDALGO</option>
                                          <option value="JAL">JALISCO</option>
                                          <option value="MICH">MICHOAC&Aacute;N</option>
                                          <option value="MOR">MORELOS</option>
                                          <option value="NAY">NAYARIT</option>
                                          <option value="NL">NUEVO LE&Oacute;N</option>
                                          <option value="OAX">OAXACA</option>
                                          <option value="PUE">PUEBLA</option>
                                          <option value="QRO">QUERETARO</option>
                                          <option value="QROO">QUINTANA ROO</option>
                                          <option value="SLP">SAN LUIS POTOS&Iacute;</option>
                                          <option value="SIN">SINALOA</option>
                                          <option value="SON">SONORA</option>
                                          <option value="TAB">TABASCO</option>
                                          <option value="TAM">TAMAULIPAS</option>
                                          <option value="TLAX">TLAXCALA</option>
                                          <option value="VER">VERACRUZ</option>
                                          <option value="YUC">YUCAT&Aacute;N</option>
                                          <option value="ZAC">ZACATECAS</option>
                                          </select></td>
                                        <td class="texto_general" valign="top"><span id="erroredo3" style="position:absolute;color:ff3300;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                <td class="ComunicaError">Campo obligatorio.</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span></td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">Delegaci&oacute;n o Municipio:</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><select tabindex="43" name="domicilio.claveDelegacion3" id="domicilio.claveDelegacion2" class="campo_general" onfocus="estilo_foco(this,'deleg','errordel', 'errorcol', 'HelpComodin');" onblur="getColoniasLocalildades(this,document.getElementById('estado')); estilo_sinfoco(this,'deleg','errordel', '' , 'errorCP');" onchange="resetCP();">
                                          <option value="">Seleccione la Delegaci&oacute;n o Municipio</option>
                                          </select></td>
                                        <td class="texto_general" valign="top"><span id="errordel3" style="position:absolute;color:ff3300;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                <td class="ComunicaError">Campo obligatorio.</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span></td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">Colonia:</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><select tabindex="44" name="domicilio.idColonia3" id="domicilio.idColonia2" class="campo_general" onfocus="estilo_foco(this,'colon','errorcol', 'errorloc', 'HelpComodin');" onblur="getCodigoPostalColonia(this); estilo_sinfoco(this,'colon','errorcol', '' , 'errorCP');" onchange="resetLocalidad();">
                                          <option value="">Seleccione Colonia</option>
                                          </select></td>
                                        <td class="texto_general" valign="top"><span id="errorcol3" style="position:absolute;color:ff3300;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left"><img src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png" alt="" /></td>
                                                <td class="ComunicaError">Campo obligatorio.</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span></td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">&nbsp;</td>
                                        <td class="textoGeneralRojo">o</td>
                                        </tr>
                                      <tr>
                                        <td class="texto_azul">*</td>
                                        <td class="textoGeneralRojo">Localidad:</td>
                                        </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                        <td><select tabindex="45" name="domicilio.localidad3" id="domicilio.localidad2" class="campo_general" onfocus="estilo_foco(this,'local','errorloc', 'errorloc', 'HelpComodin');" onblur="getCodigoPostalLocalidad(this); estilo_sinfoco(this,'local','errorloc', '' , 'HelpComodin');" onchange="resetColonia();">
                                          <option value="">Seleccione la Localidad</option>
                                          </select></td>
                                        <td class="texto_general" valign="top"><span id="errorloc3" style="position:absolute;color:ff3300;display:none;width:200;">
                                          <table cellspacing="0" cellpadding="2" width="200">
                                            <tbody>
                                              <tr valign="top">
                                                <td align="left">&nbsp;</td>
                                                <td class="ComunicaError">&nbsp;</td>
                                                </tr>
                                              </tbody>
                                            </table>
                                          </span></td>
                                        </tr>
                                      <tr>
                                        <td colspan="2">&nbsp;<span id="errorcod3" style="display:none;"></span></td>
                                        </tr>
                                      <tr>
                                        <td colspan="2">&nbsp;<span id="errorCP3" style="display:none;"></span></td>
                                        </tr>
                                      </tbody>
                                    </table></td>
                                  </tr>
                                </tbody>
                              </table></td>
                          </tr>
                        <tr>
                          <td align="center"><span class="textoGeneralRojo">
                            <input name="button2" type="submit" class="tituloInteriorRojo" id="button2" value="Guardar" />
                            </span></td>
                          </tr>
                        </tbody>
                      </table></td>
                    </tr>
                  </tbody>
                </table></TD>
              </TR>
            </TBODY>
          </TABLE>
        </TD>
       
        
      </TR>
  </TBODY></TABLE>
    
  <TABLE cellspacing="0" cellpadding="0" width="100%">
    <TBODY><TR align="center">
      <TD class="btnSubmit">&nbsp;</TD>
      </TR>
  </TBODY></TABLE>
  </FORM>
		    
		    
		    
		    
		    
  <SCRIPT> 
	setNavigation('idFrmDomicilioDefault', '/portal/empresa/actividadEconomica/show.do', 'save.do');
</SCRIPT>
		    </TD>
		  </TR>
		</TBODY></TABLE>
	</TD>
</TR>
</TBODY></TABLE>

