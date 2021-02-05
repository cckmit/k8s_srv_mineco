<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.seguridad.to.MenuTO"%>
<%@page import="mx.gob.se.rug.seguridad.serviceimpl.MenusServiceImpl"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>
<%@include file="/WEB-INF/jsp/Layout/menu/applicationCtx.jsp" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<main>
<%
//Privilegios
PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
PrivilegiosTO privilegiosTO = new PrivilegiosTO();
privilegiosTO.setIdRecurso(new Integer(6));
privilegiosTO=privilegiosDAO.getPrivilegios(privilegiosTO,(UsuarioTO)session.getAttribute(Constants.USUARIO));
Map<Integer,PrivilegioTO> priv= privilegiosTO.getMapPrivilegio();

%>
<input type="hidden" name="tipoBienAll" value="false" id="idTipoBienAll" />
<div class="container-fluid">	
	<div id="modifica" class="row">
		<div class="col s12"><div class="card">
			<div class="col s2"></div>
			<div class="col s8">
				<form id="faformulario" name="faformulario" action="saveformulario.do" method="post">
					<span class="card-title">Formulario &uacute;nico por Inscripci&oacute;n especial de traslado</span>
					<input type="hidden" name="refInscripcion" id="refInscripcion" value="<s:property value='idTramite'/>" />
					<div class="row note">
						<p>
							<span>Este formulario se completar&aacute; por el usuario que tenga un derecho de prenda o 
							      garant&iacute;a mobiliaria sobre veh&iacute;culo (s) inscrito en el Registro General de la Propiedad 
							      o en el Segundo Registro de la Propiedad y que en 
							      virtud de lo establecido en el art&iacute;culo 26 del Decreto 4-2018 
							      (Reformas a la Ley de Garant&iacute;as Mobiliarias) debe inscribirse en el Registro 
							      de Garant&iacute;as Mobiliarias. </span>
						</p>
					</div>
					<div class="row note teal">
						<span class="white-text">I. Deudor(es) \ Deudor(es) Garantes</span>
					</div>					
					<div class="row">						
						<div id ="divParteDWRxx2"></div>
					</div>
					<div class="row note teal">
						<span class="white-text">II. Acreedor (es) \Acreedor (es) Garantizado (s)</span>
					</div>					
					<div class="row">														
						<div id="divParteDWRxx3"></div>							
					</div>					
					<div class="row note teal">
						<span class="white-text">III. Datos de la prenda \ Garant&iacute;a
							Mobiliaria</span>
					</div>															
					<div class="row">
						<span class="blue-text text-darken-2">Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>
					</div>
					<div class="row">
						<div class="col s12 right-align">
							<a class="btn-floating btn-large waves-effect indigo modal-trigger" onclick="limpiaCampos()"
								href="#frmBien" id="btnAgregar"><i
								class="material-icons left">add</i></a>							
							<a class="btn-floating btn-large waves-effect indigo modal-trigger" onclick="limpiaCamposFile()"
								href="#frmFile" id="btnFile"><i
								class="material-icons left">attach_file</i></a>							
						</div>													
						<div id="divParteDWRBienes"></div>						
					</div>																	
				 	<div class="row">							    	
			    		<p>							    		
			    			<input type="checkbox" name="aRegistro" id="aRegistro" value="true" onclick="otroRegistro()"/>
			        		<label for="aRegistro">El bien se encuentra en otro registro</label>
			        	</p>
			        </div>
			        <div class="row">						   		
				   		<div class="input-field col s12">
				    		<s:textarea name="txtregistro" id="txtregistro" value="%{txtregistro}" disabled="true"/>						    	 								        	
				        	<label for="txtregistro">Especifique cual</label>
				   		</div>
				 	</div>
				 	<div class="row">						   		
				   		<div class="input-field col s12">
				    		<s:textfield name="txtfregistro" id="txtfregistro" value="%{txtfregistro}" />						    	 								        	
				        	<label for="txtfregistro">Fecha de Inscripci&oacute;n</label>
				   		</div>
				 	</div>
				 	<div class="row note">						   		
				   		<span> Ejemplo. Inscrito en Registro General de La Propiedad<br />
				   						N&uacute;mero: 01 	Folio: 01  Libro: 101<br />
				   						Inscrita el 21 de enero de 2018
				   		</span>
				 	</div>
				 	<div class="row note teal">
						<span class="white-text">IV. Contrato de financiamiento con garant&iacute;a prendaria o garant&iacute;a mobiliaria</span>
					</div>										 									 	
				 	<div class="row">
				    	<div class="input-field col s12">
				        	<s:textarea rows="10" cols="80" id="instrumento" name="instrumento" value="%{instrumento}"  
									    maxlength="3500" />
				        	<label for="instrumento">Datos generales del instrumento p&uacute;blico que documenta la constituci&oacute;n de la prenda o garant&iacute;a mobiliaria</label>
				   		</div>
				 	</div>				 	
				 	<div class="row">
				    	<div class="input-field col s12">
				    		<s:textarea id="modotrosgarantia" name="modotrosgarantia" cols="80" rows="10" maxlength="3500" value="%{modotrosgarantia}" />				        										
				        	<label for="modotrosgarantia">Observaciones Adicionales de la Garant&iacute;a</label>
				   		</div>
				 	</div>				 	
				 	<div class="row note teal">							    	
			        	<span class="white-text">
			        		V. Plazo de vigencia de la inscripci&oacute;n en el Registro de Garant&iacute;as Mobiliarias
						</span>							   		
				 	</div>
				 	<div class="row note">
				    	<span>
				    	Advertencia: El acreedor garantizado tiene derecho de prelaci&oacute;n sobre el bien objeto de prenda 
				    	o garant&iacute;a mobiliaria de conformidad con la inscripci�n original que se oper&oacute; en el Registro de 
				    	la Propiedad. La presente inscripci&oacute;n publicita a terceros en virtud del art. 26 del Decreto 4-2018  
				    	a trav&eacute;s del sistema electr&oacute;nico por el que opera el Registro de Garant&iacute;as Mobiliarias la afectaci&oacute;n 
				    	sobre el veh&acute;culo en garant�a. 
						Toda modificaci&oacute;n a esta garant&iacute;a real deber&aacute; operarse en el Registro de Garant&iacute;as Mobiliarias. 	
				    	</span>
				 	</div>				 	
				    <hr />
				 	<center>
			            <div class='row'>			            	
			            	<input type="button" id="bFirmar" name="button" class="btn btn-large waves-effect indigo" value="Aceptar" onclick="inscripcionFormulario();"/>			            				            							            	
			            </div>
		          	</center>						
				</form>	
				<s:if test="aBoolean">
					<script type="text/javascript">
							document.getElementById('aBoolean').checked = 1;
					</script>
				</s:if>		
				<s:if test="aMonto">
					<script type="text/javascript">
							document.getElementById('aMonto').checked = 1;
					</script>
				</s:if>
				<s:if test="aPrioridad">
					<script type="text/javascript">
							document.getElementById('aPrioridad').checked = 1;
					</script>
				</s:if>
				<s:if test="aRegistro">
					<script type="text/javascript">
							document.getElementById('aRegistro').checked = 1;
							document.getElementById("txtregistro").disabled = false;
							Materialize.updateTextFields();
					</script>
				</s:if>						
			</div>
		</div></div>	
	</div>
</div>
<script type="text/javascript"> 	
					var idPersona = <s:property value="idPersona"/>;
					var idTramite= <s:property value="idTramite"/>;
		
					cargaParteDeudor('divParteDWRxx2',idTramite, idPersona,'0','1');
					cargaParteAcreedor('divParteDWRxx3',idTramite, idPersona,'0','1');
					cargaParteBienes('divParteDWRBienes',idTramite);

					//escondePartes();	
				</script> 				
</main>
<div id="frmBien" class="modal">
	<div class="modal-content">
		<div class="card">
			<div class="card-content">						
				<span class="card-title">Bien Especial</span>
				<div class="row">
					<div class="col s1"></div>
					<div class="col s10">
						<div class="row">
							<div class="input-field col s12">
								<s:select name="mdBienEspecial" id="mdBienEspecial" list="listaBienEspecial" listKey="idTipo" listValue="desTipo" 
									 onchange="cambiaBienesEspeciales()"/>								    
						    	<label>Tipo Bien Especial</label>
						  	</div>
						</div>
						<div id="secId4" class="row">
							<div class="input-field col s6">
								<s:textfield name="mdFactura1" id="mdFactura1"
									cssClass="validate" maxlength="150" />
								<label id="lblMdFactura1" for="mdFactura1">Emitido por:</label>
							</div>						
							<div class="input-field col s6">
								<input type="text" name="mdFactura2" class="datepicker" id="mdFactura2" />									
								<label id="lblMdFactura2" for="mdFactura2">Fecha: </label>
							</div>						
						</div>
						<div class="row">
							<div class="input-field col s12">
								<s:textarea rows="10" id="mdDescripcion" cols="80" 												
											name="mdDescripcion" />							    	 								        	
				        		<label id="lblMdDescripcion" for="mdDescripcion">Descripci&oacute;n del bien</label>
				        	</div>
						</div>							
						<div id="secId1"class="row" style="display: none;">
							<div class="input-field col s3">
								<label id="lblMdIdentificador">Placa</label>
							</div>						
							<div class="input-field col s3">
								<s:select name="mdIdentificador" id="mdIdentificador" list="listaUsos" listKey="idTipo" listValue="desTipo"/>																
							</div>
							<div class="input-field col s6">
								<s:textfield name="mdIdentificador1" id="mdIdentificador1"
									cssClass="validate" maxlength="150" />								
							</div>
						</div>
						<div id="secId2" class="row" style="display: none;"><span class="col s12 center-align">Y</span></div>
						<div id="secId3" class="row" style="display: none;">
							<div class="input-field col s12">
								<s:textfield name="mdIdentificador2" id="mdIdentificador2"
									cssClass="validate" maxlength="150" />
								<label id="lblMdIdentificador2" for="mdIdentificador2">VIN</label>
							</div>
						</div>														
						<br />
						<hr />
						<center>
							<div id="secId5" class="row">
								<a href="#!"
									class="btn-large indigo"
									onclick="add_bien();">Aceptar</a>
							</div>
							<div id="secId6" class="row">
								<a href="#!" id="formBienButton"
									class="btn-large indigo"
									onclick="add_bien();">Aceptar</a>
							</div>							
						</center>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="frmFile" class="modal">
	<div class="modal-content">
		<div class="card">
			<div class="card-content">						
				<span class="card-title">Bien Especial</span>
				<div class="row">
					<div class="col s1"></div>
					<div class="col s10">
						<div class="row">
							<div class="input-field col s12">
								<s:select name="mdBienEspecial2" id="mdBienEspecial2" list="listaBienEspecial" listKey="idTipo" listValue="desTipo" 
									 onchange="cambiaBienesEspecialesFile()"/>								    
						    	<label>Tipo Bien Especial</label>
						  	</div>
						</div>
						<div id="secTxt3" class="row note">
							<span id="txtspan" name="txtspan"></span>							
						</div>	
						<div class="row">
							<div class="input-field col s8">
								<div class="file-field input-field">
									<div class="btn">
										<span>Archivo</span>
										 <input type="file" name="excelfile" id="excelfile" />										
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text" name="namefile" id="namefile"
											placeholder="Seleccione...">
									</div>								
								</div>
							</div>
							<div class="input-field col s4">
								<a href="#!"
									class="btn-large indigo"
									onclick="ExportToTable();">Cargar</a>
							</div>
						</div>																								
						<div class="row">
							<table id="exceltable">  
							</table> 
						</div>	
						<br />
						<hr />
						<center>
							<div class="row">
								<a href="#!"
									class="modal-close btn-large indigo">Aceptar</a>
							</div>
						</center>
					</div>					
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/xlsx.core.min.js"></script>
<script type="text/javascript"> 
function BindTable(jsondata, tableid) {/*Function used to convert the JSON array to Html Table*/  
     var columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/  
	 var idTramite = document.getElementById("refInscripcion").value;
	 var mdDescripcion = '';
	 var idTipo = document.getElementById("mdBienEspecial2").value;
	 var mdIdentificador = '';
	 var mdIdentificador1 = '';
	 var mdIdentificador2 = '';
	 var tipoId = '';
	 var correcto = 0;
	 var limite = 50;
	 
	 if(jsondata.length > limite.valueOf()){
		 alertMaterialize('Error!. Solo se pueden cargar ' + limite + ' registros');
		 return false;
	 }
	 
	 if(idTipo == '0'){
		 return false;
	 }
	 
     for (var i = 0; i < jsondata.length; i++) {  
         var row$ = $('<tr/>');  
		 mdDescripcion = '';
		 mdIdentificador = '';
		 mdIdentificador1 = '';
	     mdIdentificador2 = '';
		 correcto = 0;
		 tipoId = '';
		 
         for (var colIndex = 0; colIndex < columns.length; colIndex++) {  
             var cellValue = jsondata[i][columns[colIndex]];  
             if (cellValue == null)  
                 cellValue = "";  
			 if(idTipo == '1') {
				 if(colIndex == 0) {
					 tipoId = cellValue;
					 if(cellValue == '1'){
						cellValue = 'Placa';
					 } else if(cellValue == '2'){
						cellValue = 'VIN';
					 } else {
						cellValue = 'Valor invalido';
						correcto = 1;
					 }
				 }
				 if(colIndex == 1) {	
					if(cellValue.length > 25){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						if(tipoId == '1'){
							if(cellValue.includes("-")){
								var str = cellValue.split("-");
								mdIdentificador = str[0];
								mdIdentificador1 = str[1];
							} else {
								cellValue = 'Valor invalido';
								correcto = 1;
							}
						} else {
							mdIdentificador2 = cellValue;
						}
					}
				 }
				 if(colIndex == 2) {
					 if(cellValue.length > 100){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdDescripcion = cellValue;
					}	
				 }
				 if(colIndex > 2) {
					 correcto = 1;
				 }
			 } else if(idTipo == '2') { //Facturas				 
				 if(colIndex == 0) {	
					if(cellValue.length > 25){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {						
						mdIdentificador2 = cellValue;
					}
				 }
				 if(colIndex == 1) {
					var patt = /^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\/]\d{4}$/;
					if(!patt.test(cellValue)){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdDescripcion = cellValue;
					}	
				 }
				 if(colIndex == 2) {
					 if(cellValue.length > 25){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdDescripcion = cellValue;
					}	
				 }
				 if(colIndex == 3) {
					 if(cellValue.length > 100){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdDescripcion = cellValue;
					}	
				 }
				 if(colIndex > 3) {
					 correcto = 1;
				 }
			 } else if(idTipo == '3') { 
				 if(colIndex == 0) {	
					if(cellValue.length > 25){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdIdentificador2 = cellValue;
					}
				 }
				 if(colIndex == 1) {
					if(cellValue.length > 100){
						cellValue = 'Valor invalido';
						correcto = 1;
					} else {
						mdDescripcion = cellValue;
					}	
				 }
				 if(colIndex > 1) {
					 correcto = 1;
				 }
			 }	 
             row$.append($('<td/>').html(cellValue));  
         }  
		 if(correcto == 0) {
			ParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, 
				mdIdentificador1, mdIdentificador2, showParteBienes); 
			row$.append('<td><font color="green">Cargado</font></td>');
		 } else {
			 row$.append('<td><font color="red">Error verifique datos</font></td>');
		 }
         $(tableid).append(row$);  
     }  
 }  
function BindTableHeader(jsondata, tableid) {/*Function used to get all column names from JSON and bind the html table header*/  
     var columnSet = [];  
     var headerTr$ = $('<tr/>');  
     for (var i = 0; i < jsondata.length; i++) {  
         var rowHash = jsondata[i];  
         for (var key in rowHash) {  
             if (rowHash.hasOwnProperty(key)) {  
                 if ($.inArray(key, columnSet) == -1) {/*Adding each unique column names to a variable array*/  
                     columnSet.push(key);  
                     headerTr$.append($('<th/>').html(key));  
                 }  
             }  
         }  
     }  
	 headerTr$.append('<th>Resultado</th>');
     $(tableid).append(headerTr$);  
     return columnSet;  
 }  
function ExportToTable() { 

document.getElementById("exceltable").innerHTML = '<table id="exceltable"></table> ';

var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xlsx|.xls)$/;  
     /*Checks whether the file is a valid excel file*/  
     if (regex.test($("#excelfile").val().toLowerCase())) {  
         var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/  
         if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") > 0) {  
             xlsxflag = true;  
         }  
         /*Checks whether the browser supports HTML5*/  
         if (typeof (FileReader) != "undefined") {  
             var reader = new FileReader();  
             reader.onload = function (e) {  
                 var data = e.target.result;  
                 /*Converts the excel data in to object*/  
                 if (xlsxflag) {  
                     var workbook = XLSX.read(data, { type: 'binary' });  
                 }  
                 else {  
                     var workbook = XLS.read(data, { type: 'binary' });  
                 }  
                 /*Gets all the sheetnames of excel in to a variable*/  
                 var sheet_name_list = workbook.SheetNames;  
  
                 var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/  
                 sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/  
                     /*Convert the cell value to Json*/  
                     if (xlsxflag) {  
                         var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);  
                     }  
                     else {  
                         var exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);  
                     }  
                     if (exceljson.length > 0 && cnt == 0) {  
                         BindTable(exceljson, '#exceltable');  
                         cnt++;  
                     }  
                 });  
                 $('#exceltable').show();  
             }  
             if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/  
                 reader.readAsArrayBuffer($("#excelfile")[0].files[0]);  
             }  
             else {  
                 reader.readAsBinaryString($("#excelfile")[0].files[0]);  
             }  
         }  
         else {  
             alertMaterialize("Error! Su explorador no soporta HTML5!");  
         }  
     }  
     else {  
         alertMaterialize("Por favor seleccione un archivo de Excel valido!");  
     }  
}

function add_bien() {
	  
	  var idTramite = document.getElementById("refInscripcion").value;
	  var mdDescripcion = document.getElementById("mdDescripcion").value;
	  var idTipo = document.getElementById("mdBienEspecial").value;
	  var mdIdentificador = document.getElementById("mdIdentificador").value;
	  var mdIdentificador1 = document.getElementById("mdIdentificador1").value;
	  var mdIdentificador2 = document.getElementById("mdIdentificador2").value;
	  
	  if(!noVacio(mdDescripcion)){
		  alertMaterialize('Debe ingresar la descripcion del bien especial');
		  return false;
	  }
	  
	  if(idTipo == '2'){
		  mdDescripcion = 'Emitido por: ' + document.getElementById("mdFactura1").value + " Fecha: " + document.getElementById("mdFactura2").value + " " + mdDescripcion;
	  }
	  
	  if(idTipo == '1'){
		  if(!noVacio(mdIdentificador2)) {
			  alertMaterialize('Debe ingresar el VIN del vehiculo');
			  return false;
		  }
	  } 
	  
	  ParteDwrAction.registrarBien('divParteDWRBienes',idTramite, mdDescripcion, idTipo, mdIdentificador, 
			  mdIdentificador1, mdIdentificador2, showParteBienes);
	  
	  $(document).ready(function() {	  
			$('#frmBien').modal('close');
		});
}

function cambiaBienesEspecialesFile() {
	  var x = document.getElementById("mdBienEspecial2").value;
	  
	  if(x=='1'){
		  document.getElementById("txtspan").innerHTML = 'Los campos del excel son: '
		    + '<p><b>Tipo Identificador</b>, 1 si es Placa y 2 si es VIN<p>'
		    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'
			+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';
		  
	  } else if (x=='2'){
		  document.getElementById("txtspan").innerHTML = 'Los campos del excel son: '
		    + '<p><b>Numero Identificacion Contribuyente<b>, maximo 25 caracteres</p>'
			+ '<p><b>Fecha</b>, formato texto DD/MM/YYYY</p>'
			+ '<p><b>Numero Factura</b>, maximo 25 caracteres</p>'
			+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';
	  } else if (x=='3'){
		  document.getElementById("txtspan").innerHTML = 'Los campos del excel son: '
		    + '<p><b>Identificador</b>, maximo 25 caracteres</p>'
			+ '<p><b>Descripcion</b>, maximo 100 caracteres</p>';
	  }
	  
	  
}

function cambiaBienesEspeciales() {
	  var x = document.getElementById("mdBienEspecial").value;
	  
	  if(x=='1'){
		  document.getElementById("mdDescripcion").disabled = false;	  
		  document.getElementById("secId1").style.display = 'block'; 
		  document.getElementById("secId2").style.display = 'block';
		  document.getElementById("secId3").style.display = 'block';
		  document.getElementById("secId4").style.display = 'none';
		  
		  document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';
		  document.getElementById("lblMdIdentificador2").innerHTML = 'VIN';
	  } else if (x=='2'){
		  document.getElementById("mdDescripcion").disabled = false;	  
		  document.getElementById("secId1").style.display = 'none'; 
		  document.getElementById("secId2").style.display = 'none';
		  document.getElementById("secId3").style.display = 'block';		
		  document.getElementById("secId4").style.display = 'block';
		  
		  document.getElementById("lblMdIdentificador2").innerHTML = 'No. Factura';
		  document.getElementById("lblMdDescripcion").innerHTML = 'Observaciones Generales';
	  } else if (x=='3'){
		  document.getElementById("mdDescripcion").disabled = false;	  
		  document.getElementById("secId1").style.display = 'none'; 
		  document.getElementById("secId2").style.display = 'none';
		  document.getElementById("secId3").style.display = 'block';		
		  document.getElementById("secId4").style.display = 'none';
		  
		  document.getElementById("lblMdIdentificador2").innerHTML = 'No. Serie';
		  document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del bien';
	  }
	  
	  
}
  
function limpiaCampos() {
	  document.getElementById("secId1").style.display = 'none'; 
	  document.getElementById("secId2").style.display = 'none';
	  document.getElementById("secId3").style.display = 'none';
	  document.getElementById("secId4").style.display = 'none';
	  document.getElementById("secId5").style.display = 'block';
	  document.getElementById("secId6").style.display = 'none';
	  
	  document.getElementById("mdIdentificador").value = '0';
	  document.getElementById("mdIdentificador1").value = '';
	  document.getElementById("mdIdentificador2").value = '';
	  
	  document.getElementById("mdFactura1").value = '';
	  document.getElementById("mdFactura2").value = '';
	  	  
	  document.getElementById("mdDescripcion").value = '';	  
	  document.getElementById("mdDescripcion").disabled = true;	  
	  
	  document.getElementById("mdBienEspecial").value  = '0';
	  
	  
	  Materialize.updateTextFields();
}

function limpiaCamposFile() {	  
	  document.getElementById("mdBienEspecial2").value  = '0';
	  document.getElementById("txtspan").innerHTML = '';
	  
	  document.getElementById("exceltable").innerHTML = '<table id="exceltable"></table> ';	  
	  var input = $("#excelfile");
	  var name = $("#namefile");
	  input.replaceWith(input.val('').clone(true));
	  name.replaceWith(name.val('').clone(true));
	  
	  Materialize.updateTextFields();
}
  
  function otroRegistro() {
	  var checkBox = document.getElementById("aRegistro");
	  if (checkBox.checked == true) {
		  document.getElementById("txtregistro").disabled = false;
		  Materialize.updateTextFields();
	  } else {
		  document.getElementById("txtregistro").value  = '';
		  document.getElementById("txtregistro").disabled = true;
		  Materialize.updateTextFields();
	  }
  }
  
</script>
