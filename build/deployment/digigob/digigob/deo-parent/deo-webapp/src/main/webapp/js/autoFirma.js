function descargarAutoFirma(urlDescarga){

  var x = document.createElement("A");
  
  x.setAttribute("href", urlDescarga);
  x.setAttribute("target", "_blank");
  x.setAttribute("id", "descargar_auto_firma");
  document.body.appendChild(x);
  document.getElementById("descargar_auto_firma").style.display = "none";
  document.getElementById('descargar_auto_firma').click();
  
  var descarga = document.getElementById("descargar_auto_firma");
	descarga.parentNode.removeChild(descarga);

}


function doSign(data, field, page) {

    try{
    	AutoScript.cargarAppAfirma();
    	AutoScript.setServlets(Constants.URL_BASE_SERVICES + "/afirma-signature-storage/StorageService", Constants.URL_BASE_SERVICES + "/afirma-signature-retriever/RetrieveService");

    	AutoScript.sign(
    			(data != undefined && data != null && data != "") ? data : null,
    					"SHA1withRSA",
    					"Adobe PDF",
    					"serverUrl=http://appprueba:8080/afirma-server-triphase-signer/SignatureService\n" +
    					"signatureField="+ field  +"\n" +
    					"signaturePage="+page,
    					signSuccessCallback,
    					signErrorCallback);
    	
    }catch(e){
    	try{
    		console.log("Type: " + AutoScript.getErrorType() + " Message: " + AutoScript.getErrorMessage());    		
    	    sendEventErrorAutoFirma(AutoScript.getErrorType(), AutoScript.getErrorMessage());
    	    
    	}catch(ex){
    		console.log({'exception': ex});
    		sendEventErrorAutoFirma('Exception','Error no previsto');	  
    	}
    }
}

function sendEventErrorAutoFirma(exception, mensajeError){
	
	let mensaje = {'errorType': exception, 'mensajeError': mensajeError};
	
    console.log(mensaje);

    sendZKEvent('onAutoFirmaError', [mensaje]); 
}


function signSuccessCallback(signed, certif){
    sendZKEvent('onAutoFirma', [{'data': signed}]);
}

function signErrorCallback(errorType, errorMessage){
    sendEventErrorAutoFirma(errorType, errorMessage);     
}


function sendZKEvent(eventName, paramArray) {
	eventTarget = zk.Widget.$(jq('$firmaDocumento')[0]);
	zAu.send(new zk.Event(zk.Widget.$(eventTarget), eventName, paramArray));
}


        
