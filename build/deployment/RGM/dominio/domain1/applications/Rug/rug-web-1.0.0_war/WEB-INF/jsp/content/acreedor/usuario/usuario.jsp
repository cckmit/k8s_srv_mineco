<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"	src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"	src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"	src="${pageContext.servletContext.contextPath}/dwr/interface/BusquedaDwrAction.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/dwr/usuariosDWR.js"></script>

<s:form name="usuarioAdd" namespace="acreedor" method="Post" theme="simple" >

<table width="100%">
<tr>
<td align="left">Acreedor: <s:property value="acreedorNombre" /></td>
</tr>
<tr>
<td align="right">Usuario:<s:property value="nombreUsario" /> </td>
</tr>
</table>
<s:textfield label="Correo Electronico" name="email" ></s:textfield>


</s:form>
