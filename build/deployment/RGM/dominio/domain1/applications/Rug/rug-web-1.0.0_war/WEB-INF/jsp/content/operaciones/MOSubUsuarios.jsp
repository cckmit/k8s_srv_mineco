<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- Abraham Stalin Aguilar Valencia - RUG  -->
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/OperacionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/operacionesJS.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.ayuda.js"></script>
<table width="960px">
	<tr> <td id="idSubUsuarios">  </td> </tr>
	<tr> <td id="idMisOperaciones"> </td> </tr>
</table>

<script type="text/javascript">

var idPersona= <s:property value="idPersona"/>;
var idAcreedor= <s:property value="idAcreedorTO"/>;
var ruta = '<%=request.getContextPath()%>';

function inicializaPagina(){
	getParteUsuarios('idSubUsuarios', idAcreedor, ruta, idPersona);
} 
inicializaPagina();
</script>
