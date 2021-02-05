<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="idPersona" value="idPersona"/>

<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s2"></div>
			<div class="col s8">
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s8">
								<span class="card-title">Mis Usuarios</span>
							</div>
							<div class="col s4 right-align">
								<a class="btn btn-large waves-effect indigo modal-trigger" href="#frmUsuario" id="btnAgregar"><i class="material-icons left">add</i>Agregar</a>
							</div>
						</div>
						<div class="row">
							<table id="usuarios">
								<thead>
									<tr>
										<th>Nombre</th>
										<th>Correo</th>
										<th>Opciones</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="usuarios">
									<tr id='<s:property value="idPersona"/>'>
										<td><s:property value="nombre"/></td>
										<td><s:property value="cveUsuario"/></td>
										<td>
											<a href="#" class="btn waves-effect indigo" onclick='cargaUsuario(<s:property value="idPersona"/>)'><i class="material-icons">edit</i></a>
											<a href="#" class="btn waves-effect red darken-4" onclick='eliminarUsuario(<s:property value="idPersona"/>)'><i class="material-icons">delete</i></a>
										</td>
									</tr>
								</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col s2"></div>
		</div>
	</div>
</main>
<div class="section"></div>
<div id="frmUsuario" class="modal">
	<div class="modal-content">
		<div class="card">
			<div class="card-content">
				<span class="card-title">Subusuario</span>
				<div class="row">
					<div class="col s1"></div>
					<!--s:form namespace="usuario" action="save.do" theme="simple" cssClass="col s10" method="post"-->
					<input type="hidden" name="usuarioModificar" id="usuarioModificar" />
					<div class="col s10">
						<div class="row">
							<div class="input-field col s12">
								<s:textfield name="usuario.nombre" id="nombre" required="true" cssClass="validate" />
								<label for="nombre">Nombre completo</label>
							</div>
						</div>
					 	<div class="row">
					    	<div class="input-field col s12">
					        	<s:textfield name="usuario.docId" id="docId" required="true" cssClass="validate" maxlength="13" size="40" onkeyup="this.value = this.value.toUpperCase()" onkeypress="return aceptaalfa(event);" />
					        	<label for="docId">Documento de identificación</label>
					   		</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12">
					        	<s:textfield name="usuario.cveUsuario" id="email" type="email" required="true" cssClass="validate" maxlength="256" size="40" onkeyup="this.value = this.value.toLowerCase()" />
					        	<label for="email">Correo electrónico</label>
					   		</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12">
					    		<s:text name="usuario.field.password.ayuda" var="password.ayuda" />
					        	<s:password name="usuario.password" id="password" required="true" cssClass="validate tooltipped" data-position="right" data-delay="50" data-tooltip="%{password.ayuda}" maxlength="16" size="40" />
					        	<label for="password"><s:text name="usuario.field.password" /></label>
				        	</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12">
					        	<s:password name="usuario.confirmacion" id="confirmacion" required="true" cssClass="validate" maxlength="16" size="40" />
					        	<label for="confirmacion"><s:text name="usuario.field.confirmacion" /></label>
					   		</div>
					   		<s:fielderror fieldName="registroUsuario.confirmacion" />
					 	</div>
					 	<!--div class="row">
					    	<div class="input-field col s12">
    							<s:select name="usuario.pregRecupera" id="pregunta" list="preguntas" listKey="pregunta" listValue="pregunta" onchange="onChangePregunta()" />
					        	<label for="pregunta"><s:text name="usuario.field.pregunta" /></label>
					   		</div>
					 	</div>
					 	<div class="row" id="otra-pregunta" style="display: none;">
					    	<div class="input-field col s12">
					        	<s:textfield name="otraPregunta" id="otraPregunta" cssClass="validate" maxlength="50" size="40" />
					        	<label for="otraPregunta">Ingrese la pregunta</label>
					   		</div>
					 	</div>
					 	<div class="row">
					    	<div class="input-field col s12">
					        	<s:textfield name="usuario.respRecupera" id="respuesta" required="true" cssClass="validate" maxlength="50" size="40" />
					        	<label for="respuesta"><s:text name="usuario.field.respuesta" /></label>
					   		</div>
					   		<s:fielderror fieldName="registroUsuario.respuesta" />
					 	</div-->
					 	<div class="row note">
							<p>Para la administraci&oacute;n de las subcuentas, usted:
								<br />1. Ratifica que los t&eacute;rminos y condiciones aceptados al crear su cuenta maestra aplican para cada subcuenta creada desde dicha cuenta;
								<br />2. Acepta que las operaciones realizadas por el usuario de la subcuenta estarán bajo su completa responsabilidad;
								<br />3. La creaci&oacute;n, modificaci&oacute;n e inactivaci&oacute;n de las subcuentas asociadas a su cuenta solo podr&aacute; ser realizada con la cuenta maestra.
							</p>
						</div>
					 	<div class="row center-align" id="error">
					 	</div>
					 	<center>
				            <div class='row'>
				            	<a href="#" class="btn btn-large waves-effect indigo" onclick="guardaUsuario(<s:property value='idPersona' />)">Aceptar</a>
				            </div>
			          	</center>
		          	</div>
					<!--/s:form-->
					<div class="col s1"></div>
				</div>
			</div>
		</div>
	</div>
</div>