<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="card-content">
						<span class="card-title">Inscripci&oacute;n Garant&iacute;a Mobiliaria</span>
						<div class="row note teal">
							<span class="white-text">Es el asiento inicial de una Garantia Mobiliaria. 
								Fundamento Legal.- Art. 10 Ley de Garantias Mobiliiarias(Decreto No. 51-2007)
							</span>
						</div>
						<div class="row">
							<s:form action="creaInscripcion.do" onsubmit="return checkSubmit();"
									namespace="inscripcion" name="frmSeleccionaAcreedor"
									theme="simple" cssClass="col s12"
									id="frmSeleccionaAcreedor">
								<div class="row">
									<div class="input-field col s12">										
										<s:select list="listaAcreedores" listKey="idPersona"
										listValue="nombreCompleto" name="idAcreedorTO"></s:select>
								        <label for="idAcreedorTO"><s:text name="Seleccione el acreedor garante:" /></label>
									</div>
								</div>
								<center>
						            <div class='row'>
						            	<button class="btn btn-large waves-effect indigo" type="submit">Aceptar
						            		<i class="material-icons right">send</i>
						            	</button>
						            </div>
					          	</center>									
							</s:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>






