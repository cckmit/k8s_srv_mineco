<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<main> 
	<sj:head jqueryui="true"/>
<%
 	//Privilegios
 	PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
 	PrivilegiosTO privilegiosTO = new PrivilegiosTO();
 	privilegiosTO.setIdRecurso(new Integer(5));
 	privilegiosTO = privilegiosDAO.getPrivilegios(privilegiosTO,
 			(UsuarioTO) session.getAttribute(Constants.USUARIO));
 	Map<Integer, PrivilegioTO> priv = privilegiosTO.getMapPrivilegio();

 	privilegiosTO.setIdRecurso(new Integer(3));
 	Map<Integer, PrivilegioTO> priv2 = privilegiosDAO
 			.getPrivilegios(privilegiosTO, (UsuarioTO) session.getAttribute(Constants.USUARIO))
 			.getMapPrivilegio();

 	String domainName = new String(request.getRequestURL()).replace(request.getRequestURI(), "");
 	String urlWebserviceFirmaTramites = domainName + "/WebserviceGeneraTramiteXML";
 	String urlSuccessFulGoto = domainName + request.getContextPath() + request.getAttribute("urlBack");
 	UsuarioTO usuarioTO = (UsuarioTO) session.getAttribute(Constants.USUARIO);
 	String urlBaseCode = request.getContextPath() + "/resources/applet/";
 	Integer idTramiteNuevo = (Integer) session.getAttribute(Constants.ID_TRAMITE_NUEVO);
 	Integer idTipoTramiteNuevo = (Integer) request.getAttribute("idTipoTramite");

 	request.removeAttribute("urlBack");
 	request.removeAttribute("idTipoTramite");
 	//session.removeAttribute(Constants.ID_TRAMITE_NUEVO);
 %> <%
 	String cadenaOriginal = (String) request.getAttribute("cadenaOriginal");
 	if (cadenaOriginal != null && cadenaOriginal.length() > 5) {
 %>
<div class="container-fluid">
	<div class="row">
		<div class="col s12">
			<div class="card">
				<div class="col s2"></div>
				<div class="col s8">	
					<span class="card-title">Confirmaci&oacute;n Electr&oacute;nica</span>
					<div class="row note">
						<p>Con su confirmaci&oacute;n electrónica,
						usted, bajo protesta de decir verdad:</b> <br />1. Ratifica todos y
						cada uno de los Términos y Condiciones que aceptó al momento de
						registrarse como usuario del sistema del Registro de Garant&iacute;as Mobiliarias
						; <br />2. Reconoce la existencia y veracidad de la
						información ingresada o trasladada; y <br />3. Manifiesta que conoce la
						responsabilidad civil, administrativa y penal en que podría
						incurrir en caso de falsedad. </p>
					</div>
					<s:form action="firmaGuarda.do" theme="simple" id="b64Form">
						<s:hidden id="idBase64UserSign" name="base64UserSign" value=""></s:hidden>
						<s:hidden id="idBase64UserCertificate" name="base64UserCertificate"
							value=""></s:hidden>
						<input type="hidden" name="idTramite"
							value="<%=request.getAttribute("idTramite")%>" />
						<input type="hidden" name="cadenaOriginal"
							value="<%=request.getAttribute("cadenaOriginal")%>" />
						<input type="hidden" name="idTipoTramite"
							value="<%=request.getAttribute("idTipoTramite")%>" />
						<center>
							<div class="row">
								<div class="btn btn-large waves-effect indigo" id="btnFirma">									
									<sj:submit 	targets="result" 
										value="Confirmar" 
										timeout="2500" 
										indicator="indicator" 
										onBeforeTopics="before" 
										onCompleteTopics="complete" 
										onErrorTopics="errorState"  
										effect="highlight" 
										effectOptions="{ color : '#222222' }" 
										effectDuration="3000"/>
								</div>
							</div>
						</center>
					</s:form>
					<img id="indicator" src="<%=request.getContextPath()%>/resources/imgs/loader/loadingAnimation.gif" alt="Loading..." style="display:none"/>
				</div>
				<div class="col s2"></div>
			</div>
		</div>
	</div>
</div> 
<%
	} else {
%>
	No se pudo generar la cadena original
<%
	}
%>
</main> 
 
	<script type="text/javascript">
		$.subscribe('before', function(event,data) {
		  var fData = event.originalEvent.formData;
		 alert('About to submit: \n\n' + fData[0].value + ' to target '+event.originalEvent.options.target+' with timeout '+event.originalEvent.options.timeout );
		  var form = event.originalEvent.form[0];
		  if (form.echo.value.length < 2) {
		  alert('Please enter a value with min 2 characters');	  
		  event.originalEvent.options.submit = false;
		  }
		});
		$.subscribe('complete', function(event,data) {
		 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
		 '\n\nThe output div should have already been updated with the responseText.');
		});
		$.subscribe('errorState', function(event,data) {
		alert('status: ' + event.originalEvent.status + '\n\nrequest status: ' +event.originalEvent.request.status);
		});
	</script>  
