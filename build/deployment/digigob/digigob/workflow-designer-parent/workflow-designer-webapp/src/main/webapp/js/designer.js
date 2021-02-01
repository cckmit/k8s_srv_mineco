var graph;
var paper;
var selection;
var selectionView;

var cellModified;
var firstProject = false;

function getGridBackgroundImage(gridSize) {
	var canvas = document.createElement('canvas');
	canvas.width = gridSize;
	canvas.height = gridSize;

	if (gridSize > 1) {
		var context = canvas.getContext('2d');
		var control = 0.75
		context.beginPath();
		context.rect(gridSize * control, gridSize * control, 1, 1);
		context.fillStyle = 'black';
		context.fill();
	}

	return canvas.toDataURL('image/png');
};

// "Instancia" estados
function task(x, y, label, markupData, modifiable, selectHandler){
	
	var myAttrs={}

	if (!markupData) {
		markupData= ['<g class="rotatable">',
					'<circle fill="#ffffaa" stroke="#000000" stroke-width="3" cx="37.5" cy="39.5" r="27.2036" />',
					 '<text fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" x="36.5" font-size="12" font-family="serif" text-anchor="middle" xml:space="preserve" font-weight="bold"></text>',
					 '</g>'].join('');
		myAttrs= {
			'circle':{},
			'text': {text: label, ref: 'circle', 'ref-y': 28, 'y-alignment': 'middle'}
		}
	}
	
    var data = {
   		markup: markupData,
		position : {
			x : x,
			y : y
		},
		size : {
			width : 60,
			height : 60
		},
		name : label,
		handler : selectHandler,
		modifiable: modifiable,
		attrs: myAttrs
	};

	var cell = new joint.shapes.fsa.State(data);
	graph.addCell(cell);
	return cell;
};

function stateTemp(x, y, label, markupData, modifiable, selectHandler){
	
	var myAttrs={}

	if (!markupData) {
		markupData= ['<g class="rotatable">',
					'<circle fill="white" stroke="#000000" stroke-width="3" cx="37.5" cy="39.5" r="27.2036" />',
					 '<text fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" ' +  
					 'stroke-linecap="null" x="36.5" font-size="12" font-family="serif" text-anchor="middle" xml:space="preserve" font-weight="bold"></text>' +
					 '<image x="46" y="50"  xlink:href="imagenes/clock.png"></image>',
					 '</g>'].join('');
		myAttrs= {
			'circle':{},
			'text': {text: label, ref: 'circle', 'ref-y': 28, 'y-alignment': 'middle'}
		}
	}
	
    var data = {
   		markup: markupData,
		position : {
			x : x,
			y : y
		},
		size : {
			width : 60,
			height : 60
		},
		name : label,
		handler : selectHandler,
		modifiable: modifiable,
		attrs: myAttrs
	};

	var cell = new joint.shapes.fsa.State(data);
	graph.addCell(cell);
	return cell;
};


function state(x, y, label, markupData, modifiable, selectHandler){
	/**
	 * Modificar estructura para estados, ser diferente de las tareas (task)
	 */
	var myAttrs={}

	if (!markupData) {
		markupData=['<g class="rotatable">',
					'<circle fill="#ffffaa" stroke="#000000" stroke-width="3" cx="37.5" cy="39.5" r="27.2036" />',
					 '<text fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" x="36.5" font-size="12" font-family="serif" text-anchor="middle" xml:space="preserve" font-weight="bold"></text>',
					 '</g>'].join('');
		myAttrs= {
			'circle':{},
			'text': {text: label, ref: 'circle', 'ref-y': 28, 'y-alignment': 'middle'}
		}
	}
	
    var data = {
   		markup: markupData,
		position : {
			x : x,
			y : y
		},
		size : {
			width : 60,
			height : 60
		},
		name : label,
		handler : selectHandler,
		modifiable: modifiable,
		attrs: myAttrs
	};

	var cell = new joint.shapes.fsa.State(data);
	graph.addCell(cell);
	return cell;
};

function fork(x, y, label, markupData, modifiable, selectHandler){
	var myAttrs={}

	if (!markupData) {		
		markupData=['<g class="rotatable">',
								'<rect transform="matrix(0.23958375467964793,0.21741678561942873,-0.2395837546796479,0.2174167856194288,95.79822494102358,-17.32176808032982) " id="svg_1" height="83.30994" width="83.77164" y="31.36356" x="112.45256" stroke-width="3" stroke="#000000" fill="none"/>',
								'<path id="svg_8" d="m106.45336,29.61599l6.0117,0l0,-5.45553l5.65916,0l0,5.45553l6.01168,0l0,5.13553l-6.01168,0l0,5.45555l-5.65916,0l0,-5.45555l-6.0117,0l0,-5.13553z" stroke-linecap="null" stroke-linejoin="null" stroke-width="5" stroke="#000000" fill="#000000"/>',															
								'<text font-weight="bold" xml:space="preserve" text-anchor="middle" font-family="serif" font-size="12" y="40" x="115.55553" stroke-linecap="null" stroke-linejoin="null" stroke-width="0" stroke="#000000" fill="#000000">'+label+'</text>',
								'<text id="forkText" font-weight="bold" xml:space="preserve" text-anchor="middle" font-family="serif" font-size="12" y="10" x="115.55553" stroke-linecap="null" stroke-linejoin="null" stroke-width="0" stroke="#000000" fill="#000000">División</text>',
								'</g>'].join('');		
		myAttrs= {
			'text': {'ref-y': 33, 'y-alignment': 'middle'}
		}
	}
	
    var data = {
   		markup: markupData,
		position : {
			x : x,
			y : y
		},
		size : {
			width : 60,
			height : 60
		},
		name : 'division'+label,
		handler : selectHandler,
		modifiable: modifiable,
		attrs: myAttrs
	};

	var cell = new joint.shapes.fsa.State(data);
	cell.attributes.type = "fsa.Fork";
	graph.addCell(cell);
	return cell;
};

function join(x, y, label, markupData, modifiable, selectHandler){
	var myAttrs={}

	if (!markupData) {		
		markupData=['<g class="rotatable">',
								'<rect transform="matrix(0.23958375467964793,0.21741678561942873,-0.2395837546796479,0.2174167856194288,95.79822494102358,-17.32176808032982) " id="svg_1" height="83.30994" width="83.77164" y="31.36356" x="112.45256" stroke-width="3" stroke="#000000" fill="none"/>',
								'<path id="svg_8" d="m106.45336,29.61599l6.0117,0l0,-5.45553l5.65916,0l0,5.45553l6.01168,0l0,5.13553l-6.01168,0l0,5.45555l-5.65916,0l0,-5.45555l-6.0117,0l0,-5.13553z" stroke-linecap="null" stroke-linejoin="null" stroke-width="5" stroke="#000000" fill="#000000"/>',															
								'<text font-weight="bold" xml:space="preserve" text-anchor="middle" font-family="serif" font-size="12" y="40" x="115.55553" stroke-linecap="null" stroke-linejoin="null" stroke-width="0" stroke="#000000" fill="#000000">'+label+'</text>',
								'<text id="joinText" font-weight="bold" xml:space="preserve" text-anchor="middle" font-family="serif" font-size="12" y="10" x="115.55553" stroke-linecap="null" stroke-linejoin="null" stroke-width="0" stroke="#000000" fill="#000000">Unión</text>',
								'</g>'].join('');	
		myAttrs= {
			'text': {'ref-y': 33, 'y-alignment': 'middle'}
		}
	}
	
    var data = {
   		markup: markupData,
		position : {
			x : x,
			y : y
		},
		size : {
			width : 60,
			height : 60
		},
		name : 'union'+label,
		handler : selectHandler,
		modifiable: modifiable,
		attrs: myAttrs
	};

	var cell = new joint.shapes.fsa.State(data);
	cell.attributes.type = "fsa.Join";
	graph.addCell(cell);
	return cell;
};

function modifyCellName(cell, name) {
	if (name) {
		var data = {
			'lastName' : cell.get('name'),
			'newName' : name
		};
		
		cell.set('name', name);
		cell.attr('text/text', name);
		
		getProject().modifyAttrs(cell.attributes);

		zAu.send(new zk.Event(zk.Widget.$('$info'), 'onRenameState', JSON.stringify(data)));
	}
}

var modifyCellNameById = function (id,name) {
	var cell = graph.getCell(id);

	if (cell) {
		modifyCellName(cell,name);
	}
};

var validateLinks = function(state){
	var links = paper.model.getLinks();
	_.each(links, function (link){
		if(link.get('source').id == state.attributes.id ||
		   link.get('target').id  == state.attributes.id){
			var validLink = getProject().isValidPropertie(link.get('source'),link.get('target'));			
			if(validLink.isValid){
				if(validLink.isDouble){
					link.attr('.marker-source/transform',"scale(1)");
					link.attr('.marker-target/transform',"scale(1)");
				}else{
					if(validLink.isSingleInverse){
						link.attr('.marker-source/transform',"scale(1)");
						link.attr('.marker-target/transform',"scale(0.001)");
					}
					else{
						link.attr('.marker-source/transform',"scale(0.001)");
					}
				}
			}else{
				alert(validLink.mesagge);
				link.remove();
				removeLink(link);
				return;
			}
		}
	});
}

function modifyState(cell){	
	if(cell.get("type") == 'fsa.State'){
		var lastName=cell.get('name');
		nuevoEstado("Modificar nombre estado",function(name){		
				if (name!=lastName) {
					var counter=0;
					_.each(graph.getElements(), function(el){
						if(el.get('name') == name){
							counter++;
						};	
					});

					if (counter>=1) {
						alert("Ya existe un estado llamado: "+name);
						return;
					}

					modifyCellName(cell,name);
					
					selectedState=null;
					var estado = getProject().findById(cell.id);
					estado.properties.name = name;
					estado.attributes.name = name;

					var funcProperties = window["showProperties"];
					if (funcProperties) {
						funcProperties(cell);
					}

					
					/*
					// ---- si se tiene que realizar las modificaciones ----
					cell.set('name', name);
					cell.attr('text/text', name);

					getProject().modifyAttrs(cell.attributes);
					*/
				}
			},lastName);
	}
}

function getState(cellView){	
	var cell = graph.getCell(cellView.model.id);

	if (cell && cell.attributes.modifiable) {
		var funcProperties = window["showProperties"];
		if (funcProperties) {
			funcProperties(cell);
		}
	}
	else{
		onClickAnotherElement();
	}
};

function start(x,y,label,markupData){
	var cell = new joint.shapes.fsa.StartState({
		position: {
			x : x,
			y : y
		},
			
		name: label,
		markup: markupData,
		modifiable: false		
	});
		
	if (markupData && markupData.length>0) {
		cell.markup = markupData;
	} else {
		cell.markup=['<g class="rotatable">',
					'<circle fill="#ffffaa" stroke="#000000" stroke-width="3" cx="37.5" cy="39.5" r="27.2036" id="svg_34"/>',
					 '<text fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" x="36.5" y="36" id="svg_40" font-size="12" font-family="serif" text-anchor="middle" xml:space="preserve" font-weight="bold">'+label+'</text>',
					 '</g>'].join('');
	}
	graph.addCell(cell);
	return cell;
}

function end(x,y,label,markupData){
	var cell = new joint.shapes.fsa.EndState({
		position: {
			x : x,
			y : y
		},
		
		name: label,
		markup: markupData,
		modifiable: false
	});
	
	if (markupData && markupData.length>0) {
		cell.markup = markupData;
	} else {
		cell.markup=['<g class="rotatable">',
					'<circle fill="#ffffaa" stroke="#000000" stroke-width="3" cx="37.5" cy="39.5" r="27.2036" id="svg_34"/>',
					 '<text fill="#000000" stroke="#000000" stroke-width="0" stroke-linejoin="null" stroke-linecap="null" x="36.5" y="36" id="svg_40" font-size="12" font-family="serif" text-anchor="middle" xml:space="preserve" font-weight="bold">'+label+'</text>',
					 '</g>'].join('');
	}
	graph.addCell(cell);
	return cell;
}

var addLink=function(link, sourceId, targetId){	
	var source=graph.getCell(sourceId);
	var target=graph.getCell(targetId);

	if (sourceId && targetId && (sourceId === targetId)) {
		if (!source.attributes.modifiable) {
			/*alert("¡¡ Vinculo no permitido !!");*/
			link.remove();
			return;
		}
	}
	
	if((source.attributes.type == 'fsa.StartState' && target.attributes.type == 'fsa.State' && getProject().getStartOnlyLink() == false)||
	   (source.attributes.type == 'fsa.State' && target.attributes.type == 'fsa.StartState' && getProject().getStartOnlyLink() == false)){
		getProject().setStartOnlyLink(true);
		getProject().setStartOnlyLinkId(link.id);
		getProject().setStartOnlyLinkFirst(true);
	}else{
		if((source.attributes.type == 'fsa.StartState' && target.attributes.type == 'fsa.State' && getProject().getStartOnlyLink() == true)||
		   (source.attributes.type == 'fsa.StartState' && target.attributes.type == 'fsa.State' && getProject().getStartOnlyLink() == true)){
			getProject().setStartOnlyLinkFirst(false);
		}
	}
	
	var validLink = getProject().isValidLink(source,target,link.id);
	if(validLink.isValid){
		if(validLink.isDouble){
			link.attr('.marker-target/transform',"scale(1)");
			link.attr('.marker-source/transform',"scale(1)");
			getProject().addLink(link,true,false);
		}else{
			if(validLink.isSingleInverse){
				link.attr('.connection/stroke',"black");
				link.attr('.marker-source/d',"M 10 0 L 0 5 L 10 10 z");
				link.attr('.marker-source/transform',"scale(1)");
				link.attr('.marker-target/d',"M 10 0 L 0 5 L 10 10 z");
				link.attr('.marker-target/transform',"scale(0.001)");
				getProject().addLink(link,false,true);
			}
			else{
				link.attr('.marker-source/transform',"scale(0.001)");
				getProject().addLink(link,false,false);
			}
		}
	}else{
		alert(validLink.mesagge);
		link.remove();
		removeLink(link);
		return;
	}
}

var removeLink=function(link){	
	getProject().removeLink(link);
}

var addElements= function(cell) {
	if(cell.attributes.type && cell.attributes.type=='link'){
		cell.on("change:target", function(link,target) {			
			if (target.id) { // es que tiene un objeto de destino
				addLink(link, link.attributes.source.id,target.id);
			}
		})			
		cell.on("change:source", function(link,source) {
			if (source.id) { // es que tiene un objeto de destino
				addLink(link, source.id, link.attributes.target.id);
			}
		})						
	}
}

var newProject = function(){	
	if (myProject && getProject().getFlagModified()) { // si tengo un proyecto
			confirmaGuardar("Se generará un nuevo proyecto.", function(){
				zAu.send(new zk.Event(zk.Widget.$("$info"), 'onNuevoProyecto', null));
			}, 
			function(){
				zAu.send(new zk.Event(zk.Widget.$('$info'), 'onNuevoProyecto', null));
			});
	} else {
		zAu.send(new zk.Event(zk.Widget.$('$info'), 'onNuevoProyecto', null));
	}
}

var openProject = function(){
	if (myProject && getProject().getFlagModified()) { // si tengo un proyecto
			confirmaGuardar("Se abrirá proyecto.", function(){
				zAu.send(new zk.Event(zk.Widget.$('$info'), 'onAbrirProyecto', null));
			}, 
			function(){
				zAu.send(new zk.Event(zk.Widget.$('$info'), 'onAbrirProyecto', null));
			});
	} else {
		zAu.send(new zk.Event(zk.Widget.$('$info'), 'onAbrirProyecto', null));
	}	
}

var cargarProyecto = function(data) {
	nuevoProyecto(data);
}

var importarProyecto = function(data){
	if (myProject && getProject().getFlagModified()) { // si tengo un proyecto
			confirmaGuardar("Se quiere importar proyecto.", function(){
				nuevoProyecto(data);
			}, 
			function(){
				nuevoProyecto(data);
			});
	} else {
		nuevoProyecto(data);
	}	
}

var saveModel = function() {
	getProject().setFlagModified(false);
	var jsonModel = JSON.stringify(this.graph.toJSON());
		getProject().addModel(jsonModel);
		var json = JSON.stringify(getProject(),function(key,value){
			if (typeof value === 'function') {
				return value.toString();
			} else {
				return value;
			}			
		});
	
	getChangeLog().sendToServer();
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onSaveModel', json));
}

var saveAsModel = function(){
	getProject().setFlagModified(false);
	var jsonModel = JSON.stringify(this.graph.toJSON());
		//getProject().checkVersion();
		getProject().addModel(jsonModel);
		var json = JSON.stringify(getProject(),function(key,value){
			if (typeof value === 'function') {
				return value.toString();
			} else {
				return value;
			}			
		});
		
	getChangeLog().sendToServer();
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onSaveAsModel', json));
}

var showHistory = function(){
	var json = JSON.stringify({workflow:getProject().name});
	zAu.send(new zk.Event(zk.Widget.$('$info'),'onShowHistory', json));
}

var validateProject = function(showalert, type){
	// Modificar para validar estado y tareas
	getProject().setFlagModified(false);
	
	debugger;
	if(getProject().forks.length > 0 && getProject().joins.length > 0){
		getProject().setPathToActualState();
		getProject().setPathToActualStateJSON();
	}
	getProject().typeWorkFlow = type;
	try{
		isValid = false;
		var validatedProject = getValidator().validate(getProject());
		var encontroValidacionFaltante;
		var validacionesFaltantes = [];
		for(var validacion in validatedProject){
			if(validatedProject.hasOwnProperty(validacion)){
				var booleano = validatedProject[validacion];
				if(!booleano){
					encontroValidacionFaltante = true;
					validacionesFaltantes.push(validacion);
				}
			}
		}
		var mensajeValidaciones = "";
		if(encontroValidacionFaltante){	
			var mensajeAMostrar = "Todavia no puede generar un paquete"+"\n\n";
			for(i=0; i<validacionesFaltantes.length; i++){
				switch(validacionesFaltantes[i]) {
					case 'haveStart':
						mensajeValidaciones += "• El modelo no tiene 'Inicio' \n";
					break;
					case 'haveEnd':
						mensajeValidaciones += "• El modelo no tiene 'Cierre' \n";
					break;
					case 'haveStates':
						mensajeValidaciones += "• El modelo no tiene estados \n";
					break;
					case 'haveLinks':
						mensajeValidaciones += "• El modelo no tiene enlaces \n";
					break;
					case 'haveGuarda':
						mensajeValidaciones +="• El modelo no tiene 'Guarda Temporal' \n";
					break;
					case 'stateHasLinks':
						mensajeValidaciones += "• No dejar estados sin conectar \n";
					break;
					case 'startToState':
						mensajeValidaciones += "• 'Inicio' no esta conectado \n";
					break;
					case 'stateToGuarda':
						mensajeValidaciones += "• Algún estado debe estar conectado a 'Guarda Temporal' \n";
					break;
					case 'guardaToEnd':
						mensajeValidaciones += "• 'Guarda Temporal' no esta conectado a 'Cierre' \n";
					break;
					case 'tLibreHasLinks':
						mensajeValidaciones += "• 'Tramitación Libre' no esta conectado \n";
					break;
					case 'stateToTLibre':
						mensajeValidaciones += "• Algún estado 'Acepta Tramitación Libre' pero no existe conexión, entre ambos \n";
					break;
					case 'noBlankNames':
						mensajeValidaciones += "• Existen estados sin nombre \n";
					break;
					case 'forkConnectedToJoin':
						mensajeValidaciones += "• Tiene que existir un elemento división y unión con el mismo nombre' \n";
					break;
					case 'joinConnectedToFork':
						mensajeValidaciones += "• Tiene que existir un elemento división y unión con el mismo nombre' \n";
					break;
				}
			}
			
			if(mensajeValidaciones != null && mensajeValidaciones.length > 0) {
				mensajeAMostrar += mensajeValidaciones;
				alert(mensajeAMostrar);
				return false;
			} else {
				if (showalert) {
					zAu.cmd
				}
				return true;
			}
			
		} else {
			if (showalert) {
				alert("El modelo es valido!");
			}
			return true;
		}
	}
	catch(err){
		//alert("Debe generar un nuevo proyecto, antes de continuar!");
		alert("Error al realizar las validaciones ("+err+")!");
	}
}

var exportJSON = function() { 
	var json = getJson();
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onExportJSON', json));
}

var getJson = function(){
	var jsonModel = JSON.stringify(this.graph.toJSON());
	//getProject().checkVersion();
	getProject().addModel(jsonModel);
	var json = JSON.stringify(getProject(), function(key,value){
		if (typeof value === 'function') {
			return value.toString();
		} else {
			return value;
		}			
	});
	return json;
}

var makePackageModel = function(destination, type) {
	getProject().setFlagModified(false);
	var isValid = validateProject(false, type);
	if(isValid){
		
		getProject().setPreviousConnectionsFromJoin(getProject().joins); //function to set the 
																		 //stateConnectedToJoinNamed attribute to all the states		
		
		var jsonModel = JSON.stringify(this.graph.toJSON());
		if(getProject().checkVersion()){
			var version = getProject().getVersion();
			getProject().addOneToVersion(version);
			getProject().incrementVersion = false;
		}
		getProject().addModel(jsonModel);
		var json = JSON.stringify(getProject(),function(key,value){
			if (typeof value === 'function') {
				return value.toString();
			} else {
				return value;
			}			
		});
		
		if(destination=="local"){
			zAu.send(new zk.Event(zk.Widget.$('$info'), 'onMakePackageModel', json));
		}else{
			var data = {'project': json, 'nameSpace': destination};
			zAu.send(new zk.Event(zk.Widget.$('$info'), 'onMakePackageOnWebDav', JSON.stringify(data)));
		}
	}
}

removeState = function(data){
	data.remove();
}

removeCells = function(){
	graph.get('cells').forEach(function(cell){
		var view = paper.findViewByModel(cell)
		if(view){
			view.remove();	
		}
	});
}

var drawFromJSON = function(){
	removeCells();
	var jsonObject = JSON.parse(getProject().getModel());
	graph.fromJSON(jsonObject);
}

var initialize = function(paperWidth, paperHeight) {
	graph = new joint.dia.Graph;

	paper = new joint.dia.Paper({
		$el : $('#paper'),
		el : $('#paper'),
		width : paperWidth,
		height : paperHeight,
		gridSize : 5,
		model : graph
	});

	graph.on('change:position', function(cell, evt, x, y) {	
		if (cell._changing){
			cellModified = cell;
		}
	});

	graph.on('add', addElements);
	
	//Borra el link al clickear en el remove del halo
	paper.on('cell:pointerdown', function(cellView, evt, x, y){
		var toolRemove = $(evt.target).parents('.tool-remove')[0];
	    if(toolRemove){
			removeLink(graph.getCell(cellView.model.id));
			(graph.getCell(cellView.model.id)).remove();
	    }
	});

	//Al hacer doble click en un state, permite modificar el nombre
	paper.on('cell:pointerdblclick', function(cellView, evt, x, y){		
		var cell = graph.getCell(cellView.model.id);
		if(cell.attributes.modifiable){
			modifyState(cell);
		}
	});

	paper.on('cell:pointerup',function(cellView, evt){
		
		if(cellModified || cellModified!=null){
			getProject().modifyAttrs(cellModified.attributes);
			cellModified=null;
		}

		getState(cellView);
		
		//Borra el link al apuntar al paper, o sacarlo del source o target uma vez conectado
		if(cellView.model instanceof joint.dia.Link){
			var cell = graph.getCell(cellView.model.id);
			if(cell && cell.get('type') == 'link'){				
				if(cellView._action == "arrowhead-move"){
					if(!cellView._viewUnderPointer){
						cell.remove();
						removeLink(cell);
					}
				}
			}
			return;
		}

	    var cell = graph.getCell(cellView.model.id);
		
		if( cell.get("type") == 'fsa.StartState' || cell.get("type") == 'fsa.EndState' || cell.get("type") == 'fsa.Join' || cell.get("type") == 'fsa.Fork'){
			var halo = new joint.ui.Halo({		        
	    		graph: graph,
		        paper: paper,
		        cellView: cellView,
				
				linkAttributes: {
					'.connection': { stroke: 'black' },
		            '.marker-source': { d: 'M 10 0 L 0 5 L 10 10 z', transform: 'scale(0.001)' },
		            '.marker-target': { d: 'M 10 0 L 0 5 L 10 10 z' }
		        }
		    });
			halo.render();
			event.preventDefault();
	    	halo.$('.box').remove();
		}
	    
	    if(cell.get("type") == 'fsa.State'){
	    	var halo = new joint.ui.Halo({
	    		graph: graph,
		    	paper: paper,
		    	cellView: cellView,
		        
		    	linkAttributes: {
		    		'.connection': { stroke: 'black' },
					'.marker-source': { fill: 'red', stroke: 'red', d: 'M 10 0 L 0 5 L 10 10 z' },
					'.marker-target': { fill: 'black', d: 'M 10 0 L 0 5 L 10 10 z' }
		    	}
	    	});
	    	halo.render();
	    	event.preventDefault();
	    	halo.$('.box').remove();
	    }
	});	
};

var cargarCamposFFCC=function(){
	zAu.send(new zk.Event(zk.Widget.$('$info'), 'onCargarCamposFFCC',null));	
}

var agregarCamposFFCC=function(jsondata) {
	camposFFCC=[];
	if (jsondata && jsondata.formularioComponentes) {
		for(i=0; i<jsondata.formularioComponentes.length; i++) {
			camposFFCC.push(jsondata.formularioComponentes[i]);
		}
	}
}

var cargarEstadosPrevios = function(){	
	var statePrevioCombo = zk.Widget.$("$statePrevio");
	var arrayOrigins = getProject().getOriginsOfState(selectedState.attributes.id);
	if(arrayOrigins){
		for(var i = 0; i < arrayOrigins.length; i++){
			var statePrevioItem = new zul.inp.Comboitem({
		  	label : arrayOrigins[i]
		  });
			statePrevioCombo.appendChild(statePrevioItem);
		}
	}
}

var confirmaGuardar=function(mensaje, onGuardar, onCancelar) {
	var divAviso = new zul.wgt.Div({
		align: 'center',
		width: '100%',
		children: [
			new zul.box.Vlayout({
				width:'100%',
				children: [
					new zul.wgt.Label({value: mensaje}),
					new zul.wgt.Label({value: '¿Desea guardar el proyecto?'})
				]})
		]});

	var divBtn = new zul.wgt.Div({
		align: 'center',
		width: '100%',
		children: [
			new zul.box.Hlayout({
				width:'100%',
				children: [
		        	new zul.wgt.Button({
			            label: 'OK',
			            listeners:  {
			                onClick: function (evt) {
			                    zk.Widget.$("$confirmaGuardar").detach();			                    			                    
			                    if (onGuardar) {			                    	
			        				getProject().setFlagModified(false);
									saveModel();

			                    	onGuardar();
			                    }
			                }
			            }
			        }),
			        new zul.wgt.Separator({
			        	width:'20px'
			    	}),
		        	new zul.wgt.Button({
			            label: 'NO',
			            listeners:  {
			                onClick: function (evt) {											                	
			                    zk.Widget.$("$confirmaGuardar").detach();
			                    if (onCancelar) {
			                    	onCancelar();
			                    }
			                }
			            }
			        })		
			    ]									        
	        })											        
		]
	});

	var vlayout = new zul.box.Vlayout({
		children : [ divAviso, divBtn ]
	});

	var win = new zul.wnd.Window({
		id : "confirmaGuardar",
		title : "Confirmación de guardado",
		closable : false,
		border : 'normal',
		mode : 'modal',
		parent : zk.Widget.$("$info").getPage(),
		width: '270px',
		children : [ vlayout ]
	});

	jq('#z_messages').html(win);
}

var cerrarDesigner = function() {
	if (myProject && getProject().getFlagModified()) { // si tengo un proyecto
			confirmaGuardar("Se cerrará Workflow Designer.", function(){
				zAu.send(new zk.Event(zk.Widget.$('$info'), 'onCerrarDesigner', null));
			}, function() {
				zAu.send(new zk.Event(zk.Widget.$('$info'), 'onCerrarDesigner', null));
			});
	} else {
		zAu.send(new zk.Event(zk.Widget.$('$info'), 'onCerrarDesigner', null));
	}
}

var undo = function(){
	var proyecto = getLastModification(-1);
	if(proyecto){
		setProject(proyecto);
		getProject().onModifyProject=onModifyProject;
		getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se hizo UNDO");
		drawFromJSON();
	}else{
		showMessageBox("Deshacer cambios", "No se encontraron más cambios para deshacer.");
	}
}

var redo = function(){
	var proyecto = getLastModification(1);
	if(proyecto){
		setProject(proyecto);
		getProject().onModifyProject=onModifyProject;
		getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se hizo REDO");
		drawFromJSON();
	}else{
		showMessageBox("Rehacer cambios", "No se encontraron más cambios para rehacer.");
	}
}

var showMessageBox = function(windowTitle, windowMessage){
	var win = new zul.wnd.Window({
		id: "Ventana", title: windowTitle,
	    closable: false,
	    border: 'normal',
	    mode: 'modal',
	    width: '300px',
	    parent: zk.Widget.$("$info").getPage(),
	    children: [
	    	new zul.wgt.Label({value: windowMessage}),
	    	new zul.wgt.Button({
	            label: 'Aceptar',
	            listeners:  {
	                onClick: function (evt) {											                	
	                    zk.Widget.$("$Ventana").detach();
	                }
	            }
	        })
	    ]
	});

	jq('#z_messages').html(win);
}

var doProbeIt=function() {
	var pepe = getProject().startState;
	var celda = graph.getCell(pepe.attributes.id);
	var cellView = paper.findViewByModel(celda);

	magicGraph(celda,function(){
		validateProject(true, '');
	});
}

var bloqued = false;
var magicGraph=function(cell, finishCallback) {
 if (cell instanceof joint.dia.Link) {
     var targetCell = graph.getCell(cell.get('target').id);
     sendToken(cell, 1, function() {
         //targetCell.trigger('signal', targetCell);
         magicGraph(targetCell);
     });
 } else {
     flash(cell);
     var outboundLinks = graph.getConnectedLinks(cell, { outbound: true });
     _.each(outboundLinks, function(link) {
         if (!bloqued) {
         	magicGraph(link);
         }
     });
 }

 if (finishCallback) {
 	finishCallback();
 }
}

function flash(cell) {
	bloqued=true;
 var cellView = paper.findViewByModel(cell);
 cellView.highlight();
 _.delay(function() { cellView.unhighlight(); bloqued=false; }, 200);
}

function sendToken(link, sec, callback) {    
	bloqued=true;
 var token = V('circle', { r: 7, fill: 'green' });
 
 $(paper.viewport).append(token.node);
 token.animateAlongPath({ dur: sec + 's', repeatCount: 1 }, paper.findViewByModel(link).$('.connection')[0]);
 
 _.delay(function() {
     token.remove();
     callback();
 }, sec * 1000);
}



