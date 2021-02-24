function replaceValue(idValue){
	var cadena = getObject(idValue).value;
	var text='';
	text=cadena.replace(/“/g, "\"").replace(/”/g, "\"").replace(/’/g, "\'").replace(/‘/g, "\'").replace(/	/,"    		");
	getObject(idValue).value = text;
}