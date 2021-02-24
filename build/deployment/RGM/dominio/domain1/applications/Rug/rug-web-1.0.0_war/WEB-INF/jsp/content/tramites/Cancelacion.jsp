<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/validaciones.js"></script>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>
<script type="text/javascript">
function sendForm(){
	document.getElementById("bFirmar").value = "Enviando";
	document.getElementById("bFirmar").disabled = true;
	getObject('fcancelacion').submit();
}

function validarEnvio() {
	if(getObject('observaciones').value =="") {
		alertMaterialize('El campo observaciones no puede ser vacio');
		return false;
	}
	
	// obtener el costo de una cancelacion: tipo_tramite=4
	$.ajax({
		url: '<%= request.getContextPath() %>/rs/tipos-tramite/4',
		success: function(result) {
			MaterialDialog.dialog(
				"El costo de una " + result.descripcion + " es de Q. " + (Math.round(result.precio * 100) / 100).toFixed(2) + ", ¿está seguro que desea continuar?",
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
							modalClose:false,
							callback:function(){
								sendForm();
							}
						}
					}
				}
			);
		}
	});
}
</script>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>
<main>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>
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
				<form id="fcancelacion" name="fcancelacion" action="cancelacion.do">
					<span class="card-title">Cancelaci&oacute;n de la Inscripci&oacute;n</span>
					<div class="row note">
						<p>
							<span>Es la finalizaci&oacute;n de la Inscripci&oacute;n de la Garant&iacute;a Mobiliaria dentro del sistema.</span>
						</p>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<span class="blue-text text-darken-2">Garant&iacute;a
								Mobiliaria No. </span> <span> <s:property value="idGarantia" />
							</span>
						</div>
					</div>																	
					<div class="row">					
						<div class="input-field col s12">
							<s:textarea rows="10" cols="80" name="observaciones"
							        id="observaciones" />
							<label for="observaciones">Observaciones o motivos de la cancelaci&oacute;n: </label>
						</div>							
					</div>					
					 <hr />
			 	<center>
		            <div class='row'>			            	
		            	<input type="button" id="bFirmar" name="button" class="btn btn-large waves-effect indigo" value="Aceptar" onclick="validarEnvio();"/>			            				            							            	
		            </div>
	          	</center>		          					
				</form>
			</div>
			<div class="col s2"></div>
		</div>
	</div>
	</div>
</div>
</main>