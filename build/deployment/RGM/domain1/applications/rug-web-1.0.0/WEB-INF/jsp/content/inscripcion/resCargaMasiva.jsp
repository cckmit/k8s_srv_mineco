<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<main>

<div class="container-fluid">
	<div class="row">
		<div class="col s12">
			<div class="card">
				<div class="col s1"></div>					
				<div class="col s10">
					<table class="bordered striped centeredresponsive-table">
						<thead>
							<tr>
								<th colspan="2">Resultados de la carga masiva</th>
							</tr>
						</thead>	
						<tr>
							<td>Total de tramites en el archivo <s:property value="totalTramites"/> </td>
							<td></td>
						</tr>
						<tr>
							<td>Tramites completos : <s:property value="tramitesCompletos"/> </td>
							<td>Tramites erroneos : <s:property value="tramitesErroneos"/> </td>
						</tr>	
						<tr>
							<td colspan="2"> <a href="<%= request.getContextPath() %>/carga/masiva/descargaArchivo.servlet?idArchivo=<s:property value="idArchivoRes"/>" >Descargar el informe de la carga </a> </td>
						</tr>
						<s:if test="inscripcionesErroneas.size>0">
							<thead>
							<tr>
								<td>Clave Rastreo</td>
								<td>Mensaje</td>
							</tr>
						</thead>
						<s:iterator value="inscripcionesErroneas">
						<tr>
							<td><s:property value="claveRastreo"/></td>
							<td><s:property value="mensajeError"/></td>
						</tr>
						</s:iterator>
						<s:if test="sizeListaTramites>0">
						<tr>
							<td></td>
							<td><a class="btn indigo" onclick="confirmar();" > Confirmar correctos</a></td>
						</tr>
						</s:if>
						<s:else>
						<tr>
							<td></td>
							<td><a class="btn red" href="<%= request.getContextPath() %>/masiva/inicia.do" > Intentar de nuevo</a> </td>
						</tr>
						</s:else>
						</s:if>
						
						<s:else>
						<tr>
							<td></td>
							<td><a class="btn indigo" href="<%= request.getContextPath() %>/masiva/firmarTodos.do" >
										Confirmar todos</a> </td>
						</tr>
						</s:else>
						
					</table>
				</div>
			<div class="col s1"></div>		
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
 <script>
//setActiveTab('seisMenu');
$("#seisMenu").attr("class","linkSelected");
</script>
<script>
function confirmar(){
	var correctos = '<s:property value="tramitesCompletos"/>';
	var costoTramite = '<s:property value="costoTramiteMasivo"/>'	
	var total = costoTramite * correctos;
	
	MaterialDialog.dialog(
				"El costo de una operaci&oacute;n es de Q " + costoTramite + ".00, ¿está seguro que desea continuar?",
				{				
					title:'<table><tr><td width="10%"><i class="medium icon-green material-icons">check_circle</i></td><td style="vertical-align: middle; text-align:left;">Confirmar</td></tr></table>', // Modal title
					buttons:{
						// Use by default close and confirm buttons
						close:{
							className:"red",
							text:"cancelar"						
						},
						confirm:{
							className:"indigo",
							text:"aceptar",
							modalClose:false,
							callback:function(){								
								window.location.href = '<%= request.getContextPath() %>/masiva/firmarTodos.do';
							}
						}
					}
				}
			);										
}
</script>

</main>