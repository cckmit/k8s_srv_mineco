<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/BoletaDwrAction.js"></script>

<script type="text/javascript">
function resultadoBoleta(idBoleta, resultado) {
	BoletaDwrAction.actualizaEstadoBoleta(idBoleta, resultado, updateBoletas);
}
function updateBoletas(response) {
	console.log(response);
}
</script>
