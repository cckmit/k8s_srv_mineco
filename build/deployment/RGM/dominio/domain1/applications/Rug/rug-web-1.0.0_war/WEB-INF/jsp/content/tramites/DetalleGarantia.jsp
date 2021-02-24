<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@include file="/WEB-INF/jsp/Layout/menu/privilegios.jsp"%>
<main>
<div class="container-fluid">
	<div class="row">
			<div id="menuh">
			<ul>
				<%
				UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
				MenuTO menuTO= new MenuTO(1,request.getContextPath());	
				MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
				
				Boolean noHayCancel= (Boolean) request.getAttribute("noHayCancel");
				Boolean noVigencia = (Boolean) request.getAttribute("vigenciaValida");
				if(noHayCancel==null ||(noHayCancel!=null && noHayCancel.booleanValue()==true)){
					Integer idAcreedorRepresentado=(Integer) session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO);
					MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
					menuSecundarioTO = menuService.cargaMenuSecundario(idAcreedorRepresentado,usuarioTO,menuSecundarioTO,noVigencia);
					Iterator<String> iterator2 = menuSecundarioTO.getHtml().iterator();
					while (iterator2.hasNext()) {
						String menuItem = iterator2.next();
				%><%=menuItem%>
				<%
					}
				}
					
				%>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col s12"><div class="card">
		<div class="col s2"></div>
		<div class="col s8">
			<form action="inicia.do" id="frmDetalle" method="post"
				name="frmDetalle">
				<span class="card-title"><a
					href="<%=request.getContextPath()%>/home/detalle.do?idGarantia=<s:property value="idGarantia"/>&idTramite=<s:property value="idTramite"/> ">Detalle
						de la Garantía Mobiliaria</a> </span>
				<div align="right" style="width: 745px">
					<s:if test="hayAnterior">
						<img
							src="<%=request.getContextPath()%>/resources/imgs/flecha_anterior.gif"
							height="30" width="30" onclick="anterior();"
							style="cursor: pointer" />
					</s:if>
					<s:else>
						<img
							src="<%=request.getContextPath()%>/resources/imgs/flecha_anteriorD.gif"
							height="30" width="30" />
					</s:else>
					<s:if test="haySiguiente">
						<img
							src="<%=request.getContextPath()%>/resources/imgs/flecha_siguiente.gif"
							height="30" width="30" onclick="siguiente();"
							style="cursor: pointer" />
					</s:if>
					<s:else>
						<img
							src="<%=request.getContextPath()%>/resources/imgs/flecha_siguienteD.gif"
							height="30" width="30" />
					</s:else>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2">Garant&iacute;a
							Mobiliaria No. </span> <span> <s:property value="idGarantia" />
						</span>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s6">
						<span class="blue-text text-darken-2">Fecha de
							Inscripci&oacute;n </span> <span> <s:property
								value="fechaInscripcion" />
						</span>
					</div>
					<div class="input-field col s6">
						<span class="blue-text text-darken-2">Fecha de la
							&uacute;ltima actualizaci&oacute;n </span> <span> <s:property
								value="fechaUltAsiento" />
						</span>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s6">
						<span class="blue-text text-darken-2">Tipo de Operaci&oacute;n </span> <span>
							<s:property value="datosGaranTO.tipoAsiento" />
						</span>
					</div>
					<div class="input-field col s6">
						<span class="blue-text text-darken-2">Fecha </span> <span>
							<s:property value="fechaAsiento" />
						</span>
					</div>
				</div>
				<div class="row note teal">
					<span class="white-text">Datos de la Inscripci&oacute;n</span>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2">Vigencia: </span> <span>
							<s:property value="DetalleTO.vigencia" /> A&ntilde;os
						</span>
						<s:if test="!vigenciaValida">
							<span style="color:red;">  La vigencia en el sistema se ha vencido.</span>
						</s:if>
					</div>
				</div>
				<div class="row">
					<table id="mytable"
						class="bordered striped centered responsive-table">
						<thead>
							<tr>
								<th colspan="2"><s:property value="%{textosFormulario.get(1)}"/></th>
							</tr>
							<tr>
								<th>Nombre, Denominaci&oacute;n o Raz&oacute;n Social</th>
								<th>No. Identificaci&oacute;n \ No. Identificaci&oacute;n Tributaria</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="deudorTOs">
								<tr>
									<td><s:property value="nombre" /></td>
									<s:if test="perjuridica.equals('PM')">
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
				<s:if test="hayAcreedores">
					<div class="row">
						<div class="input-field col s12">
							<table id="mytable"
								class="bordered striped centered responsive-table">
								<thead>
									<tr>
										<th colspan="2"><s:property value="%{textosFormulario.get(2)}"/></th>
									</tr>
									<tr>
										<th>Nombre, Denominaci&oacute;n o Raz&oacute;n Social</th>
										<th>No. Identificaci&oacute;n \ No. Identificaci&oacute;n Tributaria</th>										
									</tr>
								</thead>
								<tbody>
									<s:iterator value="acreedorTOs">
										<tr>
											<td><s:property value="nombre" /></td>
											<s:if test="perjuridica.equals('PM')">
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
				<s:if test="hayOtorgantes">
					<div class="row">
						<div class="input-field col s12">
							<table id="mytable"
								class="bordered striped centered responsive-table">
								<thead>
									<tr>
										<th colspan="2"><s:property value="%{textosFormulario.get(3)}"/></th>
									</tr>
									<tr>
										<th>Nombre, Denominaci&oacute;n o Raz&oacute;n Social</th>
										<th>No. Identificaci&oacute;n \ No. Identificaci&oacute;n Tributaria</th>										
									</tr>
								</thead>
								<tbody>
									<s:iterator value="otorganteTOs">
										<tr>
											<td><s:property value="nombre" /></td>
											<s:if test="perjuridica.equals('PM')">
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
						<span class="blue-text text-darken-2"><s:property value="%{textosFormulario.get(4)}"/></span>
						<p>
							<s:property value="detalleTO.descbienes" />
						</p>
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
									<s:iterator value="bienesEspTOs">
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
				<s:if test="aBooleanNoGaraOt">
					<div class="row">
						<div class="input-field col s12">
							<span class="blue-text text-darken-2">Declaro que de conformidad con el contrato de garant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen otro gravamen, anotaci&oacute;n o limitaci&oacute;n previa.</span>
						</div>
					</div>
				</s:if>
				<s:if test="aBoolean">
					<div class="row">
						<div class="input-field col s12">
							<span class="blue-text text-darken-2">Los atribuibles y derivados no esta afectos a la Garant&iacute;a Mobiliaria.</span>
						</div>
					</div>
				</s:if>
				<s:if test="aPrioridad">
					<div class="row">
						<div class="input-field col s12">
							<span class="blue-text text-darken-2">La garant&iacute;a es prioritaria.</span>
						</div>
					</div>
				</s:if>
				<s:if test="aRegistro">
					<div class="row">
						<div class="input-field col s12">
							<span class="blue-text text-darken-2">El bien se encuentra inscrita en el registro <s:property value="detalleTO.txtRegistro"/>.</span>
						</div>
					</div>
				</s:if>							
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2"><s:property value="%{textosFormulario.get(7)}"/></span>
						<p>
							<s:property value="detalleTO.instrumento" />
						</p>
					</div>
				</div>
				<div class="row">
			    	<div class="input-field col s12">
			    		<span class="blue-text text-darken-2">Datos del Representante(s):</span>
			    		<p><s:property value="detalleTO.otroscontrato"/></p>						    		
			    	</div>
			    </div>
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2"><s:property value="%{textosFormulario.get(8)}"/></span>
						<p>
							<s:property value="detalleTO.otrosgarantia" />
						</p>
					</div>
				</div>					
				<div class="row">
					<div class="input-field col s12">
						<span class="blue-text text-darken-2">Anotaci&oacute;n de la operaci&oacute;n</span>
						<p>
							<s:property value="detalleTO.otrosterminos" />
						</p>
					</div>
				</div>					
				<div class="row">
					<span class="card-title"><s:text
							name="detalle.garantia.tramite.titulo.tramites" /></span>
					<table id="mytabledaO"
						class="centered striped bordered responsive-table">
						<thead>
							<tr>
								<th><s:text
										name="detalle.garantia.tramite.etiqueta.id.garantia" /></th>
								<th><s:text
										name="detalle.garantia.tramite.etiqueta.id.tramite" /></th>
								<th><s:text
										name="detalle.garantia.tramite.etiqueta.tramite" /></th>
								<th><s:text
										name="detalle.garantia.tramite.etiqueta.fecha.modificacion" /></th>
								<th><s:text 
										name="detalle.garantia.tramite.etiqueta.nombre.persona" /></th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="tramiteTOs">
								<tr>
									<td><s:url var="uri" action="detalle.do" encode="true">
											<s:param name="idGarantia" value="idGarantia" />
											<s:param name="idTramite" value="idTramite" />
										</s:url> <s:a href="%{uri}">
											<s:property value="idGarantia" />
										</s:a></td>
									<td><s:property value="idTramite" /></td>
									<td><s:property value="descripcionTramite" /></td>
									<td><s:property value="fechaModificacion" /></td>
									<td><s:property value="nombrePersona" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<div class="col s2"></div>
		</div></div>
	</div>
</div>
</main>

