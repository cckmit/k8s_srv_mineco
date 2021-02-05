<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/validaConexionService.js"></script>
<script type="text/javascript">

function existeConexionBD(data){
	
	if (data=="1"){						
		getObject("frmLogin").submit();
		return true;
	}else{
		//displayLoader(false);
		alert('Sucedio un error en la conexion, favor de intentarlo mas tarde');
	}
}
</script>
