
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>

<script type="text/javascript">
function mostrarMod(){
	document.getElementById('estadoMostrar').style.visibility='hidden'; 
	document.getElementById('periodoMostrar').style.visibility='hidden';
	document.getElementById('estadoElegir').style.visibility='visible';
	document.getElementById('MostrarDatosJob').style.visibility='hidden';
	document.getElementById('ModificarJobTable').style.visibility='visible';
}

function inicialMod(){
	document.getElementById('estadoMostrar').style.visibility='visible'; 
	document.getElementById('periodoMostrar').style.visibility='visible';
	document.getElementById('estadoElegir').style.visibility='hidden';
	document.getElementById('MostrarDatosJob').style.visibility='visible';
	document.getElementById('ModificarJobTable').style.visibility='hidden';
	
}

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

<s:form action="jobAvisoP.do" id="frmjobAviso" method="post" name="frmjobAviso">
	<table id="MostrarDatosJob">
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
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="descripcion"/></div></td>
			<td class="cuerpo1TablaResumen"><div id="estadoMostrar" align="center"><s:property value="estado"/></div></td>
			<td class="cuerpo1TablaResumen"><div id="periodoMostrar" align="center"><s:property value="periodicidad"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="ultEjecucion"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"><s:property value="proxEjecucion"/></div></td>
			<td class="cuerpo1TablaResumen"><div align="center"></div><input type="button" value="Modificar" class="boton_rug" onclick="window.location.href=' <%=request.getContextPath()%>/usuario/jobBuscaMod.do?idJob=<s:property value="idJob"/>'"/> <div align="center"></div><input type='button' name='Ejecutar' 
					value='Ejecutar' onclick="window.location.href=' <%=request.getContextPath()%>/usuario/jobEjecuta.do?idJob=<s:property value="idJob"/>'" class="boton_rug"/></td>  
			</tr>
			</s:iterator>
			
			</s:if>
			</table><br></br>
		<tr> 
			<td> 
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
</body>
