<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<main>
	<div class="container-fluid">
		<div class="row">
		<div class="col s1"></div>
			<div class="col s10">
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s12">		
								<span class="card-title">Mis Operaciones</span>
							</div>
						</div>
					<div class="section row">
					 	<div class="col s12">						
							<ul class="tabs">
							    <li class="tab col s3"><a class="blue-text text-darken-2" id ="tabs1" href ="#" onclick = "iniciaPaginacionPendientes('<s:property value="idPersona"/>','1')">Pendientes de Captura de Datos</a></li>
							    <li class="tab col s3"><a class="blue-text text-darken-2" id = "tabs2" href ="#" onclick = "iniciaPaginacionPendientes('<s:property value="idPersona"/>','2')">Pendientes de Confirmaci&oacute;n</a></li>
							    <li class="tab col s3"><a class="blue-text text-darken-2" id = "tabs3" href ="#" onclick = "iniciaPaginacionPenAsMultiples('<s:property value="idPersona"/>')">Pendientes de Confirmaci&oacute;n Registros Multiples</a></li>							    							    
							    <li class="tab col s3"><a class="blue-text text-darken-2" id = "tabs4" href ="#" onclick = "iniciaPaginacionPendientes('<s:property value="idPersona"/>','3')">Terminados</a>  </li>
								<div class="indicator blue" style="z-index:1"></div>
								
							</ul>
						</div>
					</div>
					<div class="section" id="tabs-1">
						<div class="row note">					    	
					        	<span>Pendientes de Captura de Datos</span>					   		
					 	</div>													
					    <div id="OpPendientes"></div>
					</div>
					<div id="tabs-2" class="section">
						<div class="row note">					    	
					        	<span>Pendientes de Confirmacion</span>					   		
					 	</div>						
					    <div id="OpPenFirma"></div>
					</div>
					<div id="tabs-3" class="section">
						<div class="row note">					    	
					        	<span>Pendientes de Confirmacion Registros Multiples</span>					   		
					 	</div>						
					    <div id="OpPenFirmaMasiva"></div>
					</div>
					<div id="tabs-4" class="section">
						<div class="row note">					    	
					        	<span>Terminados</span>					   		
					 	</div>
					 	<div class="row">
					 		<form>
								<div class="input-field col s4">									
									<input type="text" class="form-control" id="terfiltro" name="terfiltro" placeholder="Buscar" onkeypress="return runScript(event)">										
								</div>
								<div class="col s4">
									<button type="button" class="btn waves-effect indigo" onclick = "buscarporfiltro()" >
										<span class="fooicon fooicon-search"></span>
									</button>
									<p class="waves-effect waves-light btn" onclick="ExportExcelTerminadas()">Exportar</p>
								</div>								
							</form>
							
					 		<div id="OpTerminadas">
					   		</div>
					 	</div>											   
					</div> 
					<div class="col s1"></div>
					<s:hidden value="%{idPersona}" id="idPersona" name="idPersona"/>
					</div>
				</div>
			</div>
		</div>
	</div>	
</main>

<script type="text/javascript">

	function ExportExcelTerminadas(){
		var idPersona=document.getElementById('idPersona').value;
		var filtros = getObject('terfiltro').value;
		var URL="<%=request.getContextPath()%>/home/exportOperaciones.do?persona=" + idPersona + "&filtroExcel=" + filtros;
		window.open(URL, "_blank");
	}


</script>