/*
function valActiva(){
	var cadena= document.getElementById('nombre').value;
	var num = valLetras(cadena);	
	if (num==0){
		alert('cadena invalida');
	}else{
		alert('cadena valida');
	}
}*/

function valCaracteresMin(cadena,numero){
	
	if (cadena.length < numero){
		return 0;
	}else{
		return 1;
	}

}

function valCaracteresFijo(cadena,numero){
	
	if (cadena.length == numero){
		return 1;
	}else{
		return 0;
	}

}

function valCaracteresMax(cadena,numero){
	
	if (cadena.length > numero){
		return 0;
	}else{
		return 1;
	}
} 

function valNumero(cadena){
 if (isNaN(cadena)){
	return 0;
 }else{
	return 1;
 }
}

function valLetras(cadena){
 var checkOK = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚ" + "abcdefghijklmnñopqrstuvwxyzáéíóú ";
 
 var allValid = true;
 for (i = 0; i < cadena.length; i++) {
  ch = cadena.charAt(i);
  for (j = 0; j < checkOK.length; j++)
   if (ch == checkOK.charAt(j))
    break;
   if (j == checkOK.length) { 
    allValid = false;
    break;
    }
  }
 if (!allValid) {
   return 0;
 }else{
   return 1;
 }
}


function IsNumber(evt) {
	var key = (document.all) ? evt.keyCode : evt.which;
	return (key <= 13 || (key >= 48 && key <= 57));
}

function valEmail(cadena){
    re=/^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/
    if(!re.exec(cadena))    {
        return false;
    }else{
        return true;
    }
}

function validaCurp(cadena){
	var strCorrecta;
	strCorrecta = cadena;	
	if (cadena.length == 18){
		var valid = '^([a-zA-Z]{4,4}[0-9]{6}[a-zA-Z]{6,6}[0-9]{2})';
		
	}else{
		alert('Numero de Caracteres 18 no es Invalido');
		return false;
	}
	var validcurp=new RegExp(valid);
	var matchArray=strCorrecta.match(validcurp);
	if (matchArray==null) {
		alert('CURP incorrecto');
		return false;
	}
	else
	{
		alert('CURP correcto');
		return true;
	}
	
}

function trim(value){
	 return (value.replace(/^(\s|\&nbsp;)*|(\s|\&nbsp;)*$/g,""));
}

 function isBlank(value){
      var val = trim(value);
      if(val==null){
         return true;
      }
      if(val.length==0){
         return true;
      }
      return false;     
}


