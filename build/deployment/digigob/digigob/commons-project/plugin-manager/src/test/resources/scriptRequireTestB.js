loadJS('/scriptRequireTest.js');
loadJS('/scriptRegisterTest.js');

println("Comenzando... ");
var Pepe=require('droolsPlugin'); //droolsPlugin

//http://jqueryjs.googlecode.com/svn
//loadJS('../trunk/jquery/build/runtest/env.js');
//loadJS('http://code.jquery.com/jquery-2.1.4.min.js');

var prueba;

try { 
	Pepe.prototype.saludo=function() { println('esto es una funcion agregada'); return "Maria Luz"; }
	prueba = new Pepe();
} catch (Error) {
	prueba=Pepe;
	
	prueba.getWSDL("http://www.w3schools.com/webservices/tempconvert.asmx?WSDL");
}

println(prueba.saludo('Luz '+KK));

