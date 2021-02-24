<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@ page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@ include file="/WEB-INF/jsp/Layout/menu/privilegios.jsp"%>
<div class="section"></div>
<main>
	<%
				UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);
				MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
				
				Boolean tipo = menuService.cargaMenuJudicial(usuarioTO);
				
	%>
	<s:set var="tipo"><%=tipo%></s:set>
	<div class="container-fluid">
		<div class="row">
			<div class="col s1 m2"></div>
			<div class="col s12 m8">
				<s:if test="#tipo == 'false'">
					<div class="carousel carousel-slider center">
						<div class="carousel-item center">
							<div class="card teal lighten-2" style="height: 225px !important;">
								<div class="row">
									<div class="col s12">
										<div class="card-content">
											<div class="alter-title" style="font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;">Funcionalidad nueva: Mis usuarios</div>
											<p>Opci&oacute;n para crear subcuentas asociadas a la cuenta principal.</p>
											<p class="right-align">
												<a href="<%= request.getContextPath() %>/usuario/users.do" class="waves-effect waves-teal btn-flat indigo white-text">Ir a Mis usuarios</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="carousel-item center">
							<div class="card teal lighten-2" style="height: 225px !important;">
								<div class="row">
									<div class="col s12">
										<div class="card-content">
											<div class="alter-title" style="font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;">Funcionalidad nueva: Carga masiva</div>
											<p>Opci&oacute;n para realizar tr&aacute;mites de forma masiva al sistema por medio de un archivo XML.</p>										
											<p class="right-align">
												<a href="<%= request.getContextPath() %>/masiva/inicia.do" class="waves-effect waves-teal btn-flat indigo white-text">Ir a Carga masiva</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="carousel-item center">
							<div class="card teal lighten-2" style="height: 225px !important;">
								<div class="row">
									<div class="col s12">
										<div class="card-content">
											<div class="alter-title" style="font-size: 1.5em; background-color: white; text-align: center; margin-bottom: 5px;">Funcionalidad nueva: Formularios Especiales</div>
											<p>Se han agregado dos formularios especiales nuevos, para brindar un mejor servicio, estos son: </p>										
											<p class="right-align">
												Formulario para Cesi&oacute;n en Venta o en Administraci&oacute;n de Derechos de Cr&eacute;dito <a href="<%= request.getContextPath() %>/home/factoraje.do?idTipoGarantia=3" class="waves-effect waves-teal btn-flat indigo white-text">Ir a Formulario</a>
											</p>
											<p class="right-align">
												Formulario para Compra-Venta <a href="<%= request.getContextPath() %>/home/factoraje.do?idTipoGarantia=4" class="waves-effect waves-teal btn-flat indigo white-text">Ir a Formulario</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</s:if>
			</div>
			<div class="col s1 m2"></div>
		</div>
		<div class="row">
			<div class="col s1 m2"></div>
			<div class="col s12 m8">
				<div class="card">
					<div class="card-content">
						<span class="card-title center-align">Bienvenido <s:property value="#session.User.profile.nombre" />
					        <s:property  value="#session.User.profile.apellidoPaterno" />
					        <s:property value="#session.User.profile.apellidoMaterno" />
					        <s:property value="#session.User.profile.apellidoMaterno" />
				        </span>				        
				        <div class="row">
					        <p>En el Registro de Garantias Mobiliarias usted podra realizar</p>
					        <div class="collection">
								<s:if test="#tipo == 'false'">
									<a href="<%= request.getContextPath() %>/inscripcion/paso1.do" class="collection-item">Primera inscripción</a>
									<ul class="browser-default collection-item">
										<li>Modificacion</li>
										<li>Cancelacion</li>
										<li>Ejecucion</li>
										<li>Prorroga</li>
									</ul>
									<a href="<%= request.getContextPath() %>/home/busqueda.do" class="collection-item">Consultas</a>
									<a href="<%= request.getContextPath() %>/home/misBoletas.do" class="collection-item">Cargar Boletas</a>
								</s:if>
								<s:else>
									<a href="<%= request.getContextPath() %>/home/busquedaJud.do" class="collection-item">Consultas</a>
								</s:else>
					        </ul>
				        </div>
					</div>
				</div>
			</div>
			<div class="col s1 m2"></div>
		</div>
	</div>
</main>
<div class="section"></div>
