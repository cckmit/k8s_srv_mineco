<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.economia.dgi.framework.menu.dao.MenuDao"%>
<%@page import="mx.gob.se.rug.seguridad.dao.MenuDAO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Iterator"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>
<div id="menuh">
	<hr>
	<ul>
		<%
			MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
			MenusServiceImpl menuService = (MenusServiceImpl) ctx
					.getBean("menusServiceImpl");

			menuSecundarioTO = menuService.cargaMenuSecundario(
					(Integer) session
							.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO),
					(UsuarioTO) session.getAttribute("usuario"),
					menuSecundarioTO);
			Iterator<String> iterator2 = menuSecundarioTO.getHtml().iterator();
			while (iterator2.hasNext()) {
				String menuItem = iterator2.next();
		%><%=menuItem%>
		<%
			}
		%>
	</ul>
	<hr>
</div>
