<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="menuLateral">
<tr class="menuLateralNivel1" >
	<td align="left" width="9">
		<img src="<%= request.getContextPath() %>/imgs/cuadroAmarillo.png" />
	</td>		
	<td align="left">a.</td>
	<td align="left"><a id="menua" class="textoGeneralGrisBold">Inscripción</a></td>
</tr>
<tr>
	<td width="9"></td><td class="texto_general"><img id="imagenMenu1"
		src="<%= request.getContextPath() %>/imgs/perlazul_provisional.jpg" alt=" " /></td>
	<td class="texto_general"><a id="menu1" href="<%= request.getContextPath() %>/home/portada.do"
		class="textoInactivoMenuLateral" tabindex="15">1. Alta Acreedor</a></td>
</tr>
<tr>
	<td width="9"></td>
	<td class="texto_general">
		<img id="imagenMenu2" src="<%= request.getContextPath() %>/imgs/perlazul_provisional.jpg" alt=" " />
	</td>

	<td class="texto_general">
		<a id="menu2" class="textoInactivoMenuLateral" tabindex="16" href="<%= request.getContextPath() %>/home/buscador.do">
					2. B&uacute;squeda de Garant&iacute;a la Garantia Mobiliaria</a>
	</td>
</tr>
<tr>
	<td width="9"></td>
	<td class="texto_general">
		<img id="imagenMenu3" src="<%= request.getContextPath() %>/imgs/perlazul_provisional.jpg" alt=" " /></td>
	<td class="texto_general">
		<a id="menu3" class="textoInactivoMenuLateral" tabindex="17" href="<%= request.getContextPath() %>/home/grals.do">
					3. Datos Generales</a>
	</td>
</tr>
<tr>
	<td width="9"></td>
	<td class="texto_general">
		<img id="imagenMenu4" src="<%= request.getContextPath() %>/imgs/perlazul_provisional.jpg" alt=" " /></td>

	<td class="texto_general">
		<a id="menu4" class="textoInactivoMenuLateral" tabindex="18" href="<%= request.getContextPath() %>/home/garantia.do">
			4. Datos de la Garant&iacute;a Mobiliaria</a>
	</td>
</tr>
<tr>
	<td width="9"></td>
	<td class="texto_general">
		<img id="imagenMenu6" src="<%= request.getContextPath() %>/imgs/perlazul_provisional.jpg" alt=" " /></td>

	<td class="texto_general">
		<a id="menu6" class="textoInactivoMenuLateral" tabindex="20" href="<%= request.getContextPath() %>/home/valida.do">5. Validaci&oacute;n</a>
	</td>
</tr>
<tr>
	<td align="left" width="9">
   		<img src="<%= request.getContextPath() %>/imgs/cuadroAmarillo.png" />
	</td>
	<td align="left">b.</td>
	<td align="left" ><a id="menub" class="textoGeneralGrisBold" href="<%= request.getContextPath() %>/home/pago.do" >Pagar Derechos</a></td>
</tr>
<tr>
	<td align="left" width="9" >
   		<img src="<%= request.getContextPath() %>/imgs/cuadroVerde.png" />
	</td>
	<td align="left">c.</td>
	<td align="left"><a id="menuc" class="textoGeneralGrisBold" href="<%= request.getContextPath() %>/home/expediente.do">Expediente</a></td>
</tr>
</table>
<br/>
<br/>
<br/>
<table cellpadding="0" cellspacing="3" class="tituloEncabezado">
  <tbody>
    <tr class="menuLateralNivel1">
      <td colspan="3" align="center" bgcolor="#AD4110" class="titulosupblanco">Operaciones por pagar</td>
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
      <td colspan="3" class="texto_general"><img src="<%= request.getContextPath() %>/img/plecablanca.png" alt=" " border="0" width="150" height="11" /></td>
      </tr>
    <tr>
      <td class="texto_general"><span class="texto_inactiva">Total</span></td>
      <td align="center" class="texto_general">&nbsp;</td>
      <td class="texto_general"><span class="texto_inactiva">$ 300.00</span></td>
      </tr>
    </tbody>
  </table>
