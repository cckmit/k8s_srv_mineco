/**
 * 
 */

//Registro de cambios
var changeLog = null;

var getChangeLog = function() {
	if(!changeLog){
		return null;
	}
	return changeLog;
}

var setChangeLog = function() {
	changeLog = new ChangeLog();
}

var getTodayDate = function() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	var hour    = today.getHours();
	var minutes = today.getMinutes();
	var seconds = today.getSeconds();
	
	if(dd<10){
	    dd='0'+dd
	} 
	if(mm<10){
	    mm='0'+mm
	}
	return today = dd+'/'+mm+'/'+yyyy+' '+hour+':'+minutes+':'+seconds;
}

var showPanels = function() {
	var SaveDiv = $('<div z-index="100" id="SaveDiv" style="position:absolute; left: 0px; top: 0px;'+
					'visibility:visible" witdh="100%" height="100%" class="transbox" align="center">'+
					'<p>Guarde el proyecto, no prodrá continuar</p><hbox id="boxDiv"></hbox></div>').appendTo("#paper");

	var SaveButton = $('<button id="SaveButton"></button>');	
		SaveButton.appendTo("#boxDiv");
	
	$("#SaveButton").click(function() {
		saveModel();
	});
	
	$("#SaveButton").height(70);
	$("#SaveButton").width(80);
	$("#SaveButton").append("<img src='/expedientes-web/imagenes/herramientas/toolbar/Guardar.png'/>");
	
	var ExitButton = $('<button id="ExitButton"></button>');	
	    ExitButton.appendTo("#boxDiv");
	
	$("#ExitButton").click(function() {
		cerrarDesigner();
	});
	
	$("#ExitButton").height(70);
	$("#ExitButton").width(80);
	$("#ExitButton").append("<img src='/expedientes-web/imagenes/herramientas/toolbar/Exit.png'/>");

	zk.Widget.$("$panelAcciones").setVisible(false)
	zk.Widget.$("$shadowPanel").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false); 
	zk.Widget.$("$shadowPanelElements").setVisible(true);
	zk.Widget.$("$panelComponentes").setAutoscroll(false); 
}

var hidePanels = function(){
	$("#SaveDiv").remove();
	zk.Widget.$("$panelAcciones").setVisible(true);
	zk.Widget.$("$shadowPanel").setVisible(false);
	zk.Widget.$("$shadowPanelElements").setVisible(false);
	zk.Widget.$("$panelComponentes").setAutoscroll(true);
	zk.Widget.$("$panelPropiedades").setAutoscroll(true);
}

var ChangeLog = function(){
	this.changeLogArray = [];
}

ChangeLog.prototype = {
	addChange:function(nombreProyecto,ver, usuario,accion){		
		if(this.changeLogArray.length < 19){
			var Change = {workflow:nombreProyecto, version:ver , date:getTodayDate(),description:accion};
			this.changeLogArray.push(Change);	
		}
		else{
			zAu.cmd0.alert("Considerá ir guardando...");
			var Change = {workflow:nombreProyecto, version:ver , date:getTodayDate(),description:accion};
			this.changeLogArray.push(Change);
			if(this.changeLogArray.length > 28){
				showPanels();
			}
		}
	},
	getChangeLogArray:function(){
		return this.changeLogArray;
	},
	sendToServer:function(){
		hidePanels();
		sendEvent("GetChangeLog","",this.changeLogArray);
		this.changeLogArray.splice(0,this.changeLogArray.length);
	}
}