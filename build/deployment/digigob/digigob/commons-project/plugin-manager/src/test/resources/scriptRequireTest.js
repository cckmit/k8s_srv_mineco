var prueba=require('prueba');
var pruebaB=require('pruebaB');

var KK = 'pirulo';

VARS['hola']='ola k ase';

//prueba.saludito('kkc');

if (prueba) {
	var retorno = prueba.hola(); 
	println(retorno);
	println(pruebaB.holaMaxi());

} else {
	println("no esta definido el componente 'prueba'");
}

//return VARS['hola'];