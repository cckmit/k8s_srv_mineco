var myProject = null;
var flagModified = false;

var getProject = function() {
	if (!myProject) {
		zAu.cmd0.alert("Debe generar un nuevo proyecto, antes de continuar!", "Error");
		return null;
	}
	return myProject;
}

var setProject = function(jsonData) {
	myProject=new Project().fromJSON(jsonData);
}

Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}

function copy(from, to, excludes) {
	for (var prop in from) {
		if (excludes && excludes.contains(prop)) continue;
		to[prop] = from[prop];
	}
	return to;
}

function copyAttrs(fromAttrs, toAttrs) {
	copy(fromAttrs, toAttrs,["attrs","handler","forward","backward","markup"]);
}

var StateProperties = function() {
	this.name="";
	this.backward="";
	this.tipoDocumentoFFCC="";
	this.typeState="";
	this.forwardDesicion="";	
	this.forwardValidation=null;
	this.initialize=null;

	this.acceptReject=false;
	this.acceptCierreExpediente=false;
	this.acceptTramitacionLibre=false;
	
	/*variables for paralelo*/
	this.hasGroup = false;
	this.groupName = null;
	this.branchName = null;
	this.hasPreviousGroup = false;
	this.previousGroupName = null;
	this.stateConnectedToJoinNamed = null; //State connected to the last join, for closing out the group
									       //this means that the state hasn't the possibility to set the destination
									       //it's injected by the join decition
	this.stateAfterJoinNamed = null;	   
	this.stateConnectedToFork = null;
	this.forkOnlyLink = false;
	this.forkName     = null;
	
	this.pathToActualState = null;
	this.pathToActualStateJSON = null;
	
	this.joinName
	/*end of variables for paralelo*/

	// --- non-visible properties ---
	this.stateMacroURI="";
	this.windowsId="";
	this.forward=""; 
	this.acronymPase="";
	this.workflowName="";
	this.scriptFuseTask="";
	this.scriptStartState="";
	this.scriptEndState="";
	this.showPassInfo=false;
}

var State = function (objattributes, objproperties) {
	this.attributes={};
	this.properties=new StateProperties();
	this.hasProperties=true;
	this.allowedConnections=[];
	this.hint="";

	if (objattributes) {
		copyAttrs(objattributes,this.attributes);
	}

	if (objproperties) {		
		copy(objproperties,this.properties);
	}		
}

var Fork = function (objattributes) {
	this.attributes={};
	this.properties=new StateProperties();
	this.hasProperties=false;
	this.allowedConnections=[];

	if (objattributes) {
		copyAttrs(objattributes,this.attributes);
	}
}

var Join = function (objattributes, objproperties) {
	this.attributes={};
	this.properties=new StateProperties();
	this.hasProperties=true;
	this.allowedConnections=[];

	if (objattributes) {
		copyAttrs(objattributes,this.attributes);
	}

	if (objproperties) {		
		copy(objproperties,this.properties);
	}		
}

var Link = function(id,origin, destiny, doubleLink){
	this.id=id;
	this.origin=origin;
	this.destiny=destiny;
	this.doubleLink=doubleLink;
}

var Project = function(name, author, description, version) {
	this.name=name;
	this.author=author;
	this.description=description;
	if (version) {
		this.version=version;
	} else {
		this.version=0;
	}
	
	this.startState=null;
	this.endState=null;
	this.states=[];
	this.links=[];
	this.forks=[];
	this.joins=[];

	this.tramitacionLibre=false;
	this.guardaTemporal=false;
	
	this.startOnlyLink=false;
	this.startOnlyLinkId=null;
	this.startOnlyLinkFirst=false;
	
	this.onlyLinksToFork = [];
	
	this.incrementVersion=false;	     
	this.jsonModel = null;
	this.flagModified=false;
	this.scriptFuseGeneric="";
}

Project.prototype = {
	getVersion:function(){
		return this.version;
	},
	addOneToVersion:function(version){
		this.version = version + 1;
	},
	onModifyProject: function() {
	},
	getFlagModified: function(){
		return this.flagModified;
	},
	setFlagModified: function(booleano){
		this.flagModified = booleano;
		this.onModifyProject();
	},
	fromJSON: function(jsonData) {
		//var obj=JSON.parse(jsonData);
		for (var prop in jsonData) {
			this[prop] = jsonData[prop];
		}
		
		this.guardaTemporal = this.findByName("Guarda Temporal");
		this.tramitacionLibre = this.findByName("Tramitacion Libre");

		return this;
	},	
	startExist: function() {
		return (this.startState!=null);
	},
	endExist: function() {
		return (this.endState!=null);
	},
	stateNameIsReserved:function(nameElement){
		if (nameElement && [
            	"inicio", "cierre", "nuevoestado", "guardatemporal", "tramitacionlibre", "tramitacionlibre", "subsanacion", "subsanacion"
            ].contains(nameElement.replace(" ", "").toLowerCase())){
				return true;
		}
		return false;
	},
	stateExist:function(nameElement){
		if (this.states.length>0){
			for (var index = 0; index < this.states.length; index++) {
				if (this.states[index].properties.name.toUpperCase()==nameElement.toUpperCase()) {
					return true;
				}
			}
		}
		return false;
	},
	forkJoinExist:function(nameElement){
		if (this.joins.length>0){
			for (var i = 0; i < this.joins.length; i++) {
				if (this.joins[i].attributes.name.toUpperCase() == (('union'+nameElement).toUpperCase())){						
					return true;
				}	
			}
		}
		return false;
	},
	addModel:function(jsonModel){
		this.jsonModel = jsonModel;
	},
	getModel:function(){
		if(this.jsonModel == null){
			this.jsonModel = '{"cells":[]}';
		}
		return this.jsonModel;
	},
	checkVersion:function(){
		return this.incrementVersion;
	},
	addStart:function(data){
		if (!this.startState) {
			this.startState = new State(data.attributes);
			this.startState.allowedConnections = ["fsa.State"];
			this.startState.properties.acceptReject = false;
			this.startState.properties.acceptCierreExpediente = false;
			this.startState.properties.acceptTramitacionLibre=false;
			this.startState.hasProperties=false;
			
			this.incrementVersion=true;
			
			this.setFlagModified(true);
			
			sendEvent("AddStart","",this.startState);
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó Inicio");
		}
	},
	addEnd:function(data){
		if (!this.endState) {
			this.endState = new State(data.attributes);
			this.endState.allowedConnections = ["Guarda Temporal"];
			this.endState.properties.acceptReject = false;
			this.endState.properties.acceptCierreExpediente = false;
			this.endState.properties.acceptTramitacionLibre=false;
			this.endState.hasProperties=false;
			this.incrementVersion=true;
			
			this.setFlagModified(true);
			
			sendEvent("AddEnd","",this.endState);
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó Cierre");
		}
	},
	addGenericState:function(state, name){
		if (!this.stateExist(name)) {
			this.incrementVersion=true;			
			state.properties.workflowName=this.name;
			this.states.push(state);
			
			this.setFlagModified(true);

			sendEvent("AddState","",state);
		}
	},	
	addState:function(data,tipo,type){	
		if(tipo == 'tlibre'){
			this.addTramitacionLibre(data,tipo);
		}else{
			if(tipo == 'gtemporal'){
				this.addGuardaTemporal(data,tipo);
			}else{
				var state = new State(data.attributes);
				state.properties.name=data.attributes.name;
				state.properties.acceptReject = true;
				state.properties.acceptCierreExpediente = false;
				state.properties.acceptTramitacionLibre = false;
				state.properties.showPassInfo = true;
				state.allowedConnections = ["fsa.StartState","fsa.State","Tramitacion Libre","Guarda Temporal","fsa.Join","fsa.Fork"];
				
				/*variables for paralelo*/
				state.properties.hasGroup = false;
				state.properties.groupName = null;
				state.properties.branchName = null;
				state.properties.hasPreviousGroup = false;
				state.properties.previousGroupName = null;
				state.properties.stateConnectedToJoinNamed = null;
				state.properties.stateAfterJoinNamed = null;
				
				state.properties.forkOnlyLink = false;
				state.properties.forkName     = null;
				
				state.properties.stateConnectedToFork = null;				
								
				state.properties.pathToActualState = [];
				state.properties.pathToActualStateJSON = "";
				
				if(type === "temp"){
					state.properties.typeState = "TEMP";
				} else {
					state.properties.typeState = null;
				}
				/*end of variables for paralelo*/
				
				this.addGenericState(state,data.attributes.name);
				getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó '"+state.properties.name+"'");
			}
		}
	},
	addTramitacionLibre:function(data){
		if(!this.tramitacionLibre){
			var TramitacionLibreState=new State(data.attributes);
			TramitacionLibreState.properties.name="Tramitacion Libre";
			TramitacionLibreState.attributes.type = 'Tramitacion Libre';
			TramitacionLibreState.properties.acceptReject=false;
			TramitacionLibreState.properties.acceptCierreExpediente=false;
			TramitacionLibreState.properties.accceptClose=false;
			TramitacionLibreState.properties.acceptTramitacionLibre=false;
			TramitacionLibreState.allowedConnections=["fsa.State"];
			TramitacionLibreState.hasProperties=false;
			this.addGenericState(TramitacionLibreState,data.attributes.name);
			this.tramitacionLibre = true;
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó Tramitación Libre");
		}
		else{
			removeState(data);
			zAu.cmd0.alert("No se permite otro Tramitación Libre", "Error");
		}
	},
	addGuardaTemporal:function(data){
		if(!this.guardaTemporal){
			var GuardaTemporalState=new State(data.attributes);
			GuardaTemporalState.properties.name="Guarda Temporal";
			GuardaTemporalState.attributes.type = 'Guarda Temporal';
			GuardaTemporalState.properties.acceptReject=false;
			GuardaTemporalState.properties.acceptCierreExpediente=false;
			GuardaTemporalState.properties.acceptTramitacionLibre=false;
			GuardaTemporalState.allowedConnections=["fsa.State","fsa.EndState"];
			GuardaTemporalState.hasProperties=false;
			this.addGenericState(GuardaTemporalState,data.attributes.name);
			this.guardaTemporal=true;
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó Guarda Temporal");
		}
		else{
			removeState(data);
			zAu.cmd0.alert("No se permite otro Guarda Temporal", "Error");
		}
	},
	addFork:function(data){
		if(!this.forkJoinExist(data.attributes.name)) {
			var fork = new Fork(data.attributes);
			fork.properties.name=data.attributes.name;
			fork.properties.acceptReject = false;
			fork.properties.acceptCierreExpediente = false;
			fork.properties.acceptTramitacionLibre = false;
			fork.properties.showPassInfo = false;
			fork.allowedConnections = ["fsa.State"];
			
			this.incrementVersion=true;
			this.setFlagModified(true);
			
			/*variables for paralelo*/
			fork.properties.hasGroup = false;
			fork.properties.groupName = null;
			fork.properties.branchName = null;
			fork.properties.hasPreviousGroup = false;
			fork.properties.previousGroupName = null;
			fork.properties.forkOnlyLink = false;
			
			fork.properties.stateConnectedToFork = null;
			/*end of variables for paralelo*/
			
			this.forks.push(fork);
				
			sendEvent("AddFork","",this.fork);
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó '"+fork.properties.name+"'");
		}
	},
	addJoin:function(data){
		var join = new Join(data.attributes);
		join.properties.name=data.attributes.name;
		join.properties.acceptReject = false;
		join.properties.acceptCierreExpediente = false;
		join.properties.acceptTramitacionLibre = false;
		join.properties.showPassInfo = false;
		join.allowedConnections = ["fsa.State"];
		
		this.incrementVersion=true;
		this.setFlagModified(true);
		
		/*variables for paralelo*/
		join.properties.hasGroup = false;
		join.properties.groupName  = null;
		join.properties.branchName = null;
		join.properties.hasPreviousGroup = false;
		join.properties.previousGroupName = null;
		/*end of variables for paralelo*/
		
		this.joins.push(join);
			
		sendEvent("AddJoin","",this.join);
		getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó '"+join.properties.name+"'");
	},
	findById:function(id){
		if (this.states.length>0){
			for (var index = 0; index < this.states.length; index++) {
				var state=this.states[index];
				if (state.attributes.id==id) {
					return state;
				}
			}
		}
		
		if(this.startState && this.startState.attributes.id == id){
			return this.startState;
		}
		else{
			if(this.endState && this.endState.attributes.id == id){
				return this.endState;
			}
		}
		
		if (this.forks.length>0){
			for (var index = 0; index < this.forks.length; index++) {
				var fork=this.forks[index];
				if (fork.attributes.id==id) {
					return fork;
				}
			}
		}
		
		if (this.joins.length>0){
			for (var index = 0; index < this.joins.length; index++) {
				var join=this.joins[index];
				if (join.attributes.id==id) {
					return join;
				}
			}
		}		
		return null;
	},
	findByName:function(name){
		if(this.states.length>0){
			for(var i = 0; i < this.states.length; i++){
				var state = this.states[i];
				if(state.attributes.name == name || state.attributes.type == name){
					return state;
				}
			}
		}
		return null;
	},
	modifyAttrs:function(model){
		var state = this.findById(model.id);
		if (state) {
			copyAttrs(model,state);
			state.properties.name=model.name;
			this.setFlagModified(true);
			sendEvent("ModifyState","",state);
		}
	},
	findLinkById:function(id){
		if (this.links.length>0){
			for (var index = 0; index < this.links.length; index++) {
				var link=this.links[index];
				if (link.id==id) return link;
			}
		}
	},
	addLink:function(model, isDouble, isSingleInverse) {
		
		var origin = null;
		var destiny = null;
		
		if(!isSingleInverse){
			origin=model.attributes.source.id;
			destiny=model.attributes.target.id;
		}else{
			origin=model.attributes.target.id;
			destiny=model.attributes.source.id;
		}
		
		var link = this.findLinkById(model.id);

		this.incrementVersion=true;
		this.setFlagModified(true);

		if (link) {
			if (link.origin!=origin || link.destiny!=destiny) { this.incrementVersion=true };

			link.origin=origin;
			link.destiny=destiny;
			link.doubleLink=isDouble;	

			this.isValidLink(model.attributes.source,model.attributes.target,model.id);
		} else {
			link = new Link(model.id,origin,destiny,isDouble);
			this.links.push(link);
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se agregó un Link");
		}
	},
	getStartOnlyLink:function(){
		return this.startOnlyLink;
	},
	getStartOnlyLinkId:function(){
		return this.startOnlyLinkId;
	},
	getStartOnlyLinkFirst:function(){
		return this.startOnlyLinkFirst;
	},
	setStartOnlyLink:function(booleano){
		this.startOnlyLink = booleano;
	},
	setStartOnlyLinkId:function(id){
		this.startOnlyLinkId = id;
	},
	setStartOnlyLinkFirst:function(booleano){
		this.startOnlyLinkFirst = booleano;
	},
	isValidLink:function(source,target,linkId){	
		var sourceEncontrado = this.findById(source.id);
		var targetEncontrado = this.findById(target.id);
		var validLink = {isValid: false,	
						 isDouble: false,
						 isSingleInverse :false,
						 mesagge: "No es posible conectarse entre si"
						};
		
		validLink.isValid = true;
		for(var i = 0; i < sourceEncontrado.allowedConnections.length; i++){
			if(sourceEncontrado.allowedConnections[i] == targetEncontrado.attributes.type){
				validLink.isValid = true;
				i = sourceEncontrado.allowedConnections.length;
			}
		}
		
		var length = this.links.length;
		for(var i = 0; i < length; i++){
			if((this.links[i].origin == source.id && this.links[i].destiny == target.id)||
			   (this.links[i].destiny == source.id && this.links[i].origin == target.id)){
				validLink.isValid = false;
				validLink.mesagge = "Ya estan conectados entre si";
				return validLink;
			}
		}

		if(sourceEncontrado.attributes.type == 'fsa.StartState' && targetEncontrado.attributes.type == 'fsa.State' && this.startOnlyLinkFirst){
			validLink.isDouble = false;
			this.startOnlyLink = true;
			
			var length = this.links.length;
			for(var i = 0; i < length; i++){
				var indexOrigin = this.links.map(function(e){ return e.origin; }).indexOf(source.id);
				var indexDestiny = this.links.map(function(e){ return e.destiny; }).indexOf(target.id);
				if(indexOrigin != -1 && this.links[indexOrigin]){
					validLink.isValid = false;
					validLink.mesagge = "Inicio puede estar conectado con un solo estado";
				}
			}
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.StartState' && this.startOnlyLinkFirst){
				validLink.isSingleInverse = true;
				this.startOnlyLink = true;
				
				var length = this.links.length;
				for(var i = 0; i < length ; i++){
					var indexOrigin = this.links.map(function(e){ return e.origin; }).indexOf(target.id);
					var indexDestiny = this.links.map(function(e){ return e.destiny; }).indexOf(source.id);
					if(indexOrigin != -1 && this.links[indexOrigin]){
						validLink.isValid = false;
						validLink.mesagge = "Inicio puede estar conectado con un solo estado";
					}
				}
			}
			else{
				if((sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.StartState' && !this.startOnlyLinkFirst)||
				   (sourceEncontrado.attributes.type == 'fsa.StartState' && targetEncontrado.attributes.type == 'fsa.State' && !this.startOnlyLinkFirst)){
					validLink.isValid = false;
					validLink.mesagge = "Inicio puede estar conectado con un solo estado";
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'fsa.State' &&
		   targetEncontrado.properties.acceptReject){
			validLink.isDouble = true;
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.State' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   !targetEncontrado.properties.acceptReject){	
				validLink.isDouble = false;
			}else{
				if(sourceEncontrado.attributes.type == 'fsa.State' && 
				   targetEncontrado.attributes.type == 'fsa.State' &&
				   !sourceEncontrado.properties.acceptReject){	
					validLink.isSingleInverse = true;
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'Tramitacion Libre' &&
		   sourceEncontrado.properties.acceptTramitacionLibre){
			validLink.isDouble = true;
		}else{
			if(sourceEncontrado.attributes.type == 'Tramitacion Libre' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   targetEncontrado.properties.acceptTramitacionLibre){
				validLink.isDouble = true;
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'Tramitacion Libre' &&
		   !sourceEncontrado.properties.acceptTramitacionLibre){
			validLink.isValid = false;
			validLink.mesagge = "'"+sourceEncontrado.attributes.name+"'" + " no acepta Tramitación Libre";
		}else{
			if(sourceEncontrado.attributes.type == 'Tramitacion Libre' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   !targetEncontrado.properties.acceptTramitacionLibre){
				validLink.isValid = false;
				validLink.mesagge = "'"+targetEncontrado.attributes.name+"'" + " no acepta Tramitación Libre";
			}
		}
		
		if(sourceEncontrado.attributes.type == 'Guarda Temporal' &&
		   targetEncontrado.attributes.type == 'fsa.State' &&
		   !targetEncontrado.properties.hasPreviousGroup &&
		   targetEncontrado.properties.hasGroup == false){
			validLink.isDouble = false;
			validLink.isSingleInverse = true;
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.State' &&
			   sourceEncontrado.properties.hasGroup == false && 
			   targetEncontrado.attributes.type == 'Guarda Temporal'){
				validLink.isDouble = false;
				
				if(sourceEncontrado.properties.hasPreviousGroup == true){
					validLink.isValid = false;
					validLink.mesagge = "Un estado con subgrupo, no puede conectarse directamente a Guarda Temporal";
					return validLink;
				}
			}
			else{
				if(sourceEncontrado.attributes.type == 'fsa.State' &&
				   sourceEncontrado.properties.hasGroup == true &&
				   !sourceEncontrado.properties.hasPreviousGroup &&
				   targetEncontrado.attributes.type == 'Guarda Temporal'){
					validLink.isValid = false;
					validLink.mesagge = "Un estado con grupo, no puede conectarse directamente a Guarda Temporal";
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'Guarda Temporal' && 
		   targetEncontrado.attributes.type == 'fsa.EndState'){
			validLink.isDouble = false;
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.EndState' && 
			   targetEncontrado.attributes.type == 'Guarda Temporal'){
				validLink.isDouble = false;
				validLink.isSingleInverse = true;
			}
		}
		
		/*paralelo validations*/
		if(sourceEncontrado.attributes.type == 'fsa.Fork' && targetEncontrado.attributes.type == 'fsa.State'){
				var milliseconds = new Date().getTime();
				
				targetEncontrado.properties.hasGroup = true;				

				var startOfName = "division";
				var groupName = sourceEncontrado.properties.name.substring(
						sourceEncontrado.properties.name.length,
						startOfName.length);
						
				sourceEncontrado.properties.groupName = groupName;
				targetEncontrado.properties.stateConnectedToFork = sourceEncontrado.properties.stateConnectedToFork;
				targetEncontrado.properties.groupName = groupName;
				targetEncontrado.properties.branchName = 'fork_'+milliseconds;
				if(sourceEncontrado.properties.hasPreviousGroup == true){
					targetEncontrado.properties.hasPreviousGroup = sourceEncontrado.properties.hasPreviousGroup;
					targetEncontrado.properties.previousGroupName = sourceEncontrado.properties.previousGroupName;
				}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.Fork' && !targetEncontrado.properties.forkOnlyLink){
			sourceEncontrado.properties.forkOnlyLink = true; //AGREGADO PARA UN STATE			
			sourceEncontrado.properties.forkName     = targetEncontrado.properties.name;
			
			targetEncontrado.properties.forkOnlyLink = true;
			targetEncontrado.properties.stateConnectedToFork = sourceEncontrado.properties.name;
			sourceEncontrado.properties.stateConnectedToFork = sourceEncontrado.properties.name;
			
			this.onlyLinksToFork.push(linkId);
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.Fork'){
				if(targetEncontrado.properties.forkOnlyLink == true){
					validLink.isValid   = false;
					validLink.mesagge = 'Un Ãºnico estado puede estar conectado con una división';
					return validLink;
				}
				else{
					if(sourceEncontrado.hasPreviousGroup == false){
						validLink.isValid   = false;
						validLink.mesagge = 'Un estado puede estar conectado a una sola división';
						return validLink;
					}
				}
			}
		}
		
		if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == true){
			if(sourceEncontrado.properties.branchName != targetEncontrado.properties.branchName){
				validLink.isValid = false;
				validLink.mesagge = "Los estados no pertenecen a la misma rama";
			}
		}else{
			if(sourceEncontrado.properties.hasGroup == false && targetEncontrado.properties.hasGroup == false &&
			   sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.State' &&
			   !sourceEncontrado.properties.hasPreviousGroup){
				validLink.isValid = true;
				return validLink;
			}
		
			for(var i = 0; i < getProject().links.length && stateToJoin != true; i++){
				var origin   = this.findById(getProject().links[i].origin);
				var destiny = this.findById(getProject().links[i].destiny);
				
				var stateToJoin = false;
				
				if(origin.attributes.type == 'fsa.Join' && targetEncontrado.attributes.type == 'fsa.State' &&
				   destiny.attributes.id == targetEncontrado.attributes.id){
					stateToJoin = true;
				}
				else{
					if(destiny.attributes.type == 'fsa.Fork' && targetEncontrado.attributes.type == 'fsa.State' &&
					origin.attributes.id == targetEncontrado.attributes.id){
						stateToJoin = true;
					}
				}
				
				if(origin.attributes.type == 'fsa.Fork' && targetEncontrado.attributes.type == 'fsa.State' &&
				   destiny.attributes.id == targetEncontrado.attributes.id){
					stateToJoin = true;
				}
				
				if(destiny.attributes.type == 'fsa.Join' && targetEncontrado.attributes.type == 'fsa.State' &&
				   origin.attributes.id == targetEncontrado.attributes.id){
					stateToJoin = true;
				}
			}
		
			if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == false &&
			  sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.State' &&
			  stateToJoin == false){
				targetEncontrado.properties.hasGroup = true;
				targetEncontrado.properties.groupName = sourceEncontrado.properties.groupName;
				targetEncontrado.properties.branchName = sourceEncontrado.properties.branchName;
				if(sourceEncontrado.properties.hasPreviousGroup == true){
					targetEncontrado.properties.hasPreviousGroup = true;
					targetEncontrado.properties.previousGroupName = groupName;
				}
				
			}else{
				if(stateToJoin == true){
					validLink.isValid = false;
					validLink.mesagge = "Uno de los estados no pertenece al grupo";
				}
			}
		}
		
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.Join'){
			if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == false){
				if(targetEncontrado.properties.name.indexOf(sourceEncontrado.properties.groupName) > -1){
					targetEncontrado.properties.hasGroup = true;
					targetEncontrado.properties.groupName = sourceEncontrado.properties.groupName;
					
					if(sourceEncontrado.properties.hasPreviousGroup == true){
						targetEncontrado.properties.hasPreviousGroup = sourceEncontrado.properties.hasPreviousGroup;
						targetEncontrado.properties.previousGroupName = sourceEncontrado.properties.previousGroupName;
					}
				}
				else{
					if(sourceEncontrado.properties.groupName == targetEncontrado.properties.groupName){
						validLink.isValid = true;
					}
					else{
						if(sourceEncontrado.properties.groupName != targetEncontrado.properties.name){
							validLink.isValid = false;
							validLink.message = "El estado no puede conectarse con la unión de otro grupo";
						}
					}
				}
			}
			else{
				if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == true &&
				  sourceEncontrado.properties.groupName == targetEncontrado.properties.groupName){
					validLink.isValid = true;
				}else{
					if(sourceEncontrado.properties.hasPreviousGroup == true){
						validLink.isValid = true;
						return validLink;
					}
					
					validLink.isValid = false;
					validLink.message = "El estado no puede conectarse con la unión sin pertenecer a un grupo";
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && targetEncontrado.attributes.type == 'fsa.Fork'){
			if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == false){
				if(targetEncontrado.properties.name.indexOf(sourceEncontrado.properties.groupName) > -1){
					targetEncontrado.properties.hasGroup = true;
					targetEncontrado.properties.groupName = sourceEncontrado.properties.groupName;
					targetEncontrado.properties.stateConnectedToFork = sourceEncontrado.properties.name;
				}
				else{
					if(sourceEncontrado.properties.groupName == targetEncontrado.properties.groupName){
						validLink.isValid = true;
					}
					else{
						targetEncontrado.properties.hasPreviousGroup = true;
						targetEncontrado.properties.previousGroupName = sourceEncontrado.properties.groupName;
						targetEncontrado.properties.stateConnectedToFork = sourceEncontrado.properties.name;
					}
				}
			}
			else{
				if(sourceEncontrado.properties.hasGroup == true && targetEncontrado.properties.hasGroup == true &&
				  sourceEncontrado.properties.groupName == targetEncontrado.properties.groupName){
					validLink.isValid = true;
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.Join' && targetEncontrado.attributes.type == 'fsa.State'){
			if(sourceEncontrado.properties.hasPreviousGroup == true){
				targetEncontrado.properties.hasPreviousGroup = sourceEncontrado.properties.hasPreviousGroup;
				targetEncontrado.properties.previousGroupName = sourceEncontrado.properties.previousGroupName;
			}
		}
		/*paralelo validations*/
		return validLink;
	},
	isValidPropertie:function(source,target){	
		var sourceEncontrado = this.findById(source.id);
		var targetEncontrado = this.findById(target.id);
		var validLink = {isValid: false,	
						 isDouble: false,
						 isSingleInverse: false,
						 mesagge: ""
						};
						
		for(var i = 0; i < sourceEncontrado.allowedConnections.length; i++){
			if(sourceEncontrado.allowedConnections[i] == targetEncontrado.attributes.type){
				validLink.isValid = true;
				i = sourceEncontrado.allowedConnections.length;
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'fsa.State' &&
		   targetEncontrado.properties.acceptReject){
			validLink.isDouble = true;
		}else{
			if(sourceEncontrado.attributes.type == 'fsa.State' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   !targetEncontrado.properties.acceptReject){	
				validLink.isDouble = false;
			}else{
				if(sourceEncontrado.attributes.type == 'fsa.State' && 
				   targetEncontrado.attributes.type == 'fsa.State' &&
				   !sourceEncontrado.properties.acceptReject){	
					validLink.isSingleInverse = true;
				}
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'Tramitacion Libre' &&
		   sourceEncontrado.properties.acceptTramitacionLibre){
			validLink.isDouble = true;
		}else{
			if(sourceEncontrado.attributes.type == 'Tramitacion Libre' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   targetEncontrado.properties.acceptTramitacionLibre){
				validLink.isDouble = true;
			}
		}
		
		if(sourceEncontrado.attributes.type == 'fsa.State' && 
		   targetEncontrado.attributes.type == 'Tramitacion Libre' &&
		   !sourceEncontrado.properties.acceptTramitacionLibre){
			validLink.isValid = false;
			validLink.mesagge = "'"+sourceEncontrado.attributes.name+"'" + " no acepta Tramitación Libre";
		}else{
			if(sourceEncontrado.attributes.type == 'Tramitacion Libre' && 
			   targetEncontrado.attributes.type == 'fsa.State' &&
			   !targetEncontrado.properties.acceptTramitacionLibre){
				validLink.isValid = false;
				validLink.mesagge = "'"+targetEncontrado.attributes.name+"'"+" no acepta Tramitación Libre";
			}
		}
		return validLink;
	},
	removeLink: function(model){		
		this.incrementVersion=true;
		this.setFlagModified(true);
		
		if(model.id == this.getStartOnlyLinkId()){
			getProject().setStartOnlyLink(false);
			getProject().setStartOnlyLinkId(null);
			getProject().setStartOnlyLinkFirst(false);
		}
		
		if(model.origin && model.destiny){
			var source = this.findById(model.origin);
			var target = this.findById(model.destiny);		
		}
		else{
			if(model.attributes.source.id  && model.attributes.target.id){
				var source = this.findById(model.attributes.source.id);
				var target = this.findById(model.attributes.target.id);
			}
		}
		
		if(source && target){
			var sourceType = source.attributes.type;
			var targetType = target.attributes.type;
		}
		
		if((sourceType == 'fsa.StartState' && targetType == 'fsa.State')||
		   (targetType == 'fsa.State' && sourceType == 'fsa.StartState')){
			this.startOnlyLink=false;
		}
		
		if(sourceType == 'fsa.State' && targetType == 'fsa.Fork'){
			var index = this.onlyLinksToFork.map(function(e){ return e; }).indexOf(model.id);
			if(index != -1){
				target.properties.forkOnlyLink = false;
				this.onlyLinksToFork.splice(index, 1);
			}
		}
		
		if(sourceType == 'fsa.State' && targetType == 'Guarda Temporal'){
			source.properties.acceptCierreExpediente = false;
		}else{
			if(sourceType == 'Guarda Temporal' && targetType == 'fsa.State'){
				target.properties.acceptCierreExpediente = false;
			}
		}

		var index = this.links.map(function(e){ return e.id; }).indexOf(model.id);
		if(index != -1){
			this.links.splice(index, 1);
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó un Link");
		}
	},
	removeCell:function(model){
		this.incrementVersion=true;
		this.setFlagModified(true);
		
		if(model.attributes.type == "fsa.StartState"){
			this.startState=null;
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó Inicio");
		}
		if(model.attributes.type == "fsa.EndState"){
			this.endState=null;
			getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó Cierre");
		}
		
		if(model.attributes.type == 'fsa.Fork'){
			var index = this.forks.map(function(e){ return e.attributes.id }).indexOf(model.id);
			if(index != -1){
				this.resetGroups(model);
				getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó '"+this.forks[index].attributes.name+"'");	
				this.forks.splice(index,1);	
			}
		}
		
		if(model.attributes.type == 'fsa.Join'){
			if(model.id != undefined){
				var index = this.joins.map(function(e){ return e.attributes.id }).indexOf(model.id);
			}
			if(index != -1){
				getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó '"+this.joins[index].attributes.name+"'");	
				this.joins.splice(index,1);	
			}
		}
		
		/*Borra celda*/
		var index = this.states.map(function(e){ return e.attributes.id }).indexOf(model.id);
		if(index != -1){
			if(this.states[index].attributes.type == 'Tramitacion Libre'){
				this.tramitacionLibre = false;

				getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó Tramitación Libre");
			}
			else{
				if(this.states[index].attributes.type == 'Guarda Temporal'){
					this.guardaTemporal = false;
					getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó Guarda Temporal");
				}
				else{
					getChangeLog().addChange(getProject().name,getProject().version,getProject().author,"Se eliminó '"+this.states[index].attributes.name+"'");
				}
			}
			this.states.splice(index,1);	
		}
		/*Borra Links Asociados a la celda, si los hay*/
		var length = this.links.length;
		for(var i = 0; i < length ; i++){
			var indexOrigin = this.links.map(function(e){ return e.origin; }).indexOf(model.id);
			var indexDestiny = this.links.map(function(e){ return e.destiny; }).indexOf(model.id);
			if(indexOrigin != -1 && this.links[indexOrigin])
				this.removeLink(this.links[indexOrigin]);
			if(indexDestiny != -1 && this.links[indexDestiny])
				this.removeLink(this.links[indexDestiny]);
		}
	},
	resetGroups:function(model){
		var elementsArray = this.getStatesConnectedToFork(model.id);
		if(elementsArray != null){
			for(var i = 0; i < elementsArray.length; i++){
				if(elementsArray[i].properties.hasGroup){
					elementsArray[i].properties.hasGroup = false;
					elementsArray[i].properties.groupName = null;
					elementsArray[i].properties.branchName = null;
				}
			}
		}
	},
	removeJoin:function(model){
		for(var i = 0; i < this.joins.length; i ++){
			var fork = this.findById(model.id);
			if(this.joins[i].properties.groupName == fork.properties.groupName){
				this.removeCell(this.joins[i]);
			}
		}
	},
	getDesteniesOfState:function(id){
		var arrayDestenies = [];
		var destinyName;
		for(var i = 0; i < this.links.length; i++){
				if((this.findById(this.links[i].destiny)).hasProperties){
					destinyName = (this.findById(this.links[i].destiny)).attributes.name;
					arrayDestenies.push(destinyName);
				}
				else{
					if((this.findById(this.links[i].destiny)).attributes.type == 'Guarda Temporal'){
						destinyName = (this.findById(this.links[i].destiny)).attributes.type;
						arrayDestenies.push(destinyName);
					}
				}
			
		}
		if(arrayDestenies.length > 0){
			return arrayDestenies;
		}else{
			return null;
		}
	},
	getStatesConnectedToFork:function(id){
		var arrayDestenies = [];
		var destinyName;
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].origin == id){
				if((this.findById(this.links[i].destiny)).hasProperties){
					if((this.findById(this.links[i].origin)).attributes.type == 'fsa.Fork' || 
					   (this.findById(this.links[i].origin)).attributes.type == 'fsa.Join'	
					){
						destinyName = (this.findById(this.links[i].destiny));
					}else{
						destinyName = (this.findById(this.links[i].destiny)).attributes.name;
					}
					arrayDestenies.push(destinyName);
				}
			}
		}
		if(arrayDestenies.length > 0){
			return arrayDestenies;
		}else{
			return null;
		}
	},
	getOriginsOfState:function(id){
		var arrayOrigins = [];
		var originName;
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].destiny == id){
				if((this.findById(this.links[i].origin)).hasProperties){
					originName = (this.findById(this.links[i].origin)).attributes.name;
					arrayOrigins.push(originName);
				}
			}
		}
		if(arrayOrigins.length > 0){
			return arrayOrigins;
		}else{
			return null;
		}
	},
	getForkJoinConnections:function(id){ //Si sos un state conectado a fork, devuelve los target del fork
		var array = [];
		var destinyElement;
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].origin == id){
				if((this.findById(this.links[i].destiny)).attributes.type == 'fsa.Fork'){
					array.push(this.findById(this.links[i].destiny));
				}else{
					if((this.findById(this.links[i].destiny)).attributes.type == 'fsa.Join'){
						array.push(this.findById(this.links[i].destiny));
					}
				}
			}
		}
		if(array.length > 0){
			return array;
		}else{
			return null;
		}
	},
	setPreviousConnectionsFromJoin:function(joins){ //If you are a JOIN, this function returns the origins from the join	
		for(var j = 0; j < joins.length; j++){
			for(var i = 0; i < this.links.length; i++){
				if(this.links[i].destiny == joins[j].attributes.id){
					if((this.findById(this.links[i].origin)).attributes.type == 'fsa.State'){
						(this.findById(this.links[i].origin)).properties.stateConnectedToJoinNamed = (this.findById(joins[j].attributes.id)).attributes.name;
					}
				}
			}
		}
	},
	getForwardConnectionsFromJoin:function(id){//If you are a JOIN, this function returns the targets from the join
		var array = [];
		var destinyElement;
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].destiny == id){
				if((this.findById(this.links[i].destiny)).attributes.type == 'fsa.Join'){ //destiny es el join
					for(var i = 0; i < this.links.length; i++){
						if(this.links[i].origin == id){
							if((this.findById(this.links[i].destiny)).attributes.type == 'fsa.State'){
								array.push(this.findById(this.links[i].destiny));
							}
						}
					}
				}
			}
		}
		if(array.length > 0){
			return array;
		}else{
			return null;
		}
	},
	setPathToActualState : function(){
		if(this.forks.length > 0 && this.joins.length > 0){
			this.cleanPathToActualState();
			for(var i = 0; i < this.states.length ; i++){
				var actualState = this.states[i];
				for(var j = 0; j < this.links.length ; j++){
					if(this.links[j].destiny == actualState.attributes.id){
						if(actualState.attributes.type != 'Guarda Temporal'){
							var origin = this.findById(this.links[j].origin);					
							if(origin.attributes.type == 'fsa.State'){				
								if(origin.properties.pathToActualState.length > 0){
									for(var h = 0 ; h < origin.properties.pathToActualState.length ; h++){
										if(actualState.properties.pathToActualState.indexOf(origin.properties.pathToActualState[h]) == -1){
											actualState.properties.pathToActualState.push(origin.properties.pathToActualState[h]);
										}
									}
								}
								actualState.properties.pathToActualState.push(origin.attributes.name);						
							}else{
								if(origin.attributes.type == 'fsa.Join'){
									var statesConnectedToJoin = this.getPreviousConnectionsFromJoin(origin.attributes.id);
									for(var p = 0 ; p < statesConnectedToJoin.length ; p ++){
										actualState.properties.pathToActualState.push(statesConnectedToJoin[p].properties.name);
										if(statesConnectedToJoin[p].properties.pathToActualState.length > 0){
											for(var h = 0 ; h < statesConnectedToJoin[p].properties.pathToActualState.length ; h++){
												if(actualState.properties.pathToActualState.indexOf(statesConnectedToJoin[p].properties.pathToActualState[h]) == -1){
													actualState.properties.pathToActualState.push(statesConnectedToJoin[p].properties.pathToActualState[h]);
												}
											}
										}
									}
								}else{
									if(origin.attributes.type != 'fsa.StartState'){
										var originOfOrigin = this.getOriginsOf(origin.attributes.id);
										if(originOfOrigin.attributes.type == 'fsa.State'){
											actualState.properties.pathToActualState.push(originOfOrigin.properties.name);
											if(originOfOrigin.properties.pathToActualState.length > 0){
												for(var h = 0 ; h < originOfOrigin.properties.pathToActualState.length ; h++){
													if(actualState.properties.pathToActualState.indexOf(originOfOrigin.properties.pathToActualState[h]) == -1){
														actualState.properties.pathToActualState.push(originOfOrigin.properties.pathToActualState[h]);
													}
												}
											}	
										}
									}else{
										j = this.links.length - 1;
									}
								}
							}
						}
					}
				}
			}
		}
	},
	getOriginsOf:function(id){
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].destiny == id){
				if(this.findById(this.links[i].origin)){
					return this.findById(this.links[i].origin);
				}
			}
		}
	},
	getPreviousConnectionsFromJoin:function(id){ //If you are a JOIN, this function returns the origins from the join	
		var statesToJoin = [];
		var actualJoin;
		for(var j = 0; j < this.joins.length; j++){
			if(this.joins[j].attributes.id == id){
				actualJoin = this.joins[j];
			}
		}
		
		for(var i = 0; i < this.links.length; i++){
			if(this.links[i].destiny == actualJoin.attributes.id){
				var stateOrigin = this.findById(this.links[i].origin);
				if(stateOrigin.attributes.type == 'fsa.State'){
					statesToJoin.push(stateOrigin);
				}
			}
		}
		if(statesToJoin.length > 0){
			for(var h = 0; h < statesToJoin.length; h++){
				if(statesToJoin[h].properties.stateConnectedToJoinNamed!=null){
					var forwardConnections = this.getForwardConnectionsFromJoin(actualJoin.attributes.id);
					statesToJoin[h].properties.stateAfterJoinNamed = forwardConnections[0].properties.name;
				}
			}
			return statesToJoin;
		}else{
			return null;
		}
	},
	cleanPathToActualState:function(){
		for(var i = 0; i < this.states.length; i++){
			if(this.states[i].hasProperties){
				this.states[i].properties.pathToActualState = [];
			}
		}
	},
	setPathToActualStateJSON : function(){
		for(var i = 0; i < this.states.length; i++){
			if(this.states[i].hasProperties && this.states[i].properties.pathToActualState.length > 0){
				this.states[i].properties.pathToActualStateJSON = JSON.stringify(this.states[i].properties.pathToActualState);
			}
		}	
	}
};