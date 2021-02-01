package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumentoResponse;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;

public interface IExternalConsultaDocumento2Service {
		
	/**
	 * Consulta un documento en GEDO, validando existencia, y sistema que origina la consulta, en caso de "ARCH" se 
	 * devolvera response sin importar si el usuario posee privilegios reserva
	 * @param request
	 * @return un objeto ResponseExternalConsultaDocumento, con los datos básicos del documento a saber.
	 * 		   - numeroDocumento;
	 *         - usuarioGenerador;
	 *		   - fechaCreacion;
	 * @throws ParametroInvalidoConsultaException
	 * @throws DocumentoNoExisteException
	 *
	 */	
	public ResponseExternalConsultaDocumento consultarDocumentoPorNumero2(String numeroSade, String sistema, String usuario)
		throws ParametroInvalidoConsultaException, DocumentoNoExisteException, 
		ErrorConsultaDocumentoException;
	
	
	/**
	 * Consulta un documento en GEDO, validando existencia, y sistema que origina la consulta, en caso de "ARCH" se 
	 * devolvera response sin importar si el usuario posee privilegios reserva
	 * @param request
	 * @return un objeto ResponseExternalConsultaDocumentoResponse, con los datos bÃ¡sicos del documento a saber.
	 * 		   
	 * @throws ParametroInvalidoConsultaException
	 * 
	 *
	 */	
	public ResponseExternalConsultaDocumentoResponse consultarDocumentoDetalle2(String numeroSade, String sistema, String usuario) throws ParametroInvalidoConsultaException;
	
}
