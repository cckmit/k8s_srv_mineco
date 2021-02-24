<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="idPersonaUsuario" value="idPersona"/>

<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s1"></div>
			<div class="col s10 note">
				Para llevar a cabo cualquier operación en el RUG distinta de Consulta y Certificación es necesario dar de alta al acreedor respecto del cual se realizará la operación
			</div>
			<div class="col s1"></div>
		</div>
		<div class="row">
			<div class="col s1"></div>
			<div class="col s10">
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s8">
								<span class="card-title">Mis Acreedores</span>
							</div>
							<div class="col s4 right-align">
								<a class="btn btn-large waves-effect indigo modal-trigger" href="#frmAcreedor" id="btnAgregar"><i class="material-icons left">add</i>Agregar</a>
							</div>
						</div>
						<div class="row">
							<table id="acreedores">
								<thead>
									<tr>
										<th>Nombre, Denominación o Razón Social</th>
										<th>Documento de identificación</th>
										<th>Opciones</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="listaAcreedores">
									<tr id='<s:property value="idPersona"/>'>
										<td><s:property value="nombre"/></td>
										<td><s:property value="rfc"/></td>
										<td>
											<s:set var="privilegioModificar" value="privilegioModificar"/>
											<s:if test="%{privilegioModificar == 1}">
											<a href="#" class="btn waves-effect indigo" onclick='cargaAcreedor(<s:property value="idPersona"/>)'>Modificar</a>
											</s:if>
										</td>
									</tr>
								</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col s1"></div>
		</div>
		<div class="row">
			<div class="col s1"></div>
			<div class="col s10">
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s8">
								<span class="card-title">Acreedores pendientes por confirmar</span>
							</div>
						</div>
						<div class="row">
							<table id="acreedoresPendientes">
								<thead>
									<tr>
										<th>Nombre, Denominación o Razón Social</th>
										<th>Documento de identificación</th>
										<th>Opciones</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="listaAcreedoresSinFirma">
									<tr id='<s:property value="idPersona"/>'>
										<td><s:property value="nombre"/></td>
										<td><s:property value="rfc"/></td>
										<td>
											<a href="#" class="btn waves-effect indigo" onclick='firmaAcreedorRepresentado(<s:property value="idTramitePendiente"/>)'>Confirmar</a>
											<a href="#" class="btn waves-effect red darken-4" onclick="eliminaParteAcreedorRepresentado('<s:property value="idPersonaUsuario"/>','<s:property value="idPersona"/>')">Eliminar</a>
										</td>
									</tr>
								</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col s1"></div>
		</div>
	</div>
</main>
<div class="section"></div>
<div id="frmAcreedor" class="modal">
	<div class="modal-content">
		<div class="card">
			<div class="card-content">
				<span class="card-title">Acreedor Garantizado</span>
				<div class="row note">
					La persona o personas en cuyo favor el deudor garante o por la ley constituye una garantía mobiliaria, con o sin posesión, ya sea en el beneficio propio o de un tercero.
				</div>
				<s:hidden  value="%{esAutoridad}" name="esAutoridad" />
				<div class="row">
					<div class="col s1"></div>
					<div class="col s10">
					 	<div class="row">
					    	<div class="input-field col s12" id="anotacionTipoPersona">
     							<s:select name="tipoPersonaA" id="tipoPersonaA" list="listaTipoPersona" listKey="id" listValue="desc" onchange="cambiaTipoPersonaARep($('#esAutoridad').val())"/>
					        	<label for="tipoPersonaA">Seleccione Tipo de Persona</label>
					   		</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12">
     							<s:select name="nacionalidadA" id="nacionalidadA" list="listaNacionalidad" listKey="idNacionalidad" listValue="descNacionalidad" onchange='cambiaNacionalidadARep(0)'/>
					        	<label for="nacionalidadA">Nacionalidad</label>
					   		</div>
					 	</div>
					 	<!--div class="row">
					   		<div class="col s12" id="trtdoptogantefisicoAcreedores">
						   		<input  type="checkbox"  name="optotorganteAC" id="optotorganteAC" onclick="cambiaFolioElectronicoAcreedor()"/>
						   		<label for="optotorganteAC" class="checkboxLabel">
						   			El Acreedor cuenta con Folio Electrónico en el RUG
						   		</label>
					   		</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12" id="folioIDExtAC">
     							<s:textfield name="folioExistenteAC" id="folioExistenteAC" cssClass="validate" maxlength="100" size="50" onkeydown="desactivaBoton()"/>
					        	<label for="folioElectronico">Introduzca el Folio Electrónico</label>
					   		</div>
					   		<div class="col s12" id="buttonValidar">
						   		<button type="button" onclick="buscaFolioElectronicoAltaAcreedorRepresentado()" class="btn waves-effect indigo">Validar</button>
					   		</div>
					   		<div class="col s12" id="resBusqueda">
					   		</div>
					   		<div class="col s12" id="folioIDNoExtAC">
					   		</div>
					 	</div-->
					 	<!--div class="row">
					 		<div class="input-field col s12" id="NIFpaisAC">
					 			<s:textfield name="nif" id="nif" cssClass="validate" maxlength="16" onkeyup="this.value = this.value.toUpperCase()"/>
					        	<label for="nif">Número de Identificación Fiscal en el país de origen</label>
					 		</div>
					 	</div-->
					 	<div class="row" id="personaMoralTRA">
					 		<div class="row">
						 		<div class="input-field col s12">
						 			<s:textfield name="razonSocialA" id="razonSocialA" cssClass="validate" maxlength="150" />
						        	<label for="razonSocialA">Razón o Denominación Social</label>
						 		</div>
					 		</div>
					 		<div class="row note note-example">
						 		<div class="col s1">
						 			<p>Ejemplos</p>
						 		</div>
						 		<div class="col s11">
					 				<ol type="a">
					 					<li>Denominación: Electrón, S.A.</li>
					 					<li>Razón Social: López Méndez y Cía, S. A.</li>
					 				</ol>
						 		</div>
						 	</div>
					 		<div class="row">
							 	<div class="input-field col s12 m6">
						 			<s:textfield name="inscritaA" id="inscritaA" cssClass="validate" maxlength="150" />
						        	<label for="inscritaA">Inscrita bajo el número</label>
						 		</div>
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="folioA" id="folioA" cssClass="validate" maxlength="150" />
						        	<label for="folioA">Folio</label>
						 		</div>
						 	</div>
					 		<div class="row">
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="libroA" id="libroA" cssClass="validate" maxlength="150" />
						        	<label for="libroA">Libro</label>
						 		</div>
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="ubicacionA" id="ubicacionA" cssClass="validate" maxlength="150" />
						        	<label for="ubicacionA">Dirección</label>
						 		</div>
						 	</div>
					 	</div>
					 	<div id="personaFisicaTRA">
						 	<div class="row">
						 		<div class="input-field col s12">
						 			<s:textfield name="nombreA" id="nombreA" cssClass="validate" maxlength="50" onkeyup="this.value = this.value.toUpperCase()"/>
						        	<label for="nombreA">Nombre Completo</label>
						 		</div>
						 	</div>
					 	</div>
					 	<div id="rfcIDA">
					 		<div class="row">
						 		<div class="input-field col s10">
						 			<s:textfield name="rfcA" id="rfcA" cssClass="validate" maxlength="14" size="16" onkeyup="this.value = this.value.toUpperCase()" />
						        	<label for="rfcA">Documento de Identificación</label>
						 		</div>
						 		<div class="input-field col s2 right-align">
						 			<a href="#" class="btn waves-effect indigo">Buscar</a>
						 		</div>
					 		</div>
					 		<div class="row note note-example">
						 		<div class="col s1">
						 			<p>Ejemplos</p>
						 		</div>
						 		<div class="col s11">
					 				<ol type="a">
					 					<li>DPI:  10013456789</li>
					 					<li>Pasaporte:  H23585858</li>
					 				</ol>
						 		</div>
						 	</div>
						 	<div class="row">
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="extencionA" id="extencionA" cssClass="validate" maxlength="14" size="16" onkeyup="this.value = this.value.toUpperCase()" />
						        	<label for="extencionA">Extendido en</label>
						 		</div>
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="edadA" id="edadA" cssClass="validate" maxlength="14" size="16" onkeyup="this.value = this.value.toUpperCase()" />
						        	<label for="edadA">Edad</label>
						 		</div>
						 	</div>
						 	<div class="row">
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="estadoCivilA" id="estadoCivilA" cssClass="validate" maxlength="14" size="16" onkeyup="this.value = this.value.toUpperCase()" />
						        	<label for="estadoCivilA">Estado Civil</label>
						 		</div>
						 		<div class="input-field col s12 m6">
						 			<s:textfield name="profesionA" id="profesionA" cssClass="validate" maxlength="14" size="16" onkeyup="this.value = this.value.toUpperCase()" />
						        	<label for="profesionA">Profesión u Oficio</label>
						 		</div>
						 	</div>
					 	</div>
					 	<div>
					 	 	<div class="row">
						 		<div class="input-field col s12">
						 			<s:textfield name="telefono" id="telefono" cssClass="validate" maxlength="13" onkeypress="return IsNumber(event);"/>
						        	<label for="telefono">Teléfono</label>
							 	</div>
						 	</div>
						 	<div class="row">
						 		<div class="input-field col s12">
						 			<s:textfield name="correoElectronico" id="correoElectronico" cssClass="validate" maxlength="50"/>
						        	<label for="correoElectronico">Correo Electrónico</label>
							 	</div>
						 	</div>
						 	<div id="correosExtra">
						 	</div>
						 	<div class="row">
							 	<a onclick="agregaMail();" class="btn waves-effect indigo" id="Agregar"><i class="material-icons left">add</i></a>
						 	</div>
					 	</div>
			 			<h4>Domicilio para efectos del RUG</h4>
				 		<div class="row">
				 			<div class="input-field col s12">
				 				<s:select name="residenciaA" id="residenciaA" list="listaNacionalidad" listKey="idNacionalidad" listValue="descNacionalidad" onchange='cambiaResidencia()'/>
				 				<label for="residenciaA">País de residencia</label>
			 				</div>
				 		</div>
					 	<div id="calleExt">
					 		<div class="row">
					 			<div class="input-field col s12">
						 			<s:textarea name="domicilioUno" id="domicilioUno" cols="53" onKeyUp="return maximaLongitud(this,200)" cssClass="materialize-textarea"></s:textarea>
						        	<label for="domicilioUno">Domicilio</label>
							 	</div>
					 		</div>
				 		</div>
				 		<div id="cpTab"></div>
					</div>
					<div class="col s1"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<s:set var="altaParteIdPersona" value="altaParte.idPersona"/>
		<s:set var="autoridad" value="esAutoridad"/>
 		<s:if test="%{#altaParteIdPersona == 0}">
 			<!--$('#nacionalidadA').val('1');-->
 			<div class="row">
 				<div class="col s12">
 					<a href="#" class="btn waves-effect indigo" onclick="guardaParteAcreedorRepresentado('acredID',<s:property value='idPersonaUsuario' />,'0',<s:property value='esAutoridad' />);">Aceptar</a>
 					<a href="#" class="modal-action modal-close btn waves-effect indigo" onclick="ocultarAcreRep('acredID',<s:property value='idPersonaUsuario' />,'0');">Ocultar</a>
 				</div>
 			</div>
 		</s:if>
		<s:else>
			<!--$('#nacionalidadA').val('1');-->
			<div class="row">
				<div class="col s12">
					<a href="#" class="btn waves-effect indigo" onclick="modificaParteAcreedorRepresentado('acredID',<s:property value='idPersona' />, <s:property value="altaParteIdPersona" />, <s:property value='esAutoridad' />);">Aceptar</a>
					<a href="#" class="modal-action modal-close btn waves-effect indigo" onclick="ocultarAcreRep('acredID',<s:property value='idPersona' />,'0');">Ocultar</a>
				</div>
			</div>
		</s:else>
	</div>
</div>

	
	<div id="cuerpo"><!-- Empieza div cuerpo -->
	<table>	
		
		<tr>
			<td id="acredID"></td>	
		</tr>
	</table>
	</div>

