<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="idPago">
<TABLE width="600" align="center" bgcolor="#FFFFFF" >
    <TBODY><TR>
    	<TD bgcolor="#FFFFFF">
    		<TABLE border="0" bgcolor="#FFFFFF">
    			<TBODY><TR>
    				<TD class="tituloInteriorRojo"><table width="253">
    				  <tbody>
    				    <tr>
    				      <td width="189" height="32" class="titulo_exterior_rojo"> Pago de derechos&nbsp; </td>

    				      <td width="52" align="left"><a tabindex="31" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domicilio&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en el domicilio" class="thickbox"> </a></td>
  				      </tr>
  				    </tbody>
  				  </table></TD>
    			</TR>
			    <TR height="72" valign="top">
			    	<TD align="left" valign="top">
			    		<TABLE>

			    			<TBODY><TR>
			    				<TD width="25%">
			    					<IMG src="./img/e5cinco.gif">
			    				</TD>
			    				<TD width="65%">
			    					<SPAN id="mensaje" style="display:block">
				            			<TABLE width="100%" align="center" class="CajaDenominacionAzul">
				            				<TBODY><TR>
				            					<TD width="8%" align="left" valign="top">

				            					<IMG src="./resources/img/ico_nota.png" border="0"><BR><B>NOTA</B></TD>
				            					<TD width="90%">				            						 	Para continuar con el proceso, imprima la Hoja de Ayuda, realice el pago de derechos en las  
				            							 <A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
				            						 	instituciones de cr&eacute;dito autorizadas
				            						 	</A>
				            						 	, a trav&eacute;s de Internet o en las ventanillas bancarias, y concluya introduciendo los datos de su recibo bancario en esta secci&oacute;n.
				            						 	<BR>					         		   																																											
				            					</TD>
				            				</TR>
				            				<TR>

				            					<TD align="left" class="textoGrisComunicacion" colspan="2"><B>* Indica datos obligatorios</B></TD>
				            				</TR>
				            			</TBODY></TABLE>
				            		</SPAN>
			    				</TD>
			    				<TD width="10%">
			    				</TD>
			    			</TR>

			    		</TBODY></TABLE>
			    	</TD>
			    </TR>
			    <TR>
			    	<TD align="left" bgcolor="#FFFFFF" height="20" class="textoGeneralRojo">
			    		Pago por constituci&oacute;n de sociedades mercantiles 
			    		 
			    	</TD>
			    </TR>    			    			
			    <TR bgcolor="#FFFFFF">
			        <TD bgcolor="#FFFFFF">

						<DIV id="PagoUno">
			            <table align="center">
	
	<tr>
		<td>
			<table id="mytable" class="mytable" cellpadding="3" cellspacing="2" border="0" width="95%" >
		          <thead>
		           <tr>
				<TD colspan="12" class="titulo_exterior_blanco" bgcolor="#DEDEDE" height="25" nowrap="nowrap">Detalle tramites por pagar</TD>
		           </tr>
		            <tr>
		              <td width="12%" class="encabezadoTablaResumen" style="text-align: center">Seleccione para pago </td>
		              <td width="12%" class="encabezadoTablaResumen" style="text-align: center"> Tipo Transacci&oacute;n</td>
		              <td width="12%" class="encabezadoTablaResumen" style="text-align: center"> Fecha Celebraci&oacute;n</td>
		              <td width="12%" class="encabezadoTablaResumen" style="text-align: center"> Fecha T&eacute;rmino</td>
		              <td width="8%" class="encabezadoTablaResumen" style="text-align: center"> N&uacute;mero de Garant&iacute;a Mobiliaria</td>
		              <td width="19%" class="encabezadoTablaResumen" style="text-align: center">Nombre del Otorgante de la Garant&iacute;a Mobiliaria</td>
		              <td width="8%" class="encabezadoTablaResumen" style="text-align: center"> Folio Electr&oacute;nico del Otorgante</td>
		              <td width="8%" class="encabezadoTablaResumen" style="text-align: center"> Estatus</td>
		              <td width="28%" class="encabezadoTablaResumen" style="text-align: center"> Descripci&uacute;n General</td>		              
		              <td width="18%" class="encabezadoTablaResumen" style="text-align: center"> Precio </td>
		              		 <td colspan="2" width="18%" class="encabezadoTablaResumen" style="text-align: center"> Opciones</td>
		            </tr>
		           </thead>
					
					<tbody>
		          
		
					
	 			<s:iterator value="pagoTOs">
		       <tr>
		              <td align="center"><input id="p2" onclick="getTotal();" value="100" type="checkbox" checked="checked">  </td>
		              		<td> <s:property value="tipoTramite" /></td> 
		              		<td> <s:property value="fechaCelebracion" /></td> 
		              		<td> <s:property value="fechaTermino" /></td> 
		              		<td> <s:property value="idGarantia" /></td> 
		              		<td> <s:property value="nombre" /></td> 
		              		<td> <s:property value="folioMercantil" /></td> 
		              		<td> <s:property value="status" /></td> 
		              		<td> <s:property value="descGeneral" /></td> 
		              		<td> <s:property value="precio" /></td> 
		              <td > <input type="button" value="Eliminar" onclick="deleteRow(this);"/></td>
		       </tr>
		       </s:iterator>
		       <tr>
		       <td colspan="8"></td>
		       <td  align="right" class="texto_renglon2"> Total:</td>
		       <td id="totalTd" class="texto_renglon2">100</td>
		       <td ></td>
		       
		       </tr>
		       
		        </tbody>
		   </table>
		  </td>
</table>

						</DIV>

			        </TD>
			    </TR>
				<TR><TD class="texto_general" align="center">
					<TABLE cellpadding="2" cellspacing="2" border="0" width="90%">
						<TBODY><TR>
							<TD class="texto_general" align="center"> 
								<P class="texto_naranja">
									Para continuar con el tr&aacute;mite en l&iacute;nea o en ventanilla bancaria
								 	<BR>
								 	debe hacer el pago utilizando la Hoja de Ayuda que le proporciona el Portal.<BR>

								    <A tabindex="14" class="botona" href="http://www.tuempresa.gob.mx/portal/pago/ecinco/hojaAyuda/get.do" title="Click para generar hoja de ayuda" target="_blank">
								Generaci&oacute;n de Hoja de Ayuda 
								<IMG src="./img/pdf.png" border="0" title="Clic para generar hoja de ayuda">
								    </A>
							    </P></TD>
						</TR>
					</TBODY></TABLE>
				</TD></TR>
				<TR>

					<TD>
						<TABLE border="0" cellpadding="2" cellspacing="2" width="680" align="center" bgcolor="#FFFFFF">
							<TBODY><TR height="18">
								<TD width="50%" class="encabezado_tabla" align="center"><B>Pague por internet</B></TD>
								<TD width="50%" class="encabezado_tabla" align="center"><B>Pague en ventanilla</B></TD>
							</TR>
							<TR>
								<TD width="50%" valign="top">

									<TABLE border="0" cellpadding="3" cellspacing="1" width="98%" align="right">
										<TBODY><TR><TD class="texto_General" colspan="2">
										Para hacer el pago por internet es indispensable que cuente con el servicio de banca electr&oacute;nica en alguna de las 
										<A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
										instituciones de cr&eacute;dito autorizadas
										</A>
										. El procedimiento es el siguiente:
										<BR>
										</TD>
										</TR>

										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 1.-</TD>
											<TD class="texto_General">
											Obtenga e imprima la
											    <A href="http://www.tuempresa.gob.mx/portal/pago/ecinco/hojaAyuda/get.do" class="ver_todas" target="_blank">Hoja de Ayuda.</A></TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>

										<TR>
											<TD class="texto_General" valign="top"> 2.-</TD>
											<TD class="texto_General"> 
											Ingrese al portal de Internet de alguna de las 
											    <A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="37">
											instituciones de cr&eacute;dito autorizadas
											    </A> 
											en la que cuente con el servicio de banca electr&oacute;nica y seleccione la opci&oacute;n
											<B>Pago de DPAs</B>.</TD>

										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 3.-</TD>
											<TD class="texto_General">
												Capture la clave de referencia, la cadena de la dependencia y el importe a pagar, as&iacute; como los dem&aacute;s datos solicitados y efect&uacute;e el cargo a la cuenta. 
											</TD>
										</TR>

										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 4.-</TD>
											<TD class="texto_General"> 
												Imprima el recibo bancario con sello digital que la instituci&oacute;n de cr&eacute;dito le emita, el cual le sirve como comprobante del pago realizado. 
											</TD>
										</TR>
									</TBODY></TABLE>								
								</TD>

								<TD width="50%" valign="top">
									<TABLE border="0" cellpadding="3" cellspacing="2" width="98%" align="right">
										<TBODY><TR><TD class="texto_General" colspan="2">
										Para realizar el pago en una ventanilla bancaria de alguna de las 
										        <A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
										 	instituciones de cr&eacute;dito autorizadas
										        </A> 
										 es necesario presentar una Hoja de Ayuda. El procedimiento es el siguiente:
										 <BR>
										</TD>
										</TR>

										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 1.-</TD>
											<TD class="texto_General"> 
											Obtenga e imprima la 
											    <A href="http://www.tuempresa.gob.mx/portal/pago/ecinco/hojaAyuda/get.do" class="ver_todas" target="_blank">Hoja de Ayuda.</A></TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>

										<TR>
											<TD class="texto_General" valign="top"> 2.-</TD>
											<TD class="texto_General"> 
												Acuda con su Hoja de Ayuda a la instituci&oacute;n de cr&eacute;dito autorizada de su preferencia a pagar en efectivo o con cheque personal de la misma instituci&oacute;n.
											</TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 3.-</TD>

											<TD class="texto_General"> 
												El cajero realizar&aacute; la operaci&oacute;n del pago, le regresar&aacute; la Hoja de Ayuda y entregar&aacute; un recibo bancario con sello digital, el cual le sirve como comprobante del pago realizado.
											</TD>
										</TR>
									</TBODY></TABLE>
								</TD>							
							</TR>
						</TBODY></TABLE>
					</TD>
				</TR>

    		</TBODY></TABLE>
    	</TD>
    </TR>
    <TR><TD>&nbsp;</TD></TR>
    <TR align="center">
    	<TD align="left" class="titulo_interior_naranja">
		   <TABLE cellpadding="0" cellspacing="1">
	        	<TBODY><TR>
	        		<TD colspan="1" class="textoGeneralRojo">&nbsp;Validaci&oacute;n del pago &nbsp;

	                </TD>
	            </TR>
	            <TR align="center">
	            <TD>&nbsp;</TD>
	            </TR>
	             
	        </TBODY></TABLE>      
		</TD>
	</TR>
	
	<TR>

		<TD>
			<SPAN id="area_comunicacion2" style="display:block;"></SPAN>
		</TD>
	</TR>	
	<TR><TD align="center" class="textoGeneralRojo">
			Capture los datos de validaci&oacute;n tal cual aparecen en su recibo de pago.
		</TD></TR>
    <TR><TD>&nbsp;</TD></TR>
    <TR><TD>

		<INPUT type="hidden" name="pago.id" value="92" id="idFrmEcinco_pago_id">
		<INPUT type="hidden" name="pago.validaClaveReferencia" value="024000169" id="idFrmEcinco_pago_validaClaveReferencia">
		<INPUT type="hidden" name="pago.validaClaveDependencia" value="00027010012312" id="idFrmEcinco_pago_validaClaveDependencia">
		
		<TABLE width="90%" align="center" cellpadding="3" cellspacing="2">
			<TBODY>
			  <TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					Nombre o Raz&oacute;n Social:&nbsp;

				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD colspan="2" class="texto_general" align="left" width="70%">
				
					
				
					<INPUT type="text" name="pago.pagoSat.nombre" size="10" maxlength="50" value="Nombre(s)" tabindex="39" id="idNombre" class="texto_general" onfocus="resetIfClean(this,&#39;Nombre(s)&#39;); tempStyle(1); style.backgroundColor=&#39;FFFFDC&#39;;" onblur="resetField(this,&#39;Nombre(s)&#39;); qTempStyle(1); style.backgroundColor=&#39;FFFFFF&#39;;" onkeyup="this.value = this.value.toUpperCase();">
					
					<INPUT type="text" name="pago.pagoSat.apellidoPaterno" size="15" maxlength="50" value="Apellido paterno" tabindex="40" id="idPaterno" class="texto_general" onfocus="resetIfClean(this,&#39;Apellido paterno&#39;); tempStyle(1); style.backgroundColor=&#39;FFFFDC&#39;;" onblur="resetField(this,&#39;Apellido paterno&#39;); qTempStyle(1); style.backgroundColor=&#39;FFFFFF&#39;;" onkeyup="this.value = this.value.toUpperCase();">	
						
					<INPUT type="text" name="pago.pagoSat.apellidoMaterno" size="15" maxlength="50" value="Apellido materno" tabindex="41" id="idMaterno" class="texto_general" onfocus="resetIfClean(this,&#39;Apellido materno&#39;); tempStyle(1); style.backgroundColor=&#39;FFFFDC&#39;;" onblur="resetField(this,&#39;Apellido materno&#39;); style.backgroundColor=&#39;FFFFFF&#39;;" onkeyup="this.value = this.value.toUpperCase();">	

				</TD>
			
			</TR>
			<TR valign="top">

				<TD class="texto_General" align="right" width="29%">
			</TR>
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
				
					<INPUT type="text" name="pago.pagoSat.razonSocial" size="20" maxlength="150" value="Raz&oacute;n Social" tabindex="42" id="idRazonSocial" class="texto_general" onfocus="resetIfClean(this,&#39;Raz&oacute;n Social&#39;); tempStyle(2); style.backgroundColor=&#39;FFFFDC&#39;;" onblur="resetField(this,&#39;Raz&oacute;n Social&#39;); qTempStyle(2);  style.backgroundColor=&#39;FFFFFF&#39;;" onkeyup="this.value = this.value.toUpperCase();">
		            		
				</TD>
			</TR>
					
			<TR class="texto_General"><TD colspan="4"></TR>
			<TR valign="bottom">
		    	<TD class="texto_General" align="right" width="19%">
		    		RFC:
		    	</TD>
		    	<TD class="texto_azul" width="1%">	            
				
					<INPUT type="text" name="pago.rfc" size="30" maxlength="13" value="" tabindex="43" id="idFrmEcinco_pago_rfc" class="texto_general" onfocus="estilo_foco(this, &#39;ayudarfc&#39;, &#39;errorrfc&#39;, &#39;errorrfc&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;ayudarfc&#39;, &#39;errorrfc&#39;,&#39;&#39;, &#39;HelpComodin&#39;);" onkeyup="this.value = this.value.toUpperCase();">
				
		        </TD>                        
		        <TD width="40%" align="left" valign="top">
		        	<SPAN id="ayudarfc" style="position:absolute;color:5c5c5c;display:none;width:150;">
		        	<TABLE width="150"><TBODY><TR valign="top"><TD align="left">
		        	<IMG src="./img/helper_arrow.png"></TD>
		        	<TD class="ComunicaAsesoria">
		        		Este dato se debe capturar tal cual aparece en el recibo bancario. 
		        		<B>Art. 27 CFF y 25 LSPEE.</B>

		        	</TD></TR></TBODY></TABLE></SPAN>
				    <SPAN id="errorrfc" style="position:absolute;color:ff3300;display:none;width:150;">
				    <TABLE width="150"><TBODY><TR valign="top"><TD align="left">
				    <IMG src="./img/helper_arrow_error.png"></TD>
				    <TD class="ComunicaError">
				    	El RFC es incorrecto
				    </TD></TR></TBODY></TABLE></SPAN>	            	            
		        </TD>
	    	</TR>
			<TR class="texto_General">

				<TD colspan="4">
			</TR>

			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					Medio de recepci&oacute;n:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>

				<TD class="texto_general" align="left" width="35%" valign="top">
				
					
					
					<SELECT name="pago.medioRecepcion.id" tabindex="44" id="idMedioPago" class="campo_general" onfocus="estilo_foco(this, &#39;imedioPago&#39;, &#39;errormedioP&#39;, &#39;errorbanco&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;imedioPago&#39;, &#39;errormedioP&#39;, &#39;&#39;, &#39;HelpComodin&#39;);">
    <OPTION value="3">Internet</OPTION>
    <OPTION value="1">Ventanilla</OPTION>


</SELECT>
			
				</TD>
				<TD width="35%">

					<SPAN id="imedioPago" style="position:absolute;color:5c5c5c;display:none;width:250;">
						<TABLE width="250"><TBODY><TR valign="top"><TD align="left">
							<IMG src="./img/helper_arrow.png"></TD>
							<TD class="ComunicaAsesoria">
								Debe seleccionar el medio por el cual present&oacute; el pago.
							</TD></TR>
						</TBODY></TABLE>
					</SPAN>
					<SPAN id="errormedioP" style="position:absolute;color:ff1300;display:none;width:250;">

					<TABLE width="250"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow_error.png">
					</TD><TD class="ComunicaError">Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>				
			</TR>	
			<TR class="texto_General">
				<TD colspan="4">
			</TR>		
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">

					Banco:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%" valign="top">	
				
					
				
					<SELECT name="pago.banco.id" tabindex="45" id="idBanco" class="campo_general" onfocus="estilo_foco(this, &#39;ibanco&#39;, &#39;errorbanco&#39;, &#39;errorisucursal&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;ibanco&#39;, &#39;errorbanco&#39;, &#39;&#39;, &#39;HelpComodin&#39;);">
    <OPTION value="">-- Seleccione un Banco --</OPTION>
    <OPTION value="14">AFIRME</OPTION>

    <OPTION value="10">MIFEL</OPTION>
    <OPTION value="6">BAJÍO</OPTION>
    <OPTION value="8">INBURSA</OPTION>
    <OPTION value="9">INTERACCIONES</OPTION>
    <OPTION value="15">BANORTE</OPTION>
    <OPTION value="17">MULTIVA</OPTION>

    <OPTION value="2">BANAMEX</OPTION>
    <OPTION value="1">BANJERCITO</OPTION>
    <OPTION value="12">BANREGIO</OPTION>
    <OPTION value="4">SANTANDER</OPTION>
    <OPTION value="18">TOKYO</OPTION>
    <OPTION value="13">BANSI</OPTION>

    <OPTION value="3">BBVA BANCOMER</OPTION>
    <OPTION value="5">HSBC</OPTION>
    <OPTION value="7">IXE</OPTION>
    <OPTION value="11">SCOTIABANK INVERLAT</OPTION>
    <OPTION value="20">TESOFE</OPTION>
    <OPTION value="16">RBS</OPTION>


</SELECT>
				</TD>
				<TD width="35%">
					<SPAN id="ibanco" style="position:absolute;color:5c5c5c;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow.png"></TD>
					<TD class="ComunicaAsesoria">
						Debe seleccionar la instituci&oacute;n de cr&eacute;dito autorizada donde realiz&oacute; su pago para validar si es correcto.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B>

					</TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorbanco" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow_error.png"></TD>
					<TD class="ComunicaError">Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>
			</TR>		
			<TR class="texto_General">
				<TD colspan="4">

			</TR>		
			<TR valign="top">
				<TD class="texto_General" align="right" width="30%">
					Clave de referencia: 
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">

					
					
					<INPUT type="text" name="pago.claveReferencia" size="20" maxlength="9" value="" tabindex="46" id="idFrmEcinco_pago_claveReferencia" class="texto_general" onfocus="style.backgroundColor=&#39;FFFFDC&#39;;" onblur="style.backgroundColor=&#39;FFFFFF&#39;;" onkeypress="return aceptanum(event);">
					
				</TD>				
				<TD width="35%">

					
				</TD>
			</TR>
			<TR class="texto_General">
				<TD colspan="4">
			</TR>		
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					Cadena de la dependencia: 
				</TD>
				<TD class="texto_azul" width="1%">*</TD>

				<TD class="texto_general" align="left" width="35%">
				
					
				
					<INPUT type="text" name="pago.claveDependencia" size="20" maxlength="14" value="" tabindex="47" id="idFrmEcinco_pago_claveDependencia" class="texto_general" onfocus=" style.backgroundColor=&#39;FFFFDC&#39;;" onblur=" style.backgroundColor=&#39;FFFFFF&#39;;" onkeypress="return aceptanum(event);">
					
				</TD>				
				<TD width="35%">
			</TR>
			<TR class="texto_General">
				<TD colspan="4">
			</TR>			
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">

					Llave de pago:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
				
					
				
					<INPUT type="text" name="pago.pagoSat.llavePago" size="20" maxlength="10" value="" tabindex="50" id="idFrmEcinco_pago_pagoSat_llavePago" class="texto_general" onfocus="estilo_foco(this, &#39;llav&#39;, &#39;errorllav&#39;, &#39;erroroper&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;llav&#39;, &#39;errorllav&#39;, &#39;&#39;, &#39;HelpComodin&#39;);" onkeyup="this.value = this.value.toUpperCase();">
						
				</TD>
				<TD width="35%">
					<SPAN id="llav" style="position:absolute;color:5c5c5c;display:none;width:230;">

					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow.png"></TD><TD class="ComunicaAsesoria">
						Este dato se debe capturar tal cual aparece en el recibo bancario; es indispensable para validar su pago.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B></TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorllav" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow_error.png"></TD><TD class="ComunicaError">
					Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>

				</TD>				
			</TR>
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					N&uacute;mero de operaci&oacute;n:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
					<INPUT type="text" name="pago.pagoSat.numOperacion" size="20" maxlength="12" value="" tabindex="51" id="idFrmEcinco_pago_pagoSat_numOperacion" class="texto_general" onfocus="estilo_foco(this, &#39;oper&#39;, &#39;erroroper&#39;, &#39;errorfechaoper&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;oper&#39;, &#39;erroroper&#39;, &#39;&#39;, &#39;HelpComodin&#39;);" onkeypress="return aceptanum(event);">	
				</TD>				
				<TD width="35%">
					<SPAN id="oper" style="position:absolute;color:5c5c5c;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow.png"></TD>
					<TD class="ComunicaAsesoria">
						Este dato se debe capturar tal cual aparece en el recibo bancario; es indispensable para validar su pago.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B></TD></TR></TBODY></TABLE></SPAN>

					<SPAN id="erroroper" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow_error.png"></TD>
					<TD class="ComunicaError">Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				<TD class="texto_General" align="right" width="200%" valign="top" >
					Fecha de presentaci&oacute;n del pago:
				</TD>
				<TD class="texto_azul" width="1%" valign="top">*</TD>
				<TD class="texto_general" align="left" width="35%">
					
				</TD>		
			</TR>
			<TR>
				<TD class="texto_General" align="right" width="29%">
					Importe del pago:&nbsp;

				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
				

					<INPUT type="text" name="pago.pagoSat.totalPago" size="10" maxlength="10" value="100" tabindex="56" id="idFrmEcinco_pago_pagoSat_totalPago" class="texto_general" onfocus="estilo_foco(this, &#39;impor&#39;, &#39;errorimpor&#39;, &#39;errorimpor&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;impor&#39;, &#39;errorimpor&#39;, &#39;&#39;, &#39;HelpComodin&#39;);" onkeypress="return aceptanum(event);">
					
						
				</TD>
				
				<TD width="35%">
					<SPAN id="impor" style="position:absolute;color:5c5c5c;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">

					<IMG src="./img/helper_arrow.png"></TD><TD class="ComunicaAsesoria">
						Indique el monto de la operaci&oacute;n sin comas ni centavos; es un dato indispensable para validar su pago.
						<B>Indique el monto de la operación sin comas ni centavos; es un dato indispensable para validar su pago.</B></TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorimpor" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="./img/helper_arrow_error.png"></TD><TD class="ComunicaError">
					Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>

			</TR>
           	<TR>
           		<TD>
           			
				</TD>
           	</TR>
			<TR>
				<TD class="btnSubmit" align="center" colspan="4">				
					<s:form namespace="pago" action="validarPago.do" theme="simple">
					<input type="hidden" name="idTramite" value="<s:text name="idTramite" />"/>
					<s:submit value="Validar" ></s:submit>
					</s:form>
					
					
	       		
				</TD>
			</TR>

			<TR>
				<TD colspan="4">
					<SPAN id="HelpComodin"></SPAN>
				</TD>
			</TR>			
		</TBODY></TABLE>
		</FORM>




	</TD></TR>

    <TR><TD>&nbsp;</TD></TR>	
</TBODY></TABLE>
</div>

