<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<ul id="account-menu" class="dropdown-content">
	<li><a href="<%= request.getContextPath() %>/administracion/perfil/edit.do">Mi cuenta</a></li>
</ul>
<nav class="blue darken-4" role="navigation">
	<div class="nav-wrapper">
		<a href="<%= request.getContextPath() %>" class="brand-logo">
			<img class="responsive-img" src="<%=request.getContextPath()%>/resources/imgs/logo2v1.jpg">
		</a>
		<ul class="hide-on-med-and-down menu">
			<%
		if (session.getAttribute(Constants.USUARIO) != null) {
		%>
			<li>
				<!-- 			<a href="#" data-activates="slide-out" class="sidenav-trigger btn-floating btn-flat waves-effect waves-light transparent"><i class="material-icons">menu</i></a> -->
				<a href="#" data-activates="slide-out"
					class="btn-menu btn-floating btn-flat waves-effect waves-light transparent"><i
						class="material-icons">menu</i></a>
			</li>
			<%
		}
		%>
			<li class="hide-on-med-and-down" style="padding-left: 20px; font-size: 1.6em;">
				Registro de Garant&iacute;as Mobiliarias 
			</li>
		</ul>
		<ul class="right">
			<%
		if (session.getAttribute(Constants.USUARIO) == null) {
		%>
			<li><a href="<%= request.getContextPath() %>/usuario/add.do"
					class="hide-on-med-and-down waves-effect red darken-4 waves-light btn">Registrarse</a></li>
			<li><a href="<%= request.getContextPath() %>/">Iniciar sesi&oacute;n</a></li>
			<%
		} else {
		%>
			<li class="hide-on-med-and-down">
				<a href="#!" class="dropdown-trigger" data-activates="account-menu" data-belowOrigin="true">
					<span class="white-text name">
						<s:property value="#session.User.profile.nombre" />
						<s:property value="#session.User.profile.apellidoPaterno" />
						<s:property value="#session.User.profile.apellidoMaterno" />
					</span><i class="material-icons right">arrow_drop_down</i>
				</a>
			</li>
			<li class="hide-on-med-and-down"><a href="<%=request.getContextPath()%>/home/logout.do"
					class="waves-effect red darken-4 waves-light btn">Salir</a></li>
			<li class="hide-on-large-only">
				<a href="<%=request.getContextPath()%>/home/logout.do"
					class="btn-floating btn-flat waves-effect waves-light transparent">
					<i class="material-icons">exit_to_app</i>
				</a>
			</li>
			<%
		}
		%>
		</ul>
	</div>
</nav>