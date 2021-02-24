<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="col s2"></div>
					<div class="col s8">	
						<span class="card-title">Boleta Electr&oacute;nica de Garant&iacute;as Mobiliarias</span>
						<div class="row">
							<p><span>A finalizado la operaci&oacute;n existosamente.</span></p>
							<p><span>Para descargar e imprimir la boleta dar click en el siguiente bot&oacute;n:</span></p>
							<a class="btn btn-large waves-effect indigo" href="<%= request.getContextPath() %>/pdf.pdo" target="_blank"> Descargar PDF </a> <br/>
							<a href="<%= request.getContextPath() %>/carga/masiva/descargaArchivo.servlet?idArchivo=<s:property value="idArchivoRes"/>" > Descargar XML de resultado </a>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
</main>