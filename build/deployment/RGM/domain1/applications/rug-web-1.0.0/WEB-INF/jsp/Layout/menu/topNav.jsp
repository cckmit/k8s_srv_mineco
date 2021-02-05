<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="contenedorBontonGuardar" style="width: 610px; display: block;">
<table width="610" cellpadding="2" cellspacing="2"
	class="CajaDenominacion">
	<tr height="25">
		<td class="subtituloInteriorGris"></td>
		<td class="subtituloInteriorRojo" align="left"><!-- %=usuario.getSolicitud()%--> </td>
	</tr>
</table>
<div id="divbotonGuardar"
	style="position: relative; left: 420px; top: -15px; display: block;">
<table width="150" cellpadding="2" cellspacing="2">
	<tr>
		<td width="50%"><a
			href="#" > <img
			src="<%=request.getContextPath()%>/imgs/botonAnterior.png"
			border="0" alt="Anterior" /> </a></td>
		<td width="50%"><a href="#"> <img
			src="<%=request.getContextPath()%>/imgs/botonSiguiente.png"
			border="0" alt="Siguiente" /> </a></td>
	</tr>
</table>
</div>
</div>