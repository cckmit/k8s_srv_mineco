
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

</script>
<body>

	<a href="<%=request.getContextPath()%>/usuario/addExtranjero.do">
	<input type='button' name='AddExtranjero' value='Registro de Usuario' class="boton_rug_largo"></a>
	
	<a href="<%=request.getContextPath()%>/usuario/jobAvisoP.do">
	<input type="button" name="JobAnotacion" value='Trabajos' class="boton_rug_largo"></a>

<table>
	<tr>
		<td width="740" height="25" style="padding-left: 5px;">
			<h1><s:text name="Administrador de Trabajos" /></h1>
			
			
		</td>
	</tr>
</table><br></br>

<s:form action="" id="frmjobAviso" method="post" name="frmjobAviso">
	<table width="740" height="25" id="OperacionExitosa">
					<tr>
					    
	 					<td width="740" class="infoBase" align="center">
	 					Operaci&oacuten Exitosa</td>
	 					<td>
	 					
	 					</td>
					</tr> 
				 <table >
			</td>
		</tr>
</s:form>		
</body>