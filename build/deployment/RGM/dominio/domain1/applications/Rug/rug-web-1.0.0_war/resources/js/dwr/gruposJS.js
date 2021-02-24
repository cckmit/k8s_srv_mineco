
var elementID;
var elementIDDes;


function guardaGrupo(idUsuario,idAcreedor){
	var nombreGrupo = document.getElementById('nombreGrupo').value;
	f = document.forms[0];
	sels = [];
	for (i = 0, s = f["perfiles[]"], total = s.length; i < total; i ++)
		if (s[i].checked) sels.push(s[i].value);
	
	var cadena = sels.join('|');
	if (isNullOrEmpty(document.getElementById("nombreGrupo").value) || cadena.length == 0) {
		alert('Los campos marcados con * son obligatorios.');
	} else {
		GruposDwrAction.guardaGrupo(nombreGrupo,cadena,idUsuario, idAcreedor,showParteGrupo);
	}
}
function guardaCambiosGrupo(idGrupo, idUsuario, idAcreedor){	
	f = document.forms[0];
	var nombreGrupo= getObject('idNombreGrupo').value;
	sels = [];
	for (i = 0, s = f["perfiles[]"], total = s.length; i < total; i ++)
		if (s[i].checked) sels.push(s[i].value);
	
	var cadena = sels.join('|');
	if (cadena.length == 0) {
		alert('Debe de tener al menos un privilegio.');
	} else {
		GruposDwrAction.actualizaGrupo(nombreGrupo,cadena,idGrupo, idUsuario, idAcreedor, showParteGrupo);
	}
}

function isNullOrEmpty(valor){
	if ( valor == null )
		true;
	for ( i = 0; i < valor.length; i++ )
			if ( valor.charAt(i) != " " )
				return false; 
	return true;
}

function getParteGrupo(espacioID, idPersona, idAcreedor){
	
	elementID = espacioID;
	GruposDwrAction.getParteGrupo(idPersona, idAcreedor, showParteGrupo);

}

function showParteGrupo(message){
	if (message.codeError==0){
		fillObject(elementID,message.message);		
		ParceJS(message.message);
	}
}

function showParteDescripcionGrupo(message){
	if (message.codeError==0){
		fillObject(elementIDDes,message.message);		
		ParceJS(message.message);
	}
}

function showSelectGrupo(message){
	if (message.codeError==0){
		fillObject('grup',message.message);		
	}
}
function mostrarAltaGrupo(){
	ocultarAltaGrupo();
	document.getElementById('agreNuevo').style.visibility = 'visible';
	document.getElementById('agreNuevo').style.display = 'block';
	
}

function mostrarDescGrupo(idGrupo){
	elementIDDes = 'descGrupo';
	ocultarDesGrupo();
	document.getElementById('descGrupo').style.visibility = 'visible';
	document.getElementById('descGrupo').style.display = 'block';
	GruposDwrAction.getParteDesGrupo(idGrupo ,showParteDescripcionGrupo);
}

function mostrarEditarGrupo(idGrupo, idUsuario, idAcreedor){
	elementIDDes = 'descGrupo';
	ocultarDesGrupo();
	document.getElementById('descGrupo').style.visibility = 'visible';
	document.getElementById('descGrupo').style.display = 'block';
	GruposDwrAction.getParteEditarGrupo(idGrupo, idUsuario, idAcreedor ,showParteDescripcionGrupo);
}

function ocultarDesGrupo(){
	document.getElementById('descGrupo').style.visibility = 'hidden';
	document.getElementById('descGrupo').style.display = 'none';
}

function ocultarAltaGrupo(){
	document.getElementById('agreNuevo').style.visibility = 'hidden';
	document.getElementById('agreNuevo').style.display = 'none';
}
//VAriables
var idGrupo, idUsuario, idAcreedor;

function eliminarGrupo(idGrupo, idUsuario, idAcreedor){
		this.idGrupo=idGrupo;
		this.idUsuario=idUsuario;
		this.idAcreedor=idAcreedor;
		
		GruposDwrAction.eliminarGrupoAlerta(idGrupo, idUsuario, idAcreedor ,showEliminarGrupoAlerta);
}
function showEliminarGrupoAlerta(message){
	if (message.codeError==0){
		if (confirm(message.message)){
			GruposDwrAction.eliminarGrupo(this.idGrupo, this.idUsuario, this.idAcreedor ,showParteGrupo);
		}
	}
}

function refrescarGrupo(idAcreedor){
	 GruposDwrAction.refrescarGrupo(idAcreedor,showSelectGrupo);
}

function ParceJS( ObjResponse )
{
    /**************************************
	funcion creada por Abraham Stalin y Felix Diaz
           funcion encargada de recorrer el texto devuelto por el responseText de AJAX, e identificar codigo JavaScript
	y habilitarlos para modo ejecucion desde la pagina llamada

	 Nota: cualquier mejora sobre el codigo hacermela saber
          *************************************/

	 if ( "" == ObjResponse)
	   {
	      alert("No se han enviado parametros a parcear");
	      return false;
	   }
      //variable que almacena el texto del codigo javascript
	  var TextJs = "";
	  //almacena la cadena de texto a recorrer para encontrar el archivo incluido en lso js's
	  var TextSrc="";
	  //arreglo que almacena cada uno de los archivos incluidos llamados por src
	  var FileJsSrc = new Array();
	  var counter=0;
	  //guarda las porciones siguientes de codigo de HTML que se van generando por cada recorrido del parceador
	  var TextNextHtml;
	  var PosJSTagStart;
	  var PosJSTagEnd;
	  //guarda la posicion de la primera ocurrencia del parametro src
	  var SrcPosIni;
	  //guarda la posicion de ocurrencia de las comillas
	  var SrcPosComilla;
	  while (ObjResponse.indexOf("<script") > 0)
	 {
			/*encuentra la primera ocurrencia del tag <script*/
			PosJSTagStart = ObjResponse.indexOf("<script");
            /*corta el texto resultante desde la primera ocurrencia hasta el final del texto					   */
			TextNextHtml = ObjResponse.substring( PosJSTagStart,ObjResponse.length);
			/*encuentra la primera ocurrencia de finalizacion del tag >, donde cierra la palabra javascript*/
            PosJSTagEnd = TextNextHtml.indexOf(">");
            //captura el texto entre el tag <script>
			TextSrc = TextNextHtml.substring(0,PosJSTagEnd);
			//verficica si tiene le texto src de llamado a un archivo js
			if ( TextSrc.indexOf("src") > 0)
			{
				//posicion del src
				 SrcPosIni = TextSrc.indexOf( "src" );
				 //almacena el texto desde la primera aparicion del src hasta el final
				 TextSrc = TextSrc.substring(SrcPosIni, PosJSTagEnd);
				 //lee la posicion de la primer comilla
				 SrcPosComilla = TextSrc.indexOf( '"' );
				 //arma el texto, desde la primer comilla hasta el final,se le suma 1, para pasar la comilla inicial
				 TextSrc = TextSrc.substring(SrcPosComilla + 1,PosJSTagEnd);
				 //posicion de la comilla final
				 SrcPosComilla = TextSrc.indexOf('"');
				 //lee el archivo
				 SrcFileJs = TextSrc.substring(0, SrcPosComilla);
				 FileJsSrc[counter] = SrcFileJs;
				 counter++;

			}

			//TextNextHtml, nuevo porcion de texto HTML empezando desde el tag script
			TextNextHtml = TextNextHtml.substring(PosJSTagEnd + 1,ObjResponse.length);
			//encuentra el final del script
			objJSTagEndSc = TextNextHtml.indexOf("script>");

			/*recorre desde la primera ocurrencia del tag > hasta el final del script < /script>*/
			//se le resta 2 al objJSTagEndSc, para restarle el < /
			objJSText = TextNextHtml.substring(0, objJSTagEndSc - 2);

			ObjResponse = TextNextHtml;
			TextJs = TextJs + "\n" + objJSText;

     }
		//alert("FinalJS\n\n"+objJs);

		// Agrego los scripts dentro del encabezado
		EvalScript = document.createElement("script");
		EvalScript.text = TextJs;
		document.getElementsByTagName('head')[0].appendChild(EvalScript);
		// Agrego los scripts incluidos dentro del encabezado
		for (i = 0; i <  FileJsSrc.length ;i++ )
		{
			EvalScript = document.createElement("script");
			EvalScript.src = FileJsSrc[i];
			document.getElementsByTagName('head')[0].appendChild(EvalScript);
		}
		return true;
		
		
}
