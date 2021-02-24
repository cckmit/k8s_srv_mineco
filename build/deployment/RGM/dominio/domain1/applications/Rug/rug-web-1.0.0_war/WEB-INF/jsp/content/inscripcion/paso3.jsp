<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<main>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="col s1"></div>
					<div class="col s10">	
						<span class="card-title">Inscripci&oacute;n Garant&iacute;a Mobiliaria</span>
						<input type="hidden" name="refInscripcion" id="refInscripcion" value="<s:property value='idInscripcion'/>" />												
					    <div class="row note">
							<span>Verifique que la informaci&oacute;n ingresada este correcta.
							</span>
						</div>
						<div class="row note teal">
							<span class="white-text">Sujetos de la Garant&iacute;a Mobiliaria</span>
						</div>											    
					    <s:if test="hayDeudores"> 
						    <div class="row">
						    	<div class="input-field col s12">
						    		<table id="mytable" class="bordered striped centered responsive-table" >
										<thead>	
											<tr>
												<th colspan="2">Deudores Garantes</th>
											</tr>
											<tr>
												<th>Nombre, Denominaci&oacute;n o Raz&oacute;n Social</th>
												<th>No. Identificaci&oacute;n \ No. Identificaci&oacute;n Tributaria</th>														
											</tr>
										</thead>
										<tbody>
											<s:iterator value="listaDeudores">
												<tr>
													<td><s:property value="nombreCompleto"/></td>
													<s:if test="tipoPersona.equals('PM')">
														<td><s:property value="rfc"/></td>
													</s:if>
													<s:else>
														<td><s:property value="curp"/></td>
													</s:else>																	
												</tr>
											</s:iterator>
										</tbody>
									</table>
						    	</div>
						    </div>
						</s:if>
						<s:if test="hayAcreedores"> 
						    <div class="row">
						    	<div class="input-field col s12">
						    		<table id="mytable" class="bordered striped centered responsive-table" >
										<thead>	
											<tr>
												<th colspan="2">Acreedores Garantizados</th>
											</tr>
											<tr>
												<th>Nombre, Denominaci&oacute;n o Raz&oacute;n Social</th>
												<th>No. Identificaci&oacute;n \ No. Identificaci&oacute;n Tributaria</th>			
											</tr>
										</thead>
										<tbody>
											<s:iterator value="listaAcreedores">
												<tr>
													<td><s:property value="nombreCompleto"/></td>
													<s:if test="tipoPersona.equals('PM')">
														<td><s:property value="rfc"/></td>
													</s:if>
													<s:else>
														<td><s:property value="curp"/></td>
													</s:else>				
												</tr>
											</s:iterator>
										</tbody>
									</table>
						    	</div>
						    </div>
						</s:if>
						<div class="row note teal">
							<span class="white-text">Informaci&oacute;n de la Garant&iacute;a Mobiliaria</span>
						</div>						
					    <div class="row">
					    	<div class="input-field col s12">
					    		<span class="blue-text text-darken-2">Descripci&oacute;n de los Bienes Muebles objeto de la Garant&iacute;a Mobiliaria:</span>
					    		<p><s:property value="actoContratoTO.descripcion"/></p>
					    	</div>
					    </div>
					    <s:if test="hayBienes"> 
						    <div class="row">
						    	<div class="input-field col s12">
									<span class="blue-text text-darken-2">Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>
						    		<table id="mytable" class="bordered striped centered responsive-table" >
										<thead>												
											<tr>
												<th>Tipo Bien Especial</th>
												<th>Tipo Identificador</th>
												<th>Identificador</th>
												<th>Descripci&oacute;n</th>														
											</tr>
										</thead>
										<tbody>
											<s:iterator value="listaBienes">
												<tr>
													<td><s:property value="tipoBien"/></td>
													<td><s:property value="tipoIdentificador"/></td>
													<td><s:property value="identificador"/></td>	
													<td><s:property value="descripcion"/></td>					
												</tr>
											</s:iterator>
										</tbody>
									</table>
						    	</div>
						    </div>
						</s:if>   
						<div class="row note teal">
							<span class="white-text">Informaci&oacute;n Espec&iacute;fica de la Garant&iacute;a Mobiliaria</span>
						</div>		
						 <s:if test="actoContratoTO.noGarantiaPreviaOt">
						    <div class="row">
						    	<div class="input-field col s12">
						    		<span class="blue-text text-darken-2">Declaro que de conformidad con el contrato de garant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen otro gravamen, anotaci&oacute;n o limitaci&oacute;n previa.</span>						    		
						    	</div>
						    </div>
						</s:if>
						<s:if test="actoContratoTO.cambiosBienesMonto">
						    <div class="row">
						    	<div class="input-field col s12">
						    		<span class="blue-text text-darken-2">Los atribuibles y derivados no esta afectos a la Garant&iacute;a Mobiliaria.</span>						    		
						    	</div>
						    </div>
						</s:if>
						<s:if test="actoContratoTO.garantiaPrioritaria">
						    <div class="row">
						    	<div class="input-field col s12">
						    		<span class="blue-text text-darken-2">La garant&iacute;a es prioritaria.</span>						    		
						    	</div>
						    </div>
						</s:if>
						<s:if test="actoContratoTO.cpRegistro">
						    <div class="row">
						    	<div class="input-field col s12">
						    		<span class="blue-text text-darken-2">El bien se encuentra inscrita en el registro <s:property value="actoContratoTO.txtRegistro"/>.</span>						    		
						    	</div>
						    </div>
						</s:if>					    					   
						<s:if test="insPublico">
						    <div class="row">
						    	<div class="input-field col s12">
						    		<span class="blue-text text-darken-2">Informaci&oacute;n General del Contrato de la Garant&iacute;a:</span>
						    		<p><s:property value="actoContratoTO.instrumentoPub"/></p>						    		
						    	</div>
						    </div>
						</s:if>
						<div class="row">
					    	<div class="input-field col s12">
					    		<span class="blue-text text-darken-2">Datos del Representante(s):</span>
					    		<p><s:property value="obligacionTO.otrosTerminos"/></p>						    		
					    	</div>
					    </div>
						<div class="row">
					    	<div class="input-field col s12">
					    		<span class="blue-text text-darken-2">Observaciones adicionales de la Garant&iacute;a: </span>
					    		<p><s:property value="actoContratoTO.otrosTerminos"/></p>						    		
					    	</div>
					    </div>					    					    					    					    
					    <div class="row">					    	
					    	<s:form action="actualizaVigencia.do" namespace="inscripcion" theme="simple" name="formAcVig" id="formAcVig">
								<div class="row note teal">
									<span class="white-text">Vigencia de la Inscripci&oacute;n en el sistema</span>
								</div>	
					    		<div class="row">
						    		<div class="input-field col s6">										
										<s:textfield name="inscripcionTO.meses" onkeypress="return IsNumber(event);" size="5" maxlength="4"/>
										<label for="inscripcionTO.meses">Indique por cu&aacute;ntos a&ntilde;os desea que la Garant&iacute;a Mobiliaria quede inscrita en el sistema</label>
										<INPUT type="hidden" name="mdSaldo" value="<s:property value="saldo"/>" id="mdSaldo">
									</div>		
									<div class="row note note-example col s6">									
										<span>El sistema autom&aacute;ticamente le dar&aacute; una vigencia a la Inscripci&oacute;n de cinco a&ntilde;o, a menos de que usted establezca otra.</span>
									</div>
								</div>								
								<center>
									<div class="row">
										<input type="button" class="btn btn-large waves-effect indigo" value="Regresar" id="buttonID" onclick="paso2()"/>&nbsp;
										<input type="button" class="btn btn-large waves-effect indigo" onclick="msg_guardar()" value="Guardar" id="guardarID" />
										<input type="button" class="btn btn-large waves-effect indigo" onclick = "validaMesesPaso3()" value="Aceptar" id="baceptar" />
									</div>
								</center>
					    	</s:form>
					    </div>
					</div>
					<div class="col s1"></div>
				</div>
			</div>
		</div>
	</div>
</main>