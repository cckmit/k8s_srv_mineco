<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
	<center>
		<div class="container">
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><s:text name="usuario.titulo.recuperacion" /></span>
							<div class="row">
								<div class="col s2"></div>
								<div class="col s8">
									<s:form namespace="usuario" id="frmRecover" action="change.do" theme="simple">
										<s:hidden name="registroUsuario.valid" value="true" />
										<div class="row">
											<div class="col s12 action-error">
												<s:actionerror />
											</div>
										</div>
										<div class="row">
											<div class="col s12">
												<s:actionmessage />
											</div>
										</div>
										<div class="row note">
											<s:text name="usuario.recover.aviso" />
										</div>
										<div class="row">
									    	<div class="input-field col s12">
									        	<s:textfield name="personaFisica.datosContacto.emailPersonal" id="emailPersonal" required="true" onkeyup="this.value = this.value.toLowerCase()" maxlength="50" />
									        	<label for="emailPersonal"><s:text name="usuario.field.correo" /></label>
									    		<s:fielderror fieldName="personaFisica.datosContacto.emailPersonal" />
									   		</div>
									 	</div>
									 	<div class="row note note-example">
									 		<div class="col s1">
									 			<p><s:text name="common.ejemplo" /></p>
									 		</div>
									 		<div class="col s11">
									 			<ol type="a">
										 			<li><s:text name="usuario.recover.ejemplo1" /></li>							 			
										 			<li><s:text name="usuario.recover.ejemplo2" /></li>
									 			</ol>
									 		</div>
									 	</div>
									 	<div class="row" id="pregunta"></div>
									 	<center>
								            <div class='row'>
												<a class="btn btn-large waves-effect indigo" id="btnValidar" onclick="obtenerPreguntaSeguridad();">Validar</a>
												<s:submit type="button" align="center" cssClass="btn btn-large waves-effect indigo" id="btnRecuperar" style="display: none;" value="Recuperar"/>
								            </div>
							          	</center>
									</s:form>
								</div>
								<div class="col s2"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</center>
</main>
<div class="section"></div>
