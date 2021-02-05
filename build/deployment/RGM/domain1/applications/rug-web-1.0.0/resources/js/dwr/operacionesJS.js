var elementUsuario;
var elementMO;
var elementOperacion;

function muestraDetalle(idTramite, ruta){
	tb_show('Detalle de la Inscripcion', (ruta +'/home/modalDetalle.do?idInscripcion='+idTramite+'&keepThis=true&TB_iframe=true&height=500&width=800'),'');
	
}

function showStuff (id) 
{
	document.getElementById('tabs-1').style.display = "none";
	document.getElementById('tabs-2').style.display = "none";
	document.getElementById('tabs-3').style.display = "none";
	document.getElementById('tabs-4').style.display = "none";
	
	if (document.getElementById(id).style.display === "none")
    {
        document.getElementById(id).style.display = "block";
    }
}

function eliminaTramite(idTramite, idPersona, ruta, idAcreedor){
	displayLoader(true);
	elementMO = 'idMisOperaciones';
	OperacionesDwrAction.eliminaTramite(idTramite,idPersona, ruta, idAcreedor ,showParteMO);
}

function muestraMOTodos(ruta, idAcreedorStr, idPersona){
	displayLoader(true);
	elementMO = 'idMisOperaciones';
	OperacionesDwrAction.muestraMOTodos(ruta, idAcreedorStr, idPersona, showParteMO);
}

function muestraMO(IdPersona, ruta, idAcreedor){
	displayLoader(true);
	elementMO = 'idMisOperaciones';
	OperacionesDwrAction.muestraMO(IdPersona, ruta, idAcreedor, showParteMO);
}

function showParteMO(message){
	if (message.codeError==0){		
		fillObject(elementMO,message.message);
		ParceJS(message.message);
	}
	displayLoader(false);
}
function getParteUsuarios(elementId, idAcreedor, ruta, idPersona){
	displayLoader(true);
	elementUsuario = elementId;
	OperacionesDwrAction.getParteUsuarios(idAcreedor, ruta, idPersona, showParteUsuarios);
}

function showParteUsuarios(message){
	if (message.codeError==0){		
		fillObject(elementUsuario,message.message);
		ParceJS(message.message);
	}
	displayLoader(false);
}

//Funciones para Implementar La paginaci�n en mis Operaciones Penientes

function iniciaPaginacionPendientes(idPersona, tipoOperacion){
	displayLoader(true);
	
	if(tipoOperacion==1){
		showStuff('tabs-1');
		elementOperacion="OpPendientes"; 
		//document.getElementById('nombreOtorgante').value="";
		//document.getElementById('datepicker1').value="";
		//document.getElementById('datepicker2').value="";
	}
	if(tipoOperacion==2){
		showStuff('tabs-2');
		elementOperacion="OpPenFirma";
		//document.getElementById('nombreOtorgante2').value="";
		//document.getElementById('datepicker3').value="";
		//document.getElementById('datepicker4').value="";
	}
	if(tipoOperacion==3){
		showStuff('tabs-4');
		elementOperacion="OpTerminadas";
		//document.getElementById('nombreOtorgante3').value="";
		//document.getElementById('datepicker7').value="";
		//document.getElementById('datepicker8').value="";
	}
	OperacionesDwrAction.iniciaPagPendientes(idPersona, tipoOperacion, showPendientes);
}

function iniciaPaginacionBoletas(idPersona, tipoOperacion, filtro){
	if(tipoOperacion==1){
		showStuff('tabs-1');
		elementOperacion="BoAprobadas";		
	}
	if(tipoOperacion==2){
		showStuff('tabs-2');
		elementOperacion="BoPendientes";		
	}
	if(tipoOperacion==3){
		showStuff('tabs-3');
		elementOperacion="TraRealizados";		
	}
	
	BoletaDwrAction.iniciaPagBoletas(idPersona, tipoOperacion, filtro, showAprobadas);
}

function showAprobadas(message){
	 if (message.codeError==0){
		 
			fillObject(elementOperacion,message.message);
			ParceJS(message.message);
		}
	 displayLoader(false);
	 
}

function iniciaPaginacionFiltro(idPersona, tipoOperacion, filtro){
	console.log("Persona: ", idPersona, " Operacion: " , tipoOperacion , " filtro:" , filtro );
	displayLoader(true);
	
	if(tipoOperacion==1){
		showStuff('tabs-1');
		elementOperacion="OpPendientes";
		//document.getElementById('nombreOtorgante').value="";
		//document.getElementById('datepicker1').value="";
		//document.getElementById('datepicker2').value="";
	}
	if(tipoOperacion==2){
		showStuff('tabs-2');
		elementOperacion="OpPenFirma";
		//document.getElementById('nombreOtorgante2').value="";
		//document.getElementById('datepicker3').value="";
		//document.getElementById('datepicker4').value="";
	}
	if(tipoOperacion==3){
		showStuff('tabs-4');
		elementOperacion="OpTerminadas";
		//document.getElementById('nombreOtorgante3').value="";
		//document.getElementById('datepicker7').value="";
		//document.getElementById('datepicker8').value="";
	}
	OperacionesDwrAction.iniciaPagFiltro(idPersona, tipoOperacion, filtro, showPendientes);
}

function iniciaPaginacionPenAsMultiples(idPersona){
	displayLoader(true);
	showStuff('tabs-3');
	elementOperacion="OpPenFirmaMasiva";
	//document.getElementById('clvRastreo').value="";
	//document.getElementById('datepicker5').value="";
	//document.getElementById('datepicker6').value="";
	
	OperacionesDwrAction.iniciaPagPenAsiMultiples(idPersona, showPendientes);
}

function pagPendientes(registroTotales,pagActiva,regPagina){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesPendientes(idPersona, registroTotales,pagActiva,'20',showPendientes);
}

function pagBoletasAprobadas(registroTotales,pagActiva,regPagina,filtro){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BoletaDwrAction.pagBoletasAprobadasFiltro(idPersona, filtro, registroTotales,pagActiva,'15',showPendientes);
}

function pagBoletasPendientes(registroTotales,pagActiva,regPagina,filtro){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BoletaDwrAction.pagBoletasPendientesFiltro(idPersona, filtro, registroTotales,pagActiva,'15',showPendientes);
}

function pagTramitesRealizados(registroTotales,pagActiva,regPagina,nombre,fechaInicial,fechaFinal){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	BoletaDwrAction.pagTramitesRealizadosFiltro(idPersona, nombre,fechaInicial,fechaFinal, registroTotales,pagActiva,'15',showPendientes);
}

function pagPendientesFirma(registroTotales,pagActiva,regPagina){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesPendientesFirma(idPersona, registroTotales,pagActiva,'20',showPendientes);
}

function pagPenAsiMultiples(registroTotales,pagActiva,regPagina){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesPenAsienMul(idPersona, registroTotales,pagActiva,'20',showPendientes);
}

function pagPenAsiMultiples(registroTotales,pagActiva,regPagina){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesPenAsienMul(idPersona, registroTotales,pagActiva,'20',showPendientes);
}

function pagOpTerminadas(registroTotales,pagActiva,regPagina){
	
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesTerminadas(idPersona, registroTotales,pagActiva,'20',showPendientes);
}

// function ExportExcelTerminadas(){

// 	let resultado = OperacionesDwrAction.ExportExcel(idPersona,showPendientes);


// }

function pagOpTerminadasFiltro(registroTotales,pagActiva,regPagina, filtro){
	displayLoader(true);
	var idPersona=document.getElementById('idPersona').value;
	OperacionesDwrAction.pagOperacionesTerminadasFiltro(idPersona, filtro, registroTotales,pagActiva,'20',showPendientes);
}

function showPendientes(message){
	 if (message.codeError==0){
		 
			fillObject(elementOperacion,message.message);
			ParceJS(message.message);
		}
	 displayLoader(false);
	 
}

//Funciones para Implementar La paginaci�n en mis Operaciones Penientes de Firma

//function iniciaPaginacionPendientesFirma(idPersona){
//	displayLoader(true);
//	OperacionesDwrAction.iniciaPagPendientesFirma(idPersona,showPendientes);
//}


function ParceJS( ObjResponse )
{
    /**************************************
	funcion creada por Abraham Stalin y Felix Diaz
           funcion encargada de recorrer el texto devuelto por el responseText de AJAX, e identificar codigo JavaScript
	y habilitarlos para modo ejecucion desde la pagina llamada

	 Nota: cualquier mejora sobre el codigo hacernosla saber
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
