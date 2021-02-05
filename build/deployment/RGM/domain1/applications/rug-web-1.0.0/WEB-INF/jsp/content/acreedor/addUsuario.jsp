<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/engine.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/util.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/DireccionesDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/dwr/interface/ParteDwrAction.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/dwr/direccionesDWR.js"></script>
<script type="text/javascript"    src="${pageContext.servletContext.contextPath}/resources/js/partesJS/parteJS.js"></script>


<table><tr><td>
<table>

	 <td>
<tr>

<table width="100%">
	<tr>
		<td class="encabezadoTablaResumen" width="100%">
			<s:text name="acreedores.usuario.titulo.registro" />
		</td>
	</tr>
</table>
<table width="100%">
<tr>
		<tr class="texto_General">
				<td colspan="4">
					
				</td>
			</tr>
				
			<tr class="texto_General">
				<td width="2%" class="texto_general"></td>
				<td class="encabezadoTablaResumen" width="30%" align="center">
					<s:text name="acreedores.etiqueta.columna.clave.usuario" />
				</td>
				<td class="encabezadoTablaResumen" width="10%" align="center">
					<s:text name="acreedores.etiqueta.columna.situacion" />
				</td>
				<td class="encabezadoTablaResumen" width="30%" align="center">
					<s:text name="acreedores.etiqueta.columna.grupo" />
				</td>
				<td class="encabezadoTablaResumen" width="30%" align="center">
					<s:text name="acreedores.etiqueta.columna.perfil" />
				</td>
				<td width="13%" class="texto_general"></td>
			</tr>
			<!-- ITERADOR usar status="stat" para que funcionen los estilos -->
			<s:iterator value="usuariosAfiliados">
				<tr>
					<td width="2%" class="texto_general"></td>
					<td class="${((stat.index % 2) == 0) ? 'cuerpo1TablaResumen' : 'cuerpo2TablaResumen'}">
						<s:property value="datosContacto.emailPersonal" />
					</td>
					<td class="${((stat.index % 2) == 0) ? 'cuerpo1TablaResumen' : 'cuerpo2TablaResumen'}" align="center">
						<s:if test='situacion=="AC"'>
							<s:text name="ACTIVO" />
						</s:if>
						<s:if test='situacion=="IN"'>
							<s:text name="INACTIVO" />
						</s:if>
					</td>
					<td class="${((stat.index % 2) == 0) ? 'cuerpo1TablaResumen' : 'cuerpo2TablaResumen'}" align="center">
						<s:property value="grupo.descripcion" />
					</td>
					<td class="${((stat.index % 2) == 0) ? 'cuerpo1TablaResumen' : 'cuerpo2TablaResumen'}" align="center">
						<s:property value="perfil" />
					</td>
					<td width="13%" class="texto_general"></td>									
				</tr>
			</s:iterator>
</tr>
</table>

		
<s:actionmessage />
<s:actionerror />

<s:form name="formUsuario" namespace="acreedor" action="save.do" theme="simple">
	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.jefe" /></td>
						<td class="campo" valign="bottom">
							<s:property value="#session.User.profile.nombre"/> <s:property value="#session.User.profile.apellidoPaterno"/> <s:property value="#session.User.profile.apellidoMaterno"/>
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.nombre" /></td>
						<td class="campo" valign="bottom">
							<s:fielderror fieldName="personaFisica.nombre" />
							<s:textfield name="personaFisica.nombre" id="personaFisica.nombre" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.paterno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoPaterno" />
							<s:textfield name="personaFisica.apellidoPaterno" id="personaFisica.apellidoPaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo"></b><s:text name="acreedores.usuario.field.materno" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.apellidoMaterno" />
							<s:textfield name="personaFisica.apellidoMaterno" id="personaFisica.apellidoMaterno" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.correo" /></td>
						<td class="campo">
							<s:fielderror fieldName="personaFisica.datosContacto.emailPersonal" />
							<s:textfield name="personaFisica.datosContacto.emailPersonal" id="personaFisica.datosContacto.emailPersonal" required="true"
									cssClass="campo_general" maxlength="256" size="40" cssStyle="text-transform:lowercase" />
						</td>
					</tr>
					<tr valign="top">
						<td></td>
						<td class="ayuda">
							<table class="ayuda">
							<tr>
								<td class="texto_general">
									<br /><br />
								</td>
							</tr>
							</table>								
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.password" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.password" />
							<s:password name="registroUsuario.password" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.field.confirmacion" /></td>
						<td class="campo">
							<s:fielderror fieldName="registroUsuario.confirmacion" />
							<s:password name="registroUsuario.confirmacion" required="true"
									cssClass="campo_general" maxlength="16" size="40" />
						</td>
					</tr>

					--<s:property value="variableValida"/>--
					
				<!-- 
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="Perfil" /></td>
						<td class="campo">
							<s:if test='variableValida=="ACREEDOR"'>
								<s:fielderror fieldName="personaFisica.perfil" />
								<select name="personaFisica.perfil" id="personaFisica.perfil" onchange="javascript:GrupoCambio();" style="cursor: pointer;">
								    <option value="ADMINISTRADOR">ADMINISTRADOR</option>
								    <option value="OPERATIVO">OPERATIVO</option>
								</select>
							</s:if>	
							<s:if test='variableValida=="ADMINISTRADOR"'>
								<select name="personaFisica.perfil" id="personaFisica.perfil" onchange="javascript:GrupoCambio();" style="cursor: pointer;">
								    <option value="ADMINISTRADOR">ADMINISTRADOR</option>
								    <option value="OPERATIVO">OPERATIVO</option>
								</select>
							</s:if>	
							
						</td>
					</tr>							
				 -->
				 
				 	<tr>
                        <td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="Perfil" /></td>
                            
                        <td><s:select name="personaFisica.perfil" id="personaFisica.perfil" onchange="javascript:GrupoCambio();" style="cursor: pointer;"
						key="perfil" list="listPerfiles"
						listKey="perfil" listValue="descripcionPerfil" 
						required="false" />
						</td>
		            </tr>
					<tr>
						<td></td>
		                <td>
		                    <div id='_divLabelComboAdmin'>
		                        <table width="40" align="left" bgcolor="#FFFFFF" cellspacing="2" cellpadding="2">   
		                            <tr class="texto_General">
		                                <td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="Grupo" /></td>
		                               
		                                <td><s:select name="personaFisica.grupo.id" id="personaFisica.grupo.id"
											key="grupo" list="grupos"
											listKey="id" listValue="descripcion" 
											 required="false" />
										</td>
		                                
		                                
										<td width="50%">
											<!--a tabindex="40" href='<%= request.getContextPath() %>/acreedor/altaGrupo.do'>Crear Grupo </a-->
											<a onclick="addGrupo()" style="cursor: pointer;" class="tituloInteriorRojo">Crear Grupo</a>
										</td>
		                            </tr>
		                        </table>       	
		                    </div>
		                    <div id='_divLabelComboOper'>
		                        <table width="40" align="right" bgcolor="#FFFFFF" cellspacing="2" cellpadding="2">   
		                            
		                        </table>       
		                    </div>
		                </td>
		              
		            </tr>

					
					<tr>
						<td class="btnSubmit" colspan="2">
							<s:url id="uri"  value="%{getContextPath()}/imgs/%{getText('common.img.i18n.path')}/aceptar.gif" />
							<s:submit type="button" src="%{uri}" name="Aceptar" value="Aceptar" align="center" />
						</td>
					</tr>
				</table>			
			</td>
			<td width="15%" valign="top">
				<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg" />
			</td>			
		</tr>			
	</table>	
</s:form>
			
	</tr>
</td>
</table>
</td></tr></table>



<script type="text/javascript">
GrupoCambio();

fGrupoCambio();
function GrupoCambio(){
    var bPerfil = document.getElementById("personaFisica.perfil").value;
    document.getElementById("personaFisica.perfil").value =bPerfil;
    var subAdmin = document.getElementById('_divLabelComboAdmin');
    var subOperativo = document.getElementById('_divLabelComboOper');

    if(bPerfil == "ADMINISTRADOR"){
    	subAdmin.style.display = 'none';
    	subOperativo.style.display = 'block';
    }
    if(bPerfil != "ADMINISTRADOR"){
    	subAdmin.style.display = 'block';
    	subOperativo.style.display = 'none';
    }
}

function addGrupo(){
	var nombre = document.getElementById("personaFisica.nombre").value;
	var apPaterno = document.getElementById("personaFisica.apellidoPaterno").value;
	var apMaterno = document.getElementById("personaFisica.apellidoMaterno").value;
	var emailPersonal = document.getElementById("personaFisica.datosContacto.emailPersonal").value;
	var perfil = document.getElementById("personaFisica.perfil").value;
	var grupo = document.getElementById("personaFisica.grupo.id").value;
    
    document.location="<%=request.getContextPath() %>/acreedor/altaGrupo.do?personaFisica.nombre="+nombre+"&personaFisica.apellidoPaterno="+apPaterno+"&personaFisica.apellidoMaterno="+apMaterno+"&personaFisica.datosContacto.emailPersonal="+emailPersonal+"&personaFisica.perfil="+perfil+"&personaFisica.grupo.id="+grupo;
}
</script>


