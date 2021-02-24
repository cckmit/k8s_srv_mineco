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
					<div class="row">
						<p style="font-size: 20px; color: red;"> Sucedio un error en el archivo: </p>
						<p style="font-size: 15px; color: blue;"> <s:property value="errorArchivo"/> </p>
						<p style="color: white"><s:property value="mensajeErroXsd"/></p>
						
						<s:if test="detalleTecnico!=null">
						<script>
						function showDetalleTecnico(){
							document.getElementById('divDetalleTecnico').style.display='block';
							document.getElementById('divDetalleTecnico').style.visibility = 'visible';
						
						}
						</script>
						<a onclick="showDetalleTecnico()">Mostrar detalle tecnico</a>
						<div id="divDetalleTecnico" style="visibility: hidden; display: none;" >
						<s:property value="detalleTecnico"/>
						</div>
						</s:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div> 
</main>