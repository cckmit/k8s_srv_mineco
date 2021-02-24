<%@page import="org.springframework.context.ApplicationContext"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>

<%
	ApplicationContext ctx = null;
	if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(Constants.SEGURIDAD_APP_CONTEXT);
	}
%>