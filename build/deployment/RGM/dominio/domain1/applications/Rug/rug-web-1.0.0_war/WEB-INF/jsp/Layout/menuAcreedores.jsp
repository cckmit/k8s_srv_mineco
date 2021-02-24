
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.economia.dgi.framework.menu.dao.MenuDao"%>
<%@page import="mx.gob.se.rug.seguridad.dao.MenuDAO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Iterator"%>
<div>
	<%@include file="/WEB-INF/jsp/Layout/menu/menuPrincipal.jsp" %>
	<table id="mytable" class="mytable" cellpadding="3" cellspacing="2" border="0" width="650">
		<tr>
			<td id="cargaNombre" class="tituloInteriorRojo"></td>
		</tr>
			<tr><td>&nbsp;</td></tr>
		</table>
	<div id="topmenuBusqueda">
		<ul>
			<li><a href="<%= request.getContextPath() %>/controlusuario/iniciaAltaUsuario.do"  id="unoMenuBusquedaLink"><span id="unoMenuBusqueda">Usuarios</span></a></li>
		    <li><a href="<%= request.getContextPath() %>/controlusuario/busquedaGrupo.do" id="dosMenuBusquedaLink"><span id="dosMenuBusqueda">Grupos</span></a></li>
		</ul>
	</div>
</div>