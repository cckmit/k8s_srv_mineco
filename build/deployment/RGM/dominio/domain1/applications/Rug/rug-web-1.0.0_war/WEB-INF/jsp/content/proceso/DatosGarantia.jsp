<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<TABLE width="600" cellpadding="0" cellspacing="0" class="workArea">
<TBODY><TR>		
	<TD width="600">
		<TABLE width="600" cellpadding="0" cellspacing="0">
		<TBODY><TR>
			<TD>
				<DIV id="topNavContainer">

<DIV id="contenedorBontonGuardar" style="width: 600px; display: block;">
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
<TABLE class="formulario" width="600" cellspacing="0" cellpadding="0">
	<TBODY><TR>
		<TD>
	    	 <TABLE width="100%" border="0" cellspacing="2">
        		<TBODY><TR>
        			<TD class="titulo_interior_naranja">
	            		<TABLE width="253">
	                    	<TBODY>
	                    	  <TR>
	                    		<TD width="207" height="32" class="titulo_exterior_rojo">
	                    			2. Datos de la Garant&iacute;a Mobiliaria&nbsp;	                    		</TD>
	                    		<TD width="34" align="left">
				                   	<A tabindex="31" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domicilio&keepThis=true&TB_iframe=true&height=500&width=500" title="Ayuda en el domicilio" class="thickbox">
					                   	<IMG alt="Ayuda en el domicilio" src="<%= request.getContextPath() %>/imgs/documentinfo.png" border="0">				                   	</A>	                    		</TD>
	                    	</TR>
	            		</TBODY></TABLE>
            		</TD>
          		</TR>
         		  <TR>
         		    <TD><table border="0" width="95%" cellspacing="1" cellpadding="1" align="left">
         		      <tbody>
         		        <tr>
         		          <td colspan="5"><table>
         		            <tbody>
         		              <tr>
         		                <td width="320" class="tituloInteriorRojo">&nbsp;Acto o Contrato en el que se Define la Garant&iacute;a Mobiliaria</td>
         		                <td width="156" align="left" valign="bottom"><a tabindex="32" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domiciliosRegistrados&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en los domicilios registrados" class="thickbox"> <img alt="Ayuda en los domicilios registrados" src="./img/documentinfo.png" border="0" /></a></td>
       		                </tr>
       		              </tbody>
       		            </table></td>
       		          </tr>
         		        <tr>
         		          <td colspan="5" align="left"><img src="<%= request.getContextPath() %>/imgs/plecablanca.png" alt=" " border="0" width="600" height="11" /></td>
       		          </tr>
       		        </tbody>
       		      </table>
         		      <p>&nbsp;</p>
         		      <table width="100%" border="0" align="left" cellpadding="2" cellspacing="3">
         		        <tbody>
         		          <tr>
         		            <td class="texto_azul" width="10">&nbsp;</td>
         		            <td class="textoGeneralRojo" align="left" colspan="2"> Tipo de la Garant&iacute;a Mobiliaria:</td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td width="334" class="texto_general"><select tabindex="42" name="domicilio.claveEstado3" id="domicilio.claveEstado2" class="campo_general" onfocus="estilo_foco(this,'estadoa','erroredo', 'errordel', 'HelpComodin');" onblur="getMunicipios(this); estilo_sinfoco(this,'estadoa','erroredo', '' , 'errorCP');" onchange="resetCP();">
         		              <option value="gr">Prenda sin transmisi&oacute;n de posesi&oacute;n</option>
         		              <option value="pe">Fideicomiso de Garant&iacute;a</option>
         		              <option>Prenda ordinaria mercantil</option>
         		              <option>Hipoteca Industrial sobre Bienes Muebles</option>
         		              <option>Privilegio especial</option>
         		              <option>Derecho de retenci&oacute;n</option>
         		              <option>Garant&iacute;a Derivada de un Cr&eacute;dito Refaccionario</option>
         		              <option>Garant&iacute;a Derivada de un Cr&eacute;dito Habilitaci&oacute;n y Av&iacute;o</option>
         		              </select></td>
         		            <td width="234" valign="top" class="texto_general">&nbsp;</td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td class="texto_general"><span class="textoGeneralRojo">Fecha de celebraci&oacute;n:</span></td>
         		            <td class="texto_general"><span class="textoGeneralRojo">Fecha de terminaci&oacute;n:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td class="texto_general"><input name="otorgante.folio" type="text" size="20" /></td>
         		            <td class="texto_general"><input name="otorgante.folio3" type="text" size="20" /></td>
       		              </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Documento que contiene la Garant&iacute;a Mobiliaria:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><label for="textfield2"></label>
         		              <input type="text" name="textfield2" id="textfield2" />
         		              <input type="submit" name="button" id="button" value="Browse" /></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Tipos de Bienes Muebles Objeto de la Garant&iacute;a Mobiliaria:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><select tabindex="42" name="domicilio.claveEstado4" id="domicilio.claveEstado3" class="campo_general" onfocus="estilo_foco(this,'estadoa','erroredo', 'errordel', 'HelpComodin');" onblur="getMunicipios(this); estilo_sinfoco(this,'estadoa','erroredo', '' , 'errorCP');" onchange="resetCP();">
         		              <option value="pstp">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
         		              <option value="fg">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
         		              <option value="crgm">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
         		              <option value="chagm">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
         		              <option value="hi">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
         		              <option value="op">Tipo bien Tipo bien Tipo bien Tipo bien Tipo bien </option>
       		              </select></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Descripci&oacute;n de los bienes:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><textarea name="ta" cols="80" rows="10" class="ComunicaCampo" id="ta">Los certificados burs&aacute;tiles emitidos al amparo del Programa se encuentran garantizados por una prenda sin transmisi&oacute;n de posesi&oacute;n sobre (i) cuentas por cobrar, facturas y derechos de cobro presentes y futuros, derivados de contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria celebrados por la Emisora como acreedor, que se encuentran y se encontrar&aacute;n registrados en la cuenta de activo de su balance (las &ldquo;Cuentas por Cobrar&rdquo;), (ii) los bienes que se deriven de, o sean considerados como rendimientos, r&eacute;ditos, frutos o productos futuros, pendientes o ya obtenidos de las Cuentas por Cobrar a que se refiere el inciso (i) anterior, (iii) los bienes o derechos que la
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
accionistas de la Emisora.</textarea></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Valor de los bienes:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><input name="otorgante.folio2" type="text" size="20" />
         		              &nbsp;<span class="textoGeneralRojo">M.N.</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Ubicaci&oacute;n de los bienes:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><textarea name="ta2" cols="80" rows="10" class="ComunicaCampo" id="ta2">Los certificados burs&aacute;tiles emitidos al amparo del Programa se encuentran garantizados por una prenda sin transmisi&oacute;n de posesi&oacute;n sobre (i) cuentas por cobrar, facturas y derechos de cobro presentes y futuros, derivados de contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria celebrados por la Emisora como acreedor, que se encuentran y se encontrar&aacute;n registrados en la cuenta de activo de su balance (las &ldquo;Cuentas por Cobrar&rdquo;), (ii) los bienes que se deriven de, o sean considerados como rendimientos, r&eacute;ditos, frutos o productos futuros, pendientes o ya obtenidos de las Cuentas por Cobrar a que se refiere el inciso (i) anterior, (iii) los bienes o derechos que la
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
accionistas de la Emisora.</textarea></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Otros t&eacute;rminos y condiciones:</span></td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><textarea name="ta3" cols="80" rows="10" class="ComunicaCampo" id="ta3">Los certificados burs&aacute;tiles emitidos al amparo del Programa se encuentran garantizados por una prenda sin transmisi&oacute;n de posesi&oacute;n sobre (i) cuentas por cobrar, facturas y derechos de cobro presentes y futuros, derivados de contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria celebrados por la Emisora como acreedor, que se encuentran y se encontrar&aacute;n registrados en la cuenta de activo de su balance (las &ldquo;Cuentas por Cobrar&rdquo;), (ii) los bienes que se deriven de, o sean considerados como rendimientos, r&eacute;ditos, frutos o productos futuros, pendientes o ya obtenidos de las Cuentas por Cobrar a que se refiere el inciso (i) anterior, (iii) los bienes o derechos que la
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
accionistas de la Emisora.</textarea></td>
       		            </tr>
       		            </tbody>
     		        </table>
         		      <table border="0" width="95%" cellspacing="1" cellpadding="1" align="left">
         		        <tbody>
         		        <tr>
         		          <td colspan="5"><table>
         		            <tbody>
         		              <tr>
         		                <td width="456" class="tituloInteriorRojo">&nbsp;Acto o Contrato  originador de la obligaci&oacute;n garantizada</td>
         		                <td width="20" align="left" valign="bottom"><a tabindex="32" href="http://www.tuempresa.gob.mx/portal/comun/publico/help.do?llave=domiciliosRegistrados&amp;keepThis=true&amp;TB_iframe=true&amp;height=500&amp;width=500" title="Ayuda en los domicilios registrados" class="thickbox"> <img alt="Ayuda en los domicilios registrados" src="./img/documentinfo.png" border="0" /></a></td>
       		                </tr>
       		              </tbody>
       		            </table></td>
       		          </tr>
         		        <tr>
         		          <td colspan="5" align="left"><img src="./img/plecablanca.png" alt=" " border="0" width="600" height="11" /></td>
       		          </tr>
       		        </tbody>
 		      </table>
         		      <p>&nbsp;</p>
         		      <p>&nbsp;</p>
         		      <table width="95%" border="0" align="left" cellpadding="2" cellspacing="3">
         		        <tbody>
         		          <tr>
         		            <td width="10" class="texto_azul">&nbsp;</td>
         		            <td width="319" align="left" class="textoGeneralRojo">Tipo de Acto/Contrato:</td>
         		            <td width="249" align="left" class="textoGeneralRojo">&nbsp;</td>
       		              </tr>
         		          <tr>
         		            <td class="texto_azul">&nbsp;</td>
         		            <td align="left" class="textoGeneralRojo"><span class="texto_general">
         		              <select tabindex="42" name="domicilio.claveEstado" id="domicilio.claveEstado4" class="campo_general" onfocus="estilo_foco(this,'estadoa','erroredo', 'errordel', 'HelpComodin');" onblur="getMunicipios(this); estilo_sinfoco(this,'estadoa','erroredo', '' , 'errorCP');" onchange="resetCP();">
         		                <option value="gr">Cr&eacute;dito Simple</option>
         		                <option value="pe">Cr&eacute;dito en Cuenta Corriente</option>
         		                <option>Cr&eacute;dito de Habilitaci&oacute;n y Av&iacute;o</option>
                              </select>
       		              </span></td>
         		            <td align="left" class="textoGeneralRojo">&nbsp;</td>
       		              </tr>
         		          <tr>
         		            <td class="texto_azul">&nbsp;</td>
         		            <td align="left" class="textoGeneralRojo">Fecha de celebraci&oacute;n:</td>
         		            <td align="left" class="textoGeneralRojo">Fecha de terminaci&oacute;n:</td>
       		              </tr>
         		          <tr>
         		            <td class="texto_azul">&nbsp;</td>
         		            <td align="left" class="textoGeneralRojo"><input name="textfield4" type="text" id="textfield4" size="10" /></td>
         		            <td align="left" class="textoGeneralRojo"><span class="texto_general">
         		              <input name="textfield7" type="text" id="textfield5" size="10" />
         		            </span></td>
       		              </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Documento que contiene el Acto o Contrato que origina la obligaci&oacute;n garantizada:</span></td>
         		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td class="texto_general"><input type="text" name="textfield3" id="textfield3" />
                              <input type="submit" name="button2" id="button2" value="Browse" /></td>
         		            <td class="texto_general">&nbsp;</td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td class="texto_general"><span class="textoGeneralRojo">L&iacute;mite del cr&eacute;dito:</span></td>
         		            <td class="texto_general">&nbsp;</td>
       		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td class="texto_general"><label for="textfield7"><span class="textoGeneralRojo">
         		              <input name="textfield" type="text" id="textfield" size="20" />
         		            </span><span class="textoGeneralRojo">M.N.</span></label></td>
         		            <td class="texto_general">&nbsp;</td>
       		              </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><span class="textoGeneralRojo">Otros t&eacute;rminos y condiciones:</span></td>
         		            </tr>
         		          <tr>
         		            <td>&nbsp;</td>
         		            <td colspan="2" class="texto_general"><textarea name="ta3" cols="70" rows="10" class="ComunicaCampo" id="ta3">Los certificados burs&aacute;tiles emitidos al amparo del Programa se encuentran garantizados por una prenda sin transmisi&oacute;n de posesi&oacute;n sobre (i) cuentas por cobrar, facturas y derechos de cobro presentes y futuros, derivados de contratos de mutuo con inter&eacute;s y garant&iacute;a prendaria celebrados por la Emisora como acreedor, que se encuentran y se encontrar&aacute;n registrados en la cuenta de activo de su balance (las &ldquo;Cuentas por Cobrar&rdquo;), (ii) los bienes que se deriven de, o sean considerados como rendimientos, r&eacute;ditos, frutos o productos futuros, pendientes o ya obtenidos de las Cuentas por Cobrar a que se refiere el inciso (i) anterior, (iii) los bienes o derechos que la
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
accionistas de la Emisora.</textarea></td>
         		            </tr>
       		            </tbody>
       		        </table>
         		      <p>&nbsp;</p></TD>
       		    </TR>
         		  <TR>
         		    <TD><p>&nbsp;</p>

         		      <table width="95%" border="0" align="left" cellspacing="0" cellpadding="0">
         		        <tbody>
         		          </tbody>
         		        </table></TD>
       		    </TR>
         		<TR>
         		  <TD><p>&nbsp;</p>
         		    <p>&nbsp;</p>
         		    <p>&nbsp;</p></TD>
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

