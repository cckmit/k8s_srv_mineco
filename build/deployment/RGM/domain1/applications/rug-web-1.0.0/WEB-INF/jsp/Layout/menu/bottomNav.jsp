<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="div_navegar" style="display: block; width: 610px;">
<table width="100%" cellpadding="2" cellspacing="2" class="CajaMenu">
	<tr height="25">
		<td class="subtituloInteriorGris" width="100%">Para guardar su
		información presione Guardar o Siguiente</td>
	</tr>
</table>
<div id="divbotonSiguiente"
	style="position: relative; left: 420px; top: -15px; width: 150px;">
<table width="150" cellpadding="2" cellspacing="2">
	<tr>
		<td width="50%"><a
			href="javascript:AnteriorPestana(${EmpresaTO.idControlUnico});"
			tabindex="46"> <img
			src="<%=request.getContextPath()%>/resources/imgs/botonAnterior.png"
			border="0" alt="Anterior" /> </a></td>
		<td width="50%"><a href="javascript:guardarSigPestana();"
			tabindex="45"> <img
			src="<%=request.getContextPath()%>/resources/imgs/botonSiguiente.png"
			border="0" alt="Siguiente" /> </a></td>
	</tr>
</table>
</div>
</div>
