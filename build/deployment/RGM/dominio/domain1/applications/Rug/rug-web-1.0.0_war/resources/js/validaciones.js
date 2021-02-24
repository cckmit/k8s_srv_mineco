// Para usar esta funcion deberas poner en tu componente el maxlength con la longitud maxima q deses
function ismaxlength(obj){
	var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length>mlength){
		alert('"El texto ingresado será truncado a '+mlength+' caracteres, debido a que la información ingresada es mayor a la permitida. Por favor Verifique"  ');
		obj.value=obj.value.substring(0,mlength);
	}

}

function checkLength(countMe) {
    var escapedStr = encodeURI(countMe);
    if (escapedStr.indexOf("	") != -1) {    	
        var count = escapedStr.split("%").length - 1;
        if (count == 0) count++;  //perverse case; can't happen with real UTF-8
        var tmp = escapedStr.length - (count * 3);
        count = count + tmp;
    } else {
        count = escapedStr.length;
    }
    return count;
 }
function valCaracteresMin(cadena,numero){
	var vCadena = getObject(cadena).value;
	var vNumero = getObject(numero).value;;
	
	if (vCadena.length < vNumero){
		return 0;
	}
}

function valCaracteresMax(cadena,numero){
	var vCadena = getObject(cadena).value;
	var vNumero = getObject(numero).value;;
	
	if (vCadena.length > vNumero){
		return 0;
	}
} 

function valSoloNumeros(cadena) {
    var vCadena = (document.all)?e.keyCode:e.which;
    if (vCadena==8) 
    	return true;
    patron = /\d/;
    te = String.fromCharCode(vCadena);
    return patron.test(te); 
} 

function valSoloLetras(cadena) { 
    tecla = (document.all) ? cadena.keyCode : cadena.which;	
    if (tecla==8) return true;
    patron =/[A-Za-zñÑ \s]/; 
    te = String.fromCharCode(tecla);
    return patron.test(te); 
}
