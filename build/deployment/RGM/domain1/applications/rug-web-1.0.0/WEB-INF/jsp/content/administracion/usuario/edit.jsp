<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
	<center>
		<div class="container">
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<div class="card-title"><s:text name="perfil.titulo.registro" /> <strong><s:property value="#session.User.credentials.principal"/></strong></div>
							<div class="row">
								<div class="col m2"></div>
								<form class="col s12 m8">
									<div class="row">
										<div class="col s4">
											<p><s:text name="perfil.field.nombre" /></p>
										</div>
										<div class="col s8">
											<p><s:property value="personaFisica.nombre"/></p>
										</div>
									</div>
									<div class="row">
										<div class="col s4">
											<p><s:text name="perfil.field.nit" /></p>
										</div>
										<div class="col s8">
											<p><s:property value="personaFisica.rfc"/></p>
										</div>
									</div>
									<div class="row">
										<div class="col s4">
											<p><s:text name="perfil.field.docId" /></p>
										</div>
										<div class="col s8">
											<p><s:property value="personaFisica.documentoIdentificacion"/></p>
										</div>
									</div>
									<div class="row">
										<div class="col s4">
											<p><s:text name="perfil.field.tipoPersona" /></p>
										</div>
										<div class="col s8">
											<p><s:property value="personaFisica.tipoPersona"/></p>
										</div>
									</div>
									<div class="row">
										<div class="col s4">
											<p><s:text name="perfil.field.inscritoComo" /></p>
										</div>
										<div class="col s8">
											<p><s:property value="personaFisica.nacionalidadInscrito"/></p>
										</div>
									</div>
								</form>
								<div class="col m2"></div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-content">
							<div class="card-title"><s:text name="perfil.titulo.seguridad.registro" /></div>
							<div class="row">
								<div class="col m2"></div>
								<s:form namespace="administracion/perfil" id="idFormPerfil" action="update.do" theme="simple" cssClass="col s12 m8">
									<INPUT type="hidden" name="personaFisica.datosContacto.emailPersonal" value="<s:property value="#session.User.credentials.principal"/>" id="personaFisica.datosContacto.emailPersonal">
									<div class="row">
										<div class="col s12 action-error">
											<s:actionerror />
										</div>
									</div>
									<s:actionmessage />
									<div class="row">
								    	<div class="input-field col s12">
								        	<s:password name="perfil.registroUsuario.oldPassword" id="registroUsuario.oldPassword" required="true" cssClass="validate" maxlength="16" size="40" />
								        	<label for="registroUsuario.oldPassword"><s:text name="perfil.field.oldPassword" /></label>
							        	</div>
							        	<s:fielderror fieldName="perfil.registroUsuario.oldPassword" />
								 	</div>
								 	<div class="row">
								    	<div class="input-field col s12">
								    		<s:text name="perfil.field.password.ayuda" var="password.ayuda" />
								        	<s:password name="perfil.registroUsuario.password" id="contrasenaPass" required="true" cssClass="validate tooltipped" data-position="right" data-delay="50" data-tooltip="%{password.ayuda}" maxlength="16" size="40" />
								        	<label for="contrasenaPass"><s:text name="perfil.field.password" /></label>
							        	</div>
							        	<s:fielderror fieldName="perfil.registroUsuario.password" />
								 	</div>
								 	
								 	<div class="row">
								    	<div class="input-field col s12">
								        	<s:password name="perfil.registroUsuario.confirmacion" id="confirmacionPass" required="true" cssClass="validate" maxlength="16" size="40" />
								        	<label for="confirmacionPass"><s:text name="perfil.field.confirmPassword" /></label>
								   		</div>
								   		<s:fielderror fieldName="perfil.registroUsuario.confirmacion" />
								 	</div>
<!-- 								 	<div class="row"> -->
<!-- 								    	<div class="input-field col s12"> -->
<%-- 	        								<s:select name="registroUsuario.pregunta" id="pregunta" list="preguntas" listKey="pregunta" listValue="pregunta" onchange="onChangePregunta()" /> --%>
<%-- 								        	<label for="pregunta"><s:text name="usuario.field.pregunta" /></label> --%>
<!-- 								   		</div> -->
<!-- 								 	</div> -->
<!-- 								 	<div class="row" id="otra-pregunta" style="display: none;"> -->
<!-- 								    	<div class="input-field col s12"> -->
<%-- 								        	<s:textfield name="otraPregunta" id="otraPregunta" cssClass="validate" maxlength="50" size="40" /> --%>
<!-- 								        	<label for="otraPregunta">Ingrese la pregunta</label> -->
<!-- 								   		</div> -->
<!-- 								 	</div> -->
<!-- 								 	<div class="row"> -->
<!-- 								    	<div class="input-field col s12"> -->
<%-- 								        	<s:textfield name="registroUsuario.respuesta" id="registroUsuario.respuesta" required="true" cssClass="validate" maxlength="50" size="40" /> --%>
<%-- 								        	<label for="registroUsuario.respuesta"><s:text name="usuario.field.respuesta" /></label> --%>
<!-- 								   		</div> -->
<%-- 								   		<s:fielderror fieldName="registroUsuario.respuesta" /> --%>
<!-- 								 	</div> -->
									<center>
							            <div class="row">
							            	<s:submit type="button" name="Aceptar" value="Aceptar" align="center" cssClass="btn btn-large waves-effect indigo" />
							            </div>
						          	</center>
								</s:form>
								<div class="col m2"></div>
							</div>
						</div>
					</div>								
				</div>
			</div>
		</div>
	</center>
</main>
<div class="section"></div>
