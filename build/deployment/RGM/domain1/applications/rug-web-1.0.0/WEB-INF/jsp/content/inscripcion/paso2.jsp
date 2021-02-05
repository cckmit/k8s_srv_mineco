<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.Map"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegioTO"%>
<%@page import="mx.gob.se.rug.seguridad.dao.PrivilegiosDAO"%>
<%@page import="mx.gob.se.rug.seguridad.to.PrivilegiosTO"%>
<%@page import="mx.gob.se.rug.to.UsuarioTO"%>
<%@page import="mx.gob.se.rug.constants.Constants"%>

<main>
	<%
	//Privilegios
	PrivilegiosDAO privilegiosDAO = new PrivilegiosDAO();
	PrivilegiosTO privilegiosTO = new PrivilegiosTO();
	privilegiosTO.setIdRecurso(new Integer(6));
	privilegiosTO = privilegiosDAO.getPrivilegios(privilegiosTO,
			(UsuarioTO) session.getAttribute(Constants.USUARIO));
	Map<Integer, PrivilegioTO> priv = privilegiosTO.getMapPrivilegio();
%>
	<div class="container-fluid">
		<!-- Empieza div cuerpo -->
		<div id="tt">
			<div id="tttop"></div>
			<div id="ttcont"></div>
			<div id="ttbot"></div>
		</div>
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="col s1"></div>
					<div class="col s10">
						<span class="card-title">Inscripci&oacute;n Garant&iacute;a Mobiliaria</span>
						<input type="hidden" name="refInscripcion" id="refInscripcion"
							value="<s:property value='idInscripcion'/>" />
						<div class="row">
							<s:form action="agregarGarantia.do" namespace="inscripcion" theme="simple" id="formS2ag" name="formS2ag"
								acceptcharset="UTF-8" method="post">
								<div class="row">
									<div class="card">
										<div class="card-content">
											<div class="row">
												<span>Bienes en garant&iacute;a si estos no tienen n&uacute;mero de serie:</span>
											</div>
											<div class="row">
												<div class="input-field col s12">
													<s:textarea rows="10" id="descripcionId" cols="80" onchange="replaceValue('descripcionId')"
														name="actoContratoTO.descripcion" />
													<label for="descripcionId">Descripci&oacute;n general</label>
												</div>
											</div>
											<div class="row">
												<span>Bienes en garant&iacute;a si estos tienen n&uacute;mero de serie:</span>
											</div>
											<div class="row">
												<div class="col s12 right-align">
													<a class="btn-floating btn-large waves-effect indigo modal-trigger" onclick="limpiaCampos()"
														href="#frmBien" id="btnAgregar"><i class="material-icons left">add</i></a>
													<a class="btn-floating btn-large waves-effect indigo modal-trigger"
														onclick="limpiaCamposFile()" href="#frmFile" id="btnFile"><i
															class="material-icons left">attach_file</i></a>
												</div>
												<div class="row">
													<div id="divParteDWRBienes"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="card">
										<div class="card-content">
											<div class="row">
												<p>
													<input type="checkbox" name="actoContratoTO.noGarantiaPreviaOt"
														id="actoContratoTO.noGarantiaPreviaOt" value="true" />
													<label for="actoContratoTO.noGarantiaPreviaOt">Declaro que de conformidad con el contrato de
														garant&iacute;a, el deudor declar&oacute; que sobre los bienes en garant&iacute;a no existen
														otro gravamen, anotaci&oacute;n o limitaci&oacute;n previa.</label>
												</p>
											</div>
											<div class="row">
												<p>
													<input type="checkbox" name="actoContratoTO.cambiosBienesMonto"
														id="actoContratoTO.cambiosBienesMonto" value="true" />
													<label for="actoContratoTO.cambiosBienesMonto">Los atribuibles y derivados no esta afectos a
														la Garant&iacute;a Mobiliaria</label>
												</p>
											</div>
											<div class="row">
												<p>
													<input type="checkbox" name="actoContratoTO.garantiaPrioritaria"
														id="actoContratoTO.garantiaPrioritaria" value="true" onclick="esPrioritaria()" />
													<label for="actoContratoTO.garantiaPrioritaria">Es prioritaria la garant&iacute;a
														mobiliaria</label>
												</p>
											</div>
											<div class="row">
												<p>
													<input type="checkbox" name="actoContratoTO.cpRegistro" id="actoContratoTO.cpRegistro"
														value="true" onclick="otroRegistro()" />
													<label for="actoContratoTO.cpRegistro">El bien se encuentra en otro registro</label>
												</p>
											</div>
											<div class="row">
												<div class="input-field col s6">
													<s:textarea name="actoContratoTO.txtRegistro" id="actoContratoTO.txtRegistro"
														disabled="true" />
													<label for="actoContratoTO.txtRegistro">Especifique cual</label>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="card">
										<div class="card-content">
											<span class="card-title">Datos Generales del Contrato de la Garant&iacute;a</span>
											<div class="row">
												<div class="input-field col s12">
													<s:textarea rows="10" cols="80" name="actoContratoTO.instrumentoPub" maxlength="3500"
														id="actoContratoTO.instrumentoPub" />
													<label for="actoContratoTO.instrumentoPub">Informaci&oacute;n general del contrato de la
														Garant&iacute;a(Lugar y Fecha, tipo de documento, monto inicial garantizado, plazo,
														etc.)</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="card">
										<div class="card-content">
											<span class="card-title">Datos Adicionales</span>
											<div class="row">
												<div class="input-field col s12">
													<s:textarea rows="10" cols="80" id="actoContratoTO.otrosTerminos"
														name="actoContratoTO.otrosTerminos" onblur="actualizaCopia()" />
													<label for="actoContratoTO.otrosTerminos">Observaciones Adicionales</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="divider"></div>
								<center>
									<div class='row'>
										<input type="button" class="btn btn-large waves-effect indigo" value="Regresar" id="buttonID"
											onclick="paso1()" />
										<input type="button" class="btn btn-large waves-effect indigo" onclick="msg_guardar()"
											value="Guardar" id="guardarID" />
										<input onclick="seguroContinuar()" class="btn btn-large waves-effect indigo" value="Continuar"
											type="button" id="baceptar" />
									</div>
								</center>
							</s:form>
							<s:if test="actoContratoTO.noGarantiaPreviaOt">
								<script type="text/javascript">
									document.getElementById('actoContratoTO.noGarantiaPreviaOt').checked = 1;
								</script>
							</s:if>
							<s:if test="actoContratoTO.cambiosBienesMonto">
								<script type="text/javascript">
									document.getElementById('actoContratoTO.cambiosBienesMonto').checked = 1;
								</script>
							</s:if>
							<s:if test="actoContratoTO.garantiaPrioritaria">
								<script type="text/javascript">
									document.getElementById('actoContratoTO.garantiaPrioritaria').checked = 1;
								</script>
							</s:if>
							<s:if test="actoContratoTO.cpRegistro">
								<script type="text/javascript">
									document.getElementById('actoContratoTO.cpRegistro').checked = 1;
									document.getElementById("actoContratoTO.txtRegistro").disabled = false;
									Materialize.updateTextFields();
								</script>
							</s:if>
						</div>
					</div>
					<div class="col s1"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		//setActiveTab('cuatroMenu');
		//$("#cuatroMenu").attr("class","linkSelected");
		activaBtn1();
		activaBtn2();
		escondePartes();
	</script>
</main>

<div id="frmBien" class="modal">
	<div class="modal-content">
		<div class="card">
			<div id="frmBienContent" class="card-content">
				<span class="card-title">Bien Especial</span>
				<div class="row">
					<div class="col s1"></div>
					<div class="col s10">
						<div class="row">
							<div class="input-field col s12">
								<s:select name="mdBienEspecial" id="mdBienEspecial" list="listaBienEspecial" listKey="idTipo"
									listValue="desTipo" onchange="cambiaBienesEspeciales()" />
								<label>Tipo Bien Especial</label>
							</div>
						</div>
						<div id="secId4" class="row">
							<div class="input-field col s6">
								<s:textfield name="mdFactura1" id="mdFactura1" cssClass="validate" maxlength="150" />
								<label id="lblMdFactura1" for="mdFactura1">No. Contribuyente Emite:</label>
							</div>
							<div class="input-field col s6">
								<input type="text" name="mdFactura2" class="datepicker" id="mdFactura2" />
								<label id="lblMdFactura2" for="mdFactura2">Fecha: </label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<s:textarea rows="10" id="mdDescripcion" cols="80" name="mdDescripcion" cssClass="materialize-textarea" data-length="500" />
								<label id="lblMdDescripcion" for="mdDescripcion">Descripci&oacute;n del bien </label>
							</div>
						</div>
						<div id="secId1" class="row" style="display: none;">
							<div class="input-field col s3">
								<label id="lblMdIdentificador">Placa</label>
							</div>
							<div class="input-field col s3">
								<select name="mdIdentificador" id="mdIdentificador">
									<option value="0">Seleccione</option>
									<option value="P0">P0</option>
									<option value="A0">A0</option>
									<option value="C0">C0</option>
									<option value="CC">CC</option>
									<option value="CD">CD</option>
									<option value="DIS">DIS</option>
									<option value="M0">M0</option>
									<option value="MI">MI</option>
									<option value="O0">O0</option>
									<option value="TC">TC</option>
									<option value="TE">TE</option>
									<option value="TRC">TRC</option>
									<option value="U0">U0</option>
									<option value="00">00</option>
								</select>
							</div>
							<div class="input-field col s6">
								<s:textfield name="mdIdentificador1" id="mdIdentificador1" cssClass="validate" maxlength="150" />
							</div>
						</div>
						<div id="secId2" class="row" style="display: none;"><span class="col s12 center-align">Y</span></div>
						<div id="secId3" class="row" style="display: none;">
							<div class="input-field col s12">
								<s:textfield name="mdIdentificador2" id="mdIdentificador2" cssClass="validate"  data-length="150" />
								<label id="lblMdIdentificador2" for="mdIdentificador2">VIN</label>
							</div>
						</div>
						<br />
						<center>
							<div id="secId5" class="row">
								<a href="#!" class="btn-large indigo" onclick="add_bien();">Aceptar</a>
							</div>
							<div id="secId6" class="row">
								<a href="#!" id="formBienButton" class="btn-large indigo" onclick="add_bien();">Aceptar</a>
							</div>
						</center>
					</div>
					<div class="col s1"></div>
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
								<s:select name="mdBienEspecial2" id="mdBienEspecial2" list="listaBienEspecial" listKey="idTipo"
									listValue="desTipo" onchange="cambiaBienesEspecialesFile()" />
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
								<a href="#!" class="btn-large indigo" onclick="ExportToTable();">Cargar</a>
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
								<a href="#!" class="modal-close btn-large indigo">Aceptar</a>
							</div>
						</center>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/material-dialog.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/xlsx.core.min.js"></script>
<script type="text/javascript">
	var idTramite = <s:property value = "idInscripcion"/> ;

	cargaParteBienes('divParteDWRBienes', idTramite);
</script>
<script type="text/javascript">
	function add_bien() {

		var idTramite = document.getElementById("refInscripcion").value;
		var mdDescripcion = document.getElementById("mdDescripcion").value;
		var idTipo = document.getElementById("mdBienEspecial").value;
		var mdIdentificador = document.getElementById("mdIdentificador").value;
		var mdIdentificador1 = document.getElementById("mdIdentificador1").value;
		var mdIdentificador2 = document.getElementById("mdIdentificador2").value;

		if (!noVacio(mdDescripcion)) {
			alertMaterialize('Debe ingresar la descripcion del bien especial');
			return false;
		}

		if (idTipo == '2') {
			mdDescripcion = 'Emitido por: ' + document.getElementById("mdFactura1").value + " Fecha: " + document
				.getElementById("mdFactura2").value + " " + mdDescripcion;
		}

		if (idTipo == '1') {
			if (!noVacio(mdIdentificador2)) {
				alertMaterialize('Debe ingresar el VIN del vehiculo');
				return false;
			}
		}

		ParteDwrAction.registrarBien('divParteDWRBienes', idTramite, mdDescripcion, idTipo, mdIdentificador,
			mdIdentificador1, mdIdentificador2, showParteBienes);

		$(document).ready(function () {
			$('#frmBien').modal('close');
		});
	}

	function cambiaBienesEspeciales() {
		var x = document.getElementById("mdBienEspecial").value;

		if (x == '1') {
			document.getElementById("mdDescripcion").disabled = false;
			document.getElementById("secId1").style.display = 'block';
			document.getElementById("secId2").style.display = 'block';
			document.getElementById("secId3").style.display = 'block';
			document.getElementById("secId4").style.display = 'none';

			document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del veh&iacute;culo';
			document.getElementById("lblMdIdentificador2").innerHTML = 'VIN';
		} else if (x == '2') {
			document.getElementById("mdDescripcion").disabled = false;
			document.getElementById("secId1").style.display = 'none';
			document.getElementById("secId2").style.display = 'none';
			document.getElementById("secId3").style.display = 'block';
			document.getElementById("secId4").style.display = 'block';

			document.getElementById("lblMdIdentificador2").innerHTML = 'No. Factura';
			document.getElementById("lblMdDescripcion").innerHTML = 'Observaciones Generales';
		} else if (x == '3') {
			document.getElementById("mdDescripcion").disabled = false;
			document.getElementById("secId1").style.display = 'none';
			document.getElementById("secId2").style.display = 'none';
			document.getElementById("secId3").style.display = 'block';
			document.getElementById("secId4").style.display = 'none';

			document.getElementById("lblMdIdentificador2").innerHTML = 'No. Serie';
			document.getElementById("lblMdDescripcion").innerHTML = 'Descripci&oacute;n del bien';
		}


	}

	function esPrioritaria() {
		var checkBox = document.getElementById("actoContratoTO.garantiaPrioritaria");
		if (checkBox.checked == true) {
			MaterialDialog.alert(
				'<p style="text-align: justify; text-justify: inter-word;">Recuerde: <b>Artï¿½culo 17. Garantia Mobiliria Prioritaria.</b> ' +
				'La publicidad de la garantï¿½a mobiliaria se constituye por medio de la inscripciï¿½n del formulario registral, ' +
				'que haga referencia al carï¿½cter prioritario especial de esta garantï¿½a y que describa los bienes gravadoas por ' +
				'categorï¿½a, sin necesidad de descripciï¿½n pormenorizada. <br> <br>' +
				'Para el caso se consituya respecto de bienes que ' +
				'pasarï¿½n a formar parte del inventario el deudor garante, el acreedor garantizado que financie adquisicion ' +
				'de la garantï¿½a mobiliaria prioritaria deberï¿½ notificar por escrito, en papel o por medio de un documento ' +
				'electrï¿½nico, con anterioridad o al momento de la inscripciï¿½n e esta garantï¿½a, a aquello acreedores garantizados ' +
				'que hayan inscrito previamente garantï¿½as mobiliarias sobre el inventario, a fin de que obtenga un grado de ' +
				'prelacion superior al de estos acreedores. <br><br>' +
				'<b>Artï¿½culo 56. Prelaciï¿½n de la garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes. </b> Una garantï¿½a ' +
				'mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos tendrï¿½ prelaciï¿½n sobre cualquier ' +
				'garantï¿½a anterior que afecte bienes muebles futuros del mismo tipo en ' +
				'posesiï¿½n del deudor garante, siempre y cuando la garantï¿½a mobiliaria ' +
				'prioritaria se constituya y publicite conforme lo establecido por esta ' +
				'ley, aï¿½n y cuando a esta garantï¿½a mobiliaria prioritaria se le haya dado ' +
				'publicidad con posterioridad a la publicidad de la garantï¿½a anterior. La ' +
				'garantï¿½a mobiliaria prioritaria para la adquisiciï¿½n de bienes especï¿½ficos ' +
				'se extenderï¿½ exclusivamente sobre los bienes muebles especï¿½ficos ' +
				'adquiridos y al numerario especï¿½ficamente atribuible a la venta de estos ' +
				'ï¿½ltimos, siempre y cuando el acreedor garantizado cumpla con los ' +
				'requisitos de inscripciï¿½n de la garantï¿½a mobiliaria prioritaria, ' +
				'establecidos en esta ley. </p>', {
					title: 'Garantia Prioritaria', // Modal title
					buttons: { // Receive buttons (Alert only use close buttons)
						close: {
							text: 'close', //Text of close button
							className: 'red', // Class of the close button
							callback: function () { // Function for modal click
								//alert("hello")
							}
						}
					}
				}
			);
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

		document.getElementById("mdBienEspecial").value = '0';


		Materialize.updateTextFields();
	}

	function otroRegistro() {
		var checkBox = document.getElementById("actoContratoTO.cpRegistro");
		if (checkBox.checked == true) {
			document.getElementById("actoContratoTO.txtRegistro").disabled = false;
			Materialize.updateTextFields();
		} else {
			document.getElementById("actoContratoTO.txtRegistro").value = '';
			document.getElementById("actoContratoTO.txtRegistro").disabled = true;
			Materialize.updateTextFields();
		}
	}

	function BindTable(jsondata, tableid) {
		/*Function used to convert the JSON array to Html Table*/
		var columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/
		var idTramite = document.getElementById("refInscripcion").value;
		var mdDescripcion = '';
		var idTipo = document.getElementById("mdBienEspecial2").value;
		var mdIdentificador = '';
		var mdIdentificador1 = '';
		var mdIdentificador2 = '';
		var mdFactura1 = '';
		var mdFactura2 = '';
		var tipoId = '';
		var correcto = 0;
		var limite = 50;

		if (jsondata.length > limite.valueOf()) {
			alertMaterialize('Error!. Solo se pueden cargar ' + limite + ' registros');
			return false;
		}

		if (idTipo == '0') {
			return false;
		}

		for (var i = 0; i < jsondata.length; i++) {
			var row$ = $('<tr/>');
			mdDescripcion = '';
			mdIdentificador = '';
			mdIdentificador1 = '';
			mdIdentificador2 = '';
			mdFactura1 = '';
			mdFactura2 = '';
			correcto = 0;
			tipoId = '';

			for (var colIndex = 0; colIndex < columns.length; colIndex++) {
				var cellValue = jsondata[i][columns[colIndex]];
				if (cellValue == null)
					cellValue = "";
				if (idTipo == '1') {
					if (colIndex == 0) {
						tipoId = cellValue;
						if (cellValue == '1') {
							cellValue = 'Placa';
						} else if (cellValue == '2') {
							cellValue = 'VIN';
						} else {
							cellValue = 'Valor invalido';
							correcto = 1;
						}
					}
					if (colIndex == 1) {
						if (cellValue.length > 25) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							if (tipoId == '1') {
								if (cellValue.includes("-")) {
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
					if (colIndex == 2) {
						if (cellValue.length > 100) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdDescripcion = cellValue;
						}
					}
					if (colIndex > 2) {
						correcto = 1;
					}
				} else if (idTipo == '2') { //Facturas				 
					if (colIndex == 0) {
						if (cellValue.length > 25) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdFactura1 = cellValue;
						}
					}
					if (colIndex == 1) {
						var patt = /^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\/]\d{4}$/;
						console.log(cellValue);
						if (!patt.test(cellValue)) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdFactura2 = cellValue;
						}
					}
					if (colIndex == 2) {
						if (cellValue.length > 25) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdIdentificador2 = cellValue;
						}
					}
					if (colIndex == 3) {
						if (cellValue.length > 100) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdDescripcion = 'Emitido por: ' + mdFactura1 + " Fecha: " + mdFactura2 + " " + cellValue;
						}
					}
					if (colIndex > 3) {
						correcto = 1;
					}
				} else if (idTipo == '3') {
					if (colIndex == 0) {
						if (cellValue.length > 25) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdIdentificador2 = cellValue;
						}
					}
					if (colIndex == 1) {
						if (cellValue.length > 100) {
							cellValue = 'Valor invalido';
							correcto = 1;
						} else {
							mdDescripcion = cellValue;
						}
					}
					if (colIndex > 1) {
						correcto = 1;
					}
				}
				row$.append($('<td/>').html(cellValue));
			}
			if (correcto == 0) {
				ParteDwrAction.registrarBien('divParteDWRBienes', idTramite, mdDescripcion, idTipo, mdIdentificador,
					mdIdentificador1, mdIdentificador2, showParteBienes);
				row$.append('<td><font color="green">Cargado</font></td>');
			} else {
				row$.append('<td><font color="red">Error verifique datos</font></td>');
			}
			$(tableid).append(row$);
		}
	}

	function BindTableHeader(jsondata, tableid) {
		/*Function used to get all column names from JSON and bind the html table header*/
		var columnSet = [];
		var headerTr$ = $('<tr/>');
		for (var i = 0; i < jsondata.length; i++) {
			var rowHash = jsondata[i];
			for (var key in rowHash) {
				if (rowHash.hasOwnProperty(key)) {
					if ($.inArray(key, columnSet) == -1) {
						/*Adding each unique column names to a variable array*/
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
						var workbook = XLSX.read(data, {
							type: 'binary'
						});
					} else {
						var workbook = XLS.read(data, {
							type: 'binary'
						});
					}
					/*Gets all the sheetnames of excel in to a variable*/
					var sheet_name_list = workbook.SheetNames;

					var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
					sheet_name_list.forEach(function (y) {
						/*Iterate through all sheets*/
						/*Convert the cell value to Json*/
						if (xlsxflag) {
							var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
						} else {
							var exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);
						}
						if (exceljson.length > 0 && cnt == 0) {
							BindTable(exceljson, '#exceltable');
							cnt++;
						}
					});
					$('#exceltable').show();
				}
				if (xlsxflag) {
					/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
					reader.readAsArrayBuffer($("#excelfile")[0].files[0]);
				} else {
					reader.readAsBinaryString($("#excelfile")[0].files[0]);
				}
			} else {
				alertMaterialize("Error! Su explorador no soporta HTML5!");
			}
		} else {
			alertMaterialize("Por favor seleccione un archivo de Excel valido!");
		}
	}

	function cambiaBienesEspecialesFile() {
		var x = document.getElementById("mdBienEspecial2").value;

		if (x == '1') {
			document.getElementById("txtspan").innerHTML = 'Los campos del excel son: ' +
				'<p><b>Tipo Identificador</b>, 1 si es Placa y 2 si es VIN<p>' +
				'<p><b>Identificador</b>, maximo 25 caracteres</p>' +
				'<p><b>Descripcion</b>, maximo 100 caracteres</p>';

		} else if (x == '2') {
			document.getElementById("txtspan").innerHTML = 'Los campos del excel son: ' +
				'<p><b>Numero Identificacion Contribuyente</b>, maximo 25 caracteres</p>' +
				'<p><b>Fecha</b>, formato texto DD/MM/YYYY</p>' +
				'<p><b>Numero Factura</b>, maximo 25 caracteres</p>' +
				'<p><b>Descripcion</b>, maximo 100 caracteres</p>';
		} else if (x == '3') {
			document.getElementById("txtspan").innerHTML = 'Los campos del excel son: ' +
				'<p><b>Identificador</b>, maximo 25 caracteres</p>' +
				'<p><b>Descripcion</b>, maximo 100 caracteres</p>';
		}


	}

	function limpiaCamposFile() {
		document.getElementById("mdBienEspecial2").value = '0';
		document.getElementById("txtspan").innerHTML = '';

		document.getElementById("exceltable").innerHTML = '<table id="exceltable"></table> ';
		var input = $("#excelfile");
		var name = $("#namefile");
		input.replaceWith(input.val('').clone(true));
		name.replaceWith(name.val('').clone(true));

		Materialize.updateTextFields();
	}
</script>