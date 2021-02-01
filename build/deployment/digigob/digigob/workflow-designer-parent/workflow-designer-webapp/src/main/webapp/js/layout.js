// Vars
var camposFFCC = [];
var hintFunctions = [];
var selectedHint = null;
var arrayForkJoinConnections = [];
var arrayJoin = [];
var estadoRealizarPaseDiv;
var forkJoinFunctions = [];
var flagHint = false;
var oldFx = null;
var A1 = "";
var A2 = "";

var ban = 0;

// Editores
var editorStartScript = "";
var editorCopy = "";
var editorValidationCopy = "";
var editorDesicionCopy = "";
var editorFuseTask = "";
var editorFuseGeneric = "";
var editorStartSubprocess = "";
var editorEndSubprocess = "";
var editorStartState = "";
var editorEndState = "";

// "Getters" - "Setters" de editores
var setSelectedHint = function(completion) {
	selectedHint = completion;
}

var getSelectedHint = function() {
	return selectedHint;
}

function setEditor(editor) {
	editorCopy = editor;
}

function getEditor() {
	return editorCopy;	
}

function getEditorStartScript() {
	return editorStartScript;
}

function setEditorStartScript(editor) {
	editorStartScript = editor;
}

function setEditorValidation(editor) {
	editorValidationCopy = editor;
}

function getEditorValidation() {
	return editorValidationCopy;
}

function setEditorDesicion(editor) {
	editorDesicionCopy = editor;
}

function getEditorDesicion() {
	return editorDesicionCopy;
}

function setEditorFuseTask(editor) {
	editorFuseTask = editor;
}

function getEditorFuseTask() {
	return editorFuseTask;
}

function setEditorFuseGeneric(editor) {
	editorFuseGeneric = editor;
}

function getEditorFuseGeneric() {
	return editorFuseGeneric;
}

function setEditorStartSubprocess(editor) {
	editorStartSubprocess = editor;
}

function getEditorStartSubprocess() {
	return editorStartSubprocess;
}

function setEditorEndSubprocess(editor) {
	editorEndSubprocess = editor;
}

function getEditorEndSubprocess() {
	return editorEndSubprocess;
}

function setEditorStartState(editor) {
	editorStartState = editor;
}

function getEditorStartState() {
	return editorStartState;
}

function setEditorEndState(editor) {
	editorEndState = editor;
}

function getEditorEndState() {
	return editorEndState;
}

//===================
//FUNCIONES POR ORDEN
//===================
var addButton = function(container, myLabel, myListener) {
	var button = new zul.wgt.Button({
		label : myLabel,
		width : '100px',
		listeners : {
			onClick : myListener
		}
	});
	container.appendChild(button);
}

var addButtonSeparator = function(container) {
	container.appendChild(new zul.wgt.Separator({
		height : '10px'
	}));
}

var registerHintFunction = function(functionName, scriptType) {
	var functionObject = {
		name : functionName,
		type : scriptType
	}
	
	var found = false;
	
	if (hintFunctions.length == 0) {
		hintFunctions.push(functionObject);
	} else {
		for (var i = 0; i < hintFunctions.length; i++) {
			if (hintFunctions[i].name == functionName
					&& hintFunctions[i].type == scriptType) {
				found = true;
			}
		}
		
		if (!found) {
			hintFunctions.push(functionObject);
		}
	}
}

var insertCmd = function(data) {
	insertAtCursor(jq('$bigEdit $dataEdited')[0], data);
	DivGen(data, A1, A2);
}

var insertEnviarPaseCustom = function(destino) {
	
	var arrayDestinies = [];
	
	if (destino != null) {
		arrayDestinies.push(destino);
	}
	
	var jsonData = JSON.stringify(arrayDestinies);
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onRealizarPase', jsonData));
}

var ingresarFunction = function (title, nombreVariable, functionName) {
	nuevoDato(title,
		[
			new zul.box.Hlayout({
				width: '100%',
				children: [
					new zul.wgt.Label({value : nombreVariable}),
					new zul.inp.Textbox(
					{
							id : 'dataValue',
							type : 'text',
							value : '',
							cols : 20,
							maxlength : '100'
					}) 
				]
			})
		],
		[ "dataValue" ],
		function(nombre) {
			if (nombre[0] != null && !nombre[0].replace(/\s/g,"") == "") {
				var aux = "{0}('{1}'); \n".format(functionName, nombre[0]);
				
				if ($.contextMenu.target) {
					insertAtCursor($.contextMenu.target, aux);
					$.contextMenu.target = null;
					DivGen(aux);
				} else {
					insertAtCursor(jq('$bigEdit $dataEdited')[0], aux);
					DivGen(aux, A1, A2);
				}
			}
			else {
				zAu.cmd0.alert("Debe ingresar un valor", "Error");
				return 'stop';
			}
		},
		function() {
			//
		}
	);
}

var windowForkFunctions = function (title, nombreVariable, functionName) {
	
	
	forkJoinFunctions = [];
	
	var combo = new zul.inp.Combobox({
		id: 'forkJoinCombo', 
		cols: 15, 
		rows: 10, 
		readonly: true, 
		value: "",
		listeners : {
			onSelect : function(evt) {
				if (evt.data.reference.value != null) {						
					
					var destinies = getProject().getDesteniesOfState(evt.data.reference.value);
					if (jq('$forkJoinDiv')[0]) {
						jq('$forkJoinDiv')[0].hidden = false;
						for (var i = 0; i < destinies.length; i++) {
							addButtonSeparator(estadoRealizarPaseDiv);
							estadoRealizarPaseDiv.appendChild(
								new zul.wgt.Label({
									value:'Estado - '+destinies[i]+' '
								})
							);
							estadoRealizarPaseDiv.appendChild(
								new zul.inp.Textbox({
									id : destinies[i],
									type : 'text',
									value : '',
									cols : 20,
									maxlength : '100',
									listeners : {
										onDoubleClick : function(evt) {
											
											oldFx = insertCmd;
											insertCmd = function(data) { 
												
												if (zk.Widget.$("$"+evt.currentTarget.id) != null) {
													insertAtCursor(zk.Widget.$("$"+evt.currentTarget.id)._node,data);
													forkJoinFunctions.push(data);
												} else {
													
													insertAtCursor(jq('$bigEdit $dataEdited')[0], data);
													DivGen(data,A1,A2);
												}
											}
											
											insertEnviarPaseCustom(this.id);
										}
									}
								})	
							);
						}
					}
				}
			}
		}
	});

    for (var i = 0; i <= arrayForkJoinConnections.length - 1; i++) {
		
		var item = zk.Widget.$(combo)._findItem(arrayForkJoinConnections[i].attributes.id);
		
		if (!item) {
			combo.appendChild(new zul.inp.Comboitem({
				id : arrayForkJoinConnections[i].attributes.id,
				value : arrayForkJoinConnections[i].attributes.id,
				label : arrayForkJoinConnections[i].properties.name
			}));
		}
    }
    
	nuevoDato(title,
		[   
			new zul.wgt.Div({
				id:'forkJoinMainDiv',
				align : 'center',
				width : '100%',
				children : [
					new zul.box.Hlayout({
						width: '100%',
						children: [
							new zul.wgt.Label({value : nombreVariable}),
							new zul.wgt.Separator({
								heigth : '20px'
							}),
							combo
						]
					}),
					new zul.box.Vlayout({
						id: 'forkJoinVlayout',
						width : '100%',
						children : [
							estadoRealizarPaseDiv = new zul.wgt.Div({
								id : 'forkJoinDiv',
								hidden: 'true',
								align : 'center',
								width : '100%'
							})
						]
					})
				]
			})
		],
		[ "forkJoinCombo" ],
		function(nombre) {
			var aux = "{0}('{1}'); \n".format(functionName,nombre[0]);
			
			if ($.contextMenu.target) {
				insertAtCursor($.contextMenu.target, aux);
				$.contextMenu.target = null;
				DivGen(aux);
			} else {
				if (functionName.indexOf("paseDivision") >= 0 || functionName.indexOf("paseUnion") >= 0) {
					var forkJoinFunctionsConverted = [];
					for (var i = 0; i < forkJoinFunctions.length; i++) {
						if (forkJoinFunctions[i].indexOf(';') >= 0) {
							//var index = forkJoinFunctions[i].indexOf(';'); // Not used?
							var converted =  forkJoinFunctions[i].substring(0,forkJoinFunctions[i].indexOf(';'));
							
							if (forkJoinFunctions.length - 1 != i) {
								converted = converted + ',';
							}
							
							if (converted.indexOf('pase') >= 0) {
								var otroIndex = converted.indexOf('pase');
								var newConversion = converted.substring(otroIndex+1);
								

								if (functionName.indexOf("paseDivision") >= 0) {
									newConversion = 'forkP' + newConversion;
								} else {
									if (functionName.indexOf("paseUnion") >= 0) {
										newConversion = 'joinP' + newConversion;
									}
								}
								
								forkJoinFunctionsConverted.push(newConversion);
							}
						}
					}
					
					var stringConverted = forkJoinFunctionsConverted.join('');
					aux = "{0}({1}); \n".format(functionName,stringConverted);
					insertAtCursor(jq('$bigEdit $dataEdited')[0], aux);
					DivGen(aux, A1, A2);
					forkJoinFunctions = [];
					estadoRealizarPaseDiv = null;
					insertCmd = oldFx;
				} else {
					insertAtCursor(jq('$bigEdit $dataEdited')[0], aux);
					DivGen(aux, A1, A2);
				}
			}
		},
		function() {
			//
		}
		);
}

var searchFunction = function(selectedHint) {
	var fx = window[selectedHint];
	var clave = "gi"
	var RegEx = new RegExp("[^a-z]\(.*\)\{", clave);
	aux = RegEx.exec(fx);
	var pepe = aux[0];
	pepe = pepe.substring(1, pepe.length - 1);

	if (pepe == "()") {
		pepe = pepe + ";";
		return pepe;
	}

	var txt;
	txt = pepe[0];
	txt = txt + "\'";
	txt = txt + pepe.substring(1, pepe.length - 2);
	txt = txt + "\'";
	txt = txt + ")";
	txt = txt + ";";
	return txt;
}

var initVincularDocumentos = function () {	
	ingresarFunction('Vincular Documentos','Mensaje','habilitarVinculacionDocumentos');
}

var quitarSolapa = function () {	
	ingresarFunction('Deshabilitar solapa','Nombre','deshabilitarSolapa');
}

var paseJoin = function () {	
	windowForkFunctions('Pase Unión','Uniones Disponibles','paseUnion');
}

var paseFork = function () {	
	windowForkFunctions('Pase División','Estados Disponibles','paseDivision');
}

var validationOK = function () {	
	insertCmd("valido(); ");
}

var validationBAD = function () {	
	insertCmd("noValido(); ");
}

var getHintFunctions = function(scriptType) {
	var aux = [];

	for (var i = 0; i < hintFunctions.length; i++) {
		if (hintFunctions[i].type == scriptType) {
			aux.push(hintFunctions[i].name);
		}

		if (hintFunctions[i].type == 'all') {
			aux.push(hintFunctions[i].name);
		}
	}

	return aux;
}

var insertEnviarPase = function () {
	var arrayDestenies = getProject().getDesteniesOfState(selectedState.attributes.id);
	var jsonData = JSON.stringify(arrayDestenies);
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onRealizarPase', jsonData));
}

var insertCallESB = function () {
	var arrayDestenies = getProject().getDesteniesOfState(selectedState.attributes.id);
	var jsonData = JSON.stringify(arrayDestenies);
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onSelectServices', jsonData));
}

var insertApiRestEgoveris = function () {
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onSelectServicesEgoveris', null));
}

var ingresarCampoFFCC = function(title, nombreVariable) {
    var combo = new zul.inp.Combobox({id: 'cmbCamposFFCC', cols: 15, rows: 10, readonly: true, value: ""});
    
    for (var i = 0; i <= camposFFCC.length - 1; i++) {
		combo.appendChild(new zul.inp.Comboitem({
			value : camposFFCC[i].nombre,
			label : camposFFCC[i].nombre,
			description : camposFFCC[i].etiqueta
		}));
	}
    
	nuevoDato(title, [	
		new zul.box.Hlayout({
				width: '100%',
				children: [
					new zul.wgt.Label({value : nombreVariable}),
					combo 
				]
			})
		],
		[ "cmbCamposFFCC" ],
		function(nombre) {
			if (nombre[0] != null && !nombre[0].replace(/\s/g,"") == "") {
				var aux = " {0}.get('{1}') ".format(nombreVariable,nombre[0]);
				
				if ($.contextMenu.target) {
					insertAtCursor($.contextMenu.target, aux);
					$.contextMenu.target = null;
				} else {
					insertAtCursor(jq('$bigEdit $dataEdited')[0], aux);
					DivGen(aux, A1, A2);
				}
			}
			else {
				zAu.cmd0.alert("Debe seleccionar un valor", "Error");
				return 'stop';
			}
		},
		function() {
			//
		}
	)
}

var getAllComponentsForms = function() {
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onAllComponentsForms', null));
}

var ingresarVariable = function(title, nombreVariable) {
	nuevoDato(title,
		[
			new zul.box.Hlayout({
				width: '100%',
				children: [	
					new zul.wgt.Label({value : nombreVariable}),
					new zul.inp.Textbox({
						id : 'dataValue',
						type : 'text',
						value : '',
						cols : 20,
						maxlength : '100'
					}) 
				]
			})
		],
		[ "dataValue" ],
		function(nombre) {
			if (nombre[0] != null && !nombre[0].replace(/\s/g,"") == "") {
				var aux = " {0}['{1}'] ".format(nombreVariable, nombre[0]);
				
				if ($.contextMenu.target) {
					insertAtCursor($.contextMenu.target, aux);
					$.contextMenu.target = null;
					DivGen(aux);
				} else {
					insertAtCursor(jq('$bigEdit $dataEdited')[0], aux);
					DivGen(aux, A1, A2);
				}
			}
			else {
				zAu.cmd0.alert("Debe ingresar un valor", "Error");
				return 'stop';
			}
		},
		function() {
			//
		}
	)
}

var bigEditButtons = function(container, showButton) {	
	if (showButton) {
		for (var index in buttons) {
			if (buttons.hasOwnProperty(index)) {
				var btn = buttons[index];	
				var newBtn = new zul.wgt.Button({
					id: btn.name,
					label : btn.label,
					tooltiptext : btn.tooltip,
					width:'100px',
					listeners : {
						onClick : function() {
							var actionName = this.getId()+"_onClick";
							var fn = window[actionName];
							if (fn) {
								fn(this.getId(), this.getTooltiptext());
							}
						}
					}
				});
				container.appendChild(newBtn);
			}
		}
	}
}

var insertCondition = function () {
	nuevaCondicion(function(condicion) {
		insertCmd(" if" + condicion + "{\n\n}");
	});
}

var callEvaluate = function () {
	var script = zk.Widget.$("$bigEdit").$f('dataEdited').getValue();
	
	if (script.length > 0) {
		getCodeChecker().evalCode(script);
	} else {
		zAu.cmd0.alert("El script esta vacio", "Error");
	}
}

var insertMsg = function() {
	ingresarFunction('Mensaje al usuario', 'Mensaje', 'Mensaje');
}

/*
 * Function for context menu
 */
function createContextMenu(element) {
	element.className = "cmenu1 " + element.className;
	var menu1 = [];
	var items = {};
	
	for ( var index in buttons) {
		if (buttons.hasOwnProperty(index)) {
			var btn = buttons[index];
			var fnText = [ "[function (menuItem,menu) { \n",
					"		var actionName = '" + btn.name + "_onClick'; \n",
					"		var fn = window[actionName]; \n", "		if (fn) { \n",
					" 		fn('" + btn.name + "', '" + btn.tooltip + "'); \n",
					"		}\n", "}][0] \n" ];
			var fn = eval(fnText.join(""));
			items[btn.name] = fn;
		}
	}
	
	menu1.push(items);
	
	$('.cmenu1').contextMenu(menu1, {
		theme : 'vista'
	});
}

// =================================
//APERTURA POPUPS DE SCRIPTS (TASKS)
//==================================

/**
* Popup Flujo Tareas - Script Iniciacion
* @param title
* @param element
* @returns
*/
function editScriptStart(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
		
		addButton(container, 'Subir Docs', initVincularDocumentos);
		addButton(container, 'Sin solapa', quitarSolapa);
		registerHintFunction('habilitarVinculacionDocumentos', 'scriptInitialize');
		registerHintFunction('deshabilitarSolapa', 'scriptInitialize');
	};
	
	editScriptGeneric(title, element, 0, 'scriptStart', containerFunction, 'editScriptStart', 1);
}

/**
* Popup Flujo Tareas - Script Ejecucion
* @param title
* @param element
* @returns
*/
function editScriptInitialize(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
		
		addButton(container, 'Subir Docs', initVincularDocumentos);
		addButton(container, 'Sin solapa', quitarSolapa);
		registerHintFunction('habilitarVinculacionDocumentos', 'scriptInitialize');
		registerHintFunction('deshabilitarSolapa', 'scriptInitialize');
	};
	
	editScriptGeneric(title, element, 1, 'scriptInitialize', containerFunction, 'editScriptInitialize', 1);
}

/**
* Popup Flujo Tareas - Script Validacion
* @param title
* @param element
* @returns
*/
function editScriptValidation(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
		
		addButton(container, 'Valido', validationOK);
		addButton(container, 'No Valido', validationBAD);
		registerHintFunction('valido', 'scriptValidation');
		registerHintFunction('noValido', 'scriptValidation');
	};
	
	editScriptGeneric(title, element, 2, 'scriptValidation', containerFunction, 'editScriptValidation', 1);
}

/**
* Popup Flujo Tareas - Script Decision
* @param title
* @param element
* @returns
*/
function editScriptDesicion(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
		
		// addButton(container,'Setear Pase',insertEnviarPase);
		// registerHintFunction('irPaso','scriptDesicion');
		
		// Paralelo
		if (selectedState.attributes.type == 'fsa.Join') {
			var forwardConnectionsFromJoin = getProject().getForwardConnectionsFromJoin(selectedState.attributes.id);
			
			for (var i = 0; i < forwardConnectionsFromJoin.length; i++) {
				if (forwardConnectionsFromJoin[i].attributes.type == 'fsa.State') {
					arrayForkJoinConnections = [];
					arrayForkJoinConnections.push(getProject().findById(selectedState.attributes.id));
				}
			}
			
			addButton(container,'Pase Unión', paseJoin);
			registerHintFunction('setearPaseUnion','scriptDesicion');
			
			return;
		}
		
		var forkJoinConnections = getProject().getForkJoinConnections(selectedState.attributes.id);		
		
		if (forkJoinConnections != null) {
			for (i = 0; i < forkJoinConnections.length; i++) {
				if (forkJoinConnections[i].attributes.type == 'fsa.Fork') {
					arrayForkJoinConnections = [];
					arrayForkJoinConnections.push(forkJoinConnections[i]);
					addButton(container,'Pase División', paseFork);
					registerHintFunction('setearPaseDivision','scriptDesicion');
					//If i add the button from above, i don't need to add 'Setear Pase' button
				} else if (forkJoinConnections[i].attributes.type == 'fsa.Join') {
					arrayForkJoinConnections = [];
					arrayForkJoinConnections.push(forkJoinConnections[i]);
					//The states connected to a Join, don't need to have the 'Setear Pase' Button
					//addButton(container,'Pase Unión',paseJoin); not anymore
					//registerHintFunction('setearPaseUnion','scriptDesicion'); not anymore
				}
			}
		} else {
			addButton(container, 'Setear Pase', insertEnviarPase);
			registerHintFunction('irPaso', 'scriptDesicion');
		}
		// End of paralelo
	};
	
	editScriptGeneric(title, element, 3, 'scriptDesicion', containerFunction, 'editScriptDesicion', 1);
}

//=================================
//APERTURA POPUPS DE SCRIPTS - FUSE
//=================================

function editScriptFuseTask(title, element) {
	editScriptGeneric(title, element, 4, 'scriptStart', null, 'editScriptFuseTask', 2);
}

function editScriptFuseGeneric(title, element) {
	editScriptGeneric(title, element, 5, 'scriptStart', null, 'editScriptFuseGeneric', 2);
}

//========================================
//APERTURA POPUPS DE SCRIPTS - SUBPROCESOS
//========================================

function editScriptStartSubprocess(title, element) {
	editScriptGeneric(title, element, 6, 'scriptStartSubprocess', null, 'editScriptStartSubprocess', 3);
}

function editScriptEndSubprocess(title, element) {
	editScriptGeneric(title, element, 7, 'scriptEndSubprocess', null, 'editScriptEndSubprocess', 3);
}


//========================================
//APERTURA POPUPS DE SCRIPTS STATES - 
//========================================
function editScriptStartState(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
	};
	editScriptGeneric(title, element, 8, 'scriptInitialize', containerFunction, 'editScriptStartState', 4);
}

function editScriptEndState(title, element) {
	var containerFunction = function() {
		var container = zk.Widget.$("$bigEdit").$f('btnActions');
	};
	editScriptGeneric(title, element, 9, 'scriptInitialize', containerFunction, 'editScriptEndState', 4);
}

//==================================
//FUNCION GENERICA POPUPS DE SCRIPTS
//==================================

/**
* (Refactorized) Funcion generica que levanta el popup de script
* 
* @param title Titulo de la ventana
* @param element
* @param ban
* @param hintFunction
* @param containerFunction
* @param calledFrom Funcion desde la cual es invocada
* @param type [1 = TASK | 2 = FUSE | 3 = SUBPROCESS]
* @returns
*/
function editScriptGeneric(title, element, ban, hintFunction, containerFunction, calledFrom, type) {
	this.ban = ban;
	
	if (type == 1) {
		// TASK
		bigEdit(title, function(data) {
			element.setValue(data);
			propertiesChange();
		}, jq(element)[0].value);
	}
	else if (type == 2) {
		// FUSE
		bigEdit(title, function(data) {
			element.setValue(data);
			propertiesChange();
		}, jq(element)[0].value, 'FUSE');
	}if (type == 3) {
		// SUBPROCESS
		bigEdit(title, function(data) {
			element.setValue(data);
		}, jq(element)[0].value);
	} else if(type == 4){
		// STATES
		bigEdit(title, function(data) {
			element.setValue(data);
		}, jq(element)[0].value, 'STATE');
		
	}
	
	var container = zk.Widget.$("$bigEdit").$f('btnActions');
	var textarea = jq('$bigEdit $dataEdited')[0];
	
	var valorGlobalVars = true;
	
	// Particularidad caso editScriptFuseGeneric
	if (calledFrom == "editScriptFuseGeneric") {
		valorGlobalVars = false;
	}
	
	var editor = CodeMirror.fromTextArea(document.getElementById(textarea.id),
	{
		lineNumbers : true,
		mode : {
			name : "javascript",
			globalVars : valorGlobalVars
		},
		extraKeys : {
			"Alt-F" : "findPersistent",
			"Ctrl-Space" : "autocomplete",
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
				jq(".CodeMirror.cm-s-default").css("width", "100%");
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen")) {
					cm.setOption("fullScreen", false);
				}
				jq(".CodeMirror.cm-s-default").css("width",
						"620px");
			},
		}
	});
	
	editor.on('cursorActivity', function() {
		A1 = editor.getCursor().line;
		A2 = editor.getCursor().ch;
	});
	
	editor.on("keyup", function(cm, event) {
		var popupKeyCodes = {
			"9" : "tab",
			"13" : "enter",
			"27" : "escape",
			"33" : "pageup",
			"34" : "pagedown",
			"35" : "end",
			"36" : "home",
			"38" : "up",
			"40" : "down"
		}
		
		flagHint = false;
		
		if (!popupKeyCodes[(event.keyCode || event.which).toString()]) {
			var hint = document.getElementsByClassName("CodeMirror-hints")[0];
			
			if (hint) {
				hint.id = "autoCompleteId";
				jq(".CodeMirror-hints").css("z-index", "100000");
			}
			
			var orig = CodeMirror.hint.javascript;
			
			CodeMirror.hint.javascript = function(cm) {
				var inner = orig(cm) || {
					from : cm.getCursor(),
					to : cm.getCursor(),
					list : []
				};
				inner.list = [];
				inner.list = getHintFunctions(hintFunction); // SE DELEGA FUNCION RECIBIDA POR PARAMETRO
				return inner;
			}
	
			var fooHint = CodeMirror.hint.javascript;
			
			CodeMirror.hint.javascript = function(cm) {
				var result = fooHint(cm) || {
					from : cm.getCursor(),
					to : cm.getCursor()
				};
				
				if (result) {
					CodeMirror.on(result, "pick", function(completion) {
						if (completion != null && completion != "") {
							setSelectedHint(completion);
						}
					});
					
					CodeMirror.on(result, "close", function() {
						if (flagHint == false) {
							var temp;
							temp = searchFunction(getSelectedHint());
							flagHint = true;
							var pos = getSelectedHint().length;
							DivGenHint(temp, A1, A2, pos);
						}
					});
				}
				
				return result;
			}
		}
	});

	editor.on("mousedown", function(cm, event) {
		if (event.which == 3) {
			var codemirror = document.getElementsByClassName("CodeMirror cm-s-default")[0];
			createContextMenu(jq(codemirror)[0]);
		}
	});
	
	// Refactor
	setEditorValue(editor, calledFrom);
	
	if (container && containerFunction != null) {
		// SE EJECUTA LA FUNCION PASADA POR PARAMETRO
		containerFunction();
	}
	
	jq("$bigEdit $dataEdited").css("visibility", "hidden");
	jq("$bigEdit $dataEdited").css("height", "0");
	jq("$bigEdit $dataEdited").css("display", "flex");
	jq("$bigEdit $dataEdited").css("width", "620px");
	
	//firstRound(editor);
}

// Refactor
function setEditorValue(editor, calledFrom) {
	switch (calledFrom) {
		case "editScriptStart":
		case "editScriptInitialize":
			setEditor(editor);
			break;
		case "editScriptValidation":
			setEditorValidation(editor);
			break;
		case "editScriptDesicion":
			setEditorDesicion(editor);
			break;
		case "editScriptFuseTask":
			setEditorFuseTask(editor);
			break;
		case "editScriptFuseGeneric":
			setEditorFuseGeneric(editor);
			break;
		case "editScriptStartSubprocess":
			setEditorStartSubprocess(editor);
			break;
		case "editScriptEndSubprocess":
			setEditorEndSubprocess(editor);
			break;
		case "editScriptStartState":
			setEditorStartState(editor);
			break;
		case "editScriptEndState":
			setEditorEndState(editor);
			break;	
		default:
			break;
	}
}

/**
* Funcion sin utilizar, dejada comentada (just in case)
* @param editor
* @returns
*/
/*
function firstRound(editor) {
	var pos = editor.getCursor();

	flagHint = false;
	var hint = document.getElementsByClassName("CodeMirror-hints")[0];
	
	if (hint) {
		hint.id = "autoCompleteId";
		jq(".CodeMirror-hints").css("z-index", "100000");
	}
	
	var orig = CodeMirror.hint.javascript;
	CodeMirror.hint.javascript = function(cm) {
		var inner = orig(cm) || {
			from : cm.getCursor(),
			to : cm.getCursor(),
			list : []
		};
		inner.list = [];
		inner.list = getHintFunctions('scriptInitialize');
		return inner;
	}

	var fooHint = CodeMirror.hint.javascript;
	CodeMirror.hint.javascript = function(cm) {
		var result = fooHint(cm) || {
			from : cm.getCursor(),
			to : cm.getCursor()
		};
		
		if (result) {
			CodeMirror.on(result, "pick", function(completion) {
				if (completion != null && completion != "") {
					setSelectedHint(completion);

				}
			});
			
			CodeMirror.on(result, "close", function() {
				if (flagHint == false) {
					var temp;
					temp = searchFunction(getSelectedHint());
					flagHint = true;
					DivGen(temp, A1, A2);
				}
			});
		}
	
		return result;
	}
}
*/

function DivGen(text, A1, A2) {
	// Refactor
	switch (ban) {
		case 0:
		case 1:
			// Flujo tareas - iniciacion y ejecucion
			getEditor().replaceRange(text, {line: A1, ch: A2},{line: A1, ch: A2});
			break;
		case 2:
			// Flujo tareas - validacion
			getEditorValidation().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 3:
			// Flujo tareas - decision
			getEditorDesicion().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 4:
			// Flujo tareas - fuse - tarea
			getEditorFuseTask().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 5:
			// Flujo tareas - fuse - generico
			getEditorFuseGeneric().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 6:
			// Flujo estados - subproceso - inicio
			getEditorStartSubprocess().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 7:
			// Flujo estados - subproceso - fin
			getEditorEndSubprocess().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 8:
			// Flujo estados  - inicio
			getEditorStartState().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;
		case 9:
			// Flujo estados - fin
			getEditorEndState().replaceRange(text, {line: A1,ch: A2}, {line: A1,ch: A2});
			break;	
		default:
			break;
	}
}

function DivGenHint(text, A1, A2, aux) {
	A2 = A2 + aux;
	
	DivGen(text, A1, A2);
}

//=======
//BIGEDIT
//=======

function bigEdit(windowTitle, callback, defaultName, type) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.inp.Textbox({
			id : 'dataEdited',
			type : 'text',
			multiline : 'true',
			cols : 200,
			rows : 25,
			maxlength : '10000'
		}), new zul.wgt.Div({
			align : 'left',
			width : '100%',
			children : [ new zul.box.Vlayout({
				id : 'btnActions',
				width : '100%',
				children : [ new zul.box.Vlayout({
					id : 'btnContainer',
					height : '20px', // Antes 100px
					width : '100px',
					style : 'overflow-y:auto;'
				}) ]
			}) ]
		}) ]
	});
	
	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [
				new zul.wgt.Button({
					label : 'Aceptar',
					listeners : {
						onClick : function() {
							if (callback) {
								callback(zk.Widget.$("$bigEdit").$f('dataEdited').getValue());
								
								// Refactor
								setSelectedStateScript();
							}
							
							zk.Widget.$("$bigEdit").detach();
							setEditor(null);
							setEditorValidation(null);
							setEditorDesicion(null);
						}
					}
				}), new zul.wgt.Separator({
					width : '20px'
				}), new zul.wgt.Button({
					label : 'Cancelar',
					listeners : {
						onClick : function() {
							zk.Widget.$("$bigEdit").detach();
							setEditor(null);
							setEditorValidation(null);
							setEditorDesicion(null);
						}
					}
				}) ]
		}) ]
	});
	
	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});
	
	var win = new zul.wnd.Window({
		id : "bigEdit",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		parent : zk.Widget.$("$info").getPage(),
		width: '750px',
		children : [ vlayout ]
	});
	
	jq('#z_messages').html(win);
	if(type != 'STATE'){
		bigEditButtons(zk.Widget.$("$bigEdit").$f('btnActions'), true);
	}
	
	var container = zk.Widget.$("$bigEdit").$f('btnActions');
	
	if (container && type != 'STATE') {
		addButton(container, 'Mensaje', insertMsg);
		registerHintFunction('mensaje', 'all');
		addButton(container, 'Condición', insertCondition);
	} else if(type == 'STATE'){
		addButton(container, 'ESB', insertCallESB);
	}
	
	if (type == 'FUSE') {
		addButton(container, 'Setear Pase', insertEnviarPase);
	}
	
	if (defaultName) {
		zk.Widget.$("$bigEdit").$f('dataEdited').setValue(defaultName);
	}
	
	// Button of services aviables in egoveris
	addButton(container, 'SE', insertApiRestEgoveris);
}

// Refactor
function setSelectedStateScript() {
	switch (ban) {
		case 0:
			// Flujo tareas - iniciacion
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditor().getValue();
			selectedState.properties.startScript = getEditor().getValue();
			jq('$stateStart')[0].value = getEditor().getValue();
			break;
		case 1:
			// Flujo tareas - ejecucion
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditor().getValue();
			selectedState.properties.initialize = getEditor().getValue();
			jq('$stateInitialize')[0].value = getEditor().getValue();
			break;
		case 2:
			// Flujo tareas - validacion
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorValidation().getValue();
			selectedState.properties.forwardValidation = getEditorValidation().getValue();
			jq('$stateForwardValidation')[0].value = getEditorValidation().getValue();
			break;
		case 3:
			// Flujo tareas - decision
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorDesicion().getValue();
			selectedState.properties.forwardDesicion = getEditorDesicion().getValue();
			jq('$stateForwardDesicion')[0].value = getEditorDesicion().getValue();
			break;
		case 4:
			// Flujo tareas - fuse - tarea
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorFuseTask().getValue();
			selectedState.properties.scriptFuseTask = getEditorFuseTask().getValue();
			jq('$scriptFuseTask')[0].value = getEditorFuseTask().getValue();
			break;
		case 5:
			// Flujo tareas - fuse - generico
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorFuseGeneric().getValue();
			getProject().scriptFuseGeneric = getEditorFuseGeneric().getValue();
			jq('$scriptFuseGeneric')[0].value = getEditorFuseGeneric().getValue();
			break;
		case 6:
			// Flujo estados - subproceso - inicio
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorStartSubprocess().getValue();
			jq('$stateStartSubprocess')[0].value = getEditorStartSubprocess().getValue(); // Es el id de componente en el ZUL
			break;
		case 7:
			// Flujo estados - subproceso - fin
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorEndSubprocess().getValue();
			jq('$stateEndSubprocess')[0].value = getEditorEndSubprocess().getValue();
			break;
		case 8:
			// Flujo estados - inicio
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorStartState().getValue();
			selectedState.properties.scriptStartState = getEditorStartState().getValue();
			jq('$stateFlowStart')[0].value = getEditorStartState().getValue();
			break;
		case 9:
			// Flujo estados - fin
			jq('$bigEdit $dataEdited')[0].innerHTML = getEditorEndState().getValue();
			selectedState.properties.scriptEndState = getEditorEndState().getValue();
			jq('$stateFlowEnd')[0].value = getEditorEndState().getValue();
			break;		
		default:
			break;
	}
}

/*
 * Function for script edit
 */
function helpEditor(windowTitle, element, selectedState) {
	var hlayout = new zul.box.Hlayout({
		children : [ new zul.inp.Textbox({
			id : 'dataEdited',
			type : 'text',
			multiline : 'true',
			cols : 100,
			rows : 14,
			maxlength : '10000'
		}), new zul.wgt.Div({
			align : 'left',
			width : '100%',
			children : [ new zul.box.Vlayout({
				id : 'btnActions',
				width : '100%',
				children : [ new zul.box.Vlayout({
					id : 'btnContainer',
					height : '100px',
					width : '100px',
					style : 'overflow-y:auto;'
				}) ]
			}) ]
		}) ]
	});
	
	var divBtn = new zul.wgt.Div({
		align : 'center',
		width : '100%',
		children : [ new zul.box.Hlayout({
			width : '100%',
			children : [ new zul.wgt.Button({
				label : 'Aceptar',
				listeners : {
					onClick : function() {
						if (CKEDITOR) {
							var data = "";
							var ckeditorElement = "";
							
							for ( var i in CKEDITOR.instances) {
								data = CKEDITOR.instances[i].getData();
								ckeditorElement = CKEDITOR.instances[i];
							}
							
							if (data != null) {
								innerChange = false;
								propertiesChange(null, ckeditorElement);
							}
							
							zk.Widget.$("$bigEdit").detach();
						}
					}
				}
			}), new zul.wgt.Separator({
				width : '20px'
			}), new zul.wgt.Button({
				label : 'Cancelar',
				listeners : {
					onClick : function() {
						zk.Widget.$("$bigEdit").detach();
					}
				}
			}) ]
		}) ]
	});
	
	var vlayout = new zul.box.Vlayout({
		children : [ hlayout, divBtn ]
	});
	
	var win = new zul.wnd.Window({
		id : "bigEdit",
		title : windowTitle,
		closable : false,
		border : 'normal',
		mode : 'modal',
		parent : zk.Widget.$("$info").getPage(),
		width : '750px',
		children : [ vlayout ]
	});
	
	jq('#z_messages').html(win);
	
	var editor = jq('$bigEdit $dataEdited')[0];
	CKEDITOR.replace(editor);
	
	for ( var i in CKEDITOR.instances) {
		ckeditorElement = CKEDITOR.instances[i];
	}
	
	if (selectedState.hint != "") {
		ckeditorElement.setData(window.atob(selectedState.hint));
	}
}
