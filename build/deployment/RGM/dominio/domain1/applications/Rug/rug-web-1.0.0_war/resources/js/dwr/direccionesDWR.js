function cargaFormDrirecciones(elementId){
	var content="<table class='textoGeneralRojo'>" +
			"<tr>" +
			"	<td style=\"padding-left: 8px;\">Código Postal :</td> " +
			"</tr>" +
			"<tr>" +
			"	<td>  " +
			"		<table>" +
			"			<tr> " +
			"				<td style=\"padding-left: 5px;\" class=\"texto_azul\" width=\"18px\">*</td> " +
			"				<td><input type='text' size='5' maxlength='5' name='codigoPostal' id='codigoPostal' onblur='getDireccionByCP();'></td> " +
			"			</tr> " +
			"   	</table> " +
			"	</td> " +
			"</tr>" +
			"<tr>" +
			"<td id='errorCP'>" +
			"</td>" +
			"</tr>" +
			"<tr>" +
			"<td colspan='2' id='comboDirecciones'>" +
			
			"<table class='textoGeneralRojo'><tr>" +
			"<td style=\"padding-left: 6px;\">Estado:</td>" +
			"<td id='estadoDIV'>" +
			"<select id='idEstado' name='idEstado' onchange='cargaMunicipioDWR();'> " +
			"<option value='-1'>SELECCIONA</option>" +
			"<option value='AGS'>AGUASCALIENTES</option> " +
			"<option value='BCN'>BAJA CALIFORNIA</option> " +
			"<option value='BCS'>BAJA CALIFORNIA SUR</option> " +
			"<option value='CAMP'>CAMPECHE</option> " +
			"<option value='CHIH'>CHIHUAHUA</option> " +
			"<option value='CHIS'>CHIAPAS</option> " +
			"<option value='COAH'>COAHUILA</option> " +
			"<option value='COL'>COLIMA</option> " +
			"<option value='DF'>DISTRITO FEDERAL</option> " +
			"<option value='DGO'>DURANGO</option> " +
			"<option value='GRO'>GUERRERO</option> " +
			"<option value='GTO'>GUANAJUATO</option> " +
			"<option value='HGO'>HIDALGO</option> " +
			"<option value='JAL'>JALISCO</option> " +
			"<option value='MEX'>ESTADO DE MEXICO</option> " +
			"<option value='MICH'>MICHOACAN</option> " +
			"<option value='MOR'>MORELOS</option> " +
			"<option value='NAY'>NAYARIT</option> " +
			"<option value='NL'>NUEVO LEON</option> " +
			"<option value='OAX'>OAXACA</option> " +
			"<option value='PUE'>PUEBLA</option> " +
			"<option value='QRO'>QUERETARO</option> " +
			"<option value='QROO'>QUINTANA ROO</option> " +
			"<option value='SIN'>SINALOA</option> " +
			"<option value='SLP'>SAN LUIS POTOSI</option> " +
			"<option value='SON'>SONORA</option> " +
			"<option value='TAB'>TABASCO</option> " +
			"<option value='TAM'>TAMAULIPAS</option> " +
			"<option value='TLAX'>TLAXCALA</option> " +
			"<option value='VER'>VERACRUZ</option> " +
			"</select> " +

			"</td>" +
			"</tr>" +
			
			"<tr>" +
			"<td style=\"padding-left: 6px;\">Delegación/Municipio:</td>" +
			"<td id='municipioDIV'>" +
			"<select id='idMunicipio' name='idMunicipio' onchange='cargaColoniaLocalidadDWR();'>" +
			"<option value='-1'>SELECCIONA</option>" +
			"</select> " +
			"</td>" +
			"</tr>" +
			
			"<tr>" +
			"<td id='coloniaLocalidadDIV' colspan='2'>" +
			"<table width='100%' class='textoGeneralRojo'><tr>" +
			"<td style=\"padding-left: 4px;\">Colonia:</td>" +
			"<td id='coloniaDIV'>" +
			"<select id='idColonia' name='idColonia' onchange='setCPbyColonia();'>" +
			"<option value='-1'>SELECCIONA</option>" +
			"</select> " +
			"</td>" +
			"</tr>" +
			
			
			"<tr>" +
			"<td align='center' colspan='2'>" +
			"o" +
			"</td>" +
			"</tr>" +

			
			"<tr>" +
			"<td style=\"padding-left: 4px;\">Localidad:</td>" +
			"<td id='localidadDIV'>" +
			"<select id='idLocalidad' name='idLocalidad' onchange='setCPbyLocalidad();'>" +
			"<option value='-1'>SELECCIONA</option>" +
			"</select> " +
			"</td>" +
			"</tr>" +
			"</table>"+
			"</td>" +
			"</tr>" +
			"</table>"+
			
			"</td>" +
			"</tr>" +
			"</table>";
	fillObject(elementId,content);
}

function getDireccionByCP(){
	displayLoader(true);
	var cp= getObject('codigoPostal').value;
	DireccionesDwrAction.codigoPostal(cp,cargaDireccionByCP);
	
}
function cargaDireccionByCP(message){
	if(message.codeError==0){
		fillObject('comboDirecciones',message.message);
	}else{
		fillObject('errorCP',message.message);
	}
	displayLoader(false);
}

function cargaMunicipioDWR(){
	if(getObject('idEstado').value!=-1){
		DireccionesDwrAction.getMunicipios(getObject('idEstado').value,fillMunicipio);
	}
	
}
function fillMunicipio(message){
	fillObject('municipioDIV',message.message);
}
function cargaColoniaLocalidadDWR(){
	if(getObject('idMunicipio').value!=-1&&getObject('idEstado').value!=-1){
	DireccionesDwrAction.getColoniaLocalidad(getObject('idMunicipio').value,getObject('idEstado').value,fillColoniaLocalidad);
	}
}
function fillColoniaLocalidad(message){
	fillObject('coloniaLocalidadDIV',message.message);
}
function setCPbyColonia(){
	if(getObject('idColonia').selectedIndex!=0){
		getObject('idLocalidad').selectedIndex=0;
		DireccionesDwrAction.getCPByColonia(getObject('idColonia').value,fillCP);
	}
}
function setCPbyLocalidad(){
	if(getObject('idLocalidad').selectedIndex!=0){
		getObject('idColonia').selectedIndex=0;
		DireccionesDwrAction.getCPByLocalidad(getObject('idLocalidad').value,fillCP);
	}
}
function fillCP(message){
	getObject('codigoPostal').value=message.message;
}