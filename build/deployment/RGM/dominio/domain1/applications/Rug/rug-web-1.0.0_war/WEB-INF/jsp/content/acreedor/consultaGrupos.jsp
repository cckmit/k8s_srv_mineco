<%@ taglib prefix="s" uri="/struts-tags"%>

<table width="100%">
	<tr>
		<td class="encabezadoTablaResumen" width="100%">
			<s:text name="Consulta de opciones configuradas asignadas a Grupo" />
		</td>
	</tr>
</table>

<s:actionmessage />
<s:actionerror />


	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td class="textoGeneralAzul" align="right"><b class="textoGeneralRojo">*</b><s:text name="Grupo" /></td>
                        <td>
                        <s:form id="busquedaForm"  namespace="/acreedor/" action="busquedaGrupo.do" method="post">	
							<s:select name="personaFisica.grupo.id" id="personaFisica.grupo.id"  list="grupos" listKey="id" listValue="descripcion" headerKey="-1" headerValue="Seleccione un grupo" value="grupoElegido" required="true" onchange="javascript:busquedaGrupos();" cssClass="campo_general"/>
						</s:form>	
						</td>
					</tr>					
				</table>			
			</td>
		</tr>	
		
		<tr>
		        <td align="left" colspan="2">        	
	        		<div id="container">
	        			<table id="mytable" class="mytable" cellpadding="3" cellspacing="2" border="0"  width="100%" >		
							<thead>
								<tr>
									<td width="15%" class="encabezadoTablaResumen"><b class="textoGeneralBlanco"><s:text name="Opciones Configuradas" /></b></td>
									
								</tr>
							</thead>
							<tbody>
								<s:iterator value="listaperfiles" status="estatus">
									<tr>		
										<td class="${((estatus.index % 2) == 0) ? 'cuerpo1TablaResumen' : 'cuerpo2TablaResumen'}">
											<s:property value="descripcion" />
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						</td>
					</div>				                                 
		        </td>
			</tr>				
	</table>


<script type="text/javascript">		

function busquedaGrupos(){
	var form = document.getElementById("busquedaForm");
	form.submit();
}
//setActiveTab('dosMenu');
//setActiveTabBusqueda('dosMenuBusqueda');
$("#dosMenu").attr("class","linkSelected");

</script>