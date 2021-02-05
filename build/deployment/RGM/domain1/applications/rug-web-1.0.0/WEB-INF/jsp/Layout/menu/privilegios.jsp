
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Iterator"%>
<%@page
	import="mx.gob.se.rug.seguridad.serviceimpl.PrivilegiosServiceImpl"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>

<%
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
PrivilegiosServiceImpl privilegios =(PrivilegiosServiceImpl)ctx.getBean("privilegiosServiceImpl");
privilegiosTO=privilegios.cargaPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>


