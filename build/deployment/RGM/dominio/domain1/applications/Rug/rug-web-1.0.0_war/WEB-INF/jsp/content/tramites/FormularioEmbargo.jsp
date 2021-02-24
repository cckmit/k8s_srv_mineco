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
	getObject('embargoGuarda').submit();
}

function validarEnvio() {
	if(getObject('observaciones').value =="") {
		alertMaterialize('El campo observaciones no puede ser vacio');
		return false;
	}
	
	sendForm();		
		
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
		<div class="col s12"><div class="card">
			<div class="col s2"></div>
			<div class="col s8">
				<form id="embargoGuarda" name="embargoGuarda" action="embargoGuarda.do">
					<span class="card-title">Formulario Electr&oacute;nico para embargo y otras afectaciones </span>
					<div class="row note">
						<p>
							<span>Formulario de registro de embargo u otras anotaciones de demanda, situaciones de insolvencia o quiebra.</span>
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
							<s:select name="mdTipoEmbargo" id="mdTipoEmbargo" list="listaTipoEmbargo" listKey="idTipo" listValue="desTipo" />								    
						    <label>Tipo Accion</label>
						</div>						
					</div>																		
					<div class="row">					
						<div class="input-field col s12">
							<s:textarea rows="10" cols="80" name="observaciones"
							        id="observaciones" />
							<label for="observaciones">Anotaci&oacute;n de Embargo: </label>
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