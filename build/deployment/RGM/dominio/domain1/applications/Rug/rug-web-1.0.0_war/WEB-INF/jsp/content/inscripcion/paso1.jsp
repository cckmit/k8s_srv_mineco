<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="col s1"></div>
					<div class="col s10">
						<span class="card-title">Inscripci&oacute;n Garant&iacute;a Mobiliaria</span>																		
						<div class="section">
							<h5>Deudor (es) Garante</h5>
							<p>
								<div id="divParteDWR2"></div>
							</p>
						</div>
						<div class="divider"></div>
						<div class="section">
							<h5>Acreedor (es) Garantizado (s)</h5>
							<p> 
								<div id="divParteDWR3"></div>
							</p>
						</div>
						<center>
				            <div class='row'>			            	
				            	<input type="button" class="btn btn-large waves-effect indigo" onclick="paso2_d_paso1()" value="Continuar" id="buttonID" />
				            	<input type="button" class="btn btn-large waves-effect indigo" onclick="msg_guardar()" value="Guardar" id="guardarID" />
				            	<input type="hidden" name="nombreAcreedor" id="nombreAcreedor" value="<s:property value="acreedorTORep.nombreCompleto"/>" />
				            </div>
			          	</center>
			         </div>
			         <div class="col s1"></div> 	
				</div>
			</div>
		</div>
	</div>
</main>

<script type="text/javascript">
//setActiveTab('cuatroMenu');
//$("#cuatroMenu").attr("class", "linkSelected");
var idTramite = <s:property value="idInscripcion"/>;
var idPersona = <s:property value="idPersona"/>;

//cargaParteOtorgante('divParteDWR', idTramite, idPersona, '0', '1');
cargaParteDeudor('divParteDWR2', idTramite, idPersona, '0', '1');
cargaParteAcreedor('divParteDWR3', idTramite, idPersona, '0', '1');
activaBtn1_d_paso1();
	
</script>