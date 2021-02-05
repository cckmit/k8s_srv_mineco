<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>

<script type="text/javascript">


function IsNumber(evt) {
	 var key = (document.all) ? evt.keyCode : evt.which;
	 if (key == 13)
	 {  
	  validaVigencia();
	 return false;
	 }
	 return (key == 45 || key <= 13 || (key >= 48 && key <= 57));
}

function validaPeriodo (){
	if (!noVacio(getObject('periodicidad').value)){
		   alert('El campo Periodicidad (en min.) es obligatorio');
		   return false;
	}
}


function validaEnvio(){
	if (!noVacio(getObject('periodicidad').value)){
		   alert('El campo Periodicidad (en min.) es obligatorio');
		   return false;
	}else{
		
		sendForm();
	}
	
		
}

		
function sendForm(){
	document.getElementById("btnAceptar").value = "Enviando..";
	document.getElementById("btnAceptar").disabled = true;
	getObject('formModJob').submit();
}		
		
		 
 </script>

	<a href="<%=request.getContextPath()%>/usuario/addExtranjero.do">
	<input type='button' name='AddExtranjero' value='Registro de Usuario' class="boton_rug_largo"></a>
	
	<a href="<%=request.getContextPath()%>/usuario/jobAvisoP.do">
	<input type="button" name="JobAnotacion" value='Trabajos' class="boton_rug_largo"></a>

<table>
	<tr>
		<td width="740" height="25" style="padding-left: 5px;">
			<h1><s:text name="Modificaci&oacuten de Trabajos" /></h1>
			
			
		</td>
	</tr>
</table><br></br>

<s:form action="jobModifica.do" id="formModJob" method="post" name="formModJob">
	<table id="ModificarJobTable">
			<tr>
			<%if(request.getParameter("error")!=null){%> 
     			<label  style="float: center; font-size: 9px; color: red;" > Usuario o Contrase&ntilde;a incorrecto(s) </label>
     			<%}%>
			

			    
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Nombre del Job</td>
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Estado</td>
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Periodicidad (en min.)</td>
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Ultima Ejecucion</td>
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Proxima Ejecucion</td>
				<td width="15%" class="encabezadoTablaResumen" style="text-align: center">Opciones</td>
			</tr>		
			<s:if test="listaJobAvisoPreventivo.size >0">			
			<s:iterator value="listaJobAvisoPreventivo">
			<tr>
			<input type="hidden" name="idJob" value="<s:property value ="idJob"/>" />
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="descripcion"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><table><s:select cssClass="campo_general"  id="estado" name="estado" list="jobAviso.listaEstado" disabled="label" /></table></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><table><s:textfield  id="periodicidad"  name="periodicidad" required="false" onkeypress="return IsNumber(event);" onchange="validaPeriodo();"  maxlength="10" size="10" /></table></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="ultEjecucion"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="proxEjecucion"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><input type="button" value="Aceptar" id="btnAceptar" alt="Aceptar" onclick="validaEnvio();" class="boton_rug"/>
			<input type='button' name='Cancelar' value='Cancelar' onclick="window.location.href=' <%=request.getContextPath()%>/usuario/jobAvisoP.do'" class="boton_rug"/>
			</div></td>
			</tr>
			</s:iterator>
			 
			</s:if>
			</table><br></br>
		<tr> 
			<td> 
				<table>
					<tr>
	 					<td width="352px;" class="textoGris textoJustificado" align="justify">
	 					Nota: Al dar click en "Ejecutar" El Job de Aviso Preventivo sera ejecutado.</td>
					</tr> 
				</table>
			</td>
		</tr>
</s:form>		