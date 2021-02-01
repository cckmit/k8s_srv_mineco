/**
 * 
 */

String.prototype.format = function() {
    var formatted = this;
    for (var i = 0; i < arguments.length; i++) {
        var regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, arguments[i]);
    }
    return formatted;
};



var myCodeChecker = null;

var getCodeChecker = function() {
	if(!myCodeChecker){
		return null;
	}
	return myCodeChecker;
}

var setCodeChecker = function() {
	myCodeChecker = new CodeChecker();
}

var CodeChecker = function(){
	this.msgLogging = [];
}

CodeChecker.prototype = {
	evalCode:function(script){
		var codeToEval = (function() {
			try {
				if(getProject() && getProject().states.length > 0){
					for(i = 0; i < getProject().states.length; i++){
						if(getProject().states[i].hasProperties){
							window[getProject().states[i].attributes.name] = getProject().states[i].attributes.name;
						}
					}
				}	
				eval(script);
			} catch (e) {
				alert(e.message);
			}
		}());
		eval(codeToEval);
		return this.msgLogging;
	},
	log:function(texto) {
		this.msgLogging.push(texto);
		console.log(texto);
	}
}

//-----------------------------
//--------- simulator ---------
//-----------------------------
var runSimulator= function() {
	var isValid = validateProject();
	if(isValid){
		var jsonModel = JSON.stringify(this.graph.toJSON());
			getProject().addModel(jsonModel);
			var json = JSON.stringify(getProject(),function(key,value){
				if (typeof value === 'function') {
					return value.toString();
				} else {
					return value;
				}			
			});
	
		
		zAu.send(new zk.Event(zk.Widget.$(this), 'onRunSimulator', json));
	}
}

var flashCell=function(id){
	var cell = graph.getCell(id);
	if (cell) flash(cell);	
}
/*
var executeState=function(stateName) {
	var celda = getProject().findByName(stateName);
	var cell = graph.getCell(celda.attributes.id);
	flash(cell);

	var jsonData = JSON.stringify(celda);
	zAu.send(new zk.Event(zk.Widget.$(this), 'onRunSimulator', jsonData));
}
*/



