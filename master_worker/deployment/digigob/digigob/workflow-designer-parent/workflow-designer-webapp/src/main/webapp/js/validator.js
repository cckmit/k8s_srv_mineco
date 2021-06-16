var myValidator = null;

var getValidator = function() {
	if (!myValidator){
		return null;
	}
	return myValidator;
}

var setValidator = function(){
	myValidator = new Validator();	
}

var Validator = function(){}

function isBlank(str) {
    return (!str || 0 === str.length);
}

Validator.prototype = {
	validate:function(){
		
		var validatedProject = {haveStart: false,
								haveEnd:   false,
								haveStates: false,
								haveLinks: false,
								haveGuarda: false,
								stateHasLinks: false,
								startToState: false,
								guardaToEnd: false,
								stateToGuarda: false,
								tLibreHasLinks: false,
								noBlankNames: false,
								stateToTLibre:false,
								forkConnectedToJoin:false,
								joinConnectedToFork:false
								};

		
		if(getProject().startState) validatedProject.haveStart = true;
		if(getProject().endState) validatedProject.haveEnd = true;
		if(getProject().states.length > 0) validatedProject.haveStates = true;
		if(getProject().links.length > 0) validatedProject.haveLinks = true;
		if(getProject().guardaTemporal  || getProject().typeWorkFlow == 'STATE') validatedProject.haveGuarda = true;
		if(this.stateHasLinks()) validatedProject.stateHasLinks = true;
		if(this.startToState())	validatedProject.startToState = true;
		if(this.guardaToEnd() || getProject().typeWorkFlow == 'STATE') validatedProject.guardaToEnd = true;
		if(this.stateToGuarda() || getProject().typeWorkFlow == 'STATE') validatedProject.stateToGuarda = true;
		if(this.tLibreHasLinks() || getProject().typeWorkFlow == 'STATE') validatedProject.tLibreHasLinks = true;
		if(this.stateToTLibre() || getProject().typeWorkFlow == 'STATE') validatedProject.stateToTLibre = true;
		if (!this.hasBlankNames()) validatedProject.noBlankNames = true;
		if (this.forkConnectedToJoin()) validatedProject.forkConnectedToJoin = true;
		if (this.joinConnectedToFork()) validatedProject.joinConnectedToFork = true;
		return validatedProject;
	},
	hasBlankNames:function(){
		if(getProject().states.length > 0){
			for(var i = 0; i < getProject().states.length; i++){

				if (getProject().states[i].attributes.name.trim().length < 1) {
					return true;
				}
			}
		}
		return false;
	},
	stateHasLinks:function(){	
		var stateHasLinks = true;
		if(getProject().states.length > 0 && getProject().links.length > 0){
			var realStates = getProject().states.concat();
			var links = getProject().links;
			
			var encontroOrigen = false;
			var encontroDestino = false;
			
			for(i = 0; i < realStates.length; i++){
				if(!realStates[i].hasProperties){
					realStates.splice(i,1);
					i = 0;
				}
			}
			
			if(links.length > 0){
				for(i = 0; i < realStates.length; i++){
				for(j = 0; j < links.length; j++){
					if(links[j].origin == realStates[i].attributes.id && !encontroOrigen && (getProject().findById(links[j].destiny)).attributes.type != 'Tramitación Libre'){
						encontroOrigen = true;
					}
					else{
						if(links[j].destiny == realStates[i].attributes.id && !encontroDestino && (getProject().findById(links[j].origin)).attributes.type != 'Tramitación Libre'){
							encontroDestino = true;
						}
					}
				}
				
				if(encontroOrigen && encontroDestino){
					 encontroOrigen = false;
					 encontroDestino = false;
				}else{
						encontroOrigen = false;
						encontroDestino = false;
						stateHasLinks = false;
					}
				}
			}
			else{
				stateHasLinks = false;
			}
		}
		return stateHasLinks;
	},
	startToState:function(){
		var startToState = true;
		if(getProject().startState && getProject().states.length > 0 && getProject().links.length > 0){
			startToState = false;
			for(var i = 0; i < getProject().links.length; i++){
				if(getProject().startState && (getProject().startState.attributes.id == getProject().links[i].origin || getProject().startState.attributes.id == getProject().links[i].destiny)){
					var target = getProject().findById(getProject().links[i].destiny);
					if(target.attributes.type = 'fsa.State'){
						startToState = true;
					}
				}
			}
		}
		return startToState;
	},
	guardaToEnd:function(){
		var guardaToEnd = true;
		if(getProject().guardaTemporal && getProject().endState && getProject().links.length > 0){
			var guardaToEnd = false;
			for(var i = 0; i < getProject().links.length; i++){
				if(getProject().endState && getProject().endState.attributes.id == getProject().links[i].destiny){
					var source = getProject().findById(getProject().links[i].origin);
					if(source.attributes.type = 'Guarda Temporal'){
						guardaToEnd = true;
					}
				}
			}
		}
		return guardaToEnd;
	},
	stateToGuarda:function(){
		var stateToGuarda = true;
		if(getProject().states.length > 0 && getProject().guardaTemporal && getProject().links.length > 0){
			stateToGuarda = false;
			var sourceType;
			var targetType;
			
			for(var i = 0; i < getProject().links.length; i++){
					sourceType = (getProject().findById(getProject().links[i].origin)).attributes.type;
					targetType  = (getProject().findById(getProject().links[i].destiny)).attributes.type;					
					if((sourceType == 'fsa.State' && targetType == 'Guarda Temporal')||
					   (sourceType == 'Guarda Temporal' && targetType == 'fsa.State')){
						
						if(sourceType != 'Guarda Temporal'){
							(getProject().findById(getProject().links[i].origin)).properties.acceptCierreExpediente = true;
						}else{
							if(targetType != 'Guarda Temporal'){
								(getProject().findById(getProject().links[i].destiny)).properties.acceptCierreExpediente = true;
							}
						}
						stateToGuarda = true;
					}
			}
		}
		return stateToGuarda;
	},
	tLibreHasLinks:function(){
		var tLibreHasLinks = true;
		var sourceType;
		var targetType;
		
		if(getProject().tramitacionLibre && getProject().links.length > 0){
			tLibreHasLinks = false;
			
			for(var i = 0; i < getProject().links.length; i++){
				sourceType = (getProject().findById(getProject().links[i].origin)).attributes.type;
				targetType  = (getProject().findById(getProject().links[i].destiny)).attributes.type; 
				if(sourceType == 'Tramitación Libre' || targetType == 'Tramitación Libre'){
					tLibreHasLinks = true;
					return tLibreHasLinks;
				}
				else{
					tLibreHasLinks = false;
				}
			}
		}
		return tLibreHasLinks;
	},
	forkConnectedToJoin:function(){
		var forkConnectedToJoin = true;		
		var forks = getProject().forks;
		var joins  = getProject().joins;

		for(var i = 0; i < forks.length; i++){
			
			var flagSameName = false;
			for(var j = 0 ; j < joins.length ; j++){
				if(joins[j].properties.groupName == forks[i].properties.groupName){
					flagSameName = true;
				}
			}
			if(flagSameName == false){
				forkConnectedToJoin = false;
				return forkConnectedToJoin;
			}
		}
		return forkConnectedToJoin;
	},
	joinConnectedToFork:function(){
		var forkConnectedToJoin = true;		
		var forks = getProject().forks;
		var joins  = getProject().joins;

		for(var i = 0; i < joins.length; i++){
			
			var flagSameName = false;
			for(var j = 0 ; j < forks.length ; j++){
				if(forks[j].properties.groupName == joins[i].properties.groupName){
					flagSameName = true;
				}
			}
			if(flagSameName == false){
				forkConnectedToJoin = false;
				return forkConnectedToJoin;
			}
		}
		return forkConnectedToJoin;
	},
	stateToTLibre:function(){
		var stateToTLibre = true;
		var sourceType;
		var targetType;
			
		if(getProject().states.length > 0){			
			for(var i = 0; i < getProject().states.length; i++){
				if(getProject().states[i].properties.acceptTramitacionLibre){
					stateToTLibre = isConnectedToTLibre(getProject().states[i]);
				}
			}
		}
		return stateToTLibre;
	}
}

isConnectedToTLibre = function(state){
	if(getProject().links.length > 0){
		for(var i = 0; i < getProject().links.length; i++){
			
			if((getProject().links[i].origin == state.attributes.id) && (getProject().findById(getProject().links[i].destiny)).attributes.type == 'Tramitación Libre'){
				return true;
			}
			else{
				if((getProject().links[i].destiny == state.attributes.id) && (getProject().findById(getProject().links[i].origin)).attributes.type == 'Tramitación Libre'){
					return true;
				}
			}
		}
	}	
	return false;
}