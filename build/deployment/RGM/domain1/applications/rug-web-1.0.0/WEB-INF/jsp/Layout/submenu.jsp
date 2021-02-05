<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.economia.dgi.framework.menu.dao.MenuDao"%>
<%@page import="mx.gob.se.rug.seguridad.dao.MenuDAO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.PrivilegiosServiceImpl" %>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>
<%
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(8));
PrivilegiosServiceImpl privilegios =(PrivilegiosServiceImpl)ctx.getBean("privilegiosServiceImpl");
privilegiosTO=privilegios.cargaPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();
%>

<div class="navmenu">
<ul>
<%
	UsuarioTO usuarioTO = (UsuarioTO)session.getAttribute(Constants.USUARIO);

	MenuTO menuTO= new MenuTO(1,request.getContextPath());	
	MenusServiceImpl menuService = (MenusServiceImpl)ctx.getBean("menusServiceImpl");
	menuTO = menuService.cargaMenuPrincipal(menuTO,usuarioTO);
	Iterator<String> iterator= menuTO.getHtml().iterator();
	while(iterator.hasNext()){
	String menuItem=iterator.next();
	%><li><span>|</span></li><%=menuItem%> <%
	}
%>
<li><span>|</span></li>
</ul>
</div>

	<div> <h1>Detalle de la Garantia Mobiliaria<h1></div>
	      
		                 
	<div id="menuh">
	
	<ul>
		<%
		Boolean noHayCancel= (Boolean) request.getAttribute("noHayCancel");
		Boolean noVigencia = (Boolean) request.getAttribute("vigenciaValida");
		if(noHayCancel==null ||(noHayCancel!=null && noHayCancel.booleanValue()==true)){
			Integer idAcreedorRepresentado=(Integer) session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO);
//			System.out.println("data " + session.getAttribute(Constants.ID_ACREEDOR_REPRESENTADO));
			MenuTO menuSecundarioTO = new MenuTO(2, request.getContextPath());
			menuSecundarioTO = menuService.cargaMenuSecundario(idAcreedorRepresentado,usuarioTO,menuSecundarioTO,noVigencia);
//			menuSecundarioTO = menuService.cargaMenuSecundario(51071,usuarioTO,menuSecundarioTO,noVigencia);
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
	
<script>
function cancelacion(){
	<% if (priv.get(new Integer(20))!=null ) { %>
		tb_show('Cancelaci�n de la Garant�a Mobiliaria','<%= request.getContextPath() %>/home/iniciaCancelacion.do?keepThis=true&TB_iframe=true&height=371&width=500&modal=true','');
		
		
	<% }else{%>
		window.location.href ="<%= request.getContextPath() %>/home/cancelacionTramite.do";
	<%}%>
}
function sendForm(){
	var pasa= false;
	valor= document.getElementById('autoridad').value;
	for ( i = 0; i < valor.length; i++ ) {  
	    if ( valor.charAt(i) != " " ) {
		    pasa= true;
	    }
	}
	if (pasa){
		document.fcancelacion.submit()
	}
	else{
		alert("El campo de Autoridad que instruye la Anotaci�n es obligatorio");
		displayMessageAlertCancel(false);
		changeStyle("messageAlertDiv","HEIGHT:150px; WIDTH:300px");
		displayAlert(true,"Informaci�n Incompleta","El campo de Autoridad que instruye la Anotaci�n es obligatorio");
		changeStyle("messageAlertDiv","HEIGHT:260px; WIDTH:450px");
		displayMessageAlertCancel(true);
	}
}
</script>



