package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;

public interface IExternalConsultaDocumentoServiceExt extends IExternalConsultaDocumentoService {
	
	public ResponseExternalConsultaDocumento consultarPorNumeroReservaTipoDoc(
			RequestExternalConsultaDocumento request)
		throws ParametroInvalidoConsultaException, DocumentoNoExisteException, 
		SinPrivilegiosException, ErrorConsultaDocumentoException;

}
