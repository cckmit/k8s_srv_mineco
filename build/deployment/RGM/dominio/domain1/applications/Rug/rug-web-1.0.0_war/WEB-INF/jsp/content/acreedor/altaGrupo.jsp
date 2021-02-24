<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>



<table>
	<tr>
		<td class="titulo_exterior_rojo">
			Alta de Grupos
		</td>
	</tr>
</table>




<table width="100%">
	<tr>
		<td class="encabezadoTablaResumen" width="100%">
			<s:text name="acreedores.usuario.titulo.registro" />
		</td>
	</tr>
</table>
		
<s:actionmessage />
<s:actionerror />

<s:form namespace="acreedor" action="saveGrupo.do" theme="simple">
<INPUT type="hidden" name="personaFisica.nombre" value="<s:property value="personaFisica.nombre"/>" id="personaFisica.nombre">
<INPUT type="hidden" name="personaFisica.apellidoPaterno" value="<s:property value="personaFisica.apellidoPaterno"/>" id="personaFisica.apellidoPaterno">
<INPUT type="hidden" name="personaFisica.apellidoMaterno" value="<s:property value="personaFisica.apellidoMaterno"/>" id="personaFisica.apellidoMaterno">
<INPUT type="hidden" name="personaFisica.datosContacto.emailPersonal" value="<s:property value="personaFisica.datosContacto.emailPersonal"/>" id="personaFisica.datosContacto.emailPersonal">
<INPUT type="hidden" name="personaFisica.perfil" value="<s:property value="personaFisica.perfil"/>" id="personaFisica.perfil">
<INPUT type="hidden" name="personaFisica.grupo.id" value="<s:property value="personaFisica.grupo.id"/>" id="personaFisica.grupo.id">


	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.grupo.field.afiliado" /></td>
						<td class="campo" valign="bottom">
							<s:property value="#session.User.profile.nombre"/> <s:property value="#session.User.profile.apellidoPaterno"/> <s:property value="#session.User.profile.apellidoMaterno"/>
						</td>
					</tr>
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo">*</b><s:text name="acreedores.usuario.grupo.field.nombre.grupo" /></td>
						<td class="campo" valign="bottom">
							<s:fielderror fieldName="grupoPerfilTO.descripcion" />
							<s:textfield name="grupoPerfilTO.descripcion" required="true"
									cssClass="campo_general" maxlength="40" size="40" />
						</td>
					</tr>
					<tr>
				       
				       <tbody>   
				            <tr class="texto_General">
				                <td class="texto_general" align="left" colspan="6">
				                    <s:fielderror fieldName="grupoPerfilTO.perfiles.id" />
				                </td>
				            </tr>
				            <tr class="texto_General">
				                <td class="texto_general" align="right">
				                    &nbsp;&nbsp;
				                </td>
				                <td class="texto_general" align="left">
				                    <s:checkboxlist name="grupoPerfilTO.perfiles.id" list="perfiles" listKey="id" listValue="descripcion" theme="rug" value="perfiles.{?#this.id != 0}.{id}" required="true" ></s:checkboxlist>
				                </td>           
				            </tr>
				        </tbody>
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





<script>
//setActiveTab('dosMenu');
$("#dosMenu").attr("class","linkSelected");
cargaFormDrirecciones("cpTab");
</script>


