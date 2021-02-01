
Pepe=function(){
	println("comenzando funcion javascript");
};

Pepe.prototype = {
	hola: function(nombre) {
		println("ola k ase "+nombre);
	},
	adios: function(nombre) {
		println("Adiosin..."+nombre);
	},
	saludo: function(nombre) {
		println("hola "+nombre+" !!");
	}
};

//register(Pepe);