<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="idPersonaUsuario" value="idPersona" />
<s:set var="maestra" value="cuentaMaestra"/>

<div class="section"></div>
<main>
<div class="container-fluid">
	<div class="row">
		<div class="col s1"></div>
		<div class="col s10 note">En Esta secci&oacute;n podr&aacute;
			registrar las boletas de pago dentro del sistema.</div>
		<div class="col s1"></div>
	</div>
	<div class="row">
		<div class="col s1"></div>
		<div class="col s10">
			<div class="card">
				<div class="card-content">
					<div class="row">
						<div class="col s12 action-error">
							<s:actionerror />
						</div>
					</div>	
					<div class="row">
						<div class="col s12 action-error">
							<a class="waves-effect waves-light btn" onClick="regresarBoleta();">Regresar</a>
						</div>
					</div>				
				</div>
			</div>
		</div>
		<div class="col s1"></div>
	</div>
</div>
</main>
<script type="text/javascript"> 
  function regresarBoleta() {
	  window.location.href = '<%= request.getContextPath() %>/home/misBoletas.do';
  }
</script>