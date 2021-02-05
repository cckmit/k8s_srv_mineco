<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@include file="/WEB-INF/jsp/Layout/menu/privilegios.jsp"%>
<main>
<div class="container-fluid">
	<div class="row">
			<div id="menuh">
			<ul>
				<%
				UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
				MenuTO menuTO= new MenuTO(1,request.getContextPath());	
				MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
				
				Boolean noHayCancel= (Boolean) request.getAttribute("noHayCancel");
				Boolean noVigencia = (Boolean) request.getAttribute("vigenciaValida");
				if(noHayCancel==null ||(noHayCancel!=null && noHayCancel.booleanValue()==true)){
					Integer idAcreedorRepresentado=(Integer) session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO);
					MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
					menuSecundarioTO = menuService.cargaMenuSecundario(idAcreedorRepresentado,usuarioTO,menuSecundarioTO,noVigencia);
					Iterator<String> iterator2 = menuSecundarioTO.getHtml().iterator();
					while (iterator2.hasNext()) {
						String menuItem = iterator2.next();
				%><%=menuItem%>
				<%
					}
				}
					
				%>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col s12"><div class="card">
		<div class="col s2"></div>
		<div class="col s8">
				<span class="card-title">Certificaci&oacute;n de la Garant&iacute;a Mobiliaria</span>				
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2">Garant&iacute;a
							Mobiliaria No. </span> <span> <s:property value="idGarantia" />
						</span>
					</div>
				</div>											
				<div class="row">
					<span class="card-title">Hist&oacute;rico de Tr&aacute;mites</span>
					<table id="mytabledaO"
						class="centered striped bordered responsive-table">
						<thead>
							<tr>
								<th>N&uacute;mero de Garant&iacute;a</th>
								<th>N&uacute;mero de operaci&oacute;n</th>
								<th>Tipo de operaci&oacute;n</th>
								<th>Fecha de creaci&oacute;n</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="tramiteTOs">
								<tr>
									<td><s:url var="uri" action="certificaTramite.do" encode="true">
											<s:param name="idGarantia" value="idGarantia" />
											<s:param name="idTramite" value="idTramite" />
										</s:url><a href="#" onclick="certificacion(<s:property value='idGarantia' />,<s:property value='idTramite' />);">
											<s:property value="idGarantia" />
										</a></td>
									<td><s:property value="idTramite" /></td>
									<td><s:property value="descripcionTramite" /></td>
									<td><s:property value="fechaModificacion" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>			
		</div>
		<div class="col s2"></div>
		</div></div>
	</div>
</div>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript">
function certificacion(garantia, tramite) {	
	var ruta = '/Rug/home/certificaTramite.do?idGarantia=' + garantia + '&idTramite='+tramite;
	
	// obtener el costo de una certificacion: tipo_tramite=5
	$.ajax({
		url: '<%= request.getContextPath() %>/rs/tipos-tramite/5',
		success: function(result) {
			MaterialDialog.dialog(
					"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100).toFixed(2) + ", �est� seguro que desea continuar?",
				{				
					title:'<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
					buttons:{
						// Use by default close and confirm buttons
						close:{
							className:"red",
							text:"cancelar"						
						},
						confirm:{
							className:"indigo",
							text:"aceptar",
							modalClose:true,
							callback:function(){																
								window.open(ruta, "_blank");
							}
						}
					}
				}
			);
		}
	});
}
</script>
</main>
