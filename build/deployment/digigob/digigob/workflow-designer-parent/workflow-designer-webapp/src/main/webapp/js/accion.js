/**
 * 
 */

var elementType = "";
												var newStateFunc;
												var newForkFunc;
												var newJoinFunc;
												
												function command(name,element) {
													this.commandName=name;
													this.jsonData=JSON.stringify(element);
											    }
		
												function SomeState(){
													sendEvent=function(cmd,name, element) {
														var data = JSON.stringify(element);
														zAu.send(new zk.Event(zk.Widget.$(this), 'on'+cmd, data));
													}
												};
												
												function cargarToltip(){
													jQuery(":file").attr("title","Importar Archivo JSON");
												}

												SomeState.prototype.doit = function(x,y){};
												var NothingState = new SomeState();
												var AddState = new SomeState(); 
												var AddStart = new SomeState();
												var AddEnd = new SomeState();
												
												var AddFork = new SomeState();
												var AddJoin = new SomeState();
												
												var panelState = NothingState;

												function nuevoProyecto(data) {												
													$("#shadow").hide();

													zk.Widget.$("$shadowPanel").setVisible(false);
													zk.Widget.$("$shadowPanelElements").setVisible(false);
													
													zk.Widget.$("$panelComponentes").setAutoscroll(true);
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
													
													setProject(data);
													setValidator();
													setCodeChecker();
													setChangeLog();

													getProject().onModifyProject=onModifyProject;
													
													// --- cargo los datos del proyecto ---
													zk.Widget.$("$info").$f('nombreProyecto').setValue(getProject().name);
													zk.Widget.$("$info").$f('autorProyecto').setValue(getProject().author);
													zk.Widget.$("$info").$f('versionProyecto').setValue(getProject().version);
													zk.Widget.$("$info").$f('descripcionProyecto').setValue(getProject().description);

													var panel = jq('$dataInfoProject');

													jq('$dataInfoProject div').css("background-color","#E6E9EB")
													jq('$dataInfoProject td').css("background-color","#E6E9EB")

													jq('$dataInfoProject').height("64px");
													jq('$dataInfoProject').hover(function () {
													        $(this).animate({
													            "height": "200px"
													        }, "slow");
													    }, function () {
													        $(this).animate({
													            "height": "64px"
													        }, 300);
													    });

													drawFromJSON();
													initModifications();
												}

												var onModifyProject = function (){
													if (getProject().getFlagModified()) {
														jq('$dataInfoProject td').css("background-color","#9ecad8")
														jq('$dataInfoProject div').css("background-color","#9ecad8")
														
														saveModification();
													} else {			
														jq('$dataInfoProject td').css("background-color","#E6E9EB")
														jq('$dataInfoProject div').css("background-color","#E6E9EB")
													}
												}
												
												function getOffsetX() {
													zkPanelWidth=parseInt(zk.Widget.$("$panelControles").getWidth(),10)+30;											
													return zkPanelWidth;
												}
		
												function getOffsetY() {
													zkPanelHeight=parseInt(zk.Widget.$("$panelAcciones").getHeight(),10)*-1;
													//console.log("zkPanelHeight -->"+zkPanelHeight);
													return zkPanelHeight;
												}
		
												AddState.doit = function(x,y) {
										        	if (newStateFunc) {
										        		newStateFunc("New Status",function(name){
										        			insertState(x,y,name,true);	// este llamado es asincronico
										        		});
										        	} else { 
										        		var milliseconds = new Date().getTime();
										        		insertState(x,y,"dummy_"+milliseconds,false);
										        	}

										        	panelState = NothingState;
										        	document.getElementById("paper").style.cursor = 'auto';		
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
												};
		
												AddStart.doit = function(x,y) {
													x = x - 30;
													y = y - 30;

													if (getProject().startExist()) {
														zAu.cmd0.alert("No se permite otro inicio", "Error");
													} else {
														var myClone = cloneState(elementType);
														var addedElement=start(x,y,"Inicio",myClone?myClone[0].children[0].outerHTML:null);
														getProject().addStart(addedElement);
													}

										      panelState = NothingState;
										      document.getElementById("paper").style.cursor = 'auto';
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
												};
		
												AddEnd.doit = function(x,y) {
													x = x - 30;
													y = y - 30;

													if (getProject().endExist()) {
														zAu.cmd0.alert("No se permite otro cierre", "Error");
													} else {
														var myClone = cloneState(elementType);												
														var endElement=end(x,y,"Cierre",myClone?myClone[0].children[0].outerHTML:null);
														getProject().addEnd(endElement);
													}

										      panelState = NothingState;
										      document.getElementById("paper").style.cursor = 'auto';
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
												};
		
												function onClickState(element){
													clickState = true;
													elementType = element;
													newStateFunc=null;
													//console.log("onClickState() ########");
													panelState=AddState;
													if (element && element=="state") newStateFunc=nuevoEstado;
													document.getElementById("paper").style.cursor = 'crosshair';
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
													zk.Widget.$(".no-show-task-temp").setVisible(false);
												}
												
												function onClickStart(element){
													clickStart = true;
													//console.log("onClickStart() ########");
													elementType = element;
													panelState=AddStart;
													document.getElementById("paper").style.cursor = 'crosshair';
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
												}
												
												function onClickEnd(element){											
													clickEnd = true;
													//console.log("onClickEnd() ########");
													elementType = element;
													panelState=AddEnd;
													document.getElementById("paper").style.cursor = 'crosshair';
													zk.Widget.$("$shadowEstado").setVisible(true);
													zk.Widget.$("$panelPropiedades").setVisible(false);
													zk.Widget.$("$panelPropiedades").setAutoscroll(false);
												}
												
												function onClickForkOrJoin(element){
													clickForkOrJoin = true;
													elementType = element;
													newForkFunc = null;
													newJoinFunc = null;													
													if (element){
														if(element == 'fork'){
															panelState = AddFork;
															newForkFunc = nuevoFork;
														}
														else{
															if(element == 'join'){
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
												
												AddFork.doit = function(x,y){
													if(newForkFunc && clickForkOrJoin){
														newForkFunc("Nueva División",function(name){
															//insertState(x,y,name,false);
															insertFork(x,y,name,false);
															insertJoin(x+80,y,name,true);
														});
														clickForkOrJoin = false;
													}
												}
												
												AddJoin.doit = function(x,y){
													if(newJoinFunc && clickForkOrJoin){
														newJoinFunc("Nueva Unión",function(name){
															insertJoin(x,y,name,true);
														});
														clickForkOrJoin = false;
													}
												}									
												
												function cloneState(elementName){
													if (elementName && (elementName=="state" || elementName == 'join' || elementName == 'fork')) return null;
													var newSVG = $("#"+elementName).clone();													
													if (newSVG) {
														$(newSVG).children().removeAttr("onclick");											
													}
													return newSVG;
												}
		
												function insertState(x,y,name,modifiable) {
													if (getProject().stateExist(name)) {
														zAu.cmd0.alert("Ya existe el estado: " + name, "Error");
													} else if(getProject().stateNameIsReserved(name)){
														zAu.cmd0.alert("El nombre de estado: '" + name + "' tiene un componente propio", "Error");
													} else {
														if (name && name.length>0) {
															var myClone = cloneState(elementType);
															var stateElement = state(x,y,name,(myClone?myClone[0].children[0].outerHTML:null),modifiable);
															getProject().addState(stateElement,elementType, null);
															document.getElementById("paper").style.cursor = 'auto';												
														}
													}
												}
												
												function insertFork(x,y,name,modifiable) {
													if (getProject().forkJoinExist(name)) {
														zAu.cmd0.alert("Ya existe el estado: " + name, "Error");
													} else if(getProject().stateNameIsReserved(name)){
														zAu.cmd0.alert("El nombre de estado: '" + name + "' tiene un componente propio", "Error");
													} else {
														if (name && name.length>0) {
															var myClone = cloneState(elementType);
															var stateElement = fork(x,y,name,(myClone?myClone[0].children[0].outerHTML:null),modifiable);
															getProject().addFork(stateElement);
															document.getElementById("paper").style.cursor = 'auto';												
														}
													}
												}
												
												function insertJoin(x,y,name,modifiable) {
													if (getProject().forkJoinExist(name)) {
														zAu.cmd0.alert("Ya existe el estado: "+name, "Error");
													} else if(getProject().stateNameIsReserved(name)){
														zAu.cmd0.alert("El nombre de estado: '" + name + "' tiene un componente propio", "Error");
													} else {
														if (name && name.length>0) {
															var myClone = cloneState(elementType);
															var stateElement = join(x,y,name,(myClone?myClone[0].children[0].outerHTML:null),modifiable);
															getProject().addJoin(stateElement);
															document.getElementById("paper").style.cursor = 'auto';												
														}
													}
												}
		
												function nuevoEstado(windowTitle,callback, defaultName) {
										        	var hlayout = new zul.box.Hlayout({
										        		children: [
										        			new zul.wgt.Label({value: 'Staus Name: '}),
										        			new zul.inp.Textbox({
										        				id: 'stateName',
												        		type: 'text',
												        		width: '100px',
												        		maxlength: '100',
												        		constraint: 'no empty'
												        	})
										        		]
										        	});
		
										        	var divBtn = new zul.wgt.Div({
										        		align: 'center',
										        		width: '100%',
										        		children: [
										        			new zul.box.Hlayout({
										        				width:'100%',
										        				children: [
														        	new zul.wgt.Button({
															            label: 'Aceptar',
															            listeners:  {
															                onClick: function (evt) {
															                	callback(zk.Widget.$("$nuevoEstado").$f('stateName').getValue());
															                    zk.Widget.$("$nuevoEstado").detach();
															                }
															            }
															        }),
															        new zul.wgt.Separator({
															        	width:'20px'
															    	}),
														        	new zul.wgt.Button({
															            label: 'Cancelar',
															            listeners:  {
															                onClick: function (evt) {											                	
															                    zk.Widget.$("$nuevoEstado").detach();
															                }
															            }
															        })		
															    ]									        
													        })											        
										        		]
										        	});
		
										        	var vlayout = new zul.box.Vlayout({children:[hlayout,divBtn]});
		
													var win = new zul.wnd.Window({
														id: "nuevoEstado",
													    title: windowTitle,
													    closable: false,
													    border: 'normal',
													    mode: 'modal',
													    width: '300px',
													    parent: zk.Widget.$("$info").getPage(),
													    children: [vlayout]
													});
		
													jq('#z_messages').html(win);

													if (defaultName) {
														zk.Widget.$("$nuevoEstado").$f('stateName').setValue(defaultName);
													}
												}
												
												function nuevoFork(windowTitle,callback,defaultName) {
										    	var hlayout = new zul.box.Hlayout({
										      	children: [
										        	new zul.wgt.Label({value: 'Nombre de la división: '}),
										        	new zul.inp.Textbox({
										        		id: 'forkName',
												        type: 'text',
												        width: '100px',
												        maxlength: '100',
												        constraint: 'no empty'
												      })
										      	]
										      });
		
										      var divBtn = new zul.wgt.Div({
										      	align: 'center',
										        width: '100%',
										        children: [
										        	new zul.box.Hlayout({
										        		width:'100%',
										        		children: [
														    	new zul.wgt.Button({
															    	label: 'Aceptar',
															      	listeners:  {
															        	onClick: function (evt) {
															          	callback(zk.Widget.$("$nuevoFork").$f('forkName').getValue());
															            	zk.Widget.$("$nuevoFork").detach();
															         	}
															        }
															    }),
															    new zul.wgt.Separator({
															    	width:'20px'
															   	}),
														      new zul.wgt.Button({
															    	label: 'Cancelar',
															      listeners:{
															      	onClick: function (evt) {											                	
															        	zk.Widget.$("$nuevoFork").detach();
															        }
															      }
															    })		
																]									        
													    })											        
										      	]
										      });
		
										      var vlayout = new zul.box.Vlayout({children:[hlayout,divBtn]});
		
													var win = new zul.wnd.Window({
														id: "nuevoFork",
													    title: windowTitle,
													    closable: false,
													    border: 'normal',
													    mode: 'modal',
													    width: '300px',
													    parent: zk.Widget.$("$info").getPage(),
													    children: [vlayout]
													});
													jq('#z_messages').html(win);
													if (defaultName) {
														zk.Widget.$("nuevoFork").$f('forkName').setValue(defaultName);
													}
												}
												
												function nuevoJoin(windowTitle,callback,defaultName) {
										    	var hlayout = new zul.box.Hlayout({
										      	children: [
										        	new zul.wgt.Label({value: 'Nombre de la unión: '}),
										        	new zul.inp.Textbox({
										        		id: 'joinName',
												        type: 'text',
												        width: '100px',
												        maxlength: '100',
												        constraint: 'no empty'
												      })
										      	]
										      });
		
										      var divBtn = new zul.wgt.Div({
										      	align: 'center',
										        width: '100%',
										        children: [
										        	new zul.box.Hlayout({
										        		width:'100%',
										        		children: [
														    	new zul.wgt.Button({
															    	label: 'Aceptar',
															      	listeners:  {
															        	onClick: function (evt) {
															          	callback(zk.Widget.$("$nuevoJoin").$f('joinName').getValue());
															            	zk.Widget.$("$nuevoJoin").detach();
															         	}
															        }
															    }),
															    new zul.wgt.Separator({
															    	width:'20px'
															   	}),
														      new zul.wgt.Button({
															    	label: 'Cancelar',
															      listeners:{
															      	onClick: function (evt) {											                	
															        	zk.Widget.$("$nuevoJoin").detach();
															        }
															      }
															    })		
																]									        
													    })											        
										      	]
										      });
		
										      var vlayout = new zul.box.Vlayout({children:[hlayout,divBtn]});
		
													var win = new zul.wnd.Window({
														id: "nuevoJoin",
													    title: windowTitle,
													    closable: false,
													    border: 'normal',
													    mode: 'modal',
													    width: '300px',
													    parent: zk.Widget.$("$info").getPage(),
													    children: [vlayout]
													});
													jq('#z_messages').html(win);
													if (defaultName) {
														zk.Widget.$("nuevoJoin").$f('joinName').setValue(defaultName);
													}
												}

												function nuevoDato(windowTitle, childrenParam, variables, callback, cancelCallback) {
													var windowId = variables[0] + "Window";
												    var vlayout = new zul.box.Vlayout({
												        children: childrenParam
												    });

													var divBtn = new zul.wgt.Div({
												        align: 'center',
												        width: '100%',
												        children: [
												            new zul.box.Hlayout({
												                width: '100%',
												                children: [
												                    new zul.wgt.Button({
												                        label: 'Aceptar',
												                        listeners: {
												                            onClick: function(evt) {
												                                var results = [];
												                                for (i = 0; i < variables.length; i++) {
											                              	      results.push(zk.Widget.$("$"+windowId).$f(variables[i]).getValue());
													                            }

												                                callback(results);
												                                zk.Widget.$("$"+windowId).detach();

												                            }
												                        }
												                    }),
												                    new zul.wgt.Separator({
												                        width: '20px'
												                    }),
												                    new zul.wgt.Button({
												                        label: 'Cancelar',
												                        listeners: {
												                            onClick: function(evt) {
												                            				cancelCallback();
												                                zk.Widget.$("$"+windowId).detach();
												                            }
												                        }
												                    })
												                ]
												            })
												        ]
												    });

												    var vlayout = new zul.box.Vlayout({
												        children: [vlayout, divBtn]
												    });

												    var win = new zul.wnd.Window({
												        id: windowId,
												        title: windowTitle,
												        closable: false,
												        border: 'normal',
												        mode: 'modal',
												        parent: zk.Widget.$("$info").getPage(),
												        children: [vlayout]
												    });

												    jq('#z_messages').html(win);

												}
												
												function nuevoSelect(windowTitle, nombre, callback, defaultName) {
										        	var hlayout = new zul.box.Hlayout({
										        		children: [
										        			new zul.wgt.Label({value: nombre + ': '}),
										        			new zul.inp.Combobox({
										        				id: 'destinyCombo',
																readonly: true
												        	})
										        		]
										        	});
													
										        	var divBtn = new zul.wgt.Div({
										        		align: 'center',
										        		width: '100%',
										        		children: [
										        			new zul.box.Hlayout({
										        				width:'100%',
										        				children: [
														        	new zul.wgt.Button({
															            label: 'Aceptar',
															            listeners:  {
															                onClick: function (evt) {
																				var content = zk.Widget.$("$nuevoSelect").$f('destinyCombo').getValue();
																				if(content.length > 0){
																					insertAtCursor(jq('$bigEdit $dataEdited')[0],content);
																				}
															                    zk.Widget.$("$nuevoSelect").detach();
															                }
															            }
															        }),
															        new zul.wgt.Separator({
															        	width:'20px'
															    	}),
														        	new zul.wgt.Button({
															            label: 'Cancelar',
															            listeners:  {
															                onClick: function (evt) {											                	
															                    zk.Widget.$("$nuevoSelect").detach();
															                }
															            }
															        })		
															    ]									        
													        })											        
										        		]
										        	});
		
										        	var vlayout = new zul.box.Vlayout({children:[hlayout,divBtn]});
		
													var win = new zul.wnd.Window({
														id: 'nuevoSelect',
													    title: windowTitle,
													    closable: false,
													    border: 'normal',
													    mode: 'modal',
													    width: '300px',
													    parent: zk.Widget.$("$info").getPage(),
													    children: [vlayout]
													});
		
													jq('#z_messages').html(win);

												}

												function insertAtCursor(myField, myValue) {
												    //IE support
												    if (false && document.selection) {
												        myField.focus();
												        sel = document.selection.createRange();
												        sel.text = myValue;
												    }
												    //MOZILLA and others
												    else if (myField.selectionStart || myField.selectionStart == '0') {
												        var startPos = myField.selectionStart;
												        var endPos = myField.selectionEnd;
												        var lastPos = startPos + myValue.length;
												        myField.value = myField.value.substring(0, startPos)
												            + myValue
												            + myField.value.substring(endPos, myField.value.length);
												        myField.focus();
												        myField.selectionStart = lastPos;
												        myField.selectionEnd = lastPos;
												    } else {
												        myField.value += myValue;
												        myField.focus();
												    }
												}
