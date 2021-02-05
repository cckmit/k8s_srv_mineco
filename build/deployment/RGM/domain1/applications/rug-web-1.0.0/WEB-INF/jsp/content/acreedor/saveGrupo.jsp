<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>


<SCRIPT type="text/javascript">
	function cambiaTipoPersona(){
		valor = document.getElementById('acreedorTO.tipoPersona').value;
	
		if (valor == 'PM'){
			document.getElementById('curpotro').innerHTML = '<span class="textoGeneralRojo"> RFC :</span>';
			document.getElementById('personaMoralCamposA').style.visibility = 'visible';
			document.getElementById('personaMoralCamposA').style.display = 'block';
			document.getElementById('personaFisicaCamposA').style.visibility = 'hidden';
			document.getElementById('personaFisicaCamposA').style.display = 'none';
			document.getElementById('rfcID').style.visibility = 'visible';
			document.getElementById('rfcID').style.display = 'block';
			document.getElementById('curpID').style.visibility = 'hidden';
			document.getElementById('curpID').style.display = 'none';
			
			
		}else{
			
			document.getElementById('curpID').style.visibility = 'visible';
			document.getElementById('curpID').style.display = 'block';
			document.getElementById('rfcID').style.visibility = 'hidden';
			document.getElementById('rfcID').style.display = 'none';
			
			document.getElementById('curpotro').innerHTML = '<span class="textoGeneralRojo"> CURP :</span>';
			document.getElementById('personaFisicaCamposA').style.visibility = 'visible';
			document.getElementById('personaFisicaCamposA').style.display = 'block';
			document.getElementById('personaMoralCamposA').style.visibility = 'hidden';
			document.getElementById('personaMoralCamposA').style.display = 'none';
			
			
		}
		
	}

	function agregarNuevo(){
		
		document.getElementById('agreNuevo').style.visibility = 'visible';
		document.getElementById('agreNuevo').style.display = 'block';
	}

	function cambiaNacionalidad(){
		var valor = document.getElementById('idNacionalidad').value;
		if (valor == '1'){
			document.getElementById('curpID').style.visibility = 'visible';
			document.getElementById('curpID').style.display = 'block';
			document.getElementById('rfcID').style.visibility = 'hidden';
			document.getElementById('rfcID').style.display = 'none';
			document.getElementById('curpotro').innerHTML = '<span class="textoGeneralRojo"> CURP :</span>';
			document.getElementById('appMat').innerHTML = '<span class="textoGeneralRojo"> Apellido Materno :</span>';
			
			
		}else{
			document.getElementById('curpID').style.visibility = 'visible';
			document.getElementById('curpID').style.display = 'block';
			document.getElementById('rfcID').style.visibility = 'hidden';
			document.getElementById('rfcID').style.display = 'none';
			document.getElementById('curpotro').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el país :</span>';
			document.getElementById('appMat').innerHTML = '<span class="textoGeneralRojo"> Apellido Materno :</span>';
		}
		cambiaTipoPersona();
	}

	function cambiaNacionalidadMoral(){
		var valor = document.getElementById('idNacionalidadMoral').value;
		if (valor == '1'){
			
			document.getElementById('rfcID').style.visibility = 'visible';
			document.getElementById('rfcID').style.display = 'block';
			document.getElementById('curpID').style.visibility = 'hidden';
			document.getElementById('curpID').style.display = 'none';
			
			
		}else{
			document.getElementById('curpID').style.visibility = 'visible';
			document.getElementById('curpID').style.display = 'block';
			document.getElementById('rfcID').style.visibility = 'hidden';
			document.getElementById('rfcID').style.display = 'none';
			document.getElementById('curpotro').innerHTML = '<span class="textoGeneralRojo"> Documento que acredita su legal estancia en el país :</span>';
		}
	}

	function eliminarAcreedorRe(value){
		if (confirm("Esta seguro que desea eliminar ?")){
			location.href = "<%=request.getContextPath() %>/acreedor/bajaAcreedor.do?idAcreedorBaja="+value;
		}
		 
	}

</SCRIPT>

<table>
	<tr>
		<td class="titulo_exterior_rojo">
			
		</td>
	</tr>

<td>
<tr>

<h2><s:text name="Registro de Grupos de perfiles" /></h2>
<br><br><br>

<s:if test="hasFieldErrors()">
	<p class="errorMessage">
		<s:text name="usuario.msg.activacion.error" />
		<br><br>
	</p>
	<s:fielderror />    
</s:if>
<s:elseif test="hasActionErrors()">
	<s:actionerror />
</s:elseif>
<s:else>
	<s:actionmessage />

<table class="formulario" width="800px">
<tr>
	<td class="texto_general" width="63%" valign="bottom">
		<br><br>
		<!--s:text name="acreedores.usuario.estimado.titulo" />: <s:property value="#session.User.profile.nombre"/> <s:property value="#session.User.profile.apellidoPaterno"/> <s:property value="#session.User.profile.apellidoMaterno"/>
		<s:text name="acreedores.usuario.inscrito.titulo" /><b> <s:property value="personaFisica.nombre" /> <s:property value="personaFisica.apellidoPaterno" /> <s:property value="personaFisica.apellidoMaterno" /></b--> 
	</td>
	<td align="right">
		<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg"/>
	</td>
</tr>
<tr>
	<!--td class="texto_general">
		<s:text name="acreedores.usuario.registrado.titulo" />:<br>
		<b><s:property value="personaFisica.datosContacto.emailPersonal" /></b>
	</td-->
	<td></td>
</tr>
<tr>
	<td class="etiqueta" >
		<br><br>
		<h3>
			<!--s:text name="acreedores.usuario.revise.titulo" /-->
		</h3>
	</td>
	<td></td>
</tr>
</table>
	
</s:else>



<br><br>
<a href="<%= request.getContextPath() %>/acreedor/addUsuario.do?personaFisica.nombre=<s:property value="personaFisica.nombre"/>&personaFisica.apellidoPaterno=<s:property value="personaFisica.apellidoPaterno"/>&personaFisica.apellidoMaterno=<s:property value="personaFisica.apellidoMaterno"/>&personaFisica.datosContacto.emailPersonal=<s:property value="personaFisica.datosContacto.emailPersonal"/>&personaFisica.perfil=<s:property value="personaFisica.perfil"/>&personaFisica.grupo.id=<s:property value="personaFisica.grupo.id"/>" class="enlace"><s:text name="acreedores.grupo.enlace" /></a>
		


			
	</tr>
</td>

</table>




<script>
//setActiveTab('dosMenu');
$("#dosMenu").attr("class","linkSelected");
cargaFormDrirecciones("cpTab");
</script>


