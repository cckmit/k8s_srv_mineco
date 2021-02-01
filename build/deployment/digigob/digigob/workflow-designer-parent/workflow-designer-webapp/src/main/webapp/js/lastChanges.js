	var modificationIndex;
	var lastModifications;

	var initModifications = function(){
		modificationIndex=0;
		lastModifications=[];
		saveModification(getProject());
	}

	var getLastModification = function(modIndex){
		var index = modificationIndex + modIndex;
		if(index < 0 || index >= lastModifications.length){
			return null;
		}else{
			modificationIndex = index;
			var lastModification = LZString.decompress(lastModifications[modificationIndex]);
			return JSON.parse(lastModification);
		}
	}
	
	var saveModification = function(jsonData){
		var modifiedJson;
		
		if(jsonData){
			modifiedJson = LZString.compress(JSON.stringify(jsonData));
		}else{
			modifiedJson = LZString.compress(getJson());
		}

		if(lastModifications){
			if(modificationIndex && modificationIndex >= 0 && modificationIndex != lastModifications.length - 1){
				//Si hace una modificacion luego de deshacer una accion
				//Quitamos las modificaciones siguientes
				lastModifications = lastModifications.splice(0, modificationIndex);
			}
	
			if(lastModifications.length == 10){
				//Si llega al limite de modificaciones, comienzo a quitar
				lastModifications.splice(0,1);
			}
			
			lastModifications.push(modifiedJson);
		}else{
			lastModifications = [modifiedJson];
		}
	
		modificationIndex = lastModifications.length - 1;
	}