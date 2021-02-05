<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="section"></div>
<main>
	<center>
		<div class="container">
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title">Validaci&oacute;n de Boleta Electr&oacute;nica</span>
							<div class="row">
								<div class="col s2"></div>
								<div class="col s8">
									<s:if test="hasFieldErrors()">
										<div class="row">
											<div class="col s12 action-error">
												Los datos capturados son incorrectos.
												<s:fielderror />
											</div>
										</div>
									</s:if>
									<s:elseif test="hasActionErrors()">
										<div class="row">
											<div class="col s12 action-error">
												<p class="red z-depth-2" ><s:actionerror /></p>
											</div>											
										</div>										 
									</s:elseif>
									<s:else>
										<div class="row">
											<div class="col s12">
												<s:actionmessage />
												<div class="row">
											      <div class="col s12"><p class="teal z-depth-2"><span class="white-text">No. Garantia(s): </span><span class="grey-text text-lighten-1"><s:property value="garantias"/></span></p></div>									      
											      <div class="col s6"><p class="teal z-depth-2"><span class="white-text">Operacion: </span><span class="grey-text text-lighten-1"><s:property value="operacion" /><span></p></div>
											      <div class="col s6"><p class="teal z-depth-2"><span class="white-text">Fecha: </span><span class="grey-text text-lighten-1"><s:property value="fecha"/></span></p></div>
											    </div>
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
