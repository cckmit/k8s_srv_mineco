var port = null;
var idExtChrome = 'dkfcbigahchomcdelpegjflfecpahooo';
var extFirefox = '1.0.4';

function sendZKEvent(eventName, paramArray) {
	eventTarget = zk.Widget.$(jq('$firmaDocumento')[0]);
	zAu.send(new zk.Event(zk.Widget.$(eventTarget), eventName, paramArray));
}

function notifyFirmaError(errorCode, error, exec) {
	sendZKEvent('onFirmaError', [errorCode, error, exec]);
}

function notifyPrepararPdf(certificados) {
	certificados = depurarJSON(certificados);
	sendZKEvent('onFirmaPrepararPdf', [certificados]);
}

function notifyFirmarDoc(hashFirmado, hashRect) {
	sendZKEvent('onFirmaFirmarDoc', [hashFirmado, hashRect]);
}

function notifyExtError(errorMessage, version) {
	sendZKEvent('onFirmaExtError', [errorMessage, version]);
}

function iniciar() {
	// Chrome
	if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
		iniciarProcesoFirmadoChrome();
		// Firefox
	} else if ('InstallTrigger' in window) {
		if ('iniciarProcesoFirmado' in window && window.getVersionAddon() == extFirefox) {		
			window.iniciarProcesoFirmado();
		} else {
			notifyExtError('requiere-extension-firefox', extFirefox);
		}
	} else {
		alert('Requiere Mozilla Firefox o Chrome para firmar');
	}
};

function iniciarProcesoFirmadoChrome() {
	
	port = chrome.runtime.connect(idExtChrome);
	port.onDisconnect.addListener(function() {notifyExtError('requiere-extension-chrome', null);});
	port.onMessage.addListener(function(request, sender, sendResponse) {
		if (request.response == 'certificados') {
			notifyPrepararPdf(request.certs);
		} else if (request.response == 'firmar') {
			notifyFirmarDoc(request.hashFirmado, request.hashFirmadoRec);
		} else if (request.response == 'error') {
			notifyFirmaError(request.errorCode, request.errorDesc,
					request.exception);
		} else if (request.response == 'disconnectedHost') {
			notifyExtError(request.message, null);
		}
	});
	port.postMessage({'op' : 'certificados'});
};

function enviarHashPage(certAlias, hash64, hashRect64) {
	if ('InstallTrigger' in window) {
		window.enviarHash(certAlias, hash64, hashRect64);
	} else {
		enviarHashChrome(certAlias, hash64, hashRect64);
	}
};

function enviarHashChrome(certi, hash64, hashRect64) {
	var message = {
		'op' : 'firmar',
		'cert' : certi,
		'hash' : hash64,
		'hashRect' : hashRect64
	};
	port.postMessage(message);
};

function instalarTokenFirefox(url) {
	InstallTrigger.install({"Token GCABA": url });
};

function depurarJSON(json) {
	if ('InstallTrigger' in window) {
		return JSON.parse(json);
	} else {
		return json;
	}
};
