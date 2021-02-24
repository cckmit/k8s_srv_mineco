<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<TABLE class="workArea" cellspacing="0" cellpadding="0">
<TBODY><TR>
	<TD class="sideMenu">
		<DIV id="sideMenuContainer">
		  <p><BR>
		    </p>
		  <table width="80%" border="0" cellspacing="3" cellpadding="2">
		    <tr>
		      <td><table align="right" cellspacing="5" class="menuLateral">
		        <tbody>
		          <tr class="menuLateralNivel1">
		            <td align="left">&nbsp;</td>
		            <td align="left" class="tituloInteriorRojo">Anotaci&oacute;n</td>
		            </tr>
		          <tr class="menuLateralNivel1">
		            <td width="13" align="left"><span class="texto_general"><img src="<%= request.getContextPath() %>/imgs/cuadroVerde.png" alt="" width="11" height="11" /></span></td>
		            <td width="122" align="left"><a href="Inscripcion_01.htm" class="textoGeneralGrisBold" id="menua">1. Datos de la anotaci&oacute;n</a></td>
		            </tr>
		          <tr>
		            <td class="texto_general"><img src="<%= request.getContextPath() %>/imgs/cuadroVerde.png" alt="" width="11" height="11" /></td>
		            <td class="texto_general"><a href="Validaci&oacute;n.htm" class="textoGeneralGrisBold" id="menua3">2. Validaci&oacute;n</a></td>
		            </tr>
		          <tr>
		            <td class="texto_general"><img src="<%= request.getContextPath() %>/imgs/cuadroVerde.png" alt="" width="11" height="11" /></td>
		            <td class="texto_general"><a href="Pago de Derechos.htm" class="textoGeneralGrisBold" id="menua4">3. Pago de derechos</a></td>
		            </tr>
		          </tbody>
		        </table></td>
		      </tr>
		    <tr>
		      <td>&nbsp;</td>
		      </tr>
		    <tr>
		      <td><table cellpadding="0" cellspacing="3" class="tituloEncabezado">
		        <tbody>
		          <tr class="menuLateralNivel1">
		            <td colspan="3" align="center" bgcolor="#AD4110" class="titulosupblanco">Transacciones por pagar</td>
		            </tr>
		          <tr class="menuLateralNivel1">
		            <td width="63" align="left" class="texto_inactiva">Inscripci&oacute;n</td>
		            <td width="36" align="center"><span class="texto_inactiva">2</span></td>
		            <td width="62" align="left"><span class="texto_inactiva">$ 100.00</span></td>
		            </tr>
		          <tr>
		            <td class="texto_general"><span class="texto_inactiva">Modificacion</span></td>
		            <td align="center" class="texto_general"><span class="texto_inactiva">2</span></td>
		            <td class="texto_general"><span class="texto_inactiva">$ 100.00</span></td>
		            </tr>
		          <tr>
		            <td class="texto_general"><span class="texto_inactiva">Anotaci&oacute;n</span></td>
		            <td align="center" class="texto_general"><span class="texto_inactiva">2</span></td>
		            <td class="texto_general"><span class="texto_inactiva">$ 100.00</span></td>
		            </tr>
		          <tr>
		            <td colspan="3" class="texto_general"><img src="<%= request.getContextPath() %>/imgs/plecablanca.png" alt=" " border="0" width="150" height="11" /></td>
		            </tr>
		          <tr>
		            <td class="texto_general"><span class="texto_inactiva">Total</span></td>
		            <td align="center" class="texto_general">&nbsp;</td>
		            <td class="texto_general"><span class="texto_inactiva">$ 300.00</span></td>
		            </tr>
		          </tbody>
		        </table></td>
		      </tr>
		    </table>
        </DIV>
	</TD>
	<TD>
		<TABLE width="645" cellpadding="0" cellspacing="0">
		<TBODY><TR>
			<TD>
				<DIV id="topNavContainer">

<DIV id="contenedorBontonGuardar" style="width: 610px; display: block;">
	<TABLE width="610" cellpadding="2" cellspacing="2" class="CajaDenominacion">
		<TBODY><TR height="25">
			<TD class="subtituloInteriorGris" width="2%">
			<TD class="titulo_exterior_rojo" align="left" width="68%">
				BBVA Bancomer, S.A.</TD>
			<TD width="30%">&nbsp;
				
			</TD>
		</TR>
	</TBODY></TABLE>
	<DIV id="divbotonGuardar" style="position: relative; left: 420px; top: -15px; display: block;">
	<TABLE width="150" cellpadding="2" cellspacing="2">
		<TBODY><TR>
			<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/empresa/domicilios/edit.do#" onclick="submitAction(false);"> <IMG src="./img/botonAnterior.png" border="0" alt="Anterior"> </A></TD>
			<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/empresa/domicilios/edit.do#" onclick="submitAction(true);"> <IMG src="./img/botonSiguiente.png" border="0" alt="Siguiente"> </A></TD>
		</TR>
	</TBODY></TABLE>
	</DIV>
</DIV></DIV>
			</TD>
		</TR>
		<TR>
			<TD>
				



<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/engine.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/util.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/DomiciliosDwrAction.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/domicilioService.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/domicilio.js"></SCRIPT>


<FORM id="idFrmDomicilioDefault" name="frmDomicilio" action="http://www.tuempresa.gob.mx/portal/empresa/domicilios/save.do" method="post">
<INPUT type="hidden" name="domicilioEdit.idDomicilio" value="" id="idDomicilioEdit">
<INPUT type="hidden" name="domicilioEdit.idTipoDomicilio" value="" id="idTipoDomicilioEdit">
<INPUT type="hidden" name="domicilio.idDomicilio" value="" id="idDomicilio">
<TABLE class="formulario" width="780" cellspacing="0" cellpadding="0">
	<TBODY><TR>
		<TD>
	    	 <TABLE width="100%" border="0" cellspacing="2">
        		<TBODY><TR>
        		  <TD class="titulo_interior_naranja">
        		    <TABLE width="448">
        		      <TBODY>
        		        <TR>
        		          <TD width="406" height="32" class="titulo_exterior_rojo">
        		            Anotaci&oacute;n al Registro No. 123456&nbsp; </TD>
        		          <TD width="30" align="left">
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
         		        <td width="335" class="tituloInteriorRojo">&nbsp;Datos de la persona  que genera la anotaci&oacute;n</td>
         		        <td width="16" align="left" valign="bottom"><a tabindex="32" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domiciliosRegistrados&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en los domicilios registrados" class="thickbox"> <img alt="Ayuda en los domicilios registrados" src="./img/documentinfo.png" border="0" /></a></td>
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
         		          <option value="m    ">Moral</option>
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
         		                                    <td align="left"><img src="./Secretar&iacute;a de Econom&iacute;a - Portal de Empresas_files/helper_arrow.png" alt="" /></td>
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
         		              <td align="center">&nbsp;</td>
       		              </tr>
       		              </tbody>
       		          </table></td>
       		        </tr>
       		      </tbody>
       		    </table></TD>
       		  </TR>
         		<TR>
         		  <TD><p><span class="textoGeneralRojo">Descipci&oacute;n del Aviso:</span></p></TD>
       		  </TR>
         		<TR>
         		  <TD><span class="texto_general">
         		    <textarea name="ta2" cols="80" rows="10" class="ComunicaCampo" id="ta2">Los certificados burs&aacute;tiles emitidos al amparo del Programa se encuentran garantizados por una prenda sin transmisi&oacute;n de posesi&oacute;n sobre (i) cuentas por cobrar, facturas y derechos de cobro presentes y futuros, derivados de contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria celebrados por la Emisora como acreedor, que se encuentran y se encontrar&aacute;n registrados en la cuenta de activo de su balance (las &ldquo;Cuentas por Cobrar&rdquo;), (ii) los bienes que se deriven de, o sean considerados como rendimientos, r&eacute;ditos, frutos o productos futuros, pendientes o ya obtenidos de las Cuentas por Cobrar a que se refiere el inciso (i) anterior, (iii) los bienes o derechos que la
Emisora tenga derecho a recibir en pago, incluyendo sin limitaci&oacute;n,
todos los bienes, derechos de cobro, dinero e instrumentos que la
Emisora tenga derecho a recibir de terceros como pago de los
contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria, incluyendo en su
caso cualquier indemnizaci&oacute;n por da&ntilde;os, p&eacute;rdida, destrucci&oacute;n o robo
de bienes pignorados, y (iv) los derechos sobre (a) las cuentas
bancarias presentes o aperturadas, en las cuales se depositen los
pagos de las Cuentas por Cobrar (en conjunto, las &ldquo;Cuentas de
Pagos&rdquo;) y (b) los intereses generados de la inversi&oacute;n de los recursos
de las Cuentas de Pagos. A los bienes mencionados en los incisos (i)
a (iv) anteriores se les denominar&aacute; conjuntamente y en lo sucesivo
como los &ldquo;Bienes Pignorados&rdquo;. La Emisora se obliga a que los Bienes
Pignorados en todo momento representen por lo menos un valor
equivalente a 1.25 veces el monto del saldo insoluto de las
obligaciones de pago de principal, intereses y dem&aacute;s accesorios a
cargo de la Emisora derivadas del total de los certificados burs&aacute;tiles
emitidos al amparo del Programa que se encuentren en circulaci&oacute;n,
as&iacute; como de los intereses ordinarios y moratorios, accesorios y dem&aacute;s
cantidades pagaderas por la Emisora bajo los mismos (en lo sucesivo,
en forma conjunta, las &ldquo;Obligaciones Garantizadas&rdquo;).
El Contrato de Prenda (el &ldquo;Contrato de Prenda&rdquo;) entre la Emisora y
Monex Casa de Bolsa, S.A. de C.V., Monex Grupo Financiero, en su
car&aacute;cter de Representante Com&uacute;n (el &ldquo;Acreedor Prendario&rdquo;) en cuyos
t&eacute;rminos se constituye la prenda sin transmisi&oacute;n de posesi&oacute;n
constituye una obligaci&oacute;n v&aacute;lida de la Emisora exigible en su contra
de conformidad con sus t&eacute;rminos; y una vez que el Contrato quede
inscrito en el Registro P&uacute;blico de la Propiedad y del Comercio de la
Ciudad de M&eacute;xico, Distrito Federal, constituir&aacute; una obligaci&oacute;n v&aacute;lida
frente a terceros.
Adicionalmente, LOS CERTIFICADOS est&aacute;n avalados por los
accionistas de la Emisora.</textarea>
         		  </span></TD>
       		  </TR>
         		<TR>
         		  <TD>&nbsp;</TD>
       		  </TR>
    		    </TBODY>
	    	 </TABLE>
		</TD>
	</TR>
</TBODY></TABLE>

<TABLE cellspacing="0" cellpadding="0" width="100%">
	<TBODY><TR align="center">
		<TD class="btnSubmit">		
			<A href="http://www.tuempresa.gob.mx/portal/empresa/domicilios/show.do"><IMG src="<%= request.getContextPath() %>/imgs/botonCancelar.png" name="Cancelar" border="0"></A>
			
			<A href="javascript:guardar('idDomicilio');">
				<IMG src="<%= request.getContextPath() %>/imgs/botonGuardar.png">
			</A>
			
			
		</TD>
	</TR>
</TBODY></TABLE>
</FORM>





<SCRIPT>
	setNavigation('idFrmDomicilioDefault', '/portal/empresa/actividadEconomica/show.do', 'save.do');
</SCRIPT>
			</TD>
		</TR>
		<TR>
			<TD>
				<DIV id="topNavContainer">

<DIV id="div_navegar" style="display: block; width: 610px;">
<TABLE width="100%" cellpadding="2" cellspacing="2" class="CajaMenu">
	<TBODY><TR height="25">
		<TD class="subtituloInteriorGris" width="100%">
		
			Para guardar su información presione Guardar o Siguiente
		
		
		</TD>
	</TR>
</TBODY></TABLE>
<DIV id="divbotonSiguiente" style="position: relative; left: 420px; top: -15px; width: 150px;">
<TABLE width="150" cellpadding="2" cellspacing="2">
	<TBODY><TR>
		<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/empresa/domicilios/edit.do#" tabindex="46" onclick="submitAction(false);"> <IMG src="./img/botonAnterior.png" border="0" alt="Anterior"> </A></TD>
		<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/empresa/domicilios/edit.do#" tabindex="45" onclick="submitAction(true);"> <IMG src="./img/botonSiguiente.png" border="0" alt="Siguiente"> </A></TD>
	</TR>
</TBODY></TABLE>
</DIV>
</DIV>
</DIV>
			</TD>
		</TR>
		</TBODY></TABLE>
	</TD>
</TR>
</TBODY></TABLE>