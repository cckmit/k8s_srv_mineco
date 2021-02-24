
<%@page import="java.util.Iterator"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp"%>
<div class="navmenu">
	<ul>2
		<%
			MenuTO menuTO = new MenuTO(1, request.getContextPath());
			MenusServiceImpl menuService = (MenusServiceImpl) ctx
					.getBean("menusServiceImpl");
			menuTO = menuService.cargaMenuPrincipal(menuTO,
					(UsuarioTO) session.getAttribute("usuario"));
			Iterator<String> iterator = menuTO.getHtml().iterator();
			while (iterator.hasNext()) {
				String menuItem = iterator.next();
		%><li><span>|</span></li><%=menuItem%>
		<%
			}
		%>
		<li><span>|</span></li>
	</ul>
</div>