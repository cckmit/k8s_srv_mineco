<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<main> 
<div class="container-fluid">
	<div class="row">
		<div class="col s12">
			<div class="card">
				<div class="col s2"></div>
				<div class="col s8">	
					<span class="card-title">Saldo Insuficiente</span>
					<div class="row note">
						<p>No hay suficiente saldo para realizar la operaci&oacute;n favor verifique sus boletas y vuelva a intentarlo.
						   Su garantia la puede buscar en la opcion de mis operaciones/pendientes de confirmar.</p>
					</div>
				</div>
				<div class="col s2"></div>
			</div>
		</div>
	</div>
</div> 
</main> 
 
