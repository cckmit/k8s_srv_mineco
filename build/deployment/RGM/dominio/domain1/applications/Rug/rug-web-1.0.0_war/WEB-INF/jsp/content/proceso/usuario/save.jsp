<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
	<center>
		<div class="container">
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title"><s:text name="usuario.titulo.registro" /></span>
							<div class="row">
								<div class="col s2"></div>
								<div class="col s8">
									<s:if test="hasFieldErrors()">
										<div class="row">
											<div class="col s12 action-error">
												<s:text name="usuario.msg.activacion.error" />
												<s:fielderror />
											</div>
										</div>
									</s:if>
									<s:elseif test="hasActionErrors()">
										<div class="row">
											<div class="col s12 action-error">
												<s:actionerror />
											</div>
										</div>
									</s:elseif>
									<s:else>
										<div class="row">
											<div class="col s12">
												<s:actionmessage />
											</div>
										</div>
										<div class="row">
											<div class="col s12">
												<s:text name="usuario.estimado.titulo" />: <b><s:property value="personaFisica.nombre" /> <s:property value="personaFisica.apellidoPaterno" /> <s:property value="personaFisica.apellidoMaterno" /></b> 
											</div>
										</div>
										<div class="row">
											<div class="col s12">
												<s:text name="usuario.registrado.titulo" />: <b><s:property value="personaFisica.datosContacto.emailPersonal" /></b>
 											</div>
										</div>
										<div class="row">
											<div class="col s12">
												<s:text name="usuario.revise.titulo" />
 											</div>
										</div>
									</s:else>
									<center>
							            <div class='row'>
							            	<a href="<%= request.getContextPath() %>" class="btn btn-large waves-effect indigo"><s:text name="common.go.home" /></a>
							            </div>
						          	</center>
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
