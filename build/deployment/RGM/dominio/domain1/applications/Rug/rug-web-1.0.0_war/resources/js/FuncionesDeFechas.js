/*
 * Lic. Abraham Aguilar Valencia, manejo de fechas.
 * mandando una str con formato de dia, mes, año, te dira si
 * la fecha es menor o igual a la actual. devuelve true o false;
 */


/*
 * Funcion para traer la fecha del servidor
 */
var xmlHttp;
function srvTime(){
	try {
		//FF, Opera, Safari, Chrome
		xmlHttp = new XMLHttpRequest();
	}
	catch (err1) {
		//IE
		try {
			xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
		}
		catch (err2) {
			try {
				xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
			}
			catch (eerr3) {
				//AJAX not supported, use CPU time.
				alert("AJAX not supported");
			}
		}
	}
	//xmlHttp.open('HEAD',window.location.href.toString(),false);
	xmlHttp.open('HEAD','/',false);
	xmlHttp.setRequestHeader("Content-Type", "text/html");
	xmlHttp.send('');
	return xmlHttp.getResponseHeader("Date");
}



function esMenorHoy(fecha){
	var st = srvTime();
	var date = new Date(st);
	var fechaStr = fecha.split('/');		
	var fechaDate = new Date(fechaStr[2] +'/'+fechaStr[1]+'/'+fechaStr[0]);
	//var fechaActual = new Date();
	//alert("fecha enviada"+fechaDate+"fecha servidor"+date+"fecha maquina"+fechaActual);
	if (fechaDate<=date){
		return true;
	}else{
		return false;
	}	
}



/*
 * esta funcion nos regresa true, si la fecha inicial, es menor o igual a la fecha actual.
 * y falso si no lo es. * 
 */
function validaFechas(fechaInicial, fechaFinal){
	var fiarray = fechaInicial.split('/');
	var ffarray = fechaFinal.split('/');
	var dateFI = new Date(fiarray[2] +'/'+fiarray[1]+'/'+fiarray[0]);
	var dateFF = new Date(ffarray[2] +'/'+ffarray[1]+'/'+ffarray[0]);
	if (dateFI <= dateFF){
		return true;
	}else{
		return false;
	}
}

/*
 * la siguiente funcion devuelve true si la fecha es mayor a la actual o igual
 * 
 */
function esMayorHoy(fecha){	
	var fechaStr = fecha.split('/');		
	var fechaDate = new Date(fechaStr[2] +'/'+fechaStr[1]+'/'+fechaStr[0]);
	var fechaActual = new Date();
	if (fechaDate>=fechaActual){
		return true;
	}else{
		return false;
	}	
}
