var elementType = "";
var newStateFunc;
var newForkFunc;
var newJoinFunc;

function command(name, element) {
	this.commandName = name;
	this.jsonData = JSON.stringify(element);
}

function SomeState() {
	sendEvent = function(cmd, name, element) {
		var data = JSON.stringify(element);
		zAu.send(new zk.Event(zk.Widget.$('$info'), 'on' + cmd, data));
	}
};

function cargarToltip() {
	jQuery(":file").attr("title", "Importar Archivo JSON");
}

SomeState.prototype.doit = function(x, y) {
};
var NothingState = new SomeState();
var AddState = new SomeState();
var AddStart = new SomeState();
var AddEnd = new SomeState();
var AddStateTemp = new SomeState();
var AddFork = new SomeState();
var AddJoin = new SomeState();

var panelState = NothingState;

function nuevoProyecto(data) {
	$("#shadow").hide();

	zk.Widget.$("$shadowPanel").setVisible(false);
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);

	zk.Widget.$("$panelComponentes").setAutoscroll(true);
	zk.Widget.$("$shadowPanelElements").setVisible(false);

	setProject(data);
	setValidator();
	setCodeChecker();
	setChangeLog();

	getProject().onModifyProject = onModifyProject;

	// --- cargo los datos del proyecto ---
	zk.Widget.$("$info").$f('nombreProyecto').setValue(getProject().name);
	zk.Widget.$("$info").$f('autorProyecto').setValue(getProject().author);
	zk.Widget.$("$info").$f('versionProyecto').setValue(getProject().version);
	zk.Widget.$("$info").$f('descripcionProyecto').setValue(
			getProject().description);

	var panel = jq('$dataInfoProject');

	//jq('$dataInfoProject div').css("background-color", "#E6E9EB")
	//jq('$dataInfoProject td').css("background-color", "#E6E9EB")

	jq('$dataInfoProject').height("64px");
	jq('$dataInfoProject').hover(function() {
		$(this).animate({
			"height" : "200px"
		}, "slow");
	}, function() {
		$(this).animate({
			"height" : "64px"
		}, 300);
	});

	zk.Widget.$("$panelElements").setVisible(true);
	drawFromJSON();
	initModifications();
}

var onModifyProject = function() {
	if (getProject().getFlagModified()) {
		jq('$dataInfoProject').children("td").css("background-color", "#9ecad8")
		jq('$dataInfoProject').children("div").css("background-color", "#9ecad8")
		saveModification();
	} else {
		jq('$dataInfoProject').children("td").css("background-color", "#E6E9EB")
		jq('$dataInfoProject').children("div").css("background-color", "#E6E9EB")
	}
}

function getOffsetX() {
	zkPanelWidth = parseInt(zk.Widget.$("$panelControles").getWidth(), 10) + 30;
	return zkPanelWidth;
}

function getOffsetY() {
	zkPanelHeight = parseInt(zk.Widget.$("$panelAcciones").getHeight(), 10)
			* -1;
	// console.log("zkPanelHeight -->"+zkPanelHeight);
	return zkPanelHeight;
}

AddState.doit = function(x, y) {
	if (newStateFunc) {
		newStateFunc("New Status", function(name) {
			insertState(x, y, name, true); // este llamado es asincronico
		});
	} else {
		var milliseconds = new Date().getTime();
		insertState(x, y, "dummy_" + milliseconds, false);
	}

	panelState = NothingState;
	document.getElementById("paper").style.cursor = 'auto';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
};



AddStart.doit = function(x, y) {
	x = x - 30;
	y = y - 30;

	if (getProject().startExist()) {
		zAu.cmd0.alert("No se permite otro Inicio", "Error");
	} else {
		var myClone = cloneState(elementType);
		var addedElement = start(x, y, "Inicio",
				myClone ? myClone[0].children[0].outerHTML : null);
		getProject().addStart(addedElement);
	}

	panelState = NothingState;
	document.getElementById("paper").style.cursor = 'auto';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
};

AddEnd.doit = function(x, y) {
	x = x - 30;
	y = y - 30;

	if (getProject().endExist()) {
		zAu.cmd0.alert("No se permite otro Cierre", "Error");
	} else {
		var myClone = cloneState(elementType);
		var endElement = end(x, y, "Cierre",
				myClone ? myClone[0].children[0].outerHTML : null);
		getProject().addEnd(endElement);
	}

	panelState = NothingState;
	document.getElementById("paper").style.cursor = 'auto';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
};




function onClickState(element) {
	clickState = true;
	elementType = element;
	newStateFunc = null;
	panelState = AddState;
	if (element && (element == "state" || element == "stateTemp"))
		newStateFunc = nuevoEstado;
	document.getElementById("paper").style.cursor = 'crosshair';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
}


function onClickStateTemp(element) {
	clickState = true;
	elementType = element;
	panelState = AddStateTemp;
	newStateFunc = nuevoEstado;
	document.getElementById("paper").style.cursor = 'crosshair';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
}


function onClickStart(element) {
	clickStart = true;
	elementType = element;
	panelState = AddStart;
	document.getElementById("paper").style.cursor = 'crosshair';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
}

function onClickEnd(element) {
	clickEnd = true;
	// console.log("onClickEnd() ########");
	elementType = element;
	panelState = AddEnd;
	document.getElementById("paper").style.cursor = 'crosshair';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
}

function onClickForkOrJoin(element) {
	clickForkOrJoin = true;
	elementType = element;
	newForkFunc = null;
	newJoinFunc = null;
	if (element) {
		if (element == 'fork') {
			panelState = AddFork;
			newForkFunc = nuevoFork;
		} else {
			if (element == 'join') {
				panelState = AddJoin;
				newJoinFunc = nuevoJoin;
			}
		}
	}
	document.getElementById("paper").style.cursor = 'crosshair';
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
}

AddFork.doit = function(x, y) {
	if (newForkFunc && clickForkOrJoin) {
		newForkFunc("Nueva División", function(name) {
			// insertState(x,y,name,false);
			insertFork(x, y, name, false);
			insertJoin(x + 80, y, name, true);
		});
		clickForkOrJoin = false;
	}
}

AddJoin.doit = function(x, y) {
	if (newJoinFunc && clickForkOrJoin) {
		newJoinFunc("Nueva Unión", function(name) {
			insertJoin(x, y, name, true);
		});
		clickForkOrJoin = false;
	}
}

function cloneState(elementName) {
	if (elementName
			&& (elementName == "state" || elementName == 'join' || elementName == 'fork'))
		return null;
	var newSVG = $("#" + elementName).clone();
	if (newSVG) {
		$(newSVG).children().removeAttr("onclick");
	}
	return newSVG;
}

function insertState(x, y, name, modifiable) {
	if (getProject().stateExist(name)) {
		zAu.cmd0.alert("Ya existe el estado: " + name, "Error");
	} else if (getProject().stateNameIsReserved(name)) {
		zAu.cmd0.alert("El nombre de estado: '" + name
				+ "' tiene un componente propio", "Error");
	} else {
		if (name && name.length > 0) {
			var myClone;
			var stateElement;
			var typeState;
			if(elementType == 'stateTemp'){
				stateElement = stateTemp(x, y, name, null ,modifiable);	
				typeState = "temp";
			} else {
				myClone = cloneState(elementType);
				stateElement = state(x, y, name,
						(myClone ? myClone[0].children[0].outerHTML : null),
						modifiable);
				typeState = "normal";
			}
			getProject().addState(stateElement, elementType, typeState);
			document.getElementById("paper").style.cursor = 'auto';
		}
	}
}

function insertFork(x, y, name, modifiable) {
	if (getProject().forkJoinExist(name)) {
		zAu.cmd0.alert("Ya existe el estado: " + name, "Error");
	} else if (getProject().stateNameIsReserved(name)) {
		zAu.cmd0.alert("El nombre de estado: '" + name
				+ "' tiene un componente propio", "Error");
	} else {
		if (name && name.length > 0) {
			var myClone = cloneState(elementType);
			var stateElement = fork(x, y, name,
					(myClone ? myClone[0].children[0].outerHTML : null),
					modifiable);
			getProject().addFork(stateElement);
			document.getElementById("paper").style.cursor = 'auto';
		}
	}
}

function insertJoin(x, y, name, modifiable) {
	if (getProject().forkJoinExist(name)) {
		zAu.cmd0.alert("Ya existe el estado: " + name, "Error");
	} else if (getProject().stateNameIsReserved(name)) {
		zAu.cmd0.alert("El nombre de estado: '" + name
				+ "' tiene un componente propio", "Error");
	} else {
		if (name && name.length > 0) {
			var myClone = cloneState(elementType);
			var stateElement = join(x, y, name,
					(myClone ? myClone[0].children[0].outerHTML : null),
					modifiable);
			getProject().addJoin(stateElement);
			document.getElementById("paper").style.cursor = 'auto';
		}
	}
}

function nuevoEstado(windowTitle, callback, defaultName) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.wgt.Label({
			value : 'Nombre de estado: '
		}), new zul.inp.Textbox({
			id : 'stateName',
			type : 'text',
			width : '100px',
			maxlength : '50',
			constraint : 'no empty'
		}) ]
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								callback(zk.Widget.$("$nuevoEstado").$f(
										'stateName').getValue());
								zk.Widget.$("$nuevoEstado").detach();
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								zk.Widget.$("$nuevoEstado").detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : "nuevoEstado",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		width : '300px',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});

	jq('#z_messages').html(win);

	if (defaultName) {
		zk.Widget.$("$nuevoEstado").$f('stateName').setValue(defaultName);
	}
}


function nuevoEstadoTemp(windowTitle, callback, defaultName) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.wgt.Label({
			value : 'Nombre de estado: '
		}), new zul.inp.Textbox({
			id : 'stateName',
			type : 'text',
			width : '100px',
			maxlength : '50',
			constraint : 'no empty'
		}) ]
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								callback(zk.Widget.$("$nuevoEstado").$f(
										'stateName').getValue());
								zk.Widget.$("$nuevoEstado").detach();
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								zk.Widget.$("$nuevoEstado").detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : "nuevoEstado",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		width : '300px',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});

	jq('#z_messages').html(win);

	if (defaultName) {
		zk.Widget.$("$nuevoEstado").$f('stateName').setValue(defaultName);
	}
}


function nuevoFork(windowTitle, callback, defaultName) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.wgt.Label({
			value : 'Nombre de la división: '
		}), new zul.inp.Textbox({
			id : 'forkName',
			type : 'text',
			width : '100px',
			maxlength : '100',
			constraint : 'no empty'
		}) ]
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								callback(zk.Widget.$("$nuevoFork").$f(
										'forkName').getValue());
								zk.Widget.$("$nuevoFork").detach();
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								zk.Widget.$("$nuevoFork").detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : "nuevoFork",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		width : '300px',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});
	jq('#z_messages').html(win);
	if (defaultName) {
		zk.Widget.$("nuevoFork").$f('forkName').setValue(defaultName);
	}
}

function nuevoJoin(windowTitle, callback, defaultName) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.wgt.Label({
			value : 'Nombre de la unión: '
		}), new zul.inp.Textbox({
			id : 'joinName',
			type : 'text',
			width : '100px',
			maxlength : '100',
			constraint : 'no empty'
		}) ]
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								callback(zk.Widget.$("$nuevoJoin").$f(
										'joinName').getValue());
								zk.Widget.$("$nuevoJoin").detach();
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								zk.Widget.$("$nuevoJoin").detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : "nuevoJoin",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		width : '300px',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});
	jq('#z_messages').html(win);
	if (defaultName) {
		zk.Widget.$("nuevoJoin").$f('joinName').setValue(defaultName);
	}
}

function nuevoDato(windowTitle, childrenParam, variables, callback,
		cancelCallback) {
	var windowId = variables[0] + "Window";
	var vlayout = new zul.box.Vlayout({
		children : childrenParam
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								var results = [];
								for (i = 0; i < variables.length; i++) {
									results.push(zk.Widget.$("$" + windowId)
											.$f(variables[i]).getValue());
								}
								
								if (callback(results) != 'stop') {
									zk.Widget.$("$" + windowId).detach();
								}
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								cancelCallback();
								zk.Widget.$("$" + windowId).detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ vlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : windowId,
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});

	jq('#z_messages').html(win);

}

function nuevoSelect(windowTitle, nombre, callback, defaultName) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.wgt.Label({
			value : nombre + ': '
		}), new zul.inp.Combobox({
			id : 'destinyCombo',
			readonly : true
		}) ]
	});

	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
					new zul.wgt.Button({
						label : 'Ok',
						listeners : {
							onClick : function(evt) {
								var content = zk.Widget.$("$nuevoSelect").$f(
										'destinyCombo').getValue();
								if (content.length > 0) {
									insertAtCursor(
											jq('$bigEdit $dataEdited')[0],
											content);
								}
								zk.Widget.$("$nuevoSelect").detach();
							}
						}
					}), new zul.wgt.Separator({
						width : '20px'
					}), new zul.wgt.Button({
						label : 'Cancel',
						listeners : {
							onClick : function(evt) {
								zk.Widget.$("$nuevoSelect").detach();
							}
						}
					}) ]
		}) ]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : 'nuevoSelect',
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		width : '300px',
		parent : zk.Widget.$("$info").getPage(),
		children : [ vlayout ]
	});

	jq('#z_messages').html(win);

}

function insertAtCursor(myField, myValue) {
	// IE support
	if (false && document.selection) {
		myField.focus();
		sel = document.selection.createRange();
		sel.text = myValue;
	}
	// MOZILLA and others
	else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		var lastPos = startPos + myValue.length;
		myField.value = myField.value.substring(0, startPos) + myValue
				+ myField.value.substring(endPos, myField.value.length);
		myField.focus();
		myField.selectionStart = lastPos;
		myField.selectionEnd = lastPos;
	} else {
		myField.value += myValue;
		myField.focus();
	}
}


/********************************************************************************************************************************/
/**************************************************** DRAWBOARD JS***************************************************************************/


var name;
var selectedState;

function setStateName(val){								
	alert(val);
	name = val;
}

function getStateName(){
	return name;
}

function createState(x,y,z){								
	state(x,y,z);
}

function onClickPaper(){
	var evento = window.event || arguments.callee.caller.arguments[0];
	var x = evento.layerX-40;
	var y = evento.layerY-40;
	panelState.doit(x,y);
}

function onClickAnotherElement(){
	zk.Widget.$("$shadowEstado").setVisible(true);
	zk.Widget.$("$panelPropiedades").setVisible(false);
	zk.Widget.$("$panelPropiedades").setAutoscroll(false);
	selectedState=null;
}

var innerChange = false;

function showProperties(element) {								
	innerChange=true;
	var estado = getProject().findById(element.id);
	if (selectedState && selectedState==estado) {
		return;
	}
	
	selectedState=estado;
	if(zk.Widget.$("$stateFlowStart") != null){
		// STATE
		//Set of values in the properties
		jq("$stateName")[0].value=estado.properties.name||"";
		jq("$stateFlowStart")[0].value = estado.properties.scriptStartState||"";
		jq("$stateFlowEnd")[0].value = estado.properties.scriptEndState||"";
		
	} else {
		// TASK
		//Set of values in the properties
		if (estado.hasProperties) {
			if(estado.attributes.type == 'fsa.Join'){
				zk.Widget.$("$shadowEstado").setVisible(false);
				zk.Widget.$("$panelPropiedades").setVisible(true);
				zk.Widget.$("$panelPropiedades").setAutoscroll(true);
				jq("$stateName")[0].value=estado.properties.name||"";
				if(zk.Widget.$("$stateInitialize") != null){
					jq("$stateForwardDesicion")[0].value = estado.properties.forwardDesicion||"";
						
					zk.Widget.$("$info").$f('stateAcceptReject').setDisabled(true);
					zk.Widget.$("$statePrevio").setDisabled(true);
					zk.Widget.$("$stateTipoDocumentoFFCC").setDisabled(true);
					zk.Widget.$("$stateInitialize").setDisabled(true);
					zk.Widget.$("$stateForwardValidation").setDisabled(true);
					zk.Widget.$("$ayuda").setDisabled(true);											
					zk.Widget.$("$info").$f('stateAcceptFreeTransaction').setDisabled(true);
					zk.Widget.$("$info").$f('stateAcceptShowPassInfo').setDisabled(true);		
		
					zk.Widget.$("$info").$f('stateAcceptReject').setChecked(false);
					zk.Widget.$("$info").$f('stateAcceptFreeTransaction').setChecked(false);
					zk.Widget.$("$info").$f('stateAcceptShowPassInfo').setChecked(false);
					
					zk.Widget.$("$stateInitialize").doDoubleClick_ = function (evt) {}
					zk.Widget.$("$stateForwardValidation").doDoubleClick_ = function (evt) {}
					zk.Widget.$("$ayuda").doDoubleClick_ = function (evt) {}
					
					zk.Widget.$("$statePrevio")._select(null);
					var combo = zul.inp.Combobox.$(jq('$stateTipoDocumentoFFCC')[0]);
					
					var item = zk.Widget.$(combo)._findItem(tipoDocFFCC);
					if (!item) {
						combo._resetForm();
					}
					combo._select(item, null);
					combo.updateChange_();
					cargarCamposFFCC();
					jq("$stateInitialize")[0].value = "";
					jq("$stateForwardValidation")[0].value = "";
					
					jq("$ayuda")[0].value = "";
					
				}
			}
			
			else{
				zk.Widget.$("$shadowEstado").setVisible(false);
				zk.Widget.$("$panelPropiedades").setVisible(true);
				zk.Widget.$("$panelPropiedades").setAutoscroll(true);
				jq("$stateName")[0].value=estado.properties.name||"";
				if(zk.Widget.$("$stateInitialize") != null){
					
					zk.Widget.$("$stateStart").doDoubleClick_ = function (evt){
						editScriptStart('Script Inicialización',this);
					}
					zk.Widget.$("$stateInitialize").doDoubleClick_ = function (evt){
						editScriptInitialize('Script Ejecucion',this);
					}
					
					zk.Widget.$("$stateForwardValidation").doDoubleClick_ = function (evt){
						editScriptValidation('Script Validación',this);
					}
					
					zk.Widget.$("$ayuda").doDoubleClick_ = function (evt){
						helpEditor('Ayuda Workflow', this,selectedState);
					}
					
//					zk.Widget.$("$info").$f('stateAcceptReject').setDisabled(false);
//					zk.Widget.$("$statePrevio").setDisabled(false);
//					zk.Widget.$("$stateTipoDocumentoFFCC").setDisabled(false);
//					zk.Widget.$("$stateInitialize").setDisabled(true);
//					zk.Widget.$("$stateForwardValidation").setDisabled(false);
//					zk.Widget.$("$ayuda").setDisabled(false);											
//					zk.Widget.$("$info").$f('stateAcceptFreeTransaction').setDisabled(false);
//					zk.Widget.$("$info").$f('stateAcceptShowPassInfo').setDisabled(false);
				
					zk.Widget.$("$info").$f('stateAcceptReject').setChecked(estado.properties.acceptReject);
					
					if(zk.Widget.$("$info").$f('stateAcceptReject').getChecked()){
						zk.Widget.$("$statePrevio").setDisabled(false);
						zk.Widget.$("$statePrevio").clear();
						cargarEstadosPrevios();
						
						var statePrevioValue = selectedState.properties.backward;
						var statePrevioCombo = zk.Widget.$("$statePrevio");
						var statePrevioItem  = zk.Widget.$(statePrevioCombo)._findItem(statePrevioValue);
						if(statePrevioItem){
							statePrevioCombo._select(statePrevioItem,statePrevioCombo);
						}else{
							statePrevioCombo._select(null);
						}
					}else{
						zk.Widget.$("$statePrevio")._select(null);
						zk.Widget.$("$statePrevio").setDisabled(true);
					}
															
					var tipoDocFFCC=estado.properties.tipoDocumentoFFCC||"";
					var combo = zul.inp.Combobox.$(jq('$stateTipoDocumentoFFCC')[0]);
					var item = zk.Widget.$(combo)._findItem(tipoDocFFCC);
					
					if (!item) {
						combo._resetForm();
					}
					combo._select(item, null);
					combo.updateChange_();
					cargarCamposFFCC();
					jq("$stateForwardDesicion")[0].value = estado.properties.forwardDesicion||"";
					jq("$stateForwardValidation")[0].value = estado.properties.forwardValidation||"";
					jq("$stateInitialize")[0].value = estado.properties.initialize||"";
					jq("$stateStart")[0].value = estado.properties.startScript||"";
					jq("$scriptFuseTask")[0].value = estado.properties.scriptFuseTask||""
					jq("$scriptFuseGeneric")[0].value = getProject().scriptFuseGeneric||""
					
					if(estado.hint != "" && estado.hint!=null){
						jq("$ayuda")[0].value = window.atob(estado.hint);	
					}else{
						if(estado.hint == "")
						jq("$ayuda")[0].value = "";
					}
		
					zk.Widget.$("$info").$f('stateAcceptFreeTransaction').setChecked(estado.properties.acceptTramitacionLibre);
					zk.Widget.$("$info").$f('stateAcceptShowPassInfo').setChecked(estado.properties.showPassInfo);
				}
			}
		}
	}
	
	
	zk.Widget.$("$shadowEstado").setVisible(false);
	zk.Widget.$("$panelPropiedades").setVisible(true);
	zk.Widget.$("$panelPropiedades").setAutoscroll(true);
	zul.inp.Textbox.$(jq("$stateName")[0]).focus(true);
}

function propertiesChange(element,ckeditorElement) {
	if (selectedState) {
		var evento = window.event || arguments.callee.caller.arguments[0];
		var newCellName = jq("$stateName")[0].value;
		
		if (selectedState.properties.name!=newCellName){
			if(getProject().stateExist(newCellName.trim())){
				zAu.cmd0.alert("Ya existe el estado: "+newCellName, "Error");
				jq("$stateName")[0].value = selectedState.properties.name;
				modifyCellNameById(selectedState.attributes.id,selectedState.properties.name);	
			}else{
				if(getProject().stateNameIsReserved(newCellName.trim())){
					zAu.cmd0.alert("El nombre de estado: '" + newCellName + "' tiene un componente propio", "Error");
					jq("$stateName")[0].value = selectedState.properties.name;
					modifyCellNameById(selectedState.attributes.id,selectedState.properties.name);	
				}
				else{
					modifyCellNameById(selectedState.attributes.id,newCellName);	
				}
			}
		}
		
		selectedState.properties.name = newCellName;
		selectedState.attributes.name = newCellName;
		if(zk.Widget.$("$info").$f('stateAcceptReject') != null){
			selectedState.properties.acceptReject = zk.Widget.$("$info").$f('stateAcceptReject').getChecked();
			
			if(zk.Widget.$("$info").$f('stateAcceptReject').getChecked()){
				zk.Widget.$("$statePrevio").setDisabled(false);
															
				if(zk.Widget.$("$statePrevio")._sel){
					if(selectedState.properties.backward != zk.Widget.$("$statePrevio")._sel._label){
						getProject().setFlagModified(true);
						selectedState.properties.backward = zk.Widget.$("$statePrevio")._sel._label;
					}
				}else{
					selectedState.properties.backward = "";
				}
			}else{
				zk.Widget.$("$statePrevio").setDisabled(true);
				zk.Widget.$("$statePrevio")._select(null);
				selectedState.properties.backward = "";
			}
			
			var combo = zul.inp.Combobox.$(jq('$stateTipoDocumentoFFCC')[0]);

			if (combo.getValue()=='Seleccione...') {
				selectedState.properties.tipoDocumentoFFCC = null;
			} else {
				if(zk.Widget.$("$stateTipoDocumentoFFCC")._sel){
					if(selectedState.properties.tipoDocumentoFFCC != zk.Widget.$("$stateTipoDocumentoFFCC")._sel._label){
						getProject().setFlagModified(true);
						selectedState.properties.tipoDocumentoFFCC = zk.Widget.$("$stateTipoDocumentoFFCC")._sel._label;
					}
				}
				else{
					selectedState.properties.tipoDocumentoFFCC = "";
				}
			}
			
			if(element && (element.id == 'statePrevio' || element.id == 'stateTipoDocumentoFFCC')){
				return;
			}
			selectedState.properties.forwardDesicion = jq("$stateForwardDesicion")[0].value;										
			selectedState.properties.forwardValidation = jq("$stateForwardValidation")[0].value;										
			selectedState.properties.initialize = jq("$stateInitialize")[0].value;	
			selectedState.properties.acceptReject = zk.Widget.$("$info").$f('stateAcceptReject').getChecked();	
			selectedState.properties.acceptTramitacionLibre = zk.Widget.$("$info").$f('stateAcceptFreeTransaction').getChecked();
			selectedState.properties.showPassInfo = zk.Widget.$("$info").$f('stateAcceptShowPassInfo').getChecked();
			
		}
	
		if(ckeditorElement){
			selectedState.hint = window.btoa(ckeditorElement.getData());
			 jq("$ayuda")[0].value = ckeditorElement.getData();
		}

		validateLinks(selectedState);
		getProject().setFlagModified(true);
		getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se modificó '"+selectedState.properties.name+"'");
	}
		innerChange=false;									
}

function inicializar() {
	//var paperWidth = $("#paper")[0].clientWidth;

	//A partir de la versión 48 de Google Chrome, getTransformToElement no existe
	SVGElement.prototype.getTransformToElement = SVGElement.prototype.getTransformToElement || function(toElement) {
		return toElement.getScreenCTM().inverse().multiply(this.getScreenCTM());
	};
	
	jq("$bigPanel table").css("width","100%");
	jq("$panelLayout").height("600px");
	var paperWidth = "100%";
	initialize(paperWidth, 768);
}


zk.afterLoad('zul.inp', function() {
    var _Combobox = {};
    zk.override(zul.inp.Combobox.prototype, _Combobox, {
        _updnSel: function (evt, bUp) {
            var inp = this.getInputNode(),
                val = inp.value, sel, looseSel;
            // ZK-2200: the empty combo item should work
            if (val || this._sel) {
                val = val.toLowerCase();
                var beg = this._sel,
                    last = this._next(null, bUp);
                if (!beg || beg.parent != this){
                    beg = this._next(null, !bUp);
                }
                if (!beg) {
                    evt.stop();
                    return; //ignore it
                }

                //Note: we always assume strict when handling up/dn
                for (var item = beg;;) {
                    if (!item.isDisabled() && item.isVisible()) {
                        var label = item.getLabel().toLowerCase();
                        if (val == label) {
                            sel = item;
                            break;
                        } else if (!looseSel && label.startsWith(val)) {
                            looseSel = item;
                            break;
                        }
                    }
                    var nextitem = this._next(item, bUp);
                    if( item == nextitem ) break;  //prevent infinite loop
                    if ((item = nextitem) == beg)
                        break;
                }

                if (!sel)
                    sel = looseSel;

                if (sel) { //exact match
                    var ofs = zk(inp).getSelectionRange();
                    if (ofs[0] == 0 && ofs[1] == val.length){ //full selected
                        sel = this._next(sel, bUp); //next
                    }
                } else{
                    sel = this._next(null, !bUp);
                }
            } else{
                sel = this._next(null, true);
            }

            if (sel)
                zk(sel).scrollIntoView(this.$n('pp'));
            
            if (!val && sel)
                this.fire('onChange', { value: sel._label });

            this._select(sel, {sendOnSelect:true});
            evt.stop();
        }
    });
});