var mensaje = function(texto) {
	throw new Error('MSG: ' + texto);
}

var Mensaje = function(texto) {
	throw new Error('MSG: ' + texto);
}

var cerrarVentana = function(comp) {
	if (!esWS()) {
		state.closeWindow(comp);
	}
}

var mensajeOK = function(texto){
	if (!esWS()) {
		state.newMessageOK(texto);
	} else {
		print(texto);
	}
}

var mensajeERROR = function(texto){
	if (!esWS()) {
		state.newErrorMessage(texto);
	} else {
		print(texto);
	}
}

var sistemaCreador = function() { 
	return ee.getSistemaCreador();
}

var sistemaApoderado = function() { 
	return ee.getSistemaApoderado();
}

var esWS=function() {
	if (typeof __isWebService__ !=='undefined') {
		return true;
	}
	return false;
}

var invokeRemoteService = function(code, message, typeCall){
	var obj = remoteRestService.invokeRemoteService(code, message, ee, idTransactionFFCC, nameFFCC, typeCall);
}


var blockExpedient = function (){
	remoteRestService.blockExpedient(ee);
}


var validateStructure = function(formName){
	return remoteRestService.validateStructureForm(formName, Form);
}

var initSubProcess = function(nameProcedure){
	return remoteRestService.initSubprocess(ee, nameProcedure);
}


//=================================
// OPERATION FUNCTIONS
//=================================

var invokeRemoteServiceOperation = function(code, message, typeCall){
	var obj = remoteRestService.invokeRemoteServiceOperation(operation,code, message, typeCall);
}


//=================================
// UTILY FUNCTIONS REST CALL
//=================================

function httpRequest(theUrl, data, typeParameter, type){
	// Send request
	print ("==============================");
	print ("SENDING REQUEST");
	print ("HOST: " + theUrl);
	print ("DATA: " +  JSON.stringify(data));
	print ("TYPE: " +  type);
	
    if(typeParameter ==  "query") {
		var con = new java.net.URL((theUrl + "?" + data)).openConnection();	
		con.requestMethod = type;
		return asResponse(con);
	} else  {
		print("BODY");
		var con = new java.net.URL(theUrl).openConnection();
		con.requestMethod = "POST";
		con.setRequestProperty("Content-Type",  "application/json");
		con.doOutput=true;
		write(con.outputStream,JSON.stringify(data));
		return asResponse(con);
	}
 
	print ("==============================");
   
}

function asResponse(con){
    var d = read(con.inputStream);
	var response = {data : d, statusCode : con.responseCode};
	var json = JSON.stringify(response);
	return JSON.parse(json);
}

function write(outputStream, data){
    var wr = new java.io.DataOutputStream(outputStream);
    wr.writeBytes(data);
    wr.flush();
    wr.close();
}

function read(inputStream){
    var inReader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
    var inputLine;
    var response = new java.lang.StringBuffer();

    while ((inputLine = inReader.readLine()) != null) {
           response.append(inputLine);
    }
    inReader.close();
    return response.toString();
}


%s
