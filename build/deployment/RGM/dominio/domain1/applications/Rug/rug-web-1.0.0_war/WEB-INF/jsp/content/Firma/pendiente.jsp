<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="col s2"></div>
					<div class="col s8">	
						<div class="row">
						   <s:if test="idTipoTramiteMasiva!=null&&idTipoTramiteMasiva==12">
						     	La firma múltiple esta en proceso. <br/>
								Se le notificara mediante un correo electrónico cuando los acreedores estén procesados, favor de verificar.<br/>
								<s:form namespace="/acreedor" action="inicia">
									<s:submit value="Regresar a Acreedores"></s:submit>
								</s:form>
							</s:if>
							<s:else>
						     	La boleta de firma múltiple esta en proceso <br/>
								Se le notificara mediante un correo electrónico cuando termine el proceso favor de verificar.<br/>
								<s:form namespace="/home" action="busqueda">
									<s:submit value="Regresar a Busqueda"></s:submit>
								</s:form>
							</s:else>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
