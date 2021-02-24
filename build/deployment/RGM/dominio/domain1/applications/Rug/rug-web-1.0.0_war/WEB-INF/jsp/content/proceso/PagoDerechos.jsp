<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<TABLE class="workArea" cellspacing="0" cellpadding="0">
<TBODY><TR>
	<TD class="sideMenu">
		<DIV id="sideMenuContainer"></DIV>
	</TD>
	<TD>
		<TABLE cellspacing="0" cellpadding="0">
		<TBODY><TR>
			<TD>
				<DIV id="topNavContainer">

<DIV id="contenedorBontonGuardar" style="width: 610px; display: block;">
	<table width="610" cellpadding="2" cellspacing="2" class="CajaDenominacion">
	  <tbody>
	    <tr height="25">
	      <td class="subtituloInteriorGris" width="2%">&nbsp;</td>
	      <td class="titulo_exterior_rojo" align="left" width="68%"> BBVA Bancomer, S.A.</td>
	      <td width="30%">&nbsp;</td>
	      </tr>
	    </tbody>
	  </table>
	<DIV id="divbotonGuardar" style="position: relative; left: 420px; top: -15px; display: block;">
	  <TABLE width="150" cellpadding="2" cellspacing="2">
		<TBODY><TR>
			<TD width="50%"><A href="Validaci&oacute;n.htm" onclick="submitAction(false);"> <IMG src="<%= request.getContextPath() %>/imgs/botonAnterior.png" border="0" alt="Anterior"> </A></TD>
			<TD width="50%"><A href="Expediente.htm" onclick="submitAction(true);"> <IMG src="<%= request.getContextPath() %>/imgs/botonSiguiente.png" border="0" alt="Siguiente"> </A></TD>
		</TR>
	</TBODY></TABLE>
	</DIV>
</DIV></DIV>
			</TD>
		</TR>
		<TR>
			<TD>
				


<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/campos.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/ecinco.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/calendario.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%= request.getContextPath() %>/js/popcalendar.js"></SCRIPT><DIV onclick="bShow=true" id="calendar" style="z-index: 999; position: absolute; visibility: hidden; "><TABLE width="250" style="font-family:arial;font-size:11px;border-width:1;border-style:solid;border-color:#a0a0a0;font-family:arial; font-size:11px}" bgcolor="#ffffff"><TBODY><TR background="./img/calback.jpg"><TD background="./img/calback.jpg"><TABLE width="248"><TBODY><TR><TD style="padding:2px;font-family:arial; font-size:11px;"><FONT color="BLACK"><B><SPAN id="caption"><SPAN id="spanLeft" style="border-style:solid;border-width:1;border-color:#ffffff;cursor:pointer" onmouseover="swapImage(&quot;changeLeft&quot;,&quot;left1.gif&quot;);this.style.borderColor=&quot;BLACK&quot;;window.status=&quot;Click to scroll to previous month. Hold mouse button to scroll automatically.&quot;" onclick="javascript:decMonth()" onmouseout="clearInterval(intervalID1);swapImage(&quot;changeLeft&quot;,&quot;left1.gif&quot;);this.style.borderColor=&quot;#ffffff&quot;;window.status=&quot;&quot;" onmousedown="clearTimeout(timeoutID1);timeoutID1=setTimeout(&quot;StartDecMonth()&quot;,500)" onmouseup="clearTimeout(timeoutID1);clearInterval(intervalID1)">&nbsp;<IMG id="changeLeft" src="./img/left1.gif" width="10" height="11" border="0">&nbsp;</SPAN>&nbsp;<SPAN id="spanRight" style="border-style:solid;border-width:1;border-color:#ffffff;cursor:pointer" onmouseover="swapImage(&quot;changeRight&quot;,&quot;right1.gif&quot;);this.style.borderColor=&quot;BLACK&quot;;window.status=&quot;Click to scroll to next month. Hold mouse button to scroll automatically.&quot;" onmouseout="clearInterval(intervalID1);swapImage(&quot;changeRight&quot;,&quot;right1.gif&quot;);this.style.borderColor=&quot;#ffffff&quot;;window.status=&quot;&quot;" onclick="incMonth()" onmousedown="clearTimeout(timeoutID1);timeoutID1=setTimeout(&quot;StartIncMonth()&quot;,500)" onmouseup="clearTimeout(timeoutID1);clearInterval(intervalID1)">&nbsp;<IMG id="changeRight" src="./img/right1.gif" width="10" height="11" border="0">&nbsp;</SPAN>&nbsp;<SPAN id="spanMonth" style="border-style:solid;border-width:1;border-color:#ffffff;cursor:pointer" onmouseover="swapImage(&quot;changeMonth&quot;,&quot;drop1.gif&quot;);this.style.borderColor=&quot;BLACK&quot;;window.status=&quot;Click to select a month.&quot;" onmouseout="swapImage(&quot;changeMonth&quot;,&quot;drop1.gif&quot;);this.style.borderColor=&quot;#ffffff&quot;;window.status=&quot;&quot;" onclick="popUpMonth()"></SPAN>&nbsp;<SPAN id="spanYear" style="border-style:solid;border-width:1;border-color:#fffffff;cursor:pointer" onmouseover="swapImage(&quot;changeYear&quot;,&quot;drop1.gif&quot;);this.style.borderColor=&quot;BLACK&quot;;window.status=&quot;Click to select a year.&quot;" onmouseout="swapImage(&quot;changeYear&quot;,&quot;drop1.gif&quot;);this.style.borderColor=&quot;#ffffff&quot;;window.status=&quot;&quot;" onclick="popUpYear()"></SPAN>&nbsp;</SPAN></B></FONT></TD><TD align="right"><A href="javascript:hideCalendar()" id="bclose" tabindex="26"><IMG src="./img/close.gif" border="0" alt="Close the Calendar"></A></TD></TR></TBODY></TABLE></TD></TR><TR><TD style="padding:5px" bgcolor="#ffffff"><SPAN id="content"></SPAN></TD></TR><TR bgcolor="#f0f0f0"><TD style="padding:5px" align="center"><SPAN id="lblToday">Hoy es <A onmousemove="window.status=&quot;Go To Current Month&quot;" onmouseout="window.status=&quot;&quot;" title="Go To Current Month" style="text-decoration:none;color:black;" href="javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();">Mie, 12 Mayo	2010</A></SPAN></TD></TR></TBODY></TABLE></DIV><DIV id="selectMonth" style="z-index:+999;position:absolute;visibility:hidden;"></DIV><DIV id="selectYear" style="z-index:+999;position:absolute;visibility:hidden;"></DIV>

<TABLE width="100%" align="center" bgcolor="#FFFFFF">
    <TBODY><TR>
    	<TD bgcolor="#FFFFFF">
    		<TABLE border="0" bgcolor="#FFFFFF">
    			<TBODY><TR>
    				<TD class="tituloInteriorRojo"><table width="253">
    				  <tbody>
    				    <tr>
    				      <td width="189" height="32" class="titulo_exterior_rojo"> 4. Pago de derechos&nbsp; </td>
    				      <td width="52" align="left"><a tabindex="31" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domicilio&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en el domicilio" class="thickbox"> <img alt="Ayuda en el domicilio" src="./img/documentinfo.png" border="0" /></a></td>
  				      </tr>
  				    </tbody>
  				  </table></TD>
    			</TR>
			    <TR height="72" valign="top">
			    	<TD align="left" valign="top">
			    		<TABLE>
			    			<TBODY><TR>
			    				<TD width="25%">
			    					<IMG src="<%= request.getContextPath() %>/imgs/e5cinco.gif">
			    				</TD>
			    				<TD width="65%">
			    					<SPAN id="mensaje" style="display:block">
				            			<TABLE width="100%" align="center" class="CajaDenominacionAzul">
				            				<TBODY><TR>
				            					<TD width="8%" align="left" valign="top">
				            					<IMG src="<%= request.getContextPath() %>/resources/imgs/ico_nota.png" border="0"><BR><B>NOTA</B></TD>
				            					<TD width="90%">				            						 	Para continuar con el proceso, imprima la Hoja de Ayuda, realice el pago de derechos en las  
				            							 <A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
				            						 	instituciones de crédito autorizadas
				            						 	</A>
				            						 	, a través de Internet o en las ventanillas bancarias, y concluya introduciendo los datos de su recibo bancario en esta sección.
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
			    		Pago por constitución de sociedades mercantiles 
			    		 
			    	</TD>
			    </TR>    			    			
			    <TR bgcolor="#FFFFFF">
			        <TD bgcolor="#FFFFFF">
						<DIV id="PagoUno">
			            <TABLE cellpadding="2" cellspacing="2" border="0" width="90%" align="center" bgcolor="#FFFFFF">
			                <TBODY>
			                <TR><TD class="texto_general" colspan="6" height="10"></TR>
			                <TR>
			                	<TD colspan="2" class="encabezado_tabla" align="center">Trámite</TD>
			                	<TD class="encabezado_tabla" align="center">Informaci&oacute;n</TD>
			                	<td width="67" align="center" class="encabezado_tabla">Costo</td>
			                	<td width="59" align="center" class="encabezado_tabla">Cantidad</td>
			                	<TD class="encabezado_tabla" align="center" width="109">Costo Total</TD>
			                </TR>
			                <TR>
			                	<TD width="12" class="texto_renglon1">-</TD>
			                	<TD width="293" class="texto_renglon1">Inscripci&oacute;n de Garant&iacute;as Mobiliarias</TD>
			                	<TD width="107" align="center" class="texto_renglon1">Completa</TD>
			                	<td align="right" class="texto_renglon1">$ 50.00</td>
			                	<td align="center" class="texto_renglon1">3</td>
			                	<TD class="texto_renglon1" align="right" width="109">$ 150.00</TD>
			                </TR>
			                <TR>
			                  <TD class="texto_renglon2">-</TD>
			                  <TD class="texto_renglon2">Modificaci&oacute;n de Garant&iacute;as Mobiliarias</TD>
			                  <TD align="center" class="texto_renglon2">Completa</TD>
			                  <td align="right" class="texto_renglon2">$ 50.00</td>
			                  <td align="center" class="texto_renglon2">3</td>
			                  <TD class="texto_renglon2" align="right">$ 150.00</TD>
			                  </TR>
			                <tr>
			                  <td class="texto_renglon1">-</td>
			                  <td class="texto_renglon1">Consulta de Garant&iacute;as Mobiliarias</td>
			                  <td align="center" class="texto_renglon1">Completa</td>
			                  <td align="right" class="texto_renglon1">$ 25.00</td>
			                  <td align="center" class="texto_renglon1">4</td>
			                  <td class="texto_renglon1" align="right">$ 200.00</td>
			                  </tr>
			                <TR>
			                  <TD class="texto_renglon2">-</TD>
			                  <TD class="texto_renglon2">Certificado de Garant&iacute;as Mobiliarias</TD>
			                  <TD align="center" class="texto_renglon2">Por completar</TD>
			                  <td align="right" class="texto_renglon2">$ 100.00</td>
			                  <td align="center" class="texto_renglon2">5</td>
			                  <TD class="texto_renglon2" align="right">$ 500.00</TD>
			                  </TR>
			                <TR>
			                  <TD class="texto_general">
			                    <TD colspan="2" align="center" class="texto_general"><span class="texto_renglon1">Total</span>
			                      <td align="right" class="texto_renglon2">&nbsp;</td>
			                  <td align="center" class="texto_renglon2">&nbsp;</td>
			                  <TD class="texto_renglon2" align="right">
			                    
			                    
			                    $ 1,000.00
			                    
			                    </TD>
			                  </TR>
			            </TBODY></TABLE>
						</DIV>
			        </TD>
			    </TR>
				<TR><TD class="texto_general" align="center">
					<TABLE cellpadding="2" cellspacing="2" border="0" width="90%">
						<TBODY><TR>
							<TD class="texto_general" align="center"> 
								<P class="texto_naranja">
									Para continuar con el trámite en línea o en ventanilla bancaria
								 	<BR>
								 	debe hacer el pago utilizando la Hoja de Ayuda que le proporciona el Portal.<BR>
								    <A tabindex="14" class="botona" href="http://www.tuempresa.gob.mx/portal/pago/ecinco/hojaAyuda/get.do" title="Click para generar hoja de ayuda" target="_blank">
								Generación de Hoja de Ayuda 
								<IMG src="<%= request.getContextPath() %>/imgs/pdf.png" border="0" title="Clic para generar hoja de ayuda">
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
										Para hacer el pago por internet es indispensable que cuente con el servicio de banca electrónica en alguna de las 
										<A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
										instituciones de crédito autorizadas
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
											instituciones de crédito autorizadas
											    </A> 
											en la que cuente con el servicio de banca electrónica y seleccione la opción
											<B>Pago de DPAs</B>.</TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 3.-</TD>
											<TD class="texto_General">
												Capture la clave de referencia, la cadena de la dependencia y el importe a pagar, así como los demás datos solicitados y efectúe el cargo a la cuenta. 
											</TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 4.-</TD>
											<TD class="texto_General"> 
												Imprima el recibo bancario con sello digital que la institución de crédito le emita, el cual le sirve como comprobante del pago realizado. 
											</TD>
										</TR>
									</TBODY></TABLE>								
								</TD>
								<TD width="50%" valign="top">
									<TABLE border="0" cellpadding="3" cellspacing="2" width="98%" align="right">
										<TBODY><TR><TD class="texto_General" colspan="2">
										Para realizar el pago en una ventanilla bancaria de alguna de las 
										        <A href="http://www.sat.gob.mx/sitio_internet/e_sat/oficina_virtual/dpa/116_4901.html" target="_blank" class="ver_todas" tabindex="35">
										 	instituciones de crédito autorizadas
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
												Acuda con su Hoja de Ayuda a la institución de crédito autorizada de su preferencia a pagar en efectivo o con cheque personal de la misma institución.
											</TD>
										</TR>
										<TR><TD class="footer" colspan="2">&nbsp;</TD></TR>
										<TR>
											<TD class="texto_General" valign="top"> 3.-</TD>
											<TD class="texto_General"> 
												El cajero realizará la operación del pago, le regresará la Hoja de Ayuda y entregará un recibo bancario con sello digital, el cual le sirve como comprobante del pago realizado.
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
	        		<TD colspan="1" class="textoGeneralRojo">&nbsp;Validación del pago &nbsp;
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
			Capture los datos de validación tal cual aparecen en su recibo de pago.
		</TD></TR>
    <TR><TD>&nbsp;</TD></TR>
    <TR><TD>
		<FORM id="idFrmEcinco" name="frmEcinco" action="http://www.tuempresa.gob.mx/portal/pago/ecinco/validaEcinco.do" method="post">
		<INPUT type="hidden" name="pago.id" value="92" id="idFrmEcinco_pago_id">
		<INPUT type="hidden" name="pago.validaClaveReferencia" value="024000169" id="idFrmEcinco_pago_validaClaveReferencia">
		<INPUT type="hidden" name="pago.validaClaveDependencia" value="00027010012312" id="idFrmEcinco_pago_validaClaveDependencia">
		
		<TABLE width="90%" align="center" cellpadding="3" cellspacing="2">
			<TBODY>
			  <TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					Nombre o Razón Social:&nbsp;
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
				<TD class="texto_azul" width="1%">
				<TD colspan="2" class="texto_general" align="left" width="70%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ó;			</TD>
			</TR>
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
				<TD class="texto_azul" width="1%">
				<TD colspan="2" class="texto_general" align="left" width="70%">
				
					<INPUT type="text" name="pago.pagoSat.razonSocial" size="20" maxlength="150" value="Raz&oacute;n Social" tabindex="42" id="idRazonSocial" class="texto_general" onfocus="resetIfClean(this,&#39;Raz&oacute;n Social&#39;); tempStyle(2); style.backgroundColor=&#39;FFFFDC&#39;;" onblur="resetField(this,&#39;Raz&oacute;n Social&#39;); qTempStyle(2);  style.backgroundColor=&#39;FFFFFF&#39;;" onkeyup="this.value = this.value.toUpperCase();">
		            		
				</TD>
			</TR>
					
			<TR class="texto_General"><TD colspan="4"></TR>
			<TR valign="bottom">
		    	<TD class="texto_General" align="right" width="19%">
		    		RFC:
		    	</TD>
		    	<TD class="texto_azul" width="1%">	            
				<TD width="40%" align="left">
					
					
				
					<INPUT type="text" name="pago.rfc" size="30" maxlength="13" value="" tabindex="43" id="idFrmEcinco_pago_rfc" class="texto_general" onfocus="estilo_foco(this, &#39;ayudarfc&#39;, &#39;errorrfc&#39;, &#39;errorrfc&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;ayudarfc&#39;, &#39;errorrfc&#39;,&#39;&#39;, &#39;HelpComodin&#39;);" onkeyup="this.value = this.value.toUpperCase();">
				
		        </TD>                        
		        <TD width="40%" align="left" valign="top">
		        	<SPAN id="ayudarfc" style="position:absolute;color:5c5c5c;display:none;width:150;">
		        	<TABLE width="150"><TBODY><TR valign="top"><TD align="left">
		        	<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD>
		        	<TD class="ComunicaAsesoria">
		        		Este dato se debe capturar tal cual aparece en el recibo bancario. 
		        		<B>Art. 27 CFF y 25 LSPEE.</B>
		        	</TD></TR></TBODY></TABLE></SPAN>
				    <SPAN id="errorrfc" style="position:absolute;color:ff3300;display:none;width:150;">
				    <TABLE width="150"><TBODY><TR valign="top"><TD align="left">
				    <IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png"></TD>
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
					Medio de recepción:&nbsp;
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
							<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD>
							<TD class="ComunicaAsesoria">
								Debe seleccionar el medio por el cual presentó el pago.
							</TD></TR>
						</TBODY></TABLE>
					</SPAN>
					<SPAN id="errormedioP" style="position:absolute;color:ff1300;display:none;width:250;">
					<TABLE width="250"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png">
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
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD>
					<TD class="ComunicaAsesoria">
						Debe seleccionar la institución de crédito autorizada donde realizó su pago para validar si es correcto.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B>
					</TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorbanco" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png"></TD>
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
				<TD colsoan="4">
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
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD><TD class="ComunicaAsesoria">
						Este dato se debe capturar tal cual aparece en el recibo bancario; es indispensable para validar su pago.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B></TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorllav" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png"></TD><TD class="ComunicaError">
					Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>				
			</TR>
			<TR valign="top">
				<TD class="texto_General" align="right" width="29%">
					Número de operación:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
				
					
				
					<INPUT type="text" name="pago.pagoSat.numOperacion" size="20" maxlength="12" value="" tabindex="51" id="idFrmEcinco_pago_pagoSat_numOperacion" class="texto_general" onfocus="estilo_foco(this, &#39;oper&#39;, &#39;erroroper&#39;, &#39;errorfechaoper&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;oper&#39;, &#39;erroroper&#39;, &#39;&#39;, &#39;HelpComodin&#39;);" onkeypress="return aceptanum(event);">
						
				</TD>				
				<TD width="35%">
					<SPAN id="oper" style="position:absolute;color:5c5c5c;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD>
					<TD class="ComunicaAsesoria">
						Este dato se debe capturar tal cual aparece en el recibo bancario; es indispensable para validar su pago.
						<B>Arts. 1, 2, 3, 4 y 25.I-II LFD, Art. 16-A LIE y Art. 52 LIC.</B></TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="erroroper" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png"></TD>
					<TD class="ComunicaError">Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>
			</TR>		
			<TR>
				<TD class="texto_General" align="right" width="29%">
					Fecha de presentación del pago:
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
				
					
					
					
				
					<SELECT name="pago.fecha.anio" tabindex="52" id="anio" class="texto_general" onfocus="style.backgroundColor=&#39;FFFFDC&#39;;" onblur="style.backgroundColor=&#39;FFFFFF&#39;;" onchange="llenaDias(&#39;anio&#39;,&#39;mes&#39;,&#39;dia&#39;);">
    <OPTION value="">año</OPTION>
    <OPTION value="2010">2010</OPTION>
    <OPTION value="2009">2009</OPTION>


</SELECT>
	                
	                <SELECT name="pago.fecha.mes" tabindex="53" id="mes" class="texto_general" onfocus="style.backgroundColor=&#39;FFFFDC&#39;;" onblur="style.backgroundColor=&#39;FFFFFF&#39;;" onchange="llenaDias(&#39;anio&#39;,&#39;mes&#39;,&#39;dia&#39;);">
    <OPTION value="">mes</OPTION>
    <OPTION value="01">Enero</OPTION>
    <OPTION value="02">Febrero</OPTION>
    <OPTION value="03">Marzo</OPTION>
    <OPTION value="04">Abril</OPTION>
    <OPTION value="05">Mayo</OPTION>
    <OPTION value="06">Junio</OPTION>
    <OPTION value="07">Julio</OPTION>
    <OPTION value="08">Agosto</OPTION>
    <OPTION value="09">Septiembre</OPTION>
    <OPTION value="10">Octubre</OPTION>
    <OPTION value="11">Noviembre</OPTION>
    <OPTION value="12">Diciembre</OPTION>


</SELECT>
	                
	                <SELECT name="pago.fecha.dia" tabindex="54" id="dia" class="texto_general" onfocus="style.backgroundColor=&#39;FFFFDC&#39;;" onblur="style.backgroundColor=&#39;FFFFFF&#39;;">
    <OPTION value="">día</OPTION>


</SELECT>
                    
					<INPUT type="hidden" id="fec" size="10" maxlength="10" name="fecha" class="texto_general" value="">
					<INPUT tabindex="55" type="button" value="..." onclick="javascript:popUpCalendar(document.getElementById(&#39;anio&#39;),document.getElementById(&#39;mes&#39;),document.getElementById(&#39;dia&#39;),this, this, &#39;dd/mm/yyyy&#39;);">
					<SCRIPT>fAgregaCampo('fecha', 'Fecha de operacion');</SCRIPT>
				</TD>		
			</TR>
			<TR>
				<TD class="texto_General" align="right" width="29%">
					Importe del pago:&nbsp;
				</TD>
				<TD class="texto_azul" width="1%">*</TD>
				<TD class="texto_general" align="left" width="35%">
				
					
				
					<INPUT type="text" name="pago.pagoSat.totalPago" size="10" maxlength="10" value="0" tabindex="56" id="idFrmEcinco_pago_pagoSat_totalPago" class="texto_general" onfocus="estilo_foco(this, &#39;impor&#39;, &#39;errorimpor&#39;, &#39;errorimpor&#39;, &#39;HelpComodin&#39;);" onblur="estilo_sinfoco(this, &#39;impor&#39;, &#39;errorimpor&#39;, &#39;&#39;, &#39;HelpComodin&#39;);" onkeypress="return aceptanum(event);">
						
				</TD>
				<TD width="35%">
					<SPAN id="impor" style="position:absolute;color:5c5c5c;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow.png"></TD><TD class="ComunicaAsesoria">
						Indique el monto de la operación sin comas ni centavos; es un dato indispensable para validar su pago.
						<B>Indique el monto de la operación sin comas ni centavos; es un dato indispensable para validar su pago.</B></TD></TR></TBODY></TABLE></SPAN>
					<SPAN id="errorimpor" style="position:absolute;color:ff1300;display:none;width:230;">
					<TABLE width="230"><TBODY><TR valign="top"><TD align="left">
					<IMG src="<%= request.getContextPath() %>/imgs/helper_arrow_error.png"></TD><TD class="ComunicaError">
					Campo obligatorio</TD></TR></TBODY></TABLE></SPAN>
				</TD>
			</TR>
           	<TR>
           		<TD>&nbsp;</TD>
           	</TR>
			<TR>
				<TD class="btnSubmit" align="center" colspan="4">				
					
	       			<INPUT type="image" alt="Submit" src="<%= request.getContextPath() %>/imgs/validar.jpg" id="idFrmEcinco_0" value="Submit" tabindex="57">

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

<SCRIPT type="text/javascript">
	setDias('día');
	//setTimeout("tbShow('/portal')",100);
	setNavigation('idFrmEcinco', '/portal/empresa/vigilancia/show.do', 'validaEcinco.do');
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
		<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/pago/ecinco/show.do#" tabindex="46" onclick="submitAction(false);"> <IMG src="./img/botonAnterior.png" border="0" alt="Anterior"> </A></TD>
		<TD width="50%"><A href="http://www.tuempresa.gob.mx/portal/pago/ecinco/show.do#" tabindex="45" onclick="submitAction(true);"> <IMG src="./img/botonSiguiente.png" border="0" alt="Siguiente"> </A></TD>
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

