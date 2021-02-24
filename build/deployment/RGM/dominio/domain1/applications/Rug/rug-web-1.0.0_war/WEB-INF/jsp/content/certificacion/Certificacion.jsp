<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.garantia.dao.GarantiasDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<div class="section"></div>
<main>
    
      
	<div class="container-fluid">
		<div class="row">
			<div class="col s12 l6">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Certificaci&oacute;n</span>
						<div class="row note">
							Solicitud de una certificaci&oacute;n de una Garant&iacute;a Mobiliaria
						</div>
						<div class="row">
							<s:form namespace="usuario" action="resBusqueda.do" theme="simple" cssClass="col s12">
								<div class="row">
							    	<div class="input-field col s12">
							        	<s:textfield name="idGarantia" id="idGarantia" size="17"   maxlength="20" />
							        	<label for="idGarantia">N&uacute;mero de inscripci&oacute;n de la Garant&iacute;a</label>
							   		</div>
							 	</div>							 	
							 	<center>
						            <div class='row'>
						            	<a class="btn btn-large waves-effect indigo" onclick="tramitesJSP(<s:property value="idPersona"/>);">Buscar</a>
						            </div>
					          	</center>
							</s:form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Tr&aacute;mites Realizados</span>
						<div id="resultadoDIV"></div> 
					</div>
				</div>
			</div>
		</div>
	</div>
</main>